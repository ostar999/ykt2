package com.psychiatrygarden.widget.english;

import android.content.Context;
import android.graphics.Color;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.VerticalRecyclerView;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.online.bean.QuestionDetailBean;
import com.psychiatrygarden.activity.online.bean.event.NoteEventBean;
import com.psychiatrygarden.adapter.QuestionNoteListAdapter;
import com.psychiatrygarden.bean.QuestionNoteBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.QuestionDataRequest;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.interfaceclass.onDialogNoteListener;
import com.psychiatrygarden.utils.AliyunEvent;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.DividerItemDecoration;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.DeleteNotePop;
import com.psychiatrygarden.widget.DialogNoteInput;
import com.psychiatrygarden.widget.PopNoteListMenu;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class PopNoteList extends BottomPopupView implements View.OnClickListener, OnItemChildClickListener, PopNoteListMenu.OnClickBtnListener, QuestionDataCallBack<String>, OnLoadMoreListener {
    private String module_type;
    private int page;
    private final List<QuestionNoteBean> questionNoteBeans;
    private QuestionNoteListAdapter questionNoteListAdapter;
    private String question_id;
    private VerticalRecyclerView recyclerView;

    public PopNoteList(@NonNull Context context, String module_type, String question_id) {
        super(context);
        this.page = 1;
        this.questionNoteBeans = new ArrayList();
        this.question_id = question_id;
        this.module_type = module_type;
    }

    private void getNote(int page) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", this.question_id);
        ajaxParams.put("module_type", this.module_type);
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, page + "");
        ajaxParams.put(DatabaseManager.SIZE, "20");
        QuestionDataRequest.getIntance(getContext()).getNote(ajaxParams, this);
    }

    private void initRv() {
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 0, 2, Color.parseColor(SkinManager.getCurrentSkinType(getContext()) == 1 ? "#2E3241" : "#E6E6E6")));
        QuestionNoteListAdapter questionNoteListAdapter = new QuestionNoteListAdapter(R.layout.item_question_note_list, this.questionNoteBeans);
        this.questionNoteListAdapter = questionNoteListAdapter;
        questionNoteListAdapter.addChildClickViewIds(R.id.iv_note_menu);
        this.questionNoteListAdapter.setOnItemChildClickListener(this);
        this.questionNoteListAdapter.getLoadMoreModule().setOnLoadMoreListener(this);
        this.recyclerView.setAdapter(this.questionNoteListAdapter);
        this.questionNoteListAdapter.getLoadMoreModule().setEnableLoadMore(this.questionNoteBeans.size() >= 20);
        this.questionNoteListAdapter.setEmptyView(R.layout.adapter_empty_view);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$0(QuestionNoteBean questionNoteBean) {
        this.page = 1;
        getNote(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPopNoteDelete$2(int i2) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", this.question_id);
        ajaxParams.put("module_type", this.module_type);
        if (i2 < this.questionNoteBeans.size()) {
            ajaxParams.put("id", this.questionNoteBeans.get(i2).getId());
            QuestionDataRequest.getIntance(getContext()).cleanNote(ajaxParams, this);
            String str = "[\"" + this.question_id + "\"]";
            String str2 = "[\"" + this.questionNoteBeans.get(i2).getContent() + "\"]";
            QuestionDetailBean questionDetailBean = new QuestionDetailBean();
            questionDetailBean.setModule_type(this.module_type);
            questionDetailBean.setUnit_title(ProjectApp.unit_title);
            questionDetailBean.setExam_title(ProjectApp.exam_title);
            questionDetailBean.setIdentity_title(ProjectApp.identity_title);
            questionDetailBean.setIdentity_id(ProjectApp.identity_id);
            questionDetailBean.setChapter_title(ProjectApp.chapter_title);
            questionDetailBean.setChapter_id(ProjectApp.chapter_id);
            questionDetailBean.setChapter_parent_title(ProjectApp.chapter_parent_title);
            questionDetailBean.setChapter_parent_id(ProjectApp.chapter_parent_id);
            String json = ProjectApp.gson.toJson(questionDetailBean);
            AliyunEvent aliyunEvent = AliyunEvent.DeleNote;
            CommonUtil.addLog(aliyunEvent.getKey(), aliyunEvent.getValue(), System.currentTimeMillis() + "", "", str, str2, json, "2");
            this.questionNoteBeans.remove(i2);
            this.questionNoteListAdapter.notifyItemRemoved(i2);
            if (this.questionNoteBeans.size() == 0) {
                EventBus.getDefault().post(new NoteEventBean("", false, this.question_id));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPopNoteEdit$1(int i2, QuestionNoteBean questionNoteBean) {
        this.questionNoteBeans.get(i2).setContent(questionNoteBean.getContent());
        this.questionNoteBeans.get(i2).setCtime(questionNoteBean.getCtime());
        this.questionNoteBeans.get(i2).setImg(questionNoteBean.getImg());
        this.questionNoteListAdapter.notifyItemChanged(i2);
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public void dismiss() {
        super.dismiss();
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public void doAfterDismiss() {
        super.doAfterDismiss();
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.pop_note_list_bottom;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getMaxHeight() {
        return XPopupUtils.getAppHeight(getContext()) - 10;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        int id = v2.getId();
        if (id == R.id.iv_note_close) {
            dismiss();
        } else {
            if (id != R.id.tv_add_note) {
                return;
            }
            new DialogNoteInput(getContext(), this.module_type, this.question_id, new onDialogNoteListener() { // from class: com.psychiatrygarden.widget.english.j
                @Override // com.psychiatrygarden.interfaceclass.onDialogNoteListener
                public final void onclickStringBack(QuestionNoteBean questionNoteBean) {
                    this.f16460a.lambda$onClick$0(questionNoteBean);
                }
            }).show();
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        ImageView imageView = (ImageView) findViewById(R.id.iv_note_close);
        this.recyclerView = (VerticalRecyclerView) findViewById(R.id.recyclerView);
        ((TextView) findViewById(R.id.tv_add_note)).setOnClickListener(this);
        imageView.setOnClickListener(this);
        getNote(this.page);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onDismiss() {
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
    }

    @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
    public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
        if (view.getId() == R.id.iv_note_menu) {
            new XPopup.Builder(getContext()).hasShadowBg(Boolean.FALSE).atView(view).asCustom(new PopNoteListMenu(getContext(), position, this)).show();
        }
    }

    @Override // com.chad.library.adapter.base.listener.OnLoadMoreListener
    public void onLoadMore() {
        int i2 = this.page + 1;
        this.page = i2;
        getNote(i2);
    }

    @Override // com.psychiatrygarden.widget.PopNoteListMenu.OnClickBtnListener
    public void onPopNoteCopy(int position) {
        try {
            ((ClipboardManager) getContext().getSystemService("clipboard")).setText(this.questionNoteBeans.get(position).getContent());
            NewToast.showShort(getContext(), "复制成功", 0).show();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.widget.PopNoteListMenu.OnClickBtnListener
    public void onPopNoteDelete(final int position) {
        new XPopup.Builder(getContext()).asCustom(new DeleteNotePop(getContext(), new OnConfirmListener() { // from class: com.psychiatrygarden.widget.english.h
            @Override // com.lxj.xpopup.interfaces.OnConfirmListener
            public final void onConfirm() {
                this.f16456a.lambda$onPopNoteDelete$2(position);
            }
        })).show();
    }

    @Override // com.psychiatrygarden.widget.PopNoteListMenu.OnClickBtnListener
    public void onPopNoteEdit(final int position) {
        if (this.questionNoteBeans.size() > 0) {
            new DialogNoteInput(getContext(), this.module_type, this.question_id, this.questionNoteBeans.get(position), new onDialogNoteListener() { // from class: com.psychiatrygarden.widget.english.i
                @Override // com.psychiatrygarden.interfaceclass.onDialogNoteListener
                public final void onclickStringBack(QuestionNoteBean questionNoteBean) {
                    this.f16458a.lambda$onPopNoteEdit$1(position, questionNoteBean);
                }
            }).show();
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onShow() {
        super.onShow();
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String s2, String requstUrl) {
        try {
            JSONObject jSONObject = new JSONObject(s2);
            if (!requstUrl.equals(NetworkRequestsURL.getNoteURL)) {
                if (requstUrl.equals(NetworkRequestsURL.cleanNoteURL) && this.questionNoteBeans.isEmpty()) {
                    EventBus.getDefault().post(new NoteEventBean("", false, this.question_id));
                    dismiss();
                    return;
                }
                return;
            }
            if (!jSONObject.optString("code").equals("200")) {
                if (this.page != 1) {
                    this.questionNoteListAdapter.getLoadMoreModule().loadMoreFail();
                }
                ToastUtil.shortToast(getContext(), jSONObject.optString("message"));
                return;
            }
            List list = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<QuestionNoteBean>>() { // from class: com.psychiatrygarden.widget.english.PopNoteList.1
            }.getType());
            if (this.page == 1) {
                this.questionNoteBeans.clear();
                this.questionNoteBeans.addAll(list);
                initRv();
                this.questionNoteListAdapter.getLoadMoreModule().checkDisableLoadMoreIfNotFullPage();
                doShowAnimation();
                return;
            }
            if (list.size() <= 0) {
                this.questionNoteListAdapter.getLoadMoreModule().loadMoreEnd();
                return;
            }
            this.questionNoteBeans.addAll(list);
            this.questionNoteListAdapter.notifyDataSetChanged();
            this.questionNoteListAdapter.getLoadMoreModule().loadMoreComplete();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
