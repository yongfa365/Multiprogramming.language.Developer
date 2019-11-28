package yongfa365.dao;

import java.util.Collections;
import java.util.Map;

public class DBMock {
    public static Map<String, Object> executeSql(String sql) {
        if (sql.contains("Product")) {
            return Map.of("ProductId", 1254, "ProductName", "产品名称");
        } else if (sql.contains("User")) {
            return Map.of("UserId", 1001, "Name", "柳永法", "Age", 18, "Sex", "男");
        }
        return Collections.emptyMap();
    }
}
