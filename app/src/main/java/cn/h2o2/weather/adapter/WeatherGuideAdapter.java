package cn.h2o2.weather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cn.h2o2.weather.R;
import cn.h2o2.weather.model.Weather;

/**
 * author      yichen
 * Email       kedongjun@mmclick.com
 * Date        2016/3/31 14:25
 * description: TODO
 */ //listview适配器
public class WeatherGuideAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Weather.Guide> guides;

    public WeatherGuideAdapter(Context context, ArrayList<Weather.Guide> guides) {
        this.context = context;
        this.guides = guides;
    }

    @Override
    public int getCount() {
        return guides.size();
    }

    @Override
    public Object getItem(int arg0) {
        return guides.get(arg0);
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
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.alc_item_weather_guide, null);
            holderView.view = convertView;
            convertView.setTag(holderView);
        } else {
            holderView = (ViewHolder) convertView.getTag();
        }
        Weather.Guide guide = guides.get(position);
        TextView titleText = (TextView) holderView.view
                .findViewById(R.id.titleText);
        titleText.setText(guide.getTitle());
        TextView contentText = (TextView) holderView.view
                .findViewById(R.id.contentText);
        contentText.setText(guide.getContent());
        return convertView;
    }

    class ViewHolder {
        View view;
    }
}
