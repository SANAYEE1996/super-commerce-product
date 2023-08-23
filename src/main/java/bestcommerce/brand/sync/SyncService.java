package bestcommerce.brand.sync;

import bestcommerce.brand.product.dto.ProductDetailDto;
import bestcommerce.brand.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class SyncService {

    @Value("${item.url}")
    private String itemUrl;

    private final WebClientUtil webClientUtil;

    public void syncToItemServiceForUpdate(ProductDetailDto productDetailDto) throws ParseException, RuntimeException {
        WebClient webClient = webClientUtil.create();

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
}
