package bestcommerce.brand.work;

import bestcommerce.brand.product.dto.ProductInfoDto;
import bestcommerce.brand.product.service.ProductService;
import bestcommerce.brand.util.converter.DtoConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class GenerationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private DtoConverter dtoConverter;

    @Test
    void makeProductTest(){
        List<ProductInfoDto> allProductList = dtoConverter.toProductInfoDtoList(productService.findAllProduct());
        for(ProductInfoDto productInfoDto : allProductList){
            System.out.println(productInfoDto.toString());
        }

    }
}
