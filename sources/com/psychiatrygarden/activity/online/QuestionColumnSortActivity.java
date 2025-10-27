package com.psychiatrygarden.activity.online;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.online.adapter.QuestionColumnSortAdapter;
import com.psychiatrygarden.bean.SelectIdentityBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class QuestionColumnSortActivity extends BaseActivity {
    private List<SelectIdentityBean.DataBean> children = new ArrayList();
    private boolean hasSort;
    RecyclerView recycle;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        if (this.hasSort) {
            EventBus.getDefault().post("upIndexList");
        }
        finish();
    }

    public void columnSort(String parent_id, List<String> identity_id) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("identity_id", new Gson().toJson(identity_id));
        ajaxParams.put("parent_id", parent_id + "");
        ajaxParams.put("user_id", UserConfig.getUserId());
        YJYHttpUtils.post(this.mContext.getApplicationContext(), NetworkRequestsURL.columnSortURL, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.online.QuestionColumnSortActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (!jSONObject.optString("code", "").equals("200")) {
                        QuestionColumnSortActivity.this.AlertToast(jSONObject.optString("message", ""));
                    } else if (!QuestionColumnSortActivity.this.hasSort) {
                        QuestionColumnSortActivity.this.hasSort = true;
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.children = (List) getIntent().getSerializableExtra("listBean");
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.mBtnActionbarBack.setImageResource(R.mipmap.ic_push_circle_close_day);
        } else {
            this.mBtnActionbarBack.setImageResource(R.mipmap.ic_push_circle_close_night);
        }
        this.mBtnActionbarBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.i1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13427c.lambda$init$0(view);
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle);
        this.recycle = recyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        QuestionColumnSortAdapter questionColumnSortAdapter = new QuestionColumnSortAdapter(this.children);
        OnItemDragListener onItemDragListener = new OnItemDragListener() { // from class: com.psychiatrygarden.activity.online.QuestionColumnSortActivity.1
            @Override // com.chad.library.adapter.base.listener.OnItemDragListener
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
                ViewCompat.animate(viewHolder.itemView).setDuration(200L).scaleX(1.0f).scaleY(1.0f).start();
                SharePreferencesUtils.writeStrConfig(CommonParameter.catalogue_q, new Gson().toJson(QuestionColumnSortActivity.this.children), QuestionColumnSortActivity.this.mContext);
                ArrayList arrayList = new ArrayList();
                String parent_id = "";
                for (SelectIdentityBean.DataBean dataBean : QuestionColumnSortActivity.this.children) {
                    arrayList.add(dataBean.getIdentity_id());
                    parent_id = dataBean.getParent_id();
                }
                QuestionColumnSortActivity.this.columnSort(parent_id, arrayList);
            }

            @Override // com.chad.library.adapter.base.listener.OnItemDragListener
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
            }

            @Override // com.chad.library.adapter.base.listener.OnItemDragListener
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
                ViewCompat.animate(((BaseViewHolder) viewHolder).itemView).setDuration(200L).scaleX(1.1f).scaleY(1.1f).start();
            }
        };
        questionColumnSortAdapter.getDraggableModule().setDragEnabled(true);
        questionColumnSortAdapter.getDraggableModule().setOnItemDragListener(onItemDragListener);
        questionColumnSortAdapter.getDraggableModule().getItemTouchHelperCallback().setSwipeMoveFlags(48);
        this.recycle.setAdapter(questionColumnSortAdapter);
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (this.hasSort) {
            EventBus.getDefault().post("upIndexList");
        }
        super.onBackPressed();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("全部栏目");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4 && this.hasSort) {
            EventBus.getDefault().post("upIndexList");
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_question_column_sort);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
