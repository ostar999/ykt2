package com.pizidea.imagepicker.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.R;
import com.pizidea.imagepicker.bean.ImageItem;
import com.pizidea.imagepicker.ui.ImagesGridFragment;

/* loaded from: classes4.dex */
public class ImagesGridActivity extends FragmentActivity implements View.OnClickListener, AndroidImagePicker.OnImageSelectedChangeListener {
    private static final String TAG = "ImagesGridActivity";
    AndroidImagePicker androidImagePicker;
    String imagePath;
    private TextView mBtnOk;
    ImagesGridFragment mFragment;

    /* JADX INFO: Access modifiers changed from: private */
    public void go2Preview(int i2) {
        Intent intent = new Intent();
        intent.putExtra(AndroidImagePicker.KEY_PIC_SELECTED_POSITION, i2);
        intent.setClass(this, ImagePreviewActivity.class);
        startActivityForResult(intent, 2347);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        if (i3 == -1 && i2 == 2347) {
            setResult(-1);
            finish();
            this.androidImagePicker.notifyOnImagePickComplete();
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.btn_ok) {
            finish();
            this.androidImagePicker.notifyOnImagePickComplete();
        } else if (view.getId() == R.id.btn_pic_rechoose) {
            finish();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_images_grid);
        AndroidImagePicker androidImagePicker = AndroidImagePicker.getInstance();
        this.androidImagePicker = androidImagePicker;
        androidImagePicker.setCurrentSelectedImageSetPosition(0);
        this.androidImagePicker.clearSelectedImages();
        TextView textView = (TextView) findViewById(R.id.btn_ok);
        this.mBtnOk = textView;
        textView.setOnClickListener(this);
        if (this.androidImagePicker.getSelectMode() == 0) {
            this.mBtnOk.setVisibility(8);
        } else {
            this.mBtnOk.setVisibility(0);
        }
        findViewById(R.id.btn_backpress).setOnClickListener(new View.OnClickListener() { // from class: com.pizidea.imagepicker.ui.activity.ImagesGridActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ImagesGridActivity.this.finish();
            }
        });
        final boolean z2 = this.androidImagePicker.cropMode;
        this.imagePath = getIntent().getStringExtra(AndroidImagePicker.KEY_PIC_PATH);
        ImagesGridFragment imagesGridFragment = new ImagesGridFragment();
        this.mFragment = imagesGridFragment;
        imagesGridFragment.setOnImageItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.pizidea.imagepicker.ui.activity.ImagesGridActivity.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j2) {
                if (ImagesGridActivity.this.androidImagePicker.isShouldShowCamera()) {
                    i2--;
                }
                if (ImagesGridActivity.this.androidImagePicker.getSelectMode() == 1) {
                    ImagesGridActivity.this.go2Preview(i2);
                    return;
                }
                if (ImagesGridActivity.this.androidImagePicker.getSelectMode() == 0) {
                    if (z2) {
                        Intent intent = new Intent();
                        intent.setClass(ImagesGridActivity.this, ImageCropActivity.class);
                        intent.putExtra(AndroidImagePicker.KEY_PIC_PATH, ImagesGridActivity.this.androidImagePicker.getImageItemsOfCurrentImageSet().get(i2).path);
                        ImagesGridActivity.this.startActivity(intent);
                        return;
                    }
                    ImagesGridActivity.this.androidImagePicker.clearSelectedImages();
                    AndroidImagePicker androidImagePicker2 = ImagesGridActivity.this.androidImagePicker;
                    androidImagePicker2.addSelectedImageItem(i2, androidImagePicker2.getImageItemsOfCurrentImageSet().get(i2));
                    ImagesGridActivity.this.setResult(-1);
                    ImagesGridActivity.this.finish();
                    ImagesGridActivity.this.androidImagePicker.notifyOnImagePickComplete();
                }
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.container, this.mFragment).commit();
        this.androidImagePicker.addOnImageSelectedChangeListener(this);
        onImageSelectChange(0, null, this.androidImagePicker.getSelectImageCount(), this.androidImagePicker.getSelectLimit());
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        this.androidImagePicker.removeOnImageItemSelectedChangeListener(this);
        this.androidImagePicker.clearImageSets();
        Log.i(TAG, "=====removeOnImageItemSelectedChangeListener");
        super.onDestroy();
    }

    @Override // com.pizidea.imagepicker.AndroidImagePicker.OnImageSelectedChangeListener
    @SuppressLint({"StringFormatMatches"})
    public void onImageSelectChange(int i2, ImageItem imageItem, int i3, int i4) {
        if (i3 > 0) {
            this.mBtnOk.setEnabled(true);
            this.mBtnOk.setText(getResources().getString(R.string.select_complete, Integer.valueOf(i3), Integer.valueOf(i4)));
        } else {
            this.mBtnOk.setText(getResources().getString(R.string.complete));
            this.mBtnOk.setEnabled(false);
        }
        Log.i(TAG, "=====EVENT:onImageSelectChange");
    }
}
