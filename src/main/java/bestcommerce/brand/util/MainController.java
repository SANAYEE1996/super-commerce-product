package bestcommerce.brand.util;

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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class MainController {

    private final ProductService productService;

    private final QuantityService quantityService;

    private final DtoConverter dtoConverter;

    private final SizeService sizeService;

    private final ProductImageService productImageService;

    private final ObjectMapper objectMapper;

    @Value("${testUtil.fileLocation}")
    private static String fileLocation;

    @GetMapping(value = "/save")
    public void save() throws IOException {
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
