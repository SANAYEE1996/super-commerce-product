package bestcommerce.brand.product.repository;

import bestcommerce.brand.product.dto.ProductImageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
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

    @Transactional
    public void updateAll(List<ProductImageDto> productImageDtoList){
        String sql = "UPDATE product_img SET odr = ? " +
                     "WHERE id = ?";

        jdbcTemplate.batchUpdate(sql,
                productImageDtoList,
                productImageDtoList.size(),
                (PreparedStatement ps, ProductImageDto productImageDto) -> {
                    ps.setInt(1, productImageDto.getOdr());
                    ps.setLong(2, productImageDto.getImageId());
                });
    }

    @Transactional
    public void deleteAll(List<ProductImageDto> productImageDtoList){
        String sql = "DELETE FROM product_img WHERE id = ?";

        jdbcTemplate.batchUpdate(sql,
                productImageDtoList,
                productImageDtoList.size(),
                (PreparedStatement ps, ProductImageDto productImageDto) -> {
                    ps.setLong(1, productImageDto.getImageId());
                });
    }
}
