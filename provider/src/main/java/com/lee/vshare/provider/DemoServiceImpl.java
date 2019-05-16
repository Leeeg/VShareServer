package com.lee.vshare.provider;


public class DemoServiceImpl implements DemoService {

	public String sayHello(String name) {
		System.out.println("provider sayHello ");
		return "Welcome to Minbo's Dubbo, Hello " + name;
	}

}
