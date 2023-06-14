package bestcommerce.brand.size.service;

import bestcommerce.brand.size.entity.Body;
import bestcommerce.brand.size.repository.BodyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BodyService {

    private final BodyRepository bodyRepository;

    public Long save(String bodyName){
        if(bodyRepository.existsByName(bodyName)){
           return bodyRepository.findByName(bodyName).orElseThrow(() -> new RuntimeException("error")).getId();
        }
        return bodyRepository.save(new Body(0L, bodyName)).getId();
    }
}
