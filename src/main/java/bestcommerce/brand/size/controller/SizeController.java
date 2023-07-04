package bestcommerce.brand.size.controller;

import bestcommerce.brand.product.dto.ProductRequestDto;
import bestcommerce.brand.product.service.ProductService;
import bestcommerce.brand.size.dto.SizeApiDto;
import bestcommerce.brand.size.dto.SizeDto;
import bestcommerce.brand.size.service.BodyService;
import bestcommerce.brand.size.service.QuantityService;
import bestcommerce.brand.size.service.SizeService;
import bestcommerce.brand.util.ResponseDto;
import bestcommerce.brand.util.ResponseStatus;
import bestcommerce.brand.util.service.DtoValidation;
import bestcommerce.brand.util.service.PutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/size")
public class SizeController {

    private final ProductService productService;

    private final QuantityService quantityService;

    private final BodyService bodyService;

    private final PutService putService;

    private final SizeService sizeService;

    private final DtoValidation dtoValidation;

    @PostMapping(value = "/save")
    public ResponseDto save(@RequestBody SizeApiDto dto){
        List<SizeDto> insertSizeDtoList = new ArrayList<>();
        try {
            saveSize(dto.getSizeDtoList(),dto.getProductRequestDto(),insertSizeDtoList);
        }catch (RuntimeException e){
            log.error("error : {}", e.getMessage());
            return ResponseDto.builder().code(ResponseStatus.EXCEPTION.getStatusCode()).message("등록 실패").build();
        }
        sizeService.saveAll(insertSizeDtoList);
        return ResponseDto.builder().code(ResponseStatus.OK.getStatusCode()).message("등록 성공").build();
    }

    @PostMapping(value = "/update")
    public ResponseDto update(@RequestBody SizeApiDto dto){
        List<SizeDto> insertSizeDtoList = new ArrayList<>();
        try {
            saveSize(dto.getSizeDtoList(),dto.getProductRequestDto(),insertSizeDtoList);
        }catch (RuntimeException e){
            log.error("error : {}", e.getMessage());
            return ResponseDto.builder().code(ResponseStatus.EXCEPTION.getStatusCode()).message("수정 실패").build();
        }
        sizeService.deleteAll(dto.getProductRequestDto().getProductId());
        sizeService.saveAll(insertSizeDtoList);
        return ResponseDto.builder().code(ResponseStatus.OK.getStatusCode()).message("수정 성공").build();
    }

    private void saveSize(List<SizeDto> sizeDtoList, ProductRequestDto request, List<SizeDto> insertSizeDtoList) throws RuntimeException{
        Set<Long> quantitySet = new HashSet<>();
        productService.productIdCheck(request.getProductId());
        putService.putSizeList(bodyService,sizeDtoList, insertSizeDtoList, quantitySet);
        dtoValidation.sizeQuantityCheck(quantityService.findQuantityList(request.getProductId()),quantitySet);
    }
}
