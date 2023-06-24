package bestcommerce.brand.integration;

import bestcommerce.brand.product.dto.ProductImageDto;
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
import java.util.ArrayList;
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

        List<MockMultipartFile> fileList2 = Arrays.asList(file3,file4);

        ProductRequestDto dto = ProductRequestDto.builder().productId(7L).build();

        MockMultipartFile mockDto = new MockMultipartFile(
                "ProductRequestDto",
                "ProductRequestDto",
                "application/json",
                objectMapper.writeValueAsString(dto).getBytes(StandardCharsets.UTF_8)
        );

        mockMvc.perform(multipart("/image/product/save")
                        .file(file1)
                        .file(fileList2.get(0))
                        .file(fileList2.get(1))
                        .file(mockDto))
                .andDo(document("image/saveProductImage",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("상품 타이틀 사진 수정")
    @Test
    public void updateProductTitleTest() throws Exception {
        MockMultipartFile file1 = new MockMultipartFile(
                "newProductImage",           // 파라미터 이름
                "space",                    // 파일 이름
                "image/jpg",                          // 파일 타입
                new FileInputStream(fileLocation+"/space.jpg")    // 파일 내용
        );

        MockMultipartFile file2 = new MockMultipartFile(
                "newProductImage",
                "warbler",
                "image/jpg",
                new FileInputStream(fileLocation+"/warbler.jpg")
        );

        List<MockMultipartFile> fileList1 = Arrays.asList(file1,file2);

        List<ProductImageDto> deleteList = new ArrayList<>();
        List<ProductImageDto> updateList = new ArrayList<>();
        ProductRequestDto request = ProductRequestDto.builder().productId(5L).build();

        deleteList.add(ProductImageDto.builder().imageId(13L).build());
        updateList.add(ProductImageDto.builder().imageId(12L).odr(3).build());
        updateList.add(ProductImageDto.builder().imageId(14L).odr(1).build());

        MockMultipartFile deleteListDto = new MockMultipartFile(
                "deleteProductImageList",
                "deleteProductImageList",
                "application/json",
                objectMapper.writeValueAsString(deleteList).getBytes(StandardCharsets.UTF_8)
        );

        MockMultipartFile updateListDto = new MockMultipartFile(
                "updateProductImageList",
                "updateProductImageList",
                "application/json",
                objectMapper.writeValueAsString(updateList).getBytes(StandardCharsets.UTF_8)
        );

        MockMultipartFile requestDto = new MockMultipartFile(
                "ProductRequestDto",
                "ProductRequestDto",
                "application/json",
                objectMapper.writeValueAsString(request).getBytes(StandardCharsets.UTF_8)
        );

        mockMvc.perform(multipart("/image/product/title/update")
                        .file(fileList1.get(0))
                        .file(fileList1.get(1))
                        .file(deleteListDto)
                        .file(updateListDto)
                        .file(requestDto))
                .andDo(document("image/titleUpdate",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
