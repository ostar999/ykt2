package com.psychiatrygarden.activity.courselist.course;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.ViewPagerHelper;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.indicators.LinePagerIndicator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.courselist.CourseCollectEditActivity;
import com.psychiatrygarden.activity.courselist.CourseDirectoryNewFragment;
import com.psychiatrygarden.activity.courselist.CourseNoteExportActivity;
import com.psychiatrygarden.activity.courselist.CurriculumDownLoadManageActivity;
import com.psychiatrygarden.activity.courselist.bean.QRCode;
import com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryCombineListFragment;
import com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryMaterialsFragment;
import com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryNoteFragment;
import com.psychiatrygarden.bean.CourseDirectoryBeanKt;
import com.psychiatrygarden.bean.TabBean;
import com.psychiatrygarden.bean.TabType;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.fragmenthome.BaseViewPagerAdapter;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.NavigationUtilKt;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CourseWxPopupNew;
import com.psychiatrygarden.widget.GoodsSimplePagerTitleView;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CourseDirectoryActivity extends BaseActivity implements View.OnClickListener {
    public static final String EXTRA_DATA_COURSE_HAVE_COLLECT = "course_have_collect";
    public static final String EXTRA_DATA_COURSE_HAVE_DOWNLOAD = "course_have_download";
    public static final String EXTRA_DATA_COURSE_ID = "course_id";
    public static final String EXTRA_DATA_COURSE_TYPE = "course_type";
    public static final String EXTRA_DATA_COURSE_UPDATE_CHAPTER_ID = "course_update_chapter_id";
    public static final String EXTRA_DATA_COURSE_UPDATE_ID = "course_update_id";
    private static final String KEEP_QRCODE = "keep_qrcode";
    private String courseCover;
    private String courseTitle;
    private ImageView imgAnswer;
    private ImageView imgDownload;
    private ImageView imgNoteExport;
    private View ivEditCollect;
    private MagicIndicator mMagicIndicator;
    private QRCode qrCode;
    private String[] tabTitles;
    private String updateChapterId;
    private String updateId;
    private View viewGap;
    private ViewPager viewpager;
    private boolean haveNoteTab = false;
    private boolean showCourseDownloadEdit = false;
    private boolean isFirstEntry = false;
    private String courseId = "test_id";
    private String courseType = "0";
    private List<TabBean> dataTab = new ArrayList();
    private boolean haveDownload = false;
    private boolean haveCollect = false;
    private boolean innerTabIsCollect = false;

    /* renamed from: com.psychiatrygarden.activity.courselist.course.CourseDirectoryActivity$5, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] $SwitchMap$com$psychiatrygarden$bean$TabType;

        static {
            int[] iArr = new int[TabType.values().length];
            $SwitchMap$com$psychiatrygarden$bean$TabType = iArr;
            try {
                iArr[TabType.NOTE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$psychiatrygarden$bean$TabType[TabType.HANDOUT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$psychiatrygarden$bean$TabType[TabType.CATALOGUE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private void getQRCode() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("course_id", this.courseId);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.courseQRCode, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.courselist.course.CourseDirectoryActivity.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                CourseDirectoryActivity.this.imgAnswer.setVisibility(8);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String t2) {
                super.onSuccess((AnonymousClass4) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (jSONObject.optString("code").equals("200")) {
                        QRCode qRCode = (QRCode) new Gson().fromJson(jSONObject.optString("data"), QRCode.class);
                        CourseDirectoryActivity.this.qrCode = qRCode;
                        if (qRCode == null || !"1".equals(CourseDirectoryActivity.this.qrCode.is_open_qrcode())) {
                            CourseDirectoryActivity.this.imgAnswer.setVisibility(8);
                        } else {
                            CourseDirectoryActivity.this.imgAnswer.setVisibility(0);
                            if (CourseDirectoryActivity.this.isFirstEntry) {
                                CourseDirectoryActivity courseDirectoryActivity = CourseDirectoryActivity.this;
                                courseDirectoryActivity.showWxWRCode(courseDirectoryActivity.qrCode);
                            }
                        }
                    } else {
                        CourseDirectoryActivity.this.imgAnswer.setVisibility(8);
                    }
                } catch (Exception e2) {
                    CourseDirectoryActivity.this.imgAnswer.setVisibility(8);
                    Log.e("Error Line313:", e2.getMessage());
                }
            }
        });
    }

    private void getTabList() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("course_id", this.courseId);
        ajaxParams.put("type", this.courseType);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.courseDirectoryTab, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.courselist.course.CourseDirectoryActivity.3
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
                super.onSuccess((AnonymousClass3) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (jSONObject.optString("code").equals("200")) {
                        CourseDirectoryActivity.this.initTabColumn((List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<TabBean>>() { // from class: com.psychiatrygarden.activity.courselist.course.CourseDirectoryActivity.3.1
                        }.getType()));
                    }
                } catch (Exception e2) {
                    Log.e("Error Line313:", e2.getMessage());
                }
            }
        });
    }

    public static void goToCourseDirectoryActivity(Context context, String courseId, String courseType) {
        Intent intent = new Intent(context, (Class<?>) CourseDirectoryActivity.class);
        intent.putExtra("course_id", courseId);
        intent.putExtra(EXTRA_DATA_COURSE_TYPE, courseType);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initTabColumn(@NonNull List<TabBean> tab) throws Resources.NotFoundException {
        this.haveNoteTab = false;
        if (!tab.isEmpty()) {
            this.dataTab = tab;
            this.tabTitles = new String[tab.size()];
            for (int i2 = 0; i2 < tab.size(); i2++) {
                this.tabTitles[i2] = tab.get(i2).getValue();
            }
        }
        ArrayList arrayList = new ArrayList();
        for (int i3 = 0; i3 < tab.size(); i3++) {
            int i4 = AnonymousClass5.$SwitchMap$com$psychiatrygarden$bean$TabType[CourseDirectoryBeanKt.tabTypeGetType(tab.get(i3).getCode()).ordinal()];
            if (i4 == 1) {
                Bundle bundle = new Bundle();
                bundle.putString("course_id", this.courseId);
                arrayList.add(new BaseViewPagerAdapter.PagerInfo(tab.get(i3).getValue(), CourseDirectoryNoteFragment.class, bundle));
                this.haveNoteTab = true;
            } else if (i4 == 2) {
                Bundle bundle2 = new Bundle();
                bundle2.putString("course_id", this.courseId);
                arrayList.add(new BaseViewPagerAdapter.PagerInfo(tab.get(i3).getValue(), CourseDirectoryMaterialsFragment.class, bundle2));
            } else if (i4 == 3) {
                if ("3".equals(this.courseType)) {
                    Bundle bundle3 = new Bundle();
                    bundle3.putString("course_id", this.courseId);
                    arrayList.add(new BaseViewPagerAdapter.PagerInfo(tab.get(i3).getValue(), CourseDirectoryCombineListFragment.class, bundle3));
                } else {
                    Bundle bundle4 = new Bundle();
                    bundle4.putString(EXTRA_DATA_COURSE_TYPE, this.courseType);
                    bundle4.putString("course_id", this.courseId);
                    bundle4.putString(EXTRA_DATA_COURSE_UPDATE_ID, this.updateId);
                    bundle4.putString(EXTRA_DATA_COURSE_UPDATE_CHAPTER_ID, this.updateChapterId);
                    bundle4.putBoolean(EXTRA_DATA_COURSE_HAVE_COLLECT, this.haveCollect);
                    bundle4.putBoolean(EXTRA_DATA_COURSE_HAVE_DOWNLOAD, this.haveDownload);
                    arrayList.add(new BaseViewPagerAdapter.PagerInfo(tab.get(i3).getValue(), CourseDirectoryNewFragment.class, bundle4));
                }
            }
        }
        if (tab.size() == 1 && CourseDirectoryBeanKt.tabTypeGetType(tab.get(0).getCode()) == TabType.CATALOGUE) {
            this.mMagicIndicator.setVisibility(8);
            this.viewGap.setVisibility(8);
        } else {
            this.mMagicIndicator.setVisibility(0);
            this.viewGap.setVisibility(0);
        }
        this.viewpager.setAdapter(new BaseViewPagerAdapter(this.mContext, getSupportFragmentManager(), arrayList));
        this.viewpager.setCurrentItem(0);
        this.viewpager.setOffscreenPageLimit(2);
        this.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.courselist.course.CourseDirectoryActivity.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int state) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int position) {
                CourseDirectoryActivity.this.setSelectPosition(position);
            }
        });
        CommonNavigator commonNavigator = new CommonNavigator(this.mContext);
        commonNavigator.setSkimOver(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() { // from class: com.psychiatrygarden.activity.courselist.course.CourseDirectoryActivity.2
            @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
            public int getCount() {
                return CourseDirectoryActivity.this.tabTitles.length;
            }

            @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(2);
                linePagerIndicator.setLineHeight(UIUtil.dip2px(context, 3.0d));
                linePagerIndicator.setLineWidth(UIUtil.dip2px(context, 16.0d));
                linePagerIndicator.setRoundRadius(UIUtil.dip2px(context, 3.0d));
                linePagerIndicator.setStartInterpolator(new AccelerateInterpolator());
                linePagerIndicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                linePagerIndicator.setYOffset(0.0f);
                if (SkinManager.getCurrentSkinType(CourseDirectoryActivity.this.mContext) == 1) {
                    linePagerIndicator.setColors(Integer.valueOf(ContextCompat.getColor(CourseDirectoryActivity.this, R.color.main_theme_color_night)));
                } else {
                    linePagerIndicator.setColors(Integer.valueOf(ContextCompat.getColor(CourseDirectoryActivity.this, R.color.main_theme_color)));
                }
                return linePagerIndicator;
            }

            @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter
            public IPagerTitleView getTitleView(Context context, final int index) {
                CourseDirectoryActivity courseDirectoryActivity;
                int i5;
                CourseDirectoryActivity courseDirectoryActivity2;
                int i6;
                GoodsSimplePagerTitleView goodsSimplePagerTitleView = new GoodsSimplePagerTitleView(CourseDirectoryActivity.this) { // from class: com.psychiatrygarden.activity.courselist.course.CourseDirectoryActivity.2.1
                    @Override // com.psychiatrygarden.widget.GoodsSimplePagerTitleView, cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.SimplePagerTitleView, cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView
                    public void onDeselected(int index2, int totalCount) {
                        super.onDeselected(index2, totalCount);
                        setTextSize(2, 14.0f);
                    }

                    @Override // com.psychiatrygarden.widget.GoodsSimplePagerTitleView, cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles.SimplePagerTitleView, cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView
                    public void onSelected(int index2, int totalCount) {
                        super.onSelected(index2, totalCount);
                        setTextSize(2, 16.0f);
                    }
                };
                if (SkinManager.getCurrentSkinType(CourseDirectoryActivity.this.mContext) == 1) {
                    courseDirectoryActivity = CourseDirectoryActivity.this;
                    i5 = R.color.first_txt_color_night;
                } else {
                    courseDirectoryActivity = CourseDirectoryActivity.this;
                    i5 = R.color.first_txt_color;
                }
                int color = courseDirectoryActivity.getColor(i5);
                if (SkinManager.getCurrentSkinType(CourseDirectoryActivity.this.mContext) == 1) {
                    courseDirectoryActivity2 = CourseDirectoryActivity.this;
                    i6 = R.color.third_txt_color_night;
                } else {
                    courseDirectoryActivity2 = CourseDirectoryActivity.this;
                    i6 = R.color.third_txt_color;
                }
                goodsSimplePagerTitleView.setNormalColor(courseDirectoryActivity2.getColor(i6));
                goodsSimplePagerTitleView.setSelectedColor(color);
                goodsSimplePagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.course.CourseDirectoryActivity.2.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v2) throws Resources.NotFoundException {
                        CourseDirectoryActivity.this.viewpager.setCurrentItem(index);
                    }
                });
                goodsSimplePagerTitleView.setText(CourseDirectoryActivity.this.tabTitles[index].trim());
                return goodsSimplePagerTitleView;
            }
        });
        commonNavigator.setAdjustMode(true);
        this.mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(this.mMagicIndicator, this.viewpager);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSelectPosition(int position) {
        int i2 = AnonymousClass5.$SwitchMap$com$psychiatrygarden$bean$TabType[CourseDirectoryBeanKt.tabTypeGetType(this.dataTab.get(position).getCode()).ordinal()];
        if (i2 == 1) {
            this.imgDownload.setVisibility(8);
            this.imgNoteExport.setVisibility(0);
            this.ivEditCollect.setVisibility(8);
        } else if (i2 == 2) {
            this.imgDownload.setVisibility(8);
            this.imgNoteExport.setVisibility(8);
            this.ivEditCollect.setVisibility(8);
        } else {
            if (i2 != 3) {
                return;
            }
            this.imgDownload.setVisibility(0);
            this.imgNoteExport.setVisibility(8);
            this.ivEditCollect.setVisibility(this.innerTabIsCollect ? 0 : 8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showWxWRCode(QRCode qrData) {
        new XPopup.Builder(this).asCustom(new CourseWxPopupNew(this, qrData.getWechat_qrcode(), qrData.getWechat_tips(), "微信号: " + qrData.getWechat_number())).toggle();
        SharePreferencesUtils.writeBooleanConfig(KEEP_QRCODE + this.courseId, false, this);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.courseId = getIntent().getStringExtra("course_id");
        this.courseType = getIntent().getStringExtra(EXTRA_DATA_COURSE_TYPE);
        this.updateId = getIntent().getStringExtra(EXTRA_DATA_COURSE_UPDATE_ID);
        this.updateChapterId = getIntent().getStringExtra(EXTRA_DATA_COURSE_UPDATE_CHAPTER_ID);
        View viewFindViewById = findViewById(R.id.ivEditCollect);
        this.ivEditCollect = viewFindViewById;
        viewFindViewById.setOnClickListener(this);
        ImageView imageView = (ImageView) findViewById(R.id.imgCourseDirectoryNoteExport);
        this.imgNoteExport = imageView;
        imageView.setOnClickListener(this);
        ImageView imageView2 = (ImageView) findViewById(R.id.imgCourseDirectoryAnswer);
        this.imgAnswer = imageView2;
        imageView2.setOnClickListener(this);
        ImageView imageView3 = (ImageView) findViewById(R.id.imgCourseDirectoryDownload);
        this.imgDownload = imageView3;
        imageView3.setOnClickListener(this);
        ((ImageView) findViewById(R.id.imgCourseDirectoryBack)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.course.o
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11878c.lambda$init$0(view);
            }
        });
        ((TextView) findViewById(R.id.tvCourseDirectoryTitle)).setText("课程目录");
        MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.orderListMagicIndicator);
        this.mMagicIndicator = magicIndicator;
        magicIndicator.setVisibility(8);
        View viewFindViewById2 = findViewById(R.id.viewGap);
        this.viewGap = viewFindViewById2;
        viewFindViewById2.setVisibility(8);
        this.viewpager = (ViewPager) findViewById(R.id.orderListViewPage);
        getTabList();
        getQRCode();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        switch (v2.getId()) {
            case R.id.imgCourseDirectoryAnswer /* 2131363636 */:
                QRCode qRCode = this.qrCode;
                if (qRCode != null) {
                    showWxWRCode(qRCode);
                    break;
                } else {
                    this.isFirstEntry = true;
                    getQRCode();
                    break;
                }
            case R.id.imgCourseDirectoryDownload /* 2131363638 */:
                NavigationUtilKt.getToCurriculumDownLoadManage(this, this.courseId, this.courseType, this.courseTitle, this.courseCover);
                break;
            case R.id.imgCourseDirectoryNoteExport /* 2131363639 */:
                CourseNoteExportActivity.gotToExportNote(this, this.courseId);
                break;
            case R.id.ivEditCollect /* 2131363937 */:
                if (!this.showCourseDownloadEdit) {
                    CourseCollectEditActivity.gotoCollectEditActivity(this, this.courseId, this.courseType);
                    break;
                } else {
                    startActivity(new Intent(this, (Class<?>) CurriculumDownLoadManageActivity.class).putExtra("editMode", true).putExtra("title", getIntent().getStringExtra("title")).putExtra("manage", true).putExtra("course_id", this.courseId));
                    break;
                }
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
        setNewStyleStatusBarColor();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        str.hashCode();
        switch (str) {
            case "directory_tab_select_collect":
                this.ivEditCollect.setVisibility(0);
                this.innerTabIsCollect = true;
                break;
            case "ADD_NOTE":
                if (!this.haveNoteTab) {
                    getTabList();
                    break;
                } else {
                    EventBus.getDefault().post(EventBusConstant.REFRESH_NOTE);
                    break;
                }
            case "directory_tab_select_all":
            case "directory_tab_select_download":
                this.ivEditCollect.setVisibility(8);
                this.innerTabIsCollect = false;
                break;
            case "DEL_NOTE":
                if (this.haveNoteTab) {
                    getTabList();
                    break;
                }
                break;
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, android.app.Activity
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.isFirstEntry = SharePreferencesUtils.readBooleanConfig(KEEP_QRCODE + this.courseId, true, this);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_course_directory);
    }

    public void setCourseMsg(String courseTitle, String courseCover) {
        ViewPager viewPager;
        this.courseCover = courseCover;
        this.courseTitle = courseTitle;
        List<TabBean> list = this.dataTab;
        if (list == null || (viewPager = this.viewpager) == null || CourseDirectoryBeanKt.tabTypeGetType(list.get(viewPager.getCurrentItem()).getCode()) != TabType.CATALOGUE) {
            return;
        }
        this.imgDownload.setVisibility(0);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }

    public void updateEditShow(boolean show) {
        this.ivEditCollect.setVisibility((show || this.innerTabIsCollect) ? 0 : 8);
        this.showCourseDownloadEdit = show;
    }

    public static void goToCourseDirectoryActivity(Context context, String courseId, String courseType, String title, int extra) {
        Intent intent = new Intent(context, (Class<?>) CourseDirectoryActivity.class);
        intent.putExtra("course_id", courseId);
        intent.putExtra(EXTRA_DATA_COURSE_TYPE, courseType);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    public static void goToCourseDirectoryActivity(Context context, String courseId, String courseType, String updateId) {
        Intent intent = new Intent(context, (Class<?>) CourseDirectoryActivity.class);
        intent.putExtra("course_id", courseId);
        intent.putExtra(EXTRA_DATA_COURSE_TYPE, courseType);
        intent.putExtra(EXTRA_DATA_COURSE_UPDATE_CHAPTER_ID, updateId);
        context.startActivity(intent);
    }
}
