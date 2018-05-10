package com.eliteams.quick4j.web.service;

import com.eliteams.quick4j.core.generic.GenericService;
import com.eliteams.quick4j.web.model.User;

public abstract interface UserService
  extends GenericService<User, Long>
{
  public abstract User authentication(User paramUser);
  
  public abstract User selectByUsername(String paramString);
}
