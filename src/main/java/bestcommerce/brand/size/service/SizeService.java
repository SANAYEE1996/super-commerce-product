package bestcommerce.brand.size.service;

import bestcommerce.brand.size.dto.SizeDto;
import bestcommerce.brand.size.repository.SizeBulkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SizeService {

    private final SizeBulkRepository sizeBulkRepository;

    public void saveAll(List<SizeDto> sizeDtoList){
        sizeBulkRepository.saveAll(sizeDtoList);
    }
}
