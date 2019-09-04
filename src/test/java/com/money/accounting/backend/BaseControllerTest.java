package com.money.accounting.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.money.accounting.backend.dto.request.EditOperationRequest;
import com.money.accounting.backend.dto.request.LossRequest;
import com.money.accounting.backend.dto.request.ProfitRequest;
import com.money.accounting.backend.dto.request.TransferRequest;
import com.money.accounting.backend.dto.request.WalletRequest;
import com.money.accounting.backend.exception.MoneyFinanceErrorCode;
import com.money.accounting.backend.exception.MoneyFinanceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.NestedServletException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BaseControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    protected static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void checkMoneyException(NestedServletException exception, MoneyFinanceErrorCode errorCode) {
        if (exception.getCause() instanceof MoneyFinanceException) {
            MoneyFinanceException nestedException = (MoneyFinanceException) exception.getCause();
            assertEquals(errorCode, nestedException.getErrorCode());
        } else
            fail();
    }

    protected ResultActions createWallet(WalletRequest request) throws Exception {
        return mockMvc.perform(post("/api/v1/wallet")
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    protected ResultActions createProfit(ProfitRequest request) throws Exception {
        return mockMvc.perform(post("/api/v1/profit")
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    protected ResultActions createLoss(LossRequest request) throws Exception {
        return mockMvc.perform(post("/api/v1/loss")
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    protected ResultActions makeTransfer(TransferRequest request) throws Exception {
        return mockMvc.perform(post("/api/v1/wallet/transfer")
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    protected ResultActions editWallet(WalletRequest request, int walletId) throws Exception {
        return mockMvc.perform(put("/api/v1/wallet/" + walletId)
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    protected ResultActions editProfit(EditOperationRequest request, int profitId) throws Exception {
        return mockMvc.perform(put("/api/v1/profit/" + profitId)
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    protected ResultActions editLoss(EditOperationRequest request, int lossId) throws Exception {
        return mockMvc.perform(put("/api/v1/loss/" + lossId)
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    protected ResultActions deleteWallet(int walletId) throws Exception {
        return mockMvc.perform(delete("/api/v1/wallet/" + walletId)).andDo(print());
    }

    protected ResultActions deleteLoss(int lossId) throws Exception {
        return mockMvc.perform(delete("/api/v1/loss/" + lossId)).andDo(print());
    }

    protected ResultActions deleteProfit(int profitId) throws Exception {
        return mockMvc.perform(delete("/api/v1/profit/" + profitId)).andDo(print());
    }

}
