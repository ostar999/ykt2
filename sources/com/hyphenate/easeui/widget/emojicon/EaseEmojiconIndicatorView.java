package com.hyphenate.easeui.widget.emojicon;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.widget.EImageView;
import com.hyphenate.util.DensityUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressLint({"NewApi"})
/* loaded from: classes4.dex */
public class EaseEmojiconIndicatorView extends LinearLayout {
    private Context context;
    private int dotHeight;
    private List<ImageView> dotViews;
    private Bitmap selectedBitmap;
    private Bitmap unselectedBitmap;

    public EaseEmojiconIndicatorView(Context context, AttributeSet attributeSet, int i2) {
        this(context, null);
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.context = context;
        this.dotHeight = DensityUtil.dip2px(context, this.dotHeight);
        this.selectedBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ease_dot_emojicon_selected);
        this.unselectedBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ease_dot_emojicon_unselected);
        setGravity(1);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Bitmap bitmap = this.selectedBitmap;
        if (bitmap != null) {
            bitmap.recycle();
        }
        Bitmap bitmap2 = this.unselectedBitmap;
        if (bitmap2 != null) {
            bitmap2.recycle();
        }
    }

    public void selectTo(int i2) {
        Iterator<ImageView> it = this.dotViews.iterator();
        while (it.hasNext()) {
            it.next().setImageBitmap(this.unselectedBitmap);
        }
        this.dotViews.get(i2).setImageBitmap(this.selectedBitmap);
    }

    public void updateIndicator(int i2) {
        if (this.dotViews == null) {
            return;
        }
        for (int i3 = 0; i3 < this.dotViews.size(); i3++) {
            if (i3 >= i2) {
                this.dotViews.get(i3).setVisibility(8);
                ((View) this.dotViews.get(i3).getParent()).setVisibility(8);
            } else {
                this.dotViews.get(i3).setVisibility(0);
                ((View) this.dotViews.get(i3).getParent()).setVisibility(0);
            }
        }
        if (i2 > this.dotViews.size()) {
            int size = i2 - this.dotViews.size();
            for (int i4 = 0; i4 < size; i4++) {
                RelativeLayout relativeLayout = new RelativeLayout(this.context);
                int i5 = this.dotHeight;
                ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(i5, i5);
                RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
                layoutParams2.addRule(13);
                EImageView eImageView = new EImageView(this.context);
                eImageView.setImageBitmap(this.unselectedBitmap);
                relativeLayout.addView(eImageView, layoutParams2);
                relativeLayout.setVisibility(8);
                eImageView.setVisibility(8);
                addView(relativeLayout, layoutParams);
                this.dotViews.add(eImageView);
            }
        }
    }

    public EaseEmojiconIndicatorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.dotHeight = 12;
        init(context, attributeSet);
    }

    public void selectTo(int i2, int i3) {
        ImageView imageView = this.dotViews.get(i2);
        ImageView imageView2 = this.dotViews.get(i3);
        imageView.setImageBitmap(this.unselectedBitmap);
        imageView2.setImageBitmap(this.selectedBitmap);
    }

    public EaseEmojiconIndicatorView(Context context) {
        this(context, null);
    }

    public void init(int i2) {
        this.dotViews = new ArrayList();
        for (int i3 = 0; i3 < i2; i3++) {
            RelativeLayout relativeLayout = new RelativeLayout(this.context);
            int i4 = this.dotHeight;
            ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(i4, i4);
            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams2.addRule(13);
            EImageView eImageView = new EImageView(this.context);
            if (i3 == 0) {
                eImageView.setImageBitmap(this.selectedBitmap);
                relativeLayout.addView(eImageView, layoutParams2);
            } else {
                eImageView.setImageBitmap(this.unselectedBitmap);
                relativeLayout.addView(eImageView, layoutParams2);
            }
            addView(relativeLayout, layoutParams);
            this.dotViews.add(eImageView);
        }
    }
}
