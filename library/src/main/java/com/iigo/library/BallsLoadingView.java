package com.iigo.library;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com / 729717222@qq.com
 * @github https://github.com/samlss
 * @description A four balls loading view.
 */
public class BallsLoadingView extends View {
    private final static int DEFAULT_SIZE = 250; //200 px
    private static final int DEFAULT_1ST_COLOR = Color.parseColor("#E15B64");
    private static final int DEFAULT_2ND_COLOR = Color.parseColor("#F47E60");
    private static final int DEFAULT_3RD_COLOR = Color.parseColor("#F8B26A");
    private static final int DEFAULT_4TH_COLOR = Color.parseColor("#ABBD81");

    private static final int POINTS_COUNT = 4;
    private static final int DEFAULT_POINT_RADIUS = 15;

    public final static int  ANIM_TYPE_TRANSLATE = 0;
    public final static int  ANIM_TYPE_SCALE = 1;

    private int mAnimType = ANIM_TYPE_TRANSLATE;
    private float mBallRadius = DEFAULT_POINT_RADIUS;

    private int mFirstBallColor  = DEFAULT_1ST_COLOR;
    private int mSecondBallColor = DEFAULT_2ND_COLOR;
    private int mThirdBallColor  = DEFAULT_3RD_COLOR;
    private int mFourthBallColor = DEFAULT_4TH_COLOR;

    private Path[] mBallPaths = new Path[POINTS_COUNT];
    private Paint[] mBallPaints = new Paint[POINTS_COUNT];
    private PointF[] mBallCenterPosList = new PointF[POINTS_COUNT];

    private Path mCalculatePath;
    private Matrix mCalculateMatrix;
    private AnimatorSet mAnimatorSet;
    private List<Animator> mValueAnimators;

    private float[] mAnimatorValues = new float[POINTS_COUNT];

    public BallsLoadingView(Context context) {
        this(context, null);
    }

    public BallsLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BallsLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttrs(attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BallsLoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        parseAttrs(attrs);
        init();
    }

