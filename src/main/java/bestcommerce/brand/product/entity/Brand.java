package bestcommerce.brand.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "brand")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Brand {

    @Id
    @Column(name = "brand_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "intro")
    private String intro;

    @Column(name = "business_address")
    private String address;

    @Column(name = "logo_img")
    private String logo;

    @Column(name = "register_date")
    private String registerDate;

    @OneToMany(mappedBy = "brand")
    private List<Product> productList = new ArrayList<>();
}
