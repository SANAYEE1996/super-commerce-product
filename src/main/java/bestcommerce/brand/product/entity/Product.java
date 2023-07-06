package bestcommerce.brand.product.entity;


import bestcommerce.brand.manager.entity.Brand;
import bestcommerce.brand.manager.entity.Manager;
import bestcommerce.brand.size.entity.Quantity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int productPrice;

    @Column(name = "info")
    private String info;

    @Column(name = "register_date")
    private String registerDate;

    @Column(name = "modify_date")
    private String modifyDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Manager manager;

    @OneToMany(mappedBy = "product")
    private List<Quantity> quantityList = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<ProductImage> imageList = new ArrayList<>();

    public Product(Long id, String productCode, String name, int productPrice, String info, String registerDate, String modifyDate, Brand brand, Manager manager) {
        this.id = id;
        this.productCode = productCode;
        this.name = name;
        this.productPrice = productPrice;
        this.info = info;
        this.registerDate = registerDate;
        this.modifyDate = modifyDate;
        this.brand = brand;
        this.manager = manager;
    }
}
