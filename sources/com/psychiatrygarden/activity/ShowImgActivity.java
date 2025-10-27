package com.psychiatrygarden.activity;

import android.content.res.Resources;
import android.os.Bundle;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.psychiatrygarden.utils.weblong.HackyViewPager;
import com.psychiatrygarden.utils.weblong.ImageViewPagerAdapter;
import com.psychiatrygarden.utils.weblong.StringUtils;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class ShowImgActivity extends BaseActivity {
    ImageViewPagerAdapter adapter;
    HackyViewPager pager;
    private final List<String> mList = new ArrayList();
    String mInfo = "";

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        setTitle(getResources().getString(R.string.app_name));
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(AliyunLogCommon.LogLevel.INFO)) {
            this.mInfo = extras.getString(AliyunLogCommon.LogLevel.INFO);
        }
        if (StringUtils.isEmpty(this.mInfo)) {
            finish();
            return;
        }
        this.pager = (HackyViewPager) findViewById(R.id.pager);
        this.mList.add(this.mInfo);
        ImageViewPagerAdapter imageViewPagerAdapter = new ImageViewPagerAdapter(getSupportFragmentManager(), this.mList);
        this.adapter = imageViewPagerAdapter;
        this.pager.setAdapter(imageViewPagerAdapter);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_common_showimg);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
