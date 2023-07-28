package bestcommerce.brand.integration;

import bestcommerce.brand.size.dto.QuantityDto;
import bestcommerce.brand.size.dto.QuantityModifyDto;
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

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class QuantityControllerTest {

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

    @DisplayName("수량 저장 테스트")
    @Test
    void saveNewQuantityTest() throws Exception{
        List<QuantityDto> dtoList = new ArrayList<>();
        dtoList.add(new QuantityDto(null,7L,"S",40));
        dtoList.add(new QuantityDto(null,7L,"M",40));

        String content = objectMapper.writeValueAsString(dtoList);

        mockMvc.perform(post("/quantity/save").contentType(MediaType.APPLICATION_JSON).content(content))
                .andDo(document("quantity/saveNewQuantity",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("수량 수정 테스트")
    @Test
    void updateQuantityTest() throws Exception{
        List<QuantityModifyDto> dtoList = new ArrayList<>();
        dtoList.add(new QuantityModifyDto(21L,"XS",50));
        dtoList.add(new QuantityModifyDto(22L,"",70));

        String content = objectMapper.writeValueAsString(dtoList);

        mockMvc.perform(post("/quantity/update").contentType(MediaType.APPLICATION_JSON).content(content))
                .andDo(document("quantity/updateQuantity",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("수량 삭제 테스트")
    @Test
    void deleteQuantityTest() throws Exception{
        List<QuantityModifyDto> dtoList = new ArrayList<>();
        dtoList.add(new QuantityModifyDto(21L,"",0));
        dtoList.add(new QuantityModifyDto(22L,"",0));

        String content = objectMapper.writeValueAsString(dtoList);

        mockMvc.perform(post("/quantity/delete").contentType(MediaType.APPLICATION_JSON).content(content))
                .andDo(document("quantity/deleteQuantity",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
