package com.sohu.mobile.fabulous;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by dafengluo on 2017/12/20.
 */

public class LikeContent extends RelativeLayout {

    private static final int EMPTY_RES_ID = 0;
    private Canvas canvas;
    private float positionX = 0;
    private float positionY = 0;
    private Context context;
    private int resId;

    public LikeContent(Context context) {
        this(context, null);
    }

    public LikeContent(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LikeContent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attributeSet, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.LikeContent, defStyleAttr, 0);
        resId = typedArray.getInt(R.styleable.LikeContent_vectorSrc, EMPTY_RES_ID);
        if(resId == 0){
            resId = R.drawable.ic_favorite_black_24dp;
        }
        typedArray.recycle();
        this.context = context;
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.canvas == null) {
            this.canvas = canvas;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(Utils.isFastDoubleClick()) {
            final ImageView likeIv = new ImageView(context);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = (int) positionX;
            params.topMargin = (int) positionY;
            likeIv.setLayoutParams(params);
            final LikeDrawable likeDrawable = new LikeDrawable(context, resId);
            likeIv.setImageDrawable(likeDrawable);
            addView(likeIv);
            ObjectAnimator translationYAnim = ObjectAnimator.ofFloat(likeIv, "translationY", likeIv.getY(), likeIv.getY() - 464f);
            ObjectAnimator scaleAnim = ObjectAnimator.ofFloat(likeIv, "ldf", 1.0F,  1.6F);
            scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
            {
                @Override
                public void onAnimationUpdate(ValueAnimator animation)
                {
                    float cVal = (Float) animation.getAnimatedValue();
                    likeIv.setAlpha(1.6f - cVal);
                    likeIv.setScaleX(cVal);
                    likeIv.setScaleY(cVal);
                }
            });
            scaleAnim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    removeView(likeIv);
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                }
            });
            AnimatorSet animSet = new AnimatorSet();
            animSet.play(translationYAnim).with(scaleAnim);
            animSet.setDuration(1200);
            animSet.start();
        }
        positionX = event.getX();
        positionY = event.getY();

        return super.onTouchEvent(event);
    }
}
