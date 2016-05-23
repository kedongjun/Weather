package cn.h2o2.weather;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class CityManagerActivity extends AppCompatActivity {

    private Toolbar toolbar;

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
        if (id == R.id.action_add) {

            return false;
        }
        if (id == R.id.action_share) {

            return false;
        }

        return super.onOptionsItemSelected(item);
    }



    private void init() {

    }

    private void findViewById() {
        setContentView(R.layout.alc_activity_city_manager);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void initView() {
        toolbar.setTitle("城市管理");
        toolbar.setTitleTextColor(Color.BLACK);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
