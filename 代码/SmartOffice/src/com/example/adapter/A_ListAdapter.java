package com.example.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.example.bean.LightMsgItem;
import com.example.bean.StatuMsgItem;
import com.example.smartoffice.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class A_ListAdapter extends BaseAdapter {

	private ArrayList<StatuMsgItem> datalist;
	private Context context;
	private Handler myHandler;

	public A_ListAdapter() {

	}

	public A_ListAdapter(Context context, ArrayList<StatuMsgItem> datalist,
			Handler handler) {
		this.datalist = datalist;
		this.context = context;
		this.myHandler = handler;
	}

	public int getCount() {
		return datalist.size();
	}

	public Object getItem(int position) {
		return datalist.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		RelativeLayout skinRelativeLayout;// 用于切换皮肤
		TextView titleNameTextView;// 栏目名
		TextView titleItemNameTextView;// 属性名
		TextView block1_1;
		TextView block1_2;
		TextView block1_3;
		TextView block2_1;
		TextView block2_2;
		TextView block2_3;

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_generalsituation, null);

			skinRelativeLayout = (RelativeLayout) convertView
					.findViewById(R.id.title_color);
			titleNameTextView = (TextView) convertView
					.findViewById(R.id.title_name);
			titleItemNameTextView = (TextView) convertView
					.findViewById(R.id.title_item_name);
			block1_1 = (TextView) convertView.findViewById(R.id.a_block1_1);
			block1_2 = (TextView) convertView.findViewById(R.id.a_block1_2);
			block1_3 = (TextView) convertView.findViewById(R.id.a_block1_3);
			block2_1 = (TextView) convertView.findViewById(R.id.a_block2_1);
			block2_2 = (TextView) convertView.findViewById(R.id.a_block2_2);
			block2_3 = (TextView) convertView.findViewById(R.id.a_block2_3);

			ViewHolder holder = new ViewHolder(skinRelativeLayout,
					titleNameTextView, titleItemNameTextView, block1_1,
					block1_2, block1_3, block2_1, block2_2, block2_3);
			convertView.setTag(holder);
		} else {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			
			skinRelativeLayout=holder.skinRelativeLayout;
			titleNameTextView=holder.titleNameTextView;
			titleItemNameTextView=holder.titleItemNameTextView;
			block1_1=holder.block1_1;
			block1_2=holder.block1_2;
			block1_3=holder.block1_3;
			block2_1=holder.block2_1;
			block2_2=holder.block2_2;
			block2_3=holder.block2_3;
		}
		/*
		 * 获取Arraylist中的数据填充Item
		 * */
		StatuMsgItem item =(StatuMsgItem)getItem(position);
//		skinRelativeLayout.setBackgroundResource(item.getSkinIconId());
//		titleNameTextView.setText("AAA");
//		titleItemNameTextView.setText("AAA");
//		block1_1.setText("AAA");
//		block1_2.setText("AAA");
//		block1_3.setText("AAA");
//		block2_1.setText("AAA");
//		block2_2.setText("AAA");
//		block2_3.setText("AAA");
		skinRelativeLayout.setBackgroundResource(item.getSkinIconId());
		titleNameTextView.setText(item.getTitleName());
		titleItemNameTextView.setText(item.getTitleItemName());
		block1_1.setText(item.getBlock1_1());
		block1_2.setText(item.getBlock1_2());
		block1_3.setText(item.getBlock1_3());
		block2_1.setText(item.getBlock2_1());
		block2_2.setText(item.getBlock2_2());
		block2_3.setText(item.getBlock2_3());

//		switchImageView.setOnClickListener(new LightClickListener(newsItem
//				.getId()));
		// convertView.setOnClickListener(new NewsClickListener(newsItem
		// .getNewsId()));

		return convertView;
	}

	private class ViewHolder {
		public RelativeLayout skinRelativeLayout;// 用于切换皮肤
		public TextView titleNameTextView;// 栏目名
		public TextView titleItemNameTextView;// 属性名
		public TextView block1_1;
		public TextView block1_2;
		public TextView block1_3;
		public TextView block2_1;
		public TextView block2_2;
		public TextView block2_3;

		public ViewHolder(RelativeLayout skinRelativeLayout,
				TextView titleNameTextView, TextView titleItemNameTextView,
				TextView block1_1, TextView block1_2, TextView block1_3,
				TextView block2_1, TextView block2_2, TextView block2_3) {
			super();
			this.skinRelativeLayout = skinRelativeLayout;
			this.titleNameTextView = titleNameTextView;
			this.titleItemNameTextView = titleItemNameTextView;
			this.block1_1 = block1_1;
			this.block1_2 = block1_2;
			this.block1_3 = block1_3;
			this.block2_1 = block2_1;
			this.block2_2 = block2_2;
			this.block2_3 = block2_3;
		}

	}

	private class LightClickListener implements OnClickListener {

		private int id;

		public LightClickListener(int id) {
			this.id = id;
		}

		public void onClick(View v) {
			myHandler.sendMessage(myHandler.obtainMessage(100, id));
		}

	}

}
