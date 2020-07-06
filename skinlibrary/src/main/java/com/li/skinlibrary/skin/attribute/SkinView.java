package com.li.skinlibrary.skin.attribute;

import android.view.View;

import java.util.List;

/**
 * @author li
 * 版本：1.0
 * 创建日期：2020/6/30 08
 * 描述：皮肤view
 */
public class SkinView {
    private View mView;
    private List<SkinAttr> mSkinAttrs ;

    public SkinView(View mView, List<SkinAttr> mSkinAttrs) {
        this.mView = mView;
        this.mSkinAttrs = mSkinAttrs;
    }

    public void loadSkin(){
        for (SkinAttr skinAttr : mSkinAttrs) {
            skinAttr.loadSkin(mView);
        }
    }
}
