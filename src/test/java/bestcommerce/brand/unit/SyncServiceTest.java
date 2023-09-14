package bestcommerce.brand.unit;

import bestcommerce.brand.product.dto.ProductDetailDto;
import bestcommerce.brand.product.service.ProductCombineService;
import bestcommerce.brand.sync.SyncService;
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
    private ProductCombineService productCombineService;

    @Test
    @DisplayName("잘 통신하는지 테스트")
    void adminUpdateSyncTest(){
        Long productId = 1234L;
        ProductDetailDto productDetailDto = productCombineService.getDetailProduct(productId);

        try {
            syncService.syncToItemService(productDetailDto);
        }catch (RuntimeException | ParseException e){
            System.out.println(e.getMessage());
            System.out.println("실패");
        }
    }
}
