package com.promote.zjyfrestaurant.loading;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.jch.lib.util.DisplayUtil;
import com.promote.zjyfrestaurant.MainActivity;
import com.promote.zjyfrestaurant.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.promote.zjyfrestaurant.loading.GuidPageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GuidPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuidPageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String BG_INT_KEY = "bgInts";
    private static final String TEXT_INT_KEY = "textInts";
    private static final String END_KEY = "index";
    private int bgInts;
    private int textInts;
    private boolean end_flag = false;
    private boolean showStartBtn = false;

    private OnFragmentInteractionListener mListener;
    private ImageView guidimg;
    private ImageView guidtextimg;
    Animation bgAnimation;
    private ImageButton guidstartbtn;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param bgInts   Parameter 1.
     * @param textInts Parameter 2.
     * @return A new instance of fragment GuidPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GuidPageFragment newInstance(int bgInts, int textInts, boolean end_flag) {
        GuidPageFragment fragment = new GuidPageFragment();
        Bundle args = new Bundle();
        args.putInt(BG_INT_KEY, bgInts);
        args.putInt(TEXT_INT_KEY, textInts);
        args.putBoolean(END_KEY, end_flag);
        fragment.setArguments(args);
        return fragment;
    }

    public GuidPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bgInts = getArguments().getInt(BG_INT_KEY, 0);
            textInts = getArguments().getInt(TEXT_INT_KEY, 0);
            end_flag = getArguments().getBoolean(END_KEY, false);
        }
        showStartBtn = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View containerView = inflater.inflate(R.layout.fragment_guid_page, container, false);
        initialize(containerView);
        return containerView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void initialize(View containerView) {

        guidimg = (ImageView) containerView.findViewById(R.id.guid_img);
        resizeImgWidht(guidimg, 1.2);
        guidtextimg = (ImageView) containerView.findViewById(R.id.guid_text_img);
        guidstartbtn = (ImageButton) containerView.findViewById(R.id.guid_start_btn);
        guidstartbtn.setVisibility(end_flag ? View.VISIBLE : View.INVISIBLE);

        guidimg.setBackgroundResource(bgInts);
        guidtextimg.setBackgroundResource(textInts);

        bgAnimation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.guid_trans);
        guidimg.setAnimation(bgAnimation);

        guidstartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        if (!bgAnimation.hasEnded())
            bgAnimation.reset();
    }

    @Override
    public void onPause() {
        bgAnimation.cancel();
        super.onPause();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private void resizeImgWidht(View view, double percent) {
        WindowManager wm = getActivity().getWindowManager();
        Point screenPoint = new Point();
        DisplayUtil.getSize(wm.getDefaultDisplay(), screenPoint);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = (int) ((double) screenPoint.y * percent);
        view.setLayoutParams(params);
    }

}
