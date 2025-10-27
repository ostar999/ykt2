package com.psychiatrygarden.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cn.hutool.core.lang.RegexPool;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.psychiatrygarden.bean.VideoFeedBackBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.NiceRatingBar;
import com.psychiatrygarden.widget.VideoFeedBackPop;
import com.yikaobang.yixue.R;
import java.util.Iterator;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class VideoFeedBackPop extends BottomPopupView {
    private final String content;
    private final String courseId;
    private List<VideoFeedBackBean> feedBackBeans;
    private final OnConfirmListener listener;
    private final BaseQuickAdapter<VideoFeedBackBean, BaseViewHolder> mAdapter;
    private final String videoId;
    private final boolean viewOnly;

    /* renamed from: com.psychiatrygarden.widget.VideoFeedBackPop$1, reason: invalid class name */
    public class AnonymousClass1 extends BaseQuickAdapter<VideoFeedBackBean, BaseViewHolder> {
        public AnonymousClass1(int layoutResId) {
            super(layoutResId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$convert$0(VideoFeedBackBean videoFeedBackBean, BaseViewHolder baseViewHolder, float f2) {
            int i2 = (int) f2;
            videoFeedBackBean.setStar_level(String.valueOf(i2));
            baseViewHolder.setText(R.id.tv_score, i2 + "分");
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NonNull final BaseViewHolder holder, final VideoFeedBackBean videoFeedBackBean) {
            holder.setText(R.id.tv_title, videoFeedBackBean.getTitle()).setText(R.id.tv_score, videoFeedBackBean.getStar_level() + "分");
            NiceRatingBar niceRatingBar = (NiceRatingBar) holder.getView(R.id.rating_bar);
            if (!VideoFeedBackPop.this.viewOnly) {
                niceRatingBar.setOnRatingChangedListener(new NiceRatingBar.OnRatingChangedListener() { // from class: com.psychiatrygarden.widget.hj
                    @Override // com.psychiatrygarden.widget.NiceRatingBar.OnRatingChangedListener
                    public final void onRatingChanged(float f2) {
                        VideoFeedBackPop.AnonymousClass1.lambda$convert$0(videoFeedBackBean, holder, f2);
                    }
                });
                return;
            }
            String star_level = videoFeedBackBean.getStar_level();
            if (!TextUtils.isEmpty(star_level) && star_level.matches(RegexPool.NUMBERS)) {
                niceRatingBar.setRating(Integer.parseInt(star_level));
            }
            niceRatingBar.setRatingStatus(NiceRatingBar.RatingStatus.Disable);
        }
    }

    public VideoFeedBackPop(String content, @NonNull Context context, boolean viewOnly, String courseId, String videoId, OnConfirmListener listener, List<VideoFeedBackBean> feedBackBeans) {
        super(context);
        this.mAdapter = new AnonymousClass1(R.layout.item_video_feedback);
        this.viewOnly = viewOnly;
        this.videoId = videoId;
        this.listener = listener;
        this.courseId = courseId;
        this.content = content;
        this.feedBackBeans = feedBackBeans;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onCreate$1(EditText editText) {
        editText.setEnabled(true);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$2(EditText editText, View view) {
        try {
            submitContent(editText.getText().toString());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void submitContent(String content) throws Exception {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("course_id", this.courseId);
        ajaxParams.put("video_id", this.videoId);
        if (TextUtils.isEmpty(content)) {
            ToastUtil.shortToast(getContext(), "评价点什么吧");
            return;
        }
        ajaxParams.put("content", content);
        JSONArray jSONArray = new JSONArray();
        for (VideoFeedBackBean videoFeedBackBean : this.mAdapter.getData()) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("evaluate_id", videoFeedBackBean.getId());
            jSONObject.put("star_level", videoFeedBackBean.getStar_level());
            jSONObject.put("title", videoFeedBackBean.getTitle());
            jSONArray.put(jSONObject);
        }
        ajaxParams.put("evaluate", jSONArray.toString());
        YJYHttpUtils.post(getContext(), NetworkRequestsURL.submitVideoFeedBack, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.widget.VideoFeedBackPop.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (TextUtils.isEmpty(strMsg)) {
                    return;
                }
                ToastUtil.shortToast(VideoFeedBackPop.this.getContext(), strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    JSONObject jSONObject2 = new JSONObject(s2);
                    String strOptString = jSONObject2.optString("code");
                    String strOptString2 = jSONObject2.optString("message");
                    if (!TextUtils.equals("200", strOptString)) {
                        if (TextUtils.isEmpty(strOptString2)) {
                            return;
                        }
                        ToastUtil.shortToast(VideoFeedBackPop.this.getContext(), strOptString2);
                    } else {
                        ToastUtil.shortToast(VideoFeedBackPop.this.getContext(), "提交成功");
                        if (VideoFeedBackPop.this.listener != null) {
                            VideoFeedBackPop.this.listener.onConfirm();
                        }
                        VideoFeedBackPop.this.dismiss();
                    }
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.pop_layout_video_feedback;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.ej
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16448c.lambda$onCreate$0(view);
            }
        });
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_show_content);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.ll_input_content);
        linearLayout.setVisibility(this.viewOnly ? 0 : 8);
        linearLayout2.setVisibility(this.viewOnly ? 8 : 0);
        final EditText editText = (EditText) findViewById(R.id.input_et_content);
        TextView textView = (TextView) findViewById(R.id.tv_title);
        TextView textView2 = (TextView) findViewById(R.id.tv_show_content);
        if (this.viewOnly && !TextUtils.isEmpty(this.content)) {
            textView2.setText(this.content);
        }
        ((RecyclerView) findViewById(R.id.rvFeedBackList)).setAdapter(this.mAdapter);
        Iterator<VideoFeedBackBean> it = this.feedBackBeans.iterator();
        while (it.hasNext()) {
            it.next().setStar_level("5");
        }
        this.mAdapter.setList(this.feedBackBeans);
        if (this.viewOnly) {
            textView.setText("已反馈");
            editText.setEnabled(false);
            editText.setFocusable(false);
            editText.setFocusableInTouchMode(false);
            linearLayout2.removeAllViews();
        }
        TextView textView3 = (TextView) findViewById(R.id.tv_submit);
        if (this.viewOnly) {
            textView3.setVisibility(8);
        } else {
            editText.postDelayed(new Runnable() { // from class: com.psychiatrygarden.widget.fj
                @Override // java.lang.Runnable
                public final void run() {
                    VideoFeedBackPop.lambda$onCreate$1(editText);
                }
            }, 500L);
            textView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.gj
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f16529c.lambda$onCreate$2(editText, view);
                }
            });
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onShow() {
        super.onShow();
    }
}
