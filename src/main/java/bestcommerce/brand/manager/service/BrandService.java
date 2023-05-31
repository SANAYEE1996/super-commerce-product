package bestcommerce.brand.manager.service;

import bestcommerce.brand.manager.entity.Brand;
import bestcommerce.brand.manager.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    public void registerBrand(Brand brand){
        brandRepository.save(brand);
    }
}
