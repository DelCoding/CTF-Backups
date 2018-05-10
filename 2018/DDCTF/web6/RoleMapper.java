package com.eliteams.quick4j.web.dao;

import com.eliteams.quick4j.core.generic.GenericDao;
import com.eliteams.quick4j.web.model.Role;
import com.eliteams.quick4j.web.model.RoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public abstract interface RoleMapper
  extends GenericDao<Role, Long>
{
  public abstract int countByExample(RoleExample paramRoleExample);
  
  public abstract int deleteByExample(RoleExample paramRoleExample);
  
  public abstract int deleteByPrimaryKey(Long paramLong);
  
  public abstract int insert(Role paramRole);
  
  public abstract int insertSelective(Role paramRole);
  
  public abstract List<Role> selectByExample(RoleExample paramRoleExample);
  
  public abstract Role selectByPrimaryKey(Long paramLong);
  
  public abstract int updateByExampleSelective(@Param("record") Role paramRole, @Param("example") RoleExample paramRoleExample);
  
  public abstract int updateByExample(@Param("record") Role paramRole, @Param("example") RoleExample paramRoleExample);
  
  public abstract int updateByPrimaryKeySelective(Role paramRole);
  
  public abstract int updateByPrimaryKey(Role paramRole);
  
  public abstract List<Role> selectRolesByUserId(Long paramLong);
}
