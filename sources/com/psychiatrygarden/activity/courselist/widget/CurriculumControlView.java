package com.psychiatrygarden.activity.courselist.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity;
import com.psychiatrygarden.activity.comment.bean.DiscussStatus;
import com.psychiatrygarden.activity.courselist.YkBManager;
import com.psychiatrygarden.activity.courselist.bean.CurriculumLiveBean;
import com.psychiatrygarden.activity.courselist.bean.CurriculumScheduleCardBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.yikaobang.yixue.R;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes5.dex */
public class CurriculumControlView {

    public interface CurricuIml {
        void clickMethod();
    }

    public CurriculumControlView(final String isPremision, final String course_id, final Context context, @NotNull final BaseViewHolder baseViewHolder, final CurriculumLiveBean.DataDTO curriculumLiveBean, String is_hide_teacher, final CurricuIml curricuIml) throws NumberFormatException {
        String str;
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.curriculum_vid, context, "0");
        ImageView imageView = (ImageView) baseViewHolder.findView(R.id.statusimg);
        ImageView imageView2 = (ImageView) baseViewHolder.getView(R.id.lockcourse);
        TextView textView = (TextView) baseViewHolder.findView(R.id.title);
        TextView textView2 = (TextView) baseViewHolder.findView(R.id.detailTxt);
        TextView textView3 = (TextView) baseViewHolder.findView(R.id.stateTv);
        textView.setText(curriculumLiveBean.getTitle());
        boolean z2 = SkinManager.getCurrentSkinType(context) == 1;
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        imageView2.setVisibility("0".equals(isPremision) ? 0 : 8);
        imageView2.setImageResource(("0".equals(isPremision) && "1".equals(curriculumLiveBean.getIs_free_watch())) ? R.drawable.tryvideoimg : R.drawable.lockcourse);
        if (!"1".equals(is_hide_teacher) && !TextUtils.isEmpty(is_hide_teacher)) {
            spannableStringBuilder.append(curriculumLiveBean.getTeacher(), new ForegroundColorSpan(Color.parseColor(!z2 ? "#A8A7A7" : "#575F79")), 18).append((CharSequence) " ");
        }
        boolean zEquals = "0".equals(curriculumLiveBean.getLiveStatus());
        str = "#B2575C";
        int i2 = R.color.tertiary_text_color_night;
        if (zEquals) {
            textView.setTextColor(ContextCompat.getColor(context, z2 ? i2 : R.color.tertiary_text_color));
            imageView.setImageResource(R.drawable.waitimg);
            textView3.setVisibility(8);
            spannableStringBuilder.append((CharSequence) curriculumLiveBean.getLiveTimeStr()).append((CharSequence) " ");
            try {
                long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
                long j2 = 86400 + jCurrentTimeMillis;
                if (jCurrentTimeMillis >= curriculumLiveBean.getStartLiveTime() || j2 <= curriculumLiveBean.getStartLiveTime()) {
                    spannableStringBuilder.append((CharSequence) "未开始").append((CharSequence) " ");
                } else {
                    if (SkinManager.getCurrentSkinType(context) != 1) {
                        str = "#DD594A";
                    }
                    spannableStringBuilder.append("即将直播", new ForegroundColorSpan(Color.parseColor(str)), 18).append((CharSequence) " ");
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else {
            boolean zEquals2 = "1".equals(curriculumLiveBean.getLiveStatus());
            int i3 = R.color.first_text_color_night;
            if (zEquals2) {
                textView.setTextColor(ContextCompat.getColor(context, z2 ? i3 : R.color.first_text_color));
                imageView.setImageResource(R.drawable.load_lv2_anmi);
                if (!((Animatable) imageView.getDrawable()).isRunning()) {
                    ((Animatable) imageView.getDrawable()).start();
                }
                spannableStringBuilder.append((CharSequence) curriculumLiveBean.getLiveTimeStr());
                imageView2.setVisibility(8);
                textView3.setVisibility(0);
            } else {
                boolean zEquals3 = "2".equals(curriculumLiveBean.getLiveStatus());
                int i4 = R.drawable.clipnoimg_night_new;
                if (zEquals3) {
                    textView.setTextColor(ContextCompat.getColor(context, z2 ? i2 : R.color.tertiary_text_color));
                    imageView.setImageResource(z2 ? i4 : R.drawable.clipnoimg);
                    textView3.setVisibility(8);
                    spannableStringBuilder.append("直播已结束", new ForegroundColorSpan(Color.parseColor(z2 ? "#575F79" : "#A8A7A7")), 18);
                } else if ("3".equals(curriculumLiveBean.getLiveStatus())) {
                    textView.setTextColor(ContextCompat.getColor(context, z2 ? i3 : R.color.first_text_color));
                    imageView.setImageResource(z2 ? i4 : R.drawable.goneimg);
                    textView3.setVisibility(8);
                    spannableStringBuilder.append((CharSequence) curriculumLiveBean.getLiveTimeStr()).append((CharSequence) " ");
                    spannableStringBuilder.append((CharSequence) "直播回放").append((CharSequence) " ");
                    if (getVideoStatus(curriculumLiveBean.getVid()) == 5) {
                        spannableStringBuilder.append((CharSequence) "本地").append((CharSequence) " ");
                    } else if (getVideoStatus(curriculumLiveBean.getVid()) == 6) {
                        spannableStringBuilder.append((CharSequence) "本地视频不存在").append((CharSequence) " ");
                    }
                    if (strConfig.equals(curriculumLiveBean.getVid())) {
                        spannableStringBuilder.append("上次观看", new ForegroundColorSpan(Color.parseColor(SkinManager.getCurrentSkinType(context) != 1 ? "#DD594A" : "#B2575C")), 18);
                    } else {
                        double d3 = Double.parseDouble(curriculumLiveBean.getDuration());
                        double d4 = Double.parseDouble(curriculumLiveBean.getSee());
                        if (d4 > 0.0d) {
                            if (d3 - d4 < 1.0d) {
                                spannableStringBuilder.append((CharSequence) "已学完");
                            } else {
                                int iIntValue = new BigDecimal((int) ((d4 / (d3 <= 0.0d ? 1.0d : d3)) * 100.0d)).setScale(0, RoundingMode.HALF_UP).intValue();
                                if (iIntValue < 1) {
                                    spannableStringBuilder.append((CharSequence) "已学习1%");
                                } else {
                                    spannableStringBuilder.append((CharSequence) "已学习").append((CharSequence) String.valueOf(iIntValue)).append((CharSequence) "%");
                                }
                            }
                        }
                    }
                }
            }
        }
        textView2.setText(spannableStringBuilder);
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.widget.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12194c.lambda$new$0(isPremision, curriculumLiveBean, curricuIml, context, baseViewHolder, course_id, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(String str, CurriculumLiveBean.DataDTO dataDTO, CurricuIml curricuIml, Context context, BaseViewHolder baseViewHolder, String str2, View view) {
        if ("0".equals(str) && "0".equals(dataDTO.getIs_free_watch())) {
            curricuIml.clickMethod();
            return;
        }
        if ("0".equals(dataDTO.getLiveStatus())) {
            long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
            ToastUtil.shortToast(context, "" + ((jCurrentTimeMillis >= dataDTO.getStartLiveTime() || 86400 + jCurrentTimeMillis <= dataDTO.getStartLiveTime()) ? "未开始" : "即将直播"));
            return;
        }
        if ("1".equals(dataDTO.getLiveStatus())) {
            CommonUtil.launchLiveing(context, dataDTO.getUserId(), dataDTO.getAppId(), dataDTO.getAppSecret(), dataDTO.getRoomId());
            return;
        }
        if ("2".equals(dataDTO.getLiveStatus())) {
            ToastUtil.shortToast(context, "直播已结束");
            return;
        }
        if ("3".equals(dataDTO.getLiveStatus())) {
            SharePreferencesUtils.writeStrConfig(CommonParameter.curriculum_vid, "" + dataDTO.getVid(), context);
            getTheRest(dataDTO.getVid() + "", baseViewHolder);
            Intent intent = new Intent();
            intent.setClass(context, AliPlayerVideoPlayActivity.class);
            intent.putExtra("obj_id", dataDTO.getId() + "");
            intent.putExtra("chapter_id", str2 + "");
            intent.putExtra("free_watch_time", dataDTO.getIs_free_watch() + "");
            intent.putExtra("watch_permission", str);
            intent.putExtra("expire_str", "");
            intent.putExtra("module_type", 8);
            intent.putExtra("title", dataDTO.getTitle());
            intent.putExtra("vid", dataDTO.getVid() + "");
            intent.putExtra(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, "");
            intent.putExtra("seeDuration", dataDTO.getSee());
            intent.putExtra("commentEnum", DiscussStatus.CourseReview);
            intent.putExtra(AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER, dataDTO.getCover());
            context.startActivity(intent);
        }
    }

    public void getTheRest(String vid, BaseViewHolder baseViewHolder) {
        ArrayList arrayList = new ArrayList();
        BaseQuickAdapter baseQuickAdapter = (BaseQuickAdapter) baseViewHolder.getBindingAdapter();
        if (baseQuickAdapter == null || baseQuickAdapter.getData().isEmpty()) {
            return;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < baseQuickAdapter.getData().size(); i3++) {
            CurriculumLiveBean.DataDTO dataDTO = (CurriculumLiveBean.DataDTO) baseQuickAdapter.getData().get(i3);
            CurriculumScheduleCardBean.DataDTO.ChildrenDTO childrenDTO = new CurriculumScheduleCardBean.DataDTO.ChildrenDTO();
            childrenDTO.setId(dataDTO.getId());
            childrenDTO.setSee(dataDTO.getSee());
            childrenDTO.setVid(dataDTO.getVid());
            childrenDTO.setIsFreeWatch(dataDTO.getIs_free_watch() + "");
            arrayList.add(childrenDTO);
        }
        int i4 = 0;
        while (true) {
            if (i4 >= arrayList.size()) {
                break;
            }
            if (vid.equals(((CurriculumScheduleCardBean.DataDTO.ChildrenDTO) arrayList.get(i4)).getVid())) {
                i2 = i4 + 1;
                break;
            }
            i4++;
        }
        ProjectApp.mPlayerVideo.clear();
        while (i2 < arrayList.size()) {
            if (arrayList.get(i2) != null && !TextUtils.isEmpty(((CurriculumScheduleCardBean.DataDTO.ChildrenDTO) arrayList.get(i2)).getVid())) {
                ProjectApp.mPlayerVideo.add((CurriculumScheduleCardBean.DataDTO.ChildrenDTO) arrayList.get(i2));
            }
            i2++;
        }
    }

    public int getVideoStatus(String vid) {
        if (YkBManager.getInstance().mDownloadMediaLists == null) {
            return 0;
        }
        for (int i2 = 0; i2 < YkBManager.getInstance().mDownloadMediaLists.size(); i2++) {
            try {
                if (vid.equals(YkBManager.getInstance().mDownloadMediaLists.get(i2).getVid()) && YkBManager.getInstance().mDownloadMediaLists.get(i2).getProgress() == 100) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(YkBManager.getInstance().mDownloadMediaLists.get(i2).getSavePath());
                    sb.append("");
                    return !new File(sb.toString()).exists() ? 6 : 5;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return 0;
    }
}
