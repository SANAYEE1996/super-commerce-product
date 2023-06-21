package bestcommerce.brand.integration;

import bestcommerce.brand.manager.dto.LoginDto;
import bestcommerce.brand.manager.dto.ManagerDto;
import bestcommerce.brand.util.TestUtilService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class ManagerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestUtilService testUtilService;

    @RegisterExtension
    final RestDocumentationExtension restDocumentation = new RestDocumentationExtension("build/generated-snippets");

    @BeforeEach
    void initial(RestDocumentationContextProvider restDocumentation){
        mockMvc = testUtilService.setRestDocumentation(restDocumentation);
    }

    @DisplayName("회원 가입 테스트")
    @Test
    public void insertAccountInfoTest() throws Exception {

        String email = "adidas@gmail.com";
        String password = "1234";
        String name = "아디다스 관리자";
        String number = "010-0001-0021";

        ManagerDto dto = new ManagerDto(email,password,name,number,"");

        String content = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/manager/register").contentType(MediaType.APPLICATION_JSON).content(content))
                .andDo(document("manager/managerRegister",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("로그인")
    @Test
    void loginTest() throws Exception{
        LoginDto dto = new LoginDto("adidas@gmail.com","1234");

        String content = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/manager/login").contentType(MediaType.APPLICATION_JSON).content(content))
                .andDo(document("manager/login",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
