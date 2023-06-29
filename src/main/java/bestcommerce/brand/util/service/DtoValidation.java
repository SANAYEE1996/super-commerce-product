package bestcommerce.brand.util.service;

import bestcommerce.brand.size.dto.QuantityDto;
import bestcommerce.brand.size.entity.Quantity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

@Component
public class DtoValidation {

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

    public void sizeQuantityCheck(List<Quantity> quantityList, Set<Long> quantitySet) throws RuntimeException{
        if(quantityList.size() != quantitySet.size()){
            throw new RuntimeException("input quantity id and product's quantity id size is different !");
        }
        for(Quantity quantity : quantityList){
            if(!quantitySet.contains(quantity.getId())){
                throw new RuntimeException(quantity.getId() +" is not products quantity Id");
            }
        }
    }

}
