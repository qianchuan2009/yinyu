
package com.lyz.wayy.setting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;


public class ListViewAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;

    private List<Object> list;

    private ListItemView listener;

    private Context context;

    /**
     * 对外接口，设置格式
     *
     * @author 作者：panh
     * @version 创建时间：2015-6-25 下午3:46:38
     */
    public interface ListItemView {
        public View getView(int position, View convertView, ViewGroup parent, List<Object> list,
                            LayoutInflater layoutInflater);

    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    public void setListItemView(ListItemView listener) {
        this.listener = listener;
    }

    public ListViewAdapter(Context context, List<Object> list) {
        this.list = list;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return listener.getView(position, convertView, parent, list, layoutInflater);
    }

}
