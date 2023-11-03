package bestcommerce.brand.util.converter;

import bestcommerce.brand.product.dto.ProductImageDto;
import bestcommerce.brand.product.dto.ProductInfoDto;
import bestcommerce.brand.product.entity.Product;
import bestcommerce.brand.product.entity.ProductImage;
import bestcommerce.brand.size.dto.QuantityDto;
import bestcommerce.brand.size.entity.Quantity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DtoConverter {

    @Value("${img.url.header}")
    private String imageHeaderUrl;

    public QuantityDto toQuantityDto(Quantity quantity){
        return new QuantityDto(quantity.getId(), quantity.getProduct().getId(), quantity.getMeasureName(), quantity.getRemain());
    }

    public List<QuantityDto> toQuantityDtoList(List<Quantity> quantityList){
        return quantityList.stream().map(this::toQuantityDto).collect(Collectors.toList());
    }

    public ProductImageDto toProductImageDto(ProductImage productImage){
        return new ProductImageDto(productImage.getProduct().getId(), productImage.getId(), productImage.getType(), imageHeaderUrl+productImage.getImg(), productImage.getOdr());
    }

    public List<ProductImageDto> toProductImageDtoList(List<ProductImage> productImageList){
        return productImageList.stream().map(this::toProductImageDto).collect(Collectors.toList());
    }

    public List<ProductInfoDto> toProductInfoDtoList(List<Product> productList){
        return productList.stream().map(this::toProductInfoDto).collect(Collectors.toList());
    }

    public ProductInfoDto toProductInfoDto(Product product){
        return ProductInfoDto.builder()
                .id(product.getId())
                .productName(product.getName())
                .productPrice(product.getProductPrice())
                .productInfo(product.getInfo())
                .productRegisterDate(product.getRegisterDate())
                .build();
    }
}
