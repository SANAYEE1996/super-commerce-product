package bestcommerce.brand.integration;

import bestcommerce.brand.product.dto.ProductRequestDto;
import bestcommerce.brand.util.TestUtilService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class ProductImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestUtilService testUtilService;

    @RegisterExtension
    final RestDocumentationExtension restDocumentation = new RestDocumentationExtension("build/generated-snippets");

    @Value("${testUtil.fileLocation}")
    private String fileLocation;

    @BeforeEach
    void initial(RestDocumentationContextProvider restDocumentation) throws Exception {
        mockMvc = testUtilService.loginWithJwtToken(mockMvc,objectMapper,restDocumentation);
    }

    @DisplayName("상품 저장 테스트")
    @Test
    public void productSaveTest() throws Exception {
        MockMultipartFile file1 = new MockMultipartFile(
                "productImage",           // 파라미터 이름
                "foxfox",                    // 파일 이름
                "image/jpg",                          // 파일 타입
                new FileInputStream(fileLocation+"/foxfox.jpg")    // 파일 내용
        );

        MockMultipartFile file3 = new MockMultipartFile(
                "infoImage",
                "hay",
                "image/jpg",
                new FileInputStream(fileLocation+"/hay.jpg")
        );

        MockMultipartFile file4 = new MockMultipartFile(
                "infoImage",
                "space",
                "image/jpg",
                new FileInputStream(fileLocation+"/space.jpg")
        );

        List<MockMultipartFile> fileList1 = Arrays.asList(file1);
        List<MockMultipartFile> fileList2 = Arrays.asList(file3,file4);

        ProductRequestDto dto = ProductRequestDto.builder().productId(7L).build();

        MockMultipartFile mockDto = new MockMultipartFile(
                "ProductRequestDto",
                "ProductRequestDto",
                "application/json",
                objectMapper.writeValueAsString(dto).getBytes(StandardCharsets.UTF_8)
        );

        mockMvc.perform(multipart("/image/product/save")
                        .file(fileList1.get(0))
                        .file(fileList2.get(0))
                        .file(fileList2.get(1))
                        .file(mockDto))
                .andDo(document("image/saveProductImage",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
