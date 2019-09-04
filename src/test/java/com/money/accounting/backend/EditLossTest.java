package com.money.accounting.backend;

import com.money.accounting.backend.dto.request.EditOperationRequest;
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
@Sql(value = {"/clear.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-wallet-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-loss-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clear.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class EditLossTest extends BaseControllerTest {
    private int lossId = 3;

    @Test
    @WithUserDetails("somebody")
    public void editLoss() throws Exception {
        EditOperationRequest request = new EditOperationRequest("newNameNotMatter", new BigDecimal(11));
        editLoss(request, lossId)
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(request.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.moneyCount").value(request.getMoneyCount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateTime").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value(State.ACTIVE.toString()));
    }

    @Test
    public void editLossWithoutLogin() throws Exception {
        EditOperationRequest request = new EditOperationRequest("newNameNotMatter", new BigDecimal(11));
        editLoss(request, lossId)
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = "somebody")
    public void editNotExistingLoss() throws Exception {
        EditOperationRequest request = new EditOperationRequest("newNameNotMatter", new BigDecimal(11));
        try {
            editLoss(request, 123);
        } catch (NestedServletException exception) {
            checkMoneyException(exception, MoneyFinanceErrorCode.LOSS_NOT_EXIST);
        }
    }

    @Test
    @WithUserDetails(value = "somebody")
    public void editLossTooMuchMoney() throws Exception {
        EditOperationRequest request = new EditOperationRequest("newNameNotMatter", new BigDecimal(333));
        try {
            editLoss(request, lossId);
        } catch (NestedServletException exception) {
            checkMoneyException(exception, MoneyFinanceErrorCode.NOT_ENOUGH_MONEY);
        }
    }

    @Test
    @WithUserDetails(value = "somebody")
    public void editLossInvalidName() throws Exception {
        EditOperationRequest request = new EditOperationRequest("", new BigDecimal(11));
        editLoss(request, lossId)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("name"));
        request.setName(null);
        editLoss(request, lossId)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("name"));
    }

    @Test
    @WithUserDetails(value = "somebody")
    public void editLossInvalidMoneyCount() throws Exception {
        EditOperationRequest request = new EditOperationRequest("newNameNotMatter", new BigDecimal(-11));
        editLoss(request, lossId)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("moneyCount"));
        request.setMoneyCount(null);
        editLoss(request, lossId)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("moneyCount"));
    }
}
