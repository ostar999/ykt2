package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.LoginActivity;
import com.psychiatrygarden.activity.WebLongSaveActivity;
import com.psychiatrygarden.activity.ebook.BookStoreActivity;
import com.psychiatrygarden.activity.material.InformationProjectAct;
import com.psychiatrygarden.adapter.ForumProjectAdp;
import com.psychiatrygarden.bean.ForumTopBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.forum.VolunteerRankingAct;
import com.psychiatrygarden.forum.experience.SearchExperienceAct;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class CircleKingKongLayout extends LinearLayout {
    private ForumProjectAdp mAdapter;
    private View mLine;
    private RelativeLayout mLyParent;
    private RecyclerView mRecyclerView;

    public CircleKingKongLayout(Context context) {
        super(context);
        init();
    }

    private int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(1, dpValue, getResources().getDisplayMetrics());
    }

    private void init() {
        View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.view_forum_top_gride, this);
        this.mRecyclerView = (RecyclerView) viewInflate.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(0);
        this.mRecyclerView.setLayoutManager(linearLayoutManager);
        this.mLyParent = (RelativeLayout) viewInflate.findViewById(R.id.parent_layout);
        this.mLine = viewInflate.findViewById(R.id.view_line);
        final View viewFindViewById = viewInflate.findViewById(R.id.main_line);
        ForumProjectAdp forumProjectAdp = new ForumProjectAdp();
        this.mAdapter = forumProjectAdp;
        this.mRecyclerView.setAdapter(forumProjectAdp);
        this.mAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.widget.r2
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f16843c.lambda$init$0(baseQuickAdapter, view, i2);
            }
        });
        this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.psychiatrygarden.widget.CircleKingKongLayout.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int iComputeHorizontalScrollExtent = recyclerView.computeHorizontalScrollExtent();
                int iComputeHorizontalScrollRange = recyclerView.computeHorizontalScrollRange();
                viewFindViewById.setTranslationX((CircleKingKongLayout.this.mLyParent.getWidth() - viewFindViewById.getWidth()) * (recyclerView.computeHorizontalScrollOffset() / (iComputeHorizontalScrollRange - iComputeHorizontalScrollExtent)));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        ForumTopBean.TopModule item = this.mAdapter.getItem(i2);
        if (!UserConfig.isLogin()) {
            getContext().startActivity(new Intent(getContext(), (Class<?>) LoginActivity.class));
        }
        String code = item.getCode();
        code.hashCode();
        switch (code) {
            case "exp":
                SearchExperienceAct.newIntent(getContext());
                break;
            case "file":
                InformationProjectAct.newIntent(getContext());
                break;
            case "ebook":
                BookStoreActivity.newIntent(getContext());
                break;
            case "ranking":
                VolunteerRankingAct.startActivity(getContext(), "2");
                break;
            case "gee_data":
                UserConfig.getInstance().getUser().getIs_vip();
                String mobile = UserConfig.getInstance().getUser().getMobile();
                Intent intent = new Intent(ProjectApp.instance(), (Class<?>) WebLongSaveActivity.class);
                intent.putExtra("title", ProjectApp.instance().getResources().getString(R.string.app_name));
                intent.putExtra("noDark", true);
                intent.putExtra("web_url", item.getRedirect() + "?mobile=" + mobile);
                intent.setFlags(268435456);
                ProjectApp.instance().startActivity(intent);
                break;
            default:
                if (item.getType().equals("1") && !TextUtils.isEmpty(item.getRedirect())) {
                    Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse(item.getRedirect()));
                    intent2.setFlags(268435456);
                    ProjectApp.instance().startActivity(intent2);
                    break;
                }
                break;
        }
    }

    public void setData(List<ForumTopBean.TopModule> list) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < list.size(); i2++) {
            ForumTopBean.TopModule topModule = new ForumTopBean.TopModule();
            topModule.setCode(list.get(i2).getCode());
            if (list.get(i2).getCode().equals("exp")) {
                topModule.setName("经验热搜榜");
                arrayList.add(topModule);
            } else if (list.get(i2).getCode().equals("ebook")) {
                topModule.setName("图书热搜榜");
                arrayList.add(topModule);
            } else if (list.get(i2).getCode().equals("file")) {
                topModule.setName("资料热搜榜");
                arrayList.add(topModule);
            }
        }
        SharePreferencesUtils.writeStrConfig("top_grid_data", new Gson().toJson(arrayList), getContext());
        if (this.mLyParent != null) {
            if (list.size() > 5) {
                this.mLyParent.setVisibility(0);
                this.mLine.setVisibility(8);
            } else {
                this.mLyParent.setVisibility(8);
                this.mLine.setVisibility(0);
            }
        }
        this.mAdapter.setList(list);
    }

    public CircleKingKongLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleKingKongLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
}
