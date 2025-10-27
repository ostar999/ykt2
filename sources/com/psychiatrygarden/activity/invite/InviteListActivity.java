package com.psychiatrygarden.activity.invite;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.player.aliyunplayerbase.net.ServiceCommon;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.UserCommentInfoActivity;
import com.psychiatrygarden.bean.InviteListBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yikaobang.yixue.R;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0019\u001a\u00020\u001aH\u0002J\b\u0010\u001b\u001a\u00020\u001aH\u0016J\u0012\u0010\u001c\u001a\u00020\u001a2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0014J\u0012\u0010\u001f\u001a\u00020\u001a2\b\u0010 \u001a\u0004\u0018\u00010!H\u0016J\u0010\u0010\"\u001a\u00020\u001a2\u0006\u0010#\u001a\u00020$H\u0016J\u0010\u0010%\u001a\u00020\u001a2\u0006\u0010#\u001a\u00020$H\u0016J\b\u0010&\u001a\u00020\u001aH\u0016J\b\u0010'\u001a\u00020\u001aH\u0016J\b\u0010(\u001a\u00020\u001aH\u0004R\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082.¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lcom/psychiatrygarden/activity/invite/InviteListActivity;", "Lcom/psychiatrygarden/activity/BaseActivity;", "Lcom/scwang/smartrefresh/layout/listener/OnRefreshLoadMoreListener;", "()V", "adapter", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/psychiatrygarden/bean/InviteListBean$TimeLineBean;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "btnInviteFriends", "Landroid/widget/TextView;", "lyInviteFriends", "Landroid/widget/LinearLayout;", "mNavBack", "Landroid/widget/ImageView;", "mNavTabbar", "Landroid/widget/RelativeLayout;", "mTvAllPerson", "mTvAllVipDay", Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "", ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE, "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "refresh", "Lcom/scwang/smartrefresh/layout/SmartRefreshLayout;", "getInviteList", "", "init", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onEventMainThread", "str", "", "onLoadMore", "refreshLayout", "Lcom/scwang/smartrefresh/layout/api/RefreshLayout;", "onRefresh", "setContentView", "setListenerForWidget", "setStatusBarTranslucent", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class InviteListActivity extends BaseActivity implements OnRefreshLoadMoreListener {
    private BaseQuickAdapter<InviteListBean.TimeLineBean, BaseViewHolder> adapter;
    private TextView btnInviteFriends;
    private LinearLayout lyInviteFriends;
    private ImageView mNavBack;
    private RelativeLayout mNavTabbar;
    private TextView mTvAllPerson;
    private TextView mTvAllVipDay;
    private int page = 1;
    private int pageSize = 20;
    private RecyclerView recyclerView;
    private SmartRefreshLayout refresh;

    private final void getInviteList() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, String.valueOf(this.page));
        ajaxParams.put("page_size", String.valueOf(this.pageSize));
        YJYHttpUtils.get(this, NetworkRequestsURL.getInviteListUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.invite.InviteListActivity.getInviteList.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable error, int errorNo, @Nullable String strMsg) {
                super.onFailure(error, errorNo, strMsg);
                if (error != null) {
                    error.printStackTrace();
                }
                if (InviteListActivity.this.page == 1 || strMsg == null) {
                    return;
                }
                InviteListActivity.this.AlertToast(strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@Nullable String result) {
                super.onSuccess((AnonymousClass1) result);
                if (result != null) {
                    InviteListActivity inviteListActivity = InviteListActivity.this;
                    try {
                        InviteListBean inviteListBean = (InviteListBean) new Gson().fromJson(result, InviteListBean.class);
                        SmartRefreshLayout smartRefreshLayout = null;
                        if (!inviteListBean.isSuccess()) {
                            if (inviteListActivity.page != 1) {
                                inviteListActivity.AlertToast(inviteListBean.getMessage());
                                Unit unit = Unit.INSTANCE;
                                return;
                            }
                            SmartRefreshLayout smartRefreshLayout2 = inviteListActivity.refresh;
                            if (smartRefreshLayout2 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("refresh");
                            } else {
                                smartRefreshLayout = smartRefreshLayout2;
                            }
                            smartRefreshLayout.finishRefresh();
                            return;
                        }
                        TextView textView = inviteListActivity.mTvAllPerson;
                        if (textView == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mTvAllPerson");
                            textView = null;
                        }
                        textView.setText(inviteListBean.getData().getInvite_num());
                        TextView textView2 = inviteListActivity.mTvAllVipDay;
                        if (textView2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mTvAllVipDay");
                            textView2 = null;
                        }
                        textView2.setText(inviteListBean.getData().getInvite_reward());
                        List<InviteListBean.TimeLineBean> list = inviteListBean.getData().getTime_line();
                        if (inviteListActivity.page == 1) {
                            List<InviteListBean.TimeLineBean> list2 = list;
                            if (list2 == null || list2.isEmpty()) {
                                SmartRefreshLayout smartRefreshLayout3 = inviteListActivity.refresh;
                                if (smartRefreshLayout3 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("refresh");
                                    smartRefreshLayout3 = null;
                                }
                                smartRefreshLayout3.finishLoadMoreWithNoMoreData();
                                LinearLayout linearLayout = inviteListActivity.lyInviteFriends;
                                if (linearLayout == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("lyInviteFriends");
                                    linearLayout = null;
                                }
                                linearLayout.setVisibility(0);
                                RecyclerView recyclerView = inviteListActivity.recyclerView;
                                if (recyclerView == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
                                    recyclerView = null;
                                }
                                recyclerView.setVisibility(8);
                            } else {
                                LinearLayout linearLayout2 = inviteListActivity.lyInviteFriends;
                                if (linearLayout2 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("lyInviteFriends");
                                    linearLayout2 = null;
                                }
                                linearLayout2.setVisibility(8);
                                RecyclerView recyclerView2 = inviteListActivity.recyclerView;
                                if (recyclerView2 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
                                    recyclerView2 = null;
                                }
                                recyclerView2.setVisibility(0);
                                BaseQuickAdapter baseQuickAdapter = inviteListActivity.adapter;
                                if (baseQuickAdapter == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("adapter");
                                    baseQuickAdapter = null;
                                }
                                baseQuickAdapter.setList(list);
                                if (list.size() < inviteListActivity.pageSize) {
                                    SmartRefreshLayout smartRefreshLayout4 = inviteListActivity.refresh;
                                    if (smartRefreshLayout4 == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException("refresh");
                                        smartRefreshLayout4 = null;
                                    }
                                    smartRefreshLayout4.finishLoadMoreWithNoMoreData();
                                }
                            }
                            SmartRefreshLayout smartRefreshLayout5 = inviteListActivity.refresh;
                            if (smartRefreshLayout5 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("refresh");
                            } else {
                                smartRefreshLayout = smartRefreshLayout5;
                            }
                            smartRefreshLayout.finishRefresh();
                        } else {
                            List<InviteListBean.TimeLineBean> list3 = list;
                            if (list3 == null || list3.isEmpty()) {
                                SmartRefreshLayout smartRefreshLayout6 = inviteListActivity.refresh;
                                if (smartRefreshLayout6 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("refresh");
                                } else {
                                    smartRefreshLayout = smartRefreshLayout6;
                                }
                                smartRefreshLayout.finishLoadMoreWithNoMoreData();
                            } else {
                                BaseQuickAdapter baseQuickAdapter2 = inviteListActivity.adapter;
                                if (baseQuickAdapter2 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("adapter");
                                    baseQuickAdapter2 = null;
                                }
                                Intrinsics.checkNotNullExpressionValue(list, "list");
                                baseQuickAdapter2.addData((Collection) list);
                                if (list.size() < inviteListActivity.pageSize) {
                                    SmartRefreshLayout smartRefreshLayout7 = inviteListActivity.refresh;
                                    if (smartRefreshLayout7 == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException("refresh");
                                    } else {
                                        smartRefreshLayout = smartRefreshLayout7;
                                    }
                                    smartRefreshLayout.finishLoadMoreWithNoMoreData();
                                } else {
                                    SmartRefreshLayout smartRefreshLayout8 = inviteListActivity.refresh;
                                    if (smartRefreshLayout8 == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException("refresh");
                                    } else {
                                        smartRefreshLayout = smartRefreshLayout8;
                                    }
                                    smartRefreshLayout.finishLoadMore();
                                }
                            }
                        }
                        inviteListActivity.page++;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        Unit unit2 = Unit.INSTANCE;
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void init$lambda$0(InviteListActivity this$0, BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(baseQuickAdapter, "<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter(view, "<anonymous parameter 1>");
        BaseQuickAdapter<InviteListBean.TimeLineBean, BaseViewHolder> baseQuickAdapter2 = this$0.adapter;
        if (baseQuickAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            baseQuickAdapter2 = null;
        }
        InviteListBean.TimeLineBean item = baseQuickAdapter2.getItem(i2);
        Intent intent = new Intent(this$0, (Class<?>) UserCommentInfoActivity.class);
        intent.putExtra("user_id", item.getUser_id());
        intent.putExtra("jiav", "");
        intent.addFlags(67108864);
        this$0.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$1(InviteListActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$2(InviteListActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        View viewFindViewById = findViewById(R.id.refresh);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.refresh)");
        this.refresh = (SmartRefreshLayout) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.recycler_view);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.recycler_view)");
        this.recyclerView = (RecyclerView) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.nav_tabbar);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.nav_tabbar)");
        this.mNavTabbar = (RelativeLayout) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.btn_invite_friends);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.btn_invite_friends)");
        this.btnInviteFriends = (TextView) viewFindViewById4;
        View viewFindViewById5 = findViewById(R.id.ly_invite_friends);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.ly_invite_friends)");
        this.lyInviteFriends = (LinearLayout) viewFindViewById5;
        View viewFindViewById6 = findViewById(R.id.iv_actionbar_back);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(R.id.iv_actionbar_back)");
        this.mNavBack = (ImageView) viewFindViewById6;
        View viewFindViewById7 = findViewById(R.id.tv_all_person);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById(R.id.tv_all_person)");
        this.mTvAllPerson = (TextView) viewFindViewById7;
        View viewFindViewById8 = findViewById(R.id.tv_all_vip_day);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "findViewById(R.id.tv_all_vip_day)");
        this.mTvAllVipDay = (TextView) viewFindViewById8;
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(this);
        RelativeLayout relativeLayout = this.mNavTabbar;
        SmartRefreshLayout smartRefreshLayout = null;
        if (relativeLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mNavTabbar");
            relativeLayout = null;
        }
        ViewGroup.LayoutParams layoutParams = relativeLayout.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.RelativeLayout.LayoutParams");
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) layoutParams;
        layoutParams2.topMargin = statusBarHeight;
        RelativeLayout relativeLayout2 = this.mNavTabbar;
        if (relativeLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mNavTabbar");
            relativeLayout2 = null;
        }
        relativeLayout2.setLayoutParams(layoutParams2);
        RecyclerView recyclerView = this.recyclerView;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
            recyclerView = null;
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        BaseQuickAdapter<InviteListBean.TimeLineBean, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<InviteListBean.TimeLineBean, BaseViewHolder>() { // from class: com.psychiatrygarden.activity.invite.InviteListActivity.init.1
            {
                super(R.layout.item_invite_person, null, 2, null);
            }

            /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            @SuppressLint({"SimpleDateFormat"})
            public void convert(@NotNull BaseViewHolder holder, @NotNull InviteListBean.TimeLineBean item) {
                Intrinsics.checkNotNullParameter(holder, "holder");
                Intrinsics.checkNotNullParameter(item, "item");
                String nickname = item.getNickname();
                if (nickname == null) {
                    nickname = "暂无昵称";
                }
                holder.setText(R.id.tv_name, nickname);
                TextView textView = (TextView) holder.getView(R.id.tv_status);
                holder.setText(R.id.tv_vip_day, '+' + item.getReward_total());
                holder.setText(R.id.tv_time, item.getCreate_at());
                ImageView imageView = (ImageView) holder.getView(R.id.img_avatar);
                Glide.with((FragmentActivity) InviteListActivity.this).load((Object) GlideUtils.generateUrl(item.getAvatar())).placeholder(new ColorDrawable(ContextCompat.getColor(imageView.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(imageView);
                textView.setText(item.getAuth_label());
                String authentication_state = item.getAuthentication_state();
                if (authentication_state != null) {
                    int iHashCode = authentication_state.hashCode();
                    if (iHashCode == 1567) {
                        if (authentication_state.equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SHARE_TO_QQ)) {
                            if (SkinManager.getCurrentSkinTheme(InviteListActivity.this.mContext) == 1) {
                                textView.setTextColor(Color.parseColor("#C49231"));
                            } else {
                                textView.setTextColor(Color.parseColor("#F5B53D"));
                            }
                            textView.setBackgroundResource(R.drawable.invite_status_no_bg_shape);
                        }
                        return;
                    }
                    switch (iHashCode) {
                        case 48:
                            if (authentication_state.equals("0")) {
                                if (SkinManager.getCurrentSkinTheme(InviteListActivity.this.mContext) == 1) {
                                    textView.setTextColor(Color.parseColor("#244D8D"));
                                } else {
                                    textView.setTextColor(Color.parseColor("#75A2F2"));
                                }
                                textView.setBackgroundResource(R.drawable.invite_status_wait_bg_shape);
                                break;
                            }
                            break;
                        case 49:
                            if (authentication_state.equals("1")) {
                                if (SkinManager.getCurrentSkinTheme(InviteListActivity.this.mContext) == 1) {
                                    textView.setTextColor(Color.parseColor("#6AA064"));
                                } else {
                                    textView.setTextColor(Color.parseColor("#96C84E"));
                                }
                                textView.setBackgroundResource(R.drawable.invite_status_no_pass_bg_shape);
                                break;
                            }
                            break;
                        case 50:
                            if (authentication_state.equals("2")) {
                                if (SkinManager.getCurrentSkinTheme(InviteListActivity.this.mContext) == 1) {
                                    textView.setTextColor(Color.parseColor("#B2575C"));
                                } else {
                                    textView.setTextColor(Color.parseColor("#CE6151"));
                                }
                                textView.setBackgroundResource(R.drawable.invite_status_pass_bg_shape);
                                break;
                            }
                            break;
                    }
                }
            }
        };
        this.adapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.invite.h
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                InviteListActivity.init$lambda$0(this.f12534c, baseQuickAdapter2, view, i2);
            }
        });
        RecyclerView recyclerView2 = this.recyclerView;
        if (recyclerView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
            recyclerView2 = null;
        }
        BaseQuickAdapter<InviteListBean.TimeLineBean, BaseViewHolder> baseQuickAdapter2 = this.adapter;
        if (baseQuickAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            baseQuickAdapter2 = null;
        }
        recyclerView2.setAdapter(baseQuickAdapter2);
        SmartRefreshLayout smartRefreshLayout2 = this.refresh;
        if (smartRefreshLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("refresh");
            smartRefreshLayout2 = null;
        }
        smartRefreshLayout2.setOnRefreshLoadMoreListener(this);
        SmartRefreshLayout smartRefreshLayout3 = this.refresh;
        if (smartRefreshLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("refresh");
        } else {
            smartRefreshLayout = smartRefreshLayout3;
        }
        smartRefreshLayout.autoRefresh();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle savedInstanceState) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
        setStatusBarTranslucent();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
    public void onLoadMore(@NotNull RefreshLayout refreshLayout) {
        Intrinsics.checkNotNullParameter(refreshLayout, "refreshLayout");
        getInviteList();
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
    public void onRefresh(@NotNull RefreshLayout refreshLayout) {
        Intrinsics.checkNotNullParameter(refreshLayout, "refreshLayout");
        this.page = 1;
        getInviteList();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_invite_list);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        ImageView imageView = this.mNavBack;
        TextView textView = null;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mNavBack");
            imageView = null;
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.invite.f
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                InviteListActivity.setListenerForWidget$lambda$1(this.f12532c, view);
            }
        });
        TextView textView2 = this.btnInviteFriends;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnInviteFriends");
        } else {
            textView = textView2;
        }
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.invite.g
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                InviteListActivity.setListenerForWidget$lambda$2(this.f12533c, view);
            }
        });
    }

    public final void setStatusBarTranslucent() throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        StatusBarUtil.setStatusBarTranslucent(this, false);
    }
}
