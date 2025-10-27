package com.psychiatrygarden.fragmenthome;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import cn.hutool.core.text.StrPool;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.psychiatrygarden.activity.HandoutsInfoActivity;
import com.psychiatrygarden.adapter.HandoutSearchAdapter;
import com.psychiatrygarden.adapter.ReplySearchContentAdapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.CommentSearchBean;
import com.psychiatrygarden.bean.HandoutNewBean;
import com.psychiatrygarden.bean.HandoutReplySearchBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.SearchHandoutContentChangeEvent;
import com.psychiatrygarden.event.SearchHandoutEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.ClearEditText;
import com.psychiatrygarden.widget.SimpleTextWatcher;
import com.psychiatrygarden.widget.StackLayout;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import okhttp3.HttpUrl;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class HandoutSearchFragment extends BaseFragment {
    public static final int SEARCH_BY_HANDOUT = 0;
    public static final int SEARCH_BY_REPLY = 1;
    private View addFooterView;
    private boolean handleSearch;
    public HandoutSearchAdapter handoutadapter;
    private boolean hasEmptyView;
    private boolean hasInit;
    private boolean isVisible2User;
    private ListView lvSearch;
    private ReplySearchContentAdapter mAdapter;
    private ClearEditText mEditText;
    private View mEmptyView;
    private int page;
    private StackLayout sl_search_history;
    private final List<HandoutNewBean.DataBean.TopBean> mSearhResults = new ArrayList();
    private List<String> historyList = new ArrayList();
    private int mType = 0;

    /* renamed from: com.psychiatrygarden.fragmenthome.HandoutSearchFragment$1, reason: invalid class name */
    public class AnonymousClass1 implements AbsListView.OnScrollListener {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onScrollStateChanged$0() {
            if (HandoutSearchFragment.this.lvSearch.getFooterViewsCount() == 0) {
                HandoutSearchFragment.this.lvSearch.addFooterView(HandoutSearchFragment.this.addFooterView);
            }
            HandoutSearchFragment.this.addFooterView.setVisibility(0);
            HandoutSearchFragment.access$208(HandoutSearchFragment.this);
            HandoutSearchFragment.this.getHandoutDataListNew();
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == 0 && view.getLastVisiblePosition() == view.getCount() - 1) {
                view.postDelayed(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.c6
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f15500c.lambda$onScrollStateChanged$0();
                    }
                }, 200L);
            }
        }
    }

    public static /* synthetic */ int access$208(HandoutSearchFragment handoutSearchFragment) {
        int i2 = handoutSearchFragment.page;
        handoutSearchFragment.page = i2 + 1;
        return i2;
    }

    public static /* synthetic */ int access$210(HandoutSearchFragment handoutSearchFragment) {
        int i2 = handoutSearchFragment.page;
        handoutSearchFragment.page = i2 - 1;
        return i2;
    }

    private void addHistoryContent(String content) {
        this.historyList.remove(content);
        if (this.historyList.indexOf(content) != -1) {
            return;
        }
        this.historyList.add(0, content);
        if (this.historyList.size() > 20) {
            this.historyList = this.historyList.subList(0, 20);
        }
        SharePreferencesUtils.writeStrConfig(CommonParameter.getHandoutSaveSearchHistoryData(), new Gson().toJson(this.historyList), getActivity());
        addStack(this.historyList);
    }

    private void addStack(List<String> hots) {
        this.sl_search_history.removeAllViews();
        for (int i2 = 0; i2 < hots.size(); i2++) {
            View viewInflate = LayoutInflater.from(getActivity()).inflate(R.layout.item_serach_history_tag, (ViewGroup) null);
            final TextView textView = (TextView) viewInflate.findViewById(R.id.tv_search_hot_tag);
            final String str = hots.get(i2);
            if (str.length() > 10) {
                textView.setText(String.format("%s...", str.substring(0, 10)));
            } else {
                textView.setText(str);
            }
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.w5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f16091c.lambda$addStack$5(str, textView, view);
                }
            });
            this.sl_search_history.addView(viewInflate);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getHandoutDataListNew() {
        String str = this.mType == 1 ? NetworkRequestsURL.handout_comment_search : NetworkRequestsURL.mexsearch;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, String.valueOf(this.page));
        ajaxParams.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, requireContext(), ""));
        if (this.mType == 0) {
            ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_KEYWORD, this.mEditText.getText().toString());
            ajaxParams.put(CrashHianalyticsData.TIME, "0");
        } else {
            ajaxParams.put("comment_type", "2");
            ajaxParams.put("module_type", "3");
            ajaxParams.put("search", this.mEditText.getText().toString());
        }
        YJYHttpUtils.post(requireContext(), str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.HandoutSearchFragment.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (HandoutSearchFragment.this.page > 1) {
                    HandoutSearchFragment.access$210(HandoutSearchFragment.this);
                }
                if (HandoutSearchFragment.this.mAdapter != null && !HandoutSearchFragment.this.hasEmptyView) {
                    HandoutSearchFragment.this.mAdapter.getLoadMoreModule().loadMoreFail();
                    HandoutSearchFragment.this.hasEmptyView = true;
                    HandoutSearchFragment.this.mAdapter.setEmptyView(R.layout.adapter_empty_txt_view);
                }
                if (strMsg != null) {
                    HandoutSearchFragment.this.AlertToast(strMsg);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass4) s2);
                HandoutSearchFragment.this.getViewHolder().get(R.id.ll_search).setVisibility(8);
                if (HandoutSearchFragment.this.mType != 0) {
                    try {
                        CommentSearchBean commentSearchBean = (CommentSearchBean) new Gson().fromJson(s2, CommentSearchBean.class);
                        JSONObject jSONObject = new JSONObject(s2);
                        if (commentSearchBean.getCode().equals("200")) {
                            List list = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<HandoutReplySearchBean>>() { // from class: com.psychiatrygarden.fragmenthome.HandoutSearchFragment.4.1
                            }.getType());
                            if (HandoutSearchFragment.this.page == 1) {
                                HandoutSearchFragment.this.mAdapter.setSearchContent(HandoutSearchFragment.this.mEditText.getText().toString());
                                HandoutSearchFragment.this.mAdapter.setList(list);
                                HandoutSearchFragment.this.mAdapter.getLoadMoreModule().checkDisableLoadMoreIfNotFullPage();
                            } else {
                                if (list != null && list.size() > 0) {
                                    HandoutSearchFragment.this.mAdapter.addData((Collection) list);
                                    HandoutSearchFragment.this.mAdapter.getLoadMoreModule().loadMoreComplete();
                                } else if (HandoutSearchFragment.this.page > 1) {
                                    HandoutSearchFragment.access$210(HandoutSearchFragment.this);
                                    HandoutSearchFragment.this.mAdapter.getLoadMoreModule().loadMoreEnd();
                                } else {
                                    HandoutSearchFragment.this.mAdapter.getData().clear();
                                    HandoutSearchFragment.this.mAdapter.notifyDataSetChanged();
                                }
                                HandoutSearchFragment.this.mAdapter.getLoadMoreModule().checkDisableLoadMoreIfNotFullPage();
                            }
                        } else {
                            if (HandoutSearchFragment.this.page > 1) {
                                HandoutSearchFragment.this.mAdapter.getLoadMoreModule().loadMoreEnd();
                                HandoutSearchFragment.access$210(HandoutSearchFragment.this);
                            }
                            HandoutSearchFragment.this.AlertToast(commentSearchBean.getMessage());
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        if (HandoutSearchFragment.this.page > 1) {
                            HandoutSearchFragment.access$210(HandoutSearchFragment.this);
                            HandoutSearchFragment.this.mAdapter.getLoadMoreModule().loadMoreFail();
                        }
                    }
                    if (HandoutSearchFragment.this.hasEmptyView || HandoutSearchFragment.this.mAdapter == null) {
                        return;
                    }
                    HandoutSearchFragment.this.hasEmptyView = true;
                    HandoutSearchFragment.this.mAdapter.setEmptyView(R.layout.adapter_empty_txt_view);
                    return;
                }
                HandoutNewBean handoutNewBean = (HandoutNewBean) new Gson().fromJson(s2, HandoutNewBean.class);
                if (!handoutNewBean.getCode().equals("200")) {
                    if (HandoutSearchFragment.this.page > 1) {
                        HandoutSearchFragment.access$210(HandoutSearchFragment.this);
                        return;
                    }
                    return;
                }
                if (HandoutSearchFragment.this.page != 1) {
                    HandoutSearchFragment.this.lvSearch.setVisibility(HandoutSearchFragment.this.mSearhResults.isEmpty() ? 8 : 0);
                    HandoutSearchFragment.this.mEmptyView.setVisibility(HandoutSearchFragment.this.mSearhResults.isEmpty() ? 0 : 8);
                    if (HandoutSearchFragment.this.mSearhResults.isEmpty()) {
                        HandoutSearchFragment.access$210(HandoutSearchFragment.this);
                        ToastUtil.shortToast(HandoutSearchFragment.this.requireContext(), "已是最后一条");
                    }
                    HandoutSearchFragment.this.lvSearch.removeFooterView(HandoutSearchFragment.this.addFooterView);
                    HandoutSearchFragment.this.addFooterView.setVisibility(8);
                    HandoutSearchFragment.this.lvSearch.invalidateViews();
                    List<HandoutNewBean.DataBean.TopBean> list2 = handoutNewBean.getData().getList();
                    if (list2 == null || list2.size() <= 0) {
                        return;
                    }
                    HandoutSearchFragment.this.mSearhResults.addAll(list2);
                    HandoutSearchFragment.this.handoutadapter.notifyDataSetChanged();
                    return;
                }
                HandoutSearchFragment.this.mSearhResults.clear();
                List<HandoutNewBean.DataBean.TopBean> top2 = handoutNewBean.getData().getTop();
                List<HandoutNewBean.DataBean.TopBean> list3 = handoutNewBean.getData().getList();
                if (top2 != null && top2.size() > 0) {
                    for (int i2 = 0; i2 < top2.size(); i2++) {
                        top2.get(i2).setZhiding("1");
                    }
                    HandoutSearchFragment.this.mSearhResults.addAll(top2);
                }
                if (list3 != null && list3.size() > 0) {
                    HandoutSearchFragment.this.mSearhResults.addAll(list3);
                }
                HandoutSearchFragment.this.handoutadapter = new HandoutSearchAdapter(HandoutSearchFragment.this.mSearhResults);
                HandoutSearchFragment handoutSearchFragment = HandoutSearchFragment.this;
                handoutSearchFragment.handoutadapter.setSearchContent(handoutSearchFragment.mEditText.getText().toString());
                HandoutSearchFragment.this.lvSearch.setAdapter((ListAdapter) HandoutSearchFragment.this.handoutadapter);
                HandoutSearchFragment.this.lvSearch.setVisibility(HandoutSearchFragment.this.mSearhResults.isEmpty() ? 8 : 0);
                HandoutSearchFragment.this.mEmptyView.setVisibility(HandoutSearchFragment.this.mSearhResults.isEmpty() ? 0 : 8);
            }
        });
    }

    public static HandoutSearchFragment getInstance(int type) {
        HandoutSearchFragment handoutSearchFragment = new HandoutSearchFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        handoutSearchFragment.setArguments(bundle);
        return handoutSearchFragment;
    }

    private void hideInputMethod() {
        if (getActivity() == null) {
            return;
        }
        View currentFocus = getActivity().getCurrentFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService("input_method");
        if (inputMethodManager == null || currentFocus == null) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initHistory() {
        List<String> list = (List) new Gson().fromJson(SharePreferencesUtils.readStrConfig(CommonParameter.getHandoutSaveSearchHistoryData(), getActivity(), HttpUrl.PATH_SEGMENT_ENCODE_SET_URI), new TypeToken<List<String>>() { // from class: com.psychiatrygarden.fragmenthome.HandoutSearchFragment.3
        }.getType());
        this.historyList = list;
        if (list.size() <= 0) {
            getViewHolder().get(R.id.ll_search).setVisibility(8);
        } else {
            getViewHolder().get(R.id.ll_search).setVisibility(0);
            addStack(this.historyList);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addStack$5(String str, TextView textView, View view) {
        getViewHolder().get(R.id.ll_search).setVisibility(8);
        hideInputMethod();
        this.mEditText.setText(str);
        this.mEditText.setSelection(str.length());
        EventBus.getDefault().post(new SearchHandoutContentChangeEvent(textView.getText().toString(), this.mType == 0 ? 1 : 0));
        EventBus.getDefault().post(new SearchHandoutEvent(textView.getText().toString(), this.mType == 0 ? 1 : 0));
        this.page = 1;
        getHandoutDataListNew();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(View view) {
        this.historyList.clear();
        SharePreferencesUtils.writeStrConfig(CommonParameter.getHandoutSaveSearchHistoryData(), new Gson().toJson(this.historyList), getActivity());
        getViewHolder().get(R.id.ll_search).setVisibility(8);
        addStack(this.historyList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(AdapterView adapterView, View view, int i2, long j2) {
        if (isLogin()) {
            Intent intent = new Intent(requireContext(), (Class<?>) HandoutsInfoActivity.class);
            intent.putExtra("cat_id", this.mSearhResults.get(i2).getCid());
            intent.putExtra("article", this.mSearhResults.get(i2).getId());
            intent.putExtra("json_path", this.mSearhResults.get(i2).getJson_path());
            intent.putExtra("html_path", this.mSearhResults.get(i2).getHtml_path());
            intent.putExtra("h5_path", this.mSearhResults.get(i2).getH5_path());
            intent.putExtra("is_rich_text", this.mSearhResults.get(i2).getIs_rich_text());
            intent.putExtra("index", this.mSearhResults.get(i2).getCid() + StrPool.UNDERLINE + this.mSearhResults.get(i2).getId());
            startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2() {
        this.page++;
        getHandoutDataListNew();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$3(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        HandoutReplySearchBean handoutReplySearchBean;
        String obj_id;
        if (!isLogin() || (obj_id = (handoutReplySearchBean = (HandoutReplySearchBean) this.mAdapter.getItem(i2)).getObj_id()) == null) {
            return;
        }
        Intent intent = new Intent(requireContext(), (Class<?>) HandoutsInfoActivity.class);
        intent.putExtra("article", obj_id);
        intent.putExtra("cat_id", handoutReplySearchBean.getCat_id());
        intent.putExtra("json_path", handoutReplySearchBean.getJson_path());
        intent.putExtra("html_path", handoutReplySearchBean.getHtml_path());
        intent.putExtra("h5_path", handoutReplySearchBean.getH5_path());
        intent.putExtra("is_rich_text", handoutReplySearchBean.getIs_rich_text());
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initViews$4(TextView textView, int i2, KeyEvent keyEvent) {
        if (i2 != 3) {
            return false;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) textView.getContext().getSystemService("input_method");
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(textView.getApplicationWindowToken(), 0);
        }
        if (!TextUtils.isEmpty(textView.getText())) {
            getViewHolder().get(R.id.ll_search).setVisibility(8);
            this.page = 1;
            getHandoutDataListNew();
            EventBus.getDefault().post(new SearchHandoutEvent(textView.getText().toString(), this.mType == 0 ? 1 : 0));
            addHistoryContent(textView.getText().toString());
        }
        return true;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_search_handout;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        if (getArguments() != null) {
            this.mType = getArguments().getInt("type", 0);
        }
        View view = holder.get(R.id.fl_handout);
        View view2 = holder.get(R.id.fl_reply);
        holder.get(R.id.tv_hot_search).setVisibility(8);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.get(R.id.ll_search).getLayoutParams();
        layoutParams.leftMargin = CommonUtil.dip2px(requireContext(), 20.0f);
        holder.get(R.id.ll_search).setLayoutParams(layoutParams);
        ImageView imageView = (ImageView) holder.get(R.id.iv_search_history_delete);
        imageView.setImageResource(SkinManager.getCurrentSkinType(getActivity()) == 1 ? R.mipmap.icon_delete_def_night : R.mipmap.icon_delete_def);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.x5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.f16111c.lambda$initViews$0(view3);
            }
        });
        this.sl_search_history = (StackLayout) holder.get(R.id.sl_search_history);
        view.setVisibility(this.mType == 0 ? 0 : 8);
        view2.setVisibility(this.mType == 0 ? 8 : 0);
        if (this.mType == 0) {
            this.addFooterView = getLayoutInflater().inflate(R.layout.activity_hideview, (ViewGroup) null);
            this.mEmptyView = holder.get(R.id.empty_view);
            ListView listView = (ListView) holder.get(R.id.lv_search);
            this.lvSearch = listView;
            listView.setOnScrollListener(new AnonymousClass1());
            this.lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.fragmenthome.y5
                @Override // android.widget.AdapterView.OnItemClickListener
                public final void onItemClick(AdapterView adapterView, View view3, int i2, long j2) {
                    this.f16138c.lambda$initViews$1(adapterView, view3, i2, j2);
                }
            });
        } else {
            this.mAdapter = new ReplySearchContentAdapter(R.layout.item_search_by_reply, new ArrayList());
            ((RecyclerView) holder.get(R.id.rv_reply)).setAdapter(this.mAdapter);
            this.mAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.fragmenthome.z5
                @Override // com.chad.library.adapter.base.listener.OnLoadMoreListener
                public final void onLoadMore() {
                    this.f16160c.lambda$initViews$2();
                }
            });
            this.mAdapter.setHeaderWithEmptyEnable(true);
            this.mAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.fragmenthome.a6
                @Override // com.chad.library.adapter.base.listener.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view3, int i2) {
                    this.f15436c.lambda$initViews$3(baseQuickAdapter, view3, i2);
                }
            });
        }
        ClearEditText clearEditText = (ClearEditText) holder.get(R.id.et_search);
        this.mEditText = clearEditText;
        clearEditText.addTextChangedListener(new SimpleTextWatcher() { // from class: com.psychiatrygarden.fragmenthome.HandoutSearchFragment.2
            @Override // com.psychiatrygarden.widget.SimpleTextWatcher, android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
                super.afterTextChanged(s2);
                if (HandoutSearchFragment.this.isVisible2User && HandoutSearchFragment.this.hasInit) {
                    EventBus.getDefault().post(new SearchHandoutContentChangeEvent(s2.toString(), HandoutSearchFragment.this.mType == 0 ? 1 : 0));
                    if (TextUtils.isEmpty(s2)) {
                        HandoutSearchFragment.this.initHistory();
                        if (HandoutSearchFragment.this.mType != 0) {
                            if (HandoutSearchFragment.this.mAdapter != null) {
                                HandoutSearchFragment.this.mAdapter.getData().clear();
                                HandoutSearchFragment.this.mAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        }
                        HandoutSearchFragment.this.mEmptyView.setVisibility(8);
                        HandoutSearchFragment handoutSearchFragment = HandoutSearchFragment.this;
                        if (handoutSearchFragment.handoutadapter != null) {
                            handoutSearchFragment.mSearhResults.clear();
                            HandoutSearchFragment.this.handoutadapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
        this.mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.psychiatrygarden.fragmenthome.b6
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
                return this.f15461c.lambda$initViews$4(textView, i2, keyEvent);
            }
        });
        initHistory();
        this.hasInit = true;
    }

    @Subscribe
    public void onEventMainThread(SearchHandoutContentChangeEvent e2) {
        if (e2.getType() == this.mType) {
            this.mEditText.setText(e2.getContent());
            this.mEditText.setSelection(TextUtils.isEmpty(e2.getContent()) ? 0 : e2.getContent().length());
            if (TextUtils.isEmpty(e2.getContent())) {
                if (this.mType == 0) {
                    if (this.handoutadapter != null) {
                        this.mSearhResults.clear();
                        this.handoutadapter.notifyDataSetChanged();
                        this.mEmptyView.setVisibility(8);
                        return;
                    }
                    return;
                }
                ReplySearchContentAdapter replySearchContentAdapter = this.mAdapter;
                if (replySearchContentAdapter != null) {
                    this.hasEmptyView = false;
                    replySearchContentAdapter.removeEmptyView();
                    this.mAdapter.getData().clear();
                    this.mAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisible2User = isVisibleToUser;
        if (this.hasInit && isVisibleToUser) {
            if (this.handleSearch) {
                this.page = 1;
                if (!TextUtils.isEmpty(this.mEditText.getText().toString())) {
                    getHandoutDataListNew();
                } else if (this.mType == 0) {
                    if (this.handoutadapter != null) {
                        this.mSearhResults.clear();
                        this.handoutadapter.notifyDataSetChanged();
                    }
                    this.mEmptyView.setVisibility(8);
                } else {
                    this.hasEmptyView = false;
                    ReplySearchContentAdapter replySearchContentAdapter = this.mAdapter;
                    if (replySearchContentAdapter != null) {
                        replySearchContentAdapter.removeEmptyView();
                        this.mAdapter.getData().clear();
                        this.mAdapter.notifyDataSetChanged();
                    }
                }
                this.handleSearch = false;
                return;
            }
            if (TextUtils.isEmpty(this.mEditText.getText())) {
                initHistory();
                if (this.mType == 0) {
                    if (this.handoutadapter != null) {
                        this.mEmptyView.setVisibility(8);
                        this.mSearhResults.clear();
                        this.handoutadapter.notifyDataSetChanged();
                        return;
                    }
                    return;
                }
                ReplySearchContentAdapter replySearchContentAdapter2 = this.mAdapter;
                if (replySearchContentAdapter2 != null) {
                    this.hasEmptyView = false;
                    replySearchContentAdapter2.removeEmptyView();
                    this.mAdapter.getData().clear();
                    this.mAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Subscribe
    public void onEventMainThread(SearchHandoutEvent e2) {
        if (e2.getType() == this.mType) {
            this.mEditText.setText(e2.getContent());
            this.mEditText.setSelection(TextUtils.isEmpty(e2.getContent()) ? 0 : e2.getContent().length());
            this.handleSearch = true;
        }
    }
}
