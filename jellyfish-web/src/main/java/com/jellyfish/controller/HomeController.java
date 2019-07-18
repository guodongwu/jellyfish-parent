package com.jellyfish.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jellyfish.common.FastDfsClient;
import com.jellyfish.common.JsonResult;
import com.jellyfish.entity.Etsuser;
import com.jellyfish.entity.User;
import com.jellyfish.enums.ResultCode;
import com.jellyfish.service.IEtsuserService;
import com.jellyfish.service.ISequenceService;
import com.jellyfish.service.impl.EtsuserServiceImpl;
import com.jellyfish.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Wrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HomeController {

    @Autowired
    private IEtsuserService etsuserService;
    @Autowired
    private ISequenceService sequenceService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    @Qualifier("mysqlJdbcTemplate")
    private JdbcTemplate mysqlJdbcTemplate;
    @Autowired
    @Qualifier("oracleJdbcTemplate")
    private JdbcTemplate oracleJdbcTemplate;
    @Autowired
    private FastDfsClient fastDfsClient;
    @RequestMapping(value = {"/","/home"})
    public User home(){
        User user = new User();
        user.setId("1");
        user.setName("张二虎");
        String json=redisUtils.get("user1");
        if(StringUtils.isBlank(json)) {
            user.setDesc("自建");
            json = JSONObject.toJSONString(user);
            redisUtils.set("user1", json);
        }else{
            user.setDesc("来自redis");
        }
        return user;
    }
    @RequestMapping("/mybatis")
    public JsonResult mybatis(){
        Etsuser etsuser=new Etsuser();
        etsuser.setName("老");
        etsuser.setId(5);
        List<Etsuser> list=etsuserService.list(etsuser);
        boolean r=etsuserService.saveOrUpdate(etsuser);
        boolean r2=etsuserService.saveOrUpdateForOracle(etsuser);
        JsonResult result;
        if(r && r2){
            result=new JsonResult(ResultCode.SUCCESS);
        }else{
            result=new JsonResult(ResultCode.ERROR);
        }
        result.setData(list);
        return  result;
    }
    @RequestMapping("/jdbc")
    public JsonResult jdbc(){
       List list=mysqlJdbcTemplate.query("select * from etsuser", new RowMapper<Etsuser>() {
            @Override
            public Etsuser mapRow(ResultSet resultSet, int i) throws SQLException {
                Etsuser etsuser=new Etsuser();
                etsuser.setId(resultSet.getInt("id"));
                etsuser.setName(resultSet.getString("name"));
                return etsuser;
            }
        });
        List list2=oracleJdbcTemplate.query("select * from etsuser", new RowMapper<Etsuser>() {
            @Override
            public Etsuser mapRow(ResultSet resultSet, int i) throws SQLException {
                Etsuser etsuser=new Etsuser();
                etsuser.setId(resultSet.getInt("id"));
                etsuser.setName(resultSet.getString("name"));
                return etsuser;
            }
        });
        Map<String,Object> map=new HashMap<>();
        map.put("mysql",list);
        map.put("oracle",list2);
        return  new JsonResult(ResultCode.SUCCESS,map);
    }
    // 上传文件
    @PostMapping(value = "/upload")
    public String upload(MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fileUrl = fastDfsClient.upload(file);
        return fileUrl;
    }
}
