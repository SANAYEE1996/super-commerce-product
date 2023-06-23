package bestcommerce.brand.product.controller;

import bestcommerce.brand.product.dto.ProductImageDto;
import bestcommerce.brand.product.dto.ProductRequestDto;
import bestcommerce.brand.product.service.ProductImageService;
import bestcommerce.brand.product.service.ProductService;
import bestcommerce.brand.util.ResponseDto;
import bestcommerce.brand.util.ResponseStatus;
import bestcommerce.brand.util.image.ImageSaveService;
import com.amazonaws.services.s3.AmazonS3Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/image/product")
public class ProductImageController {

    private final ProductService productService;

    private final ImageSaveService imageSaveService;

    private final AmazonS3Client amazonS3Client;

    private final ProductImageService productImageService;

    @PostMapping(value = "/save")
    public ResponseDto save(@RequestPart(value = "productImage", required = false) List<MultipartFile> productImage,
                            @RequestPart(value = "infoImage", required = false) List<MultipartFile> infoImage,
                            @RequestPart(name = "ProductRequestDto") ProductRequestDto productRequestDto){
        Long productId = productService.findProduct(productRequestDto.getProductId()).getId();
        List<ProductImageDto> productImageDtoList = new ArrayList<>();
        try {
            imageSaveService.saveProductImage(amazonS3Client, productId, productImage, infoImage, productImageDtoList);
        }catch (IOException e){
            log.error(e.getMessage());
            return ResponseDto.builder().message(e.getMessage()).responseStatus(ResponseStatus.EXCEPTION).build();
        }
        productImageService.saveAll(productImageDtoList);
        return ResponseDto.builder().message("등록 성공").build();
    }
}
