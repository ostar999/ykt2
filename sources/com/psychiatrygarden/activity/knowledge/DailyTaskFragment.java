package com.psychiatrygarden.activity.knowledge;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.hutool.core.text.StrPool;
import com.psychiatrygarden.activity.online.ChartAnswerSheetActivity;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.bean.EventBusMessage;
import com.psychiatrygarden.bean.KnowledgeChartNodeBean;
import com.psychiatrygarden.bean.SelectIdentityBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.psychiatrygarden.widget.treenode.TreeNode;
import com.psychiatrygarden.widget.treenode.TreeNodeAdapter;
import com.psychiatrygarden.widget.treenode.TreeNodeDelegate;
import com.psychiatrygarden.widget.treenode.ViewHolder;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.yikaobang.yixue.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import kotlin.Pair;
import org.greenrobot.eventbus.Subscribe;

/* loaded from: classes5.dex */
public class DailyTaskFragment extends BaseFragment {
    private static final String TAG = "dailyTask";
    private TreeNodeAdapter<KnowledgeChartNodeBeanTreeNode> adapter;
    private CustomEmptyView emptyView;
    private List<TreeNode<KnowledgeChartNodeBeanTreeNode>> list;
    private RecyclerView recyclerView;
    private SelectIdentityBean.DataBean child = null;
    private String day = "";
    private String continueId = "";
    private List<KnowledgeChartNodeBean> mDataList = new ArrayList();
    private boolean isAtTop = false;
    private String lastNodeId = "";
    private String lastKnowledgeId = "";
    boolean isSlowTop = false;
    boolean isLoad = false;

    private void dailyTaskUpdate(String jieId, String finishCount, String rate, boolean continueDo) {
        for (int i2 = 0; i2 < this.list.size(); i2++) {
            List<KnowledgeChartNodeBean> children = this.list.get(i2).getValue().getData().getChildren();
            for (int i3 = 0; i3 < children.size(); i3++) {
                KnowledgeChartNodeBean knowledgeChartNodeBean = children.get(i3);
                if (jieId.equals(knowledgeChartNodeBean.getId())) {
                    knowledgeChartNodeBean.setRight_rate(rate);
                    knowledgeChartNodeBean.setUser_answer_total(finishCount);
                    knowledgeChartNodeBean.setContinueDo(continueDo);
                }
            }
        }
        this.adapter.refreshTreeNode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Pair<List<String>, List<String>> initAllNodeId(String clickId) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (this.mDataList != null) {
            boolean z2 = false;
            for (int i2 = 0; i2 < this.mDataList.size(); i2++) {
                KnowledgeChartNodeBean knowledgeChartNodeBean = this.mDataList.get(i2);
                for (int i3 = 0; i3 < knowledgeChartNodeBean.getChildren().size(); i3++) {
                    KnowledgeChartNodeBean knowledgeChartNodeBean2 = knowledgeChartNodeBean.getChildren().get(i3);
                    if (knowledgeChartNodeBean2.getId().equals(clickId)) {
                        z2 = true;
                    }
                    if (z2 && !TextUtils.equals(knowledgeChartNodeBean2.getId(), clickId)) {
                        arrayList.add(knowledgeChartNodeBean2.getId());
                        arrayList2.add(knowledgeChartNodeBean2.getName());
                    }
                }
            }
        }
        return new Pair<>(arrayList, arrayList2);
    }

