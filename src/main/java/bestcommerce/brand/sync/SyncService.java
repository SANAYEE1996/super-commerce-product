package bestcommerce.brand.sync;

import bestcommerce.brand.product.dto.ProductDetailDto;
import bestcommerce.brand.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SyncService {

    private final WebClientUtil webClientUtil;

    private final WebClient webClient = webClientUtil.create();

    public void syncToItemService(ProductDetailDto productDetailDto) throws ParseException, RuntimeException {

        String responseDto = webClient
                .post()
                .uri("/admin/save")
                .bodyValue(productDetailDto)
                .retrieve()
                .bodyToMono(String.class).block();

        JSONObject jsonObj = (JSONObject) new JSONParser().parse(responseDto);

        String syncResponseStatus = jsonObj.get("code").toString();

        if(!"200".equals(syncResponseStatus)){
            throw new RuntimeException("동기화 실패");
        }
    }

    public void syncBatchToItemService(List<ProductDetailDto> productDetailDtoList) throws ParseException, RuntimeException {

        String responseDto = webClient
                .post()
                .uri("/admin/saveAll")
                .bodyValue(productDetailDtoList)
                .retrieve()
                .bodyToMono(String.class).block();

        JSONObject jsonObj = (JSONObject) new JSONParser().parse(responseDto);

        String syncResponseStatus = jsonObj.get("code").toString();

        if(!"200".equals(syncResponseStatus)){
            throw new RuntimeException("동기화 실패");
        }
    }
}
