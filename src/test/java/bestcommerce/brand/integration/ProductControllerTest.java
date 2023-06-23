package bestcommerce.brand.integration;

import bestcommerce.brand.product.dto.ProductCreateDto;
import bestcommerce.brand.product.dto.ProductRequestDto;
import bestcommerce.brand.size.dto.QuantityDto;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class ProductControllerTest {

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
                "deer",                    // 파일 이름
                "image/jpg",                          // 파일 타입
                new FileInputStream(fileLocation+"/deer.jpg")    // 파일 내용
        );

        MockMultipartFile file3 = new MockMultipartFile(
                "infoImage",
                "fauna",
                "image/jpg",
                new FileInputStream(fileLocation+"/fauna.jpg")
        );

        List<MockMultipartFile> fileList1 = Arrays.asList(file1);
        List<MockMultipartFile> fileList2 = Arrays.asList(file3);

        List<QuantityDto> quantityDtoList = new ArrayList<>();
        quantityDtoList.add(new QuantityDto("S",100));
        quantityDtoList.add(new QuantityDto("M",100));
        quantityDtoList.add(new QuantityDto("L",100));
        
        ProductCreateDto dto = ProductCreateDto
                                .builder()
                                .brandId(1L)
                                .managerEmail("nike@gmail.com")
                                .productCode("45aq8d6")
                                .productName("책입니다")
                                .productPrice(24000)
                                .productInfo("무슨 책인지는 모릅니다.")
                                .quantityDtoList(quantityDtoList)
                                .build();
        MockMultipartFile mockDto = new MockMultipartFile(
                "productCreateDto",
                "productCreateDto",
                "application/json",
                objectMapper.writeValueAsString(dto).getBytes(StandardCharsets.UTF_8)
        );

        mockMvc.perform(multipart("/product/save")
                        .file(fileList1.get(0))
                        .file(fileList2.get(0))
                        .file(mockDto))
                .andDo(document("product/saveProduct",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("상품 디테일 조회")
    @Test
    void getDetailViewTest() throws Exception{
        ProductRequestDto dto = ProductRequestDto.builder().productId(3L).build();

        String content = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/product/detail/view").contentType(MediaType.APPLICATION_JSON).content(content))
                .andDo(document("product/detailView",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