    private void initLastDoId() {
        String strConfig = SharePreferencesUtils.readStrConfig("LAST_DO_NODE_KNOWLEDGE_ID_" + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext), this.mContext, "");
        if (strConfig == null || !strConfig.contains(StrPool.UNDERLINE)) {
            return;
        }
        this.lastKnowledgeId = strConfig.split(StrPool.UNDERLINE)[1];
        this.lastNodeId = strConfig.split(StrPool.UNDERLINE)[0];
        Log.d(TAG, "initLayout:  -- lastNodeId: " + this.lastNodeId + "--- lastKnowledgeId:" + this.lastKnowledgeId);
    }

    private void initListLayout(List<KnowledgeChartNodeBean> data) {
        final int i2;
        final int i3;
        initLastDoId();
        this.list = TreeNodeDailyTaskUtilKt.dailyTaskDataItemToTreeNodeData(data, this.lastNodeId, this.lastKnowledgeId);
        if (SkinManager.getCurrentSkinType(this.mContext) == 1) {
            i2 = R.drawable.ic_kp_down_night;
            i3 = R.drawable.ic_kp_up_night;
        } else {
            i2 = R.drawable.ic_kp_down_day;
            i3 = R.drawable.ic_kp_up_day;
        }
        TreeNodeAdapter<KnowledgeChartNodeBeanTreeNode> treeNodeAdapter = new TreeNodeAdapter<>(getActivity(), this.list);
        this.adapter = treeNodeAdapter;
        treeNodeAdapter.addItemViewDelegate(new TreeNodeDelegate<KnowledgeChartNodeBeanTreeNode>() { // from class: com.psychiatrygarden.activity.knowledge.DailyTaskFragment.1
            @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
            public void convert(ViewHolder holder, final TreeNode<KnowledgeChartNodeBeanTreeNode> treeNode) {
                KnowledgeChartNodeBean data2 = treeNode.getValue().getData();
                LinearLayout linearLayout = (LinearLayout) holder.getView(R.id.layoutRoot);
                View view = holder.getView(R.id.viewGap);
                TextView textView = (TextView) holder.getView(R.id.tv_title);
                TextView textView2 = (TextView) holder.getView(R.id.tv_continue_do);
                ImageView imageView = (ImageView) holder.getView(R.id.iv_exp_col);
                View view2 = holder.getView(R.id.line);
                textView.setText(data2.getName());
                if (treeNode.isExpand()) {
                    textView2.setVisibility(8);
                } else {
                    textView2.setVisibility(data2.isContinueDo() ? 0 : 8);
                }
                holder.getConvertView().setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.knowledge.DailyTaskFragment.1.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v2) {
                        if (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
                            DailyTaskFragment.this.toastOnUiThread("暂无子章节");
                        } else {
                            AnonymousClass1.this.adapter.expandOrCollapseTreeNode(treeNode);
                        }
                    }
                });
                if (treeNode.isExpand()) {
                    view2.setVisibility(0);
                    imageView.setImageResource(i3);
                    linearLayout.setBackgroundResource(R.drawable.shape_new_bg_two_color_corners_8_top);
                    view.setVisibility(8);
                    return;
                }
                imageView.setImageResource(i2);
                view2.setVisibility(8);
                linearLayout.setBackgroundResource(R.drawable.bg_item_home_chart);
                view.setVisibility(0);
            }

            @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
            public int getLayoutId() {
                return R.layout.item_home_chart_new;
            }

            @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
            public boolean isItemType(TreeNode<KnowledgeChartNodeBeanTreeNode> treeNode) {
                return treeNode.getCustomerLevel() == 0;
            }
        });
        this.adapter.addItemViewDelegate(new TreeNodeDelegate<KnowledgeChartNodeBeanTreeNode>() { // from class: com.psychiatrygarden.activity.knowledge.DailyTaskFragment.2
            @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
            public void convert(ViewHolder holder, final TreeNode<KnowledgeChartNodeBeanTreeNode> treeNode) {
                final KnowledgeChartNodeBean data2 = treeNode.getValue().getData();
                LinearLayout linearLayout = (LinearLayout) holder.getView(R.id.layoutRoot);
                View view = holder.getView(R.id.viewGap);
                TextView textView = (TextView) holder.getView(R.id.tv_title);
                TextView textView2 = (TextView) holder.getView(R.id.tv_continue_do);
                TextView textView3 = (TextView) holder.getView(R.id.tv_count);
                TextView textView4 = (TextView) holder.getView(R.id.tv_percent);
                TextView textView5 = (TextView) holder.getView(R.id.tv_knowledge_point_count);
                View view2 = holder.getView(R.id.line);
                textView.setText(data2.getName());
                textView3.setText(data2.getUser_answer_total() + "/" + data2.getQuestion_total());
                textView5.setText("去做题");
                textView4.setText("正确率" + data2.getRight_rate());
                holder.getConvertView().setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.knowledge.DailyTaskFragment.2.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v2) {
                        AnonymousClass2.this.adapter.expandOrCollapseTreeNode(treeNode);
                    }
                });
                textView2.setVisibility(data2.isContinueDo() ? 0 : 8);
                if (TreeNodeDailyTaskUtilKt.isOnlyOneItem(treeNode) || TreeNodeDailyTaskUtilKt.isBottomRadiusItem(treeNode)) {
                    linearLayout.setBackgroundResource(R.drawable.shape_new_bg_two_color_corner_8_bottom);
                } else {
                    linearLayout.setBackgroundResource(R.drawable.shape_new_bg_two_color);
                }
                boolean zIsLastItem = TreeNodeDailyTaskUtilKt.isLastItem(treeNode);
                view.setVisibility(zIsLastItem ? 0 : 8);
                view2.setVisibility(zIsLastItem ? 8 : 0);
                holder.getConvertView().setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.knowledge.DailyTaskFragment.2.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view3) {
                        Pair pairInitAllNodeId = DailyTaskFragment.this.initAllNodeId(data2.getId());
                        Intent intent = new Intent(((BaseFragment) DailyTaskFragment.this).mContext, (Class<?>) ChartAnswerSheetActivity.class);
                        intent.putExtra("node_id", data2.getId());
                        intent.putExtra("node_parent_id", ((KnowledgeChartNodeBeanTreeNode) treeNode.getParent().getValue()).getData().getId());
                        intent.putExtra(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, data2.getActivity_id());
                        intent.putExtra("question_bank_id", DailyTaskFragment.this.child.getQuestion_bank_id());
                        intent.putExtra("identity_id", DailyTaskFragment.this.child.getId());
                        intent.putExtra("map_node", false);
                        intent.putExtra("isKnowledge", false);
                        intent.putExtra("isDailyTaskPage", true);
                        intent.putExtra("title", data2.getName());
                        intent.putExtra("node_title", data2.getName());
                        intent.putExtra("desc", TextUtils.isEmpty(data2.getDescribe()) ? "" : data2.getDescribe());
                        intent.putExtra("day", DailyTaskFragment.this.day);
                        intent.putExtra("nodeIdList", (Serializable) pairInitAllNodeId.getFirst());
                        intent.putExtra("nodeIdTitleList", (Serializable) pairInitAllNodeId.getSecond());
                        DailyTaskFragment.this.startActivity(intent);
                    }
                });
            }

            @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
            public int getLayoutId() {
                return R.layout.item_home_chart_child_new;
            }

            @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
            public boolean isItemType(TreeNode<KnowledgeChartNodeBeanTreeNode> treeNode) {
                return treeNode.getCustomerLevel() == 1;
            }
        });
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initParams() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            List<KnowledgeChartNodeBean> list = (List) arguments.getSerializable("dailyTaskKnowledgeList");
            this.mDataList = list;
            initListLayout(list);
            this.child = (SelectIdentityBean.DataBean) arguments.getSerializable("child");
            this.day = arguments.getString("day", "");
        }
    }

    private void updateKnowledgeIdContinueDo(String knowledgeId) {
        for (int i2 = 0; i2 < this.list.size(); i2++) {
            KnowledgeChartNodeBean data = this.list.get(i2).getValue().getData();
            if (data.getId().equals(knowledgeId)) {
                data.setContinueDo(true);
            } else {
                data.setContinueDo(false);
            }
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_daily_task;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(com.psychiatrygarden.baseview.ViewHolder holder, View root) {
        this.recyclerView = (RecyclerView) holder.get(R.id.recyclerView);
        this.emptyView = (CustomEmptyView) holder.get(R.id.emptyView);
    }

    @Subscribe
    public void onEventMainThread(EventBusMessage<String> msg) {
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        super.onEventMainThread(str);
        if (EventBusConstant.EVENT_QUESTION_UPDATE_DAILY_TASK_LIST.equals(str)) {
            List<KnowledgeChartNodeBean> fragmentData = ((DailyTasksActivity) getActivity()).getFragmentData(this.child.getId());
            initLastDoId();
            updateKnowledgeIdContinueDo(this.lastNodeId);
            for (int i2 = 0; i2 < fragmentData.size(); i2++) {
                List<KnowledgeChartNodeBean> children = fragmentData.get(i2).getChildren();
                if (children != null && !children.isEmpty()) {
                    for (int i3 = 0; i3 < children.size(); i3++) {
                        KnowledgeChartNodeBean knowledgeChartNodeBean = children.get(i3);
                        dailyTaskUpdate(knowledgeChartNodeBean.getId(), knowledgeChartNodeBean.getUser_answer_total(), knowledgeChartNodeBean.getRight_rate(), knowledgeChartNodeBean.getId().equals(this.lastKnowledgeId));
                    }
                }
            }
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initParams();
    }
}
