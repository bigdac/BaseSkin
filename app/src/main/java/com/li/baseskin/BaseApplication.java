package com.li.baseskin;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.li.skinlibrary.skin.SkinManager;
import com.li.skinlibrary.skin.config.SkinLoadUtils;

import java.io.File;


public class BaseApplication extends Application {
    public static Context getContext() {
        return getContext().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.getInstance().init(this);

    }
}
