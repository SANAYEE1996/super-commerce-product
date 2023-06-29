package bestcommerce.brand.size.service;

import bestcommerce.brand.size.dto.SizeDto;
import bestcommerce.brand.size.repository.SizeBulkRepository;
import bestcommerce.brand.size.repository.SizeSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SizeService {

    private final SizeBulkRepository sizeBulkRepository;

    private final SizeSearchRepository sizeSearchRepository;

    public void saveAll(List<SizeDto> sizeDtoList){
        sizeBulkRepository.saveAll(sizeDtoList);
    }

    public List<SizeDto> getSizeList(Long productId){
        return sizeSearchRepository.getSizeInfoForProductDetail(productId);
    }

    public void deleteAll(Long productId){
        sizeBulkRepository.deleteAll(productId);
    }
}
