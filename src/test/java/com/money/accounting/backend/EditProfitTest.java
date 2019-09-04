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
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-wallet-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-profit-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clear.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class EditProfitTest extends BaseControllerTest {
    private int profitId = 2;

    @Test
    @WithUserDetails("somebody")
    public void editProfit() throws Exception {
        EditOperationRequest request = new EditOperationRequest("newNameNotMatter", new BigDecimal(11));
        editProfit(request, profitId)
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(request.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.moneyCount").value(request.getMoneyCount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateTime").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value(State.ACTIVE.toString()));
    }

    @Test
    public void editProfitWithoutLogin() throws Exception {
        EditOperationRequest request = new EditOperationRequest("newNameNotMatter", new BigDecimal(11));
        editProfit(request, profitId)
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = "somebody")
    public void editNotExistingProfit() throws Exception {
        EditOperationRequest request = new EditOperationRequest("newNameNotMatter", new BigDecimal(11));
        try {
            editProfit(request, 123);
        } catch (NestedServletException exception) {
            checkMoneyException(exception, MoneyFinanceErrorCode.PROFIT_NOT_EXIST);
        }
    }

    @Test
    @WithUserDetails(value = "somebody")
    public void editProfitInvalidName() throws Exception {
        EditOperationRequest request = new EditOperationRequest("", new BigDecimal(11));
        editProfit(request, profitId)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("name"));
        request.setName(null);
        editProfit(request, profitId)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("name"));
    }

    @Test
    @WithUserDetails(value = "somebody")
    public void editProfitInvalidMoneyCount() throws Exception {
        EditOperationRequest request = new EditOperationRequest("newNameNotMatter", new BigDecimal(-11));
        editProfit(request, profitId)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("moneyCount"));
        request.setMoneyCount(null);
        editProfit(request, profitId)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("moneyCount"));
    }

}
