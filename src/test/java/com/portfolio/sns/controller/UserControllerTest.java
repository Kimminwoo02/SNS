package com.portfolio.sns.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.sns.controller.request.UserJoinRequest;
import com.portfolio.sns.controller.request.UserLoginRequest;
import com.portfolio.sns.exception.ErrorCode;
import com.portfolio.sns.exception.SnsApplicationException;
import com.portfolio.sns.fixture.UserEntityFixture;
import com.portfolio.sns.model.User;
import com.portfolio.sns.model.entity.UserEntity;
import com.portfolio.sns.repository.UserEntityRepository;
import com.portfolio.sns.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.beans.Encoder;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;
    @Autowired
    private UserEntityRepository userEntityRepository;

    @Test
    public void ????????????() throws Exception {
        String userName = "userName";
        String password = "password";


        when(userService.join(userName,password)).thenReturn(mock(User.class));

        mockMvc.perform(post("/api/v1/users/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName,password)))
        ).andDo(print())
                .andExpect(status().isOk());
    }

@Test
    public void ???????????????_??????_???????????????_UserName??????_????????????????????????_????????????() throws Exception{
        String userName = "userName";
        String password = "password";


        when(userService.join(userName,password)).thenThrow(new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME));

        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName,password)))
                ).andDo(print())
                .andExpect(status().isConflict());
    }



    @Test
    public void ?????????() throws Exception {
        String userName = "userName";
        String password = "password";

        when(userService.login(userName,password)).thenReturn("test_token");

        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName,password)))
                ).andDo(print())
                .andExpect(status().isOk());

    }
    @Test
    public void ????????????_???????????????_??????_userNAme???_???????????????_????????????() throws Exception {
        String userName = "userName";
        String password = "password";

        when(userService.login(userName,password)).thenThrow(new SnsApplicationException(ErrorCode.USER_NOT_FOUND));

        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName,password)))
                ).andDo(print())
                .andExpect(status().isNotFound());

    }
    @Test
    public void ????????????_??????_password???_???????????????_????????????() throws Exception {
        String userName = "userName";
        String password = "password";

        when(userService.login(userName,password)).thenThrow(new SnsApplicationException(ErrorCode.INVALID_PASSWORD));

        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName,password)))
                ).andDo(print())
                .andExpect(status().isUnauthorized());

    }
    @Test
    @WithMockUser
    void ????????????() throws Exception{
        when(userService.alarmList(any(), any())).thenReturn(Page.empty());

        mockMvc.perform(get("/api/v1/users/alarm")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void ????????????????????????_???????????????_????????????() throws Exception{
        when(userService.alarmList(any(), any())).thenReturn(Page.empty());

        mockMvc.perform(get("/api/v1/users/alarm")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

}
