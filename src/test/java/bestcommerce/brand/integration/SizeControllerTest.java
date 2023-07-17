package bestcommerce.brand.integration;

import bestcommerce.brand.product.dto.ProductRequestDto;
import bestcommerce.brand.size.dto.SizeApiDto;
import bestcommerce.brand.size.dto.SizeDto;
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
public class SizeControllerTest {

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

    @DisplayName("사이즈 저장 테스트")
    @Test
    public void insertSizeTest() throws Exception {

        ProductRequestDto dto = ProductRequestDto.builder().productId(4L).build();

        List<SizeDto> sizeDtoList = new ArrayList<>();
        sizeDtoList.add(SizeDto.builder().quantityId(9L).bodyName("기장").sizeValue("100").build());
        sizeDtoList.add(SizeDto.builder().quantityId(9L).bodyName("허리").sizeValue("38").build());
        sizeDtoList.add(SizeDto.builder().quantityId(9L).bodyName("밑단").sizeValue("28").build());

        sizeDtoList.add(SizeDto.builder().quantityId(10L).bodyName("기장").sizeValue("102").build());
        sizeDtoList.add(SizeDto.builder().quantityId(10L).bodyName("허리").sizeValue("42").build());
        sizeDtoList.add(SizeDto.builder().quantityId(10L).bodyName("밑단").sizeValue("32").build());

        sizeDtoList.add(SizeDto.builder().quantityId(11L).bodyName("기장").sizeValue("105").build());
        sizeDtoList.add(SizeDto.builder().quantityId(11L).bodyName("허리").sizeValue("46").build());
        sizeDtoList.add(SizeDto.builder().quantityId(11L).bodyName("밑단").sizeValue("36").build());


        SizeApiDto sizeApiDto = new SizeApiDto(dto, sizeDtoList);

        String content = objectMapper.writeValueAsString(sizeApiDto);

        mockMvc.perform(post("/size/save").contentType(MediaType.APPLICATION_JSON).content(content))
                .andDo(document("size/saveSize",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("사이즈 업데이트 테스트")
    @Test
    public void updateSizeTest() throws Exception {

        ProductRequestDto dto = ProductRequestDto.builder().productId(3L).build();

        List<SizeDto> sizeDtoList = new ArrayList<>();
        sizeDtoList.add(SizeDto.builder().quantityId(5L).bodyName("어깨").sizeValue("48").build());
        sizeDtoList.add(SizeDto.builder().quantityId(5L).bodyName("소매").sizeValue("92").build());
        sizeDtoList.add(SizeDto.builder().quantityId(5L).bodyName("기장").sizeValue("65").build());

        sizeDtoList.add(SizeDto.builder().quantityId(6L).bodyName("어깨").sizeValue("52").build());
        sizeDtoList.add(SizeDto.builder().quantityId(6L).bodyName("소매").sizeValue("96").build());
        sizeDtoList.add(SizeDto.builder().quantityId(6L).bodyName("기장").sizeValue("70").build());

        sizeDtoList.add(SizeDto.builder().quantityId(7L).bodyName("어깨").sizeValue("56").build());
        sizeDtoList.add(SizeDto.builder().quantityId(7L).bodyName("소매").sizeValue("100").build());
        sizeDtoList.add(SizeDto.builder().quantityId(7L).bodyName("기장").sizeValue("75").build());

        sizeDtoList.add(SizeDto.builder().quantityId(8L).bodyName("어깨").sizeValue("60").build());
        sizeDtoList.add(SizeDto.builder().quantityId(8L).bodyName("소매").sizeValue("110").build());
        sizeDtoList.add(SizeDto.builder().quantityId(8L).bodyName("기장").sizeValue("80").build());

        SizeApiDto sizeApiDto = new SizeApiDto(dto, sizeDtoList);

        String content = objectMapper.writeValueAsString(sizeApiDto);

        mockMvc.perform(post("/size/update")
                        .contentType(MediaType.APPLICATION_JSON).content(content))
                .andDo(document("size/updateSize",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
