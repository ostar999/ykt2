package com.psychiatrygarden.activity.ebook;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.LoginActivity;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.BookStackBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.SearchBookStoreEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.widget.ClearEditText;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yikaobang.yixue.R;
import com.ykb.ebook.activity.ReadBookActivity;
import java.util.ArrayList;
import java.util.Collection;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;

/* loaded from: classes5.dex */
public class BookStoreFragment extends BaseFragment implements OnRefreshLoadMoreListener {
    private CustomEmptyView emptyView;
    private boolean enableSearch;
    private ImageView ivBookShelf;
    private ProjectBookStoreAdp mAdapter;
    private ClearEditText mEtSearch;
    private SmartRefreshLayout mRefreshLayout;
    private int page = 1;
    private RecyclerView rvList;
    private String searchWord;
    private boolean showTopSearch;
    private TextView tvSearch;

    public static BookStoreFragment getInstance(boolean showTopSearch, boolean enableSearch) {
        BookStoreFragment bookStoreFragment = new BookStoreFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("showTopSearch", showTopSearch);
        bundle.putBoolean("enableSearch", enableSearch);
        bookStoreFragment.setArguments(bundle);
        return bookStoreFragment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(View view) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(View view) {
        startActivity(new Intent(view.getContext(), (Class<?>) BookStoreSearchActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2(View view) {
        if (this.mEtSearch.getText().toString().trim().length() > 0) {
            this.mRefreshLayout.autoRefresh();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initViews$3(TextView textView, int i2, KeyEvent keyEvent) {
        if (i2 != 3) {
            return false;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) textView.getContext().getSystemService("input_method");
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(textView.getApplicationWindowToken(), 0);
        }
        if (!this.mEtSearch.getText().toString().equals("")) {
            this.page = 1;
            loadData();
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$4(View view) {
        loadData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$5(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        if (!UserConfig.isLogin()) {
            startActivity(new Intent(getActivity(), (Class<?>) LoginActivity.class));
            return;
        }
        BookStackBean.BookStackData item = this.mAdapter.getItem(i2);
        startActivity(ReadBookActivity.INSTANCE.newIntent(getActivity(), item.getId(), UserConfig.getUserId(), SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1"), UserConfig.getInstance().getUser().getAdmin(), UserConfig.getInstance().getUser().getAvatar(), UserConfig.getInstance().getUser().getToken(), UserConfig.getInstance().getUser().getSecret()));
    }

    private void loadData() {
        AjaxParams ajaxParams = new AjaxParams();
        if (TextUtils.isEmpty(this.mEtSearch.getText().toString().trim())) {
            this.mAdapter.setList(new ArrayList());
            this.mRefreshLayout.finishRefresh();
            return;
        }
        ajaxParams.put("title", this.mEtSearch.getText().toString().trim());
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, this.page + "");
        ajaxParams.put("page_size", "20");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.proBookStackList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ebook.BookStoreFragment.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                BookStoreFragment.this.mRefreshLayout.finishRefresh();
                if (BookStoreFragment.this.page == 1) {
                    BookStoreFragment.this.mAdapter.getEmptyLayout().setVisibility(0);
                    BookStoreFragment.this.emptyView.setLoadFileResUi(BookStoreFragment.this.getActivity());
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                BookStoreFragment.this.emptyView.showEmptyView();
                BookStoreFragment.this.mRefreshLayout.finishRefresh();
                try {
                    BookStackBean bookStackBean = (BookStackBean) new Gson().fromJson(s2, BookStackBean.class);
                    if (bookStackBean.getCode().equals("200")) {
                        if (bookStackBean.getData() == null || bookStackBean.getData().isEmpty()) {
                            if (BookStoreFragment.this.page == 1) {
                                BookStoreFragment.this.mAdapter.setList(new ArrayList());
                                BookStoreFragment.this.mAdapter.getEmptyLayout().setVisibility(0);
                            }
                            BookStoreFragment.this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                            return;
                        }
                        if (BookStoreFragment.this.page == 1) {
                            BookStoreFragment.this.mAdapter.setSearchContent(BookStoreFragment.this.mEtSearch.getText().toString());
                            BookStoreFragment.this.mAdapter.setList(bookStackBean.getData());
                            if (bookStackBean.getData().size() < 20) {
                                BookStoreFragment.this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                                return;
                            }
                            return;
                        }
                        if (bookStackBean.getData().isEmpty()) {
                            return;
                        }
                        BookStoreFragment.this.mAdapter.addData((Collection) bookStackBean.getData());
                        if (bookStackBean.getData().size() < 20) {
                            BookStoreFragment.this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                        } else {
                            BookStoreFragment.this.mRefreshLayout.finishLoadMore();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    if (BookStoreFragment.this.page == 1) {
                        BookStoreFragment.this.emptyView.setLoadFileResUi(BookStoreFragment.this.getActivity());
                        BookStoreFragment.this.mAdapter.getEmptyLayout().setVisibility(0);
                    }
                }
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_book_store;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.showTopSearch = arguments.getBoolean("showTopSearch", true);
            this.enableSearch = arguments.getBoolean("enableSearch", true);
        }
        holder.get(R.id.iv_actionbar_back).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ebook.q
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12301c.lambda$initViews$0(view);
            }
        });
        if (!this.showTopSearch) {
            holder.get(R.id.titlebar_layout).setVisibility(8);
        }
        this.mRefreshLayout = (SmartRefreshLayout) holder.get(R.id.refresh);
        this.rvList = (RecyclerView) holder.get(R.id.rvList);
        ImageView imageView = (ImageView) holder.get(R.id.iv_book_shelf);
        this.ivBookShelf = imageView;
        imageView.setVisibility(8);
        TextView textView = (TextView) holder.get(R.id.tv_search_area);
        this.mEtSearch = (ClearEditText) holder.get(R.id.et_search);
        this.tvSearch = (TextView) holder.get(R.id.tv_search);
        this.mEtSearch.setVisibility(this.enableSearch ? 0 : 8);
        this.tvSearch.setVisibility(this.enableSearch ? 0 : 8);
        textView.setVisibility(!this.enableSearch ? 0 : 8);
        showSoftInput(this.mEtSearch);
        if (this.showTopSearch && !this.enableSearch) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ebook.r
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f12302c.lambda$initViews$1(view);
                }
            });
        }
        if (this.showTopSearch && this.enableSearch) {
            this.tvSearch.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ebook.s
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f12303c.lambda$initViews$2(view);
                }
            });
            this.mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.psychiatrygarden.activity.ebook.t
                @Override // android.widget.TextView.OnEditorActionListener
                public final boolean onEditorAction(TextView textView2, int i2, KeyEvent keyEvent) {
                    return this.f12304c.lambda$initViews$3(textView2, i2, keyEvent);
                }
            });
        }
        this.mRefreshLayout.setOnRefreshLoadMoreListener(this);
        ProjectBookStoreAdp projectBookStoreAdp = new ProjectBookStoreAdp();
        this.mAdapter = projectBookStoreAdp;
        this.rvList.setAdapter(projectBookStoreAdp);
        CustomEmptyView customEmptyView = new CustomEmptyView(getActivity(), 0, "");
        this.emptyView = customEmptyView;
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ebook.u
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12305c.lambda$initViews$4(view);
            }
        });
        this.mAdapter.setEmptyView(this.emptyView);
        this.mAdapter.getEmptyLayout().setVisibility(8);
        this.mAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.ebook.v
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f12306c.lambda$initViews$5(baseQuickAdapter, view, i2);
            }
        });
    }

    @Subscribe
    public void onEventMainThread(SearchBookStoreEvent event) {
        String keyword = event.getKeyword();
        this.searchWord = keyword;
        if (TextUtils.isEmpty(keyword)) {
            return;
        }
        this.mRefreshLayout.autoRefresh();
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (this.mEtSearch.getText().toString().trim().length() <= 0) {
            this.mRefreshLayout.finishLoadMore();
        } else {
            this.page++;
            loadData();
        }
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (this.mEtSearch.getText().toString().trim().length() <= 0) {
            this.mRefreshLayout.finishRefresh();
        } else {
            this.page = 1;
            loadData();
        }
    }
}
