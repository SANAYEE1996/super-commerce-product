package bestcommerce.brand.sync;

import bestcommerce.brand.product.dto.ProductInfoDto;
import bestcommerce.brand.product.service.ProductService;
import bestcommerce.brand.util.ResponseDto;
import bestcommerce.brand.util.ResponseStatus;
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
@RequestMapping("/sync")
public class SyncController {

    private final ProductService productService;

    @PostMapping(value = "/product")
    public ResponseDto syncOne(@RequestBody ProductInfoDto dto){
        try {
            productService.productIdCheck(dto.getId());
        }catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseDto.builder().message(e.getMessage()).code(ResponseStatus.EXCEPTION.getStatusCode()).build();
        }
        return ResponseDto.builder().message("수정 성공").code(ResponseStatus.OK.getStatusCode()).build();
    }

    @PostMapping(value = "/products")
    public ResponseDto syncBatch(@RequestBody List<ProductInfoDto> productInfoDtoList){
        try {

        }catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseDto.builder().message(e.getMessage()).code(ResponseStatus.EXCEPTION.getStatusCode()).build();
        }
        return ResponseDto.builder().message("수정 성공").code(ResponseStatus.OK.getStatusCode()).build();
    }
}
