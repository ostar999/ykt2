package com.psychiatrygarden.activity.courselist.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
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
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.bean.CourseDirectoryContentItem;
import com.psychiatrygarden.bean.CourseDirectoryItemData;
import com.psychiatrygarden.bean.CourseDirectoryTreeBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.NavigationUtilKt;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CourseDirectoryNoteFragment extends BaseFragment {
    private static final String TAG = "CourseDirectoryNoteFrag";
    private TreeNodeAdapter<CourseDirectoryTreeBean> adapter;
    private RecyclerView courseNoteRecyclerView;
    private String course_id;
    private int drawableIdAllSelect;
    private int drawableIdPartSelect;
    private int drawableIdUnSelect;
    private CustomEmptyView emptyView;
    private TreeNode<CourseDirectoryTreeBean> firstExpandTree;
    private SmartRefreshLayout refreshLayout;
    private int resId;
    private TreeNode<CourseDirectoryTreeBean> secondExpandTree;
    private SelectNumChangeListener selectNumChangeListener;
    private final int EXPAND_COLLAPSE_PAYLOAD = 110;
    private int position = 0;
    private int page = 1;
    private List<TreeNode<CourseDirectoryTreeBean>> list = new ArrayList();
    private String expandFirstId = "";
    private String expandSecondId = "";
    private boolean EDIT_MODE = false;
    private HashMap<String, Integer> noteCountMap = new HashMap<>();
    private int selectNoteCount = 0;

    /* renamed from: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryNoteFragment$1, reason: invalid class name */
    public class AnonymousClass1 extends TreeNodeDelegate<CourseDirectoryTreeBean> {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(TreeNode treeNode, View view) {
            if (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
                CourseDirectoryNoteFragment.this.toastOnUiThread("没有子课程");
                return;
            }
            CourseDirectoryNoteFragment.this.selectOperaAllDirectory(treeNode, this.adapter, Boolean.valueOf(!TreeNodeUtilKt.allSelect(treeNode)));
            CourseDirectoryNoteFragment courseDirectoryNoteFragment = CourseDirectoryNoteFragment.this;
            courseDirectoryNoteFragment.updateSelectNoteCount(courseDirectoryNoteFragment.list);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$1(TreeNode treeNode, View view) {
            if (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
                CourseDirectoryNoteFragment.this.toastOnUiThread("改目录下没有课程!");
                return;
            }
            if (CourseDirectoryNoteFragment.this.firstExpandTree != null && !TreeNodeUtilKt.treeNodeEquals(treeNode, CourseDirectoryNoteFragment.this.firstExpandTree)) {
                CourseDirectoryNoteFragment.this.firstExpandTree.isExpand();
            }
            this.adapter.expandOrCollapseTreeNode(treeNode);
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public void convert(ViewHolder holder, final TreeNode<CourseDirectoryTreeBean> treeNode) {
            ImageView imageView = (ImageView) holder.getView(R.id.noteFirstImage);
            TextView textView = (TextView) holder.getView(R.id.noteFirstTitle);
            TextView textView2 = (TextView) holder.getView(R.id.tvNoteCount);
            CourseDirectoryItemData item = treeNode.getValue().getItem();
            textView.setText(item.getTitle());
            if (CourseDirectoryNoteFragment.this.noteCountMap.get(item.getChapter_id()) != null) {
                textView2.setText(CourseDirectoryNoteFragment.this.noteCountMap.get(item.getChapter_id()) + "条笔记");
            }
            if (CourseDirectoryNoteFragment.this.EDIT_MODE) {
                imageView.setImageDrawable(CourseDirectoryNoteFragment.this.getResources().getDrawable(CourseDirectoryNoteFragment.this.getSelectImg(treeNode)));
                imageView.setVisibility(0);
                Log.d(CourseDirectoryNoteFragment.TAG, "convert: 刷新一级目录： " + treeNode.getValue().getTitle());
                imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.y0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f12074c.lambda$convert$0(treeNode, view);
                    }
                });
            }
            holder.getConvertView().setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.z0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f12080c.lambda$convert$1(treeNode, view);
                }
            });
            if (!treeNode.isExpand()) {
                CourseDirectoryNoteFragment.this.setArrowSpin(holder, treeNode, false);
            } else {
                CourseDirectoryNoteFragment.this.setArrowSpin(holder, treeNode, false);
                CourseDirectoryNoteFragment.this.firstExpandTree = treeNode;
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

    /* renamed from: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryNoteFragment$3, reason: invalid class name */
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
            CourseDirectoryNoteFragment courseDirectoryNoteFragment = CourseDirectoryNoteFragment.this;
            courseDirectoryNoteFragment.updateSelectNoteCount(courseDirectoryNoteFragment.list);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$1(CourseDirectoryContentItem courseDirectoryContentItem, View view) {
            TreeNodeUtilKt.initWaitPlayList(courseDirectoryContentItem.getObj_id(), courseDirectoryContentItem.getVid(), courseDirectoryContentItem.getTitle(), courseDirectoryContentItem.getCurrent_duration(), courseDirectoryContentItem.getType());
            NavigationUtilKt.goToAliPlayerVideoPlayActivity(CourseDirectoryNoteFragment.this.getActivity(), courseDirectoryContentItem.getObj_id(), courseDirectoryContentItem.getCourse_id(), courseDirectoryContentItem.getVid(), courseDirectoryContentItem.getCover(), courseDirectoryContentItem.getType());
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public void convert(ViewHolder holder, final TreeNode<CourseDirectoryTreeBean> treeNode) {
            int absoluteAdapterPosition = holder.getAbsoluteAdapterPosition();
            int iDip2px = DpOrPxUtils.dip2px(((BaseFragment) CourseDirectoryNoteFragment.this).mContext, 16.0f);
            Log.d(CourseDirectoryNoteFragment.TAG, "convert: 第三级数据： pos:" + absoluteAdapterPosition + "--- value: " + treeNode.getValue().getTitle());
            RelativeLayout relativeLayout = (RelativeLayout) holder.getView(R.id.itemInnerLayout);
            ImageView imageView = (ImageView) holder.getView(R.id.noteThirdSelectImage);
            View view = holder.getView(R.id.thirdNoteItemLine);
            TextView textView = (TextView) holder.getView(R.id.tvThirdNoteNum);
            TextView textView2 = (TextView) holder.getView(R.id.noteThirdTitle);
            final CourseDirectoryContentItem contentItem = treeNode.getValue().getContentItem();
            textView2.setText(contentItem.getTitle());
            textView.setText(contentItem.getCount() + "条笔记");
            if (TreeNodeUtilKt.haveSecondPresent(treeNode)) {
                relativeLayout.setPadding(iDip2px, 0, iDip2px, 0);
                if (TreeNodeUtilKt.isOnlyOneItem(treeNode)) {
                    view.setVisibility(4);
                    relativeLayout.setBackground(CourseDirectoryNoteFragment.this.getResources().getDrawable(R.drawable.shape_new_bg_two_color_corner12, ((BaseFragment) CourseDirectoryNoteFragment.this).mContext.getTheme()));
                } else if (TreeNodeUtilKt.isTopRadiusItem(treeNode)) {
                    view.setVisibility(0);
                    relativeLayout.setBackground(CourseDirectoryNoteFragment.this.getResources().getDrawable(R.drawable.shape_new_bg_two_color_top_corner12, ((BaseFragment) CourseDirectoryNoteFragment.this).mContext.getTheme()));
                } else if (TreeNodeUtilKt.isBottomRadiusItem(treeNode)) {
                    view.setVisibility(4);
                    relativeLayout.setBackground(CourseDirectoryNoteFragment.this.getResources().getDrawable(R.drawable.shape_new_bg_two_color_bottom_corner12, ((BaseFragment) CourseDirectoryNoteFragment.this).mContext.getTheme()));
                } else {
                    view.setVisibility(0);
                    relativeLayout.setBackground(CourseDirectoryNoteFragment.this.getResources().getDrawable(R.drawable.shape_project_normal_bg, ((BaseFragment) CourseDirectoryNoteFragment.this).mContext.getTheme()));
                }
            } else {
                relativeLayout.setBackground(null);
                relativeLayout.setPadding(0, 0, 0, 0);
            }
            if (CourseDirectoryNoteFragment.this.EDIT_MODE) {
                imageView.setImageDrawable(CourseDirectoryNoteFragment.this.getResources().getDrawable(CourseDirectoryNoteFragment.this.getSelectImg(treeNode)));
                imageView.setVisibility(0);
                imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.a1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        this.f11915c.lambda$convert$0(treeNode, view2);
                    }
                });
            }
            relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.b1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f11923c.lambda$convert$1(contentItem, view2);
                }
            });
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public int getLayoutId() {
            return R.layout.item_course_note_third;
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public boolean isItemType(TreeNode<CourseDirectoryTreeBean> treeNode) {
            return treeNode.getCustomerLevel() == 2;
        }
    }

    public interface SelectNumChangeListener {
        void selectNum(int num);
    }

    private void getListData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("course_id", this.course_id);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.courseDirectoryNote, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryNoteFragment.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                CourseDirectoryNoteFragment.this.setErrorView();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass4) t2);
                try {
                    CourseDirectoryNoteFragment.this.refreshLayout.finishRefresh(true);
                    JSONObject jSONObject = new JSONObject(t2);
                    if (jSONObject.optString("code").equals("200")) {
                        List list = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<CourseDirectoryItemData>>() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryNoteFragment.4.1
                        }.getType());
                        if (list == null || list.isEmpty()) {
                            CourseDirectoryNoteFragment.this.setEmptyView();
                        } else {
                            CourseDirectoryNoteFragment.this.initNoteCount(list);
                            CourseDirectoryNoteFragment.this.initListLayout(list);
                            CourseDirectoryNoteFragment.this.emptyView.setVisibility(8);
                            CourseDirectoryNoteFragment.this.courseNoteRecyclerView.setVisibility(0);
                        }
                    } else {
                        CourseDirectoryNoteFragment.this.setEmptyView();
                    }
                } catch (Exception e2) {
                    Log.e(CourseDirectoryNoteFragment.TAG, "onSuccess: 请求目录列表异常:" + e2.getMessage());
                    CourseDirectoryNoteFragment.this.setErrorView();
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
        this.list = TreeNodeUtilKt.courseDirectoryItemToTreeNodeData(dateBeanList);
        TreeNodeAdapter<CourseDirectoryTreeBean> treeNodeAdapter = new TreeNodeAdapter<>(getActivity(), this.list);
        this.adapter = treeNodeAdapter;
        treeNodeAdapter.addItemViewDelegate(new AnonymousClass1());
        this.adapter.addItemViewDelegate(new TreeNodeDelegate<CourseDirectoryTreeBean>() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryNoteFragment.2
            @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
            public void convert(ViewHolder holder, final TreeNode<CourseDirectoryTreeBean> treeNode) {
                TextView textView = (TextView) holder.getView(R.id.itemNoteSecondTitle);
                ImageView imageView = (ImageView) holder.getView(R.id.noteSecondSelectImage);
                TextView textView2 = (TextView) holder.getView(R.id.tvNoteCount);
                CourseDirectoryItemData item = treeNode.getValue().getItem();
                textView.setText(item.getTitle());
                if (CourseDirectoryNoteFragment.this.noteCountMap.get(item.getChapter_id()) != null) {
                    textView2.setText(CourseDirectoryNoteFragment.this.noteCountMap.get(item.getChapter_id()) + "条笔记");
                }
                if (CourseDirectoryNoteFragment.this.EDIT_MODE) {
                    imageView.setImageDrawable(CourseDirectoryNoteFragment.this.getResources().getDrawable(CourseDirectoryNoteFragment.this.getSelectImg(treeNode)));
                    imageView.setVisibility(0);
                    imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryNoteFragment.2.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View v2) {
                            if (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
                                CourseDirectoryNoteFragment.this.toastOnUiThread("没有子课程");
                                return;
                            }
                            AnonymousClass2 anonymousClass2 = AnonymousClass2.this;
                            CourseDirectoryNoteFragment.this.selectOperaAllDirectory(treeNode, anonymousClass2.adapter, Boolean.valueOf(!TreeNodeUtilKt.allSelect(r1)));
                            CourseDirectoryNoteFragment courseDirectoryNoteFragment = CourseDirectoryNoteFragment.this;
                            courseDirectoryNoteFragment.updateSelectNoteCount(courseDirectoryNoteFragment.list);
                            if (treeNode.getParent() != null) {
                                AnonymousClass2.this.adapter.notifyTreeNode(treeNode.getParent());
                            }
                        }
                    });
                }
                holder.getConvertView().setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryNoteFragment.2.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v2) {
                        if (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
                            CourseDirectoryNoteFragment.this.toastOnUiThread("改目录下没有课程!");
                            return;
                        }
                        if (CourseDirectoryNoteFragment.this.secondExpandTree != null && !TreeNodeUtilKt.treeNodeEquals(CourseDirectoryNoteFragment.this.secondExpandTree, treeNode)) {
                            CourseDirectoryNoteFragment.this.secondExpandTree.isExpand();
                        }
                        AnonymousClass2.this.adapter.expandOrCollapseTreeNode(treeNode);
                    }
                });
                if (!treeNode.isExpand()) {
                    CourseDirectoryNoteFragment.this.setArrowSpin(holder, treeNode, false);
                } else {
                    CourseDirectoryNoteFragment.this.secondExpandTree = treeNode;
                    CourseDirectoryNoteFragment.this.setArrowSpin(holder, treeNode, false);
                }
            }

            @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
            public int getLayoutId() {
                return R.layout.item_course_note_second;
            }

            @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
            public boolean isItemType(TreeNode<CourseDirectoryTreeBean> treeNode) {
                return treeNode.getCustomerLevel() == 1;
            }
        });
        this.adapter.addItemViewDelegate(new AnonymousClass3());
        this.courseNoteRecyclerView.setAdapter(this.adapter);
        this.courseNoteRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        this.page = 1;
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

    private int selectNum(TreeNode<CourseDirectoryTreeBean> data) {
        if (data.getValue().isSelect()) {
            String count = data.getValue().getContentItem().getCount();
            i = TextUtils.isEmpty(count) ? 0 : Integer.parseInt(count);
            Log.d(TAG, "selectNum: -------" + i);
        }
        return i;
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
            objectAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryNoteFragment.5
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animation) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    imageView.setRotation(0.0f);
                    if (SkinManager.getCurrentSkinType(CourseDirectoryNoteFragment.this.getActivity()) == 1) {
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
    public void setEmptyView() {
        this.emptyView.uploadEmptyViewResUi();
        this.emptyView.setEmptyTextStr("暂无笔记  ");
        this.emptyView.setImgEmptyRes(this.resId);
        this.emptyView.setVisibility(0);
        this.courseNoteRecyclerView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setErrorView() {
        this.emptyView.setLoadFileResUi(getContext());
        this.emptyView.setVisibility(0);
        this.courseNoteRecyclerView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSelectNoteCount(List<TreeNode<CourseDirectoryTreeBean>> list) {
        List<TreeNode<CourseDirectoryTreeBean>> children;
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
            new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.courselist.fragment.v0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f12054c.lambda$updateSelectNoteCount$2();
                }
            }, 500L);
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_course_directory_note;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(com.psychiatrygarden.baseview.ViewHolder holder, View root) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.course_id = arguments.getString("course_id");
        }
        this.courseNoteRecyclerView = (RecyclerView) holder.get(R.id.courseNoteRecyclerView);
        this.refreshLayout = (SmartRefreshLayout) holder.get(R.id.courseNoteRefresh);
        this.courseNoteRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.refreshLayout.setEnableLoadMore(false);
        this.refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.w0
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f12059c.lambda$initViews$0(refreshLayout);
            }
        });
        this.drawableIdAllSelect = isNightTheme() ? R.drawable.download_new_select_night : R.drawable.download_new_select_day;
        this.drawableIdUnSelect = isNightTheme() ? R.drawable.download_new_not_select_night : R.drawable.download_new_not_select;
        this.drawableIdPartSelect = isNightTheme() ? R.drawable.download_part_select_night : R.drawable.download_part_select;
        this.resId = SkinManager.getCurrentSkinType(getContext()) == 1 ? R.drawable.ic_empty_data_note_night : R.drawable.ic_empty_data_note_day;
        CustomEmptyView customEmptyView = (CustomEmptyView) holder.get(R.id.emptyView);
        this.emptyView = customEmptyView;
        customEmptyView.setImgEmptyRes(this.resId);
        this.emptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.x0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12067c.lambda$initViews$1(view);
            }
        });
        this.emptyView.changeEmptyViewWriteBg();
        this.emptyView.setVisibility(8);
        getListData();
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        boolean z2 = false;
        if (arguments != null && arguments.getBoolean("edit_mode", false)) {
            z2 = true;
        }
        this.EDIT_MODE = z2;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        super.onEventMainThread(str);
        if (EventBusConstant.REFRESH_NOTE.equals(str)) {
            getListData();
        }
    }

    public void selectOperaAll(Boolean selectAllOrUnSelectAll) {
        for (int i2 = 0; i2 < this.list.size(); i2++) {
            List<TreeNode<CourseDirectoryTreeBean>> children = this.list.get(i2).getChildren();
            if (children != null && !children.isEmpty()) {
                for (int i3 = 0; i3 < children.size(); i3++) {
                    TreeNode<CourseDirectoryTreeBean> treeNode = children.get(i3);
                    List<TreeNode<CourseDirectoryTreeBean>> children2 = treeNode.getChildren();
                    if (children2 == null || children2.isEmpty()) {
                        treeNode.getValue().setSelect(selectAllOrUnSelectAll.booleanValue());
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

    public void setSelectNumChangeListener(SelectNumChangeListener selectNumChangeListener) {
        this.selectNumChangeListener = selectNumChangeListener;
    }
}
