package com.example.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.example.bean.LightMsgItem;
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
import android.widget.TextView;

public class LightListAdapter extends BaseAdapter {

	private ArrayList<LightMsgItem> newsList;
	private Context context;
	private Handler myHandler;

	public LightListAdapter() {
	}

	public LightListAdapter(Context context, ArrayList<LightMsgItem> newsList,Handler handler) {
		this.newsList = newsList;
		this.context = context;
		this.myHandler=handler;
	}

	public int getCount() {
		return newsList.size();
	}

	public Object getItem(int position) {
		return newsList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView img;// 灯标号
		TextView nameTextView;// 灯名
		TextView timeTextView;// 使用时间
		ImageView switchImageView;// 开关

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_light, null);

			img = (ImageView) convertView.findViewById(R.id.item_light_img);
			nameTextView = (TextView) convertView
					.findViewById(R.id.item_light_name);
			timeTextView = (TextView) convertView
					.findViewById(R.id.item_light_time);
			switchImageView = (ImageView) convertView
					.findViewById(R.id.item_light_switch);
			ViewHolder holder = new ViewHolder(img, nameTextView, timeTextView,
					switchImageView);
			convertView.setTag(holder);
		} else {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			img = holder.img;
			nameTextView = holder.nameTextView;
			timeTextView = holder.timeTextView;
			switchImageView = holder.switchImageView;
		}

		LightMsgItem newsItem = (LightMsgItem) getItem(position);
		img.setImageResource(newsItem.getImgid());
		nameTextView.setText(newsItem.getName());
		timeTextView.setText(newsItem.getTime());
		switchImageView.setOnClickListener(new LightClickListener(newsItem
				.getId()));
		// convertView.setOnClickListener(new NewsClickListener(newsItem
		// .getNewsId()));

		return convertView;
	}

	private class ViewHolder {
		public ImageView img;
		public TextView nameTextView;
		public TextView timeTextView;
		public ImageView switchImageView;

		public ViewHolder(ImageView img, TextView newsTitle, TextView newsTime,
				ImageView newsImage) {
			this.img = img;
			this.nameTextView = newsTitle;
			this.timeTextView = newsTime;
			this.switchImageView = newsImage;
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
