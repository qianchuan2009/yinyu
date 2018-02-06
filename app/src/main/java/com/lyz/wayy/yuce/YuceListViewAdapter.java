package com.lyz.wayy.yuce;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyz.wayy.R;
import com.lyz.wayy.msg.News;

import java.util.ArrayList;
import java.util.List;

public class YuceListViewAdapter extends ArrayAdapter {

	private int resourceId;//每个门户的视图资源id
	private List<News> arrList = new ArrayList<News>();
	private Context ctx;
//	private String dh;

//	private GridView gridView;

	public YuceListViewAdapter(Context context, int resource, List<News> objects) {
		super(context, resource, objects);
		resourceId=resource;
		arrList=objects;
		ctx=context;
//		dh=type;
	}
	
    public void setArrList(ArrayList<News> aList) {
    	arrList=aList;
    }

	@Override
	public int getCount() {
		if (arrList != null) {
			return arrList.size();
		}
		return 0;
	}

	
	/**
	 * 获取每一个门户项的视图
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		News news = (News) getItem(position);
		News news = (News) arrList.get(position);
		String title="",desc="",time="";
		title=news.getNewsTitle();
		desc=news.getDesc();
		time=news.getNewsTime();
				
		View view;
		ViewHolder viewHolder;//存储列表视图中的组件，避免重复查找
		if (convertView == null) {
			view =  LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.title = (TextView) view.findViewById(R.id.bbs_title);
			viewHolder.desc = (TextView) view.findViewById(R.id.bbs_desc);
			viewHolder.time = (TextView) view.findViewById(R.id.bbs_time);
			view.setTag(viewHolder); // 将ViewHolder存储在View中
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
		}
		viewHolder.title.setText(title);
		viewHolder.desc.setText(desc);
		viewHolder.time.setText(time);
		return view;
	}


	class ViewHolder {
		ImageView img;
		TextView title;//顺序
		TextView desc;//顺序
		TextView time;//顺序
	}
}
