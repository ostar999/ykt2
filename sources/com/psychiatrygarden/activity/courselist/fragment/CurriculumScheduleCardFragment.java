package com.psychiatrygarden.activity.courselist.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.motion.widget.Key;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.aliyun.player.alivcplayerexpand.util.TimeFormater;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.ActCourseOrGoodsDetail;
import com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity;
import com.psychiatrygarden.activity.comment.bean.DiscussStatus;
import com.psychiatrygarden.activity.courselist.CurriculumDownLoadManageActivity;
import com.psychiatrygarden.activity.courselist.YkBManager;
import com.psychiatrygarden.activity.courselist.bean.CurriculumLiveBean;
import com.psychiatrygarden.activity.courselist.bean.CurriculumScheduleCardBean;
import com.psychiatrygarden.activity.courselist.widget.CurriculumControlView;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.activity.vip.bean.MemCenterBean;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.EventBusMessage;
import com.psychiatrygarden.bean.PipPlayEvent;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.Md5Util;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CurriculumScheduleCardFragment extends BaseFragment {
    public String activity_id;
    public CurriculumScheduleCardExpandNodeAdapter adapter;
    public ImageView closevideo;
    public String course_id;
    public String cover;
    public String have_chapter;
    private boolean isPipModePlay;
    public String is_hide_teacher;
    public RelativeLayout liveingRel;
    public RecyclerView recycleId;
    public SmartRefreshLayout refreshLayout;
    public String title;
    public TextView titleTv;
    public String type;
    public int EXPAND_COLLAPSE_PAYLOAD = 110;
    public ArrayList<String> set_meal_ids = new ArrayList<>();
    public String isPremision = "0";
    public int position = 0;

    /* renamed from: com.psychiatrygarden.activity.courselist.fragment.CurriculumScheduleCardFragment$2, reason: invalid class name */
    public class AnonymousClass2 extends AjaxCallBack<String> {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(String str) {
            List<CurriculumScheduleCardBean.DataDTO> data = ((CurriculumScheduleCardBean) new Gson().fromJson(str, CurriculumScheduleCardBean.class)).getData();
            for (CurriculumScheduleCardBean.DataDTO dataDTO : data) {
                List<CurriculumScheduleCardBean.DataDTO.ChildrenDTO> children = dataDTO.getChildren();
                if (children != null && !children.isEmpty()) {
                    for (CurriculumScheduleCardBean.DataDTO.ChildrenDTO childrenDTO : children) {
                        childrenDTO.setStatus(CurriculumScheduleCardFragment.this.getVideoStatus(childrenDTO.getVid()));
                        childrenDTO.setChapterTitle(dataDTO.getTitle());
                        childrenDTO.setPid(dataDTO.getId());
                        if (TextUtils.equals("1", childrenDTO.getVideo_type())) {
                            if (TextUtils.equals("1", childrenDTO.getLive().getLiveStatus())) {
                                dataDTO.setLive_status("1");
                                CurriculumScheduleCardFragment.this.showLivingView(childrenDTO.getLive());
                            }
                        } else if (TextUtils.equals("0", CurriculumScheduleCardFragment.this.isPremision) && TextUtils.equals("1", childrenDTO.getIsFreeWatch())) {
                            dataDTO.setIsFreeWatch("1");
                        }
                    }
                }
            }
            CurriculumScheduleCardFragment curriculumScheduleCardFragment = CurriculumScheduleCardFragment.this;
            curriculumScheduleCardFragment.adapter.setList(curriculumScheduleCardFragment.getAdapterData(data));
            CurriculumScheduleCardFragment.this.scrollViewTop();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            CurriculumScheduleCardFragment.this.refreshLayout.finishRefresh(false);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onStart() {
            super.onStart();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(final String t2) {
            super.onSuccess((AnonymousClass2) t2);
            CurriculumScheduleCardFragment.this.refreshLayout.finishRefresh(true);
            try {
                JSONObject jSONObject = new JSONObject(t2);
                if (TextUtils.equals("200", jSONObject.optString("code"))) {
                    CurriculumScheduleCardFragment.this.refreshLayout.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.courselist.fragment.q3
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f12023c.lambda$onSuccess$0(t2);
                        }
                    }, 450L);
                    return;
                }
                String strOptString = jSONObject.optString("message");
                if (TextUtils.isEmpty(strOptString)) {
                    return;
                }
                CurriculumScheduleCardFragment.this.AlertToast(strOptString);
            } catch (Exception e2) {
                e2.printStackTrace();
                CurriculumScheduleCardFragment.this.refreshLayout.finishRefresh(false);
            }
        }
    }

    public class CurriculumScheduleCardExpandNodeAdapter extends BaseNodeAdapter {
        public CurriculumScheduleCardExpandNodeAdapter() {
            addFullSpanNodeProvider(CurriculumScheduleCardFragment.this.new FirstNoteProvider());
            addNodeProvider(CurriculumScheduleCardFragment.this.new SecondNoteProvider());
        }

        @Override // com.chad.library.adapter.base.BaseProviderMultiAdapter
        public int getItemType(@NotNull List<? extends BaseNode> list, int i2) {
            BaseNode baseNode = list.get(i2);
            if (baseNode instanceof CurriculumScheduleCardBean.DataDTO) {
                return 0;
            }
            return baseNode instanceof CurriculumScheduleCardBean.DataDTO.ChildrenDTO ? 1 : -1;
        }
    }

    public class FirstNoteProvider extends BaseNodeProvider {
        public FirstNoteProvider() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Type inference failed for: r0v11, types: [com.chad.library.adapter.base.BaseNodeAdapter] */
        /* JADX WARN: Type inference failed for: r3v4, types: [com.chad.library.adapter.base.BaseNodeAdapter] */
        public /* synthetic */ void lambda$convert$0(CurriculumScheduleCardBean.DataDTO dataDTO, BaseViewHolder baseViewHolder, View view) {
            if (dataDTO.getChildNode() == null || dataDTO.getChildNode().size() == 0) {
                ToastUtil.shortToast(CurriculumScheduleCardFragment.this.getActivity(), "无视频");
                return;
            }
            if (!dataDTO.getIsExpanded() && getAdapter2() != null) {
                for (int i2 = 0; i2 < getAdapter2().getData().size(); i2++) {
                    if (((CurriculumScheduleCardBean.DataDTO) getAdapter2().getData().get(i2)).getIsExpanded()) {
                        getAdapter2().expandOrCollapse(i2, true, true, Integer.valueOf(CurriculumScheduleCardFragment.this.EXPAND_COLLAPSE_PAYLOAD));
                    }
                }
            }
            getAdapter2().expandOrCollapse(baseViewHolder.getBindingAdapterPosition(), true, true, Integer.valueOf(CurriculumScheduleCardFragment.this.EXPAND_COLLAPSE_PAYLOAD));
        }

        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
        public int getItemViewType() {
            return 0;
        }

        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
        public int getLayoutId() {
            return R.layout.layout_curriculum_schedule_card_frist;
        }

        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
        public /* bridge */ /* synthetic */ void convert(@NotNull BaseViewHolder helper, BaseNode item, @NotNull List payloads) {
            convert2(helper, item, (List<?>) payloads);
        }

        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
        public void convert(@NotNull final BaseViewHolder baseViewHolder, BaseNode baseNode) {
            boolean z2;
            final CurriculumScheduleCardBean.DataDTO dataDTO = (CurriculumScheduleCardBean.DataDTO) baseNode;
            TextView textView = (TextView) baseViewHolder.getView(R.id.title);
            TextView textView2 = (TextView) baseViewHolder.getView(R.id.tryTextview);
            ImageView imageView = (ImageView) baseViewHolder.getView(R.id.croteimg);
            ImageView imageView2 = (ImageView) baseViewHolder.getView(R.id.lottieview);
            ImageView imageView3 = (ImageView) baseViewHolder.getView(R.id.iv_last_watch);
            String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.curriculum_vid, CurriculumScheduleCardFragment.this.getActivity(), "0");
            List<CurriculumScheduleCardBean.DataDTO.ChildrenDTO> children = dataDTO.getChildren();
            if (children != null && !children.isEmpty()) {
                Iterator<CurriculumScheduleCardBean.DataDTO.ChildrenDTO> it = children.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z2 = false;
                        break;
                    } else if (TextUtils.equals(it.next().getVid(), strConfig)) {
                        z2 = true;
                        break;
                    }
                }
                imageView3.setVisibility((dataDTO.getIsExpanded() || !z2) ? 8 : 0);
            } else {
                imageView3.setVisibility(8);
                z2 = false;
            }
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) baseViewHolder.getView(R.id.rl_content).getLayoutParams();
            layoutParams.topMargin = (children == null || children.isEmpty() || dataDTO.getIsExpanded() || !z2) ? 0 : CommonUtil.dip2px(CurriculumScheduleCardFragment.this.requireContext(), 6.0f);
            baseViewHolder.getView(R.id.rl_content).setLayoutParams(layoutParams);
            textView.setText(dataDTO.getTitle());
            baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.r3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f12030c.lambda$convert$0(dataDTO, baseViewHolder, view);
                }
            });
            if (dataDTO.getChildNode() != null && dataDTO.getChildNode().size() != 0) {
                imageView.setVisibility(0);
                if (dataDTO.getIsExpanded()) {
                    if (SkinManager.getCurrentSkinType(CurriculumScheduleCardFragment.this.getActivity()) == 1) {
                        imageView.setImageResource(R.drawable.ctopimg_night);
                    } else {
                        imageView.setImageResource(R.drawable.ctopimg);
                    }
                    baseViewHolder.itemView.setSelected(true);
                    textView2.setVisibility(8);
                    CurriculumScheduleCardFragment.this.hideLottieView(imageView2);
                    return;
                }
                if ("1".equals(dataDTO.getIsFreeWatch())) {
                    textView2.setVisibility(0);
                } else {
                    textView2.setVisibility(8);
                }
                if ("1".equals(dataDTO.getLive_status())) {
                    CurriculumScheduleCardFragment.this.showLottieView(imageView2);
                } else {
                    CurriculumScheduleCardFragment.this.hideLottieView(imageView2);
                }
                if (SkinManager.getCurrentSkinType(CurriculumScheduleCardFragment.this.getActivity()) == 1) {
                    imageView.setImageResource(R.drawable.cbottomimg_night);
                } else {
                    imageView.setImageResource(R.drawable.cbottomimg);
                }
                baseViewHolder.itemView.setSelected(false);
                return;
            }
            if (SkinManager.getCurrentSkinType(CurriculumScheduleCardFragment.this.getActivity()) == 1) {
                imageView.setImageResource(R.drawable.cbottomimg_night);
            } else {
                imageView.setImageResource(R.drawable.cbottomimg);
            }
            imageView.setVisibility(4);
            baseViewHolder.itemView.setSelected(false);
            textView2.setVisibility(8);
            CurriculumScheduleCardFragment.this.hideLottieView(imageView2);
        }

        /* renamed from: convert, reason: avoid collision after fix types in other method */
        public void convert2(@NotNull BaseViewHolder helper, BaseNode item, @NotNull List<?> payloads) {
            boolean z2;
            super.convert(helper, (BaseViewHolder) item, (List<? extends Object>) payloads);
            CurriculumScheduleCardBean.DataDTO dataDTO = (CurriculumScheduleCardBean.DataDTO) item;
            if (dataDTO.getChildNode() == null || dataDTO.getChildNode().size() == 0) {
                return;
            }
            Iterator<?> it = payloads.iterator();
            while (true) {
                z2 = true;
                if (!it.hasNext()) {
                    break;
                }
                Object next = it.next();
                if (next instanceof Integer) {
                    int iIntValue = ((Integer) next).intValue();
                    CurriculumScheduleCardFragment curriculumScheduleCardFragment = CurriculumScheduleCardFragment.this;
                    if (iIntValue == curriculumScheduleCardFragment.EXPAND_COLLAPSE_PAYLOAD) {
                        curriculumScheduleCardFragment.setArrowSpin(helper, dataDTO, true);
                    }
                }
            }
            ImageView imageView = (ImageView) helper.getView(R.id.iv_last_watch);
            String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.curriculum_vid, CurriculumScheduleCardFragment.this.getActivity(), "0");
            List<CurriculumScheduleCardBean.DataDTO.ChildrenDTO> children = dataDTO.getChildren();
            int i2 = 8;
            int iDip2px = 0;
            if (children != null && !children.isEmpty()) {
                Iterator<CurriculumScheduleCardBean.DataDTO.ChildrenDTO> it2 = children.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        z2 = false;
                        break;
                    } else if (TextUtils.equals(it2.next().getVid(), strConfig)) {
                        break;
                    }
                }
                if (!dataDTO.getIsExpanded() && z2) {
                    i2 = 0;
                }
                imageView.setVisibility(i2);
            } else {
                imageView.setVisibility(8);
                z2 = false;
            }
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) helper.getView(R.id.rl_content).getLayoutParams();
            if (children != null && !children.isEmpty() && !dataDTO.getIsExpanded() && z2) {
                iDip2px = CommonUtil.dip2px(CurriculumScheduleCardFragment.this.requireContext(), 6.0f);
            }
            layoutParams.topMargin = iDip2px;
            helper.getView(R.id.rl_content).setLayoutParams(layoutParams);
        }
    }

    public class SecondNoteProvider extends BaseNodeProvider {
        public SecondNoteProvider() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(CurriculumScheduleCardBean.DataDTO.ChildrenDTO childrenDTO, View view) {
            if (CurriculumScheduleCardFragment.this.isAdded() && CurriculumScheduleCardFragment.this.getActivity() != null) {
                if ("0".equals(CurriculumScheduleCardFragment.this.isPremision) && "0".equals(childrenDTO.getIsFreeWatch())) {
                    CurriculumScheduleCardFragment.this.verifyPermissions(childrenDTO, 0);
                } else {
                    CurriculumScheduleCardFragment.this.goToVideoActivity(childrenDTO);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$1(CurriculumScheduleCardBean.DataDTO.ChildrenDTO childrenDTO) {
            CurriculumScheduleCardFragment.this.verifyPermissions(childrenDTO, 1);
        }

        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
        public int getItemViewType() {
            return 1;
        }

        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
        public int getLayoutId() {
            return R.layout.layout_curriculum_schedule_card_secend;
        }

        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
        public /* bridge */ /* synthetic */ void convert(@NotNull BaseViewHolder helper, BaseNode item, @NotNull List payloads) throws NumberFormatException {
            convert2(helper, item, (List<?>) payloads);
        }

        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
        public void convert(@NotNull BaseViewHolder baseViewHolder, BaseNode baseNode) throws NumberFormatException {
            List<CurriculumScheduleCardBean.DataDTO.ChildrenDTO> children;
            final CurriculumScheduleCardBean.DataDTO.ChildrenDTO childrenDTO = (CurriculumScheduleCardBean.DataDTO.ChildrenDTO) baseNode;
            if ("2".equals(childrenDTO.getVideo_type())) {
                ImageView imageView = (ImageView) baseViewHolder.getView(R.id.statusimg);
                ImageView imageView2 = (ImageView) baseViewHolder.getView(R.id.lockcourse);
                baseViewHolder.getView(R.id.line);
                String pid = childrenDTO.getPid();
                List<BaseNode> data = CurriculumScheduleCardFragment.this.adapter.getData();
                int i2 = 0;
                while (true) {
                    if (i2 >= data.size()) {
                        break;
                    }
                    if (data.get(i2) instanceof CurriculumScheduleCardBean.DataDTO) {
                        CurriculumScheduleCardBean.DataDTO dataDTO = (CurriculumScheduleCardBean.DataDTO) data.get(i2);
                        if (TextUtils.equals(pid, dataDTO.getId()) && (children = dataDTO.getChildren()) != null && !children.isEmpty()) {
                            children.size();
                            break;
                        }
                    }
                    i2++;
                }
                if ("1".equals(CurriculumScheduleCardFragment.this.have_chapter)) {
                    if (SkinManager.getCurrentSkinType(CurriculumScheduleCardFragment.this.getActivity()) == 1) {
                        imageView.setImageResource(R.drawable.goneimg_night);
                    } else {
                        imageView.setImageResource(R.drawable.goneimg);
                    }
                } else if (SkinManager.getCurrentSkinType(CurriculumScheduleCardFragment.this.getActivity()) == 1) {
                    imageView.setImageResource(R.drawable.playloadimg_night);
                } else {
                    imageView.setImageResource(R.drawable.playloadimg);
                }
                if ("0".equals(CurriculumScheduleCardFragment.this.isPremision)) {
                    imageView2.setVisibility(0);
                    if ("0".equals(CurriculumScheduleCardFragment.this.isPremision) && "1".equals(childrenDTO.getIsFreeWatch())) {
                        imageView2.setImageResource(SkinManager.getCurrentSkinType(CurriculumScheduleCardFragment.this.getActivity()) == 0 ? R.drawable.tryvideoimg : R.drawable.tryvideoimg_night);
                    } else {
                        imageView2.setImageResource(SkinManager.getCurrentSkinType(getContext()) == 0 ? R.drawable.lockcourse : R.drawable.lockcourse_night);
                    }
                } else {
                    imageView2.setVisibility(8);
                }
                ((TextView) baseViewHolder.getView(R.id.stateTv)).setVisibility(8);
                TextView textView = (TextView) baseViewHolder.getView(R.id.title);
                TextView textView2 = (TextView) baseViewHolder.getView(R.id.detailTxt);
                textView.setText(childrenDTO.getTitle());
                CurriculumScheduleCardFragment.this.showHintStr(childrenDTO, textView2);
                baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.s3
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f12038c.lambda$convert$0(childrenDTO, view);
                    }
                });
                return;
            }
            CurriculumScheduleCardFragment curriculumScheduleCardFragment = CurriculumScheduleCardFragment.this;
            new CurriculumControlView(curriculumScheduleCardFragment.isPremision, curriculumScheduleCardFragment.course_id, curriculumScheduleCardFragment.getActivity(), baseViewHolder, childrenDTO.getLive(), CurriculumScheduleCardFragment.this.is_hide_teacher, new CurriculumControlView.CurricuIml() { // from class: com.psychiatrygarden.activity.courselist.fragment.t3
                @Override // com.psychiatrygarden.activity.courselist.widget.CurriculumControlView.CurricuIml
                public final void clickMethod() {
                    this.f12043a.lambda$convert$1(childrenDTO);
                }
            });
        }

        /* renamed from: convert, reason: avoid collision after fix types in other method */
        public void convert2(@NotNull BaseViewHolder helper, BaseNode item, @NotNull List<?> payloads) throws NumberFormatException {
            super.convert(helper, (BaseViewHolder) item, (List<? extends Object>) payloads);
            CurriculumScheduleCardBean.DataDTO.ChildrenDTO childrenDTO = (CurriculumScheduleCardBean.DataDTO.ChildrenDTO) item;
            if (childrenDTO == null) {
                return;
            }
            TextView textView = (TextView) helper.getView(R.id.detailTxt);
            for (Object obj : payloads) {
                if ((obj instanceof Integer) && ((Integer) obj).intValue() == 666) {
                    CurriculumScheduleCardFragment.this.showHintStr(childrenDTO, textView);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getCourseListData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("course_id", "" + this.course_id);
        String str = NetworkRequestsURL.curriculumdetailUrl;
        if ("我的收藏".equals(this.title)) {
            str = NetworkRequestsURL.curriculummyCollectionUrl;
        } else if ("我的笔记".equals(this.title)) {
            str = NetworkRequestsURL.curriculummyNoteUrl;
        } else if ("我的评论".equals(this.title)) {
            str = NetworkRequestsURL.curriculummyCommentUrl;
        }
        ajaxParams.put("type", "" + this.type);
        YJYHttpUtils.get(this.mContext, str, ajaxParams, new AnonymousClass2());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$goToVideoActivity$3() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Permission.WRITE_EXTERNAL_STORAGE}, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(RefreshLayout refreshLayout) {
        getMemData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$verifyPermissions$1(int i2, CurriculumScheduleCardBean.DataDTO.ChildrenDTO childrenDTO) {
        if (i2 == 0) {
            goToVideoActivity(childrenDTO);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$verifyPermissions$2(String str) {
        Intent intent = new Intent(getContext(), (Class<?>) ActCourseOrGoodsDetail.class);
        intent.putExtra("goods_id", str);
        intent.putExtra("type_course", getArguments().getString("type_course", ""));
        intent.putExtra("course_id", getArguments().getString("course_id_p", ""));
        intent.putExtra("type", this.type);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setArrowSpin(BaseViewHolder helper, BaseNode data, boolean isAnimate) {
        CurriculumScheduleCardBean.DataDTO dataDTO = (CurriculumScheduleCardBean.DataDTO) data;
        final ImageView imageView = (ImageView) helper.getView(R.id.croteimg);
        TextView textView = (TextView) helper.getView(R.id.tryTextview);
        ImageView imageView2 = (ImageView) helper.getView(R.id.lottieview);
        if (dataDTO.getIsExpanded()) {
            textView.setVisibility(8);
            hideLottieView(imageView2);
            if (isAnimate) {
                helper.itemView.setSelected(true);
                imageView.animate().rotation(180.0f).setDuration(300L).setInterpolator(new LinearInterpolator()).start();
                return;
            } else if (SkinManager.getCurrentSkinType(getActivity()) == 1) {
                imageView.setImageResource(R.drawable.ic_arrow_up_night);
                return;
            } else {
                imageView.setImageResource(R.drawable.ctopimg);
                return;
            }
        }
        helper.itemView.setSelected(false);
        if ("1".equals(dataDTO.getIsFreeWatch())) {
            textView.setVisibility(0);
        } else {
            textView.setVisibility(8);
        }
        if ("1".equals(dataDTO.getLive_status())) {
            showLottieView(imageView2);
        } else {
            hideLottieView(imageView2);
        }
        if (isAnimate) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(imageView, Key.ROTATION, 0.0f, 180.0f);
            objectAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.CurriculumScheduleCardFragment.3
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animation) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    imageView.setRotation(0.0f);
                    if (SkinManager.getCurrentSkinType(CurriculumScheduleCardFragment.this.getActivity()) == 1) {
                        imageView.setImageResource(R.drawable.cbottomimg_night);
                    } else {
                        imageView.setImageResource(R.drawable.cbottomimg);
                    }
                    animation.cancel();
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animation) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animation) {
                }
            });
            objectAnimatorOfFloat.start();
        } else if (SkinManager.getCurrentSkinType(getActivity()) == 1) {
            imageView.setImageResource(R.drawable.cbottomimg_night);
        } else {
            imageView.setImageResource(R.drawable.cbottomimg);
        }
    }

    public List<BaseNode> getAdapterData(List<CurriculumScheduleCardBean.DataDTO> data) {
        ArrayList arrayList = new ArrayList();
        if (data != null && data.size() > 0) {
            String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.curriculum_vid, getActivity(), "");
            for (int i2 = 0; i2 < data.size(); i2++) {
                CurriculumScheduleCardBean.DataDTO dataDTO = data.get(i2);
                dataDTO.setExpanded(false);
                if (dataDTO.getChildren() != null) {
                    for (int i3 = 0; i3 < dataDTO.getChildren().size(); i3++) {
                        CurriculumScheduleCardBean.DataDTO.ChildrenDTO childrenDTO = dataDTO.getChildren().get(i3);
                        if (strConfig.equals(childrenDTO.getVid() + "") && !dataDTO.getIsExpanded()) {
                            dataDTO.setExpanded(true);
                            this.position = i2;
                        }
                        dataDTO.addChildren(childrenDTO);
                    }
                }
                arrayList.add(dataDTO);
            }
        }
        return arrayList;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_curriculum_schedule_card;
    }

    public void getMemData() {
        final AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("module_name", "course");
        ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, "" + this.activity_id);
        ajaxParams.put("id", "" + this.course_id);
        ArrayList<String> arrayList = this.set_meal_ids;
        if (arrayList != null && arrayList.size() > 0) {
            ajaxParams.put("set_meal_id", new Gson().toJson(this.set_meal_ids));
        }
        YJYHttpUtils.get(getActivity(), NetworkRequestsURL.vipApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.courselist.fragment.CurriculumScheduleCardFragment.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                CurriculumScheduleCardFragment.this.refreshLayout.finishRefresh(false);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                String str;
                super.onSuccess((AnonymousClass1) s2);
                try {
                    MemCenterBean memCenterBean = (MemCenterBean) new Gson().fromJson(s2, MemCenterBean.class);
                    if (memCenterBean.getCode() != 200) {
                        ToastUtil.shortToast(CurriculumScheduleCardFragment.this.getActivity(), memCenterBean.getMessage() + "");
                        return;
                    }
                    String str2 = "" + memCenterBean.getData().getPass();
                    if (TextUtils.isEmpty(ajaxParams.getParam().get("module_name"))) {
                        str = "1";
                    } else {
                        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, CurriculumScheduleCardFragment.this.getActivity());
                        String strMD5Encode = Md5Util.MD5Encode(Md5Util.MD5Encode(UserConfig.getUserId() + ajaxParams.getParam().get("id") + strConfig + ajaxParams.getParam().get(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID)) + "SmpaaK0eEwCxdiU9sGkbI7GvEQ0WbaJD1");
                        str2 = memCenterBean.getData().getPermission() + "";
                        str = strMD5Encode;
                    }
                    if (str2.equals(str)) {
                        CurriculumScheduleCardFragment.this.isPremision = "1";
                    } else {
                        CurriculumScheduleCardFragment.this.isPremision = "0";
                    }
                    CurriculumScheduleCardFragment.this.getCourseListData();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void getTheRest(CurriculumScheduleCardBean.DataDTO.ChildrenDTO mChildrenDTO, String vid) {
        int i2;
        CurriculumScheduleCardBean.DataDTO dataDTO;
        ArrayList arrayList = new ArrayList();
        if (this.adapter != null) {
            for (int i3 = 0; i3 < this.adapter.getData().size(); i3++) {
                if ((this.adapter.getData().get(i3) instanceof CurriculumScheduleCardBean.DataDTO) && (dataDTO = (CurriculumScheduleCardBean.DataDTO) this.adapter.getData().get(i3)) != null && dataDTO.getChildNode() != null) {
                    for (int i4 = 0; i4 < dataDTO.getChildNode().size(); i4++) {
                        if (dataDTO.getChildNode().get(i4) instanceof CurriculumScheduleCardBean.DataDTO.ChildrenDTO) {
                            arrayList.add((CurriculumScheduleCardBean.DataDTO.ChildrenDTO) dataDTO.getChildNode().get(i4));
                        }
                    }
                }
            }
            int i5 = 0;
            while (true) {
                if (i5 >= arrayList.size()) {
                    i2 = 0;
                    break;
                } else {
                    if (vid.equals(((CurriculumScheduleCardBean.DataDTO.ChildrenDTO) arrayList.get(i5)).getVid())) {
                        i2 = i5 + 1;
                        break;
                    }
                    i5++;
                }
            }
            ProjectApp.mPlayerVideo.clear();
            while (i2 < arrayList.size()) {
                if (arrayList.get(i2) != null && !TextUtils.isEmpty(((CurriculumScheduleCardBean.DataDTO.ChildrenDTO) arrayList.get(i2)).getVid())) {
                    ProjectApp.mPlayerVideo.add((CurriculumScheduleCardBean.DataDTO.ChildrenDTO) arrayList.get(i2));
                }
                i2++;
            }
        }
        if (this.isPipModePlay) {
            Intent intent = new Intent();
            intent.setAction(EventBusConstant.CLOSE_PIP);
            getActivity().sendBroadcast(intent);
        }
        SharePreferencesUtils.writeStrConfig(CommonParameter.curriculum_vid, "" + mChildrenDTO.getVid(), getActivity());
        Intent intent2 = new Intent();
        intent2.putExtra("sort", 0);
        intent2.putExtra(AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER, this.cover);
        intent2.setClass(getActivity(), AliPlayerVideoPlayActivity.class);
        intent2.putExtra("obj_id", mChildrenDTO.getId() + "");
        intent2.putExtra(DatabaseManager.SIZE, mChildrenDTO.getSize());
        intent2.putExtra("pid", mChildrenDTO.getPid());
        intent2.putExtra("title", mChildrenDTO.getTitle());
        intent2.putExtra("chapter_title", mChildrenDTO.getChapterTitle());
        intent2.putExtra("chapter_id", this.course_id + "");
        intent2.putExtra("free_watch_time", 0);
        intent2.putExtra("type", this.type);
        intent2.putExtra("isFreeWatch", mChildrenDTO.getIsFreeWatch() + "");
        intent2.putExtra("watch_permission", this.isPremision);
        intent2.putExtra("expire_str", "");
        intent2.putExtra("module_type", 8);
        intent2.putExtra("hasChapter", true);
        intent2.putExtra("vid", mChildrenDTO.getVid() + "");
        intent2.putExtra(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, "" + this.activity_id);
        if (mChildrenDTO.getCurrent_see().equals("0")) {
            intent2.putExtra("seeDuration", mChildrenDTO.getSee());
        } else {
            intent2.putExtra("seeDuration", mChildrenDTO.getCurrent_see());
        }
        intent2.putExtra("commentEnum", DiscussStatus.CourseReview);
        startActivity(intent2);
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

    public void goToVideoActivity(CurriculumScheduleCardBean.DataDTO.ChildrenDTO childrenDTO) {
        boolean z2 = ContextCompat.checkSelfPermission(getActivity(), Permission.WRITE_EXTERNAL_STORAGE) == 0;
        boolean zShouldShowRequestPermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Permission.WRITE_EXTERNAL_STORAGE);
        if (!z2 || zShouldShowRequestPermissionRationale) {
            new XPopup.Builder(getActivity()).asCustom(new RequestMediaPermissionPop(getActivity(), new OnConfirmListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.n3
                @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                public final void onConfirm() {
                    this.f11997a.lambda$goToVideoActivity$3();
                }
            })).show();
        } else {
            CommonUtil.copyEncryptedFile(this.mContext);
            getTheRest(childrenDTO, childrenDTO.getVid());
        }
    }

    public void hideLottieView(ImageView lottieview) {
        lottieview.setVisibility(8);
        if (((Animatable) lottieview.getDrawable()).isRunning()) {
            ((Animatable) lottieview.getDrawable()).stop();
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        if (getArguments() != null) {
            this.set_meal_ids = getArguments().getStringArrayList("set_meal_id");
            this.have_chapter = getArguments().getString("have_chapter", "");
            this.is_hide_teacher = getArguments().getString("is_hide_teacher", "");
            this.course_id = getArguments().getString("course_id", "");
            this.type = getArguments().getString("type", "");
            this.activity_id = getArguments().getString(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, "");
            this.cover = getArguments().getString(AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER, "");
            this.title = getArguments().getString("title", "");
        }
        this.closevideo = (ImageView) holder.get(R.id.closevideo);
        this.titleTv = (TextView) holder.get(R.id.titleTv);
        this.liveingRel = (RelativeLayout) holder.get(R.id.liveingRel);
        this.refreshLayout = (SmartRefreshLayout) holder.get(R.id.refresh);
        RecyclerView recyclerView = (RecyclerView) holder.get(R.id.recycleId);
        this.recycleId = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.refreshLayout.setEnableLoadMore(false);
        this.refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.m3
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f11991c.lambda$initViews$0(refreshLayout);
            }
        });
        this.refreshLayout.autoRefresh();
        CurriculumScheduleCardExpandNodeAdapter curriculumScheduleCardExpandNodeAdapter = new CurriculumScheduleCardExpandNodeAdapter();
        this.adapter = curriculumScheduleCardExpandNodeAdapter;
        this.recycleId.setAdapter(curriculumScheduleCardExpandNodeAdapter);
        this.adapter.setEmptyView(R.layout.adapter_empty_txt_view);
    }

    public void mRefreshVideo(String vid, int payload) {
        if (this.adapter != null) {
            for (int i2 = 0; i2 < this.adapter.getData().size(); i2++) {
                if (this.adapter.getData().get(i2) instanceof CurriculumScheduleCardBean.DataDTO.ChildrenDTO) {
                    if (vid.equals(((CurriculumScheduleCardBean.DataDTO.ChildrenDTO) this.adapter.getData().get(i2)).getVid() + "")) {
                        this.adapter.notifyItemChanged(i2, Integer.valueOf(payload));
                        return;
                    }
                }
            }
        }
    }

    @Subscribe
    public void onEvent(PipPlayEvent event) {
        this.isPipModePlay = event.isPipMode();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        CurriculumScheduleCardExpandNodeAdapter curriculumScheduleCardExpandNodeAdapter;
        if (!"downimg2".equals(str) || !isAdded() || getActivity() == null || (curriculumScheduleCardExpandNodeAdapter = this.adapter) == null || curriculumScheduleCardExpandNodeAdapter.getData() == null) {
            return;
        }
        if ("0".equals(this.isPremision)) {
            verifyPermissions(null, 2);
            return;
        }
        Intent intent = new Intent(getActivity(), (Class<?>) CurriculumDownLoadManageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("course_id", this.course_id);
        bundle.putString("type", this.type);
        bundle.putString(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, this.activity_id);
        bundle.putString(AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER, this.cover);
        bundle.putString("title", this.title);
        bundle.putBoolean("manage", false);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void scrollViewTop() {
        LinearLayoutManager linearLayoutManager;
        if (this.adapter.getData().size() <= 0 || this.adapter.getData().size() <= this.position + 1 || (linearLayoutManager = (LinearLayoutManager) this.recycleId.getLayoutManager()) == null) {
            return;
        }
        linearLayoutManager.scrollToPosition(0);
    }

    public void showHintStr(CurriculumScheduleCardBean.DataDTO.ChildrenDTO childrenDTO, TextView detailTxt) throws NumberFormatException {
        String str;
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.curriculum_vid, getActivity(), "0");
        if ("1".equals(this.is_hide_teacher) || TextUtils.isEmpty(childrenDTO.getTeacher())) {
            str = "";
        } else {
            str = "" + childrenDTO.getTeacher() + "&#8194;";
        }
        if (childrenDTO.getStatus() == 5) {
            str = str + "本地&#8194;";
        } else if (childrenDTO.getStatus() == 6) {
            str = str + "本地视频不存在&#8194;";
        }
        if (childrenDTO.getDuration() == null || "".equals(childrenDTO.getDuration())) {
            detailTxt.setText("未观看");
            return;
        }
        if ("1".equals(this.have_chapter)) {
            str = str + "直播回放&#8194;";
        }
        double d3 = Double.parseDouble(childrenDTO.getDuration());
        double d4 = Double.parseDouble(childrenDTO.getSee());
        Double.parseDouble(childrenDTO.getCurrent_see());
        String str2 = str + TimeFormater.formatMs((long) (1000.0d * d3)) + "&#8194;";
        String str3 = SkinManager.getCurrentSkinType(this.mContext) == 0 ? "#DD594A" : "#B2575C";
        if (strConfig.equals(childrenDTO.getVid())) {
            str2 = str2 + "<font color=" + str3 + ">上次观看</font>&#8194";
        } else if (d4 > 0.0d) {
            if (d3 - d4 < 1.0d) {
                str2 = str2 + "已学完&#8194;";
            } else {
                if (d3 <= 0.0d) {
                    d3 = 1.0d;
                }
                int iIntValue = new BigDecimal((int) ((d4 / d3) * 100.0d)).setScale(0, RoundingMode.HALF_UP).intValue();
                if (iIntValue < 1) {
                    str2 = str2 + "已学习1%";
                } else {
                    str2 = str2 + "已学习" + iIntValue + "%";
                }
            }
        }
        detailTxt.setText(Html.fromHtml(str2));
    }

    public void showLivingView(CurriculumLiveBean.DataDTO live) {
    }

    public void showLottieView(ImageView lottieView) {
        lottieView.setVisibility(0);
        ((Animatable) lottieView.getDrawable()).start();
    }

    public void verifyPermissions(final CurriculumScheduleCardBean.DataDTO.ChildrenDTO childrenDTO, final int type) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("module_name", "course");
        ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, "" + this.activity_id);
        ajaxParams.put("id", "" + this.course_id);
        ArrayList<String> arrayList = this.set_meal_ids;
        if (arrayList != null && arrayList.size() > 0) {
            ajaxParams.put("set_meal_id", new Gson().toJson(this.set_meal_ids));
        }
        ajaxParams.put("type", this.type);
        MemInterface.getInstance().getMemData(getActivity(), ajaxParams, false, 0);
        MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.o3
            @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
            public final void mUShareListener() {
                this.f12006a.lambda$verifyPermissions$1(type, childrenDTO);
            }
        });
        MemInterface.getInstance().setJumpCourseListener(new MemInterface.JumpCourseListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.p3
            @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.JumpCourseListener
            public final void jump2CourseDetail(String str) {
                this.f12014a.lambda$verifyPermissions$2(str);
            }
        });
    }

    public void onEventMainThread(EventBusMessage<String> str) {
        CurriculumScheduleCardBean.DataDTO dataDTO;
        if (!"completion".equals(str.getKey()) && !RequestParameters.SUBRESOURCE_DELETE.equals(str.getKey())) {
            if ("refreshDuration".equals(str.getKey())) {
                getMemData();
                return;
            }
            return;
        }
        String str2 = str.getValueObj() + "";
        if (this.adapter != null) {
            for (int i2 = 0; i2 < this.adapter.getData().size(); i2++) {
                if ((this.adapter.getData().get(i2) instanceof CurriculumScheduleCardBean.DataDTO) && (dataDTO = (CurriculumScheduleCardBean.DataDTO) this.adapter.getData().get(i2)) != null && dataDTO.getChildNode() != null) {
                    for (int i3 = 0; i3 < dataDTO.getChildNode().size(); i3++) {
                        if (dataDTO.getChildNode().get(i3) instanceof CurriculumScheduleCardBean.DataDTO.ChildrenDTO) {
                            CurriculumScheduleCardBean.DataDTO.ChildrenDTO childrenDTO = (CurriculumScheduleCardBean.DataDTO.ChildrenDTO) dataDTO.getChildNode().get(i3);
                            if (str2.equals(childrenDTO.getVid() + "")) {
                                if ("completion".equals(str.getKey())) {
                                    childrenDTO.setStatus(5);
                                } else if (RequestParameters.SUBRESOURCE_DELETE.equals(str.getKey())) {
                                    childrenDTO.setStatus(0);
                                }
                                mRefreshVideo(childrenDTO.getVid() + "", R2.attr.bl_selected_gradient_centerY);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}
