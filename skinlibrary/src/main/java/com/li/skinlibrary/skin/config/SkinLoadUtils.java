package com.li.skinlibrary.skin.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * @author li
 * 版本：1.0
 * 创建日期：2020/6/30 18
 * 描述：皮肤保存加载Utils
 */
public  class SkinLoadUtils {
    private static volatile SkinLoadUtils mInstance;
    private Context mContext;
    public SkinLoadUtils() {
    }

    public static SkinLoadUtils getInstance() {
        if (mInstance==null){
            synchronized (SkinLoadUtils.class){
                if (mInstance==null){
                    mInstance = new SkinLoadUtils();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context){
        this.mContext = context;
    }

    /**
     * @description 保存路径
     * @param
     */
    public void saveLoadPath(String path){
        mContext.getSharedPreferences(SkinConfig.SKIN_CONFIG_PATH,Context.MODE_PRIVATE).edit()
                .putString(SkinConfig.SKIN_LOAD_PATH,path).commit();
    }
    /**
     * @description 返回当前皮肤路径
     * @param 
     */
    public String getLoadPath(){
      return mContext.getSharedPreferences(SkinConfig.SKIN_CONFIG_PATH,Context.MODE_PRIVATE)
              .getString(SkinConfig.SKIN_LOAD_PATH,"");
    }
    /**
     * @description 清除路径
     * @param 
     */
    public void cleanLoadPath (){
        mContext.getSharedPreferences(SkinConfig.SKIN_CONFIG_PATH,Context.MODE_PRIVATE).edit()
                .putString(SkinConfig.SKIN_LOAD_PATH,"").commit();
    }
}
