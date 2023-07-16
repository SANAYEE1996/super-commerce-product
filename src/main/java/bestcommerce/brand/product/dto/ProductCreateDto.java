package bestcommerce.brand.product.dto;

import bestcommerce.brand.size.dto.QuantityDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProductCreateDto {

    private Long brandId;

    private Long managerId;

    private String managerEmail;

    private String productCode;

    private String productName;

    private int productPrice;

    private String productInfo;

    private List<QuantityDto> quantityDtoList;

}
