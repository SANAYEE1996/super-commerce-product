package bestcommerce.brand.product.repository;

import bestcommerce.brand.product.dto.ProductImageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.PreparedStatement;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductImageBulkRepository {

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void saveAll(List<ProductImageDto> productImageDtoList){
        String sql = "INSERT INTO product_img (product_id, type, img, odr) " +
                     "VALUES (?, ?, ?, ?) ";

        jdbcTemplate.batchUpdate(sql,
                productImageDtoList,
                productImageDtoList.size(),
                (PreparedStatement ps, ProductImageDto productImageDto) -> {
                    ps.setLong(1, productImageDto.getProductId());
                    ps.setString(2, productImageDto.getType());
                    ps.setString(3, productImageDto.getImage());
                    ps.setInt(4, productImageDto.getOdr());
                });
    }
}
