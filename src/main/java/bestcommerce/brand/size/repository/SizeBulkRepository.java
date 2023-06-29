package bestcommerce.brand.size.repository;

import bestcommerce.brand.size.dto.SizeDto;
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
public class SizeBulkRepository {

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void saveAll(List<SizeDto> sizeDtoList){
        String sql = "INSERT INTO size (quantity_id, body_id, value) "+
                     "VALUES (?,?,?)";

        jdbcTemplate.batchUpdate(sql,
                sizeDtoList,
                sizeDtoList.size(),
                (PreparedStatement ps, SizeDto sizeDto) -> {
                    ps.setLong(1, sizeDto.getQuantityId());
                    ps.setLong(2, sizeDto.getBodyId());
                    ps.setString(3, sizeDto.getSizeValue());
                });
    }

    @Transactional
    public void deleteAll(Long productId){
        String sql = "DELETE s FROM size s INNER JOIN quantity q ON s.quantity_id = q.quantity_id WHERE q.product_id = ? ";

        jdbcTemplate.update(sql, productId);
    }
}
