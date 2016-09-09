package com.example.smartoffice;

import com.example.service.HttpServer;
import com.example.tools.SysApplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DirectCode_Activity extends Activity {

	private EditText myEditText;
	private Button myButton;
	private HttpServer httpServer;
	private static final int NET_ERROR = 1;// 网络问题
	private static final int WRITE_OK = 2;// 写指令失败(数据库写入失败)
	private static final int WRITE_FAILE = 3;// 写指令成功
	private static final int MSG_NULL=4;//EditText输入为空

	private Handler myHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case NET_ERROR:
				Toast.makeText(DirectCode_Activity.this, "网络故障，请检查网络", Toast.LENGTH_LONG)
				.show();
				break;
			case WRITE_OK:
				Toast.makeText(DirectCode_Activity.this, "OK！指令成功写入数据库：", Toast.LENGTH_LONG)
				.show();
				break;
			case WRITE_FAILE:
				Toast.makeText(DirectCode_Activity.this, "NO！指令写入数据库失败，或服务器崩溃", Toast.LENGTH_LONG)
				.show();
				break;
			case MSG_NULL:
				Toast.makeText(DirectCode_Activity.this, "请先输入指令", Toast.LENGTH_LONG)
				.show();
				break;
			default:
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_direct);
		SysApplication.getInstance().addActivity(this);

		httpServer = new HttpServer();
		if (!httpServer.isNetworkAvailable(getApplicationContext())) {
			Toast.makeText(DirectCode_Activity.this, "请先打开设备网络", Toast.LENGTH_LONG)
					.show();
		}
		InitView();
		myButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String codeString=myEditText.getText().toString();
				if(codeString==null||codeString.equals("")){
					myHandler.sendEmptyMessage(MSG_NULL);
				}else{
					sendCode(codeString);
					myEditText.setText("");
				}
			}
		});
	}

	private void InitView() {
		// TODO Auto-generated method stub
		myEditText = (EditText) findViewById(R.id.input_et);
		myButton = (Button) findViewById(R.id.send_bt);
	}

	private void sendCode(final String code) {
		Runnable runnable = new Runnable() {
			public void run() {

				String urlString = "http://wocaowocao.duapp.com/WriteAction?code="
						+ code;
				String jsonString = httpServer.getData(urlString);
				if(jsonString!=null){
					if(jsonString.equals("true")){
						myHandler.sendEmptyMessage(WRITE_OK);
					}else if(jsonString.equals("false")){
						myHandler.sendEmptyMessage(WRITE_FAILE);
					}
				}else{
					myHandler.sendEmptyMessage(NET_ERROR);
				}
			}
		};
		new Thread(runnable).start();
	}

}
