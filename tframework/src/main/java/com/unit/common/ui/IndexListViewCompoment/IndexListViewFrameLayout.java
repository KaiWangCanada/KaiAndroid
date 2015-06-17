package com.unit.common.ui.IndexListViewCompoment;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.unit.common.R;
import com.unit.common.utils.DisplayUtils;

import java.util.List;

//带右边快速选择的索引条

public class IndexListViewFrameLayout extends FrameLayout {
    private Context mContext;
    
    private List<String> alphabetArray;//索引值存放数组
    private int alphabetLength;  //字母索引大小
    
    //字母索引背景与位置参数
    private int sourceId= R.drawable.new_normal_corner;
    private float rightMargin=5;
    private float topMargin=10;
    private float bottomMargin=10; 
    private float alphabetSize=18;
    
    private ListView mListView;
    public LinearLayout alphabetLayout;
    private TextView mTextView;

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

    public IndexListViewFrameLayout (Context context) {
        super(context);
        mContext = context;
        //init(context);
    }
    
    public IndexListViewFrameLayout (Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        //init(context);
    }
    
    /**
     * 初始化字母索引的背景与位置并初始化
     * @param sourceId     索引字母的背景
     * @param alphabetSize 字母大小 
     * @param rightMargin  离屏幕右边距离
     * @param topMargin    距离listview头部距离
     * @param bottomMargin 距离listview底部距离 
     */
    public void setAlphabetParamAndInit(int sourceId,float alphabetSize,float rightMargin,float topMargin,float bottomMargin){
        this.sourceId=sourceId;
        this.alphabetSize=alphabetSize;
        this.rightMargin=rightMargin;
        this.topMargin=topMargin;
        this.bottomMargin=bottomMargin;
        init(mContext);
    }
    
    private void init(Context mContext) {
        screenDensity = mContext.getResources().getDisplayMetrics().density;
        mHandler = new Handler();
        
//        initAlphabetLayout(mContext);
        
        //中间显示字母的布局
        mTextView = new TextView(mContext);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,50);
        mTextView.setTextColor(Color.argb(150, 150, 150, 150));
        //mTextView.setBackgroundColor(Color.argb(200, 0, 0, 0));
        mTextView.setMinWidth(DisplayUtils.convertPX2DIP(mContext,46));
        mTextView.setMinHeight(DisplayUtils.convertPX2DIP(mContext,46));
        int pixels = DisplayUtils.convertPX2DIP(mContext,4);
        mTextView.setPadding(pixels, pixels, pixels, pixels);
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setVisibility(View.INVISIBLE);
        LayoutParams textLayoutParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        textLayoutParams.gravity = Gravity.CENTER;
        mTextView.setLayoutParams(textLayoutParams);
    }


    public void setAdapter(ListView listView,BaseAdapter adapter, AlphabetPositionListener positionListener,List<String> alphabet) {
        if (positionListener == null)
            throw new IllegalArgumentException("AlphabetPositionListener is required");

        this.removeAllViews();
        if(null!=listView && null!=adapter && null!=positionListener){
            mListView = listView;
            mListView.setAdapter(adapter);
            this.addView(mListView);
            if(alphabet!=null && !alphabet.isEmpty()){
                this.alphabetArray = alphabet;
                this.alphabetLength = alphabet.size();
                initAlphabetLayout(mContext);
                this.positionListener = positionListener;
                this.addView(alphabetLayout);
                this.addView(mTextView);
            }
        }
    }

    //初始化字母索引布局
    private void initAlphabetLayout(Context context) {
        alphabetLayout = new LinearLayout(context);
        alphabetLayout.setBackgroundResource(sourceId);
        alphabetLayout.setOrientation(LinearLayout.VERTICAL);
        LayoutParams alphabetLayoutParams = new LayoutParams(
                DisplayUtils.convertDIP2PX(mContext,18),
                ViewGroup.LayoutParams.FILL_PARENT);
        
        
        alphabetLayoutParams.gravity = Gravity.RIGHT|Gravity.CENTER_VERTICAL;
        alphabetLayoutParams.rightMargin = DisplayUtils.convertDIP2PX(mContext, (int)rightMargin);
        alphabetLayoutParams.topMargin =  DisplayUtils.convertDIP2PX(mContext, (int)topMargin);
        alphabetLayoutParams.bottomMargin= DisplayUtils.convertDIP2PX(mContext, (int)bottomMargin);
        alphabetLayout.setLayoutParams(alphabetLayoutParams);
        
//        final String[] alphabet = new String[]{"A","B","C","D","E","F","G","H","I","J"
//                ,"K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT
                ,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        params.gravity = Gravity.CENTER_HORIZONTAL;
        for (int i=0, count=alphabetArray.size(); i < count; i++) {
            TextView textView = new TextView(context);
            textView.setTextColor(Color.argb(150, 150, 150, 150));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,alphabetSize);
            textView.getPaint().setFakeBoldText(true);
            textView.setText(alphabetArray.get(i));
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(params);
            textView.setTag(i+1);
            alphabetLayout.addView(textView);
        }
        
        //字母索引添加touch事件
        alphabetLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //设置字母索引背景
                        alphabetLayout.setBackgroundResource(sourceId);
                        int l = (int)(event.getY()/(alphabetLayout.getHeight()/alphabetLength))-1;
                        if (l > alphabetLength) l = alphabetLength-1;
                        else if (l < 0) l = 0;
                        int pos = positionListener.getPosition(alphabetArray.get(l));
                        if (pos != -1) {
                            mTextView.setText(alphabetArray.get(l));
                            mTextView.setVisibility(View.VISIBLE);
                            mHandler.removeCallbacks(mHideIndicator);
                            mHandler.postDelayed(mHideIndicator, indicatorDuration);
//                            mListView.requestFocusFromTouch();
                            mListView.setSelection(pos);
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        l = (int)((event.getY()+alphabetLayout.getHeight()/alphabetLength/2)/(alphabetLayout.getHeight()/alphabetLength))-1;
                        if (l >=alphabetLength) l = alphabetLength-1;
                        else if (l < 0) l = 0;
                        pos = positionListener.getPosition(alphabetArray.get(l));
                        if (pos != -1) {
                            mTextView.setText(alphabetArray.get(l));
                            mTextView.setVisibility(View.VISIBLE);
                            mHandler.removeCallbacks(mHideIndicator);
                            mHandler.postDelayed(mHideIndicator, indicatorDuration);
//                            mListView.requestFocusFromTouch();
                            mListView.setSelection(pos);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        alphabetLayout.setBackgroundResource(sourceId);
                        break;
                }
                return true;
            }
        });
    }

//    public int convertDIP2PX(float dip) {
//        return (int)(dip*screenDensity + 0.5f*(dip>=0?1:-1));
//    }
    
    //转换px为dip
  /*  public int convertPX2DIP(Context context, int px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(px/scale + 0.5f*(px>=0?1:-1));
    }
    */
    public static interface AlphabetPositionListener {
        public static final int UNKNOW = -1;
        public int getPosition(String letter);
    }
}
