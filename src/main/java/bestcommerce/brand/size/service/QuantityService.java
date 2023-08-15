package bestcommerce.brand.size.service;

import bestcommerce.brand.product.entity.Product;
import bestcommerce.brand.size.dto.QuantityDto;
import bestcommerce.brand.size.dto.QuantityModifyDto;
import bestcommerce.brand.size.entity.Quantity;
import bestcommerce.brand.size.repository.QuantityBulkRepository;
import bestcommerce.brand.size.repository.QuantityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuantityService {

    private final QuantityRepository quantityRepository;

    private final QuantityBulkRepository quantityBulkRepository;

    public void saveAll(List<QuantityDto> quantityDtoList){
        quantityBulkRepository.saveAll(quantityDtoList);
    }

    public List<Quantity> findQuantityList(Product product){
        return quantityRepository.findAllByProduct(product);
    }

    public void updateQuantity(List<QuantityModifyDto> quantityDtoList){
        for (QuantityModifyDto quantityDto : quantityDtoList){
            if(StringUtils.hasText(quantityDto.getQuantityName())){
                quantityRepository.updateNameRemainQuantity(quantityDto.getQuantityName(), quantityDto.getQuantity(), quantityDto.getQuantityId());
                continue;
            }
            quantityRepository.updateRemainQuantity(quantityDto.getQuantity(), quantityDto.getQuantityId());
        }
    }

    public void deleteAll(List<QuantityModifyDto> quantityDtoList){
        quantityBulkRepository.deleteAll(quantityDtoList);
    }

    public List<Quantity> findQuantityList(Long productId){
        return quantityRepository.findAllQuantityByProductId(productId);
    }

    public void checkQuantityExistsByProductId(Long productId) throws RuntimeException{
        if(quantityRepository.checkQuantityExistsByProductId(productId) > 0){
            throw new RuntimeException(productId + " this productId is still exists in Quantity !");
        }
    }

    public List<Quantity> findAll(){
        return quantityRepository.findAll();
    }
}
