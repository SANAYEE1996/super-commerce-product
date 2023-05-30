package bestcommerce.brand.manager.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BrandRegisterDto {

    private Long managerId;

    private String brandName;

    private String brandIntro;

    private String address;

    public BrandRegisterDto(Long managerId, String brandName, String brandIntro, String address) {
        this.managerId = managerId;
        this.brandName = brandName;
        this.brandIntro = brandIntro;
        this.address = address;
    }
}
