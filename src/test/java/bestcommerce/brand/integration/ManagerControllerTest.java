package bestcommerce.brand.integration;

import bestcommerce.brand.manager.dto.ManagerDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ManagerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("회원 가입 테스트")
    @Test
    public void insertAccountInfoTest() throws Exception {

        String email = "dudtkd0219@gmail.com";
        String password = "1234";
        String name = "박영상";
        String number = "010-1234-5678";

        ManagerDto dto = new ManagerDto(email,password,name,number,"");

        String content = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/manager/register").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
