package com.money.accounting.backend;

import com.money.accounting.backend.dto.request.LossRequest;
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
public class CreateLossTest extends BaseControllerTest {

    @Test
    @WithUserDetails(value = "somebody")
    public void createLoss() throws Exception {
        LossRequest request = new LossRequest(2, "lossName", new BigDecimal(13.55));
        createLoss(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(request.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.moneyCount").value(request.getMoneyCount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value(State.ACTIVE.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateTime").exists());
    }

    @Test
    public void createLossWithoutLogin() throws Exception {
        LossRequest request = new LossRequest(2, "lossName", new BigDecimal(13.55));
        createLoss(request)
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = "somebody")
    public void createLossForNotExistingWallet() throws Exception {
        LossRequest request = new LossRequest(123, "lossName", new BigDecimal(13.55));
        try {
            createLoss(request);
        } catch (NestedServletException exception) {
            checkMoneyException(exception, MoneyFinanceErrorCode.WALLET_NOT_EXIST);
        }
    }

    @Test
    @WithUserDetails(value = "somebody")
    public void createLossForInactiveWallet() throws Exception {
        LossRequest request = new LossRequest(2, "lossName", new BigDecimal(13.55));
        deleteWallet(2);
        try {
            createLoss(request);
        } catch (NestedServletException exception) {
            checkMoneyException(exception, MoneyFinanceErrorCode.WALLET_NOT_ACTIVE);
        }
    }

    @Test
    @WithUserDetails(value = "somebody")
    public void createTooMuckMoneyLoss() throws Exception {
        LossRequest request = new LossRequest(2, "lossName", new BigDecimal(444));
        try {
            createLoss(request);
        } catch (NestedServletException exception) {
            checkMoneyException(exception, MoneyFinanceErrorCode.NOT_ENOUGH_MONEY);
        }
    }

    @Test
    @WithUserDetails(value = "somebody")
    public void createLossInvalidName() throws Exception {
        LossRequest request = new LossRequest(2, "", new BigDecimal(13.55));
        createLoss(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("name"));
        request.setName(null);
        createLoss(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("name"));
    }

    @Test
    @WithUserDetails(value = "somebody")
    public void createLossInvalidMoneyCount() throws Exception {
        LossRequest request = new LossRequest(2, "lossName", new BigDecimal(-13.55));
        createLoss(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("moneyCount"));
        request.setMoneyCount(null);
        createLoss(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("moneyCount"));
    }
}
