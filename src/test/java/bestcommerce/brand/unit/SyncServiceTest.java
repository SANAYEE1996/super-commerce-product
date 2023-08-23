package bestcommerce.brand.unit;

import okhttp3.mockwebserver.MockWebServer;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

public class SyncServiceTest {

    @Value("${item.url}")
    private String itemUrl;

    private static MockWebServer mockWebServer;

    @BeforeAll
    static void setup() throws IOException{
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void tearDown() throws IOException{
        mockWebServer.shutdown();
    }

    @BeforeEach
    void init(){

    }

    @Test
    void getTest(){
        WebClient webClient = WebClient.builder()
                .baseUrl(itemUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build()
                .mutate()
                .build();

        String responseDto = webClient
                .get()
                .uri("/product/detail?id=product_1085")
                .retrieve()
                .bodyToMono(String.class).block();

        System.out.println(responseDto);

        responseDto = webClient
                .get()
                .uri("/product/all?page=10&size=20")
                .retrieve()
                .bodyToMono(String.class).block();

        System.out.println(responseDto);
    }
}
