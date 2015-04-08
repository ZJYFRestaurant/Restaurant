package com.promote.zjyfrestaurant.personal;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.promote.zjyfrestaurant.BaseActivity;
import com.promote.zjyfrestaurant.MainActivity;
import com.promote.zjyfrestaurant.R;
import com.promote.zjyfrestaurant.application.HttpConstant;
import com.promote.zjyfrestaurant.application.ZjyfApplication;
import com.promote.zjyfrestaurant.bean.AddressBean;
import com.promote.zjyfrestaurant.book.subFragment.ToRestFragment;
import com.promote.zjyfrestaurant.impl.RequestCallback;
import com.promote.zjyfrestaurant.impl.ShowMenuRequset;
import com.promote.zjyfrestaurant.impl.ZJYFRequestParmater;
import com.promote.zjyfrestaurant.util.ContextUtil;
import com.promote.zjyfrestaurant.util.LogCat;
import com.promote.zjyfrestaurant.util.ZJYFDialog;

import java.util.ArrayList;

/**
 * 地址管理。
 */
public class AddressActivity extends BaseActivity implements View.OnClickListener, DelAddrCallBack, AdapterView.OnItemClickListener {

    static final int ADDCODE = 100;    //添加。
    static final int MODCODE = 101;     //修改。
    public static final int MODIFY_ITEM = 102;
    public static final int SEL_ITEM = 103;
    public static final String SEl_ADDR_KEY = "select_addr";
    public static final String ITEM_PRESS_KEY = "press_item";
    //跳转过来的activity.key，由于BookActivity和Maintivity的launchMode不同。主要是判断地址选择时是bookActivity还是MainActivity.以区分不同返回方法。
    public static final String FROM_ACIVITY_KEY = "from_activity";
    public static final int FROM_MAIN_CODE = 44;
    public static final int FROM_BOOK_CODE = 45;

    private int fromActivityCode = -1;
    private int itemType = MODIFY_ITEM;
    static final String ADDINFO_KEY = "addr";
    private ListView addrlv;
    private AddressAdapter adapter;
    private ArrayList<AddressBean> addresses = new ArrayList<>();
    private LinearLayout noAddrLl;

    @Override
    protected View initContentView() {

        View containerView = View.inflate(getApplicationContext(), R.layout.activity_address, null);

        init(containerView);
        return containerView;
    }

