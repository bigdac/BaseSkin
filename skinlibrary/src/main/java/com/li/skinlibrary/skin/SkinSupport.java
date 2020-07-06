package com.li.skinlibrary.skin;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import com.li.skinlibrary.skin.attribute.SkinAttr;
import com.li.skinlibrary.skin.attribute.SkinType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author li
 * 版本：1.0
 * 创建日期：2020/6/30 09
 * 描述：皮肤解析支持类
 */
public class SkinSupport {
    public static List<SkinAttr> getSkinAttrs(Context context, AttributeSet attrs) {
        //1.创建List
        List<SkinAttr> skinAttrs = new ArrayList<>();
        //2 创建skinAttr
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String AttrName = attrs.getAttributeName(i);
            String Value = attrs.getAttributeValue(i);
//            Log.e("TAG", "getSkinAttrs: -->" + AttrName + "..." + Value);
//            textColor...@2130968616
            //获取我们需要的skinType
            SkinType mSkinType = getSkinTypes(AttrName);
            if (mSkinType!=null){
                String ResName = getSkinResName(context,Value);
                if (TextUtils.isEmpty(ResName)){
                    continue;
                }
                SkinAttr skinAttr = new SkinAttr(ResName,mSkinType);
                skinAttrs.add(skinAttr);
            }

        }

        return skinAttrs;
    }

    private static String getSkinResName(Context context, String value) {
        if (value.startsWith("@")){
            value = value.substring(1);
            String resName = context.getResources().getResourceEntryName(Integer.parseInt(value));
//            Log.e("TAG", "getSkinResName: -->"+resName );
            return resName;
        }
        return null;
    }

    private static SkinType getSkinTypes(String resName) {
        SkinType [] skinTypes = SkinType.values();
        for (SkinType skinType : skinTypes) {
            if (skinType.getResName().equals(resName)){
                return  skinType;
            }
        }

        return null;
    }
}
