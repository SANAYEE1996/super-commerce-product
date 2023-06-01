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

    public Long registerBrand(Brand brand){
        return brandRepository.save(brand).getId();
    }

    public Brand findBrand(Long id){
        return brandRepository.findById(id).orElseThrow(()->new NullPointerException("등록된 브랜드가 아닙니다."));
    }

    public void deleteBrand(Long id){
        brandRepository.deleteById(id);
    }
}
