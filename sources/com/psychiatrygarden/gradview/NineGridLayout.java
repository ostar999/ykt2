package com.psychiatrygarden.gradview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.psychiatrygarden.bean.ImagesBean;
import com.psychiatrygarden.utils.CommonUtil;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import org.slf4j.Marker;

/* loaded from: classes5.dex */
public abstract class NineGridLayout extends ViewGroup {
    private static final float DEFUALT_SPACING = 3.0f;
    private static final int MAX_COUNT = 9;
    public String downImgUrl;
    private int mColumns;
    protected Context mContext;
    private ImagesBean mImagesBean;
    private boolean mIsFirst;
    private boolean mIsShowAll;
    private int mRows;
    private int mSingleWidth;
    private float mSpacing;
    private int mTotalWidth;
    private final List<String> mUrlList;
    public VideoClickIml mVideoClickIml;
    private Long ss;
    String strTxt;
    public int type;

    public interface VideoClickIml {
        void mVideoClickData();
    }

    public NineGridLayout(Context context) {
        super(context);
        this.mSpacing = 3.0f;
        this.downImgUrl = "";
        this.mIsShowAll = false;
        this.mIsFirst = true;
        this.mUrlList = new ArrayList();
        this.type = 0;
        this.strTxt = "";
        init(context);
    }

