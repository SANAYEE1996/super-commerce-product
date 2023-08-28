package bestcommerce.brand.unit;

import bestcommerce.brand.product.dto.ProductDetailDto;
import bestcommerce.brand.product.service.ProductImageService;
import bestcommerce.brand.product.service.ProductService;
import bestcommerce.brand.size.service.QuantityService;
import bestcommerce.brand.size.service.SizeService;
import bestcommerce.brand.sync.SyncService;
import bestcommerce.brand.util.converter.DtoConverter;
import bestcommerce.brand.util.service.CombinationService;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    @Autowired
    private CombinationService combinationService;

    @Test
    @DisplayName("잘 통신하는지 테스트")
    void adminUpdateSyncTest(){
        Long productId = 1234L;
        ProductDetailDto productDetailDto = combinationService.findProductDetail(productService,quantityService,sizeService,productImageService,dtoConverter,productId);

        try {
            syncService.syncToItemServiceForUpdate(productDetailDto);
        }catch (RuntimeException | ParseException e){
            System.out.println(e.getMessage());
            System.out.println("실패");
        }
    }
}
