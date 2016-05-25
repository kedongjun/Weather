package cn.h2o2.weather.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * @author xiaanming&&wangxuanao
 * @blog http://blog.csdn.net/xiaanming
 */
public class DragGridView extends GridView {
    /**
     * DragGridView的item长按响应的时间， 默认是1000毫秒，也可以自行设置。目前没有用到延迟。
     */
    private long dragResponseMS = 1000;

    /**
     * 是否可以拖拽，默认不可以
     */
    private boolean isDrag = false;

    private int mDownX;
    private int mDownY;
    private int moveX;
    private int moveY;

    private int nColumns = 2;

    /**
     * 是否计算状态栏的高度在内，涉及到镜像的起始位置y
     */
    private boolean isContainStatusHeight = false;

    private boolean isMoving = false;

    /**
     * 正在拖拽的position
     */
    private int mDragPosition;

    /**
     * 刚开始拖拽的item对应的View
     */
    private View mStartDragItemView = null;

    /**
     * 用于拖拽的镜像，这里直接用一个ImageView
     */
    private ImageView mDragImageView;

    /**
     * 震动器
     */
    private Vibrator mVibrator;

    private WindowManager mWindowManager;
    /**
     * item镜像的布局参数
     */
    private WindowManager.LayoutParams mWindowLayoutParams;

    /**
     * 我们拖拽的item对应的Bitmap
     */
    private Bitmap mDragBitmap;

    /**
     * 按下的点到所在item的上边缘的距离
     */
    private int mPoint2ItemTop;

    /**
     * 按下的点到所在item的左边缘的距离
     */
    private int mPoint2ItemLeft;

    /**
     * DragGridView距离屏幕顶部的偏移量
     */
    private int mOffset2Top;

    /**
     * DragGridView距离屏幕左边的偏移量
     */
    private int mOffset2Left;

    /**
     * 状态栏的高度
     */
    private int mStatusHeight;

    /**
     * DragGridView自动向下滚动的边界值
     */
    private int mDownScrollBorder;

    /**
     * DragGridView自动向上滚动的边界值
     */
    private int mUpScrollBorder;

    /**
     * DragGridView自动滚动的速度
     */
    private static final int speed = 20;

    public DragGridView(Context context) {
        this(context, null);
    }

