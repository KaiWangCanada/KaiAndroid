package com.mmfcommon.utils.storage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mmfcommon.bean.CityBean;
import com.mmfcommon.bean.DistrictBean;
import com.mmfcommon.bean.ProvinceBean;
import com.mmfcommon.bean.StreetBean;
import com.mmfcommon.common.MmfCommonSetting;
import com.unit.common.utils.LogOutputUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 地区数据库管理类
 * <p/>
 * Created by 黄东鲁 on 15/1/23.
 */
public class RegionDatabaseManager {


    /**
     * 通过此方法只能拿到省市,因为每个区的数据量太大
     * @return
     */
    public static List<ProvinceBean> getAllProvinces(Context context) {
        List<ProvinceBean> provinces = new ArrayList<>();
        SQLiteDatabase database = getDatabase(context);
        String sql = "SELECT * FROM def WHERE level=1;";
        Cursor mCursor = database.rawQuery(sql, null);
        long startTime = System.currentTimeMillis();
        if (mCursor != null && mCursor.moveToFirst()) {
            do {
                ProvinceBean province = new ProvinceBean();
                province.setId(mCursor.getString(mCursor.getColumnIndex("id")));
                province.setName(mCursor.getString(mCursor.getColumnIndex("name")));
                province.setFullname(mCursor.getString(mCursor.getColumnIndex("fullname")));
                // sql = "select * from def WHERE id in (select toid from link where level=2 and fromid=?);";
                sql = "SELECT def.*, link.fromid, link.toid FROM def INNER JOIN link ON fromid=? and def.id=link.toid and def.level=2;";

                Cursor cityCursor = database.rawQuery(sql, new String[]{province.getId()});
                if (cityCursor != null && cityCursor.moveToFirst()) {
                    do {
                        CityBean city = new CityBean();
                        city.setId(cityCursor.getString(cityCursor.getColumnIndex("id")));
                        city.setName(cityCursor.getString(cityCursor.getColumnIndex("name")));
                        city.setFullName(cityCursor.getString(cityCursor.getColumnIndex("fullname")));
                        province.getCitys().add(city);
                    } while (cityCursor.moveToNext());
                    cityCursor.close();
                }
                provinces.add(province);
            } while (mCursor.moveToNext());
            mCursor.close();
        }
        long dueTime = System.currentTimeMillis() - startTime;
        LogOutputUtils.d(">>>>", new SimpleDateFormat("mm分ss秒").format(new Date(dueTime)));
        return provinces;
    }

    public static  List<CityBean> getAllCity(Context context) {
        List<CityBean> cityItems = new ArrayList<>();
        SQLiteDatabase database = getDatabase(context);
        String sql = "SELECT * FROM def WHERE level=2;";
        Cursor mCursor = database.rawQuery(sql, null);
        long startTime = System.currentTimeMillis();
        if (mCursor != null && mCursor.moveToFirst()) {
            do {
                CityBean cityBean = new CityBean();
                cityBean.setId(mCursor.getString(mCursor.getColumnIndex("id")));
                cityBean.setName(mCursor.getString(mCursor.getColumnIndex("name")));
                cityBean.setFullName(mCursor.getString(mCursor.getColumnIndex("fullname")));
                // sql = "select * from def WHERE id in (select toid from link where level=2 and fromid=?);";
                cityItems.add(cityBean);
            } while (mCursor.moveToNext());
            mCursor.close();
        }
        long dueTime = System.currentTimeMillis() - startTime;
        LogOutputUtils.d(">>>>", new SimpleDateFormat("mm分ss秒").format(new Date(dueTime)));
        return cityItems;
    }

