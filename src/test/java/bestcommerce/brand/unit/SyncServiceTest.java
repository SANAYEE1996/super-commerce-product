package bestcommerce.brand.unit;

import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest
public class SyncServiceTest {

    @Value("${item.url}")
    private String itemUrl;

    @Test
    void postTest(){
        WebClient webClient = WebClient.builder()
                .baseUrl(itemUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build()
                .mutate()
                .build();

        String responseDto = webClient
                .get()
                .uri("/product/detail?id=product_100")
                .retrieve()
                .bodyToMono(String.class).block();

        System.out.println(responseDto);
    }
}
