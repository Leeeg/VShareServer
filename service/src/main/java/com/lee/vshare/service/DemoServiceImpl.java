package com.lee.vshare.service;


import com.lee.vshare.common.DemoService;

public class DemoServiceImpl implements DemoService {

	public String sayHello(String name) {
		return "Welcome to Minbo's Dubbo, Hello " + name;
	}

}
