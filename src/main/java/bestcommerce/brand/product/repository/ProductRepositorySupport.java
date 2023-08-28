package bestcommerce.brand.product.repository;

import bestcommerce.brand.product.dto.ProductInfoDto;
import bestcommerce.brand.util.TimeFormat;
import com.querydsl.core.dml.UpdateClause;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;


import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static bestcommerce.brand.product.entity.QBrand.brand;
import static bestcommerce.brand.product.entity.QProduct.product;
import static bestcommerce.brand.product.entity.QProductImage.productImage;

@Slf4j
@Repository
public class ProductRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    @Value("${img.url.header}")
    private String imgUrlHeader;

    public ProductRepositorySupport(JPAQueryFactory queryFactory) {
        super(ProductInfoDto.class);
        this.queryFactory = queryFactory;
    }

    public ProductInfoDto getDetailProduct(Long productId){
        return listInitial()
                .where(product.id.eq(productId)).fetchOne();
    }

    public List<ProductInfoDto> getProductList(Long brandId){
        return listInitial(brandId)
                .where(productImage.type.eq("TITLE").and(productImage.odr.eq(0)))
                .fetch();
    }

    public List<ProductInfoDto> searchList(Long brandId, String search){
        return listInitial(brandId)
                .where(productImage.type.eq("TITLE")
                        .and(productImage.odr.eq(0))
                        .and(product.info.contains(search)
                            .or(product.name.contains(search))
                            .or(brand.name.contains(search))))
                .fetch();
    }

    @Modifying
    @Transactional
    public void updateProductInfo(ProductInfoDto dto){
        UpdateClause<JPAUpdateClause> builder = update(product);
        setUpdateProductBuilder(dto,builder);
        builder.set(product.modifyDate, LocalDateTime.now().format(TimeFormat.timeFormatter));
        builder.where(product.id.eq(dto.getId())).execute();
    }

    private JPAQuery<ProductInfoDto> listInitial(){
        return queryFactory.select(Projections.constructor(ProductInfoDto.class,
                        product.id.as("id"),
                        product.productCode.as("productCode"),
                        product.name.as("productName"),
                        product.productPrice.as("productPrice"),
                        product.info.as("productInfo"),
                        product.registerDate.as("productRegisterDate"),
                        brand.id.as("brandId"),
                        brand.name.as("brandName"),
                        brand.logo.prepend(imgUrlHeader).as("brandLogoImage")
                ))
                .from(product)
                .innerJoin(product.brand, brand);
    }

    private JPAQuery<ProductInfoDto> listInitial(Long brandId){
        return queryFactory.select(Projections.constructor(ProductInfoDto.class,
                        product.id.as("id"),
                        product.name.as("productName"),
                        product.productPrice.as("productPrice"),
                        product.registerDate.as("productRegisterDate"),
                        productImage.img.prepend(imgUrlHeader).as("productThumbnail")
                ))
                .from(product)
                .innerJoin(productImage).on(productImage.product.eq(product))
                .innerJoin(product.brand).on(product.brand.id.eq(brandId));
    }

    private void setUpdateProductBuilder(ProductInfoDto dto, UpdateClause<JPAUpdateClause> builder){
        if(StringUtils.hasText(dto.getProductCode())){
            builder.set(product.productCode, dto.getProductCode());
        }
        if(StringUtils.hasText(dto.getProductName())){
            builder.set(product.name, dto.getProductName());
        }
        if(StringUtils.hasText(dto.getProductInfo())){
            builder.set(product.info, dto.getProductInfo());
        }
    }
}
