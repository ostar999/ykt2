package com.psychiatrygarden.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ExpandableListView;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.vip.MemberCenterActivity;
import com.psychiatrygarden.adapter.SubjectFWNExpandableListAdapter;
import com.psychiatrygarden.bean.ComYearFWNItemBean;
import com.psychiatrygarden.bean.FavoritesBean;
import com.psychiatrygarden.bean.FavoritesBeanDao;
import com.psychiatrygarden.bean.NotesBean;
import com.psychiatrygarden.bean.NotesBeanDao;
import com.psychiatrygarden.bean.WrongBean;
import com.psychiatrygarden.bean.WrongBeanDao;
import com.psychiatrygarden.bean.oneTitleDb;
import com.psychiatrygarden.bean.twoTitleDb;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.ExportDescriptionPop;
import com.yikaobang.yixue.R;
import de.greenrobot.dao.query.WhereCondition;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

/* loaded from: classes5.dex */
public class YearFWNXiZhiActivity extends BaseActivity {
    private ExpandableListView elv_subject;
    private SubjectFWNExpandableListAdapter mAdapter;
    public ArrayList<oneTitleDb> sListDb;
    private String type;
    private int tempPosition = 0;
    private final String notefileurl = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/yikaobang/" + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext) + "/";

    public class ProgressBarAsyncTask extends AsyncTask<Integer, Integer, String> {
        public ProgressBarAsyncTask() {
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
            YearFWNXiZhiActivity.this.showProgressDialog("加载中...");
        }

        @Override // android.os.AsyncTask
        public void onProgressUpdate(Integer... values) {
        }

