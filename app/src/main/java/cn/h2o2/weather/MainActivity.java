package cn.h2o2.weather;

import android.Manifest;
import android.animation.Animator;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.widget.PullZoomView;

public class MainActivity extends AppCompatActivity {

    private final static int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1000;
    TextView caishenTalkImage;
    ImageView caishenImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alc_fragment_weather_details);


//        caishenTalkImage = (TextView) findViewById(R.id.caishenTalkImage);

        caishenImage = (ImageView) findViewById(R.id.caishenImage);
        caishenImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimatorUtil.caishenAnimator(caishenImage, 1500,listener);
            }
        });

        PullZoomView pzv = (PullZoomView) findViewById(R.id.pzv);
        pzv.setIsParallax(false);
        pzv.setIsZoomEnable(true);
        pzv.setSensitive(1.5f);
        pzv.setZoomTime(500);


        //在这里为应用设置异常处理程序，然后我们的程序才能捕获未处理的异常
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        }
    }

    Animator.AnimatorListener listener = new Animator.AnimatorListener() {

        @Override
        public void onAnimationStart(Animator animator) {

            caishenTalkImage.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onAnimationEnd(Animator animator) {
            caishenTalkImage.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    };

}
