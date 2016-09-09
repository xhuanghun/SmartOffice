package com.example.smartoffice;

import java.util.ArrayList;

import com.example.adapter.GridAdapter;
import com.example.bean.GridItem;
import com.example.tools.SysApplication;
import com.example.view.AutoScrollTextView;
import com.example.view.MyGridView;

import android.R.integer;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.os.Build;

/*
 * 这个版面制作效果为：
 * 1、九宫格
 * 2、可以拓展，给人丰富的感觉
 * */

public class C_Activity extends Activity {

	// 组件
	private Context context;
	private MyGridView gridView;
	private GridAdapter gridAdapter;
	private AutoScrollTextView myAutoScrollTextView;

	// 数据
	private ArrayList<GridItem> gridData = null;
	private String[] nameList = { "考勤", "日程", "备忘录", "语音笔", "评优", "应酬管理",
			"加密短信", "工资", "更多" };
	private int[] iconList = { R.drawable.meeting_ico5,
			R.drawable.meeting_ico1, R.drawable.meeting_ico11,
			R.drawable.meeting_ico15, R.drawable.meeting_ico14,
			R.drawable.meeting_ico6, R.drawable.meeting_ico8,
			R.drawable.meeting_ico10, R.drawable.meeting_ico9 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_c);
		SysApplication.getInstance().addActivity(this);
		context = getApplicationContext();

		InitView();
		myAutoScrollTextView.starScroll();// 通告栏开始滚动

	}

	private void InitView() {
		// TODO Auto-generated method stub

		// 电子公告栏
		myAutoScrollTextView = (AutoScrollTextView) findViewById(R.id.c_auto_textview);
		myAutoScrollTextView.initScrollTextView(this.getWindowManager(),
				"今日值班：刘小备    晨会情况：全部道勤    软件组通告：下午3点部门例会，请准时与会");
		myAutoScrollTextView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				// if(event.getAction()==MotionEvent.ACTION_DOWN){
				// myAutoScrollTextView.stopScroll();
				// }else if(event.getAction()==MotionEvent.ACTION_UP){
				// myAutoScrollTextView.starScroll();
				// }

				return false;
			}
		});
		// 九宫格
		gridView = (MyGridView) findViewById(R.id.mygridview);
		getGridData();
		gridAdapter = new GridAdapter(context, gridData);
		gridView.setAdapter(gridAdapter);

		/*
		 * 九宫格点击事件
		 * */
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				// GridItem item=gridData.get(arg2);
				Intent intent = new Intent();
				switch (arg2) {
				case 0:

					break;
				case 1:

					break;
				case 2:

					break;
				case 3:

					break;
				case 4:

					break;
				case 5:

					break;
				case 6:

					break;
				case 7:

					break;
				case 8:

					break;

				default:
					break;
				}
			}
		});
		/*
		 * 九宫格长按监听
		 */
		gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				return false;
			}
		});

	}

	private void getGridData() {
		// TODO Auto-generated method stub
		gridData = new ArrayList<GridItem>();

		for (int i = 0; i < nameList.length; i++) {

			GridItem gridItem = new GridItem();
			gridItem.setGridName(nameList[i]);
			gridItem.setGridImgDrawable(iconList[i]);
			gridData.add(gridItem);
		}
	}

	public static boolean isPackageNameExisit(Context context, String pkgName) {
		if (TextUtils.isEmpty(pkgName)) {
			return false;
		}
		try {
			context.getPackageManager().getApplicationInfo(pkgName, 0);
			return true;
		} catch (Exception e) {

		}
		return false;
	}
}