    public DragGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mVibrator = (Vibrator) context
                .getSystemService(Context.VIBRATOR_SERVICE);
        mWindowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        mStatusHeight = getStatusHeight(context); // 获取状态栏的高度

    }

    private Handler mHandler = new Handler();

    // 用来处理是否为长按的Runnable
    private Runnable mLongClickRunnable = new Runnable() {

        @Override
        public void run() {
            isDrag = true; // 设置可以拖拽
            isMoving = false;
            mVibrator.vibrate(50); // 震动一下

            mStartDragItemView.setVisibility(View.INVISIBLE);// 隐藏该item

            // 根据我们按下的点显示item镜像
            createDragImage(mDragBitmap, mDownX, mDownY);
        }
    };

    /**
     * 设置响应拖拽的毫秒数，默认是1000毫秒
     *
     * @param dragResponseMS
     */
    public void setDragResponseMS(long dragResponseMS) {
        this.dragResponseMS = dragResponseMS;
    }

    /**
     * 长按检测事件
     *
     * @author wangxuanao
     */
    public boolean setOnItemLongClickListener(final MotionEvent ev) {

        setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(
                    AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //*************长按最后一个，则把事情取消了***********
                if (arg2 == getChildCount() - 1) {
                    return true;
                }

                // arg2和arg3一直是相等的。及点击的item的position
                mDownX = (int) ev.getX();
                mDownY = (int) ev.getY();

                // 获取所点击item的position
                mDragPosition = arg2;

                if (mDragPosition == AdapterView.INVALID_POSITION) {
                    Log.e("tag====onItemLongClick", "INVALID_POSITION");
                    return true;
                }

                // 根据position获取该item所对应的View
                mStartDragItemView = getChildAt(mDragPosition
                        - getFirstVisiblePosition());

                // 下面这几个距离大家可以参考我的博客上面的图来理解下
                mPoint2ItemTop = mDownY - mStartDragItemView.getTop();
                mPoint2ItemLeft = mDownX - mStartDragItemView.getLeft();

                mOffset2Top = (int) (ev.getRawY() - mDownY);
                mOffset2Left = (int) (ev.getRawX() - mDownX);

                // 获取DragGridView自动向上滚动的偏移量，小于这个值，DragGridView向下滚动
                mDownScrollBorder = getHeight() / 4;
                // 获取DragGridView自动向下滚动的偏移量，大于这个值，DragGridView向上滚动
                mUpScrollBorder = getHeight() * 3 / 4;

                // 开启mDragItemView绘图缓存
                mStartDragItemView.setDrawingCacheEnabled(true);
                // 获取mDragItemView在缓存中的Bitmap对象
                mDragBitmap = Bitmap.createBitmap(mStartDragItemView
                        .getDrawingCache());
                // 这一步很关键，释放绘图缓存，避免出现重复的镜像
                mStartDragItemView.destroyDrawingCache();

                // 使用Handler延迟dragResponseMS执行mLongClickRunnable
                // mHandler.postDelayed(mLongClickRunnable, dragResponseMS);
                // 不延迟加载
                mHandler.post(mLongClickRunnable);

                return false;
            }
        });
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            return setOnItemLongClickListener(ev);
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isDrag && mDragImageView != null) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    moveX = (int) ev.getX();
                    moveY = (int) ev.getY();
                    // 拖动item
                    onDragItem(moveX, moveY);
                    break;
                case MotionEvent.ACTION_UP:
                    onStopDrag();
                    isDrag = false;
                    mHandler.removeCallbacks(mLongClickRunnable); // 线程结束
                    mHandler.removeCallbacks(mScrollRunnable);
                    break;
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 创建拖动的镜像
     *
     * @param bitmap
     * @param downX  按下的点相对父控件的X坐标
     * @param downY  按下的点相对父控件的X坐标
     */
    private void createDragImage(Bitmap bitmap, int downX, int downY) {

        DragAdapter adapter = (DragAdapter) this.getAdapter();
        adapter.showDropItem(false);

        mWindowLayoutParams = new WindowManager.LayoutParams();
        mWindowLayoutParams.format = PixelFormat.TRANSLUCENT; // 图片之外的其他地方透明
        mWindowLayoutParams.gravity = Gravity.TOP | Gravity.LEFT;
        mWindowLayoutParams.x = downX - mPoint2ItemLeft + mOffset2Left;
        if (isContainStatusHeight) {
            mWindowLayoutParams.y = downY - mPoint2ItemTop + mOffset2Top
                    - mStatusHeight; // 状态栏高度 可选
        } else {
            mWindowLayoutParams.y = downY - mPoint2ItemTop + mOffset2Top;
        }
        Log.e("tag", "mWindowLayoutParams   x=" + mWindowLayoutParams.x
                + "   y=" + mWindowLayoutParams.y);
        mWindowLayoutParams.alpha = 0.55f; // 透明度
        mWindowLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;

        mDragImageView = new ImageView(getContext());
        mDragImageView.setImageBitmap(bitmap);
        mWindowManager.addView(mDragImageView, mWindowLayoutParams);
    }

    /**
     * 从界面上面移动拖动镜像
     */
    private void removeDragImage() {
        if (mDragImageView != null) {
            mWindowManager.removeView(mDragImageView);
            mDragImageView = null;
        }
    }

    /**
     * 拖动item，在里面实现了item镜像的位置更新，item的相互交换以及GridView的自行滚动
     *
     * @param moveX
     * @param moveY
     */
    private void onDragItem(int moveX, int moveY) {
        mWindowLayoutParams.x = moveX - mPoint2ItemLeft + mOffset2Left;

        if (isContainStatusHeight) {
            mWindowLayoutParams.y = moveY - mPoint2ItemTop + mOffset2Top
                    - mStatusHeight + 360;// 状态栏高度 可选 barHeight
        } else {
            mWindowLayoutParams.y = moveY - mPoint2ItemTop + mOffset2Top + 360;
        }
        mWindowManager.updateViewLayout(mDragImageView,
                mWindowLayoutParams); // 更新镜像的位置
        onSwapItem(moveX, moveY);

        // GridView自动滚动
        mHandler.post(mScrollRunnable);
    }

    /**
     * 当moveY的值大于向上滚动的边界值，触发GridView自动向上滚动 当moveY的值小于向下滚动的边界值，触犯GridView自动向下滚动
     * 否则不进行滚动
     */
    private Runnable mScrollRunnable = new Runnable() {

        @Override
        public void run() {
            int scrollY;
            if (moveY > mUpScrollBorder) {
                scrollY = speed;
                mHandler.postDelayed(mScrollRunnable, 25);
            } else if (moveY < mDownScrollBorder) {
                scrollY = -speed;
                mHandler.postDelayed(mScrollRunnable, 25);
            } else {
                scrollY = 0;
                mHandler.removeCallbacks(mScrollRunnable);
            }

            // 当我们的手指到达GridView向上或者向下滚动的偏移量的时候，可能我们手指没有移动，但是DragGridView在自动的滚动
            // 所以我们在这里调用下onSwapItem()方法来交换item
            onSwapItem(moveX, moveY);

            smoothScrollBy(scrollY, 10);
        }
    };

    /**
     * 交换item,并且控制item之间的显示与隐藏效果
     *
     * @param moveX
     * @param moveY
     */
    private void onSwapItem(int moveX, int moveY) {
        // 获取我们手指移动到的那个item的position
        final int tempPosition = pointToPosition(moveX, moveY);

        // 假如tempPosition 改变了并且tempPosition不等于-1,则进行交换
        //*************保存最后不动 tempPosition != getChildCount() - 1***********
        if (tempPosition != mDragPosition
                && tempPosition != AdapterView.INVALID_POSITION
                && tempPosition != getChildCount() - 1) {

            if (!isMoving) {

                final int dropPosition = tempPosition
                        - getFirstVisiblePosition();
                int dragPosition = mDragPosition - getFirstVisiblePosition();
                final int startPosition = mDragPosition;
                final int endPosition = tempPosition;
                int MoveNum = dropPosition - dragPosition;
                int holdPosition;
                if (dragPosition == dropPosition)
                    MoveNum = 0;
                if (MoveNum != 0) {
                    int itemMoveNum = Math.abs(MoveNum);
                    float Xoffset, Yoffset;
                    for (int i = 0; i < itemMoveNum; i++) {

                        if (MoveNum > 0) {
                            holdPosition = dragPosition + 1;
                            Xoffset = (dragPosition / nColumns == holdPosition
                                    / nColumns) ? (-1) : (nColumns - 1);
                            Yoffset = (dragPosition / nColumns == holdPosition
                                    / nColumns) ? 0 : (-1);
                        } else {
                            holdPosition = dragPosition - 1;
                            Xoffset = (dragPosition / nColumns == holdPosition
                                    / nColumns) ? 1 : (-(nColumns - 1));
                            Yoffset = (dragPosition / nColumns == holdPosition
                                    / nColumns) ? 0 : 1;
                        }
                        ViewGroup moveView = (ViewGroup) getChildAt(holdPosition);
                        Animation animation = getMoveAnimation(Xoffset, Yoffset);
                        moveView.startAnimation(animation);
                        dragPosition = holdPosition;
                        final DragAdapter adapter = (DragAdapter) this
                                .getAdapter();

                        isMoving = true;

                        if (i == itemMoveNum - 1)
                            animation
                                    .setAnimationListener(new Animation.AnimationListener() {

                                        @Override
                                        public void onAnimationStart(
                                                Animation animation) {
                                        }

                                        @Override
                                        public void onAnimationRepeat(
                                                Animation animation) {
                                        }

                                        @Override
                                        public void onAnimationEnd(
                                                Animation animation) {
                                            adapter.exchange(startPosition,
                                                    endPosition);
                                            isMoving = false;
                                        }
                                    });
                    }
                }
            }
            mDragPosition = tempPosition;
        }
    }

    /**
     * 动画交换交换item
     *
     * @param dropPosition
     * @param dragPosition
     */
    public void OnMove(int dropPosition, int dragPosition) {

    }

    /**
     * 停止拖拽我们将之前隐藏的item显示出来，并将镜像移除
     */
    private void onStopDrag() {
        DragAdapter adapter = (DragAdapter) this.getAdapter();
        adapter.showDropItem(true);
        adapter.notifyDataSetChanged();
        //并将镜像移除
        removeDragImage();
    }

    /**
     * 获取状态栏的高度
     *
     * @param context
     * @return
     */
    private static int getStatusHeight(Context context) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        ((Activity) context).getWindow().getDecorView()
                .getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass
                        .getField("status_bar_height").get(localObject)
                        .toString());
                statusHeight = context.getResources().getDimensionPixelSize(i5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

    /**
     * 移动动画
     *
     * @author wangxuanao
     */
    public Animation getMoveAnimation(float x, float y) {
        TranslateAnimation go = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, x,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, y);
        go.setFillAfter(true);
        go.setDuration(300);
        return go;
    }

}
