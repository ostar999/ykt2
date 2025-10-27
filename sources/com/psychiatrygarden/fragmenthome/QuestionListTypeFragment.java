package com.psychiatrygarden.fragmenthome;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.QuestionDetailActivity;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.ContactCustomerBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class QuestionListTypeFragment extends BaseFragment {
    public BaseQuickAdapter<ContactCustomerBean.DataDTO, BaseViewHolder> adapterQuestion;
    public String category_id = "";
    public RecyclerView recyid;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        Intent intent = new Intent(getActivity(), (Class<?>) QuestionDetailActivity.class);
        intent.putExtra("id", "" + this.adapterQuestion.getData().get(i2).getId());
        startActivity(intent);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_question_list_type;
    }

    public void getQuestionList() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("category_id", this.category_id);
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "1");
        ajaxParams.put("limit", "50");
        YJYHttpUtils.get(getActivity(), NetworkRequestsURL.missueApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.QuestionListTypeFragment.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ProjectApp.instance().hideDialogWindow();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                ProjectApp.instance().showDialogWindow();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    ProjectApp.instance().hideDialogWindow();
                    ContactCustomerBean contactCustomerBean = (ContactCustomerBean) new Gson().fromJson(s2, ContactCustomerBean.class);
                    if (!contactCustomerBean.getCode().equals("200")) {
                        ToastUtil.shortToast(QuestionListTypeFragment.this.getActivity(), contactCustomerBean.getMessage() + "");
                    } else if (contactCustomerBean.getData().size() > 0) {
                        QuestionListTypeFragment.this.adapterQuestion.setList(contactCustomerBean.getData());
                    } else {
                        ToastUtil.shortToast(QuestionListTypeFragment.this.getActivity(), contactCustomerBean.getMessage());
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.category_id = getArguments().getString("category_id");
        RecyclerView recyclerView = (RecyclerView) holder.get(R.id.recyid);
        this.recyid = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        BaseQuickAdapter<ContactCustomerBean.DataDTO, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<ContactCustomerBean.DataDTO, BaseViewHolder>(R.layout.layout_contact_question) { // from class: com.psychiatrygarden.fragmenthome.QuestionListTypeFragment.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NonNull BaseViewHolder holder2, ContactCustomerBean.DataDTO item) {
                ((TextView) holder2.findView(R.id.questionTitle)).setText(item.getTitle());
            }
        };
        this.adapterQuestion = baseQuickAdapter;
        this.recyid.setAdapter(baseQuickAdapter);
        this.adapterQuestion.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.fragmenthome.qb
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                this.f15942c.lambda$initViews$0(baseQuickAdapter2, view, i2);
            }
        });
        getQuestionList();
    }
}
