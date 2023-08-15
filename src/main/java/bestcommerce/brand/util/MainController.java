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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private String fileLocation;

    @GetMapping(value = "/save")
    public void save() throws IOException {
        List<ProductInfoDto> productInfoDtoList = productService.getAllDetailProduct();
        List<QuantityDto> allQuantityDtoList = dtoConverter.toQuantityDtoList(quantityService.findAll());
        List<SizeDto> allSizeDtoList = sizeService.findAll();
        List<ProductImageDto> allProductImageDtoList = dtoConverter.toProductImageDtoList(productImageService.findAll());

        Map<Long, List<SizeDto>> sizeMap = new HashMap<>();
        Map<Long, List<QuantityDto>> quantityMap = new HashMap<>();
        Map<Long, List<ProductImageDto>> imageMap = new HashMap<>();
        Map<Long, Long> quantityProductIdMap = new HashMap<>();

        List<ProductDetailDto> productDetailDtoList = new ArrayList<>();

        for(QuantityDto quantityDto : allQuantityDtoList){
            Long productId = quantityDto.getProductId();
            quantityProductIdMap.put(quantityDto.getQuantityId(), productId);
            if(!quantityMap.containsKey(productId)){
                quantityMap.put(productId, new ArrayList<>());
            }
            quantityMap.get(productId).add(quantityDto);
        }

        for(SizeDto sizeDto : allSizeDtoList){
            Long productId = quantityProductIdMap.get(sizeDto.getQuantityId());
            if(!sizeMap.containsKey(productId)){
                sizeMap.put(productId, new ArrayList<>());
            }
            sizeMap.get(productId).add(sizeDto);
        }

        for(ProductImageDto productImageDto : allProductImageDtoList){
            Long productId = productImageDto.getProductId();
            if(!imageMap.containsKey(productId)){
                imageMap.put(productId, new ArrayList<>());
            }
            imageMap.get(productId).add(productImageDto);
        }

        for(ProductInfoDto productInfoDto : productInfoDtoList){
            Long productId = productInfoDto.getId();
            List<QuantityDto> quantityDtoList = quantityMap.get(productId);
            List<SizeDto> sizeDtoList = sizeMap.get(productId);
            List<ProductImageDto> productImageDtoList = imageMap.get(productId);
            productDetailDtoList.add(new ProductDetailDto(productInfoDto, quantityDtoList, sizeDtoList, productImageDtoList));
        }
        String content = objectMapper.writeValueAsString(productDetailDtoList);
        FileWriter file = new FileWriter(fileLocation+"/test1.json");
        file.write(content);
        file.flush();
        file.close();
    }
}
