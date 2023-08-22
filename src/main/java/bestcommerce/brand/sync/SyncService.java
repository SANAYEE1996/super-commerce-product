package bestcommerce.brand.sync;

import bestcommerce.brand.util.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class SyncService {

    @Value("${item.url}")
    private String itemUrl;

    public void syncToItemServiceForUpdate(){
        WebClient webClient = WebClient.builder()
                        .baseUrl(itemUrl)
                        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .build()
                        .mutate()
                        .build();

        ResponseDto responseDto = webClient.post().uri("/product/detail").bodyValue("").retrieve().bodyToMono(ResponseDto.class).block();
    }
}
