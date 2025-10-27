package com.psychiatrygarden.activity.courselist.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
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
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.bean.CourseDirectoryTreeBean;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.TreeNodeUtilKt;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.psychiatrygarden.widget.treenode.TreeNode;
import com.psychiatrygarden.widget.treenode.TreeNodeAdapter;
import com.psychiatrygarden.widget.treenode.TreeNodeDelegate;
import com.psychiatrygarden.widget.treenode.ViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class CourseDirectoryEditListFragment extends BaseFragment {
    private static final String TAG = "CourseDirectoryNoteFrag";
    private TreeNodeAdapter<CourseDirectoryTreeBean> adapter;
    private int drawableIdAllSelect;
    private int drawableIdPartSelect;
    private int drawableIdUnSelect;
    private CustomEmptyView emptyView;
    private TreeNode<CourseDirectoryTreeBean> firstExpandTree;
    private RecyclerView recyclerview;
    private SmartRefreshLayout refreshLayout;
    private TreeNode<CourseDirectoryTreeBean> secondExpandTree;
    private SelectCallBack selectCallBack;
    private final int EXPAND_COLLAPSE_PAYLOAD = 110;
    private int position = 0;
    private int page = 1;
    private List<TreeNode<CourseDirectoryTreeBean>> list = new ArrayList();
    private String expandFirstId = "";
    private String expandSecondId = "";

    public interface SelectCallBack {
        void selectNum(int num);
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

    private int getSelectNumByDirectory(TreeNode<CourseDirectoryTreeBean> data) {
        List<TreeNode<CourseDirectoryTreeBean>> children = data.getChildren();
        if (children == null || children.isEmpty()) {
            return 0;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < children.size(); i3++) {
            TreeNode<CourseDirectoryTreeBean> treeNode = children.get(i3);
            if (treeNode.getValue().isSelect() && treeNode.getCustomerLevel() == 2) {
                i2++;
            }
            List<TreeNode<CourseDirectoryTreeBean>> children2 = treeNode.getChildren();
            if (children2 != null && !children2.isEmpty()) {
                for (int i4 = 0; i4 < children2.size(); i4++) {
                    TreeNode<CourseDirectoryTreeBean> treeNode2 = children2.get(i4);
                    if (treeNode2.getValue().isSelect() && treeNode2.getCustomerLevel() == 2) {
                        i2++;
                    }
                }
            }
        }
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(RefreshLayout refreshLayout) {
        this.page = 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(RefreshLayout refreshLayout) {
        this.page++;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$initViews$2(View view) {
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
        }
        adapter.notifyTreeNode(data);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setArrowSpin(ViewHolder helper, TreeNode<CourseDirectoryTreeBean> data, boolean isAnimate) {
        final ImageView imageView = (ImageView) helper.getView(R.id.itemArrow);
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
            objectAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryEditListFragment.4
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animation) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    imageView.setRotation(0.0f);
                    if (SkinManager.getCurrentSkinType(CourseDirectoryEditListFragment.this.getActivity()) == 1) {
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

    private void setNewLayout() {
        for (int i2 = 0; i2 < 10; i2++) {
            TreeNode<CourseDirectoryTreeBean> treeNode = new TreeNode<>(new CourseDirectoryTreeBean("第" + i2 + "章 绪论【直播】:", i2 + ""));
            treeNode.setCustomerLevel(0);
            this.list.add(treeNode);
            if (i2 != 1) {
                for (int i3 = 0; i3 < 4; i3++) {
                    TreeNode<CourseDirectoryTreeBean> treeNode2 = new TreeNode<>(new CourseDirectoryTreeBean("第:" + i3 + "节", i3 + ""));
                    treeNode2.setCustomerLevel(1);
                    treeNode.addChild(treeNode2);
                    if (i3 == 0) {
                        treeNode2.setCustomerLevel(1);
                    } else if (i3 != 1) {
                        treeNode2.setCustomerLevel(2);
                    } else {
                        for (int i4 = 0; i4 < 3; i4++) {
                            TreeNode<CourseDirectoryTreeBean> treeNode3 = new TreeNode<>(new CourseDirectoryTreeBean("25考研长难句试听课直播课:" + i4, i4 + ""));
                            treeNode3.setCustomerLevel(2);
                            treeNode2.addChild(treeNode3);
                        }
                    }
                }
            } else {
                treeNode.setCustomerLevel(2);
            }
        }
        TreeNodeAdapter<CourseDirectoryTreeBean> treeNodeAdapter = new TreeNodeAdapter<>(getActivity(), this.list);
        this.adapter = treeNodeAdapter;
        treeNodeAdapter.addItemViewDelegate(new TreeNodeDelegate<CourseDirectoryTreeBean>() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryEditListFragment.1
            @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
            public void convert(ViewHolder holder, final TreeNode<CourseDirectoryTreeBean> treeNode4) {
                ImageView imageView = (ImageView) holder.getView(R.id.noteFirstImage);
                TextView textView = (TextView) holder.getView(R.id.itemFirstTitle);
                holder.getView(R.id.viewEditLine).setVisibility(8);
                textView.setText(treeNode4.getValue().getTitle());
                textView.getPaint().setFakeBoldText(true);
                imageView.setImageDrawable(CourseDirectoryEditListFragment.this.getResources().getDrawable(CourseDirectoryEditListFragment.this.getSelectImg(treeNode4)));
                imageView.setVisibility(0);
                Log.d(CourseDirectoryEditListFragment.TAG, "convert: 刷新一级目录： " + treeNode4.getValue().getTitle());
                imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryEditListFragment.1.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v2) {
                        if (treeNode4.getChildren() == null || treeNode4.getChildren().isEmpty()) {
                            CourseDirectoryEditListFragment.this.toastOnUiThread("没有子课程");
                            return;
                        }
                        if (TreeNodeUtilKt.allSelect(treeNode4)) {
                            AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                            CourseDirectoryEditListFragment.this.selectOperaAllDirectory(treeNode4, anonymousClass1.adapter, Boolean.FALSE);
                        } else {
                            AnonymousClass1 anonymousClass12 = AnonymousClass1.this;
                            CourseDirectoryEditListFragment.this.selectOperaAllDirectory(treeNode4, anonymousClass12.adapter, Boolean.TRUE);
                        }
                        if (CourseDirectoryEditListFragment.this.selectCallBack != null) {
                            SelectCallBack selectCallBack = CourseDirectoryEditListFragment.this.selectCallBack;
                            CourseDirectoryEditListFragment courseDirectoryEditListFragment = CourseDirectoryEditListFragment.this;
                            selectCallBack.selectNum(courseDirectoryEditListFragment.getSelectAllNum(courseDirectoryEditListFragment.list));
                        }
                    }
                });
                holder.getConvertView().setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryEditListFragment.1.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v2) {
                        if (treeNode4.getChildren() == null || treeNode4.getChildren().isEmpty()) {
                            CourseDirectoryEditListFragment.this.toastOnUiThread("改目录下没有课程!");
                            return;
                        }
                        if (CourseDirectoryEditListFragment.this.firstExpandTree != null && !TreeNodeUtilKt.treeNodeEquals(treeNode4, CourseDirectoryEditListFragment.this.firstExpandTree) && CourseDirectoryEditListFragment.this.firstExpandTree.isExpand()) {
                            AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                            anonymousClass1.adapter.expandOrCollapseTreeNode(CourseDirectoryEditListFragment.this.firstExpandTree);
                        }
                        AnonymousClass1.this.adapter.expandOrCollapseTreeNode(treeNode4);
                    }
                });
                if (!treeNode4.isExpand()) {
                    CourseDirectoryEditListFragment.this.setArrowSpin(holder, treeNode4, false);
                } else {
                    CourseDirectoryEditListFragment.this.setArrowSpin(holder, treeNode4, false);
                    CourseDirectoryEditListFragment.this.firstExpandTree = treeNode4;
                }
            }

            @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
            public int getLayoutId() {
                return R.layout.item_course_directory_edit_first;
            }

            @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
            public boolean isItemType(TreeNode<CourseDirectoryTreeBean> treeNode4) {
                return treeNode4.getCustomerLevel() == 0;
            }
        });
        this.adapter.addItemViewDelegate(new TreeNodeDelegate<CourseDirectoryTreeBean>() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryEditListFragment.2
            @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
            public void convert(ViewHolder holder, final TreeNode<CourseDirectoryTreeBean> treeNode4) {
                ImageView imageView = (ImageView) holder.getView(R.id.noteFirstImage);
                TextView textView = (TextView) holder.getView(R.id.itemFirstTitle);
                holder.getView(R.id.viewEditLine).setVisibility(8);
                textView.setText(treeNode4.getValue().getTitle());
                textView.getPaint().setFakeBoldText(false);
                imageView.setImageDrawable(CourseDirectoryEditListFragment.this.getResources().getDrawable(CourseDirectoryEditListFragment.this.getSelectImg(treeNode4)));
                imageView.setVisibility(0);
                imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryEditListFragment.2.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v2) {
                        if (treeNode4.getChildren() == null || treeNode4.getChildren().isEmpty()) {
                            CourseDirectoryEditListFragment.this.toastOnUiThread("没有子课程");
                            return;
                        }
                        if (TreeNodeUtilKt.allSelect(treeNode4)) {
                            AnonymousClass2 anonymousClass2 = AnonymousClass2.this;
                            CourseDirectoryEditListFragment.this.selectOperaAllDirectory(treeNode4, anonymousClass2.adapter, Boolean.FALSE);
                        } else {
                            AnonymousClass2 anonymousClass22 = AnonymousClass2.this;
                            CourseDirectoryEditListFragment.this.selectOperaAllDirectory(treeNode4, anonymousClass22.adapter, Boolean.TRUE);
                        }
                        if (treeNode4.getParent() != null) {
                            AnonymousClass2.this.adapter.notifyTreeNode(treeNode4.getParent());
                        }
                        if (CourseDirectoryEditListFragment.this.selectCallBack != null) {
                            SelectCallBack selectCallBack = CourseDirectoryEditListFragment.this.selectCallBack;
                            CourseDirectoryEditListFragment courseDirectoryEditListFragment = CourseDirectoryEditListFragment.this;
                            selectCallBack.selectNum(courseDirectoryEditListFragment.getSelectAllNum(courseDirectoryEditListFragment.list));
                        }
                    }
                });
                holder.getConvertView().setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryEditListFragment.2.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v2) {
                        if (treeNode4.getChildren() == null || treeNode4.getChildren().isEmpty()) {
                            CourseDirectoryEditListFragment.this.toastOnUiThread("改目录下没有课程!");
                            return;
                        }
                        if (CourseDirectoryEditListFragment.this.secondExpandTree != null && !TreeNodeUtilKt.treeNodeEquals(CourseDirectoryEditListFragment.this.secondExpandTree, treeNode4) && CourseDirectoryEditListFragment.this.secondExpandTree.isExpand()) {
                            AnonymousClass2 anonymousClass2 = AnonymousClass2.this;
                            anonymousClass2.adapter.expandOrCollapseTreeNode(CourseDirectoryEditListFragment.this.secondExpandTree);
                        }
                        AnonymousClass2.this.adapter.expandOrCollapseTreeNode(treeNode4);
                    }
                });
                if (!treeNode4.isExpand()) {
                    CourseDirectoryEditListFragment.this.setArrowSpin(holder, treeNode4, false);
                } else {
                    CourseDirectoryEditListFragment.this.secondExpandTree = treeNode4;
                    CourseDirectoryEditListFragment.this.setArrowSpin(holder, treeNode4, false);
                }
            }

            @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
            public int getLayoutId() {
                return R.layout.item_course_directory_edit_first;
            }

            @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
            public boolean isItemType(TreeNode<CourseDirectoryTreeBean> treeNode4) {
                return treeNode4.getCustomerLevel() == 1;
            }
        });
        this.adapter.addItemViewDelegate(new TreeNodeDelegate<CourseDirectoryTreeBean>() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryEditListFragment.3
            @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
            public void convert(ViewHolder holder, final TreeNode<CourseDirectoryTreeBean> treeNode4) {
                holder.getAbsoluteAdapterPosition();
                int iDip2px = DpOrPxUtils.dip2px(((BaseFragment) CourseDirectoryEditListFragment.this).mContext, 16.0f);
                ImageView imageView = (ImageView) holder.getView(R.id.noteFirstImage);
                TextView textView = (TextView) holder.getView(R.id.itemFirstTitle);
                ImageView imageView2 = (ImageView) holder.getView(R.id.itemArrow);
                View view = holder.getView(R.id.viewEditLine);
                RelativeLayout relativeLayout = (RelativeLayout) holder.getView(R.id.layoutContent);
                imageView2.setVisibility(8);
                Log.d(CourseDirectoryEditListFragment.TAG, "convert: 第三级数据： :--- value: " + treeNode4.getValue().getTitle());
                if (TreeNodeUtilKt.haveSecondPresent(treeNode4)) {
                    relativeLayout.setPadding(iDip2px, 0, iDip2px, 0);
                    if (TreeNodeUtilKt.isOnlyOneItem(treeNode4)) {
                        view.setVisibility(4);
                        relativeLayout.setBackground(CourseDirectoryEditListFragment.this.getResources().getDrawable(R.drawable.shape_new_bg_two_color_corner12, ((BaseFragment) CourseDirectoryEditListFragment.this).mContext.getTheme()));
                    } else if (TreeNodeUtilKt.isTopRadiusItem(treeNode4)) {
                        view.setVisibility(0);
                        relativeLayout.setBackground(CourseDirectoryEditListFragment.this.getResources().getDrawable(R.drawable.shape_new_bg_two_color_top_corner12, ((BaseFragment) CourseDirectoryEditListFragment.this).mContext.getTheme()));
                    } else if (TreeNodeUtilKt.isBottomRadiusItem(treeNode4)) {
                        view.setVisibility(4);
                        relativeLayout.setBackground(CourseDirectoryEditListFragment.this.getResources().getDrawable(R.drawable.shape_new_bg_two_color_bottom_corner12, ((BaseFragment) CourseDirectoryEditListFragment.this).mContext.getTheme()));
                    } else {
                        view.setVisibility(0);
                        relativeLayout.setBackground(CourseDirectoryEditListFragment.this.getResources().getDrawable(R.drawable.shape_project_normal_bg, ((BaseFragment) CourseDirectoryEditListFragment.this).mContext.getTheme()));
                    }
                } else {
                    relativeLayout.setPadding(0, 0, 0, 0);
                    relativeLayout.setBackground(null);
                }
                imageView.setImageDrawable(CourseDirectoryEditListFragment.this.getResources().getDrawable(CourseDirectoryEditListFragment.this.getSelectImg(treeNode4)));
                imageView.setVisibility(0);
                imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryEditListFragment.3.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v2) {
                        ((CourseDirectoryTreeBean) treeNode4.getValue()).setSelect(!((CourseDirectoryTreeBean) treeNode4.getValue()).isSelect());
                        AnonymousClass3.this.adapter.notifyTreeNode(treeNode4);
                        if (treeNode4.getParent() != null) {
                            AnonymousClass3.this.adapter.notifyTreeNode(treeNode4.getParent());
                        }
                        if (treeNode4.getParent() == null || treeNode4.getParent().getParent() == null) {
                            return;
                        }
                        AnonymousClass3.this.adapter.notifyTreeNode(treeNode4.getParent().getParent());
                    }
                });
                textView.setText(treeNode4.getValue().getTitle());
                if (CourseDirectoryEditListFragment.this.selectCallBack != null) {
                    SelectCallBack selectCallBack = CourseDirectoryEditListFragment.this.selectCallBack;
                    CourseDirectoryEditListFragment courseDirectoryEditListFragment = CourseDirectoryEditListFragment.this;
                    selectCallBack.selectNum(courseDirectoryEditListFragment.getSelectAllNum(courseDirectoryEditListFragment.list));
                }
            }

            @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
            public int getLayoutId() {
                return R.layout.item_course_directory_edit_first;
            }

            @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
            public boolean isItemType(TreeNode<CourseDirectoryTreeBean> treeNode4) {
                return treeNode4.getCustomerLevel() == 2;
            }
        });
        this.recyclerview.setAdapter(this.adapter);
        this.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_course_directory_edit_list;
    }

    public int getSelectAllNum(List<TreeNode<CourseDirectoryTreeBean>> list) {
        if (list == null || list.isEmpty()) {
            return 0;
        }
        int selectNumByDirectory = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            selectNumByDirectory += getSelectNumByDirectory(list.get(i2));
        }
        return selectNumByDirectory;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(com.psychiatrygarden.baseview.ViewHolder holder, View root) {
        this.recyclerview = (RecyclerView) holder.get(R.id.courseDirectoryListRecyclerView);
        this.refreshLayout = (SmartRefreshLayout) holder.get(R.id.courseDirectoryListRefresh);
        this.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.refreshLayout.setEnableLoadMore(false);
        this.refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.b0
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f11922c.lambda$initViews$0(refreshLayout);
            }
        });
        this.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.c0
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                this.f11931c.lambda$initViews$1(refreshLayout);
            }
        });
        this.drawableIdAllSelect = SkinManager.getCurrentSkinType(getContext()) == 0 ? R.drawable.download_new_select_day : R.drawable.download_new_select_night;
        this.drawableIdUnSelect = SkinManager.getCurrentSkinType(getContext()) == 0 ? R.drawable.download_new_not_select : R.drawable.download_new_not_select_night;
        this.drawableIdPartSelect = SkinManager.getCurrentSkinType(getContext()) == 0 ? R.drawable.download_part_select : R.drawable.download_part_select_night;
        int i2 = SkinManager.getCurrentSkinType(getContext()) == 1 ? R.drawable.ic_empty_data_note_night : R.drawable.ic_empty_data_note_day;
        CustomEmptyView customEmptyView = (CustomEmptyView) holder.get(R.id.emptyView);
        this.emptyView = customEmptyView;
        customEmptyView.setEmptyTextStr("暂无笔记");
        this.emptyView.setImgEmptyRes(i2);
        this.emptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.d0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CourseDirectoryEditListFragment.lambda$initViews$2(view);
            }
        });
        this.emptyView.changeEmptyViewWriteBg();
        this.emptyView.setVisibility(8);
        setNewLayout();
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getArguments();
    }

    public void selectOperaAll(Boolean selectAllOrUnSelectAll) {
        for (int i2 = 0; i2 < this.list.size(); i2++) {
            TreeNode<CourseDirectoryTreeBean> treeNode = this.list.get(i2);
            if (treeNode.getChildren() == null) {
                treeNode.setSelect(selectAllOrUnSelectAll.booleanValue());
            }
            List<TreeNode<CourseDirectoryTreeBean>> children = treeNode.getChildren();
            if (children != null && !children.isEmpty()) {
                for (int i3 = 0; i3 < children.size(); i3++) {
                    TreeNode<CourseDirectoryTreeBean> treeNode2 = children.get(i3);
                    List<TreeNode<CourseDirectoryTreeBean>> children2 = treeNode2.getChildren();
                    if (children2 == null || children2.isEmpty()) {
                        treeNode2.getValue().setSelect(selectAllOrUnSelectAll.booleanValue());
                    } else {
                        for (int i4 = 0; i4 < children2.size(); i4++) {
                            children2.get(i4).getValue().setSelect(selectAllOrUnSelectAll.booleanValue());
                        }
                    }
                }
            }
        }
        this.adapter.refreshTreeNode();
    }

    public void setSelectCallBack(SelectCallBack callBack) {
        this.selectCallBack = callBack;
    }
}
