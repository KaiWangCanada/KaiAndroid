package com.mmfcommon.zhuge;

import com.mmfcommon.activity.MmfCommonAppBaseApplication;
import com.mmfcommon.bean.SalonBaseBean;
import com.mmfcommon.bean.ServiceBaseBean;
import com.mmfcommon.bean.ServiceBean;
import com.mmfcommon.bean.StylistBaseBean;
import com.mmfcommon.bean.UserInfoBean;
import com.zhuge.analysis.stat.ZhugeSDK;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 美美发业务统计自定义事件类
 * Created by titanic on 2015-06-19.
 */
public class ZhuGeBusinessStatistics extends BaseZhuGeBusinessStatistics {

    /**
     * 查看首页服务
     *
     * @param serviceBaseBean 服务
     * @param userInfoBean    用户信息
     */
    public static void LookMainService(ServiceBaseBean serviceBaseBean, UserInfoBean userInfoBean, String locationName) {
        try {
            JSONObject eventObject = ZhuGeBaseJSONObject.GetInstance(locationName);
            if (serviceBaseBean != null)
                eventObject.put(serviceName, serviceBaseBean.getServiceName());
            if (userInfoBean != null)
                eventObject.put(userGender, userInfoBean.getGender());
            ZhugeSDK.getInstance().onEvent(MmfCommonAppBaseApplication.getAppContext(), "点击首页服务", eventObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void LookSalonService(SalonBaseBean salonBaseBean, ServiceBean serviceBean, UserInfoBean userInfoBean, String locationName) {
        try {
            JSONObject eventObject = ZhuGeBaseJSONObject.GetInstance(locationName);
            if (serviceBean != null)
                eventObject.put(salonName, salonBaseBean.getSalonName());
            if (userInfoBean != null)
            eventObject.put(userGender, userInfoBean.getGender());

            if(serviceBean!=null) {
                eventObject.put(serviceName, serviceBean.getServiceName());
                eventObject.put(serviceMinPrice, serviceBean.getMin_price());
                eventObject.put(serviceMaxPrice, serviceBean.getMax_price());
                eventObject.put(serviceStorePrice, serviceBean.getMaxStorePrice());
            }
            ZhugeSDK.getInstance().onEvent(MmfCommonAppBaseApplication.getAppContext(), "查看服务详情", eventObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查看店铺详情
     *
     * @param salonBaseBean
     * @param userInfoBean
     */
    public static void LookSalonDetail(SalonBaseBean salonBaseBean, UserInfoBean userInfoBean, String locationName) {
        BaseSalon(salonBaseBean, userInfoBean, "查看店铺详情", locationName);
    }

    public static void FavoriteAddSalonDetail(SalonBaseBean salonBaseBean, UserInfoBean userInfoBean, String locationName) {
        BaseSalon(salonBaseBean, userInfoBean, "收藏店铺", locationName);
    }

    public static void FavoriteDelSalonDetail(SalonBaseBean salonBaseBean, UserInfoBean userInfoBean, String locationName) {
        BaseSalon(salonBaseBean, userInfoBean, "取消收藏店铺", locationName);
    }

    public static void LookSalonStylist(SalonBaseBean salonBaseBean, UserInfoBean userInfoBean, StylistBaseBean stylistBaseBean, String locationName) {
//        BaseStylist(salonBaseBean,userInfoBean,stylistBaseBean,null,"查看店铺发型师",locationName);
        BaseStylist(salonBaseBean, userInfoBean, stylistBaseBean, null, "查看发型师", locationName);
    }

    public static void FavAddSalonStylist(SalonBaseBean salonBaseBean, UserInfoBean userInfoBean, StylistBaseBean stylistBaseBean, String locationName) {
        BaseStylist(salonBaseBean, userInfoBean, stylistBaseBean, null, "收藏发型师", locationName);
    }

    public static void FavDelSalonStylist(SalonBaseBean salonBaseBean, UserInfoBean userInfoBean, StylistBaseBean stylistBaseBean, String locationName) {
        BaseStylist(salonBaseBean, userInfoBean, stylistBaseBean, null, "取消收藏发型师", locationName);
    }

    public static void LookWork(SalonBaseBean salonBaseBean, UserInfoBean userInfoBean, StylistBaseBean stylistBaseBean, String locationName) {
        BaseWork(salonBaseBean, userInfoBean, stylistBaseBean, "查看作品", locationName);
    }

    public static void FavAddWork(SalonBaseBean salonBaseBean, UserInfoBean userInfoBean, StylistBaseBean stylistBaseBean, String locationName) {
        BaseWork(salonBaseBean, userInfoBean, stylistBaseBean, "收藏发作品", locationName);
    }

    public static void FavDelWork(SalonBaseBean salonBaseBean, UserInfoBean userInfoBean, StylistBaseBean stylistBaseBean, String locationName) {
        BaseWork(salonBaseBean, userInfoBean, stylistBaseBean, "取消收藏作品", locationName);
    }

    //    public static void LookSalonServiceStylist(SalonBaseBean salonBaseBean,ServiceBean serviceBean,UserInfoBean userInfoBean,StylistBaseBean stylistBaseBean,String locationName){
//      BaseStylist(salonBaseBean,userInfoBean,stylistBaseBean,serviceBean,"查看店铺服务发型师",locationName);
//        BaseStylist(salonBaseBean,userInfoBean,stylistBaseBean,null,"查看发型师",locationName);
//    }
//    public static void BookSalonService(SalonBaseBean salonBaseBean,ServiceBean serviceBean,UserInfoBean userInfoBean,StylistBaseBean stylistBaseBean,String bookTime,String locationName){
//       BookBaseService(salonBaseBean, serviceBean, userInfoBean, stylistBaseBean, bookTime, "", "预约店铺服务",locationName);
//        BookService(salonBaseBean, serviceBean, userInfoBean, stylistBaseBean, bookTime,locationName);
//    }
//
//    public static void BookStylistService(SalonBaseBean salonBaseBean,ServiceBean serviceBean,UserInfoBean userInfoBean,StylistBaseBean stylistBaseBean,String bookTime,String locationName) {
//        BookBaseService(salonBaseBean, serviceBean, userInfoBean, stylistBaseBean, bookTime, "", "预约发型师服务",locationName);
//        BookService(salonBaseBean, serviceBean, userInfoBean, stylistBaseBean, bookTime,locationName);
//    }

//    public static void BookSalonServiceSuccess(SalonBaseBean salonBaseBean,ServiceBean serviceBean,UserInfoBean userInfoBean,StylistBaseBean stylistBaseBean,String bookTime,String payType,String locationName){
//        BookBaseService(salonBaseBean, serviceBean, userInfoBean, stylistBaseBean, bookTime, payType, "预约店铺服务成功付款",locationName);
//        BookServiceSuccess(salonBaseBean, serviceBean, userInfoBean, stylistBaseBean, bookTime, payType,locationName);
//    }
//
//    public static void BookSalonServiceFaild(SalonBaseBean salonBaseBean,ServiceBean serviceBean,UserInfoBean userInfoBean,StylistBaseBean stylistBaseBean,String bookTime,String payType,String locationName){
//        BookBaseService(salonBaseBean, serviceBean, userInfoBean, stylistBaseBean, bookTime, payType, "预约店铺服务取消付款",locationName);
//        BookServiceFaild(salonBaseBean, serviceBean, userInfoBean, stylistBaseBean, bookTime, payType,locationName);
//    }
//
//    public static void BookStylistServiceSuccess(SalonBaseBean salonBaseBean,ServiceBean serviceBean,UserInfoBean userInfoBean,StylistBaseBean stylistBaseBean,String bookTime,String payType,String locationName){
//        BookBaseService(salonBaseBean, serviceBean, userInfoBean, stylistBaseBean, bookTime, payType, "预约发型师服务成功付款",locationName);
//        BookServiceSuccess(salonBaseBean, serviceBean, userInfoBean, stylistBaseBean, bookTime, payType,locationName);
//    }
//
//    public static void BookStylistServiceFaild(SalonBaseBean salonBaseBean,ServiceBean serviceBean,UserInfoBean userInfoBean,StylistBaseBean stylistBaseBean,String bookTime,String payType,String locationName){
//        BookBaseService(salonBaseBean, serviceBean, userInfoBean, stylistBaseBean, bookTime, payType, "预约发型师服务取消付款",locationName);
//        BookServiceFaild(salonBaseBean, serviceBean, userInfoBean, stylistBaseBean, bookTime, payType,locationName);
//    }

}
