package com.li.skinlibrary.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.VectorEnabledTintResources;
import androidx.core.view.LayoutInflaterCompat;
import androidx.core.view.ViewCompat;

import com.li.skinlibrary.skin.SkinManager;
import com.li.skinlibrary.skin.SkinResource;
import com.li.skinlibrary.skin.SkinSupport;
import com.li.skinlibrary.skin.SkinViewInflater;
import com.li.skinlibrary.skin.attribute.SkinAttr;
import com.li.skinlibrary.skin.attribute.SkinView;
import com.li.skinlibrary.skin.call.SkinChangeCallBack;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author li
 * 版本：1.0
 * 创建日期：2020/6/29 16
 * 描述：
 */
public abstract class SkinActivity extends AppCompatActivity implements SkinChangeCallBack {
    private SkinViewInflater mAppCompatViewInflater;
    private static final boolean IS_PRE_LOLLIPOP = Build.VERSION.SDK_INT < 21;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory2(layoutInflater, this);
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
//        1.创建View
        View view = createView(parent, name, context, attrs);
//        2.获取皮肤资源
        if (view != null) {
            Log.e("TAG", "onCreateView: --》"+view );
            List<SkinAttr> mSkinAttrs = SkinSupport.getSkinAttrs(context, attrs);
//        创建皮肤View
            SkinView skinView = new SkinView(view, mSkinAttrs);
//        3交给SkinManager管理
            managerSkinView(skinView);
            // 4 判断要不要换肤
            SkinManager.getInstance().checkSkinView(skinView);

        }
        return view;
    }

    private void managerSkinView(SkinView skinView) {
        List<SkinView> skinViews = SkinManager.getInstance().getSkinViews(this);
        if (skinViews == null) {
            skinViews = new ArrayList<>();
            SkinManager.getInstance().registerSkinView(this, skinViews);
        }
        skinViews.add(skinView);
    }


    @SuppressLint("RestrictedApi")
    public View createView(View parent, final String name, @NonNull Context context,
                           @NonNull AttributeSet attrs) {
        if (mAppCompatViewInflater == null) {
            mAppCompatViewInflater = new SkinViewInflater();
        }

        boolean inheritContext = false;
        if (IS_PRE_LOLLIPOP) {
            inheritContext = (attrs instanceof XmlPullParser)
                    // If we have a XmlPullParser, we can detect where we are in the layout
                    ? ((XmlPullParser) attrs).getDepth() > 1
                    // Otherwise we have to use the old heuristic
                    : shouldInheritContext((ViewParent) parent);
        }

        return mAppCompatViewInflater.createView(parent, name, context, attrs, inheritContext,
                IS_PRE_LOLLIPOP, /* Only read android:theme pre-L (L+ handles this anyway) */
                true, /* Read read app:theme as a fallback at all times for legacy reasons */
                VectorEnabledTintResources.shouldBeUsed() /* Only tint wrap the context if enabled */
        );
    }

    private boolean shouldInheritContext(ViewParent parent) {
        if (parent == null) {
            // The initial parent is null so just return false
            return false;
        }
        final View windowDecor = getWindow().getDecorView();
        while (true) {
            if (parent == null) {
                // Bingo. We've hit a view which has a null parent before being terminated from
                // the loop. This is (most probably) because it's the root view in an inflation
                // call, therefore we should inherit. This works as the inflated layout is only
                // added to the hierarchy at the end of the inflate() call.
                return true;
            } else if (parent == windowDecor || !(parent instanceof View)
                    || ViewCompat.isAttachedToWindow((View) parent)) {
                // We have either hit the window's decor view, a parent which isn't a View
                // (i.e. ViewRootImpl), or an attached view, so we know that the original parent
                // is currently added to the view hierarchy. This means that it has not be
                // inflated in the current inflate() call and we should not inherit the context.
                return false;
            }
            parent = parent.getParent();
        }
    }

    @Override
    protected void onDestroy() {
        SkinManager.getInstance().unBinderSkinActivity(this);
        super.onDestroy();
    }
    /**
     * @description 切换皮肤回调
     * @param 
     */
    @Override
    public void skinChange(SkinResource skinResource) {

    }
}
