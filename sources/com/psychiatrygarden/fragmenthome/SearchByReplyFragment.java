package com.psychiatrygarden.fragmenthome;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.interfaces.SimpleCallback;
import com.psychiatrygarden.activity.circleactivity.CircleInfoActivity;
import com.psychiatrygarden.activity.vip.MemberCenterActivity;
import com.psychiatrygarden.adapter.CircleSearchHistoryAdapter;
import com.psychiatrygarden.adapter.CircleSearchValueAdp;
import com.psychiatrygarden.adapter.ReplySearchContentAdapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.CommentSearchBean;
import com.psychiatrygarden.bean.TopicListBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CircleTopicHeaderPopWindow;
import com.psychiatrygarden.widget.ClearEditText;
import com.psychiatrygarden.widget.StackLayout;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import okhttp3.HttpUrl;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SearchByReplyFragment extends BaseFragment implements OnLoadMoreListener, OnItemClickListener {
    private ReplySearchContentAdapter circleSearchContentAdapter;
    private CircleSearchHistoryAdapter circleSearchHistoryAdapter;
    private RecyclerView circleheader;
    private ClearEditText ed_search;
    private boolean hasEmptyView;
    private LinearLayout ll_history_title;
    private CircleSearchValueAdp mSearchValueAdp;
    private RecyclerView rc_search;
    private RecyclerView rc_search_content;
    private RecyclerView rc_search_value;
    private RelativeLayout relopenview;
    private LinearLayout reltopicview;
    private StackLayout sl_search_history;
    private TextView tv_hot_search;
    private View view1;
    private final List<String> stringHotList = new ArrayList();
    private List<CommentSearchBean.DataBean> data = new ArrayList();
    int page = 1;
    private List<String> historyList = new ArrayList();
    public String sort = "-1";
    public String group = "-1";
    public String type = "-1";
    public String topic_id = "0";
    private String editTextData = "";
    private boolean flagEdit = false;
    private int searchPage = 1;
    private boolean isClickSearchKey = false;

    public static /* synthetic */ int access$910(SearchByReplyFragment searchByReplyFragment) {
        int i2 = searchByReplyFragment.searchPage;
        searchByReplyFragment.searchPage = i2 - 1;
        return i2;
    }

    private void addHistoryContent(String content) {
        this.historyList.remove(content);
        this.historyList.add(0, content);
        if (this.historyList.size() > 20) {
            this.historyList = this.historyList.subList(0, 20);
        }
        SharePreferencesUtils.writeStrConfig(CommonParameter.getSaveSearchHistoryData(), new Gson().toJson(this.historyList), getActivity());
        addStack(this.historyList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addStack(List<String> hots) {
        this.sl_search_history.removeAllViews();
        for (int i2 = 0; i2 < hots.size(); i2++) {
            View viewInflate = LayoutInflater.from(getActivity()).inflate(R.layout.item_serach_history_tag, (ViewGroup) null);
            TextView textView = (TextView) viewInflate.findViewById(R.id.tv_search_hot_tag);
            final String str = hots.get(i2);
            if (str.length() > 10) {
                textView.setText(String.format("%s...", str.substring(0, 10)));
            } else {
                textView.setText(str);
            }
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.ic
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f15670c.lambda$addStack$8(str, view);
                }
            });
            this.sl_search_history.addView(viewInflate);
        }
    }

    public static SearchByReplyFragment getInstance(Bundle args) {
        SearchByReplyFragment searchByReplyFragment = new SearchByReplyFragment();
        searchByReplyFragment.setArguments(args);
        return searchByReplyFragment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getReplyListData(String isEditSearch) {
        if (TextUtils.isEmpty(this.ed_search.getText().toString())) {
            return;
        }
        addHistoryContent(this.ed_search.getText().toString());
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("module_type", Constants.VIA_REPORT_TYPE_SET_AVATAR);
        ajaxParams.put("comment_type", "2");
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_ID, this.topic_id);
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.page);
        ajaxParams.put("is_hot", isEditSearch);
        ajaxParams.put("search", this.ed_search.getText().toString());
        YJYHttpUtils.post(this.mContext.getApplicationContext(), NetworkRequestsURL.commsearchApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.SearchByReplyFragment.11
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                SearchByReplyFragment searchByReplyFragment = SearchByReplyFragment.this;
                int i2 = searchByReplyFragment.page;
                if (i2 > 1) {
                    searchByReplyFragment.page = i2 - 1;
                    searchByReplyFragment.circleSearchContentAdapter.getLoadMoreModule().loadMoreFail();
                }
                if (SearchByReplyFragment.this.hasEmptyView || SearchByReplyFragment.this.circleSearchContentAdapter == null) {
                    return;
                }
                SearchByReplyFragment.this.hasEmptyView = true;
                SearchByReplyFragment.this.circleSearchContentAdapter.setEmptyView(R.layout.adapter_empty_txt_view);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass11) s2);
                try {
                    CommentSearchBean commentSearchBean = (CommentSearchBean) new Gson().fromJson(s2, CommentSearchBean.class);
                    if (!commentSearchBean.getCode().equals("200")) {
                        SearchByReplyFragment searchByReplyFragment = SearchByReplyFragment.this;
                        int i2 = searchByReplyFragment.page;
                        if (i2 > 1) {
                            searchByReplyFragment.page = i2 - 1;
                            searchByReplyFragment.circleSearchContentAdapter.getLoadMoreModule().loadMoreEnd();
                        }
                        SearchByReplyFragment.this.AlertToast(commentSearchBean.getMessage());
                    } else if (commentSearchBean.getData() == null || commentSearchBean.getData().size() <= 0) {
                        SearchByReplyFragment searchByReplyFragment2 = SearchByReplyFragment.this;
                        int i3 = searchByReplyFragment2.page;
                        if (i3 > 1) {
                            searchByReplyFragment2.page = i3 - 1;
                        } else {
                            searchByReplyFragment2.data.clear();
                            SearchByReplyFragment.this.circleSearchContentAdapter.setList(SearchByReplyFragment.this.data);
                            SearchByReplyFragment.this.circleSearchContentAdapter.getLoadMoreModule().checkDisableLoadMoreIfNotFullPage();
                        }
                        if (SearchByReplyFragment.this.circleSearchContentAdapter != null) {
                            SearchByReplyFragment.this.circleSearchContentAdapter.getLoadMoreModule().loadMoreEnd();
                        }
                    } else {
                        SearchByReplyFragment searchByReplyFragment3 = SearchByReplyFragment.this;
                        if (searchByReplyFragment3.page == 1) {
                            searchByReplyFragment3.data.clear();
                            SearchByReplyFragment.this.data = commentSearchBean.getData();
                            SearchByReplyFragment.this.circleSearchContentAdapter.setSearchContent(SearchByReplyFragment.this.ed_search.getText().toString());
                            SearchByReplyFragment.this.circleSearchContentAdapter.setList(SearchByReplyFragment.this.data);
                            SearchByReplyFragment.this.circleSearchContentAdapter.getLoadMoreModule().checkDisableLoadMoreIfNotFullPage();
                        } else {
                            List<CommentSearchBean.DataBean> data = commentSearchBean.getData();
                            SearchByReplyFragment.this.data.addAll(data);
                            SearchByReplyFragment.this.circleSearchContentAdapter.addData((Collection) data);
                            SearchByReplyFragment.this.circleSearchContentAdapter.getLoadMoreModule().loadMoreComplete();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    SearchByReplyFragment searchByReplyFragment4 = SearchByReplyFragment.this;
                    int i4 = searchByReplyFragment4.page;
                    if (i4 > 1) {
                        searchByReplyFragment4.page = i4 - 1;
                        searchByReplyFragment4.circleSearchContentAdapter.getLoadMoreModule().loadMoreFail();
                    }
                }
                if (!SearchByReplyFragment.this.hasEmptyView && SearchByReplyFragment.this.circleSearchContentAdapter != null) {
                    SearchByReplyFragment.this.hasEmptyView = true;
                    SearchByReplyFragment.this.circleSearchContentAdapter.setEmptyView(R.layout.adapter_empty_txt_view);
                }
                SearchByReplyFragment.this.reltopicview.setVisibility(0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getSearchValueByKeywords(String key) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_KEYWORD, key);
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.searchPage);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.wordBySearchKey, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.SearchByReplyFragment.12
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (SearchByReplyFragment.this.searchPage > 1) {
                    SearchByReplyFragment.access$910(SearchByReplyFragment.this);
                    SearchByReplyFragment.this.mSearchValueAdp.getLoadMoreModule().loadMoreFail();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass12) s2);
                try {
                    SearchByReplyFragment.this.rc_search.setVisibility(8);
                    SearchByReplyFragment.this.rc_search_content.setVisibility(8);
                    SearchByReplyFragment.this.rc_search_value.setVisibility(0);
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    if (SearchByReplyFragment.this.searchPage > 1) {
                        SearchByReplyFragment.access$910(SearchByReplyFragment.this);
                        SearchByReplyFragment.this.mSearchValueAdp.getLoadMoreModule().loadMoreFail();
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideInputMethod() {
        View currentFocus = getActivity().getCurrentFocus();
        FragmentActivity activity = getActivity();
        getActivity();
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService("input_method");
        if (inputMethodManager == null || currentFocus == null) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
    }

    private void initRcy() {
        this.historyList = (List) new Gson().fromJson(SharePreferencesUtils.readStrConfig(CommonParameter.getSaveSearchHistoryData(), getActivity(), HttpUrl.PATH_SEGMENT_ENCODE_SET_URI), new TypeToken<List<String>>() { // from class: com.psychiatrygarden.fragmenthome.SearchByReplyFragment.2
        }.getType());
        this.rc_search.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.rc_search_content.setLayoutManager(new LinearLayoutManager(getActivity()));
        CircleSearchValueAdp circleSearchValueAdp = new CircleSearchValueAdp();
        this.mSearchValueAdp = circleSearchValueAdp;
        this.rc_search_value.setAdapter(circleSearchValueAdp);
        this.mSearchValueAdp.setEmptyView(R.layout.view_empty_search_value);
        this.circleSearchHistoryAdapter = new CircleSearchHistoryAdapter(R.layout.item_search_history, new ArrayList());
        View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.head_search_history, (ViewGroup) null);
        this.ll_history_title = (LinearLayout) viewInflate.findViewById(R.id.ll_history_title);
        this.sl_search_history = (StackLayout) viewInflate.findViewById(R.id.sl_search_history);
        this.tv_hot_search = (TextView) viewInflate.findViewById(R.id.tv_hot_search);
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.iv_search_history_delete);
        imageView.setImageResource(SkinManager.getCurrentSkinType(getActivity()) == 1 ? R.mipmap.icon_delete_def_night : R.mipmap.icon_delete_def);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.nc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15873c.lambda$initRcy$0(view);
            }
        });
        if (this.historyList.size() > 0) {
            this.ll_history_title.setVisibility(0);
            this.sl_search_history.setVisibility(0);
            addStack(this.historyList);
        } else {
            this.ll_history_title.setVisibility(8);
            this.sl_search_history.setVisibility(8);
        }
        this.circleSearchHistoryAdapter.addHeaderView(viewInflate);
        this.circleSearchHistoryAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.fragmenthome.oc
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f15895c.lambda$initRcy$1(baseQuickAdapter, view, i2);
            }
        });
        this.rc_search.setAdapter(this.circleSearchHistoryAdapter);
        ReplySearchContentAdapter replySearchContentAdapter = new ReplySearchContentAdapter(R.layout.item_search_by_reply, new ArrayList());
        this.circleSearchContentAdapter = replySearchContentAdapter;
        replySearchContentAdapter.setHeaderWithEmptyEnable(true);
        this.circleSearchContentAdapter.setOnItemClickListener(this);
        this.circleSearchContentAdapter.getLoadMoreModule().setOnLoadMoreListener(this);
        this.rc_search_content.setAdapter(this.circleSearchContentAdapter);
        this.mSearchValueAdp.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.fragmenthome.pc
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                SearchByReplyFragment.lambda$initRcy$2(baseQuickAdapter, view, i2);
            }
        });
        this.mSearchValueAdp.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.fragmenthome.qc
            @Override // com.chad.library.adapter.base.listener.OnLoadMoreListener
            public final void onLoadMore() {
                this.f15943c.lambda$initRcy$3();
            }
        });
        getHeaderData();
        this.reltopicview.setVisibility(8);
    }

    private void initSearch() {
        if (this.flagEdit) {
            String strConfig = SharePreferencesUtils.readStrConfig("searchCircleKeyWords", this.mContext);
            this.editTextData = strConfig;
            this.ed_search.setText(strConfig);
            getReplyListData("0");
            showAndHide(8, 0);
        }
        this.ed_search.post(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.lc
            @Override // java.lang.Runnable
            public final void run() {
                this.f15813c.lambda$initSearch$6();
            }
        });
        this.ed_search.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.psychiatrygarden.fragmenthome.mc
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
                return this.f15849c.lambda$initSearch$7(textView, i2, keyEvent);
            }
        });
        this.ed_search.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.fragmenthome.SearchByReplyFragment.9
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
                if (s2.length() > 0) {
                    if (SearchByReplyFragment.this.isClickSearchKey) {
                        return;
                    }
                    SearchByReplyFragment.this.searchPage = 1;
                    SearchByReplyFragment.this.mSearchValueAdp.getData().clear();
                    SearchByReplyFragment.this.getSearchValueByKeywords(s2.toString());
                    return;
                }
                SearchByReplyFragment.this.isClickSearchKey = false;
                SearchByReplyFragment.this.rc_search_value.setVisibility(8);
                SharePreferencesUtils.writeStrConfig("searchCircleKeyWords", "", ((BaseFragment) SearchByReplyFragment.this).mContext);
                if (SearchByReplyFragment.this.historyList.size() > 0) {
                    SearchByReplyFragment.this.ll_history_title.setVisibility(0);
                    SearchByReplyFragment.this.sl_search_history.setVisibility(0);
                    SearchByReplyFragment searchByReplyFragment = SearchByReplyFragment.this;
                    searchByReplyFragment.addStack(searchByReplyFragment.historyList);
                } else {
                    SearchByReplyFragment.this.ll_history_title.setVisibility(8);
                    SearchByReplyFragment.this.sl_search_history.setVisibility(8);
                }
                SearchByReplyFragment.this.showAndHide(0, 8);
                if (SearchByReplyFragment.this.circleSearchContentAdapter != null) {
                    SearchByReplyFragment.this.data.clear();
                    SearchByReplyFragment.this.circleSearchContentAdapter.notifyDataSetChanged();
                }
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addStack$8(String str, View view) {
        this.isClickSearchKey = true;
        this.rc_search_value.setVisibility(8);
        showAndHide(8, 0);
        hideInputMethod();
        this.ed_search.setText(str);
        this.ed_search.setSelection(str.length());
        SharePreferencesUtils.writeStrConfig("searchCircleKeyWords", this.ed_search.getText().toString().trim(), this.mContext);
        this.page = 1;
        getReplyListData("0");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initHeaderView$4(List list, BaseQuickAdapter baseQuickAdapter, BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
        if (list.size() > 0) {
            this.isClickSearchKey = true;
            this.rc_search_value.setVisibility(8);
            this.topic_id = ((TopicListBean.DataDTO) list.get(i2)).getId() + "";
            baseQuickAdapter.notifyDataSetChanged();
            showAndHide(8, 0);
            this.page = 1;
            getReplyListData("0");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initHeaderView$5(List list, final BaseQuickAdapter baseQuickAdapter, View view) {
        if (list.size() > 0) {
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < baseQuickAdapter.getData().size(); i2++) {
                TopicListBean.DataDTO dataDTO = new TopicListBean.DataDTO();
                dataDTO.setId(((TopicListBean.DataDTO) baseQuickAdapter.getData().get(i2)).getId() + "");
                dataDTO.setName(((TopicListBean.DataDTO) baseQuickAdapter.getData().get(i2)).getName() + "");
                dataDTO.setIs_default(((TopicListBean.DataDTO) baseQuickAdapter.getData().get(i2)).getIs_default());
                arrayList.add(dataDTO);
            }
            new XPopup.Builder(getActivity()).popupPosition(PopupPosition.Bottom).setPopupCallback(new SimpleCallback() { // from class: com.psychiatrygarden.fragmenthome.SearchByReplyFragment.7
                @Override // com.lxj.xpopup.interfaces.SimpleCallback, com.lxj.xpopup.interfaces.XPopupCallback
                public void onDismiss(BasePopupView popupView) {
                    super.onDismiss(popupView);
                    SearchByReplyFragment.this.hideInputMethod();
                }
            }).atView(this.view1).asCustom(new CircleTopicHeaderPopWindow(getActivity(), arrayList, this.topic_id, new CircleTopicHeaderPopWindow.CirclePartClick() { // from class: com.psychiatrygarden.fragmenthome.SearchByReplyFragment.6
                /* JADX WARN: Code restructure failed: missing block: B:18:0x0056, code lost:
                
                    r5.this$0.getmCustomTopicListData(new com.google.gson.Gson().toJson(r1));
                 */
                @Override // com.psychiatrygarden.widget.CircleTopicHeaderPopWindow.CirclePartClick
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public void dismiss(java.util.List<com.psychiatrygarden.bean.TopicListBean.DataDTO> r6) {
                    /*
                        r5 = this;
                        int r0 = r6.size()
                        if (r0 <= 0) goto L9e
                        com.chad.library.adapter.base.BaseQuickAdapter r0 = r2
                        if (r0 == 0) goto L9e
                        java.util.List r0 = r0.getData()
                        int r0 = r0.size()
                        if (r0 <= 0) goto L9e
                        r0 = 0
                        java.util.ArrayList r1 = new java.util.ArrayList     // Catch: java.lang.Exception -> L67
                        r1.<init>()     // Catch: java.lang.Exception -> L67
                        r2 = 1
                    L1b:
                        int r3 = r6.size()     // Catch: java.lang.Exception -> L67
                        if (r2 >= r3) goto L2d
                        java.lang.Object r3 = r6.get(r2)     // Catch: java.lang.Exception -> L67
                        com.psychiatrygarden.bean.TopicListBean$DataDTO r3 = (com.psychiatrygarden.bean.TopicListBean.DataDTO) r3     // Catch: java.lang.Exception -> L67
                        r1.add(r3)     // Catch: java.lang.Exception -> L67
                        int r2 = r2 + 1
                        goto L1b
                    L2d:
                        r2 = r0
                    L2e:
                        int r3 = r1.size()     // Catch: java.lang.Exception -> L67
                        if (r2 >= r3) goto L6b
                        com.chad.library.adapter.base.BaseQuickAdapter r3 = r2     // Catch: java.lang.Exception -> L67
                        java.util.List r3 = r3.getData()     // Catch: java.lang.Exception -> L67
                        int r4 = r2 + 1
                        java.lang.Object r3 = r3.get(r4)     // Catch: java.lang.Exception -> L67
                        com.psychiatrygarden.bean.TopicListBean$DataDTO r3 = (com.psychiatrygarden.bean.TopicListBean.DataDTO) r3     // Catch: java.lang.Exception -> L67
                        java.lang.String r3 = r3.getId()     // Catch: java.lang.Exception -> L67
                        java.lang.Object r2 = r1.get(r2)     // Catch: java.lang.Exception -> L67
                        com.psychiatrygarden.bean.TopicListBean$DataDTO r2 = (com.psychiatrygarden.bean.TopicListBean.DataDTO) r2     // Catch: java.lang.Exception -> L67
                        java.lang.String r2 = r2.getId()     // Catch: java.lang.Exception -> L67
                        boolean r2 = r3.equals(r2)     // Catch: java.lang.Exception -> L67
                        if (r2 != 0) goto L65
                        com.psychiatrygarden.fragmenthome.SearchByReplyFragment r2 = com.psychiatrygarden.fragmenthome.SearchByReplyFragment.this     // Catch: java.lang.Exception -> L67
                        com.google.gson.Gson r3 = new com.google.gson.Gson     // Catch: java.lang.Exception -> L67
                        r3.<init>()     // Catch: java.lang.Exception -> L67
                        java.lang.String r1 = r3.toJson(r1)     // Catch: java.lang.Exception -> L67
                        r2.getmCustomTopicListData(r1)     // Catch: java.lang.Exception -> L67
                        goto L6b
                    L65:
                        r2 = r4
                        goto L2e
                    L67:
                        r1 = move-exception
                        r1.printStackTrace()
                    L6b:
                        com.chad.library.adapter.base.BaseQuickAdapter r1 = r2
                        r1.setList(r6)
                        r1 = r0
                    L71:
                        int r2 = r6.size()
                        if (r1 >= r2) goto L9e
                        com.psychiatrygarden.fragmenthome.SearchByReplyFragment r2 = com.psychiatrygarden.fragmenthome.SearchByReplyFragment.this
                        java.lang.String r2 = r2.topic_id
                        java.lang.Object r3 = r6.get(r1)
                        com.psychiatrygarden.bean.TopicListBean$DataDTO r3 = (com.psychiatrygarden.bean.TopicListBean.DataDTO) r3
                        java.lang.String r3 = r3.getId()
                        boolean r2 = r2.equals(r3)
                        if (r2 == 0) goto L9b
                        com.psychiatrygarden.fragmenthome.SearchByReplyFragment r6 = com.psychiatrygarden.fragmenthome.SearchByReplyFragment.this
                        androidx.recyclerview.widget.RecyclerView r6 = com.psychiatrygarden.fragmenthome.SearchByReplyFragment.access$2000(r6)
                        androidx.recyclerview.widget.RecyclerView$LayoutManager r6 = r6.getLayoutManager()
                        androidx.recyclerview.widget.LinearLayoutManager r6 = (androidx.recyclerview.widget.LinearLayoutManager) r6
                        r6.scrollToPositionWithOffset(r1, r0)
                        goto L9e
                    L9b:
                        int r1 = r1 + 1
                        goto L71
                    L9e:
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.fragmenthome.SearchByReplyFragment.AnonymousClass6.dismiss(java.util.List):void");
                }

                @Override // com.psychiatrygarden.widget.CircleTopicHeaderPopWindow.CirclePartClick
                public void putValue(String value, String label) {
                    SearchByReplyFragment.this.isClickSearchKey = true;
                    SearchByReplyFragment.this.rc_search_value.setVisibility(8);
                    SearchByReplyFragment.this.topic_id = value + "";
                    SearchByReplyFragment.this.showAndHide(8, 0);
                    SearchByReplyFragment searchByReplyFragment = SearchByReplyFragment.this;
                    searchByReplyFragment.page = 1;
                    searchByReplyFragment.getReplyListData("0");
                }
            })).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRcy$0(View view) {
        this.historyList.clear();
        SharePreferencesUtils.writeStrConfig(CommonParameter.getSaveSearchHistoryData(), new Gson().toJson(this.historyList), getActivity());
        this.ll_history_title.setVisibility(8);
        this.sl_search_history.setVisibility(8);
        addStack(this.historyList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRcy$1(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        this.isClickSearchKey = true;
        this.rc_search_value.setVisibility(8);
        showAndHide(8, 0);
        hideInputMethod();
        this.ed_search.setText(this.stringHotList.get(i2));
        this.ed_search.setSelection(this.stringHotList.get(i2).length());
        SharePreferencesUtils.writeStrConfig("searchCircleKeyWords", this.ed_search.getText().toString().trim(), this.mContext);
        this.page = 1;
        getReplyListData("0");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$initRcy$2(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRcy$3() {
        this.searchPage++;
        getSearchValueByKeywords(this.ed_search.getText().toString().trim());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initSearch$6() {
        this.ed_search.clearFocus();
        this.ed_search.requestFocus();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initSearch$7(TextView textView, int i2, KeyEvent keyEvent) {
        if (i2 != 3) {
            return false;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) textView.getContext().getSystemService("input_method");
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(textView.getApplicationWindowToken(), 0);
        }
        if (!this.ed_search.getText().toString().equals("")) {
            this.isClickSearchKey = true;
            this.rc_search_value.setVisibility(8);
            SharePreferencesUtils.writeStrConfig("searchCircleKeyWords", this.ed_search.getText().toString().trim(), this.mContext);
            showAndHide(8, 0);
            this.page = 1;
            getReplyListData("1");
        }
        return true;
    }

    public void getHeaderData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", "list");
        ajaxParams.put("cid", "-1");
        YJYHttpUtils.get(getActivity(), NetworkRequestsURL.mTopicListApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.SearchByReplyFragment.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    TopicListBean topicListBean = (TopicListBean) new Gson().fromJson(s2, TopicListBean.class);
                    if (!topicListBean.getCode().equals("200") || topicListBean.getData() == null || topicListBean.getData().size() <= 0) {
                        return;
                    }
                    SearchByReplyFragment.this.initHeaderView(topicListBean.getData());
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.activity_circle_search_new;
    }

    public void getRankData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_KEYWORD, "" + this.ed_search.getText().toString());
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.page);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.msearchRankData, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.SearchByReplyFragment.10
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                SearchByReplyFragment.this.tv_hot_search.setVisibility(8);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass10) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optInt("code") != 200) {
                        SearchByReplyFragment.this.AlertToast(jSONObject.optString("message"));
                        SearchByReplyFragment.this.tv_hot_search.setVisibility(8);
                        return;
                    }
                    jSONObject.optJSONArray("data");
                    for (int i2 = 0; i2 < jSONObject.optJSONArray("data").length(); i2++) {
                        SearchByReplyFragment.this.stringHotList.add(jSONObject.optJSONArray("data").getString(i2));
                    }
                    SearchByReplyFragment.this.circleSearchHistoryAdapter.setNewInstance(SearchByReplyFragment.this.stringHotList);
                    if (SearchByReplyFragment.this.stringHotList.size() > 0) {
                        SearchByReplyFragment.this.tv_hot_search.setVisibility(0);
                    } else {
                        SearchByReplyFragment.this.tv_hot_search.setVisibility(8);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getmCustomTopicListData(String json) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("custom_sort", json);
        YJYHttpUtils.post(getActivity(), NetworkRequestsURL.mCustomTopicListApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.SearchByReplyFragment.8
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass8) s2);
            }
        });
    }

    public void initHeaderView(final List<TopicListBean.DataDTO> data) {
        final BaseQuickAdapter<TopicListBean.DataDTO, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<TopicListBean.DataDTO, BaseViewHolder>(R.layout.layout_topic_header_item, data) { // from class: com.psychiatrygarden.fragmenthome.SearchByReplyFragment.4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NotNull BaseViewHolder baseViewHolder, TopicListBean.DataDTO dataDTO) {
                baseViewHolder.setText(R.id.name, dataDTO.getName());
                baseViewHolder.getView(R.id.name).setSelected(SearchByReplyFragment.this.topic_id.equals(dataDTO.getId()));
            }
        };
        this.circleheader.setAdapter(baseQuickAdapter);
        this.circleheader.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.psychiatrygarden.fragmenthome.SearchByReplyFragment.5
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        int i2 = 0;
        while (true) {
            if (i2 >= data.size()) {
                break;
            }
            if (this.topic_id.equals(data.get(i2).getId())) {
                ((LinearLayoutManager) this.circleheader.getLayoutManager()).scrollToPositionWithOffset(i2, 0);
                break;
            }
            i2++;
        }
        View viewInflate = LayoutInflater.from(getActivity()).inflate(R.layout.layout_topic_header_item, (ViewGroup) null);
        viewInflate.setVisibility(4);
        baseQuickAdapter.addFooterView(viewInflate, -1, 0);
        baseQuickAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.fragmenthome.jc
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i3) {
                this.f15694c.lambda$initHeaderView$4(data, baseQuickAdapter, baseQuickAdapter2, view, i3);
            }
        });
        this.relopenview.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.kc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15720c.lambda$initHeaderView$5(data, baseQuickAdapter, view);
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        getActivity().getWindow().clearFlags(131072);
        getActivity().getWindow().setSoftInputMode(5);
        this.editTextData = getArguments().getString("editTextData");
        this.flagEdit = getArguments().getBoolean("flagEdit", false);
        this.editTextData = SharePreferencesUtils.readStrConfig("searchCircleKeyWords", this.mContext);
        this.ed_search = (ClearEditText) holder.get(R.id.ed_search);
        this.reltopicview = (LinearLayout) holder.get(R.id.reltopicview);
        this.view1 = holder.get(R.id.view1);
        this.relopenview = (RelativeLayout) holder.get(R.id.relopenview);
        RecyclerView recyclerView = (RecyclerView) holder.get(R.id.circleheader);
        this.circleheader = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), 0, false));
        this.rc_search = (RecyclerView) holder.get(R.id.rc_search);
        this.rc_search_content = (RecyclerView) holder.get(R.id.rc_search_content);
        this.rc_search_value = (RecyclerView) holder.get(R.id.rc_search_value);
        this.ed_search.setText(this.editTextData);
        initRcy();
        initSearch();
        getRankData();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // com.chad.library.adapter.base.listener.OnItemClickListener
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        try {
            if (isLogin()) {
                if ("1".equals(this.data.get(position).getNo_access())) {
                    startActivity(new Intent(getActivity(), (Class<?>) MemberCenterActivity.class));
                    return;
                }
                Intent intent = new Intent(this.mContext, (Class<?>) CircleInfoActivity.class);
                intent.putExtra("article_id", "" + this.data.get(position).getObj_id());
                intent.putExtra("module_type", com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SET_AVATAR);
                startActivity(intent);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.chad.library.adapter.base.listener.OnLoadMoreListener
    public void onLoadMore() {
        this.page++;
        getReplyListData("0");
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            this.editTextData = SharePreferencesUtils.readStrConfig("searchCircleKeyWords", this.mContext);
            this.historyList = (List) new Gson().fromJson(SharePreferencesUtils.readStrConfig(CommonParameter.getSaveSearchHistoryData(), getActivity(), HttpUrl.PATH_SEGMENT_ENCODE_SET_URI), new TypeToken<List<String>>() { // from class: com.psychiatrygarden.fragmenthome.SearchByReplyFragment.1
            }.getType());
            Log.e("user_visible", "按回复页面可见：" + this.editTextData + ";list=" + this.historyList.size());
            ClearEditText clearEditText = this.ed_search;
            if (clearEditText != null && !this.editTextData.equals(clearEditText.getText().toString().trim())) {
                this.isClickSearchKey = true;
                this.rc_search_value.setVisibility(8);
                showAndHide(8, 0);
                hideInputMethod();
                this.ed_search.setText(this.editTextData);
                this.ed_search.setSelection(this.editTextData.length());
                this.page = 1;
                getReplyListData("0");
            }
            if (this.historyList.size() <= 0) {
                this.ll_history_title.setVisibility(8);
                this.sl_search_history.setVisibility(8);
            } else {
                this.ll_history_title.setVisibility(0);
                this.sl_search_history.setVisibility(0);
                addStack(this.historyList);
            }
        }
    }

    public void showAndHide(int v2, int v12) {
        this.rc_search.setVisibility(v2);
        this.rc_search_content.setVisibility(v12);
        RecyclerView recyclerView = this.circleheader;
        if (recyclerView == null || recyclerView.getAdapter() == null || this.circleheader.getAdapter().getItemCount() <= 0) {
            return;
        }
        this.reltopicview.setVisibility(v12);
    }
}
