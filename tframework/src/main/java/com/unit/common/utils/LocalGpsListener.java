package com.unit.common.utils;

import android.content.*;
import android.content.SharedPreferences.*;
import android.location.*;
import android.os.*;
import android.text.*;

/**
 * Android原生定位
 * Created by kingkong on 13-10-2.
 */
public class LocalGpsListener {


    private final String GPSConfig = "GPSConfig";
    private final String SpLocation = "SpLocation";
    LocationManager manager;
    private static LocalGpsListener localGpsListener;  //单例
    private static Context context;             //单例
    private static MyLoactionListener listener; //单例
    private static Location myLocation = null;
    private static Handler locationHandler = null;
    //1.私有化构造方法

    private LocalGpsListener () {
    }

    ;

    //2. 提供一个静态的方法 可以返回他的一个实例
    public static synchronized LocalGpsListener getInstance (Context context, Handler handler) {
        if (localGpsListener == null) {
            synchronized (LocalGpsListener.class) {
                if (localGpsListener == null) {
                    localGpsListener = new LocalGpsListener();
                    LocalGpsListener.context = context;
                    locationHandler = handler;
                }
            }
        }
        return localGpsListener;
    }


    public Location getMyLocation () {
        return myLocation;
    }

    /**
     * 获取gps 信息
     *
     * @return lat, lng
     */
    public String getLocation () {
        manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //获取所有的定位方式
        //manager.getAllProviders(); // gps //wifi //
        //获取当前手机最好的位置提供者
        String provider = getProvider(manager);
        // 注册位置的监听器
        //60000每隔一分钟获取当前位置(最大频率)
        //位置每改变50米重新获取位置信息
        //getListener()位置发生改变时的回调方法
        if (TextUtils.isEmpty(provider)) {
            return "";
        }
        if (myLocation == null) {
            myLocation = manager.getLastKnownLocation(provider);
            if (myLocation != null) {
                locationHandler.sendEmptyMessage(0);
            }
        }
        manager.requestLocationUpdates(provider, 60000, 50, getListener());
        //拿到最后一次的位置信息
        SharedPreferences sp = context.getSharedPreferences(GPSConfig, Context.MODE_PRIVATE);
        String location = sp.getString(SpLocation, "");

        return location;
    }


    //停止gps监听
    public void stopGPSListener () {
        manager.removeUpdates(getListener());
    }


    //获取gps监听实例
    private synchronized MyLoactionListener getListener () {
        if (listener == null) {
            synchronized (LocalGpsListener.class) {
                if (listener == null) {
                    listener = new MyLoactionListener();
                }
            }

        }
        return listener;
    }

    /**
     * 计算两点距离
     *
     * @param lat_a
     * @param lng_a
     * @param lat_b
     * @param lng_b
     * @return
     */
    public static double getDistance (double lat_a, double lng_a, double lat_b, double lng_b) {
        double d = 0;
        lat_a = lat_a * Math.PI / 180;
        lng_a = lng_a * Math.PI / 180;
        lat_b = lat_b * Math.PI / 180;
        lng_b = lng_b * Math.PI / 180;

        d = Math.sin(lat_a) * Math.sin(lat_b) + Math.cos(lat_a) * Math.cos(lat_b) * Math.cos(lng_b - lng_a);
        d = Math.sqrt(1 - d * d);
        d = Math.cos(lat_b) * Math.sin(lng_b - lng_a) / d;
        d = Math.asin(d) * 180 / Math.PI;

//     d = Math.round(d*10000);
        return d;
    }

    private class MyLoactionListener implements LocationListener {

        /**
         * 当手机位置发生改变的时候 调用的方法
         */
        public void onLocationChanged (Location location) {
            String latitude = "latitude " + location.getLatitude(); //获取纬度
            String longtitude = "longtitude " + location.getLongitude(); //获取精度
            //最后一次获取到的位置信息 存放到sharedpreference里面
            SharedPreferences sp = context.getSharedPreferences(GPSConfig, Context.MODE_PRIVATE);
            String latlng = latitude + "," + longtitude;
            Editor editor = sp.edit();
            editor.putString(SpLocation, latlng);
            editor.commit();
            if (locationHandler != null) {
                myLocation = location;
                Message message = locationHandler.obtainMessage();
                message.obj = (String) latlng;
                locationHandler.sendMessage(message);
            }
        }


        /**
         * 某一个设备的状态发生改变的时候 调用
         * 可用->不可用
         * 不可用->可用
         * status 当前状态
         * extras 额外消息
         */
        public void onStatusChanged (String provider, int status, Bundle extras) {

        }

        /**
         * 某个设备被打开
         */
        public void onProviderEnabled (String provider) {

        }

        /**
         * 某个设备被禁用
         */
        public void onProviderDisabled (String provider) {

        }

    }

    /**
     * \
     *
     * @param manager 位置管理服务
     * @return 最好的位置提供者
     */
    private String getProvider (LocationManager manager) {
        if (!isOPen(context)) {
            return null;
        }
        //设置查询条件
        Criteria criteria = new Criteria();
        //定位精准度
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //对海拔是否敏感
        criteria.setAltitudeRequired(false);
        //对手机耗电性能要求（获取频率）
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
        //对速度变化是否敏感
        criteria.setSpeedRequired(true);
        //是否运行产生开销（费用）
        criteria.setCostAllowed(true);
        //如果置为ture只会返回当前打开的gps设备
        //如果置为false如果设备关闭也会返回
        return manager.getBestProvider(criteria, true);
    }

    public static final boolean isOPen (final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }

        return false;
    }

}

