package com.pizidea.imagepicker.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.R;
import com.pizidea.imagepicker.bean.ImageItem;
import com.pizidea.imagepicker.ui.ImagePreviewFragment;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes4.dex */
public class ImagePreviewActivity extends FragmentActivity implements View.OnClickListener, ImagePreviewFragment.OnImageSingleTapClickListener, ImagePreviewFragment.OnImagePageSelectedListener, AndroidImagePicker.OnImageSelectedChangeListener {
    private static final String TAG = "ImagePreviewActivity";
    AndroidImagePicker androidImagePicker;
    TextView mBtnOk;
    CheckBox mCbSelected;
    ImagePreviewFragment mFragment;
    List<ImageItem> mImageList;
    int mShowItemPosition = 0;
    TextView mTitleCount;

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.btn_ok) {
            setResult(-1);
            finish();
        } else if (view.getId() == R.id.btn_pic_rechoose) {
            finish();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_image_pre);
        AndroidImagePicker androidImagePicker = AndroidImagePicker.getInstance();
        this.androidImagePicker = androidImagePicker;
        androidImagePicker.addOnImageSelectedChangeListener(this);
        this.mImageList = AndroidImagePicker.getInstance().getImageItemsOfCurrentImageSet();
        this.mShowItemPosition = getIntent().getIntExtra(AndroidImagePicker.KEY_PIC_SELECTED_POSITION, 0);
        TextView textView = (TextView) findViewById(R.id.btn_ok);
        this.mBtnOk = textView;
        textView.setOnClickListener(this);
        this.mCbSelected = (CheckBox) findViewById(R.id.btn_check);
        TextView textView2 = (TextView) findViewById(R.id.tv_title_count);
        this.mTitleCount = textView2;
        textView2.setText("1/" + this.mImageList.size());
        onImageSelectChange(0, null, AndroidImagePicker.getInstance().getSelectImageCount(), this.androidImagePicker.getSelectLimit());
        findViewById(R.id.btn_backpress).setOnClickListener(new View.OnClickListener() { // from class: com.pizidea.imagepicker.ui.activity.ImagePreviewActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ImagePreviewActivity.this.finish();
            }
        });
        this.mCbSelected.setOnClickListener(new View.OnClickListener() { // from class: com.pizidea.imagepicker.ui.activity.ImagePreviewActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ImagePreviewActivity.this.androidImagePicker.getSelectImageCount() <= ImagePreviewActivity.this.androidImagePicker.getSelectLimit() || !ImagePreviewActivity.this.mCbSelected.isChecked()) {
                    return;
                }
                ImagePreviewActivity.this.mCbSelected.toggle();
                Toast.makeText(ImagePreviewActivity.this, ImagePreviewActivity.this.getResources().getString(R.string.you_have_a_select_limit, Integer.valueOf(ImagePreviewActivity.this.androidImagePicker.getSelectLimit())), 0).show();
            }
        });
        this.mCbSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.pizidea.imagepicker.ui.activity.ImagePreviewActivity.3
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                ImagePreviewActivity.this.mFragment.selectCurrent(z2);
            }
        });
        this.mFragment = new ImagePreviewFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putSerializable(AndroidImagePicker.KEY_PIC_PATH, (Serializable) this.mImageList);
        bundle2.putInt(AndroidImagePicker.KEY_PIC_SELECTED_POSITION, this.mShowItemPosition);
        this.mFragment.setArguments(bundle2);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, this.mFragment).commit();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        this.androidImagePicker.removeOnImageItemSelectedChangeListener(this);
        Log.i(TAG, "=====removeOnImageItemSelectedChangeListener");
        super.onDestroy();
    }

    @Override // com.pizidea.imagepicker.ui.ImagePreviewFragment.OnImagePageSelectedListener
    public void onImagePageSelected(int i2, ImageItem imageItem, boolean z2) {
        this.mTitleCount.setText((i2 + 1) + "/" + this.mImageList.size());
        this.mCbSelected.setChecked(z2);
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

    @Override // com.pizidea.imagepicker.ui.ImagePreviewFragment.OnImageSingleTapClickListener
    public void onImageSingleTap(MotionEvent motionEvent) {
        View viewFindViewById = findViewById(R.id.top_bar);
        View viewFindViewById2 = findViewById(R.id.bottom_bar);
        if (viewFindViewById.getVisibility() == 0) {
            viewFindViewById.setAnimation(AnimationUtils.loadAnimation(this, R.anim.top_out));
            viewFindViewById2.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
            viewFindViewById.setVisibility(8);
            viewFindViewById2.setVisibility(8);
            return;
        }
        viewFindViewById.setAnimation(AnimationUtils.loadAnimation(this, R.anim.top_in));
        viewFindViewById2.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        viewFindViewById.setVisibility(0);
        viewFindViewById2.setVisibility(0);
    }
}
