package com.psychiatrygarden.activity.setting;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.aliPlayer.utils.AliPlayUtils;
import com.psychiatrygarden.aliPlayer.utils.VideoDefinition;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.AliyunEvent;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class VideoDefinitionSelectActivity extends BaseActivity {
    public static final String CONFIG_TYPE = "CONFIG_TYPE";
    public static final int TYPE_DOWNLOAD = 1;
    public static final int TYPE_PLAY = 0;
    public static final int TYPE_WRONG_QUESTION = 2;
    private String currentConfig;
    private final BaseQuickAdapter<VideoDefinition, BaseViewHolder> mAdapter = new BaseQuickAdapter<VideoDefinition, BaseViewHolder>(R.layout.item_video_definition) { // from class: com.psychiatrygarden.activity.setting.VideoDefinitionSelectActivity.1
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NonNull BaseViewHolder holder, VideoDefinition item) {
            holder.setText(R.id.tv_name, item.getLabel());
            ((TextView) holder.getView(R.id.tv_name)).setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable) null, (Drawable) null, TextUtils.equals(item.getDefinition(), VideoDefinitionSelectActivity.this.currentConfig) ? ContextCompat.getDrawable(getContext(), R.drawable.tiku_select_press_gou) : null, (Drawable) null);
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(int i2, RecyclerView recyclerView, BaseQuickAdapter baseQuickAdapter, View view, int i3) {
        if (i2 == 1) {
            AliPlayUtils.saveDownloadVideoDefinition(this.mAdapter.getItem(i3).getDefinition(), recyclerView.getContext());
            AliPlayUtils.saveDownloadVideoDefinitionLabel(this.mAdapter.getItem(i3).getLabel(), recyclerView.getContext());
        } else {
            AliPlayUtils.savePlayVideoDefinition(this.mAdapter.getItem(i3).getDefinition(), recyclerView.getContext());
            AliPlayUtils.savePlayVideoDefinitionLabel(this.mAdapter.getItem(i3).getLabel(), recyclerView.getContext());
        }
        this.currentConfig = this.mAdapter.getItem(i3).getDefinition();
        this.mAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(BaseQuickAdapter baseQuickAdapter, BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
        settingErrorConfig(i2 == 0 ? "1" : "2", baseQuickAdapter);
    }

    private void settingErrorConfig(final String status, final BaseQuickAdapter<String, BaseViewHolder> adapter) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("status", status);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.settingErrorConfig, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.setting.VideoDefinitionSelectActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                VideoDefinitionSelectActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                VideoDefinitionSelectActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        VideoDefinitionSelectActivity.this.currentConfig = status.equals("1") ? "做对移除" : "做对不移除";
                        adapter.notifyDataSetChanged();
                        UserConfig.getInstance().getUser().setError_set(status);
                    } else {
                        ToastUtil.shortToast(VideoDefinitionSelectActivity.this, jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                VideoDefinitionSelectActivity.this.hideProgressDialog();
            }
        });
        AliyunEvent aliyunEvent = AliyunEvent.ErrorQuestionSetting;
        CommonUtil.addLog(aliyunEvent.getKey(), aliyunEvent.getValue(), System.currentTimeMillis() + "", "", "", "", "", "2");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        final int intExtra = getIntent().getIntExtra(CONFIG_TYPE, 0);
        this.currentConfig = intExtra == 0 ? AliPlayUtils.getPlayVideoDefinition(this) : AliPlayUtils.getDownloadVideoDefinition(this);
        setTitle(intExtra == 0 ? "播放清晰度" : intExtra == 1 ? "下载清晰度" : "错题设置");
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvList);
        if (intExtra != 2) {
            recyclerView.setAdapter(this.mAdapter);
            ArrayList arrayList = new ArrayList(0);
            arrayList.add(VideoDefinition.FD);
            arrayList.add(VideoDefinition.LD);
            arrayList.add(VideoDefinition.SD);
            arrayList.add(VideoDefinition.HD);
            this.mAdapter.setList(arrayList);
            this.mAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.setting.d1
                @Override // com.chad.library.adapter.base.listener.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                    this.f13846c.lambda$init$0(intExtra, recyclerView, baseQuickAdapter, view, i2);
                }
            });
            return;
        }
        ArrayList arrayList2 = new ArrayList(2);
        this.currentConfig = UserConfig.getInstance().getUser().getError_set().equals("1") ? "做对移除" : "做对不移除";
        arrayList2.add("做对移除");
        arrayList2.add("做对不移除");
        final BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_video_definition) { // from class: com.psychiatrygarden.activity.setting.VideoDefinitionSelectActivity.2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(@NonNull BaseViewHolder holder, String item) {
                holder.setText(R.id.tv_name, item);
                ((TextView) holder.getView(R.id.tv_name)).setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable) null, (Drawable) null, TextUtils.equals(item, VideoDefinitionSelectActivity.this.currentConfig) ? ContextCompat.getDrawable(getContext(), R.drawable.tiku_select_press_gou) : null, (Drawable) null);
            }
        };
        recyclerView.setAdapter(baseQuickAdapter);
        baseQuickAdapter.setList(arrayList2);
        baseQuickAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.setting.e1
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                this.f13851c.lambda$init$1(baseQuickAdapter, baseQuickAdapter2, view, i2);
            }
        });
        recyclerView.setAdapter(baseQuickAdapter);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_video_definition_select);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
