package bestcommerce.brand.unit;

import bestcommerce.brand.product.dto.ProductDetailDto;
import bestcommerce.brand.product.dto.ProductImageDto;
import bestcommerce.brand.product.dto.ProductInfoDto;
import bestcommerce.brand.product.service.ProductImageService;
import bestcommerce.brand.product.service.ProductService;
import bestcommerce.brand.size.dto.QuantityDto;
import bestcommerce.brand.size.dto.SizeDto;
import bestcommerce.brand.size.service.QuantityService;
import bestcommerce.brand.size.service.SizeService;
import bestcommerce.brand.util.converter.DtoConverter;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;


@SpringBootTest
public class SyncServiceTest {

    @Value("${item.url}")
    private String itemUrl;

    @Autowired
    private ProductService productService;

    @Autowired
    private QuantityService quantityService;

    @Autowired
    private SizeService sizeService;

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private DtoConverter dtoConverter;

    private WebClient webClient;

    @BeforeEach
    void init(){
        webClient = WebClient.builder()
                .baseUrl(itemUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build()
                .mutate()
                .build();
    }

    @Test
    void getTest(){
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
    
    @Test
    @DisplayName("잘 통신하는지 테스트")
    void adminSyncTest(){
        Long productId = 1234L;
        ProductInfoDto proInfo = productService.getDetailProduct(productId);
        List<QuantityDto> quantityDtoList = dtoConverter.toQuantityDtoList(quantityService.findQuantityList(productId));
        List<SizeDto> sizeDtoList = sizeService.getSizeList(productId);
        List<ProductImageDto> productImageDtoList = dtoConverter.toProductImageDtoList(productImageService.getProductImageList(productId));
        ProductDetailDto productDetailDto = new ProductDetailDto(proInfo, quantityDtoList, sizeDtoList, productImageDtoList);

        String responseDto = webClient
                .post()
                .uri("/admin/save")
                .bodyValue(productDetailDto)
                .retrieve()
                .bodyToMono(String.class).block();

        System.out.println(responseDto);
    }
}
