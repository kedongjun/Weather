package cn.h2o2.weather;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import cn.h2o2.weather.model.Weather;
import cn.h2o2.weather.view.DragAdapter;
import cn.h2o2.weather.view.DragGridView;

public class CityManagerActivity extends AppCompatActivity {

    private ArrayList<Weather> weathers = new ArrayList<>();
    private Toolbar toolbar;
    private DragGridView dragGridView;
    private DragAdapter dragAdapter;
    private boolean isEdit;

    public static void start(Context context, ArrayList<Weather> weathers) {
        Intent intent = new Intent(context, CityManagerActivity.class);
        intent.putExtra("weathers", weathers);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        findViewById();
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_city_manager, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //
        if (id == R.id.action_refresh) {

            return false;
        }
        if (id == R.id.action_edit) {
            isEdit = !isEdit;
            dragAdapter.isEdit(isEdit);
            return false;
        }

        return super.onOptionsItemSelected(item);
    }


    private void init() {
        ArrayList<Weather> temps = (ArrayList<Weather>) getIntent()
                .getSerializableExtra("weathers");
        if (temps != null) {
            weathers.addAll(temps);
        }
    }

    private void findViewById() {
        setContentView(R.layout.alc_activity_city_manager);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        dragGridView = (DragGridView) findViewById(R.id.dragGridView);
    }

    private void initView() {
        toolbar.setTitle("城市管理");
        toolbar.setTitleTextColor(Color.BLACK);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //
        dragAdapter = new DragAdapter(this);
        dragAdapter.addItemAll(weathers);
        dragGridView.setAdapter(dragAdapter);
    }
}
