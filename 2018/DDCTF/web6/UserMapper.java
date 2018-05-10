package com.eliteams.quick4j.web.dao;

import com.eliteams.quick4j.core.feature.orm.mybatis.Page;
import com.eliteams.quick4j.core.generic.GenericDao;
import com.eliteams.quick4j.web.model.User;
import com.eliteams.quick4j.web.model.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public abstract interface UserMapper
  extends GenericDao<User, Long>
{
  public abstract int countByExample(UserExample paramUserExample);
  
  public abstract int deleteByExample(UserExample paramUserExample);
  
  public abstract int deleteByPrimaryKey(Long paramLong);
  
  public abstract int insert(User paramUser);
  
  public abstract int insertSelective(User paramUser);
  
  public abstract List<User> selectByExample(UserExample paramUserExample);
  
  public abstract User selectByPrimaryKey(Long paramLong);
  
  public abstract int updateByExampleSelective(@Param("record") User paramUser, @Param("example") UserExample paramUserExample);
  
  public abstract int updateByExample(@Param("record") User paramUser, @Param("example") UserExample paramUserExample);
  
  public abstract int updateByPrimaryKeySelective(User paramUser);
  
  public abstract int updateByPrimaryKey(User paramUser);
  
  public abstract User authentication(@Param("record") User paramUser);
  
  public abstract List<User> selectByExampleAndPage(Page<User> paramPage, UserExample paramUserExample);
}
