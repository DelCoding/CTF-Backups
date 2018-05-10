package com.eliteams.quick4j.web.model;

import java.util.Date;

public class User
{
  private Long id;
  private String username;
  private String password;
  private String state;
  private Date createTime;
  
  public User() {}
  
  public User(String username, String password)
  {
    this.username = username;
    this.password = password;
  }
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long id)
  {
    this.id = id;
  }
  
  public String getUsername()
  {
    return this.username;
  }
  
  public void setUsername(String username)
  {
    this.username = (username == null ? null : username.trim());
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String password)
  {
    this.password = (password == null ? null : password.trim());
  }
  
  public String getState()
  {
    return this.state;
  }
  
  public void setState(String state)
  {
    this.state = (state == null ? null : state.trim());
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }
  
  public String toString()
  {
    return "User [id=" + this.id + ", username=" + this.username + ", password=" + this.password + ", state=" + this.state + ", createTime=" + this.createTime + "]";
  }
}
