package bestcommerce.brand.integration;

import bestcommerce.brand.manager.dto.ManagerDto;
import bestcommerce.brand.util.TestUtilService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ManagerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestUtilService testUtilService;

    @BeforeEach
    void initial() throws Exception {
        mockMvc = testUtilService.loginWithJwtToken(mockMvc,objectMapper);
    }

    @DisplayName("회원 가입 테스트")
    @Test
    public void insertAccountInfoTest() throws Exception {

        String email = "nike@gmail.com";
        String password = "1234";
        String name = "나이키 관리자";
        String number = "010-0001-0001";

        ManagerDto dto = new ManagerDto(email,password,name,number,"");

        String content = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/manager/register").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("로그인 하고 발급된 bearer token으로 다른 api 접속 가능한지 테스트")
    @Test
    void LoginAndAccessAuthenticatedAPITest() throws Exception {

        mockMvc.perform(get("/manager/test"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }
}
