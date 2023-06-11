package bestcommerce.brand.size;

import bestcommerce.brand.product.service.ProductService;
import bestcommerce.brand.size.service.BodyService;
import bestcommerce.brand.size.service.QuantityService;
import bestcommerce.brand.size.service.SizeService;
import bestcommerce.brand.util.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/size")
public class SizeController {

    private final SizeService sizeService;

    private final ProductService productService;

    private final QuantityService quantityService;

    private final BodyService bodyService;

    @PostMapping(value = "/save")
    public ResponseDto save(){


        return ResponseDto.builder().message("등록 성공").build();
    }
}
