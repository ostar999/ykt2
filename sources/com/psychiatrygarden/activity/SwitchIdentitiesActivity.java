package com.psychiatrygarden.activity;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.bean.CheckBoxBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.List;

/* loaded from: classes5.dex */
public class SwitchIdentitiesActivity extends BaseActivity {
    public boolean App_ISTIKU;
    public int App_Position;
    public String app_title;
    private boolean flag;
    private int position;
    public int show_count;
    public int year_show;
    public String App_Id = "";
    public String App_Type = "";
    public String App_Name = "";
    public String Student_Type = "";

    /* renamed from: com.psychiatrygarden.activity.SwitchIdentitiesActivity$2, reason: invalid class name */
    public class AnonymousClass2 extends BaseQuickAdapter<CheckBoxBean.CheckBoxBean2, BaseViewHolder> {
        public AnonymousClass2(int layoutResId, List data) {
            super(layoutResId, data);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(CheckBoxBean.CheckBoxBean2 checkBoxBean2, View view) throws SQLException {
            SwitchIdentitiesActivity.this.showProgressDialog();
            SwitchIdentitiesActivity.this.App_Id = checkBoxBean2.getApp_id();
            SwitchIdentitiesActivity.this.App_Type = checkBoxBean2.getApp_type();
            SwitchIdentitiesActivity.this.App_Name = checkBoxBean2.getApp_name();
            SwitchIdentitiesActivity.this.Student_Type = checkBoxBean2.getStuden_type();
            SwitchIdentitiesActivity.this.App_Position = checkBoxBean2.getPosition();
            SwitchIdentitiesActivity.this.App_ISTIKU = checkBoxBean2.isApp_ISTIKU();
            SwitchIdentitiesActivity.this.show_count = checkBoxBean2.getShow_count();
            SwitchIdentitiesActivity.this.year_show = checkBoxBean2.getYear_show();
            SwitchIdentitiesActivity.this.app_title = checkBoxBean2.getApp_title();
            String str = CommonParameter.APP_TIKU_MARK;
            SwitchIdentitiesActivity switchIdentitiesActivity = SwitchIdentitiesActivity.this;
            SharePreferencesUtils.writeStrConfig(str, switchIdentitiesActivity.App_Type, switchIdentitiesActivity.mContext);
            String str2 = CommonParameter.App_Id;
            SwitchIdentitiesActivity switchIdentitiesActivity2 = SwitchIdentitiesActivity.this;
            SharePreferencesUtils.writeStrConfig(str2, switchIdentitiesActivity2.App_Id, switchIdentitiesActivity2.mContext);
            String str3 = CommonParameter.App_Name;
            SwitchIdentitiesActivity switchIdentitiesActivity3 = SwitchIdentitiesActivity.this;
            SharePreferencesUtils.writeStrConfig(str3, switchIdentitiesActivity3.App_Name, switchIdentitiesActivity3.mContext);
            String str4 = CommonParameter.App_Type;
            SwitchIdentitiesActivity switchIdentitiesActivity4 = SwitchIdentitiesActivity.this;
            SharePreferencesUtils.writeStrConfig(str4, switchIdentitiesActivity4.App_Type, switchIdentitiesActivity4.mContext);
            String str5 = CommonParameter.Student_Type;
            SwitchIdentitiesActivity switchIdentitiesActivity5 = SwitchIdentitiesActivity.this;
            SharePreferencesUtils.writeStrConfig(str5, switchIdentitiesActivity5.Student_Type, switchIdentitiesActivity5.mContext);
            String str6 = CommonParameter.App_Position;
            SwitchIdentitiesActivity switchIdentitiesActivity6 = SwitchIdentitiesActivity.this;
            SharePreferencesUtils.writeIntConfig(str6, switchIdentitiesActivity6.App_Position, switchIdentitiesActivity6.mContext);
            String str7 = CommonParameter.show_count;
            SwitchIdentitiesActivity switchIdentitiesActivity7 = SwitchIdentitiesActivity.this;
            SharePreferencesUtils.writeIntConfig(str7, switchIdentitiesActivity7.show_count, switchIdentitiesActivity7.mContext);
            String str8 = CommonParameter.year_show;
            SwitchIdentitiesActivity switchIdentitiesActivity8 = SwitchIdentitiesActivity.this;
            SharePreferencesUtils.writeIntConfig(str8, switchIdentitiesActivity8.year_show, switchIdentitiesActivity8.mContext);
            String str9 = CommonParameter.app_title;
            SwitchIdentitiesActivity switchIdentitiesActivity9 = SwitchIdentitiesActivity.this;
            SharePreferencesUtils.writeStrConfig(str9, switchIdentitiesActivity9.app_title, switchIdentitiesActivity9.mContext);
            String str10 = CommonParameter.App_ISTIKU;
            SwitchIdentitiesActivity switchIdentitiesActivity10 = SwitchIdentitiesActivity.this;
            SharePreferencesUtils.writeBooleanConfig(str10, switchIdentitiesActivity10.App_ISTIKU, switchIdentitiesActivity10.mContext);
            SharePreferencesUtils.writeBooleanConfig(CommonParameter.APP_TIKU_MARK_zhiyeyishi, false, SwitchIdentitiesActivity.this.mContext);
            ProjectApp.listSubmitData.clear();
            SharePreferencesUtils.writeIntConfig(CommonParameter.ISCESHITIKU, 0, SwitchIdentitiesActivity.this.mContext);
            SharePreferencesUtils.writeStrConfig(CommonParameter.questionids, "", SwitchIdentitiesActivity.this);
            ProjectApp.mComDb.mClearData();
            ProjectApp.mComDb.getDaoSession().getWrongBeanDao().deleteAll();
            ProjectApp.mComDb.getDaoSession().getAnsweredQuestionBeanDao().deleteAll();
            ProjectApp.mComDb.getDaoSession().getFavoritesBeanDao().deleteAll();
            ProjectApp.mComDb.getDaoSession().getNotesBeanDao().deleteAll();
            EventBus.getDefault().post("QuestionHomeAdPic");
            EventBus.getDefault().post("qiehuan");
            EventBus.getDefault().post("isIJINGYANLogin");
            EventBus.getDefault().post("isReploadData");
            EventBus.getDefault().post(EventBusConstant.VIDEO_COURSE_DAKA);
            EventBus.getDefault().post("xitongxiaoxi");
            EventBus.getDefault().post("upIndexList");
            EventBus.getDefault().post("PersonalCenterFragment");
            if (!UserConfig.getUserId().equals("")) {
                EventBus.getDefault().post(EventBusConstant.TIKU_ANSWER_SELF_DATA);
                EventBus.getDefault().post("personData");
            }
            SwitchIdentitiesActivity.this.setResult(-1);
            SwitchIdentitiesActivity.this.hideProgressDialog();
            SwitchIdentitiesActivity.this.finish();
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(BaseViewHolder vHolder, final CheckBoxBean.CheckBoxBean2 item) {
            TextView textView = (TextView) vHolder.getView(R.id.tv_register_select);
            LinearLayout linearLayout = (LinearLayout) vHolder.getView(R.id.line_id);
            ImageView imageView = (ImageView) vHolder.getView(R.id.checkBox1);
            textView.setText(item.getApp_name());
            if (SharePreferencesUtils.readStrConfig(CommonParameter.App_Name, SwitchIdentitiesActivity.this.mContext, "").equals(item.getApp_name())) {
                imageView.setVisibility(0);
            } else {
                imageView.setVisibility(8);
            }
            linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ko
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws SQLException {
                    this.f12642c.lambda$convert$0(item, view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        if (SharePreferencesUtils.readStrConfig(CommonParameter.APP_TIKU_MARK, this.mContext).equals("")) {
            ToastUtil.shortToast(this, "身份已重置,请选择身份！");
        } else {
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        Intent intent = new Intent(this, (Class<?>) SwitchIdentitiesActivity.class);
        intent.putExtra("position", i2 + 1);
        intent.putExtra("flag", true);
        startActivityForResult(intent, 111);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        boolean booleanExtra = getIntent().getBooleanExtra("flag", false);
        this.flag = booleanExtra;
        if (booleanExtra) {
            this.position = getIntent().getExtras().getInt("position");
            recyclerView.setAdapter(new AnonymousClass2(R.layout.adapter_register_select, new CheckBoxBean(this.position).getmCheckBoxBean2()));
            return;
        }
        this.mBtnActionbarBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.io
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12535c.lambda$init$0(view);
            }
        });
        BaseQuickAdapter<CheckBoxBean.MarkDataBean, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<CheckBoxBean.MarkDataBean, BaseViewHolder>(R.layout.activity_select_mark, new CheckBoxBean().getMarks()) { // from class: com.psychiatrygarden.activity.SwitchIdentitiesActivity.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, CheckBoxBean.MarkDataBean item) {
                helper.setText(R.id.marktitle, item.getTitle()).setText(R.id.markhidetitle, item.getHideTitle());
            }
        };
        recyclerView.setAdapter(baseQuickAdapter);
        baseQuickAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.jo
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                this.f12567c.lambda$init$1(baseQuickAdapter2, view, i2);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            finish();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("切换身份");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4 || event.getRepeatCount() != 0) {
            return false;
        }
        if (!SharePreferencesUtils.readStrConfig(CommonParameter.APP_TIKU_MARK, this.mContext).equals("")) {
            finish();
            return true;
        }
        if (this.flag) {
            finish();
            return true;
        }
        ToastUtil.shortToast(this, "身份已重置,请选择身份！");
        return true;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_select_identity_show_frist);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
