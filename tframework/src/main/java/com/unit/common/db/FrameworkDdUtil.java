package com.unit.common.db;

import android.content.Context;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.util.LogUtils;
import com.unit.common.application.FrameBaseApplication;
import com.unit.common.config.CommonSetting;
import com.unit.common.utils.LogOutputUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingkong on 13-9-4.
 */
public class FrameworkDdUtil {

    private static String DBName = "tframeworkapps.db";//默认数据库名称

    public static String getTAG() {
        return TAG;
    }

    public static String getDBName() {
        return DBName;
    }

    static String TAG = FrameworkDdUtil.class.getSimpleName().toString();
    static DbUtils dbUtils = null;

    /**
     * 获取 数据库对象
     *
     * @param context
     * @return
     */
    public static DbUtils getDbUtils(Context context) {
        try {

            if (dbUtils == null) {
                dbUtils = DbUtils.create(context, CommonSetting.downloadDirectory.getAbsolutePath(), DBName);
                dbUtils.configAllowTransaction(true);
                dbUtils.configDebug(true);
            }
            return dbUtils;
        } catch (Exception e) {
            LogOutputUtils.e(TAG, e.toString());
            return null;
        }

    }

    public static void setDBName(String DbName) {
        DBName = DbName;
    }


    public static void DelInfoFromeDb(Class mycalss, WhereBuilder whereBuilder) {
        try {
            DbUtils frameworkDdUtil = FrameworkDdUtil.getDbUtils(FrameBaseApplication.appContext);

            frameworkDdUtil.delete(mycalss, whereBuilder);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void DelInfoFromeDb(Object myObject) {
        try {
            DbUtils frameworkDdUtil = FrameworkDdUtil.getDbUtils(FrameBaseApplication.appContext);

            frameworkDdUtil.delete(myObject);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void AddInfoToDb(Object myObject) {
        try {
            DbUtils frameworkDdUtil = FrameworkDdUtil.getDbUtils(FrameBaseApplication.appContext);

            frameworkDdUtil.saveOrUpdate(myObject);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void AddInfoListToDb(List<Class> myclassList) {
        try {
            DbUtils frameworkDdUtil = FrameworkDdUtil.getDbUtils(FrameBaseApplication.appContext);

            frameworkDdUtil.saveAll(myclassList);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static <T> List<T> GetAllInfoFromDb(Class myclass) {
        List<T> list = null;

        try {
            DbUtils frameworkDdUtil = FrameworkDdUtil.getDbUtils(FrameBaseApplication.appContext);
            list = frameworkDdUtil.findAll(Selector.from(myclass));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

    public static <T> T GetInfoBykeyFromDb(Selector selector) {
        List<T> list = new ArrayList<T>();

        try {
            DbUtils frameworkDdUtil = FrameworkDdUtil.getDbUtils(FrameBaseApplication.appContext);
            list = frameworkDdUtil.findAll(selector);
            if (list.size() > 0) {
                return list.get(0);
            }
            return list.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void testDB(Context context) {


        Parent parent = new Parent();
        parent.setPid(1);


        Child child = new Child();

        child.setCid(11);
        parent.setCid(child.getCid());
        child.setParent(parent);

        Child child2 = new Child();
        child2.setCid(22);
        parent.setCid(child2.getCid());
        child2.setParent(parent);


        try {
            DbUtils dbUtils = DbUtils.create(context);
            dbUtils.configAllowTransaction(true);
            dbUtils.configDebug(true);
            dbUtils.save(parent);
            dbUtils.save(child);
//            dbUtils.saveBindingId(child2);
            int parentId = 1;
            LogUtils.e(TAG + " parentId:" + parentId);
            //这个查找是从哪个表查找
            List<Child> children = dbUtils.findAll(Selector.from(Child.class).where(WhereBuilder.b("parentId", "=", 1)));//.where(WhereBuilder.b("name", "=", "child' name")));
            LogUtils.e(TAG + " parentId:" + parentId);
            if (children.size() > 0) {
                LogUtils.e(TAG + " size:" + children.size());
                for (Child temp : children) {
                    LogOutputUtils.e(TAG + "testDb child:", temp.toString());
                }

            }

        } catch (Exception e) {
            LogOutputUtils.e(TAG, e.toString());
        }


    }

}
