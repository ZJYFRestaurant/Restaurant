package com.promo.zjyfrestaurant.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jch.lib.util.DisplayUtil;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.bean.DishBean;
import com.promo.zjyfrestaurant.shoppingcart.ShoppingCart;

/**
 * 菜品添加组件。
 * TODO: document your custom view class.
 */
public class AddDealPicker extends LinearLayout implements View.OnClickListener {
    /**
     * 更新数据是否通知cart跟新。
     */
    private boolean notifyCartFlag = false;

    public boolean isNotifyCartFlag() {
        return notifyCartFlag;
    }

    public void setNotifyCartFlag(boolean notifyCartFlag) {
        this.notifyCartFlag = notifyCartFlag;
    }

    /**
     * Holds pairs of adjacent attribute data: attribute name followed by its value.
     *
     * @hide
     */
    @ViewDebug.ExportedProperty(category = "attributes", hasAdjacentMapping = true)
    public String[] mAttributes;

    /**
     * Maps a Resource id to its name.
     */
    private static SparseArray<String> mAttributeMap;
    /**
     * When set to true, this view will save its attribute data.
     *
     * @hide
     */
    public static boolean mDebugViewAttributes = false;
    /**
     * 按钮图片默认间距。 *
     */
    private final static int BETWEEN_PAD = 15;
    /**
     * 默认字体大小。
     */
    private static final int TEXT_SIZE = 15;
    /**
     * 默认字体颜色。
     */
    private static final int TEXT_COLOR = Color.BLACK;
    /**
     * img
     */
    private static final String DEF_TEXT = "0";

    private static final float IMG_SIZE = 50;

    /**
     * 减
     */
    private ImageView reduceImg = null;
    /**
     * 加
     */
    private ImageView plusImg = null;

    private TextView numTv;

    /**
     * 左边减按钮. *
     */
    private Drawable reduceDraw = null;
    /**
     * 右边加按钮. *
     */
    private Drawable plusImgDraw = null;
    /**
     * 数字 *
     */
    private String text = null;

    private float textSize = 0;

    private int textColor = 0;

    private float imgSize = 0;

    /**
     * 内部组件间距。
     */
    private float between_pad;
    /**
     * 对应添加的菜品。 *
     */
    private DishBean dishBean = null;

    private ShoppingCart cart = null;


    public AddDealPicker(Context context) {
        super(context);
        init(null, 0);
    }

