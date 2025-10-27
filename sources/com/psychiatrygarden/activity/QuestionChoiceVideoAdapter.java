package com.psychiatrygarden.activity;

import android.content.Intent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.activity.comment.alipler.AliperCommentActivity;
import com.psychiatrygarden.activity.comment.bean.DiscussStatus;
import com.psychiatrygarden.activity.online.bean.QuestionDetailBean;
import com.psychiatrygarden.bean.CourseChapterBean;
import com.psychiatrygarden.http.QuestionDataRequest;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.yikaobang.yixue.R;
import java.util.List;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class QuestionChoiceVideoAdapter extends BaseQuickAdapter<QuestionDetailBean.VideoListDTO, BaseViewHolder> {
    private String question_id;

    public QuestionChoiceVideoAdapter(int layoutResId, @Nullable List<QuestionDetailBean.VideoListDTO> data, String question_id) {
        super(layoutResId, data);
        this.question_id = question_id;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$0(View view) {
        String str = (String) view.getTag();
        QuestionDataRequest.getIntance(view.getContext()).questionVideo(this.question_id + "", str, new QuestionDataCallBack<String>() { // from class: com.psychiatrygarden.activity.QuestionChoiceVideoAdapter.1
            @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg, String requstUrl) {
            }

            @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
            public void onStart(String requstUrl) {
            }

            @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
            public void onSuccess(String s2, String requstUrl) {
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        CourseChapterBean courseChapterBean = (CourseChapterBean) new Gson().fromJson(jSONObject.optString("data"), CourseChapterBean.class);
                        if (courseChapterBean != null) {
                            Intent intent = new Intent();
                            intent.setClass(QuestionChoiceVideoAdapter.this.getContext(), AliperCommentActivity.class);
                            intent.putExtra("obj_id", courseChapterBean.getId());
                            intent.putExtra("free_watch_time", courseChapterBean.getFree_watch_time());
                            intent.putExtra("watch_permission", courseChapterBean.getWatch_permission());
                            intent.putExtra("expire_str", courseChapterBean.getExpire_str());
                            intent.putExtra("realVideo", true);
                            intent.putExtra("module_type", 10);
                            intent.putExtra("vid", courseChapterBean.getVid());
                            intent.putExtra(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, "real_question_video");
                            intent.putExtra("commentEnum", DiscussStatus.QuestionBankVideo);
                            QuestionChoiceVideoAdapter.this.getContext().startActivity(intent);
                        } else {
                            NewToast.showShort(QuestionChoiceVideoAdapter.this.getContext(), "获取视频源失败", 0).show();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder holder, QuestionDetailBean.VideoListDTO item) {
        GlideApp.with(getContext()).load((Object) GlideUtils.generateUrl(CommonUtil.getVideoMd5key(item.getThumb()))).apply((BaseRequestOptions<?>) new RequestOptions().override(Integer.MIN_VALUE).disallowHardwareConfig()).into((RoundedImageView) holder.getView(R.id.iv_play_image_scro));
        holder.itemView.setTag(item.getVideo_id());
        holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.lf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12665c.lambda$convert$0(view);
            }
        });
    }
}
