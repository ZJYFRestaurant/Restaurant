package com.promo.zjyfrestaurant.personal;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.promo.zjyfrestaurant.BaseActivity;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.application.HttpConstant;
import com.promo.zjyfrestaurant.application.ZjyfApplication;
import com.promo.zjyfrestaurant.bean.AddressBean;
import com.promo.zjyfrestaurant.book.subFragment.ToRestFragment;
import com.promo.zjyfrestaurant.impl.RequestCallback;
import com.promo.zjyfrestaurant.impl.ShowMenuRequset;
import com.promo.zjyfrestaurant.impl.ZJYFRequestParmater;
import com.promo.zjyfrestaurant.util.ContextUtil;
import com.promo.zjyfrestaurant.util.LogCat;

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

    private int itemType = MODIFY_ITEM;
    static final String ADDINFO_KEY = "addr";
    private ListView addrlv;
    private AddressAdapter adapter;
    private ArrayList<AddressBean> addresses = new ArrayList<>();

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
            }

            @Override
            public void onSuccess(ArrayList<AddressBean> data) {
                addresses.clear();
                addresses.addAll(data);
                adapter.notifyDataSetChanged(data);
            }
        });

    }

    private void init(View containerView) {

        setTitle(getResources().getString(R.string.personal_address));
        addTittleRightBtn();
        itemType = getIntent().getIntExtra(ITEM_PRESS_KEY, MODIFY_ITEM);

        addrlv = (ListView) containerView.findViewById(R.id.addr_lv);
        adapter = new AddressAdapter(getApplicationContext());
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
    public void delAddr(int pointion) {
        delAddr(addresses.get(pointion));
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
                adapter.notifyDataSetChanged(addresses);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        LogCat.d("onItemClick");

        if (itemType == MODIFY_ITEM) {  //修改
            Intent intent = new Intent(AddressActivity.this, ModifyAddrActivity.class);
            intent.putExtra(ModifyAddrActivity.MODIFY_ADDR_KEY, addresses.get(position));
            startActivityForResult(intent, MODCODE);
        } else if (itemType == SEL_ITEM) {  //选择地址，来自预定单。
            Intent intent = new Intent();
            intent.putExtra(ToRestFragment.ADDR_KEY, addresses.get(position));
            setResult(RESULT_OK, intent);
            this.finish();
        }
    }

}
