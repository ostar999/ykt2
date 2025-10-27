package com.psychiatrygarden.fragmenthome;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.hutool.core.text.StrPool;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.HandoutsInfoActivity;
import com.psychiatrygarden.activity.LoginActivity;
import com.psychiatrygarden.activity.circleactivity.CircleInfoActivity;
import com.psychiatrygarden.activity.vip.MemberCenterActivity;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.CirclrListBean;
import com.psychiatrygarden.bean.EventBusMessage;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.event.OnRefreshEvent;
import com.psychiatrygarden.event.RefreshChannelData;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.utils.URLImageParser;
import com.psychiatrygarden.utils.VerticalImageSpan;
import com.psychiatrygarden.widget.CircleListBookView;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CircleListNewFragment extends BaseFragment {
    private View addFooterView;
    private int channel_id;
    private FloatingActionButton floatButton2;
    private boolean hasSetEmptyView;
    private boolean initHeader;
    private boolean isCanLoadNextPage;
    private RecyclerView listview;
    private CirclrListBean mCirclrListBean;
    private BaseQuickAdapter<CirclrListBean.DataBean, BaseViewHolder> mCommAdapter;
    private Context mContext;
    private RelativeLayout mLyLoading;
    private TextView mTvNoData;
    private List<CirclrListBean.DataBean> data = new ArrayList();
    private int page = 1;
    private String topic_id = "0";
    private int previousFirstVisibleItem = 0;
    private long previousEventTime = 0;
    private boolean isLoadMore = false;
    int scrollHeight = 0;
    private boolean isNoMoreData = false;
    private boolean isFirstLoad = false;

    /* renamed from: com.psychiatrygarden.fragmenthome.CircleListNewFragment$1, reason: invalid class name */
    public class AnonymousClass1 extends BaseQuickAdapter<CirclrListBean.DataBean, BaseViewHolder> {
        public AnonymousClass1(int layoutResId) {
            super(layoutResId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(BaseViewHolder baseViewHolder, View view) {
            if ("1".equals(((CirclrListBean.DataBean) CircleListNewFragment.this.data.get(baseViewHolder.getLayoutPosition())).getNo_access())) {
                CircleListNewFragment.this.startActivity(new Intent(CircleListNewFragment.this.getActivity(), (Class<?>) MemberCenterActivity.class));
                return;
            }
            Intent intent = new Intent(CircleListNewFragment.this.mContext, (Class<?>) CircleInfoActivity.class);
            intent.putExtra("channel_id", String.valueOf(CircleListNewFragment.this.channel_id));
            intent.putExtra("article_id", ((CirclrListBean.DataBean) CircleListNewFragment.this.data.get(baseViewHolder.getLayoutPosition())).getId());
            intent.putExtra("module_type", Constants.VIA_REPORT_TYPE_SET_AVATAR);
            intent.putExtra("commentCount", ((CirclrListBean.DataBean) CircleListNewFragment.this.data.get(baseViewHolder.getLayoutPosition())).getComment_count());
            CircleListNewFragment.this.startActivity(intent);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$1(CirclrListBean.DataBean dataBean, View view) {
            if (!UserConfig.isLogin()) {
                getContext().startActivity(new Intent(getContext(), (Class<?>) LoginActivity.class));
            } else {
                if ("1".equals(dataBean.getNo_access())) {
                    CircleListNewFragment.this.startActivity(new Intent(CircleListNewFragment.this.getActivity(), (Class<?>) MemberCenterActivity.class));
                    return;
                }
                Intent intent = new Intent(CircleListNewFragment.this.mContext, (Class<?>) CircleInfoActivity.class);
                intent.putExtra("channel_id", String.valueOf(CircleListNewFragment.this.channel_id));
                intent.putExtra("article_id", dataBean.getId());
                intent.putExtra("module_type", Constants.VIA_REPORT_TYPE_SET_AVATAR);
                intent.putExtra("commentCount", dataBean.getComment_count());
                CircleListNewFragment.this.startActivity(intent);
            }
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NonNull final BaseViewHolder vHolder, final CirclrListBean.DataBean dataBean) {
            LinearLayout linearLayout;
            int i2;
            int i3;
            TextView textView = (TextView) vHolder.getView(R.id.title);
            vHolder.setText(R.id.time, dataBean.getComment_time()).setText(R.id.commnum, dataBean.getComment_count() + " 评论");
            StringBuffer stringBuffer = new StringBuffer();
            if (dataBean.getIcon_img() == null || dataBean.getIcon_img().size() <= 0) {
                textView.setText(dataBean.getTitle());
            } else {
                if (!(dataBean.getTitle() + vHolder.getAdapterPosition()).equals(textView.getTag())) {
                    for (int i4 = 0; i4 < dataBean.getIcon_img().size(); i4++) {
                        stringBuffer.append(StrPool.BRACKET_START);
                        stringBuffer.append(dataBean.getIcon_img().get(i4));
                        stringBuffer.append(StrPool.BRACKET_END);
                    }
                    stringBuffer.append(dataBean.getTitle());
                    CircleListNewFragment.this.getImageData(stringBuffer, textView);
                }
            }
            textView.setTag(dataBean.getTitle() + vHolder.getAdapterPosition());
            LinearLayout linearLayout2 = (LinearLayout) vHolder.getView(R.id.ly_push_circle);
            RelativeLayout relativeLayout = (RelativeLayout) vHolder.getView(R.id.view_one_ebook);
            HorizontalScrollView horizontalScrollView = (HorizontalScrollView) vHolder.getView(R.id.ly_book_view);
            LinearLayout linearLayout3 = (LinearLayout) vHolder.getView(R.id.ly_add_book_view);
            TextView textView2 = (TextView) vHolder.getView(R.id.tv_circle_name);
            TextView textView3 = (TextView) vHolder.getView(R.id.tv_circle_num);
            if (dataBean.getPush_data() == null || dataBean.getPush_data().isEmpty()) {
                linearLayout = linearLayout3;
                i2 = 8;
                linearLayout2.setVisibility(8);
            } else {
                linearLayout2.setVisibility(0);
                StringBuffer stringBuffer2 = new StringBuffer();
                if (dataBean.getPush_data().size() > 1) {
                    textView3.setVisibility(0);
                    textView3.setText(dataBean.getPush_data().size() + "条推贴");
                } else {
                    textView3.setVisibility(8);
                }
                if (dataBean.getPush_data().get(0).getIcon_img() == null || dataBean.getPush_data().get(0).getIcon_img().size() <= 0) {
                    linearLayout = linearLayout3;
                    i3 = 0;
                    textView2.setText(dataBean.getPush_data().get(0).getTitle().replaceAll("<font.*?>", "").replaceAll("</font>", ""));
                } else {
                    String strReplaceAll = dataBean.getPush_data().get(0).getTitle().replaceAll("<font.*?>", "").replaceAll("</font>", "");
                    if ((strReplaceAll + vHolder.getLayoutPosition()).equals(textView2.getTag())) {
                        linearLayout = linearLayout3;
                    } else {
                        int i5 = 0;
                        while (true) {
                            linearLayout = linearLayout3;
                            if (i5 >= dataBean.getPush_data().get(0).getIcon_img().size()) {
                                break;
                            }
                            stringBuffer2.append(StrPool.BRACKET_START);
                            stringBuffer2.append(dataBean.getPush_data().get(0).getIcon_img().get(i5));
                            stringBuffer2.append(StrPool.BRACKET_END);
                            i5++;
                            linearLayout3 = linearLayout;
                        }
                        stringBuffer2.append(strReplaceAll);
                        CircleListNewFragment.this.getImageData(stringBuffer2, textView2);
                    }
                    i3 = 0;
                }
                textView2.setTag(dataBean.getPush_data().get(i3).getTitle().replaceAll("<font.*?>", "").replaceAll("</font>", "") + vHolder.getLayoutPosition());
                linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.z
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f16151c.lambda$convert$0(vHolder, view);
                    }
                });
                i2 = 8;
            }
            if (dataBean.getEbook_data() == null || dataBean.getEbook_data().isEmpty()) {
                int i6 = i2;
                relativeLayout.setVisibility(i6);
                horizontalScrollView.setVisibility(i6);
                return;
            }
            if (dataBean.getEbook_data().size() > 1) {
                relativeLayout.setVisibility(i2);
                horizontalScrollView.setVisibility(0);
                linearLayout.removeAllViews();
                for (int i7 = 0; i7 < dataBean.getEbook_data().size(); i7++) {
                    CircleListBookView circleListBookView = new CircleListBookView(CircleListNewFragment.this.mContext);
                    circleListBookView.setData(dataBean.getEbook_data().get(i7), String.valueOf(CircleListNewFragment.this.channel_id), dataBean.getId(), dataBean.getComment_count(), dataBean.getNo_access());
                    linearLayout.addView(circleListBookView);
                }
                return;
            }
            relativeLayout.setVisibility(0);
            horizontalScrollView.setVisibility(8);
            TextView textView4 = (TextView) vHolder.getView(R.id.tv_grade);
            TextView textView5 = (TextView) vHolder.getView(R.id.tv_label);
            vHolder.setText(R.id.tv_title, dataBean.getEbook_data().get(0).getTitle());
            vHolder.setText(R.id.tv_author, dataBean.getEbook_data().get(0).getAuthor());
            if (TextUtils.isEmpty(dataBean.getEbook_data().get(0).getRate()) || dataBean.getEbook_data().get(0).getRate().equals("0")) {
                textView4.setVisibility(8);
                textView5.setText("暂无评分");
                textView5.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(getContext()) == 1 ? "#606A8A" : "#7B7E83"));
            } else {
                textView4.setVisibility(0);
                textView4.setText(dataBean.getEbook_data().get(0).getRate());
                textView5.setText("分");
                textView5.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(getContext()) == 1 ? "#C49231" : "#FFC100"));
            }
            GlideUtils.loadImage(getContext(), dataBean.getEbook_data().get(0).getThumbnail(), (ImageView) vHolder.getView(R.id.img_pic));
            relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.a0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f15426c.lambda$convert$1(dataBean, view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishLoadMore(boolean success) {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof QuestionDatabaseFragmentNew) {
            ((QuestionDatabaseFragmentNew) parentFragment).finishLoadMore(success, this.channel_id);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishLoadMoreNoMoreData() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof QuestionDatabaseFragmentNew) {
            ((QuestionDatabaseFragmentNew) parentFragment).finishLoadMoreNoMoreData(this.channel_id);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishRefresh(boolean success) {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof QuestionDatabaseFragmentNew) {
            ((QuestionDatabaseFragmentNew) parentFragment).finishRefresh(success, this.channel_id);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0() {
        this.listview.scrollToPosition(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(View view) {
        this.listview.post(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.y
            @Override // java.lang.Runnable
            public final void run() {
                this.f16126c.lambda$initViews$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        try {
            if (isLogin()) {
                if ("1".equals(this.data.get(i2).getNo_access())) {
                    startActivity(new Intent(getActivity(), (Class<?>) MemberCenterActivity.class));
                    return;
                }
                if (!this.data.get(i2).getId().equals(this.data.get(i2).getExp_id())) {
                    Intent intent = new Intent(this.mContext, (Class<?>) CircleInfoActivity.class);
                    intent.putExtra("channel_id", String.valueOf(this.channel_id));
                    intent.putExtra("article_id", this.data.get(i2).getId());
                    intent.putExtra("module_type", Constants.VIA_REPORT_TYPE_SET_AVATAR);
                    intent.putExtra("commentCount", this.data.get(i2).getComment_count());
                    startActivity(intent);
                    return;
                }
                Intent intent2 = new Intent(getActivity(), (Class<?>) HandoutsInfoActivity.class);
                intent2.putExtra("cat_id", this.data.get(i2).getCid());
                intent2.putExtra("article", this.data.get(i2).getId());
                intent2.putExtra("json_path", this.data.get(i2).getJson_path());
                intent2.putExtra("html_path", this.data.get(i2).getHtml_path());
                intent2.putExtra("h5_path", this.data.get(i2).getH5_path());
                intent2.putExtra("is_rich_text", this.data.get(i2).getIs_rich_text());
                intent2.putExtra("index", 0);
                intent2.putExtra("app_id", this.data.get(i2).getApp_id());
                startActivity(intent2);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setEmptyView$3(View view) {
        this.page = 1;
        getData();
        EventBus.getDefault().post("refreshForumTopData");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setEmptyView(String type, int viewType) {
        int i2;
        String str;
        if (this.hasSetEmptyView) {
            return;
        }
        this.hasSetEmptyView = true;
        i2 = 0;
        this.mCommAdapter.setList(new ArrayList(0));
        this.mCommAdapter.setEmptyView(R.layout.layout_empty_view);
        type.hashCode();
        switch (type) {
            case "-3":
                i2 = SkinManager.getCurrentSkinType(this.mContext) == 1 ? R.drawable.ic_empty_data_circle_night : R.drawable.ic_empty_data_circle_day;
                str = "暂无发帖";
                break;
            case "-4":
                i2 = SkinManager.getCurrentSkinType(this.mContext) == 1 ? R.drawable.ic_empty_data_collection_night : R.drawable.ic_empty_data_collection_day;
                str = "暂无收藏";
                break;
            case "-5":
                i2 = SkinManager.getCurrentSkinType(this.mContext) == 1 ? R.drawable.ic_empty_data_comment_night : R.drawable.ic_empty_data_comment_day;
                str = "暂无评论";
                break;
            default:
                str = "";
                break;
        }
        CustomEmptyView customEmptyView = new CustomEmptyView(this.mContext, i2, str);
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.x
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16105c.lambda$setEmptyView$3(view);
            }
        });
        if (viewType == 1) {
            customEmptyView.setLoadFileResUi(this.mContext);
            customEmptyView.setIsShowReloadBtn(true, "点击刷新页面");
        }
        customEmptyView.showEmptyView();
        this.mCommAdapter.setEmptyView(customEmptyView);
    }

    public void getData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", String.valueOf(this.channel_id));
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, String.valueOf(this.page));
        ajaxParams.put("limit", "20");
        if (this.page <= 1) {
            this.mCommAdapter.removeFooterView(this.addFooterView);
            this.mLyLoading.setVisibility(0);
            this.mTvNoData.setVisibility(8);
        } else if (this.mCommAdapter.getFooterLayout() == null) {
            this.mCommAdapter.addFooterView(this.addFooterView);
        }
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.articleList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.CircleListNewFragment.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (CircleListNewFragment.this.isLoadMore) {
                    CircleListNewFragment.this.finishLoadMore(false);
                } else {
                    CircleListNewFragment.this.finishRefresh(false);
                }
                CircleListNewFragment.this.mCommAdapter.removeFooterView(CircleListNewFragment.this.addFooterView);
                CircleListNewFragment circleListNewFragment = CircleListNewFragment.this;
                circleListNewFragment.setEmptyView(String.valueOf(circleListNewFragment.channel_id), 1);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    CircleListNewFragment.this.mCirclrListBean = (CirclrListBean) new Gson().fromJson(s2, CirclrListBean.class);
                    if (CircleListNewFragment.this.isLoadMore) {
                        CircleListNewFragment.this.finishLoadMore(true);
                    } else {
                        CircleListNewFragment.this.finishRefresh(true);
                    }
                    if (!CircleListNewFragment.this.mCirclrListBean.getCode().equals("200") || CircleListNewFragment.this.mCirclrListBean.getData() == null) {
                        CircleListNewFragment.this.finishRefresh(false);
                        CircleListNewFragment.this.finishLoadMoreNoMoreData();
                        CircleListNewFragment circleListNewFragment = CircleListNewFragment.this;
                        circleListNewFragment.setEmptyView(String.valueOf(circleListNewFragment.channel_id), 0);
                        String strOptString = new JSONObject(s2).optString("message", null);
                        if (!TextUtils.isEmpty(strOptString)) {
                            ToastUtil.shortToast(CircleListNewFragment.this.mContext, strOptString);
                        }
                    } else {
                        int size = CircleListNewFragment.this.mCommAdapter.getData().size();
                        if (CircleListNewFragment.this.page > 1 && CircleListNewFragment.this.mCommAdapter.getData().size() >= 20) {
                            List listSubList = CircleListNewFragment.this.mCommAdapter.getData().subList(size - 20, size);
                            if (CircleListNewFragment.this.mCirclrListBean.getData().size() > 0) {
                                ArrayList arrayList = new ArrayList();
                                for (int i2 = 0; i2 < listSubList.size(); i2++) {
                                    arrayList.add(((CirclrListBean.DataBean) listSubList.get(i2)).getId());
                                }
                                ArrayList arrayList2 = new ArrayList();
                                arrayList2.addAll(CircleListNewFragment.this.mCirclrListBean.getData());
                                for (int i3 = 0; i3 < arrayList2.size(); i3++) {
                                    String id = ((CirclrListBean.DataBean) arrayList2.get(i3)).getId();
                                    if (arrayList.contains(id)) {
                                        CircleListNewFragment.this.mCirclrListBean.getData().remove(CircleListNewFragment.this.mCirclrListBean.getData().get(i3));
                                        LogUtils.e("circleId", "id相等====>" + id + ";size===>" + CircleListNewFragment.this.mCirclrListBean.getData().size() + ";newList_size=" + arrayList2.size());
                                    }
                                }
                            }
                        }
                        if (CircleListNewFragment.this.page == 1) {
                            CircleListNewFragment circleListNewFragment2 = CircleListNewFragment.this;
                            circleListNewFragment2.data = circleListNewFragment2.mCirclrListBean.getData();
                            CircleListNewFragment.this.mCommAdapter.setList(CircleListNewFragment.this.data);
                            if (CircleListNewFragment.this.data == null || CircleListNewFragment.this.data.isEmpty()) {
                                if (CircleListNewFragment.this.isLoadMore) {
                                    CircleListNewFragment.this.finishLoadMoreNoMoreData();
                                    CircleListNewFragment.this.mLyLoading.setVisibility(8);
                                    CircleListNewFragment.this.mTvNoData.setVisibility(0);
                                    CircleListNewFragment.this.addFooterView.setVisibility(8);
                                }
                                CircleListNewFragment circleListNewFragment3 = CircleListNewFragment.this;
                                circleListNewFragment3.setEmptyView(String.valueOf(circleListNewFragment3.channel_id), 0);
                            }
                        } else {
                            List<CirclrListBean.DataBean> data = CircleListNewFragment.this.mCirclrListBean.getData();
                            if (data == null || data.isEmpty()) {
                                CircleListNewFragment.this.finishLoadMoreNoMoreData();
                                CircleListNewFragment.this.isNoMoreData = true;
                                CircleListNewFragment.this.mLyLoading.setVisibility(8);
                                CircleListNewFragment.this.mTvNoData.setVisibility(0);
                            } else {
                                CircleListNewFragment.this.data.addAll(data);
                                CircleListNewFragment.this.mCommAdapter.addData((Collection) data);
                            }
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    CircleListNewFragment.this.mCommAdapter.removeFooterView(CircleListNewFragment.this.addFooterView);
                    if (CircleListNewFragment.this.isLoadMore) {
                        CircleListNewFragment.this.finishLoadMore(false);
                    } else {
                        CircleListNewFragment.this.finishRefresh(false);
                    }
                    CircleListNewFragment circleListNewFragment4 = CircleListNewFragment.this;
                    circleListNewFragment4.setEmptyView(String.valueOf(circleListNewFragment4.channel_id), 1);
                }
                CircleListNewFragment.this.isCanLoadNextPage = false;
            }
        });
    }

    public void getImageData(StringBuffer stringBuffer, TextView mTextView) {
        try {
            float textSize = mTextView.getPaint().getTextSize();
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(stringBuffer.toString());
            Matcher matcher = Pattern.compile("\\[[^\\]]+\\]").matcher(stringBuffer.toString());
            while (matcher.find()) {
                String strGroup = matcher.group();
                if (strGroup.contains("http")) {
                    spannableStringBuilder.setSpan(new VerticalImageSpan(new URLImageParser(mTextView, this.mContext, (int) textSize).getDrawable(strGroup.substring(1, strGroup.length() - 1))), matcher.start(), matcher.end(), 33);
                }
            }
            mTextView.setText(spannableStringBuilder);
        } catch (Exception e2) {
            e2.printStackTrace();
            mTextView.setText(stringBuffer);
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.activity_circle_list;
    }

    public void getScrollView(int dy) {
        LogUtils.e("is_first_load", "dy====>" + dy);
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.listview.getLayoutManager();
        int iFindFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        int childCount = linearLayoutManager.getChildCount();
        this.scrollHeight += dy;
        if (dy < 0) {
            if (childCount <= 0 || iFindFirstVisibleItemPosition <= 7) {
                this.floatButton2.hide();
                return;
            } else {
                if (this.previousFirstVisibleItem != iFindFirstVisibleItemPosition) {
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    this.previousFirstVisibleItem = iFindFirstVisibleItemPosition;
                    this.previousEventTime = jCurrentTimeMillis;
                    this.floatButton2.show();
                    return;
                }
                return;
            }
        }
        this.floatButton2.hide();
        if (dy > 0) {
            this.isFirstLoad = true;
            if (this.mCommAdapter.getItemCount() - iFindFirstVisibleItemPosition > 10 || this.isCanLoadNextPage || this.isNoMoreData) {
                return;
            }
            this.isCanLoadNextPage = true;
            this.isLoadMore = true;
            this.page++;
            getData();
            LogUtils.e("load_next", "加载下一页数据:" + this.page);
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        RecyclerView recyclerView = (RecyclerView) holder.get(R.id.listview);
        this.listview = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.mCommAdapter = new AnonymousClass1(R.layout.item_circle);
        View viewInflate = getLayoutInflater().inflate(R.layout.activity_hideview, (ViewGroup) null);
        this.addFooterView = viewInflate;
        this.mTvNoData = (TextView) viewInflate.findViewById(R.id.tv_no_more_data);
        this.mLyLoading = (RelativeLayout) this.addFooterView.findViewById(R.id.hide_sub_floor_content);
        this.listview.setAdapter(this.mCommAdapter);
        FloatingActionButton floatingActionButton = (FloatingActionButton) holder.get(R.id.floatButton2);
        this.floatButton2 = floatingActionButton;
        floatingActionButton.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.v
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16056c.lambda$initViews$1(view);
            }
        });
        this.listview.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.psychiatrygarden.fragmenthome.CircleListNewFragment.2
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView2, int newState) {
                super.onScrollStateChanged(recyclerView2, newState);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(@NonNull RecyclerView recyclerView2, int dx, int dy) {
                super.onScrolled(recyclerView2, dx, dy);
                CircleListNewFragment.this.getScrollView(dy);
            }
        });
        this.mCommAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.fragmenthome.w
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f16083c.lambda$initViews$2(baseQuickAdapter, view, i2);
            }
        });
        getData();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, androidx.fragment.app.Fragment
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        Bundle arguments = getArguments();
        this.channel_id = arguments != null ? arguments.getInt("id", 0) : 0;
    }

    public void onEventMainThread(OnRefreshEvent event) {
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(final String str) {
        if (str.equals("isRefaulfCircle")) {
            this.listview.smoothScrollToPosition(0);
            this.page = 1;
            getData();
        } else if (str.equals("ebook_logout")) {
            UserConfig.getInstance().logOut();
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(EventBusMessage<String> eventBusMessage) {
        if (eventBusMessage.getKey().equals("returnjump")) {
            this.topic_id = eventBusMessage.getValueObj() + "";
            this.page = 1;
            getData();
        }
    }

    public void onEventMainThread(RefreshChannelData data) {
        if (String.valueOf(this.channel_id).equals(data.getChannelId())) {
            if (data.isRefresh()) {
                this.isLoadMore = false;
                this.isNoMoreData = false;
                this.page = 1;
            } else {
                this.isLoadMore = true;
                this.page++;
            }
            getData();
        }
    }
}
