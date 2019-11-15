package yongfa365.dao;

import yongfa365.core.annotation.Select;
import yongfa365.domain.User;

public interface UserMapper {

    @Select("select * from Users where UserId = #{id}")
    User getUserById(Integer id);
}
