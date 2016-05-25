package cn.h2o2.weather;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;

/**
 * author      yichen
 * Email       kedongjun@mmclick.com
 * Date        2016/5/19 19:32
 * description: TODO
 */
public class AnimatorUtil {

    public static void caishenAnimator(View view, long duration,
                                       Animator.AnimatorListener listener) {
        //X后退到原点
        PropertyValuesHolder translationX = PropertyValuesHolder
                .ofFloat("translationX", 100f,0);
        //Y前进到原点
        PropertyValuesHolder translationY = PropertyValuesHolder
                .ofFloat("translationY", -100f, 0);
        //X从0放大到1倍
        PropertyValuesHolder scaleX = PropertyValuesHolder
                .ofFloat("scaleX", 0, 1f);
        //Y从0放大到1倍
        PropertyValuesHolder scaleY = PropertyValuesHolder
                .ofFloat("scaleY", 0, 1f);

        //组合执行
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(
                view, translationX, translationY, scaleX, scaleY).
                setDuration(duration);
        animator.addListener(listener);
        animator.start();
    }
}
