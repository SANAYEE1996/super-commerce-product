package bestcommerce.brand.util;

import bestcommerce.brand.manager.dto.LoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
public class TestUtilService {
    @Value("${testUtil.login.id}")
    private String testId;

    @Value("${testUtil.login.pw}")
    private String testPwd;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @RegisterExtension
    final RestDocumentationExtension restDocumentation = new RestDocumentationExtension("custom");

    public MockMvc loginWithJwtToken(MockMvc mockMvc, ObjectMapper objectMapper, RestDocumentationContextProvider restDocumentation) throws Exception{

        String result = mockMvc.perform(post("/manager/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new LoginDto(testId,testPwd))))
                .andReturn().getResponse().getContentAsString();

        String token = new JSONObject(new JSONObject(new JSONObject(result).getString("body")).getString("data")).getString("accessToken");

        return MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(documentationConfiguration(restDocumentation))
                .defaultRequest(get("/").header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .defaultRequest(post("/").header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .build();
    }

    public MockMvc setRestDocumentation(RestDocumentationContextProvider restDocumentation){
        return MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(documentationConfiguration(restDocumentation))
                .build();
    }
}
