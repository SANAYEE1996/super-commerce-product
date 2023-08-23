package bestcommerce.brand.util.work;

import bestcommerce.brand.size.dto.QuantityDto;
import bestcommerce.brand.size.dto.SizeDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class GenerateRandomString {

    private static final String[] typeA = {"SS","S","M","L","XL","XL"};
    private static final String[] typeB = {"18","20","22","24","26","28","30","32","34","36","38","40"};
    private static final String[] typeC = {"210","215","220","225","230","235","240","245","250","255","260","265","270","275","280","285","290","295","300"};

    private static final Long[] typeOne = {10L, 7L, 12L, 8L};

    private static final Long[] typeTwo = {4L, 13L, 6L, 14L, 9L};

    private static final Long[] typeThree = {15L, 16L, 17L};

    private static final Long[] typeFour = {18L, 19L, 20L};

    private static final Long[] typeFive = {19L};

    public String generateProductCode(int a){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < a; i++){
            sb.append(getRandomCharacter((int)(Math.random()*3)));
        }
        return sb.toString();
    }

    private Character getRandomCharacter(int a){
        if(!(0 <= a && a <= 2)){
            return '0';
        }
        if(a == 0){
            return (char)((Math.random() * 10) + 48);
        }
        else if(a == 1){
            return (char)((Math.random() * 26) + 65);
        }
        return (char)((Math.random() * 26) + 97);
    }

    public List<String> getRandomQuantityList(int a){
        if(!(0 <= a && a <= 2)){
            return Arrays.asList(typeA);
        }
        if(a == 0){
            return setList(typeA);
        }
        else if(a == 1){
            return setList(typeB);
        }
        return setList(typeC);
    }

    private List<String> setList(String[] a){
        int count = (int)(Math.random()*a.length);
        int start = (int)(Math.random()*(a.length - count));
        return Arrays.asList(Arrays.copyOfRange(a,start, start+count));
    }

    public void putRandomBodyValue(List<SizeDto> sizeDtoList, List<QuantityDto> quantityDtoList){
        int random = (int)(Math.random()*6);
        if(random == 1){
            putSizeValue(sizeDtoList, quantityDtoList, typeOne);
        }
        else if(random == 2){
            putSizeValue(sizeDtoList, quantityDtoList, typeTwo);
        }
        else if(random == 3){
            putSizeValue(sizeDtoList, quantityDtoList, typeThree);
        }
        else if(random == 4){
            putSizeValue(sizeDtoList, quantityDtoList, typeFour);
        }
        else if(random == 5){
            putSizeValue(sizeDtoList, quantityDtoList, typeFive);
        }
    }

    private void putSizeValue(List<SizeDto> sizeDtoList, List<QuantityDto> quantityDtoList, Long[] A){
        for(QuantityDto quantityDto : quantityDtoList){
            for(Long i : A){
                sizeDtoList.add(SizeDto.builder()
                        .quantityId(quantityDto.getQuantityId())
                        .bodyId(i)
                        .sizeValue(String.valueOf((int)(Math.random()*100)+10))
                        .build());
            }
        }
    }

}
