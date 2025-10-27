package com.pizidea.imagepicker.ui;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.pizidea.imagepicker.ImgLoader;
import com.pizidea.imagepicker.UilImgLoader;
import com.pizidea.imagepicker.bean.ImageItem;
import com.pizidea.imagepicker.ui.ImagePreviewFragment;
import com.pizidea.imagepicker.widget.TouchImageView;

/* loaded from: classes4.dex */
public class SinglePreviewFragment extends Fragment {
    public static final String KEY_URL = "key_url";
    private boolean enableSingleTap = true;
    private TouchImageView imageView;
    ImgLoader mImagePresenter;
    private String url;

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mImagePresenter = new UilImgLoader();
        this.url = ((ImageItem) getArguments().getSerializable(KEY_URL)).path;
        TouchImageView touchImageView = new TouchImageView(getActivity());
        this.imageView = touchImageView;
        touchImageView.setBackgroundColor(-16777216);
        this.imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        this.imageView.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() { // from class: com.pizidea.imagepicker.ui.SinglePreviewFragment.1
            @Override // android.view.GestureDetector.OnDoubleTapListener
            public boolean onDoubleTap(MotionEvent motionEvent) {
                return false;
            }

            @Override // android.view.GestureDetector.OnDoubleTapListener
            public boolean onDoubleTapEvent(MotionEvent motionEvent) {
                return false;
            }

            @Override // android.view.GestureDetector.OnDoubleTapListener
            public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                if (!SinglePreviewFragment.this.enableSingleTap || !(SinglePreviewFragment.this.getActivity() instanceof ImagePreviewFragment.OnImageSingleTapClickListener)) {
                    return false;
                }
                ((ImagePreviewFragment.OnImageSingleTapClickListener) SinglePreviewFragment.this.getActivity()).onImageSingleTap(motionEvent);
                return false;
            }
        });
        UilImgLoader uilImgLoader = (UilImgLoader) this.mImagePresenter;
        TouchImageView touchImageView2 = this.imageView;
        uilImgLoader.onPresentImage2(touchImageView2, this.url, touchImageView2.getWidth());
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return this.imageView;
    }
}
