package bestcommerce.brand.size.service;

import bestcommerce.brand.size.entity.Size;
import bestcommerce.brand.size.repository.SizeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SizeService {

    private final SizeRepository sizeRepository;

    public Long save(Size size){
        return sizeRepository.save(size).getId();
    }
}
