package bestcommerce.brand.work;

import bestcommerce.brand.product.dto.ProductDetailDto;
import bestcommerce.brand.product.dto.ProductImageDto;
import bestcommerce.brand.product.dto.ProductInfoDto;
import bestcommerce.brand.product.service.ProductImageService;
import bestcommerce.brand.product.service.ProductService;
import bestcommerce.brand.size.dto.QuantityDto;
import bestcommerce.brand.size.dto.SizeDto;
import bestcommerce.brand.size.service.QuantityService;
import bestcommerce.brand.size.service.SizeService;
import bestcommerce.brand.util.converter.DtoConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class WorkTest {

    @Value("${testUtil.fileLocation}")
    private String fileLocation;

    @Autowired
    private ProductService productService;

    @Autowired
    private QuantityService quantityService;

    @Autowired
    private SizeService sizeService;

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private DtoConverter dtoConverter;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("파일 저장 작업")
    void writeTest() throws IOException {
        Long brandId = 1L;
        List<ProductInfoDto> productInfoDtoList = productService.getAllDetailProduct();
        List<ProductDetailDto> productDetailDtoList = new ArrayList<>();
        for(ProductInfoDto productInfoDto : productInfoDtoList){
            Long productId = productInfoDto.getId();
            ProductInfoDto proInfo = productService.getDetailProduct(productId);
            List<QuantityDto> quantityDtoList = dtoConverter.toQuantityDtoList(quantityService.findQuantityList(productId));
            List<SizeDto> sizeDtoList = sizeService.getSizeList(productId);
            List<ProductImageDto> productImageDtoList = dtoConverter.toProductImageDtoList(productImageService.getProductImageList(productId));
            productDetailDtoList.add(new ProductDetailDto(proInfo, quantityDtoList, sizeDtoList, productImageDtoList));
        }
        String content = objectMapper.writeValueAsString(productDetailDtoList);
        System.out.println(content);
        FileWriter file = new FileWriter(fileLocation+"/test.json");
        file.write(content);
        file.flush();
        file.close();
    }

}
