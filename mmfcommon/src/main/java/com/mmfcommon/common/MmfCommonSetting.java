package com.mmfcommon.common;

import com.meimeifa.base.common.Constants;

/**
 * Created by kingkong on 15/3/23.
 */
public class MmfCommonSetting {
    //推送是根据APP_Key来判别的

    public static String Host = "https://api.meimeifa.com/";
    public static String Host_Version = Constants.Host_Version;
    public static String Host_Head_Key = "X-MMF-Application-Key";
    public static String Host_Head_Language = "X-MMF-Language";
    public static String Host_Head_Sign = "X-MMF-Request-Sign";
    public static String Host_Head_Token = "X-MMF-Session-Token";
    public static String isRequestType="";  //那种请求方式，

    public static final String isRequestType_SongSen="isRequestType_SongSen";//如果是松森平台，则对api和时间戳做了md5；
    public static final String isRequestType_Dewu="isRequestType_Dewu"; //如果是德武平台则没有对api和时间戳做md5

    public static final String Demo = "demo";
    public static final String Test = "test";

    public static String EN_Language = "en";
    public static String ZH_CN_Language = "zh_cn";
    public static String Language_Type = "";
    public static final String Flag_Push = "Flag_Push";//推送标记

    //用户版测试版 开发使用
    public static String CLIENT_APP_HOST_TEST = "http://upay.meimeifa.cn/";
    public static String CLIENT_APP_KEY_TEST = "fw_test";
    public static String CLIENT_APP_SECRET_TEST = "MeiMeiFafw_test123456";

    //用户版演示版 oa员工使用
    public static String CLIENT_APP_HOST_Demo = "http://api.meimeifa.com.cn/";
    public static String CLIENT_APP_KEY_Demo = "meimeifa_android_enterprise";
    public static String CLIENT_APP_SECRET_Demo = "f8f0e587ef8ca39fbee68ba3aa669e2e9dbc955e";

    //用户版正式版
    public static String CLIENT_APP_HOST = "https://u.meimeifa.com/";
    public static String CLIENT_APP_KEY = "meimeifa_android";
    public static String CLIENT_APP_SECRET = "49b3063e59cba6bb1c3f7bbb2add96ec";


    //用户版国际版正式版
    public static String CLIENT_EN_APP_HOST = "https://u.mmfsalon.com/";
    public static String CLIENT_EN_APP_KEY = "mmf_en_android";
    public static String CLIENT_EN_APP_SECRET = "0901b154f2abf36e295c95b9948f97be10de8262";

    //用户版国际版演示版  推送是根据APP_Key来判别的
    public static String CLIENT_EN_APP_HOST_DEMO = "http://api.meimeifa.ca/";
    public static String CLIENT_EN_APP_KEY_DEMO = "meimeifa_android_enterprise";
    public static String CLIENT_EN_APP_SECRET_DEMO = "bf52e9cd2a823232baf8890c255117c900d612b7";
    //用户版国际版测试版
    public static String CLIENT_EN_APP_HOST_TEST = "http://api.en.meimeifa.cn/";
    public static String CLIENT_EN_APP_KEY_TEST = "mmf_en_android";
    public static String CLIENT_EN_APP_SECRET_TEST = "0901b154f2abf36e295c95b9948f97be10de8262";

    //商家版正式版
    public static String Store_APP_HOST = "https://s.meimeifa.com/";
    public static String Store_APP_KEY = "meimeifa_salon_android";
    public static String Store_APP_SECRET = "c59130d24fdf29c88b4ec5c9f49da6e4fbfa2a92";

    //商家版测试版
    public static String Store_APP_HOST_TEST = "https://s.meimeifa.cn/";
    public static String Store_APP_KEY_TEST = "fw_test";
    public static String Store_APP_SECRET_TEST = "MeiMeiFafw_test123456";

    //商家版演示版版
    public static String Store_APP_HOST_Demo = "http://s.meimeifa.com.cn/";
    public static String Store_APP_KEY_Demo = "salon_android_enterprise";
    public static String Store_APP_SECRET_Demo = "3c2c9aa2bd73e9779422e61bf8bacd4aef44faea";

    //OA测试版
    public static String OA_APP_HOST_TEST = "http://fw-oa.meimeifa.cn/index.php";

    //OA正式版
    public static String OA_APP_HOST = "http://fw-oa.meimeifa.com/index.php";
    public static String OA_APP_KEY = "android01";
    public static String OA_APP_SECRET = "!@#SDFDSF#$!SDF@RSSDF";


    public static final int PageSize = 20;        // 每页请求数
    public static String SALON_IMAGE_PATH = "SALON_IMAGE_PATH";

    public static final String Area_Code = "area_code";

    //-------------------------------------http url---------------------------------------------------
    public static String Host_Login = Host + Host_Version + "user/login";
    public static String Host_push_binding_save = Host + Host_Version + "push/binding/save";
    public static String Host_push_binding_delete = Host + Host_Version + "push/binding/delete";
    public static String Host_token_get = Host + Host_Version + "token/refresh";
    public static String Host_Check_Version = Host + Host_Version + "android/version/latest";

    public static String Host_Common_Check_Update = "http://fw-oa.meimeifa.com/version-a.php";

    public static String Host_Area_Code_Get = Host + Host_Version + "areaCode/list";

    public static String API_GET_Bind_push = "";
    public static String API_GET_Delete_push = "";

    //-------------------------------------http请求参数key名称---------------------------------------------------

