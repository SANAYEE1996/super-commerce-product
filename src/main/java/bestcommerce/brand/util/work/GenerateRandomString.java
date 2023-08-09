package bestcommerce.brand.util.work;

import org.springframework.stereotype.Component;

@Component
public class GenerateRandomString {

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

}
