package bestcommerce.brand.util.service;

import bestcommerce.brand.size.dto.SizeDto;
import bestcommerce.brand.size.service.BodyService;
import bestcommerce.brand.size.service.QuantityService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class PutService {

    public void putSizeList(QuantityService quantityService, BodyService bodyService, List<SizeDto> sizeInsertDtoList, List<SizeDto> sizeInsertList ){
        HashMap<Long, Long> quantityMap = new HashMap<>();
        HashMap<String, Long> bodyMap = new HashMap<>();
        for(SizeDto sizeInsertDto : sizeInsertDtoList){
            Long quantityId = sizeInsertDto.getQuantityId();
            String bodyName = sizeInsertDto.getBodyName();
            if(!quantityMap.containsKey(quantityId)) quantityMap.put(quantityId, quantityService.verifyQuantity(sizeInsertDto.getQuantityId()));
            if(!bodyMap.containsKey(bodyName)) bodyMap.put(bodyName, bodyService.save(bodyName));
            sizeInsertList.add(SizeDto.builder().quantityId(quantityMap.get(quantityId)).bodyId(bodyMap.get(bodyName)).sizeValue(sizeInsertDto.getSizeValue()).build());
        }
    }
}
