package com.psychiatrygarden.activity.ebook;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.adapter.BookShelfAdapter;
import com.psychiatrygarden.bean.BookShelfBean;
import com.psychiatrygarden.bean.BookShelfDataBean;
import com.psychiatrygarden.bean.PushBookData;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.forum.PushCircleAndArticleAct;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yikaobang.yixue.R;
import com.ykb.ebook.activity.ReadBookActivity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class BookShelfActivity extends BaseActivity implements View.OnClickListener, OnRefreshLoadMoreListener {
    private String appId;
    private boolean editMode;
    private CustomEmptyView emptyView;
    private ImageView ivModeSwitch;
    private View llEdit;
    private BookShelfAdapter mAdapter;
    private ImageView mImgBack;
    private SmartRefreshLayout mRefresh;
    private RecyclerView mRvList;
    private TextView mTvTitle;
    private int page = 1;
    private boolean selectAll;
    private String stockId;
    private TextView tvActionBarRight;
    private TextView tvReadDuration;

    private void delBook(String bookIds, List<Integer> positions) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("ids", bookIds);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.delBook, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ebook.BookShelfActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                BookShelfActivity.this.mRefresh.finishRefresh();
                if (BookShelfActivity.this.page == 1) {
                    BookShelfActivity.this.emptyView.setLoadFileResUi(BookShelfActivity.this);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                BookShelfActivity.this.mRefresh.finishRefresh();
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        ArrayList arrayList = new ArrayList();
                        for (int i2 = 0; i2 < BookShelfActivity.this.mAdapter.getData().size(); i2++) {
                            if (!BookShelfActivity.this.mAdapter.getData().get(i2).isSelect() || i2 == BookShelfActivity.this.mAdapter.getData().size() - 1) {
                                arrayList.add(BookShelfActivity.this.mAdapter.getData().get(i2));
                            }
                        }
                        BookShelfActivity.this.mAdapter.setList(arrayList);
                        BookShelfActivity.this.mTvTitle.setText(String.format("已选择 %d 本", Integer.valueOf(BookShelfActivity.this.getSelectCount())));
                        List<BookShelfDataBean> data = BookShelfActivity.this.mAdapter.getData();
                        if (data.size() == 1 && data.get(0).getType() == 2) {
                            BookShelfActivity.this.exitEditMode();
                        }
                    }
                    ToastUtil.shortToast(BookShelfActivity.this, jSONObject.optString("message"));
                } catch (Exception e2) {
                    e2.printStackTrace();
                    if (BookShelfActivity.this.page == 1) {
                        BookShelfActivity.this.emptyView.setLoadFileResUi(BookShelfActivity.this);
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void exitEditMode() {
        if (!this.editMode) {
            this.mRefresh.setEnableRefresh(true);
            this.mRefresh.setEnableLoadMore(true);
            finish();
            return;
        }
        this.mRefresh.setEnableRefresh(true);
        this.mRefresh.setEnableLoadMore(true);
        this.tvActionBarRight.setVisibility(8);
        this.mImgBack.setImageResource(SkinManager.getCurrentSkinType(this) == 1 ? R.mipmap.ic_black_back_night : R.mipmap.ic_black_back);
        showBottomPanel(false);
        this.mTvTitle.setText("书架");
        this.editMode = false;
        this.selectAll = false;
        for (BookShelfDataBean bookShelfDataBean : this.mAdapter.getData()) {
            bookShelfDataBean.setSelect(false);
            bookShelfDataBean.setEditMode(false);
        }
        this.mAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getSelectCount() {
        int i2 = 0;
        if (this.mAdapter.getData().size() == 0) {
            return 0;
        }
        for (BookShelfDataBean bookShelfDataBean : this.mAdapter.getData().subList(0, this.mAdapter.getData().size() - 1)) {
            if (bookShelfDataBean.getType() == 1) {
                i2 += bookShelfDataBean.isSelect() ? 1 : 0;
            }
        }
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        loadData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setAdapterListener$1(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        updateItemSelect(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setAdapterListener$2(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        boolean z2 = !this.editMode;
        this.editMode = z2;
        if (z2) {
            this.mRefresh.setEnableRefresh(false);
            this.mRefresh.setEnableLoadMore(false);
            List<BookShelfDataBean> data = this.mAdapter.getData();
            int i3 = 0;
            while (i3 < data.size()) {
                data.get(i3).setSelect(i3 == i2);
                data.get(i3).setEditMode(true);
                i3++;
            }
            baseQuickAdapter.notifyDataSetChanged();
            this.mImgBack.setImageResource(SkinManager.getCurrentSkinType(view.getContext()) == 1 ? R.mipmap.ic_download_close_night : R.mipmap.ic_download_close);
            this.tvActionBarRight.setText("全选");
            this.mTvTitle.setText(String.format("已选择 %d 本", Integer.valueOf(getSelectCount())));
            showBottomPanel(true);
            this.tvActionBarRight.setVisibility(0);
            this.llEdit.setVisibility(0);
        } else {
            this.llEdit.setVisibility(8);
            this.mRefresh.setEnableRefresh(true);
            this.mRefresh.setEnableLoadMore(true);
        }
        return this.editMode;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showBottomPanel$3(ValueAnimator valueAnimator) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.llEdit.getLayoutParams();
        layoutParams.height = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        this.llEdit.setLayoutParams(layoutParams);
    }

    private void loadData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, this.page + "");
        ajaxParams.put("page_size", "20");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.bookList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ebook.BookShelfActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                BookShelfActivity.this.mRefresh.finishRefresh();
                if (BookShelfActivity.this.page == 1) {
                    BookShelfActivity.this.emptyView.setLoadFileResUi(BookShelfActivity.this);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) throws NumberFormatException {
                super.onSuccess((AnonymousClass1) s2);
                BookShelfActivity.this.mRefresh.finishRefresh();
                try {
                    BookShelfBean bookShelfBean = (BookShelfBean) new Gson().fromJson(s2, BookShelfBean.class);
                    if (!bookShelfBean.getCode().equals("200")) {
                        BookShelfActivity.this.emptyView.uploadEmptyViewResUi();
                        return;
                    }
                    if (bookShelfBean.getData() == null || bookShelfBean.getData() == null || bookShelfBean.getData().isEmpty()) {
                        if (BookShelfActivity.this.page == 1) {
                            BookShelfDataBean bookShelfDataBean = new BookShelfDataBean();
                            bookShelfDataBean.setBookId("");
                            bookShelfDataBean.setSelect(false);
                            bookShelfDataBean.setBookTitle("");
                            bookShelfDataBean.setThumbnail("");
                            ArrayList arrayList = new ArrayList();
                            arrayList.add(bookShelfDataBean);
                            BookShelfActivity.this.mAdapter.setList(arrayList);
                        }
                        BookShelfActivity.this.mRefresh.finishLoadMoreWithNoMoreData();
                        return;
                    }
                    if (TextUtils.isEmpty(bookShelfBean.getData().get(0).getTodayDuration())) {
                        BookShelfActivity.this.tvReadDuration.setText("今日阅读 0 分钟");
                    } else {
                        int i2 = Integer.parseInt(bookShelfBean.getData().get(0).getTodayDuration());
                        int i3 = (i2 / 60) % 60;
                        if (i3 < 60) {
                            BookShelfActivity.this.tvReadDuration.setText("今日阅读 " + i3 + " 分钟");
                        } else {
                            BookShelfActivity.this.tvReadDuration.setText("今日阅读 " + (i2 / 3600) + " 小时" + i3 + "分钟");
                        }
                    }
                    if (BookShelfActivity.this.page != 1) {
                        if (bookShelfBean.getData().isEmpty()) {
                            return;
                        }
                        BookShelfActivity.this.mAdapter.addData(BookShelfActivity.this.mAdapter.getData().size() - 1, (Collection) bookShelfBean.getData());
                        if (bookShelfBean.getData().size() < 20) {
                            BookShelfActivity.this.mRefresh.finishLoadMoreWithNoMoreData();
                            return;
                        } else {
                            BookShelfActivity.this.mRefresh.finishLoadMore();
                            return;
                        }
                    }
                    BookShelfActivity.this.mAdapter.setList(bookShelfBean.getData());
                    BookShelfDataBean bookShelfDataBean2 = new BookShelfDataBean();
                    bookShelfDataBean2.setBookId("");
                    bookShelfDataBean2.setSelect(false);
                    bookShelfDataBean2.setBookTitle("");
                    bookShelfDataBean2.setThumbnail("");
                    BookShelfActivity.this.mAdapter.addData((BookShelfAdapter) bookShelfDataBean2);
                    if (bookShelfBean.getData().size() < 20) {
                        BookShelfActivity.this.mRefresh.finishLoadMoreWithNoMoreData();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    if (BookShelfActivity.this.page == 1) {
                        BookShelfActivity.this.emptyView.setLoadFileResUi(BookShelfActivity.this);
                    }
                }
            }
        });
    }

    public static void newIntent(Context context, String stockId, String appId) {
        Intent intent = new Intent(context, (Class<?>) BookShelfActivity.class);
        intent.putExtra("stockId", stockId);
        intent.putExtra("appId", appId);
        context.startActivity(intent);
    }

    private void setAdapterListener() {
        this.mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() { // from class: com.psychiatrygarden.activity.ebook.i
            @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f12293c.lambda$setAdapterListener$1(baseQuickAdapter, view, i2);
            }
        });
        this.mAdapter.setOnItemClickLisenter(new BookShelfAdapter.setOnItemClickLisenter() { // from class: com.psychiatrygarden.activity.ebook.BookShelfActivity.3
            @Override // com.psychiatrygarden.adapter.BookShelfAdapter.setOnItemClickLisenter
            public void setItemClickAction(int position, BookShelfDataBean item) {
                if (TextUtils.isEmpty(item.getBookId())) {
                    BookStoreActivity.newIntent(BookShelfActivity.this);
                    return;
                }
                if (BookShelfActivity.this.editMode) {
                    BookShelfActivity.this.updateItemSelect(position);
                    return;
                }
                String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1");
                String admin = UserConfig.getInstance().getUser().getAdmin();
                String avatar = UserConfig.getInstance().getUser().getAvatar();
                BookShelfActivity bookShelfActivity = BookShelfActivity.this;
                bookShelfActivity.startActivity(ReadBookActivity.INSTANCE.newIntent(bookShelfActivity.mContext, item.getBookId(), UserConfig.getUserId(), strConfig, admin, avatar, UserConfig.getInstance().getUser().getToken(), UserConfig.getInstance().getUser().getSecret()));
            }
        });
        this.mAdapter.setOnItemLongClickListener(new OnItemLongClickListener() { // from class: com.psychiatrygarden.activity.ebook.j
            @Override // com.chad.library.adapter.base.listener.OnItemLongClickListener
            public final boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                return this.f12294a.lambda$setAdapterListener$2(baseQuickAdapter, view, i2);
            }
        });
    }

    private void showBottomPanel(boolean show) {
        int[] iArr = new int[2];
        iArr[0] = show ? 0 : CommonUtil.dip2px(this, 56.0f);
        iArr[1] = show ? CommonUtil.dip2px(this, 56.0f) : 0;
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(iArr);
        valueAnimatorOfInt.setDuration(200L);
        valueAnimatorOfInt.setInterpolator(new AccelerateInterpolator());
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.activity.ebook.k
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f12295c.lambda$showBottomPanel$3(valueAnimator);
            }
        });
        valueAnimatorOfInt.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateItemSelect(int position) {
        BookShelfDataBean item = this.mAdapter.getItem(position);
        item.setSelect(!item.isSelect());
        item.setEditMode(true);
        this.mAdapter.notifyItemChanged(position);
        this.mTvTitle.setText(String.format("已选择 %d 本", Integer.valueOf(getSelectCount())));
        if (getSelectCount() == this.mAdapter.getData().size() - 1) {
            this.tvActionBarRight.setText("反选");
        } else {
            this.tvActionBarRight.setText("全选");
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.stockId = getIntent().getStringExtra("stockId");
        this.appId = getIntent().getStringExtra("appId");
        TextView textView = (TextView) findViewById(R.id.txt_actionbar_title);
        this.mTvTitle = textView;
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        this.mTvTitle.setText("书架");
        this.ivModeSwitch = (ImageView) findViewById(R.id.iv_mode_switch);
        this.tvActionBarRight = (TextView) findViewById(R.id.tv_actionbar_right);
        this.tvReadDuration = (TextView) findViewById(R.id.tv_read_duration);
        this.llEdit = findViewById(R.id.ll_edit);
        this.mImgBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        this.mRvList = (RecyclerView) findViewById(R.id.rvList);
        this.mRefresh = (SmartRefreshLayout) findViewById(R.id.refresh);
        if (this.mRvList.getItemAnimator() instanceof SimpleItemAnimator) {
            this.mRvList.setItemAnimator(null);
        }
        int intConfig = SharePreferencesUtils.readIntConfig(CommonParameter.BOOK_SHELF_LIST_MODE, this, 1);
        BookShelfAdapter bookShelfAdapter = new BookShelfAdapter(intConfig == 1);
        this.mAdapter = bookShelfAdapter;
        if (intConfig == 1) {
            bookShelfAdapter.setShowMode(false);
            this.ivModeSwitch.setImageResource(R.drawable.ic_list_show_mode_grid);
            this.mRvList.setLayoutManager(new LinearLayoutManager(this, 1, false));
        } else {
            bookShelfAdapter.setShowMode(true);
            this.ivModeSwitch.setImageResource(R.drawable.ic_list_show_mode_vertical);
            this.mRvList.setLayoutManager(new GridLayoutManager(this, 3));
        }
        this.mRvList.setAdapter(this.mAdapter);
        this.mActionBar.hide();
        setNewStyleStatusBarColor();
        CustomEmptyView customEmptyView = new CustomEmptyView(this, 0, "");
        this.emptyView = customEmptyView;
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ebook.h
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12292c.lambda$init$0(view);
            }
        });
        this.mAdapter.setEmptyView(this.emptyView);
        loadData();
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportActivity, cn.webdemo.com.supporfragment.ISupportActivity
    public void onBackPressedSupport() {
        if (this.editMode) {
            exitEditMode();
        } else {
            super.onBackPressedSupport();
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        int id = v2.getId();
        if (id == R.id.tv_actionbar_right) {
            boolean z2 = !this.selectAll;
            this.selectAll = z2;
            this.tvActionBarRight.setText(!z2 ? "全选" : "反选");
            Iterator<BookShelfDataBean> it = this.mAdapter.getData().iterator();
            while (it.hasNext()) {
                it.next().setSelect(this.selectAll);
            }
            this.mAdapter.notifyDataSetChanged();
            this.mTvTitle.setText(String.format("已选择 %d 本", Integer.valueOf(getSelectCount())));
            return;
        }
        if (id == R.id.iv_actionbar_back) {
            exitEditMode();
            return;
        }
        if (id == R.id.tv_read_history) {
            BookReadRecordActivity.newIntent(this);
            return;
        }
        if (id == R.id.iv_mode_switch) {
            int i2 = SharePreferencesUtils.readIntConfig(CommonParameter.BOOK_SHELF_LIST_MODE, this, 1) == 2 ? 1 : 2;
            SharePreferencesUtils.writeIntConfig(CommonParameter.BOOK_SHELF_LIST_MODE, i2, this);
            if (i2 == 2) {
                this.ivModeSwitch.setImageResource(R.drawable.ic_list_show_mode_vertical);
            } else {
                this.ivModeSwitch.setImageResource(R.drawable.ic_list_show_mode_grid);
            }
            this.mRvList.setLayoutManager(i2 == 2 ? new GridLayoutManager(this, 3) : new LinearLayoutManager(this, 1, false));
            this.mAdapter.setShowMode(i2 == 2);
            this.mAdapter.notifyDataSetChanged();
            setAdapterListener();
            return;
        }
        if (id == R.id.ll_push_book) {
            if (getSelectCount() <= 0) {
                ToastUtil.shortToast(this, "请勾选书籍");
                return;
            }
            List<BookShelfDataBean> listSubList = this.mAdapter.getData().subList(0, this.mAdapter.getData().size() - 1);
            ArrayList arrayList = new ArrayList();
            for (BookShelfDataBean bookShelfDataBean : listSubList) {
                if (bookShelfDataBean.isSelect()) {
                    PushBookData pushBookData = new PushBookData();
                    pushBookData.setTitle(bookShelfDataBean.getBookTitle());
                    pushBookData.setId(bookShelfDataBean.getBookId());
                    pushBookData.setAuthor(bookShelfDataBean.getAuthor());
                    pushBookData.setGrade(bookShelfDataBean.getGrade());
                    pushBookData.setThumbnail(bookShelfDataBean.getThumbnail());
                    arrayList.add(pushBookData);
                }
            }
            if (arrayList.size() > 10) {
                ToastUtil.shortToast(this, "最多只能添加10本书籍");
                return;
            } else {
                PushCircleAndArticleAct.newIntent(this, false, arrayList, 12);
                return;
            }
        }
        if (id == R.id.ll_remove) {
            if (getSelectCount() == 0) {
                ToastUtil.shortToast(this, "请勾选书籍");
                return;
            }
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            List<BookShelfDataBean> listSubList2 = this.mAdapter.getData().subList(0, this.mAdapter.getData().size() - 1);
            for (int i3 = 0; i3 < listSubList2.size(); i3++) {
                if (listSubList2.get(i3).isSelect()) {
                    arrayList2.add(listSubList2.get(i3).getBookId());
                    arrayList3.add(Integer.valueOf(i3));
                }
            }
            StringBuilder sb = new StringBuilder();
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                sb.append((String) it2.next());
                sb.append(",");
            }
            String strSubstring = sb.substring(0, sb.length() - 1);
            Log.e("res_id", strSubstring);
            delBook(strSubstring, arrayList3);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals("updateBookList")) {
            onRefresh(this.mRefresh);
        }
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        this.page++;
        loadData();
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        this.page = 1;
        loadData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.act_book_shelf);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mImgBack.setOnClickListener(this);
        findViewById(R.id.tv_read_history).setOnClickListener(this);
        this.ivModeSwitch.setOnClickListener(this);
        findViewById(R.id.ll_push_book).setOnClickListener(this);
        findViewById(R.id.ll_remove).setOnClickListener(this);
        this.tvActionBarRight.setOnClickListener(this);
        setAdapterListener();
        this.mRefresh.setOnRefreshLoadMoreListener(this);
    }
}
