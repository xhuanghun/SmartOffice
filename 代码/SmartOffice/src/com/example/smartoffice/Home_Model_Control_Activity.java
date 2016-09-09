package com.example.smartoffice;

import com.example.service.HttpServer;
import com.example.tools.SysApplication;

import android.R.integer;
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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class Home_Model_Control_Activity extends Activity implements
		OnClickListener {

	// 标题控件
	private ImageView gobackImageView;
	// 功能控件
	private Button bt1, bt2, bt3;

	private HttpServer httpServer = new HttpServer();
	private static final int WRITE_OK = 2;// 写指令失败(数据库写入失败)
	private static final int WRITE_FAILE = 3;// 写指令成功
	private static final int NET_ERROR = 5;

	private Context context;

	private Handler myHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {

			case WRITE_OK:
				
				int id = (int) msg.what;
				switch (id) {
				case 1:
					Toast.makeText(context, "情景模式-一键全关闭", Toast.LENGTH_SHORT).show();
					break;
				case 2:
					Toast.makeText(context, "情景模式-自动模式", Toast.LENGTH_SHORT).show();
					break;
				case 3:
					Toast.makeText(context, "情景模式-正常模式", Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
				}
				break;

			case WRITE_FAILE:
				Toast.makeText(context, "NO！指令写入数据库失败，或服务器崩溃",
						Toast.LENGTH_LONG).show();
				break;

			case NET_ERROR:
				Toast.makeText(context, "网络故障，请检查网络", Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_model);
		SysApplication.getInstance().addActivity(this);
		context = getApplicationContext();

		InitView();
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
		// 初始化功能控件
		bt1 = (Button) findViewById(R.id.b_model_1_button);
		bt2 = (Button) findViewById(R.id.b_model_2_button);
		bt3 = (Button) findViewById(R.id.b_model_3_button);

		bt1.setOnClickListener(this);
		bt2.setOnClickListener(this);
		bt3.setOnClickListener(this);

	}

	private void sendCode(final int id, final String code) {
		Runnable runnable = new Runnable() {
			public void run() {

				// String urlString =
				// "http://wocaowocao.duapp.com/WriteAction?code="
				// + code;
				String urlString = "http://wocaowocao.duapp.com/LightWriteAction?id="
						+ id + "&code=" + code;
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.b_model_1_button:
			sendCode(1, "all_close");
			break;
		case R.id.b_model_2_button:
			sendCode(2, "autohelp_on");
			break;
		case R.id.b_model_3_button:
			sendCode(3, "autohelp_off");
			break;

		default:
			break;
		}
	}

}
