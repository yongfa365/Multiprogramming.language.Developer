package yongfa365;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@ApiModel("订单")
@Data
public class Order {
    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("订单联系人") //TODO：swagger不显示这个
    private OrderPerson contact;

    @ApiModelProperty("紧急联系人")
    private OrderPerson emergencyContact;

    private Long id;
    private Integer age;
    private LocalDateTime birthday;
    private Float height;
    private BigDecimal totalAmout;


    @ApiModel("订单各种人")
    public static class OrderPerson {

        @ApiModelProperty("姓名")
        private String name;

        @ApiModelProperty("性别")
        private Boolean isMain;
    }
}
