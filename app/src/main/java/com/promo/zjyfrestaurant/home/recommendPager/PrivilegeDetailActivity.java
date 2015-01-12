package com.promo.zjyfrestaurant.home.recommendPager;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jch.lib.util.DisplayUtil;
import com.jch.lib.util.ImageManager;
import com.promo.zjyfrestaurant.BaseActivity;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.application.Constant;
import com.promo.zjyfrestaurant.application.HttpConstant;
import com.promo.zjyfrestaurant.bean.PrivilegeActionBean;
import com.promo.zjyfrestaurant.impl.RequestCallback;
import com.promo.zjyfrestaurant.impl.ShowMenuRequset;
import com.promo.zjyfrestaurant.impl.ZJYFRequestParmater;
import com.promo.zjyfrestaurant.util.ContextUtil;
import com.promo.zjyfrestaurant.util.LogCat;

/**
 * 优惠活动详情。
 */
public class PrivilegeDetailActivity extends BaseActivity {

    public static final String DETAIL_ID = "detailId";
    public static final String DETAIL_KEY = "detail";
    private ImageView privilegedetailimg;
    private TextView privilegetitlenametv;
    private TextView privilegetimetv;
    private TextView privilegedetailtv;
    private String detailId;
    private PrivilegeActionBean detailbean;

    @Override
    protected View initContentView() {
        View view = View.inflate(getApplicationContext(), R.layout.activity_privilege_detail, null);

        setTitle(getString(R.string.activity_detail));
        addShareBtn();

        init(view);
        return view;
    }

    private void addShareBtn() {

        Button button = new Button(getApplicationContext());
        button.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        button.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.share_sel), null);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND); // 启动分享发送的属性
                intent.setType("text/plain"); // 分享发送的数据类型
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_subject)); // 分享的主题
                intent.putExtra(Intent.EXTRA_TEXT, "优惠活动：" + detailbean.getCover()); // 分享的内容
                startActivity(Intent.createChooser(intent, "选择分享"));
            }
        });

        addRightItem(button);
    }

    private void init(View view) {
        privilegedetailimg = (ImageView) view.findViewById(R.id.privilege_detail_img);
        privilegetitlenametv = (TextView) view.findViewById(R.id.privilege_title_name_tv);
        privilegetimetv = (TextView) view.findViewById(R.id.privilege_time_tv);
        privilegedetailtv = (TextView) view.findViewById(R.id.privilege_detail_tv);

        detailId = getIntent().getExtras().getString(DETAIL_ID);
        detailbean = getIntent().getExtras().getParcelable(DETAIL_KEY);

//        getData();
        initData();
    }

    private void initData() {

//        DisplayUtil.resizeViewByScreenWidth(privilegedetailimg, Constant.RECM_IMG_POINT.x, Constant.RECM_IMG_POINT.y, );

        int exceptInt = (int) ((getResources().getDimension(R.dimen.common_top_marg) +
                getResources().getDimension(R.dimen.home_item_pad)) * 2);
        DisplayUtil.resizeViewByScreenWidth(privilegedetailimg, Constant.RECM_IMG_POINT.x, Constant.RECM_IMG_POINT.y, exceptInt, PrivilegeDetailActivity.this);
        ImageManager.load(detailbean.getCover(), privilegedetailimg, ContextUtil.getRectangleImgOptions(), (int) getResources().getDimension(R.dimen.img_circle_corner));
        privilegetitlenametv.setText(detailbean.getTitle());
        privilegetimetv.setText(detailbean.getStart_date());
        privilegedetailtv.setText(detailbean.getContent());
    }

    @Override
    protected void getData() {

        ZJYFRequestParmater parma = new ZJYFRequestParmater(getApplicationContext());
        parma.put("detailid", detailId);

        ShowMenuRequset.getData(PrivilegeDetailActivity.this, HttpConstant.getActivityDetail, parma, PrivilegeActionBean.class, new RequestCallback<PrivilegeActionBean>() {
            @Override
            public void onfailed(String msg) {
                ContextUtil.toast(getApplicationContext(), getString(R.string.download_fialed));
            }

            @Override
            public void onSuccess(PrivilegeActionBean data) {
                LogCat.v("my test data" + data);

            }
        });

    }

}
