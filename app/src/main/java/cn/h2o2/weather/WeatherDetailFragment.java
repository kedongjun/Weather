package cn.h2o2.weather;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lzy.widget.PullZoomView;

import java.util.ArrayList;

import cn.h2o2.weather.adapter.IconListViewAdapter;
import cn.h2o2.weather.adapter.TextListViewAdapter;
import cn.h2o2.weather.adapter.WeatherGuideAdapter;
import cn.h2o2.weather.model.Weather;

/**
 * author      yichen
 * Email       kedongjun@mmclick.com
 * Date        2016/5/23 10:30
 * description: TODO
 */
public class WeatherDetailFragment extends Fragment {

    WeatherDetailActivity activity;
    ViewGroup rootLayout;
    public boolean isLoad;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (rootLayout != null) {
            isLoad = true;
            return;
        }
        activity = (WeatherDetailActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootLayout == null) {
            rootLayout = (ViewGroup) inflater.inflate(
                    R.layout.alc_fragment_weather_details, null);
        }
        return rootLayout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isLoad) {
            return;
        }
        findViewById();
        showCaiShenUI();
        setTopUI();
        setHourUI();
        setDayUI();
        setGuideUI();
        setDetail();
    }

    private void findViewById() {
        //
        PullZoomView pzv = (PullZoomView) rootLayout.findViewById(R.id.pzv);
        pzv.setIsParallax(false);
        pzv.setIsZoomEnable(true);
        pzv.setSensitive(1.5f);
        pzv.setZoomTime(500);
    }


    private void setTopUI() {
        TextView updateText = (TextView) rootLayout.findViewById(R.id.updateText);
        TextView temText = (TextView) rootLayout.findViewById(R.id.temText);
        TextView stateText = (TextView) rootLayout.findViewById(R.id.stateText);
        TextView kongqiText = (TextView) rootLayout.findViewById(R.id.kongqiText);
        ListView topList = (ListView) rootLayout.findViewById(R.id.topList);
        Weather.Top top = activity.weather.getTop();
        temText.setText(top.getTem());
        stateText.setText(top.getState());
        kongqiText.setText(top.getKongqi() + " 优");
        topList.setAdapter(new IconListViewAdapter(activity, top.getInfos()));
    }

    private void showCaiShenUI() {
        final TextView caishenTalkText = (TextView) rootLayout.findViewById(R.id.caishenTalkText);
        ImageView caishenImage = (ImageView) rootLayout.findViewById(R.id.caishenImage);
        ImageView deleteImage = (ImageView) rootLayout.findViewById(R.id.deleteImage);
        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootLayout.findViewById(R.id.caishenLayout).setVisibility(View.GONE);
            }
        });

        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {

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

        AnimatorUtil.caishenAnimator(caishenImage, 1500, animatorListener);
    }

    private void setHourUI() {
        LinearLayout hourView = (LinearLayout) rootLayout.findViewById(R.id.hourView);
        ArrayList<Weather.Hour> hours = activity.weather.getHours();
        for (int i = 0; i < hours.size(); i++) {
            Weather.Hour hour = hours.get(i);
            View view = LinearLayout.inflate(activity,
                    R.layout.alc_item_weather_24hours, null);
            TextView textView = (TextView) view.findViewById(R.id.temText);
            textView.setText(hour.getTem());

            hourView.addView(view);
        }
    }

    private void setDayUI() {
        LinearLayout hourView = (LinearLayout) rootLayout.findViewById(R.id.daysView);
        ArrayList<Weather.Day> days = activity.weather.getDays();
        for (int i = 0; i < days.size(); i++) {
            Weather.Day day = days.get(i);
            View view = LinearLayout.inflate(activity,
                    R.layout.alc_item_weather_15days, null);
            TextView dayText = (TextView) view.findViewById(R.id.dayText);
            dayText.setText(day.getDay());

            hourView.addView(view);
        }
    }

    private void setGuideUI() {
        GridView guideView = (GridView) rootLayout.findViewById(R.id.guideView);
        guideView.setAdapter(new WeatherGuideAdapter(activity,
                activity.weather.getGuides()));
    }

    private void setDetail(){
        ListView detailList = (ListView) rootLayout.findViewById(R.id.detailList);
        Weather.Detail detail = activity.weather.getDetail();
        detailList.setAdapter(new TextListViewAdapter(activity, detail.getInfos()));
    }
}
