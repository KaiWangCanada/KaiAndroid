package com.unit.common.ui.IndexListViewCompoment;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unit.common.R;

import java.util.ArrayList;

//带圆角框的索引

public class CornerIndexFramelayout extends FrameLayout {
    private Context mContext;

    private ExpandableListView mListView;
    private LinearLayout alphabetLayout;
    private TextView mTextView;
    private ArrayList<String> indexArray;//索引值存放数组
    private int length;//索引值长度

    private AlphabetPositionListener positionListener;
    
    private float screenDensity;
    
    private Handler mHandler;
    private HideIndicator mHideIndicator = new HideIndicator();
    
    private int indicatorDuration = 1000;
    public void setIndicatorDuration(int duration) {
        this.indicatorDuration = duration;
    }
    
    private final class HideIndicator implements Runnable {
        @Override
        public void run() {
            mTextView.setVisibility(View.INVISIBLE);
        }
    }

    public CornerIndexFramelayout (Context context) {
        super(context);
        init(context);
    }
    
    public CornerIndexFramelayout (Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    
    private void init(Context context) {
        mContext = context;

        screenDensity = context.getResources().getDisplayMetrics().density;
        mHandler = new Handler();
        
        //initAlphabetLayout(mContext);

        mTextView = new TextView(mContext);
        mTextView.setTextSize(convertDIP2PX(40));
        mTextView.setTextColor(Color.argb(150, 150, 150, 150));
        //mTextView.setBackgroundColor(Color.argb(200, 0, 0, 0));
        mTextView.setMinWidth(convertDIP2PX(70));
        mTextView.setMinHeight(convertDIP2PX(70));
        int pixels = convertDIP2PX(10);
        mTextView.setPadding(pixels, pixels, pixels, pixels);
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setVisibility(View.INVISIBLE);
        LayoutParams textLayoutParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        textLayoutParams.gravity = Gravity.CENTER;
        mTextView.setLayoutParams(textLayoutParams);
    }

    public void setAdapter(ExpandableListView expandListView,BaseExpandableListAdapter adapter, AlphabetPositionListener positionListener,ArrayList<String> index) {
        if (positionListener == null)
            throw new IllegalArgumentException("AlphabetPositionListener is required");

        this.removeAllViews();
        mListView = expandListView;
        expandListView.setAdapter(adapter);
        this.positionListener = positionListener;
        this.indexArray = index;
        length  = index.size();
        initAlphabetLayout(mContext);
        this.addView(mListView);
        this.addView(alphabetLayout);
        this.addView(mTextView);
    }

    //初始化字母索引布局
    private void initAlphabetLayout(Context context) {
        alphabetLayout = new LinearLayout(context);
        alphabetLayout.setOrientation(LinearLayout.VERTICAL);
        LayoutParams alphabetLayoutParams = new LayoutParams(
                convertDIP2PX(20),
                ViewGroup.LayoutParams.FILL_PARENT);
        alphabetLayoutParams.gravity = Gravity.RIGHT|Gravity.CENTER_VERTICAL;
        alphabetLayoutParams.rightMargin = convertDIP2PX(7);
        alphabetLayoutParams.topMargin = convertDIP2PX(5);
        alphabetLayoutParams.bottomMargin=convertDIP2PX(8);
        alphabetLayout.setLayoutParams(alphabetLayoutParams);
        //alphabetLayout.setBackgroundColor(Color.parseColor("#E0E0E0"));
        alphabetLayout.setBackgroundResource(R.drawable.new_normal_corner);
       // String[] myIndex =  (String[]) indexArray.toArray();
       // final String[] alphabet = myIndex;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT
                ,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        params.gravity = Gravity.CENTER_HORIZONTAL;
        for (int i=0, count=indexArray.size(); i < count; i++) {
            TextView textView = new TextView(context);
            if(indexArray.get(i).equals("荐")){
                textView.setTextColor(Color.parseColor("#CC0000"));
            }else{
                textView.setTextColor(Color.argb(140, 105,115,125)); 
            }
            
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
            textView.getPaint().setFakeBoldText(true);
            textView.setText(indexArray.get(i));
            textView.setGravity(Gravity.CENTER);
           // textView.sets
            textView.setLayoutParams(params);
            textView.setTag(i+1);
            TextPaint tp =  textView.getPaint();
            tp.setFakeBoldText(true);
            alphabetLayout.addView(textView);
        }
        
        //字母索引添加touch事件
        alphabetLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //设置字母索引背景
                        alphabetLayout.setBackgroundResource(R.drawable.new_normal_corner);
                        int l = (int)(event.getY()/(alphabetLayout.getHeight()/length))-1;
                        if (l >= length) l = length-1;
                        else if (l < 0) l = 0;
                        int pos = positionListener.getPosition(indexArray.get(l));
                        if (pos != -1) {
                            mTextView.setText(indexArray.get(l));
                            mTextView.setVisibility(View.VISIBLE);
                            mHandler.removeCallbacks(mHideIndicator);
                            mHandler.postDelayed(mHideIndicator, indicatorDuration);
//                            mListView.requestFocusFromTouch();
                            mListView.setSelectedGroup(pos);
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        l = (int)((event.getY()+alphabetLayout.getHeight()/length/2)/(alphabetLayout.getHeight()/length))-1;
                        if (l >=length) l = length-1;
                        else if (l < 0) l = 0;
                        pos = positionListener.getPosition(indexArray.get(l));
                        if (pos != -1) {
                            mTextView.setText(indexArray.get(l));
                            mTextView.setVisibility(View.VISIBLE);
                            mHandler.removeCallbacks(mHideIndicator);
                            mHandler.postDelayed(mHideIndicator, indicatorDuration);
                            mListView.setSelectedGroup(pos);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        alphabetLayout.setBackgroundResource(R.drawable.new_normal_corner);
                        break;
                }
                return true;
            }
        });
    }

    public int convertDIP2PX(float dip) {
        return (int)(dip*screenDensity + 0.5f*(dip>=0?1:-1));
    }
    
    public static interface AlphabetPositionListener {
        public static final int UNKNOW = -1;
        public int getPosition(String letter);
    }
}
