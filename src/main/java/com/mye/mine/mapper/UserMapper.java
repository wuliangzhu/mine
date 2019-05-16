package com.mye.mine.mapper;

import com.mye.mine.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    /**
     * 如果只有一个参数 @Param 可以省略
     * @param id
     * @return
     */
    User getUserById(@Param("id") int id);

    /**
     * 如果加了@Param sql 语句中就需要 user 这个参数，否则可以直接用user的属性
     * @param user
     * @return
     */
    int insertUser(User user);
}
