package com.psychiatrygarden.fragmenthome.knowledge;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
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
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.bean.KnowledgeChartNodeBean;
import com.psychiatrygarden.bean.KnowledgeListType;
import com.psychiatrygarden.bean.MyCutQuestionEditEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.KnowledgeTreeNodeUtilKt;
import com.psychiatrygarden.utils.NavigationUtilKt;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CommonLoadingPop;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class KnowledgeQuestionListFragment extends BaseFragment {
    public static final String EXTRA_DOMAIN_TYPE = "domain_type";
    public static final String EXTRA_LEVEL1_ID = "level1Id";
    public static final String EXTRA_TREE_ID = "tree_id";
    public static final String SHOW_LOADING_DIALOG = "show.dialog";
    private static final String TAG = "CourseDirectoryNoteFrag";
    private TreeNodeAdapter<KnowledgeChartNodeBean> adapter;
    private RecyclerView courseNoteRecyclerView;
    private String domain_type;
    private CustomEmptyView emptyView;
    private TreeNode<KnowledgeChartNodeBean> firstExpandTree;
    private CommonLoadingPop loadingPop;
    private SmartRefreshLayout refreshLayout;
    private int resId;
    private TreeNode<KnowledgeChartNodeBean> secondExpandTree;
    private String treeId;
    private List<TreeNode<KnowledgeChartNodeBean>> list = new ArrayList();
    private HashMap<String, Integer> countMap = new HashMap<>();
    private String level1Id = "";

    /* renamed from: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeQuestionListFragment$1, reason: invalid class name */
    public class AnonymousClass1 extends TreeNodeDelegate<KnowledgeChartNodeBean> {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(TreeNode treeNode, View view) {
            if (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
                KnowledgeQuestionListFragment.this.toastOnUiThread("改目录下没有课程!");
            } else {
                this.adapter.expandOrCollapseTreeNode(treeNode);
            }
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public void convert(ViewHolder holder, final TreeNode<KnowledgeChartNodeBean> treeNode) {
            TextView textView = (TextView) holder.getView(R.id.firstTitle);
            TextView textView2 = (TextView) holder.getView(R.id.tvFirstCount);
            KnowledgeChartNodeBean value = treeNode.getValue();
            textView.setText(value.getName());
            textView2.setText(KnowledgeQuestionListFragment.this.getCountStr("" + KnowledgeQuestionListFragment.this.countMap.get(value.getId())));
            holder.getConvertView().setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.g0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f15747c.lambda$convert$0(treeNode, view);
                }
            });
            if (!treeNode.isExpand()) {
                KnowledgeQuestionListFragment.this.setArrowSpin(holder, treeNode, false);
            } else {
                KnowledgeQuestionListFragment.this.setArrowSpin(holder, treeNode, false);
                KnowledgeQuestionListFragment.this.firstExpandTree = treeNode;
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

    /* renamed from: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeQuestionListFragment$2, reason: invalid class name */
    public class AnonymousClass2 extends TreeNodeDelegate<KnowledgeChartNodeBean> {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(TreeNode treeNode, View view) {
            if (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
                KnowledgeQuestionListFragment.this.toastOnUiThread("改目录下没有考点!");
            } else {
                this.adapter.expandOrCollapseTreeNode(treeNode);
            }
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public void convert(ViewHolder holder, final TreeNode<KnowledgeChartNodeBean> treeNode) {
            TextView textView = (TextView) holder.getView(R.id.secondTitle);
            TextView textView2 = (TextView) holder.getView(R.id.tvSecondCount);
            KnowledgeChartNodeBean value = treeNode.getValue();
            textView.setText(value.getName());
            textView2.setText(KnowledgeQuestionListFragment.this.getCountStr("" + KnowledgeQuestionListFragment.this.countMap.get(value.getId())));
            holder.getConvertView().setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.h0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f15751c.lambda$convert$0(treeNode, view);
                }
            });
            if (!treeNode.isExpand()) {
                KnowledgeQuestionListFragment.this.setArrowSpin(holder, treeNode, false);
            } else {
                KnowledgeQuestionListFragment.this.secondExpandTree = treeNode;
                KnowledgeQuestionListFragment.this.setArrowSpin(holder, treeNode, false);
            }
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

    /* renamed from: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeQuestionListFragment$3, reason: invalid class name */
    public class AnonymousClass3 extends TreeNodeDelegate<KnowledgeChartNodeBean> {
        public AnonymousClass3() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(String str, String str2, String str3, View view) {
            NavigationUtilKt.gotoChartAnswerSheetActivity(KnowledgeQuestionListFragment.this.getActivity(), str, str2, KnowledgeQuestionListFragment.this.domain_type, KnowledgeQuestionListFragment.this.level1Id, str3);
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public void convert(ViewHolder holder, final TreeNode<KnowledgeChartNodeBean> treeNode) {
            int absoluteAdapterPosition = holder.getAbsoluteAdapterPosition();
            int iDip2px = DpOrPxUtils.dip2px(((BaseFragment) KnowledgeQuestionListFragment.this).mContext, 16.0f);
            Log.d(KnowledgeQuestionListFragment.TAG, "convert: 第三级数据： pos:" + absoluteAdapterPosition + "--- value: " + treeNode.getValue().getName());
            RelativeLayout relativeLayout = (RelativeLayout) holder.getView(R.id.itemInnerLayout);
            ((ImageView) holder.getView(R.id.thirdSelectImage)).setVisibility(8);
            View view = holder.getView(R.id.thirdItemLine);
            TextView textView = (TextView) holder.getView(R.id.tvThirdNum);
            TextView textView2 = (TextView) holder.getView(R.id.thirdTitle);
            KnowledgeChartNodeBean value = treeNode.getValue();
            textView2.setText(value.getName());
            textView.setText(KnowledgeQuestionListFragment.this.getCountStr(value.getQuestion_count()));
            relativeLayout.setPadding(iDip2px, 0, iDip2px, 0);
            if (KnowledgeTreeNodeUtilKt.isOnlyOneItem(treeNode)) {
                view.setVisibility(4);
                relativeLayout.setBackground(KnowledgeQuestionListFragment.this.getResources().getDrawable(R.drawable.shape_new_bg_two_color_corner12, ((BaseFragment) KnowledgeQuestionListFragment.this).mContext.getTheme()));
            } else if (KnowledgeTreeNodeUtilKt.isTopRadiusItem(treeNode)) {
                view.setVisibility(0);
                relativeLayout.setBackground(KnowledgeQuestionListFragment.this.getResources().getDrawable(R.drawable.shape_new_bg_two_color_top_corner12, ((BaseFragment) KnowledgeQuestionListFragment.this).mContext.getTheme()));
            } else if (KnowledgeTreeNodeUtilKt.isBottomRadiusItem(treeNode)) {
                view.setVisibility(4);
                relativeLayout.setBackground(KnowledgeQuestionListFragment.this.getResources().getDrawable(R.drawable.shape_new_bg_two_color_bottom_corner12, ((BaseFragment) KnowledgeQuestionListFragment.this).mContext.getTheme()));
            } else {
                view.setVisibility(0);
                relativeLayout.setBackground(KnowledgeQuestionListFragment.this.getResources().getDrawable(R.drawable.shape_project_normal_bg, ((BaseFragment) KnowledgeQuestionListFragment.this).mContext.getTheme()));
            }
            final String name = treeNode.getParent().getValue().getName();
            final String id = treeNode.getValue().getId();
            final String id2 = treeNode.getParent().getValue().getId();
            if (KnowledgeQuestionListFragment.this.getActivity() != null) {
                relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.i0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        this.f15755c.lambda$convert$0(name, id, id2, view2);
                    }
                });
            }
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

    /* renamed from: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeQuestionListFragment$6, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass6 {
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

    /* JADX INFO: Access modifiers changed from: private */
    public void dialogDismiss() {
        CommonLoadingPop commonLoadingPop = this.loadingPop;
        if (commonLoadingPop != null) {
            commonLoadingPop.dismiss();
        }
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
        switch (AnonymousClass6.$SwitchMap$com$psychiatrygarden$bean$KnowledgeListType[KnowledgeListType.INSTANCE.getKnowledgeListType(this.domain_type).ordinal()]) {
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
        showDialog();
        ajaxParams.put(EXTRA_TREE_ID, this.treeId);
        ajaxParams.put(EXTRA_DOMAIN_TYPE, this.domain_type);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.knowledgeList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeQuestionListFragment.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                KnowledgeQuestionListFragment.this.setErrorView();
                KnowledgeQuestionListFragment.this.dialogDismiss();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass4) t2);
                try {
                    KnowledgeQuestionListFragment.this.dialogDismiss();
                    KnowledgeQuestionListFragment.this.refreshLayout.finishRefresh(true);
                    JSONObject jSONObject = new JSONObject(t2);
                    if (!jSONObject.optString("code").equals("200")) {
                        KnowledgeQuestionListFragment.this.setEmptyView();
                        return;
                    }
                    JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                    if (jSONObjectOptJSONObject == null) {
                        return;
                    }
                    List list = (List) new Gson().fromJson(jSONObjectOptJSONObject.optString("chapter_list"), new TypeToken<List<KnowledgeChartNodeBean>>() { // from class: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeQuestionListFragment.4.1
                    }.getType());
                    boolean z2 = false;
                    if (list == null || list.isEmpty()) {
                        KnowledgeQuestionListFragment.this.setEmptyView();
                    } else {
                        KnowledgeQuestionListFragment.this.initListLayout(list);
                        KnowledgeQuestionListFragment.this.emptyView.setVisibility(8);
                        KnowledgeQuestionListFragment.this.courseNoteRecyclerView.setVisibility(0);
                    }
                    EventBus eventBus = EventBus.getDefault();
                    String str = KnowledgeQuestionListFragment.this.treeId;
                    if (list != null && !list.isEmpty()) {
                        z2 = true;
                    }
                    eventBus.post(new MyCutQuestionEditEvent(str, z2, true));
                } catch (Exception e2) {
                    Log.e(KnowledgeQuestionListFragment.TAG, "onSuccess: 请求目录列表异常:" + e2.getMessage());
                    KnowledgeQuestionListFragment.this.setErrorView();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initListLayout(List<KnowledgeChartNodeBean> dateBeanList) {
        List<TreeNode<KnowledgeChartNodeBean>> listKnowledgeListToTreeNodeData = KnowledgeTreeNodeUtilKt.KnowledgeListToTreeNodeData(dateBeanList);
        this.list = listKnowledgeListToTreeNodeData;
        getAllCountMethod(listKnowledgeListToTreeNodeData, this.countMap);
        TreeNodeAdapter<KnowledgeChartNodeBean> treeNodeAdapter = new TreeNodeAdapter<>(getActivity(), this.list);
        this.adapter = treeNodeAdapter;
        treeNodeAdapter.addItemViewDelegate(new AnonymousClass1());
        this.adapter.addItemViewDelegate(new AnonymousClass2());
        this.adapter.addItemViewDelegate(new AnonymousClass3());
        this.courseNoteRecyclerView.setAdapter(this.adapter);
        this.courseNoteRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
            objectAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeQuestionListFragment.5
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animation) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    imageView.setRotation(0.0f);
                    if (SkinManager.getCurrentSkinType(KnowledgeQuestionListFragment.this.getActivity()) == 1) {
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
        this.emptyView.setEmptyTextStr("暂无数据");
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

    private void showDialog() {
        if (this.loadingPop == null) {
            XPopup.Builder builder = new XPopup.Builder(getActivity());
            Boolean bool = Boolean.FALSE;
            this.loadingPop = (CommonLoadingPop) builder.dismissOnBackPressed(bool).dismissOnTouchOutside(bool).asCustom(new CommonLoadingPop(getActivity()));
        }
        this.loadingPop.show();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_knowledeg_question_list;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(com.psychiatrygarden.baseview.ViewHolder holder, View root) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.treeId = arguments.getString(EXTRA_TREE_ID);
            this.domain_type = arguments.getString(EXTRA_DOMAIN_TYPE);
            this.level1Id = arguments.getString(EXTRA_LEVEL1_ID);
        }
        this.courseNoteRecyclerView = (RecyclerView) holder.get(R.id.knowledgeListRecyclerView);
        this.refreshLayout = (SmartRefreshLayout) holder.get(R.id.knowledgeListRefresh);
        this.courseNoteRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.refreshLayout.setEnableLoadMore(false);
        this.refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.e0
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f15741c.lambda$initViews$0(refreshLayout);
            }
        });
        this.resId = SkinManager.getCurrentSkinType(getContext()) == 1 ? R.drawable.ic_empty_data_normal_night : R.drawable.ic_empty_data_normal_day;
        CustomEmptyView customEmptyView = (CustomEmptyView) holder.get(R.id.knowledgeListEmptyView);
        this.emptyView = customEmptyView;
        customEmptyView.setEmptyTextStr("暂无数据");
        this.emptyView.setImgEmptyRes(this.resId);
        this.emptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.f0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15744c.lambda$initViews$1(view);
            }
        });
        this.emptyView.changeEmptyViewWriteBg();
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    @Subscribe
    public void onEventMainThread(String str) {
        super.onEventMainThread(str);
        if (EventBusConstant.EVENT_ZHAN_TI_MOVE_UPDATE.equals(str)) {
            getListData();
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getListData();
    }
}
