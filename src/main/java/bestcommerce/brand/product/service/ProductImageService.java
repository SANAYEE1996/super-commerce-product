package bestcommerce.brand.product.service;

import bestcommerce.brand.product.dto.ProductImageDto;
import bestcommerce.brand.product.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductImageService {

    private final ProductImageRepository productImageRepository;

    private void saveAll(List<ProductImageDto> productImageDtoList){

    }
}
