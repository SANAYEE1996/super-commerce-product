package bestcommerce.brand.product.service;

import bestcommerce.brand.product.entity.Brand;
import bestcommerce.brand.product.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    public Brand findBrand(Long id){
        return brandRepository.findById(id).orElseThrow(() -> new RuntimeException(id + " is not exist brand id!"));
    }

}