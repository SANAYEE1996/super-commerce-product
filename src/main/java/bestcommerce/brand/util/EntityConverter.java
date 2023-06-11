package bestcommerce.brand.util;

import bestcommerce.brand.manager.dto.BrandRegisterDto;
import bestcommerce.brand.manager.entity.Brand;
import bestcommerce.brand.manager.entity.Manager;
import bestcommerce.brand.product.dto.ProductCreateDto;
import bestcommerce.brand.product.entity.Product;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EntityConverter {

    public Brand toBrandEntity(BrandRegisterDto dto, String logo){
        return new Brand(0L, dto.getBrandName(), dto.getBrandIntro(), dto.getAddress(), logo, LocalDateTime.now().format(TimeFormat.timeFormatter));
    }

    public Product toProductEntity(ProductCreateDto dto, Brand brand, Manager manager){
        return new Product(0L, dto.getProductCode(), dto.getProductName(), dto.getProductPrice(), dto.getProductInfo(), LocalDateTime.now().format(TimeFormat.timeFormatter), brand, manager);
    }
}
