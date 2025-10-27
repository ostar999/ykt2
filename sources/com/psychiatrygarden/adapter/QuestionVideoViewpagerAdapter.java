package com.psychiatrygarden.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.activity.comment.alipler.AliperCommentActivity;
import com.psychiatrygarden.activity.comment.bean.DiscussStatus;
import com.psychiatrygarden.bean.CourseChapterBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.yikaobang.yixue.R;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class QuestionVideoViewpagerAdapter extends PagerAdapter {
    private Context context;
    private boolean isCeshi;
    Handler mHandler;
    private Map<Integer, View> map;
    private int moudle_type;
    private long question_id;
    private String[] strVideo;

    public QuestionVideoViewpagerAdapter(Context context, String[] strVideo, long question_id, int moudle_type) {
        this.map = new HashMap();
        this.isCeshi = false;
        this.mHandler = new Handler() { // from class: com.psychiatrygarden.adapter.QuestionVideoViewpagerAdapter.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what != 10) {
                    return;
                }
                QuestionVideoViewpagerAdapter.this.getVideoData(msg.arg1);
            }
        };
        this.context = context;
        this.strVideo = strVideo;
        this.question_id = question_id;
        this.moudle_type = moudle_type;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$instantiateItem$0(int i2, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        if (!CommonUtil.isNetworkConnected(this.context)) {
            NewToast.showShort(this.context, "当前无网络连接", 0).show();
            return;
        }
        Message messageObtainMessage = this.mHandler.obtainMessage();
        messageObtainMessage.arg1 = i2;
        messageObtainMessage.what = 10;
        this.mHandler.sendMessage(messageObtainMessage);
    }

    public void destoryHandler() {
        this.mHandler.removeCallbacksAndMessages(null);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void finishUpdate(View arg0) {
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    /* renamed from: getCount */
    public int getSize() {
        return this.strVideo.length;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getItemPosition(Object object) {
        return -2;
    }

    public void getVideoData(final int position) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", this.question_id + "");
        YJYHttpUtils.post(this.context, this.isCeshi ? NetworkRequestsURL.mgetVideosUrl : NetworkRequestsURL.getVideos, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.adapter.QuestionVideoViewpagerAdapter.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                NewToast.showShort(QuestionVideoViewpagerAdapter.this.context, "获取视频失败", 0).show();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        List list = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<CourseChapterBean>>() { // from class: com.psychiatrygarden.adapter.QuestionVideoViewpagerAdapter.2.1
                        }.getType());
                        if (list == null || list.size() <= 0) {
                            NewToast.showShort(QuestionVideoViewpagerAdapter.this.context, "获取视频源失败", 0).show();
                        } else {
                            Intent intent = new Intent();
                            intent.setClass(QuestionVideoViewpagerAdapter.this.context, AliperCommentActivity.class);
                            intent.putExtra("obj_id", ((CourseChapterBean) list.get(position)).getId());
                            intent.putExtra("free_watch_time", ((CourseChapterBean) list.get(position)).getFree_watch_time());
                            intent.putExtra("watch_permission", ((CourseChapterBean) list.get(position)).getWatch_permission());
                            intent.putExtra("expire_str", ((CourseChapterBean) list.get(position)).getExpire_str());
                            intent.putExtra("realVideo", true);
                            intent.putExtra("module_type", QuestionVideoViewpagerAdapter.this.moudle_type);
                            intent.putExtra("vid", ((CourseChapterBean) list.get(position)).getVid());
                            intent.putExtra(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, "real_question_video");
                            intent.putExtra("commentEnum", DiscussStatus.QuestionBankVideo);
                            QuestionVideoViewpagerAdapter.this.context.startActivity(intent);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public Object instantiateItem(final ViewGroup container, final int position) {
        View viewInflate = LayoutInflater.from(this.context).inflate(R.layout.adapter_question_video_item, (ViewGroup) null);
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.iv_play_image_scro);
        try {
            GlideApp.with(imageView.getContext()).load((Object) GlideUtils.generateUrl(CommonUtil.getVideoMd5key(this.strVideo[position]))).apply((BaseRequestOptions<?>) new RequestOptions().override(Integer.MIN_VALUE).disallowHardwareConfig()).into(imageView);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.oe
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14851c.lambda$instantiateItem$0(position, view);
            }
        });
        container.addView(viewInflate);
        this.map.put(Integer.valueOf(position), viewInflate);
        return viewInflate;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(View v2, Object arg1) {
        return v2 == arg1;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public Parcelable saveState() {
        return null;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void startUpdate(View arg0) {
    }

    public QuestionVideoViewpagerAdapter(Context context, String[] strVideo, long question_id, boolean isCeshi, int moudle_type) {
        this.map = new HashMap();
        this.isCeshi = false;
        this.mHandler = new Handler() { // from class: com.psychiatrygarden.adapter.QuestionVideoViewpagerAdapter.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what != 10) {
                    return;
                }
                QuestionVideoViewpagerAdapter.this.getVideoData(msg.arg1);
            }
        };
        this.context = context;
        this.strVideo = strVideo;
        this.question_id = question_id;
        this.isCeshi = isCeshi;
        this.moudle_type = moudle_type;
    }
}
