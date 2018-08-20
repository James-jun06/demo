package com.cmcc.hy.learnDemo.Dto;

/**
 * Project:  learnDemo
 * File Created by James on 2018/6/28
 *
 * Copyright 2018 CMCC Corporation Limited.
 * All rights reserved.
 */
public class HelloMessage {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public HelloMessage(){

    }

    public HelloMessage(String name) {
        this.name = name;
    }
}
