package com.ssm.vue.jd.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.junit.Test;

//生成商品编号
public class CreateOrderId {

	private static long tmpID = 0;

	private static boolean tmpIDlocked = false;

	public static long createOrderId() {
		long ltime = 0;
		while (true) {
			if (tmpIDlocked == false) {
				tmpIDlocked = true;
				// 当前：（年、月、日、时、分、秒、毫秒）*10000
				ltime = Long.valueOf(new SimpleDateFormat("yyMMddhhmmssSSS").format(new Date()).toString()) * 10000;
				if (tmpID < ltime) {
					tmpID = ltime;
				} else {
					tmpID = tmpID + 1;
					ltime = tmpID;
				}
				tmpIDlocked = false;
				return ltime;
			}
		}
	}
	
	@Test
	public void test1() {
		for(int i = 0; i< 1000000; i++) {
			long l = this.createOrderId();
		System.out.println(l);}
		
	}

}
