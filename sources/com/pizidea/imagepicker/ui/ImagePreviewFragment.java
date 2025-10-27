package com.pizidea.imagepicker.ui;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.R;
import com.pizidea.imagepicker.bean.ImageItem;
import java.util.List;

/* loaded from: classes4.dex */
public class ImagePreviewFragment extends Fragment {
    private static final String TAG = "ImagePreviewFragment";
    AndroidImagePicker androidImagePicker;
    TouchImageAdapter mAdapter;
    Activity mContext;
    private int mCurrentItemPosition = 0;
    List<ImageItem> mImageList;
    ViewPager mViewPager;

    public interface OnImagePageSelectedListener {
        void onImagePageSelected(int i2, ImageItem imageItem, boolean z2);
    }

    public interface OnImageSingleTapClickListener {
        void onImageSingleTap(MotionEvent motionEvent);
    }

    public class TouchImageAdapter extends FragmentStatePagerAdapter {
        public TouchImageAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        /* renamed from: getCount */
        public int getSize() {
            return ImagePreviewFragment.this.mImageList.size();
        }

        @Override // androidx.fragment.app.FragmentStatePagerAdapter
        public Fragment getItem(int i2) {
            SinglePreviewFragment singlePreviewFragment = new SinglePreviewFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(SinglePreviewFragment.KEY_URL, ImagePreviewFragment.this.mImageList.get(i2));
            singlePreviewFragment.setArguments(bundle);
            return singlePreviewFragment;
        }
    }

    private void initView(View view) throws Resources.NotFoundException {
        this.mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        TouchImageAdapter touchImageAdapter = new TouchImageAdapter(((FragmentActivity) this.mContext).getSupportFragmentManager());
        this.mAdapter = touchImageAdapter;
        this.mViewPager.setAdapter(touchImageAdapter);
        this.mViewPager.setCurrentItem(this.mCurrentItemPosition, false);
        ImageItem imageItem = this.mImageList.get(this.mCurrentItemPosition);
        if (this.mContext instanceof OnImagePageSelectedListener) {
            boolean zIsSelect = this.androidImagePicker.isSelect(this.mCurrentItemPosition, imageItem);
            OnImagePageSelectedListener onImagePageSelectedListener = (OnImagePageSelectedListener) this.mContext;
            int i2 = this.mCurrentItemPosition;
            onImagePageSelectedListener.onImagePageSelected(i2, this.mImageList.get(i2), zIsSelect);
        }
        this.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.pizidea.imagepicker.ui.ImagePreviewFragment.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i3) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i3, float f2, int i4) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i3) {
                ImagePreviewFragment.this.mCurrentItemPosition = i3;
                ImagePreviewFragment imagePreviewFragment = ImagePreviewFragment.this;
                if (imagePreviewFragment.mContext instanceof OnImagePageSelectedListener) {
                    ImageItem imageItem2 = imagePreviewFragment.mImageList.get(imagePreviewFragment.mCurrentItemPosition);
                    boolean zIsSelect2 = ImagePreviewFragment.this.androidImagePicker.isSelect(i3, imageItem2);
                    ImagePreviewFragment imagePreviewFragment2 = ImagePreviewFragment.this;
                    ((OnImagePageSelectedListener) imagePreviewFragment2.mContext).onImagePageSelected(imagePreviewFragment2.mCurrentItemPosition, imageItem2, zIsSelect2);
                }
            }
        });
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getActivity();
        this.androidImagePicker = AndroidImagePicker.getInstance();
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) throws Resources.NotFoundException {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_preview, (ViewGroup) null);
        this.mImageList = this.androidImagePicker.getImageItemsOfCurrentImageSet();
        this.mCurrentItemPosition = getArguments().getInt(AndroidImagePicker.KEY_PIC_SELECTED_POSITION, 0);
        initView(viewInflate);
        return viewInflate;
    }

    public void selectCurrent(boolean z2) {
        ImageItem imageItem = this.mImageList.get(this.mCurrentItemPosition);
        boolean zIsSelect = this.androidImagePicker.isSelect(this.mCurrentItemPosition, imageItem);
        if (z2) {
            if (zIsSelect) {
                return;
            }
            AndroidImagePicker.getInstance().addSelectedImageItem(this.mCurrentItemPosition, imageItem);
        } else if (zIsSelect) {
            AndroidImagePicker.getInstance().deleteSelectedImageItem(this.mCurrentItemPosition, imageItem);
        }
    }
}
