package com.psychiatrygarden.activity.ebook;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.LoginActivity;
import com.psychiatrygarden.adapter.BookReadRecordAdapter;
import com.psychiatrygarden.bean.BookShelfBean;
import com.psychiatrygarden.bean.BookShelfDataBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.ComputerNextDialog;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yikaobang.yixue.R;
import com.ykb.ebook.activity.ReadBookActivity;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class BookReadRecordActivity extends BaseActivity implements OnRefreshLoadMoreListener {
    private CustomEmptyView emptyView;
    private BookReadRecordAdapter mAdapter;
    private SmartRefreshLayout mRefreshLayout;
    private TextView mTvRight;
    private TextView tvDelete;
    private int page = 1;
    private boolean isEditMode = false;
    private boolean selectAll = false;
    private int selectCount = 0;

    /* JADX INFO: Access modifiers changed from: private */
    public void addBook(String bookId, final int pos) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("book_id", bookId);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.addBook, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ebook.BookReadRecordActivity.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass4) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        BookReadRecordActivity.this.mAdapter.getData().get(pos).setIsBookshelf("1");
                        BookReadRecordActivity.this.mAdapter.notifyItemChanged(pos, 1);
                        EventBus.getDefault().post("updateBookList");
                    }
                    ToastUtil.shortToast(BookReadRecordActivity.this, jSONObject.optString("message"));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void changeDelButtonBgBySelectNum(boolean isSelect) {
        TypedArray typedArrayObtainStyledAttributes = getTheme().obtainStyledAttributes(new int[]{R.attr.red_round_coner_color_F95843_radius_12, R.attr.downloadBtn_no_select_bg});
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(0);
        Drawable drawable2 = typedArrayObtainStyledAttributes.getDrawable(1);
        if (isSelect) {
            this.tvDelete.setBackground(drawable);
            this.tvDelete.setTextColor(getColor(R.color.white));
        } else {
            this.tvDelete.setBackground(drawable2);
            this.tvDelete.setTextColor(getColor(SkinManager.getCurrentSkinType(this) == 1 ? R.color.forth_txt_color_night : R.color.forth_txt_color));
        }
    }

    private void clearReadRecord(final List<String> bookIds, final StringBuilder stringBuilder) {
        new XPopup.Builder(this.mContext).asCustom(new ComputerNextDialog(this.mContext, new ComputerNextDialog.ClickIml() { // from class: com.psychiatrygarden.activity.ebook.BookReadRecordActivity.1
            @Override // com.psychiatrygarden.widget.ComputerNextDialog.ClickIml
            public void mClickIml() {
                BookReadRecordActivity.this.delHistory(bookIds, stringBuilder);
            }

            @Override // com.psychiatrygarden.widget.ComputerNextDialog.ClickIml
            public void mClickLeftIml() {
            }
        }, new SpannableStringBuilder("确定删除所选书籍？"), "取消", "删除")).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void delHistory(final List<String> bookIds, StringBuilder stringBuilder) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("ids", stringBuilder.toString());
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.delHistoryRecord, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ebook.BookReadRecordActivity.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass5) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        for (String str : bookIds) {
                            int i2 = 0;
                            while (true) {
                                if (i2 >= BookReadRecordActivity.this.mAdapter.getData().size()) {
                                    break;
                                }
                                if (TextUtils.equals(str, BookReadRecordActivity.this.mAdapter.getData().get(i2).getBookId()) && BookReadRecordActivity.this.mAdapter.getData().get(i2).isSelect()) {
                                    BookReadRecordActivity.this.mAdapter.remove((BookReadRecordAdapter) BookReadRecordActivity.this.mAdapter.getData().get(i2));
                                    break;
                                }
                                i2++;
                            }
                        }
                    }
                    ToastUtil.shortToast(BookReadRecordActivity.this, jSONObject.optString("message"));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void exitEditMode() {
        boolean z2 = SkinManager.getCurrentSkinType(this) == 1;
        this.isEditMode = false;
        if (this.selectAll) {
            this.selectAll = false;
        }
        findViewById(R.id.tv_delete).setVisibility(8);
        unSelectAll();
        ((TextView) findViewById(R.id.tv_actionbar_right)).setText("编辑");
        ((ImageView) findViewById(R.id.iv_actionbar_back)).setImageResource(z2 ? R.mipmap.ic_black_back_night : R.mipmap.ic_black_back);
        this.mRefreshLayout.setEnableRefresh(true);
    }

    private void itemClick(int i2) {
        if (this.isEditMode) {
            this.mAdapter.getItem(i2).setSelect(!r0.isSelect());
            List<BookShelfDataBean> data = this.mAdapter.getData();
            this.selectCount = 0;
            for (BookShelfDataBean bookShelfDataBean : data) {
                if (bookShelfDataBean.isSelect()) {
                    this.selectCount += bookShelfDataBean.isSelect() ? 1 : 0;
                }
            }
            this.mAdapter.notifyItemChanged(i2, 999);
            if (this.selectCount == data.size()) {
                this.selectAll = true;
                this.mTvRight.setText("取消全选");
            } else {
                this.selectAll = false;
                this.mTvRight.setText("全选");
            }
            changeDelButtonBgBySelectNum(this.selectCount > 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        if (this.isEditMode) {
            exitEditMode();
        } else {
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(ImageView imageView, View view) {
        if (this.isEditMode) {
            boolean z2 = !this.selectAll;
            this.selectAll = z2;
            this.mTvRight.setText(z2 ? "取消全选" : "全选");
            if (this.selectAll) {
                selectAll();
                return;
            } else {
                unSelectAll();
                return;
            }
        }
        this.isEditMode = true;
        boolean z3 = SkinManager.getCurrentSkinType(view.getContext()) == 1;
        this.tvDelete.setVisibility(0);
        Drawable drawable = ContextCompat.getDrawable(view.getContext(), z3 ? R.mipmap.ic_download_close_night : R.mipmap.ic_download_close);
        if (!z3 && drawable != null) {
            drawable.setColorFilter(ContextCompat.getColor(view.getContext(), R.color.first_txt_color), PorterDuff.Mode.SRC_IN);
        }
        imageView.setImageDrawable(drawable);
        this.mTvRight.setText(this.selectAll ? "取消全选" : "全选");
        this.mRefreshLayout.setEnableRefresh(false);
        unSelectAll();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view) {
        int size = this.mAdapter.getData().size();
        List<BookShelfDataBean> data = this.mAdapter.getData();
        StringBuilder sb = new StringBuilder();
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        while (i2 < size) {
            if (data.get(i2).isSelect()) {
                arrayList.add(data.get(i2).getBookId());
                sb.append(data.get(i2).getBookId());
                sb.append(i2 == data.size() + (-1) ? "" : ",");
            }
            i2++;
        }
        if (arrayList.isEmpty()) {
            return;
        }
        clearReadRecord(arrayList, sb);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3(View view) {
        loadData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$4(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        itemClick(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$5(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        itemClick(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$6(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        if (this.isEditMode) {
            return;
        }
        openBook(i2);
    }

    private void loadData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, this.page + "");
        ajaxParams.put("page_size", com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SHARE_TO_QQ);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.readHistoryList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ebook.BookReadRecordActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                BookReadRecordActivity.this.mRefreshLayout.finishRefresh();
                if (BookReadRecordActivity.this.page == 1) {
                    BookReadRecordActivity.this.emptyView.setLoadFileResUi(BookReadRecordActivity.this);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                BookReadRecordActivity.this.emptyView.showEmptyView();
                BookReadRecordActivity.this.mRefreshLayout.finishRefresh();
                try {
                    BookShelfBean bookShelfBean = (BookShelfBean) new Gson().fromJson(s2, BookShelfBean.class);
                    if (bookShelfBean.getCode().equals("200")) {
                        if (bookShelfBean.getData() == null || bookShelfBean.getData() == null || bookShelfBean.getData().isEmpty()) {
                            BookReadRecordActivity.this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                        } else if (BookReadRecordActivity.this.page == 1) {
                            BookReadRecordActivity.this.mAdapter.setList(bookShelfBean.getData());
                            if (bookShelfBean.getData().size() < 10) {
                                BookReadRecordActivity.this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                            }
                        } else if (!bookShelfBean.getData().isEmpty()) {
                            BookReadRecordActivity.this.mAdapter.addData((Collection) bookShelfBean.getData());
                            if (bookShelfBean.getData().size() < 10) {
                                BookReadRecordActivity.this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                            } else {
                                BookReadRecordActivity.this.mRefreshLayout.finishLoadMore();
                            }
                        }
                        BookReadRecordActivity.this.findViewById(R.id.tv_actionbar_right).setVisibility(BookReadRecordActivity.this.mAdapter.getData().isEmpty() ? 8 : 0);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    if (BookReadRecordActivity.this.page == 1) {
                        BookReadRecordActivity.this.emptyView.setLoadFileResUi(BookReadRecordActivity.this);
                    }
                }
            }
        });
    }

    public static void newIntent(Context context) {
        context.startActivity(new Intent(context, (Class<?>) BookReadRecordActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openBook(int position) {
        if (!UserConfig.isLogin()) {
            startActivity(new Intent(this, (Class<?>) LoginActivity.class));
            return;
        }
        BookShelfDataBean item = this.mAdapter.getItem(position);
        startActivity(ReadBookActivity.INSTANCE.newIntent(this, item.getBookId(), UserConfig.getUserId(), SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1"), UserConfig.getInstance().getUser().getAdmin(), UserConfig.getInstance().getUser().getAvatar(), UserConfig.getInstance().getUser().getToken(), UserConfig.getInstance().getUser().getSecret()));
    }

    private void selectAll() {
        this.mAdapter.getData();
        for (int i2 = 0; i2 < this.mAdapter.getData().size(); i2++) {
            this.mAdapter.getData().get(i2).setSelect(true);
            this.mAdapter.getData().get(i2).setEditMode(this.isEditMode);
        }
        this.mAdapter.notifyDataSetChanged();
        changeDelButtonBgBySelectNum(true);
    }

    private void unSelectAll() {
        for (BookShelfDataBean bookShelfDataBean : this.mAdapter.getData()) {
            bookShelfDataBean.setEditMode(this.isEditMode);
            bookShelfDataBean.setSelect(false);
        }
        this.mAdapter.notifyDataSetChanged();
        changeDelButtonBgBySelectNum(false);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mActionBar.hide();
        this.mAdapter = new BookReadRecordAdapter();
        TextView textView = (TextView) findViewById(R.id.txt_actionbar_title);
        ImageView imageView = (ImageView) findViewById(R.id.iv_actionbar_right);
        final ImageView imageView2 = (ImageView) findViewById(R.id.iv_actionbar_back);
        this.mTvRight = (TextView) findViewById(R.id.tv_actionbar_right);
        this.tvDelete = (TextView) findViewById(R.id.tv_delete);
        textView.setText(R.string.book_read_record);
        this.mTvRight.setText(R.string.edit_label_only);
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ebook.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12281c.lambda$init$0(view);
            }
        });
        this.mTvRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ebook.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12283c.lambda$init$1(imageView2, view);
            }
        });
        imageView.setVisibility(8);
        this.mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refresh);
        ((RecyclerView) findViewById(R.id.rvList)).setAdapter(this.mAdapter);
        this.tvDelete.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ebook.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12286c.lambda$init$2(view);
            }
        });
        CustomEmptyView customEmptyView = new CustomEmptyView(this, 0, "");
        this.emptyView = customEmptyView;
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ebook.d
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12288c.lambda$init$3(view);
            }
        });
        this.emptyView.changeEmptyViewWriteBg();
        this.mAdapter.setEmptyView(this.emptyView);
        this.mRefreshLayout.setOnRefreshLoadMoreListener(this);
        loadData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
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
        setContentView(R.layout.act_book_read_record);
        setNewStyleStatusBarColor();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() { // from class: com.psychiatrygarden.activity.ebook.e
            @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f12289c.lambda$setListenerForWidget$4(baseQuickAdapter, view, i2);
            }
        });
        this.mAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.ebook.f
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f12290c.lambda$setListenerForWidget$5(baseQuickAdapter, view, i2);
            }
        });
        this.mAdapter.setOnItemActionLisenter(new BookReadRecordAdapter.OnItemActionLisenter() { // from class: com.psychiatrygarden.activity.ebook.BookReadRecordActivity.2
            @Override // com.psychiatrygarden.adapter.BookReadRecordAdapter.OnItemActionLisenter
            public void setAddBook(int pos, BookShelfDataBean item) {
                BookReadRecordActivity.this.addBook(item.getBookId(), pos);
            }

            @Override // com.psychiatrygarden.adapter.BookReadRecordAdapter.OnItemActionLisenter
            public void setOpenBook(int pos, BookShelfDataBean item) {
                BookReadRecordActivity.this.openBook(pos);
            }
        });
        this.mAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.ebook.g
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f12291c.lambda$setListenerForWidget$6(baseQuickAdapter, view, i2);
            }
        });
    }
}