    @Override
    protected void getData() {

        ZJYFRequestParmater parma = new ZJYFRequestParmater(getApplicationContext());
        parma.put("uid", ((ZjyfApplication) getApplicationContext()).getUid());

        ShowMenuRequset.getListData(AddressActivity.this, HttpConstant.getAddressList, parma, AddressBean.class, new RequestCallback<ArrayList<AddressBean>>() {
            @Override
            public void onfailed(String msg) {
                ContextUtil.toast(AddressActivity.this, msg);
                addrlv.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(ArrayList<AddressBean> data) {
                if (data != null && data.size() != 0) {
                    noAddrLl.setVisibility(View.GONE);
                    addresses.clear();
                    addresses.addAll(data);
                    adapter.notifyDataSetChanged(data);
                } else {
                    addrlv.setVisibility(View.GONE);
                }
            }
        });

    }

    private void init(View containerView) {

        setTitle(getResources().getString(R.string.personal_address));
        Intent intent = getIntent();
        itemType = intent.getIntExtra(ITEM_PRESS_KEY, MODIFY_ITEM);

        addTittleRightBtn();
        fromActivityCode = intent.getIntExtra(FROM_ACIVITY_KEY, -1);

        addrlv = (ListView) containerView.findViewById(R.id.addr_lv);
        noAddrLl = (LinearLayout) containerView.findViewById(R.id.addr_no_msg_ll);
        adapter = new AddressAdapter(getApplicationContext(), itemType);
        adapter.setDelAddrCallBack(this);
        addrlv.setAdapter(adapter);
        addrlv.setOnItemClickListener(this);

        getData();
    }

    private void addTittleRightBtn() {

        Button addBtn = (Button) View.inflate(getApplicationContext(), R.layout.add_btn_layout, null);
        addBtn.setOnClickListener(this);
        addRightItem(addBtn);

    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.add_btn_layout: {
                Intent intent = new Intent(AddressActivity.this, AddAddressActivity.class);
                startActivityForResult(intent, ADDCODE);
//                startActivity(intent);
                break;
            }
            default: {

            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK: {
                if (requestCode == ADDCODE) {
                    AddressBean addressBean = data.getParcelableExtra(ADDINFO_KEY);
                    addresses.add(addressBean);
                    hasData();
                    adapter.notifyDataSetChanged(addresses);
                } else if (requestCode == MODCODE) {        //获取修改结果。
                    AddressBean address = data.getParcelableExtra(ModifyAddrActivity.MODIFY_ADDR_KEY);
                    if (address != null)
                        for (AddressBean address_arr : addresses) {         //显示更新的地址
                            if (address_arr.getId() == address.getId()) {
                                address_arr.setTel(address.getTel());
                                address_arr.setContact(address.getContact());
                                address_arr.setContent(address.getContent());
                                adapter.notifyDataSetChanged(addresses);
                                break;
                            }
                        }

                }
                break;
            }

        }


    }

    @Override
    public void delAddr(final int pointion) {

        ZJYFDialog.Builder builder = ZJYFDialog.Builder.zJYFDialog(AddressActivity.this);
        builder.setTitle(R.string.dialog_promot)
                .setContentMsg(R.string.delete_addr_confirm)
                .setPositiveBtn(R.string.ok, new ZJYFDialog.ZJYFOnclickListener() {
                    @Override
                    public void onclick() {
                        delAddr(addresses.get(pointion));
                    }
                }).setNectiveBtn(R.string.cancel, new ZJYFDialog.ZJYFOnclickListener() {
            @Override
            public void onclick() {

            }
        }).show();

    }

    /**
     * 删除地址。
     *
     * @param address
     */
    private void delAddr(final AddressBean address) {

        ZJYFRequestParmater parma = new ZJYFRequestParmater(getApplicationContext());
        parma.put("address_id", address.getId());
        ShowMenuRequset.getData(AddressActivity.this, HttpConstant.deleteAddress, parma, String.class, new RequestCallback<String>() {
            @Override
            public void onfailed(String msg) {
                ContextUtil.toast(getApplicationContext(), msg);
            }

            @Override
            public void onSuccess(String data) {
                ContextUtil.toast(getApplicationContext(), data);
                addresses.remove(address);
                if (addresses.size() == 0) {
                    noData();
                }
                adapter.notifyDataSetChanged(addresses);
            }
        });
    }

    private void hasData() {
        noAddrLl.setVisibility(View.GONE);
        addrlv.setVisibility(View.VISIBLE);
    }

    private void noData() {
        noAddrLl.setVisibility(View.VISIBLE);
        addrlv.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        LogCat.d("onItemClick");

        if (itemType == MODIFY_ITEM) {  //修改
            Intent intent = new Intent(AddressActivity.this, ModifyAddrActivity.class);
            intent.putExtra(ModifyAddrActivity.MODIFY_ADDR_KEY, addresses.get(position));
            startActivityForResult(intent, MODCODE);
        } else if (itemType == SEL_ITEM) {  //选择地址，来自预定单。
            Intent intent = new Intent(AddressActivity.this, MainActivity.class);
            intent.putExtra(ToRestFragment.ADDR_KEY, addresses.get(position));
            intent.putExtra(MainActivity.INTENT_TYPE_KEY, MainActivity.FROM_ADDR);

            if (fromActivityCode == FROM_MAIN_CODE)         //MainActivty和BookActivity的不同返回方式。
                startActivity(intent);
            else if (fromActivityCode == FROM_BOOK_CODE)
                setResult(RESULT_OK, intent);
            this.finish();
        }
    }

}
