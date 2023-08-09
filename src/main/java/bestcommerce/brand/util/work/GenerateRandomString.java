package bestcommerce.brand.util.work;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class GenerateRandomString {

    private static final String[] typeA = {"SS","S","M","L","XL","XL"};
    private static final String[] typeB = {"18","20","22","24","26","28","30","32","34","36","38","40"};
    private static final String[] typeC = {"210","215","220","225","230","235","240","245","250","255","260","265","270","275","280","285","290","295","300"};

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
        List<String> quantityList = new ArrayList<>();
        if(!(0 <= a && a <= 2)){
            return Arrays.asList(typeA);
        }

        return quantityList;
    }

    private void setList(){
        int count = (int)(Math.random()*typeA.length);
        int start = (int)(Math.random()*(typeA.length - count));
        Arrays.asList(typeA);
    }

}