    public static final String Http_Request_Key_Api = "api";
    public static final String SALON_ID = "salon_id";
    public static final String SERVICE_INFO = "service";
    public static final String SALON_INFO = "salon";
    public static final String Http_Request_Key_time_id = "time_id";
    public static final String Http_Request_Key_appoint_time = "appoint_time";
    public static final String Http_Request_Key_appoint_id = "appoint_id";
    public static final String Http_Request_Key_type = "type";
    public static final String Http_Request_Key_name = "name";
    public static final String Http_Request_Key_handset = "handset";
    public static final String Http_Request_Key_password = "password";
    public static final String Http_Request_Key_old_password = "old_password";
    public static final String Http_Request_Key_new_password = "new_password";

    public static final String Http_Request_Key_lng = "lng";
    public static final String Http_Request_Key_lat = "lat";
    public static final String Http_Request_Key_province_id = "province_id";
    public static final String Http_Request_Key_city_id = "city_id";
    public static final String Http_Request_Key_district_id = "district_id";
    public static final String Http_Request_Key_town_id = "town_id";
    public static final String Http_Request_Key_business_id = "business_id";
    public static final String Http_Request_Key_service_id = "service_id";
    public static final String Http_Request_Key_km = "km";
    public static final String Http_Request_Key_keywords = "keywords";

    public static final String Http_Request_Key_comments = "comments";
    public static final String Http_Request_Key_message = "message";
    public static final String Http_Request_Key_message_id = "message_id";
    public static final String Http_Request_Key_code = "code";
    public static final String Http_Request_Key_area_code = "area_code";
    public static final String Http_Request_Key_upload = "files[]";
    public final static String Http_Request_Key_tokens = "token";
    public final static String Http_Request_Key_user_id = "user_id";
    public final static String Http_Request_Key_order_id = "order_id";
    public final static String Http_Request_Key_salon_id = "salon_id";
    public final static String Http_Request_Key_stylist_id = "stylist_id";
    public final static String Http_Request_Key_fftd_stars = "fftd_stars";
    public final static String Http_Request_Key_mfxg_stars = "mfxg_stars";
    public final static String Http_Request_Key_dphj_stars = "dphj_stars";
    public final static String Http_Request_Key_content = "content";
    public final static String Http_Request_Key_imgs = "imgs";
    public final static String Http_Request_Key_is_anonymous = "is_anonymous";
    public final static String Http_Request_Key_app_package = "app_package_name";

    public final static String Http_Request_Key_offset = "offset";
    public final static String Http_Request_Key_limit = "limit";

    public static final String Http_Request_Key_push_id = "push_id";
    //    public static final String Http_Request_Key_service_id = "service_id";
    public static final String Http_Request_Key_salon_service_id = "salon_service_id";

    public static final String Http_Request_Key_id = "id";
    public final static String Http_Request_Key_stylist_works_id = "stylist_works_id";
    public final static String Http_Request_Key_pic_id = "stylist_works_item_id";
    public final static String Http_Request_Key_order = "order";
    public final static String Http_Request_Key_stylist_works_tag_id = "stylist_works_tag_id";
    public final static String Http_Request_Key_userScheduleTime = "userScheduleTime";

//    public final static String Http_Request_Key_style_ids = "style_ids";

    public final static String Http_Request_Key_status = "status";
    public static String Http_Request_Key_Update_name = "name";

    public static final String USER_INFO_FILE = "user";
    public static final String USER_LOGIN_KEY = "user_info";
    public static final String USER_TOKEN_KEY = "token";

    public static final String PushBind = "push";

    public static final int HTTP_SUCCESS_CODE = 1;
    public static final int HTTP_NoData_CODE = -1;

    //OA用到101,102,103,其他美美发项目只用到2
    public static final int TOKEN_ERROR = 2;
    public static final int TOKEN_PASSWORD_ERROR = 1001; //调用获取token接口发现该接口的帐号密码错误的code

    public static final String BroadcastTabDoubleClick = Constants.BroadcastTabDoubleClick;
    public static final String BroadcastTabDoubleClick_BottomIndex = "BottomIndex";

    public static final String MultiSelectable = "multi_selectable";
    public static final String Title = "title";
    public static final String Item_Data = "item_data";
    public static final String Selected_Item = "selected_item";

    public static final String LOGIN_API = "ucenter.user.login";
    public static final String TOKEN_GET_API = "system.token.get";
    public static final String API_KEY_ACCOUNT = "account";
    public static final String PREF_KEY_LOGIN_PARAMS = "loginParams";
    public static final String API_KEY_PASSWORD = "password";
    public static final String REGION_DB = "region.db";

    //-------------------------------------http解析参数的值------------------------------------------
    public final static String Http_Responce_Key_price = "price";
    public final static String Http_Responce_Key_messages = "messages";
    public final static String Http_Response_Key_order_code = "order_code";

    public final static String PREFERENCE_DEFAULT = "preference";
    public final static String PREFERENCE_KEY_CITY = "city";
    public final static String PREFERENCE_KEY_DISTRICT = "district";
    public final static String PREFERENCE_KEY_LAT = "lat";
    public final static String PREFERENCE_KEY_LNG = "lng";
    public final static String PREFERENCE_KEY_HISTORY_CITY = "history_city";

    //OA用到101,102,103,其他美美发项目只用到2
    public final static int TOKEN_OUT_OF_DATE = 101;
    public final static int TOKEN_NULL = 102;
    public final static int TOKEN_GONE = 103;

    private String SongSenHost = "";

}
