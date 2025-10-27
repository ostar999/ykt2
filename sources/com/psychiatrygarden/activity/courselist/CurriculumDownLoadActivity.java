package com.psychiatrygarden.activity.courselist;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.Key;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadMediaInfo;
import com.aliyun.player.alivcplayerexpand.util.download.StorageUtil;
import com.aliyun.player.aliyunplayerbase.util.Formatter;
import com.aliyun.svideo.common.utils.ThreadUtils;
import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.courselist.bean.CurriculumScheduleCardBean;
import com.psychiatrygarden.activity.courselist.bean.VideoDownTempBean;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.CourseCoverBean;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.CourseDirectoryBean;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.VideoDownBean;
import com.psychiatrygarden.activity.mine.setting.DownloadSetting;
import com.psychiatrygarden.aliPlayer.utils.AliPlayUtils;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CancelConfrimPop;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.yikaobang.yixue.R;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CurriculumDownLoadActivity extends BaseActivity {
    CurriculumScheduleCardExpandNodeAdapter adapter;
    public TextView downTv;
    private ImageView icCloseBack;
    public RecyclerView recycledown;
    private boolean showDownloadDetail;
    public String title;
    public TextView tvSelectAll;
    public List<BaseNode> dataList = new ArrayList();
    private ArrayMap<String, Integer> nodeMap = new ArrayMap<>();
    public int EXPAND_COLLAPSE_PAYLOAD = 112;
    public String course_id = "0";
    public String type = "";
    public String activity_id = "";
    public String cover = "";
    private boolean isHasSelected = false;

    /* renamed from: com.psychiatrygarden.activity.courselist.CurriculumDownLoadActivity$4, reason: invalid class name */
    public class AnonymousClass4 extends AjaxCallBack<String> {
        public AnonymousClass4() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(List list) {
            CurriculumDownLoadActivity curriculumDownLoadActivity = CurriculumDownLoadActivity.this;
            curriculumDownLoadActivity.adapter.setList(curriculumDownLoadActivity.getAdaterData(list));
            if (CurriculumDownLoadActivity.this.isHasSelected) {
                CurriculumDownLoadActivity.this.downTv.setEnabled(true);
                CurriculumDownLoadActivity.this.downTv.setClickable(true);
                CurriculumDownLoadActivity.this.isHasSelected = false;
            }
            CurriculumDownLoadActivity.this.showHideBtn();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1(final List list) {
            List<VideoDownBean> videoDownLoadInfo = ProjectApp.database.getVideoDownDao().getVideoDownLoadInfo("course_" + CurriculumDownLoadActivity.this.course_id);
            for (int i2 = 0; i2 < list.size(); i2++) {
                ((CurriculumScheduleCardBean.DataDTO) list.get(i2)).setEditMode(true);
                if (((CurriculumScheduleCardBean.DataDTO) list.get(i2)).getChildren() != null && ((CurriculumScheduleCardBean.DataDTO) list.get(i2)).getChildren().size() > 0) {
                    List<CurriculumScheduleCardBean.DataDTO.ChildrenDTO> children = ((CurriculumScheduleCardBean.DataDTO) list.get(i2)).getChildren();
                    CurriculumDownLoadActivity.this.nodeMap.put(((CurriculumScheduleCardBean.DataDTO) list.get(i2)).getId(), Integer.valueOf(children == null ? 0 : children.size()));
                    boolean z2 = true;
                    boolean z3 = true;
                    for (CurriculumScheduleCardBean.DataDTO.ChildrenDTO childrenDTO : ((CurriculumScheduleCardBean.DataDTO) list.get(i2)).getChildren()) {
                        if (!TextUtils.isEmpty(childrenDTO.getVid())) {
                            int iIsHadVideoStatus = CurriculumDownLoadActivity.this.isHadVideoStatus(videoDownLoadInfo, childrenDTO.getVid());
                            if (iIsHadVideoStatus == 5) {
                                childrenDTO.setSelected(0);
                            } else if (iIsHadVideoStatus == -1) {
                                childrenDTO.setSelected(0);
                                z2 = false;
                                z3 = false;
                            } else {
                                childrenDTO.setSelected(1);
                                CurriculumDownLoadActivity.this.isHasSelected = true;
                                z2 = false;
                            }
                            childrenDTO.setStatus(iIsHadVideoStatus);
                        }
                    }
                    if (z2) {
                        ((CurriculumScheduleCardBean.DataDTO) list.get(i2)).setStatus(5);
                    }
                    if (z3) {
                        ((CurriculumScheduleCardBean.DataDTO) list.get(i2)).setSelected(1);
                    }
                }
            }
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.courselist.v1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f12187c.lambda$onSuccess$0(list);
                }
            });
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onStart() {
            super.onStart();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String t2) {
            super.onSuccess((AnonymousClass4) t2);
            try {
                CurriculumScheduleCardBean curriculumScheduleCardBean = (CurriculumScheduleCardBean) new Gson().fromJson(t2, CurriculumScheduleCardBean.class);
                if (curriculumScheduleCardBean.getCode().equals("200")) {
                    CurriculumDownLoadActivity curriculumDownLoadActivity = CurriculumDownLoadActivity.this;
                    if (curriculumDownLoadActivity.downTv != null) {
                        curriculumDownLoadActivity.tvSelectAll.setText("全选");
                        CurriculumDownLoadActivity.this.tvSelectAll.setSelected(false);
                        CurriculumDownLoadActivity.this.downTv.setEnabled(false);
                        CurriculumDownLoadActivity.this.downTv.setClickable(false);
                    }
                    final List<CurriculumScheduleCardBean.DataDTO> data = curriculumScheduleCardBean.getData();
                    if (data != null) {
                        ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.activity.courselist.w1
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f12192c.lambda$onSuccess$1(data);
                            }
                        });
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* renamed from: com.psychiatrygarden.activity.courselist.CurriculumDownLoadActivity$5, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status;

        static {
            int[] iArr = new int[AliyunDownloadMediaInfo.Status.values().length];
            $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status = iArr;
            try {
                iArr[AliyunDownloadMediaInfo.Status.Idle.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[AliyunDownloadMediaInfo.Status.Prepare.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[AliyunDownloadMediaInfo.Status.Wait.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[AliyunDownloadMediaInfo.Status.Start.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[AliyunDownloadMediaInfo.Status.Stop.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[AliyunDownloadMediaInfo.Status.Complete.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[AliyunDownloadMediaInfo.Status.Error.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public class CurriculumScheduleCardExpandNodeAdapter extends BaseNodeAdapter {
        public CurriculumScheduleCardExpandNodeAdapter(@Nullable List<BaseNode> nodeList) {
            super(nodeList);
            addFullSpanNodeProvider(CurriculumDownLoadActivity.this.new FirstNoteProvider());
            addNodeProvider(CurriculumDownLoadActivity.this.new SecondNoteProvider());
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
        static final /* synthetic */ boolean $assertionsDisabled = false;

        public FirstNoteProvider() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Type inference failed for: r2v4, types: [com.chad.library.adapter.base.BaseNodeAdapter] */
        public /* synthetic */ void lambda$convert$0(CurriculumScheduleCardBean.DataDTO dataDTO, BaseViewHolder baseViewHolder, View view) {
            if (dataDTO.getChildNode() == null || dataDTO.getChildNode().size() == 0) {
                ToastUtil.shortToast(CurriculumDownLoadActivity.this, "无视频");
            } else {
                getAdapter2().expandOrCollapse(baseViewHolder.getBindingAdapterPosition(), true, true, Integer.valueOf(CurriculumDownLoadActivity.this.EXPAND_COLLAPSE_PAYLOAD));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$1(CurriculumScheduleCardBean.DataDTO dataDTO, View view) {
            try {
                if (dataDTO.getStatus() != 5 && dataDTO.getChildNode() != null && !dataDTO.getChildNode().isEmpty()) {
                    int i2 = 1;
                    for (int i3 = 0; i3 < dataDTO.getChildNode().size(); i3++) {
                        CurriculumScheduleCardBean.DataDTO.ChildrenDTO childrenDTO = (CurriculumScheduleCardBean.DataDTO.ChildrenDTO) dataDTO.getChildNode().get(i3);
                        if (childrenDTO.getStatus() == 5 || TextUtils.isEmpty(childrenDTO.getVid()) || dataDTO.getSelected() == 1) {
                            childrenDTO.setSelected(0);
                            i2 = 0;
                        } else {
                            childrenDTO.setSelected(1);
                        }
                    }
                    if (dataDTO.getSelected() == 1) {
                        dataDTO.setSelected(0);
                    } else {
                        dataDTO.setSelected(i2);
                    }
                    CurriculumDownLoadActivity.this.adapter.notifyDataSetChanged();
                    CurriculumDownLoadActivity.this.updateTitle();
                    CurriculumDownLoadActivity.this.collorexpend();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        private void updateSelect(ImageView selectTv, CurriculumScheduleCardBean.DataDTO dataDTO) {
            int i2;
            List<BaseNode> childNode = dataDTO.getChildNode();
            if (childNode == null || childNode.isEmpty()) {
                i2 = 0;
            } else {
                i2 = 0;
                for (BaseNode baseNode : childNode) {
                    if ((baseNode instanceof CurriculumScheduleCardBean.DataDTO.ChildrenDTO) && ((CurriculumScheduleCardBean.DataDTO.ChildrenDTO) baseNode).getSelected() == 1) {
                        i2++;
                    }
                }
            }
            if (childNode != null && childNode.size() == i2) {
                selectTv.setSelected(true);
                TypedArray typedArrayObtainStyledAttributes = CurriculumDownLoadActivity.this.getTheme().obtainStyledAttributes(new int[]{R.attr.download_select});
                selectTv.setImageDrawable(typedArrayObtainStyledAttributes.getDrawable(0));
                typedArrayObtainStyledAttributes.recycle();
                return;
            }
            if (i2 > 0) {
                if (SkinManager.getCurrentSkinType(getContext()) == 1) {
                    selectTv.setImageResource(R.drawable.download_part_select_night);
                    return;
                } else {
                    selectTv.setImageResource(R.drawable.download_part_select);
                    return;
                }
            }
            selectTv.setSelected(false);
            TypedArray typedArrayObtainStyledAttributes2 = CurriculumDownLoadActivity.this.getTheme().obtainStyledAttributes(new int[]{R.attr.download_new_not_select});
            selectTv.setImageDrawable(typedArrayObtainStyledAttributes2.getDrawable(0));
            typedArrayObtainStyledAttributes2.recycle();
        }

        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
        public int getItemViewType() {
            return 0;
        }

        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
        public int getLayoutId() {
            return R.layout.item_curriculum_download_parent_node;
        }

        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
        public /* bridge */ /* synthetic */ void convert(@NotNull BaseViewHolder helper, BaseNode item, @NotNull List payloads) {
            convert2(helper, item, (List<?>) payloads);
        }

        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
        public void convert(@NotNull final BaseViewHolder baseViewHolder, BaseNode baseNode) {
            final CurriculumScheduleCardBean.DataDTO dataDTO = (CurriculumScheduleCardBean.DataDTO) baseNode;
            TextView textView = (TextView) baseViewHolder.findView(R.id.title);
            ImageView imageView = (ImageView) baseViewHolder.findView(R.id.croteimg);
            ImageView imageView2 = (ImageView) baseViewHolder.findView(R.id.selectTv);
            ImageView imageView3 = (ImageView) baseViewHolder.findView(R.id.ic_video);
            imageView2.setVisibility(dataDTO.isEditMode() ? 0 : 8);
            imageView3.setVisibility(!dataDTO.isEditMode() ? 0 : 8);
            textView.setText(dataDTO.getTitle());
            baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.x1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f12206c.lambda$convert$0(dataDTO, baseViewHolder, view);
                }
            });
            if (dataDTO.getStatus() == 5) {
                imageView2.setVisibility(8);
            } else {
                textView.setCompoundDrawables(null, null, null, null);
                imageView2.setVisibility(0);
                updateSelect(imageView2, dataDTO);
            }
            imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.y1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f12211c.lambda$convert$1(dataDTO, view);
                }
            });
            List<BaseNode> childNode = dataDTO.getChildNode();
            int i2 = R.drawable.cbottomimg_night;
            if (childNode != null && !dataDTO.getChildNode().isEmpty()) {
                imageView.setVisibility(0);
                if (dataDTO.getIsExpanded()) {
                    imageView.setImageResource(SkinManager.getCurrentSkinType(CurriculumDownLoadActivity.this.mContext) == 1 ? R.drawable.ic_arrow_up_night : R.drawable.ctopimg);
                    return;
                }
                if (SkinManager.getCurrentSkinType(CurriculumDownLoadActivity.this.mContext) != 1) {
                    i2 = R.drawable.cbottomimg;
                }
                imageView.setImageResource(i2);
                return;
            }
            if (SkinManager.getCurrentSkinType(CurriculumDownLoadActivity.this) == 1) {
                imageView.setImageResource(R.drawable.cbottomimg_night);
                textView.setTextColor(CurriculumDownLoadActivity.this.mContext.getResources().getColor(R.color.tertiary_text_color_night));
            } else {
                imageView.setImageResource(R.drawable.cbottomimg);
                textView.setTextColor(CurriculumDownLoadActivity.this.mContext.getResources().getColor(R.color.tertiary_text_color));
            }
            imageView.setVisibility(4);
        }

        /* renamed from: convert, reason: avoid collision after fix types in other method */
        public void convert2(@NotNull BaseViewHolder helper, BaseNode item, @NotNull List<?> payloads) {
            super.convert(helper, (BaseViewHolder) item, (List<? extends Object>) payloads);
            ImageView imageView = (ImageView) helper.findView(R.id.selectTv);
            CurriculumScheduleCardBean.DataDTO dataDTO = (CurriculumScheduleCardBean.DataDTO) item;
            if (dataDTO.getChildNode() == null || dataDTO.getChildNode().size() == 0) {
                return;
            }
            for (Object obj : payloads) {
                boolean z2 = obj instanceof Integer;
                if (z2) {
                    int iIntValue = ((Integer) obj).intValue();
                    CurriculumDownLoadActivity curriculumDownLoadActivity = CurriculumDownLoadActivity.this;
                    if (iIntValue == curriculumDownLoadActivity.EXPAND_COLLAPSE_PAYLOAD) {
                        curriculumDownLoadActivity.setArrowSpin(helper, dataDTO, true);
                    }
                }
                if (z2 && ((Integer) obj).intValue() == 222) {
                    updateSelect(imageView, dataDTO);
                    CurriculumDownLoadActivity.this.updateTitle();
                }
            }
        }
    }

    public class SecondNoteProvider extends BaseNodeProvider {
        public SecondNoteProvider() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Type inference failed for: r5v2, types: [com.chad.library.adapter.base.BaseNodeAdapter] */
        public /* synthetic */ void lambda$convert$0(CurriculumScheduleCardBean.DataDTO.ChildrenDTO childrenDTO, ImageView imageView, BaseViewHolder baseViewHolder, View view) {
            try {
                if (childrenDTO.getStatus() == 5) {
                    ToastUtil.shortToast(CurriculumDownLoadActivity.this, "已下载");
                    return;
                }
                if (TextUtils.isEmpty(childrenDTO.getVid())) {
                    ToastUtil.shortToast(CurriculumDownLoadActivity.this, "直播未完成，暂不可下载");
                    return;
                }
                int i2 = 0;
                if (childrenDTO.getSelected() == 1) {
                    imageView.setSelected(false);
                    childrenDTO.setSelected(0);
                } else {
                    imageView.setSelected(true);
                    childrenDTO.setSelected(1);
                }
                int iFindParentNode = getAdapter2().findParentNode(baseViewHolder.getBindingAdapterPosition());
                BaseNode baseNode = CurriculumDownLoadActivity.this.adapter.getData().get(iFindParentNode);
                if (baseNode instanceof CurriculumScheduleCardBean.DataDTO) {
                    CurriculumScheduleCardBean.DataDTO dataDTO = (CurriculumScheduleCardBean.DataDTO) baseNode;
                    if (dataDTO.getChildNode() == null || dataDTO.getChildNode().size() <= 0) {
                        i2 = 1;
                    } else {
                        for (int i3 = 0; i3 < dataDTO.getChildNode().size(); i3++) {
                            CurriculumScheduleCardBean.DataDTO.ChildrenDTO childrenDTO2 = (CurriculumScheduleCardBean.DataDTO.ChildrenDTO) dataDTO.getChildNode().get(i3);
                            if (childrenDTO2.getSelected() != 1 || childrenDTO2.getStatus() == 5 || TextUtils.isEmpty(childrenDTO2.getVid())) {
                                break;
                            }
                        }
                        i2 = 1;
                    }
                    dataDTO.setSelected(i2);
                    CurriculumDownLoadActivity.this.adapter.notifyItemChanged(iFindParentNode, 222);
                    CurriculumDownLoadActivity.this.updateTitle();
                    CurriculumDownLoadActivity.this.collorexpend();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
        public int getItemViewType() {
            return 1;
        }

        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
        public int getLayoutId() {
            return R.layout.item_curriculum_down_child;
        }

        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
        public /* bridge */ /* synthetic */ void convert(@NotNull BaseViewHolder helper, BaseNode item, @NotNull List payloads) {
            convert2(helper, item, (List<?>) payloads);
        }

        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
        public void convert(@NotNull final BaseViewHolder baseViewHolder, BaseNode baseNode) {
            String str;
            final CurriculumScheduleCardBean.DataDTO.ChildrenDTO childrenDTO = (CurriculumScheduleCardBean.DataDTO.ChildrenDTO) baseNode;
            int layoutPosition = baseViewHolder.getLayoutPosition();
            int iFindParentNode = CurriculumDownLoadActivity.this.adapter.findParentNode(baseNode);
            if (iFindParentNode != -1) {
                BaseNode baseNode2 = CurriculumDownLoadActivity.this.adapter.getData().get(iFindParentNode);
                if (baseNode2 instanceof CurriculumScheduleCardBean.DataDTO) {
                    View view = baseViewHolder.getView(R.id.rl_root);
                    List<CurriculumScheduleCardBean.DataDTO.ChildrenDTO> children = ((CurriculumScheduleCardBean.DataDTO) baseNode2).getChildren();
                    if (children.size() == 1) {
                        view.setBackground(CurriculumDownLoadActivity.this.getDrawable(R.drawable.bg_course_download_child));
                    } else if (children.size() > 1) {
                        if (layoutPosition == iFindParentNode + 1) {
                            view.setBackground(CurriculumDownLoadActivity.this.getDrawable(R.drawable.bg_course_download_child_top));
                        } else if (layoutPosition == children.size() + iFindParentNode) {
                            view.setBackground(CurriculumDownLoadActivity.this.getDrawable(R.drawable.bg_course_download_child_bottom));
                        } else {
                            view.setBackground(CurriculumDownLoadActivity.this.getDrawable(R.drawable.bg_course_download_child_middle));
                        }
                    }
                    baseViewHolder.setGone(R.id.line, children.size() == 1 || layoutPosition == iFindParentNode + children.size());
                }
            }
            TextView textView = (TextView) baseViewHolder.getView(R.id.title);
            TextView textView2 = (TextView) baseViewHolder.getView(R.id.detailTxt);
            final ImageView imageView = (ImageView) baseViewHolder.getView(R.id.selectTv);
            textView.setText(String.format("%s", childrenDTO.getTitle()));
            if (childrenDTO.getDuration() != null && !"".equals(childrenDTO.getDuration())) {
                str = "" + (((long) (Double.parseDouble(childrenDTO.getDuration()) / 60.0d)) + "分钟");
            } else {
                str = "未知数据时长";
            }
            if (childrenDTO.getSize() != null && !"".equals(childrenDTO.getSize())) {
                String size = childrenDTO.getSize();
                if (!TextUtils.isEmpty(size)) {
                    str = str + "&#8194;" + ((Long.parseLong(size) / 1024) / 1024) + "MB";
                }
            }
            String str2 = SkinManager.getCurrentSkinType(CurriculumDownLoadActivity.this.mContext) == 1 ? "#575F79" : "#909090";
            switch (childrenDTO.getStatus()) {
                case 1:
                    SkinManager.getCurrentSkinType(CurriculumDownLoadActivity.this.mContext);
                    str = str + "&#8194;<font color='#B2575C'>下载中-" + childrenDTO.getProcess() + "%</font>";
                    break;
                case 2:
                    str = str + "&#8194;<font color='" + str2 + "'>请重试</font>";
                    break;
                case 3:
                    str = str + "&#8194;<font color='" + str2 + "'>队列中</font>";
                    break;
                case 4:
                    str = str + "&#8194;<font color='" + str2 + "'>暂停中</font>";
                    break;
                case 5:
                    str = str + "&#8194;<font color='" + str2 + "'>已下载</font>";
                    break;
                case 6:
                    str = str + "&#8194;<font color='" + str2 + "'>本地文件不存在</font>";
                    break;
            }
            if (CurriculumDownLoadActivity.this.showDownloadDetail) {
                textView2.setText(Html.fromHtml(str));
            }
            imageView.setSelected(childrenDTO.getSelected() == 1);
            baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.z1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f12215c.lambda$convert$0(childrenDTO, imageView, baseViewHolder, view2);
                }
            });
        }

        /* renamed from: convert, reason: avoid collision after fix types in other method */
        public void convert2(@NotNull BaseViewHolder helper, BaseNode item, @NotNull List<?> payloads) {
            super.convert(helper, (BaseViewHolder) item, (List<? extends Object>) payloads);
        }
    }

    private void calculationCache() {
        Iterator it = ((ArrayList) YkBManager.getInstance().mDownloadMediaLists).iterator();
        long size = 0;
        while (it.hasNext()) {
            size += ((AliyunDownloadMediaInfo) it.next()).getSize();
        }
        Formatter.getFileSizeDescription(size);
        StorageUtil.getAvailableExternalMemorySize();
    }

    private void getCourseListData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("course_id", this.course_id);
        ajaxParams.put("type", this.type);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.curriculumdetailUrl, ajaxParams, new AnonymousClass4());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$downloadData$10(final String str, final String str2, final String str3) {
        CurriculumScheduleCardBean.DataDTO dataDTO;
        List<BaseNode> childNode;
        CurriculumScheduleCardBean.DataDTO dataDTO2;
        CourseCoverBean courseCoverBean = new CourseCoverBean();
        courseCoverBean.setCover(this.cover);
        courseCoverBean.setActivity_id(this.activity_id);
        courseCoverBean.setId(Integer.parseInt(this.course_id));
        courseCoverBean.setTitle(this.title);
        courseCoverBean.setSort(0);
        ProjectApp.database.getCourseCoverDao().insertTopic(courseCoverBean);
        if (this.adapter != null) {
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < this.adapter.getData().size(); i2++) {
                if ((this.adapter.getData().get(i2) instanceof CurriculumScheduleCardBean.DataDTO) && (dataDTO2 = (CurriculumScheduleCardBean.DataDTO) this.adapter.getData().get(i2)) != null && dataDTO2.getChildNode() != null && dataDTO2.getChildNode().size() > 0) {
                    int i3 = 0;
                    while (true) {
                        if (i3 >= dataDTO2.getChildNode().size()) {
                            break;
                        }
                        if ((dataDTO2.getChildNode().get(i3) instanceof CurriculumScheduleCardBean.DataDTO.ChildrenDTO) && ((CurriculumScheduleCardBean.DataDTO.ChildrenDTO) dataDTO2.getChildNode().get(i3)).getSelected() == 1) {
                            CourseDirectoryBean courseDirectoryBean = new CourseDirectoryBean();
                            courseDirectoryBean.setId(Integer.parseInt(dataDTO2.getId()));
                            courseDirectoryBean.setPid(this.course_id);
                            courseDirectoryBean.setSort(i2);
                            courseDirectoryBean.setTitle(dataDTO2.getTitle());
                            arrayList.add(courseDirectoryBean);
                            break;
                        }
                        i3++;
                    }
                }
            }
            if (arrayList.size() > 0) {
                ProjectApp.database.getCourseDirectoryDao().insertTopicList(arrayList);
            }
            ArrayList arrayList2 = new ArrayList();
            for (BaseNode baseNode : this.adapter.getData()) {
                if (baseNode != null && (baseNode instanceof CurriculumScheduleCardBean.DataDTO) && (childNode = (dataDTO = (CurriculumScheduleCardBean.DataDTO) baseNode).getChildNode()) != null && !childNode.isEmpty()) {
                    for (BaseNode baseNode2 : childNode) {
                        if (baseNode2 instanceof CurriculumScheduleCardBean.DataDTO.ChildrenDTO) {
                            CurriculumScheduleCardBean.DataDTO.ChildrenDTO childrenDTO = (CurriculumScheduleCardBean.DataDTO.ChildrenDTO) baseNode2;
                            if (childrenDTO.getSelected() == 1 && !TextUtils.isEmpty(childrenDTO.getVid())) {
                                childrenDTO.setPid(String.valueOf(dataDTO.getId()));
                                childrenDTO.setStatus(3);
                                arrayList2.add(childrenDTO);
                            }
                        }
                    }
                }
            }
            if (arrayList2.size() > 0) {
                VideoDownBean[] videoDownBeanArr = new VideoDownBean[arrayList2.size()];
                for (int i4 = 0; i4 < arrayList2.size(); i4++) {
                    if (!hasVidData(((CurriculumScheduleCardBean.DataDTO.ChildrenDTO) arrayList2.get(i4)).getVid(), ProjectApp.vids).booleanValue()) {
                        ProjectApp.vids.add(new VideoDownTempBean(((CurriculumScheduleCardBean.DataDTO.ChildrenDTO) arrayList2.get(i4)).getVid(), str, str2, str3));
                    }
                    VideoDownBean videoDownBean = new VideoDownBean();
                    videoDownBean.setcId("course_" + this.course_id);
                    videoDownBean.setChapter_id(((CurriculumScheduleCardBean.DataDTO.ChildrenDTO) arrayList2.get(i4)).getId());
                    videoDownBean.setParent_id(((CurriculumScheduleCardBean.DataDTO.ChildrenDTO) arrayList2.get(i4)).getPid());
                    videoDownBean.videoType = Integer.parseInt(this.type);
                    videoDownBean.setVid(((CurriculumScheduleCardBean.DataDTO.ChildrenDTO) arrayList2.get(i4)).getVid());
                    videoDownBean.setmTitle(((CurriculumScheduleCardBean.DataDTO.ChildrenDTO) arrayList2.get(i4)).getTitle());
                    videoDownBean.setmSize(Long.parseLong(((CurriculumScheduleCardBean.DataDTO.ChildrenDTO) arrayList2.get(i4)).getSize()));
                    videoDownBean.setSort(((CurriculumScheduleCardBean.DataDTO.ChildrenDTO) arrayList2.get(i4)).getSort());
                    videoDownBean.setmStatus(3);
                    videoDownBean.setmFormat(AliPlayUtils.getDownloadVideoDefinition(this));
                    videoDownBeanArr[i4] = videoDownBean;
                }
                ProjectApp.database.getVideoDownDao().insertTopicList(videoDownBeanArr);
            }
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.courselist.p1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f12142c.lambda$downloadData$8(str, str2, str3);
                }
            });
        }
        runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.courselist.q1
            @Override // java.lang.Runnable
            public final void run() {
                this.f12152c.lambda$downloadData$9();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$downloadData$8(String str, String str2, String str3) {
        this.adapter.notifyDataSetChanged();
        List<VideoDownTempBean> list = ProjectApp.vids;
        if (list == null || list.size() <= 0) {
            return;
        }
        AliyunDownloadMediaInfo aliyunDownloadMediaInfoHasAliyunVideo = hasAliyunVideo(ProjectApp.vids.get(0).vid);
        if (aliyunDownloadMediaInfoHasAliyunVideo == null || ProjectApp.downloadManager.getmVidSts() == null) {
            CommonUtil.initDownAliyunVideo(ProjectApp.vids.get(0).vid, str, str2, str3);
            return;
        }
        if (aliyunDownloadMediaInfoHasAliyunVideo.getStatus() == AliyunDownloadMediaInfo.Status.Error) {
            aliyunDownloadMediaInfoHasAliyunVideo.setStatus(AliyunDownloadMediaInfo.Status.Stop);
        }
        if (aliyunDownloadMediaInfoHasAliyunVideo.getStatus() != AliyunDownloadMediaInfo.Status.Start) {
            aliyunDownloadMediaInfoHasAliyunVideo.setQuality(AliPlayUtils.getDownloadVideoDefinition(this));
            ProjectApp.downloadManager.startDownload(aliyunDownloadMediaInfoHasAliyunVideo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$downloadData$9() {
        ToastUtil.shortToast(this, "已加入下载列表");
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        int size = 0;
        for (BaseNode baseNode : this.adapter.getData()) {
            if (baseNode instanceof CurriculumScheduleCardBean.DataDTO) {
                List<BaseNode> childNode = baseNode.getChildNode();
                if (((CurriculumScheduleCardBean.DataDTO) baseNode).getSelected() == 1) {
                    size += childNode == null ? 0 : childNode.size();
                } else if (childNode != null && !childNode.isEmpty()) {
                    for (BaseNode baseNode2 : childNode) {
                        if ((baseNode2 instanceof CurriculumScheduleCardBean.DataDTO.ChildrenDTO) && ((CurriculumScheduleCardBean.DataDTO.ChildrenDTO) baseNode2).getSelected() == 1) {
                            size++;
                        }
                    }
                }
            }
        }
        if (size == 0) {
            finish();
        } else {
            collorexpendList(0);
            this.tvSelectAll.setSelected(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        if (this.adapter.getData().isEmpty()) {
            return;
        }
        if (this.tvSelectAll.isSelected()) {
            collorexpendList(0);
            this.tvSelectAll.setText("全选");
            this.tvSelectAll.setSelected(false);
        } else {
            collorexpendList(1);
            this.tvSelectAll.setText("取消全选");
            this.tvSelectAll.setSelected(true);
        }
        updateTitle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2() {
        ActivityCompat.requestPermissions(this, new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE}, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3(View view) {
        Context context = this.mContext;
        if (context == null) {
            return;
        }
        if (!CommonUtil.isNetworkConnected(context)) {
            NewToast.showShort(this.mContext, "当前无网络连接", 0).show();
        } else if (ContextCompat.checkSelfPermission(this.mContext, Permission.WRITE_EXTERNAL_STORAGE) != 0) {
            new XPopup.Builder(this).asCustom(new RequestMediaPermissionPop(this, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.courselist.n1
                @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                public final void onConfirm() {
                    this.f12127a.lambda$init$2();
                }
            })).show();
        } else {
            startDownload();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$mRefreshDownloadData$7(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
        CurriculumScheduleCardExpandNodeAdapter curriculumScheduleCardExpandNodeAdapter = this.adapter;
        if (curriculumScheduleCardExpandNodeAdapter != null) {
            curriculumScheduleCardExpandNodeAdapter.getData();
            if (aliyunDownloadMediaInfo != null) {
                for (int i2 = 0; i2 < this.adapter.getData().size(); i2++) {
                    if (this.adapter.getData().get(i2) instanceof CurriculumScheduleCardBean.DataDTO) {
                        CurriculumScheduleCardBean.DataDTO dataDTO = (CurriculumScheduleCardBean.DataDTO) this.adapter.getData().get(i2);
                        if (dataDTO.getChildNode() != null) {
                            boolean z2 = true;
                            for (int i3 = 0; i3 < dataDTO.getChildNode().size(); i3++) {
                                if (dataDTO.getChildNode().get(i3) instanceof CurriculumScheduleCardBean.DataDTO.ChildrenDTO) {
                                    CurriculumScheduleCardBean.DataDTO.ChildrenDTO childrenDTO = (CurriculumScheduleCardBean.DataDTO.ChildrenDTO) dataDTO.getChildNode().get(i3);
                                    if ((childrenDTO.getVid() + "").equals(aliyunDownloadMediaInfo.getVid() + "")) {
                                        childrenDTO.setProcess(aliyunDownloadMediaInfo.getProgress() + "");
                                        switch (AnonymousClass5.$SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[aliyunDownloadMediaInfo.getStatus().ordinal()]) {
                                            case 1:
                                            case 2:
                                                childrenDTO.setStatus(0);
                                                break;
                                            case 3:
                                                childrenDTO.setStatus(3);
                                                break;
                                            case 4:
                                                childrenDTO.setStatus(1);
                                                break;
                                            case 5:
                                                childrenDTO.setStatus(4);
                                                break;
                                            case 6:
                                                childrenDTO.setStatus(5);
                                                childrenDTO.setSelected(0);
                                                break;
                                            case 7:
                                                childrenDTO.setStatus(2);
                                                break;
                                        }
                                        if (aliyunDownloadMediaInfo.getProgress() == 100) {
                                            childrenDTO.setStatus(5);
                                            childrenDTO.setSelected(0);
                                        }
                                    }
                                    if (childrenDTO.getStatus() != 5) {
                                        z2 = false;
                                    }
                                }
                            }
                            if (z2) {
                                dataDTO.setStatus(5);
                                dataDTO.setSelected(0);
                            }
                        }
                    }
                }
                this.adapter.notifyDataSetChanged();
                showHideBtn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showNetChangeDialog$5(DialogInterface dialogInterface, int i2) {
        getCourseAk();
        if (dialogInterface != null) {
            dialogInterface.dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showNetChangeDialog$6(DialogInterface dialogInterface, int i2) {
        if (dialogInterface != null) {
            dialogInterface.dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showOpenDownloadDialog$4() {
        if (isLogin()) {
            goActivity(DownloadSetting.class);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setArrowSpin(BaseViewHolder helper, BaseNode data, boolean isAnimate) {
        final ImageView imageView = (ImageView) helper.getView(R.id.croteimg);
        if (((CurriculumScheduleCardBean.DataDTO) data).getIsExpanded()) {
            if (isAnimate) {
                ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(imageView, Key.ROTATION, 0.0f, 180.0f);
                objectAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.psychiatrygarden.activity.courselist.CurriculumDownLoadActivity.2
                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animation) {
                        imageView.setRotation(0.0f);
                        if (SkinManager.getCurrentSkinType(CurriculumDownLoadActivity.this.mContext) == 1) {
                            imageView.setImageResource(R.drawable.ctopimg_night);
                        } else {
                            imageView.setImageResource(R.drawable.ctopimg);
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
                return;
            } else if (SkinManager.getCurrentSkinType(this.mContext) == 1) {
                imageView.setImageResource(R.drawable.ctopimg_night);
                return;
            } else {
                imageView.setImageResource(R.drawable.ctopimg);
                return;
            }
        }
        if (isAnimate) {
            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(imageView, Key.ROTATION, 0.0f, 180.0f);
            objectAnimatorOfFloat2.addListener(new Animator.AnimatorListener() { // from class: com.psychiatrygarden.activity.courselist.CurriculumDownLoadActivity.3
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animation) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    imageView.setRotation(0.0f);
                    if (SkinManager.getCurrentSkinType(CurriculumDownLoadActivity.this.mContext) == 1) {
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
            objectAnimatorOfFloat2.start();
        } else if (SkinManager.getCurrentSkinType(this.mContext) == 1) {
            imageView.setImageResource(R.drawable.cbottomimg_night);
        } else {
            imageView.setImageResource(R.drawable.cbottomimg);
        }
    }

    private void showNetChangeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        builder.setTitle("网络切换为4G");
        builder.setMessage("是否继续下载？");
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.r1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                this.f12157c.lambda$showNetChangeDialog$5(dialogInterface, i2);
            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.s1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                CurriculumDownLoadActivity.lambda$showNetChangeDialog$6(dialogInterface, i2);
            }
        });
        builder.create();
        builder.show();
    }

    private void showOpenDownloadDialog() {
        new XPopup.Builder(this.mContext).asCustom(new CancelConfrimPop(this.mContext, new CancelConfrimPop.ClickIml() { // from class: com.psychiatrygarden.activity.courselist.k1
            @Override // com.psychiatrygarden.widget.CancelConfrimPop.ClickIml
            public final void mClickIml() {
                this.f12111a.lambda$showOpenDownloadDialog$4();
            }
        }, "当前设置不允许流量下载，如仍需下载可以到【设置-下载设置】里开启", "温馨提示", "暂不开启", "去开启")).show();
    }

    private void startDownload() {
        if (CommonUtil.isNetworkConnected(this.mContext)) {
            if (CommonUtil.isWifi(this.mContext) || UserConfig.isCanDownloadBy4g(this.mContext)) {
                getCourseAk();
            } else {
                showOpenDownloadDialog();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTitle() {
        int size = 0;
        for (BaseNode baseNode : this.adapter.getData()) {
            if (baseNode instanceof CurriculumScheduleCardBean.DataDTO) {
                List<BaseNode> childNode = baseNode.getChildNode();
                if (((CurriculumScheduleCardBean.DataDTO) baseNode).getSelected() == 1) {
                    size += childNode == null ? 0 : childNode.size();
                } else if (childNode != null) {
                    for (BaseNode baseNode2 : childNode) {
                        if ((baseNode2 instanceof CurriculumScheduleCardBean.DataDTO.ChildrenDTO) && ((CurriculumScheduleCardBean.DataDTO.ChildrenDTO) baseNode2).getSelected() == 1) {
                            size++;
                        }
                    }
                }
            }
        }
        ((TextView) findViewById(R.id.title)).setText(String.format("已选择%d个文件", Integer.valueOf(size)));
    }

    public void collorexpend() {
        String str;
        CurriculumScheduleCardExpandNodeAdapter curriculumScheduleCardExpandNodeAdapter = this.adapter;
        if (curriculumScheduleCardExpandNodeAdapter != null && curriculumScheduleCardExpandNodeAdapter.getData().size() > 0) {
            boolean z2 = false;
            int i2 = 0;
            while (true) {
                if (i2 >= this.adapter.getData().size()) {
                    str = "取消全选";
                    z2 = true;
                    break;
                } else {
                    if ((this.adapter.getData().get(i2) instanceof CurriculumScheduleCardBean.DataDTO) && ((CurriculumScheduleCardBean.DataDTO) this.adapter.getData().get(i2)).getSelected() != 1) {
                        str = "全选";
                        break;
                    }
                    i2++;
                }
            }
            this.tvSelectAll.setSelected(z2);
            this.tvSelectAll.setText(str);
        }
        showDownSelectView();
    }

    public void collorexpendList(int select) {
        CurriculumScheduleCardExpandNodeAdapter curriculumScheduleCardExpandNodeAdapter = this.adapter;
        if (curriculumScheduleCardExpandNodeAdapter != null && curriculumScheduleCardExpandNodeAdapter.getData().size() > 0) {
            for (int i2 = 0; i2 < this.adapter.getData().size(); i2++) {
                if (this.adapter.getData().get(i2) instanceof CurriculumScheduleCardBean.DataDTO) {
                    CurriculumScheduleCardBean.DataDTO dataDTO = (CurriculumScheduleCardBean.DataDTO) this.adapter.getData().get(i2);
                    if (dataDTO.getChildNode() != null && dataDTO.getChildNode().size() > 0) {
                        int i3 = 1;
                        for (int i4 = 0; i4 < dataDTO.getChildNode().size(); i4++) {
                            if (dataDTO.getChildNode().get(i4) instanceof CurriculumScheduleCardBean.DataDTO.ChildrenDTO) {
                                CurriculumScheduleCardBean.DataDTO.ChildrenDTO childrenDTO = (CurriculumScheduleCardBean.DataDTO.ChildrenDTO) dataDTO.getChildNode().get(i4);
                                if (childrenDTO.getStatus() == 5 || TextUtils.isEmpty(childrenDTO.getVid())) {
                                    childrenDTO.setSelected(0);
                                    i3 = 0;
                                } else {
                                    childrenDTO.setSelected(select);
                                }
                            }
                        }
                        if (select == 0) {
                            dataDTO.setSelected(0);
                        } else {
                            dataDTO.setSelected(i3);
                        }
                    }
                }
            }
            this.adapter.notifyDataSetChanged();
        }
        showDownSelectView();
    }

    public void downloadData(final String acId, final String akSceret, final String securityToken) {
        ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.activity.courselist.m1
            @Override // java.lang.Runnable
            public final void run() {
                this.f12119c.lambda$downloadData$10(acId, akSceret, securityToken);
            }
        });
    }

    public List<BaseNode> getAdaterData(List<CurriculumScheduleCardBean.DataDTO> data) {
        ArrayList arrayList = new ArrayList();
        if (data != null && data.size() > 0) {
            for (int i2 = 0; i2 < data.size(); i2++) {
                CurriculumScheduleCardBean.DataDTO dataDTO = data.get(i2);
                dataDTO.setExpanded(true);
                if (dataDTO.getChildren() != null) {
                    for (int i3 = 0; i3 < dataDTO.getChildren().size(); i3++) {
                        CurriculumScheduleCardBean.DataDTO.ChildrenDTO childrenDTO = dataDTO.getChildren().get(i3);
                        if (!TextUtils.isEmpty(childrenDTO.getVid())) {
                            dataDTO.addChildren(childrenDTO);
                        }
                    }
                    if (dataDTO.getChildNode() != null && dataDTO.getChildNode().size() > 0) {
                        arrayList.add(dataDTO);
                    }
                }
            }
        }
        return arrayList;
    }

    public void getCourseAk() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getCourseAkApi, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.courselist.CurriculumDownLoadActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ProjectApp.instance().hideDialogWindow();
                ToastUtil.shortToast(CurriculumDownLoadActivity.this, "获取视频信息失败！");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                ProjectApp.instance().showDialogWindow();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if ("200".equals(jSONObject.optString("code"))) {
                        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                        if (jSONObjectOptJSONObject == null) {
                            return;
                        }
                        CurriculumDownLoadActivity.this.downloadData(DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObjectOptJSONObject.optString("akId")), DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObjectOptJSONObject.optString("akSecret")), DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObjectOptJSONObject.optString("st")));
                    } else {
                        ToastUtil.shortToast(CurriculumDownLoadActivity.this, jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                ProjectApp.instance().hideDialogWindow();
            }
        });
    }

    public AliyunDownloadMediaInfo hasAliyunVideo(String vid) {
        if (!TextUtils.isEmpty(vid) && YkBManager.getInstance().mDownloadMediaLists != null && YkBManager.getInstance().mDownloadMediaLists.size() > 0) {
            for (AliyunDownloadMediaInfo aliyunDownloadMediaInfo : YkBManager.getInstance().mDownloadMediaLists) {
                if (vid.equals(aliyunDownloadMediaInfo.getVid())) {
                    return aliyunDownloadMediaInfo;
                }
            }
        }
        return null;
    }

    public Boolean hasVidData(String vid, List<VideoDownTempBean> vids) {
        if (vids != null && vids.size() > 0) {
            Iterator<VideoDownTempBean> it = vids.iterator();
            while (it.hasNext()) {
                if (vid.equals(it.next().vid)) {
                    return Boolean.TRUE;
                }
            }
        }
        return Boolean.FALSE;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.title = getIntent().getExtras().getString("title");
        this.cover = getIntent().getExtras().getString(AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER);
        this.course_id = getIntent().getExtras().getString("course_id", "0");
        this.type = getIntent().getExtras().getString("type");
        this.activity_id = getIntent().getExtras().getString(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID);
        this.showDownloadDetail = getIntent().getBooleanExtra("show_download_detail", false);
        this.downTv = (TextView) findViewById(R.id.downTv);
        this.icCloseBack = (ImageView) findViewById(R.id.iv_close_back);
        this.tvSelectAll = (TextView) findViewById(R.id.tv_select_all);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycledown);
        this.recycledown = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.icCloseBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.t1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12172c.lambda$init$0(view);
            }
        });
        CurriculumScheduleCardExpandNodeAdapter curriculumScheduleCardExpandNodeAdapter = new CurriculumScheduleCardExpandNodeAdapter(new ArrayList());
        this.adapter = curriculumScheduleCardExpandNodeAdapter;
        this.recycledown.setAdapter(curriculumScheduleCardExpandNodeAdapter);
        this.adapter.setEmptyView(R.layout.layout_empty_view);
        this.tvSelectAll.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.u1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12176c.lambda$init$1(view);
            }
        });
        this.downTv.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.l1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12115c.lambda$init$3(view);
            }
        });
    }

    public int isHadVideoStatus(List<VideoDownBean> videoDownBeans, String vid) {
        if (videoDownBeans == null || videoDownBeans.size() <= 0) {
            return -1;
        }
        for (VideoDownBean videoDownBean : videoDownBeans) {
            if (vid.equals(videoDownBean.getVid() + "")) {
                List<VideoDownTempBean> list = ProjectApp.vids;
                if (list != null && list.size() > 0) {
                    return videoDownBean.getmStatus();
                }
                if (videoDownBean.getmStatus() == 5) {
                    return !new File(videoDownBean.getmSavePath()).exists() ? 6 : 5;
                }
                return 4;
            }
        }
        return -1;
    }

    public void mRefreshDownloadData() {
        YkBManager.getInstance().getVideoDownBean().observe(this, new Observer() { // from class: com.psychiatrygarden.activity.courselist.o1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f12134a.lambda$mRefreshDownloadData$7((AliyunDownloadMediaInfo) obj);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, android.app.Activity
    public void onPostCreate(@androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPostResume() {
        super.onPostResume();
        getCourseListData();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (1 == requestCode && grantResults[0] == -1) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Permission.WRITE_EXTERNAL_STORAGE)) {
                return;
            }
            ToastUtil.shortToast(this, "无法下载，请检查app存储权限是否打开！");
        } else if (1 == requestCode && grantResults[0] == 0) {
            startDownload();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_curriculum_down);
        if (this.mBaseTheme == 0) {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.new_bg_one_color), 0);
            getWindow().setNavigationBarColor(Color.parseColor("#FBFBFB"));
        } else {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.new_bg_one_color_night), 0);
            getWindow().setNavigationBarColor(Color.parseColor("#121622"));
        }
        setNewStyleStatusBarColor();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }

    public void showDownSelectView() {
        CurriculumScheduleCardExpandNodeAdapter curriculumScheduleCardExpandNodeAdapter = this.adapter;
        if (curriculumScheduleCardExpandNodeAdapter == null || curriculumScheduleCardExpandNodeAdapter.getData().size() <= 0) {
            return;
        }
        this.downTv.setEnabled(false);
        this.downTv.setClickable(false);
        for (int i2 = 0; i2 < this.adapter.getData().size(); i2++) {
            if (this.adapter.getData().get(i2) instanceof CurriculumScheduleCardBean.DataDTO) {
                CurriculumScheduleCardBean.DataDTO dataDTO = (CurriculumScheduleCardBean.DataDTO) this.adapter.getData().get(i2);
                if (dataDTO.getSelected() == 1) {
                    this.downTv.setEnabled(true);
                    this.downTv.setClickable(true);
                    return;
                } else if (dataDTO.getChildNode() != null && dataDTO.getChildNode().size() > 0) {
                    for (int i3 = 0; i3 < dataDTO.getChildNode().size(); i3++) {
                        if ((dataDTO.getChildNode().get(i3) instanceof CurriculumScheduleCardBean.DataDTO.ChildrenDTO) && ((CurriculumScheduleCardBean.DataDTO.ChildrenDTO) dataDTO.getChildNode().get(i3)).getSelected() == 1) {
                            this.downTv.setEnabled(true);
                            this.downTv.setClickable(true);
                            return;
                        }
                    }
                }
            }
        }
    }

    public void showHideBtn() {
        boolean z2;
        CurriculumScheduleCardExpandNodeAdapter curriculumScheduleCardExpandNodeAdapter = this.adapter;
        if (curriculumScheduleCardExpandNodeAdapter != null) {
            curriculumScheduleCardExpandNodeAdapter.getData();
            int i2 = 0;
            while (true) {
                if (i2 >= this.adapter.getData().size()) {
                    z2 = true;
                    break;
                } else {
                    if ((this.adapter.getData().get(i2) instanceof CurriculumScheduleCardBean.DataDTO) && ((CurriculumScheduleCardBean.DataDTO) this.adapter.getData().get(i2)).getStatus() != 5) {
                        z2 = false;
                        break;
                    }
                    i2++;
                }
            }
            if (z2) {
                this.tvSelectAll.setVisibility(8);
                this.downTv.setVisibility(8);
            } else {
                this.tvSelectAll.setVisibility(0);
                this.downTv.setVisibility(0);
            }
        }
    }
}
