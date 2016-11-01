package com.jafir.rxjavatest.wigdet;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.jafir.rxjavatest.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by jafir on 16/9/7.
 * 这个类 事件触发之后header会向上移
 */
public class TranslationSearchView extends ViewGroup {

    private Context context;

    /**
     * 偏移量（为header的高度）
     */
    private int offY;

    /**
     * 滚动的事件
     */
    private long duration = 500;

    /**
     * 差值器 默认为减速
     */
    private Interpolator interpolator = new DecelerateInterpolator();
    /**
     * 出去的属性动画
     */
    private ValueAnimator outAnimator;
    /**
     * 进入的属性动画
     */
    private ValueAnimator inAnimator;
    /**
     * header是否滚出去
     */
    private boolean isOpen = true;
    /**
     * 是否动画进行中
     */
    private boolean isRunning = false;
    private int density = (int) getResources().getDisplayMetrics().density;


    private List list = new ArrayList<>();

    private BaseAdapter adapter;
    private ListView listView;
    private EditText search;
    //    是否是灰色透明界面
    private boolean isApha = false;
    private int titlebarHeight;
    private LinearLayout linearLayout;


    public TranslationSearchView(Context context) {
        super(context);
        init(context);
    }


    public TranslationSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TranslationSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 计算出所有的childView的宽和高
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        View head = getChildAt(0);
        int height = head.getMeasuredHeight();


        View editText = getChildAt(1);
        editText.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 40 * density));

        View content = getChildAt(2);
        int cHeight = sizeHeight - height;
        content.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, cHeight - 40 * density));

        View list = getChildAt(3);
        list.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, cHeight - 40 * density));


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {


        View header = getChildAt(0);
        int width = header.getMeasuredWidth();
        int titlebarHeight = header.getMeasuredHeight();
        header.layout(0, 0 - offY, width, titlebarHeight - offY);

        View editText = getChildAt(1);
        int width1 = editText.getMeasuredWidth();
        int height1 = editText.getMeasuredHeight();
        editText.layout(0, titlebarHeight - offY, width1, titlebarHeight + height1);

        View content = getChildAt(2);
        int width2 = content.getMeasuredWidth();
        int height2 = editText.getMeasuredHeight();
        content.layout(0, titlebarHeight - offY + height1, width2, b);


        View list = getChildAt(3);
        int width3 = list.getMeasuredWidth();
        list.layout(0, titlebarHeight - offY + height1, width3, b);


        if (outAnimator == null) {

            outAnimator = ValueAnimator.ofInt(0, titlebarHeight);
            outAnimator.setInterpolator(new DecelerateInterpolator());
            outAnimator.setDuration(duration);
            outAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    offY = (int) animation.getAnimatedValue();
                    BigDecimal alpha = new BigDecimal(offY).divide(new BigDecimal(titlebarHeight * 3), 2, BigDecimal.ROUND_UP);
                    float a = alpha.floatValue();
                    linearLayout.setAlpha(a);

                    requestLayout();


                }
            });

            outAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    isRunning = true;

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    isRunning = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }

        if (inAnimator == null) {

            inAnimator = ValueAnimator.ofInt(titlebarHeight, 0);
            inAnimator.setInterpolator(new DecelerateInterpolator());
            inAnimator.setDuration(duration);
            inAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    offY = (int) animation.getAnimatedValue();
                    BigDecimal alpha = new BigDecimal(offY).divide(new BigDecimal(titlebarHeight * 3), 2, BigDecimal.ROUND_UP);
                    float a = alpha.floatValue();
                    linearLayout.setAlpha(a);
                    requestLayout();
                }
            });
            inAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    isRunning = true;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    isRunning = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
    }






    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        search = new EditText(context);
        search.setBackgroundResource(R.color.color1);
        search.setSingleLine(true);
        linearLayout = new LinearLayout(context);
        listView = new ListView(context);
        linearLayout.setBackgroundResource(R.color.color2);
        linearLayout.setAlpha(0);
        linearLayout.setVisibility(GONE);
        linearLayout.addView(listView);
        addView(search, 1);
        addView(linearLayout);
        search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen()) {
                    close();
                    isApha = true;
                }
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                处理搜索逻辑


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        linearLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isApha) {
                    open();
                    isApha = false;
                }
            }
        });


    }

    private void init(Context context) {
        this.context = context;

    }


    public void open() {
        if (isRunning) {
            return;
        }
        hideSoftInputFromWindow(search);
        linearLayout.setVisibility(GONE);
        if (inAnimator != null) {
            inAnimator.start();
            isOpen = true;
        }
    }


    public void close() {
        if (isRunning) {
            return;
        }

        linearLayout.setVisibility(VISIBLE);
        linearLayout.setAlpha(0);

        if (outAnimator != null) {
            outAnimator.start();
            isOpen = false;
        }


    }

    public void hideSoftInputFromWindow(EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Interpolator getInterpolator() {
        return interpolator;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}

