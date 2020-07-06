package com.li.baseskin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.li.skinlibrary.base.SkinActivity;
import com.li.skinlibrary.skin.SkinManager;
import com.li.skinlibrary.skin.SkinResource;

import java.io.File;

public class MainActivity extends SkinActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission. READ_EXTERNAL_STORAGE},100);
        }
        Button bt_change = findViewById(R.id.bt_change);
        Button bt_back = findViewById(R.id.bt_back);
        ImageView iv_1 = findViewById(R.id.iv_1);
        iv_1.setOnClickListener(this);
        bt_change.setOnClickListener(this);
        bt_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_1:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.bt_change:
                SkinManager.getInstance().loadResource(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "pic.skin");
                break;
            case R.id.bt_back:
                SkinManager.getInstance().recoverDefault();
                break;
        }
    }

    @Override
    public void skinChange(SkinResource skinResource) {
        Log.e("TAG", "skinChange: ---》" );

    }
}
