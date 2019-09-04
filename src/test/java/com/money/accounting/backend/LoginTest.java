package com.money.accounting.backend;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clear.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class LoginTest extends BaseControllerTest {

    @Test
    public void accessDeniedTest() throws Exception {
        mockMvc.perform(get("/api/v1/ping"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = "somebody")
    public void loginUser() throws Exception {
        mockMvc.perform(get("/api/v1/ping"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(authenticated());
    }
}
