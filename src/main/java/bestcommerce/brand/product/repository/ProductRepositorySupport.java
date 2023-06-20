package bestcommerce.brand.product.repository;

import bestcommerce.brand.product.dto.ProductInfoDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;


import static bestcommerce.brand.manager.entity.QBrand.brand;
import static bestcommerce.brand.product.entity.QProduct.product;

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
}
