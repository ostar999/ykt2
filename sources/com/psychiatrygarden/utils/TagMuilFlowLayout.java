package com.psychiatrygarden.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.bean.BiaoqianBeab;
import com.psychiatrygarden.utils.TagAdapter;
import com.yikaobang.yixue.R;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes6.dex */
public class TagMuilFlowLayout extends FlowLayout implements TagAdapter.OnDataChangedListener {
    private static final String KEY_CHOOSE_POS = "key_choose_pos";
    private static final String KEY_DEFAULT = "key_default";
    private static final String TAG = "TagFlowLayout";
    private List<BiaoqianBeab.DataBean> dataBiao;
    private OnSelectListener mOnSelectListener;
    private OnTagClickListener mOnTagClickListener;
    public int mPositionVal;
    private int mSelectedMax;
    private Set<Integer> mSelectedView;
    private TagAdapter mTagAdapter;

    public interface OnSelectListener {
        void onSelected(Set<Integer> selectPosSet);
    }

    public interface OnTagClickListener {
        boolean onTagClick(View view, int position, FlowLayout parent);
    }

    public TagMuilFlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mSelectedMax = -1;
        this.mSelectedView = new HashSet();
        this.mPositionVal = 0;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.TagFlowLayoutStyle);
        this.mSelectedMax = typedArrayObtainStyledAttributes.getInt(2, -1);
        typedArrayObtainStyledAttributes.recycle();
    }

    private void changeAdapter() {
        removeAllViews();
        TagAdapter tagAdapter = this.mTagAdapter;
        HashSet<Integer> preCheckedList = tagAdapter.getPreCheckedList();
        for (final int i2 = 0; i2 < tagAdapter.getCount(); i2++) {
            View view = tagAdapter.getView(this, i2, tagAdapter.getItem(i2));
            final TagView tagView = new TagView(getContext());
            view.setDuplicateParentStateEnabled(true);
            if (view.getLayoutParams() != null) {
                tagView.setLayoutParams(view.getLayoutParams());
            } else {
                ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(-2, -2);
                marginLayoutParams.setMargins(dip2px(getContext(), 5.0f), dip2px(getContext(), 5.0f), dip2px(getContext(), 5.0f), dip2px(getContext(), 5.0f));
                tagView.setLayoutParams(marginLayoutParams);
            }
            view.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            tagView.addView(view);
            addView(tagView);
            if (preCheckedList.contains(Integer.valueOf(i2))) {
                setChildChecked(i2, tagView, false);
            }
            if (this.mTagAdapter.setSelected(i2, tagAdapter.getItem(i2))) {
                setChildChecked(i2, tagView, false);
            }
            view.setClickable(false);
            List<BiaoqianBeab.DataBean> list = this.dataBiao;
            if (list != null && list.size() > 0 && this.dataBiao.get(i2).getUser_label().equals("1")) {
                doSelect(tagView, i2, false);
            }
            tagView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.utils.TagMuilFlowLayout.1
                @Override // android.view.View.OnClickListener
                public void onClick(View v2) {
                    TagMuilFlowLayout.this.doSelect(tagView, i2, true);
                    if (TagMuilFlowLayout.this.mOnTagClickListener != null) {
                        TagMuilFlowLayout.this.mOnTagClickListener.onTagClick(tagView, i2, TagMuilFlowLayout.this);
                    }
                }
            });
        }
        this.mSelectedView.addAll(preCheckedList);
    }

    public static int dip2px(Context context, float dpValue) {
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doSelect(TagView child, int position, boolean isClick) {
        if (child.isChecked()) {
            setChildUnChecked(position, child, isClick);
            this.mSelectedView.remove(Integer.valueOf(position));
        } else if (this.mSelectedMax == 1 && this.mSelectedView.size() == 1) {
            Integer next = this.mSelectedView.iterator().next();
            setChildUnChecked(next.intValue(), (TagView) getChildAt(next.intValue()), isClick);
            setChildChecked(position, child, isClick);
            this.mSelectedView.remove(next);
            this.mSelectedView.add(Integer.valueOf(position));
        } else {
            if (this.mSelectedMax > 0 && this.mSelectedView.size() >= this.mSelectedMax) {
                try {
                    NewToast.showShort(ProjectApp.instance(), "最多选择2个标签！", 0).show();
                    return;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return;
                }
            }
            setChildChecked(position, child, isClick);
            this.mSelectedView.add(Integer.valueOf(position));
        }
        OnSelectListener onSelectListener = this.mOnSelectListener;
        if (onSelectListener != null) {
            onSelectListener.onSelected(new HashSet(this.mSelectedView));
        }
    }

    private void setChildChecked(int position, TagView view, boolean isClick) {
        view.setChecked(true);
        this.mTagAdapter.onSelected(position, view.getTagView(), isClick);
    }

    private void setChildUnChecked(int position, TagView view, boolean isClick) {
        view.setChecked(false);
        this.mTagAdapter.unSelected(position, view.getTagView(), isClick);
    }

    public TagAdapter getAdapter() {
        return this.mTagAdapter;
    }

    public List<BiaoqianBeab.DataBean> getDataBiao() {
        return this.dataBiao;
    }

    public Set<Integer> getSelectedList() {
        return new HashSet(this.mSelectedView);
    }

    public int getmPositionVal() {
        return this.mPositionVal;
    }

    @Override // com.psychiatrygarden.utils.TagAdapter.OnDataChangedListener
    public void onChanged() {
        this.mSelectedView.clear();
        changeAdapter();
    }

    @Override // com.psychiatrygarden.utils.FlowLayout, android.view.View
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
                    setChildChecked(i2, tagView, false);
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

    public void setAdapter(TagAdapter adapter) {
        this.mTagAdapter = adapter;
        adapter.setOnDataChangedListener(this);
        this.mSelectedView.clear();
        changeAdapter();
    }

    public void setDataBiao(List<BiaoqianBeab.DataBean> dataBiao) {
        this.dataBiao = dataBiao;
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
    }

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        this.mOnTagClickListener = onTagClickListener;
    }

    public void setmPositionVal(int mPositionVal) {
        this.mPositionVal = mPositionVal;
    }

    public TagMuilFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagMuilFlowLayout(Context context) {
        this(context, null);
    }
}
