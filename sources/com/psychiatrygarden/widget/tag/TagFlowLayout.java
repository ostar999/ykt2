package com.psychiatrygarden.widget.tag;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.psychiatrygarden.widget.tag.TagAdapter;
import com.yikaobang.yixue.R;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes6.dex */
public class TagFlowLayout extends FlowLayout implements TagAdapter.OnDataChangedListener {
    private static final String KEY_CHOOSE_POS = "key_choose_pos";
    private static final String KEY_DEFAULT = "key_default";
    private static final String TAG = "TagFlowLayout";
    private boolean mAutoSelectEffect;
    private MotionEvent mMotionEvent;
    private OnSelectListener mOnSelectListener;
    private OnTagClickListener mOnTagClickListener;
    private int mSelectedMax;
    private Set<Integer> mSelectedView;
    private TagAdapter mTagAdapter;

    public interface OnSelectListener {
        void onSelected(Set<Integer> selectPosSet);
    }

    public interface OnTagClickListener {
        boolean onTagClick(View view, int position, FlowLayout parent);
    }

    public TagFlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mAutoSelectEffect = true;
        this.mSelectedMax = -1;
        this.mSelectedView = new HashSet();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.TagFlowLayout);
        this.mAutoSelectEffect = typedArrayObtainStyledAttributes.getBoolean(0, true);
        this.mSelectedMax = typedArrayObtainStyledAttributes.getInt(2, -1);
        typedArrayObtainStyledAttributes.recycle();
        if (this.mAutoSelectEffect) {
            setClickable(true);
        }
    }

    private void changeAdapter() {
        removeAllViews();
        TagAdapter tagAdapter = this.mTagAdapter;
        HashSet<Integer> preCheckedList = tagAdapter.getPreCheckedList();
        for (int i2 = 0; i2 < tagAdapter.getCount(); i2++) {
            View view = tagAdapter.getView(this, i2, tagAdapter.getItem(i2));
            TagView tagView = new TagView(getContext());
            view.setDuplicateParentStateEnabled(true);
            if (view.getLayoutParams() != null) {
                tagView.setLayoutParams(view.getLayoutParams());
            } else {
                ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(-2, -2);
                marginLayoutParams.setMargins(dip2px(getContext(), 5.0f), dip2px(getContext(), 5.0f), dip2px(getContext(), 5.0f), dip2px(getContext(), 5.0f));
                tagView.setLayoutParams(marginLayoutParams);
            }
            tagView.addView(view);
            addView(tagView);
            if (preCheckedList.contains(Integer.valueOf(i2))) {
                tagView.setChecked(true);
            }
            if (this.mTagAdapter.setSelected(i2, tagAdapter.getItem(i2))) {
                this.mSelectedView.add(Integer.valueOf(i2));
                tagView.setChecked(true);
            }
        }
        this.mSelectedView.addAll(preCheckedList);
    }

    public static int dip2px(Context context, float dpValue) {
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    private void doSelect(TagView child, int position) {
        if (this.mAutoSelectEffect) {
            if (child.isChecked()) {
                child.setChecked(false);
                this.mSelectedView.remove(Integer.valueOf(position));
            } else if (this.mSelectedMax == 1 && this.mSelectedView.size() == 1) {
                Integer next = this.mSelectedView.iterator().next();
                ((TagView) getChildAt(next.intValue())).setChecked(false);
                child.setChecked(true);
                this.mSelectedView.remove(next);
                this.mSelectedView.add(Integer.valueOf(position));
            } else {
                if (this.mSelectedMax > 0 && this.mSelectedView.size() >= this.mSelectedMax) {
                    return;
                }
                child.setChecked(true);
                this.mSelectedView.add(Integer.valueOf(position));
            }
            OnSelectListener onSelectListener = this.mOnSelectListener;
            if (onSelectListener != null) {
                onSelectListener.onSelected(new HashSet(this.mSelectedView));
            }
        }
    }

    private TagView findChild(int x2, int y2) {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            TagView tagView = (TagView) getChildAt(i2);
            if (tagView.getVisibility() != 8) {
                Rect rect = new Rect();
                tagView.getHitRect(rect);
                if (rect.contains(x2, y2)) {
                    return tagView;
                }
            }
        }
        return null;
    }

    private int findPosByView(View child) {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            if (getChildAt(i2) == child) {
                return i2;
            }
        }
        return -1;
    }

    public TagAdapter getAdapter() {
        return this.mTagAdapter;
    }

    public Set<Integer> getSelectedList() {
        return new HashSet(this.mSelectedView);
    }

    @Override // com.psychiatrygarden.widget.tag.TagAdapter.OnDataChangedListener
    public void onChanged() {
        this.mSelectedView.clear();
        changeAdapter();
    }

    @Override // com.psychiatrygarden.widget.tag.FlowLayout, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            TagView tagView = (TagView) getChildAt(i2);
            if (tagView.getVisibility() != 8 && tagView.getTagView().getVisibility() == 8) {
                tagView.setVisibility(8);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable state) throws NumberFormatException {
        if (!(state instanceof Bundle)) {
            super.onRestoreInstanceState(state);
            return;
        }
        Bundle bundle = (Bundle) state;
        String string = bundle.getString(KEY_CHOOSE_POS);
        if (!TextUtils.isEmpty(string)) {
            for (String str : string.split("\\|")) {
                int i2 = Integer.parseInt(str);
                this.mSelectedView.add(Integer.valueOf(i2));
                TagView tagView = (TagView) getChildAt(i2);
                if (tagView != null) {
                    tagView.setChecked(true);
                }
            }
        }
        super.onRestoreInstanceState(bundle.getParcelable(KEY_DEFAULT));
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_DEFAULT, super.onSaveInstanceState());
        String strSubstring = "";
        if (this.mSelectedView.size() > 0) {
            Iterator<Integer> it = this.mSelectedView.iterator();
            while (it.hasNext()) {
                strSubstring = strSubstring + it.next().intValue() + HiAnalyticsConstant.REPORT_VAL_SEPARATOR;
            }
            strSubstring = strSubstring.substring(0, strSubstring.length() - 1);
        }
        bundle.putString(KEY_CHOOSE_POS, strSubstring);
        return bundle;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == 1) {
            this.mMotionEvent = MotionEvent.obtain(event);
        }
        return super.onTouchEvent(event);
    }

    @Override // android.view.View
    public boolean performClick() {
        MotionEvent motionEvent = this.mMotionEvent;
        if (motionEvent == null) {
            return super.performClick();
        }
        int x2 = (int) motionEvent.getX();
        int y2 = (int) this.mMotionEvent.getY();
        this.mMotionEvent = null;
        TagView tagViewFindChild = findChild(x2, y2);
        int iFindPosByView = findPosByView(tagViewFindChild);
        if (tagViewFindChild == null) {
            return true;
        }
        doSelect(tagViewFindChild, iFindPosByView);
        OnTagClickListener onTagClickListener = this.mOnTagClickListener;
        if (onTagClickListener != null) {
            return onTagClickListener.onTagClick(tagViewFindChild.getTagView(), iFindPosByView, this);
        }
        return true;
    }

    public void setAdapter(TagAdapter adapter) {
        this.mTagAdapter = adapter;
        adapter.setOnDataChangedListener(this);
        this.mSelectedView.clear();
        changeAdapter();
    }

    public void setMaxSelectCount(int count) {
        if (this.mSelectedView.size() > count) {
            Log.w(TAG, "you has already select more than " + count + " views , so it will be clear .");
            this.mSelectedView.clear();
        }
        this.mSelectedMax = count;
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.mOnSelectListener = onSelectListener;
        if (onSelectListener != null) {
            setClickable(true);
        }
    }

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        this.mOnTagClickListener = onTagClickListener;
        if (onTagClickListener != null) {
            setClickable(true);
        }
    }

    public TagFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagFlowLayout(Context context) {
        this(context, null);
    }
}
