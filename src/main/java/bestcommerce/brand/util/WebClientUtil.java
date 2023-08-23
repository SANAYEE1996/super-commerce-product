package bestcommerce.brand.util;

import bestcommerce.brand.config.ItemWebClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class WebClientUtil {

    private final ItemWebClient itemWebClient;

    public WebClient create(){
        return itemWebClient.getItemWebClient();
    }
}