    public static List<DistrictBean> getDistrictsByCityName(Context context,String name) {
        List<DistrictBean> districts = new ArrayList<>();
        SQLiteDatabase database = getDatabase(context);
        String sql = "select id from def where name like ? and level=2;";
        Cursor mCursor = database.rawQuery(sql, new String[]{name});
        if (mCursor != null && mCursor.moveToFirst()) {
            String id = mCursor.getString(0);
            sql = "SELECT def.id, def.name, def.fullname FROM def INNER JOIN link ON fromid=? and def.id=link.toid and def.level=3;";
            Cursor areaCursor = database.rawQuery(sql, new String[]{id});
            if (areaCursor != null && areaCursor.moveToFirst()) {
                do {
                    DistrictBean district = new DistrictBean();
                    district.setId(areaCursor.getString(areaCursor.getColumnIndex("id")));
                    district.setName(areaCursor.getString(areaCursor.getColumnIndex("name")));
                    district.setFullName(areaCursor.getString(areaCursor.getColumnIndex("fullname")));
                    districts.add(district);
                } while (areaCursor.moveToNext());
            }
        }
        return districts;
    }

    public static  CityBean getCityByName(Context context,String name) {
        List<DistrictBean> districts = new ArrayList<>();
        SQLiteDatabase database = getDatabase(context);
        String sql = "select * from def where fullname like '%" + name + "%' and level=2;";
        Log.d(">>>>", "sql = " + sql);
        Cursor mCursor = database.rawQuery(sql, null);
        CityBean city = new CityBean();
        if (mCursor != null && mCursor.moveToFirst()) {
            city.setId(mCursor.getString(mCursor.getColumnIndex("id")));
            city.setName(mCursor.getString(mCursor.getColumnIndex("name")));
            city.setFullName(mCursor.getString(mCursor.getColumnIndex("fullname")));
        }
        Log.d(">>>>", "city = " + city.getId());
        return city;
    }

    public static String getCityIdByDistrictId(Context context,String id) {
        String sql = "select fromid from link where toid=? and level=3;";
        SQLiteDatabase database = getDatabase(context);
        Cursor city = database.rawQuery(sql, new String[]{id});
        String cityId = "";
        if (city != null && city.moveToFirst()) {
            cityId = city.getString(0);
        }
        return cityId;
    }

    public static String getProvinceIdByCityId(Context context,String id) {
        String sql = "select fromid from link where toid=? and level=2;";
        SQLiteDatabase database = getDatabase(context);
        Cursor city = database.rawQuery(sql, new String[]{id});
        String cityId = "";
        if (city != null && city.moveToFirst()) {
            cityId = city.getString(0);
        }
        return cityId;
    }

    public static List<DistrictBean> getDistrictsByCityId(Context context,String id) {
        List<DistrictBean> districts = new ArrayList<>();
        SQLiteDatabase database = getDatabase(context);
        String sql = "SELECT def.id, def.name, def.fullname FROM def INNER JOIN link ON fromid=? and def.id=link.toid and def.level=3;";
        Cursor areaCursor = database.rawQuery(sql, new String[]{id});
        if (areaCursor != null && areaCursor.moveToFirst()) {
            do {
                DistrictBean district = new DistrictBean();
                district.setId(areaCursor.getString(areaCursor.getColumnIndex("id")));
                district.setName(areaCursor.getString(areaCursor.getColumnIndex("name")));
                district.setFullName(areaCursor.getString(areaCursor.getColumnIndex("fullname")));
                // 街道
                List<StreetBean> streets = new ArrayList<>();
                sql = "SELECT def.id, def.name, def.fullname FROM def INNER JOIN link ON fromid=? and def.id=link.toid and def.level=4;";
                Cursor streetsCursor = database.rawQuery(sql, new String[]{district.getId()});
                if (streetsCursor != null && streetsCursor.moveToFirst()) {
                    do {
                        StreetBean streetBean = new StreetBean();
                        streetBean.setId(streetsCursor.getString(streetsCursor.getColumnIndex("id")));
                        streetBean.setName(streetsCursor.getString(streetsCursor.getColumnIndex("name")));
                        streetBean.setFullName(streetsCursor.getString(streetsCursor.getColumnIndex("fullname")));
                        streets.add(streetBean);
                    } while (streetsCursor.moveToNext());
                    streetsCursor.close();
                }
                district.setStreets(streets);
                districts.add(district);
            } while (areaCursor.moveToNext());
            areaCursor.close();
        }
        return districts;
    }

