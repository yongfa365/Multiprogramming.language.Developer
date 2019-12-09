package yongfa365;

import yongfa365.domain.Product;
import yongfa365.mybatis.MapperProxy;
import yongfa365.dao.ProductMapper;
import yongfa365.dao.UserMapper;
import yongfa365.domain.User;


public class App {
    public static void main(String[] args) {
        UserMapper userMapper = MapperProxy.newInstance(UserMapper.class);
        User user = userMapper.getUserById(1001);
        System.out.println(user);
        System.out.println(userMapper);

        System.out.println("========================================================================".repeat(2));

        ProductMapper productMapper = MapperProxy.newInstance(ProductMapper.class);
        Product product = productMapper.getProduct(1254,"产品名称");
        System.out.println(product);
        System.out.println(productMapper);

        // spring与mybatis结合后所做的是：将以上的userMapper或productMapper放到IOC里，
        // 这样spring或mybatis这些框架底层做了什么就不用使用者关心了。

    }
}
