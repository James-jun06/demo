package com.cmcc.hy.learnDemo.Controller;

import com.cmcc.hy.learnDemo.Utils.QRcodeUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * Project:  learnDemo
 * File Created by James on 2018/2/13
 *
 * Copyright 2018 CMCC Corporation Limited.
 * All rights reserved.
 */
@RestController
public class HelloController {

    @GetMapping("/")
    public String index(){
        return "welcome to new page";
    }

    @ApiOperation(value = "获取用户信息", notes = "111")
    //@ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String")
    @GetMapping("/hello")
    public String hello(@RequestParam("id")String id){
        return "id="+id;
    }

    @GetMapping("/qrcode")
    public String qrcodeGenerate(HttpServletResponse response){
        return QRcodeUtil.showQRCode(response);
    }

    @GetMapping("/qrcode/loginCheck")
    public ResponseEntity<?> appQrcodeLogin(@RequestParam("token") String token,
                                            @RequestParam(value = "key", required = false) String key){
        HttpHeaders headers = new HttpHeaders();
        headers.set("location", "http://www.baidu.com");//如果不是内部apps扫描，则跳转至官网
        if(key == null){
            return new ResponseEntity<>(headers, HttpStatus.TEMPORARY_REDIRECT);
        }
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
