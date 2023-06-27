package bestcommerce.brand.size.controller;

import bestcommerce.brand.product.dto.ProductCreateDto;
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

    @PostMapping(value = "/update")
    public ResponseDto save(@RequestBody List<QuantityDto> quantityDtoList, @RequestBody List<QuantityDto> deleteList) {

        return ResponseDto.builder().build();
    }
}
