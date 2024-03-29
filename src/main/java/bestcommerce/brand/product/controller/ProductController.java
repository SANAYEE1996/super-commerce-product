package bestcommerce.brand.product.controller;

import bestcommerce.brand.product.dto.*;
import bestcommerce.brand.product.service.*;
import bestcommerce.brand.util.ResponseBody;
import bestcommerce.brand.util.ResponseDto;
import bestcommerce.brand.util.ResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductCombineService productCombineService;

    @PostMapping(value = "/save")
    public ResponseDto save(@RequestBody ProductCreateDto productCreateDto){
        try {
            Long productId = productCombineService.saveProduct(productCreateDto);
            return ResponseDto.builder().code(ResponseStatus.OK.getStatusCode()).message("등록 성공").body(new ResponseBody<>(productId)).build();
        }catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseDto.builder().code(ResponseStatus.EXCEPTION.getStatusCode()).message(e.getMessage()).build();
        }
    }

    @PostMapping(value = "/detail/view")
    public ResponseDto detailView(@RequestBody ProductRequestDto dto){
        try {
            ProductDetailDto productDetailDto = productCombineService.getDetailProduct(dto.getProductId());
            return ResponseDto.builder().code(ResponseStatus.OK.getStatusCode()).message("조회 성공").body(new ResponseBody<>(productDetailDto)).build();
        }catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseDto.builder().code(ResponseStatus.EXCEPTION.getStatusCode()).message(e.getMessage()).build();
        }
    }

    @PostMapping(value = "/list")
    public ResponseDto productList(@RequestBody ProductRequestDto dto){
        List<ProductInfoDto> productList = productCombineService.getProductList(dto);
        return ResponseDto.builder().code(ResponseStatus.OK.getStatusCode()).message("조회 성공").body(new ResponseBody<>(productList)).build();
    }

    @PostMapping(value = "/search")
    public ResponseDto searchList(@RequestBody ProductRequestDto dto){
        List<ProductInfoDto> searchList = productCombineService.getProductSearchList(dto);
        return ResponseDto.builder().code(ResponseStatus.OK.getStatusCode()).message("조회 성공").body(new ResponseBody<>(searchList)).build();
    }

    @PostMapping(value = "/update")
    public ResponseDto update(@RequestBody ProductInfoDto dto){
        try {
            productCombineService.updateProduct(dto);
        }catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseDto.builder().message(e.getMessage()).code(ResponseStatus.EXCEPTION.getStatusCode()).build();
        }
        return ResponseDto.builder().message("수정 성공").code(ResponseStatus.OK.getStatusCode()).build();
    }

    @PostMapping(value = "/delete")
    public ResponseDto delete(@RequestBody ProductRequestDto dto){
        try {
            productCombineService.deleteProduct(dto);
        }catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseDto.builder().message(e.getMessage()).code(ResponseStatus.EXCEPTION.getStatusCode()).build();
        }
        productCombineService.deleteProduct(dto.getProductId());
        return ResponseDto.builder().message("삭제 성공").build();
    }

}
