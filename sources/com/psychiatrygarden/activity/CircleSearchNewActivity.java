package com.psychiatrygarden.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.psychiatrygarden.adapter.CircleSearchContentAdapter;
import com.psychiatrygarden.adapter.CircleSearchHistoryAdapter;
import com.psychiatrygarden.bean.CirclrListBean;
import com.psychiatrygarden.bean.TopicListBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.DividerItemDecoration;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StickerSpan;
import com.psychiatrygarden.utils.URLImageParser;
import com.psychiatrygarden.widget.CircleTopicHeaderPopWindow;
import com.psychiatrygarden.widget.ClearEditText;
import com.psychiatrygarden.widget.StackLayout;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import okhttp3.HttpUrl;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CircleSearchNewActivity extends BaseActivity implements OnLoadMoreListener, OnItemClickListener {
    public TextView allSections;
    public TextView allTypes;
    private CircleSearchContentAdapter circleSearchContentAdapter;
    private CircleSearchHistoryAdapter circleSearchHistoryAdapter;
    private RecyclerView circleheader;
    public TextView comprehensiveSorting;
    private ClearEditText ed_search;
    private boolean hasEmptyView;
    private LinearLayout ll_history_title;
    private CirclrListBean mCirclrListBean;
    private RecyclerView rc_search;
    private RecyclerView rc_search_content;
    private RelativeLayout relopenview;
    private LinearLayout reltopicview;
    private TextView searchview;
    private StackLayout sl_search_history;
    private TextView tv_hot_search;
    private View view1;
    private final List<String> stringHotList = new ArrayList();
    private List<CirclrListBean.DataBean> data = new ArrayList();
    int page = 1;
    private List<String> historyList = new ArrayList();
    public String sort = "-1";
    public String group = "-1";
    public String type = "-1";
    public String topic_id = "0";

    private void addHistoryContent(String content) {
        this.historyList.remove(content);
        this.historyList.add(0, content);
        if (this.historyList.size() > 20) {
            this.historyList = this.historyList.subList(0, 20);
        }
        SharePreferencesUtils.writeStrConfig(CommonParameter.getSaveSearchHistoryData(), new Gson().toJson(this.historyList), this);
        addStack(this.historyList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addStack(List<String> hots) {
        this.sl_search_history.removeAllViews();
        for (int i2 = 0; i2 < hots.size(); i2++) {
            View viewInflate = LayoutInflater.from(this).inflate(R.layout.item_serach_history_tag, (ViewGroup) null);
            TextView textView = (TextView) viewInflate.findViewById(R.id.tv_search_hot_tag);
            final String str = hots.get(i2);
            if (str.length() > 10) {
                textView.setText(String.format("%s...", str.substring(0, 10)));
            } else {
                textView.setText(str);
            }
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.v1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f14006c.lambda$addStack$7(str, view);
                }
            });
            this.sl_search_history.addView(viewInflate);
        }
    }

    private void initRcy() {
        this.historyList = (List) new Gson().fromJson(SharePreferencesUtils.readStrConfig(CommonParameter.getSaveSearchHistoryData(), this, HttpUrl.PATH_SEGMENT_ENCODE_SET_URI), new TypeToken<List<String>>() { // from class: com.psychiatrygarden.activity.CircleSearchNewActivity.1
        }.getType());
        this.rc_search.setLayoutManager(new LinearLayoutManager(this));
        this.rc_search_content.setLayoutManager(new LinearLayoutManager(this));
        this.rc_search_content.addItemDecoration(new DividerItemDecoration(this, 0, 2, Color.parseColor(SkinManager.getCurrentSkinType(this) == 1 ? "#1C2134" : "#EEEEEE")));
        this.circleSearchHistoryAdapter = new CircleSearchHistoryAdapter(R.layout.item_search_history, new ArrayList());
        View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.head_search_history, (ViewGroup) null);
        this.ll_history_title = (LinearLayout) viewInflate.findViewById(R.id.ll_history_title);
        this.sl_search_history = (StackLayout) viewInflate.findViewById(R.id.sl_search_history);
        this.tv_hot_search = (TextView) viewInflate.findViewById(R.id.tv_hot_search);
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.iv_search_history_delete);
        imageView.setImageResource(SkinManager.getCurrentSkinType(this) == 1 ? R.mipmap.icon_delete_def_night : R.mipmap.icon_delete_def);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.a2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f10981c.lambda$initRcy$0(view);
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
        this.circleSearchHistoryAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.b2
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f11094c.lambda$initRcy$1(baseQuickAdapter, view, i2);
            }
        });
        this.rc_search.setAdapter(this.circleSearchHistoryAdapter);
        CircleSearchContentAdapter circleSearchContentAdapter = new CircleSearchContentAdapter(R.layout.activity_circleitem, new ArrayList(), false);
        this.circleSearchContentAdapter = circleSearchContentAdapter;
        circleSearchContentAdapter.setHeaderWithEmptyEnable(true);
        this.circleSearchContentAdapter.setOnItemClickListener(this);
        this.circleSearchContentAdapter.getLoadMoreModule().setOnLoadMoreListener(this);
        this.rc_search_content.setAdapter(this.circleSearchContentAdapter);
        this.searchview.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.c2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11133c.lambda$initRcy$2(view);
            }
        });
        getHeaderData();
        this.reltopicview.setVisibility(8);
    }

    private void initSearch() {
        if (getIntent().getExtras().getBoolean("flagEdit", false)) {
            this.ed_search.setText(getIntent().getExtras().getString("editTextData"));
            getData();
            showAndHide(8, 0);
        }
        this.ed_search.post(new Runnable() { // from class: com.psychiatrygarden.activity.w1
            @Override // java.lang.Runnable
            public final void run() {
                this.f14135c.lambda$initSearch$5();
            }
        });
        this.ed_search.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.psychiatrygarden.activity.x1
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
                return this.f14168c.lambda$initSearch$6(textView, i2, keyEvent);
            }
        });
        this.ed_search.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.activity.CircleSearchNewActivity.8
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
                if (s2.length() <= 0) {
                    if (CircleSearchNewActivity.this.historyList.size() > 0) {
                        CircleSearchNewActivity.this.ll_history_title.setVisibility(0);
                        CircleSearchNewActivity.this.sl_search_history.setVisibility(0);
                        CircleSearchNewActivity circleSearchNewActivity = CircleSearchNewActivity.this;
                        circleSearchNewActivity.addStack(circleSearchNewActivity.historyList);
                    } else {
                        CircleSearchNewActivity.this.ll_history_title.setVisibility(8);
                        CircleSearchNewActivity.this.sl_search_history.setVisibility(8);
                    }
                    CircleSearchNewActivity.this.showAndHide(0, 8);
                    if (CircleSearchNewActivity.this.circleSearchContentAdapter != null) {
                        CircleSearchNewActivity.this.data.clear();
                        CircleSearchNewActivity.this.circleSearchContentAdapter.notifyDataSetChanged();
                    }
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
    public /* synthetic */ void lambda$addStack$7(String str, View view) {
        showAndHide(8, 0);
        hideInputMethod();
        this.ed_search.setText(str);
        this.ed_search.setSelection(str.length());
        this.page = 1;
        getData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initHeaderView$3(List list, BaseQuickAdapter baseQuickAdapter, BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
        if (list.size() > 0) {
            this.topic_id = ((TopicListBean.DataDTO) list.get(i2)).getId() + "";
            baseQuickAdapter.notifyDataSetChanged();
            showAndHide(8, 0);
            this.page = 1;
            getData();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initHeaderView$4(List list, final BaseQuickAdapter baseQuickAdapter, View view) {
        if (list.size() > 0) {
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < baseQuickAdapter.getData().size(); i2++) {
                TopicListBean.DataDTO dataDTO = new TopicListBean.DataDTO();
                dataDTO.setId(((TopicListBean.DataDTO) baseQuickAdapter.getData().get(i2)).getId() + "");
                dataDTO.setName(((TopicListBean.DataDTO) baseQuickAdapter.getData().get(i2)).getName() + "");
                dataDTO.setIs_default(((TopicListBean.DataDTO) baseQuickAdapter.getData().get(i2)).getIs_default());
                arrayList.add(dataDTO);
            }
            new XPopup.Builder(this).popupPosition(PopupPosition.Bottom).setPopupCallback(new SimpleCallback() { // from class: com.psychiatrygarden.activity.CircleSearchNewActivity.6
                @Override // com.lxj.xpopup.interfaces.SimpleCallback, com.lxj.xpopup.interfaces.XPopupCallback
                public void onDismiss(BasePopupView popupView) {
                    super.onDismiss(popupView);
                    CircleSearchNewActivity.this.hideInputMethod();
                }
            }).atView(this.view1).asCustom(new CircleTopicHeaderPopWindow(this, arrayList, this.topic_id, new CircleTopicHeaderPopWindow.CirclePartClick() { // from class: com.psychiatrygarden.activity.CircleSearchNewActivity.5
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
                        com.psychiatrygarden.activity.CircleSearchNewActivity r2 = com.psychiatrygarden.activity.CircleSearchNewActivity.this     // Catch: java.lang.Exception -> L67
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
                        com.psychiatrygarden.activity.CircleSearchNewActivity r2 = com.psychiatrygarden.activity.CircleSearchNewActivity.this
                        java.lang.String r2 = r2.topic_id
                        java.lang.Object r3 = r6.get(r1)
                        com.psychiatrygarden.bean.TopicListBean$DataDTO r3 = (com.psychiatrygarden.bean.TopicListBean.DataDTO) r3
                        java.lang.String r3 = r3.getId()
                        boolean r2 = r2.equals(r3)
                        if (r2 == 0) goto L9b
                        com.psychiatrygarden.activity.CircleSearchNewActivity r6 = com.psychiatrygarden.activity.CircleSearchNewActivity.this
                        androidx.recyclerview.widget.RecyclerView r6 = com.psychiatrygarden.activity.CircleSearchNewActivity.access$1300(r6)
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
                    throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.CircleSearchNewActivity.AnonymousClass5.dismiss(java.util.List):void");
                }

                @Override // com.psychiatrygarden.widget.CircleTopicHeaderPopWindow.CirclePartClick
                public void putValue(String value, String label) {
                    CircleSearchNewActivity.this.topic_id = value + "";
                    CircleSearchNewActivity.this.showAndHide(8, 0);
                    CircleSearchNewActivity circleSearchNewActivity = CircleSearchNewActivity.this;
                    circleSearchNewActivity.page = 1;
                    circleSearchNewActivity.getData();
                }
            })).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRcy$0(View view) {
        this.historyList.clear();
        SharePreferencesUtils.writeStrConfig(CommonParameter.getSaveSearchHistoryData(), new Gson().toJson(this.historyList), this);
        this.ll_history_title.setVisibility(8);
        this.sl_search_history.setVisibility(8);
        addStack(this.historyList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRcy$1(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        showAndHide(8, 0);
        hideInputMethod();
        this.ed_search.setText(this.stringHotList.get(i2));
        this.ed_search.setSelection(this.stringHotList.get(i2).length());
        this.page = 1;
        getData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRcy$2(View view) {
        if (this.ed_search.getText().toString().equals("")) {
            return;
        }
        showAndHide(8, 0);
        this.page = 1;
        getData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initSearch$5() {
        this.ed_search.clearFocus();
        this.ed_search.requestFocus();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initSearch$6(TextView textView, int i2, KeyEvent keyEvent) {
        if (i2 != 3) {
            return false;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) textView.getContext().getSystemService("input_method");
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(textView.getApplicationWindowToken(), 0);
        }
        if (!this.ed_search.getText().toString().equals("")) {
            showAndHide(8, 0);
            this.page = 1;
            getData();
        }
        return true;
    }

    public void getData() {
        String str;
        if (TextUtils.isEmpty(this.ed_search.getText().toString())) {
            return;
        }
        addHistoryContent(this.ed_search.getText().toString());
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_KEYWORD, "" + this.ed_search.getText().toString());
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.page);
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_ID, "" + this.topic_id);
        if (16 == getIntent().getExtras().getInt("module_type", 12)) {
            str = NetworkRequestsURL.getforumsearchDataApi;
            ajaxParams.put("group_id", "" + getIntent().getExtras().getString("group_id"));
            if (!"-1".equals(this.sort)) {
                ajaxParams.put("sort", this.sort);
            }
            if (!"-1".equals(this.group)) {
                ajaxParams.put("group", this.group);
            }
            if (!"-1".equals(this.type)) {
                ajaxParams.put("type", this.type);
            }
        } else {
            str = NetworkRequestsURL.msearchData;
        }
        YJYHttpUtils.post(this.mContext, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.CircleSearchNewActivity.10
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                CircleSearchNewActivity circleSearchNewActivity = CircleSearchNewActivity.this;
                int i2 = circleSearchNewActivity.page;
                if (i2 > 1) {
                    circleSearchNewActivity.page = i2 - 1;
                    circleSearchNewActivity.circleSearchContentAdapter.getLoadMoreModule().loadMoreFail();
                }
                if (CircleSearchNewActivity.this.hasEmptyView || CircleSearchNewActivity.this.circleSearchContentAdapter == null) {
                    return;
                }
                CircleSearchNewActivity.this.hasEmptyView = true;
                CircleSearchNewActivity.this.circleSearchContentAdapter.setEmptyView(R.layout.adapter_empty_txt_view);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass10) s2);
                try {
                    CircleSearchNewActivity.this.mCirclrListBean = (CirclrListBean) new Gson().fromJson(s2, CirclrListBean.class);
                    if (!CircleSearchNewActivity.this.mCirclrListBean.getCode().equals("200")) {
                        CircleSearchNewActivity circleSearchNewActivity = CircleSearchNewActivity.this;
                        int i2 = circleSearchNewActivity.page;
                        if (i2 > 1) {
                            circleSearchNewActivity.page = i2 - 1;
                            circleSearchNewActivity.circleSearchContentAdapter.getLoadMoreModule().loadMoreEnd();
                        }
                        CircleSearchNewActivity circleSearchNewActivity2 = CircleSearchNewActivity.this;
                        circleSearchNewActivity2.AlertToast(circleSearchNewActivity2.mCirclrListBean.getMessage());
                    } else if (CircleSearchNewActivity.this.mCirclrListBean.getData() == null || CircleSearchNewActivity.this.mCirclrListBean.getData().size() <= 0) {
                        CircleSearchNewActivity circleSearchNewActivity3 = CircleSearchNewActivity.this;
                        int i3 = circleSearchNewActivity3.page;
                        if (i3 > 1) {
                            circleSearchNewActivity3.page = i3 - 1;
                        } else {
                            circleSearchNewActivity3.data.clear();
                            CircleSearchNewActivity.this.circleSearchContentAdapter.setList(CircleSearchNewActivity.this.data);
                            CircleSearchNewActivity.this.circleSearchContentAdapter.getLoadMoreModule().checkDisableLoadMoreIfNotFullPage();
                        }
                        if (CircleSearchNewActivity.this.circleSearchContentAdapter != null) {
                            CircleSearchNewActivity.this.circleSearchContentAdapter.getLoadMoreModule().loadMoreEnd();
                        }
                    } else {
                        CircleSearchNewActivity circleSearchNewActivity4 = CircleSearchNewActivity.this;
                        if (circleSearchNewActivity4.page == 1) {
                            circleSearchNewActivity4.data.clear();
                            CircleSearchNewActivity circleSearchNewActivity5 = CircleSearchNewActivity.this;
                            circleSearchNewActivity5.data = circleSearchNewActivity5.mCirclrListBean.getData();
                            CircleSearchNewActivity.this.circleSearchContentAdapter.setSearchContent(CircleSearchNewActivity.this.ed_search.getText().toString());
                            CircleSearchNewActivity.this.circleSearchContentAdapter.setList(CircleSearchNewActivity.this.data);
                            CircleSearchNewActivity.this.circleSearchContentAdapter.getLoadMoreModule().checkDisableLoadMoreIfNotFullPage();
                        } else {
                            List<CirclrListBean.DataBean> data = circleSearchNewActivity4.mCirclrListBean.getData();
                            CircleSearchNewActivity.this.data.addAll(data);
                            CircleSearchNewActivity.this.circleSearchContentAdapter.addData((Collection) data);
                            CircleSearchNewActivity.this.circleSearchContentAdapter.getLoadMoreModule().loadMoreComplete();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    CircleSearchNewActivity circleSearchNewActivity6 = CircleSearchNewActivity.this;
                    int i4 = circleSearchNewActivity6.page;
                    if (i4 > 1) {
                        circleSearchNewActivity6.page = i4 - 1;
                        circleSearchNewActivity6.circleSearchContentAdapter.getLoadMoreModule().loadMoreFail();
                    }
                }
                if (!CircleSearchNewActivity.this.hasEmptyView && CircleSearchNewActivity.this.circleSearchContentAdapter != null) {
                    CircleSearchNewActivity.this.hasEmptyView = true;
                    CircleSearchNewActivity.this.circleSearchContentAdapter.setEmptyView(R.layout.adapter_empty_txt_view);
                }
                CircleSearchNewActivity.this.reltopicview.setVisibility(CircleSearchNewActivity.this.circleSearchContentAdapter.getData().isEmpty() ? 8 : 0);
            }
        });
    }

    public void getHeaderData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", "list");
        ajaxParams.put("cid", "-1");
        YJYHttpUtils.get(this, NetworkRequestsURL.mTopicListApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.CircleSearchNewActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    TopicListBean topicListBean = (TopicListBean) new Gson().fromJson(s2, TopicListBean.class);
                    if (!topicListBean.getCode().equals("200") || topicListBean.getData() == null || topicListBean.getData().size() <= 0) {
                        return;
                    }
                    CircleSearchNewActivity.this.initHeaderView(topicListBean.getData());
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getImageData(SpannableStringBuilder spannableString, TextView mTextView) {
        try {
            float textSize = mTextView.getPaint().getTextSize();
            Matcher matcher = Pattern.compile("\\[[^\\]]+\\]").matcher(spannableString.toString());
            while (matcher.find()) {
                String strGroup = matcher.group();
                if (strGroup.contains("http")) {
                    spannableString.setSpan(new StickerSpan(new URLImageParser(mTextView, this.mContext, (int) textSize).getDrawable(strGroup.substring(1, strGroup.length() - 1)), 1), matcher.start(), matcher.end(), 33);
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        mTextView.setText(spannableString);
    }

    public void getRankData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_KEYWORD, "" + this.ed_search.getText().toString());
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.page);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.msearchRankData, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.CircleSearchNewActivity.9
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                CircleSearchNewActivity.this.tv_hot_search.setVisibility(8);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass9) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optInt("code") != 200) {
                        CircleSearchNewActivity.this.AlertToast(jSONObject.optString("message"));
                        CircleSearchNewActivity.this.tv_hot_search.setVisibility(8);
                        return;
                    }
                    jSONObject.optJSONArray("data");
                    for (int i2 = 0; i2 < jSONObject.optJSONArray("data").length(); i2++) {
                        CircleSearchNewActivity.this.stringHotList.add(jSONObject.optJSONArray("data").getString(i2));
                    }
                    CircleSearchNewActivity.this.circleSearchHistoryAdapter.setNewInstance(CircleSearchNewActivity.this.stringHotList);
                    if (CircleSearchNewActivity.this.stringHotList.size() > 0) {
                        CircleSearchNewActivity.this.tv_hot_search.setVisibility(0);
                    } else {
                        CircleSearchNewActivity.this.tv_hot_search.setVisibility(8);
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
        YJYHttpUtils.post(this, NetworkRequestsURL.mCustomTopicListApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.CircleSearchNewActivity.7
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass7) s2);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.ed_search = (ClearEditText) findViewById(R.id.ed_search);
        this.searchview = (TextView) findViewById(R.id.searchview);
        this.reltopicview = (LinearLayout) findViewById(R.id.reltopicview);
        this.view1 = findViewById(R.id.view1);
        this.relopenview = (RelativeLayout) findViewById(R.id.relopenview);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.circleheader);
        this.circleheader = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this, 0, false));
        this.rc_search = (RecyclerView) findViewById(R.id.rc_search);
        this.rc_search_content = (RecyclerView) findViewById(R.id.rc_search_content);
        initRcy();
        initSearch();
        getRankData();
    }

    public void initHeaderView(final List<TopicListBean.DataDTO> data) {
        final BaseQuickAdapter<TopicListBean.DataDTO, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<TopicListBean.DataDTO, BaseViewHolder>(R.layout.layout_topic_header_item, data) { // from class: com.psychiatrygarden.activity.CircleSearchNewActivity.3
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NotNull BaseViewHolder baseViewHolder, TopicListBean.DataDTO dataDTO) {
                baseViewHolder.setText(R.id.name, dataDTO.getName());
                baseViewHolder.getView(R.id.name).setSelected(CircleSearchNewActivity.this.topic_id.equals(dataDTO.getId()));
            }
        };
        this.circleheader.setAdapter(baseQuickAdapter);
        this.circleheader.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.psychiatrygarden.activity.CircleSearchNewActivity.4
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
        View viewInflate = LayoutInflater.from(this).inflate(R.layout.layout_topic_header_item, (ViewGroup) null);
        viewInflate.setVisibility(4);
        baseQuickAdapter.addFooterView(viewInflate, -1, 0);
        baseQuickAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.y1
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i3) {
                this.f14201c.lambda$initHeaderView$3(data, baseQuickAdapter, baseQuickAdapter2, view, i3);
            }
        });
        this.relopenview.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.z1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14230c.lambda$initHeaderView$4(data, baseQuickAdapter, view);
            }
        });
    }

    public void isSelected(boolean comprehensiveSortingBoolean, boolean allSectionsBoolean, boolean allTypesBoolean) {
        this.comprehensiveSorting.setSelected(comprehensiveSortingBoolean);
        this.allSections.setSelected(allSectionsBoolean);
        this.allTypes.setSelected(allTypesBoolean);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(131072);
        getWindow().setSoftInputMode(5);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.chad.library.adapter.base.listener.OnItemClickListener
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        try {
            if (isLogin()) {
                if ("1".equals(this.data.get(position).getNo_access())) {
                    startActivity(new Intent(this, (Class<?>) MemberCenterActivity.class));
                    return;
                }
                if (this.data.get(position).getType().equals("3")) {
                    Intent intent = new Intent(this, (Class<?>) HandoutsInfoActivity.class);
                    intent.putExtra("cat_id", this.data.get(position).getCid());
                    intent.putExtra("article", this.data.get(position).getId());
                    intent.putExtra("json_path", this.data.get(position).getJson_path());
                    intent.putExtra("html_path", this.data.get(position).getHtml_path());
                    intent.putExtra("h5_path", this.data.get(position).getH5_path());
                    intent.putExtra("is_rich_text", this.data.get(position).getIs_rich_text());
                    intent.putExtra("index", 0);
                    startActivity(intent);
                    return;
                }
                Intent intent2 = new Intent(this.mContext, (Class<?>) CircleInfoActivity.class);
                intent2.putExtra("article_id", "" + this.data.get(position).getId());
                intent2.putExtra("group_id", "" + getIntent().getExtras().getString("group_id"));
                intent2.putExtra("commentCount", "" + this.data.get(position).getComment_count());
                intent2.putExtra("module_type", getIntent().getExtras().getInt("module_type", 12));
                startActivity(intent2);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.chad.library.adapter.base.listener.OnLoadMoreListener
    public void onLoadMore() {
        this.page++;
        getData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_circle_search_new);
        setTitle("搜索");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
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
