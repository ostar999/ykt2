package com.psychiatrygarden.activity.courselist;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.courselist.bean.CurriculumItemBean;
import com.psychiatrygarden.activity.courselist.fragment.CurriculumFragment;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CourseAllActivity extends BaseActivity {
    public AppBarLayout appbarlayout;
    public String course_id;
    public TextView goumai;
    public RelativeLayout goumai_tv;
    public ImageView iconBack;
    public LinearLayout linepaxview;
    public TextView pressTxt;
    public TextView proTitle;
    public TextView prodesc;
    public TextView pronum;
    public TextView proprice;
    public RelativeLayout relPrice;
    public TextView services_tv;
    public TextView title;
    public String type;
    public String catory_id = "";
    public ArrayList<String> setlist = new ArrayList<>();

    /* renamed from: com.psychiatrygarden.activity.courselist.CourseAllActivity$1, reason: invalid class name */
    public class AnonymousClass1 extends AjaxCallBack<String> {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(CurriculumItemBean.DataDTO dataDTO, View view) {
            CourseAllActivity.this.verifyPermissions(dataDTO);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1(CurriculumItemBean.DataDTO dataDTO, View view) {
            CommonUtil.onlineService(CourseAllActivity.this, dataDTO.getCs());
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onStart() {
            super.onStart();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String t2) {
            final CurriculumItemBean.DataDTO dataDTO;
            super.onSuccess((AnonymousClass1) t2);
            try {
                if (!"200".equals(new JSONObject(t2).optString("code")) || (dataDTO = (CurriculumItemBean.DataDTO) new Gson().fromJson(new JSONObject(t2).optString("data"), CurriculumItemBean.DataDTO.class)) == null) {
                    return;
                }
                CourseAllActivity.this.proTitle.setText(dataDTO.getTitle());
                CourseAllActivity.this.prodesc.setText(dataDTO.getDescription());
                CourseAllActivity.this.pronum.setText(String.format(Locale.CHINA, "%d门课程", Integer.valueOf(((List) dataDTO.getSetMeal()).size())));
                CourseAllActivity.this.proprice.setText(dataDTO.getPrice());
                try {
                    if ("1".equals(dataDTO.getIs_permission())) {
                        CourseAllActivity.this.goumai_tv.setVisibility(8);
                        CourseAllActivity.this.pressTxt.setText("已购买");
                    } else {
                        CourseAllActivity.this.goumai_tv.setVisibility(0);
                        CourseAllActivity.this.pressTxt.setText(dataDTO.getSales_volume() + "人购买");
                    }
                } catch (Exception e2) {
                    CourseAllActivity.this.proprice.setText(dataDTO.getPrice());
                    e2.printStackTrace();
                }
                CurriculumFragment curriculumFragment = new CurriculumFragment();
                Bundle bundle = new Bundle();
                bundle.putBinder("dataDTO", dataDTO);
                bundle.putString("id", "" + CourseAllActivity.this.catory_id);
                bundle.putString("type", CourseAllActivity.this.type);
                bundle.putString("course_id", CourseAllActivity.this.course_id);
                bundle.putString(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, "" + dataDTO.getActivityId());
                bundle.putString("title", "合集code");
                bundle.putStringArrayList("set_meal_id", CourseAllActivity.this.setlist);
                curriculumFragment.setArguments(bundle);
                if (CourseAllActivity.this.findFragment(CurriculumFragment.class) == null) {
                    CourseAllActivity.this.loadRootFragment(R.id.fid, curriculumFragment);
                } else {
                    CourseAllActivity.this.replaceFragment(curriculumFragment, false);
                }
                CourseAllActivity.this.goumai.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.d
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f11899c.lambda$onSuccess$0(dataDTO, view);
                    }
                });
                CourseAllActivity.this.services_tv.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.e
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f11903c.lambda$onSuccess$1(dataDTO, view);
                    }
                });
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }

    private void getCourseListData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("course_id", "" + this.course_id);
        ajaxParams.put("type", "" + this.type);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.curriculumdetailUrl, ajaxParams, new AnonymousClass1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(AppBarLayout appBarLayout, int i2) {
        this.linepaxview.setAlpha(1.0f - Math.abs((i2 * 1.0f) / appBarLayout.getTotalScrollRange()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$verifyPermissions$2() {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.setlist = getIntent().getBundleExtra("bundle").getStringArrayList("set_meal_id");
        this.catory_id = getIntent().getBundleExtra("bundle").getString("catory_id");
        this.course_id = getIntent().getBundleExtra("bundle").getString("course_id");
        this.type = getIntent().getBundleExtra("bundle").getString("type");
        this.relPrice = (RelativeLayout) findViewById(R.id.relPrice);
        this.goumai_tv = (RelativeLayout) findViewById(R.id.goumai_tv);
        this.goumai = (TextView) findViewById(R.id.goumai);
        this.services_tv = (TextView) findViewById(R.id.services_tv);
        this.iconBack = (ImageView) findViewById(R.id.nav_back);
        this.title = (TextView) findViewById(R.id.title);
        this.proTitle = (TextView) findViewById(R.id.proTitle);
        this.prodesc = (TextView) findViewById(R.id.prodesc);
        this.pronum = (TextView) findViewById(R.id.pronum);
        this.pressTxt = (TextView) findViewById(R.id.pressTxt);
        this.proprice = (TextView) findViewById(R.id.proprice);
        this.linepaxview = (LinearLayout) findViewById(R.id.linepaxview);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbarlayout);
        this.appbarlayout = appBarLayout;
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.psychiatrygarden.activity.courselist.c
            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout2, int i2) {
                this.f11842a.lambda$init$0(appBarLayout2, i2);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if ("BuySuccess".equals(str)) {
            getCourseListData();
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, android.app.Activity
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPostResume() {
        super.onPostResume();
        getCourseListData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_course_all);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.iconBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11834c.lambda$setListenerForWidget$1(view);
            }
        });
    }

    public void verifyPermissions(CurriculumItemBean.DataDTO dataDTO) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("module_name", "course");
        ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, "" + dataDTO.getActivityId());
        ajaxParams.put("id", "" + dataDTO.getId());
        MemInterface.getInstance().getMemData(this, ajaxParams, false, 0);
        MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.courselist.a
            @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
            public final void mUShareListener() {
                CourseAllActivity.lambda$verifyPermissions$2();
            }
        });
    }
}
