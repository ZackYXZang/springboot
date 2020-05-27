package com.example.springboot.Controller;

import com.example.springboot.mapper.SysUserMapper;
import com.example.springboot.pojo.SysUser;
import com.example.springboot.service.CreateToken;
import com.example.springboot.service.UserLoginToken;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class LoginController {
    @Autowired
    SysUserMapper userService;
    @Autowired
    CreateToken tokenService;
    //登录
    @PostMapping("/ln")
    public Object login(@RequestBody SysUser user){
        JSONObject jsonObject=new JSONObject();
        SysUser userForBase=userService.findByUsername(user.getName());
        if(userForBase==null){
            jsonObject.put("message","登录失败,用户不存在");
            return jsonObject;
        }else {
            if (!userForBase.getPassword().equals(user.getPassword())){
                jsonObject.put("message","登录失败,密码错误");
                return jsonObject;
            }else {
                String token = tokenService.getToken(userForBase);
                jsonObject.put("token", token);
                jsonObject.put("user", userForBase);
                return jsonObject;
            }
        }
    }
    @UserLoginToken
    @GetMapping("/getMessage")
    public String getMessage(){
        return "你已通过验证";
    }
}
