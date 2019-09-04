package com.money.accounting.backend;

import com.money.accounting.backend.dto.request.ProfitRequest;
import com.money.accounting.backend.exception.MoneyFinanceErrorCode;
import com.money.accounting.backend.model.enums.State;
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
public class CreateProfitTest extends BaseControllerTest {

    @Test
    @WithUserDetails(value = "somebody")
    public void createProfit() throws Exception {
        ProfitRequest request = new ProfitRequest(2, "someName", new BigDecimal(123.5));
        createProfit(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(request.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.moneyCount").value(request.getMoneyCount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value(State.ACTIVE.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateTime").exists());
    }

    @Test
    public void createProfitWithoutLogin() throws Exception {
        ProfitRequest request = new ProfitRequest(2, "someName", new BigDecimal(123.5));
        createProfit(request)
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = "somebody")
    public void createProfitForNotExistingWallet() throws Exception {
        ProfitRequest request = new ProfitRequest(123, "someName", new BigDecimal(123.5));
        try {
            createProfit(request);
        } catch (NestedServletException exception) {
            checkMoneyException(exception, MoneyFinanceErrorCode.WALLET_NOT_EXIST);
        }
    }

    @Test
    @WithUserDetails(value = "somebody")
    public void createProfitForInactiveWallet() throws Exception {
        ProfitRequest request = new ProfitRequest(2, "someName", new BigDecimal(123.5));
        deleteWallet(2);
        try {
            createProfit(request);
        } catch (NestedServletException exception) {
            checkMoneyException(exception, MoneyFinanceErrorCode.WALLET_NOT_ACTIVE);
        }
    }

    @Test
    @WithUserDetails(value = "somebody")
    public void createProfitInvalidName() throws Exception {
        ProfitRequest request = new ProfitRequest(2, "", new BigDecimal(123.5));
        createProfit(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("name"));
        request.setName(null);
        createProfit(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("name"));
    }

    @Test
    @WithUserDetails(value = "somebody")
    public void createProfitInvalidMoneyCount() throws Exception {
        ProfitRequest request = new ProfitRequest(2, "goodName", new BigDecimal(-123));
        createProfit(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("moneyCount"));
        request.setMoneyCount(null);
        createProfit(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("moneyCount"));
    }

}
