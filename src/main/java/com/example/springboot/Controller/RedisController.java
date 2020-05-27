package com.example.springboot.Controller;

import com.example.springboot.pojo.IMoocJSONResult;
import com.example.springboot.pojo.User;
import com.example.springboot.utils.JsonUtils;
import com.example.springboot.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("redis")
public class RedisController {
	
	@Autowired
	private StringRedisTemplate strRedis;
	
	@Autowired
	private RedisOperator redis;
	/*
	@RequestMapping("/test")
	public IMoocJSONResult test() {

		strRedis.opsForValue().set("imooc-cache", "hello 慕课网~~~~~~");

		SysUser user = new SysUser();
		user.setId("100111");
		user.setUsername("imooc");
		user.setPassword("abc123");
		user.setIsDelete(0);
		user.setRegistTime(new Date());
		strRedis.opsForValue().set("json:user", JsonUtils.objectToJson(user));

		SysUser jsonUser = JsonUtils.jsonToPojo(strRedis.opsForValue().get("json:user"), SysUser.class);

		return IMoocJSONResult.ok(jsonUser);
	}*/
	
	@RequestMapping("/getJsonList")
	public IMoocJSONResult getJsonList() {
		


		
		List<User> userList = new ArrayList<>();

		
		redis.set("json:info:userlist", JsonUtils.objectToJson(userList), 2000);
		
		String userListJson = redis.get("json:info:userlist");
		List<User> userListBorn = JsonUtils.jsonToList(userListJson, User.class);
		
		return IMoocJSONResult.ok(userListBorn);
	}
}