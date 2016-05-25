package cn.h2o2.weather.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.h2o2.weather.R;
import cn.h2o2.weather.model.Weather;

/**
 * @author xiaanming&&wangxuanao
 * @blog http://blog.csdn.net/xiaanming
 */
public class DragAdapter extends BaseAdapter {
    private ArrayList<Weather> cities = new ArrayList<>();
    private Context context;
    private int mHidePosition = -1;
    private boolean isChanged = false;
    private boolean ShowItem = false;
    private Weather empty = new Weather();

    public DragAdapter(Context context) {
        this.context = context;
        cities.add(empty);
    }

    public void addItem(Weather weather) {
        cities.remove(empty);
        cities.add(weather);
        cities.add(empty);
    }

    public void addItemAll(ArrayList<Weather> weathers) {
        cities.remove(empty);
        cities.addAll(weathers);
        cities.add(empty);
    }


    public void isEdit(boolean isEidt) {
        for (int i = 0; i < cities.size(); i++) {
            Weather weather = cities.get(i);
            weather.setIsEdit(isEidt);
        }
        //
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public Object getItem(int position) {
        return cities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 由于复用convertView导致某些item消失了，所以这里不复用item，
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(
                R.layout.alc_item_city_manager, null);

        final Weather city = cities.get(position);
        if (city != empty) {
            convertView.findViewById(R.id.weatherLayout).setVisibility(View.VISIBLE);
            convertView.findViewById(R.id.addLayout).setVisibility(View.GONE);

            TextView locationText = (TextView) convertView
                    .findViewById(R.id.locationText);
            TextView temText = (TextView) convertView
                    .findViewById(R.id.temText);
            ImageView deleteImage = (ImageView) convertView
                    .findViewById(R.id.deleteImage);
            //
            locationText.setText(city.getLocation());
            temText.setText(city.getTemMin() + "\n" + city.getTemMax());
            deleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cities.remove(city);
                    notifyDataSetChanged();
                }
            });
            if (city.isEdit()) {
                deleteImage.setVisibility(View.VISIBLE);
            } else {
                deleteImage.setVisibility(View.GONE);
            }

        } else {
            convertView.findViewById(R.id.weatherLayout).setVisibility(View.GONE);
            convertView.findViewById(R.id.addLayout).setVisibility(View.VISIBLE);
            convertView.findViewById(R.id.deleteImage).setVisibility(View.GONE);
        }

        //
        if (isChanged) {
            if (position == mHidePosition) {
                if (!ShowItem) {
                    convertView.setVisibility(View.INVISIBLE);
                }
            }
        }

        return convertView;
    }

    /**
     * 设置是否隐藏
     *
     * @param showItem
     */
    public void showDropItem(boolean showItem) {
        this.ShowItem = showItem;
    }

    /**
     * 设置隐藏的position
     *
     * @param hidePosition
     */
    public void setHidePosition(int hidePosition) {
        this.mHidePosition = hidePosition;
    }

    /**
     * 交换数据
     *
     * @param startPosition
     * @param endPosition
     */
    public void exchange(int startPosition, int endPosition) {
        // HashMap<String, Object> temp = cities.get(from);
        //
        // // 这里的处理需要注意下
        // if (from < to) {
        // for (int i = from; i < to; i++) {
        // Collections.swap(cities, i, i + 1);
        // }
        // } else if (from > to) {
        // for (int i = from; i > to; i--) {
        // Collections.swap(cities, i, i - 1);
        // }
        // }
        //
        // cities.set(to, temp);
        this.mHidePosition = endPosition;
        Object startObject = getItem(startPosition);
        Log.e("log", "startPostion ==== " + startPosition);
        Log.e("log", "endPosition ==== " + endPosition);
        if (startPosition < endPosition) {
            cities.add(endPosition + 1, (Weather) startObject);
            cities.remove(startPosition);
        } else {
            cities.add(endPosition, (Weather) startObject);
            cities.remove(startPosition + 1);
        }

        isChanged = true;

        notifyDataSetChanged();
    }

}
