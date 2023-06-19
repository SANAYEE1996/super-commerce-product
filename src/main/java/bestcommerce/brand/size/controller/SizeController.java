package bestcommerce.brand.size.controller;

import bestcommerce.brand.size.dto.SizeDto;
import bestcommerce.brand.size.service.BodyService;
import bestcommerce.brand.size.service.QuantityService;
import bestcommerce.brand.size.service.SizeService;
import bestcommerce.brand.util.ResponseDto;
import bestcommerce.brand.util.service.PutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/size")
public class SizeController {

    private final QuantityService quantityService;

    private final BodyService bodyService;

    private final PutService putService;

    private final SizeService sizeService;

    @PostMapping(value = "/save")
    public ResponseDto save(@RequestBody List<SizeDto> sizeDtoList){
        List<SizeDto> insertSizeDtoList = new ArrayList<>();
        try {
            putService.putSizeList(quantityService,bodyService,sizeDtoList, insertSizeDtoList);
            sizeService.saveAll(insertSizeDtoList);
        }catch (RuntimeException e){
            log.error("error : {}", e.getMessage());
            return ResponseDto.builder().message("등록 실패").build();
        }
        return ResponseDto.builder().message("등록 성공").build();
    }
}
