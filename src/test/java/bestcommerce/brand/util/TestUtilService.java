package bestcommerce.brand.util;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

@Component
public class TestUtilService {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @RegisterExtension
    final RestDocumentationExtension restDocumentation = new RestDocumentationExtension("custom");

    public MockMvc setRestDocumentation(RestDocumentationContextProvider restDocumentation){
        return MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(documentationConfiguration(restDocumentation))
                .build();
    }
}
