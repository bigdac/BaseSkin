package com.li.skinlibrary.skin.attribute;

import android.view.View;

/**
 * @author li
 * 版本：1.0
 * 创建日期：2020/6/30 08
 * 描述：皮肤资源
 */
public class SkinAttr {
    private String mResName;
    private SkinType mSkinType;

    public SkinAttr(String mResName, SkinType mSkinType) {
        this.mResName = mResName;
        this.mSkinType = mSkinType;
    }

    public void loadSkin(View view) {
        mSkinType.loadSkin(view,mResName);
    }
}
