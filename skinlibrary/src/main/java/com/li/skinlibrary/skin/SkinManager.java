package com.li.skinlibrary.skin;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import com.li.skinlibrary.base.SkinActivity;
import com.li.skinlibrary.skin.attribute.SkinView;
import com.li.skinlibrary.skin.call.SkinChangeCallBack;
import com.li.skinlibrary.skin.config.SkinLoadUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author li
 * 版本：1.0
 * 创建日期：2020/6/30 09
 * 描述：
 */
public class SkinManager {
    private static volatile SkinManager mInstance;
    private Context mContext;

    private SkinResource mSkinResource;
    private Map<SkinChangeCallBack,List<SkinView>> mViewMap = new HashMap<>();

    private SkinManager() {

    }

    public static SkinManager getInstance() {
        if (mInstance == null) {
            synchronized (SkinManager.class) {
                if (mInstance == null) {
                    mInstance = new SkinManager();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        this.mContext = context.getApplicationContext();
        //打开应用会立刻初始化如果有皮肤加载需要初始化SkinResource
        SkinLoadUtils.getInstance().init(context);
        String currentSkinPath = SkinLoadUtils.getInstance().getLoadPath();
        File file = new File(currentSkinPath);
        if (!file.exists()){
            SkinLoadUtils.getInstance().cleanLoadPath();
            return ;
        }
        String mPackageName = context.getPackageManager().getPackageArchiveInfo(currentSkinPath, PackageManager.GET_ACTIVITIES).packageName;
        if (TextUtils.isEmpty(mPackageName)){
            SkinLoadUtils.getInstance().cleanLoadPath();
            return ;
        }
        mSkinResource = new SkinResource(mContext, currentSkinPath);
    }


    public void loadResource(String path) {
//       判断 path 文件是否存在
        File file = new File(path);
        if (!file.isFile()){
            SkinLoadUtils.getInstance().cleanLoadPath();
            return;
        }
//        判断是否是APK文件，包名是否存在
        String mPackageName = mContext.getPackageManager().getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES).packageName;
        if (TextUtils.isEmpty(mPackageName)){
            SkinLoadUtils.getInstance().cleanLoadPath();
            return ;
        }
//       判断是否是已经载的的地址
        String currentSkinPath = SkinLoadUtils.getInstance().getLoadPath();
        if (!TextUtils.isEmpty(currentSkinPath)){
            if (currentSkinPath.equals(path)){
                return;
            }
        }
        //初始化资源
        mSkinResource = new SkinResource(mContext, path);
//       加载皮肤
        loadActivitySkin();
//       保存皮肤
        saveSkinLoadPath(path);
    }

    private void loadActivitySkin() {
        Set<SkinChangeCallBack> skinChangeCallBacks =mViewMap.keySet();
        for (SkinChangeCallBack callBack : skinChangeCallBacks) {
            List<SkinView> skinViews = mViewMap.get(callBack);
            if (skinViews!=null){
                for (SkinView skinView : skinViews) {
                    skinView.loadSkin();
                }
                callBack.skinChange(mSkinResource);
            }
        }
    }

    private void saveSkinLoadPath(String path) {
        SkinLoadUtils.getInstance().saveLoadPath(path);
    }


    public List<SkinView> getSkinViews(SkinChangeCallBack callBack) {
        return mViewMap.get(callBack);
    }

    public void registerSkinView(SkinChangeCallBack skinChangeCallBack ,List<SkinView> skinViews) {
        mViewMap.put(skinChangeCallBack,skinViews);
    }

    public SkinResource getSkinResource() {
        return mSkinResource;
    }
    /**
     * @description 恢复默认
     * @param
     */
    public void recoverDefault() {
        String currentSkinPath = SkinLoadUtils.getInstance().getLoadPath();
        if (TextUtils.isEmpty(currentSkinPath)){
            return;
        }
        String mPackageResourcePath = mContext.getPackageResourcePath();
        SkinLoadUtils.getInstance().cleanLoadPath();
        mSkinResource = new SkinResource(mContext,mPackageResourcePath);
        loadActivitySkin();
    }

    /**
     * @description 检测是否需要换肤
     * @param 
     */
    public void checkSkinView(SkinView skinView) {
        //保存的皮肤就换肤
        if (!TextUtils.isEmpty(SkinLoadUtils.getInstance().getLoadPath())) {
//            Log.e("TAG", "checkSkinView: -->"+SkinLoadUtils.getInstance().getLoadPath());
            skinView.loadSkin();
        }
    }
    /**
     * @description 解绑
     * @param
     */
    public void unBinderSkinActivity(SkinChangeCallBack skinChangeCallBack) {
        mViewMap.remove(skinChangeCallBack);
    }
}
