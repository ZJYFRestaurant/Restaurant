package com.promo.zjyfrestaurant.map;


import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.jch.lib.util.HttpUtil;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.promo.zjyfrestaurant.BaseFragment;
import com.promo.zjyfrestaurant.R;
import com.promo.zjyfrestaurant.application.HttpConstant;
import com.promo.zjyfrestaurant.impl.ZJYFRequestParmater;
import com.promo.zjyfrestaurant.util.ContextUtil;
import com.promo.zjyfrestaurant.util.LogCat;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends BaseFragment implements BaiduMap.OnMapClickListener, OnGetRoutePlanResultListener {

    private static MapFragment fragment = null;
    private LocationClient mLocClient = null;
    /**
     * 初始化地图显示中心点*
     */
    private final LatLng initPoint = new LatLng(32.026763, 118.786463);

    private LatLng startPoint;

    /**
     * 路线规划终点。
     */
    private LatLng endPoint;

    private float mZoom = 15.0f;    //地图放缩比例

    private View mContainerView = null;
    private MapView bmapView;
    private BaiduMap mBaiduMap;
    private SDKReceiver mReceiver = null;
    boolean isFirstLoc = true;// 是否首次定位
    private Marker startMark;
    private InfoWindow mInfoWindow;
    BitmapDescriptor startBd = BitmapDescriptorFactory
            .fromResource(R.drawable.map_start);

    private Marker endMark;
    BitmapDescriptor endBd = BitmapDescriptorFactory.fromResource(R.drawable.map_end);

    private String address;
    private String shop_name;
    private String tel;

    private int freshPeriod = 3000;
    //路线规划。
    RouteLine route = null;
    RoutePlanSearch mSearch = null;
    private ImageButton maptelbtn;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MapFragment.
     */
    public static MapFragment newInstance() {

        if (fragment == null) {
            fragment = new MapFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
        }


        return fragment;
    }

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    protected View addContentView(LayoutInflater inflater) {

        mContainerView = inflater.inflate(R.layout.fragment_map, null);
        init(mContainerView);

        return mContainerView;

    }

    private void init(View containerView) {
        hideHeadView();

        maptelbtn = (ImageButton) containerView.findViewById(R.id.map_tel_btn);

        maptelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel));
                startActivity(intent);
            }
        });
        bmapView = (MapView) containerView.findViewById(R.id.bmap_view);
        bmapView.setClickable(true);
        mBaiduMap = bmapView.getMap();

        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);

        //初始化地图界面
        //初始化展示地图范围
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(initPoint, mZoom);
        mBaiduMap.setMapStatus(u);

        //定位初始化
        mLocClient = new LocationClient(getActivity().getApplicationContext());
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocClient.registerLocationListener(myLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);    //打开gps。
        option.setCoorType("bd09ll");   //设置坐标类型。
//        option.setScanSpan(freshPeriod);
        mLocClient.setLocOption(option);
        mLocClient.start();
        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
        MyLocationConfiguration.LocationMode locationMode = MyLocationConfiguration.LocationMode.NORMAL;
//        mCurrentMarker = BitmapDescriptorFactory
//                .fromResource(android.R.drawable.screen_background_light_transparent);
        MyLocationConfiguration config = new MyLocationConfiguration(locationMode, false, null);
        mBaiduMap.setMyLocationConfigeration(config);

        mReceiver = new SDKReceiver();
        // 初始化搜索模块，注册事件监听
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);

        getDestLocation();
    }

    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            // map view 销毁后不在处理新接收的位置
            if (location == null || bmapView == null)
                return;
            if (isFirstLoc) {
                isFirstLoc = false;
                mBaiduMap.clear();
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
            }
            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            startPoint = new LatLng(location.getLatitude(),
                    location.getLongitude());
            searchDriver();

        }

        @Override
        public void onReceivePoi(BDLocation bdLocation) {

        }
    }

    /**
     * 获取地理位置。
     */
    private void getDestLocation() {

        HttpUtil.post(HttpConstant.getMapPosition, new ZJYFRequestParmater(getActivity().getApplicationContext()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    JSONObject resObj = response.getJSONObject("response");
                    int code = resObj.getInt("code");
                    if (code == 100) {
                        JSONObject dataObj = resObj.getJSONObject("data");
                        double longitude = dataObj.getDouble("long");
                        double latitude = dataObj.getDouble("lat");
                        address = dataObj.getString("address");
                        shop_name = dataObj.getString("shop_name");
                        tel = dataObj.getString("tel");
                        endPoint = new LatLng(latitude, longitude);
                        searchDriver();
                    } else {
                        ContextUtil.toast(getActivity().getApplicationContext(), getString(R.string.get_local_erro));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    ContextUtil.toast(getActivity().getApplicationContext(), getString(R.string.get_local_erro));
                }

                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                ContextUtil.toast(getActivity().getApplicationContext(), getString(R.string.get_local_erro));
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                ContextUtil.toast(getActivity().getApplicationContext(), getString(R.string.get_local_erro));
                super.onSuccess(statusCode, headers, responseString);
            }
        });

    }

    /**
     * 查询公交路线。
     */
    private void searchDriver() {
        if (startPoint != null && endPoint != null) {
            mSearch.drivingSearch(new DrivingRoutePlanOption().from(PlanNode.withLocation(startPoint)).to(PlanNode.withLocation(endPoint)));

        }
    }


    @Override
    public void onMapClick(LatLng latLng) {
        mBaiduMap.hideInfoWindow();
    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        return false;
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(getActivity(), "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            //result.getSuggestAddrInfo()
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(result.getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();

        }

    }

    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public void setData(DrivingRouteLine drivingRouteLine) {

            super.setData(drivingRouteLine);
        }

        @Override
        public BitmapDescriptor getStartMarker() {

//            OverlayOptions startOO = new MarkerOptions().position(startPoint).icon(startBd).zIndex(10).draggable(false);
//            startMark = (Marker) (mBaiduMap.addOverlay(startOO));
            return startBd;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {

            OverlayOptions endOO = new MarkerOptions().position(endPoint).icon(endBd).zIndex(10).draggable(false);
            endMark = (Marker) (mBaiduMap.addOverlay(endOO));

            InfoWindow.OnInfoWindowClickListener listener = null;
            LogCat.v("on Marker click  2--");
            View view = View.inflate(getActivity().getApplicationContext(), R.layout.map_dermern_layout, null);
            TextView nameTv = (TextView) view.findViewById(R.id.map_win_name);
            TextView addrTv = (TextView) view.findViewById(R.id.map_win_addr);
            TextView telTv = (TextView) view.findViewById(R.id.map_win_tel);
            nameTv.setText(shop_name);
            addrTv.setText(address);
            telTv.setText(tel);
            listener = new InfoWindow.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick() {
                }
            };

            mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(view), endPoint, -47, listener);
            mBaiduMap.showInfoWindow(mInfoWindow);

            return endBd;
        }

        @Override
        public boolean onRouteNodeClick(int i) {
            LogCat.v("on Route node click--");

            return super.onRouteNodeClick(i);
        }

    }


    @Override
    public void onDestroy() {
        startBd.recycle();
        endBd.recycle();
        mSearch.destroy();
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        getActivity().unregisterReceiver(mReceiver);
        bmapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        // 注册 SDK 广播监听者
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        getActivity().registerReceiver(mReceiver, iFilter);
        bmapView.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        bmapView.onPause();
    }


}

