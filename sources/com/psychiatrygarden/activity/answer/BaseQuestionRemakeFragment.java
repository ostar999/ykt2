package com.psychiatrygarden.activity.answer;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.Key;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.vod.common.utils.UriUtil;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.answer.BaseQuestionRemakeFragment;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewBean;
import com.psychiatrygarden.activity.online.bean.QuestionDetailBean;
import com.psychiatrygarden.activity.q2;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.bean.KnowledgeChartNodeBean;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class BaseQuestionRemakeFragment extends BaseFragment implements QuestionDataCallBack<String> {
    private static final String TAG = "KnowledgeListEditFragment";
    private TreeNodeAdapter<KnowledgeChartNodeBean> adapter;
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
    private String type;
    private String unitId;
    private List<TreeNode<KnowledgeChartNodeBean>> list = new ArrayList();
    private List<KnowledgeChartNodeBean> dataList = new ArrayList();
    private List<String> selectIds = new ArrayList();
    private int selectCount = 0;
    private int allCount = 0;
    private List<String> preSelectIds = new ArrayList();
    private List<String> chooseChapterId = new ArrayList();
    private List<String> chooseChapterTitleList = new ArrayList();
    private Map<String, List<String>> mStringListMap = new ArrayMap();
    private Map<String, List<String>> mStringListTitleMap = new ArrayMap();

    /* renamed from: com.psychiatrygarden.activity.answer.BaseQuestionRemakeFragment$1, reason: invalid class name */
    public class AnonymousClass1 extends TreeNodeDelegate<KnowledgeChartNodeBean> {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0() {
            BaseQuestionRemakeFragment.this.getQuestionBankData();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$1(boolean z2, KnowledgeChartNodeBean knowledgeChartNodeBean, TreeNode treeNode, View view) {
            String activity_id;
            if (z2) {
                if (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
                    BaseQuestionRemakeFragment.this.toastOnUiThread("没有考点");
                    return;
                }
                boolean zAllSelect = KnowledgeTreeNodeUtilKt.allSelect(treeNode);
                treeNode.setSelect(!treeNode.isSelect());
                BaseQuestionRemakeFragment.this.selectOperaAllDirectory(treeNode, this.adapter, Boolean.valueOf(!zAllSelect));
                BaseQuestionRemakeFragment baseQuestionRemakeFragment = BaseQuestionRemakeFragment.this;
                baseQuestionRemakeFragment.updateSelectNoteCount(baseQuestionRemakeFragment.list);
                return;
            }
            AjaxParams ajaxParams = new AjaxParams();
            Iterator it = BaseQuestionRemakeFragment.this.dataList.iterator();
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
            MemInterface.getInstance().getMemData(BaseQuestionRemakeFragment.this.getActivity(), ajaxParams, false, 0);
            MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.answer.u0
                @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
                public final void mUShareListener() {
                    this.f11065a.lambda$convert$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$2(boolean z2, TreeNode treeNode, View view) {
            if (!z2) {
                NewToast.showShort(BaseQuestionRemakeFragment.this.getActivity(), "该章节未解锁");
                return;
            }
            if (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
                BaseQuestionRemakeFragment.this.toastOnUiThread("没有考点");
                return;
            }
            boolean zAllSelect = KnowledgeTreeNodeUtilKt.allSelect(treeNode);
            treeNode.setSelect(!treeNode.isSelect());
            BaseQuestionRemakeFragment.this.selectOperaAllDirectory(treeNode, this.adapter, Boolean.valueOf(!zAllSelect));
            BaseQuestionRemakeFragment baseQuestionRemakeFragment = BaseQuestionRemakeFragment.this;
            baseQuestionRemakeFragment.updateSelectNoteCount(baseQuestionRemakeFragment.list);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$3() {
            BaseQuestionRemakeFragment.this.getQuestionBankData();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$4(boolean z2, KnowledgeChartNodeBean knowledgeChartNodeBean, TreeNode treeNode, View view) {
            String activity_id;
            if (z2) {
                if (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
                    BaseQuestionRemakeFragment.this.toastOnUiThread("该目录下没有考点");
                    return;
                } else {
                    this.adapter.expandOrCollapseTreeNode(treeNode);
                    return;
                }
            }
            AjaxParams ajaxParams = new AjaxParams();
            Iterator it = BaseQuestionRemakeFragment.this.dataList.iterator();
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
            MemInterface.getInstance().getMemData(BaseQuestionRemakeFragment.this.getActivity(), ajaxParams, false, 0);
            MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.answer.y0
                @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
                public final void mUShareListener() {
                    this.f11081a.lambda$convert$3();
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
            int selectImg = BaseQuestionRemakeFragment.this.getSelectImg(treeNode);
            if (selectImg == BaseQuestionRemakeFragment.this.drawableIdAllSelect) {
                value.setSelectAll(true);
                value.setChildSelect(true);
            } else if (selectImg == BaseQuestionRemakeFragment.this.drawableIdPartSelect) {
                value.setSelectAll(false);
                value.setChildSelect(true);
            } else if (selectImg == BaseQuestionRemakeFragment.this.drawableIdUnSelect) {
                value.setSelectAll(false);
                value.setChildSelect(false);
            }
            final boolean zEquals = "1".equals(value.getHas_permission());
            if (zEquals) {
                imageView.setImageDrawable(BaseQuestionRemakeFragment.this.getResources().getDrawable(selectImg));
            } else {
                imageView.setImageDrawable(BaseQuestionRemakeFragment.this.getResources().getDrawable(SkinManager.getCurrentSkinType(BaseQuestionRemakeFragment.this.getContext()) == 1 ? R.drawable.homepage_question_password_night : R.drawable.homepage_question_password));
            }
            imageView.setVisibility(0);
            Log.d(BaseQuestionRemakeFragment.TAG, "convert: 刷新一级目录： " + treeNode.getValue().getName());
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.v0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f11067c.lambda$convert$1(zEquals, value, treeNode, view);
                }
            });
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.w0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f11072c.lambda$convert$2(zEquals, treeNode, view);
                }
            });
            holder.getConvertView().setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.x0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f11076c.lambda$convert$4(zEquals, value, treeNode, view);
                }
            });
            imageView2.setVisibility(0);
            if (!treeNode.isExpand()) {
                BaseQuestionRemakeFragment.this.setArrowSpin(holder, treeNode, false);
            } else {
                BaseQuestionRemakeFragment.this.setArrowSpin(holder, treeNode, false);
                BaseQuestionRemakeFragment.this.firstExpandTree = treeNode;
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

    /* renamed from: com.psychiatrygarden.activity.answer.BaseQuestionRemakeFragment$2, reason: invalid class name */
    public class AnonymousClass2 extends TreeNodeDelegate<KnowledgeChartNodeBean> {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$convert$0(View view) {
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public void convert(ViewHolder holder, final TreeNode<KnowledgeChartNodeBean> treeNode) {
            ImageView imageView = (ImageView) holder.getView(R.id.imgArrow);
            TextView textView = (TextView) holder.getView(R.id.secondTitle);
            ImageView imageView2 = (ImageView) holder.getView(R.id.secondSelectImage);
            KnowledgeChartNodeBean value = treeNode.getValue();
            textView.setText(value.getName());
            int selectImg = BaseQuestionRemakeFragment.this.getSelectImg(treeNode);
            boolean zLevel2IsAllSelect = BaseQuestionRemakeFragment.this.level2IsAllSelect(treeNode);
            if (selectImg == BaseQuestionRemakeFragment.this.drawableIdAllSelect) {
                value.setSelectAll(true);
                value.setChildSelect(true);
            } else if (selectImg == BaseQuestionRemakeFragment.this.drawableIdPartSelect) {
                value.setSelectAll(false);
                value.setChildSelect(true);
            } else if (selectImg == BaseQuestionRemakeFragment.this.drawableIdUnSelect) {
                value.setSelectAll(false);
                value.setChildSelect(false);
            }
            value.setSelectAll(zLevel2IsAllSelect);
            if ("1".equals(value.getHas_permission())) {
                imageView2.setImageDrawable(BaseQuestionRemakeFragment.this.getResources().getDrawable(selectImg));
            } else {
                imageView2.setImageDrawable(BaseQuestionRemakeFragment.this.getResources().getDrawable(SkinManager.getCurrentSkinType(BaseQuestionRemakeFragment.this.getContext()) == 1 ? R.drawable.homepage_question_password_night : R.drawable.homepage_question_password));
            }
            imageView2.setVisibility(0);
            imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.BaseQuestionRemakeFragment.2.1
                @Override // android.view.View.OnClickListener
                public void onClick(View v2) {
                    boolean zAllSelect = KnowledgeTreeNodeUtilKt.allSelect(treeNode);
                    AnonymousClass2 anonymousClass2 = AnonymousClass2.this;
                    BaseQuestionRemakeFragment.this.selectOperaAllDirectory(treeNode, anonymousClass2.adapter, Boolean.valueOf(!zAllSelect));
                    if (treeNode.getParent() != null) {
                        AnonymousClass2.this.adapter.notifyTreeNode(treeNode.getParent());
                    }
                    BaseQuestionRemakeFragment baseQuestionRemakeFragment = BaseQuestionRemakeFragment.this;
                    baseQuestionRemakeFragment.updateSelectNoteCount(baseQuestionRemakeFragment.list);
                }
            });
            imageView.setVisibility(8);
            holder.getConvertView().setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.z0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    BaseQuestionRemakeFragment.AnonymousClass2.lambda$convert$0(view);
                }
            });
            if (!treeNode.isExpand()) {
                BaseQuestionRemakeFragment.this.setArrowSpin(holder, treeNode, false);
            } else {
                BaseQuestionRemakeFragment.this.setArrowSpin(holder, treeNode, false);
                BaseQuestionRemakeFragment.this.secondExpandTree = treeNode;
            }
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public int getLayoutId() {
            return R.layout.item_knowledge_second_question;
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public boolean isItemType(TreeNode<KnowledgeChartNodeBean> treeNode) {
            return treeNode.getCustomerLevel() == 1;
        }
    }

    public interface SelectNumChangeListener {
        void selectNum(int num);
    }

    private int getAllCountMethod(List<TreeNode<KnowledgeChartNodeBean>> list) {
        int i2 = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            if ("1".equals(list.get(i3).getValue().getHas_permission())) {
                i2++;
            }
        }
        return i2;
    }

    private void getListData() {
        getQuestionBankData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getQuestionBankData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("identity_id", this.identityId);
        ajaxParams.put("module_type", this.moduleType);
        ajaxParams.put("type", this.type);
        if ("unit".equals(this.category)) {
            ajaxParams.put(UriUtil.QUERY_CATEGORY, "unit");
        } else {
            ajaxParams.put(UriUtil.QUERY_CATEGORY, "chapter");
        }
        if (!TextUtils.isEmpty(this.unitId)) {
            ajaxParams.put("unit_id", this.unitId);
        }
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.redoOtherChapterList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.answer.BaseQuestionRemakeFragment.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                BaseQuestionRemakeFragment.this.refreshLayout.finishRefresh(false);
                BaseQuestionRemakeFragment.this.emptyView.setLoadFileResUi(BaseQuestionRemakeFragment.this.getContext());
                BaseQuestionRemakeFragment.this.emptyView.setVisibility(0);
                BaseQuestionRemakeFragment.this.mRecyclerView.setVisibility(8);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                BaseQuestionRemakeFragment.this.refreshLayout.finishRefresh(true);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (!"200".equals(jSONObject.optString("code"))) {
                        BaseQuestionRemakeFragment.this.AlertToast(jSONObject.optString("message"));
                        BaseQuestionRemakeFragment.this.emptyView.uploadEmptyViewResUi();
                        BaseQuestionRemakeFragment.this.emptyView.setVisibility(0);
                        BaseQuestionRemakeFragment.this.mRecyclerView.setVisibility(8);
                        return;
                    }
                    BaseQuestionRemakeFragment.this.dataList.clear();
                    QuestionBankNewBean questionBankNewBean = (QuestionBankNewBean) new Gson().fromJson(s2, QuestionBankNewBean.class);
                    if (questionBankNewBean.getData() == null || questionBankNewBean.getData().isEmpty()) {
                        BaseQuestionRemakeFragment.this.emptyView.uploadEmptyViewResUi();
                        BaseQuestionRemakeFragment.this.emptyView.setVisibility(0);
                        BaseQuestionRemakeFragment.this.mRecyclerView.setVisibility(8);
                        return;
                    }
                    List<QuestionBankNewBean.DataBean> data = questionBankNewBean.getData();
                    if (data == null || data.isEmpty()) {
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
                        BaseQuestionRemakeFragment.this.dataList.add(knowledgeChartNodeBean);
                        List<QuestionBankNewBean.DataBean.ChildrenBean> children = dataBean.getChildren();
                        if (children != null && !children.isEmpty()) {
                            List arrayList = (List) BaseQuestionRemakeFragment.this.mStringListMap.get(dataBean.getChapter_id());
                            List arrayList2 = (List) BaseQuestionRemakeFragment.this.mStringListTitleMap.get(dataBean.getChapter_id());
                            if (arrayList == null) {
                                arrayList = new ArrayList();
                                BaseQuestionRemakeFragment.this.mStringListMap.put(dataBean.getChapter_id(), arrayList);
                            }
                            if (arrayList2 == null) {
                                arrayList2 = new ArrayList();
                                BaseQuestionRemakeFragment.this.mStringListTitleMap.put(dataBean.getChapter_id(), arrayList2);
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
                    BaseQuestionRemakeFragment baseQuestionRemakeFragment = BaseQuestionRemakeFragment.this;
                    baseQuestionRemakeFragment.initListLayout(baseQuestionRemakeFragment.dataList);
                    BaseQuestionRemakeFragment.this.emptyView.setVisibility(8);
                    BaseQuestionRemakeFragment.this.mRecyclerView.setVisibility(0);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    BaseQuestionRemakeFragment.this.refreshLayout.finishRefresh(false);
                    BaseQuestionRemakeFragment.this.emptyView.setLoadFileResUi(BaseQuestionRemakeFragment.this.getContext());
                    BaseQuestionRemakeFragment.this.emptyView.setVisibility(0);
                    BaseQuestionRemakeFragment.this.mRecyclerView.setVisibility(8);
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
        List<TreeNode<KnowledgeChartNodeBean>> listKnowledgeListToTreeNodeDataForRemake = KnowledgeTreeNodeUtilKt.KnowledgeListToTreeNodeDataForRemake(dateBeanList, this.preSelectIds);
        this.list = listKnowledgeListToTreeNodeDataForRemake;
        this.allCount = getAllCountMethod(listKnowledgeListToTreeNodeDataForRemake);
        TreeNodeAdapter<KnowledgeChartNodeBean> treeNodeAdapter = new TreeNodeAdapter<>(getActivity(), this.list);
        this.adapter = treeNodeAdapter;
        treeNodeAdapter.addItemViewDelegate(new AnonymousClass1());
        this.adapter.addItemViewDelegate(new AnonymousClass2());
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
        } else if (data.getCustomerLevel() == 1) {
            data.getValue().setSelect(!data.getValue().isSelect());
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
            objectAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.psychiatrygarden.activity.answer.BaseQuestionRemakeFragment.4
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animation) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    imageView.setRotation(0.0f);
                    if (SkinManager.getCurrentSkinType(BaseQuestionRemakeFragment.this.getActivity()) == 1) {
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
        customDialog.setNegativeBtn(R.string.cancel, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.r0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BaseQuestionRemakeFragment.lambda$showRedoDialog$2(customDialog, view);
            }
        });
        customDialog.setPositiveBtn("确定", new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.s0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11058c.lambda$showRedoDialog$3(isWrong, strChapterId, customDialog, view);
            }
        });
        customDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSelectNoteCount(List<TreeNode<KnowledgeChartNodeBean>> list) {
        this.selectIds.clear();
        this.chooseChapterId.clear();
        this.chooseChapterTitleList.clear();
        int i2 = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            TreeNode<KnowledgeChartNodeBean> treeNode = list.get(i3);
            if (treeNode.getCustomerLevel() == 0) {
                List<TreeNode<KnowledgeChartNodeBean>> children = treeNode.getChildren();
                for (int i4 = 0; i4 < children.size(); i4++) {
                    if (children.get(i4).getValue().isSelect()) {
                        String id = children.get(i4).getValue().getId();
                        String name = children.get(i4).getValue().getName();
                        this.chooseChapterId.add(id);
                        this.chooseChapterTitleList.add(name);
                        if (!this.selectIds.contains(id)) {
                            this.selectIds.add(children.get(i4).getValue().getId());
                        }
                        i2++;
                    }
                }
            } else if (treeNode.getCustomerLevel() == 1 && treeNode.getValue().isSelect()) {
                String id2 = treeNode.getValue().getId();
                String name2 = treeNode.getValue().getName();
                this.chooseChapterId.add(id2);
                this.chooseChapterTitleList.add(name2);
                if (!this.selectIds.contains(id2)) {
                    this.selectIds.add(treeNode.getValue().getId());
                }
                i2++;
            }
        }
        this.selectCount = i2;
        if (this.selectNumChangeListener != null) {
            new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.answer.t0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f11063c.lambda$updateSelectNoteCount$4();
                }
            }, 500L);
        }
        Log.e(TAG, "updateSelectNoteCount: " + q2.a(",", this.selectIds));
    }

    public void btnCommit() {
        if (getActivity() == null || this.list == null) {
            return;
        }
        this.isChoosedCurrentChapter = false;
        String strA = q2.a(",", this.selectIds);
        this.isChoosedCurrentChapter = this.selectIds.contains(this.chapterId);
        LogUtils.d("selectIds", strA);
        if (this.selectIds.isEmpty()) {
            return;
        }
        showRedoDialog(strA, "0", "是否重做所选章节所有试题？");
    }

    public Bundle getBundle(String identityId, String moduleType, String category, String type, String unitId, String chapterId) {
        Bundle bundle = new Bundle();
        bundle.putString("identityId", identityId);
        bundle.putString("moduleType", moduleType);
        bundle.putString(UriUtil.QUERY_CATEGORY, category);
        bundle.putString("type", type);
        bundle.putString("unitId", unitId);
        bundle.putSerializable("chapterId", chapterId);
        bundle.putSerializable("baseQuestionBankRedo", Boolean.TRUE);
        return bundle;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_base_question_remake;
    }

    public int getNoteAllCount() {
        return this.allCount;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(com.psychiatrygarden.baseview.ViewHolder holder, View root) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.type = arguments.getString("type");
            this.identityId = arguments.getString("identityId");
            this.moduleType = arguments.getString("moduleType");
            this.category = arguments.getString(UriUtil.QUERY_CATEGORY);
            this.unitId = arguments.getString("unitId");
            this.chapterId = arguments.getString("chapterId");
        }
        this.mRecyclerView = (RecyclerView) holder.get(R.id.recyclerView);
        this.refreshLayout = (SmartRefreshLayout) holder.get(R.id.refresh);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.refreshLayout.setEnableLoadMore(false);
        this.refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.answer.p0
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f11050c.lambda$initViews$0(refreshLayout);
            }
        });
        this.drawableIdAllSelect = isNightTheme() ? R.drawable.download_new_select_night : R.drawable.download_new_select_day;
        this.drawableIdUnSelect = isNightTheme() ? R.drawable.download_new_not_select_night : R.drawable.download_new_not_select;
        this.drawableIdPartSelect = isNightTheme() ? R.drawable.download_part_select_night : R.drawable.download_part_select;
        int i2 = SkinManager.getCurrentSkinType(getContext()) == 1 ? R.drawable.ic_empty_data_note_night : R.drawable.ic_empty_data_note_day;
        CustomEmptyView customEmptyView = (CustomEmptyView) holder.get(R.id.emptyView);
        this.emptyView = customEmptyView;
        customEmptyView.setImgEmptyRes(i2);
        this.emptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.answer.q0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11053c.lambda$initViews$1(view);
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
            this.list.get(i2).setSelect(selectAllOrUnSelectAll.booleanValue());
            selectOperaAllDirectory(this.list.get(i2), this.adapter, selectAllOrUnSelectAll);
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
