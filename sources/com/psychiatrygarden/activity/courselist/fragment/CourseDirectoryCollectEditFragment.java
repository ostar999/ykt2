package com.psychiatrygarden.activity.courselist.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.fastjson.JSON;
import com.easefun.polyv.livecommon.ui.widget.expandmenu.utils.DpOrPxUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.activity.courselist.course.CourseDirectoryActivity;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.bean.CourseDirectoryContentItem;
import com.psychiatrygarden.bean.CourseDirectoryItemData;
import com.psychiatrygarden.bean.CourseDirectoryTreeBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.CollectEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.NewToast;
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
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CourseDirectoryCollectEditFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "CourseDirectoryCollectEditFragment";
    private TreeNodeAdapter<CourseDirectoryTreeBean> adapter;
    private OnClickBack clickBack;
    private TextView collectSubmit;
    private RecyclerView courseCollectRecyclerView;
    private String course_id;
    private String course_type;
    private int drawableIdAllSelect;
    private int drawableIdPartSelect;
    private int drawableIdUnSelect;
    private CustomEmptyView emptyView;
    private SmartRefreshLayout refreshLayout;
    private TextView tvAllSelect;
    private TextView tvTitle;
    private List<TreeNode<CourseDirectoryTreeBean>> list = new ArrayList();
    private final HashMap<String, List<String>> newChapterParams = new HashMap<>();
    private int selectCollectCount = 0;
    private int collectAllCount = 0;
    private final String ALL_SELECT = "全选";

    /* renamed from: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryCollectEditFragment$1, reason: invalid class name */
    public class AnonymousClass1 extends TreeNodeDelegate<CourseDirectoryTreeBean> {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(TreeNode treeNode, View view) {
            if (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
                CourseDirectoryCollectEditFragment.this.toastOnUiThread("没有子课程");
                return;
            }
            CourseDirectoryCollectEditFragment.this.selectOperaAllDirectory(treeNode, this.adapter, Boolean.valueOf(!TreeNodeUtilKt.allSelect(treeNode)));
            CourseDirectoryCollectEditFragment courseDirectoryCollectEditFragment = CourseDirectoryCollectEditFragment.this;
            courseDirectoryCollectEditFragment.updateSelectCount(courseDirectoryCollectEditFragment.list);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$1(TreeNode treeNode, View view) {
            if (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
                CourseDirectoryCollectEditFragment.this.toastOnUiThread("该目录下没有收藏");
            } else {
                this.adapter.expandOrCollapseTreeNode(treeNode);
            }
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public void convert(ViewHolder holder, final TreeNode<CourseDirectoryTreeBean> treeNode) {
            ImageView imageView = (ImageView) holder.getView(R.id.collectFirstImage);
            ImageView imageView2 = (ImageView) holder.getView(R.id.imgArrow);
            ((TextView) holder.getView(R.id.collectFirstTitle)).setText(treeNode.getValue().getItem().getTitle());
            imageView.setImageResource(CourseDirectoryCollectEditFragment.this.getSelectImg(treeNode));
            imageView.setVisibility(0);
            Log.d(CourseDirectoryCollectEditFragment.TAG, "convert: 刷新一级目录： " + treeNode.getValue().getTitle());
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.o
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f11998c.lambda$convert$0(treeNode, view);
                }
            });
            holder.getConvertView().setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.p
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f12009c.lambda$convert$1(treeNode, view);
                }
            });
            imageView2.setVisibility(0);
            if (treeNode.isExpand()) {
                CourseDirectoryCollectEditFragment.this.setArrowSpin(holder, treeNode);
            } else {
                CourseDirectoryCollectEditFragment.this.setArrowSpin(holder, treeNode);
            }
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public int getLayoutId() {
            return R.layout.item_course_collect_edit_first;
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public boolean isItemType(TreeNode<CourseDirectoryTreeBean> treeNode) {
            return treeNode.getCustomerLevel() == 0;
        }
    }

    /* renamed from: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryCollectEditFragment$2, reason: invalid class name */
    public class AnonymousClass2 extends TreeNodeDelegate<CourseDirectoryTreeBean> {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(TreeNode treeNode, View view) {
            if (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
                CourseDirectoryCollectEditFragment.this.toastOnUiThread("没有子课程");
                return;
            }
            CourseDirectoryCollectEditFragment.this.selectOperaAllDirectory(treeNode, this.adapter, Boolean.valueOf(!TreeNodeUtilKt.allSelect(treeNode)));
            CourseDirectoryCollectEditFragment courseDirectoryCollectEditFragment = CourseDirectoryCollectEditFragment.this;
            courseDirectoryCollectEditFragment.updateSelectCount(courseDirectoryCollectEditFragment.list);
            if (treeNode.getParent() != null) {
                this.adapter.notifyTreeNode(treeNode.getParent());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$1(TreeNode treeNode, View view) {
            if (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
                CourseDirectoryCollectEditFragment.this.toastOnUiThread("该目录下没有收藏");
            } else {
                this.adapter.expandOrCollapseTreeNode(treeNode);
            }
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public void convert(ViewHolder holder, final TreeNode<CourseDirectoryTreeBean> treeNode) {
            ImageView imageView = (ImageView) holder.getView(R.id.imgArrow);
            TextView textView = (TextView) holder.getView(R.id.itemCollectSecondTitle);
            ImageView imageView2 = (ImageView) holder.getView(R.id.collectSecondSelectImage);
            textView.setText(treeNode.getValue().getItem().getTitle());
            imageView2.setImageResource(CourseDirectoryCollectEditFragment.this.getSelectImg(treeNode));
            imageView2.setVisibility(0);
            imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.q
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f12015c.lambda$convert$0(treeNode, view);
                }
            });
            imageView.setVisibility(0);
            holder.getConvertView().setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.r
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f12025c.lambda$convert$1(treeNode, view);
                }
            });
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public int getLayoutId() {
            return R.layout.item_course_collect_edit_second;
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public boolean isItemType(TreeNode<CourseDirectoryTreeBean> treeNode) {
            return treeNode.getCustomerLevel() == 1;
        }
    }

    /* renamed from: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryCollectEditFragment$3, reason: invalid class name */
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
            CourseDirectoryCollectEditFragment courseDirectoryCollectEditFragment = CourseDirectoryCollectEditFragment.this;
            courseDirectoryCollectEditFragment.updateSelectCount(courseDirectoryCollectEditFragment.list);
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public void convert(ViewHolder holder, final TreeNode<CourseDirectoryTreeBean> treeNode) {
            int absoluteAdapterPosition = holder.getAbsoluteAdapterPosition();
            int iDip2px = DpOrPxUtils.dip2px(((BaseFragment) CourseDirectoryCollectEditFragment.this).mContext, 12.0f);
            Log.d(CourseDirectoryCollectEditFragment.TAG, "convert: 第三级数据： pos:" + absoluteAdapterPosition + "--- value: " + treeNode.getValue().getTitle());
            RelativeLayout relativeLayout = (RelativeLayout) holder.getView(R.id.itemInnerLayout);
            ImageView imageView = (ImageView) holder.getView(R.id.collectThirdSelectImage);
            View view = holder.getView(R.id.thirdCollectItemLine);
            ((TextView) holder.getView(R.id.collectThirdTitle)).setText(treeNode.getValue().getContentItem().getTitle());
            if (TreeNodeUtilKt.haveSecondPresent(treeNode)) {
                relativeLayout.setPadding(iDip2px, 0, iDip2px, 0);
                if (TreeNodeUtilKt.isOnlyOneItem(treeNode)) {
                    view.setVisibility(4);
                    relativeLayout.setBackground(CourseDirectoryCollectEditFragment.this.getResources().getDrawable(R.drawable.shape_new_bg_two_color_corner12, ((BaseFragment) CourseDirectoryCollectEditFragment.this).mContext.getTheme()));
                } else if (TreeNodeUtilKt.isTopRadiusItem(treeNode)) {
                    view.setVisibility(0);
                    relativeLayout.setBackground(CourseDirectoryCollectEditFragment.this.getResources().getDrawable(R.drawable.shape_new_bg_two_color_top_corner12, ((BaseFragment) CourseDirectoryCollectEditFragment.this).mContext.getTheme()));
                } else if (TreeNodeUtilKt.isBottomRadiusItem(treeNode)) {
                    view.setVisibility(4);
                    relativeLayout.setBackground(CourseDirectoryCollectEditFragment.this.getResources().getDrawable(R.drawable.shape_new_bg_two_color_bottom_corner12, ((BaseFragment) CourseDirectoryCollectEditFragment.this).mContext.getTheme()));
                } else {
                    view.setVisibility(0);
                    relativeLayout.setBackground(CourseDirectoryCollectEditFragment.this.getResources().getDrawable(R.drawable.shape_project_normal_bg, ((BaseFragment) CourseDirectoryCollectEditFragment.this).mContext.getTheme()));
                }
            } else {
                relativeLayout.setBackground(null);
                relativeLayout.setPadding(0, 0, 0, 0);
            }
            imageView.setImageResource(CourseDirectoryCollectEditFragment.this.getSelectImg(treeNode));
            imageView.setVisibility(0);
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.s
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f12033c.lambda$convert$0(treeNode, view2);
                }
            });
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public int getLayoutId() {
            return R.layout.item_course_collect_edit_third;
        }

        @Override // com.psychiatrygarden.widget.treenode.TreeNodeDelegate
        public boolean isItemType(TreeNode<CourseDirectoryTreeBean> treeNode) {
            return treeNode.getCustomerLevel() == 2;
        }
    }

    public interface OnClickBack {
        void clickBack();
    }

    private int allNum(TreeNode<CourseDirectoryTreeBean> data) {
        return 1;
    }

    private void cancelCollect(Context context, final String courseId) {
        if (!CommonUtil.isNetworkConnected(context)) {
            NewToast.showShort(context, "请检查网络连接");
            return;
        }
        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<String, List<String>>> it = this.newChapterParams.entrySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            List<String> value = it.next().getValue();
            if (value != null) {
                for (int i2 = 0; i2 < value.size(); i2++) {
                    sb.append(value.get(i2));
                    sb.append(",");
                }
            }
        }
        String string = sb.toString();
        if (string.endsWith(",")) {
            string = string.substring(0, string.length() - 1);
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("video_id", string);
        ajaxParams.put("course_id", courseId);
        ajaxParams.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, context, "0"));
        ajaxParams.put("type", "2");
        YJYHttpUtils.post(context, NetworkRequestsURL.courseVideoCollect, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryCollectEditFragment.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass5) s2);
                try {
                    if (TextUtils.equals("200", new JSONObject(s2).optString("code"))) {
                        NewToast.showShort(((BaseFragment) CourseDirectoryCollectEditFragment.this).mContext, "取消收藏");
                        EventBus.getDefault().post(new CollectEvent(courseId, false));
                        CourseDirectoryCollectEditFragment.this.getListData();
                    }
                } catch (Exception e2) {
                    System.out.println("ErrorTag:" + e2.getMessage());
                }
            }
        });
    }

    private int getAllCollectCount(List<TreeNode<CourseDirectoryTreeBean>> list) {
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

    /* JADX INFO: Access modifiers changed from: private */
    public void getListData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("course_id", this.course_id);
        ajaxParams.put("type", this.course_type);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.courseDirectoryCollect, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryCollectEditFragment.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                CourseDirectoryCollectEditFragment.this.emptyView.setLoadFileResUi(CourseDirectoryCollectEditFragment.this.getContext());
                CourseDirectoryCollectEditFragment.this.emptyView.setVisibility(0);
                CourseDirectoryCollectEditFragment.this.courseCollectRecyclerView.setVisibility(8);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass4) t2);
                try {
                    CourseDirectoryCollectEditFragment.this.refreshLayout.finishRefresh(true);
                    JSONObject jSONObject = new JSONObject(t2);
                    if (!jSONObject.optString("code").equals("200")) {
                        CourseDirectoryCollectEditFragment.this.emptyView.uploadEmptyViewResUi();
                        CourseDirectoryCollectEditFragment.this.emptyView.setVisibility(0);
                        CourseDirectoryCollectEditFragment.this.courseCollectRecyclerView.setVisibility(8);
                        return;
                    }
                    List list = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<CourseDirectoryItemData>>() { // from class: com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryCollectEditFragment.4.1
                    }.getType());
                    if (list != null && !list.isEmpty()) {
                        CourseDirectoryCollectEditFragment.this.initListLayout(list);
                        CourseDirectoryCollectEditFragment.this.emptyView.setVisibility(8);
                        CourseDirectoryCollectEditFragment.this.courseCollectRecyclerView.setVisibility(0);
                        return;
                    }
                    if (CourseDirectoryCollectEditFragment.this.clickBack != null) {
                        CourseDirectoryCollectEditFragment.this.clickBack.clickBack();
                    }
                } catch (Exception e2) {
                    Log.e(CourseDirectoryCollectEditFragment.TAG, "onSuccess: 请求目录列表异常:" + e2.getMessage());
                    CourseDirectoryCollectEditFragment.this.emptyView.setLoadFileResUi(CourseDirectoryCollectEditFragment.this.getContext());
                    CourseDirectoryCollectEditFragment.this.emptyView.setVisibility(0);
                    CourseDirectoryCollectEditFragment.this.courseCollectRecyclerView.setVisibility(8);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getSelectImg(TreeNode<CourseDirectoryTreeBean> treeNode) {
        return (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) ? treeNode.getValue().isSelect() ? this.drawableIdAllSelect : this.drawableIdUnSelect : TreeNodeUtilKt.allSelect(treeNode) ? this.drawableIdAllSelect : TreeNodeUtilKt.noOneSelect(treeNode) ? this.drawableIdUnSelect : this.drawableIdPartSelect;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initListLayout(List<CourseDirectoryItemData> dateBeanList) {
        List<TreeNode<CourseDirectoryTreeBean>> listCourseDirectoryItemToTreeNodeData = TreeNodeUtilKt.courseDirectoryItemToTreeNodeData(dateBeanList);
        this.list = listCourseDirectoryItemToTreeNodeData;
        this.collectAllCount = getAllCollectCount(listCourseDirectoryItemToTreeNodeData);
        TreeNodeAdapter<CourseDirectoryTreeBean> treeNodeAdapter = new TreeNodeAdapter<>(getActivity(), this.list);
        this.adapter = treeNodeAdapter;
        treeNodeAdapter.addItemViewDelegate(new AnonymousClass1());
        this.adapter.addItemViewDelegate(new AnonymousClass2());
        this.adapter.addItemViewDelegate(new AnonymousClass3());
        this.courseCollectRecyclerView.setAdapter(this.adapter);
        this.courseCollectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.selectCollectCount = 0;
        selectNumChangeUI(0);
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
    public /* synthetic */ void lambda$updateSelectCount$2() {
        selectNumChangeUI(this.selectCollectCount);
    }

    private void selectAll() {
        selectOperaAll(Boolean.valueOf("全选".equals(this.tvAllSelect.getText().toString())));
    }

    private int selectNum(TreeNode<CourseDirectoryTreeBean> data) throws NumberFormatException {
        int i2 = 0;
        if (data.getValue().isSelect()) {
            CourseDirectoryContentItem contentItem = data.getValue().getContentItem();
            if (!TextUtils.isEmpty("1")) {
                int i3 = Integer.parseInt("1");
                String chapter_id = contentItem.getChapter_id();
                if (TextUtils.isEmpty(chapter_id)) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(contentItem.getVideo_id());
                    this.newChapterParams.put(contentItem.getObj_id(), arrayList);
                } else if (this.newChapterParams.get(chapter_id) == null) {
                    ArrayList arrayList2 = new ArrayList();
                    arrayList2.add(contentItem.getVideo_id());
                    this.newChapterParams.put(chapter_id, arrayList2);
                } else {
                    List<String> list = this.newChapterParams.get(chapter_id);
                    if (list != null) {
                        list.add(contentItem.getVideo_id());
                        this.newChapterParams.put(chapter_id, list);
                    }
                }
                i2 = i3;
            }
            Log.d(TAG, "selectNum: -------" + i2);
        }
        return i2;
    }

    private void selectNumChangeUI(int num) {
        this.tvAllSelect.setText(num < getCollectAllCount() ? "全选" : "取消全选");
        this.tvTitle.setText("已选择 " + num + " 条收藏");
        this.collectSubmit.setEnabled(num > 0);
    }

    private void selectOperaAll(Boolean selectAllOrUnSelectAll) {
        for (int i2 = 0; i2 < this.list.size(); i2++) {
            selectOperaAllDirectory(this.list.get(i2), this.adapter, selectAllOrUnSelectAll);
        }
        if (!selectAllOrUnSelectAll.booleanValue()) {
            this.newChapterParams.clear();
        }
        updateSelectCount(this.list);
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
    public void setArrowSpin(ViewHolder helper, TreeNode<CourseDirectoryTreeBean> data) {
        ImageView imageView = (ImageView) helper.getView(R.id.imgArrow);
        if (data.isExpand()) {
            Log.d(TAG, "setArrowSpin: --- 展开 ");
            if (SkinManager.getCurrentSkinType(getActivity()) == 1) {
                imageView.setImageResource(R.drawable.icon_top_arrow_night);
                return;
            } else {
                imageView.setImageResource(R.drawable.icon_top_arrow_day);
                return;
            }
        }
        Log.d(TAG, "setArrowSpin: --- 关闭 ");
        if (SkinManager.getCurrentSkinType(getActivity()) == 1) {
            imageView.setImageResource(R.drawable.icon_bottom_arrow_night);
        } else {
            imageView.setImageResource(R.drawable.icon_bottom_arrow_day);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSelectCount(List<TreeNode<CourseDirectoryTreeBean>> list) {
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
        this.selectCollectCount = iSelectNum;
        new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.courselist.fragment.n
            @Override // java.lang.Runnable
            public final void run() {
                this.f11992c.lambda$updateSelectCount$2();
            }
        }, 500L);
        Log.e(TAG, "updateSelectNoteCount: " + JSON.toJSONString(this.newChapterParams));
    }

    public int getCollectAllCount() {
        return this.collectAllCount;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_course_directory_collect_edit;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(com.psychiatrygarden.baseview.ViewHolder holder, View root) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.course_id = arguments.getString("course_id");
            this.course_type = arguments.getString(CourseDirectoryActivity.EXTRA_DATA_COURSE_TYPE);
        }
        ImageView imageView = (ImageView) holder.get(R.id.iv_back);
        this.tvTitle = (TextView) holder.get(R.id.tv_title);
        this.tvAllSelect = (TextView) holder.get(R.id.tv_all_select);
        TextView textView = (TextView) holder.get(R.id.collectSubmit);
        this.collectSubmit = textView;
        textView.setOnClickListener(this);
        this.courseCollectRecyclerView = (RecyclerView) holder.get(R.id.courseCollectRecyclerView);
        this.refreshLayout = (SmartRefreshLayout) holder.get(R.id.courseCollectRefresh);
        this.courseCollectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.refreshLayout.setEnableLoadMore(false);
        this.refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.l
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f11980c.lambda$initViews$0(refreshLayout);
            }
        });
        this.drawableIdAllSelect = isNightTheme() ? R.drawable.download_new_select_night : R.drawable.download_new_select_day;
        this.drawableIdUnSelect = isNightTheme() ? R.drawable.download_new_not_select_night : R.drawable.download_new_not_select;
        this.drawableIdPartSelect = isNightTheme() ? R.drawable.download_part_select_night : R.drawable.download_part_select;
        int i2 = SkinManager.getCurrentSkinType(getContext()) == 1 ? R.drawable.ic_empty_data_note_night : R.drawable.ic_empty_data_note_day;
        CustomEmptyView customEmptyView = (CustomEmptyView) holder.get(R.id.emptyView);
        this.emptyView = customEmptyView;
        customEmptyView.setImgEmptyRes(i2);
        this.emptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.m
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11986c.lambda$initViews$1(view);
            }
        });
        this.emptyView.changeEmptyViewWriteBg();
        this.emptyView.setVisibility(8);
        getListData();
        imageView.setOnClickListener(this);
        this.tvAllSelect.setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        int id = v2.getId();
        if (id == R.id.collectSubmit) {
            cancelCollect(getContext(), this.course_id);
            return;
        }
        if (id != R.id.iv_back) {
            if (id != R.id.tv_all_select) {
                return;
            }
            selectAll();
        } else {
            OnClickBack onClickBack = this.clickBack;
            if (onClickBack != null) {
                onClickBack.clickBack();
            }
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setClickBack(OnClickBack clickBack) {
        this.clickBack = clickBack;
    }
}
