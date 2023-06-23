package bestcommerce.brand.product.repository;

import bestcommerce.brand.product.dto.ProductInfoDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;


import java.util.List;

import static bestcommerce.brand.manager.entity.QBrand.brand;
import static bestcommerce.brand.manager.entity.QManager.manager;
import static bestcommerce.brand.product.entity.QProduct.product;
import static bestcommerce.brand.product.entity.QProductImage.productImage;

@Slf4j
@Repository
public class ProductRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public ProductRepositorySupport(JPAQueryFactory queryFactory) {
        super(ProductInfoDto.class);
        this.queryFactory = queryFactory;
    }

    public ProductInfoDto getDetailProductDetail(Long productId){
        return queryFactory.select(Projections.constructor(ProductInfoDto.class,
                    product.id.as("id"),
                    product.productCode.as("productCode"),
                    product.name.as("productName"),
                    product.productPrice.as("productPrice"),
                    product.info.as("productInfo"),
                    product.registerDate.as("productRegisterDate"),
                    brand.id.as("brandId"),
                    brand.name.as("brandName"),
                    brand.logo.as("brandLogoImage")
                ))
                .from(product)
                .innerJoin(product.brand, brand)
                .where(product.id.eq(productId)).fetchOne();
    }

    public List<ProductInfoDto> getProductList(String managerEmail){
        return queryFactory.select(Projections.constructor(ProductInfoDto.class,
                        product.id.as("id"),
                        product.name.as("productName"),
                        product.productPrice.as("productPrice"),
                        product.info.as("productInfo"),
                        product.registerDate.as("productRegisterDate"),
                        productImage.img.as("productThumbnail")
                ))
                .from(product)
                .innerJoin(productImage)
                .innerJoin(brand)
                .innerJoin(manager)
                .on(manager.managerEmail.eq(managerEmail))
                .where(productImage.type.eq("TITLE"))
                .fetch();
    }
}
