package com.psychiatrygarden.fragmenthome;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.ViewPagerHelper;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.activity.courselist.bean.CurriculumCategroyBean;
import com.psychiatrygarden.activity.courselist.fragment.CurriculumFragment;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CourseNewFragment extends BaseFragment {
    private static final int COURSEDATA = 4114;
    private ImageView iv_network_load_fail;
    private ImageView load_anim;
    private MagicIndicator mMagicIndicator;
    private ViewPager viewpager;
    private List<CurriculumCategroyBean.DataDTO> dataNewList = new ArrayList();
    private int mItemWidth = 0;

    @SuppressLint({"HandlerLeak"})
    private Handler mHandler = new Handler() { // from class: com.psychiatrygarden.fragmenthome.CourseNewFragment.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 4114) {
                CourseNewFragment.this.getCourseData();
            }
        }
    };

    /* renamed from: com.psychiatrygarden.fragmenthome.CourseNewFragment$3, reason: invalid class name */
    public class AnonymousClass3 extends CommonNavigatorAdapter {
        public AnonymousClass3() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getTitleView$0(int i2, View view) throws Resources.NotFoundException {
            CourseNewFragment.this.viewpager.setCurrentItem(i2);
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            return CourseNewFragment.this.dataNewList.size();
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerIndicator getIndicator(Context context) {
            return null;
        }

        @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(Context context, final int index) {
            CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(CourseNewFragment.this.getActivity());
            commonPagerTitleView.setContentView(R.layout.item_question_column);
            final TextView textView = (TextView) commonPagerTitleView.findViewById(R.id.tv_column_name);
            final ImageView imageView = (ImageView) commonPagerTitleView.findViewById(R.id.img_choose);
            textView.setText(((CurriculumCategroyBean.DataDTO) CourseNewFragment.this.dataNewList.get(index)).getTitle());
            commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.u3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    this.f16031c.lambda$getTitleView$0(index, view);
                }
            });
            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.psychiatrygarden.fragmenthome.CourseNewFragment.3.1
                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onDeselected(int index2, int totalCount) {
                    textView.setTypeface(Typeface.DEFAULT);
                    textView.setTextSize(14.0f);
                    imageView.setVisibility(8);
                    if (SkinManager.getCurrentSkinType(((BaseFragment) CourseNewFragment.this).mContext) == 1) {
                        textView.setTextColor(Color.parseColor("#575F79"));
                    } else {
                        textView.setTextColor(Color.parseColor("#909499"));
                    }
                }

                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onEnter(int index2, int totalCount, float enterPercent, boolean leftToRight) {
                }

                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onLeave(int index2, int totalCount, float leavePercent, boolean leftToRight) {
                }

                @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                public void onSelected(int index2, int totalCount) {
                    textView.setTypeface(Typeface.DEFAULT_BOLD);
                    textView.setTextSize(16.0f);
                    imageView.setVisibility(0);
                    if (SkinManager.getCurrentSkinType(((BaseFragment) CourseNewFragment.this).mContext) == 1) {
                        textView.setTextColor(Color.parseColor("#7380A9"));
                    } else {
                        textView.setTextColor(Color.parseColor("#141516"));
                    }
                }
            });
            return commonPagerTitleView;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getCourseData() {
        this.iv_network_load_fail.setVisibility(8);
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("identity_id", "" + SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, getActivity()));
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.curriculumCategroyUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.CourseNewFragment.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) throws Resources.NotFoundException {
                super.onFailure(t2, errorNo, strMsg);
                CourseNewFragment.this.getInfoData("");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) throws Resources.NotFoundException {
                super.onSuccess((AnonymousClass4) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (jSONObject.optString("code").equals("200")) {
                        CourseNewFragment.this.getInfoData(jSONObject.optString("data"));
                    } else if (jSONObject.optString("code").equals("309")) {
                        ToastUtil.shortToast(CourseNewFragment.this.getActivity(), jSONObject.optString("message"));
                    } else {
                        CourseNewFragment.this.getInfoData("");
                    }
                } catch (Exception unused) {
                    CourseNewFragment.this.getInfoData("");
                }
            }
        });
    }

    private void initTabColumn() throws Resources.NotFoundException {
        ArrayList arrayList = new ArrayList();
        List<CurriculumCategroyBean.DataDTO> list = this.dataNewList;
        if (list == null || list.size() <= 1) {
            this.mMagicIndicator.setVisibility(8);
        } else {
            this.mMagicIndicator.setVisibility(0);
        }
        for (int i2 = 0; i2 < this.dataNewList.size(); i2++) {
            Bundle bundle = new Bundle();
            bundle.putString("id", this.dataNewList.get(i2).getId());
            bundle.putString("title", this.dataNewList.get(i2).getTitle());
            arrayList.add(new BaseViewPagerAdapter.PagerInfo(this.dataNewList.get(i2).getTitle(), CurriculumFragment.class, bundle));
        }
        if (isAdded()) {
            this.viewpager.setAdapter(new BaseViewPagerAdapter(this.mContext, getChildFragmentManager(), arrayList));
            this.viewpager.setCurrentItem(0);
            this.viewpager.setOffscreenPageLimit(2);
            CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
            commonNavigator.setSkimOver(true);
            commonNavigator.setAdapter(new AnonymousClass3());
            this.mMagicIndicator.setNavigator(commonNavigator);
            ViewPagerHelper.bind(this.mMagicIndicator, this.viewpager);
            mStopAnim(this.load_anim);
            this.load_anim.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(View view) {
        this.mHandler.sendEmptyMessage(4114);
    }

    public static CourseNewFragment newInstance() {
        Bundle bundle = new Bundle();
        CourseNewFragment courseNewFragment = new CourseNewFragment();
        courseNewFragment.setArguments(bundle);
        return courseNewFragment;
    }

    public void getInfoData(String mDataStr) throws Resources.NotFoundException {
        if (mDataStr != null && !"".equals(mDataStr)) {
            this.iv_network_load_fail.setVisibility(8);
            this.dataNewList = (List) new Gson().fromJson(mDataStr, new TypeToken<List<CurriculumCategroyBean.DataDTO>>() { // from class: com.psychiatrygarden.fragmenthome.CourseNewFragment.2
            }.getType());
            initTabColumn();
        } else {
            int currentSkinType = SkinManager.getCurrentSkinType(this.mContext);
            this.iv_network_load_fail.setVisibility(0);
            this.iv_network_load_fail.setImageResource(currentSkinType == 0 ? R.drawable.icon_net_load_error : R.drawable.icon_net_load_error_night);
            mStopAnim(this.load_anim);
            this.load_anim.setVisibility(8);
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_course_new;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.mMagicIndicator = (MagicIndicator) holder.get(R.id.magic_indicator);
        this.viewpager = (ViewPager) holder.get(R.id.viewpager);
        this.load_anim = (ImageView) holder.get(R.id.load_anim);
        this.iv_network_load_fail = (ImageView) holder.get(R.id.iv_network_load_fail);
        if (SkinManager.getCurrentSkinType(getActivity()) == 0) {
            this.iv_network_load_fail.setImageDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.kong_data));
        } else {
            this.iv_network_load_fail.setImageDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.zanwukecheng2_night));
        }
        this.mItemWidth = CommonUtil.getScreenWidth(this.mContext) / 4;
        mStartAnim(this.load_anim);
        this.iv_network_load_fail.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.t3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16005c.lambda$initViews$0(view);
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(final String str) {
        if (str.equals(EventBusConstant.VIDEO_COURSE_DAKA)) {
            getCourseData();
        }
        if (str.equals(EventBusConstant.EVENT_PHONE_CHANGE_SUCCESS)) {
            getCourseData();
        }
        if ("JumpZHibo".equals(str)) {
            getCourseData();
        }
        if ("BuySuccess".equals(str)) {
            try {
                ViewPager viewPager = this.viewpager;
                if (viewPager == null || viewPager.getAdapter().getSize() <= 2) {
                    return;
                }
                this.viewpager.setCurrentItem(1);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        this.mHandler.sendEmptyMessage(4114);
    }
}
