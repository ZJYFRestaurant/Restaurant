package com.jch.lib.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.jch.lib.R;

/**
 * Created by ACER on 2014/11/27.
 * <p/>
 * 消失提示对话框。可在一定时间能消失。相当于toast的作用。
 */
public class MyMsgAlertDialog extends Dialog {

    private static final int ENDMSG_WHAT = 0x5256;
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            endDismissAnimal();
        }
    };
    private View contentLayoutView;
    private Context mContext;
    private TextView mTv;
    private int animalDuration = 500;
    private int mShowTime = 1500;
    private String msgStr;

    public MyMsgAlertDialog(Context context) {

        super(context, R.style.dialog_tran);
        init(context);
    }

    public MyMsgAlertDialog(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    protected MyMsgAlertDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        contentLayoutView = View.inflate(context, R.layout.msg_dialog_layout, null);
        setContentView(contentLayoutView);
    }

    public int getShowTime() {
        return mShowTime;
    }

    public void setShowTime(int mShowTime) {
        this.mShowTime = mShowTime;
    }

    public int getAnimalDuration() {
        return animalDuration;
    }

    public void setAnimalDuration(int animalDuration) {
        this.animalDuration = animalDuration;
    }

    public View getContentLayoutView() {
        return contentLayoutView;
    }

    public void setContentLayoutView(View contentLayoutView) {
        this.contentLayoutView = contentLayoutView;
    }

    public void setMsg(String msg) {
        this.msgStr = msg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
//        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTv = (TextView) contentLayoutView.findViewById(R.id.msg_dialog_tv);
        if (msgStr != null && !msgStr.equals("")) {
            mTv.setText(msgStr);
        } else {
            throw new NullPointerException("message 不能为空。");
        }
        startShowAnimal();

        handler.sendEmptyMessageDelayed(ENDMSG_WHAT, mShowTime);
    }

    /**
     * 开始动画。
     */
    private void startShowAnimal() {

        Animation alphaAnimal = new AlphaAnimation(0, 1);
        alphaAnimal.setInterpolator(new LinearInterpolator());
        alphaAnimal.setFillEnabled(true);
        alphaAnimal.setFillBefore(true);
        alphaAnimal.setFillAfter(true);
        alphaAnimal.setDuration(animalDuration);

        Animation scaleAnimal = new ScaleAnimation(0, 1.0f, 0, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimal.setInterpolator(new OvershootInterpolator());
        scaleAnimal.setFillEnabled(true);
        scaleAnimal.setFillBefore(true);
        scaleAnimal.setFillAfter(true);
        scaleAnimal.setDuration(animalDuration);


        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(alphaAnimal);
        animationSet.addAnimation(scaleAnimal);
        contentLayoutView.startAnimation(animationSet);
    }

    private void endDismissAnimal() {

        Animation alphaAnimal = new AlphaAnimation(1, 0);
        alphaAnimal.setInterpolator(new LinearInterpolator());
        alphaAnimal.setFillEnabled(true);
        alphaAnimal.setFillBefore(true);
        alphaAnimal.setFillAfter(true);
        alphaAnimal.setDuration(animalDuration);

        Animation scaleAnimal = new ScaleAnimation(1.0f, 0, 1.0f, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimal.setInterpolator(new AccelerateInterpolator());
        scaleAnimal.setFillEnabled(true);
        scaleAnimal.setFillBefore(true);
        scaleAnimal.setFillAfter(true);
        scaleAnimal.setDuration(animalDuration);


        AnimationSet animationSet = new AnimationSet(false);
        animationSet.setAnimationListener(new EndAnimalListener());
        animationSet.addAnimation(alphaAnimal);
        animationSet.addAnimation(scaleAnimal);

        contentLayoutView.startAnimation(animationSet);
    }

    /**
     * 动画消失监听事件。
     */
    private class EndAnimalListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            MyMsgAlertDialog.this.dismiss();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }


}
