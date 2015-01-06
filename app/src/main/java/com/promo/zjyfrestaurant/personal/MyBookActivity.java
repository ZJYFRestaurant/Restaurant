package com.promo.zjyfrestaurant.personal;

import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.promo.zjyfrestaurant.BaseActivity;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.application.HttpConstant;
import com.promo.zjyfrestaurant.application.ZjyfApplication;
import com.promo.zjyfrestaurant.bean.MyBooksBean;
import com.promo.zjyfrestaurant.impl.RequestCallback;
import com.promo.zjyfrestaurant.impl.ShowMenuRequset;
import com.promo.zjyfrestaurant.impl.ZJYFRequestParmater;
import com.promo.zjyfrestaurant.util.ContextUtil;

/**
 * 我的预定。
 */
public class MyBookActivity extends BaseActivity {


    private ExpandableListView mybookelist;
    private MyBookAdapter adapter;

    @Override

    protected View initContentView() {

        View containerView = View.inflate(getApplicationContext(), R.layout.activity_my_book, null);

        init(containerView);

        return containerView;
    }

    @Override
    protected void getData() {

        int uid = ((ZjyfApplication) getApplicationContext()).getUid();
        ZJYFRequestParmater parmater = new ZJYFRequestParmater(getApplicationContext());
        parmater.put("uid", uid);

        ShowMenuRequset.getData(MyBookActivity.this, HttpConstant.getOrderList, parmater, MyBooksBean.class, new RequestCallback<MyBooksBean>() {
            @Override
            public void onfailed(String msg) {
                ContextUtil.toast(getApplicationContext(), msg);
            }

            @Override
            public void onSuccess(MyBooksBean data) {
                if (data != null) {
                    adapter.notifyDataSetChanged(data);
                    extentList();
                }
            }
        });
    }

    private void extentList() {
        mybookelist.expandGroup(0);
        mybookelist.expandGroup(1);
    }


    private void init(View containerView) {

        setTitle(getResources().getString(R.string.personal_book));

        mybookelist = (ExpandableListView) containerView.findViewById(R.id.mybook_elist);
        mybookelist.setEnabled(true);
        adapter = new MyBookAdapter(getApplicationContext());
        mybookelist.setAdapter(adapter);
        mybookelist.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });


        getData();

    }

    private void addRightBtn() {

        Button addBtn = new Button(getApplicationContext());

    }

}
