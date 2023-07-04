package bestcommerce.brand.manager;

import lombok.Getter;

@Getter
public enum ManagerRole {

    NONE("NONE"),
    BRAND_MASTER("BRAND_MASTER"),
    BRAND_MANAGER("BRAND_MANAGER");

    String role;

    ManagerRole(String role) {
        this.role = role;
    }
}
