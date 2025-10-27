package com.psychiatrygarden.activity.chat;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.ViewPagerHelper;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.indicators.WrapPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.SimplePagerTitleView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.adapter.GroupChatSortAdapter;
import com.psychiatrygarden.bean.GroupChatCategoryBean;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.fragmenthome.chat.GroupChatListFragment;
import com.psychiatrygarden.http.ChatRequest;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class GroupChatHomeActivity extends BaseActivity implements OnItemClickListener, QuestionDataCallBack<String> {
    private List<GroupChatCategoryBean.DataDTO> data;
    private GroupChatSortAdapter groupChatSortAdapter;
    private LinearLayout ll_search;
    private MagicIndicator magic_chat_group;
    private RecyclerView recycleview_group_chat;
    private TextView tv_empty_content;
    private ViewPager viewpager_group_chat;

    private void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(0);
        this.recycleview_group_chat.setLayoutManager(linearLayoutManager);
        ChatRequest.getIntance(this).communityCategory(this);
    }

    private void initViewpagerData(final int position) throws Resources.NotFoundException {
        final List<BaseViewPagerAdapter.PagerInfo> viewPageInfo = getViewPageInfo(position);
        if (viewPageInfo.size() <= 0) {
            this.viewpager_group_chat.setVisibility(8);
            this.magic_chat_group.setVisibility(8);
            this.tv_empty_content.setVisibility(0);
            return;
        }
        this.viewpager_group_chat.setVisibility(0);
        this.magic_chat_group.setVisibility(0);
        this.tv_empty_content.setVisibility(8);
        BaseViewPagerAdapter baseViewPagerAdapter = new BaseViewPagerAdapter(this.mContext, getSupportFragmentManager(), viewPageInfo);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setLeftPadding(ScreenUtil.getPxByDp((Context) this, 15));
        commonNavigator.setAdapter(new CommonNavigatorAdapter() { // from class: com.psychiatrygarden.activity.chat.GroupChatHomeActivity.1
            @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
            public int getCount() {
                List list = viewPageInfo;
                if (list == null) {
                    return 0;
                }
                return list.size();
            }

            @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
            public IPagerIndicator getIndicator(Context context) {
                WrapPagerIndicator wrapPagerIndicator = new WrapPagerIndicator(context);
                wrapPagerIndicator.setRoundRadius(ScreenUtil.getPxByDp((Context) GroupChatHomeActivity.this, 18));
                wrapPagerIndicator.setFillColor(Color.parseColor("#F7F7F7"));
                return wrapPagerIndicator;
            }

            @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(((GroupChatCategoryBean.DataDTO) GroupChatHomeActivity.this.data.get(position)).getChildren().get(index).getName());
                simplePagerTitleView.setTextSize(2, 13.0f);
                simplePagerTitleView.setPadding(ScreenUtil.getPxByDp((Context) GroupChatHomeActivity.this, 15), ScreenUtil.getPxByDp((Context) GroupChatHomeActivity.this, 8), ScreenUtil.getPxByDp((Context) GroupChatHomeActivity.this, 15), ScreenUtil.getPxByDp((Context) GroupChatHomeActivity.this, 8));
                simplePagerTitleView.setNormalColor(Color.parseColor("#ADADAD"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#1E1E1E"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chat.GroupChatHomeActivity.1.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v2) throws Resources.NotFoundException {
                        GroupChatHomeActivity.this.viewpager_group_chat.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }
        });
        this.magic_chat_group.setNavigator(commonNavigator);
        ViewPagerHelper.bind(this.magic_chat_group, this.viewpager_group_chat);
        this.viewpager_group_chat.setAdapter(baseViewPagerAdapter);
        this.viewpager_group_chat.setOffscreenPageLimit(3);
        this.viewpager_group_chat.setCurrentItem(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0(View view) {
        startActivity(new Intent(this, (Class<?>) GroupChatSearchActivity.class));
    }

    public List<BaseViewPagerAdapter.PagerInfo> getViewPageInfo(int position) {
        ArrayList arrayList = new ArrayList();
        try {
            if (this.data != null) {
                for (int i2 = 0; i2 < this.data.get(position).getChildren().size(); i2++) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", this.data.get(position).getChildren().get(i2).getId());
                    arrayList.add(new BaseViewPagerAdapter.PagerInfo(this.data.get(position).getChildren().get(i2).getName(), GroupChatListFragment.class, bundle));
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return arrayList;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        setTitle("加群");
        this.ll_search = (LinearLayout) findViewById(R.id.ll_search);
        this.tv_empty_content = (TextView) findViewById(R.id.tv_empty_content);
        this.recycleview_group_chat = (RecyclerView) findViewById(R.id.recycleview_group_chat);
        this.magic_chat_group = (MagicIndicator) findViewById(R.id.magic_chat_group);
        this.viewpager_group_chat = (ViewPager) findViewById(R.id.viewpager_group_chat);
        initData();
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            findViewById(R.id.view_line).setBackgroundColor(Color.parseColor("#eeeeee"));
        } else {
            findViewById(R.id.view_line).setBackgroundColor(getResources().getColor(R.color.app_theme_night));
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
    }

    @Override // com.chad.library.adapter.base.listener.OnItemClickListener
    public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
        try {
            if (this.data == null) {
                return;
            }
            int i2 = 0;
            while (i2 < this.data.size()) {
                this.data.get(i2).setSelected(i2 == position);
                i2++;
            }
            initViewpagerData(position);
            this.groupChatSortAdapter.notifyDataSetChanged();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_chat_group_home1);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.ll_search.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chat.o
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11169c.lambda$setListenerForWidget$0(view);
            }
        });
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String s2, String requstUrl) {
        try {
            GroupChatCategoryBean groupChatCategoryBean = (GroupChatCategoryBean) new Gson().fromJson(s2, GroupChatCategoryBean.class);
            if (!groupChatCategoryBean.getCode().equals("200")) {
                ToastUtil.shortToast(this, groupChatCategoryBean.getMessage());
                return;
            }
            List<GroupChatCategoryBean.DataDTO> data = groupChatCategoryBean.getData();
            this.data = data;
            if (data.size() > 0) {
                this.data.get(0).setSelected(true);
                initViewpagerData(0);
            }
            GroupChatSortAdapter groupChatSortAdapter = new GroupChatSortAdapter(R.layout.adapter_group_chat_sort, this.data);
            this.groupChatSortAdapter = groupChatSortAdapter;
            this.recycleview_group_chat.setAdapter(groupChatSortAdapter);
            this.groupChatSortAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.chat.p
                @Override // com.chad.library.adapter.base.listener.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                    this.f11170c.onItemClick(baseQuickAdapter, view, i2);
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
