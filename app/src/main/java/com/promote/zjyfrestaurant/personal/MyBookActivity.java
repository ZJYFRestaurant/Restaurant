package com.promote.zjyfrestaurant.personal;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.promote.zjyfrestaurant.BaseActivity;
import com.promote.zjyfrestaurant.MainActivity;
import com.promote.zjyfrestaurant.R;
import com.promote.zjyfrestaurant.application.HttpConstant;
import com.promote.zjyfrestaurant.application.ZjyfApplication;
import com.promote.zjyfrestaurant.bean.MyBooksBean;
import com.promote.zjyfrestaurant.book.BookDetailActivity;
import com.promote.zjyfrestaurant.impl.RequestCallback;
import com.promote.zjyfrestaurant.impl.ShowMenuRequset;
import com.promote.zjyfrestaurant.impl.ZJYFRequestParmater;
import com.promote.zjyfrestaurant.util.ContextUtil;

/**
 * 我的预定。
 */
public class MyBookActivity extends BaseActivity implements ExpandableListView.OnChildClickListener {

    public static final String TO_MYBOOK_KEY = "tomybook";
    public static final int FROM_CONFIRM = 30;
    public static final int FROM_PERSONAL = 40;

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

        if (getIntent().getIntExtra(MyBookActivity.TO_MYBOOK_KEY, FROM_PERSONAL) == FROM_CONFIRM)
            setBackClickListener(new BackClickListener());          //

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

    /**
     * 回退监听。
     */
    private class BackClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int fromCode = getIntent().getIntExtra(AddressActivity.FROM_ACIVITY_KEY, -1);
            Intent intent = new Intent(MyBookActivity.this, MainActivity.class);
            intent.putExtra(AddressActivity.FROM_ACIVITY_KEY, fromCode);
            startActivity(intent);
        }
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
