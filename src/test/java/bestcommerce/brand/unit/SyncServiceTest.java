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
import bestcommerce.brand.sync.SyncService;
import bestcommerce.brand.util.converter.DtoConverter;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SyncServiceTest {

    @Autowired
    private SyncService syncService;

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

    @Test
    @DisplayName("잘 통신하는지 테스트")
    void adminUpdateSyncTest(){
        Long productId = 1234L;
        ProductInfoDto proInfo = productService.getDetailProduct(productId);
        List<QuantityDto> quantityDtoList = dtoConverter.toQuantityDtoList(quantityService.findQuantityList(productId));
        List<SizeDto> sizeDtoList = sizeService.getSizeList(productId);
        List<ProductImageDto> productImageDtoList = dtoConverter.toProductImageDtoList(productImageService.getProductImageList(productId));
        ProductDetailDto productDetailDto = new ProductDetailDto(proInfo, quantityDtoList, sizeDtoList, productImageDtoList);

        try {
            syncService.syncToItemServiceForUpdate(productDetailDto);
        }catch (RuntimeException | ParseException e){
            System.out.println(e.getMessage());
            System.out.println("실패");
        }
    }
}
