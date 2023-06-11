package bestcommerce.brand.product.controller;

import bestcommerce.brand.manager.entity.Brand;
import bestcommerce.brand.manager.entity.Manager;
import bestcommerce.brand.manager.service.BrandService;
import bestcommerce.brand.manager.service.ManagerService;
import bestcommerce.brand.product.dto.ProductCreateDto;
import bestcommerce.brand.size.dto.QuantityDto;
import bestcommerce.brand.product.dto.ProductImageDto;
import bestcommerce.brand.product.service.ProductImageService;
import bestcommerce.brand.product.service.ProductService;
import bestcommerce.brand.size.service.QuantityService;
import bestcommerce.brand.util.EntityConverter;
import bestcommerce.brand.util.ResponseDto;
import bestcommerce.brand.util.ResponseStatus;
import bestcommerce.brand.util.image.ImageSaveService;
import com.amazonaws.services.s3.AmazonS3Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final AmazonS3Client amazonS3Client;

    private final BrandService brandService;

    private final ManagerService managerService;

    private final ProductService productService;

    private final ProductImageService productImageService;

    private final QuantityService quantityService;

    private final ImageSaveService imageSaveService;

    private final EntityConverter entityConverter;


    @PostMapping(value = "/save")
    public ResponseDto save(@RequestPart(value = "productImage", required = false) List<MultipartFile> productImage,
                            @RequestPart(value = "infoImage", required = false) List<MultipartFile> infoImage,
                            @RequestPart ProductCreateDto productCreateDto){
        Brand brand = brandService.findBrand(productCreateDto.getBrandId());
        Manager manager = managerService.findManager(productCreateDto.getManagerEmail());
        List<QuantityDto> quantityDtoList = productCreateDto.getQuantityDtoList();
        Long productId = productService.save(entityConverter.toProductEntity(productCreateDto,brand,manager));
        List<ProductImageDto> productImageDtoList = new ArrayList<>();
        try {
            imageSaveService.saveProductImage(amazonS3Client, productId, productImage, infoImage, productImageDtoList);
        }catch (IOException e){
            log.error(e.getMessage());
            productService.delete(productId);
            return ResponseDto.builder().message(e.getMessage()).responseStatus(ResponseStatus.EXCEPTION).build();
        }
        productImageService.saveAll(productImageDtoList);
        for(QuantityDto quantityDto : quantityDtoList){
            quantityDto.setProductId(productId);
        }
        quantityService.saveAll(quantityDtoList);
        return ResponseDto.builder().message("등록 성공").build();
    }

}
