package com.psychiatrygarden.fragmenthome.knowledge;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
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
import com.easefun.polyv.livecommon.ui.widget.expandmenu.utils.DpOrPxUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.activity.q2;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.bean.CourseDirectoryTreeBean;
import com.psychiatrygarden.bean.KnowledgeChartNodeBean;
import com.psychiatrygarden.bean.KnowledgeListType;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.RefreshCutQuestionEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.AliyunEvent;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.KnowledgeTreeNodeUtilKt;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.psychiatrygarden.widget.treenode.TreeNode;
import com.psychiatrygarden.widget.treenode.TreeNodeAdapter;
import com.psychiatrygarden.widget.treenode.TreeNodeDelegate;
import com.psychiatrygarden.widget.treenode.ViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class KnowledgeListEditFragment extends BaseFragment {
    private static final String TAG = "KnowledgeListEditFragment";
    private TreeNodeAdapter<KnowledgeChartNodeBean> adapter;
    private String course_id;
    private String domain_type;
    private int drawableIdAllSelect;
    private int drawableIdPartSelect;
    private int drawableIdUnSelect;
    private CustomEmptyView emptyView;
    private String filePath;
    private TreeNode<KnowledgeChartNodeBean> firstExpandTree;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout refreshLayout;
    private TreeNode<KnowledgeChartNodeBean> secondExpandTree;
    private SelectNumChangeListener selectNumChangeListener;
    private String treeId;
    private List<TreeNode<KnowledgeChartNodeBean>> list = new ArrayList();
    private HashMap<String, Integer> noteCountMap = new HashMap<>();
    private HashMap<String, List<String>> newChapterParams = new HashMap<>();
    private List<String> selectIds = new ArrayList();
    private int selectCount = 0;
    private int allCount = 0;

    /* renamed from: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditFragment$1, reason: invalid class name */
    public class AnonymousClass1 extends TreeNodeDelegate<KnowledgeChartNodeBean> {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(TreeNode treeNode, View view) {
            if (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
                KnowledgeListEditFragment.this.toastOnUiThread("没有子课程");
                return;
            }
            KnowledgeListEditFragment.this.selectOperaAllDirectory(treeNode, this.adapter, Boolean.valueOf(!KnowledgeTreeNodeUtilKt.allSelect(treeNode)));
            KnowledgeListEditFragment knowledgeListEditFragment = KnowledgeListEditFragment.this;
            knowledgeListEditFragment.updateSelectNoteCount(knowledgeListEditFragment.list);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$1(TreeNode treeNode, View view) {
            if (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
                KnowledgeListEditFragment.this.toastOnUiThread("该目录下没有笔记");
            } else {
                this.adapter.expandOrCollapseTreeNode(treeNode);
            }
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public void convert(ViewHolder holder, final TreeNode<KnowledgeChartNodeBean> treeNode) {
            ImageView imageView = (ImageView) holder.getView(R.id.firstSelectImage);
            ImageView imageView2 = (ImageView) holder.getView(R.id.imgArrow);
            TextView textView = (TextView) holder.getView(R.id.firstTitle);
            TextView textView2 = (TextView) holder.getView(R.id.tvFirstCount);
            KnowledgeChartNodeBean value = treeNode.getValue();
            textView.setText(value.getName());
            if (KnowledgeListEditFragment.this.noteCountMap.get(value.getId()) != null) {
                textView2.setText(KnowledgeListEditFragment.this.getCountStr("" + KnowledgeListEditFragment.this.noteCountMap.get(value.getId())));
            }
            imageView.setImageDrawable(KnowledgeListEditFragment.this.getResources().getDrawable(KnowledgeListEditFragment.this.getSelectImg(treeNode)));
            imageView.setVisibility(0);
            Log.d(KnowledgeListEditFragment.TAG, "convert: 刷新一级目录： " + treeNode.getValue().getName());
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.f
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f15742c.lambda$convert$0(treeNode, view);
                }
            });
            holder.getConvertView().setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.g
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f15745c.lambda$convert$1(treeNode, view);
                }
            });
            imageView2.setVisibility(0);
            if (!treeNode.isExpand()) {
                KnowledgeListEditFragment.this.setArrowSpin(holder, treeNode, false);
            } else {
                KnowledgeListEditFragment.this.setArrowSpin(holder, treeNode, false);
                KnowledgeListEditFragment.this.firstExpandTree = treeNode;
            }
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public int getLayoutId() {
            return R.layout.item_knowledge_first;
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public boolean isItemType(TreeNode<KnowledgeChartNodeBean> treeNode) {
            return treeNode.getCustomerLevel() == 0;
        }
    }

    /* renamed from: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditFragment$2, reason: invalid class name */
    public class AnonymousClass2 extends TreeNodeDelegate<KnowledgeChartNodeBean> {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(TreeNode treeNode, View view) {
            if (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
                KnowledgeListEditFragment.this.toastOnUiThread("该目录下没有笔记");
            } else {
                this.adapter.expandOrCollapseTreeNode(treeNode);
            }
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public void convert(ViewHolder holder, final TreeNode<KnowledgeChartNodeBean> treeNode) {
            ImageView imageView = (ImageView) holder.getView(R.id.imgArrow);
            TextView textView = (TextView) holder.getView(R.id.secondTitle);
            ImageView imageView2 = (ImageView) holder.getView(R.id.secondSelectImage);
            TextView textView2 = (TextView) holder.getView(R.id.tvSecondCount);
            KnowledgeChartNodeBean value = treeNode.getValue();
            textView.setText(value.getName());
            if (KnowledgeListEditFragment.this.noteCountMap.get(value.getId()) != null) {
                textView2.setText(KnowledgeListEditFragment.this.getCountStr("" + KnowledgeListEditFragment.this.noteCountMap.get(value.getId())));
            }
            imageView2.setImageDrawable(KnowledgeListEditFragment.this.getResources().getDrawable(KnowledgeListEditFragment.this.getSelectImg(treeNode)));
            imageView2.setVisibility(0);
            imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditFragment.2.1
                @Override // android.view.View.OnClickListener
                public void onClick(View v2) {
                    if (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
                        KnowledgeListEditFragment.this.toastOnUiThread("没有子课程");
                        return;
                    }
                    boolean zAllSelect = KnowledgeTreeNodeUtilKt.allSelect(treeNode);
                    AnonymousClass2 anonymousClass2 = AnonymousClass2.this;
                    KnowledgeListEditFragment.this.selectOperaAllDirectory(treeNode, anonymousClass2.adapter, Boolean.valueOf(!zAllSelect));
                    KnowledgeListEditFragment knowledgeListEditFragment = KnowledgeListEditFragment.this;
                    knowledgeListEditFragment.updateSelectNoteCount(knowledgeListEditFragment.list);
                    if (treeNode.getParent() != null) {
                        AnonymousClass2.this.adapter.notifyTreeNode(treeNode.getParent());
                    }
                }
            });
            imageView.setVisibility(0);
            holder.getConvertView().setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.h
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f15749c.lambda$convert$0(treeNode, view);
                }
            });
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public int getLayoutId() {
            return R.layout.item_knowledge_second;
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public boolean isItemType(TreeNode<KnowledgeChartNodeBean> treeNode) {
            return treeNode.getCustomerLevel() == 1;
        }
    }

    /* renamed from: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditFragment$3, reason: invalid class name */
    public class AnonymousClass3 extends TreeNodeDelegate<KnowledgeChartNodeBean> {
        public AnonymousClass3() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(TreeNode treeNode, View view) {
            ((KnowledgeChartNodeBean) treeNode.getValue()).setSelect(!((KnowledgeChartNodeBean) treeNode.getValue()).isSelect());
            this.adapter.notifyTreeNode(treeNode);
            if (treeNode.getParent() != null) {
                this.adapter.notifyTreeNode(treeNode.getParent());
            }
            if (treeNode.getParent() != null && treeNode.getParent().getParent() != null) {
                this.adapter.notifyTreeNode(treeNode.getParent().getParent());
            }
            KnowledgeListEditFragment knowledgeListEditFragment = KnowledgeListEditFragment.this;
            knowledgeListEditFragment.updateSelectNoteCount(knowledgeListEditFragment.list);
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public void convert(ViewHolder holder, final TreeNode<KnowledgeChartNodeBean> treeNode) {
            int absoluteAdapterPosition = holder.getAbsoluteAdapterPosition();
            int iDip2px = DpOrPxUtils.dip2px(((BaseFragment) KnowledgeListEditFragment.this).mContext, 12.0f);
            Log.d(KnowledgeListEditFragment.TAG, "convert: 第三级数据： pos:" + absoluteAdapterPosition + "--- value: " + treeNode.getValue().getName());
            RelativeLayout relativeLayout = (RelativeLayout) holder.getView(R.id.itemInnerLayout);
            ImageView imageView = (ImageView) holder.getView(R.id.thirdSelectImage);
            View view = holder.getView(R.id.thirdItemLine);
            TextView textView = (TextView) holder.getView(R.id.tvThirdNum);
            TextView textView2 = (TextView) holder.getView(R.id.thirdTitle);
            KnowledgeChartNodeBean value = treeNode.getValue();
            textView2.setText(value.getName());
            textView.setText(KnowledgeListEditFragment.this.getCountStr("" + value.getQuestion_count()));
            relativeLayout.setPadding(iDip2px, 0, iDip2px, 0);
            if (KnowledgeTreeNodeUtilKt.isOnlyOneItem(treeNode)) {
                view.setVisibility(4);
                relativeLayout.setBackground(KnowledgeListEditFragment.this.getResources().getDrawable(R.drawable.shape_new_bg_two_color_corner12, ((BaseFragment) KnowledgeListEditFragment.this).mContext.getTheme()));
            } else if (KnowledgeTreeNodeUtilKt.isTopRadiusItem(treeNode)) {
                view.setVisibility(0);
                relativeLayout.setBackground(KnowledgeListEditFragment.this.getResources().getDrawable(R.drawable.shape_new_bg_two_color_top_corner12, ((BaseFragment) KnowledgeListEditFragment.this).mContext.getTheme()));
            } else if (KnowledgeTreeNodeUtilKt.isBottomRadiusItem(treeNode)) {
                view.setVisibility(4);
                relativeLayout.setBackground(KnowledgeListEditFragment.this.getResources().getDrawable(R.drawable.shape_new_bg_two_color_bottom_corner12, ((BaseFragment) KnowledgeListEditFragment.this).mContext.getTheme()));
            } else {
                view.setVisibility(0);
                relativeLayout.setBackground(KnowledgeListEditFragment.this.getResources().getDrawable(R.drawable.shape_project_normal_bg, ((BaseFragment) KnowledgeListEditFragment.this).mContext.getTheme()));
            }
            imageView.setImageDrawable(KnowledgeListEditFragment.this.getResources().getDrawable(KnowledgeListEditFragment.this.getSelectImg(treeNode)));
            imageView.setVisibility(0);
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.i
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f15753c.lambda$convert$0(treeNode, view2);
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

    /* renamed from: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditFragment$8, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass8 {
        static final /* synthetic */ int[] $SwitchMap$com$psychiatrygarden$bean$KnowledgeListType;

        static {
            int[] iArr = new int[KnowledgeListType.values().length];
            $SwitchMap$com$psychiatrygarden$bean$KnowledgeListType = iArr;
            try {
                iArr[KnowledgeListType.ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$psychiatrygarden$bean$KnowledgeListType[KnowledgeListType.NOTE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$psychiatrygarden$bean$KnowledgeListType[KnowledgeListType.CUT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$psychiatrygarden$bean$KnowledgeListType[KnowledgeListType.ALL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$psychiatrygarden$bean$KnowledgeListType[KnowledgeListType.PRAISE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$psychiatrygarden$bean$KnowledgeListType[KnowledgeListType.COMMENT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$psychiatrygarden$bean$KnowledgeListType[KnowledgeListType.COLLECTION.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public interface SelectNumChangeListener {
        void selectNum(int num);
    }

    private boolean childIsVideoItem(TreeNode<CourseDirectoryTreeBean> treeNode) {
        return (treeNode == null || treeNode.getCustomerLevel() != 0 || treeNode.getChildren() == null || treeNode.getChildren().isEmpty() || treeNode.getChildren().get(0).getCustomerLevel() != 2) ? false : true;
    }

    private int getAllCountMethod(List<TreeNode<KnowledgeChartNodeBean>> list, HashMap<String, Integer> noteCountMap) {
        int i2;
        int i3;
        int i4 = 0;
        for (int i5 = 0; i5 < list.size(); i5++) {
            TreeNode<KnowledgeChartNodeBean> treeNode = list.get(i5);
            String id = treeNode.getValue().getId();
            List<TreeNode<KnowledgeChartNodeBean>> children = treeNode.getChildren();
            if (children == null || children.isEmpty()) {
                i2 = 0;
            } else {
                i2 = 0;
                for (int i6 = 0; i6 < children.size(); i6++) {
                    TreeNode<KnowledgeChartNodeBean> treeNode2 = children.get(i6);
                    String id2 = treeNode2.getValue().getId();
                    if (treeNode2.getChildren() == null || treeNode2.getChildren().isEmpty()) {
                        i3 = 0;
                    } else {
                        List<TreeNode<KnowledgeChartNodeBean>> children2 = treeNode2.getChildren();
                        i3 = 0;
                        for (int i7 = 0; i7 < children2.size(); i7++) {
                            String question_count = children2.get(i7).getValue().getQuestion_count();
                            if (!TextUtils.isEmpty(question_count)) {
                                i2 += Integer.parseInt(question_count);
                                i3 += Integer.parseInt(question_count);
                                i4 += Integer.parseInt(question_count);
                            }
                        }
                    }
                    noteCountMap.put(id2, Integer.valueOf(i3));
                }
            }
            noteCountMap.put(id, Integer.valueOf(i2));
        }
        return i4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getCountStr(String count) {
        switch (AnonymousClass8.$SwitchMap$com$psychiatrygarden$bean$KnowledgeListType[KnowledgeListType.INSTANCE.getKnowledgeListType(this.domain_type).ordinal()]) {
            case 1:
                return String.format("错%s题", count);
            case 2:
                return String.format("%s条笔记", count);
            case 3:
            case 4:
                return String.format("%s题", count);
            case 5:
                return String.format("%s条点赞", count);
            case 6:
                return String.format("%s条评论", count);
            case 7:
                return String.format("收藏%s题", count);
            default:
                return "";
        }
    }

    private void getListData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(KnowledgeQuestionListFragment.EXTRA_TREE_ID, this.treeId);
        ajaxParams.put(KnowledgeQuestionListFragment.EXTRA_DOMAIN_TYPE, this.domain_type);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.knowledgeList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditFragment.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                KnowledgeListEditFragment.this.emptyView.setLoadFileResUi(KnowledgeListEditFragment.this.getContext());
                KnowledgeListEditFragment.this.emptyView.setVisibility(0);
                KnowledgeListEditFragment.this.mRecyclerView.setVisibility(8);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass4) t2);
                try {
                    KnowledgeListEditFragment.this.refreshLayout.finishRefresh(true);
                    JSONObject jSONObject = new JSONObject(t2);
                    if (jSONObject.optString("code").equals("200")) {
                        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                        if (jSONObjectOptJSONObject == null) {
                            return;
                        }
                        KnowledgeListEditFragment.this.initListLayout((List) new Gson().fromJson(jSONObjectOptJSONObject.optString("chapter_list"), new TypeToken<List<KnowledgeChartNodeBean>>() { // from class: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditFragment.4.1
                        }.getType()));
                        KnowledgeListEditFragment.this.emptyView.setVisibility(8);
                        KnowledgeListEditFragment.this.mRecyclerView.setVisibility(0);
                    } else {
                        KnowledgeListEditFragment.this.emptyView.uploadEmptyViewResUi();
                        KnowledgeListEditFragment.this.emptyView.setVisibility(0);
                        KnowledgeListEditFragment.this.mRecyclerView.setVisibility(8);
                    }
                } catch (Exception e2) {
                    Log.e(KnowledgeListEditFragment.TAG, "onSuccess: 请求目录列表异常:" + e2.getMessage());
                    KnowledgeListEditFragment.this.emptyView.setLoadFileResUi(KnowledgeListEditFragment.this.getContext());
                    KnowledgeListEditFragment.this.emptyView.setVisibility(0);
                    KnowledgeListEditFragment.this.mRecyclerView.setVisibility(8);
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
        List<TreeNode<KnowledgeChartNodeBean>> listKnowledgeListToTreeNodeData = KnowledgeTreeNodeUtilKt.KnowledgeListToTreeNodeData(dateBeanList);
        this.list = listKnowledgeListToTreeNodeData;
        this.allCount = getAllCountMethod(listKnowledgeListToTreeNodeData, this.noteCountMap);
        TreeNodeAdapter<KnowledgeChartNodeBean> treeNodeAdapter = new TreeNodeAdapter<>(getActivity(), this.list);
        this.adapter = treeNodeAdapter;
        treeNodeAdapter.addItemViewDelegate(new AnonymousClass1());
        this.adapter.addItemViewDelegate(new AnonymousClass2());
        this.adapter.addItemViewDelegate(new AnonymousClass3());
        this.mRecyclerView.setAdapter(this.adapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
    public /* synthetic */ void lambda$updateSelectNoteCount$2() {
        this.selectNumChangeListener.selectNum(this.selectCount);
    }

    private int selectNum(TreeNode<KnowledgeChartNodeBean> data) throws NumberFormatException {
        int i2 = 0;
        if (data.getValue().isSelect()) {
            KnowledgeChartNodeBean value = data.getValue();
            String question_count = value.getQuestion_count();
            if (!TextUtils.isEmpty(question_count)) {
                int i3 = Integer.parseInt(question_count);
                String id = value.getId();
                if (!TextUtils.isEmpty(id) && !this.selectIds.contains(id)) {
                    this.selectIds.add(id);
                }
                i2 = i3;
            }
            Log.d(TAG, "selectNum: -------" + i2);
        }
        return i2;
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
            objectAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditFragment.7
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animation) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    imageView.setRotation(0.0f);
                    if (SkinManager.getCurrentSkinType(KnowledgeListEditFragment.this.getActivity()) == 1) {
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

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSelectNoteCount(List<TreeNode<KnowledgeChartNodeBean>> list) {
        List<TreeNode<KnowledgeChartNodeBean>> children;
        this.selectIds.clear();
        int iSelectNum = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            TreeNode<KnowledgeChartNodeBean> treeNode = list.get(i2);
            if (treeNode.getCustomerLevel() == 0 && (children = treeNode.getChildren()) != null && !children.isEmpty()) {
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
        }
        this.selectCount = iSelectNum;
        if (this.selectNumChangeListener != null) {
            new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.knowledge.e
                @Override // java.lang.Runnable
                public final void run() {
                    this.f15740c.lambda$updateSelectNoteCount$2();
                }
            }, 500L);
        }
        Log.e(TAG, "updateSelectNoteCount: " + q2.a(",", this.selectIds));
    }

    public Bundle getBundle(String treeId, String domain_type) {
        Bundle bundle = new Bundle();
        bundle.putString(KnowledgeQuestionListFragment.EXTRA_TREE_ID, treeId);
        bundle.putString(KnowledgeQuestionListFragment.EXTRA_DOMAIN_TYPE, domain_type);
        return bundle;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_knowledge_list_edit;
    }

    public int getNoteAllCount() {
        return this.allCount;
    }

    public void getNoteDownLoad() {
        String str;
        File file = new File(this.filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        CommonUtil.deleteFile(new File(this.filePath));
        AjaxParams ajaxParams = new AjaxParams();
        if (this.domain_type.equals(KnowledgeListType.ERROR.getType())) {
            str = NetworkRequestsURL.wcApi;
            ajaxParams.put("type", "wrong");
        } else {
            String str2 = this.domain_type;
            KnowledgeListType knowledgeListType = KnowledgeListType.COLLECTION;
            if (str2.equals(knowledgeListType.getType())) {
                str = NetworkRequestsURL.wcApi;
                ajaxParams.put("type", knowledgeListType.getType());
            } else {
                str = NetworkRequestsURL.noteFileApi;
            }
        }
        ajaxParams.put("knowledge_id", q2.a(",", this.selectIds));
        ajaxParams.put("version", "2");
        ajaxParams.put("part_id", this.treeId);
        ajaxParams.put("am_pm", SharePreferencesUtils.readStrConfig(CommonParameter.am_pm, getActivity(), "0"));
        if (!SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext).equals("40")) {
            ajaxParams.put("school_year", "");
        }
        ajaxParams.put("uid", UserConfig.getUserId());
        ajaxParams.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getActivity(), "1"));
        LogUtils.d("export__", ajaxParams.getParamString() + ",url=" + str);
        YJYHttpUtils.getNoteFile(getActivity(), str, this.filePath, ajaxParams, new AjaxCallBack<File>() { // from class: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditFragment.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                KnowledgeListEditFragment.this.hideProgressDialog();
                if (errorNo == 0) {
                    KnowledgeListEditFragment.this.AlertToast("无法导出,请检查app存储权限是否打开！");
                    return;
                }
                if (errorNo == 200) {
                    KnowledgeListEditFragment.this.AlertToast(strMsg);
                    return;
                }
                KnowledgeListEditFragment.this.AlertToast(strMsg + "");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onLoading(long count, long current) {
                super.onLoading(count, current);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                KnowledgeListEditFragment.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(File file2) {
                super.onSuccess((AnonymousClass5) file2);
                KnowledgeListEditFragment.this.hideProgressDialog();
                CommonUtil.showDialog(KnowledgeListEditFragment.this.getActivity(), file2.getAbsolutePath());
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(com.psychiatrygarden.baseview.ViewHolder holder, View root) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.treeId = arguments.getString(KnowledgeQuestionListFragment.EXTRA_TREE_ID);
            this.domain_type = arguments.getString(KnowledgeQuestionListFragment.EXTRA_DOMAIN_TYPE);
        }
        this.mRecyclerView = (RecyclerView) holder.get(R.id.recyclerView);
        this.refreshLayout = (SmartRefreshLayout) holder.get(R.id.refresh);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.refreshLayout.setEnableLoadMore(false);
        this.refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.c
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f15734c.lambda$initViews$0(refreshLayout);
            }
        });
        this.drawableIdAllSelect = isNightTheme() ? R.drawable.download_new_select_night : R.drawable.download_new_select_day;
        this.drawableIdUnSelect = isNightTheme() ? R.drawable.download_new_not_select_night : R.drawable.download_new_not_select;
        this.drawableIdPartSelect = isNightTheme() ? R.drawable.download_part_select_night : R.drawable.download_part_select;
        int i2 = SkinManager.getCurrentSkinType(getContext()) == 1 ? R.drawable.ic_empty_data_note_night : R.drawable.ic_empty_data_note_day;
        CustomEmptyView customEmptyView = (CustomEmptyView) holder.get(R.id.emptyView);
        this.emptyView = customEmptyView;
        customEmptyView.setImgEmptyRes(i2);
        this.emptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.d
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15737c.lambda$initViews$1(view);
            }
        });
        this.emptyView.changeEmptyViewWriteBg();
        this.emptyView.setVisibility(0);
        getListData();
        if (Build.VERSION.SDK_INT >= 29) {
            this.filePath = getActivity().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getPath();
        } else {
            this.filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath();
        }
    }

    public void moveZhanTi() {
        if (this.selectIds.isEmpty()) {
            return;
        }
        Iterator<String> it = this.selectIds.iterator();
        String str = "";
        while (it.hasNext()) {
            str = str + it.next() + ",";
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("identity_id", this.treeId);
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("module_type", "110");
        ajaxParams.put("chapter_id", str.substring(0, str.length() - 1));
        YJYHttpUtils.post(getActivity(), NetworkRequestsURL.moveToQuestion, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditFragment.6
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass6) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code", "").equals("200")) {
                        EventBus.getDefault().post(new RefreshCutQuestionEvent(KnowledgeListEditFragment.this.treeId));
                        jSONObject.optJSONObject("data");
                        EventBus.getDefault().post(EventBusConstant.EVENT_ZHAN_TI_MOVE_UPDATE);
                        KnowledgeListEditActivity knowledgeListEditActivity = (KnowledgeListEditActivity) KnowledgeListEditFragment.this.getActivity();
                        if (knowledgeListEditActivity != null) {
                            knowledgeListEditActivity.finish();
                        }
                    } else {
                        String strOptString = jSONObject.optString("message", "");
                        if (!TextUtils.isEmpty(strOptString)) {
                            KnowledgeListEditFragment.this.AlertToast(strOptString);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
        AliyunEvent aliyunEvent = AliyunEvent.CutQuestionMoveBack;
        CommonUtil.addLog(aliyunEvent.getKey(), aliyunEvent.getValue(), System.currentTimeMillis() + "", "", "", "", "", "2");
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void selectOperaAll(Boolean selectAllOrUnSelectAll) {
        for (int i2 = 0; i2 < this.list.size(); i2++) {
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
}