    private RatioImageView createImageView(final int i2, final String url, String str) {
        final RatioImageView ratioImageView = new RatioImageView(this.mContext, str);
        ratioImageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.gradview.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16179c.lambda$createImageView$0(i2, url, ratioImageView, view);
            }
        });
        ratioImageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.ic_order_default));
        return ratioImageView;
    }

    private int[] findPosition(int childNum) {
        int[] iArr = new int[2];
        for (int i2 = 0; i2 < this.mRows; i2++) {
            int i3 = 0;
            while (true) {
                int i4 = this.mColumns;
                if (i3 >= i4) {
                    break;
                }
                if ((i4 * i2) + i3 == childNum) {
                    iArr[0] = i2;
                    iArr[1] = i3;
                    break;
                }
                i3++;
            }
        }
        return iArr;
    }

    private void generateChildrenLayout(int length) {
        if (length <= 3) {
            this.mRows = 1;
            this.mColumns = length;
            return;
        }
        if (length <= 6) {
            this.mRows = 2;
            this.mColumns = 3;
            if (length == 4) {
                this.mColumns = 2;
                return;
            }
            return;
        }
        this.mColumns = 3;
        if (!this.mIsShowAll) {
            this.mRows = 3;
            return;
        }
        int i2 = length / 3;
        this.mRows = i2;
        if (length % 3 > 0) {
            this.mRows = i2 + 1;
        }
    }

    private int getFontHeight(float fontSize) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (int) Math.ceil(fontMetrics.descent - fontMetrics.ascent);
    }

    private int getListSize(List<String> list) {
        if (list == null || list.size() == 0) {
            return 0;
        }
        return list.size();
    }

    private void init(Context context) {
        this.mContext = context;
        this.ss = Long.valueOf((System.currentTimeMillis() / 1000) + 1800);
        if (getListSize(this.mUrlList) == 0) {
            setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createImageView$0(int i2, String str, RatioImageView ratioImageView, View view) {
        if (this.type != 1) {
            onClickImage(i2, str, this.mUrlList, this.strTxt, ratioImageView);
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.mImagesBean.getB_img());
        onClickImage(i2, str, arrayList, this.strTxt, ratioImageView);
    }

    private void layoutImageView(RatioImageView imageView, int i2, String url, boolean showNumFlag) {
        int listSize;
        int i3 = (int) ((this.mTotalWidth - (this.mSpacing * 2.0f)) / 3.0f);
        int[] iArrFindPosition = findPosition(i2);
        float f2 = i3;
        float f3 = this.mSpacing;
        int i4 = (int) ((f2 + f3) * iArrFindPosition[1]);
        int i5 = (int) ((f2 + f3) * iArrFindPosition[0]);
        int i6 = i4 + i3;
        int i7 = i5 + i3;
        imageView.layout(i4, i5, i6, i7);
        addView(imageView);
        if (showNumFlag && getListSize(this.mUrlList) - 9 > 0) {
            TextView textView = new TextView(this.mContext);
            textView.setText(Marker.ANY_NON_NULL_MARKER + String.valueOf(listSize));
            textView.setTextColor(-1);
            textView.setPadding(0, (i3 / 2) - getFontHeight(30.0f), 0, 0);
            textView.setTextSize(30.0f);
            textView.setGravity(17);
            textView.setBackgroundColor(-16777216);
            textView.getBackground().setAlpha(120);
            textView.layout(i4, i5, i6, i7);
            addView(textView);
        }
        displayImage(imageView, url);
    }

    private void layoutParams() {
        int i2 = this.mSingleWidth;
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        int i3 = this.mRows;
        layoutParams.height = (int) ((i2 * i3) + (this.mSpacing * (i3 - 1)));
        setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refresh() {
        RatioImageView ratioImageViewCreateImageView;
        removeAllViews();
        int listSize = getListSize(this.mUrlList);
        if (listSize > 0) {
            setVisibility(0);
        } else {
            setVisibility(8);
        }
        if (listSize == 1) {
            String str = this.mUrlList.get(0);
            RatioImageView ratioImageViewCreateImageView2 = createImageView(0, str, "none");
            ratioImageViewCreateImageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            layoutParams.height = this.mSingleWidth;
            setLayoutParams(layoutParams);
            int i2 = this.mSingleWidth;
            ratioImageViewCreateImageView2.layout(0, 0, i2, i2);
            if (displayOneImage(ratioImageViewCreateImageView2, str, this.mTotalWidth)) {
                layoutImageView(ratioImageViewCreateImageView2, 0, str, false);
                return;
            } else {
                addView(ratioImageViewCreateImageView2);
                return;
            }
        }
        generateChildrenLayout(listSize);
        layoutParams();
        for (int i3 = 0; i3 < listSize; i3++) {
            String str2 = this.mUrlList.get(i3);
            if (this.mIsShowAll) {
                ratioImageViewCreateImageView = createImageView(i3, str2, "none");
                layoutImageView(ratioImageViewCreateImageView, i3, str2, false);
            } else if (i3 < 8) {
                ratioImageViewCreateImageView = createImageView(i3, str2, "none");
                layoutImageView(ratioImageViewCreateImageView, i3, str2, false);
            } else if (listSize > 9) {
                layoutImageView(createImageView(i3, str2, "none"), i3, str2, true);
                return;
            } else {
                ratioImageViewCreateImageView = createImageView(i3, str2, "none");
                layoutImageView(ratioImageViewCreateImageView, i3, str2, false);
            }
            ratioImageViewCreateImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshData() {
        float f2;
        float f3;
        float f4;
        removeAllViews();
        ImagesBean imagesBean = this.mImagesBean;
        if (imagesBean == null && TextUtils.isEmpty(imagesBean.getS_img())) {
            setVisibility(8);
            return;
        }
        setVisibility(0);
        String videoMd5keyMyvalue = !TextUtils.isEmpty(this.mImagesBean.getVideoId()) ? CommonUtil.getVideoMd5keyMyvalue(this.mImagesBean.getS_img(), this.ss) : this.mImagesBean.getS_img();
        if (videoMd5keyMyvalue.toUpperCase().endsWith(".GIF")) {
            this.strTxt = "GIF";
        } else if (this.mImagesBean.getS_height() / this.mImagesBean.getS_width() >= 3) {
            this.strTxt = "长图";
        } else {
            this.strTxt = "none";
        }
        if (!TextUtils.isEmpty(this.mImagesBean.getVideoId())) {
            this.strTxt = "video";
        }
        RatioImageView ratioImageViewCreateImageView = createImageView(0, videoMd5keyMyvalue, this.strTxt);
        float s_width = this.mImagesBean.getS_width();
        float s_height = this.mImagesBean.getS_height();
        if (s_height >= s_width * 2.0f) {
            if (s_height > s_width * 2.5d) {
                ratioImageViewCreateImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
            f2 = this.mTotalWidth / 3;
            f3 = 2.0f * f2;
        } else if (s_height < s_width) {
            ratioImageViewCreateImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (s_width >= s_height * 2.0f) {
                f2 = (this.mTotalWidth * 2) / 3;
                f3 = f2 / 2.0f;
            } else {
                if (s_width / s_height <= 1.1d) {
                    f2 = (this.mTotalWidth / 5) * 3;
                    f4 = 4.0f;
                } else {
                    f2 = (this.mTotalWidth / 5) * 3;
                    f4 = 3.0f;
                }
                f3 = (f4 * f2) / 5.0f;
            }
        } else {
            ratioImageViewCreateImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            f2 = (this.mTotalWidth / 2) - 50;
            f3 = (s_height * f2) / s_width;
        }
        ratioImageViewCreateImageView.layout(0, 0, (int) f2, (int) f3);
        if (displayOneImage(ratioImageViewCreateImageView, videoMd5keyMyvalue, this.mTotalWidth)) {
            layoutImageView(ratioImageViewCreateImageView, 0, videoMd5keyMyvalue, false);
        } else {
            addView(ratioImageViewCreateImageView);
        }
    }

    public abstract void displayImage(RatioImageView imageView, String url);

    public abstract boolean displayOneImage(RatioImageView imageView, String url, int parentWidth);

    public String getDownImgUrl() {
        return this.downImgUrl;
    }

    public VideoClickIml getmVideoClickIml() {
        return this.mVideoClickIml;
    }

    public void notifyDataSetChanged() {
        post(new TimerTask() { // from class: com.psychiatrygarden.gradview.NineGridLayout.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                NineGridLayout nineGridLayout = NineGridLayout.this;
                if (nineGridLayout.type == 1) {
                    nineGridLayout.refreshData();
                } else {
                    nineGridLayout.refresh();
                }
            }
        });
    }

    public abstract void onClickImage(int position, String url, List<String> urlList, String strTxt);

    public abstract void onClickImage(int position, String url, List<String> urlList, String strTxt, ImageView imageView);

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int left, int top2, int right, int bottom) {
        int i2 = right - left;
        this.mTotalWidth = i2;
        this.mSingleWidth = (int) ((i2 - (this.mSpacing * 2.0f)) / 3.0f);
        if (this.mIsFirst) {
            notifyDataSetChanged();
            this.mIsFirst = false;
        }
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setDownImgUrl(String downImgUrl) {
        this.downImgUrl = downImgUrl;
    }

    public void setIsShowAll(boolean isShowAll) {
        this.mIsShowAll = isShowAll;
    }

    public void setOneImageLayoutParams(RatioImageView imageView, int width, int height) {
        imageView.setLayoutParams(new ViewGroup.LayoutParams(width, height));
        imageView.layout(0, 0, width, height);
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = height;
        setLayoutParams(layoutParams);
    }

    public void setSpacing(float spacing) {
        this.mSpacing = spacing;
    }

    public void setUrlList(List<String> urlList) {
        if (getListSize(urlList) == 0) {
            setVisibility(8);
            return;
        }
        setVisibility(0);
        this.mUrlList.clear();
        this.mUrlList.addAll(urlList);
        if (this.mIsFirst) {
            return;
        }
        notifyDataSetChanged();
    }

    public void setmImagesBean(ImagesBean mImagesBean, int type) {
        if (mImagesBean == null || TextUtils.isEmpty(mImagesBean.getS_img())) {
            setVisibility(8);
            return;
        }
        this.mImagesBean = mImagesBean;
        this.type = type;
        setVisibility(0);
        if (this.mIsFirst) {
            return;
        }
        notifyDataSetChanged();
    }

    public void setmVideoClickIml(VideoClickIml mVideoClickIml) {
        this.mVideoClickIml = mVideoClickIml;
    }

    public NineGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mSpacing = 3.0f;
        this.downImgUrl = "";
        this.mIsShowAll = false;
        this.mIsFirst = true;
        this.mUrlList = new ArrayList();
        this.type = 0;
        this.strTxt = "";
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.NineGridLayout);
        this.mSpacing = typedArrayObtainStyledAttributes.getDimension(0, 3.0f);
        typedArrayObtainStyledAttributes.recycle();
        init(context);
    }
}
