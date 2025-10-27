package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.text.ClipboardManager;
import android.text.SpannableStringBuilder;
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
import com.lxj.xpopup.core.DrawerPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.VerticalRecyclerView;
import com.psychiatrygarden.activity.online.bean.event.NoteEventBean;
import com.psychiatrygarden.adapter.QuestionNoteListAdapter;
import com.psychiatrygarden.bean.QuestionNoteBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.UpdateVideoCommentNote;
import com.psychiatrygarden.event.VideoStateEvent;
import com.psychiatrygarden.http.CourseDataRequest;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.interfaceclass.NoteListener;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.interfaceclass.onDialogNoteListener;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CommonTwoBtnDialog;
import com.psychiatrygarden.widget.PopNoteListMenu;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class PopNoteCourseListLandScape extends DrawerPopupView implements View.OnClickListener, OnItemChildClickListener, PopNoteListMenu.OnClickBtnListener, QuestionDataCallBack<String>, OnLoadMoreListener {
    private static final int preLoadDataThreshold = 3;
    private final boolean fromVideo;
    private boolean hasMoreData;
    private boolean isLoading;
    private String mCourseId;
    private final List<QuestionNoteBean> mNoteList;
    private String mVideoId;
    private String moduleType;
    private final NoNoteListener noNoteListener;
    private int page;
    private QuestionNoteListAdapter questionNoteListAdapter;
    private VerticalRecyclerView recyclerView;
    private final String screenShotFilePath;

    public interface NoNoteListener {
        void noNote();
    }

    public PopNoteCourseListLandScape(@NonNull Context context, String video_id, String course_id, NoNoteListener noNoteListener, boolean fromVideo, String screenShotFilePath) {
        super(context);
        this.page = 1;
        this.mNoteList = new ArrayList();
        this.isLoading = true;
        this.screenShotFilePath = screenShotFilePath;
        this.fromVideo = fromVideo;
        this.mCourseId = course_id;
        this.mVideoId = video_id;
        this.noNoteListener = noNoteListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getNote(int page) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("video_id", this.mVideoId);
        ajaxParams.put("course_id", this.mCourseId);
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, String.valueOf(page));
        ajaxParams.put(DatabaseManager.SIZE, "20");
        CourseDataRequest.getIntance(getContext()).getNote(ajaxParams, this, NetworkRequestsURL.courseVideoNoteList);
    }

    private void initRv() {
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        QuestionNoteListAdapter questionNoteListAdapter = new QuestionNoteListAdapter(R.layout.item_video_note_list_landscape, this.mNoteList, this.fromVideo, true);
        this.questionNoteListAdapter = questionNoteListAdapter;
        questionNoteListAdapter.addChildClickViewIds(R.id.iv_note_menu);
        this.questionNoteListAdapter.setOnItemChildClickListener(this);
        this.recyclerView.setAdapter(this.questionNoteListAdapter);
        this.questionNoteListAdapter.getLoadMoreModule().setEnableLoadMore(false);
        this.questionNoteListAdapter.setEmptyView(R.layout.adapter_empty_view);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$0(DialogInterface dialogInterface) {
        setFocusable(true);
        EventBus.getDefault().post(new VideoStateEvent(true));
        requestFocus();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPopNoteDelete$2(int i2) {
        if (!CommonUtil.isNetworkConnected(getContext())) {
            ToastUtil.shortToast(getContext(), "请检查网络连接");
            return;
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("course_id", this.mCourseId);
        ajaxParams.put("video_id", this.mVideoId);
        ajaxParams.put("id", this.mNoteList.get(i2).getId());
        CourseDataRequest.getIntance(getContext()).deleteNote(ajaxParams, this);
        this.mNoteList.remove(i2);
        this.questionNoteListAdapter.notifyItemRemoved(i2);
        if (this.mNoteList.size() == 0) {
            EventBus.getDefault().post(new NoteEventBean("", false, this.mVideoId));
            ((TextView) findViewById(R.id.tv_title)).setText(String.format("%d条笔记", 0));
        } else {
            int intConfig = SharePreferencesUtils.readIntConfig(CommonParameter.NOTE_COUNT, getContext(), 0);
            if (intConfig > 0) {
                ((TextView) findViewById(R.id.tv_title)).setText(String.format("%d条笔记", Integer.valueOf(intConfig - 1)));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPopNoteEdit$1(int i2, QuestionNoteBean questionNoteBean) {
        this.mNoteList.get(i2).setContent(questionNoteBean.getContent());
        this.mNoteList.get(i2).setCtime(questionNoteBean.getCtime());
        this.mNoteList.get(i2).setImg(questionNoteBean.getImg());
        this.questionNoteListAdapter.notifyItemChanged(i2);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.pop_note_list_bottom_landscape;
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
            EventBus.getDefault().post(new VideoStateEvent(true));
            return;
        }
        if (id == R.id.ll_add_note || id == R.id.tv_add_note) {
            EventBus.getDefault().post(new VideoStateEvent(false));
            DialogVideoNoteInputLandScape dialogVideoNoteInputLandScape = new DialogVideoNoteInputLandScape(getContext(), this.mCourseId, this.mVideoId, new NoteListener() { // from class: com.psychiatrygarden.widget.PopNoteCourseListLandScape.1
                @Override // com.psychiatrygarden.interfaceclass.NoteListener, com.psychiatrygarden.interfaceclass.onDialogNoteListener
                public void onclickStringBack(QuestionNoteBean questionNoteBean) {
                    PopNoteCourseListLandScape popNoteCourseListLandScape = PopNoteCourseListLandScape.this;
                    popNoteCourseListLandScape.getNote(popNoteCourseListLandScape.page);
                }

                @Override // com.psychiatrygarden.interfaceclass.NoteListener
                public void updateNoteCount(int count) {
                    super.updateNoteCount(count);
                }
            });
            dialogVideoNoteInputLandScape.setCanceledOnTouchOutside(true);
            dialogVideoNoteInputLandScape.setDismissListener(new DialogInterface.OnDismissListener() { // from class: com.psychiatrygarden.widget.pc
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    this.f16793c.lambda$onClick$0(dialogInterface);
                }
            });
            dialogVideoNoteInputLandScape.setScreenShotFilePath(this.screenShotFilePath);
            dialogVideoNoteInputLandScape.setModuleType(this.moduleType);
            dialogVideoNoteInputLandScape.show();
            setFocusable(false);
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        ImageView imageView = (ImageView) findViewById(R.id.iv_note_close);
        this.recyclerView = (VerticalRecyclerView) findViewById(R.id.recyclerView);
        ((TextView) findViewById(R.id.tv_add_note)).setOnClickListener(this);
        imageView.setOnClickListener(this);
        findViewById(R.id.ll_add_note).setOnClickListener(this);
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
            PopNoteListMenu popNoteListMenu = new PopNoteListMenu(getContext(), position, this);
            popNoteListMenu.setLandScape(true);
            new XPopup.Builder(getContext()).hasShadowBg(Boolean.FALSE).atView(view).asCustom(popNoteListMenu).show();
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
            ((ClipboardManager) getContext().getSystemService("clipboard")).setText(this.mNoteList.get(position).getContent());
            NewToast.showShort(getContext(), "复制成功", 0).show();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.widget.PopNoteListMenu.OnClickBtnListener
    public void onPopNoteDelete(final int position) {
        new XPopup.Builder(getContext()).asCustom(new CommonTwoBtnDialog(getContext(), new CommonTwoBtnDialog.ClickIml() { // from class: com.psychiatrygarden.widget.qc
            @Override // com.psychiatrygarden.widget.CommonTwoBtnDialog.ClickIml
            public final void mClickIml() {
                this.f16825a.lambda$onPopNoteDelete$2(position);
            }
        }, new SpannableStringBuilder("是否确认删除该条笔记?"), "", "取消", "确认")).show();
    }

    @Override // com.psychiatrygarden.widget.PopNoteListMenu.OnClickBtnListener
    public void onPopNoteEdit(final int position) {
        new DialogVideoNoteInputLandScape(getContext(), this.mCourseId, this.mVideoId, this.mNoteList.get(position), new onDialogNoteListener() { // from class: com.psychiatrygarden.widget.rc
            @Override // com.psychiatrygarden.interfaceclass.onDialogNoteListener
            public final void onclickStringBack(QuestionNoteBean questionNoteBean) {
                this.f16859a.lambda$onPopNoteEdit$1(position, questionNoteBean);
            }
        }).show();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onShow() {
        super.onShow();
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requstUrl) {
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String s2, String requstUrl) {
        try {
            JSONObject jSONObject = new JSONObject(s2);
            if (!requstUrl.equals(NetworkRequestsURL.mCourseNoteListURL) && !requstUrl.equals(NetworkRequestsURL.courseVideoNoteList)) {
                if (requstUrl.equals(NetworkRequestsURL.mCourseClearNoteURL) || requstUrl.equals(NetworkRequestsURL.deleteVideoNote)) {
                    if (this.mNoteList.size() == 0) {
                        NoNoteListener noNoteListener = this.noNoteListener;
                        if (noNoteListener != null) {
                            noNoteListener.noNote();
                        }
                        dismiss();
                    }
                    int intConfig = SharePreferencesUtils.readIntConfig(CommonParameter.NOTE_COUNT, getContext(), 0);
                    int intConfig2 = SharePreferencesUtils.readIntConfig(CommonParameter.COMMENT_COUNT, getContext(), 0);
                    int i2 = intConfig - 1;
                    SharePreferencesUtils.writeIntConfig(CommonParameter.NOTE_COUNT, i2, getContext());
                    EventBus.getDefault().post(new UpdateVideoCommentNote(i2, intConfig2));
                    EventBus.getDefault().post(EventBusConstant.DEL_NOTE);
                    return;
                }
                return;
            }
            TextView textView = (TextView) findViewById(R.id.tv_title);
            if (jSONObject.optString("code").equals("200")) {
                this.isLoading = false;
                List list = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<QuestionNoteBean>>() { // from class: com.psychiatrygarden.widget.PopNoteCourseListLandScape.2
                }.getType());
                if (this.page == 1) {
                    this.mNoteList.clear();
                    this.mNoteList.addAll(list);
                    initRv();
                    this.questionNoteListAdapter.getLoadMoreModule().checkDisableLoadMoreIfNotFullPage();
                } else if (list.size() > 0) {
                    this.mNoteList.addAll(list);
                    this.questionNoteListAdapter.notifyDataSetChanged();
                    this.questionNoteListAdapter.getLoadMoreModule().loadMoreComplete();
                } else {
                    this.questionNoteListAdapter.getLoadMoreModule().loadMoreEnd();
                }
                this.hasMoreData = list.size() > 0;
            } else {
                if (this.page != 1) {
                    this.questionNoteListAdapter.getLoadMoreModule().loadMoreFail();
                }
                ToastUtil.shortToast(getContext(), jSONObject.optString("message"));
            }
            this.hasMoreData = false;
            textView.setText(String.format("%d条笔记", Integer.valueOf(SharePreferencesUtils.readIntConfig(CommonParameter.NOTE_COUNT, getContext(), 0))));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
