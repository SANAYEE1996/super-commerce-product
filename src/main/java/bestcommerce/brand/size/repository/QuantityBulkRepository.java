package bestcommerce.brand.size.repository;

import bestcommerce.brand.size.dto.QuantityDto;
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
public class QuantityBulkRepository {

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void saveAll(List<QuantityDto> quantityDtoList){
        String sql = "INSERT INTO quantity (product_id, measure_name, remain) " +
                     "VALUES (?, ?, ?) ";

        jdbcTemplate.batchUpdate(sql,
                quantityDtoList,
                quantityDtoList.size(),
                (PreparedStatement ps, QuantityDto quantityDto) -> {
                    ps.setLong(1, quantityDto.getProductId());
                    ps.setString(2, quantityDto.getQuantityName());
                    ps.setInt(3, quantityDto.getQuantity());
                });
    }
}
