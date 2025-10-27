package com.psychiatrygarden.activity.mine.myfile;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.material.InformationPreviewAct;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewBean;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.bean.MaterialListBean;
import com.psychiatrygarden.bean.MyCollectionBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.ComputerNextDialog;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class MyFileAct extends BaseActivity implements OnRefreshListener {
    private View addFooterView;
    private CustomEmptyView emptyView;
    private boolean isCanLoadNextPage;
    private MyFileAdp mAdapter;
    private TextView mBtnDel;
    private LinearLayout mLyBottom;
    private LinearLayout mLyDel;
    private RelativeLayout mLyLoading;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefresh;
    private TextView mTvNoData;
    private int page = 1;
    public List<QuestionBankNewBean.DataBean> dataList = new ArrayList();
    private boolean isNoMoreData = false;
    private boolean isEdit = false;
    private boolean selectAll = false;
    private List<String> selectFileIds = new ArrayList();

    public static /* synthetic */ int access$412(MyFileAct myFileAct, int i2) {
        int i3 = myFileAct.page + i2;
        myFileAct.page = i3;
        return i3;
    }

    private void changeDelButtonBgBySelectNum(boolean isSelect) {
        TypedArray typedArrayObtainStyledAttributes = getTheme().obtainStyledAttributes(new int[]{R.attr.red_round_coner_color_F95843_radius_12, R.attr.downloadBtn_no_select_bg});
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(0);
        Drawable drawable2 = typedArrayObtainStyledAttributes.getDrawable(1);
        if (isSelect) {
            this.mBtnDel.setBackground(drawable);
            this.mBtnDel.setTextColor(getColor(R.color.white));
        } else {
            this.mBtnDel.setBackground(drawable2);
            this.mBtnDel.setTextColor(getColor(SkinManager.getCurrentSkinType(this) == 1 ? R.color.forth_txt_color_night : R.color.forth_txt_color));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void delCollection(String ids, List<Integer> pos) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("ids", ids);
        YJYHttpUtils.post(this, NetworkRequestsURL.delCollection, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.myfile.MyFileAct.3
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
                super.onSuccess((AnonymousClass3) s2);
                try {
                    if ("200".equals(((MyCollectionBean) new Gson().fromJson(s2, MyCollectionBean.class)).getCode())) {
                        for (String str : MyFileAct.this.selectFileIds) {
                            int i2 = 0;
                            while (true) {
                                if (i2 >= MyFileAct.this.mAdapter.getData().size()) {
                                    break;
                                }
                                if (TextUtils.equals(str, MyFileAct.this.mAdapter.getData().get(i2).getId())) {
                                    MyFileAct.this.mAdapter.remove((MyFileAdp) MyFileAct.this.mAdapter.getData().get(i2));
                                    break;
                                }
                                i2++;
                            }
                        }
                        MyFileAct.this.selectFileIds.clear();
                        if (MyFileAct.this.mAdapter.getData().isEmpty()) {
                            MyFileAct.this.exitEditMode();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void exitEditMode() {
        this.mAdapter.setEdit(false);
        this.mAdapter.notifyDataSetChanged();
        this.mRefresh.setEnableRefresh(true);
        boolean z2 = SkinManager.getCurrentSkinType(this) == 1;
        this.isEdit = false;
        if (this.selectAll) {
            this.selectAll = false;
        }
        unSelectAll();
        this.mBtnActionbarRight.setText("编辑");
        this.mBtnActionbarBack.setImageResource(z2 ? R.mipmap.ic_black_back_night : R.mipmap.ic_black_back);
        setTitle("我的收藏");
        this.mLyDel.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, this.page + "");
        ajaxParams.put("page_size", com.tencent.connect.common.Constants.VIA_REPORT_TYPE_WPA_STATE);
        if (this.page <= 1) {
            this.mAdapter.removeFooterView(this.addFooterView);
            this.mLyLoading.setVisibility(0);
            this.mTvNoData.setVisibility(8);
        } else if (this.mAdapter.getFooterLayout() == null) {
            this.mAdapter.addFooterView(this.addFooterView);
        }
        YJYHttpUtils.post(this, NetworkRequestsURL.myCollectionList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.myfile.MyFileAct.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                MyFileAct.this.mRefresh.finishRefresh(false);
                if (MyFileAct.this.page == 1) {
                    MyFileAct.this.emptyView.showEmptyView();
                    MyFileAct.this.emptyView.setIsShowReloadBtn(true, "点击刷新页面");
                    MyFileAct.this.mAdapter.removeFooterView(MyFileAct.this.addFooterView);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                MyFileAct.this.mRefresh.finishRefresh();
                MyFileAct.this.emptyView.showEmptyView();
                super.onSuccess((AnonymousClass2) s2);
                try {
                    MyCollectionBean myCollectionBean = (MyCollectionBean) new Gson().fromJson(s2, MyCollectionBean.class);
                    if (myCollectionBean.getCode().equals("200")) {
                        if (myCollectionBean.getData() == null || myCollectionBean.getData().size() <= 0) {
                            MyFileAct.this.isNoMoreData = true;
                            MyFileAct.this.mLyLoading.setVisibility(8);
                            MyFileAct.this.mTvNoData.setVisibility(0);
                            if (MyFileAct.this.page == 1) {
                                MyFileAct.this.mAdapter.setList(new ArrayList());
                            }
                            MyFileAct.this.mRefresh.finishLoadMoreWithNoMoreData();
                        } else if (MyFileAct.this.page == 1) {
                            MyFileAct.this.mAdapter.setList(myCollectionBean.getData());
                            if (myCollectionBean.getData().size() < 15) {
                                MyFileAct.this.mRefresh.finishLoadMoreWithNoMoreData();
                                MyFileAct.this.isNoMoreData = true;
                                MyFileAct.this.mLyLoading.setVisibility(8);
                                MyFileAct.this.mTvNoData.setVisibility(0);
                            }
                        } else if (!myCollectionBean.getData().isEmpty()) {
                            MyFileAct.this.mAdapter.addData((Collection) myCollectionBean.getData());
                            if (myCollectionBean.getData().size() < 15) {
                                MyFileAct.this.isNoMoreData = true;
                                MyFileAct.this.mLyLoading.setVisibility(8);
                                MyFileAct.this.mTvNoData.setVisibility(0);
                                MyFileAct.this.mRefresh.finishLoadMoreWithNoMoreData();
                            } else {
                                MyFileAct.this.mRefresh.finishLoadMore();
                            }
                        }
                        MyFileAct.this.isCanLoadNextPage = false;
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    if (MyFileAct.this.page == 1) {
                        MyFileAct.this.emptyView.setLoadFileResUi(MyFileAct.this);
                    }
                    MyFileAct.this.mAdapter.removeFooterView(MyFileAct.this.addFooterView);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        onRefresh(this.mRefresh);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(MaterialListBean.MaterialListData materialListData, int i2) {
        materialListData.setIs_rights("1");
        this.mAdapter.notifyItemChanged(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(BaseQuickAdapter baseQuickAdapter, View view, final int i2) {
        final MaterialListBean.MaterialListData materialListData = (MaterialListBean.MaterialListData) baseQuickAdapter.getItem(i2);
        if (this.isEdit) {
            return;
        }
        if (!TextUtils.isEmpty(materialListData.getIs_rights()) && materialListData.getIs_rights().equals("1")) {
            InformationPreviewAct.newIntent(this, materialListData.getFile_id(), materialListData.getUrl(), false);
            return;
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("enclosure_id", materialListData.getFile_id());
        MemInterface.getInstance().getFilePermission(this, ajaxParams);
        MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.mine.myfile.g
            @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
            public final void mUShareListener() {
                this.f12928a.lambda$init$1(materialListData, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        MaterialListBean.MaterialListData item = this.mAdapter.getItem(i2);
        if (view.getId() == R.id.iv_select) {
            item.setSelect(!item.isSelect());
            this.mAdapter.notifyItemChanged(i2, 1);
            if (item.isSelect()) {
                if (!this.selectFileIds.contains(item.getId())) {
                    this.selectFileIds.add(item.getId());
                }
            } else if (this.selectFileIds.contains(item.getId())) {
                this.selectFileIds.remove(item.getId());
                boolean z2 = !this.selectAll;
                this.selectAll = z2;
                this.mBtnActionbarRight.setText(z2 ? "取消全选" : "全选");
            }
            setTitle("已选择" + this.selectFileIds.size() + "个文件");
            changeDelButtonBgBySelectNum(this.selectFileIds.isEmpty() ^ true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$4(View view) {
        if (this.selectFileIds.isEmpty()) {
            return;
        }
        new XPopup.Builder(this.mContext).asCustom(new ComputerNextDialog(this.mContext, new ComputerNextDialog.ClickIml() { // from class: com.psychiatrygarden.activity.mine.myfile.MyFileAct.4
            @Override // com.psychiatrygarden.widget.ComputerNextDialog.ClickIml
            public void mClickIml() {
                if (MyFileAct.this.isLogin()) {
                    ArrayList arrayList = new ArrayList();
                    String str = "";
                    for (int i2 = 0; i2 < MyFileAct.this.mAdapter.getData().size(); i2++) {
                        if (MyFileAct.this.mAdapter.getData().get(i2).isSelect()) {
                            str = str + MyFileAct.this.mAdapter.getData().get(i2).getId() + ",";
                            arrayList.add(Integer.valueOf(i2));
                        }
                    }
                    MyFileAct.this.delCollection(str.substring(0, str.length() - 1), arrayList);
                }
            }

            @Override // com.psychiatrygarden.widget.ComputerNextDialog.ClickIml
            public void mClickLeftIml() {
            }
        }, new SpannableStringBuilder("确定取消所选的收藏资料？"), "取消", "确定")).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$5(View view) {
        if (this.isEdit) {
            exitEditMode();
        } else {
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$6(View view) {
        if (this.mAdapter.getData().size() == 0) {
            return;
        }
        this.mRefresh.setEnableRefresh(false);
        if (this.isEdit) {
            boolean z2 = !this.selectAll;
            this.selectAll = z2;
            this.mBtnActionbarRight.setText(z2 ? "取消全选" : "全选");
            if (this.selectAll) {
                selectAll();
                return;
            } else {
                unSelectAll();
                return;
            }
        }
        boolean z3 = SkinManager.getCurrentSkinType(this) == 1;
        this.isEdit = true;
        Drawable drawable = ContextCompat.getDrawable(view.getContext(), z3 ? R.mipmap.ic_download_close_night : R.mipmap.ic_download_close);
        if (!z3 && drawable != null) {
            drawable.setColorFilter(ContextCompat.getColor(this, R.color.first_txt_color), PorterDuff.Mode.SRC_IN);
        }
        this.mBtnActionbarBack.setImageDrawable(drawable);
        this.mBtnActionbarRight.setText(this.selectAll ? "取消全选" : "全选");
        this.mAdapter.setEdit(true);
        unSelectAll();
        this.mLyDel.setVisibility(0);
    }

    public static void newIntent(Context context) {
        context.startActivity(new Intent(context, (Class<?>) MyFileAct.class));
    }

    private void selectAll() {
        List<MaterialListBean.MaterialListData> data = this.mAdapter.getData();
        this.selectFileIds.clear();
        for (MaterialListBean.MaterialListData materialListData : data) {
            materialListData.setSelect(true);
            this.selectFileIds.add(materialListData.getId());
        }
        this.mAdapter.notifyDataSetChanged();
        setTitle("已选择" + this.selectFileIds.size() + "个文件");
        changeDelButtonBgBySelectNum(true);
    }

    private void unSelectAll() {
        List<MaterialListBean.MaterialListData> data = this.mAdapter.getData();
        this.selectFileIds.clear();
        Iterator<MaterialListBean.MaterialListData> it = data.iterator();
        while (it.hasNext()) {
            it.next().setSelect(false);
        }
        this.mAdapter.notifyDataSetChanged();
        setTitle("已选择0个文件");
        changeDelButtonBgBySelectNum(false);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        setTitle("我的收藏");
        this.mBtnActionbarRight.setText("编辑");
        this.mBtnActionbarRight.setVisibility(0);
        this.mRefresh = (SmartRefreshLayout) findViewById(R.id.mSwipeLayput);
        this.mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        this.mLyBottom = (LinearLayout) findViewById(R.id.linexportview);
        this.mBtnDel = (TextView) findViewById(R.id.btn_sure);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linexportview);
        this.mLyDel = linearLayout;
        linearLayout.setVisibility(8);
        ImageView imageView = (ImageView) findViewById(R.id.iv_actionbar_back);
        TypedArray typedArrayObtainStyledAttributes = getTheme().obtainStyledAttributes(new int[]{R.attr.first_text_color});
        imageView.setColorFilter(typedArrayObtainStyledAttributes.getColor(0, Color.parseColor("#303030")), PorterDuff.Mode.SRC_IN);
        typedArrayObtainStyledAttributes.recycle();
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyFileAdp myFileAdp = new MyFileAdp();
        this.mAdapter = myFileAdp;
        myFileAdp.setAnimationWithDefault(BaseQuickAdapter.AnimationType.AlphaIn);
        this.mRecyclerView.setAdapter(this.mAdapter);
        CustomEmptyView customEmptyView = new CustomEmptyView(this, 0, "");
        this.emptyView = customEmptyView;
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.myfile.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12922c.lambda$init$0(view);
            }
        });
        this.mAdapter.setEmptyView(this.emptyView);
        View viewInflate = getLayoutInflater().inflate(R.layout.activity_hideview, (ViewGroup) null);
        this.addFooterView = viewInflate;
        this.mTvNoData = (TextView) viewInflate.findViewById(R.id.tv_no_more_data);
        this.mLyLoading = (RelativeLayout) this.addFooterView.findViewById(R.id.hide_sub_floor_content);
        this.mRefresh.setEnableLoadMore(false);
        this.mRefresh.setOnRefreshListener(this);
        this.mAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.mine.myfile.b
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f12923c.lambda$init$2(baseQuickAdapter, view, i2);
            }
        });
        this.mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() { // from class: com.psychiatrygarden.activity.mine.myfile.c
            @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f12924c.lambda$init$3(baseQuickAdapter, view, i2);
            }
        });
        this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.psychiatrygarden.activity.mine.myfile.MyFileAct.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (MyFileAct.this.isEdit) {
                    return;
                }
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) MyFileAct.this.mRecyclerView.getLayoutManager();
                int iFindFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                if (dy > 0) {
                    int itemCount = linearLayoutManager.getItemCount() - iFindFirstVisibleItemPosition;
                    LogUtils.e("dis_count", "count====>" + itemCount);
                    if (itemCount > 11 || MyFileAct.this.isCanLoadNextPage || MyFileAct.this.isNoMoreData) {
                        return;
                    }
                    MyFileAct.this.isCanLoadNextPage = true;
                    MyFileAct.access$412(MyFileAct.this, 1);
                    MyFileAct.this.getData();
                    LogUtils.e("load_next", "加载下一页数据:" + MyFileAct.this.page);
                }
            }
        });
        getData();
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        if (this.isEdit) {
            exitEditMode();
        } else {
            finish();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals("onRefreshInfo")) {
            onRefresh(this.mRefresh);
        }
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        this.page = 1;
        this.isNoMoreData = false;
        getData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_my_file);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnDel.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.myfile.d
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12925c.lambda$setListenerForWidget$4(view);
            }
        });
        this.mBtnActionbarBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.myfile.e
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12926c.lambda$setListenerForWidget$5(view);
            }
        });
        this.mBtnActionbarRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.myfile.f
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12927c.lambda$setListenerForWidget$6(view);
            }
        });
    }
}
