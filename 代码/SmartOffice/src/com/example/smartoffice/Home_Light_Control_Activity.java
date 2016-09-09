package com.example.smartoffice;

import java.security.spec.MGF1ParameterSpec;
import java.util.ArrayList;

import com.example.adapter.LightListAdapter;
import com.example.adapter.MyViewPageAdapter;
import com.example.bean.LightMsgItem;
import com.example.service.GlobalVar;
import com.example.service.HttpServer;
import com.example.tools.SysApplication;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Build;

public class Home_Light_Control_Activity extends Activity implements
		OnClickListener {

	// 标题控件
	private ImageView gobackImageView;
	// 功能控件
	private ImageView img_light1, img_light2, img_light3, img_light4;
	// listview
	private ListView myListView;
	private LightListAdapter myLightListAdapter;
	// 数据
	private ArrayList<LightMsgItem> dataArrayList = new ArrayList<>();

	private static final int WRITE_OK = 2;// 写指令失败(数据库写入失败)
	private static final int WRITE_FAILE = 3;// 写指令成功
	private static final int MSG_NULL = 4;// EditText输入为空
	private static final int NET_ERROR = 5;
	private static final int FLASH_VIEW=6;//子线程通知UI刷新界面
	private boolean isCheck=false;
	
	private Context context;
	private HttpServer httpServer = new HttpServer();
	private Handler myHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 100:
				ChangeLightFace((int) msg.obj);
				break;
			case 1:
				Toast.makeText(context, "请先开启可用网络！", Toast.LENGTH_SHORT).show();
				break;
			case WRITE_OK:
				Toast.makeText(context, "成功操作" + (int) msg.obj + "号灯",
						Toast.LENGTH_SHORT).show();
				break;
			case WRITE_FAILE:
				Toast.makeText(context, "NO！指令写入数据库失败，或服务器崩溃",
						Toast.LENGTH_LONG).show();
				break;
			case NET_ERROR:
				Toast.makeText(context, "网络故障，请检查网络", Toast.LENGTH_LONG).show();
				break;
			case FLASH_VIEW:
				InitLightFace();
				break;
			default:
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_light);
		SysApplication.getInstance().addActivity(this);

		context = getApplicationContext();
		InitMsg();
		InitView();// 初始化控件
		InitLightFace();// 初始化灯泡状态显示
		
		//启动刷新灯状态的线程
		isCheck=true;
		Start_LightStatusFlashThread();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		isCheck=false;
	}

	/**
	 * 刷新等状态的线程
	 */
	private void Start_LightStatusFlashThread() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(isCheck){
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					myHandler.sendEmptyMessage(FLASH_VIEW);
				}
			}
		}).start();
		
	}

	private void InitMsg() {
		// TODO Auto-generated method stub
		LightMsgItem item1 = new LightMsgItem(1, "美的节能吊灯W系列",
				"能耗0.1KW/H,已用电85KW", R.drawable.light1_off);
		LightMsgItem item2 = new LightMsgItem(2, "美的节能吊灯W系列",
				"能耗0.1KW/H,已用电65KW", R.drawable.light2_off);
		LightMsgItem item3 = new LightMsgItem(3, "凯瑞吊灯125型号",
				"能耗0.15KW/H,已用电44KW", R.drawable.light3_off);
		LightMsgItem item4 = new LightMsgItem(4, "凯瑞吊灯125型号",
				"能耗0.15KW/H,已用电102KW", R.drawable.light4_off);
		dataArrayList.add(item1);
		dataArrayList.add(item2);
		dataArrayList.add(item3);
		dataArrayList.add(item4);
	}

	private void InitView() {
		// TODO Auto-generated method stub
		// 标题初始化
		gobackImageView = (ImageView) findViewById(R.id.goback);
		gobackImageView.setVisibility(View.VISIBLE);
		gobackImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		// 地图按钮初始化
		img_light1 = (ImageView) findViewById(R.id.light_1);
		img_light2 = (ImageView) findViewById(R.id.light_2);
		img_light3 = (ImageView) findViewById(R.id.light_3);
		img_light4 = (ImageView) findViewById(R.id.light_4);
		img_light1.setOnClickListener(this);
		img_light2.setOnClickListener(this);
		img_light3.setOnClickListener(this);
		img_light4.setOnClickListener(this);

		myListView = (ListView) findViewById(R.id.light_list);
		myLightListAdapter = new LightListAdapter(context, dataArrayList,
				myHandler);
		myListView.setAdapter(myLightListAdapter);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.light_1:
			ChangeLightFace(1);
			break;
		case R.id.light_2:
			ChangeLightFace(2);
			break;
		case R.id.light_3:
			ChangeLightFace(3);
			break;
		case R.id.light_4:
			ChangeLightFace(4);
			break;

		default:
			break;
		}
		ChangeLightFace(v.getId());
	}

	private void ChangeLightFace(int id) {
		HttpServer httpServer = new HttpServer();
		// 网络可用才给予开关使用权

		if (httpServer.isNetworkAvailable(context)) {

			switch (id) {

			case 1:
				if (GlobalVar.light1) {
					img_light1.setImageResource(R.drawable.light1_off);
					GlobalVar.light1 = false;
					sendCode(1, "light1_off");
				} else {
					img_light1.setImageResource(R.drawable.light1_on);
					GlobalVar.light1 = true;
					sendCode(1, "light1_on");
				}
				break;
			case 2:
				if (GlobalVar.light2) {
					img_light2.setImageResource(R.drawable.light2_off);
					GlobalVar.light2 = false;
					sendCode(2, "light2_off");
				} else {
					img_light2.setImageResource(R.drawable.light2_on);
					GlobalVar.light2 = true;
					sendCode(2, "light2_on");
				}
				break;
			case 3:
				if (GlobalVar.light3) {
					img_light3.setImageResource(R.drawable.light3_off);
					GlobalVar.light3 = false;
					sendCode(3, "light3_off");
				} else {
					img_light3.setImageResource(R.drawable.light3_on);
					GlobalVar.light3 = true;
					sendCode(3, "light3_on");
				}
				break;
			case 4:
				if (GlobalVar.light4) {
					img_light4.setImageResource(R.drawable.light4_off);
					GlobalVar.light4 = false;
					sendCode(4, "light4_off");
				} else {
					img_light4.setImageResource(R.drawable.light4_on);
					GlobalVar.light4 = true;
					sendCode(4, "light4_on");
				}
				break;

			default:
				break;
			}
		} else {
			myHandler.sendEmptyMessage(1);
		}

	}

	private void InitLightFace() {
		// TODO Auto-generated method stub
		if (GlobalVar.light1) {
			img_light1.setImageResource(R.drawable.light1_on);
		} else {
			img_light1.setImageResource(R.drawable.light1_off);
		}
		if (GlobalVar.light2) {
			img_light2.setImageResource(R.drawable.light2_on);
		} else {
			img_light2.setImageResource(R.drawable.light2_off);
		}
		if (GlobalVar.light3) {
			img_light3.setImageResource(R.drawable.light3_on);
		} else {
			img_light3.setImageResource(R.drawable.light3_off);
		}
		if (GlobalVar.light4) {
			img_light4.setImageResource(R.drawable.light4_on);
		} else {
			img_light4.setImageResource(R.drawable.light4_off);
		}
	}

	private void sendCode(final int id, final String code) {
		Runnable runnable = new Runnable() {
			public void run() {

				// String urlString =
				// "http://wocaowocao.duapp.com/WriteAction?code="
				// + code;
				String urlString = "http://wocaowocao.duapp.com/LightWriteAction?id="
						+ id + "&code="+code;
				String jsonString = httpServer.getData(urlString);
				if (jsonString != null) {
					if (jsonString.equals("true")) {
						// myHandler.sendEmptyMessage(WRITE_OK);
						myHandler.sendMessage(myHandler.obtainMessage(WRITE_OK,
								id));
					} else if (jsonString.equals("false")) {
						myHandler.sendEmptyMessage(WRITE_FAILE);
					}
				} else {
					myHandler.sendEmptyMessage(NET_ERROR);
				}
			}
		};
		new Thread(runnable).start();
	}
}
