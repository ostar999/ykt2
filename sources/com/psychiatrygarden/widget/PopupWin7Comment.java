package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.BottomPopupView;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.onDialogShareClickListener;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.psychiatrygarden.widget.CommonTwoBtnDialog;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class PopupWin7Comment extends BottomPopupView implements OnItemClickListener {
    private onDialogShareClickListener clickListener;
    private String commentId;
    private String content;
    private Context context;
    private String extra;
    private int flag;
    private boolean isShow;
    private String is_del;
    private boolean landScape;
    private String module_type;
    private String nickName;
    private boolean onlyCopy;
    private View popAnchorView;
    private int replyCount;
    private View view;

    /* renamed from: com.psychiatrygarden.widget.PopupWin7Comment$2, reason: invalid class name */
    public class AnonymousClass2 extends BaseQuickAdapter<Integer, BaseViewHolder> {
        public AnonymousClass2(int layoutResId, List data) {
            super(layoutResId, data);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(Integer num, View view) {
            switch (num.intValue()) {
                case 0:
                    PopupWin7Comment.this.clickListener.onclickIntBack(0);
                    break;
                case 1:
                    PopupWin7Comment.this.clickListener.onclickIntBack(3);
                    break;
                case 2:
                    PopupWin7Comment.this.clickListener.onclickIntBack(1);
                    break;
                case 3:
                    PopupWin7Comment.this.clickListener.onclickIntBack(8);
                    break;
                case 4:
                    if (!UserConfig.isLogin() || !UserConfig.getInstance().getUser().getAdmin().equals("1")) {
                        PopupWin7Comment popupWin7Comment = PopupWin7Comment.this;
                        popupWin7Comment.deleteAtoast(2, 0, popupWin7Comment.clickListener);
                        break;
                    } else if (PopupWin7Comment.this.replyCount <= 0) {
                        PopupWin7Comment popupWin7Comment2 = PopupWin7Comment.this;
                        popupWin7Comment2.deleteAtoast(2, 0, popupWin7Comment2.clickListener);
                        break;
                    } else {
                        PopupWin7Comment popupWin7Comment3 = PopupWin7Comment.this;
                        popupWin7Comment3.getReplyCount(popupWin7Comment3.context, PopupWin7Comment.this.commentId);
                        break;
                    }
                    break;
                case 5:
                    PopupWin7Comment.this.clickListener.onclickIntBack(7);
                    break;
                case 6:
                    PopupWin7Comment.this.clickListener.onclickIntBack(4);
                    break;
                case 7:
                    PopupWin7Comment.this.clickListener.onclickIntBack(9);
                    break;
            }
            PopupWin7Comment.this.dismiss();
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NotNull BaseViewHolder baseViewHolder, final Integer integer) {
            TextView textView = (TextView) baseViewHolder.getView(R.id.tv_name);
            baseViewHolder.getView(R.id.line).setVisibility(baseViewHolder.getLayoutPosition() == getData().size() + (-1) ? 8 : 0);
            switch (integer.intValue()) {
                case 0:
                    textView.setText("回复");
                    break;
                case 1:
                    textView.setText("复制");
                    break;
                case 2:
                    textView.setText("编辑");
                    break;
                case 3:
                    textView.setText("举报");
                    break;
                case 4:
                    textView.setText("删除");
                    break;
                case 5:
                    textView.setText("封禁");
                    break;
                case 6:
                    textView.setText("私信");
                    break;
                case 7:
                    textView.setText("添加至笔记");
                    break;
            }
            baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.ud
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f16960c.lambda$convert$0(integer, view);
                }
            });
        }
    }

    public PopupWin7Comment(Context context, String content, String nickName, boolean onlyCopy, onDialogShareClickListener clickListener) {
        super(context);
        this.extra = "0";
        this.is_del = "1";
        this.module_type = "";
        this.context = context;
        this.clickListener = clickListener;
        this.onlyCopy = onlyCopy;
        this.content = content;
        this.nickName = nickName;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getReplyCount(Context mContext, String id) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("id", id);
        ajaxParams.put("module_type", this.module_type);
        ajaxParams.put("comment_type", "2");
        YJYHttpUtils.post(mContext, NetworkRequestsURL.getReplyCommentCount, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.widget.PopupWin7Comment.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (jSONObject.optString("code").equals("200")) {
                        String strOptString = jSONObject.optJSONObject("data").optString("count");
                        int i2 = TextUtils.isEmpty(strOptString) ? 0 : Integer.parseInt(strOptString);
                        PopupWin7Comment popupWin7Comment = PopupWin7Comment.this;
                        popupWin7Comment.deleteAtoast(2, i2, popupWin7Comment.clickListener);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void initLandScapeView() {
        View viewInflate = View.inflate(this.context, R.layout.pop_question_comment_menu_bottom_landscape, null);
        final PopupWindow popupWindow = new PopupWindow(viewInflate, -1, SizeUtil.dp2px(this.context, R2.attr.ad_height));
        popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(this.context, R.drawable.bg_pop_comment));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(R.style.popupwindow_anim_style);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_comment_content);
        viewInflate.findViewById(R.id.tv_comment_cancel).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.PopupWin7Comment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                popupWindow.dismiss();
                PopupWin7Comment.this.dismiss();
            }
        });
        if (TextUtils.isEmpty(this.content)) {
            textView.setVisibility(8);
        } else {
            textView.setVisibility(0);
            textView.setText(String.format("%s：%s", this.nickName, this.content));
        }
        initRv((RecyclerView) viewInflate.findViewById(R.id.recyclerView));
        popupWindow.showAtLocation(this.popAnchorView, 80, 0, 0);
        popupWindow.update();
    }

    private void initRv(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList arrayList = new ArrayList();
        if (this.onlyCopy) {
            arrayList.add(1);
        } else if (UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            arrayList.add(0);
            arrayList.add(1);
            if (!TextUtils.isEmpty(this.module_type) && (this.module_type.equals("1") || this.module_type.equals("4"))) {
                arrayList.add(7);
            }
            arrayList.add(2);
            arrayList.add(4);
            arrayList.add(5);
        } else if (SharePreferencesUtils.readBooleanConfig(CommonParameter.AUTHOR_ID, false, getContext())) {
            arrayList.add(0);
            arrayList.add(1);
            if (!TextUtils.isEmpty(this.module_type) && (this.module_type.equals("1") || this.module_type.equals("4"))) {
                arrayList.add(7);
            }
            arrayList.add(2);
            arrayList.add(3);
            arrayList.add(4);
        } else {
            if (this.flag == 1) {
                arrayList.add(0);
                arrayList.add(1);
                if (!TextUtils.isEmpty(this.module_type) && (this.module_type.equals("1") || this.module_type.equals("4"))) {
                    arrayList.add(7);
                }
                arrayList.add(2);
                arrayList.add(3);
                arrayList.add(4);
            } else {
                arrayList.add(0);
                arrayList.add(1);
                if (!TextUtils.isEmpty(this.module_type) && (this.module_type.equals("1") || this.module_type.equals("4"))) {
                    arrayList.add(7);
                }
                arrayList.add(3);
            }
            "1".equals(this.extra + "");
        }
        AnonymousClass2 anonymousClass2 = new AnonymousClass2(R.layout.layout_pop_comment_item_new, arrayList);
        recyclerView.setAdapter(anonymousClass2);
        View viewInflate = View.inflate(this.context, R.layout.layout_video_comment_pop_cancel, null);
        anonymousClass2.addFooterView(viewInflate);
        viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.sd
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16901c.lambda$initRv$1(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRv$1(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    public void deleteAtoast(final int i2, int count, final onDialogShareClickListener clickListener) {
        SpannableStringBuilder spannableStringBuilder;
        SpannableStringBuilder spannableStringBuilder2;
        CustomDialog customDialog = new CustomDialog(this.context, 2);
        customDialog.setCancelable(false);
        customDialog.isOutTouchDismiss(false);
        if (this.isShow) {
            spannableStringBuilder = new SpannableStringBuilder("您确定删除此帖");
            customDialog.setMessage("您确定删除此帖？");
        } else {
            if (count > 0) {
                String str = "删除此评论，此评论的相关盖楼评论（共" + count + "条）也将被同时删除，是否继续？";
                int iIndexOf = str.indexOf("共");
                int iIndexOf2 = str.indexOf("条");
                SpannableStringBuilder spannableStringBuilder3 = new SpannableStringBuilder(str);
                if (SkinManager.getCurrentSkinType(this.context) == 0) {
                    spannableStringBuilder3.setSpan(new ForegroundColorSpan(Color.parseColor("#DD594A")), iIndexOf + 1, iIndexOf2, 34);
                } else {
                    spannableStringBuilder3.setSpan(new ForegroundColorSpan(Color.parseColor("#B2575C")), iIndexOf + 1, iIndexOf2, 34);
                }
                customDialog.setMessage(spannableStringBuilder3);
                spannableStringBuilder2 = spannableStringBuilder3;
                new XPopup.Builder(getContext()).asCustom(new CommonTwoBtnDialog(this.context, new CommonTwoBtnDialog.ClickIml() { // from class: com.psychiatrygarden.widget.rd
                    @Override // com.psychiatrygarden.widget.CommonTwoBtnDialog.ClickIml
                    public final void mClickIml() {
                        clickListener.onclickIntBack(i2);
                    }
                }, spannableStringBuilder2, "取消", "确定")).show();
            }
            customDialog.setMessage("您确定删除" + this.nickName + "的评论？");
            spannableStringBuilder = new SpannableStringBuilder("您确定删除" + this.nickName + "的评论？");
        }
        spannableStringBuilder2 = spannableStringBuilder;
        new XPopup.Builder(getContext()).asCustom(new CommonTwoBtnDialog(this.context, new CommonTwoBtnDialog.ClickIml() { // from class: com.psychiatrygarden.widget.rd
            @Override // com.psychiatrygarden.widget.CommonTwoBtnDialog.ClickIml
            public final void mClickIml() {
                clickListener.onclickIntBack(i2);
            }
        }, spannableStringBuilder2, "取消", "确定")).show();
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
        return !this.landScape ? R.layout.pop_question_comment_menu_bottom : R.layout.pop_question_comment_menu_bottom_landscape;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        TextView textView = (TextView) findViewById(R.id.tv_comment_content);
        findViewById(R.id.tv_comment_cancel).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.td
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16934c.lambda$onCreate$0(view);
            }
        });
        if (TextUtils.isEmpty(this.content)) {
            textView.setVisibility(8);
        } else {
            textView.setVisibility(0);
            textView.setText(String.format("%s：%s", this.nickName, this.content));
        }
        if (this.landScape) {
            return;
        }
        initRv((RecyclerView) findViewById(R.id.recyclerView));
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onDismiss() {
    }

    @Override // com.chad.library.adapter.base.listener.OnItemClickListener
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onShow() {
        super.onShow();
    }

    public void setLandScape(boolean landScape) {
        this.landScape = landScape;
    }

    public void setPopAnchorView(View v2) {
        this.popAnchorView = v2;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public BasePopupView show() {
        if (!this.landScape || this.popAnchorView == null) {
            return super.show();
        }
        initLandScapeView();
        return this;
    }

    public PopupWin7Comment(Context context, String content, boolean isShow, onDialogShareClickListener clickListener) {
        super(context);
        this.extra = "0";
        this.is_del = "1";
        this.module_type = "";
        this.onlyCopy = false;
        this.context = context;
        this.content = content;
        this.clickListener = clickListener;
        this.isShow = isShow;
    }

    public PopupWin7Comment(Context context, String content, String nickName, int flag, String extra, String is_del, onDialogShareClickListener clickListener) {
        super(context);
        this.module_type = "";
        this.onlyCopy = false;
        this.context = context;
        this.flag = flag;
        this.extra = extra;
        this.is_del = is_del;
        this.content = content;
        this.nickName = nickName;
        this.clickListener = clickListener;
    }

    public PopupWin7Comment(Context context, String content, String nickName, int flag, String extra, String module_type, String is_del, String commentId, int replyCount, onDialogShareClickListener clickListener) {
        super(context);
        this.onlyCopy = false;
        this.context = context;
        this.flag = flag;
        this.module_type = module_type;
        this.extra = extra;
        this.is_del = is_del;
        this.content = content;
        this.nickName = nickName;
        this.commentId = commentId;
        this.replyCount = replyCount;
        this.clickListener = clickListener;
    }
}
