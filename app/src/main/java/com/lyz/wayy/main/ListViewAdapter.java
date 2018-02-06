package com.lyz.wayy.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyz.wayy.R;
import com.lyz.wayy.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends ArrayAdapter {
	
	private int resourceId;//每个门户的视图资源id
	private List<String> arrList = new ArrayList<String>();
	private Context ctx;
	private String dh;

//	private GridView gridView;
	
	public ListViewAdapter(Context context, int resource, List<String> objects, String type) {
		super(context, resource, objects);
		resourceId=resource;
		arrList=objects;
		ctx=context;
		dh=type;
	}
	
    public void setArrList(ArrayList<String> aList) {
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
		String str = (String) getItem(position);
		JSONObject item=null;

		String expect="",opencode="",opentime="";
		try{
			item=new JSONObject(str);
			expect=item.getString("expect");
			opencode=item.getString("opencode");
			opentime=item.getString("opentime");
		}catch (Exception e){
			e.printStackTrace();

		}
				
		View view;
		ViewHolder viewHolder;//存储列表视图中的组件，避免重复查找
		if (convertView == null) {
			view =  LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.order = (TextView) view.findViewById(R.id.order);
			if("qlc".equalsIgnoreCase(dh)){
				LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(Utils.dp2px(100,ctx),Utils.dp2px(35,ctx));
				viewHolder.order.setLayoutParams(params);
			}


			viewHolder.a1= (Button) view.findViewById(R.id.a001);
			viewHolder.a2= (Button) view.findViewById(R.id.a002);
			viewHolder.a3= (Button) view.findViewById(R.id.a003);
			viewHolder.a4= (Button) view.findViewById(R.id.a004);
			viewHolder.a5= (Button) view.findViewById(R.id.a005);
			viewHolder.a6= (Button) view.findViewById(R.id.a006);
			viewHolder.a7= (Button) view.findViewById(R.id.a007);
			viewHolder.a9= (Button) view.findViewById(R.id.a009);
			viewHolder.a10= (Button) view.findViewById(R.id.a010);
//			viewHolder.name = (TextView) view.findViewById(R.id.name);
//			viewHolder.count = (TextView) view.findViewById(R.id.count);
			view.setTag(viewHolder); // 将ViewHolder存储在View中
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
		}
		viewHolder.order.setText("第"+expect+"期");
		String[] arr=opencode.split(",");
		viewHolder.a1.setText(arr[0]);
		viewHolder.a2.setText(arr[1]);
		viewHolder.a3.setText(arr[2]);
		if(dh.equalsIgnoreCase("ahk3")||dh.equalsIgnoreCase("bjk3")||dh.equalsIgnoreCase("tjssc")||dh.equalsIgnoreCase("pl3")||dh.equalsIgnoreCase("fc3d")||dh.equalsIgnoreCase("pl5")||dh.equalsIgnoreCase("ah11x5")){
			if(dh.equalsIgnoreCase("ahk3")||dh.equalsIgnoreCase("bjk3")||dh.equalsIgnoreCase("pl3")||dh.equalsIgnoreCase("fc3d")) {
				viewHolder.a4.setVisibility(View.GONE);
				viewHolder.a5.setVisibility(View.GONE);
			}else{
				viewHolder.a4.setText(arr[3]);
				viewHolder.a5.setText(arr[4]);
			}
			viewHolder.a6.setVisibility(View.GONE);
			viewHolder.a7.setVisibility(View.GONE);
			viewHolder.a9.setVisibility(View.GONE);
			viewHolder.a10.setVisibility(View.GONE);
		}else if(dh.equalsIgnoreCase("qlc")||(dh.equalsIgnoreCase("qxc"))){
			viewHolder.a4.setText(arr[3]);
			viewHolder.a5.setText(arr[4]);
			viewHolder.a6.setText(arr[5]);
			viewHolder.a10.setVisibility(View.GONE);
			if(dh.equalsIgnoreCase("qlc")){
				viewHolder.a7.setText(arr[6].substring(0,2));
				viewHolder.a9.setText(arr[6].substring(3));
			}else{
				viewHolder.a7.setText(arr[6]);
				viewHolder.a9.setVisibility(View.GONE);
			}
		}else if(dh.equalsIgnoreCase("ssq")){
			viewHolder.a4.setText(arr[3]);
			viewHolder.a5.setText(arr[4]);
			viewHolder.a6.setText(arr[5].substring(0,2));
			viewHolder.a9.setText(arr[5].substring(3));
			viewHolder.a7.setVisibility(View.GONE);
			viewHolder.a10.setVisibility(View.GONE);
		}else  if(dh.equalsIgnoreCase("dlt")){
			viewHolder.a4.setText(arr[3]);
			viewHolder.a5.setText(arr[4].substring(0,2));

			viewHolder.a6.setVisibility(View.GONE);
			viewHolder.a7.setVisibility(View.GONE);
			viewHolder.a9.setText(arr[4].substring(3));
			viewHolder.a10.setText(arr[5]);
		}
//		viewHolder.name.setText(opencode+"");
//		viewHolder.count.setText(opentime+"");
		return view;
	}


	class ViewHolder {
		TextView order;//顺序
		Button a1;//词
		Button a2;//词
		Button a3;//词
		Button a4;//词
		Button a5;//词
		Button a6;//词
		Button a7;//词
//		Button a8;//词
		Button a9;//词
		Button a10;//词
	}
}
