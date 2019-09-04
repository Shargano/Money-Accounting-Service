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
@Sql(value = {"/clear.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CreateWalletTest extends BaseControllerTest {

    @Test
    public void createWallet() throws Exception {
        WalletRequest request = new WalletRequest("someName", new BigDecimal(123.5));
        createWallet(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(request.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.moneyCount").value(request.getMoneyCount()));
    }

    @Test
    public void createWalletWithoutLogin() throws Exception {
        WalletRequest request = new WalletRequest("someName", new BigDecimal(123.5));
        createWallet(request)
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void createWalletAlreadyExistingWallet() throws Exception {
        WalletRequest request = new WalletRequest("someName", new BigDecimal(123.5));
        createWallet(request);
        try {
            createWallet(request);
        } catch (NestedServletException exception) {
            checkMoneyException(exception, MoneyFinanceErrorCode.WALLET_ALREADY_EXISTS);
        }
    }

    @Test
    @WithUserDetails(value = "somebody")
    public void createWalletInvalidName() throws Exception {
        WalletRequest request = new WalletRequest("", new BigDecimal(123.5));
        createWallet(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("name"));
        request.setName(null);
        createWallet(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("name"));
    }

    @Test
    @WithUserDetails(value = "somebody")
    public void createWalletInvalidMoneyCount() throws Exception {
        WalletRequest request = new WalletRequest("bestWallet", new BigDecimal(-123));
        createWallet(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("moneyCount"));
        request.setMoneyCount(null);
        createWallet(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("moneyCount"));
    }

}
