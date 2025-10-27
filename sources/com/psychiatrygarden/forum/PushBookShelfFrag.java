package com.psychiatrygarden.forum;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.psychiatrygarden.bean.BookShelfBean;
import com.psychiatrygarden.bean.PushBookData;
import com.psychiatrygarden.forum.PushBookAdp;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.LocalBroadcastManager;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yikaobang.yixue.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class PushBookShelfFrag extends Fragment implements OnRefreshLoadMoreListener {
    private CustomEmptyView emptyView;
    private PushBookAdp mAdapter;
    private RecyclerView mRecycler;
    private SmartRefreshLayout mRefresh;
    private int page = 1;
    private String mSearchKey = "";
    private boolean isShelf = true;
    private boolean isLoadData = false;
    private List<String> mChooseBookIds = new ArrayList();

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onViewCreated$0(View view) {
        loadData();
    }

    private void loadData() {
        String str;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, this.page + "");
        ajaxParams.put("page_size", com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SHARE_TO_QQ);
        if (TextUtils.isEmpty(this.mSearchKey)) {
            str = NetworkRequestsURL.bookList;
            if (!this.isShelf) {
                str = NetworkRequestsURL.readHistoryList;
            }
        } else {
            str = NetworkRequestsURL.proBookStackList;
            ajaxParams.put("title", this.mSearchKey);
        }
        YJYHttpUtils.post(getActivity(), str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.forum.PushBookShelfFrag.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                PushBookShelfFrag.this.mRefresh.finishRefresh();
                if (PushBookShelfFrag.this.page == 1) {
                    PushBookShelfFrag.this.emptyView.setLoadFileResUi(PushBookShelfFrag.this.getActivity());
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                PushBookShelfFrag.this.emptyView.showEmptyView();
                PushBookShelfFrag.this.mRefresh.finishRefresh();
                PushBookShelfFrag.this.isLoadData = true;
                try {
                    BookShelfBean bookShelfBean = (BookShelfBean) new Gson().fromJson(s2, BookShelfBean.class);
                    if (bookShelfBean.getCode().equals("200")) {
                        if (bookShelfBean.getData() == null || bookShelfBean.getData().isEmpty()) {
                            if (PushBookShelfFrag.this.page == 1) {
                                PushBookShelfFrag.this.mAdapter.setList(new ArrayList());
                                PushBookShelfFrag.this.mAdapter.setEmptyView(PushBookShelfFrag.this.emptyView);
                                return;
                            }
                            return;
                        }
                        ArrayList arrayList = new ArrayList();
                        for (int i2 = 0; i2 < bookShelfBean.getData().size(); i2++) {
                            PushBookData pushBookData = new PushBookData();
                            pushBookData.setId(bookShelfBean.getData().get(i2).getBookId());
                            pushBookData.setThumbnail(bookShelfBean.getData().get(i2).getThumbnail());
                            pushBookData.setGrade(bookShelfBean.getData().get(i2).getGrade());
                            pushBookData.setAuthor(bookShelfBean.getData().get(i2).getAuthor());
                            pushBookData.setTitle(bookShelfBean.getData().get(i2).getBookTitle());
                            pushBookData.setDescribe(bookShelfBean.getData().get(i2).getDescribe());
                            if (PushBookShelfFrag.this.mChooseBookIds.contains(pushBookData.getId())) {
                                pushBookData.setSelected(true);
                            }
                            arrayList.add(pushBookData);
                        }
                        if (PushBookShelfFrag.this.page == 1) {
                            PushBookShelfFrag.this.mAdapter.setSearchContent(PushBookShelfFrag.this.mSearchKey);
                            PushBookShelfFrag.this.mAdapter.setList(arrayList);
                            if (bookShelfBean.getData().size() < 10) {
                                PushBookShelfFrag.this.mRefresh.finishLoadMoreWithNoMoreData();
                                return;
                            }
                            return;
                        }
                        if (bookShelfBean.getData().isEmpty()) {
                            return;
                        }
                        PushBookShelfFrag.this.mAdapter.addData((Collection) arrayList);
                        if (bookShelfBean.getData().size() < 10) {
                            PushBookShelfFrag.this.mRefresh.finishLoadMoreWithNoMoreData();
                        } else {
                            PushBookShelfFrag.this.mRefresh.finishLoadMore();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    if (PushBookShelfFrag.this.page == 1) {
                        PushBookShelfFrag.this.emptyView.setLoadFileResUi(PushBookShelfFrag.this.getActivity());
                    }
                }
            }
        });
    }

    public static PushBookShelfFrag newInstance(boolean isShelf, List<String> bookIdList) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isShelf", isShelf);
        bundle.putSerializable("bookIdList", (Serializable) bookIdList);
        PushBookShelfFrag pushBookShelfFrag = new PushBookShelfFrag();
        pushBookShelfFrag.setArguments(bundle);
        return pushBookShelfFrag;
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.isShelf = getArguments().getBoolean("isShelf");
        this.mChooseBookIds = (List) getArguments().getSerializable("bookIdList");
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewInflate = inflater.inflate(R.layout.frag_push_book, container, false);
        this.mRecycler = (RecyclerView) viewInflate.findViewById(R.id.recyclerview);
        this.mRefresh = (SmartRefreshLayout) viewInflate.findViewById(R.id.refresh);
        return viewInflate;
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        this.page++;
        loadData();
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        this.page = 1;
        loadData();
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (this.mChooseBookIds == null) {
            this.mChooseBookIds = new ArrayList();
        }
        this.mAdapter = new PushBookAdp(false, this.mChooseBookIds);
        this.mRefresh.setOnRefreshLoadMoreListener(this);
        CustomEmptyView customEmptyView = new CustomEmptyView(getActivity(), 0, "");
        this.emptyView = customEmptyView;
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.forum.o0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f15388c.lambda$onViewCreated$0(view2);
            }
        });
        this.mAdapter.setEmptyView(this.emptyView);
        this.mRecycler.setAdapter(this.mAdapter);
        loadData();
        this.mAdapter.setOnItemChoosedLisenter(new PushBookAdp.OnChooseItemLisenter() { // from class: com.psychiatrygarden.forum.PushBookShelfFrag.1
            @Override // com.psychiatrygarden.forum.PushBookAdp.OnChooseItemLisenter
            public void onDelChoosed(int pos, PushBookData item) {
            }

            @Override // com.psychiatrygarden.forum.PushBookAdp.OnChooseItemLisenter
            public void onItemChoosed(int pos, PushBookData item) {
                if (PushBookShelfFrag.this.mChooseBookIds.size() < 10) {
                    item.setSelected(!item.isSelected());
                    PushBookShelfFrag.this.mAdapter.notifyItemChanged(pos, 1);
                    Intent intent = new Intent("updateBottomUi");
                    intent.putExtra("book", item);
                    LocalBroadcastManager.getInstance(PushBookShelfFrag.this.requireContext()).sendBroadcast(intent);
                    return;
                }
                if (!item.isSelected()) {
                    ToastUtil.shortToast(PushBookShelfFrag.this.getContext(), "最多只能添加10本书籍");
                    return;
                }
                item.setSelected(!item.isSelected());
                PushBookShelfFrag.this.mAdapter.notifyItemChanged(pos, 1);
                Intent intent2 = new Intent("updateBottomUi");
                intent2.putExtra("book", item);
                LocalBroadcastManager.getInstance(PushBookShelfFrag.this.requireContext()).sendBroadcast(intent2);
            }
        });
    }

    public void setmSearchKey(String mSearchKey) {
        this.mSearchKey = mSearchKey;
        this.page = 1;
        onRefresh(this.mRefresh);
    }

    public void updateChoosedItem(List<String> bookIds) {
        if (!this.isLoadData || bookIds == null) {
            return;
        }
        for (int i2 = 0; i2 < this.mAdapter.getData().size(); i2++) {
            if (bookIds.contains(this.mAdapter.getData().get(i2).getId())) {
                this.mAdapter.getData().get(i2).setSelected(true);
            } else {
                this.mAdapter.getData().get(i2).setSelected(false);
            }
        }
        this.mAdapter.notifyDataSetChanged();
    }
}
