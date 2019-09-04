package com.money.accounting.backend;

import com.money.accounting.backend.dto.request.WalletRequest;
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
public class EditWalletTest extends BaseControllerTest {
    private int walletId = 2;

    @Test
    @WithUserDetails("somebody")
    public void editWallet() throws Exception {
        WalletRequest request = new WalletRequest("newSuperName", new BigDecimal(111.44));
        editWallet(request, walletId)
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(request.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.moneyCount").value(request.getMoneyCount()));
    }

    @Test
    public void editWalletWithoutLogin() throws Exception {
        WalletRequest request = new WalletRequest("someName", new BigDecimal(123.5));
        editWallet(request, walletId)
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = "somebody")
    public void editInactiveWallet() throws Exception {
        WalletRequest request = new WalletRequest("someNewName", new BigDecimal(555.66));
        deleteWallet(walletId);
        try {
            editWallet(request, walletId);
        } catch (NestedServletException exception) {
            checkMoneyException(exception, MoneyFinanceErrorCode.WALLET_NOT_ACTIVE);
        }
    }

    @Test
    @WithUserDetails(value = "somebody")
    public void editNotExistingWallet() throws Exception {
        WalletRequest request = new WalletRequest("someNewName", new BigDecimal(555.66));
        try {
            editWallet(request, 123);
        } catch (NestedServletException exception) {
            checkMoneyException(exception, MoneyFinanceErrorCode.WALLET_NOT_EXIST);
        }
    }

    @Test
    @WithUserDetails(value = "somebody")
    public void editWalletInvalidName() throws Exception {
        WalletRequest request = new WalletRequest("", new BigDecimal(123.5));
        editWallet(request, walletId)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("name"));
        request.setName(null);
        editWallet(request, walletId)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("name"));
    }

    @Test
    @WithUserDetails(value = "somebody")
    public void editWalletInvalidMoneyCount() throws Exception {
        WalletRequest request = new WalletRequest("bestWallet", new BigDecimal(-123));
        editWallet(request, walletId)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("moneyCount"));
        request.setMoneyCount(null);
        editWallet(request, walletId)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("moneyCount"));
    }

}
