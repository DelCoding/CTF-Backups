package com.eliteams.quick4j.web.controller;

import com.eliteams.quick4j.web.model.User;
import com.eliteams.quick4j.web.service.UserService;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

@Controller
@RequestMapping({"/user"})
public class UserController
{
  public static final String hintFile = "/flag/hint.txt";
  @Resource
  private UserService userService;
  
  @RequestMapping(value={"/login"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String login(@Valid User user, BindingResult result, Model model, HttpServletRequest request)
  {
    try
    {
      Subject subject = SecurityUtils.getSubject();
      if (subject.isAuthenticated()) {
        return "redirect:/";
      }
      if (result.hasErrors())
      {
        model.addAttribute("error", "参数错误！");
        return "login";
      }
      if ((user.getUsername().isEmpty()) || (user.getUsername() == null) || 
        (user.getPassword().isEmpty()) || (user.getPassword() == null)) {
        return "login";
      }
      subject.login(new UsernamePasswordToken(user.getUsername(), user.getPassword()));
      
      User authUserInfo = this.userService.selectByUsername(user.getUsername());
      request.getSession().setAttribute("userInfo", authUserInfo);
    }
    catch (AuthenticationException e)
    {
      model.addAttribute("error", "用户名或密码错误 ！");
      return "login";
    }
    return "redirect:/";
  }
  
  @RequestMapping(value={"/logout"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String logout(HttpSession session)
  {
    session.removeAttribute("userInfo");
    
    Subject subject = SecurityUtils.getSubject();
    subject.logout();
    return "login";
  }
  
  @RequestMapping(value={"/admin"}, produces={"text/html;charset=UTF-8"})
  @ResponseBody
  @RequiresRoles({"admin"})
  public String admin()
  {
    return "拥有admin角色,能访问";
  }
  
  @RequestMapping(value={"/create"}, produces={"text/html;charset=UTF-8"})
  @ResponseBody
  @RequiresPermissions({"user:create"})
  public String create()
  {
    return "拥有user:create权限,能访问";
  }
  
  @RequestMapping(value={"/getInfomation"}, produces={"text/html;charset=UTF-8"})
  @ResponseBody
  @RequiresRoles({"guest"})
  public ResponseEntity<byte[]> download(HttpServletRequest request, String filename)
    throws IOException
  {
    if ((filename.contains("../")) || (filename.contains("./")) || (filename.contains("..\\")) || (filename.contains(".\\"))) {
      return null;
    }
    String path = request.getServletContext().getRealPath("/");
    System.out.println(path);
    
    File file = new File(path + File.separator + filename);
    HttpHeaders headers = new HttpHeaders();
    
    String downloadFielName = new String(filename.getBytes("UTF-8"), "iso-8859-1");
    
    headers.setContentDispositionFormData("attachment", downloadFielName);
    
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    return new ResponseEntity(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
  }
  
  @RequestMapping(value={"/nicaicaikan_url_23333_secret"}, produces={"text/html;charset=UTF-8"})
  @ResponseBody
  @RequiresRoles({"super_admin"})
  public String xmlView(String xmlData)
  {
    if (xmlData.length() >= 1000) {
      return "Too long~~";
    }
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    
    factory.setExpandEntityReferences(true);
    try
    {
      DocumentBuilder builder = factory.newDocumentBuilder();
      
      InputStream xmlInputStream = new ByteArrayInputStream(xmlData.getBytes());
      
      Document localDocument = builder.parse(xmlInputStream);
    }
    catch (ParserConfigurationException e)
    {
      e.printStackTrace();
      return "ParserConfigurationException";
    }
    catch (SAXException e)
    {
      e.printStackTrace();
      return "SAXException";
    }
    catch (IOException e)
    {
      e.printStackTrace();
      return "IOException";
    }
    return "ok~ try to read /flag/hint.txt";
  }
}
