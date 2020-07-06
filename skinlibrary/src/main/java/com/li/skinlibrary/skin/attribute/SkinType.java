package com.li.skinlibrary.skin.attribute;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.li.skinlibrary.skin.SkinManager;
import com.li.skinlibrary.skin.SkinResource;

/**
 * @author li
 * 版本：1.0
 * 创建日期：2020/6/30 08
 * 描述：皮肤类别
 */
public enum  SkinType {
    TEXT_COLOR("textColor"){
        @Override
        public void loadSkin(View view, String resName) {
            SkinResource skinResource = getSkinResource();
            ColorStateList color = skinResource.getColorByName(resName);
            if (color==null){
                return;
            }
            TextView textView = (TextView) view;
            textView.setTextColor(color);
        }
    },BACKGROUND("background") {
        @Override
        public void loadSkin(View view, String resName) {
            SkinResource skinResource = getSkinResource();
            Drawable drawable = skinResource.getDrawableByName(resName);
            if (drawable!=null){
                view.setBackground(drawable);
                return;
            }
            ColorStateList color = skinResource.getColorByName(resName);
            if (color!=null){
                view.setBackgroundColor(color.getDefaultColor());
            }

        }
    },SRC("src") {
        @Override
        public void loadSkin(View view, String resName) {
            SkinResource skinResource = getSkinResource();
            Drawable drawable = skinResource.getDrawableByName(resName);
            if (drawable!=null){
                ImageView imageView = (ImageView) view;
                imageView.setImageDrawable(drawable);
            }
        }
    },TEXT("text"){
        @Override
        public void loadSkin(View view, String resName) {
            SkinResource skinResource = getSkinResource();
            String text = skinResource.getTextRes(resName);
            if (!TextUtils.isEmpty(text)){
                if (view instanceof TextView){
                    ((TextView) view).setText(text);
                }
            }
        }
    };

    protected  SkinResource getSkinResource(){
        return SkinManager.getInstance().getSkinResource();
    };

    private String mResName;
    SkinType(String resName) {
        this.mResName = resName;
    }

    public abstract void loadSkin(View view, String mResName)  ;

    public String getResName() {
        return mResName;
    }
}
