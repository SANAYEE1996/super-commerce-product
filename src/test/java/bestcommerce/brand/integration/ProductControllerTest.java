package bestcommerce.brand.integration;

import bestcommerce.brand.product.dto.ProductCreateDto;
import bestcommerce.brand.size.dto.QuantityDto;
import bestcommerce.brand.util.TestUtilService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestUtilService testUtilService;

    @Value("${testUtil.fileLocation}")
    private String fileLocation;

    @BeforeEach
    void initial() throws Exception {
        mockMvc = testUtilService.loginWithJwtToken(mockMvc,objectMapper);
    }

    @DisplayName("MultipartFile 팡팡 테스트")
    @Test
    public void MultipartFileTest() throws Exception {
        MockMultipartFile file1 = new MockMultipartFile(
                "productImage",           // 파라미터 이름
                "bear",                    // 파일 이름
                "jpg",                          // 파일 타입
                new FileInputStream(fileLocation+"/bear.jpg")    // 파일 내용
        );

        MockMultipartFile file2 = new MockMultipartFile(
                "productImage",           // 파라미터 이름
                "flowers",                    // 파일 이름
                "jpg",                          // 파일 타입
                new FileInputStream(fileLocation+"/flowers.png")      // 파일 내용
        );

        MockMultipartFile file3 = new MockMultipartFile(
                "infoImage",              // 파라미터 이름
                "milk",                    // 파일 이름
                "jpg",                          // 파일 타입
                new FileInputStream(fileLocation+"/milk.png")     // 파일 내용
        );

        List<MockMultipartFile> fileList1 = Arrays.asList(file1, file2);
        List<MockMultipartFile> fileList2 = Arrays.asList(file3);

        mockMvc.perform(multipart("/product/test")
                        .file(fileList1.get(0))
                        .file(fileList1.get(1))
                        .file(fileList2.get(0)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("MultipartFile 팡팡 테스트")
    @Test
    public void productSaveTest() throws Exception {
        MockMultipartFile file1 = new MockMultipartFile(
                "productImage",           // 파라미터 이름
                "bear",                    // 파일 이름
                "jpg",                          // 파일 타입
                new FileInputStream(fileLocation+"/bear.jpg")    // 파일 내용
        );

        MockMultipartFile file2 = new MockMultipartFile(
                "productImage",           // 파라미터 이름
                "flowers",                    // 파일 이름
                "jpg",                          // 파일 타입
                new FileInputStream(fileLocation+"/flowers.png")      // 파일 내용
        );

        MockMultipartFile file3 = new MockMultipartFile(
                "infoImage",
                "milk",
                "jpg",
                new FileInputStream(fileLocation+"/milk.png")
        );

        List<MockMultipartFile> fileList1 = Arrays.asList(file1, file2);
        List<MockMultipartFile> fileList2 = Arrays.asList(file3);

        List<QuantityDto> quantityDtoList = new ArrayList<>();
        quantityDtoList.add(new QuantityDto("S",100));
        quantityDtoList.add(new QuantityDto("M",100));
        quantityDtoList.add(new QuantityDto("L",100));
        quantityDtoList.add(new QuantityDto("XL",100));
        ProductCreateDto dto = ProductCreateDto
                                .builder()
                                .brandId(1L)
                                .managerEmail("nike@gmail.com")
                                .productCode("j1q84zl8")
                                .productName("나이키 퀀텀 슬립 퀸즈 나이트")
                                .productPrice(170000)
                                .productInfo("이것은 나이키의 혁명 나이키는 최고 하지만 나에게 나이키 신발은 없다!")
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
                        .file(fileList1.get(1))
                        .file(fileList2.get(0))
                        .file(mockDto))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
