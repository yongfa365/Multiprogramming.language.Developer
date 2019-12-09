package yongfa365.dao;

import yongfa365.mybatis.annotation.Select;
import yongfa365.domain.Product;

public interface ProductMapper {

    @Select("select * from Product where ProductName='#{productName}' and ProductId = #{productId}")
    Product getProduct(Integer productId,String productName);
}
