package bestcommerce.brand.size.controller;

import bestcommerce.brand.product.dto.ProductRequestDto;
import bestcommerce.brand.product.service.ProductService;
import bestcommerce.brand.size.dto.QuantityDto;
import bestcommerce.brand.size.service.QuantityService;
import bestcommerce.brand.util.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/quantity")
public class QuantityController {

    private final ProductService productService;

    private final QuantityService quantityService;

    @PostMapping(value = "/insert")
    public ResponseDto save(@RequestBody List<QuantityDto> dtoList, @RequestBody ProductRequestDto productRequestDto) {
        Long productId = productService.findProduct(productRequestDto.getProductId()).getId();
        for(QuantityDto quantityDto : dtoList){
            quantityDto.setProductId(productId);
        }
        quantityService.saveAll(dtoList);
        return ResponseDto.builder().message("저장 성공").build();
    }

    @PostMapping(value = "/update")
    public ResponseDto update(@RequestBody List<QuantityDto> updateList) {
        quantityService.updateQuantity(updateList);
        return ResponseDto.builder().message("수정 성공").build();
    }

    @PostMapping(value = "/delete")
    public ResponseDto delete(@RequestBody List<QuantityDto> deleteList) {
        quantityService.deleteAll(deleteList);
        return ResponseDto.builder().message("삭제 성공").build();
    }
}