        @Override // android.os.AsyncTask
        public String doInBackground(Integer... params) throws JSONException {
            YearFWNXiZhiActivity.this.setData();
            return null;
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(String result) {
            YearFWNXiZhiActivity yearFWNXiZhiActivity = YearFWNXiZhiActivity.this;
            YearFWNXiZhiActivity yearFWNXiZhiActivity2 = YearFWNXiZhiActivity.this;
            yearFWNXiZhiActivity.mAdapter = new SubjectFWNExpandableListAdapter(yearFWNXiZhiActivity2.mContext, yearFWNXiZhiActivity2.sListDb, yearFWNXiZhiActivity2.type);
            YearFWNXiZhiActivity.this.elv_subject.setAdapter(YearFWNXiZhiActivity.this.mAdapter);
            YearFWNXiZhiActivity.this.hideProgressDialog();
            if (YearFWNXiZhiActivity.this.sListDb.size() > 0) {
                YearFWNXiZhiActivity.this.findViewById(R.id.tv_empty).setVisibility(8);
            } else {
                YearFWNXiZhiActivity.this.findViewById(R.id.tv_empty).setVisibility(0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setListenerForWidget$0(ExpandableListView expandableListView, View view, int i2, int i3, long j2) {
        long[] list_questionid = this.sListDb.get(i2).getChapters().get(i3).getList_questionid();
        Intent intent = new Intent(this.mContext, (Class<?>) YearQuestionListActivity.class);
        intent.putExtra("list", list_questionid);
        intent.putExtra("year", this.sListDb.get(i2).getYear());
        intent.putExtra("title", getIntent().getStringExtra("type"));
        intent.putExtra("unit", this.sListDb.get(i2).getChapters().get(i3).getUnit());
        intent.putExtra("ISPractice", true);
        startActivity(intent);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(int i2) {
        int i3 = this.tempPosition;
        if (i3 != i2) {
            this.elv_subject.collapseGroup(i3);
            this.tempPosition = i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        if (getIntent().getStringExtra("type").equals("note") || getIntent().getStringExtra("type").equals("error") || getIntent().getStringExtra("type").equals("collect")) {
            if (UserConfig.getInstance().getUser().getIs_vip().equals("0")) {
                startActivity(new Intent(this, (Class<?>) MemberCenterActivity.class));
            } else {
                new XPopup.Builder(this).asCustom(new ExportDescriptionPop(this, getIntent().getExtras())).toggle();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setData() throws JSONException {
        JSONArray jSONArray;
        int i2;
        int i3;
        JSONArray jSONArray2;
        int i4;
        this.sListDb = new ArrayList<>();
        JSONArray jSONArray3 = new JSONArray();
        for (int i5 = 2016; i5 >= 2000; i5--) {
            jSONArray3.put(i5);
        }
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.JSON_YEAR, getApplicationContext(), jSONArray3.toString());
        if (strConfig.equals("")) {
            strConfig = jSONArray3.toString();
        }
        try {
            JSONArray jSONArray4 = new JSONArray(strConfig);
            int i6 = 0;
            int i7 = 0;
            while (i7 < jSONArray4.length()) {
                String string = jSONArray4.getString(i7);
                oneTitleDb onetitledb = new oneTitleDb();
                ArrayList arrayList = new ArrayList();
                onetitledb.setCate_name(string + "");
                onetitledb.setYear(string + "");
                if (this.type.equals("error")) {
                    onetitledb.setTotal(ProjectApp.mDaoSession.getWrongBeanDao().queryBuilder().where(WrongBeanDao.Properties.Year.eq(string), new WhereCondition[i6]).list().size() + "");
                    int i8 = i6;
                    while (i8 < 4) {
                        twoTitleDb twotitledb = new twoTitleDb();
                        if (i8 == 0) {
                            jSONArray2 = jSONArray4;
                            i4 = i7;
                            List<WrongBean> list = ProjectApp.mDaoSession.getWrongBeanDao().queryBuilder().where(WrongBeanDao.Properties.Year.eq(string), WrongBeanDao.Properties.Unit.eq("U1")).orderAsc(WrongBeanDao.Properties.Year_num).list();
                            twotitledb.setUnit("U1");
                            twotitledb.setYear(string + "");
                            twotitledb.setCate_name("U1");
                            twotitledb.setTotal(list.size() + "");
                            if (list.size() > 0) {
                                long[] jArr = new long[list.size()];
                                for (int i9 = 0; i9 < list.size(); i9++) {
                                    jArr[i9] = list.get(i9).getQuestion_id().longValue();
                                }
                                twotitledb.setList_questionid(jArr);
                                arrayList.add(twotitledb);
                            }
                        } else if (i8 == 1) {
                            jSONArray2 = jSONArray4;
                            i4 = i7;
                            List<WrongBean> list2 = ProjectApp.mDaoSession.getWrongBeanDao().queryBuilder().where(WrongBeanDao.Properties.Year.eq(string), WrongBeanDao.Properties.Unit.eq("U2")).orderAsc(WrongBeanDao.Properties.Year_num).list();
                            twotitledb.setUnit("U2");
                            twotitledb.setYear(string + "");
                            twotitledb.setCate_name("U2");
                            twotitledb.setTotal(list2.size() + "");
                            if (list2.size() > 0) {
                                long[] jArr2 = new long[list2.size()];
                                for (int i10 = 0; i10 < list2.size(); i10++) {
                                    jArr2[i10] = list2.get(i10).getQuestion_id().longValue();
                                }
                                twotitledb.setList_questionid(jArr2);
                                arrayList.add(twotitledb);
                            }
                        } else if (i8 == 2) {
                            jSONArray2 = jSONArray4;
                            i4 = i7;
                            List<WrongBean> list3 = ProjectApp.mDaoSession.getWrongBeanDao().queryBuilder().where(WrongBeanDao.Properties.Year.eq(string), WrongBeanDao.Properties.Unit.eq("U3")).orderAsc(WrongBeanDao.Properties.Year_num).list();
                            twotitledb.setUnit("U3");
                            twotitledb.setYear(string + "");
                            twotitledb.setCate_name("U3");
                            twotitledb.setTotal(list3.size() + "");
                            if (list3.size() > 0) {
                                long[] jArr3 = new long[list3.size()];
                                for (int i11 = 0; i11 < list3.size(); i11++) {
                                    jArr3[i11] = list3.get(i11).getQuestion_id().longValue();
                                }
                                twotitledb.setList_questionid(jArr3);
                                arrayList.add(twotitledb);
                            }
                        } else if (i8 != 3) {
                            jSONArray2 = jSONArray4;
                            i4 = i7;
                        } else {
                            jSONArray2 = jSONArray4;
                            i4 = i7;
                            List<WrongBean> list4 = ProjectApp.mDaoSession.getWrongBeanDao().queryBuilder().where(WrongBeanDao.Properties.Year.eq(string), WrongBeanDao.Properties.Unit.eq("U4")).orderAsc(WrongBeanDao.Properties.Year_num).list();
                            twotitledb.setUnit("U4");
                            twotitledb.setYear(string + "");
                            twotitledb.setCate_name("U4");
                            twotitledb.setTotal(list4.size() + "");
                            if (list4.size() > 0) {
                                long[] jArr4 = new long[list4.size()];
                                for (int i12 = 0; i12 < list4.size(); i12++) {
                                    jArr4[i12] = list4.get(i12).getQuestion_id().longValue();
                                }
                                twotitledb.setList_questionid(jArr4);
                                arrayList.add(twotitledb);
                            }
                        }
                        i8++;
                        jSONArray4 = jSONArray2;
                        i7 = i4;
                        i6 = 0;
                    }
                    jSONArray = jSONArray4;
                    i2 = i7;
                    i3 = i6;
                } else {
                    jSONArray = jSONArray4;
                    i2 = i7;
                    if (this.type.equals("collect")) {
                        onetitledb.setTotal(ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Year.eq(string), new WhereCondition[0]).list().size() + "");
                        for (int i13 = 0; i13 < 4; i13++) {
                            twoTitleDb twotitledb2 = new twoTitleDb();
                            if (i13 == 0) {
                                List<FavoritesBean> list5 = ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Year.eq(string), FavoritesBeanDao.Properties.Unit.eq("U1")).orderAsc(FavoritesBeanDao.Properties.Year_num).list();
                                twotitledb2.setUnit("U1");
                                twotitledb2.setYear(string + "");
                                twotitledb2.setCate_name("U1");
                                twotitledb2.setTotal(list5.size() + "");
                                if (list5.size() > 0) {
                                    long[] jArr5 = new long[list5.size()];
                                    for (int i14 = 0; i14 < list5.size(); i14++) {
                                        jArr5[i14] = list5.get(i14).getQuestion_id().longValue();
                                    }
                                    twotitledb2.setList_questionid(jArr5);
                                    arrayList.add(twotitledb2);
                                }
                            } else if (i13 == 1) {
                                List<FavoritesBean> list6 = ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Year.eq(string), FavoritesBeanDao.Properties.Unit.eq("U2")).orderAsc(FavoritesBeanDao.Properties.Year_num).list();
                                twotitledb2.setUnit("U2");
                                twotitledb2.setYear(string + "");
                                twotitledb2.setCate_name("U2");
                                twotitledb2.setTotal(list6.size() + "");
                                if (list6.size() > 0) {
                                    long[] jArr6 = new long[list6.size()];
                                    for (int i15 = 0; i15 < list6.size(); i15++) {
                                        jArr6[i15] = list6.get(i15).getQuestion_id().longValue();
                                    }
                                    twotitledb2.setList_questionid(jArr6);
                                    arrayList.add(twotitledb2);
                                }
                            } else if (i13 == 2) {
                                List<FavoritesBean> list7 = ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Year.eq(string), FavoritesBeanDao.Properties.Unit.eq("U3")).orderAsc(FavoritesBeanDao.Properties.Year_num).list();
                                twotitledb2.setUnit("U3");
                                twotitledb2.setYear(string + "");
                                twotitledb2.setCate_name("U3");
                                twotitledb2.setTotal(list7.size() + "");
                                if (list7.size() > 0) {
                                    long[] jArr7 = new long[list7.size()];
                                    for (int i16 = 0; i16 < list7.size(); i16++) {
                                        jArr7[i16] = list7.get(i16).getQuestion_id().longValue();
                                    }
                                    twotitledb2.setList_questionid(jArr7);
                                    arrayList.add(twotitledb2);
                                }
                            } else if (i13 == 3) {
                                List<FavoritesBean> list8 = ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Year.eq(string), FavoritesBeanDao.Properties.Unit.eq("U4")).orderAsc(FavoritesBeanDao.Properties.Year_num).list();
                                twotitledb2.setUnit("U4");
                                twotitledb2.setYear(string + "");
                                twotitledb2.setCate_name("U4");
                                twotitledb2.setTotal(list8.size() + "");
                                if (list8.size() > 0) {
                                    long[] jArr8 = new long[list8.size()];
                                    for (int i17 = 0; i17 < list8.size(); i17++) {
                                        jArr8[i17] = list8.get(i17).getQuestion_id().longValue();
                                    }
                                    twotitledb2.setList_questionid(jArr8);
                                    arrayList.add(twotitledb2);
                                }
                            }
                        }
                    } else if (this.type.equals("note")) {
                        onetitledb.setTotal(ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Year.eq(string), new WhereCondition[0]).list().size() + "");
                        for (int i18 = 0; i18 < 4; i18++) {
                            twoTitleDb twotitledb3 = new twoTitleDb();
                            if (i18 == 0) {
                                List<NotesBean> list9 = ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Year.eq(string), NotesBeanDao.Properties.Unit.eq("U1")).orderAsc(NotesBeanDao.Properties.Year_num).list();
                                twotitledb3.setUnit("U1");
                                twotitledb3.setYear(string + "");
                                twotitledb3.setCate_name("U1");
                                twotitledb3.setTotal(list9.size() + "");
                                if (list9.size() > 0) {
                                    long[] jArr9 = new long[list9.size()];
                                    for (int i19 = 0; i19 < list9.size(); i19++) {
                                        jArr9[i19] = list9.get(i19).getQuestion_id().longValue();
                                    }
                                    twotitledb3.setList_questionid(jArr9);
                                    arrayList.add(twotitledb3);
                                }
                            } else if (i18 == 1) {
                                List<NotesBean> list10 = ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Year.eq(string), NotesBeanDao.Properties.Unit.eq("U2")).orderAsc(NotesBeanDao.Properties.Year_num).list();
                                twotitledb3.setUnit("U2");
                                twotitledb3.setYear(string + "");
                                twotitledb3.setCate_name("U2");
                                twotitledb3.setTotal(list10.size() + "");
                                if (list10.size() > 0) {
                                    long[] jArr10 = new long[list10.size()];
                                    for (int i20 = 0; i20 < list10.size(); i20++) {
                                        jArr10[i20] = list10.get(i20).getQuestion_id().longValue();
                                    }
                                    twotitledb3.setList_questionid(jArr10);
                                    arrayList.add(twotitledb3);
                                }
                            } else if (i18 == 2) {
                                List<NotesBean> list11 = ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Year.eq(string), NotesBeanDao.Properties.Unit.eq("U3")).orderAsc(NotesBeanDao.Properties.Year_num).list();
                                twotitledb3.setUnit("U3");
                                twotitledb3.setYear(string + "");
                                twotitledb3.setCate_name("U3");
                                twotitledb3.setTotal(list11.size() + "");
                                if (list11.size() > 0) {
                                    long[] jArr11 = new long[list11.size()];
                                    for (int i21 = 0; i21 < list11.size(); i21++) {
                                        jArr11[i21] = list11.get(i21).getQuestion_id().longValue();
                                    }
                                    twotitledb3.setList_questionid(jArr11);
                                    arrayList.add(twotitledb3);
                                }
                            } else if (i18 == 3) {
                                List<NotesBean> list12 = ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Year.eq(string), NotesBeanDao.Properties.Unit.eq("U4")).orderAsc(NotesBeanDao.Properties.Year_num).list();
                                twotitledb3.setUnit("U4");
                                twotitledb3.setYear(string + "");
                                twotitledb3.setCate_name("U4");
                                twotitledb3.setTotal(list12.size() + "");
                                if (list12.size() > 0) {
                                    long[] jArr12 = new long[list12.size()];
                                    for (int i22 = 0; i22 < list12.size(); i22++) {
                                        jArr12[i22] = list12.get(i22).getQuestion_id().longValue();
                                    }
                                    twotitledb3.setList_questionid(jArr12);
                                    arrayList.add(twotitledb3);
                                }
                            }
                        }
                    }
                    i3 = 0;
                }
                onetitledb.setChapters(arrayList);
                if (Integer.parseInt(onetitledb.getTotal()) > 0) {
                    this.sListDb.add(onetitledb);
                }
                i7 = i2 + 1;
                i6 = i3;
                jSONArray4 = jSONArray;
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.elv_subject = (ExpandableListView) findViewById(R.id.elv_subject);
        ((SwipeRefreshLayout) findViewById(R.id.swipe1)).setEnabled(false);
        new ProgressBarAsyncTask().execute(1000);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!getIntent().getStringExtra("type").equals("note") && !getIntent().getStringExtra("type").equals("error") && !getIntent().getStringExtra("type").equals("collect")) {
            this.mBtnActionbarRight.setVisibility(8);
            return;
        }
        this.mBtnActionbarRight.setVisibility(0);
        this.mBtnActionbarRight.setText("导出");
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.mBtnActionbarRight.setTextColor(ContextCompat.getColor(this, R.color.white));
        } else {
            this.mBtnActionbarRight.setTextColor(ContextCompat.getColor(this, R.color.app_title_color_night));
        }
    }

    public void onEventMainThread(ComYearFWNItemBean mComSubFWNItemBean) {
        if (mComSubFWNItemBean.getmEventStr().equals("YearQuestionClearn") || mComSubFWNItemBean.getmEventStr().equals("YearQuestionClearnEdit")) {
            ArrayList<oneTitleDb> arrayList = this.sListDb;
            if (arrayList != null && arrayList.size() > 0) {
                for (int i2 = 0; i2 < this.sListDb.size(); i2++) {
                    List<twoTitleDb> chapters = this.sListDb.get(i2).getChapters();
                    if (chapters != null && chapters.size() > 0) {
                        for (int i3 = 0; i3 < chapters.size(); i3++) {
                            if (chapters.get(i3).getYear().equals(mComSubFWNItemBean.getYear()) && chapters.get(i3).getUnit().equals(mComSubFWNItemBean.getUnit())) {
                                long[] long_questionitems = mComSubFWNItemBean.getLong_questionitems();
                                try {
                                    int i4 = Integer.parseInt(chapters.get(i3).getTotal()) - long_questionitems.length;
                                    this.sListDb.get(i2).setTotal((Integer.parseInt(this.sListDb.get(i2).getTotal()) - i4) + "");
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                                if (long_questionitems != null && long_questionitems.length > 0) {
                                    chapters.get(i3).setTotal(long_questionitems.length + "");
                                    this.sListDb.get(i2).getChapters().get(i3).setList_questionid(long_questionitems);
                                } else if (this.sListDb.size() > 0) {
                                    if (this.sListDb.get(i2).getChapters().size() > 1) {
                                        chapters.remove(chapters.get(i3));
                                    } else if (chapters.get(i3).getYear().equals(mComSubFWNItemBean.getYear()) && chapters.get(i3).getUnit().equals(mComSubFWNItemBean.getUnit())) {
                                        ArrayList<oneTitleDb> arrayList2 = this.sListDb;
                                        arrayList2.remove(arrayList2.get(i2));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            this.mAdapter.setListData(this.sListDb);
            if (this.sListDb.size() > 0) {
                findViewById(R.id.tv_empty).setVisibility(8);
            } else {
                findViewById(R.id.tv_empty).setVisibility(0);
            }
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        String stringExtra = getIntent().getStringExtra("type");
        this.type = stringExtra;
        stringExtra.hashCode();
        switch (stringExtra) {
            case "note":
                setTitle("我的笔记");
                break;
            case "error":
                setTitle("我的错题");
                break;
            case "collect":
                setTitle("我的收藏");
                break;
        }
        setContentView(R.layout.activity_subject_fwn);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.elv_subject.setOnChildClickListener(new ExpandableListView.OnChildClickListener() { // from class: com.psychiatrygarden.activity.nr
            @Override // android.widget.ExpandableListView.OnChildClickListener
            public final boolean onChildClick(ExpandableListView expandableListView, View view, int i2, int i3, long j2) {
                return this.f13060a.lambda$setListenerForWidget$0(expandableListView, view, i2, i3, j2);
            }
        });
        this.elv_subject.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() { // from class: com.psychiatrygarden.activity.or
            @Override // android.widget.ExpandableListView.OnGroupExpandListener
            public final void onGroupExpand(int i2) {
                this.f13514a.lambda$setListenerForWidget$1(i2);
            }
        });
        this.mBtnActionbarRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.pr
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13556c.lambda$setListenerForWidget$2(view);
            }
        });
    }
}
