package bestcommerce.brand.size.entity;

import bestcommerce.brand.product.entity.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "quantity")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quantity {

    @Id
    @Column(name = "quantity_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "measure_name")
    private String measureName;

    @Column(name = "remain")
    private int remain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "quantity")
    private List<Size> sizeList = new ArrayList<>();

    public Quantity(Long id, String measureName, int remain, Product product) {
        this.id = id;
        this.measureName = measureName;
        this.remain = remain;
        this.product = product;
    }
}
