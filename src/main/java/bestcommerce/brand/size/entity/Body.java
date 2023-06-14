package bestcommerce.brand.size.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "body")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Body {

    @Id
    @Column(name = "body_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @OneToMany(mappedBy = "body")
    private List<Size> sizeList = new ArrayList<>();

    public Body(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
