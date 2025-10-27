package com.psychiatrygarden.widget.english;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.text.ClipboardManager;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
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
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.VerticalRecyclerView;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.online.bean.event.NoteEventBean;
import com.psychiatrygarden.adapter.QuestionNoteListAdapter;
import com.psychiatrygarden.bean.QuestionNoteBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.UpdateVideoCommentNote;
import com.psychiatrygarden.event.VideoStateEvent;
import com.psychiatrygarden.http.CourseDataRequest;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.interfaceclass.onDialogNoteListener;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.DividerItemDecoration;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.psychiatrygarden.widget.CommonTwoBtnDialog;
import com.psychiatrygarden.widget.DialogCourseNoteInput;
import com.psychiatrygarden.widget.DialogVideoNoteInput;
import com.psychiatrygarden.widget.PopNoteListMenu;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class PopNoteCourseList extends BottomPopupView implements View.OnClickListener, OnItemChildClickListener, PopNoteListMenu.OnClickBtnListener, QuestionDataCallBack<String>, OnLoadMoreListener {
    private static final int preLoadDataThreshold = 3;
    private String course_id;
    private boolean fromVideo;
    private boolean fullScreen;
    private boolean hasMoreData;
    private boolean isLoading;
    private ImageView iv_note_close;
    private NoNoteListener noNoteListener;
    private int page;
    private List<QuestionNoteBean> questionNoteBeans;
    private QuestionNoteListAdapter questionNoteListAdapter;
    private VerticalRecyclerView recyclerView;
    private String screenShotFilePath;
    private TextView tv_add_note;
    private String video_id;

    public interface NoNoteListener {
        void noNote();
    }

    public PopNoteCourseList(@NonNull Context context, String video_id, String course_id, NoNoteListener noNoteListener) {
        super(context);
        this.page = 1;
        this.questionNoteBeans = new ArrayList();
        this.isLoading = true;
        this.course_id = course_id;
        this.video_id = video_id;
        this.noNoteListener = noNoteListener;
    }

    private void deleteFile() {
        if (TextUtils.isEmpty(this.screenShotFilePath)) {
            return;
        }
        try {
            File file = new File(this.screenShotFilePath);
            if (file.exists() && file.length() > 0) {
                file.delete();
                if (Build.VERSION.SDK_INT < 29) {
                    Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                    intent.setData(Uri.parse("file://" + this.screenShotFilePath));
                    ProjectApp.instance().sendBroadcast(intent);
                } else {
                    MediaScannerConnection.scanFile(ProjectApp.instance(), new String[]{file.getParent()}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.psychiatrygarden.widget.english.e
                        @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                        public final void onScanCompleted(String str, Uri uri) {
                            PopNoteCourseList.lambda$deleteFile$0(str, uri);
                        }
                    });
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void getNote(int page) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("video_id", this.video_id);
        ajaxParams.put("course_id", this.course_id);
        if (!this.fromVideo) {
            ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, page + "");
            ajaxParams.put(DatabaseManager.SIZE, "20");
        }
        if (this.fromVideo) {
            CourseDataRequest.getIntance(getContext()).getNote(ajaxParams, this, NetworkRequestsURL.courseVideoNoteList);
        } else {
            CourseDataRequest.getIntance(getContext()).getNote(ajaxParams, this);
        }
    }

    private void initRv() {
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (!this.fromVideo) {
            this.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 0, 2, Color.parseColor(SkinManager.getCurrentSkinType(getContext()) == 1 ? "#2E3241" : "#E6E6E6")));
        }
        boolean z2 = this.fromVideo;
        QuestionNoteListAdapter questionNoteListAdapter = new QuestionNoteListAdapter(!z2 ? R.layout.item_question_note_list : R.layout.item_video_note_list, this.questionNoteBeans, z2, false);
        this.questionNoteListAdapter = questionNoteListAdapter;
        questionNoteListAdapter.addChildClickViewIds(R.id.iv_note_menu);
        this.questionNoteListAdapter.setOnItemChildClickListener(this);
        if (!this.fromVideo) {
            this.questionNoteListAdapter.getLoadMoreModule().setOnLoadMoreListener(this);
        }
        this.recyclerView.setAdapter(this.questionNoteListAdapter);
        this.questionNoteListAdapter.getLoadMoreModule().setEnableLoadMore(false);
        this.questionNoteListAdapter.setEmptyView(R.layout.adapter_empty_view);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$deleteFile$0(String str, Uri uri) {
        String str2;
        StringBuilder sb = new StringBuilder();
        sb.append("path = ");
        sb.append(str);
        if (uri != null) {
            str2 = " uri = " + uri.getPath();
        } else {
            str2 = "";
        }
        sb.append(str2);
        LogUtils.d("onScanCompleted", sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$1(QuestionNoteBean questionNoteBean) {
        this.page = 1;
        getNote(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onClick$2(DialogInterface dialogInterface) {
        EventBus.getDefault().post(new VideoStateEvent(true));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$3(QuestionNoteBean questionNoteBean) {
        this.page = 1;
        getNote(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$4(DialogInterface dialogInterface) {
        EventBus.getDefault().post(new VideoStateEvent(true));
        ((Activity) getContext()).getWindow().clearFlags(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPopNoteEdit$5(int i2, QuestionNoteBean questionNoteBean) {
        this.questionNoteBeans.get(i2).setContent(questionNoteBean.getContent());
        this.questionNoteBeans.get(i2).setCtime(questionNoteBean.getCtime());
        this.questionNoteBeans.get(i2).setImg(questionNoteBean.getImg());
        this.questionNoteListAdapter.notifyItemChanged(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPopNoteEdit$6(int i2, QuestionNoteBean questionNoteBean) {
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
        return this.fromVideo ? R.layout.pop_note_list_bottom_video : R.layout.pop_note_list_bottom;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getMaxHeight() {
        int intConfig = SharePreferencesUtils.readIntConfig(CommonParameter.VIDEO_POP_HEIGHT, getContext(), SizeUtil.dp2px(getContext(), 300)) + StatusBarUtil.getStatusBarHeight(getContext());
        if (this.fromVideo) {
            return intConfig;
        }
        return XPopupUtils.getAppHeight(getContext()) - (!this.fromVideo ? 10 : SizeUtil.dp2px(getContext(), 162));
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        int id = v2.getId();
        if (id == R.id.iv_note_close) {
            dismiss();
            EventBus.getDefault().post(new VideoStateEvent(true));
            return;
        }
        if (id != R.id.tv_add_note) {
            return;
        }
        EventBus.getDefault().post(new VideoStateEvent(false));
        if (!this.fromVideo) {
            DialogCourseNoteInput dialogCourseNoteInput = new DialogCourseNoteInput(getContext(), this.course_id, this.video_id, new onDialogNoteListener() { // from class: com.psychiatrygarden.widget.english.a
                @Override // com.psychiatrygarden.interfaceclass.onDialogNoteListener
                public final void onclickStringBack(QuestionNoteBean questionNoteBean) {
                    this.f16449a.lambda$onClick$1(questionNoteBean);
                }
            });
            dialogCourseNoteInput.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.psychiatrygarden.widget.english.b
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    PopNoteCourseList.lambda$onClick$2(dialogInterface);
                }
            });
            dialogCourseNoteInput.show();
        } else {
            if (this.fullScreen) {
                return;
            }
            DialogVideoNoteInput dialogVideoNoteInputBuild = new DialogVideoNoteInput.Builder(getContext()).setScreenShotFilePath(this.screenShotFilePath).setCourseId(this.course_id).setObjId(this.video_id).setClickNoteListener(new onDialogNoteListener() { // from class: com.psychiatrygarden.widget.english.c
                @Override // com.psychiatrygarden.interfaceclass.onDialogNoteListener
                public final void onclickStringBack(QuestionNoteBean questionNoteBean) {
                    this.f16450a.lambda$onClick$3(questionNoteBean);
                }
            }).setDismissListener(new DialogInterface.OnDismissListener() { // from class: com.psychiatrygarden.widget.english.d
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    this.f16451c.lambda$onClick$4(dialogInterface);
                }
            }).build();
            dialogVideoNoteInputBuild.getWindow().addFlags(2);
            dialogVideoNoteInputBuild.show();
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        int intConfig;
        super.onCreate();
        View rootView = getRootView();
        if (this.fromVideo) {
            ViewGroup.LayoutParams layoutParams = rootView.getLayoutParams();
            layoutParams.height = ScreenUtil.getScreenHeight((Activity) getContext()) - (StatusBarUtil.getStatusBarHeight(getContext()) + SizeUtil.dp2px(getContext(), 230));
            if (this.fromVideo && (intConfig = SharePreferencesUtils.readIntConfig(CommonParameter.VIDEO_POP_HEIGHT, getContext(), layoutParams.height) + StatusBarUtil.getStatusBarHeight(getContext())) >= layoutParams.height) {
                layoutParams.height = intConfig - SizeUtil.dp2px(getContext(), 10);
            }
            rootView.setLayoutParams(layoutParams);
        }
        this.iv_note_close = (ImageView) findViewById(R.id.iv_note_close);
        this.recyclerView = (VerticalRecyclerView) findViewById(R.id.recyclerView);
        TextView textView = (TextView) findViewById(R.id.tv_add_note);
        this.tv_add_note = textView;
        textView.setOnClickListener(this);
        this.iv_note_close.setOnClickListener(this);
        if (this.fromVideo) {
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(new int[]{R.attr.secondary_backgroup_color, R.attr.third_txt_color});
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setCornerRadius(SizeUtil.dp2px(getContext(), 100));
            gradientDrawable.setColor(typedArrayObtainStyledAttributes.getColor(0, getContext().getColor(R.color.secondary_backgroup_color)));
            this.tv_add_note.setBackground(gradientDrawable);
            this.tv_add_note.setHintTextColor(typedArrayObtainStyledAttributes.getColor(1, getContext().getColor(R.color.third_txt_color)));
            typedArrayObtainStyledAttributes.recycle();
        }
        getNote(this.page);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onDismiss() {
        deleteFile();
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
            NewToast.showShort(getContext(), "复制成功");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.widget.PopNoteListMenu.OnClickBtnListener
    public void onPopNoteDelete(final int position) {
        new XPopup.Builder(getContext()).asCustom(new CommonTwoBtnDialog(getContext(), new CommonTwoBtnDialog.ClickIml() { // from class: com.psychiatrygarden.widget.english.PopNoteCourseList.1
            @Override // com.psychiatrygarden.widget.CommonTwoBtnDialog.ClickIml
            public void mClickIml() {
                if (!CommonUtil.isNetworkConnected(PopNoteCourseList.this.getContext())) {
                    ToastUtil.shortToast(PopNoteCourseList.this.getContext(), "请检查网络连接");
                    return;
                }
                AjaxParams ajaxParams = new AjaxParams();
                ajaxParams.put("course_id", PopNoteCourseList.this.course_id);
                ajaxParams.put("video_id", PopNoteCourseList.this.video_id);
                ajaxParams.put("id", ((QuestionNoteBean) PopNoteCourseList.this.questionNoteBeans.get(position)).getId());
                if (PopNoteCourseList.this.fromVideo) {
                    CourseDataRequest.getIntance(PopNoteCourseList.this.getContext()).deleteNote(ajaxParams, PopNoteCourseList.this);
                } else {
                    CourseDataRequest.getIntance(PopNoteCourseList.this.getContext()).cleanNote(ajaxParams, PopNoteCourseList.this);
                }
                PopNoteCourseList.this.questionNoteBeans.remove(position);
                PopNoteCourseList.this.questionNoteListAdapter.notifyItemRemoved(position);
                if (PopNoteCourseList.this.questionNoteBeans.size() == 0) {
                    EventBus.getDefault().post(new NoteEventBean("", false, PopNoteCourseList.this.video_id));
                }
            }
        }, new SpannableStringBuilder("是否确认删除该条笔记?"), "", "取消", "确认")).show();
    }

    @Override // com.psychiatrygarden.widget.PopNoteListMenu.OnClickBtnListener
    public void onPopNoteEdit(final int position) {
        if (!this.fromVideo) {
            new DialogCourseNoteInput(getContext(), this.course_id, this.video_id, this.questionNoteBeans.get(position), new onDialogNoteListener() { // from class: com.psychiatrygarden.widget.english.f
                @Override // com.psychiatrygarden.interfaceclass.onDialogNoteListener
                public final void onclickStringBack(QuestionNoteBean questionNoteBean) {
                    this.f16452a.lambda$onPopNoteEdit$5(position, questionNoteBean);
                }
            }).show();
            return;
        }
        DialogVideoNoteInput.Builder builder = new DialogVideoNoteInput.Builder(getContext());
        builder.setCourseId(this.course_id).setObjId(this.video_id).setContent(this.questionNoteBeans.get(position).getContent()).setTitle("编辑笔记").setClickNoteListener(new onDialogNoteListener() { // from class: com.psychiatrygarden.widget.english.g
            @Override // com.psychiatrygarden.interfaceclass.onDialogNoteListener
            public final void onclickStringBack(QuestionNoteBean questionNoteBean) {
                this.f16454a.lambda$onPopNoteEdit$6(position, questionNoteBean);
            }
        }).setEditNote(this.questionNoteBeans.get(position));
        builder.build().show();
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
            boolean z2 = false;
            if (!requstUrl.equals(NetworkRequestsURL.mCourseNoteListURL) && !requstUrl.equals(NetworkRequestsURL.courseVideoNoteList)) {
                if (requstUrl.equals(NetworkRequestsURL.mCourseClearNoteURL) || TextUtils.equals(NetworkRequestsURL.deleteVideoNote, requstUrl)) {
                    NewToast.showShort(getContext(), "删除成功");
                    if (this.questionNoteBeans.size() == 0) {
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
            if (!jSONObject.optString("code").equals("200")) {
                if (this.page != 1) {
                    this.questionNoteListAdapter.getLoadMoreModule().loadMoreFail();
                }
                ToastUtil.shortToast(getContext(), jSONObject.optString("message"));
                return;
            }
            if (this.fromVideo && this.isLoading) {
                this.isLoading = false;
            }
            List list = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<QuestionNoteBean>>() { // from class: com.psychiatrygarden.widget.english.PopNoteCourseList.2
            }.getType());
            if (this.fromVideo) {
                if (list != null && list.size() > 0) {
                    z2 = true;
                }
                this.hasMoreData = z2;
            }
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

    public PopNoteCourseList(@NonNull Context context, String video_id, String course_id, NoNoteListener noNoteListener, boolean fromVideo, String screenShotFilePath) {
        super(context);
        this.page = 1;
        this.questionNoteBeans = new ArrayList();
        this.isLoading = true;
        this.screenShotFilePath = screenShotFilePath;
        this.fromVideo = fromVideo;
        this.course_id = course_id;
        this.video_id = video_id;
        this.noNoteListener = noNoteListener;
    }
}
