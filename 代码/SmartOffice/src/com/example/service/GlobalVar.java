package com.example.service;

import android.R.integer;

public class GlobalVar {
	// 状态保存
	public static boolean light1 = false;//灯1的状态，默认为关闭
	public static boolean light2 = false;//灯2的状态，默认为关闭
	public static boolean light3 = false;//灯3的状态，默认为关闭
	public static boolean light4 = false;//灯4的状态，默认为关闭
	
	public static boolean room_inside_status=false;//室内明亮情况 ：true为亮 false为暗
	public static boolean room_outside_status=false;//室外明亮情况：true为亮 false为暗
	
	public static boolean door1_status=false;//门1开关状态   true为开 false为关
	public static boolean door2_status=false;//门2开关状态   true为开 false为关
	
	public static boolean attendance1=false;//人1考情 true为到勤  false为缺勤
	public static boolean attendance2=false;//人2考情 true为到勤  false为缺勤
	public static boolean attendance3=false;//人3考情 true为到勤  false为缺勤
	
	public static String pm25=null;//pm2.5的值
	public static String temperature=null;//温度值
	public static String temperature2=null;//温度值2
	public static String humidity=null;//湿度
	public static String air_quality=null;//空气质量指标
	
	public static boolean isLife = true;// 开关-A_Activity中使用   因为其onDestroy方法失效
}