    private void parseAttrs(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BallsLoadingView);
        mFirstBallColor = typedArray.getColor(R.styleable.BallsLoadingView_firstBallColor, DEFAULT_1ST_COLOR);
        mSecondBallColor = typedArray.getColor(R.styleable.BallsLoadingView_secondBallColor, DEFAULT_2ND_COLOR);
        mThirdBallColor = typedArray.getColor(R.styleable.BallsLoadingView_thirdBallColor, DEFAULT_3RD_COLOR);
        mFourthBallColor = typedArray.getColor(R.styleable.BallsLoadingView_fourthBallColor, DEFAULT_4TH_COLOR);
        mAnimType = typedArray.getInteger(R.styleable.BallsLoadingView_animType, ANIM_TYPE_TRANSLATE);
        mBallRadius = typedArray.getDimensionPixelOffset(R.styleable.BallsLoadingView_ballRadius, DEFAULT_POINT_RADIUS);
        typedArray.recycle();
    }

    private void init() {
        for (int i =0; i < POINTS_COUNT; i++){
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
            Path path = new Path();

            mBallPaints[i] = paint;
            mBallPaths[i]  = path;
        }

        mBallPaints[0].setColor(mFirstBallColor);
        mBallPaints[1].setColor(mSecondBallColor);
        mBallPaints[2].setColor(mThirdBallColor);
        mBallPaints[3].setColor(mFourthBallColor);

        mAnimatorSet = new AnimatorSet();
        mCalculatePath = new Path();
        mCalculateMatrix = new Matrix();

        mValueAnimators  = new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        int w = widthSpecSize;
        int h = heightSpecSize;

        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            w = DEFAULT_SIZE;
            h = DEFAULT_SIZE;
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            w = DEFAULT_SIZE;
            h = heightSpecSize;
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            w = widthSpecSize;
            h = DEFAULT_SIZE;
        }

        setMeasuredDimension(w, h);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        setupBalls(w, h);
    }

    private void setupBalls(int w, int h){
        release();

        if (w <= 0 && h <= 0){
            return;
        }

        for (Path path : mBallPaths){
            path.reset();
        }

        float pointsCenterY = 0;

        if (mAnimType == ANIM_TYPE_TRANSLATE) {
            pointsCenterY = h / 2 + h / 4;
        }else if (mAnimType == ANIM_TYPE_SCALE){
            pointsCenterY = h / 2;
        }

        float allGapLength = mBallRadius * 3;
        //4 is 4 points.
        float allLengthForPointAndGap = allGapLength + POINTS_COUNT * mBallRadius * 2;
        float _1stPointLeft = (w - allLengthForPointAndGap) / 2;

        mBallCenterPosList[0] = new PointF(_1stPointLeft + mBallRadius, pointsCenterY);
        mBallCenterPosList[1] = new PointF(_1stPointLeft + mBallRadius * 4, pointsCenterY);
        mBallCenterPosList[2] = new PointF(_1stPointLeft + mBallRadius * 7, pointsCenterY);
        mBallCenterPosList[3] = new PointF(_1stPointLeft + mBallRadius * 10, pointsCenterY);

        mBallPaths[0].addCircle(mBallCenterPosList[0].x, mBallCenterPosList[0].y, mBallRadius, Path.Direction.CW);
        mBallPaths[1].addCircle(mBallCenterPosList[1].x, mBallCenterPosList[1].y, mBallRadius, Path.Direction.CW);
        mBallPaths[2].addCircle(mBallCenterPosList[2].x, mBallCenterPosList[2].y, mBallRadius, Path.Direction.CW);
        mBallPaths[3].addCircle(mBallCenterPosList[3].x, mBallCenterPosList[3].y, mBallRadius, Path.Direction.CW);

        if (mAnimType == ANIM_TYPE_TRANSLATE) {
            setupTranslateAnimatorSet(h);
        }else if (mAnimType == ANIM_TYPE_SCALE){
            setupScaleAnimatorSet();
        }
    }

    private void setupTranslateAnimatorSet(int height){
        mValueAnimators.clear();

        for (int i = 0; i < POINTS_COUNT; i++){
            ValueAnimator valueAnimator;
            valueAnimator = ValueAnimator.ofFloat(0, -height / 2);

            final int finalI = i;
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    mAnimatorValues[finalI] = (float) valueAnimator.getAnimatedValue();
                    invalidate();
                }
            });

            valueAnimator.setInterpolator(new AccelerateInterpolator(0.2f));
            valueAnimator.setStartDelay(i * 180);
            valueAnimator.setDuration(300);
            valueAnimator.setRepeatCount(1);
            valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
            mValueAnimators.add(valueAnimator);
        }

       start();
    }

    private void setupScaleAnimatorSet(){
        mValueAnimators.clear();

        for (int i = 0; i < POINTS_COUNT; i++){
            ValueAnimator valueAnimator;
            valueAnimator = ValueAnimator.ofFloat(0, 1.3f);

            final int finalI = i;
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    mAnimatorValues[finalI] = (float) valueAnimator.getAnimatedValue();
                    invalidate();
                }
            });

            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.setStartDelay(i * 100);
            valueAnimator.setDuration(500);
            valueAnimator.setRepeatCount(1);
            valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
            mValueAnimators.add(valueAnimator);
        }

        start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < POINTS_COUNT; i++){
            mCalculateMatrix.reset();
            mCalculatePath.reset();

            if (mAnimType == ANIM_TYPE_TRANSLATE) {
                mCalculateMatrix.postTranslate(0, mAnimatorValues[i]);
            }else{
                mCalculateMatrix.postScale(mAnimatorValues[i], mAnimatorValues[i], mBallCenterPosList[i].x,
                        mBallCenterPosList[i].y);
            }
            mBallPaths[i].transform(mCalculateMatrix, mCalculatePath);
            canvas.drawPath(mCalculatePath, mBallPaints[i]);
        }
    }

    /**
     * Get the animation type {@link #ANIM_TYPE_SCALE}, {@link #ANIM_TYPE_TRANSLATE}.
     * */
    public int getAnimType() {
        return mAnimType;
    }

    /**
     * Set the animation type.
     * You can only set type of {@link #ANIM_TYPE_SCALE}, {@link #ANIM_TYPE_TRANSLATE}.
     * */
    public void setAnimType(int animType) {
        if (animType != ANIM_TYPE_SCALE
                && animType != ANIM_TYPE_TRANSLATE){
            Log.e("BallsLoadingView", "Please input a valid type.");
            return;
        }
        this.mAnimType = animType;
        setupBalls(getMeasuredWidth(), getMeasuredHeight());
    }

    /**
     * Get the first ball color.
     * */
    public int getFirstBallColor() {
        return mFirstBallColor;
    }

    /**
     * Set the first ball color.
     * */
    public void setFirstBallColor(int firstBallColor) {
        this.mFirstBallColor = firstBallColor;
        this.mBallPaints[0].setColor(mFirstBallColor);
        postInvalidate();
    }

    /**
     * Get the second ball color.
     * */
    public int getSecondBallColor() {
        return mSecondBallColor;
    }

    /**
     * Set the second ball color.
     * */
    public void setSecondBallColor(int secondBallColor) {
        this.mSecondBallColor = secondBallColor;
        mBallPaints[1].setColor(mSecondBallColor);
        postInvalidate();
    }

    /**
     * Get the third ball color.
     * */
    public int getThirdBallColor() {
        return mThirdBallColor;
    }

    /**
     * Set the third ball color.
     * */
    public void setThirdBallColor(int thirdBallColor) {
        this.mThirdBallColor = thirdBallColor;
        mBallPaints[2].setColor(mThirdBallColor);
        postInvalidate();
    }

    /**
     * Get the fourth ball color.
     * */
    public int getFourthBallColor() {
        return mFourthBallColor;
    }

    /**
     * Set the fourth ball color.
     * */
    public void setFourthBallColor(int fourthBallColor) {
        this.mFourthBallColor = fourthBallColor;
        mBallPaints[3].setColor(mFourthBallColor);
        postInvalidate();
    }

    /**
     * Set the ball radius in pixel.
     * */
    public void setBallRadius(float ballRadius) {
        this.mBallRadius = ballRadius;
        setupBalls(getMeasuredWidth(), getMeasuredHeight());
    }

    /**
     * Get the ball radius.
     * */
    public float getBallRadius() {
        return mBallRadius;
    }

    /**
     * Start animation.
     * */
    public void start(){
        if (mAnimatorSet != null && !mAnimatorSet.isRunning()){
            for (int i = 0; i < POINTS_COUNT; i++){
                mAnimatorValues[i] = 0;
                postInvalidate();
            }

            mAnimatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mAnimatorSet.setStartDelay(50);
                    mAnimatorSet.playTogether(mValueAnimators);
                    mAnimatorSet.start();
                }
            });
            mAnimatorSet.playTogether(mValueAnimators);
            mAnimatorSet.start();
        }
    }

    /**
     * Stop animation.
     * */
    public void stop(){
        if (mAnimatorSet != null && mAnimatorSet.isRunning()){
            mAnimatorSet.removeAllListeners();
            mAnimatorSet.cancel();

            for (Animator animator : mValueAnimators){
                animator.cancel();
            }
        }
    }

    /**
     * When you do not need to use this, you should better to call this method to release.
     * */
    public void release(){
        stop();
        for (Animator animator : mValueAnimators){
            ((ValueAnimator)animator).removeAllUpdateListeners();
        }

        mValueAnimators.clear();
    }
}
