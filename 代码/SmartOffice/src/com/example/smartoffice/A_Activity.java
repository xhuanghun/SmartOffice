package com.example.smartoffice;

import java.net.ContentHandler;
import java.util.ArrayList;

import com.example.adapter.A_ListAdapter;
import com.example.bean.LightMsgItem;
import com.example.bean.StatuMsgItem;
import com.example.service.GlobalVar;
import com.example.service.HttpServer;
import com.example.tools.SysApplication;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;
import android.provider.Settings.Global;

public class A_Activity extends Activity {

	private Context context;
	private ListView myListView;
	private A_ListAdapter myAdapter;
	// 数据
	private ArrayList<StatuMsgItem> dataArrayList = new ArrayList<>();
	private static final int HEART_BEAT = 5000;// 每隔5秒访问一次网络
	private static final int NET_LOST = 1;// 网速不好
	private static final int MSG = 2;// 温度一

	

	private Handler myHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			switch (msg.what) {
			case NET_LOST:
				Toast.makeText(context, "网络不好！", Toast.LENGTH_SHORT).show();
				break;
			case MSG:
				// 拆分数据
				splitMsg(msg.obj.toString().trim());
				// 刷新UI
				freshView();
				break;

			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a);
		SysApplication.getInstance().addActivity(this);
		context = getApplicationContext();

		InitMsg();
		InitView();
		InitNetWork();
	}

	/**
	 * 初始化网络业务 这里是：访问接口更新数据 使用getData方法获取
	 */

	private void InitNetWork() {
		// TODO Auto-generated method stub
		final HttpServer httpServer = new HttpServer();
		GlobalVar.isLife=true;//因为推出前设置为false了  故开启前要先设置为true
		
		Runnable netRunnable = new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (GlobalVar.isLife) {
					// 温度一
					String temp1String = httpServer
							.getData("http://wocaowocao.duapp.com/TemperatureReadAction?id=1");
					if (temp1String != null) {
						if (!temp1String.equals("")) {
							myHandler.sendMessage(myHandler.obtainMessage(MSG,
									temp1String));
						}
					} else {
						// myHandler.sendEmptyMessage(NET_LOST);
					}

					try {
						Thread.sleep(HEART_BEAT);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		new Thread(netRunnable).start();

	}

	/**
	 * 填充dataArrayList
	 */
	private void InitMsg() {
		// TODO Auto-generated method stub
		StatuMsgItem item = new StatuMsgItem(R.drawable.icon_a_orange_bg,
				"情景模式", "状态", "电灯节能模式", "开启中：", "15小时", "自动控温模式", "关闭：", "建议开启");
		StatuMsgItem item1 = new StatuMsgItem(R.drawable.icon_a_green_bg,
				"照明状态", "耗电", "5/12", "今日当前", "51度", "开启/总数", "累计", "1652度");
		StatuMsgItem item2 = new StatuMsgItem(R.drawable.icon_a_blue_bg,
				"室温控制", "温度值", "办公室东区", "当前温度：", "28℃", "办公室西区", "当前温度：", "26℃");
		StatuMsgItem item3 = new StatuMsgItem(R.drawable.icon_a_red_bg, "空气检测",
				"良好度", "PM2.5", "数值：", "300", "湿度", "数值：", "300");

		dataArrayList.add(item);
		dataArrayList.add(item1);
		dataArrayList.add(item2);
		dataArrayList.add(item3);
	}

	private void InitView() {
		// TODO Auto-generated method stub
		myListView = (ListView) findViewById(R.id.a_list);
		myAdapter = new A_ListAdapter(context, dataArrayList, myHandler);
		myListView.setAdapter(myAdapter);

	}

	private void splitMsg(String string) {
		try {
			String[] strs = string.split("A");
			GlobalVar.pm25 = strs[1];
			GlobalVar.temperature = strs[2];
			GlobalVar.humidity = strs[3];
			GlobalVar.air_quality = strs[4];
			GlobalVar.temperature2=strs[5];
			String temp = strs[0];
//			Toast.makeText(context, "10指令为："+temp.charAt(10), Toast.LENGTH_SHORT).show();
			if (temp.charAt(0) == '1') {
				GlobalVar.room_inside_status = true;
			} else {
				GlobalVar.room_inside_status = false;
			}
			if (temp.charAt(1) == '1') {
				GlobalVar.room_outside_status = true;
			} else {
				GlobalVar.room_outside_status = false;
			}
			if (temp.charAt(2) == '1') {
				GlobalVar.door1_status = true;
			} else {
				GlobalVar.door1_status = false;
			}
			if (temp.charAt(3) == '1') {
				GlobalVar.door2_status = true;
			} else {
				GlobalVar.door2_status = false;
			}
			if (temp.charAt(4) == '1') {
				GlobalVar.attendance1 = true;
			} else {
				GlobalVar.attendance1 = false;
			}
			if (temp.charAt(5) == '1') {
				GlobalVar.attendance2 = true;
			} else {
				GlobalVar.attendance2 = false;
			}
			if (temp.charAt(6) == '1') {
				GlobalVar.attendance3 = true;
			} else {
				GlobalVar.attendance3 = false;
			}
			if (temp.charAt(7) == '1') {
//				Toast.makeText(context, "灯1亮", Toast.LENGTH_SHORT).show();
				GlobalVar.light1 = true;
			} else {
				GlobalVar.light1 = false;
			}
			if (temp.charAt(8) == '1') {
//				Toast.makeText(context, "灯1亮", Toast.LENGTH_SHORT).show();
				GlobalVar.light2 = true;
			} else {
				GlobalVar.light2 = false;
			}
			if (temp.charAt(9) == '1') {
//				Toast.makeText(context, "灯1亮", Toast.LENGTH_SHORT).show();
				GlobalVar.light3 = true;
			} else {
				GlobalVar.light3 = false;
			}
			if (temp.charAt(10) == '1') {
//				Toast.makeText(context, "灯1亮", Toast.LENGTH_SHORT).show();
				GlobalVar.light4 = true;
			} else {
				GlobalVar.light4 = false;
			}

		} catch (StringIndexOutOfBoundsException ee) {
			Toast.makeText(context, "数组越界异常！--来自服务器数据", Toast.LENGTH_SHORT)
					.show();
			// TODO: handle exception
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(context, "异常！--来自服务器数据", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 每次心跳获得数据后刷新A_Activity的UI
	 */
	private void freshView() {
		dataArrayList.get(2).setBlock1_3(GlobalVar.temperature + "℃");// 温度
		dataArrayList.get(2).setBlock2_3(GlobalVar.temperature2 + "℃");// 温度
		dataArrayList.get(3).setBlock1_3(GlobalVar.pm25);// PM2.5
		dataArrayList.get(3).setBlock2_3(GlobalVar.humidity);// 湿度
		int i = 0;
		if (GlobalVar.light1)
			i++;
		if (GlobalVar.light2)
			i++;
		if (GlobalVar.light3)
			i++;
		if (GlobalVar.light4)
			i++;
		dataArrayList.get(1).setBlock1_1(i + "/4");// 照明
		// 启动刷新
		myAdapter.notifyDataSetChanged();
	}

}
