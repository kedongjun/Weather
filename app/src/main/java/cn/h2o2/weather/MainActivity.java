package cn.h2o2.weather;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.widget.PullZoomView;

import java.util.ArrayList;

import cn.h2o2.weather.model.Weather;

public class MainActivity extends AppCompatActivity {


    private Weather weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        findViewById();

        setTopUI();
        setHourUI();
        setDayUI();
    }

    private void init() {
        weather = new Weather();
        ArrayList<Weather.Hour> hours = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            Weather.Hour hour = new Weather.Hour();
            hour.setHour(i + "时");
            hour.setState("晴转多云");
            hour.setTem(i + "°");
            hours.add(hour);
        }
        weather.setHours(hours);

        ArrayList<Weather.Day> days = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            Weather.Day day = new Weather.Day();
            day.setDay(i + "今天");
            day.setDate("05-"+i);
            day.setStateMin("晴转多云");
            day.setStateMax("晴转多云");
            day.setTemMin(i + "°");
            day.setTemMax((i*3) + "°");
            days.add(day);
        }
        weather.setDays(days);
    }

    private void findViewById() {
        setContentView(R.layout.alc_fragment_weather_details);

    }

    private void setTopUI() {

        final TextView caishenTalkText = (TextView) findViewById(R.id.caishenTalkText);
        final ImageView caishenImage = (ImageView) findViewById(R.id.caishenImage);
        final Animator.AnimatorListener listener = new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animator) {
                caishenTalkText.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                caishenTalkText.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        };
        AnimatorUtil.caishenAnimator(caishenImage, 1500, listener);

        //
        PullZoomView pzv = (PullZoomView) findViewById(R.id.pzv);
        pzv.setIsParallax(true);
        pzv.setIsZoomEnable(true);
        pzv.setSensitive(1.5f);
        pzv.setZoomTime(500);

    }


    private void setHourUI() {
        LinearLayout hourView = (LinearLayout) findViewById(R.id.hourView);
        ArrayList<Weather.Hour> hours = weather.getHours();
        for (int i = 0; i < hours.size(); i++) {
            Weather.Hour hour = hours.get(i);
            View view = LinearLayout.inflate(this,
                    R.layout.alc_item_weather_24hours, null);
            TextView textView = (TextView) view.findViewById(R.id.temText);
            textView.setText(hour.getTem());

            hourView.addView(view);
        }
    }

    private void setDayUI() {
        LinearLayout hourView = (LinearLayout) findViewById(R.id.daysView);
        ArrayList<Weather.Day> days = weather.getDays();
        for (int i = 0; i < days.size(); i++) {
            Weather.Day day = days.get(i);
            View view = LinearLayout.inflate(this,
                    R.layout.alc_item_weather_15days, null);
            TextView dayText = (TextView) view.findViewById(R.id.dayText);
            dayText.setText(day.getDay());

            hourView.addView(view);
        }
    }


}
