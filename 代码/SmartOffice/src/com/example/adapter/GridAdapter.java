package com.example.adapter;

import java.util.ArrayList;
import com.example.bean.GridItem;
import com.example.smartoffice.R;
import com.example.smartoffice.R.color;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {
	private LayoutInflater layoutInflater;
	private ArrayList<GridItem> dataArrayList;
	private Context context;
	private int[] background = { R.drawable.first_bg, R.drawable.second_bg,
			R.drawable.third, R.drawable.four, R.drawable.five, R.drawable.six,
			R.drawable.seven, R.drawable.eight, R.drawable.nine,
			R.drawable.seven, R.drawable.third, R.drawable.second_bg,
			R.drawable.four, R.drawable.six, R.drawable.first_bg,
			R.drawable.nine };

	public GridAdapter(Context context, ArrayList<GridItem> folders) {
		layoutInflater = LayoutInflater.from(context);
		this.context = context;
		this.dataArrayList = folders;
	}

	@Override
	public int getCount() {
		return dataArrayList.size();
	}

	@Override
	public GridItem getItem(int i) {
		return dataArrayList.get(i);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.grid_item1, null);
			holder = new ViewHolder();
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.grid_item_img1);
			holder.textView = (TextView) convertView
					.findViewById(R.id.grid_item_name1);
			holder.bgTextView = (TextView) convertView
					.findViewById(R.id.grid_item_bg1);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		GridItem gridItem = getItem(position);
		Drawable drawable = context.getResources().getDrawable(
				background[position]);
		holder.bgTextView.setBackgroundDrawable(drawable);
		drawable = context.getResources().getDrawable(
				gridItem.getGridImgDrawable());
		holder.imageView.setImageDrawable(drawable);
		Log.i("name", gridItem.getGridName());
		holder.textView.setText(gridItem.getGridName());
		holder.textView.setTextColor(color.main_gray);
		return convertView;
	}

	private class ViewHolder {
		public TextView bgTextView;
		public ImageView imageView;
		public TextView textView;
	}
}
