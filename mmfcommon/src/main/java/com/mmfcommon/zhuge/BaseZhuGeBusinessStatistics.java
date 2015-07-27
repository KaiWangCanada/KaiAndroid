package com.mmfcommon.zhuge;

import com.mmfcommon.activity.MmfCommonAppBaseApplication;
import com.mmfcommon.bean.SalonBaseBean;
import com.mmfcommon.bean.ServiceBean;
import com.mmfcommon.bean.StylistBaseBean;
import com.mmfcommon.bean.UserInfoBean;
import com.unit.common.utils.TimeUtils;
import com.zhuge.analysis.stat.ZhugeSDK;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by titanic on 2015-06-19.
 */
public class BaseZhuGeBusinessStatistics {
    protected   final static String createTime="创建时间";
    protected  final static String userLocation="用户坐标";
    protected  final static String userGender="用户性别";

    protected  final static String salonName="店铺名称";
//    protected  final static String salonRate="店铺评分";

    protected  final static String stylistName="发型师名称";
    protected  final static String stylistGender="发型师性别";
    protected  final static String stylistRate="发型师评分";
    protected  final static String stylistWorkCount="发型师作品数";

    protected  final static String serviceName="服务名称";
    protected  final static String serviceMinPrice="服务最低价格";
    protected  final static String serviceMaxPrice="服务最高价格";
    protected  final static String serviceStorePrice="服务店面价格";
    protected  final static String serviceTime="预约服务时间";
    protected  final static String servicePayType="预约支付类型";

    protected  final static String workName="作品名称";
    protected  final static String workRate="作品点赞数";
    protected  final static String workCommentCount="作品评论数";


    public  static void BookService(SalonBaseBean salonBaseBean,ServiceBean serviceBean,UserInfoBean userInfoBean,StylistBaseBean stylistBaseBean,String bookTime,String locationName){
        BookBaseService(salonBaseBean, serviceBean, userInfoBean, stylistBaseBean, bookTime, "", "预约服务",locationName);
    }

    public static void BookServiceSuccess(SalonBaseBean salonBaseBean,ServiceBean serviceBean,UserInfoBean userInfoBean,StylistBaseBean stylistBaseBean,String bookTime,String payType,String locationName){
        BookBaseService(salonBaseBean, serviceBean, userInfoBean, stylistBaseBean, bookTime, payType, "预约服务成功付款",locationName);
    }

    public static void BookServiceFaild(SalonBaseBean salonBaseBean,ServiceBean serviceBean,UserInfoBean userInfoBean,StylistBaseBean stylistBaseBean,String bookTime,String payType,String locationName){
        BookBaseService(salonBaseBean, serviceBean, userInfoBean, stylistBaseBean, bookTime, payType, "预约服务取消付款",locationName);
    }

    public static void BookServiceReturn(SalonBaseBean salonBaseBean,ServiceBean serviceBean,UserInfoBean userInfoBean,StylistBaseBean stylistBaseBean,String bookTime,String payType,String locationName){
        BookBaseService(salonBaseBean, serviceBean, userInfoBean, stylistBaseBean, bookTime, payType, "预约服务申请退款",locationName);
    }

