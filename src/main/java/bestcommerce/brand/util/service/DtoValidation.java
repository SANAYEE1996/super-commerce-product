package bestcommerce.brand.util.service;

import bestcommerce.brand.product.dto.ProductImageDto;
import bestcommerce.brand.size.dto.QuantityDto;
import bestcommerce.brand.size.entity.Quantity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashSet;
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

    public void validateImageTitleUpdate(List<ProductImageDto> updateList, List<Integer> newOdrList, int length) throws IOException{
        Set<Integer> updateOdrSet = new HashSet<>();
        for(ProductImageDto productImageDto : updateList) {
            updateOdrSet.add(productImageDto.getOdr());
        }
        if(updateOdrSet.size() != updateList.size()){
            throw new IOException("Update Order parameter Error");
        }
        putNewOdrList(updateOdrSet, newOdrList, length);
    }

    private void putNewOdrList(Set<Integer> updateOdrSet, List<Integer> newOdrList, int length){
        for(int i = 0; i < length; i++){
            if(updateOdrSet.contains(i)) continue;
            newOdrList.add(i);
        }
    }

}
