package com.promo.zjyfrestaurant;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {

    private View mView = null;
    private RelativeLayout mHeadView = null;
    /**
     * 主页的内容类。
     */
    private FrameLayout mContainerView = null;
    private Button backBtn = null;
    private TextView titleTv = null;


    public BaseFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_base, container, false);
        init(mView);

        View fragContainer = addContentView(inflater);
        fragContainer.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        if (fragContainer != null)      //添加fragment的内容.
            mContainerView.addView(fragContainer);

        return mView;
    }

    private void init(View view) {

        mContainerView = (FrameLayout) view.findViewById(R.id.base_frag_content);
        backBtn = (Button) view.findViewById(R.id.back_btn);
        backBtn.setVisibility(View.GONE);
        titleTv = (TextView) view.findViewById(R.id.title_tv);
        mHeadView = (RelativeLayout) view.findViewById(R.id.title_rl);
    }

    protected void hideHeadView() {
        mHeadView.setVisibility(View.GONE);
    }

    protected void hideBackBtn() {
        backBtn.setVisibility(View.GONE);
    }

    protected abstract View addContentView(LayoutInflater inflater);

    /**
     * 添加title右边的组件.
     *
     * @param rightItem
     */
    protected void addRightItem(View rightItem) {

        if (rightItem != null) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            rightItem.setLayoutParams(params);
            mHeadView.addView(rightItem);

        }
    }
}
