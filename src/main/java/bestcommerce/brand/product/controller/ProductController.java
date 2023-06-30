package bestcommerce.brand.product.controller;

import bestcommerce.brand.manager.entity.Brand;
import bestcommerce.brand.manager.entity.Manager;
import bestcommerce.brand.manager.service.BrandService;
import bestcommerce.brand.manager.service.ManagerService;
import bestcommerce.brand.product.dto.*;
import bestcommerce.brand.product.entity.Product;
import bestcommerce.brand.size.dto.QuantityDto;
import bestcommerce.brand.product.service.ProductImageService;
import bestcommerce.brand.product.service.ProductService;
import bestcommerce.brand.size.dto.SizeDto;
import bestcommerce.brand.size.service.QuantityService;
import bestcommerce.brand.size.service.SizeService;
import bestcommerce.brand.util.DtoConverter;
import bestcommerce.brand.util.EntityConverter;
import bestcommerce.brand.util.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final BrandService brandService;

    private final ManagerService managerService;

    private final ProductService productService;

    private final ProductImageService productImageService;

    private final QuantityService quantityService;

    private final SizeService sizeService;

    private final EntityConverter entityConverter;

    private final DtoConverter dtoConverter;


    @PostMapping(value = "/save")
    public ResponseDto save(@RequestBody ProductCreateDto productCreateDto){
        Brand brand = brandService.findBrand(productCreateDto.getBrandId());
        Manager manager = managerService.findManager(productCreateDto.getManagerEmail());
        List<QuantityDto> quantityDtoList = productCreateDto.getQuantityDtoList();
        Long productId = productService.save(entityConverter.toProductEntity(productCreateDto,brand,manager));
        for(QuantityDto quantityDto : quantityDtoList){
            quantityDto.setProductId(productId);
        }
        quantityService.saveAll(quantityDtoList);
        return ResponseDto.builder().message("등록 성공").productId(productId).build();
    }

    @PostMapping(value = "/detail/view")
    public ProductDetailDto detailView(@RequestBody ProductRequestDto dto){
        Product product = productService.findProduct(dto.getProductId());
        ProductInfoDto productInfoDto = productService.getDetailProduct(dto.getProductId());
        List<QuantityDto> quantityDtoList = dtoConverter.toQuantityDtoList(quantityService.findQuantityList(product));
        List<SizeDto> sizeDtoList = sizeService.getSizeList(dto.getProductId());
        List<ProductImageDto> productImageDtoList = dtoConverter.toProductImageDtoList(productImageService.getProductImageList(product));
        return new ProductDetailDto(productInfoDto, quantityDtoList, sizeDtoList, productImageDtoList);
    }

    @PostMapping(value = "/list")
    public List<ProductInfoDto> productList(@RequestBody ProductRequestDto dto){
        return productService.getProductList(dto.getManagerEmail());
    }

    @PostMapping(value = "/search")
    public List<ProductInfoDto> searchList(@RequestBody ProductRequestDto dto){
        return productService.searchList(dto.getManagerEmail(), dto.getSearch());
    }

    @PostMapping(value = "/update")
    public ResponseDto update(@RequestBody ProductRequestDto dto){
        return ResponseDto.builder().message("수정 성공").build();
    }

    @PostMapping(value = "/delete")
    public ResponseDto delete(@RequestBody ProductRequestDto dto){
        return ResponseDto.builder().message("삭제 성공").build();
    }

}
