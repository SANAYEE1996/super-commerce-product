package bestcommerce.brand.sync;

import bestcommerce.brand.product.dto.ProductInfoDto;
import bestcommerce.brand.product.service.ProductImageService;
import bestcommerce.brand.product.service.ProductService;
import bestcommerce.brand.product.service.ProductUtilService;
import bestcommerce.brand.size.service.QuantityService;
import bestcommerce.brand.size.service.SizeService;
import bestcommerce.brand.util.ResponseDto;
import bestcommerce.brand.util.ResponseStatus;
import bestcommerce.brand.util.converter.DtoConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sync")
public class SyncController {

    private final ProductService productService;

    private final ProductImageService productImageService;

    private final QuantityService quantityService;

    private final SizeService sizeService;

    private final DtoConverter dtoConverter;

    private final SyncService syncService;

    private final ProductUtilService productUtilService = new ProductUtilService(productService,quantityService,sizeService,productImageService,dtoConverter);


    @PostMapping(value = "/product")
    public ResponseDto update(@RequestBody ProductInfoDto dto){
        try {
            productService.productIdCheck(dto.getId());
            syncService.syncToItemServiceForUpdate(productUtilService.findProductDetail(dto.getId()));
        }catch (RuntimeException | ParseException e){
            log.error(e.getMessage());
            return ResponseDto.builder().message(e.getMessage()).code(ResponseStatus.EXCEPTION.getStatusCode()).build();
        }
        return ResponseDto.builder().message("수정 성공").code(ResponseStatus.OK.getStatusCode()).build();
    }
}
