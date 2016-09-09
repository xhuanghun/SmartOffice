package com.example.smartoffice;

import com.example.tools.SysApplication;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import android.os.Build;

public class B_Activity extends Activity implements OnClickListener {

	// ImageView即为图标，分别对应
	// “照明”、“空调”、“门窗”、“公告板”、“情景模式”、“语音控制”
	private ImageView img1, img2, img3, img4, img5, img6;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_b);
		SysApplication.getInstance().addActivity(this);

		InitView();
	}

	private void InitView() {
		// TODO Auto-generated method stub
		img1 = (ImageView) findViewById(R.id.img_1);
		img2 = (ImageView) findViewById(R.id.img_2);
		img3 = (ImageView) findViewById(R.id.img_3);
		img4 = (ImageView) findViewById(R.id.img_4);
		img5 = (ImageView) findViewById(R.id.img_5);
		img6 = (ImageView) findViewById(R.id.img_6);

		img1.setOnClickListener(this);
		img2.setOnClickListener(this);
		img3.setOnClickListener(this);
		img4.setOnClickListener(this);
		img5.setOnClickListener(this);
		img6.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent();
		switch (v.getId()) {
		case R.id.img_1:
			intent.setClass(B_Activity.this, Home_Light_Control_Activity.class);
			startActivity(intent);
			break;
		case R.id.img_2:
			showToast("开发中");
			break;
		case R.id.img_3:
			showToast("开发中");
			break;
		case R.id.img_4:
			showToast("开发中");
			break;
		case R.id.img_5:
//			showToast("开发中");
			intent.setClass(B_Activity.this, Home_Model_Control_Activity.class);
			startActivity(intent);
			break;
		case R.id.img_6:
			showToast("开发中");
			break;

		default:
			break;
		}
	}
	
	private void showToast(String msg){
		Toast.makeText(B_Activity.this, msg, Toast.LENGTH_LONG).show();
	}

}
