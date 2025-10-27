package com.pizidea.imagepicker.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.ImgLoader;
import com.pizidea.imagepicker.R;
import com.pizidea.imagepicker.UilImgLoader;
import com.pizidea.imagepicker.Util;
import com.pizidea.imagepicker.widget.AvatarRectView;
import com.pizidea.imagepicker.widget.SuperImageView;

/* loaded from: classes4.dex */
public class AvatarCropFragment extends Fragment {
    Activity mContext;
    ImgLoader mImagePresenter;
    AvatarRectView mRectView;
    private final int margin = 30;
    private String picPath;
    private FrameLayout rootView;
    private int screenWidth;
    SuperImageView superImageView;

    public Bitmap getCropBitmap(int i2) {
        if (i2 <= 0) {
            return null;
        }
        Bitmap bitmap = ((BitmapDrawable) this.superImageView.getDrawable()).getBitmap();
        int iFloor = (int) Math.floor((this.superImageView.getImageRotation() + 0.7853981633974483d) / 1.5707963267948966d);
        if (iFloor != 0) {
            bitmap = Util.rotate(bitmap, iFloor * 90);
        }
        return AndroidImagePicker.makeCropBitmap(bitmap, this.mRectView.getCropRect(), this.superImageView.getMatrixRect(), i2);
    }

    public void initView(View view) {
        this.superImageView = (SuperImageView) view.findViewById(R.id.iv_pic);
        this.rootView = (FrameLayout) view.findViewById(R.id.container);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        AvatarRectView avatarRectView = new AvatarRectView(this.mContext, this.screenWidth - 60);
        this.mRectView = avatarRectView;
        this.rootView.addView(avatarRectView, 1, layoutParams);
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getActivity();
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_avatar_crop, (ViewGroup) null);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mContext.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.screenWidth = displayMetrics.widthPixels;
        initView(viewInflate);
        this.picPath = getArguments().getString(AndroidImagePicker.KEY_PIC_PATH);
        this.mImagePresenter = new UilImgLoader();
        if (TextUtils.isEmpty(this.picPath)) {
            throw new RuntimeException("AndroidImagePicker:you have to give me an image path from sdcard");
        }
        this.mImagePresenter.onPresentImage(this.superImageView, this.picPath, this.screenWidth);
        return viewInflate;
    }
}
