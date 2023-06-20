package bestcommerce.brand.size.service;

import bestcommerce.brand.product.entity.Product;
import bestcommerce.brand.size.dto.QuantityDto;
import bestcommerce.brand.size.entity.Quantity;
import bestcommerce.brand.size.repository.QuantityBulkRepository;
import bestcommerce.brand.size.repository.QuantityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public Long verifyQuantity(Long quantityId){
        return quantityRepository.findById(quantityId).orElseThrow(()->new RuntimeException("Not enrolled Quantity Id")).getId();
    }

    public List<Quantity> findQuantityList(Product product){
        return quantityRepository.findAllByProduct(product);
    }
}
