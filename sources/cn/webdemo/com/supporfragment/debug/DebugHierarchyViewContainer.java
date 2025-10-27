package cn.webdemo.com.supporfragment.debug;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import java.util.List;

/* loaded from: classes.dex */
public class DebugHierarchyViewContainer extends ScrollView {
    private Context mContext;
    private int mItemHeight;
    private LinearLayout mLinearLayout;
    private int mPadding;
    private LinearLayout mTitleLayout;

    public DebugHierarchyViewContainer(Context context) {
        super(context);
        initView(context);
    }

    private int dip2px(float f2) {
        return (int) ((f2 * this.mContext.getResources().getDisplayMetrics().density) + 0.5f);
    }

    private TextView getTextView(DebugFragmentRecord debugFragmentRecord, int i2) {
        TextView textView = new TextView(this.mContext);
        textView.setLayoutParams(new ViewGroup.LayoutParams(-1, this.mItemHeight));
        if (i2 == 0) {
            textView.setTextColor(Color.parseColor("#333333"));
            textView.setTextSize(16.0f);
        }
        textView.setGravity(16);
        int i3 = this.mPadding;
        textView.setPadding((int) (i3 + (i2 * i3 * 1.5d)), 0, i3, 0);
        textView.setCompoundDrawablePadding(this.mPadding / 2);
        TypedArray typedArrayObtainStyledAttributes = this.mContext.obtainStyledAttributes(new int[]{R.attr.selectableItemBackground});
        textView.setBackgroundDrawable(typedArrayObtainStyledAttributes.getDrawable(0));
        typedArrayObtainStyledAttributes.recycle();
        textView.setText(debugFragmentRecord.fragmentName);
        return textView;
    }

    @NonNull
    private LinearLayout getTitleLayout() {
        LinearLayout linearLayout = this.mTitleLayout;
        if (linearLayout != null) {
            return linearLayout;
        }
        LinearLayout linearLayout2 = new LinearLayout(this.mContext);
        this.mTitleLayout = linearLayout2;
        linearLayout2.setPadding(dip2px(24.0f), dip2px(24.0f), 0, dip2px(8.0f));
        this.mTitleLayout.setOrientation(0);
        this.mTitleLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        TextView textView = new TextView(this.mContext);
        textView.setText("栈视图(Stack)");
        textView.setTextSize(20.0f);
        textView.setTextColor(-16777216);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 16;
        textView.setLayoutParams(layoutParams);
        this.mTitleLayout.addView(textView);
        ImageView imageView = new ImageView(this.mContext);
        imageView.setImageResource(cn.webdemo.com.supporfragment.R.drawable.fragmentation_help);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams2.leftMargin = dip2px(16.0f);
        layoutParams2.gravity = 16;
        imageView.setLayoutParams(layoutParams2);
        this.mTitleLayout.setOnClickListener(new View.OnClickListener() { // from class: cn.webdemo.com.supporfragment.debug.DebugHierarchyViewContainer.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Toast.makeText(DebugHierarchyViewContainer.this.mContext, "* means not in backBack.", 0).show();
            }
        });
        this.mTitleLayout.addView(imageView);
        return this.mTitleLayout;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleExpandView(List<DebugFragmentRecord> list, int i2, TextView textView) {
        setView(list, i2, textView);
        textView.setCompoundDrawablesWithIntrinsicBounds(cn.webdemo.com.supporfragment.R.drawable.fragmentation_ic_expandable, 0, 0, 0);
    }

    private void initView(Context context) {
        this.mContext = context;
        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(context);
        LinearLayout linearLayout = new LinearLayout(context);
        this.mLinearLayout = linearLayout;
        linearLayout.setOrientation(1);
        horizontalScrollView.addView(this.mLinearLayout);
        addView(horizontalScrollView);
        this.mItemHeight = dip2px(50.0f);
        this.mPadding = dip2px(16.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeView(int i2) {
        for (int childCount = this.mLinearLayout.getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = this.mLinearLayout.getChildAt(childCount);
            int i3 = cn.webdemo.com.supporfragment.R.id.hierarchy;
            if (childAt.getTag(i3) != null && ((Integer) childAt.getTag(i3)).intValue() >= i2) {
                this.mLinearLayout.removeView(childAt);
            }
        }
    }

    private void setView(List<DebugFragmentRecord> list, int i2, TextView textView) {
        for (int size = list.size() - 1; size >= 0; size--) {
            DebugFragmentRecord debugFragmentRecord = list.get(size);
            final TextView textView2 = getTextView(debugFragmentRecord, i2);
            textView2.setTag(cn.webdemo.com.supporfragment.R.id.hierarchy, Integer.valueOf(i2));
            final List<DebugFragmentRecord> list2 = debugFragmentRecord.childFragmentRecord;
            if (list2 == null || list2.size() <= 0) {
                int paddingLeft = textView2.getPaddingLeft();
                int i3 = this.mPadding;
                textView2.setPadding(paddingLeft + i3, 0, i3, 0);
            } else {
                final int i4 = i2 + 1;
                textView2.setCompoundDrawablesWithIntrinsicBounds(cn.webdemo.com.supporfragment.R.drawable.fragmentation_ic_right, 0, 0, 0);
                textView2.setOnClickListener(new View.OnClickListener() { // from class: cn.webdemo.com.supporfragment.debug.DebugHierarchyViewContainer.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        int i5 = cn.webdemo.com.supporfragment.R.id.isexpand;
                        if (view.getTag(i5) == null) {
                            textView2.setTag(i5, Boolean.TRUE);
                            DebugHierarchyViewContainer.this.handleExpandView(list2, i4, textView2);
                            return;
                        }
                        boolean zBooleanValue = ((Boolean) view.getTag(i5)).booleanValue();
                        if (zBooleanValue) {
                            textView2.setCompoundDrawablesWithIntrinsicBounds(cn.webdemo.com.supporfragment.R.drawable.fragmentation_ic_right, 0, 0, 0);
                            DebugHierarchyViewContainer.this.removeView(i4);
                        } else {
                            DebugHierarchyViewContainer.this.handleExpandView(list2, i4, textView2);
                        }
                        view.setTag(i5, Boolean.valueOf(!zBooleanValue));
                    }
                });
            }
            if (textView == null) {
                this.mLinearLayout.addView(textView2);
            } else {
                LinearLayout linearLayout = this.mLinearLayout;
                linearLayout.addView(textView2, linearLayout.indexOfChild(textView) + 1);
            }
        }
    }

    public void bindFragmentRecords(List<DebugFragmentRecord> list) {
        this.mLinearLayout.removeAllViews();
        this.mLinearLayout.addView(getTitleLayout());
        if (list == null) {
            return;
        }
        setView(list, 0, null);
    }

    public DebugHierarchyViewContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    public DebugHierarchyViewContainer(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        initView(context);
    }
}