    public static void BookBaseService(SalonBaseBean salonBaseBean, ServiceBean serviceBean, UserInfoBean userInfoBean, StylistBaseBean stylistBaseBean, String bookTime, String payType, String bookServiceType,String locationName){
        try {
            JSONObject eventObject = ZhuGeBaseJSONObject.GetInstance(locationName);
            if (salonBaseBean != null)
                eventObject.put(salonName, salonBaseBean.getSalonName());
            if (userInfoBean != null)
                eventObject.put(userGender, userInfoBean.getGender());

            if(serviceBean!=null) {
                eventObject.put(serviceName, serviceBean.getServiceName());
                eventObject.put(serviceMinPrice, serviceBean.getMin_price());
                eventObject.put(serviceMaxPrice, serviceBean.getMax_price());
                eventObject.put(serviceStorePrice, serviceBean.getMaxStorePrice());
            }

            if(stylistBaseBean!=null) {
                eventObject.put(stylistName, stylistBaseBean.getStylistName());
                eventObject.put(stylistGender, stylistBaseBean.getGender());
                eventObject.put(stylistRate, stylistBaseBean.getStars());
            }

            eventObject.put(serviceTime,bookTime);
            eventObject.put(servicePayType,payType);

            ZhugeSDK.getInstance().onEvent(MmfCommonAppBaseApplication.getAppContext(), bookServiceType, eventObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void BaseSalon(SalonBaseBean salonBaseBean, UserInfoBean userInfoBean, String favType,String locationName){
        try {
            JSONObject eventObject = ZhuGeBaseJSONObject.GetInstance(locationName);
            if (salonBaseBean != null)
                eventObject.put(salonName, salonBaseBean.getSalonName());
            if (userInfoBean != null)
                eventObject.put(userGender, userInfoBean.getGender());

            ZhugeSDK.getInstance().onEvent(MmfCommonAppBaseApplication.getAppContext(), favType, eventObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void BaseStylist(SalonBaseBean salonBaseBean, UserInfoBean userInfoBean, StylistBaseBean stylistBaseBean ,ServiceBean serviceBean,String favType,String locationName){
        try {
            JSONObject eventObject = ZhuGeBaseJSONObject.GetInstance(locationName);
            if (salonBaseBean != null)
                eventObject.put(salonName, salonBaseBean.getSalonName());
            if (userInfoBean != null)
                eventObject.put(userGender, userInfoBean.getGender());

            if(serviceBean!=null) {
                eventObject.put(serviceName, serviceBean.getServiceName());
                eventObject.put(serviceMinPrice, serviceBean.getMin_price());
                eventObject.put(serviceMaxPrice, serviceBean.getMax_price());
                eventObject.put(serviceStorePrice, serviceBean.getMaxStorePrice());
            }

            if(serviceBean!=null) {
                eventObject.put(stylistName, stylistBaseBean.getStylistName());
                eventObject.put(stylistGender, stylistBaseBean.getGender());
                eventObject.put(stylistRate, stylistBaseBean.getStars());
            }
            ZhugeSDK.getInstance().onEvent(MmfCommonAppBaseApplication.getAppContext(), favType, eventObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void BaseWork(SalonBaseBean salonBaseBean,UserInfoBean userInfoBean,StylistBaseBean stylistBaseBean,String favType,String locationName){
        try {
            JSONObject eventObject = ZhuGeBaseJSONObject.GetInstance(locationName);
            if (salonBaseBean != null)
                eventObject.put(salonName, salonBaseBean.getSalonName());
            if (userInfoBean != null)
                eventObject.put(userGender, userInfoBean.getGender());

            if(stylistBaseBean!=null) {
                eventObject.put(stylistName, stylistBaseBean.getStylistName());
                eventObject.put(stylistGender, stylistBaseBean.getGender());
                eventObject.put(stylistRate, stylistBaseBean.getStars());
            }

//            eventObject.put(workName,workEntity.getStylist_works_name());
//            eventObject.put(workRate,workEntity.getFavourites());
//            eventObject.put(workCommentCount,workEntity.getComments());

            ZhugeSDK.getInstance().onEvent(MmfCommonAppBaseApplication.getAppContext(), favType, eventObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

   public static class ZhuGeBaseJSONObject extends JSONObject{
       private ZhuGeBaseJSONObject(){

       }

       public static ZhuGeBaseJSONObject GetInstance(String locationName){
           ZhuGeBaseJSONObject jsonObject=new ZhuGeBaseJSONObject();
           try {
               jsonObject.put(createTime, TimeUtils.formatTime(new Date().getTime(), ""));
               jsonObject.put(userLocation,locationName);
           } catch (JSONException e) {
               e.printStackTrace();
           }
           return jsonObject;
       }
   }


}
