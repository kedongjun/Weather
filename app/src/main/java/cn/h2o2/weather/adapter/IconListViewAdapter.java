package cn.h2o2.weather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.h2o2.weather.R;

/**
 * author      yichen
 * Email       kedongjun@mmclick.com
 * Date        2016/3/31 14:25
 * description: TODO
 */ //listview适配器
public class IconListViewAdapter extends BaseAdapter {

    private Context context;
    private HashMap<String, String> data;
    private ArrayList<HashMap<String, String>> arrayLists;

    public IconListViewAdapter(Context context, HashMap<String, String> data) {
        this.context = context;
        this.data = data;
        this.arrayLists = new ArrayList<>();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            HashMap<String, String> map = new HashMap<>();
            map.put(entry.getKey(), entry.getValue());
            arrayLists.add(map);
        }
    }

    @Override
    public int getCount() {
        return arrayLists.size();
    }

    @Override
    public Object getItem(int arg0) {
        return arrayLists.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holderView;
        if (convertView == null) {
            holderView = new ViewHolder();
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.alc_item_weather_top, null);
            holderView.view = convertView;
            convertView.setTag(holderView);
        } else {
            holderView = (ViewHolder) convertView.getTag();
        }
        for (Map.Entry<String, String> entry : arrayLists.get(position).entrySet()) {

            TextView keyText = (TextView) holderView.view.findViewById(R.id.keyText);
            keyText.setText(entry.getKey());
            TextView valueText = (TextView) holderView.view.findViewById(R.id.valueText);
            valueText.setText(entry.getValue());
        }
        return convertView;
    }

    class ViewHolder {
        View view;
    }
}
