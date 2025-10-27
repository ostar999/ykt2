package com.psychiatrygarden.activity.courselist.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.activity.material.InformationPreviewAct;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.CourseMaterialsBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.BaseViewHolderUtilKt;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CourseDirectoryMaterialsFragment extends BaseFragment {
    private CourseMaterialsAdapter adapter;
    private String course_id;
    private CustomEmptyView emptyView;
    private SmartRefreshLayout refresh;
    private int page = 1;
    private final int PAGE_SIZE = 100;
    private boolean isLastPage = false;

    public class CourseMaterialsAdapter extends BaseQuickAdapter<CourseMaterialsBean, BaseViewHolder> {
        public CourseMaterialsAdapter() {
            super(R.layout.item_course_directory_materials);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$combineContainView$0(CourseMaterialsBean courseMaterialsBean, View view) {
            SharePreferencesUtils.writeStrConfig(CommonParameter.LIVE_COURSE_ID, CourseDirectoryMaterialsFragment.this.course_id, CourseDirectoryMaterialsFragment.this.getActivity());
            InformationPreviewAct.newIntent(getContext(), courseMaterialsBean.getId(), courseMaterialsBean.getUrl(), false, true, courseMaterialsBean.getTitle());
        }

        public void combineContainView(BaseViewHolder baseViewHolder, final CourseMaterialsBean itemData) {
            ImageView imageView = (ImageView) baseViewHolder.getView(R.id.itemMaterialsImg);
            TextView textView = (TextView) baseViewHolder.getView(R.id.itemMaterialsTitle);
            ((TextView) baseViewHolder.getView(R.id.itemMaterialsSize)).setText(itemData.getSize_info());
            String type = itemData.getType();
            String title = itemData.getTitle();
            boolean zIsEmpty = TextUtils.isEmpty(type);
            int i2 = R.drawable.ic_ppt;
            if (!zIsEmpty) {
                Objects.requireNonNull(type);
                switch (type) {
                    case "1":
                        if (TextUtils.isEmpty(title) || title.endsWith(".doc")) {
                            textView.setText(title);
                        } else {
                            textView.setText(title + ".doc");
                        }
                        i2 = R.drawable.ic_word;
                        break;
                    case "2":
                        if (TextUtils.isEmpty(title) || title.endsWith(".docx")) {
                            textView.setText(title);
                        } else {
                            textView.setText(title + ".docx");
                        }
                        i2 = R.drawable.ic_word;
                        break;
                    case "3":
                        if (TextUtils.isEmpty(title) || title.endsWith(".pdf")) {
                            textView.setText(title);
                        } else {
                            textView.setText(title + ".pdf");
                        }
                        i2 = R.drawable.ic_pdf;
                        break;
                    case "4":
                        if (!TextUtils.isEmpty(title) && !title.endsWith(".ppt")) {
                            textView.setText(title + ".ppt");
                            break;
                        } else {
                            textView.setText(title);
                            break;
                        }
                        break;
                    case "5":
                        if (!TextUtils.isEmpty(title) && !title.endsWith(".pptx")) {
                            textView.setText(title + ".pptx");
                            break;
                        } else {
                            textView.setText(title);
                            break;
                        }
                }
            }
            imageView.setImageResource(i2);
            BaseViewHolderUtilKt.getCustomerItemView(baseViewHolder).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.u0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f12046c.lambda$combineContainView$0(itemData, view);
                }
            });
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NotNull BaseViewHolder baseViewHolder, CourseMaterialsBean item) {
            combineContainView(baseViewHolder, item);
        }
    }

    private void getListData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("course_id", this.course_id);
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.page);
        ajaxParams.put("page_size", "100");
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.courseJIANG_YI, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryMaterialsFragment.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                CourseDirectoryMaterialsFragment.this.refresh.finishRefresh(false);
                CourseDirectoryMaterialsFragment.this.refresh.setNoMoreData(true);
                CourseDirectoryMaterialsFragment.this.setEmptyView(1);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass1) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (CourseDirectoryMaterialsFragment.this.page == 1) {
                        CourseDirectoryMaterialsFragment.this.refresh.finishRefresh(true);
                    } else {
                        CourseDirectoryMaterialsFragment.this.refresh.finishLoadMore(true);
                    }
                    if (!"200".equals(jSONObject.optString("code"))) {
                        CourseDirectoryMaterialsFragment.this.refresh.finishRefresh(false);
                        CourseDirectoryMaterialsFragment.this.refresh.setNoMoreData(true);
                        CourseDirectoryMaterialsFragment.this.setEmptyView(1);
                        return;
                    }
                    List list = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<CourseMaterialsBean>>() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryMaterialsFragment.1.1
                    }.getType());
                    CourseDirectoryMaterialsFragment.this.isLastPage = list != null && list.size() < 100;
                    if (CourseDirectoryMaterialsFragment.this.page == 1) {
                        CourseDirectoryMaterialsFragment.this.adapter.setList(list);
                        if (list == null || list.isEmpty()) {
                            CourseDirectoryMaterialsFragment.this.refresh.setNoMoreData(true);
                            CourseDirectoryMaterialsFragment.this.setEmptyView(0);
                        }
                    } else if (list == null || list.isEmpty()) {
                        CourseDirectoryMaterialsFragment.this.refresh.setNoMoreData(true);
                    } else {
                        CourseDirectoryMaterialsFragment.this.adapter.addData((Collection) list);
                    }
                    if (CourseDirectoryMaterialsFragment.this.isLastPage) {
                        CourseDirectoryMaterialsFragment.this.refresh.setNoMoreData(true);
                    }
                } catch (Exception e2) {
                    Log.d("Error---", "error: " + e2.getMessage());
                    CourseDirectoryMaterialsFragment.this.refresh.finishRefresh(false);
                    CourseDirectoryMaterialsFragment.this.refresh.setNoMoreData(true);
                    CourseDirectoryMaterialsFragment.this.setEmptyView(1);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(View view) {
        getListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(RefreshLayout refreshLayout) {
        this.page = 1;
        getListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2(RefreshLayout refreshLayout) {
        this.page++;
        getListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setEmptyView(int type) {
        this.adapter.setList(new ArrayList());
        this.adapter.setEmptyView(this.emptyView);
        if (type == 0) {
            this.emptyView.uploadEmptyViewResUi();
        } else {
            this.emptyView.setLoadFileResUi(getActivity());
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_course_directory_materials;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.course_id = arguments.getString("course_id");
        }
        this.refresh = (SmartRefreshLayout) holder.get(R.id.combineMaterialsRefresh);
        RecyclerView recyclerView = (RecyclerView) holder.get(R.id.recyclerCombineMaterials);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CourseMaterialsAdapter courseMaterialsAdapter = new CourseMaterialsAdapter();
        this.adapter = courseMaterialsAdapter;
        recyclerView.setAdapter(courseMaterialsAdapter);
        CustomEmptyView customEmptyView = new CustomEmptyView(getActivity(), 0, "");
        this.emptyView = customEmptyView;
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.r0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12027c.lambda$initViews$0(view);
            }
        });
        this.refresh.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.s0
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f12035c.lambda$initViews$1(refreshLayout);
            }
        });
        this.refresh.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.t0
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                this.f12041c.lambda$initViews$2(refreshLayout);
            }
        });
        getListData();
    }
}
