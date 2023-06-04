package bestcommerce.brand.product.controller;

import bestcommerce.brand.manager.service.BrandService;
import bestcommerce.brand.manager.service.ManagerService;
import bestcommerce.brand.product.service.ProductImageService;
import bestcommerce.brand.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final BrandService brandService;

    private final ManagerService managerService;

    private final ProductService productService;

    private final ProductImageService productImageService;

    @PostMapping(value = "/save")
    public void save(){

    }
}
