package com.promo.zjyfrestaurant.personal;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.promo.zjyfrestaurant.BaseFragment;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.personal.login.LoginActivity;
import com.promo.zjyfrestaurant.util.VersionManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonalFragment extends BaseFragment implements View.OnClickListener {

    private static PersonalFragment fragment = null;

    private View mContainerView = null;
    private ImageView mHeadIv = null;
    private TableRow mPersonbookTr = null;
    private TableRow mPersonOrderTr = null;
    private TableRow mPersonAddrTr = null;
    private TableRow mPersonShareTr = null;
    private TableRow mPersonFeedbackTr = null;
    private TableRow mPersonAboutTr = null;
    private TableRow mPersonUpdateTr = null;
    private TextView mPersonVersonTv = null;
    private Button mLogoutBtn = null;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PersonalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonalFragment newInstance() {
        if (fragment == null) {
            fragment = new PersonalFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
        }

        return fragment;
    }

    public PersonalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected View addContentView(LayoutInflater inflater) {

        mContainerView = inflater.inflate(R.layout.fragment_personal, null);
        init(mContainerView);
        return mContainerView;
    }

    private void init(View containerView) {

        hideHeadView();
        mHeadIv = (ImageView) containerView.findViewById(R.id.personal_head_img);
        mPersonAboutTr = (TableRow) containerView.findViewById(R.id.person_about);
        mPersonbookTr = (TableRow) containerView.findViewById(R.id.person_book);
        mPersonOrderTr = (TableRow) containerView.findViewById(R.id.person_order);
        mPersonAddrTr = (TableRow) containerView.findViewById(R.id.person_address);
        mPersonShareTr = (TableRow) containerView.findViewById(R.id.person_share);
        mPersonFeedbackTr = (TableRow) containerView.findViewById(R.id.person_feedback);
        mPersonUpdateTr = (TableRow) containerView.findViewById(R.id.person_update);
        mPersonVersonTv = (TextView) containerView.findViewById(R.id.personal_version_tv);
        mLogoutBtn = (Button) containerView.findViewById(R.id.logout_btn);

        mPersonVersonTv.append(getVersion());

        mHeadIv.setOnClickListener(this);
        mPersonAboutTr.setOnClickListener(this);
        mPersonbookTr.setOnClickListener(this);
        mPersonOrderTr.setOnClickListener(this);
        mPersonAddrTr.setOnClickListener(this);
        mPersonShareTr.setOnClickListener(this);
        mPersonFeedbackTr.setOnClickListener(this);
        mPersonUpdateTr.setOnClickListener(this);

    }

    /**
     * 获取当前版本号。
     *
     * @return
     */
    private String getVersion() {
        String versionName = "1.0";
        try {
            versionName = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionName;
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {

            case R.id.personal_head_img: {
//                login();

                break;
            }
            case R.id.person_about: {
                Intent intent = new Intent(getActivity(), AboutUsActivity.class);
                transNextPage(intent);

                break;

            }
            case R.id.person_book: {

                Intent intent = new Intent(getActivity(), MyBookActivity.class);
                transNextPage(intent);
                break;
            }
            case R.id.person_order: {

                Intent intent = new Intent(getActivity(), MyOrderActivity.class);
                transNextPage(intent);
                break;
            }
            case R.id.person_address: {

                Intent intent = new Intent(getActivity(), AddressActivity.class);
                transNextPage(intent);

                break;
            }
            case R.id.person_share: {

                Intent intent = new Intent(Intent.ACTION_SEND); // 启动分享发送的属性
                intent.setType("text/plain"); // 分享发送的数据类型
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_subject)); // 分享的主题
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_content)); // 分享的内容
                startActivity(Intent.createChooser(intent, "选择分享"));

                break;
            }
            case R.id.person_feedback: {

                Intent intent = new Intent(getActivity(), FeedBackActivity.class);
                transNextPage(intent);

                break;
            }
            case R.id.person_update: {
                updata();

                break;
            }

            case R.id.logout_btn: {


                break;
            }
            default: {

            }

        }

    }

    /**
     * 检查版本更新。
     */
    private void updata() {
        VersionManager.requestVersionCode(getActivity().getApplicationContext());
    }

    /**
     *
     */
    private void login() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        transNextPage(intent);
    }

}
