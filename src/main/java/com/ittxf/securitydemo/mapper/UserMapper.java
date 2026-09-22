package com.ittxf.securitydemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ittxf.securitydemo.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
