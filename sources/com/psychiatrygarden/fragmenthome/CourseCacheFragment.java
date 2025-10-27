package com.psychiatrygarden.fragmenthome;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadMediaInfo;
import com.aliyun.player.alivcplayerexpand.util.download.StorageUtil;
import com.aliyun.player.aliyunplayerbase.util.Formatter;
import com.aliyun.svideo.common.utils.ThreadUtils;
import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.courselist.YkBManager;
import com.psychiatrygarden.activity.courselist.bean.VideoDownTempBean;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.CourseCoverBean;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.CourseDirectoryBean;
import com.psychiatrygarden.activity.courselist.roomDB.dataBean.VideoDownBean;
import com.psychiatrygarden.activity.mine.setting.DownloadSetting;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CancelConfrimPop;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import de.greenrobot.event.EventBus;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CourseCacheFragment extends BaseFragment {
    public CurriculumScheduleCardExpandNodeAdapter adapter;
    public RelativeLayout cnmrel;
    public TextView contentTv;
    public TextView deleteTv;
    public ImageView iconBack;
    private View mLine;
    public LinearLayout managerLine;
    public TextView managerTv;
    public TextView pauseTv;
    public RecyclerView recycledown;
    public RelativeLayout relTitle;
    public List<CourseCoverBean> courseCoverBeans = new ArrayList();
    public int EXPAND_COLLAPSE_PAYLOAD = 112;

    /* renamed from: com.psychiatrygarden.fragmenthome.CourseCacheFragment$3, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass3 {
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
            addFullSpanNodeProvider(CourseCacheFragment.this.new ZreoNoteProvider());
            addFullSpanNodeProvider(CourseCacheFragment.this.new FristNoteProvider());
            addNodeProvider(CourseCacheFragment.this.new SecendNoteProvider());
        }

        @Override // com.chad.library.adapter.base.BaseProviderMultiAdapter
        public int getItemType(@NotNull List<? extends BaseNode> list, int i2) {
            BaseNode baseNode = list.get(i2);
            if (baseNode instanceof CourseDirectoryBean) {
                return 0;
            }
            if (baseNode instanceof VideoDownBean) {
                return 1;
            }
            return baseNode instanceof CourseCoverBean ? 2 : -1;
        }
    }

    public class FristNoteProvider extends BaseNodeProvider {
        public FristNoteProvider() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Type inference failed for: r5v2, types: [com.chad.library.adapter.base.BaseNodeAdapter] */
        public /* synthetic */ void lambda$convert$0(CourseDirectoryBean courseDirectoryBean, BaseNode baseNode, View view) {
            try {
                int i2 = 0;
                if (courseDirectoryBean.getSelect() == 1) {
                    courseDirectoryBean.setSelect(0);
                } else {
                    courseDirectoryBean.setSelect(1);
                }
                if (courseDirectoryBean.getChildNode() != null && courseDirectoryBean.getChildNode().size() > 0) {
                    for (int i3 = 0; i3 < courseDirectoryBean.getChildNode().size(); i3++) {
                        ((VideoDownBean) courseDirectoryBean.getChildNode().get(i3)).setSelect(courseDirectoryBean.getSelect() == 1);
                    }
                    BaseNode baseNode2 = CourseCacheFragment.this.adapter.getData().get(getAdapter2().findParentNode(baseNode));
                    if (baseNode2 instanceof CourseCoverBean) {
                        CourseCoverBean courseCoverBean = (CourseCoverBean) baseNode2;
                        if (courseCoverBean.getChildNode() == null || courseCoverBean.getChildNode().size() <= 0) {
                            i2 = 1;
                            courseCoverBean.setSelect(i2);
                        } else {
                            for (int i4 = 0; i4 < courseCoverBean.getChildNode().size(); i4++) {
                                if (((CourseDirectoryBean) courseCoverBean.getChildNode().get(i4)).getSelect() != 1) {
                                    break;
                                }
                            }
                            i2 = 1;
                            courseCoverBean.setSelect(i2);
                        }
                    }
                    CourseCacheFragment.this.adapter.notifyDataSetChanged();
                    CourseCacheFragment.this.updateTextStatus();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
        public int getItemViewType() {
            return 0;
        }

        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
        public int getLayoutId() {
            return R.layout.layout_curriculum_download_frist;
        }

        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
        public /* bridge */ /* synthetic */ void convert(@NotNull BaseViewHolder helper, BaseNode item, @NotNull List payloads) {
            convert2(helper, item, (List<?>) payloads);
        }

        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
        public void convert(@NotNull BaseViewHolder baseViewHolder, final BaseNode baseNode) {
            final CourseDirectoryBean courseDirectoryBean = (CourseDirectoryBean) baseNode;
            TextView textView = (TextView) baseViewHolder.findView(R.id.title);
            ((ImageView) baseViewHolder.findView(R.id.croteimg)).setVisibility(8);
            ImageView imageView = (ImageView) baseViewHolder.findView(R.id.selectTv);
            textView.setText(courseDirectoryBean.getTitle() + "");
            baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.j2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f15680c.lambda$convert$0(courseDirectoryBean, baseNode, view);
                }
            });
            imageView.setSelected(courseDirectoryBean.getSelect() == 1);
            if (courseDirectoryBean.isShowBtn()) {
                imageView.setVisibility(0);
            } else {
                imageView.setVisibility(8);
            }
        }

        /* renamed from: convert, reason: avoid collision after fix types in other method */
        public void convert2(@NotNull BaseViewHolder helper, BaseNode item, @NotNull List<?> payloads) {
            super.convert(helper, (BaseViewHolder) item, (List<? extends Object>) payloads);
            ImageView imageView = (ImageView) helper.findView(R.id.selectTv);
            ImageView imageView2 = (ImageView) helper.findView(R.id.croteimg);
            CourseDirectoryBean courseDirectoryBean = (CourseDirectoryBean) item;
            if (courseDirectoryBean.getChildNode() == null || courseDirectoryBean.getChildNode().size() == 0) {
                return;
            }
            if (courseDirectoryBean.isShowBtn()) {
                imageView.setVisibility(0);
            } else {
                imageView.setVisibility(8);
            }
            for (Object obj : payloads) {
                boolean z2 = obj instanceof Integer;
                if (z2 && ((Integer) obj).intValue() == CourseCacheFragment.this.EXPAND_COLLAPSE_PAYLOAD) {
                    if (courseDirectoryBean.getIsExpanded()) {
                        imageView2.setImageResource(SkinManager.getCurrentSkinType(((BaseFragment) CourseCacheFragment.this).mContext) == 1 ? R.drawable.ctopimg_night : R.drawable.ctopimg);
                    } else {
                        imageView2.setImageResource(SkinManager.getCurrentSkinType(((BaseFragment) CourseCacheFragment.this).mContext) == 1 ? R.drawable.cbottomimg_night : R.drawable.cbottomimg);
                    }
                }
                if (z2 && ((Integer) obj).intValue() == 222) {
                    imageView.setSelected(courseDirectoryBean.getSelect() == 1);
                }
            }
        }
    }

    public class SecendNoteProvider extends BaseNodeProvider {
        public SecendNoteProvider() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Removed duplicated region for block: B:22:0x0085 A[Catch: Exception -> 0x00d6, TryCatch #0 {Exception -> 0x00d6, blocks: (B:2:0x0000, B:4:0x0008, B:6:0x0015, B:8:0x0033, B:10:0x003b, B:13:0x0046, B:15:0x0050, B:20:0x0066, B:22:0x0085, B:24:0x008d, B:27:0x0098, B:29:0x00a2, B:34:0x00b7, B:32:0x00b3, B:35:0x00c5, B:18:0x0062, B:36:0x00d0, B:5:0x000f), top: B:41:0x0000 }] */
        /* JADX WARN: Type inference failed for: r5v2, types: [com.chad.library.adapter.base.BaseNodeAdapter] */
        /* JADX WARN: Type inference failed for: r6v8, types: [com.chad.library.adapter.base.BaseNodeAdapter] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public /* synthetic */ void lambda$convert$0(com.psychiatrygarden.activity.courselist.roomDB.dataBean.VideoDownBean r5, android.widget.ImageView r6, com.chad.library.adapter.base.viewholder.BaseViewHolder r7, android.view.View r8) {
            /*
                r4 = this;
                boolean r8 = r5.isSelect()     // Catch: java.lang.Exception -> Ld6
                r0 = 0
                r1 = 1
                if (r8 == 0) goto Lf
                r6.setSelected(r0)     // Catch: java.lang.Exception -> Ld6
                r5.setSelect(r0)     // Catch: java.lang.Exception -> Ld6
                goto L15
            Lf:
                r6.setSelected(r1)     // Catch: java.lang.Exception -> Ld6
                r5.setSelect(r1)     // Catch: java.lang.Exception -> Ld6
            L15:
                com.chad.library.adapter.base.BaseNodeAdapter r5 = r4.getAdapter2()     // Catch: java.lang.Exception -> Ld6
                int r6 = r7.getBindingAdapterPosition()     // Catch: java.lang.Exception -> Ld6
                int r5 = r5.findParentNode(r6)     // Catch: java.lang.Exception -> Ld6
                com.psychiatrygarden.fragmenthome.CourseCacheFragment r6 = com.psychiatrygarden.fragmenthome.CourseCacheFragment.this     // Catch: java.lang.Exception -> Ld6
                com.psychiatrygarden.fragmenthome.CourseCacheFragment$CurriculumScheduleCardExpandNodeAdapter r6 = r6.adapter     // Catch: java.lang.Exception -> Ld6
                java.util.List r6 = r6.getData()     // Catch: java.lang.Exception -> Ld6
                java.lang.Object r6 = r6.get(r5)     // Catch: java.lang.Exception -> Ld6
                com.chad.library.adapter.base.entity.node.BaseNode r6 = (com.chad.library.adapter.base.entity.node.BaseNode) r6     // Catch: java.lang.Exception -> Ld6
                boolean r7 = r6 instanceof com.psychiatrygarden.activity.courselist.roomDB.dataBean.CourseDirectoryBean     // Catch: java.lang.Exception -> Ld6
                if (r7 == 0) goto Ld0
                com.psychiatrygarden.activity.courselist.roomDB.dataBean.CourseDirectoryBean r6 = (com.psychiatrygarden.activity.courselist.roomDB.dataBean.CourseDirectoryBean) r6     // Catch: java.lang.Exception -> Ld6
                java.util.List r7 = r6.getChildNode()     // Catch: java.lang.Exception -> Ld6
                if (r7 == 0) goto L65
                java.util.List r7 = r6.getChildNode()     // Catch: java.lang.Exception -> Ld6
                int r7 = r7.size()     // Catch: java.lang.Exception -> Ld6
                if (r7 <= 0) goto L65
                r7 = r0
            L46:
                java.util.List r8 = r6.getChildNode()     // Catch: java.lang.Exception -> Ld6
                int r8 = r8.size()     // Catch: java.lang.Exception -> Ld6
                if (r7 >= r8) goto L65
                java.util.List r8 = r6.getChildNode()     // Catch: java.lang.Exception -> Ld6
                java.lang.Object r8 = r8.get(r7)     // Catch: java.lang.Exception -> Ld6
                com.psychiatrygarden.activity.courselist.roomDB.dataBean.VideoDownBean r8 = (com.psychiatrygarden.activity.courselist.roomDB.dataBean.VideoDownBean) r8     // Catch: java.lang.Exception -> Ld6
                boolean r8 = r8.isSelect()     // Catch: java.lang.Exception -> Ld6
                if (r8 != 0) goto L62
                r7 = r0
                goto L66
            L62:
                int r7 = r7 + 1
                goto L46
            L65:
                r7 = r1
            L66:
                r6.setSelect(r7)     // Catch: java.lang.Exception -> Ld6
                com.chad.library.adapter.base.BaseNodeAdapter r6 = r4.getAdapter2()     // Catch: java.lang.Exception -> Ld6
                int r6 = r6.findParentNode(r5)     // Catch: java.lang.Exception -> Ld6
                com.psychiatrygarden.fragmenthome.CourseCacheFragment r7 = com.psychiatrygarden.fragmenthome.CourseCacheFragment.this     // Catch: java.lang.Exception -> Ld6
                com.psychiatrygarden.fragmenthome.CourseCacheFragment$CurriculumScheduleCardExpandNodeAdapter r7 = r7.adapter     // Catch: java.lang.Exception -> Ld6
                java.util.List r7 = r7.getData()     // Catch: java.lang.Exception -> Ld6
                java.lang.Object r7 = r7.get(r6)     // Catch: java.lang.Exception -> Ld6
                com.chad.library.adapter.base.entity.node.BaseNode r7 = (com.chad.library.adapter.base.entity.node.BaseNode) r7     // Catch: java.lang.Exception -> Ld6
                boolean r8 = r7 instanceof com.psychiatrygarden.activity.courselist.roomDB.dataBean.CourseCoverBean     // Catch: java.lang.Exception -> Ld6
                r2 = 222(0xde, float:3.11E-43)
                if (r8 == 0) goto Lc5
                com.psychiatrygarden.activity.courselist.roomDB.dataBean.CourseCoverBean r7 = (com.psychiatrygarden.activity.courselist.roomDB.dataBean.CourseCoverBean) r7     // Catch: java.lang.Exception -> Ld6
                java.util.List r8 = r7.getChildNode()     // Catch: java.lang.Exception -> Ld6
                if (r8 == 0) goto Lb6
                java.util.List r8 = r7.getChildNode()     // Catch: java.lang.Exception -> Ld6
                int r8 = r8.size()     // Catch: java.lang.Exception -> Ld6
                if (r8 <= 0) goto Lb6
                r8 = r0
            L98:
                java.util.List r3 = r7.getChildNode()     // Catch: java.lang.Exception -> Ld6
                int r3 = r3.size()     // Catch: java.lang.Exception -> Ld6
                if (r8 >= r3) goto Lb6
                java.util.List r3 = r7.getChildNode()     // Catch: java.lang.Exception -> Ld6
                java.lang.Object r3 = r3.get(r8)     // Catch: java.lang.Exception -> Ld6
                com.psychiatrygarden.activity.courselist.roomDB.dataBean.CourseDirectoryBean r3 = (com.psychiatrygarden.activity.courselist.roomDB.dataBean.CourseDirectoryBean) r3     // Catch: java.lang.Exception -> Ld6
                int r3 = r3.getSelect()     // Catch: java.lang.Exception -> Ld6
                if (r3 == r1) goto Lb3
                goto Lb7
            Lb3:
                int r8 = r8 + 1
                goto L98
            Lb6:
                r0 = r1
            Lb7:
                r7.setSelect(r0)     // Catch: java.lang.Exception -> Ld6
                com.psychiatrygarden.fragmenthome.CourseCacheFragment r7 = com.psychiatrygarden.fragmenthome.CourseCacheFragment.this     // Catch: java.lang.Exception -> Ld6
                com.psychiatrygarden.fragmenthome.CourseCacheFragment$CurriculumScheduleCardExpandNodeAdapter r7 = r7.adapter     // Catch: java.lang.Exception -> Ld6
                java.lang.Integer r8 = java.lang.Integer.valueOf(r2)     // Catch: java.lang.Exception -> Ld6
                r7.notifyItemChanged(r6, r8)     // Catch: java.lang.Exception -> Ld6
            Lc5:
                com.psychiatrygarden.fragmenthome.CourseCacheFragment r6 = com.psychiatrygarden.fragmenthome.CourseCacheFragment.this     // Catch: java.lang.Exception -> Ld6
                com.psychiatrygarden.fragmenthome.CourseCacheFragment$CurriculumScheduleCardExpandNodeAdapter r6 = r6.adapter     // Catch: java.lang.Exception -> Ld6
                java.lang.Integer r7 = java.lang.Integer.valueOf(r2)     // Catch: java.lang.Exception -> Ld6
                r6.notifyItemChanged(r5, r7)     // Catch: java.lang.Exception -> Ld6
            Ld0:
                com.psychiatrygarden.fragmenthome.CourseCacheFragment r5 = com.psychiatrygarden.fragmenthome.CourseCacheFragment.this     // Catch: java.lang.Exception -> Ld6
                r5.updateTextStatus()     // Catch: java.lang.Exception -> Ld6
                goto Lda
            Ld6:
                r5 = move-exception
                r5.printStackTrace()
            Lda:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.fragmenthome.CourseCacheFragment.SecendNoteProvider.lambda$convert$0(com.psychiatrygarden.activity.courselist.roomDB.dataBean.VideoDownBean, android.widget.ImageView, com.chad.library.adapter.base.viewholder.BaseViewHolder, android.view.View):void");
        }

        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
        public int getItemViewType() {
            return 1;
        }

        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
        public int getLayoutId() {
            return R.layout.layout_curriculum_down_secend;
        }

        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
        public /* bridge */ /* synthetic */ void convert(@NotNull BaseViewHolder helper, BaseNode item, @NotNull List payloads) {
            convert2(helper, item, (List<?>) payloads);
        }

        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
        public void convert(@NotNull final BaseViewHolder baseViewHolder, BaseNode baseNode) {
            String str;
            final VideoDownBean videoDownBean = (VideoDownBean) baseNode;
            TextView textView = (TextView) baseViewHolder.getView(R.id.title);
            TextView textView2 = (TextView) baseViewHolder.getView(R.id.detailTxt);
            final ImageView imageView = (ImageView) baseViewHolder.getView(R.id.selectTv);
            textView.setText(videoDownBean.getmTitle() + "");
            imageView.setSelected(videoDownBean.isSelect());
            if (videoDownBean.isShowBtn()) {
                imageView.setVisibility(0);
            } else {
                imageView.setVisibility(8);
            }
            long j2 = (videoDownBean.getmSize() / 1024) / 1024;
            if (j2 <= 0) {
                str = "未知大小";
            } else {
                str = "" + j2 + "MB";
            }
            String str2 = SkinManager.getCurrentSkinType(((BaseFragment) CourseCacheFragment.this).mContext) == 1 ? "#575F79" : "#909090";
            switch (videoDownBean.getmStatus()) {
                case 1:
                    if (SkinManager.getCurrentSkinType(((BaseFragment) CourseCacheFragment.this).mContext) == 1) {
                        str = str + "&#8194;<font color='#B2575C'>下载中-" + videoDownBean.getmProgress() + "%</font>";
                        break;
                    } else {
                        str = str + "&#8194;<font color='#dd594a'>下载中-" + videoDownBean.getmProgress() + "%</font>";
                        break;
                    }
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
            textView2.setText(Html.fromHtml(str));
            baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.k2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f15704c.lambda$convert$0(videoDownBean, imageView, baseViewHolder, view);
                }
            });
        }

        /* renamed from: convert, reason: avoid collision after fix types in other method */
        public void convert2(@NotNull BaseViewHolder helper, BaseNode item, @NotNull List<?> payloads) {
            super.convert(helper, (BaseViewHolder) item, (List<? extends Object>) payloads);
            VideoDownBean videoDownBean = (VideoDownBean) item;
            if (videoDownBean == null) {
                return;
            }
            TextView textView = (TextView) helper.getView(R.id.detailTxt);
            for (Object obj : payloads) {
                if ((obj instanceof Integer) && ((Integer) obj).intValue() == 666) {
                    String str = "" + ((videoDownBean.getmSize() / 1024) / 1024) + "MB";
                    String str2 = SkinManager.getCurrentSkinType(((BaseFragment) CourseCacheFragment.this).mContext) == 1 ? "#575F79" : "#909090";
                    switch (videoDownBean.getmStatus()) {
                        case 1:
                            if (SkinManager.getCurrentSkinType(((BaseFragment) CourseCacheFragment.this).mContext) != 1) {
                                str = str + "&#8194;<font color='#dd594a'>下载中-" + videoDownBean.getmProgress() + "%</font>";
                                break;
                            } else {
                                str = str + "&#8194;<font color='#b2575c'>下载中-" + videoDownBean.getmProgress() + "%</font>";
                                break;
                            }
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
                    textView.setText(Html.fromHtml(str, 0));
                }
            }
        }
    }

    public class ZreoNoteProvider extends BaseNodeProvider {
        public ZreoNoteProvider() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(CourseCoverBean courseCoverBean, View view) {
            int i2 = courseCoverBean.getSelect() == 1 ? 0 : 1;
            courseCoverBean.setSelect(i2);
            if (courseCoverBean.getChildNode() != null) {
                for (int i3 = 0; i3 < courseCoverBean.getChildNode().size(); i3++) {
                    if (courseCoverBean.getChildNode().get(i3) instanceof CourseDirectoryBean) {
                        CourseDirectoryBean courseDirectoryBean = (CourseDirectoryBean) courseCoverBean.getChildNode().get(i3);
                        courseDirectoryBean.setSelect(i2);
                        if (courseDirectoryBean.getChildNode() != null) {
                            for (int i4 = 0; i4 < courseDirectoryBean.getChildNode().size(); i4++) {
                                ((VideoDownBean) courseDirectoryBean.getChildNode().get(i4)).setSelect(i2 == 1);
                            }
                        }
                    }
                }
            }
            CourseCacheFragment.this.adapter.notifyDataSetChanged();
            CourseCacheFragment.this.updateTextStatus();
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Type inference failed for: r2v5, types: [com.chad.library.adapter.base.BaseNodeAdapter] */
        public /* synthetic */ void lambda$convert$1(CourseCoverBean courseCoverBean, BaseViewHolder baseViewHolder, View view) {
            if (courseCoverBean.getChildNode() == null || courseCoverBean.getChildNode().size() == 0) {
                ToastUtil.shortToast(CourseCacheFragment.this.getActivity(), "无视频");
            } else {
                getAdapter2().expandOrCollapse(baseViewHolder.getBindingAdapterPosition(), true, true, Integer.valueOf(CourseCacheFragment.this.EXPAND_COLLAPSE_PAYLOAD));
            }
        }

        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
        public int getItemViewType() {
            return 2;
        }

        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
        public int getLayoutId() {
            return R.layout.layout_curriculum_download_zreo;
        }

        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
        public /* bridge */ /* synthetic */ void convert(@NotNull BaseViewHolder helper, BaseNode item, @NotNull List payloads) {
            convert2(helper, item, (List<?>) payloads);
        }

        @Override // com.chad.library.adapter.base.provider.BaseItemProvider
        public void convert(@NotNull final BaseViewHolder baseViewHolder, BaseNode baseNode) {
            CourseDirectoryBean courseDirectoryBean;
            CourseDirectoryBean courseDirectoryBean2;
            final CourseCoverBean courseCoverBean = (CourseCoverBean) baseNode;
            ImageView imageView = (ImageView) baseViewHolder.findView(R.id.selectTv);
            ImageView imageView2 = (ImageView) baseViewHolder.findView(R.id.cover);
            ImageView imageView3 = (ImageView) baseViewHolder.findView(R.id.croteimg);
            TextView textView = (TextView) baseViewHolder.findView(R.id.title);
            TextView textView2 = (TextView) baseViewHolder.findView(R.id.hinttv);
            if (textView != null) {
                textView.setText(courseCoverBean.getTitle());
            }
            GlideApp.with(CourseCacheFragment.this.getActivity()).load((Object) GlideUtils.generateUrl(courseCoverBean.getCover())).into(imageView2);
            int i2 = 0;
            if (imageView != null) {
                imageView.setSelected(courseCoverBean.getSelect() == 1);
                if (courseCoverBean.isShowBtn()) {
                    imageView.setVisibility(0);
                } else {
                    imageView.setVisibility(8);
                }
                imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.l2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f15801c.lambda$convert$0(courseCoverBean, view);
                    }
                });
            }
            if (courseCoverBean.getChildNode() != null && courseCoverBean.getChildNode().size() > 0) {
                imageView3.setVisibility(0);
            } else {
                imageView3.setVisibility(4);
            }
            baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.m2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f15823c.lambda$convert$1(courseCoverBean, baseViewHolder, view);
                }
            });
            if (courseCoverBean.getChildNode() != null && courseCoverBean.getChildNode().size() > 0) {
                ArrayList arrayList = new ArrayList();
                long j2 = 0;
                int i3 = 0;
                long j3 = 0;
                while (i3 < courseCoverBean.getChildNode().size()) {
                    if ((courseCoverBean.getChildNode().get(i3) instanceof CourseDirectoryBean) && (courseDirectoryBean = (CourseDirectoryBean) courseCoverBean.getChildNode().get(i3)) != null && courseDirectoryBean.getChildNode() != null) {
                        int i4 = i2;
                        while (i4 < courseDirectoryBean.getChildNode().size()) {
                            if (courseDirectoryBean.getChildNode().get(i4) instanceof VideoDownBean) {
                                VideoDownBean videoDownBean = (VideoDownBean) courseDirectoryBean.getChildNode().get(i4);
                                j3 += videoDownBean.getmSize();
                                courseDirectoryBean2 = courseDirectoryBean;
                                arrayList.add(Integer.valueOf(videoDownBean.getmStatus()));
                                j2 = (long) (j2 + (videoDownBean.getmSize() * (videoDownBean.getmProgress() / 100.0f)));
                            } else {
                                courseDirectoryBean2 = courseDirectoryBean;
                            }
                            i4++;
                            courseDirectoryBean = courseDirectoryBean2;
                        }
                    }
                    i3++;
                    i2 = 0;
                }
                courseCoverBean.setSizeprocess(j2);
                courseCoverBean.setSize(j3);
                courseCoverBean.setStatus(arrayList);
            }
            String str = "&#8194;" + Formatter.getFileSizeDescription(courseCoverBean.getSizeprocess()) + " / " + Formatter.getFileSizeDescription(courseCoverBean.getSize());
            String str2 = SkinManager.getCurrentSkinType(((BaseFragment) CourseCacheFragment.this).mContext) == 1 ? "#575F79" : "#909090";
            if (courseCoverBean.getStatus().contains(1)) {
                SkinManager.getCurrentSkinType(((BaseFragment) CourseCacheFragment.this).mContext);
                textView2.setText(Html.fromHtml(("<font color='#B2575C'>下载中</font>") + str));
                return;
            }
            if (courseCoverBean.getStatus().contains(4)) {
                textView2.setText(Html.fromHtml(("<font color='" + str2 + "'>暂停中</font>") + str));
                return;
            }
            if (courseCoverBean.getStatus().contains(3)) {
                textView2.setText(Html.fromHtml(("<font color='" + str2 + "'>队列中</font>") + str));
                return;
            }
            if (courseCoverBean.getStatus().contains(2)) {
                textView2.setText(Html.fromHtml(("<font color='" + str2 + "'>请重试</font>") + str));
                return;
            }
            if (courseCoverBean.getStatus().contains(5)) {
                textView2.setText(Html.fromHtml(("<font color='" + str2 + "'>已下载</font>") + str));
                return;
            }
            if (courseCoverBean.getStatus().contains(6)) {
                textView2.setText(Html.fromHtml(("<font color='" + str2 + "'>本地文件不存在</font>") + str));
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r10v0 */
        /* JADX WARN: Type inference failed for: r10v1, types: [int] */
        /* JADX WARN: Type inference failed for: r10v2 */
        /* JADX WARN: Type inference failed for: r10v3, types: [int] */
        /* JADX WARN: Type inference failed for: r10v4 */
        /* JADX WARN: Type inference failed for: r10v6, types: [int] */
        /* JADX WARN: Type inference failed for: r13v1 */
        /* JADX WARN: Type inference failed for: r13v2, types: [int] */
        /* JADX WARN: Type inference failed for: r13v4 */
        /* JADX WARN: Type inference failed for: r8v15, types: [int] */
        /* JADX WARN: Type inference failed for: r8v19 */
        /* JADX WARN: Type inference failed for: r8v20 */
        /* renamed from: convert, reason: avoid collision after fix types in other method */
        public void convert2(@NotNull BaseViewHolder baseViewHolder, BaseNode baseNode, @NotNull List<?> list) {
            CourseDirectoryBean courseDirectoryBean;
            super.convert(baseViewHolder, (BaseViewHolder) baseNode, (List<? extends Object>) list);
            ImageView imageView = (ImageView) baseViewHolder.findView(R.id.selectTv);
            ImageView imageView2 = (ImageView) baseViewHolder.findView(R.id.croteimg);
            TextView textView = (TextView) baseViewHolder.findView(R.id.hinttv);
            CourseCoverBean courseCoverBean = (CourseCoverBean) baseNode;
            if (courseCoverBean.getChildNode() == null || courseCoverBean.getChildNode().size() == 0) {
                return;
            }
            boolean z2 = false;
            if (courseCoverBean.isShowBtn()) {
                imageView.setVisibility(0);
            } else {
                imageView.setVisibility(8);
            }
            for (Object obj : list) {
                boolean z3 = obj instanceof Integer;
                if (z3 && ((Integer) obj).intValue() == CourseCacheFragment.this.EXPAND_COLLAPSE_PAYLOAD) {
                    if (courseCoverBean.getIsExpanded()) {
                        imageView2.setImageResource(SkinManager.getCurrentSkinType(((BaseFragment) CourseCacheFragment.this).mContext) == 1 ? R.drawable.ctopimg_night : R.drawable.ctopimg);
                    } else {
                        imageView2.setImageResource(SkinManager.getCurrentSkinType(((BaseFragment) CourseCacheFragment.this).mContext) == 1 ? R.drawable.cbottomimg_night : R.drawable.cbottomimg);
                    }
                }
                if (z3 && ((Integer) obj).intValue() == 222) {
                    imageView.setSelected(courseCoverBean.getSelect() == 1 ? true : z2);
                }
                if (z3 && ((Integer) obj).intValue() == 777) {
                    if (courseCoverBean.getChildNode() != null && courseCoverBean.getChildNode().size() > 0) {
                        ArrayList arrayList = new ArrayList();
                        boolean z4 = z2;
                        ?? r10 = z4;
                        int i2 = r10;
                        for (?? r8 = z4; r8 < courseCoverBean.getChildNode().size(); r8++) {
                            if ((courseCoverBean.getChildNode().get(r8) instanceof CourseDirectoryBean) && (courseDirectoryBean = (CourseDirectoryBean) courseCoverBean.getChildNode().get(r8)) != null && courseDirectoryBean.getChildNode() != null) {
                                for (?? r13 = z2; r13 < courseDirectoryBean.getChildNode().size(); r13++) {
                                    if (courseDirectoryBean.getChildNode().get(r13) instanceof VideoDownBean) {
                                        VideoDownBean videoDownBean = (VideoDownBean) courseDirectoryBean.getChildNode().get(r13);
                                        i2 = (int) (i2 + videoDownBean.getmSize());
                                        r10 = (int) (((float) r10) + (videoDownBean.getmSize() * (videoDownBean.getmProgress() / 100.0f)));
                                        arrayList = arrayList;
                                        arrayList.add(Integer.valueOf(videoDownBean.getmStatus()));
                                    }
                                }
                            }
                            z2 = false;
                        }
                        courseCoverBean.setSizeprocess((long) r10);
                        courseCoverBean.setSize(i2);
                        courseCoverBean.setStatus(arrayList);
                    }
                    String str = "&#8194;" + Formatter.getFileSizeDescription(courseCoverBean.getSizeprocess()) + " / " + Formatter.getFileSizeDescription(courseCoverBean.getSize());
                    if (courseCoverBean.getStatus().contains(1)) {
                        textView.setText(Html.fromHtml("下载中" + str));
                        return;
                    }
                    if (courseCoverBean.getStatus().contains(4)) {
                        textView.setText(Html.fromHtml("暂停中" + str));
                        return;
                    }
                    if (courseCoverBean.getStatus().contains(3)) {
                        textView.setText(Html.fromHtml("队列中" + str));
                        return;
                    }
                    if (courseCoverBean.getStatus().contains(2)) {
                        textView.setText(Html.fromHtml("请重试" + str));
                        return;
                    }
                    if (courseCoverBean.getStatus().contains(5)) {
                        textView.setText(Html.fromHtml("已完成" + str));
                        return;
                    }
                    if (courseCoverBean.getStatus().contains(6)) {
                        textView.setText(Html.fromHtml("本地文件不存在" + str));
                        return;
                    }
                }
                z2 = false;
            }
        }
    }

    private void calculationCache() {
        Iterator it = ((ArrayList) YkBManager.getInstance().mDownloadMediaLists).iterator();
        long size = 0;
        while (it.hasNext()) {
            size += ((AliyunDownloadMediaInfo) it.next()).getSize();
        }
        String fileSizeDescription = Formatter.getFileSizeDescription(size);
        long availableExternalMemorySize = StorageUtil.getAvailableExternalMemorySize();
        TextView textView = this.contentTv;
        if (textView != null) {
            textView.setText(String.format(getResources().getString(R.string.alivc_player_video_cache_storage_tips), fileSizeDescription, Formatter.getFileSizeDescription(availableExternalMemorySize * 1024)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$deleteData$12(List list, List list2, List list3) {
        if (!list.isEmpty()) {
            ProjectApp.database.getCourseCoverDao().deleteAllData((String[]) list.toArray(new String[list.size()]));
        }
        if (!list2.isEmpty()) {
            ProjectApp.database.getCourseDirectoryDao().deleteAllData((String[]) list2.toArray(new String[list2.size()]));
        }
        if (list3.isEmpty()) {
            return;
        }
        ProjectApp.database.getVideoDownDao().deleteAllData((String[]) list3.toArray(new String[list3.size()]));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$downloadData$10(List list) {
        String[] strArr = new String[list.size()];
        for (int i2 = 0; i2 < list.size(); i2++) {
            strArr[i2] = (String) list.get(i2);
        }
        ProjectApp.database.getVideoDownDao().updataModelStatus(3, strArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$initViews$0(View view) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(View view) {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2(View view) {
        deleteData();
        EventBus.getDefault().post("okEvent");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$3(View view) {
        if ("下载".equals(this.pauseTv.getText().toString())) {
            show4gDialog();
        } else {
            pauseData();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$4() {
        CurriculumScheduleCardExpandNodeAdapter curriculumScheduleCardExpandNodeAdapter = this.adapter;
        if (curriculumScheduleCardExpandNodeAdapter != null) {
            curriculumScheduleCardExpandNodeAdapter.setList(getAdaterData(this.courseCoverBeans));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$5() {
        this.courseCoverBeans = ProjectApp.database.getCourseCoverDao().getList();
        List<CourseDirectoryBean> list = ProjectApp.database.getCourseDirectoryDao().getList();
        List<VideoDownBean> videoDownLoadAllList = ProjectApp.database.getVideoDownDao().getVideoDownLoadAllList();
        List<CourseCoverBean> list2 = this.courseCoverBeans;
        if (list2 == null || list2.size() <= 0) {
            return;
        }
        if (list != null && list.size() > 0) {
            for (int i2 = 0; i2 < this.courseCoverBeans.size(); i2++) {
                ArrayList arrayList = new ArrayList();
                for (int i3 = 0; i3 < list.size(); i3++) {
                    if (list.get(i3).getPid().equals(this.courseCoverBeans.get(i2).getId() + "")) {
                        arrayList.add(list.get(i3));
                    }
                }
                if (arrayList.size() > 0) {
                    Collections.sort(arrayList);
                    for (int i4 = 0; i4 < arrayList.size(); i4++) {
                        if (videoDownLoadAllList != null && videoDownLoadAllList.size() > 0) {
                            ArrayList arrayList2 = new ArrayList();
                            for (int i5 = 0; i5 < videoDownLoadAllList.size(); i5++) {
                                if (videoDownLoadAllList.get(i5).getParent_id().equals(((CourseDirectoryBean) arrayList.get(i4)).getId() + "")) {
                                    videoDownLoadAllList.get(i5).setmStatus(isHadVideoStatus(videoDownLoadAllList.get(i5)));
                                    arrayList2.add(videoDownLoadAllList.get(i5));
                                }
                            }
                            if (!arrayList2.isEmpty()) {
                                Collections.sort(arrayList2);
                                ((CourseDirectoryBean) arrayList.get(i4)).setVideoDownBeans(arrayList2);
                            }
                        }
                    }
                    this.courseCoverBeans.get(i2).setCourseDirectoryBeanList(arrayList);
                }
            }
        }
        ThreadUtils.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.w1
            @Override // java.lang.Runnable
            public final void run() {
                this.f16086c.lambda$initViews$4();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$pauseData$11(List list) {
        if (list.isEmpty()) {
            return;
        }
        String[] strArr = new String[list.size()];
        for (int i2 = 0; i2 < list.size(); i2++) {
            strArr[i2] = (String) list.get(i2);
        }
        ProjectApp.database.getVideoDownDao().updataModelStatus(4, strArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$show4gDialog$6() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE}, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showNetChangeDialog$8(DialogInterface dialogInterface, int i2) {
        getCourseAk();
        if (dialogInterface != null) {
            dialogInterface.dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showNetChangeDialog$9(DialogInterface dialogInterface, int i2) {
        if (dialogInterface != null) {
            dialogInterface.dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showOpenDownloadDialog$7() {
        if (isLogin()) {
            goActivity(DownloadSetting.class);
        }
    }

    private void showNetChangeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        builder.setTitle("网络切换为4G");
        builder.setMessage("是否继续下载？");
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.a2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                this.f15429c.lambda$showNetChangeDialog$8(dialogInterface, i2);
            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.b2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                CourseCacheFragment.lambda$showNetChangeDialog$9(dialogInterface, i2);
            }
        });
        builder.create();
        builder.show();
    }

    private void showOpenDownloadDialog() {
        new XPopup.Builder(this.mContext).asCustom(new CancelConfrimPop(this.mContext, new CancelConfrimPop.ClickIml() { // from class: com.psychiatrygarden.fragmenthome.i2
            @Override // com.psychiatrygarden.widget.CancelConfrimPop.ClickIml
            public final void mClickIml() {
                this.f15659a.lambda$showOpenDownloadDialog$7();
            }
        }, "当前设置不允许流量下载，如仍需下载可以到【设置-下载设置】里开启", "温馨提示", "暂不开启", "去开启")).show();
    }

    public void autoPeekVideo() {
        List<VideoDownTempBean> list = ProjectApp.vids;
        if (list == null || list.size() <= 0) {
            return;
        }
        CommonUtil.initDownAliyunVideo(ProjectApp.vids.get(0).vid, ProjectApp.vids.get(0).acId, ProjectApp.vids.get(0).akSceret, ProjectApp.vids.get(0).securityToken);
    }

    public void deleteData() {
        CourseCoverBean courseCoverBean;
        CourseDirectoryBean courseDirectoryBean;
        try {
            if (this.adapter != null) {
                final ArrayList arrayList = new ArrayList();
                final ArrayList arrayList2 = new ArrayList();
                final ArrayList arrayList3 = new ArrayList();
                int i2 = 0;
                while (i2 < this.adapter.getData().size()) {
                    if ((this.adapter.getData().get(i2) instanceof CourseCoverBean) && (courseCoverBean = (CourseCoverBean) this.adapter.getData().get(i2)) != null && courseCoverBean.getChildNode() != null) {
                        int i3 = 0;
                        while (i3 < courseCoverBean.getChildNode().size()) {
                            if ((courseCoverBean.getChildNode().get(i3) instanceof CourseDirectoryBean) && (courseDirectoryBean = (CourseDirectoryBean) courseCoverBean.getChildNode().get(i3)) != null && courseDirectoryBean.getChildNode() != null) {
                                int i4 = 0;
                                while (i4 < courseDirectoryBean.getChildNode().size()) {
                                    if (courseDirectoryBean.getChildNode().get(i4) instanceof VideoDownBean) {
                                        VideoDownBean videoDownBean = (VideoDownBean) courseDirectoryBean.getChildNode().get(i4);
                                        if (videoDownBean.isSelect()) {
                                            arrayList3.add(videoDownBean.getVid());
                                            courseDirectoryBean.getChildNode().remove(videoDownBean);
                                            i4--;
                                        }
                                    }
                                    i4++;
                                }
                                if (courseDirectoryBean.getChildNode().size() <= 0) {
                                    arrayList2.add(courseDirectoryBean.getId() + "");
                                    courseCoverBean.getChildNode().remove(courseDirectoryBean);
                                    i3 += -1;
                                }
                            }
                            i3++;
                        }
                        if (courseCoverBean.getChildNode().size() <= 0) {
                            arrayList.add(courseCoverBean.getId() + "");
                            this.adapter.getData().remove(courseCoverBean);
                            i2 += -1;
                        }
                    }
                    i2++;
                }
                Iterator<BaseNode> it = this.adapter.getData().iterator();
                while (it.hasNext()) {
                    BaseNode next = it.next();
                    if (next instanceof VideoDownBean) {
                        VideoDownBean videoDownBean2 = (VideoDownBean) next;
                        if (videoDownBean2.isSelect) {
                            if (!hasIds(videoDownBean2.getVid() + "", arrayList3)) {
                                arrayList3.add(videoDownBean2.getVid() + "");
                            }
                            it.remove();
                        }
                    } else if (next instanceof CourseDirectoryBean) {
                        CourseDirectoryBean courseDirectoryBean2 = (CourseDirectoryBean) next;
                        if (courseDirectoryBean2.getSelect() == 1) {
                            if (!hasIds(courseDirectoryBean2.getId() + "", arrayList2)) {
                                arrayList2.add(courseDirectoryBean2.getId() + "");
                            }
                            it.remove();
                        }
                    } else if (next instanceof CourseCoverBean) {
                        CourseCoverBean courseCoverBean2 = (CourseCoverBean) next;
                        if (courseCoverBean2.getSelect() == 1) {
                            if (!hasIds(courseCoverBean2.getId() + "", arrayList)) {
                                arrayList.add(courseCoverBean2.getId() + "");
                            }
                            it.remove();
                        }
                    }
                }
                this.adapter.notifyDataSetChanged();
                ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.y1
                    @Override // java.lang.Runnable
                    public final void run() {
                        CourseCacheFragment.lambda$deleteData$12(arrayList, arrayList2, arrayList3);
                    }
                });
                if (!arrayList3.isEmpty()) {
                    for (int i5 = 0; i5 < arrayList3.size(); i5++) {
                        getRemoveData(arrayList3.get(i5));
                        AliyunDownloadMediaInfo aliyunDownloadMediaInfoHasAliyunVideo = hasAliyunVideo(arrayList3.get(i5));
                        if (aliyunDownloadMediaInfoHasAliyunVideo != null) {
                            ProjectApp.downloadManager.deleteFile(aliyunDownloadMediaInfoHasAliyunVideo);
                        }
                    }
                }
                autoPeekVideo();
                updateTextStatus();
                EventBus.getDefault().post("cnm");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void downloadData(String acId, String akSceret, String securityToken) {
        CourseCoverBean courseCoverBean;
        CourseDirectoryBean courseDirectoryBean;
        if (this.adapter != null) {
            final ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < this.adapter.getData().size(); i2++) {
                if ((this.adapter.getData().get(i2) instanceof CourseCoverBean) && (courseCoverBean = (CourseCoverBean) this.adapter.getData().get(i2)) != null && courseCoverBean.getChildNode() != null) {
                    for (int i3 = 0; i3 < courseCoverBean.getChildNode().size(); i3++) {
                        if ((courseCoverBean.getChildNode().get(i3) instanceof CourseDirectoryBean) && (courseDirectoryBean = (CourseDirectoryBean) courseCoverBean.getChildNode().get(i3)) != null && courseDirectoryBean.getChildNode() != null) {
                            for (int i4 = 0; i4 < courseDirectoryBean.getChildNode().size(); i4++) {
                                if (courseDirectoryBean.getChildNode().get(i4) instanceof VideoDownBean) {
                                    VideoDownBean videoDownBean = (VideoDownBean) courseDirectoryBean.getChildNode().get(i4);
                                    if (videoDownBean.isSelect() && videoDownBean.getmStatus() != 5 && videoDownBean.getmStatus() != 1) {
                                        videoDownBean.setmStatus(3);
                                        if (!hasVidData(videoDownBean.getVid(), ProjectApp.vids).booleanValue()) {
                                            ProjectApp.vids.add(new VideoDownTempBean(videoDownBean.getVid(), acId, akSceret, securityToken));
                                        }
                                        arrayList.add(videoDownBean.getVid());
                                    }
                                }
                            }
                        }
                    }
                }
            }
            this.adapter.notifyDataSetChanged();
            if (!arrayList.isEmpty()) {
                this.pauseTv.setText("暂停");
                ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.x1
                    @Override // java.lang.Runnable
                    public final void run() {
                        CourseCacheFragment.lambda$downloadData$10(arrayList);
                    }
                });
            }
        }
        List<VideoDownTempBean> list = ProjectApp.vids;
        if (list == null || list.size() <= 0) {
            return;
        }
        AliyunDownloadMediaInfo aliyunDownloadMediaInfoHasAliyunVideo = hasAliyunVideo(ProjectApp.vids.get(0).vid);
        if (aliyunDownloadMediaInfoHasAliyunVideo == null || ProjectApp.downloadManager.getmVidSts() == null) {
            CommonUtil.initDownAliyunVideo(ProjectApp.vids.get(0).vid, acId, akSceret, securityToken);
            return;
        }
        aliyunDownloadMediaInfoHasAliyunVideo.setStatus(AliyunDownloadMediaInfo.Status.Stop);
        if (aliyunDownloadMediaInfoHasAliyunVideo.getStatus() != AliyunDownloadMediaInfo.Status.Start) {
            ProjectApp.downloadManager.startDownload(aliyunDownloadMediaInfoHasAliyunVideo);
        }
    }

    public List<BaseNode> getAdaterData(List<CourseCoverBean> data) {
        ArrayList arrayList = new ArrayList();
        if (data != null && data.size() > 0) {
            for (int i2 = 0; i2 < data.size(); i2++) {
                CourseCoverBean courseCoverBean = data.get(i2);
                courseCoverBean.setExpanded(false);
                if (courseCoverBean.getCourseDirectoryBeanList() != null) {
                    for (int i3 = 0; i3 < courseCoverBean.getCourseDirectoryBeanList().size(); i3++) {
                        CourseDirectoryBean courseDirectoryBean = courseCoverBean.getCourseDirectoryBeanList().get(i3);
                        if (courseDirectoryBean != null && courseDirectoryBean.getVideoDownBeans() != null) {
                            for (int i4 = 0; i4 < courseDirectoryBean.getVideoDownBeans().size(); i4++) {
                                courseDirectoryBean.addChildren(courseDirectoryBean.getVideoDownBeans().get(i4));
                            }
                        }
                        courseCoverBean.addChildren(courseDirectoryBean);
                    }
                }
                arrayList.add(courseCoverBean);
            }
        }
        return arrayList;
    }

    public void getCourseAk() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getCourseAkApi, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.CourseCacheFragment.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ProjectApp.instance().hideDialogWindow();
                ToastUtil.shortToast(CourseCacheFragment.this.getActivity(), "获取视频信息失败！");
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
                        CourseCacheFragment.this.downloadData(DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObject.optJSONObject("data").optString("akId")), DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObject.optJSONObject("data").optString("akSecret")), DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObject.optJSONObject("data").optString("st")));
                    } else {
                        ToastUtil.shortToast(CourseCacheFragment.this.getActivity(), jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                ProjectApp.instance().hideDialogWindow();
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.activity_download_manager;
    }

    public void getRemoveData(String vidt) {
        Iterator<VideoDownTempBean> it = ProjectApp.vids.iterator();
        while (it.hasNext()) {
            if (it.next().vid.equals(vidt)) {
                it.remove();
                return;
            }
        }
    }

    public AliyunDownloadMediaInfo hasAliyunVideo(String vid) {
        if (YkBManager.getInstance().mDownloadMediaLists == null || YkBManager.getInstance().mDownloadMediaLists.size() <= 0) {
            return null;
        }
        for (AliyunDownloadMediaInfo aliyunDownloadMediaInfo : YkBManager.getInstance().mDownloadMediaLists) {
            if (vid.equals(aliyunDownloadMediaInfo.getVid())) {
                return aliyunDownloadMediaInfo;
            }
        }
        return null;
    }

    public boolean hasIds(String id, List<String> ids) {
        if (ids == null || ids.size() <= 0) {
            return false;
        }
        Iterator<String> it = ids.iterator();
        while (it.hasNext()) {
            if (id.equals(it.next())) {
                return true;
            }
        }
        return false;
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

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        Context context;
        int i2;
        this.relTitle = (RelativeLayout) holder.get(R.id.relTitle);
        this.mLine = holder.get(R.id.line);
        this.cnmrel = (RelativeLayout) holder.get(R.id.cnmrel);
        this.relTitle.setVisibility(8);
        this.mLine.setVisibility(8);
        this.deleteTv = (TextView) holder.get(R.id.deleteTv);
        this.pauseTv = (TextView) holder.get(R.id.pauseTv);
        this.contentTv = (TextView) holder.get(R.id.contentTv);
        this.managerLine = (LinearLayout) holder.get(R.id.managerLine);
        this.iconBack = (ImageView) holder.get(R.id.iconBack);
        this.recycledown = (RecyclerView) holder.get(R.id.recycledown);
        if (SkinManager.getCurrentSkinType(getActivity()) == 1) {
            this.recycledown.setBackgroundResource(R.color.app_bg_night);
        } else {
            this.recycledown.setBackgroundResource(R.color.white_background);
        }
        this.recycledown.setLayoutManager(new LinearLayoutManager(getActivity()));
        TextView textView = (TextView) holder.get(R.id.managerTv);
        this.managerTv = textView;
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.c2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CourseCacheFragment.lambda$initViews$0(view);
            }
        });
        this.iconBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.d2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15533c.lambda$initViews$1(view);
            }
        });
        this.deleteTv.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.e2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15557c.lambda$initViews$2(view);
            }
        });
        this.pauseTv.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.f2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15582c.lambda$initViews$3(view);
            }
        });
        CurriculumScheduleCardExpandNodeAdapter curriculumScheduleCardExpandNodeAdapter = new CurriculumScheduleCardExpandNodeAdapter(new ArrayList());
        this.adapter = curriculumScheduleCardExpandNodeAdapter;
        this.recycledown.setAdapter(curriculumScheduleCardExpandNodeAdapter);
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setGravity(17);
        linearLayout.setOrientation(1);
        ImageView imageView = new ImageView(getActivity());
        int currentSkinType = SkinManager.getCurrentSkinType(this.mContext);
        imageView.setImageResource(currentSkinType == 0 ? R.drawable.ic_empty_data_normal_day : R.drawable.ic_empty_data_normal_night);
        linearLayout.addView(imageView, new LinearLayout.LayoutParams(ScreenUtil.getPxByDp((Context) getActivity(), 100), ScreenUtil.getPxByDp((Context) getActivity(), 100)));
        TextView textView2 = new TextView(getActivity());
        textView2.setText("暂无数据");
        textView2.setTextSize(14.0f);
        if (currentSkinType == 0) {
            context = getContext();
            i2 = R.color.forth_txt_color;
        } else {
            context = getContext();
            i2 = R.color.fourth_text_color_night;
        }
        textView2.setTextColor(context.getColor(i2));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.topMargin = ScreenUtil.getPxByDp((Context) getActivity(), 16);
        textView2.setLayoutParams(layoutParams);
        linearLayout.addView(textView2);
        this.adapter.setEmptyView(linearLayout);
        ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.g2
            @Override // java.lang.Runnable
            public final void run() {
                this.f15608c.lambda$initViews$5();
            }
        });
        calculationCache();
        mRefreshDownloadData();
    }

    public int isHadVideoStatus(VideoDownBean videoDownBean) {
        List<VideoDownTempBean> list = ProjectApp.vids;
        if (list != null && list.size() > 0) {
            return videoDownBean.getmStatus();
        }
        if (videoDownBean.getmStatus() == 5) {
            return !new File(videoDownBean.getmSavePath()).exists() ? 6 : 5;
        }
        return 4;
    }

    public void mRefreshDownloadData() {
        YkBManager.getInstance().getVideoDownBean().observe(this, new Observer<AliyunDownloadMediaInfo>() { // from class: com.psychiatrygarden.fragmenthome.CourseCacheFragment.2
            @Override // androidx.lifecycle.Observer
            public void onChanged(@androidx.annotation.Nullable AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
                CourseCoverBean courseCoverBean;
                CourseDirectoryBean courseDirectoryBean;
                if (CourseCacheFragment.this.adapter == null || aliyunDownloadMediaInfo == null) {
                    return;
                }
                for (int i2 = 0; i2 < CourseCacheFragment.this.adapter.getData().size(); i2++) {
                    if ((CourseCacheFragment.this.adapter.getData().get(i2) instanceof CourseCoverBean) && (courseCoverBean = (CourseCoverBean) CourseCacheFragment.this.adapter.getData().get(i2)) != null && courseCoverBean.getChildNode() != null) {
                        for (int i3 = 0; i3 < courseCoverBean.getChildNode().size(); i3++) {
                            if ((courseCoverBean.getChildNode().get(i3) instanceof CourseDirectoryBean) && (courseDirectoryBean = (CourseDirectoryBean) courseCoverBean.getChildNode().get(i3)) != null && courseDirectoryBean.getChildNode() != null) {
                                int i4 = 0;
                                while (true) {
                                    if (i4 >= courseDirectoryBean.getChildNode().size()) {
                                        break;
                                    }
                                    if (courseDirectoryBean.getChildNode().get(i4) instanceof VideoDownBean) {
                                        VideoDownBean videoDownBean = (VideoDownBean) courseDirectoryBean.getChildNode().get(i4);
                                        if (aliyunDownloadMediaInfo.getVid().equals(videoDownBean.getVid() + "")) {
                                            videoDownBean.setmProgress(aliyunDownloadMediaInfo.getProgress());
                                            videoDownBean.setmSize(aliyunDownloadMediaInfo.getSize());
                                            switch (AnonymousClass3.$SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[aliyunDownloadMediaInfo.getStatus().ordinal()]) {
                                                case 1:
                                                case 2:
                                                    videoDownBean.setmStatus(0);
                                                    break;
                                                case 3:
                                                    videoDownBean.setmStatus(3);
                                                    break;
                                                case 4:
                                                    videoDownBean.setmStatus(1);
                                                    break;
                                                case 5:
                                                    videoDownBean.setmStatus(4);
                                                    break;
                                                case 6:
                                                    videoDownBean.setmStatus(5);
                                                    break;
                                                case 7:
                                                    videoDownBean.setmStatus(2);
                                                    break;
                                            }
                                            if (aliyunDownloadMediaInfo.getProgress() == 100) {
                                                videoDownBean.setmStatus(5);
                                            }
                                            CourseCacheFragment.this.adapter.notifyItemChanged(i2, Integer.valueOf(R2.attr.blur_border_width));
                                            CourseCacheFragment.this.mRefreshVideo(videoDownBean.getVid());
                                        }
                                    }
                                    i4++;
                                }
                            }
                        }
                    }
                }
                EventBus.getDefault().post("cnm");
            }
        });
    }

    public void mRefreshVideo(String vid) {
        if (this.adapter != null) {
            for (int i2 = 0; i2 < this.adapter.getData().size(); i2++) {
                if (this.adapter.getData().get(i2) instanceof VideoDownBean) {
                    if (vid.equals(((VideoDownBean) this.adapter.getData().get(i2)).getVid() + "")) {
                        this.adapter.notifyItemChanged(i2, Integer.valueOf(R2.attr.bl_selected_gradient_centerY));
                        return;
                    }
                }
            }
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        if ("open".equals(str)) {
            if (this.adapter.getData().isEmpty()) {
                return;
            }
            updateDataExpend(1);
            this.managerTv.setText("取消");
            this.contentTv.setVisibility(8);
            this.managerLine.setVisibility(0);
            return;
        }
        if (!"cancel".equals(str)) {
            if ("cnm".equals(str)) {
                calculationCache();
            }
        } else {
            if (this.adapter.getData().isEmpty()) {
                return;
            }
            updateDataExpend(0);
            this.managerTv.setText("全选");
            this.contentTv.setVisibility(0);
            this.managerLine.setVisibility(8);
        }
    }

    public void pauseData() {
        CourseCoverBean courseCoverBean;
        CourseDirectoryBean courseDirectoryBean;
        if (this.adapter != null) {
            final ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < this.adapter.getData().size(); i2++) {
                if ((this.adapter.getData().get(i2) instanceof CourseCoverBean) && (courseCoverBean = (CourseCoverBean) this.adapter.getData().get(i2)) != null && courseCoverBean.getChildNode() != null) {
                    for (int i3 = 0; i3 < courseCoverBean.getChildNode().size(); i3++) {
                        if ((courseCoverBean.getChildNode().get(i3) instanceof CourseDirectoryBean) && (courseDirectoryBean = (CourseDirectoryBean) courseCoverBean.getChildNode().get(i3)) != null && courseDirectoryBean.getChildNode() != null) {
                            for (int i4 = 0; i4 < courseDirectoryBean.getChildNode().size(); i4++) {
                                if (courseDirectoryBean.getChildNode().get(i4) instanceof VideoDownBean) {
                                    VideoDownBean videoDownBean = (VideoDownBean) courseDirectoryBean.getChildNode().get(i4);
                                    if (videoDownBean.isSelect() && videoDownBean.getmStatus() != 5) {
                                        videoDownBean.setmStatus(4);
                                        getRemoveData(videoDownBean.getVid());
                                        AliyunDownloadMediaInfo aliyunDownloadMediaInfoHasAliyunVideo = hasAliyunVideo(videoDownBean.getVid() + "");
                                        if (aliyunDownloadMediaInfoHasAliyunVideo != null) {
                                            ProjectApp.downloadManager.pauseDownload(aliyunDownloadMediaInfoHasAliyunVideo);
                                        }
                                        arrayList.add(videoDownBean.getVid());
                                    }
                                }
                            }
                        }
                    }
                }
            }
            this.adapter.notifyDataSetChanged();
            if (arrayList.isEmpty()) {
                return;
            }
            this.pauseTv.setText("下载");
            ThreadUtils.runOnSubThread(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.z1
                @Override // java.lang.Runnable
                public final void run() {
                    CourseCacheFragment.lambda$pauseData$11(arrayList);
                }
            });
        }
    }

    public void show4gDialog() {
        Context context = this.mContext;
        if (context == null) {
            return;
        }
        if (ContextCompat.checkSelfPermission(context, Permission.WRITE_EXTERNAL_STORAGE) != 0) {
            new XPopup.Builder(getActivity()).asCustom(new RequestMediaPermissionPop(getActivity(), new OnConfirmListener() { // from class: com.psychiatrygarden.fragmenthome.h2
                @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                public final void onConfirm() {
                    this.f15635a.lambda$show4gDialog$6();
                }
            })).show();
            return;
        }
        if (!CommonUtil.isNetworkConnected(this.mContext)) {
            NewToast.showShort(this.mContext, "当前无网络连接", 0).show();
        } else if (CommonUtil.isWifi(this.mContext) || UserConfig.isCanDownloadBy4g(this.mContext)) {
            getCourseAk();
        } else {
            showOpenDownloadDialog();
        }
    }

    public void updateDataExpend(int status) {
        if (this.adapter != null) {
            for (int i2 = 0; i2 < this.adapter.getData().size(); i2++) {
                if (this.adapter.getData().get(i2) instanceof CourseCoverBean) {
                    CourseCoverBean courseCoverBean = (CourseCoverBean) this.adapter.getData().get(i2);
                    courseCoverBean.setSelect(status);
                    courseCoverBean.setShowBtn(status == 1);
                    if (courseCoverBean.getChildNode() != null) {
                        for (int i3 = 0; i3 < courseCoverBean.getChildNode().size(); i3++) {
                            if (courseCoverBean.getChildNode().get(i3) instanceof CourseDirectoryBean) {
                                CourseDirectoryBean courseDirectoryBean = (CourseDirectoryBean) courseCoverBean.getChildNode().get(i3);
                                courseDirectoryBean.setSelect(status);
                                courseDirectoryBean.setShowBtn(status == 1);
                                if (courseDirectoryBean.getChildNode() != null) {
                                    for (int i4 = 0; i4 < courseDirectoryBean.getChildNode().size(); i4++) {
                                        if (courseDirectoryBean.getChildNode().get(i4) instanceof VideoDownBean) {
                                            VideoDownBean videoDownBean = (VideoDownBean) courseDirectoryBean.getChildNode().get(i4);
                                            videoDownBean.setSelect(status == 1);
                                            videoDownBean.setShowBtn(status == 1);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            this.adapter.notifyDataSetChanged();
        }
    }

    public void updateTextStatus() {
        CourseCoverBean courseCoverBean;
        CourseDirectoryBean courseDirectoryBean;
        this.contentTv.setVisibility(0);
        this.managerLine.setVisibility(8);
        this.pauseTv.setText("下载");
        if (this.adapter != null) {
            for (int i2 = 0; i2 < this.adapter.getData().size(); i2++) {
                if ((this.adapter.getData().get(i2) instanceof CourseCoverBean) && (courseCoverBean = (CourseCoverBean) this.adapter.getData().get(i2)) != null && courseCoverBean.getChildNode() != null) {
                    for (int i3 = 0; i3 < courseCoverBean.getChildNode().size(); i3++) {
                        if ((courseCoverBean.getChildNode().get(i3) instanceof CourseDirectoryBean) && (courseDirectoryBean = (CourseDirectoryBean) courseCoverBean.getChildNode().get(i3)) != null && courseDirectoryBean.getChildNode() != null) {
                            for (int i4 = 0; i4 < courseDirectoryBean.getChildNode().size(); i4++) {
                                if (courseDirectoryBean.getChildNode().get(i4) instanceof VideoDownBean) {
                                    VideoDownBean videoDownBean = (VideoDownBean) courseDirectoryBean.getChildNode().get(i4);
                                    if (videoDownBean.isSelect()) {
                                        this.contentTv.setVisibility(8);
                                        this.managerLine.setVisibility(0);
                                        upmanagerTvTxt();
                                        if (videoDownBean.getmStatus() == 1) {
                                            this.pauseTv.setText("暂停");
                                            return;
                                        }
                                    } else {
                                        continue;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void upmanagerTvTxt() {
        CurriculumScheduleCardExpandNodeAdapter curriculumScheduleCardExpandNodeAdapter;
        if (this.managerTv == null || (curriculumScheduleCardExpandNodeAdapter = this.adapter) == null) {
            return;
        }
        for (BaseNode baseNode : curriculumScheduleCardExpandNodeAdapter.getData()) {
            if ((baseNode instanceof CourseCoverBean) && ((CourseCoverBean) baseNode).getSelect() != 1) {
                this.managerTv.setText("全选");
                return;
            }
        }
        this.managerTv.setText("取消");
    }
}
