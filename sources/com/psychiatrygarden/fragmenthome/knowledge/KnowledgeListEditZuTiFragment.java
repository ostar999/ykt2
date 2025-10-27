package com.psychiatrygarden.fragmenthome.knowledge;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.Key;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.vod.common.utils.UriUtil;
import com.easefun.polyv.livecommon.ui.widget.expandmenu.utils.DpOrPxUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewBean;
import com.psychiatrygarden.activity.online.bean.QuestionDetailBean;
import com.psychiatrygarden.activity.q2;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.bean.CourseDirectoryTreeBean;
import com.psychiatrygarden.bean.KnowledgeChartNodeBean;
import com.psychiatrygarden.event.RedoMultiKnowledgeEvent;
import com.psychiatrygarden.event.RedoOtherQuestionEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.QuestionDataRequest;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.QuestionDataCallBack;
import com.psychiatrygarden.utils.AliyunEvent;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.KnowledgeTreeNodeUtilKt;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CustomDialog;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.psychiatrygarden.widget.treenode.TreeNode;
import com.psychiatrygarden.widget.treenode.TreeNodeAdapter;
import com.psychiatrygarden.widget.treenode.TreeNodeDelegate;
import com.psychiatrygarden.widget.treenode.ViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Triple;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class KnowledgeListEditZuTiFragment extends BaseFragment implements QuestionDataCallBack<String> {
    private static final String TAG = "KnowledgeListEditFragment";
    private TreeNodeAdapter<KnowledgeChartNodeBean> adapter;
    private boolean baseQuestionBankRedo;
    private String category;
    private String chapterId;
    private int drawableIdAllSelect;
    private int drawableIdPartSelect;
    private int drawableIdUnSelect;
    private CustomEmptyView emptyView;
    private TreeNode<KnowledgeChartNodeBean> firstExpandTree;
    private String identityId;
    private boolean isChoosedCurrentChapter;
    private RecyclerView mRecyclerView;
    private String moduleType;
    private SmartRefreshLayout refreshLayout;
    private TreeNode<KnowledgeChartNodeBean> secondExpandTree;
    private SelectNumChangeListener selectNumChangeListener;
    private boolean studyPlanSelect;
    private String treeId;
    private String type;
    private String unitId;
    private List<TreeNode<KnowledgeChartNodeBean>> list = new ArrayList();
    private List<KnowledgeChartNodeBean> dataList = new ArrayList();
    private HashMap<String, Integer> noteCountMap = new HashMap<>();
    private List<String> selectIds = new ArrayList();
    private int selectCount = 0;
    private int allCount = 0;
    private List<String> preSelectIds = new ArrayList();
    private List<String> chooseChapterId = new ArrayList();
    private List<String> chooseChapterTitleList = new ArrayList();
    private Map<String, List<String>> mStringListMap = new ArrayMap();
    private Map<String, List<String>> mStringListTitleMap = new ArrayMap();

    /* renamed from: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditZuTiFragment$2, reason: invalid class name */
    public class AnonymousClass2 extends TreeNodeDelegate<KnowledgeChartNodeBean> {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0() {
            KnowledgeListEditZuTiFragment.this.getQuestionBankData();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$1(boolean z2, KnowledgeChartNodeBean knowledgeChartNodeBean, TreeNode treeNode, View view) {
            String activity_id;
            if (z2) {
                if (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
                    KnowledgeListEditZuTiFragment.this.toastOnUiThread("没有考点");
                    return;
                }
                boolean zAllSelect = KnowledgeTreeNodeUtilKt.allSelect(treeNode);
                if (KnowledgeListEditZuTiFragment.this.baseQuestionBankRedo) {
                    treeNode.setSelect(!treeNode.isSelect());
                }
                KnowledgeListEditZuTiFragment.this.selectOperaAllDirectory(treeNode, this.adapter, Boolean.valueOf(!zAllSelect));
                KnowledgeListEditZuTiFragment knowledgeListEditZuTiFragment = KnowledgeListEditZuTiFragment.this;
                knowledgeListEditZuTiFragment.updateSelectNoteCount(knowledgeListEditZuTiFragment.list);
                return;
            }
            if (!KnowledgeListEditZuTiFragment.this.baseQuestionBankRedo) {
                NewToast.showShort(KnowledgeListEditZuTiFragment.this.getActivity(), "该章节未解锁");
                return;
            }
            AjaxParams ajaxParams = new AjaxParams();
            Iterator it = KnowledgeListEditZuTiFragment.this.dataList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    activity_id = "";
                    break;
                }
                KnowledgeChartNodeBean knowledgeChartNodeBean2 = (KnowledgeChartNodeBean) it.next();
                if (TextUtils.equals(knowledgeChartNodeBean2.getId(), knowledgeChartNodeBean.getId())) {
                    activity_id = knowledgeChartNodeBean2.getActivity_id();
                    break;
                }
            }
            ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, activity_id);
            MemInterface.getInstance().getMemData(KnowledgeListEditZuTiFragment.this.getActivity(), ajaxParams, false, 0);
            MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.q
                @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
                public final void mUShareListener() {
                    this.f15772a.lambda$convert$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$2(boolean z2, TreeNode treeNode, View view) {
            if (!z2) {
                NewToast.showShort(KnowledgeListEditZuTiFragment.this.getActivity(), "该章节未解锁");
                return;
            }
            if (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
                KnowledgeListEditZuTiFragment.this.toastOnUiThread("没有考点");
                return;
            }
            boolean zAllSelect = KnowledgeTreeNodeUtilKt.allSelect(treeNode);
            if (KnowledgeListEditZuTiFragment.this.baseQuestionBankRedo) {
                treeNode.setSelect(!treeNode.isSelect());
            }
            KnowledgeListEditZuTiFragment.this.selectOperaAllDirectory(treeNode, this.adapter, Boolean.valueOf(!zAllSelect));
            KnowledgeListEditZuTiFragment knowledgeListEditZuTiFragment = KnowledgeListEditZuTiFragment.this;
            knowledgeListEditZuTiFragment.updateSelectNoteCount(knowledgeListEditZuTiFragment.list);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$3() {
            KnowledgeListEditZuTiFragment.this.getQuestionBankData();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$4(boolean z2, KnowledgeChartNodeBean knowledgeChartNodeBean, TreeNode treeNode, View view) {
            String activity_id;
            if (z2) {
                if (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
                    KnowledgeListEditZuTiFragment.this.toastOnUiThread("该目录下没有考点");
                    return;
                } else {
                    this.adapter.expandOrCollapseTreeNode(treeNode);
                    return;
                }
            }
            if (!KnowledgeListEditZuTiFragment.this.baseQuestionBankRedo) {
                NewToast.showShort(KnowledgeListEditZuTiFragment.this.getActivity(), "该章节未解锁");
                return;
            }
            AjaxParams ajaxParams = new AjaxParams();
            Iterator it = KnowledgeListEditZuTiFragment.this.dataList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    activity_id = "";
                    break;
                }
                KnowledgeChartNodeBean knowledgeChartNodeBean2 = (KnowledgeChartNodeBean) it.next();
                if (TextUtils.equals(knowledgeChartNodeBean2.getId(), knowledgeChartNodeBean.getId())) {
                    activity_id = knowledgeChartNodeBean2.getActivity_id();
                    break;
                }
            }
            ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, activity_id);
            MemInterface.getInstance().getMemData(KnowledgeListEditZuTiFragment.this.getActivity(), ajaxParams, false, 0);
            MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.r
                @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
                public final void mUShareListener() {
                    this.f15773a.lambda$convert$3();
                }
            });
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public void convert(ViewHolder holder, final TreeNode<KnowledgeChartNodeBean> treeNode) {
            ImageView imageView = (ImageView) holder.getView(R.id.firstSelectImage);
            ImageView imageView2 = (ImageView) holder.getView(R.id.imgArrow);
            TextView textView = (TextView) holder.getView(R.id.firstTitle);
            final KnowledgeChartNodeBean value = treeNode.getValue();
            textView.setText(value.getName());
            int selectImg = KnowledgeListEditZuTiFragment.this.getSelectImg(treeNode);
            if (selectImg == KnowledgeListEditZuTiFragment.this.drawableIdAllSelect) {
                value.setSelectAll(true);
                value.setChildSelect(true);
            } else if (selectImg == KnowledgeListEditZuTiFragment.this.drawableIdPartSelect) {
                value.setSelectAll(false);
                value.setChildSelect(true);
            } else if (selectImg == KnowledgeListEditZuTiFragment.this.drawableIdUnSelect) {
                value.setSelectAll(false);
                value.setChildSelect(false);
            }
            final boolean zEquals = "1".equals(value.getHas_permission());
            if (zEquals) {
                imageView.setImageDrawable(KnowledgeListEditZuTiFragment.this.getResources().getDrawable(selectImg));
            } else {
                imageView.setImageDrawable(KnowledgeListEditZuTiFragment.this.getResources().getDrawable(SkinManager.getCurrentSkinType(KnowledgeListEditZuTiFragment.this.getContext()) == 1 ? R.drawable.homepage_question_password_night : R.drawable.homepage_question_password));
            }
            imageView.setVisibility(0);
            Log.d(KnowledgeListEditZuTiFragment.TAG, "convert: 刷新一级目录： " + treeNode.getValue().getName());
            if (!TextUtils.isEmpty(KnowledgeListEditZuTiFragment.this.type) || KnowledgeListEditZuTiFragment.this.studyPlanSelect || KnowledgeListEditZuTiFragment.this.baseQuestionBankRedo) {
                textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.s
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f15774c.lambda$convert$1(zEquals, value, treeNode, view);
                    }
                });
            }
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.t
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f15778c.lambda$convert$2(zEquals, treeNode, view);
                }
            });
            holder.getConvertView().setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.u
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f15781c.lambda$convert$4(zEquals, value, treeNode, view);
                }
            });
            imageView2.setVisibility(0);
            if (!treeNode.isExpand()) {
                KnowledgeListEditZuTiFragment.this.setArrowSpin(holder, treeNode, false);
            } else {
                KnowledgeListEditZuTiFragment.this.setArrowSpin(holder, treeNode, false);
                KnowledgeListEditZuTiFragment.this.firstExpandTree = treeNode;
            }
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public int getLayoutId() {
            return R.layout.item_node_select_first;
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public boolean isItemType(TreeNode<KnowledgeChartNodeBean> treeNode) {
            return treeNode.getCustomerLevel() == 0;
        }
    }

    /* renamed from: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditZuTiFragment$3, reason: invalid class name */
    public class AnonymousClass3 extends TreeNodeDelegate<KnowledgeChartNodeBean> {
        public AnonymousClass3() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(boolean z2, TreeNode treeNode, View view) {
            if (KnowledgeListEditZuTiFragment.this.baseQuestionBankRedo) {
                return;
            }
            if (!z2) {
                NewToast.showShort(KnowledgeListEditZuTiFragment.this.getActivity(), "该章节未解锁");
            } else if (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
                KnowledgeListEditZuTiFragment.this.toastOnUiThread("该目录下没有考点");
            } else {
                this.adapter.expandOrCollapseTreeNode(treeNode);
            }
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public void convert(ViewHolder holder, final TreeNode<KnowledgeChartNodeBean> treeNode) {
            ImageView imageView = (ImageView) holder.getView(R.id.imgArrow);
            TextView textView = (TextView) holder.getView(R.id.secondTitle);
            ImageView imageView2 = (ImageView) holder.getView(R.id.secondSelectImage);
            KnowledgeChartNodeBean value = treeNode.getValue();
            textView.setText(value.getName());
            int selectImg = KnowledgeListEditZuTiFragment.this.getSelectImg(treeNode);
            boolean zLevel2IsAllSelect = KnowledgeListEditZuTiFragment.this.level2IsAllSelect(treeNode);
            if (selectImg == KnowledgeListEditZuTiFragment.this.drawableIdAllSelect) {
                value.setSelectAll(true);
                value.setChildSelect(true);
            } else if (selectImg == KnowledgeListEditZuTiFragment.this.drawableIdPartSelect) {
                value.setSelectAll(false);
                value.setChildSelect(true);
            } else if (selectImg == KnowledgeListEditZuTiFragment.this.drawableIdUnSelect) {
                value.setSelectAll(false);
                value.setChildSelect(false);
            }
            value.setSelectAll(zLevel2IsAllSelect);
            final boolean zEquals = "1".equals(value.getHas_permission());
            if (zEquals) {
                imageView2.setImageDrawable(KnowledgeListEditZuTiFragment.this.getResources().getDrawable(selectImg));
            } else {
                imageView2.setImageDrawable(KnowledgeListEditZuTiFragment.this.getResources().getDrawable(SkinManager.getCurrentSkinType(KnowledgeListEditZuTiFragment.this.getContext()) == 1 ? R.drawable.homepage_question_password_night : R.drawable.homepage_question_password));
            }
            imageView2.setVisibility(0);
            if (KnowledgeListEditZuTiFragment.this.baseQuestionBankRedo) {
                imageView2.setVisibility(8);
            } else {
                imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditZuTiFragment.3.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v2) {
                        if (!zEquals) {
                            NewToast.showShort(KnowledgeListEditZuTiFragment.this.getActivity(), "该章节未解锁");
                            return;
                        }
                        if (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
                            KnowledgeListEditZuTiFragment.this.toastOnUiThread("没有考点");
                            return;
                        }
                        boolean zAllSelect = KnowledgeTreeNodeUtilKt.allSelect(treeNode);
                        AnonymousClass3 anonymousClass3 = AnonymousClass3.this;
                        KnowledgeListEditZuTiFragment.this.selectOperaAllDirectory(treeNode, anonymousClass3.adapter, Boolean.valueOf(!zAllSelect));
                        KnowledgeListEditZuTiFragment knowledgeListEditZuTiFragment = KnowledgeListEditZuTiFragment.this;
                        knowledgeListEditZuTiFragment.updateSelectNoteCount(knowledgeListEditZuTiFragment.list);
                        if (treeNode.getParent() != null) {
                            AnonymousClass3.this.adapter.notifyTreeNode(treeNode.getParent());
                        }
                    }
                });
            }
            imageView.setVisibility(0);
            if (KnowledgeListEditZuTiFragment.this.baseQuestionBankRedo) {
                imageView.setVisibility(8);
            }
            holder.getConvertView().setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.v
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f15785c.lambda$convert$0(zEquals, treeNode, view);
                }
            });
            if (!treeNode.isExpand()) {
                KnowledgeListEditZuTiFragment.this.setArrowSpin(holder, treeNode, false);
            } else {
                KnowledgeListEditZuTiFragment.this.setArrowSpin(holder, treeNode, false);
                KnowledgeListEditZuTiFragment.this.secondExpandTree = treeNode;
            }
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public int getLayoutId() {
            return !KnowledgeListEditZuTiFragment.this.baseQuestionBankRedo ? R.layout.item_knowledge_second : R.layout.item_knowledge_second_question;
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public boolean isItemType(TreeNode<KnowledgeChartNodeBean> treeNode) {
            return treeNode.getCustomerLevel() == 1;
        }
    }

    /* renamed from: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditZuTiFragment$4, reason: invalid class name */
    public class AnonymousClass4 extends TreeNodeDelegate<KnowledgeChartNodeBean> {
        public AnonymousClass4() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(boolean z2, TreeNode treeNode, View view) {
            if (!z2) {
                NewToast.showShort(KnowledgeListEditZuTiFragment.this.getActivity(), "该章节未解锁");
                return;
            }
            ((KnowledgeChartNodeBean) treeNode.getValue()).setSelect(!((KnowledgeChartNodeBean) treeNode.getValue()).isSelect());
            this.adapter.notifyTreeNode(treeNode);
            if (treeNode.getParent() != null) {
                this.adapter.notifyTreeNode(treeNode.getParent());
            }
            if (treeNode.getParent() != null && treeNode.getParent().getParent() != null) {
                this.adapter.notifyTreeNode(treeNode.getParent().getParent());
            }
            KnowledgeListEditZuTiFragment knowledgeListEditZuTiFragment = KnowledgeListEditZuTiFragment.this;
            knowledgeListEditZuTiFragment.updateSelectNoteCount(knowledgeListEditZuTiFragment.list);
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public void convert(ViewHolder holder, final TreeNode<KnowledgeChartNodeBean> treeNode) {
            int absoluteAdapterPosition = holder.getAbsoluteAdapterPosition();
            int iDip2px = DpOrPxUtils.dip2px(((BaseFragment) KnowledgeListEditZuTiFragment.this).mContext, 12.0f);
            Log.d(KnowledgeListEditZuTiFragment.TAG, "convert: 第三级数据： pos:" + absoluteAdapterPosition + "--- value: " + treeNode.getValue().getName());
            RelativeLayout relativeLayout = (RelativeLayout) holder.getView(R.id.itemInnerLayout);
            ImageView imageView = (ImageView) holder.getView(R.id.thirdSelectImage);
            View view = holder.getView(R.id.thirdItemLine);
            TextView textView = (TextView) holder.getView(R.id.thirdTitle);
            KnowledgeChartNodeBean value = treeNode.getValue();
            textView.setText(value.getName());
            relativeLayout.setPadding(iDip2px, 0, iDip2px, 0);
            if (KnowledgeTreeNodeUtilKt.isOnlyOneItem(treeNode)) {
                view.setVisibility(4);
                relativeLayout.setBackground(KnowledgeListEditZuTiFragment.this.getResources().getDrawable(R.drawable.shape_new_bg_two_color_corner12, ((BaseFragment) KnowledgeListEditZuTiFragment.this).mContext.getTheme()));
            } else if (KnowledgeTreeNodeUtilKt.isTopRadiusItem(treeNode)) {
                view.setVisibility(0);
                relativeLayout.setBackground(KnowledgeListEditZuTiFragment.this.getResources().getDrawable(R.drawable.shape_new_bg_two_color_top_corner12, ((BaseFragment) KnowledgeListEditZuTiFragment.this).mContext.getTheme()));
            } else if (KnowledgeTreeNodeUtilKt.isBottomRadiusItem(treeNode)) {
                view.setVisibility(4);
                relativeLayout.setBackground(KnowledgeListEditZuTiFragment.this.getResources().getDrawable(R.drawable.shape_new_bg_two_color_bottom_corner12, ((BaseFragment) KnowledgeListEditZuTiFragment.this).mContext.getTheme()));
            } else {
                view.setVisibility(0);
                relativeLayout.setBackground(KnowledgeListEditZuTiFragment.this.getResources().getDrawable(R.drawable.shape_project_normal_bg, ((BaseFragment) KnowledgeListEditZuTiFragment.this).mContext.getTheme()));
            }
            int selectImg = KnowledgeListEditZuTiFragment.this.getSelectImg(treeNode);
            final boolean zEquals = "1".equals(value.getHas_permission());
            if (zEquals) {
                imageView.setImageDrawable(KnowledgeListEditZuTiFragment.this.getResources().getDrawable(selectImg));
            } else {
                imageView.setImageDrawable(KnowledgeListEditZuTiFragment.this.getResources().getDrawable(SkinManager.getCurrentSkinType(KnowledgeListEditZuTiFragment.this.getContext()) == 1 ? R.drawable.homepage_question_password_night : R.drawable.homepage_question_password));
            }
            imageView.setVisibility(0);
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.w
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f15788c.lambda$convert$0(zEquals, treeNode, view2);
                }
            });
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public int getLayoutId() {
            return R.layout.item_knowledge_third;
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public boolean isItemType(TreeNode<KnowledgeChartNodeBean> treeNode) {
            return treeNode.getCustomerLevel() == 2;
        }
    }

    public interface SelectNumChangeListener {
        void selectNum(int num);
    }

    private boolean childIsVideoItem(TreeNode<CourseDirectoryTreeBean> treeNode) {
        return (treeNode == null || treeNode.getCustomerLevel() != 0 || treeNode.getChildren() == null || treeNode.getChildren().isEmpty() || treeNode.getChildren().get(0).getCustomerLevel() != 2) ? false : true;
    }

    private int getAllCountMethod(List<TreeNode<KnowledgeChartNodeBean>> list) {
        int size = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            TreeNode<KnowledgeChartNodeBean> treeNode = list.get(i2);
            if (!this.baseQuestionBankRedo) {
                List<TreeNode<KnowledgeChartNodeBean>> children = treeNode.getChildren();
                if (children != null && !children.isEmpty()) {
                    for (int i3 = 0; i3 < children.size(); i3++) {
                        TreeNode<KnowledgeChartNodeBean> treeNode2 = children.get(i3);
                        if (treeNode2.getChildren() != null && !treeNode2.getChildren().isEmpty()) {
                            size += treeNode2.getChildren().size();
                        }
                    }
                }
            } else if ("1".equals(treeNode.getValue().getHas_permission())) {
                size++;
            }
        }
        return size;
    }

    private void getListData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("part_id", this.treeId);
        ajaxParams.put(KnowledgeQuestionListFragment.EXTRA_TREE_ID, this.treeId);
        ajaxParams.put("type", this.type);
        if (this.baseQuestionBankRedo) {
            getQuestionBankData();
            return;
        }
        if (this.studyPlanSelect) {
            ajaxParams.put("field", "knowledge_tree");
            YJYHttpUtils.post(this.mContext, NetworkRequestsURL.studyPlanNodeParams, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditZuTiFragment.5
                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onFailure(Throwable t2, int errorNo, String strMsg) {
                    super.onFailure(t2, errorNo, strMsg);
                }

                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onSuccess(String s2) {
                    super.onSuccess((AnonymousClass5) s2);
                    try {
                        KnowledgeListEditZuTiFragment.this.refreshLayout.finishRefresh(true);
                        JSONObject jSONObject = new JSONObject(s2);
                        if (!jSONObject.optString("code").equals("200")) {
                            KnowledgeListEditZuTiFragment.this.emptyView.uploadEmptyViewResUi();
                            KnowledgeListEditZuTiFragment.this.emptyView.setVisibility(0);
                            KnowledgeListEditZuTiFragment.this.mRecyclerView.setVisibility(8);
                            return;
                        }
                        List<KnowledgeChartNodeBean> list = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<KnowledgeChartNodeBean>>() { // from class: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditZuTiFragment.5.1
                        }.getType());
                        KnowledgeListEditZuTiFragment.this.dataList.clear();
                        for (KnowledgeChartNodeBean knowledgeChartNodeBean : list) {
                            knowledgeChartNodeBean.setHas_permission("1");
                            knowledgeChartNodeBean.setLevel(1);
                            KnowledgeListEditZuTiFragment.this.dataList.add(knowledgeChartNodeBean);
                            List<KnowledgeChartNodeBean> children = knowledgeChartNodeBean.getChildren();
                            if (children != null) {
                                KnowledgeListEditZuTiFragment.this.dataList.addAll(children);
                                for (KnowledgeChartNodeBean knowledgeChartNodeBean2 : children) {
                                    knowledgeChartNodeBean2.setLevel(2);
                                    knowledgeChartNodeBean2.setParentId(knowledgeChartNodeBean.getId());
                                    knowledgeChartNodeBean2.setHas_permission("1");
                                    List<KnowledgeChartNodeBean> children2 = knowledgeChartNodeBean2.getChildren();
                                    if (children2 != null) {
                                        KnowledgeListEditZuTiFragment.this.dataList.addAll(children2);
                                        for (KnowledgeChartNodeBean knowledgeChartNodeBean3 : children2) {
                                            knowledgeChartNodeBean3.setLevel(3);
                                            knowledgeChartNodeBean3.setHas_permission("1");
                                            knowledgeChartNodeBean3.setParentId(knowledgeChartNodeBean2.getId());
                                            knowledgeChartNodeBean3.setChapterId(knowledgeChartNodeBean.getId());
                                        }
                                    }
                                }
                            }
                        }
                        KnowledgeListEditZuTiActivity knowledgeListEditZuTiActivity = (KnowledgeListEditZuTiActivity) KnowledgeListEditZuTiFragment.this.getActivity();
                        if (knowledgeListEditZuTiActivity != null) {
                            knowledgeListEditZuTiActivity.initSelectedData(KnowledgeListEditZuTiFragment.this.dataList);
                        }
                        KnowledgeListEditZuTiFragment.this.initListLayout(list);
                        KnowledgeListEditZuTiFragment.this.emptyView.setVisibility(8);
                        KnowledgeListEditZuTiFragment.this.mRecyclerView.setVisibility(0);
                    } catch (Exception e2) {
                        Log.e(KnowledgeListEditZuTiFragment.TAG, "onSuccess: 请求目录列表异常:" + e2.getMessage());
                        KnowledgeListEditZuTiFragment.this.emptyView.setLoadFileResUi(KnowledgeListEditZuTiFragment.this.getContext());
                        KnowledgeListEditZuTiFragment.this.emptyView.setVisibility(0);
                        KnowledgeListEditZuTiFragment.this.mRecyclerView.setVisibility(8);
                    }
                }
            });
        } else if (TextUtils.isEmpty(this.type)) {
            YJYHttpUtils.get(this.mContext, NetworkRequestsURL.knowledgeList_zz_zt, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditZuTiFragment.7
                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onFailure(Throwable t2, int errorNo, String strMsg) {
                    super.onFailure(t2, errorNo, strMsg);
                    KnowledgeListEditZuTiFragment.this.emptyView.setLoadFileResUi(KnowledgeListEditZuTiFragment.this.getContext());
                    KnowledgeListEditZuTiFragment.this.emptyView.setVisibility(0);
                    KnowledgeListEditZuTiFragment.this.mRecyclerView.setVisibility(8);
                }

                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onStart() {
                    super.onStart();
                }

                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onSuccess(String t2) {
                    super.onSuccess((AnonymousClass7) t2);
                    try {
                        KnowledgeListEditZuTiFragment.this.refreshLayout.finishRefresh(true);
                        JSONObject jSONObject = new JSONObject(t2);
                        if (jSONObject.optString("code").equals("200")) {
                            KnowledgeListEditZuTiFragment.this.initListLayout((List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<KnowledgeChartNodeBean>>() { // from class: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditZuTiFragment.7.1
                            }.getType()));
                            KnowledgeListEditZuTiFragment.this.emptyView.setVisibility(8);
                            KnowledgeListEditZuTiFragment.this.mRecyclerView.setVisibility(0);
                        } else {
                            KnowledgeListEditZuTiFragment.this.emptyView.uploadEmptyViewResUi();
                            KnowledgeListEditZuTiFragment.this.emptyView.setVisibility(0);
                            KnowledgeListEditZuTiFragment.this.mRecyclerView.setVisibility(8);
                        }
                    } catch (Exception e2) {
                        Log.e(KnowledgeListEditZuTiFragment.TAG, "onSuccess: 请求目录列表异常:" + e2.getMessage());
                        KnowledgeListEditZuTiFragment.this.emptyView.setLoadFileResUi(KnowledgeListEditZuTiFragment.this.getContext());
                        KnowledgeListEditZuTiFragment.this.emptyView.setVisibility(0);
                        KnowledgeListEditZuTiFragment.this.mRecyclerView.setVisibility(8);
                    }
                }
            });
        } else {
            YJYHttpUtils.post(this.mContext, NetworkRequestsURL.redoKnowledgeTree, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditZuTiFragment.6
                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onFailure(Throwable t2, int errorNo, String strMsg) {
                    super.onFailure(t2, errorNo, strMsg);
                }

                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onSuccess(String s2) {
                    super.onSuccess((AnonymousClass6) s2);
                    try {
                        KnowledgeListEditZuTiFragment.this.refreshLayout.finishRefresh(true);
                        JSONObject jSONObject = new JSONObject(s2);
                        if (!jSONObject.optString("code").equals("200")) {
                            KnowledgeListEditZuTiFragment.this.emptyView.uploadEmptyViewResUi();
                            KnowledgeListEditZuTiFragment.this.emptyView.setVisibility(0);
                            KnowledgeListEditZuTiFragment.this.mRecyclerView.setVisibility(8);
                            return;
                        }
                        List<KnowledgeChartNodeBean> list = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<KnowledgeChartNodeBean>>() { // from class: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditZuTiFragment.6.1
                        }.getType());
                        for (KnowledgeChartNodeBean knowledgeChartNodeBean : list) {
                            knowledgeChartNodeBean.setHas_permission("1");
                            List<KnowledgeChartNodeBean> children = knowledgeChartNodeBean.getChildren();
                            if (children != null) {
                                for (KnowledgeChartNodeBean knowledgeChartNodeBean2 : children) {
                                    knowledgeChartNodeBean2.setHas_permission("1");
                                    List<KnowledgeChartNodeBean> children2 = knowledgeChartNodeBean2.getChildren();
                                    if (children2 != null) {
                                        Iterator<KnowledgeChartNodeBean> it = children2.iterator();
                                        while (it.hasNext()) {
                                            it.next().setHas_permission("1");
                                        }
                                    }
                                }
                            }
                        }
                        KnowledgeListEditZuTiFragment.this.initListLayout(list);
                        KnowledgeListEditZuTiFragment.this.emptyView.setVisibility(8);
                        KnowledgeListEditZuTiFragment.this.mRecyclerView.setVisibility(0);
                    } catch (Exception e2) {
                        Log.e(KnowledgeListEditZuTiFragment.TAG, "onSuccess: 请求目录列表异常:" + e2.getMessage());
                        KnowledgeListEditZuTiFragment.this.emptyView.setLoadFileResUi(KnowledgeListEditZuTiFragment.this.getContext());
                        KnowledgeListEditZuTiFragment.this.emptyView.setVisibility(0);
                        KnowledgeListEditZuTiFragment.this.mRecyclerView.setVisibility(8);
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getQuestionBankData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("identity_id", this.identityId);
        ajaxParams.put("module_type", this.moduleType);
        ajaxParams.put("type", this.type);
        if (this.category.equals("unit")) {
            ajaxParams.put(UriUtil.QUERY_CATEGORY, "unit");
        } else {
            ajaxParams.put(UriUtil.QUERY_CATEGORY, "chapter");
        }
        if (!TextUtils.isEmpty(this.unitId)) {
            ajaxParams.put("unit_id", this.unitId);
        }
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.redoOtherChapterList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditZuTiFragment.8
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                KnowledgeListEditZuTiFragment.this.refreshLayout.finishRefresh(false);
                KnowledgeListEditZuTiFragment.this.emptyView.setLoadFileResUi(KnowledgeListEditZuTiFragment.this.getContext());
                KnowledgeListEditZuTiFragment.this.emptyView.setVisibility(0);
                KnowledgeListEditZuTiFragment.this.mRecyclerView.setVisibility(8);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                List<QuestionBankNewBean.DataBean> data;
                super.onSuccess((AnonymousClass8) s2);
                KnowledgeListEditZuTiFragment.this.refreshLayout.finishRefresh(true);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (!"200".equals(jSONObject.optString("code"))) {
                        KnowledgeListEditZuTiFragment.this.AlertToast(jSONObject.optString("message"));
                        KnowledgeListEditZuTiFragment.this.emptyView.uploadEmptyViewResUi();
                        KnowledgeListEditZuTiFragment.this.emptyView.setVisibility(0);
                        KnowledgeListEditZuTiFragment.this.mRecyclerView.setVisibility(8);
                        return;
                    }
                    KnowledgeListEditZuTiFragment.this.dataList.clear();
                    QuestionBankNewBean questionBankNewBean = (QuestionBankNewBean) new Gson().fromJson(s2, QuestionBankNewBean.class);
                    if (questionBankNewBean.getData() == null || questionBankNewBean.getData().isEmpty() || (data = questionBankNewBean.getData()) == null || data.isEmpty()) {
                        return;
                    }
                    for (QuestionBankNewBean.DataBean dataBean : data) {
                        KnowledgeChartNodeBean knowledgeChartNodeBean = new KnowledgeChartNodeBean();
                        knowledgeChartNodeBean.setLevel(1);
                        knowledgeChartNodeBean.setHave(dataBean.have);
                        knowledgeChartNodeBean.setName(dataBean.title);
                        knowledgeChartNodeBean.setId(dataBean.getChapter_id());
                        knowledgeChartNodeBean.setActivity_id(dataBean.activity_id);
                        knowledgeChartNodeBean.setHas_permission(dataBean.pass);
                        KnowledgeListEditZuTiFragment.this.dataList.add(knowledgeChartNodeBean);
                        List<QuestionBankNewBean.DataBean.ChildrenBean> children = dataBean.getChildren();
                        if (children != null && !children.isEmpty()) {
                            List arrayList = (List) KnowledgeListEditZuTiFragment.this.mStringListMap.get(dataBean.getChapter_id());
                            List arrayList2 = (List) KnowledgeListEditZuTiFragment.this.mStringListTitleMap.get(dataBean.getChapter_id());
                            if (arrayList == null) {
                                arrayList = new ArrayList();
                                KnowledgeListEditZuTiFragment.this.mStringListMap.put(dataBean.getChapter_id(), arrayList);
                            }
                            if (arrayList2 == null) {
                                arrayList2 = new ArrayList();
                                KnowledgeListEditZuTiFragment.this.mStringListTitleMap.put(dataBean.getChapter_id(), arrayList2);
                            }
                            ArrayList arrayList3 = new ArrayList();
                            for (QuestionBankNewBean.DataBean.ChildrenBean childrenBean : children) {
                                arrayList.add(childrenBean.getChapter_id());
                                arrayList2.add(childrenBean.getTitle());
                                KnowledgeChartNodeBean knowledgeChartNodeBean2 = new KnowledgeChartNodeBean();
                                knowledgeChartNodeBean2.setLevel(2);
                                knowledgeChartNodeBean2.setName(childrenBean.title);
                                knowledgeChartNodeBean2.setId(childrenBean.chapter_id);
                                knowledgeChartNodeBean2.setParentId(knowledgeChartNodeBean.getId());
                                knowledgeChartNodeBean2.setHas_permission(knowledgeChartNodeBean.getHas_permission());
                                arrayList3.add(knowledgeChartNodeBean2);
                            }
                            knowledgeChartNodeBean.setChildren(arrayList3);
                        }
                    }
                    KnowledgeListEditZuTiFragment knowledgeListEditZuTiFragment = KnowledgeListEditZuTiFragment.this;
                    knowledgeListEditZuTiFragment.initListLayout(knowledgeListEditZuTiFragment.dataList);
                    KnowledgeListEditZuTiFragment.this.emptyView.setVisibility(8);
                    KnowledgeListEditZuTiFragment.this.mRecyclerView.setVisibility(0);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    KnowledgeListEditZuTiFragment.this.refreshLayout.finishRefresh(false);
                    KnowledgeListEditZuTiFragment.this.emptyView.setLoadFileResUi(KnowledgeListEditZuTiFragment.this.getContext());
                    KnowledgeListEditZuTiFragment.this.emptyView.setVisibility(0);
                    KnowledgeListEditZuTiFragment.this.mRecyclerView.setVisibility(8);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getSelectImg(TreeNode<KnowledgeChartNodeBean> treeNode) {
        if (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
            if (treeNode.getValue().isSelect()) {
                Log.d(TAG, "getSelectImg: ----获取图片---单课程--选中");
                return this.drawableIdAllSelect;
            }
            Log.d(TAG, "getSelectImg: ----获取图片---单课程--未选中");
            return this.drawableIdUnSelect;
        }
        if (KnowledgeTreeNodeUtilKt.allSelect(treeNode)) {
            Log.d(TAG, "getSelectImg: ----获取图片---全部选中");
            return this.drawableIdAllSelect;
        }
        if (KnowledgeTreeNodeUtilKt.noOneSelect(treeNode)) {
            Log.d(TAG, "getSelectImg: ----获取图片---未选中");
            return this.drawableIdUnSelect;
        }
        Log.d(TAG, "getSelectImg: ----获取图片---部分选中");
        return this.drawableIdPartSelect;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initListLayout(List<KnowledgeChartNodeBean> dateBeanList) {
        List<TreeNode<KnowledgeChartNodeBean>> listKnowledgeListToTreeNodeData = KnowledgeTreeNodeUtilKt.KnowledgeListToTreeNodeData(dateBeanList, this.preSelectIds);
        this.list = listKnowledgeListToTreeNodeData;
        this.allCount = getAllCountMethod(listKnowledgeListToTreeNodeData);
        TreeNodeAdapter<KnowledgeChartNodeBean> treeNodeAdapter = new TreeNodeAdapter<>(getActivity(), this.list);
        this.adapter = treeNodeAdapter;
        treeNodeAdapter.addItemViewDelegate(new AnonymousClass2());
        this.adapter.addItemViewDelegate(new AnonymousClass3());
        if (!this.baseQuestionBankRedo) {
            this.adapter.addItemViewDelegate(new AnonymousClass4());
        }
        this.mRecyclerView.setAdapter(this.adapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateSelectNoteCount(this.list);
    }

    private boolean isNightTheme() {
        return 1 == SkinManager.getCurrentSkinType(getContext());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(RefreshLayout refreshLayout) {
        getListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(View view) {
        getListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showRedoDialog$2(CustomDialog customDialog, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        customDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showRedoDialog$3(String str, String str2, CustomDialog customDialog, View view) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("is_wrong", str);
        ajaxParams.put("module_type", this.moduleType);
        ajaxParams.put("obj_id", str2);
        ajaxParams.put("identity_id", this.identityId);
        if (this.category.equals("unit")) {
            ajaxParams.put(UriUtil.QUERY_CATEGORY, "unit");
        } else {
            ajaxParams.put(UriUtil.QUERY_CATEGORY, "chapter");
        }
        if (!TextUtils.isEmpty(this.unitId)) {
            ajaxParams.put("unit_id", this.unitId);
        }
        if (this.type.equals("all")) {
            ajaxParams.put("type", "chapter");
        } else {
            ajaxParams.put("type", this.type);
        }
        QuestionDataRequest.getIntance(this.mContext).redoAnswer(ajaxParams, NetworkRequestsURL.questionRedo, this);
        customDialog.dismissNoAnimaltion();
        QuestionDetailBean questionDetailBean = new QuestionDetailBean();
        questionDetailBean.setModule_type(this.moduleType);
        questionDetailBean.setUnit_title(ProjectApp.unit_title);
        questionDetailBean.setUnit_id(ProjectApp.unit_id);
        questionDetailBean.setExam_title(ProjectApp.exam_title);
        questionDetailBean.setIdentity_title(ProjectApp.identity_title);
        questionDetailBean.setIdentity_id(ProjectApp.identity_id);
        questionDetailBean.setChapter_title(ProjectApp.chapter_title);
        questionDetailBean.setChapter_id(ProjectApp.chapter_id);
        questionDetailBean.setChapter_parent_title(ProjectApp.chapter_parent_title);
        questionDetailBean.setChapter_parent_id(ProjectApp.chapter_parent_id);
        String json = ProjectApp.gson.toJson(questionDetailBean);
        String str3 = "[\"" + q2.a("\",\"", this.chooseChapterId) + "\"]";
        String str4 = "[\"" + q2.a("\",\"", this.chooseChapterTitleList) + "\"]";
        AliyunEvent aliyunEvent = AliyunEvent.RedoAnswer_Chapter;
        CommonUtil.addLog(aliyunEvent.getKey(), aliyunEvent.getValue(), System.currentTimeMillis() + "", "", str3, str4, json, "2");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateSelectNoteCount$4() {
        this.selectNumChangeListener.selectNum(this.selectCount);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean level2IsAllSelect(TreeNode<KnowledgeChartNodeBean> treeNode) {
        if (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
            if (treeNode.getValue().isSelect()) {
                Log.d(TAG, "getSelectImg: ----获取图片---单课程--选中");
                return true;
            }
            Log.d(TAG, "getSelectImg: ----获取图片---单课程--未选中");
            return false;
        }
        if (KnowledgeTreeNodeUtilKt.allSelect(treeNode)) {
            Log.d(TAG, "getSelectImg: ----获取图片---全部选中");
            return true;
        }
        if (KnowledgeTreeNodeUtilKt.noOneSelect(treeNode)) {
            Log.d(TAG, "getSelectImg: ----获取图片---未选中");
            return false;
        }
        Log.d(TAG, "getSelectImg: ----获取图片---部分选中");
        return false;
    }

    private int selectNum(TreeNode<KnowledgeChartNodeBean> data) {
        if (!"1".equals(data.getValue().getHas_permission()) || !data.getValue().isSelect()) {
            return 0;
        }
        String id = data.getValue().getId();
        if (!TextUtils.isEmpty(id) && !this.selectIds.contains(id)) {
            this.selectIds.add(id);
        }
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void selectOperaAllDirectory(TreeNode<KnowledgeChartNodeBean> data, TreeNodeAdapter<KnowledgeChartNodeBean> adapter, Boolean selectAllOrUnSelectAll) {
        List<TreeNode<KnowledgeChartNodeBean>> children = data.getChildren();
        if (children != null && !children.isEmpty()) {
            for (int i2 = 0; i2 < children.size(); i2++) {
                TreeNode<KnowledgeChartNodeBean> treeNode = children.get(i2);
                treeNode.getValue().setSelect(selectAllOrUnSelectAll.booleanValue());
                adapter.notifyTreeNode(treeNode);
                List<TreeNode<KnowledgeChartNodeBean>> children2 = treeNode.getChildren();
                if (children2 != null && !children2.isEmpty()) {
                    for (int i3 = 0; i3 < children2.size(); i3++) {
                        TreeNode<KnowledgeChartNodeBean> treeNode2 = children2.get(i3);
                        treeNode2.getValue().setSelect(selectAllOrUnSelectAll.booleanValue());
                        adapter.notifyTreeNode(treeNode2);
                    }
                }
            }
        } else if (data.getCustomerLevel() == 2) {
            data.getValue().setSelect(selectAllOrUnSelectAll.booleanValue());
        }
        adapter.notifyTreeNode(data);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setArrowSpin(ViewHolder helper, TreeNode<KnowledgeChartNodeBean> data, boolean isAnimate) {
        final ImageView imageView = (ImageView) helper.getView(R.id.imgArrow);
        if (data.isExpand()) {
            Log.d(TAG, "setArrowSpin: --- 展开 ");
            if (isAnimate) {
                imageView.animate().rotation(180.0f).setDuration(300L).setInterpolator(new LinearInterpolator()).start();
                return;
            } else {
                imageView.setImageResource(R.drawable.icon_top_arrow_main_theme_color);
                return;
            }
        }
        Log.d(TAG, "setArrowSpin: --- 关闭 ");
        if (isAnimate) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(imageView, Key.ROTATION, 0.0f, 180.0f);
            objectAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditZuTiFragment.9
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animation) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    imageView.setRotation(0.0f);
                    if (SkinManager.getCurrentSkinType(KnowledgeListEditZuTiFragment.this.getActivity()) == 1) {
                        imageView.setImageResource(R.drawable.icon_bottom_arrow_night);
                    } else {
                        imageView.setImageResource(R.drawable.icon_bottom_arrow_day);
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
            imageView.setImageResource(R.drawable.icon_bottom_arrow_night);
        } else {
            imageView.setImageResource(R.drawable.icon_bottom_arrow_day);
        }
    }

    private void showRedoDialog(final String strChapterId, final String isWrong, String message) {
        final CustomDialog customDialog = new CustomDialog(this.mContext, 2);
        customDialog.setCancelable(false);
        customDialog.setMessage(message);
        customDialog.setNegativeBtn(R.string.cancel, new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.n
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KnowledgeListEditZuTiFragment.lambda$showRedoDialog$2(customDialog, view);
            }
        });
        customDialog.setPositiveBtn("确定", new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.o
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15767c.lambda$showRedoDialog$3(isWrong, strChapterId, customDialog, view);
            }
        });
        customDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSelectNoteCount(List<TreeNode<KnowledgeChartNodeBean>> list) {
        this.selectIds.clear();
        int iSelectNum = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            TreeNode<KnowledgeChartNodeBean> treeNode = list.get(i2);
            if (treeNode.getCustomerLevel() == 0) {
                if (!this.baseQuestionBankRedo) {
                    List<TreeNode<KnowledgeChartNodeBean>> children = treeNode.getChildren();
                    if (children != null && !children.isEmpty()) {
                        for (int i3 = 0; i3 < children.size(); i3++) {
                            TreeNode<KnowledgeChartNodeBean> treeNode2 = children.get(i3);
                            if (treeNode2.getCustomerLevel() == 2) {
                                iSelectNum += selectNum(treeNode2);
                            }
                            if (treeNode2.getChildren() != null && !treeNode2.getChildren().isEmpty()) {
                                List<TreeNode<KnowledgeChartNodeBean>> children2 = treeNode2.getChildren();
                                for (int i4 = 0; i4 < children2.size(); i4++) {
                                    TreeNode<KnowledgeChartNodeBean> treeNode3 = children2.get(i4);
                                    if (treeNode3.getCustomerLevel() == 2) {
                                        iSelectNum += selectNum(treeNode3);
                                    }
                                }
                            }
                        }
                    }
                } else if (treeNode.isSelect()) {
                    iSelectNum++;
                }
            }
        }
        this.selectCount = iSelectNum;
        if (this.selectNumChangeListener != null) {
            new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.knowledge.p
                @Override // java.lang.Runnable
                public final void run() {
                    this.f15771c.lambda$updateSelectNoteCount$4();
                }
            }, 500L);
        }
        Log.e(TAG, "updateSelectNoteCount: " + q2.a(",", this.selectIds));
    }

    public void btnCommit() {
        if (getActivity() == null || this.list == null) {
            return;
        }
        Intent intent = new Intent();
        if (this.selectIds.isEmpty()) {
            intent.putExtra("chooseChapterId", "");
        } else {
            intent.putExtra("chooseChapterId", new Gson().toJson(this.selectIds));
        }
        intent.putExtra("chapterCount", this.selectIds.size());
        intent.putExtra("selectNodeCount", String.valueOf(this.selectIds.size()));
        intent.putExtra("isChooseAll", this.selectIds.size() == this.allCount);
        if (!TextUtils.isEmpty(this.type) && !this.baseQuestionBankRedo) {
            EventBus.getDefault().post(new RedoMultiKnowledgeEvent(q2.a(",", this.selectIds)));
        }
        if (this.studyPlanSelect) {
            KnowledgeListEditZuTiActivity knowledgeListEditZuTiActivity = (KnowledgeListEditZuTiActivity) getActivity();
            if (knowledgeListEditZuTiActivity != null) {
                Triple<String, String, String> tripleConvertData = knowledgeListEditZuTiActivity.convertData(this.dataList);
                ArrayList arrayList = new ArrayList();
                for (KnowledgeChartNodeBean knowledgeChartNodeBean : this.dataList) {
                    if (knowledgeChartNodeBean.getLevel() == 3) {
                        arrayList.add(knowledgeChartNodeBean.getId());
                    }
                }
                if (!TextUtils.isEmpty(tripleConvertData.getFirst())) {
                    intent.putExtra("part_ids", tripleConvertData.getFirst());
                }
                if (!TextUtils.isEmpty(tripleConvertData.getSecond())) {
                    intent.putExtra("chapter_ids", tripleConvertData.getSecond());
                }
                if (!TextUtils.isEmpty(tripleConvertData.getThird())) {
                    intent.putExtra("node_ids", tripleConvertData.getThird());
                }
                ArrayList arrayList2 = new ArrayList();
                for (KnowledgeChartNodeBean knowledgeChartNodeBean2 : this.dataList) {
                    if (knowledgeChartNodeBean2.getLevel() == 3 && knowledgeChartNodeBean2.isSelect()) {
                        arrayList2.add(knowledgeChartNodeBean2.getId());
                    }
                }
                if (!this.selectIds.isEmpty()) {
                    intent.putExtra("selectChildIdList", q2.a(",", this.selectIds));
                }
            }
        } else if (this.baseQuestionBankRedo) {
            ArrayList arrayList3 = new ArrayList();
            this.chooseChapterId.clear();
            this.chooseChapterTitleList.clear();
            this.isChoosedCurrentChapter = false;
            for (TreeNode<KnowledgeChartNodeBean> treeNode : this.list) {
                KnowledgeChartNodeBean value = treeNode.getValue();
                if (value.getLevel() == 1 && treeNode.isSelect()) {
                    if ("0".equals(value.getHave())) {
                        arrayList3.add(value.getId());
                        this.chooseChapterId.add(value.getId());
                        this.chooseChapterTitleList.add(value.getName());
                    } else {
                        List<String> list = this.mStringListMap.get(value.getId());
                        if (list != null && !list.isEmpty()) {
                            arrayList3.addAll(list);
                            this.chooseChapterId.addAll(list);
                        }
                        List<String> list2 = this.mStringListTitleMap.get(value.getId());
                        if (list2 != null && !list2.isEmpty()) {
                            this.chooseChapterTitleList.addAll(list2);
                        }
                    }
                }
            }
            String strA = q2.a(",", arrayList3);
            this.isChoosedCurrentChapter = arrayList3.contains(this.chapterId);
            LogUtils.d("selectIds", strA);
            if (!strA.isEmpty()) {
                showRedoDialog(strA, "0", "是否重做所选章节所有试题？");
            }
        }
        if (this.baseQuestionBankRedo) {
            return;
        }
        getActivity().setResult(-1, intent);
        getActivity().finish();
    }

    public Bundle getBundle(String treeId, String selectIds) {
        Bundle bundle = new Bundle();
        bundle.putString("treeId", treeId);
        bundle.putSerializable("ListData", selectIds);
        return bundle;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_knowledge_list_edit_zu_ti;
    }

    public int getNoteAllCount() {
        return this.allCount;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(com.psychiatrygarden.baseview.ViewHolder holder, View root) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.treeId = arguments.getString("treeId");
            this.type = arguments.getString("type");
            String string = arguments.getString("ListData");
            this.studyPlanSelect = arguments.getBoolean("studyPlanSelect", false);
            this.baseQuestionBankRedo = arguments.getBoolean("baseQuestionBankRedo", false);
            if (!TextUtils.isEmpty(string)) {
                List list = (List) new Gson().fromJson(string, new TypeToken<List<String>>() { // from class: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditZuTiFragment.1
                }.getType());
                this.preSelectIds.clear();
                this.preSelectIds.addAll(list);
            }
            if (this.baseQuestionBankRedo) {
                this.identityId = arguments.getString("identityId");
                this.moduleType = arguments.getString("moduleType");
                this.category = arguments.getString(UriUtil.QUERY_CATEGORY);
                this.unitId = arguments.getString("unitId");
                this.chapterId = arguments.getString("chapterId");
            }
        }
        this.mRecyclerView = (RecyclerView) holder.get(R.id.recyclerView);
        this.refreshLayout = (SmartRefreshLayout) holder.get(R.id.refresh);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.refreshLayout.setEnableLoadMore(false);
        this.refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.l
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f15764c.lambda$initViews$0(refreshLayout);
            }
        });
        this.drawableIdAllSelect = isNightTheme() ? R.drawable.download_new_select_night : R.drawable.download_new_select_day;
        this.drawableIdUnSelect = isNightTheme() ? R.drawable.download_new_not_select_night : R.drawable.download_new_not_select;
        this.drawableIdPartSelect = isNightTheme() ? R.drawable.download_part_select_night : R.drawable.download_part_select;
        int i2 = SkinManager.getCurrentSkinType(getContext()) == 1 ? R.drawable.ic_empty_data_note_night : R.drawable.ic_empty_data_note_day;
        CustomEmptyView customEmptyView = (CustomEmptyView) holder.get(R.id.emptyView);
        this.emptyView = customEmptyView;
        customEmptyView.setImgEmptyRes(i2);
        this.emptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.m
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15765c.lambda$initViews$1(view);
            }
        });
        this.emptyView.changeEmptyViewWriteBg();
        getListData();
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onFailure(Throwable t2, int errorNo, String strMsg, String requestUrl) {
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onStart(String requestUrl) {
        if (requestUrl.equals(NetworkRequestsURL.questionRedo)) {
            showProgressDialog();
        }
    }

    public void selectOperaAll(Boolean selectAllOrUnSelectAll) {
        for (int i2 = 0; i2 < this.list.size(); i2++) {
            if (this.baseQuestionBankRedo) {
                this.list.get(i2).setSelect(selectAllOrUnSelectAll.booleanValue());
            }
            selectOperaAllDirectory(this.list.get(i2), this.adapter, selectAllOrUnSelectAll);
            selectAllOrUnSelectAll.booleanValue();
        }
        if (!selectAllOrUnSelectAll.booleanValue()) {
            this.selectIds.clear();
        }
        updateSelectNoteCount(this.list);
    }

    public void setSelectNumChangeListener(SelectNumChangeListener selectNumChangeListener) {
        this.selectNumChangeListener = selectNumChangeListener;
    }

    @Override // com.psychiatrygarden.interfaceclass.QuestionDataCallBack
    public void onSuccess(String s2, String requestUrl) {
        showProgressDialog();
        if (requestUrl.equals(NetworkRequestsURL.questionRedo)) {
            if (this.isChoosedCurrentChapter) {
                EventBus.getDefault().post(new RedoOtherQuestionEvent("all", false));
            }
            hideProgressDialog();
            getActivity().finish();
        }
    }
}
