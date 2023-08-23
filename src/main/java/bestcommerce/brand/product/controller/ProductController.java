package bestcommerce.brand.product.controller;

import bestcommerce.brand.product.dto.*;
import bestcommerce.brand.product.entity.Product;
import bestcommerce.brand.product.service.BrandService;
import bestcommerce.brand.size.dto.QuantityDto;
import bestcommerce.brand.product.service.ProductImageService;
import bestcommerce.brand.product.service.ProductService;
import bestcommerce.brand.size.dto.SizeDto;
import bestcommerce.brand.size.service.QuantityService;
import bestcommerce.brand.size.service.SizeService;
import bestcommerce.brand.sync.SyncService;
import bestcommerce.brand.util.ResponseBody;
import bestcommerce.brand.util.converter.DtoConverter;
import bestcommerce.brand.util.converter.EntityConverter;
import bestcommerce.brand.util.ResponseDto;
import bestcommerce.brand.util.ResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    private final BrandService brandService;

    private final ProductImageService productImageService;

    private final QuantityService quantityService;

    private final SizeService sizeService;

    private final EntityConverter entityConverter;

    private final DtoConverter dtoConverter;

    private final SyncService syncService;


    @PostMapping(value = "/save")
    public ResponseDto save(@RequestBody ProductCreateDto productCreateDto){
        try {
            List<QuantityDto> quantityDtoList = productCreateDto.getQuantityDtoList();
            Long productId = productService.save(entityConverter.toProductEntity(productCreateDto, brandService.findBrand(productCreateDto.getBrandId())));
            for(QuantityDto quantityDto : quantityDtoList){
                quantityDto.setProductId(productId);
            }
            quantityService.saveAll(quantityDtoList);
            return ResponseDto.builder().code(ResponseStatus.OK.getStatusCode()).message("등록 성공").body(new ResponseBody<>(productId)).build();
        }catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseDto.builder().code(ResponseStatus.EXCEPTION.getStatusCode()).message(e.getMessage()).build();
        }
    }

    @PostMapping(value = "/detail/view")
    public ResponseDto detailView(@RequestBody ProductRequestDto dto){
        try {
            return ResponseDto.builder().code(ResponseStatus.OK.getStatusCode()).message("조회 성공").body(new ResponseBody<>(findProductDetail(dto.getProductId()))).build();
        }catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseDto.builder().code(ResponseStatus.EXCEPTION.getStatusCode()).message(e.getMessage()).build();
        }
    }

    @PostMapping(value = "/list")
    public ResponseDto productList(@RequestBody ProductRequestDto dto){
        List<ProductInfoDto> productList = productService.getProductList(dto.getBrandId());
        return ResponseDto.builder().code(ResponseStatus.OK.getStatusCode()).message("조회 성공").body(new ResponseBody<>(productList)).build();
    }

    @PostMapping(value = "/search")
    public ResponseDto searchList(@RequestBody ProductRequestDto dto){
        List<ProductInfoDto> searchList = productService.searchList(dto.getBrandId(), dto.getSearch());
        return ResponseDto.builder().code(ResponseStatus.OK.getStatusCode()).message("조회 성공").body(new ResponseBody<>(searchList)).build();
    }

    @PostMapping(value = "/update")
    public ResponseDto update(@RequestBody ProductInfoDto dto){
        try {
            productService.productIdCheck(dto.getId());
            productService.updateProduct(dto);
            ProductDetailDto productDetailDto = findProductDetail(dto.getId());
            syncService.syncToItemServiceForUpdate(productDetailDto);
        }catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseDto.builder().message(e.getMessage()).code(ResponseStatus.EXCEPTION.getStatusCode()).build();
        }catch (ParseException e){
            log.error(e.getMessage());
            return ResponseDto.builder().message(e.getMessage()).code(ResponseStatus.EXCEPTION.getStatusCode()).build();
        }
        return ResponseDto.builder().message("수정 성공").code(ResponseStatus.OK.getStatusCode()).build();
    }

    @PostMapping(value = "/delete")
    public ResponseDto delete(@RequestBody ProductRequestDto dto){
        try {
            productService.productIdCheck(dto.getProductId());
            quantityService.checkQuantityExistsByProductId(dto.getProductId());
            productImageService.checkImageExistsByProductId(dto.getProductId());
        }catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseDto.builder().message(e.getMessage()).code(ResponseStatus.EXCEPTION.getStatusCode()).build();
        }
        productService.deleteProduct(dto.getProductId());
        return ResponseDto.builder().message("삭제 성공").build();
    }

    @PostMapping(value = "/sync")
    public ResponseDto sync(){
        return ResponseDto.builder().message("삭제 성공").build();
    }

    private ProductDetailDto findProductDetail(Long productId) throws RuntimeException{
        Product product = productService.findProduct(productId);
        ProductInfoDto productInfoDto = productService.getDetailProduct(productId);
        List<QuantityDto> quantityDtoList = dtoConverter.toQuantityDtoList(quantityService.findQuantityList(product));
        List<SizeDto> sizeDtoList = sizeService.getSizeList(productId);
        List<ProductImageDto> productImageDtoList = dtoConverter.toProductImageDtoList(productImageService.getProductImageList(product));
        return new ProductDetailDto(productInfoDto, quantityDtoList, sizeDtoList, productImageDtoList);
    }

}
