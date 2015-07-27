package com.mmfcommon.event;

import android.os.Handler;
import android.os.Message;
import com.unit.common.utils.LogOutputUtils;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 黄东鲁 on 15/7/1.
 */
public class ClickCountManger {

  private int count = 0;
  private long firstTime = 0;
  private Timer delayTimer;
  private Handler handler;
  private TimerTask task;
  private long interval = 500;

  private OnClickCountResult onClickCountResult;


  public ClickCountManger() {
    handler = new Handler() {
      @Override public void handleMessage(Message msg) {
        LogOutputUtils.d(">>>>", "连续点击了" + count + "次");

        if (onClickCountResult != null) {
          onClickCountResult.onResult(count);
        }


        delayTimer.cancel();
        count = 0;
        super.handleMessage(msg);
      }
    };
  }


  public void destory() {
    task.cancel();
    delayTimer.cancel();
  }


  // 延迟时间是连击的时间间隔有效范围
  private void delay() {
    if (task != null) task.cancel();

    task = new TimerTask() {
      @Override public void run() {
        Message message = new Message();
        // message.what = 0;
        handler.sendMessage(message);
      }
    };
    delayTimer = new Timer();
    delayTimer.schedule(task, interval);
  }


  public void click() {
    long secondTime = System.currentTimeMillis();
    // 判断每次点击的事件间隔是否符合连击的有效范围
    // 不符合时，有可能是连击的开始，否则就仅仅是单击
    if (secondTime - firstTime <= interval) {
      ++count;
    } else {
      count = 1;
    }
    // 延迟，用于判断用户的点击操作是否结束
    delay();
    firstTime = secondTime;
  }

  public OnClickCountResult getOnClickCountResult() {
    return onClickCountResult;
  }

  public void setOnClickCountResult(OnClickCountResult onClickCountResult) {
    this.onClickCountResult = onClickCountResult;
  }

  public interface OnClickCountResult {
    void onResult(int count);
  }
}
