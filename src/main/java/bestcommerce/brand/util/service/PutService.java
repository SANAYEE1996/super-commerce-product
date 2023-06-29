package bestcommerce.brand.util.service;

import bestcommerce.brand.size.dto.SizeDto;
import bestcommerce.brand.size.service.BodyService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Component
public class PutService {

    public void putSizeList(BodyService bodyService, List<SizeDto> sizeInsertDtoList, List<SizeDto> sizeInsertList, Set<Long> quantitySet){
        HashMap<String, Long> bodyMap = new HashMap<>();
        for(SizeDto sizeInsertDto : sizeInsertDtoList){
            String bodyName = sizeInsertDto.getBodyName();
            quantitySet.add(sizeInsertDto.getQuantityId());
            if(!bodyMap.containsKey(bodyName)) bodyMap.put(bodyName, bodyService.save(bodyName));
            sizeInsertList.add(SizeDto.builder().quantityId(sizeInsertDto.getQuantityId()).bodyId(bodyMap.get(bodyName)).sizeValue(sizeInsertDto.getSizeValue()).build());
        }
    }
}
