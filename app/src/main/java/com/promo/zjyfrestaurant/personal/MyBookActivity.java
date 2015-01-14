package com.promo.zjyfrestaurant.personal;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.promo.zjyfrestaurant.BaseActivity;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.application.HttpConstant;
import com.promo.zjyfrestaurant.application.ZjyfApplication;
import com.promo.zjyfrestaurant.bean.MyBooksBean;
import com.promo.zjyfrestaurant.book.BookDetailActivity;
import com.promo.zjyfrestaurant.impl.RequestCallback;
import com.promo.zjyfrestaurant.impl.ShowMenuRequset;
import com.promo.zjyfrestaurant.impl.ZJYFRequestParmater;
import com.promo.zjyfrestaurant.util.ContextUtil;

/**
 * 我的预定。
 */
public class MyBookActivity extends BaseActivity implements ExpandableListView.OnChildClickListener {


    private ExpandableListView mybookelist;
    private MyBookAdapter adapter;

    private MyBooksBean myBooksBean;
    private LinearLayout noDataLl;


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
                mybookelist.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(MyBooksBean data) {
                if (data != null && data.getBookTypeNum() != 0) {
                    noDataLl.setVisibility(View.GONE);
                    myBooksBean = data;
                    adapter.notifyDataSetChanged(data);
                    extentList();
                } else {
                    mybookelist.setVisibility(View.GONE);
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
        noDataLl = (LinearLayout) containerView.findViewById(R.id.mybook_no_msg_ll);
        mybookelist.setEnabled(true);
        adapter = new MyBookAdapter(getApplicationContext());
        mybookelist.setAdapter(adapter);
        mybookelist.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });
        mybookelist.setOnChildClickListener(this);


        getData();

    }

    private void addRightBtn() {
        Button addBtn = new Button(getApplicationContext());
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        int bookId = 0;

        if (myBooksBean.getBookTypeNum() == 1) {
            if (myBooksBean.getOneChildIndex() == 0) {          //有一种类型。
                bookId = myBooksBean.getNormal().get(childPosition).getId();
            } else bookId = myBooksBean.getOverdue().get(childPosition).getId();
        } else if (groupPosition == 0) {
            bookId = myBooksBean.getNormal().get(childPosition).getId();
        } else if (groupPosition == 1) {
            bookId = myBooksBean.getOverdue().get(childPosition).getId();
        }

        Intent intent = new Intent(MyBookActivity.this, BookDetailActivity.class);
        intent.putExtra(BookDetailActivity.ORDERID_KEY, bookId);
        transNextPage(intent, true);

        return false;
    }
}
