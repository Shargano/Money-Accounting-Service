package com.money.accounting.backend;

import com.money.accounting.backend.exception.MoneyFinanceErrorCode;
import com.money.accounting.backend.model.enums.State;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.NestedServletException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@Sql(value = {"/clear.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-wallet-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-loss-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clear.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ArchiveLossTest extends BaseControllerTest {
    private int lossId = 3;
    private int walletId = 2;

    @Test
    @WithUserDetails("somebody")
    public void archiveLoss() throws Exception {
        deleteLoss(lossId)
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value(State.REMOVED.toString()));
    }

    @Test
    public void archiveLossWithoutLogin() throws Exception {
        deleteLoss(lossId)
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails("somebody")
    public void archiveNotExistingLoss() throws Exception {
        try {
            deleteLoss(123);
        } catch (NestedServletException exception) {
            checkMoneyException(exception, MoneyFinanceErrorCode.LOSS_NOT_EXIST);
        }
    }

    @Test
    @WithUserDetails("somebody")
    public void archiveInactiveLoss() throws Exception {
        deleteLoss(lossId);
        try {
            deleteLoss(lossId);
        } catch (NestedServletException exception) {
            checkMoneyException(exception, MoneyFinanceErrorCode.LOSS_NOT_ACTIVE);
        }
    }

    @Test
    @WithUserDetails("somebody")
    public void archiveLossInactiveWallet() throws Exception {
        deleteWallet(walletId);
        try {
            deleteLoss(lossId);
        } catch (NestedServletException exception) {
            checkMoneyException(exception, MoneyFinanceErrorCode.WALLET_NOT_ACTIVE);
        }
    }

}
