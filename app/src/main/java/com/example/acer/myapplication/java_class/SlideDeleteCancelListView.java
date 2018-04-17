package com.example.acer.myapplication.java_class;

import android.content.Context;

import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.acer.myapplication.R;

public class SlideDeleteCancelListView extends ListView  {
    private static final String TAG = "SlideDelListView";
    private LayoutInflater mInflater = null;
    /**
     * 用户滑动的最小距离
     */
    private int touchSlop;
    /**
     * 是否响应滑动
     */
    private boolean isSliding;
    /**
     * 手指按下时的x坐标
     */
    private int xDown;
    /**
     * 手指按下时的y坐标
     */
    private int yDown;
    /**
     * 手指移动时的x坐标
     */
    private int xMove;
    /**
     * 手指移动时的y坐标
     */
    private int yMove;

    /**
     * 当前手指触摸的View
     */
    private View mCurrentView;
    /**
     * 单签手指触摸的位置
     */
    private int mCurrentViewPos;

    //回调接口
    private DelButtonClickListener mDelListener = null;
    private ChangeButtonClickListener mChangeListener = null;
    private ShareButtonClickListener mShareListener=null;
    private PopupWindow mPopupWindow = null;
    //声明按钮
    private Button mDelBtn = null,mChangeBtn= null,mShareBtn=null;

    private int mPopupWindowWidth, mPopupWindowHeight;

    /** 自定义ListView的构造方法 在里面做一些必要的一些初始化
     * @param context
     * @param attrs
     */
    public SlideDeleteCancelListView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mInflater = LayoutInflater.from(context);
        //用户手指移动的最小距离，用来判断是否响应触发移动事件
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        View view = mInflater.inflate(R.layout.item_hide,null);
         //加载所有按钮
        mDelBtn = (Button) view.findViewById(R.id.item_delete);
        mChangeBtn = (Button) view.findViewById(R.id.item_change);
        mShareBtn=(Button) view.findViewById(R.id.item_share);
        mPopupWindow = new PopupWindow(view,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
/**
 * 先调用下measure,否则拿不到宽和高
 */
        mPopupWindow.getContentView().measure(0,0);
        mPopupWindowHeight = mPopupWindow.getContentView().getMeasuredHeight();
        mPopupWindowWidth = mPopupWindow.getContentView().getMeasuredWidth();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                //手指按下时水平方向x的位置
                xDown = x;
                //手指按下时垂直方向y的位置
                yDown = y;
/*
* 如果当前popupWindow显示，则直接隐藏，然后屏蔽ListView的Touch事件的下传
* */
                if (mPopupWindow.isShowing()){
                    dismissPopWindow();
                    return false;
                }
                // 获得当前手指按下时的item的位置
                mCurrentViewPos = pointToPosition(xDown,yDown);
                // 获得当前手指按下时的ListView的item项
                mCurrentView = getChildAt(mCurrentViewPos - getFirstVisiblePosition());
                break;
            case MotionEvent.ACTION_MOVE:
                //手指移动时x的位置
                xMove = x;
                //手指一动时y的位置
                yMove = y;
                //水平滑动的距离（可能为负值）
                int dx = xMove - xDown;
                //垂直滑动的距离（可能为负值）
                int dy = yMove - yDown;
/*
* 判断是否是从右到左的滑动
* */
                if ( xMove < xDown && Math.abs(dx) > touchSlop && Math.abs(dy) < touchSlop ){
                    Log.e(TAG, "touchslop = " + touchSlop + " , dx = " + dx + " , dy = " + dy);
                    isSliding = true;
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
/*
* 如果是从右到左的滑动才响应，之前已在 dispatchTouchEvent 中获得了是否是从右向左滑动
* */
        if ( isSliding ){
            switch (action){
                case MotionEvent.ACTION_MOVE:
                    int []location = new int[2];
//获得当前item的位置x与y
                    mCurrentView.getLocationOnScreen(location);
//设置PopupWindow的动画
                    mPopupWindow.setAnimationStyle(R.style.popupwindow_anim_style);
                    mPopupWindow.update();

                    Log.e(TAG,"width location[0]: " + location[0]);
                    Log.e(TAG,"height location[1]: " + location[1]);
                    Log.e(TAG,"mCurrentView.getWidth(): " + mCurrentView.getWidth());
                    Log.e(TAG,"mCurrentView.getHeight(): " + mCurrentView.getHeight());
                    Log.e(TAG,"mPopupWindowHeight: " + mPopupWindowHeight);
//设置“取消关注”、“删除”按钮PopWindow的显示位置
//相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
//相对某个控件的位置，有偏移;xoff表示x轴的偏移，正值表示向左，负值表示向右；yoff表示相对y轴的偏移，正值是向下，负值是向上；
                    mPopupWindow.showAtLocation(mCurrentView,
                            Gravity.LEFT | Gravity.TOP,
                            location[0] + mCurrentView.getWidth() ,
                            location[1] + mCurrentView.getHeight()/2 - mPopupWindowHeight /2);
//-------------------------设置按钮的回调------------------------------------------
                    mDelBtn.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            mDelListener.onDelClick(mCurrentViewPos);
                            mPopupWindow.dismiss();
                        }
                    });

                    mChangeBtn.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            mChangeListener.onChangeClick(mCurrentViewPos);
                            mPopupWindow.dismiss();
                        }
                    });

                    mShareBtn.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            mShareListener.onShareClick(mCurrentViewPos);
                            mPopupWindow.dismiss();
                        }
                    });

                    Log.e(TAG, "mPopupWindow.getHeight()=" + mPopupWindowHeight);
                    break;
                case MotionEvent.ACTION_UP:
//设置侧滑关闭
                    isSliding = false;
                    break;
            }
// 相应滑动期间屏幕itemClick事件，避免发生冲突
            return true;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 隐藏popupWindow
     */
    private void dismissPopWindow()
    {
        if (mPopupWindow != null && mPopupWindow.isShowing())
        {
            mPopupWindow.dismiss();
        }
    }

  //定义具体Listener  把当前Listener对象传入
    public void setDelButtonClickListener(DelButtonClickListener listener)
    {
        mDelListener = listener;
    }


    public void setChangeButtonClickListener(ChangeButtonClickListener listener){
        mChangeListener  = listener;
    }

    public void setShareButtonClickListener(ShareButtonClickListener listener){
        mShareListener  = listener;
    }
    //-----------------实现接口重写点击事件方法-------------------------------------------


    interface DelButtonClickListener{
        void onDelClick(int position);
    }

    interface ChangeButtonClickListener{
        void onChangeClick(int position);

    }

    interface ShareButtonClickListener{

        void onShareClick(int position);
    }

}