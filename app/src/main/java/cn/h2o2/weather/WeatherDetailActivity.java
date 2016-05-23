package cn.h2o2.weather;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;

import cn.h2o2.weather.adapter.FmPagerAdapter;
import cn.h2o2.weather.model.Weather;

public class WeatherDetailActivity extends AppCompatActivity {

    public Weather weather;
    private Toolbar toolbar;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        findViewById();
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_weather_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //
        if (id == R.id.action_refresh) {

            return false;
        }
        if (id == R.id.action_add) {
            Intent intent = new Intent(this, CityManagerActivity.class);
            startActivity(intent);
            return false;
        }
        if (id == R.id.action_share) {

            return false;
        }

        return super.onOptionsItemSelected(item);
    }



    private void init() {
        weather = new Weather();
        //top
        Weather.Top top = new Weather.Top();
        top.setKongqi(25);
        top.setState("晴转多云");
        top.setTem(20 + "°");
        HashMap<String, String> map = new HashMap<>();
        map.put("风力", "2级");
        map.put("能见度", "5.0km");
        map.put("湿度", "74%");
        map.put("北京单行限号", "");
        top.setInfos(map);
        weather.setTop(top);
        //hour
        ArrayList<Weather.Hour> hours = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            Weather.Hour hour = new Weather.Hour();
            hour.setHour(i + "时");
            hour.setState("晴转多云");
            hour.setTem(i + "°");
            hours.add(hour);
        }
        weather.setHours(hours);
        //day
        ArrayList<Weather.Day> days = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            Weather.Day day = new Weather.Day();
            day.setDay(i + "今天");
            day.setDate("05-" + i);
            day.setStateMin("晴转多云");
            day.setStateMax("晴转多云");
            day.setTemMin(i + "°");
            day.setTemMax((i * 3) + "°");
            days.add(day);
        }
        weather.setDays(days);

        //detail
        Weather.Detail detail = new Weather.Detail();
        HashMap<String,String> detailMap = new HashMap<>();
        detailMap.put("体感温度","30°");
        detailMap.put("云量","多云");
        detailMap.put("露点温度","12°");
        detailMap.put("气压","1004pm");
        detail.setInfos(detailMap);
        weather.setDetail(detail);

        //guide
        ArrayList<Weather.Guide> guides = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Weather.Guide guide = new Weather.Guide();
            guide.setTitle("穿衣服" + i);
            guide.setContent("长袖衬衣" + i);
            guides.add(guide);
        }
        weather.setGuides(guides);
    }

    private void findViewById() {
        setContentView(R.layout.alc_activity_weather_detail);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    private void initView() {
        toolbar.setTitle("天气详细");
        toolbar.setTitleTextColor(Color.BLACK);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        FmPagerAdapter adapter = new FmPagerAdapter(getSupportFragmentManager(), this);
        adapter.addFragment("广州", new WeatherDetailFragment());
        adapter.addFragment("深圳", new WeatherDetailFragment());
        adapter.addFragment("茂名", new WeatherDetailFragment());
        adapter.addFragment("顺德", new WeatherDetailFragment());
        viewPager.setAdapter(adapter);


        //
        final LinearLayout indicatorLayout = (LinearLayout)
                findViewById(R.id.indicatorLayout);
        for (int i = 0; i < adapter.getCount(); i++) {
            ImageView imageView = new ImageView(this);
            if (viewPager.getCurrentItem() == i) {
                imageView.setImageResource(R.drawable.alc_weather_indicator_on);
            } else {
                imageView.setImageResource(
                        R.drawable.alc_weather_indicator_off);
            }
            indicatorLayout.addView(imageView);
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < indicatorLayout.getChildCount(); i++) {
                    ImageView imageView = (ImageView) indicatorLayout.getChildAt(i);
                    imageView.setImageResource(
                            R.drawable.alc_weather_indicator_off);
                    if (position == i) {
                        imageView.setImageResource(
                                R.drawable.alc_weather_indicator_on);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });


    }
}
