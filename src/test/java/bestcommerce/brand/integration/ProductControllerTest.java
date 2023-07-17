package bestcommerce.brand.integration;

import bestcommerce.brand.product.dto.ProductCreateDto;
import bestcommerce.brand.product.dto.ProductInfoDto;
import bestcommerce.brand.product.dto.ProductRequestDto;
import bestcommerce.brand.size.dto.QuantityDto;
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
public class ProductControllerTest {

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

    @DisplayName("상품 저장 테스트")
    @Test
    public void productSaveTest() throws Exception {

        List<QuantityDto> quantityDtoList = new ArrayList<>();
        quantityDtoList.add(new QuantityDto(0L,0L,"S",30));
        quantityDtoList.add(new QuantityDto(0L,0L,"M",40));
        quantityDtoList.add(new QuantityDto(0L,0L,"L",35));
        
        ProductCreateDto dto = ProductCreateDto
                                .builder()
                                .brandId(1L)
                                .managerEmail("nike@gmail.com")
                                .productCode("23aq8d6")
                                .productName("화이트 아이보리 크리미 티셔츠")
                                .productPrice(78000)
                                .productInfo("무슨 색인지 모르겠는 비싸기만 한 티셔츠.")
                                .quantityDtoList(quantityDtoList)
                                .build();

        String content = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/product/save").contentType(MediaType.APPLICATION_JSON).content(content))
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

    @DisplayName("상품 리스트 조회")
    @Test
    void getProductListTest() throws Exception{
        ProductRequestDto dto = ProductRequestDto.builder().brandId(1L).build();

        String content = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/product/list").contentType(MediaType.APPLICATION_JSON).content(content))
                .andDo(document("product/productList",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("상품 검색 조회")
    @Test
    void productSearchTest() throws Exception{
        ProductRequestDto dto = ProductRequestDto.builder().brandId(1L).search("티").build();

        String content = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/product/search").contentType(MediaType.APPLICATION_JSON).content(content))
                .andDo(document("product/searchList",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("상품 업데이트 테스트")
    @Test
    void productUpdateTest() throws Exception{
        ProductInfoDto dto = new ProductInfoDto(5L,null,"완전 이쁜 티샤쓰 신상",
                                        0,null,null,
                                        null,null,null);

        String content = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/product/update").contentType(MediaType.APPLICATION_JSON).content(content))
                .andDo(document("product/updateProduct",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("상품 삭제 테스트")
    @Test
    void productDeleteTest() throws Exception{
        ProductRequestDto dto = ProductRequestDto.builder().productId(8L).build();

        String content = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/product/delete").contentType(MediaType.APPLICATION_JSON).content(content))
                .andDo(document("product/deleteProduct",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
