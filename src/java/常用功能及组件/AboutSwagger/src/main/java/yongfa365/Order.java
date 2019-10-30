package yongfa365;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.naming.Name;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@ApiModel(description = "订单")
@Data
public class Order {
    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("订单联系人") //TODO：swagger不显示这个
    private OrderPerson contact;

    @ApiModelProperty("紧急联系人")
    private OrderPerson emergencyContact;

    @ApiModelProperty("其他联系人")
    private List<OrderPerson> others;

    private Long id;
    private Integer age;
    private LocalDateTime birthday;
    private Float height;
    private BigDecimal totalAmout;


}

//@ApiModel("这个是替换名字的，一般不写，如果想给类改个好看的名字可以写这个")
@ApiModel(description = "订单各种人")
@Data
class OrderPerson {

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("性别")
    private Boolean isMain;
}
