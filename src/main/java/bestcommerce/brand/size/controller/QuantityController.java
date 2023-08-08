package bestcommerce.brand.size.controller;

import bestcommerce.brand.size.dto.QuantityDto;
import bestcommerce.brand.size.dto.QuantityModifyDto;
import bestcommerce.brand.size.service.QuantityService;
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
@RequestMapping("/quantity")
public class QuantityController {

    private final QuantityService quantityService;

    @PostMapping(value = "/save")
    public ResponseDto save(@RequestBody List<QuantityDto> dtoList) {
        quantityService.saveAll(dtoList);
        return ResponseDto.builder().code(ResponseStatus.OK.getStatusCode()).message("저장 성공").build();
    }

    @PostMapping(value = "/update")
    public ResponseDto update(@RequestBody List<QuantityModifyDto> updateList) {
        quantityService.updateQuantity(updateList);
        return ResponseDto.builder().code(ResponseStatus.OK.getStatusCode()).message("수정 성공").build();
    }

    @PostMapping(value = "/delete")
    public ResponseDto delete(@RequestBody List<QuantityModifyDto> deleteList) {
        quantityService.deleteAll(deleteList);
        return ResponseDto.builder().code(ResponseStatus.OK.getStatusCode()).message("삭제 성공").build();
    }
}
