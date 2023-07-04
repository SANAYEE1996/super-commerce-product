package bestcommerce.brand.product.controller;

import bestcommerce.brand.product.dto.ProductImageDto;
import bestcommerce.brand.product.dto.ProductRequestDto;
import bestcommerce.brand.product.service.ProductImageService;
import bestcommerce.brand.product.service.ProductService;
import bestcommerce.brand.util.ResponseDto;
import bestcommerce.brand.util.ResponseStatus;
import bestcommerce.brand.util.image.ImageSaveService;
import bestcommerce.brand.util.service.DtoValidation;
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

    private final DtoValidation validate;

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
            return ResponseDto.builder().message(e.getMessage()).code(ResponseStatus.EXCEPTION.getStatusCode()).build();
        }
        productImageService.saveAll(productImageDtoList);
        return ResponseDto.builder().message("등록 성공").build();
    }

    @PostMapping(value = "/title/update")
    public ResponseDto update(@RequestPart(value = "newProductImage", required = false) List<MultipartFile> newProductImage,
                              @RequestPart(name = "deleteProductImageList") List<ProductImageDto> deleteList,
                              @RequestPart(name = "updateProductImageList") List<ProductImageDto> updateList,
                              @RequestPart(name = "ProductRequestDto") ProductRequestDto productRequestDto){
        List<ProductImageDto> imageDtoList = new ArrayList<>();
        try{
            Long productId = productService.findProduct(productRequestDto.getProductId()).getId();
            List<Integer> newOdrList = new ArrayList<>();
            validate.validateImageTitleUpdate(updateList, newOdrList, updateList.size()+newProductImage.size());
            imageSaveService.updateProductTitleImage(amazonS3Client, productId, newProductImage, imageDtoList, newOdrList);
        }catch (IOException e){
            log.error(e.getMessage());
            return ResponseDto.builder().message(e.getMessage()).code(ResponseStatus.EXCEPTION.getStatusCode()).build();
        }
        productImageService.saveAll(imageDtoList);
        productImageService.updateAll(updateList);
        productImageService.deleteAll(deleteList);

        return ResponseDto.builder().message("수정 성공").build();
    }
}
