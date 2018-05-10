package com.eliteams.quick4j.web.dao;

import com.eliteams.quick4j.core.generic.GenericDao;
import com.eliteams.quick4j.web.model.Permission;
import com.eliteams.quick4j.web.model.PermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public abstract interface PermissionMapper
  extends GenericDao<Permission, Long>
{
  public abstract int countByExample(PermissionExample paramPermissionExample);
  
  public abstract int deleteByExample(PermissionExample paramPermissionExample);
  
  public abstract int deleteByPrimaryKey(Long paramLong);
  
  public abstract int insert(Permission paramPermission);
  
  public abstract int insertSelective(Permission paramPermission);
  
  public abstract List<Permission> selectByExample(PermissionExample paramPermissionExample);
  
  public abstract Permission selectByPrimaryKey(Long paramLong);
  
  public abstract int updateByExampleSelective(@Param("record") Permission paramPermission, @Param("example") PermissionExample paramPermissionExample);
  
  public abstract int updateByExample(@Param("record") Permission paramPermission, @Param("example") PermissionExample paramPermissionExample);
  
  public abstract int updateByPrimaryKeySelective(Permission paramPermission);
  
  public abstract int updateByPrimaryKey(Permission paramPermission);
  
  public abstract List<Permission> selectPermissionsByRoleId(Long paramLong);
}
