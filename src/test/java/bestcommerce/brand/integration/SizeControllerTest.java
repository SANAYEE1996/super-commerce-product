package bestcommerce.brand.integration;

import bestcommerce.brand.size.dto.SizeDto;
import bestcommerce.brand.util.TestUtilService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class SizeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestUtilService testUtilService;

    @BeforeEach
    void initial() throws Exception {
        mockMvc = testUtilService.loginWithJwtToken(mockMvc,objectMapper);
    }

    @DisplayName("사이즈 저장 테스트")
    @Test
    public void insertAccountInfoTest() throws Exception {

        List<SizeDto> sizeDtoList = new ArrayList<>();
        sizeDtoList.add(SizeDto.builder().quantityId(5L).bodyName("허리").sizeValue("40").build());
        sizeDtoList.add(SizeDto.builder().quantityId(5L).bodyName("기장").sizeValue("92").build());
        sizeDtoList.add(SizeDto.builder().quantityId(5L).bodyName("허벅지").sizeValue("28").build());

        sizeDtoList.add(SizeDto.builder().quantityId(6L).bodyName("허리").sizeValue("42").build());
        sizeDtoList.add(SizeDto.builder().quantityId(6L).bodyName("기장").sizeValue("96").build());
        sizeDtoList.add(SizeDto.builder().quantityId(6L).bodyName("허벅지").sizeValue("32").build());

        sizeDtoList.add(SizeDto.builder().quantityId(7L).bodyName("허리").sizeValue("44").build());
        sizeDtoList.add(SizeDto.builder().quantityId(7L).bodyName("기장").sizeValue("100").build());
        sizeDtoList.add(SizeDto.builder().quantityId(7L).bodyName("허벅지").sizeValue("36").build());

        sizeDtoList.add(SizeDto.builder().quantityId(8L).bodyName("허리").sizeValue("46").build());
        sizeDtoList.add(SizeDto.builder().quantityId(8L).bodyName("기장").sizeValue("104").build());
        sizeDtoList.add(SizeDto.builder().quantityId(8L).bodyName("허벅지").sizeValue("40").build());

        String content = objectMapper.writeValueAsString(sizeDtoList);

        mockMvc.perform(post("/size/save").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
