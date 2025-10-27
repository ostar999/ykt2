package com.easefun.polyv.livecloudclass.modules.download;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecloudclass.modules.download.fragment.PLVPlaybackCacheFragment;
import com.easefun.polyv.livecommon.ui.widget.itemview.adapter.PLVViewPagerAdapter;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.PLVMagicIndicator;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.PLVViewPagerHelper;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.PLVCommonNavigator;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerIndicator;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerTitleView;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.PLVCommonNavigatorAdapter;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.indicators.PLVLinePagerIndicator;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.titles.PLVColorTransitionPagerTitleView;
import com.easefun.polyv.livecommon.ui.window.PLVBaseActivity;
import com.plv.foundationsdk.utils.PLVFormatUtils;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVPlaybackCacheActivity extends PLVBaseActivity implements View.OnClickListener {
    private ImageView playbackCacheBackIv;
    private final List<PLVPlaybackCacheFragment> playbackCacheFragments = new ArrayList();
    private PLVMagicIndicator playbackCacheTab;
    private ViewPager playbackCacheVp;
    private PagerAdapter viewPagerAdapter;

    /* renamed from: com.easefun.polyv.livecloudclass.modules.download.PLVPlaybackCacheActivity$1, reason: invalid class name */
    public class AnonymousClass1 extends PLVCommonNavigator {

        /* renamed from: com.easefun.polyv.livecloudclass.modules.download.PLVPlaybackCacheActivity$1$1, reason: invalid class name and collision with other inner class name */
        public class C01071 extends PLVCommonNavigatorAdapter {
            public C01071() {
            }

            @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.PLVCommonNavigatorAdapter
            public int getCount() {
                return PLVPlaybackCacheActivity.this.playbackCacheFragments.size();
            }

            @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.PLVCommonNavigatorAdapter
            public IPLVPagerIndicator getIndicator(Context context) {
                return new PLVLinePagerIndicator(context) { // from class: com.easefun.polyv.livecloudclass.modules.download.PLVPlaybackCacheActivity.1.1.2
                    {
                        setMode(1);
                        setLineHeight(ConvertUtils.dp2px(2.0f));
                        setRoundRadius(ConvertUtils.dp2px(1.0f));
                        setColors(Integer.valueOf(PLVFormatUtils.parseColor("#FFFFFF")));
                    }
                };
            }

            @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.PLVCommonNavigatorAdapter
            public IPLVPagerTitleView getTitleView(Context context, int i2) {
                if (PLVPlaybackCacheActivity.this.playbackCacheFragments.isEmpty() || PLVPlaybackCacheActivity.this.playbackCacheFragments.size() <= i2) {
                    return null;
                }
                return new PLVColorTransitionPagerTitleView(context, i2) { // from class: com.easefun.polyv.livecloudclass.modules.download.PLVPlaybackCacheActivity.1.1.1
                    final /* synthetic */ int val$index;

                    {
                        this.val$index = i2;
                        setPadding(ConvertUtils.dp2px(16.0f), 0, ConvertUtils.dp2px(16.0f), 0);
                        setNormalColor(PLVFormatUtils.parseColor("#ADADC0"));
                        setSelectedColor(PLVFormatUtils.parseColor("#FFFFFF"));
                        setText(((PLVPlaybackCacheFragment) PLVPlaybackCacheActivity.this.playbackCacheFragments.get(i2)).getTitle());
                        setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.download.PLVPlaybackCacheActivity.1.1.1.1
                            @Override // android.view.View.OnClickListener
                            public void onClick(View view) throws Resources.NotFoundException {
                                PLVPlaybackCacheActivity.this.playbackCacheVp.setCurrentItem(C01081.this.val$index);
                            }
                        });
                    }
                };
            }
        }

        public AnonymousClass1(Context context) {
            super(context);
            setAdapter(new C01071());
        }
    }

    private void findView() {
        this.playbackCacheBackIv = (ImageView) findViewById(R.id.plv_playback_cache_back_iv);
        this.playbackCacheTab = (PLVMagicIndicator) findViewById(R.id.plv_playback_cache_tab);
        this.playbackCacheVp = (ViewPager) findViewById(R.id.plv_playback_cache_vp);
        this.playbackCacheBackIv.setOnClickListener(this);
    }

    private void initTab() throws Resources.NotFoundException {
        this.playbackCacheFragments.add(PLVPlaybackCacheFragment.create("下载中", true));
        this.playbackCacheFragments.add(PLVPlaybackCacheFragment.create("已下载", false));
        PLVViewPagerAdapter pLVViewPagerAdapter = new PLVViewPagerAdapter(getSupportFragmentManager(), this.playbackCacheFragments);
        this.viewPagerAdapter = pLVViewPagerAdapter;
        this.playbackCacheVp.setAdapter(pLVViewPagerAdapter);
        this.playbackCacheVp.setOffscreenPageLimit(this.playbackCacheFragments.size() - 1);
        this.playbackCacheTab.setNavigator(new AnonymousClass1(this));
        PLVViewPagerHelper.bind(this.playbackCacheTab, this.playbackCacheVp);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == this.playbackCacheBackIv.getId()) {
            finish();
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) throws Resources.NotFoundException {
        super.onCreate(bundle);
        setContentView(R.layout.plv_playback_cache_activity);
        findView();
        initTab();
    }
}
