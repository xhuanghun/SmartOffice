package com.example.smartoffice;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.MyViewPageAdapter;
import com.example.service.GlobalVar;
import com.example.tools.SysApplication;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends Activity {
	
	//控件
	private TextView tv1, tv2, tv3, tv4;
	private ImageView img1,img2,img3,img4;
	private ViewPager vp;
	private LinearLayout LL1,LL2,LL3,LL4;
	//工具
	private LocalActivityManager manager;
	private MyViewPageAdapter viewPageAdapter;
	private OnClickListener clickListener;
	private OnPageChangeListener pageChangeListener;
	//量
	private int textviewW = 0;
	private int currentIndex = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SysApplication.getInstance().addActivity(this);
		
		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);

		vp = (ViewPager) findViewById(R.id.QD_news_content);
		InitView();
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		GlobalVar.isLife=false;
	}


	private void InitView() {
		// TODO Auto-generated method stub
		tv1 = (TextView) findViewById(R.id.quality_tv1);
		tv2 = (TextView) findViewById(R.id.quality_tv2);
		tv3 = (TextView) findViewById(R.id.quality_tv3);
		tv4 = (TextView) findViewById(R.id.quality_tv4);
		img1=(ImageView)findViewById(R.id.main_img1);
		img2=(ImageView)findViewById(R.id.main_img2);
		img3=(ImageView)findViewById(R.id.main_img3);
		img4=(ImageView)findViewById(R.id.main_img4);
		LL1=(LinearLayout)findViewById(R.id.LL1);
		LL2=(LinearLayout)findViewById(R.id.LL2);
		LL3=(LinearLayout)findViewById(R.id.LL3);
		LL4=(LinearLayout)findViewById(R.id.LL4);
		clickListener = new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub

				switch (v.getId())
				{
				case R.id.quality_tv1:
					vp.setCurrentItem(0);
					break;
				case R.id.quality_tv2:
					vp.setCurrentItem(1);
					break;
				case R.id.quality_tv3:
					vp.setCurrentItem(2);
					break;
				case R.id.quality_tv4:
					vp.setCurrentItem(3);
					break;
				case R.id.main_img1:
					vp.setCurrentItem(0);
					break;
				case R.id.main_img2:
					vp.setCurrentItem(1);
					break;
				case R.id.main_img3:
					vp.setCurrentItem(2);
					break;
				case R.id.main_img4:
					vp.setCurrentItem(3);
					break;
				}
			}
		};

		tv1.setOnClickListener(clickListener);
		tv2.setOnClickListener(clickListener);
		tv3.setOnClickListener(clickListener);
		tv4.setOnClickListener(clickListener);

		img1.setOnClickListener(clickListener);
		img2.setOnClickListener(clickListener);
		img3.setOnClickListener(clickListener);
		img4.setOnClickListener(clickListener);
		InitPager();
		
	}

	private void InitPager() {
		// TODO Auto-generated method stub
		pageChangeListener = new OnPageChangeListener()
		{

			@Override
			public void onPageSelected(int arg0)
			{
				// TODO Auto-generated method stub
				if (textviewW == 0)
				{
					textviewW = tv1.getWidth();
				}
				Animation animation = new TranslateAnimation(textviewW
						* currentIndex, textviewW * arg0, 0, 0);
				currentIndex = arg0;
				animation.setFillAfter(true);
				animation.setDuration(300);
				setFaceMoved(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0)
			{
				// TODO Auto-generated method stub

			}
		};

		AddActivitiesToViewPager();
		vp.setCurrentItem(0);
		vp.setOnPageChangeListener(pageChangeListener);
	}

	private void AddActivitiesToViewPager()
	{
		List<View> mViews = new ArrayList<View>();
		Intent intent = new Intent();

		
		intent.setClass(this, A_Activity.class);
		intent.putExtra("id", 1);
		mViews.add(getView("QualityActivity1", intent));

		intent.setClass(this, B_Activity.class);
		intent.putExtra("id", 2);
		mViews.add(getView("QualityActivity2", intent));

		intent.setClass(this, C_Activity.class);
		intent.putExtra("id", 3);
		mViews.add(getView("QualityActivity3", intent));

		intent.setClass(this, DirectCode_Activity.class);
		intent.putExtra("id", 4);
		mViews.add(getView("QualityActivity4", intent));

		viewPageAdapter = new MyViewPageAdapter(mViews);
		vp.setAdapter(viewPageAdapter);

	}
	private View getView(String id, Intent intent)
	{
		return manager.startActivity(id, intent).getDecorView();

	}
	private void setFaceMoved(int arg0)
	{
		switch (arg0)
		{
		case 0:
			tv1.setTextColor(getResources().getColor(R.color.blue));
			tv2.setTextColor(getResources().getColor(R.color.white));
			tv3.setTextColor(getResources().getColor(R.color.white));
			tv4.setTextColor(getResources().getColor(R.color.white));
			img1.setImageResource(R.drawable.main_icon1_1);
			img2.setImageResource(R.drawable.main_icon2_2);
			img3.setImageResource(R.drawable.main_icon3_2);
			img4.setImageResource(R.drawable.main_icon4_2);
			LL1.setBackgroundResource(R.drawable.touch_bg);
			LL2.setBackgroundResource(R.drawable.real_empty);
			LL3.setBackgroundResource(R.drawable.real_empty);
			LL4.setBackgroundResource(R.drawable.real_empty);
			break;
		case 1:
			tv1.setTextColor(getResources().getColor(R.color.white));
			tv2.setTextColor(getResources().getColor(R.color.blue));
			tv3.setTextColor(getResources().getColor(R.color.white));
			tv4.setTextColor(getResources().getColor(R.color.white));
			img1.setImageResource(R.drawable.main_icon1_2);
			img2.setImageResource(R.drawable.main_icon2_1);
			img3.setImageResource(R.drawable.main_icon3_2);
			img4.setImageResource(R.drawable.main_icon4_2);
			LL1.setBackgroundResource(R.drawable.real_empty);
			LL2.setBackgroundResource(R.drawable.touch_bg);
			LL3.setBackgroundResource(R.drawable.real_empty);
			LL4.setBackgroundResource(R.drawable.real_empty);
			break;
		case 2:
			tv1.setTextColor(getResources().getColor(R.color.white));
			tv2.setTextColor(getResources().getColor(R.color.white));
			tv3.setTextColor(getResources().getColor(R.color.blue));
			tv4.setTextColor(getResources().getColor(R.color.white));
			img1.setImageResource(R.drawable.main_icon1_2);
			img2.setImageResource(R.drawable.main_icon2_2);
			img3.setImageResource(R.drawable.main_icon3_1);
			img4.setImageResource(R.drawable.main_icon4_2);
			LL1.setBackgroundResource(R.drawable.real_empty);
			LL2.setBackgroundResource(R.drawable.real_empty);
			LL3.setBackgroundResource(R.drawable.touch_bg);
			LL4.setBackgroundResource(R.drawable.real_empty);
			break;
		case 3:
			tv1.setTextColor(getResources().getColor(R.color.white));
			tv2.setTextColor(getResources().getColor(R.color.white));
			tv3.setTextColor(getResources().getColor(R.color.white));
			tv4.setTextColor(getResources().getColor(R.color.blue));
			img1.setImageResource(R.drawable.main_icon1_2);
			img2.setImageResource(R.drawable.main_icon2_2);
			img3.setImageResource(R.drawable.main_icon3_2);
			img4.setImageResource(R.drawable.main_icon4_1);
			LL1.setBackgroundResource(R.drawable.real_empty);
			LL2.setBackgroundResource(R.drawable.real_empty);
			LL3.setBackgroundResource(R.drawable.real_empty);
			LL4.setBackgroundResource(R.drawable.touch_bg);
			break;
		}
	}
}