    public static SQLiteDatabase getDatabase(Context context) {
        AssetsDatabaseManager.initManager(context);
        AssetsDatabaseManager dbManager = AssetsDatabaseManager.getManager();
        return dbManager.getDatabase(MmfCommonSetting.REGION_DB);
    }

    public static DistrictBean getDistrictById(Context context,String id) {
        SQLiteDatabase database = getDatabase(context);
        String sql = "select * from def where id=? and level=3;";
        Cursor mCursor = database.rawQuery(sql, new String[]{id});
        DistrictBean district = null;
        if (mCursor != null && mCursor.moveToFirst()) {
            district = new DistrictBean();
            district.setId(mCursor.getString(mCursor.getColumnIndex("id")));
            district.setName(mCursor.getString(mCursor.getColumnIndex("name")));
            district.setFullName(mCursor.getString(mCursor.getColumnIndex("fullname")));
            List<StreetBean> streets = new ArrayList<>();
            sql = "SELECT def.id, def.name, def.fullname FROM def INNER JOIN link ON fromid=? and def.id=link.toid and def.level=4;";
            Cursor streetsCursor = database.rawQuery(sql, new String[]{district.getId()});
            if (streetsCursor != null && streetsCursor.moveToFirst()) {
                do {
                    StreetBean streetBean = new StreetBean();
                    streetBean.setId(streetsCursor.getString(streetsCursor.getColumnIndex("id")));
                    streetBean.setName(streetsCursor.getString(streetsCursor.getColumnIndex("name")));
                    streetBean.setFullName(streetsCursor.getString(streetsCursor.getColumnIndex("fullname")));
                    streets.add(streetBean);
                } while (streetsCursor.moveToNext());
                streetsCursor.close();
            }
            district.setStreets(streets);
            mCursor.close();
        }
        return district;
    }

    public static CityBean getCityById(Context context,String id) {
        SQLiteDatabase database = getDatabase(context);
        String sql = "select * from def where id=? and level=2;";
        Cursor mCursor = database.rawQuery(sql, new String[]{id});
        CityBean district = null;
        if (mCursor != null && mCursor.moveToFirst()) {
            district = new CityBean();
            district.setId(mCursor.getString(mCursor.getColumnIndex("id")));
            district.setName(mCursor.getString(mCursor.getColumnIndex("name")));
            district.setFullName(mCursor.getString(mCursor.getColumnIndex("fullname")));
            mCursor.close();
        }
        return district;
    }


    public static ProvinceBean getProvinceById(Context context,String id) {
        SQLiteDatabase database = getDatabase(context);
        String sql = "select * from def where id=? and level=1;";
        Cursor mCursor = database.rawQuery(sql, new String[]{id});
        ProvinceBean province = null;
        if (mCursor != null && mCursor.moveToFirst()) {
            province = new ProvinceBean();
            province.setId(mCursor.getString(mCursor.getColumnIndex("id")));
            province.setName(mCursor.getString(mCursor.getColumnIndex("name")));
            province.setFullname(mCursor.getString(mCursor.getColumnIndex("fullname")));
            mCursor.close();
        }
        return province;
    }

    public static StreetBean getStreeById(Context context,String id) {
        SQLiteDatabase database = getDatabase(context);
        String sql = "select * from def where id=? and level=4;";
        Cursor mCursor = database.rawQuery(sql, new String[]{id});
        StreetBean s = null;
        if (mCursor != null && mCursor.moveToFirst()) {
            s = new StreetBean();
            s.setId(mCursor.getString(mCursor.getColumnIndex("id")));
            s.setName(mCursor.getString(mCursor.getColumnIndex("name")));
            s.setFullName(mCursor.getString(mCursor.getColumnIndex("fullname")));
            mCursor.close();
        }
        return s;
    }
}
