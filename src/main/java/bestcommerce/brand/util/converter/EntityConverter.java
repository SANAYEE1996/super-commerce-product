package bestcommerce.brand.util.converter;

import bestcommerce.brand.product.dto.ProductCreateDto;
import bestcommerce.brand.product.entity.Product;
import bestcommerce.brand.util.TimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EntityConverter {

    public Product toProductEntity(ProductCreateDto dto, Long brand, Long manager){
        return new Product(0L, dto.getProductCode(), dto.getProductName(), dto.getProductPrice(), dto.getProductInfo(), LocalDateTime.now().format(TimeFormat.timeFormatter), null, brand, manager);
    }
}
