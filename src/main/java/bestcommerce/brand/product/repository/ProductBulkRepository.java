package bestcommerce.brand.product.repository;

import bestcommerce.brand.product.dto.ProductInfoDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductBulkRepository {

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void saveAll(List<ProductInfoDto> productInfoDtoList){

        String sql = "INSERT INTO product (brand_id, product_code, name, price, info) "
                    +"VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, productInfoDtoList, productInfoDtoList.size(),
                (PreparedStatement ps, ProductInfoDto productInfoDto) -> {
               ps.setLong(1, productInfoDto.getBrandId());
               ps.setString(2, productInfoDto.getProductCode());
               ps.setString(3, productInfoDto.getProductName());
               ps.setInt(4, productInfoDto.getProductPrice());
               ps.setString(5, productInfoDto.getProductInfo());
        });

    }
}
