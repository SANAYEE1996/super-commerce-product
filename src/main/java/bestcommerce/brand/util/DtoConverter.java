package bestcommerce.brand.util;

import bestcommerce.brand.product.dto.ProductImageDto;
import bestcommerce.brand.product.entity.ProductImage;
import bestcommerce.brand.size.dto.QuantityDto;
import bestcommerce.brand.size.entity.Quantity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DtoConverter {

    @Value("${img.url.header}")
    private String imageHeaderUrl;

    public QuantityDto toQuantityDto(Quantity quantity){
        return new QuantityDto(quantity.getMeasureName(), quantity.getRemain());
    }

    public List<QuantityDto> toQuantityDtoList(List<Quantity> quantityList){
        List<QuantityDto> quantityDtoList = new ArrayList<>();
        for(Quantity quantity : quantityList){
            quantityDtoList.add(toQuantityDto(quantity));
        }
        return quantityDtoList;
    }

    public ProductImageDto toProductImageDto(ProductImage productImage){
        return new ProductImageDto(productImage.getId(), productImage.getType(), imageHeaderUrl+productImage.getImg(), productImage.getOdr());
    }

    public List<ProductImageDto> toProductImageDtoList(List<ProductImage> productImageList){
        List<ProductImageDto> productImageDtoList = new ArrayList<>();
        for(ProductImage productImage : productImageList){
            productImageDtoList.add(toProductImageDto(productImage));
        }
        return productImageDtoList;
    }
}
