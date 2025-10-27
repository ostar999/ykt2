package catchpig.widget;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.viewpager.widget.PagerAdapter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class ImagePagerAdapter extends PagerAdapter {
    private int mCount;
    private ImageLoader mImageLoader;
    public List<String> mImages;
    private boolean mInfiniteLoop;
    private OnItemClickListener mListener;

    public interface ImageLoader {
        void displayImage(ImageView imageView, String path);
    }

    public interface OnItemClickListener {
        void onItemClick(int index);
    }

    public ImagePagerAdapter() {
        this.mImages = new ArrayList();
        this.mInfiniteLoop = false;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        if (this.mInfiniteLoop) {
            return Integer.MAX_VALUE;
        }
        return this.mCount;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(container.getContext());
        final int i2 = position % this.mCount;
        ImageLoader imageLoader = this.mImageLoader;
        if (imageLoader != null) {
            imageLoader.displayImage(imageView, this.mImages.get(i2));
        }
        if (this.mListener != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: catchpig.widget.ImagePagerAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View v2) {
                    ImagePagerAdapter.this.mListener.onItemClick(i2);
                }
            });
        }
        container.addView(imageView);
        return imageView;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void setImageLoader(ImageLoader imageLoader) {
        this.mImageLoader = imageLoader;
    }

    public void setImages(List<String> images) {
        this.mCount = 0;
        if (images != null) {
            this.mImages = images;
            this.mCount = images.size();
        }
    }

    public void setInfiniteLoop(boolean infiniteLoop) {
        this.mInfiniteLoop = infiniteLoop;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public ImagePagerAdapter(List<String> images) {
        this.mImages = new ArrayList();
        this.mInfiniteLoop = false;
        if (images != null) {
            this.mImages = images;
        }
    }
}
