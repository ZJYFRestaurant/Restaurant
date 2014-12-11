package com.jch.lib.view;

import android.animation.LayoutTransition;
import android.annotation.TargetApi;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;

/**
 * Created by ACER on 2014/11/10.
 */
public class SlideGridViewController extends DataSetObserver implements View.OnClickListener {

    private Context context;

    private SlideGridView slideGridView;
    private SlideGridViewBaseAdapter mAdatper;
    private boolean animalRunning = false;
    /**
     * grade Inflate.
     */
    private LayoutInflater mInflater;

    private int currentItem = -1;
    //用于实现显示subItem的隐藏效果.
    private int oldClickItem = currentItem;

    private ArrayList<LinearLayout> tagViews = new ArrayList<LinearLayout>();
    //存储所有的可点击的item.
    private ArrayList<View> itemViews = new ArrayList<View>();

    public SlideGridViewController(Context context, SlideGridView slideGridView) {

        this.context = context;
        this.slideGridView = slideGridView;
        addContentAnim();
    }

    @Override
    public void onChanged() {
        super.onChanged();
        onInvalidated();
        layoutItem();

    }

    @Override
    public void onInvalidated() {
        super.onInvalidated();
        if (mAdatper == null) {
            mAdatper = slideGridView.getmAdapter();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void addContentAnim() {
        LayoutTransition transitioner = new LayoutTransition();
        transitioner.setDuration(200);
        transitioner.addTransitionListener(new LayoutTransition.TransitionListener() {

            @Override
            public void startTransition(LayoutTransition transition,
                                        ViewGroup container, View view, int transitionType) {
                animalRunning = true;
            }

            @Override
            public void endTransition(LayoutTransition transition,
                                      ViewGroup container, View view, int transitionType) {
                animalRunning = false;
            }
        });

        slideGridView.setLayoutTransition(transitioner);
    }

    /**
     * 将要显示的项添加到slidGridView中。
     */
    private void layoutItem() {
        int itemNum = mAdatper.getCount();
        int columNum = slideGridView.getColulmeNum();
        int lineNum = (int) Math.ceil((double) itemNum / (double) columNum);

        int index = 0;
        for (int i = 0; i < lineNum; i++) {
            LinearLayout row = getRowLayout();
            LinearLayout rowTagView = getRowTagLayout();
            for (int t = 0; t < columNum; t++) {
                if (index < itemNum) {
                    View view = mAdatper.getView(index, null, null);
                    View divideView = getHorizontalDivide(context, t, columNum - 1);
                    LinearLayout.LayoutParams itemParam = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                    view.setLayoutParams(itemParam);
                    view.setTag(rowTagView);

                    itemViews.add(view);
                    view.setOnClickListener(this);

                    row.addView(view);
                    if (divideView != null)     //添加横向间距。
                        row.addView(divideView);
                }
                index++;
            }
            if (i != 0) {           //添加分割线。
                slideGridView.addView(getVerticalDivide());
            }
            slideGridView.addView(row);
            slideGridView.addView(rowTagView);
            tagViews.add(rowTagView);

        }
    }

    /**
     * 布局横向间距。
     *
     * @param context
     * @param index
     * @param columeNum
     * @return
     */
    private View getHorizontalDivide(Context context, int index, int columeNum) {

        if (index < columeNum) {

            View view = new View(context);
            view.setLayoutParams(new LinearLayout.LayoutParams((int) slideGridView.getDivideHeight(), ViewGroup.LayoutParams.MATCH_PARENT));
            view.setBackgroundColor(slideGridView.getDivideColor());
            return view;
        } else
            return null;


    }

    private View getVerticalDivide() {
        View view = new View(context);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) slideGridView.getDivideHeight()));
        view.setBackgroundColor(slideGridView.getDivideColor());
        return view;
    }

    /**
     * 每一行。
     *
     * @return
     */
    private LinearLayout getRowLayout() {

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setWeightSum(slideGridView.getColulmeNum());
        linearLayout.setLayoutParams(params);
        return linearLayout;
    }

    /**
     * 子级View 的layout。
     */
    private LinearLayout getRowTagLayout() {

        LinearLayout frameLayout = new LinearLayout(context);
        frameLayout.setBackgroundColor(slideGridView.getSubBg());
//        frameLayout.setBackgroundColor(context.getResources().getColor(R.color.btn_press_color));
        frameLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        frameLayout.setOrientation(LinearLayout.VERTICAL);
        frameLayout.setVisibility(View.GONE);

        return frameLayout;
    }

    @Override
    public void onClick(View v) {

        if (itemViews.contains(v) && !animalRunning) {
            int clickPosition = itemViews.indexOf(v);
            controlTagView(clickPosition, (LinearLayout) v.getTag());
        }

    }

    /**
     * 通过tagView控制subView的隐藏和显示。
     *
     * @param index   item的序列.
     * @param tagView tagVIew
     */
    private void controlTagView(int index, LinearLayout tagView) {

        for (LinearLayout view : tagViews) {
            view.removeAllViews();
            view.setVisibility(View.GONE);
        }

        if (oldClickItem != index) {     //当前item没有被点击，显示更新

            ViewGroup tagContentView = layoutSubView(index);
            if (tagContentView.getChildCount() > 0) {
                tagView.addView(getIndexTagView(index));    //添加当前项表示箭头。
            }
            tagView.addView(tagContentView);
            tagView.setVisibility(View.VISIBLE);
            oldClickItem = index;
        } else { //当前的item已经被点击。隐藏当前的item。
            oldClickItem = -1;
        }

    }

    /**
     * 绘制当前指示图标.
     *
     * @param position
     * @return
     */
    private LinearLayout getIndexTagView(int position) {
        LinearLayout indexTagView = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        indexTagView.setOrientation(LinearLayout.HORIZONTAL);
        indexTagView.setLayoutParams(params);
        int curIdx = position % slideGridView.getColulmeNum();
        for (int i = 0; i < slideGridView.getColulmeNum(); i++) {

            View view = new View(context);
            view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            if (i == curIdx) {  //绘制当前指示箭头.

                view.setBackgroundResource(slideGridView.getArroIndexIds());
            } else {     //绘制普通下划线.

                view.setBackgroundResource(slideGridView.getArroIndexIdsNomal());

            }
            indexTagView.addView(view);
        }
        return indexTagView;
    }

    /**
     * 布局子分类.
     *
     * @param position 主分类的序列号.
     * @return
     */
    private ViewGroup layoutSubView(int position) {

        int subCount = mAdatper.getSubItemCountByPosition(position);
        TableLayout tbLayout = new TableLayout(context);
        tbLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        int subLineCount = (int) (Math.ceil((double) subCount / (double) slideGridView.getSubColumNum()));
        int subIndex = 0;
        for (int i = 0; i < subLineCount; i++) {
            TableRow tr = new TableRow(context);
            tr.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tr.setWeightSum(slideGridView.getSubColumNum());
            for (int j = 0; j < slideGridView.getSubColumNum(); j++) {
                if (subIndex < subCount) {
                    FrameLayout subItemFl = new FrameLayout(context);
                    TableRow.LayoutParams subItemParam = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                    subItemParam.gravity = Gravity.CENTER;
                    subItemFl.setLayoutParams(subItemParam);
                    View itemView = mAdatper.getSubItemView(position, subIndex);
                    subItemFl.addView(itemView);
                    //TODO 子view添加监听。
                    subItemFl.setOnClickListener(new SubClickListener(position, subIndex));
                    tr.addView(subItemFl);
                }
                subIndex++;
            }
            tbLayout.addView(tr);
        }

        return tbLayout;
    }

    private class SubClickListener implements View.OnClickListener {

        private int position;
        //子分类的position.
        private int subPosition;

        public SubClickListener(int position, int subPosition) {
            this.position = position;
            this.subPosition = subPosition;
        }

        @Override
        public void onClick(View v) {
            if (slideGridView.getItemClickListener() != null) {
                slideGridView.getItemClickListener().onSubItemClick(position, subPosition, v);
            }
        }
    }


}
