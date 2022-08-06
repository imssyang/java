package com.demo;

public class Worker
{
	public String serial;
	public String number;
	public String action;
	public String date;
	public String flag;
	public String reason;
	public String source;

	public Worker(String serial,String number, String action, String date, String flag, String reason, String source) {
		this.serial = serial;
		this.number = number;
		this.action = action;
		this.date = date;
		this.flag = flag;
		this.reason = reason;
		this.source = source;
	}
};