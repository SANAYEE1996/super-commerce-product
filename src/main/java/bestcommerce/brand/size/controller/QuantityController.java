package bestcommerce.brand.size.controller;

import bestcommerce.brand.size.dto.QuantityDto;
import bestcommerce.brand.size.service.QuantityService;
import bestcommerce.brand.util.ResponseDto;
import bestcommerce.brand.util.service.QuantityCheck;
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

    private final QuantityService quantityService;

    private final QuantityCheck quantityCheck;

    @PostMapping(value = "/save")
    public ResponseDto save(@RequestBody List<QuantityDto> dtoList) {
        try {
            quantityCheck.saveQuantityCheck(dtoList);
        } catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseDto.builder().message(e.getMessage()).build();
        }
        quantityService.saveAll(dtoList);
        return ResponseDto.builder().message("저장 성공").build();
    }

    @PostMapping(value = "/update")
    public ResponseDto update(@RequestBody List<QuantityDto> updateList) {
        try {
            quantityCheck.updateDeleteQuantityCheck(updateList);
        } catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseDto.builder().message(e.getMessage()).build();
        }
        quantityService.updateQuantity(updateList);
        return ResponseDto.builder().message("수정 성공").build();
    }

    @PostMapping(value = "/delete")
    public ResponseDto delete(@RequestBody List<QuantityDto> deleteList) {
        try {
            quantityCheck.updateDeleteQuantityCheck(deleteList);
        } catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseDto.builder().message(e.getMessage()).build();
        }
        quantityService.deleteAll(deleteList);
        return ResponseDto.builder().message("삭제 성공").build();
    }
}
