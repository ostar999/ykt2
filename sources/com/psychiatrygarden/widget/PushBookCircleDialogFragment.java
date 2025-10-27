package com.psychiatrygarden.widget;

import android.R;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.psychiatrygarden.bean.PushBookData;
import com.psychiatrygarden.forum.PushBookRecordFrag;
import com.psychiatrygarden.forum.PushBookShelfFrag;
import com.psychiatrygarden.interfaceclass.LocalBroadcastManager;
import com.psychiatrygarden.utils.AnimUtil;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.imagezoom.HackyViewPager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class PushBookCircleDialogFragment extends DialogFragment {
    private OnChoosedLisenter chooseCircleLisenter;
    private List<String> mChooseBookIds = new ArrayList();
    private List<PushBookData> mChooseBookItems = new ArrayList();
    private List<String> mLocalSaveIds = new ArrayList();
    private RelativeLayout mLyBottom;
    private UpdateBottomValueReceiver mReceiver;
    private TextView mTvCount;

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public MyPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            new ArrayList();
            this.fragments = fragments;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        /* renamed from: getCount */
        public int getSize() {
            return this.fragments.size();
        }

        @Override // androidx.fragment.app.FragmentPagerAdapter
        @NonNull
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }
    }

    public static abstract class OnChoosedLisenter {
        public abstract void onCircleChoosed(List<String> oldCircleIds, List<String> newCircleIds, List<PushBookData> mChooseCircleItems);
    }

    public class UpdateBottomValueReceiver extends BroadcastReceiver {
        private UpdateBottomValueReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            PushBookData pushBookData = (PushBookData) intent.getSerializableExtra("book");
            String id = pushBookData.getId();
            if (PushBookCircleDialogFragment.this.mChooseBookItems.size() > 0) {
                for (int i2 = 0; i2 < PushBookCircleDialogFragment.this.mChooseBookItems.size(); i2++) {
                    if (((PushBookData) PushBookCircleDialogFragment.this.mChooseBookItems.get(i2)).getId().equals(id)) {
                        if (!pushBookData.isSelected()) {
                            PushBookCircleDialogFragment.this.mChooseBookItems.remove(i2);
                            PushBookCircleDialogFragment.this.mChooseBookIds.remove(id);
                        }
                    } else if (!PushBookCircleDialogFragment.this.mChooseBookIds.contains(id)) {
                        PushBookCircleDialogFragment.this.mChooseBookItems.add(pushBookData);
                        PushBookCircleDialogFragment.this.mChooseBookIds.add(id);
                    }
                }
            } else {
                PushBookCircleDialogFragment.this.mChooseBookItems.add(pushBookData);
                PushBookCircleDialogFragment.this.mChooseBookIds.add(pushBookData.getId());
            }
            Log.e("book_res", "size===>" + PushBookCircleDialogFragment.this.mChooseBookItems.size());
            PushBookCircleDialogFragment pushBookCircleDialogFragment = PushBookCircleDialogFragment.this;
            pushBookCircleDialogFragment.setBottomTxtValue(pushBookCircleDialogFragment.mChooseBookItems.size());
        }
    }

    private int getContextRect(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.height();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onCreateView$0(InputMethodManager inputMethodManager, ClearEditText clearEditText, HackyViewPager hackyViewPager, List list, LinearLayout linearLayout, TextView textView, int i2, KeyEvent keyEvent) throws Resources.NotFoundException {
        if (i2 != 3) {
            return false;
        }
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(textView.getApplicationWindowToken(), 0);
        }
        String strTrim = clearEditText.getText().toString().trim();
        if (!strTrim.equals("")) {
            int currentItem = hackyViewPager.getCurrentItem();
            if (list.get(currentItem) instanceof PushBookShelfFrag) {
                ((PushBookShelfFrag) list.get(currentItem)).setmSearchKey(strTrim);
            }
            if (list.get(currentItem) instanceof PushBookRecordFrag) {
                ((PushBookRecordFrag) list.get(currentItem)).setmSearchKey(strTrim);
            }
            hackyViewPager.setLocked(true);
            hackyViewPager.setCurrentItem(0);
            linearLayout.setVisibility(8);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreateView$1(View view) {
        getDialog().dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreateView$2(View view) {
        getDialog().dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreateView$3(View view) {
        if (this.mChooseBookItems.isEmpty()) {
            getDialog().dismiss();
            return;
        }
        OnChoosedLisenter onChoosedLisenter = this.chooseCircleLisenter;
        if (onChoosedLisenter != null) {
            onChoosedLisenter.onCircleChoosed(this.mLocalSaveIds, this.mChooseBookIds, this.mChooseBookItems);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreateView$4(TextView textView, TextView textView2, View view, View view2, HackyViewPager hackyViewPager, View view3) throws Resources.NotFoundException {
        setTabUi(true, textView, textView2, view, view2, hackyViewPager);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreateView$5(TextView textView, TextView textView2, View view, View view2, HackyViewPager hackyViewPager, View view3) throws Resources.NotFoundException {
        setTabUi(false, textView, textView2, view, view2, hackyViewPager);
    }

    public static PushBookCircleDialogFragment newInstance(List<PushBookData> bookList) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("bookList", (Serializable) bookList);
        PushBookCircleDialogFragment pushBookCircleDialogFragment = new PushBookCircleDialogFragment();
        pushBookCircleDialogFragment.setArguments(bundle);
        return pushBookCircleDialogFragment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setBottomTxtValue(int count) {
        String str = "已推荐 " + count + " 本书";
        if (this.mTvCount != null) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
            if (SkinManager.getCurrentSkinType(getContext()) == 0) {
                spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor(count > 0 ? "#DD594A" : "#141516")), 4, str.length() - 2, 34);
            } else {
                spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor(count > 0 ? "#B2575C" : "#7380A9")), 4, str.length() - 2, 34);
            }
            this.mTvCount.setText(spannableStringBuilder);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTabUi(boolean isShelf, TextView mTvShelf, TextView mTvHistory, View mLineShelf, View mLineHistory, ViewPager viewPager) throws Resources.NotFoundException {
        if (isShelf) {
            mTvShelf.setTextColor(SkinManager.getCurrentSkinType(getContext()) == 1 ? Color.parseColor("#7380A9") : Color.parseColor("#141516"));
            mTvHistory.setTextColor(SkinManager.getCurrentSkinType(getContext()) == 1 ? Color.parseColor("#575F79") : Color.parseColor("#909499"));
            mLineShelf.setVisibility(0);
            mLineHistory.setVisibility(4);
            viewPager.setCurrentItem(0);
            return;
        }
        viewPager.setCurrentItem(1);
        mLineShelf.setVisibility(4);
        mLineHistory.setVisibility(0);
        mTvHistory.setTextColor(SkinManager.getCurrentSkinType(getContext()) == 1 ? Color.parseColor("#7380A9") : Color.parseColor("#141516"));
        mTvShelf.setTextColor(SkinManager.getCurrentSkinType(getContext()) == 1 ? Color.parseColor("#575F79") : Color.parseColor("#909499"));
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) throws Resources.NotFoundException {
        Window window = getDialog().getWindow();
        window.requestFeature(1);
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.color.transparent));
        getContextRect(getActivity());
        window.setLayout(-1, -1);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.dimAmount = 0.5f;
        window.setAttributes(attributes);
        getDialog().setCancelable(true);
        getDialog().setCanceledOnTouchOutside(true);
        View viewOnCreateView = super.onCreateView(inflater, container, savedInstanceState);
        if (viewOnCreateView == null) {
            viewOnCreateView = inflater.inflate(com.yikaobang.yixue.R.layout.dialog_push_book_view, container, false);
        }
        View view = viewOnCreateView;
        List list = (List) getArguments().getSerializable("bookList");
        this.mChooseBookItems.clear();
        this.mChooseBookItems.addAll(list);
        this.mReceiver = new UpdateBottomValueReceiver();
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(this.mReceiver, new IntentFilter("updateBottomUi"));
        ArrayList arrayList = new ArrayList();
        arrayList.add("书架");
        arrayList.add("阅读历史");
        final HackyViewPager hackyViewPager = (HackyViewPager) view.findViewById(com.yikaobang.yixue.R.id.viewPager);
        TextView textView = (TextView) view.findViewById(com.yikaobang.yixue.R.id.btn_sure);
        this.mTvCount = (TextView) view.findViewById(com.yikaobang.yixue.R.id.tv_count);
        final ClearEditText clearEditText = (ClearEditText) view.findViewById(com.yikaobang.yixue.R.id.ed_search);
        ImageView imageView = (ImageView) view.findViewById(com.yikaobang.yixue.R.id.btn_cancel);
        this.mLyBottom = (RelativeLayout) view.findViewById(com.yikaobang.yixue.R.id.ly_bottom);
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(com.yikaobang.yixue.R.id.ly_shelf);
        final TextView textView2 = (TextView) view.findViewById(com.yikaobang.yixue.R.id.tv_shelf);
        final View viewFindViewById = view.findViewById(com.yikaobang.yixue.R.id.line_shelf);
        RelativeLayout relativeLayout2 = (RelativeLayout) view.findViewById(com.yikaobang.yixue.R.id.ly_history);
        final TextView textView3 = (TextView) view.findViewById(com.yikaobang.yixue.R.id.tv_history);
        final View viewFindViewById2 = view.findViewById(com.yikaobang.yixue.R.id.line_history);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(com.yikaobang.yixue.R.id.ly_content);
        final LinearLayout linearLayout2 = (LinearLayout) view.findViewById(com.yikaobang.yixue.R.id.top_tab_ll);
        linearLayout.setLayoutParams(new RelativeLayout.LayoutParams(-1, getContext().getResources().getDisplayMetrics().heightPixels - ScreenUtil.getPxByDp(getContext(), 96)));
        AnimUtil.fromBottomToTopAnim(linearLayout);
        if (this.mChooseBookItems.size() > 0) {
            setBottomTxtValue(this.mChooseBookItems.size());
            for (PushBookData pushBookData : this.mChooseBookItems) {
                this.mLocalSaveIds.add(pushBookData.getId());
                this.mChooseBookIds.add(pushBookData.getId());
            }
        }
        final ArrayList arrayList2 = new ArrayList();
        arrayList2.add(PushBookShelfFrag.newInstance(true, this.mChooseBookIds));
        arrayList2.add(PushBookRecordFrag.newInstance(false, this.mChooseBookIds));
        hackyViewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager(), arrayList2));
        hackyViewPager.setOffscreenPageLimit(1);
        hackyViewPager.setCurrentItem(0);
        final InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService("input_method");
        clearEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.psychiatrygarden.widget.ce
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView4, int i2, KeyEvent keyEvent) {
                return PushBookCircleDialogFragment.lambda$onCreateView$0(inputMethodManager, clearEditText, hackyViewPager, arrayList2, linearLayout2, textView4, i2, keyEvent);
            }
        });
        clearEditText.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.widget.PushBookCircleDialogFragment.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
                if (s2.length() == 0) {
                    int currentItem = hackyViewPager.getCurrentItem();
                    if (arrayList2.get(currentItem) instanceof PushBookShelfFrag) {
                        ((PushBookShelfFrag) arrayList2.get(currentItem)).setmSearchKey("");
                    }
                    if (arrayList2.get(currentItem) instanceof PushBookRecordFrag) {
                        ((PushBookRecordFrag) arrayList2.get(currentItem)).setmSearchKey("");
                    }
                    hackyViewPager.setLocked(false);
                    linearLayout2.setVisibility(0);
                }
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
            }
        });
        view.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.de
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f16408c.lambda$onCreateView$1(view2);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.ee
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f16441c.lambda$onCreateView$2(view2);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.fe
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f16489c.lambda$onCreateView$3(view2);
            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.ge
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) throws Resources.NotFoundException {
                this.f16518c.lambda$onCreateView$4(textView2, textView3, viewFindViewById, viewFindViewById2, hackyViewPager, view2);
            }
        });
        relativeLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.he
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) throws Resources.NotFoundException {
                this.f16552c.lambda$onCreateView$5(textView2, textView3, viewFindViewById, viewFindViewById2, hackyViewPager, view2);
            }
        });
        hackyViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.widget.PushBookCircleDialogFragment.2
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i2, float v2, int i12) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i2) throws Resources.NotFoundException {
                PushBookCircleDialogFragment.this.setTabUi(i2 == 0, textView2, textView3, viewFindViewById, viewFindViewById2, hackyViewPager);
                if (i2 == 0) {
                    if (arrayList2.get(0) instanceof PushBookShelfFrag) {
                        ((PushBookShelfFrag) arrayList2.get(0)).updateChoosedItem(PushBookCircleDialogFragment.this.mChooseBookIds);
                    }
                } else if (arrayList2.get(1) instanceof PushBookRecordFrag) {
                    ((PushBookRecordFrag) arrayList2.get(1)).updateChoosedItem(PushBookCircleDialogFragment.this.mChooseBookIds);
                }
            }
        });
        return view;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(this.mReceiver);
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnDismissListener
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        getDialog().dismiss();
    }

    public void setOnChoosedLisenter(OnChoosedLisenter lisenter) {
        this.chooseCircleLisenter = lisenter;
    }

    @Override // androidx.fragment.app.DialogFragment
    public void show(FragmentManager manager, String tag) {
        try {
            manager.beginTransaction().remove(this).commit();
            super.show(manager, tag);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
