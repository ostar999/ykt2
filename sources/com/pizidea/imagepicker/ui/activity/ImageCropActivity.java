package com.pizidea.imagepicker.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.R;
import com.pizidea.imagepicker.ui.AvatarCropFragment;

/* loaded from: classes4.dex */
public class ImageCropActivity extends FragmentActivity implements View.OnClickListener {
    private TextView btnOk;
    private TextView btnReChoose;
    String imagePath;
    private ImageView ivShow;
    AvatarCropFragment mFragment;

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.btn_pic_ok) {
            Bitmap cropBitmap = this.mFragment.getCropBitmap(AndroidImagePicker.getInstance().cropSize);
            finish();
            AndroidImagePicker.getInstance().notifyImageCropComplete(cropBitmap, 0);
        } else if (view.getId() == R.id.btn_pic_rechoose) {
            finish();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_crop);
        this.btnOk = (TextView) findViewById(R.id.btn_pic_ok);
        this.btnReChoose = (TextView) findViewById(R.id.btn_pic_rechoose);
        this.ivShow = (ImageView) findViewById(R.id.iv_show);
        this.btnOk.setOnClickListener(this);
        this.btnReChoose.setOnClickListener(this);
        this.imagePath = getIntent().getStringExtra(AndroidImagePicker.KEY_PIC_PATH);
        this.mFragment = new AvatarCropFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString(AndroidImagePicker.KEY_PIC_PATH, this.imagePath);
        this.mFragment.setArguments(bundle2);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, this.mFragment).commit();
    }
}
