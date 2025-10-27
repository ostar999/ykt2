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
import com.alibaba.fastjson.JSON;
import com.easefun.polyv.livecommon.ui.widget.expandmenu.utils.DpOrPxUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryExportNoteFragment;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.bean.CourseDirectoryContentItem;
import com.psychiatrygarden.bean.CourseDirectoryItemData;
import com.psychiatrygarden.bean.CourseDirectoryTreeBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.TreeNodeUtilKt;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.psychiatrygarden.widget.treenode.TreeNode;
import com.psychiatrygarden.widget.treenode.TreeNodeAdapter;
import com.psychiatrygarden.widget.treenode.TreeNodeDelegate;
import com.psychiatrygarden.widget.treenode.ViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class KnowledgeQuestionListEditFragment extends BaseFragment {
    private static final String TAG = "CourseDirectoryNoteFrag";
    private TreeNodeAdapter<CourseDirectoryTreeBean> adapter;
    private String course_id;
    private int drawableIdAllSelect;
    private int drawableIdPartSelect;
    private int drawableIdUnSelect;
    private CustomEmptyView emptyView;
    private TreeNode<CourseDirectoryTreeBean> firstExpandTree;
    private String noteFilePath;
    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private TreeNode<CourseDirectoryTreeBean> secondExpandTree;
    private CourseDirectoryExportNoteFragment.SelectNumChangeListener selectNumChangeListener;
    private List<TreeNode<CourseDirectoryTreeBean>> list = new ArrayList();
    private final HashMap<String, Integer> noteCountMap = new HashMap<>();
    private HashMap<String, List<String>> newChapterParams = new HashMap<>();
    private int selectNoteCount = 0;
    private int noteAllCount = 0;

    /* renamed from: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeQuestionListEditFragment$1, reason: invalid class name */
    public class AnonymousClass1 extends TreeNodeDelegate<CourseDirectoryTreeBean> {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(TreeNode treeNode, View view) {
            if (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
                KnowledgeQuestionListEditFragment.this.toastOnUiThread("没有子课程");
                return;
            }
            KnowledgeQuestionListEditFragment.this.selectOperaAllDirectory(treeNode, this.adapter, Boolean.valueOf(!TreeNodeUtilKt.allSelect(treeNode)));
            KnowledgeQuestionListEditFragment knowledgeQuestionListEditFragment = KnowledgeQuestionListEditFragment.this;
            knowledgeQuestionListEditFragment.updateSelectNoteCount(knowledgeQuestionListEditFragment.list);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$1(TreeNode treeNode, View view) {
            if (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
                KnowledgeQuestionListEditFragment.this.toastOnUiThread("该目录下没有笔记");
            } else {
                this.adapter.expandOrCollapseTreeNode(treeNode);
            }
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public void convert(ViewHolder holder, final TreeNode<CourseDirectoryTreeBean> treeNode) {
            ImageView imageView = (ImageView) holder.getView(R.id.noteFirstImage);
            ImageView imageView2 = (ImageView) holder.getView(R.id.noteImgArrow);
            TextView textView = (TextView) holder.getView(R.id.noteFirstTitle);
            TextView textView2 = (TextView) holder.getView(R.id.tvNoteCount);
            CourseDirectoryItemData item = treeNode.getValue().getItem();
            textView.setText(item.getTitle());
            if (KnowledgeQuestionListEditFragment.this.noteCountMap.get(item.getChapter_id()) != null) {
                textView2.setText(KnowledgeQuestionListEditFragment.this.noteCountMap.get(item.getChapter_id()) + "条笔记");
            }
            imageView.setImageDrawable(KnowledgeQuestionListEditFragment.this.getResources().getDrawable(KnowledgeQuestionListEditFragment.this.getSelectImg(treeNode)));
            imageView.setVisibility(0);
            Log.d(KnowledgeQuestionListEditFragment.TAG, "convert: 刷新一级目录： " + treeNode.getValue().getTitle());
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.a0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f15729c.lambda$convert$0(treeNode, view);
                }
            });
            KnowledgeQuestionListEditFragment.this.childIsVideoItem(treeNode);
            holder.getConvertView().setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.b0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f15732c.lambda$convert$1(treeNode, view);
                }
            });
            imageView2.setVisibility(0);
            if (!treeNode.isExpand()) {
                KnowledgeQuestionListEditFragment.this.setArrowSpin(holder, treeNode, false);
            } else {
                KnowledgeQuestionListEditFragment.this.setArrowSpin(holder, treeNode, false);
                KnowledgeQuestionListEditFragment.this.firstExpandTree = treeNode;
            }
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public int getLayoutId() {
            return R.layout.item_course_note_first;
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public boolean isItemType(TreeNode<CourseDirectoryTreeBean> treeNode) {
            return treeNode.getCustomerLevel() == 0;
        }
    }

    /* renamed from: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeQuestionListEditFragment$2, reason: invalid class name */
    public class AnonymousClass2 extends TreeNodeDelegate<CourseDirectoryTreeBean> {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(TreeNode treeNode, View view) {
            if (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
                KnowledgeQuestionListEditFragment.this.toastOnUiThread("该目录下没有笔记");
            } else {
                this.adapter.expandOrCollapseTreeNode(treeNode);
            }
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public void convert(ViewHolder holder, final TreeNode<CourseDirectoryTreeBean> treeNode) {
            ImageView imageView = (ImageView) holder.getView(R.id.noteImgArrow);
            TextView textView = (TextView) holder.getView(R.id.itemNoteSecondTitle);
            ImageView imageView2 = (ImageView) holder.getView(R.id.noteSecondSelectImage);
            TextView textView2 = (TextView) holder.getView(R.id.tvNoteCount);
            CourseDirectoryItemData item = treeNode.getValue().getItem();
            textView.setText(item.getTitle());
            if (KnowledgeQuestionListEditFragment.this.noteCountMap.get(item.getChapter_id()) != null) {
                textView2.setText(KnowledgeQuestionListEditFragment.this.noteCountMap.get(item.getChapter_id()) + "条笔记");
            }
            imageView2.setImageDrawable(KnowledgeQuestionListEditFragment.this.getResources().getDrawable(KnowledgeQuestionListEditFragment.this.getSelectImg(treeNode)));
            imageView2.setVisibility(0);
            imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeQuestionListEditFragment.2.1
                @Override // android.view.View.OnClickListener
                public void onClick(View v2) {
                    if (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
                        KnowledgeQuestionListEditFragment.this.toastOnUiThread("没有子课程");
                        return;
                    }
                    boolean zAllSelect = TreeNodeUtilKt.allSelect(treeNode);
                    AnonymousClass2 anonymousClass2 = AnonymousClass2.this;
                    KnowledgeQuestionListEditFragment.this.selectOperaAllDirectory(treeNode, anonymousClass2.adapter, Boolean.valueOf(!zAllSelect));
                    KnowledgeQuestionListEditFragment knowledgeQuestionListEditFragment = KnowledgeQuestionListEditFragment.this;
                    knowledgeQuestionListEditFragment.updateSelectNoteCount(knowledgeQuestionListEditFragment.list);
                    if (treeNode.getParent() != null) {
                        AnonymousClass2.this.adapter.notifyTreeNode(treeNode.getParent());
                    }
                }
            });
            imageView.setVisibility(0);
            holder.getConvertView().setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.c0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f15735c.lambda$convert$0(treeNode, view);
                }
            });
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public int getLayoutId() {
            return R.layout.item_course_note_second;
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public boolean isItemType(TreeNode<CourseDirectoryTreeBean> treeNode) {
            return treeNode.getCustomerLevel() == 1;
        }
    }

    /* renamed from: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeQuestionListEditFragment$3, reason: invalid class name */
    public class AnonymousClass3 extends TreeNodeDelegate<CourseDirectoryTreeBean> {
        public AnonymousClass3() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(TreeNode treeNode, View view) {
            ((CourseDirectoryTreeBean) treeNode.getValue()).setSelect(!((CourseDirectoryTreeBean) treeNode.getValue()).isSelect());
            this.adapter.notifyTreeNode(treeNode);
            if (treeNode.getParent() != null) {
                this.adapter.notifyTreeNode(treeNode.getParent());
            }
            if (treeNode.getParent() != null && treeNode.getParent().getParent() != null) {
                this.adapter.notifyTreeNode(treeNode.getParent().getParent());
            }
            KnowledgeQuestionListEditFragment knowledgeQuestionListEditFragment = KnowledgeQuestionListEditFragment.this;
            knowledgeQuestionListEditFragment.updateSelectNoteCount(knowledgeQuestionListEditFragment.list);
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public void convert(ViewHolder holder, final TreeNode<CourseDirectoryTreeBean> treeNode) {
            int absoluteAdapterPosition = holder.getAbsoluteAdapterPosition();
            int iDip2px = DpOrPxUtils.dip2px(((BaseFragment) KnowledgeQuestionListEditFragment.this).mContext, 12.0f);
            Log.d(KnowledgeQuestionListEditFragment.TAG, "convert: 第三级数据： pos:" + absoluteAdapterPosition + "--- value: " + treeNode.getValue().getTitle());
            RelativeLayout relativeLayout = (RelativeLayout) holder.getView(R.id.itemInnerLayout);
            ImageView imageView = (ImageView) holder.getView(R.id.noteThirdSelectImage);
            View view = holder.getView(R.id.thirdNoteItemLine);
            TextView textView = (TextView) holder.getView(R.id.tvThirdNoteNum);
            TextView textView2 = (TextView) holder.getView(R.id.noteThirdTitle);
            CourseDirectoryContentItem contentItem = treeNode.getValue().getContentItem();
            textView2.setText(contentItem.getTitle());
            textView.setText(contentItem.getCount() + "条笔记");
            if (TreeNodeUtilKt.haveSecondPresent(treeNode)) {
                relativeLayout.setPadding(iDip2px, 0, iDip2px, 0);
                if (TreeNodeUtilKt.isOnlyOneItem(treeNode)) {
                    view.setVisibility(4);
                    relativeLayout.setBackground(KnowledgeQuestionListEditFragment.this.getResources().getDrawable(R.drawable.shape_new_bg_two_color_corner12, ((BaseFragment) KnowledgeQuestionListEditFragment.this).mContext.getTheme()));
                } else if (TreeNodeUtilKt.isTopRadiusItem(treeNode)) {
                    view.setVisibility(0);
                    relativeLayout.setBackground(KnowledgeQuestionListEditFragment.this.getResources().getDrawable(R.drawable.shape_new_bg_two_color_top_corner12, ((BaseFragment) KnowledgeQuestionListEditFragment.this).mContext.getTheme()));
                } else if (TreeNodeUtilKt.isBottomRadiusItem(treeNode)) {
                    view.setVisibility(4);
                    relativeLayout.setBackground(KnowledgeQuestionListEditFragment.this.getResources().getDrawable(R.drawable.shape_new_bg_two_color_bottom_corner12, ((BaseFragment) KnowledgeQuestionListEditFragment.this).mContext.getTheme()));
                } else {
                    view.setVisibility(0);
                    relativeLayout.setBackground(KnowledgeQuestionListEditFragment.this.getResources().getDrawable(R.drawable.shape_project_normal_bg, ((BaseFragment) KnowledgeQuestionListEditFragment.this).mContext.getTheme()));
                }
            } else {
                relativeLayout.setBackground(null);
                relativeLayout.setPadding(0, 0, 0, 0);
            }
            imageView.setImageDrawable(KnowledgeQuestionListEditFragment.this.getResources().getDrawable(KnowledgeQuestionListEditFragment.this.getSelectImg(treeNode)));
            imageView.setVisibility(0);
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.d0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f15738c.lambda$convert$0(treeNode, view2);
                }
            });
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public int getLayoutId() {
            return R.layout.item_course_export_note_third;
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public boolean isItemType(TreeNode<CourseDirectoryTreeBean> treeNode) {
            return treeNode.getCustomerLevel() == 2;
        }
    }

    public interface SelectNumChangeListener {
        void selectNum(int num);
    }

    private int allNum(TreeNode<CourseDirectoryTreeBean> data) {
        String count = data.getValue().getContentItem().getCount();
        int i2 = !TextUtils.isEmpty(count) ? Integer.parseInt(count) : 0;
        Log.d(TAG, "selectNum: -------" + i2);
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean childIsVideoItem(TreeNode<CourseDirectoryTreeBean> treeNode) {
        return (treeNode == null || treeNode.getCustomerLevel() != 0 || treeNode.getChildren() == null || treeNode.getChildren().isEmpty() || treeNode.getChildren().get(0).getCustomerLevel() != 2) ? false : true;
    }

    private int getAllNoteCount(List<TreeNode<CourseDirectoryTreeBean>> list) {
        List<TreeNode<CourseDirectoryTreeBean>> children;
        int iAllNum = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            TreeNode<CourseDirectoryTreeBean> treeNode = list.get(i2);
            if (treeNode.getCustomerLevel() == 2) {
                iAllNum += allNum(treeNode);
            }
            if (treeNode.getCustomerLevel() == 0 && (children = treeNode.getChildren()) != null && !children.isEmpty()) {
                for (int i3 = 0; i3 < children.size(); i3++) {
                    TreeNode<CourseDirectoryTreeBean> treeNode2 = children.get(i3);
                    if (treeNode2.getCustomerLevel() == 2) {
                        iAllNum += allNum(treeNode2);
                    }
                    if (treeNode2.getChildren() != null && !treeNode2.getChildren().isEmpty()) {
                        List<TreeNode<CourseDirectoryTreeBean>> children2 = treeNode2.getChildren();
                        for (int i4 = 0; i4 < children2.size(); i4++) {
                            TreeNode<CourseDirectoryTreeBean> treeNode3 = children2.get(i4);
                            if (treeNode3.getCustomerLevel() == 2) {
                                iAllNum += allNum(treeNode3);
                            }
                        }
                    }
                }
            }
        }
        return iAllNum;
    }

    private void getListData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("course_id", this.course_id);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.courseDirectoryNote, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeQuestionListEditFragment.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                KnowledgeQuestionListEditFragment.this.emptyView.setLoadFileResUi(KnowledgeQuestionListEditFragment.this.getContext());
                KnowledgeQuestionListEditFragment.this.emptyView.setVisibility(0);
                KnowledgeQuestionListEditFragment.this.recyclerView.setVisibility(8);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass4) t2);
                try {
                    KnowledgeQuestionListEditFragment.this.refreshLayout.finishRefresh(true);
                    JSONObject jSONObject = new JSONObject(t2);
                    if (jSONObject.optString("code").equals("200")) {
                        List list = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<CourseDirectoryItemData>>() { // from class: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeQuestionListEditFragment.4.1
                        }.getType());
                        KnowledgeQuestionListEditFragment.this.initNoteCount(list);
                        KnowledgeQuestionListEditFragment.this.initListLayout(list);
                        KnowledgeQuestionListEditFragment.this.emptyView.setVisibility(8);
                        KnowledgeQuestionListEditFragment.this.recyclerView.setVisibility(0);
                    } else {
                        KnowledgeQuestionListEditFragment.this.emptyView.uploadEmptyViewResUi();
                        KnowledgeQuestionListEditFragment.this.emptyView.setVisibility(0);
                        KnowledgeQuestionListEditFragment.this.recyclerView.setVisibility(8);
                    }
                } catch (Exception e2) {
                    Log.e(KnowledgeQuestionListEditFragment.TAG, "onSuccess: 请求目录列表异常:" + e2.getMessage());
                    KnowledgeQuestionListEditFragment.this.emptyView.setLoadFileResUi(KnowledgeQuestionListEditFragment.this.getContext());
                    KnowledgeQuestionListEditFragment.this.emptyView.setVisibility(0);
                    KnowledgeQuestionListEditFragment.this.recyclerView.setVisibility(8);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getSelectImg(TreeNode<CourseDirectoryTreeBean> treeNode) {
        if (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
            if (treeNode.getValue().isSelect()) {
                Log.d(TAG, "getSelectImg: ----获取图片---单课程--选中");
                return this.drawableIdAllSelect;
            }
            Log.d(TAG, "getSelectImg: ----获取图片---单课程--未选中");
            return this.drawableIdUnSelect;
        }
        if (TreeNodeUtilKt.allSelect(treeNode)) {
            Log.d(TAG, "getSelectImg: ----获取图片---全部选中");
            return this.drawableIdAllSelect;
        }
        if (TreeNodeUtilKt.noOneSelect(treeNode)) {
            Log.d(TAG, "getSelectImg: ----获取图片---未选中");
            return this.drawableIdUnSelect;
        }
        Log.d(TAG, "getSelectImg: ----获取图片---部分选中");
        return this.drawableIdPartSelect;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initListLayout(List<CourseDirectoryItemData> dateBeanList) {
        List<TreeNode<CourseDirectoryTreeBean>> listCourseDirectoryItemToTreeNodeData = TreeNodeUtilKt.courseDirectoryItemToTreeNodeData(dateBeanList);
        this.list = listCourseDirectoryItemToTreeNodeData;
        this.noteAllCount = getAllNoteCount(listCourseDirectoryItemToTreeNodeData);
        TreeNodeAdapter<CourseDirectoryTreeBean> treeNodeAdapter = new TreeNodeAdapter<>(getActivity(), this.list);
        this.adapter = treeNodeAdapter;
        treeNodeAdapter.addItemViewDelegate(new AnonymousClass1());
        this.adapter.addItemViewDelegate(new AnonymousClass2());
        this.adapter.addItemViewDelegate(new AnonymousClass3());
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initNoteCount(List<CourseDirectoryItemData> dataList) throws NumberFormatException {
        int i2;
        for (int i3 = 0; i3 < dataList.size(); i3++) {
            CourseDirectoryItemData courseDirectoryItemData = dataList.get(i3);
            if (courseDirectoryItemData.getContent() != null && !courseDirectoryItemData.getContent().isEmpty()) {
                int i4 = 0;
                for (int i5 = 0; i5 < courseDirectoryItemData.getContent().size(); i5++) {
                    String count = courseDirectoryItemData.getContent().get(i5).getCount();
                    if (!TextUtils.isEmpty(count)) {
                        i4 += Integer.parseInt(count);
                    }
                }
                this.noteCountMap.put(courseDirectoryItemData.getChapter_id(), Integer.valueOf(i4));
            } else if (courseDirectoryItemData.getChildren() == null || courseDirectoryItemData.getChildren().isEmpty()) {
                this.noteCountMap.put(courseDirectoryItemData.getChapter_id(), 0);
            } else {
                List<CourseDirectoryItemData> children = courseDirectoryItemData.getChildren();
                int i6 = 0;
                for (int i7 = 0; i7 < children.size(); i7++) {
                    CourseDirectoryItemData courseDirectoryItemData2 = children.get(i7);
                    if (courseDirectoryItemData2.getContent() == null || courseDirectoryItemData2.getContent().isEmpty()) {
                        i2 = 0;
                    } else {
                        i2 = 0;
                        for (int i8 = 0; i8 < courseDirectoryItemData2.getContent().size(); i8++) {
                            String count2 = courseDirectoryItemData2.getContent().get(i8).getCount();
                            if (!TextUtils.isEmpty(count2)) {
                                int i9 = Integer.parseInt(count2);
                                i2 += i9;
                                i6 += i9;
                            }
                        }
                    }
                    this.noteCountMap.put(courseDirectoryItemData2.getChapter_id(), Integer.valueOf(i2));
                }
                this.noteCountMap.put(courseDirectoryItemData.getChapter_id(), Integer.valueOf(i6));
            }
        }
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
        this.selectNumChangeListener.selectNum(this.selectNoteCount);
    }

    private int selectNum(TreeNode<CourseDirectoryTreeBean> data) throws NumberFormatException {
        int i2 = 0;
        if (data.getValue().isSelect()) {
            CourseDirectoryContentItem contentItem = data.getValue().getContentItem();
            String count = contentItem.getCount();
            if (!TextUtils.isEmpty(count)) {
                int i3 = Integer.parseInt(count);
                String chapter_id = contentItem.getChapter_id();
                if (TextUtils.isEmpty(chapter_id)) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(contentItem.getObj_id());
                    this.newChapterParams.put(contentItem.getChapter_id(), arrayList);
                } else if (this.newChapterParams.get(chapter_id) == null) {
                    ArrayList arrayList2 = new ArrayList();
                    arrayList2.add(contentItem.getObj_id());
                    this.newChapterParams.put(chapter_id, arrayList2);
                } else {
                    List<String> list = this.newChapterParams.get(chapter_id);
                    if (list != null) {
                        list.add(contentItem.getObj_id());
                        this.newChapterParams.put(chapter_id, list);
                    }
                }
                i2 = i3;
            }
            Log.d(TAG, "selectNum: -------" + i2);
        }
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void selectOperaAllDirectory(TreeNode<CourseDirectoryTreeBean> data, TreeNodeAdapter<CourseDirectoryTreeBean> adapter, Boolean selectAllOrUnSelectAll) {
        List<TreeNode<CourseDirectoryTreeBean>> children = data.getChildren();
        if (children != null && !children.isEmpty()) {
            for (int i2 = 0; i2 < children.size(); i2++) {
                TreeNode<CourseDirectoryTreeBean> treeNode = children.get(i2);
                treeNode.getValue().setSelect(selectAllOrUnSelectAll.booleanValue());
                adapter.notifyTreeNode(treeNode);
                List<TreeNode<CourseDirectoryTreeBean>> children2 = treeNode.getChildren();
                if (children2 != null && !children2.isEmpty()) {
                    for (int i3 = 0; i3 < children2.size(); i3++) {
                        TreeNode<CourseDirectoryTreeBean> treeNode2 = children2.get(i3);
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
    public void setArrowSpin(ViewHolder helper, TreeNode<CourseDirectoryTreeBean> data, boolean isAnimate) {
        final ImageView imageView = (ImageView) helper.getView(R.id.noteImgArrow);
        if (data.isExpand()) {
            Log.d(TAG, "setArrowSpin: --- 展开 ");
            if (isAnimate) {
                imageView.animate().rotation(180.0f).setDuration(300L).setInterpolator(new LinearInterpolator()).start();
                return;
            } else if (SkinManager.getCurrentSkinType(getActivity()) == 1) {
                imageView.setImageResource(R.drawable.icon_top_arrow_night);
                return;
            } else {
                imageView.setImageResource(R.drawable.icon_top_arrow_day);
                return;
            }
        }
        Log.d(TAG, "setArrowSpin: --- 关闭 ");
        if (isAnimate) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(imageView, Key.ROTATION, 0.0f, 180.0f);
            objectAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeQuestionListEditFragment.6
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animation) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    imageView.setRotation(0.0f);
                    if (SkinManager.getCurrentSkinType(KnowledgeQuestionListEditFragment.this.getActivity()) == 1) {
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
    public void updateSelectNoteCount(List<TreeNode<CourseDirectoryTreeBean>> list) {
        List<TreeNode<CourseDirectoryTreeBean>> children;
        this.newChapterParams.clear();
        int iSelectNum = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            TreeNode<CourseDirectoryTreeBean> treeNode = list.get(i2);
            if (treeNode.getCustomerLevel() == 2) {
                iSelectNum += selectNum(treeNode);
            }
            if (treeNode.getCustomerLevel() == 0 && (children = treeNode.getChildren()) != null && !children.isEmpty()) {
                for (int i3 = 0; i3 < children.size(); i3++) {
                    TreeNode<CourseDirectoryTreeBean> treeNode2 = children.get(i3);
                    if (treeNode2.getCustomerLevel() == 2) {
                        iSelectNum += selectNum(treeNode2);
                    }
                    if (treeNode2.getChildren() != null && !treeNode2.getChildren().isEmpty()) {
                        List<TreeNode<CourseDirectoryTreeBean>> children2 = treeNode2.getChildren();
                        for (int i4 = 0; i4 < children2.size(); i4++) {
                            TreeNode<CourseDirectoryTreeBean> treeNode3 = children2.get(i4);
                            if (treeNode3.getCustomerLevel() == 2) {
                                iSelectNum += selectNum(treeNode3);
                            }
                        }
                    }
                }
            }
        }
        this.selectNoteCount = iSelectNum;
        if (this.selectNumChangeListener != null) {
            new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.knowledge.z
                @Override // java.lang.Runnable
                public final void run() {
                    this.f15793c.lambda$updateSelectNoteCount$2();
                }
            }, 500L);
        }
        Log.e(TAG, "updateSelectNoteCount: " + JSON.toJSONString(this.newChapterParams));
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_knowledge_question_list_edit;
    }

    public int getNoteAllCount() {
        return this.noteAllCount;
    }

    public void getNoteDownLoad() {
        if (this.newChapterParams.isEmpty()) {
            toastOnUiThread("没有选中笔记");
            return;
        }
        File file = new File(this.noteFilePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        CommonUtil.deleteFile(new File(this.noteFilePath));
        AjaxParams ajaxParams = new AjaxParams();
        String str = NetworkRequestsURL.courseNoteExport;
        ajaxParams.put("uid", UserConfig.getUserId());
        ajaxParams.put("chapters", JSON.toJSONString(this.newChapterParams));
        ajaxParams.put("course_id", this.course_id);
        LogUtils.d(TAG, ajaxParams.getParamString() + ",url=" + str);
        YJYHttpUtils.getNoteFile(getActivity(), str, this.noteFilePath, ajaxParams, new AjaxCallBack<File>() { // from class: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeQuestionListEditFragment.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                KnowledgeQuestionListEditFragment.this.hideProgressDialog();
                if (errorNo == 0) {
                    KnowledgeQuestionListEditFragment.this.AlertToast("无法导出,请检查app存储权限是否打开！");
                    return;
                }
                if (errorNo == 200) {
                    KnowledgeQuestionListEditFragment.this.AlertToast(strMsg);
                    return;
                }
                KnowledgeQuestionListEditFragment.this.AlertToast(strMsg + "");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onLoading(long count, long current) {
                super.onLoading(count, current);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                KnowledgeQuestionListEditFragment.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(File file2) {
                super.onSuccess((AnonymousClass5) file2);
                KnowledgeQuestionListEditFragment.this.hideProgressDialog();
                CommonUtil.showDialog(KnowledgeQuestionListEditFragment.this.getActivity(), file2.getAbsolutePath());
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(com.psychiatrygarden.baseview.ViewHolder holder, View root) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.course_id = arguments.getString("course_id");
        }
        this.recyclerView = (RecyclerView) holder.get(R.id.recyclerView);
        this.refreshLayout = (SmartRefreshLayout) holder.get(R.id.refresh);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.refreshLayout.setEnableLoadMore(false);
        this.refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.x
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f15791c.lambda$initViews$0(refreshLayout);
            }
        });
        this.drawableIdAllSelect = isNightTheme() ? R.drawable.download_new_select_night : R.drawable.download_new_select_day;
        this.drawableIdUnSelect = isNightTheme() ? R.drawable.download_new_not_select_night : R.drawable.download_new_not_select;
        this.drawableIdPartSelect = isNightTheme() ? R.drawable.download_part_select_night : R.drawable.download_part_select;
        int i2 = SkinManager.getCurrentSkinType(getContext()) == 1 ? R.drawable.ic_empty_data_note_night : R.drawable.ic_empty_data_note_day;
        CustomEmptyView customEmptyView = (CustomEmptyView) holder.get(R.id.emptyView);
        this.emptyView = customEmptyView;
        customEmptyView.setImgEmptyRes(i2);
        this.emptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.y
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15792c.lambda$initViews$1(view);
            }
        });
        this.emptyView.changeEmptyViewWriteBg();
        this.emptyView.setVisibility(8);
        getListData();
        if (Build.VERSION.SDK_INT >= 29) {
            this.noteFilePath = getActivity().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getPath();
        } else {
            this.noteFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath();
        }
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
            this.newChapterParams.clear();
        }
        updateSelectNoteCount(this.list);
    }

    public void setSelectNumChangeListener(CourseDirectoryExportNoteFragment.SelectNumChangeListener selectNumChangeListener) {
        this.selectNumChangeListener = selectNumChangeListener;
    }
}
