package com.money.accounting.backend;

import com.money.accounting.backend.dto.request.TransferRequest;
import com.money.accounting.backend.exception.MoneyFinanceErrorCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.NestedServletException;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-wallet-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clear.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TransferTest extends BaseControllerTest {
    private int walletFromId = 2;
    private int walletToId = 3;

    @Test
    @WithUserDetails(value = "somebody")
    public void makeTransfer() throws Exception {
        TransferRequest request = new TransferRequest(walletFromId, walletToId, new BigDecimal(23.55));
        makeTransfer(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.moneyCount").value(new BigDecimal(23.55)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.walletFrom.moneyCount").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.walletTo.moneyCount").exists());
    }

    @Test
    public void makeTransferWithoutLogin() throws Exception {
        TransferRequest request = new TransferRequest(walletFromId, walletToId, new BigDecimal(23.55));
        makeTransfer(request)
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = "somebody")
    public void createTransferForNotExistingWallet() throws Exception {
        TransferRequest request = new TransferRequest(123, walletToId, new BigDecimal(23.55));
        try {
            makeTransfer(request);
        } catch (NestedServletException exception) {
            checkMoneyException(exception, MoneyFinanceErrorCode.WALLET_NOT_EXIST);
        }
    }

    @Test
    @WithUserDetails(value = "somebody")
    public void createTransferForInactiveWallet() throws Exception {
        TransferRequest request = new TransferRequest(walletFromId, walletToId, new BigDecimal(23.55));
        deleteWallet(walletToId);
        try {
            makeTransfer(request);
        } catch (NestedServletException exception) {
            checkMoneyException(exception, MoneyFinanceErrorCode.WALLET_NOT_ACTIVE);
        }
    }

    @Test
    @WithUserDetails(value = "somebody")
    public void createTooMuchMoneyTransfer() throws Exception {
        TransferRequest request = new TransferRequest(walletFromId, walletToId, new BigDecimal(623.55));
        try {
            makeTransfer(request);
        } catch (NestedServletException exception) {
            checkMoneyException(exception, MoneyFinanceErrorCode.NOT_ENOUGH_MONEY);
        }
    }

    @Test
    @WithUserDetails(value = "somebody")
    public void createTransferInvalidMoneyCount() throws Exception {
        TransferRequest request = new TransferRequest(walletFromId, walletToId, new BigDecimal(-23.55));
        makeTransfer(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("moneyCount"));
        request.setMoneyCount(null);
        makeTransfer(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("moneyCount"));
    }

}
