package bestcommerce.brand.size.repository;

import bestcommerce.brand.size.dto.SizeDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static bestcommerce.brand.product.entity.QProduct.product;
import static bestcommerce.brand.size.entity.QQuantity.quantity;
import static bestcommerce.brand.size.entity.QSize.size;
import static bestcommerce.brand.size.entity.QBody.body;

@Slf4j
@Repository
public class SizeSearchRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public SizeSearchRepository(JPAQueryFactory queryFactory) {
        super(SizeDto.class);
        this.queryFactory = queryFactory;
    }

    public List<SizeDto> getSizeInfoForProductDetail(Long productId){
        return queryFactory.select(Projections.constructor(SizeDto.class,
                        size.id.as("sizeId"),
                        quantity.id.as("quantityId"),
                        quantity.measureName.as("measureName"),
                        body.id.as("bodyId"),
                        body.name.as("bodyName"),
                        size.value.as("sizeValue")
                ))
                .from(size)
                .leftJoin(size.quantity, quantity)
                .leftJoin(size.body, body)
                .leftJoin(quantity.product, product)
                .on(product.id.eq(productId)).fetch();
    }
}
