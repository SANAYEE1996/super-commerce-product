package bestcommerce.brand.util.service;

import bestcommerce.brand.size.dto.QuantityDto;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class QuantityCheck {

    public void saveQuantityCheck(List<QuantityDto> dtoList) throws RuntimeException{
        for(QuantityDto quantityDto : dtoList){
            if(quantityDto.getProductId() == null) {
                throw new RuntimeException("Product Id is Null");
            }
            else if(!StringUtils.hasText(quantityDto.getQuantityName())) {
                throw new RuntimeException("Quantity Name is Empty");
            }
        }
    }

    public void updateDeleteQuantityCheck(List<QuantityDto> dtoList) throws RuntimeException{
        for(QuantityDto quantityDto : dtoList){
            if(quantityDto.getQuantityId() == null) {
                throw new RuntimeException("Quantity Id is Null");
            }
        }
    }
}
