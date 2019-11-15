package yongfa365.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Product {
    private Integer productId;
    private String productName;
    private LocalDateTime expireDate;
}
