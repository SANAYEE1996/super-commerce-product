package bestcommerce.brand.util.service;

import bestcommerce.brand.product.dto.ProductImageDto;
import bestcommerce.brand.size.entity.Quantity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Component
public class DtoValidation {

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

    public void validateImageTitleUpdate(List<ProductImageDto> updateList, Set<Integer> updateOdrSet) throws IOException{
        for(ProductImageDto productImageDto : updateList) {
            updateOdrSet.add(productImageDto.getOdr());
        }
        if(updateOdrSet.size() != updateList.size()){
            throw new IOException("Update Order parameter Error");
        }
    }

}
