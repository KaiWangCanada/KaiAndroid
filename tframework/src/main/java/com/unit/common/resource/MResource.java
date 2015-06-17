package com.unit.common.resource;

import android.content.Context;
import android.util.Log;

public class MResource {
    //className is id, string, layout, etc.
    //name is resource name.
    public static int getIdByName(Context context, String className, String name) {  
        String packageName = context.getPackageName();
//        Log.v("packageName", packageName);
        Class r = null;  
        int id = 0;  
        try {  
            r = Class.forName(packageName + ".R");  
  
            Class[] classes = r.getClasses();  
            Class desireClass = null;  
  
            for (int i = 0; i < classes.length; ++i) {
//                Log.v("class name", classes[i].getName().split("\\$")[1]);
//                Log.v(classes[i].getName(), String.valueOf(classes[i].getField(name).getInt(desireClass)));
                if (classes[i].getName().split("\\$")[1].equals(className)) {
                    desireClass = classes[i];
                    break;  
                }  
            }  
  
            if (desireClass != null)  
                id = desireClass.getField(name).getInt(desireClass);  
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
        } catch (IllegalArgumentException e) {  
            e.printStackTrace();  
        } catch (SecurityException e) {  
            e.printStackTrace();  
        } catch (IllegalAccessException e) {  
            e.printStackTrace();  
        } catch (NoSuchFieldException e) {  
            e.printStackTrace();  
        }  
  
        return id;  
    }  
}  