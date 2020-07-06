package com.li.skinlibrary.skin;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import java.lang.reflect.Method;

/**
 * @author li
 * 版本：1.0
 * 创建日期：2020/6/30 09
 * 描述：皮肤资源
 */
public class SkinResource {
    private Resources mResource;
    private String mPackageName;
    public SkinResource(Context context ,String path) {
//        获取本地资源
        try {
            Resources superRes = context.getResources();
            AssetManager asset  =AssetManager.class.newInstance();
            Method method  = AssetManager.class.getDeclaredMethod("addAssetPath",String.class);
            method.setAccessible(true);
            method.invoke(asset,path);
            mResource = new Resources(asset,superRes.getDisplayMetrics(),superRes.getConfiguration());
            mPackageName = context.getPackageManager().getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES).packageName;
        } catch ( Exception e) {
            e.printStackTrace();
        }

    }

    public ColorStateList getColorByName(String resName) {
        int resId = mResource.getIdentifier(resName,"color",mPackageName);
        ColorStateList  colorStateList = null;
        try {
            colorStateList = mResource.getColorStateList(resId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return colorStateList;
    }

    public Drawable getDrawableByName(String resName) {
        int resId = mResource.getIdentifier(resName,"drawable",mPackageName);
        Drawable drawable = null;
        try {
            drawable = mResource.getDrawable(resId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drawable;
    }

    public String getTextRes(String resName) {
        int resId = mResource.getIdentifier(resName,"string",mPackageName);
        String text = null;
        try {
            text = mResource.getString(resId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }
}