    public AddDealPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }


    private void init(AttributeSet attrs, int defStyle) {
        setGravity(Gravity.CENTER_VERTICAL);        //子控件垂直居中对其。
        setOrientation(HORIZONTAL);         //子空间平行布局。

        text = DEF_TEXT;
        between_pad = BETWEEN_PAD;
        textSize = TEXT_SIZE;
        textColor = TEXT_COLOR;
        imgSize = IMG_SIZE;


        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.AddDealPicker, defStyle, 0);

        if (mDebugViewAttributes) {
            saveAttributeData(attrs, a);
        }

        final int N = a.getIndexCount();
        for (int i = 0; i < N; i++) {
            int attr = a.getIndex(i);
            switch (attr) {

                case R.styleable.AddDealPicker_left_img: {
//                    overScrollMode = a.getInt(attr, OVER_SCROLL_IF_CONTENT_SCROLLS);
                    reduceDraw = a.getDrawable(attr);
                    break;
                }
                case R.styleable.AddDealPicker_right_img: {
                    plusImgDraw = a.getDrawable(attr);
                    break;
                }

                case R.styleable.AddDealPicker_drawable_padding: {

                    between_pad = a.getDimension(attr, BETWEEN_PAD);
                }
                case R.styleable.AddDealPicker_text: {
                    text = a.getString(attr);
                    break;
                }

                case R.styleable.AddDealPicker_text_color: {
                    textColor = a.getColor(attr, TEXT_COLOR);
                    break;
                }

                case R.styleable.AddDealPicker_text_size: {

                    textSize = a.getDimensionPixelSize(attr, (int) textSize);
                    break;
                }

                case R.styleable.AddDealPicker_img_size: {
                    imgSize = a.getDimension(attr, IMG_SIZE);
                }

            }
        }

        a.recycle();

        cart = ShoppingCart.newInstance();

        addView();

    }

    /**
     * 获取菜品实例数据
     *
     * @return
     */
    public DishBean getDishBean() {
        return dishBean;
    }

    /**
     * 设置菜品实例数据。
     *
     * @param dishBean
     */
    public void setDishBean(DishBean dishBean) {
        this.dishBean = dishBean;

        if (this.dishBean != null) {
            int num = ShoppingCart.newInstance().getDishNum(dishBean.getId());
            numTv.setText(String.valueOf(num));
            this.dishBean.setNum(num);
        }
        checkNum();
    }

    private void saveAttributeData(AttributeSet attrs, TypedArray a) {
        int length = ((attrs == null ? 0 : attrs.getAttributeCount()) + a.getIndexCount()) * 2;
        mAttributes = new String[length];

        int i = 0;
        if (attrs != null) {
            for (i = 0; i < attrs.getAttributeCount(); i += 2) {
                mAttributes[i] = attrs.getAttributeName(i);
                mAttributes[i + 1] = attrs.getAttributeValue(i);
            }

        }

        SparseArray<String> attributeMap = getAttributeMap();
        for (int j = 0; j < a.length(); ++j) {
            if (a.hasValue(j)) {
                try {
                    int resourceId = a.getResourceId(j, 0);
                    if (resourceId == 0) {
                        continue;
                    }

                    String resourceName = attributeMap.get(resourceId);
                    if (resourceName == null) {
                        resourceName = a.getResources().getResourceName(resourceId);
                        attributeMap.put(resourceId, resourceName);
                    }

                    mAttributes[i] = resourceName;
                    mAttributes[i + 1] = a.getText(j).toString();
                    i += 2;
                } catch (Resources.NotFoundException e) {
                    // if we can't get the resource name, we just ignore it
                }
            }
        }
    }

    private static SparseArray<String> getAttributeMap() {
        if (mAttributeMap == null) {
            mAttributeMap = new SparseArray<String>();
        }
        return mAttributeMap;
    }

    /**
     * 添加view。
     */
    private void addView() {
        reduceImg = new ImageView(getContext());
        DisplayUtil.setBackground(reduceImg, reduceDraw);
        LayoutParams rightParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rightParams.rightMargin = (int) between_pad;
        rightParams.width = (int) imgSize;
        rightParams.height = (int) imgSize;
        reduceImg.setLayoutParams(rightParams);
        reduceImg.setOnClickListener(this);
        reduceImg.setId(R.id.dealPickleftImgId);
        addView(reduceImg);

        numTv = new TextView(getContext());
        numTv.setTextColor(textColor);
        numTv.setTextSize(textSize);
        numTv.setText(text);
        LayoutParams textParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        numTv.setLayoutParams(textParams);
        addView(numTv);

        plusImg = new ImageView(getContext());
        DisplayUtil.setBackground(plusImg, plusImgDraw);
        LayoutParams left_params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        left_params.leftMargin = (int) between_pad;
        left_params.width = (int) imgSize;
        left_params.height = (int) imgSize;
        plusImg.setLayoutParams(left_params);
        plusImg.setOnClickListener(this);
        plusImg.setId(R.id.dealPickrightImgId);
        addView(plusImg);


    }

    /**
     * 获得结果数据。
     *
     * @return
     */
    public String getText() {

        String numStr = null;
        if (numTv != null)
            numStr = numTv.getText().toString();
        return numStr;
    }

    /**
     * 设置结果数据。
     *
     * @param numStr
     */
    public void setText(String numStr) {
        if (numTv != null)
            numTv.setText(numStr);
    }


    public int getBetween_pad() {
        return (int) between_pad;
    }

    public void setBetween_pad(int between_pad) {
        this.between_pad = between_pad;
    }

    public int getTextColor() {

        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        numTv.setTextColor(textColor);
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(Integer textSize) {
        this.textSize = textSize;
        numTv.setTextSize(textSize);
    }

    public Drawable getPlusImgDraw() {
        return plusImgDraw;
    }

    public void setPlusImgDraw(Drawable plusImgDraw) {
        this.plusImgDraw = plusImgDraw;
        DisplayUtil.setBackground(plusImg, plusImgDraw);
    }

    public Drawable getReduceDraw() {
        return reduceDraw;
    }

    public void setReduceDraw(Drawable reduceDraw) {
        this.reduceDraw = reduceDraw;
        DisplayUtil.setBackground(reduceImg, reduceDraw);
    }

    public TextView getNumTv() {
        return numTv;
    }

    public void setNumTv(TextView numTv) {
        this.numTv = numTv;
    }

    public ImageView getPlusImg() {
        return plusImg;
    }

    public void setPlusImg(ImageView plusImg) {
        this.plusImg = plusImg;
    }

    public ImageView getReduceImg() {
        return reduceImg;
    }

    public void setReduceImg(ImageView reduceImg) {
        this.reduceImg = reduceImg;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.dealPickleftImgId: {
                subtractionTv();    //数据减1
                break;
            }
            case R.id.dealPickrightImgId: {
                additionTv();    //数据加1
                break;
            }

        }
        checkNum();
    }

    /**
     * 显示数据加1。
     */
    private void additionTv() {
        if (dishBean != null) {
            numTv.setText(String.valueOf(ShoppingCart.newInstance().addDish(dishBean)));
            if (notifyCartFlag) {
                dishBean.dataChanged();
                dishBean.nodifyDataChanged();       //更新购物车订购数据。
            }
        }
    }

    /**
     * Checking dish number. if dish's number is 0, hide subtraction view and number view. viceverse.
     */
    private void checkNum() {

        if (ShoppingCart.newInstance().getDishNum(dishBean.getId()) == 0) {
            reduceImg.setVisibility(INVISIBLE);
            numTv.setVisibility(INVISIBLE);
        } else {
            reduceImg.setVisibility(VISIBLE);
            numTv.setVisibility(VISIBLE);
        }
    }

    /**
     * 显示数据减1。
     */
    private void subtractionTv() {
        if (dishBean != null) {
            ShoppingCart shoppingCart = ShoppingCart.newInstance();
            if (shoppingCart.getDishNum(dishBean.getId()) > 0)      //数值不能为负
                numTv.setText(String.valueOf(shoppingCart.reduceDish(dishBean)));
            if (notifyCartFlag) {
                dishBean.dataChanged();
                dishBean.nodifyDataChanged();       //更新购物车订购数据。
            }
        }
    }
}