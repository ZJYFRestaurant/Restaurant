package com.promo.zjyfrestaurant.map;


import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.promo.zjyfrestaurant.BaseFragment;
import com.promo.zjyfrestaurant.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link com.promo.zjyfrestaurant.map.MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends BaseFragment implements BaiduMap.OnMapClickListener, OnGetRoutePlanResultListener {

    private static MapFragment fragment = null;
    private LocationClient mLocClient = null;
    /**
     * 初始化地图显示中心点*
     */
    private final LatLng initPoint = new LatLng(32.026763, 118.786463);
    /**
     * 路线规划终点。
     */
    private final LatLng endPoint = new LatLng(32.026763, 118.906463);

    private float mZoom = 15.0f;    //地图放缩比例

    private View mContainerView = null;
    private MapView bmapView;
    private BaiduMap mBaiduMap;
    private SDKReceiver mReceiver = null;
    boolean isFirstLoc = true;// 是否首次定位

    private int freshPeriod = 3000;
    //路线规划。
    RouteLine route = null;
    OverlayManager routeOverlay = null;
    RoutePlanSearch mSearch = null;

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

        bmapView = (MapView) containerView.findViewById(R.id.bmap_view);
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
        BitmapDescriptor mCurrentMarker = null;
        MyLocationConfiguration.LocationMode locationMode = MyLocationConfiguration.LocationMode.FOLLOWING;
        mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.drawable.home_stars);
        MyLocationConfiguration config = new MyLocationConfiguration(locationMode, true, mCurrentMarker);
        mBaiduMap.setMyLocationConfigeration(config);

        mReceiver = new SDKReceiver();
        // 初始化搜索模块，注册事件监听
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);

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
            LatLng latLng = new LatLng(location.getLatitude(),
                    location.getLongitude());
            mSearch.drivingSearch(new DrivingRoutePlanOption().from(PlanNode.withLocation(latLng)).to(PlanNode.withLocation(endPoint)));

        }

        @Override
        public void onReceivePoi(BDLocation bdLocation) {

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
            route = result.getRouteLines().get(0);
            DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaiduMap);
            routeOverlay = overlay;
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
        public BitmapDescriptor getStartMarker() {
            return super.getStartMarker();
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            return super.getTerminalMarker();
        }
    }


    @Override
    public void onDestroy() {
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

