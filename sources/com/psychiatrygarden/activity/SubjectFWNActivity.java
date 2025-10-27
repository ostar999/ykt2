package com.psychiatrygarden.activity;

import android.content.Intent;
import android.database.SQLException;
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
import com.psychiatrygarden.bean.AnsweredQuestionBeanDao;
import com.psychiatrygarden.bean.ComSubFWNItemBean;
import com.psychiatrygarden.bean.FavoritesBean;
import com.psychiatrygarden.bean.FavoritesBeanDao;
import com.psychiatrygarden.bean.NotesBean;
import com.psychiatrygarden.bean.NotesBeanDao;
import com.psychiatrygarden.bean.SectionBean;
import com.psychiatrygarden.bean.SectionBeanDao;
import com.psychiatrygarden.bean.SectionPartBean;
import com.psychiatrygarden.bean.SectionPartBeanDao;
import com.psychiatrygarden.bean.WrongBean;
import com.psychiatrygarden.bean.WrongBeanDao;
import com.psychiatrygarden.bean.oneTitleDb;
import com.psychiatrygarden.bean.twoTitleDb;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CustomDialog;
import com.psychiatrygarden.widget.ExportDescriptionPop;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class SubjectFWNActivity extends BaseActivity {
    private ExpandableListView elv_subject;
    private SubjectFWNExpandableListAdapter mAdapter;
    public ArrayList<oneTitleDb> sListDb;
    private String type;
    private int tempPosition = 0;
    private int sign = -1;
    private final String notefileurl = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/yikaobang/" + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext) + "/";

    public class ProgressBarAsyncTask extends AsyncTask<Integer, Integer, String> {
        public ProgressBarAsyncTask() {
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
            SubjectFWNActivity.this.showProgressDialog("加载中...");
        }

        @Override // android.os.AsyncTask
        public void onProgressUpdate(Integer... values) {
        }

        @Override // android.os.AsyncTask
        public String doInBackground(Integer... params) {
            if (SubjectFWNActivity.this.getIntent().getBooleanExtra("isXitong", false)) {
                SubjectFWNActivity.this.setDataXiTong(false);
                return null;
            }
            SubjectFWNActivity.this.setData(false);
            return null;
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(String result) {
            if (SubjectFWNActivity.this.mAdapter != null) {
                SubjectFWNActivity.this.mAdapter.setListData(SubjectFWNActivity.this.sListDb);
            } else {
                SubjectFWNActivity subjectFWNActivity = SubjectFWNActivity.this;
                SubjectFWNActivity subjectFWNActivity2 = SubjectFWNActivity.this;
                subjectFWNActivity.mAdapter = new SubjectFWNExpandableListAdapter(subjectFWNActivity2.mContext, subjectFWNActivity2.sListDb, subjectFWNActivity2.type);
                SubjectFWNActivity.this.elv_subject.setAdapter(SubjectFWNActivity.this.mAdapter);
            }
            if (SubjectFWNActivity.this.sListDb.size() > 0) {
                SubjectFWNActivity.this.findViewById(R.id.tv_empty).setVisibility(8);
            } else {
                SubjectFWNActivity.this.findViewById(R.id.tv_empty).setVisibility(0);
            }
            SubjectFWNActivity.this.hideProgressDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$clearnDialog$0(CustomDialog customDialog, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        customDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearnDialog$1(CustomDialog customDialog, View view) throws SQLException {
        if (CommonUtil.isFastClick()) {
            return;
        }
        customDialog.dismissNoAnimaltion();
        if (getIntent().getBooleanExtra("IsLisnxi", false)) {
            ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder().where(AnsweredQuestionBeanDao.Properties.Is_right.eq("0"), AnsweredQuestionBeanDao.Properties.Is_practice.eq("1")).buildDelete().executeDeleteWithoutDetachingEntities();
        } else {
            int intConfig = SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FILTRATE_SELECT, this.mContext, 6);
            if (intConfig == 1) {
                ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder().where(AnsweredQuestionBeanDao.Properties.Is_right.eq("0"), AnsweredQuestionBeanDao.Properties.Media_url.notEq(""), AnsweredQuestionBeanDao.Properties.Is_practice.eq("0")).buildDelete().executeDeleteWithoutDetachingEntities();
            } else if (intConfig == 4) {
                ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder().where(AnsweredQuestionBeanDao.Properties.Is_right.eq("0"), AnsweredQuestionBeanDao.Properties.Year.lt(Integer.valueOf(SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FILTRATE_SELECT_YEAR, this.mContext, R2.attr.icon_search))), AnsweredQuestionBeanDao.Properties.Is_practice.eq("0")).buildDelete().executeDeleteWithoutDetachingEntities();
            } else if (intConfig == 6) {
                ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder().where(AnsweredQuestionBeanDao.Properties.Is_right.eq("0"), AnsweredQuestionBeanDao.Properties.Is_practice.eq("0")).buildDelete().executeDeleteWithoutDetachingEntities();
            } else if (intConfig != 7) {
                ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder().where(AnsweredQuestionBeanDao.Properties.Is_right.eq("0"), AnsweredQuestionBeanDao.Properties.Year.ge(Integer.valueOf(SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FILTRATE_SELECT_YEAR, this.mContext, R2.attr.icon_search))), AnsweredQuestionBeanDao.Properties.Is_practice.eq("0")).buildDelete().executeDeleteWithoutDetachingEntities();
            } else {
                ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().queryBuilder().where(AnsweredQuestionBeanDao.Properties.Is_right.eq("0"), AnsweredQuestionBeanDao.Properties.Isgaopinkaodian.eq("1"), AnsweredQuestionBeanDao.Properties.Is_practice.eq("0")).buildDelete().executeDeleteWithoutDetachingEntities();
            }
        }
        EventBus.getDefault().post("QuestionSubjectFragment");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setListenerForWidget$2(ExpandableListView expandableListView, View view, int i2, int i3, long j2) {
        if (CommonUtil.isFastClick()) {
            return true;
        }
        long[] list_questionid = this.sListDb.get(i2).getChapters().get(i3).getList_questionid();
        Intent intent = new Intent(this.mContext, (Class<?>) SubjectQuestionListActivity.class);
        intent.putExtra("list", list_questionid);
        intent.putExtra("IsLisnxi", getIntent().getBooleanExtra("IsLisnxi", false));
        intent.putExtra("title", getIntent().getStringExtra("type") + "");
        intent.putExtra("subject_name", this.sListDb.get(i2).getCate_name());
        intent.putExtra("chapter_name", this.sListDb.get(i2).getChapters().get(i3).getCate_name());
        intent.putExtra("chapter_id", this.sListDb.get(i2).getChapters().get(i3).getCate_id());
        intent.putExtra("chapter_p_id", this.sListDb.get(i2).getCate_p_id());
        intent.putExtra("ISPractice", true);
        intent.putExtra("isXitong", getIntent().getExtras().getBoolean("isXitong", false));
        if (SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext).equals("20") && getIntent().getExtras().getInt("position") == 2) {
            intent.putExtra("iszhongyibank", true);
        }
        startActivity(intent);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setListenerForWidget$3(ExpandableListView expandableListView, View view, int i2, long j2) {
        int i3 = this.sign;
        if (i3 == -1) {
            this.elv_subject.expandGroup(i2);
            this.elv_subject.setSelectedGroup(i2);
            this.sign = i2;
            return true;
        }
        if (i3 == i2) {
            this.elv_subject.collapseGroup(i3);
            this.sign = -1;
            return true;
        }
        this.elv_subject.collapseGroup(i3);
        this.elv_subject.expandGroup(i2);
        this.elv_subject.setSelectedGroup(i2);
        this.sign = i2;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$4(int i2) {
        int i3 = this.tempPosition;
        if (i3 != i2) {
            this.elv_subject.collapseGroup(i3);
            this.tempPosition = i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v8 */
    public void setData(boolean isRefush) {
        String str;
        oneTitleDb onetitledb;
        String str2;
        SubjectFWNActivity subjectFWNActivity = this;
        new ArrayList();
        int i2 = 1;
        int i3 = 0;
        List<SectionBean> list = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, subjectFWNActivity.mContext).equals("40") ? ProjectApp.mTiKuDaoSession.getSectionBeanDao().queryBuilder().where(SectionBeanDao.Properties.Chapter_parent_id.eq("0"), SectionBeanDao.Properties.School_year.eq(getIntent().getExtras().getInt("position") + "")).orderAsc(SectionBeanDao.Properties.Sort).list() : ProjectApp.mTiKuDaoSession.getSectionBeanDao().queryBuilder().where(SectionBeanDao.Properties.Chapter_parent_id.eq("0"), new WhereCondition[0]).orderAsc(SectionBeanDao.Properties.Sort).list();
        subjectFWNActivity.sListDb = new ArrayList<>();
        String str3 = subjectFWNActivity.type;
        str3.hashCode();
        char c3 = 65535;
        switch (str3.hashCode()) {
            case 3387378:
                if (str3.equals("note")) {
                    c3 = 0;
                    break;
                }
                break;
            case 96784904:
                if (str3.equals("error")) {
                    c3 = 1;
                    break;
                }
                break;
            case 949444906:
                if (str3.equals("collect")) {
                    c3 = 2;
                    break;
                }
                break;
        }
        String str4 = CommonParameter.QUESTION_FILTRATE_SELECT;
        String str5 = "IsLisnxi";
        switch (c3) {
            case 0:
                String str6 = CommonParameter.QUESTION_FILTRATE_SELECT;
                String str7 = "IsLisnxi";
                for (int i4 = 0; i4 < list.size(); i4++) {
                    oneTitleDb onetitledb2 = new oneTitleDb();
                    ArrayList arrayList = new ArrayList();
                    List<SectionBean> list2 = ProjectApp.mTiKuDaoSession.getSectionBeanDao().queryBuilder().where(SectionBeanDao.Properties.Chapter_parent_id.eq(list.get(i4).getChapter_id()), new WhereCondition[0]).orderAsc(SectionBeanDao.Properties.Sort).list();
                    if (SharePreferencesUtils.readStrConfig(CommonParameter.APP_TIKU_MARK, subjectFWNActivity.mContext, "").equals(CommonParameter.Xueshuo)) {
                        String str8 = str7;
                        if (getIntent().getBooleanExtra(str8, false)) {
                            onetitledb2.setTotal(ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Chapter_parent_id.eq(list.get(i4).getChapter_id()), NotesBeanDao.Properties.Isxueshuo.eq("1"), NotesBeanDao.Properties.Is_practice.eq("1")).list().size() + "");
                            int i5 = 0;
                            while (i5 < list2.size()) {
                                twoTitleDb twotitledb = new twoTitleDb();
                                twotitledb.setCate_id(list2.get(i5).getChapter_id() + "");
                                twotitledb.setCate_name(list2.get(i5).getTitle());
                                twotitledb.setCate_p_id("1");
                                String str9 = str8;
                                List<SectionBean> list3 = list2;
                                List<NotesBean> list4 = ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Chapter_id.eq(list2.get(i5).getChapter_id()), NotesBeanDao.Properties.Isxueshuo.eq("1"), NotesBeanDao.Properties.Is_practice.eq("1")).orderAsc(NotesBeanDao.Properties.Cate_num).list();
                                twotitledb.setTotal(list4.size() + "");
                                if (list4.size() > 0) {
                                    long[] jArr = new long[list4.size()];
                                    for (int i6 = 0; i6 < list4.size(); i6++) {
                                        jArr[i6] = list4.get(i6).getQuestion_id().longValue();
                                    }
                                    twotitledb.setList_questionid(jArr);
                                    arrayList.add(twotitledb);
                                }
                                i5++;
                                str8 = str9;
                                list2 = list3;
                            }
                            str7 = str8;
                        } else {
                            List<SectionBean> list5 = list2;
                            str7 = str8;
                            String str10 = str6;
                            int intConfig = SharePreferencesUtils.readIntConfig(str10, subjectFWNActivity.mContext, 6);
                            if (intConfig == 1) {
                                str6 = str10;
                                onetitledb2.setTotal(ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Chapter_parent_id.eq(list.get(i4).getChapter_id()), NotesBeanDao.Properties.Media_url.notEq(""), NotesBeanDao.Properties.Isxueshuo.eq("1"), NotesBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                                int i7 = 0;
                                while (i7 < list5.size()) {
                                    twoTitleDb twotitledb2 = new twoTitleDb();
                                    StringBuilder sb = new StringBuilder();
                                    List<SectionBean> list6 = list5;
                                    sb.append(list6.get(i7).getChapter_id());
                                    sb.append("");
                                    twotitledb2.setCate_id(sb.toString());
                                    twotitledb2.setCate_name(list6.get(i7).getTitle());
                                    twotitledb2.setCate_p_id("1");
                                    List<NotesBean> list7 = ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Chapter_id.eq(list6.get(i7).getChapter_id()), NotesBeanDao.Properties.Media_url.notEq(""), NotesBeanDao.Properties.Isxueshuo.eq("1"), NotesBeanDao.Properties.Is_practice.eq("0")).orderAsc(NotesBeanDao.Properties.Cate_num).list();
                                    twotitledb2.setTotal(list7.size() + "");
                                    if (list7.size() > 0) {
                                        long[] jArr2 = new long[list7.size()];
                                        for (int i8 = 0; i8 < list7.size(); i8++) {
                                            jArr2[i8] = list7.get(i8).getQuestion_id().longValue();
                                        }
                                        twotitledb2.setList_questionid(jArr2);
                                        arrayList.add(twotitledb2);
                                    }
                                    i7++;
                                    list5 = list6;
                                }
                            } else if (intConfig == 4) {
                                str6 = str10;
                                List<SectionBean> list8 = list5;
                                SubjectFWNActivity subjectFWNActivity2 = this;
                                onetitledb2.setTotal(ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Chapter_parent_id.eq(list.get(i4).getChapter_id()), NotesBeanDao.Properties.Year.lt(Integer.valueOf(SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FILTRATE_SELECT_YEAR, subjectFWNActivity2.mContext, R2.attr.icon_search))), NotesBeanDao.Properties.Isxueshuo.eq("1"), NotesBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                                int i9 = 0;
                                while (i9 < list8.size()) {
                                    twoTitleDb twotitledb3 = new twoTitleDb();
                                    twotitledb3.setCate_id(list8.get(i9).getChapter_id() + "");
                                    twotitledb3.setCate_name(list8.get(i9).getTitle());
                                    twotitledb3.setCate_p_id("1");
                                    List<SectionBean> list9 = list8;
                                    List<NotesBean> list10 = ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Chapter_id.eq(list8.get(i9).getChapter_id()), NotesBeanDao.Properties.Year.lt(Integer.valueOf(SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FILTRATE_SELECT_YEAR, subjectFWNActivity2.mContext, R2.attr.icon_search))), NotesBeanDao.Properties.Isxueshuo.eq("1"), NotesBeanDao.Properties.Is_practice.eq("0")).orderAsc(NotesBeanDao.Properties.Cate_num).list();
                                    twotitledb3.setTotal(list10.size() + "");
                                    if (list10.size() > 0) {
                                        long[] jArr3 = new long[list10.size()];
                                        for (int i10 = 0; i10 < list10.size(); i10++) {
                                            jArr3[i10] = list10.get(i10).getQuestion_id().longValue();
                                        }
                                        twotitledb3.setList_questionid(jArr3);
                                        arrayList.add(twotitledb3);
                                    }
                                    i9++;
                                    subjectFWNActivity2 = this;
                                    list8 = list9;
                                }
                            } else if (intConfig == 6) {
                                str6 = str10;
                                onetitledb2.setTotal(ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Chapter_parent_id.eq(list.get(i4).getChapter_id()), NotesBeanDao.Properties.Isxueshuo.eq("1"), NotesBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                                for (int i11 = 0; i11 < list5.size(); i11++) {
                                    twoTitleDb twotitledb4 = new twoTitleDb();
                                    twotitledb4.setCate_id(list5.get(i11).getChapter_id() + "");
                                    twotitledb4.setCate_name(list5.get(i11).getTitle());
                                    twotitledb4.setCate_p_id("1");
                                    List<NotesBean> list11 = ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Chapter_id.eq(list5.get(i11).getChapter_id()), NotesBeanDao.Properties.Isxueshuo.eq("1"), NotesBeanDao.Properties.Is_practice.eq("0")).orderAsc(NotesBeanDao.Properties.Cate_num).list();
                                    twotitledb4.setTotal(list11.size() + "");
                                    if (list11.size() > 0) {
                                        long[] jArr4 = new long[list11.size()];
                                        for (int i12 = 0; i12 < list11.size(); i12++) {
                                            jArr4[i12] = list11.get(i12).getQuestion_id().longValue();
                                        }
                                        twotitledb4.setList_questionid(jArr4);
                                        arrayList.add(twotitledb4);
                                    }
                                }
                            } else if (intConfig != 7) {
                                onetitledb2.setTotal(ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Chapter_parent_id.eq(list.get(i4).getChapter_id()), NotesBeanDao.Properties.Year.ge(Integer.valueOf(SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FILTRATE_SELECT_YEAR, subjectFWNActivity.mContext, R2.attr.icon_search))), NotesBeanDao.Properties.Isxueshuo.eq("1"), NotesBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                                int i13 = 0;
                                while (i13 < list5.size()) {
                                    twoTitleDb twotitledb5 = new twoTitleDb();
                                    twotitledb5.setCate_id(list5.get(i13).getChapter_id() + "");
                                    twotitledb5.setCate_name(list5.get(i13).getTitle());
                                    twotitledb5.setCate_p_id("1");
                                    String str11 = str10;
                                    List<NotesBean> list12 = ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Chapter_id.eq(list5.get(i13).getChapter_id()), NotesBeanDao.Properties.Year.ge(Integer.valueOf(SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FILTRATE_SELECT_YEAR, subjectFWNActivity.mContext, R2.attr.icon_search))), NotesBeanDao.Properties.Isxueshuo.eq("1"), NotesBeanDao.Properties.Is_practice.eq("0")).orderAsc(NotesBeanDao.Properties.Cate_num).list();
                                    twotitledb5.setTotal(list12.size() + "");
                                    if (list12.size() > 0) {
                                        long[] jArr5 = new long[list12.size()];
                                        for (int i14 = 0; i14 < list12.size(); i14++) {
                                            jArr5[i14] = list12.get(i14).getQuestion_id().longValue();
                                        }
                                        twotitledb5.setList_questionid(jArr5);
                                        arrayList.add(twotitledb5);
                                    }
                                    i13++;
                                    subjectFWNActivity = this;
                                    str10 = str11;
                                }
                                str6 = str10;
                            } else {
                                str6 = str10;
                                onetitledb2.setTotal(ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Chapter_parent_id.eq(list.get(i4).getChapter_id()), NotesBeanDao.Properties.Isxueshuo.eq("1"), NotesBeanDao.Properties.Isgaopinkaodian.eq("1"), NotesBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                                int i15 = 0;
                                while (i15 < list5.size()) {
                                    twoTitleDb twotitledb6 = new twoTitleDb();
                                    StringBuilder sb2 = new StringBuilder();
                                    List<SectionBean> list13 = list5;
                                    sb2.append(list13.get(i15).getChapter_id());
                                    sb2.append("");
                                    twotitledb6.setCate_id(sb2.toString());
                                    twotitledb6.setCate_name(list13.get(i15).getTitle());
                                    twotitledb6.setCate_p_id("1");
                                    List<NotesBean> list14 = ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Chapter_id.eq(list13.get(i15).getChapter_id()), NotesBeanDao.Properties.Isxueshuo.eq("1"), NotesBeanDao.Properties.Isgaopinkaodian.eq("1"), NotesBeanDao.Properties.Is_practice.eq("0")).orderAsc(NotesBeanDao.Properties.Cate_num).list();
                                    twotitledb6.setTotal(list14.size() + "");
                                    if (list14.size() > 0) {
                                        long[] jArr6 = new long[list14.size()];
                                        for (int i16 = 0; i16 < list14.size(); i16++) {
                                            jArr6[i16] = list14.get(i16).getQuestion_id().longValue();
                                        }
                                        twotitledb6.setList_questionid(jArr6);
                                        arrayList.add(twotitledb6);
                                    }
                                    i15++;
                                    list5 = list13;
                                }
                            }
                        }
                    } else {
                        List<SectionBean> list15 = list2;
                        String str12 = str7;
                        if (getIntent().getBooleanExtra(str12, false)) {
                            onetitledb2.setTotal(ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Chapter_parent_id.eq(list.get(i4).getChapter_id()), NotesBeanDao.Properties.Iszhuanshuo.eq("1"), NotesBeanDao.Properties.Is_practice.eq("1")).list().size() + "");
                            int i17 = 0;
                            while (i17 < list15.size()) {
                                twoTitleDb twotitledb7 = new twoTitleDb();
                                twotitledb7.setCate_id(list15.get(i17).getChapter_id() + "");
                                twotitledb7.setCate_name(list15.get(i17).getTitle());
                                twotitledb7.setCate_p_id("1");
                                String str13 = str12;
                                List<NotesBean> list16 = ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Chapter_id.eq(list15.get(i17).getChapter_id()), NotesBeanDao.Properties.Iszhuanshuo.eq("1"), NotesBeanDao.Properties.Is_practice.eq("1")).orderAsc(NotesBeanDao.Properties.Cate_num).list();
                                twotitledb7.setTotal(list16.size() + "");
                                if (list16.size() > 0) {
                                    long[] jArr7 = new long[list16.size()];
                                    for (int i18 = 0; i18 < list16.size(); i18++) {
                                        jArr7[i18] = list16.get(i18).getQuestion_id().longValue();
                                    }
                                    twotitledb7.setList_questionid(jArr7);
                                    arrayList.add(twotitledb7);
                                }
                                i17++;
                                str12 = str13;
                            }
                            str7 = str12;
                        } else {
                            SubjectFWNActivity subjectFWNActivity3 = this;
                            str7 = str12;
                            String str14 = str6;
                            int intConfig2 = SharePreferencesUtils.readIntConfig(str14, subjectFWNActivity3.mContext, 6);
                            if (intConfig2 == 1) {
                                str6 = str14;
                                List<SectionBean> list17 = list15;
                                onetitledb2.setTotal(ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Chapter_parent_id.eq(list.get(i4).getChapter_id()), NotesBeanDao.Properties.Media_url.notEq(""), NotesBeanDao.Properties.Iszhuanshuo.eq("1"), NotesBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                                int i19 = 0;
                                while (i19 < list17.size()) {
                                    twoTitleDb twotitledb8 = new twoTitleDb();
                                    StringBuilder sb3 = new StringBuilder();
                                    List<SectionBean> list18 = list17;
                                    sb3.append(list18.get(i19).getChapter_id());
                                    sb3.append("");
                                    twotitledb8.setCate_id(sb3.toString());
                                    twotitledb8.setCate_name(list18.get(i19).getTitle());
                                    twotitledb8.setCate_p_id("1");
                                    List<NotesBean> list19 = ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Chapter_id.eq(list18.get(i19).getChapter_id()), NotesBeanDao.Properties.Media_url.notEq(""), NotesBeanDao.Properties.Iszhuanshuo.eq("1"), NotesBeanDao.Properties.Is_practice.eq("0")).orderAsc(NotesBeanDao.Properties.Cate_num).list();
                                    twotitledb8.setTotal(list19.size() + "");
                                    if (list19.size() > 0) {
                                        long[] jArr8 = new long[list19.size()];
                                        for (int i20 = 0; i20 < list19.size(); i20++) {
                                            jArr8[i20] = list19.get(i20).getQuestion_id().longValue();
                                        }
                                        twotitledb8.setList_questionid(jArr8);
                                        arrayList.add(twotitledb8);
                                    }
                                    i19++;
                                    list17 = list18;
                                }
                            } else if (intConfig2 == 4) {
                                str6 = str14;
                                SubjectFWNActivity subjectFWNActivity4 = this;
                                onetitledb2.setTotal(ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Chapter_parent_id.eq(list.get(i4).getChapter_id()), NotesBeanDao.Properties.Year.lt(Integer.valueOf(SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FILTRATE_SELECT_YEAR, subjectFWNActivity4.mContext, R2.attr.icon_search))), NotesBeanDao.Properties.Iszhuanshuo.eq("1"), NotesBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                                int i21 = 0;
                                while (i21 < list15.size()) {
                                    twoTitleDb twotitledb9 = new twoTitleDb();
                                    twotitledb9.setCate_id(list15.get(i21).getChapter_id() + "");
                                    twotitledb9.setCate_name(list15.get(i21).getTitle());
                                    twotitledb9.setCate_p_id("1");
                                    List<SectionBean> list20 = list15;
                                    List<NotesBean> list21 = ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Chapter_id.eq(list15.get(i21).getChapter_id()), NotesBeanDao.Properties.Year.lt(Integer.valueOf(SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FILTRATE_SELECT_YEAR, subjectFWNActivity4.mContext, R2.attr.icon_search))), NotesBeanDao.Properties.Iszhuanshuo.eq("1"), NotesBeanDao.Properties.Is_practice.eq("0")).orderAsc(NotesBeanDao.Properties.Cate_num).list();
                                    twotitledb9.setTotal(list21.size() + "");
                                    if (list21.size() > 0) {
                                        long[] jArr9 = new long[list21.size()];
                                        for (int i22 = 0; i22 < list21.size(); i22++) {
                                            jArr9[i22] = list21.get(i22).getQuestion_id().longValue();
                                        }
                                        twotitledb9.setList_questionid(jArr9);
                                        arrayList.add(twotitledb9);
                                    }
                                    i21++;
                                    subjectFWNActivity4 = this;
                                    list15 = list20;
                                }
                            } else if (intConfig2 == 6) {
                                str6 = str14;
                                onetitledb2.setTotal(ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Chapter_parent_id.eq(list.get(i4).getChapter_id()), NotesBeanDao.Properties.Iszhuanshuo.eq("1"), NotesBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                                for (int i23 = 0; i23 < list15.size(); i23++) {
                                    twoTitleDb twotitledb10 = new twoTitleDb();
                                    twotitledb10.setCate_id(list15.get(i23).getChapter_id() + "");
                                    twotitledb10.setCate_name(list15.get(i23).getTitle());
                                    twotitledb10.setCate_p_id("1");
                                    List<NotesBean> list22 = ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Chapter_id.eq(list15.get(i23).getChapter_id()), NotesBeanDao.Properties.Iszhuanshuo.eq("1"), NotesBeanDao.Properties.Is_practice.eq("0")).orderAsc(NotesBeanDao.Properties.Cate_num).list();
                                    twotitledb10.setTotal(list22.size() + "");
                                    if (list22.size() > 0) {
                                        long[] jArr10 = new long[list22.size()];
                                        for (int i24 = 0; i24 < list22.size(); i24++) {
                                            jArr10[i24] = list22.get(i24).getQuestion_id().longValue();
                                        }
                                        twotitledb10.setList_questionid(jArr10);
                                        arrayList.add(twotitledb10);
                                    }
                                }
                            } else if (intConfig2 != 7) {
                                str6 = str14;
                                onetitledb2.setTotal(ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Chapter_parent_id.eq(list.get(i4).getChapter_id()), NotesBeanDao.Properties.Year.ge(Integer.valueOf(SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FILTRATE_SELECT_YEAR, subjectFWNActivity3.mContext, R2.attr.icon_search))), NotesBeanDao.Properties.Iszhuanshuo.eq("1"), NotesBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                                int i25 = 0;
                                while (i25 < list15.size()) {
                                    twoTitleDb twotitledb11 = new twoTitleDb();
                                    twotitledb11.setCate_id(list15.get(i25).getChapter_id() + "");
                                    twotitledb11.setCate_name(list15.get(i25).getTitle());
                                    twotitledb11.setCate_p_id("1");
                                    List<SectionBean> list23 = list15;
                                    List<NotesBean> list24 = ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Chapter_id.eq(list15.get(i25).getChapter_id()), NotesBeanDao.Properties.Year.ge(Integer.valueOf(SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FILTRATE_SELECT_YEAR, subjectFWNActivity3.mContext, R2.attr.icon_search))), NotesBeanDao.Properties.Iszhuanshuo.eq("1"), NotesBeanDao.Properties.Is_practice.eq("0")).orderAsc(NotesBeanDao.Properties.Cate_num).list();
                                    twotitledb11.setTotal(list24.size() + "");
                                    if (list24.size() > 0) {
                                        long[] jArr11 = new long[list24.size()];
                                        for (int i26 = 0; i26 < list24.size(); i26++) {
                                            jArr11[i26] = list24.get(i26).getQuestion_id().longValue();
                                        }
                                        twotitledb11.setList_questionid(jArr11);
                                        arrayList.add(twotitledb11);
                                    }
                                    i25++;
                                    subjectFWNActivity3 = this;
                                    list15 = list23;
                                }
                            } else {
                                str6 = str14;
                                List<SectionBean> list25 = list15;
                                onetitledb2.setTotal(ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Chapter_parent_id.eq(list.get(i4).getChapter_id()), NotesBeanDao.Properties.Iszhuanshuo.eq("1"), NotesBeanDao.Properties.Isgaopinkaodian.eq("1"), NotesBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                                int i27 = 0;
                                while (i27 < list25.size()) {
                                    twoTitleDb twotitledb12 = new twoTitleDb();
                                    StringBuilder sb4 = new StringBuilder();
                                    List<SectionBean> list26 = list25;
                                    sb4.append(list26.get(i27).getChapter_id());
                                    sb4.append("");
                                    twotitledb12.setCate_id(sb4.toString());
                                    twotitledb12.setCate_name(list26.get(i27).getTitle());
                                    twotitledb12.setCate_p_id("1");
                                    List<NotesBean> list27 = ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Chapter_id.eq(list26.get(i27).getChapter_id()), NotesBeanDao.Properties.Iszhuanshuo.eq("1"), NotesBeanDao.Properties.Isgaopinkaodian.eq("1"), NotesBeanDao.Properties.Is_practice.eq("0")).orderAsc(NotesBeanDao.Properties.Cate_num).list();
                                    twotitledb12.setTotal(list27.size() + "");
                                    if (list27.size() > 0) {
                                        long[] jArr12 = new long[list27.size()];
                                        for (int i28 = 0; i28 < list27.size(); i28++) {
                                            jArr12[i28] = list27.get(i28).getQuestion_id().longValue();
                                        }
                                        twotitledb12.setList_questionid(jArr12);
                                        arrayList.add(twotitledb12);
                                    }
                                    i27++;
                                    list25 = list26;
                                }
                            }
                        }
                    }
                    onetitledb2.setChapters(arrayList);
                    onetitledb2.setCate_p_id(list.get(i4).getChapter_id() + "");
                    onetitledb2.setCate_name(list.get(i4).getTitle() + "");
                    onetitledb2.setCate_user_count(0);
                    if (Integer.parseInt(onetitledb2.getTotal()) > 0) {
                        subjectFWNActivity = this;
                        subjectFWNActivity.sListDb.add(onetitledb2);
                    } else {
                        subjectFWNActivity = this;
                    }
                }
                break;
            case 1:
                String str15 = CommonParameter.QUESTION_FILTRATE_SELECT;
                String str16 = "IsLisnxi";
                for (int i29 = 0; i29 < list.size(); i29++) {
                    oneTitleDb onetitledb3 = new oneTitleDb();
                    ArrayList arrayList2 = new ArrayList();
                    List<SectionBean> list28 = ProjectApp.mTiKuDaoSession.getSectionBeanDao().queryBuilder().where(SectionBeanDao.Properties.Chapter_parent_id.eq(list.get(i29).getChapter_id()), new WhereCondition[0]).orderAsc(SectionBeanDao.Properties.Sort).list();
                    if (SharePreferencesUtils.readStrConfig(CommonParameter.APP_TIKU_MARK, subjectFWNActivity.mContext, "").equals(CommonParameter.Xueshuo)) {
                        String str17 = str16;
                        if (getIntent().getBooleanExtra(str17, false)) {
                            onetitledb3.setTotal(ProjectApp.mDaoSession.getWrongBeanDao().queryBuilder().where(WrongBeanDao.Properties.Chapter_parent_id.eq(list.get(i29).getChapter_id()), WrongBeanDao.Properties.Isxueshuo.eq("1"), WrongBeanDao.Properties.Is_practice.eq("1")).list().size() + "");
                            int i30 = 0;
                            while (i30 < list28.size()) {
                                twoTitleDb twotitledb13 = new twoTitleDb();
                                twotitledb13.setCate_id(list28.get(i30).getChapter_id() + "");
                                twotitledb13.setCate_name(list28.get(i30).getTitle());
                                twotitledb13.setCate_p_id("1");
                                String str18 = str17;
                                List<SectionBean> list29 = list28;
                                List<WrongBean> list30 = ProjectApp.mDaoSession.getWrongBeanDao().queryBuilder().where(WrongBeanDao.Properties.Chapter_id.eq(list28.get(i30).getChapter_id()), WrongBeanDao.Properties.Isxueshuo.eq("1"), WrongBeanDao.Properties.Is_practice.eq("1")).orderAsc(WrongBeanDao.Properties.Cate_num).list();
                                twotitledb13.setTotal(list30.size() + "");
                                if (list30.size() > 0) {
                                    long[] jArr13 = new long[list30.size()];
                                    for (int i31 = 0; i31 < list30.size(); i31++) {
                                        jArr13[i31] = list30.get(i31).getQuestion_id().longValue();
                                    }
                                    twotitledb13.setList_questionid(jArr13);
                                    arrayList2.add(twotitledb13);
                                }
                                i30++;
                                str17 = str18;
                                list28 = list29;
                            }
                            str16 = str17;
                        } else {
                            List<SectionBean> list31 = list28;
                            str16 = str17;
                            String str19 = str15;
                            int intConfig3 = SharePreferencesUtils.readIntConfig(str19, subjectFWNActivity.mContext, 6);
                            if (intConfig3 == 1) {
                                str15 = str19;
                                onetitledb3.setTotal(ProjectApp.mDaoSession.getWrongBeanDao().queryBuilder().where(WrongBeanDao.Properties.Chapter_parent_id.eq(list.get(i29).getChapter_id()), WrongBeanDao.Properties.Media_url.notEq(""), WrongBeanDao.Properties.Isxueshuo.eq("1"), WrongBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                                int i32 = 0;
                                while (i32 < list31.size()) {
                                    twoTitleDb twotitledb14 = new twoTitleDb();
                                    StringBuilder sb5 = new StringBuilder();
                                    List<SectionBean> list32 = list31;
                                    sb5.append(list32.get(i32).getChapter_id());
                                    sb5.append("");
                                    twotitledb14.setCate_id(sb5.toString());
                                    twotitledb14.setCate_name(list32.get(i32).getTitle());
                                    twotitledb14.setCate_p_id("1");
                                    List<WrongBean> list33 = ProjectApp.mDaoSession.getWrongBeanDao().queryBuilder().where(WrongBeanDao.Properties.Chapter_id.eq(list32.get(i32).getChapter_id()), WrongBeanDao.Properties.Media_url.notEq(""), WrongBeanDao.Properties.Isxueshuo.eq("1"), WrongBeanDao.Properties.Is_practice.eq("0")).orderAsc(WrongBeanDao.Properties.Cate_num).list();
                                    twotitledb14.setTotal(list33.size() + "");
                                    if (list33.size() > 0) {
                                        long[] jArr14 = new long[list33.size()];
                                        for (int i33 = 0; i33 < list33.size(); i33++) {
                                            jArr14[i33] = list33.get(i33).getQuestion_id().longValue();
                                        }
                                        twotitledb14.setList_questionid(jArr14);
                                        arrayList2.add(twotitledb14);
                                    }
                                    i32++;
                                    list31 = list32;
                                }
                            } else if (intConfig3 == 4) {
                                str15 = str19;
                                List<SectionBean> list34 = list31;
                                SubjectFWNActivity subjectFWNActivity5 = this;
                                onetitledb3.setTotal(ProjectApp.mDaoSession.getWrongBeanDao().queryBuilder().where(WrongBeanDao.Properties.Chapter_parent_id.eq(list.get(i29).getChapter_id()), WrongBeanDao.Properties.Year.lt(Integer.valueOf(SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FILTRATE_SELECT_YEAR, subjectFWNActivity5.mContext, R2.attr.icon_search))), WrongBeanDao.Properties.Isxueshuo.eq("1"), WrongBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                                int i34 = 0;
                                while (i34 < list34.size()) {
                                    twoTitleDb twotitledb15 = new twoTitleDb();
                                    twotitledb15.setCate_id(list34.get(i34).getChapter_id() + "");
                                    twotitledb15.setCate_name(list34.get(i34).getTitle());
                                    twotitledb15.setCate_p_id("1");
                                    List<SectionBean> list35 = list34;
                                    List<WrongBean> list36 = ProjectApp.mDaoSession.getWrongBeanDao().queryBuilder().where(WrongBeanDao.Properties.Chapter_id.eq(list34.get(i34).getChapter_id()), WrongBeanDao.Properties.Year.lt(Integer.valueOf(SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FILTRATE_SELECT_YEAR, subjectFWNActivity5.mContext, R2.attr.icon_search))), WrongBeanDao.Properties.Isxueshuo.eq("1"), WrongBeanDao.Properties.Is_practice.eq("0")).orderAsc(WrongBeanDao.Properties.Cate_num).list();
                                    twotitledb15.setTotal(list36.size() + "");
                                    if (list36.size() > 0) {
                                        long[] jArr15 = new long[list36.size()];
                                        for (int i35 = 0; i35 < list36.size(); i35++) {
                                            jArr15[i35] = list36.get(i35).getQuestion_id().longValue();
                                        }
                                        twotitledb15.setList_questionid(jArr15);
                                        arrayList2.add(twotitledb15);
                                    }
                                    i34++;
                                    subjectFWNActivity5 = this;
                                    list34 = list35;
                                }
                            } else if (intConfig3 == 6) {
                                str15 = str19;
                                onetitledb3.setTotal(ProjectApp.mDaoSession.getWrongBeanDao().queryBuilder().where(WrongBeanDao.Properties.Chapter_parent_id.eq(list.get(i29).getChapter_id()), WrongBeanDao.Properties.Isxueshuo.eq("1"), WrongBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                                for (int i36 = 0; i36 < list31.size(); i36++) {
                                    twoTitleDb twotitledb16 = new twoTitleDb();
                                    twotitledb16.setCate_id(list31.get(i36).getChapter_id() + "");
                                    twotitledb16.setCate_name(list31.get(i36).getTitle());
                                    twotitledb16.setCate_p_id("1");
                                    List<WrongBean> list37 = ProjectApp.mDaoSession.getWrongBeanDao().queryBuilder().where(WrongBeanDao.Properties.Chapter_id.eq(list31.get(i36).getChapter_id()), WrongBeanDao.Properties.Isxueshuo.eq("1"), WrongBeanDao.Properties.Is_practice.eq("0")).orderAsc(WrongBeanDao.Properties.Cate_num).list();
                                    twotitledb16.setTotal(list37.size() + "");
                                    if (list37.size() > 0) {
                                        long[] jArr16 = new long[list37.size()];
                                        for (int i37 = 0; i37 < list37.size(); i37++) {
                                            jArr16[i37] = list37.get(i37).getQuestion_id().longValue();
                                        }
                                        twotitledb16.setList_questionid(jArr16);
                                        arrayList2.add(twotitledb16);
                                    }
                                }
                            } else if (intConfig3 != 7) {
                                onetitledb3.setTotal(ProjectApp.mDaoSession.getWrongBeanDao().queryBuilder().where(WrongBeanDao.Properties.Chapter_parent_id.eq(list.get(i29).getChapter_id()), WrongBeanDao.Properties.Year.ge(Integer.valueOf(SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FILTRATE_SELECT_YEAR, subjectFWNActivity.mContext, R2.attr.icon_search))), WrongBeanDao.Properties.Isxueshuo.eq("1"), WrongBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                                int i38 = 0;
                                while (i38 < list31.size()) {
                                    twoTitleDb twotitledb17 = new twoTitleDb();
                                    twotitledb17.setCate_id(list31.get(i38).getChapter_id() + "");
                                    twotitledb17.setCate_name(list31.get(i38).getTitle());
                                    twotitledb17.setCate_p_id("1");
                                    String str20 = str19;
                                    List<WrongBean> list38 = ProjectApp.mDaoSession.getWrongBeanDao().queryBuilder().where(WrongBeanDao.Properties.Chapter_id.eq(list31.get(i38).getChapter_id()), WrongBeanDao.Properties.Year.ge(Integer.valueOf(SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FILTRATE_SELECT_YEAR, subjectFWNActivity.mContext, R2.attr.icon_search))), WrongBeanDao.Properties.Isxueshuo.eq("1"), WrongBeanDao.Properties.Is_practice.eq("0")).orderAsc(WrongBeanDao.Properties.Cate_num).list();
                                    twotitledb17.setTotal(list38.size() + "");
                                    if (list38.size() > 0) {
                                        long[] jArr17 = new long[list38.size()];
                                        for (int i39 = 0; i39 < list38.size(); i39++) {
                                            jArr17[i39] = list38.get(i39).getQuestion_id().longValue();
                                        }
                                        twotitledb17.setList_questionid(jArr17);
                                        arrayList2.add(twotitledb17);
                                    }
                                    i38++;
                                    subjectFWNActivity = this;
                                    str19 = str20;
                                }
                                str15 = str19;
                            } else {
                                str15 = str19;
                                onetitledb3.setTotal(ProjectApp.mDaoSession.getWrongBeanDao().queryBuilder().where(WrongBeanDao.Properties.Chapter_parent_id.eq(list.get(i29).getChapter_id()), WrongBeanDao.Properties.Isxueshuo.eq("1"), WrongBeanDao.Properties.Isgaopinkaodian.eq("1"), WrongBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                                int i40 = 0;
                                while (i40 < list31.size()) {
                                    twoTitleDb twotitledb18 = new twoTitleDb();
                                    StringBuilder sb6 = new StringBuilder();
                                    List<SectionBean> list39 = list31;
                                    sb6.append(list39.get(i40).getChapter_id());
                                    sb6.append("");
                                    twotitledb18.setCate_id(sb6.toString());
                                    twotitledb18.setCate_name(list39.get(i40).getTitle());
                                    twotitledb18.setCate_p_id("1");
                                    List<WrongBean> list40 = ProjectApp.mDaoSession.getWrongBeanDao().queryBuilder().where(WrongBeanDao.Properties.Chapter_id.eq(list39.get(i40).getChapter_id()), WrongBeanDao.Properties.Isxueshuo.eq("1"), WrongBeanDao.Properties.Isgaopinkaodian.eq("1"), WrongBeanDao.Properties.Is_practice.eq("0")).orderAsc(WrongBeanDao.Properties.Cate_num).list();
                                    twotitledb18.setTotal(list40.size() + "");
                                    if (list40.size() > 0) {
                                        long[] jArr18 = new long[list40.size()];
                                        for (int i41 = 0; i41 < list40.size(); i41++) {
                                            jArr18[i41] = list40.get(i41).getQuestion_id().longValue();
                                        }
                                        twotitledb18.setList_questionid(jArr18);
                                        arrayList2.add(twotitledb18);
                                    }
                                    i40++;
                                    list31 = list39;
                                }
                            }
                        }
                    } else {
                        List<SectionBean> list41 = list28;
                        String str21 = str16;
                        if (getIntent().getBooleanExtra(str21, false)) {
                            onetitledb3.setTotal(ProjectApp.mDaoSession.getWrongBeanDao().queryBuilder().where(WrongBeanDao.Properties.Chapter_parent_id.eq(list.get(i29).getChapter_id()), WrongBeanDao.Properties.Iszhuanshuo.eq("1"), WrongBeanDao.Properties.Is_practice.eq("1")).list().size() + "");
                            int i42 = 0;
                            while (i42 < list41.size()) {
                                twoTitleDb twotitledb19 = new twoTitleDb();
                                twotitledb19.setCate_id(list41.get(i42).getChapter_id() + "");
                                twotitledb19.setCate_name(list41.get(i42).getTitle());
                                twotitledb19.setCate_p_id("1");
                                String str22 = str21;
                                List<WrongBean> list42 = ProjectApp.mDaoSession.getWrongBeanDao().queryBuilder().where(WrongBeanDao.Properties.Chapter_id.eq(list41.get(i42).getChapter_id()), WrongBeanDao.Properties.Iszhuanshuo.eq("1"), WrongBeanDao.Properties.Is_practice.eq("1")).orderAsc(WrongBeanDao.Properties.Cate_num).list();
                                twotitledb19.setTotal(list42.size() + "");
                                if (list42.size() > 0) {
                                    long[] jArr19 = new long[list42.size()];
                                    for (int i43 = 0; i43 < list42.size(); i43++) {
                                        jArr19[i43] = list42.get(i43).getQuestion_id().longValue();
                                    }
                                    twotitledb19.setList_questionid(jArr19);
                                    arrayList2.add(twotitledb19);
                                }
                                i42++;
                                str21 = str22;
                            }
                            str16 = str21;
                        } else {
                            SubjectFWNActivity subjectFWNActivity6 = this;
                            str16 = str21;
                            String str23 = str15;
                            int intConfig4 = SharePreferencesUtils.readIntConfig(str23, subjectFWNActivity6.mContext, 6);
                            if (intConfig4 == 1) {
                                str15 = str23;
                                List<SectionBean> list43 = list41;
                                onetitledb3.setTotal(ProjectApp.mDaoSession.getWrongBeanDao().queryBuilder().where(WrongBeanDao.Properties.Chapter_parent_id.eq(list.get(i29).getChapter_id()), WrongBeanDao.Properties.Media_url.notEq(""), WrongBeanDao.Properties.Iszhuanshuo.eq("1"), WrongBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                                int i44 = 0;
                                while (i44 < list43.size()) {
                                    twoTitleDb twotitledb20 = new twoTitleDb();
                                    StringBuilder sb7 = new StringBuilder();
                                    List<SectionBean> list44 = list43;
                                    sb7.append(list44.get(i44).getChapter_id());
                                    sb7.append("");
                                    twotitledb20.setCate_id(sb7.toString());
                                    twotitledb20.setCate_name(list44.get(i44).getTitle());
                                    twotitledb20.setCate_p_id("1");
                                    List<WrongBean> list45 = ProjectApp.mDaoSession.getWrongBeanDao().queryBuilder().where(WrongBeanDao.Properties.Chapter_id.eq(list44.get(i44).getChapter_id()), WrongBeanDao.Properties.Media_url.notEq(""), WrongBeanDao.Properties.Iszhuanshuo.eq("1"), WrongBeanDao.Properties.Is_practice.eq("0")).orderAsc(WrongBeanDao.Properties.Cate_num).list();
                                    twotitledb20.setTotal(list45.size() + "");
                                    if (list45.size() > 0) {
                                        long[] jArr20 = new long[list45.size()];
                                        for (int i45 = 0; i45 < list45.size(); i45++) {
                                            jArr20[i45] = list45.get(i45).getQuestion_id().longValue();
                                        }
                                        twotitledb20.setList_questionid(jArr20);
                                        arrayList2.add(twotitledb20);
                                    }
                                    i44++;
                                    list43 = list44;
                                }
                            } else if (intConfig4 == 4) {
                                str15 = str23;
                                SubjectFWNActivity subjectFWNActivity7 = this;
                                onetitledb3.setTotal(ProjectApp.mDaoSession.getWrongBeanDao().queryBuilder().where(WrongBeanDao.Properties.Chapter_parent_id.eq(list.get(i29).getChapter_id()), WrongBeanDao.Properties.Year.lt(Integer.valueOf(SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FILTRATE_SELECT_YEAR, subjectFWNActivity7.mContext, R2.attr.icon_search))), WrongBeanDao.Properties.Iszhuanshuo.eq("1"), WrongBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                                int i46 = 0;
                                while (i46 < list41.size()) {
                                    twoTitleDb twotitledb21 = new twoTitleDb();
                                    twotitledb21.setCate_id(list41.get(i46).getChapter_id() + "");
                                    twotitledb21.setCate_name(list41.get(i46).getTitle());
                                    twotitledb21.setCate_p_id("1");
                                    List<SectionBean> list46 = list41;
                                    List<WrongBean> list47 = ProjectApp.mDaoSession.getWrongBeanDao().queryBuilder().where(WrongBeanDao.Properties.Chapter_id.eq(list41.get(i46).getChapter_id()), WrongBeanDao.Properties.Year.lt(Integer.valueOf(SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FILTRATE_SELECT_YEAR, subjectFWNActivity7.mContext, R2.attr.icon_search))), WrongBeanDao.Properties.Iszhuanshuo.eq("1"), WrongBeanDao.Properties.Is_practice.eq("0")).orderAsc(WrongBeanDao.Properties.Cate_num).list();
                                    twotitledb21.setTotal(list47.size() + "");
                                    if (list47.size() > 0) {
                                        long[] jArr21 = new long[list47.size()];
                                        for (int i47 = 0; i47 < list47.size(); i47++) {
                                            jArr21[i47] = list47.get(i47).getQuestion_id().longValue();
                                        }
                                        twotitledb21.setList_questionid(jArr21);
                                        arrayList2.add(twotitledb21);
                                    }
                                    i46++;
                                    subjectFWNActivity7 = this;
                                    list41 = list46;
                                }
                            } else if (intConfig4 == 6) {
                                str15 = str23;
                                onetitledb3.setTotal(ProjectApp.mDaoSession.getWrongBeanDao().queryBuilder().where(WrongBeanDao.Properties.Chapter_parent_id.eq(list.get(i29).getChapter_id()), WrongBeanDao.Properties.Iszhuanshuo.eq("1"), WrongBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                                for (int i48 = 0; i48 < list41.size(); i48++) {
                                    twoTitleDb twotitledb22 = new twoTitleDb();
                                    twotitledb22.setCate_id(list41.get(i48).getChapter_id() + "");
                                    twotitledb22.setCate_name(list41.get(i48).getTitle());
                                    twotitledb22.setCate_p_id("1");
                                    List<WrongBean> list48 = ProjectApp.mDaoSession.getWrongBeanDao().queryBuilder().where(WrongBeanDao.Properties.Chapter_id.eq(list41.get(i48).getChapter_id()), WrongBeanDao.Properties.Iszhuanshuo.eq("1"), WrongBeanDao.Properties.Is_practice.eq("0")).orderAsc(WrongBeanDao.Properties.Cate_num).list();
                                    twotitledb22.setTotal(list48.size() + "");
                                    if (list48.size() > 0) {
                                        long[] jArr22 = new long[list48.size()];
                                        for (int i49 = 0; i49 < list48.size(); i49++) {
                                            jArr22[i49] = list48.get(i49).getQuestion_id().longValue();
                                        }
                                        twotitledb22.setList_questionid(jArr22);
                                        arrayList2.add(twotitledb22);
                                    }
                                }
                            } else if (intConfig4 != 7) {
                                str15 = str23;
                                onetitledb3.setTotal(ProjectApp.mDaoSession.getWrongBeanDao().queryBuilder().where(WrongBeanDao.Properties.Chapter_parent_id.eq(list.get(i29).getChapter_id()), WrongBeanDao.Properties.Year.ge(Integer.valueOf(SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FILTRATE_SELECT_YEAR, subjectFWNActivity6.mContext, R2.attr.icon_search))), WrongBeanDao.Properties.Iszhuanshuo.eq("1"), WrongBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                                int i50 = 0;
                                while (i50 < list41.size()) {
                                    twoTitleDb twotitledb23 = new twoTitleDb();
                                    twotitledb23.setCate_id(list41.get(i50).getChapter_id() + "");
                                    twotitledb23.setCate_name(list41.get(i50).getTitle());
                                    twotitledb23.setCate_p_id("1");
                                    List<SectionBean> list49 = list41;
                                    List<WrongBean> list50 = ProjectApp.mDaoSession.getWrongBeanDao().queryBuilder().where(WrongBeanDao.Properties.Chapter_id.eq(list41.get(i50).getChapter_id()), WrongBeanDao.Properties.Year.ge(Integer.valueOf(SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FILTRATE_SELECT_YEAR, subjectFWNActivity6.mContext, R2.attr.icon_search))), WrongBeanDao.Properties.Iszhuanshuo.eq("1"), WrongBeanDao.Properties.Is_practice.eq("0")).orderAsc(WrongBeanDao.Properties.Cate_num).list();
                                    twotitledb23.setTotal(list50.size() + "");
                                    if (list50.size() > 0) {
                                        long[] jArr23 = new long[list50.size()];
                                        for (int i51 = 0; i51 < list50.size(); i51++) {
                                            jArr23[i51] = list50.get(i51).getQuestion_id().longValue();
                                        }
                                        twotitledb23.setList_questionid(jArr23);
                                        arrayList2.add(twotitledb23);
                                    }
                                    i50++;
                                    subjectFWNActivity6 = this;
                                    list41 = list49;
                                }
                            } else {
                                str15 = str23;
                                List<SectionBean> list51 = list41;
                                onetitledb3.setTotal(ProjectApp.mDaoSession.getWrongBeanDao().queryBuilder().where(WrongBeanDao.Properties.Chapter_parent_id.eq(list.get(i29).getChapter_id()), WrongBeanDao.Properties.Iszhuanshuo.eq("1"), WrongBeanDao.Properties.Isgaopinkaodian.eq("1"), WrongBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                                int i52 = 0;
                                while (i52 < list51.size()) {
                                    twoTitleDb twotitledb24 = new twoTitleDb();
                                    StringBuilder sb8 = new StringBuilder();
                                    List<SectionBean> list52 = list51;
                                    sb8.append(list52.get(i52).getChapter_id());
                                    sb8.append("");
                                    twotitledb24.setCate_id(sb8.toString());
                                    twotitledb24.setCate_name(list52.get(i52).getTitle());
                                    twotitledb24.setCate_p_id("1");
                                    List<WrongBean> list53 = ProjectApp.mDaoSession.getWrongBeanDao().queryBuilder().where(WrongBeanDao.Properties.Chapter_id.eq(list52.get(i52).getChapter_id()), WrongBeanDao.Properties.Iszhuanshuo.eq("1"), WrongBeanDao.Properties.Isgaopinkaodian.eq("1"), WrongBeanDao.Properties.Is_practice.eq("0")).orderAsc(WrongBeanDao.Properties.Cate_num).list();
                                    twotitledb24.setTotal(list53.size() + "");
                                    if (list53.size() > 0) {
                                        long[] jArr24 = new long[list53.size()];
                                        for (int i53 = 0; i53 < list53.size(); i53++) {
                                            jArr24[i53] = list53.get(i53).getQuestion_id().longValue();
                                        }
                                        twotitledb24.setList_questionid(jArr24);
                                        arrayList2.add(twotitledb24);
                                    }
                                    i52++;
                                    list51 = list52;
                                }
                            }
                        }
                    }
                    onetitledb3.setChapters(arrayList2);
                    onetitledb3.setCate_p_id(list.get(i29).getChapter_id() + "");
                    onetitledb3.setCate_name(list.get(i29).getTitle() + "");
                    onetitledb3.setCate_user_count(0);
                    if (Integer.parseInt(onetitledb3.getTotal()) > 0) {
                        subjectFWNActivity = this;
                        subjectFWNActivity.sListDb.add(onetitledb3);
                    } else {
                        subjectFWNActivity = this;
                    }
                }
                break;
            case 2:
                int i54 = 0;
                while (i54 < list.size()) {
                    oneTitleDb onetitledb4 = new oneTitleDb();
                    ArrayList arrayList3 = new ArrayList();
                    QueryBuilder<SectionBean> queryBuilderWhere = ProjectApp.mTiKuDaoSession.getSectionBeanDao().queryBuilder().where(SectionBeanDao.Properties.Chapter_parent_id.eq(list.get(i54).getChapter_id()), new WhereCondition[i3]);
                    Property[] propertyArr = new Property[i2];
                    propertyArr[i3] = SectionBeanDao.Properties.Sort;
                    List<SectionBean> list54 = queryBuilderWhere.orderAsc(propertyArr).list();
                    if (!SharePreferencesUtils.readStrConfig(CommonParameter.APP_TIKU_MARK, subjectFWNActivity.mContext, "").equals(CommonParameter.Xueshuo)) {
                        List<SectionBean> list55 = list54;
                        str = str4;
                        onetitledb = onetitledb4;
                        String str24 = str5;
                        if (getIntent().getBooleanExtra(str24, false)) {
                            onetitledb.setTotal(ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Chapter_parent_id.eq(list.get(i54).getChapter_id()), FavoritesBeanDao.Properties.Iszhuanshuo.eq("1"), FavoritesBeanDao.Properties.Is_practice.eq("1")).list().size() + "");
                            int i55 = 0;
                            while (i55 < list55.size()) {
                                twoTitleDb twotitledb25 = new twoTitleDb();
                                twotitledb25.setCate_id(list55.get(i55).getChapter_id() + "");
                                twotitledb25.setCate_name(list55.get(i55).getTitle());
                                twotitledb25.setCate_p_id("1");
                                String str25 = str24;
                                List<FavoritesBean> list56 = ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Chapter_id.eq(list55.get(i55).getChapter_id()), FavoritesBeanDao.Properties.Iszhuanshuo.eq("1"), FavoritesBeanDao.Properties.Is_practice.eq("1")).orderAsc(FavoritesBeanDao.Properties.Cate_num).list();
                                twotitledb25.setTotal(list56.size() + "");
                                if (list56.size() > 0) {
                                    long[] jArr25 = new long[list56.size()];
                                    for (int i56 = 0; i56 < list56.size(); i56++) {
                                        jArr25[i56] = list56.get(i56).getQuestion_id().longValue();
                                    }
                                    twotitledb25.setList_questionid(jArr25);
                                    arrayList3.add(twotitledb25);
                                }
                                i55++;
                                str24 = str25;
                            }
                            str2 = str24;
                        } else {
                            SubjectFWNActivity subjectFWNActivity8 = this;
                            str2 = str24;
                            int intConfig5 = SharePreferencesUtils.readIntConfig(str, subjectFWNActivity8.mContext, 6);
                            if (intConfig5 == 1) {
                                str = str;
                                List<SectionBean> list57 = list55;
                                onetitledb.setTotal(ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Chapter_parent_id.eq(list.get(i54).getChapter_id()), FavoritesBeanDao.Properties.Media_url.notEq(""), FavoritesBeanDao.Properties.Iszhuanshuo.eq("1"), FavoritesBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                                int i57 = 0;
                                while (i57 < list57.size()) {
                                    twoTitleDb twotitledb26 = new twoTitleDb();
                                    StringBuilder sb9 = new StringBuilder();
                                    List<SectionBean> list58 = list57;
                                    sb9.append(list58.get(i57).getChapter_id());
                                    sb9.append("");
                                    twotitledb26.setCate_id(sb9.toString());
                                    twotitledb26.setCate_name(list58.get(i57).getTitle());
                                    twotitledb26.setCate_p_id("1");
                                    List<FavoritesBean> list59 = ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Chapter_id.eq(list58.get(i57).getChapter_id()), FavoritesBeanDao.Properties.Media_url.notEq(""), FavoritesBeanDao.Properties.Iszhuanshuo.eq("1"), FavoritesBeanDao.Properties.Is_practice.eq("0")).orderAsc(FavoritesBeanDao.Properties.Cate_num).list();
                                    twotitledb26.setTotal(list59.size() + "");
                                    if (list59.size() > 0) {
                                        long[] jArr26 = new long[list59.size()];
                                        for (int i58 = 0; i58 < list59.size(); i58++) {
                                            jArr26[i58] = list59.get(i58).getQuestion_id().longValue();
                                        }
                                        twotitledb26.setList_questionid(jArr26);
                                        arrayList3.add(twotitledb26);
                                    }
                                    i57++;
                                    list57 = list58;
                                }
                            } else if (intConfig5 == 4) {
                                str = str;
                                SubjectFWNActivity subjectFWNActivity9 = this;
                                onetitledb.setTotal(ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Chapter_parent_id.eq(list.get(i54).getChapter_id()), FavoritesBeanDao.Properties.Year.lt(Integer.valueOf(SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FILTRATE_SELECT_YEAR, subjectFWNActivity9.mContext, R2.attr.icon_search))), FavoritesBeanDao.Properties.Iszhuanshuo.eq("1"), FavoritesBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                                int i59 = 0;
                                while (i59 < list55.size()) {
                                    twoTitleDb twotitledb27 = new twoTitleDb();
                                    twotitledb27.setCate_id(list55.get(i59).getChapter_id() + "");
                                    twotitledb27.setCate_name(list55.get(i59).getTitle());
                                    twotitledb27.setCate_p_id("1");
                                    List<SectionBean> list60 = list55;
                                    List<FavoritesBean> list61 = ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Chapter_id.eq(list55.get(i59).getChapter_id()), FavoritesBeanDao.Properties.Year.lt(Integer.valueOf(SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FILTRATE_SELECT_YEAR, subjectFWNActivity9.mContext, R2.attr.icon_search))), FavoritesBeanDao.Properties.Iszhuanshuo.eq("1"), FavoritesBeanDao.Properties.Is_practice.eq("0")).orderAsc(FavoritesBeanDao.Properties.Cate_num).list();
                                    twotitledb27.setTotal(list61.size() + "");
                                    if (list61.size() > 0) {
                                        long[] jArr27 = new long[list61.size()];
                                        for (int i60 = 0; i60 < list61.size(); i60++) {
                                            jArr27[i60] = list61.get(i60).getQuestion_id().longValue();
                                        }
                                        twotitledb27.setList_questionid(jArr27);
                                        arrayList3.add(twotitledb27);
                                    }
                                    i59++;
                                    subjectFWNActivity9 = this;
                                    list55 = list60;
                                }
                            } else if (intConfig5 == 6) {
                                str = str;
                                onetitledb.setTotal(ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Chapter_parent_id.eq(list.get(i54).getChapter_id()), FavoritesBeanDao.Properties.Iszhuanshuo.eq("1"), FavoritesBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                                for (int i61 = 0; i61 < list55.size(); i61++) {
                                    twoTitleDb twotitledb28 = new twoTitleDb();
                                    twotitledb28.setCate_id(list55.get(i61).getChapter_id() + "");
                                    twotitledb28.setCate_name(list55.get(i61).getTitle());
                                    twotitledb28.setCate_p_id("1");
                                    List<FavoritesBean> list62 = ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Chapter_id.eq(list55.get(i61).getChapter_id()), FavoritesBeanDao.Properties.Iszhuanshuo.eq("1"), FavoritesBeanDao.Properties.Is_practice.eq("0")).orderAsc(FavoritesBeanDao.Properties.Cate_num).list();
                                    twotitledb28.setTotal(list62.size() + "");
                                    if (list62.size() > 0) {
                                        long[] jArr28 = new long[list62.size()];
                                        for (int i62 = 0; i62 < list62.size(); i62++) {
                                            jArr28[i62] = list62.get(i62).getQuestion_id().longValue();
                                        }
                                        twotitledb28.setList_questionid(jArr28);
                                        arrayList3.add(twotitledb28);
                                    }
                                }
                            } else if (intConfig5 != 7) {
                                str = str;
                                onetitledb.setTotal(ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Chapter_parent_id.eq(list.get(i54).getChapter_id()), FavoritesBeanDao.Properties.Year.ge(Integer.valueOf(SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FILTRATE_SELECT_YEAR, subjectFWNActivity8.mContext, R2.attr.icon_search))), FavoritesBeanDao.Properties.Iszhuanshuo.eq("1"), FavoritesBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                                int i63 = 0;
                                while (i63 < list55.size()) {
                                    twoTitleDb twotitledb29 = new twoTitleDb();
                                    twotitledb29.setCate_id(list55.get(i63).getChapter_id() + "");
                                    twotitledb29.setCate_name(list55.get(i63).getTitle());
                                    twotitledb29.setCate_p_id("1");
                                    List<SectionBean> list63 = list55;
                                    List<FavoritesBean> list64 = ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Chapter_id.eq(list55.get(i63).getChapter_id()), FavoritesBeanDao.Properties.Year.ge(Integer.valueOf(SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FILTRATE_SELECT_YEAR, subjectFWNActivity8.mContext, R2.attr.icon_search))), FavoritesBeanDao.Properties.Iszhuanshuo.eq("1"), FavoritesBeanDao.Properties.Is_practice.eq("0")).orderAsc(FavoritesBeanDao.Properties.Cate_num).list();
                                    twotitledb29.setTotal(list64.size() + "");
                                    if (list64.size() > 0) {
                                        long[] jArr29 = new long[list64.size()];
                                        for (int i64 = 0; i64 < list64.size(); i64++) {
                                            jArr29[i64] = list64.get(i64).getQuestion_id().longValue();
                                        }
                                        twotitledb29.setList_questionid(jArr29);
                                        arrayList3.add(twotitledb29);
                                    }
                                    i63++;
                                    subjectFWNActivity8 = this;
                                    list55 = list63;
                                }
                            } else {
                                str = str;
                                List<SectionBean> list65 = list55;
                                onetitledb.setTotal(ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Chapter_parent_id.eq(list.get(i54).getChapter_id()), FavoritesBeanDao.Properties.Iszhuanshuo.eq("1"), FavoritesBeanDao.Properties.Isgaopinkaodian.eq("1"), FavoritesBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                                int i65 = 0;
                                while (i65 < list65.size()) {
                                    twoTitleDb twotitledb30 = new twoTitleDb();
                                    StringBuilder sb10 = new StringBuilder();
                                    List<SectionBean> list66 = list65;
                                    sb10.append(list66.get(i65).getChapter_id());
                                    sb10.append("");
                                    twotitledb30.setCate_id(sb10.toString());
                                    twotitledb30.setCate_name(list66.get(i65).getTitle());
                                    twotitledb30.setCate_p_id("1");
                                    List<FavoritesBean> list67 = ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Chapter_id.eq(list66.get(i65).getChapter_id()), FavoritesBeanDao.Properties.Iszhuanshuo.eq("1"), FavoritesBeanDao.Properties.Isgaopinkaodian.eq("1"), FavoritesBeanDao.Properties.Is_practice.eq("0")).orderAsc(FavoritesBeanDao.Properties.Cate_num).list();
                                    twotitledb30.setTotal(list67.size() + "");
                                    if (list67.size() > 0) {
                                        long[] jArr30 = new long[list67.size()];
                                        for (int i66 = 0; i66 < list67.size(); i66++) {
                                            jArr30[i66] = list67.get(i66).getQuestion_id().longValue();
                                        }
                                        twotitledb30.setList_questionid(jArr30);
                                        arrayList3.add(twotitledb30);
                                    }
                                    i65++;
                                    list65 = list66;
                                }
                            }
                        }
                    } else if (getIntent().getBooleanExtra(str5, i3)) {
                        onetitledb4.setTotal(ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Chapter_parent_id.eq(list.get(i54).getChapter_id()), FavoritesBeanDao.Properties.Isxueshuo.eq("1"), FavoritesBeanDao.Properties.Is_practice.eq("1")).list().size() + "");
                        int i67 = 0;
                        while (i67 < list54.size()) {
                            twoTitleDb twotitledb31 = new twoTitleDb();
                            twotitledb31.setCate_id(list54.get(i67).getChapter_id() + "");
                            twotitledb31.setCate_name(list54.get(i67).getTitle());
                            twotitledb31.setCate_p_id("1");
                            String str26 = str5;
                            List<SectionBean> list68 = list54;
                            oneTitleDb onetitledb5 = onetitledb4;
                            List<FavoritesBean> list69 = ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Chapter_id.eq(list54.get(i67).getChapter_id()), FavoritesBeanDao.Properties.Isxueshuo.eq("1"), FavoritesBeanDao.Properties.Is_practice.eq("1")).orderAsc(FavoritesBeanDao.Properties.Cate_num).list();
                            twotitledb31.setTotal(list69.size() + "");
                            if (list69.size() > 0) {
                                long[] jArr31 = new long[list69.size()];
                                for (int i68 = 0; i68 < list69.size(); i68++) {
                                    jArr31[i68] = list69.get(i68).getQuestion_id().longValue();
                                }
                                twotitledb31.setList_questionid(jArr31);
                                arrayList3.add(twotitledb31);
                            }
                            i67++;
                            list54 = list68;
                            str5 = str26;
                            onetitledb4 = onetitledb5;
                        }
                        str = str4;
                        str2 = str5;
                        onetitledb = onetitledb4;
                    } else {
                        List<SectionBean> list70 = list54;
                        str2 = str5;
                        int intConfig6 = SharePreferencesUtils.readIntConfig(str4, subjectFWNActivity.mContext, 6);
                        if (intConfig6 == 1) {
                            onetitledb = onetitledb4;
                            List<SectionBean> list71 = list70;
                            str = str4;
                            onetitledb.setTotal(ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Chapter_parent_id.eq(list.get(i54).getChapter_id()), FavoritesBeanDao.Properties.Media_url.notEq(""), FavoritesBeanDao.Properties.Isxueshuo.eq("1"), FavoritesBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                            int i69 = 0;
                            while (i69 < list71.size()) {
                                twoTitleDb twotitledb32 = new twoTitleDb();
                                StringBuilder sb11 = new StringBuilder();
                                List<SectionBean> list72 = list71;
                                sb11.append(list72.get(i69).getChapter_id());
                                sb11.append("");
                                twotitledb32.setCate_id(sb11.toString());
                                twotitledb32.setCate_name(list72.get(i69).getTitle());
                                twotitledb32.setCate_p_id("1");
                                List<FavoritesBean> list73 = ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Chapter_id.eq(list72.get(i69).getChapter_id()), FavoritesBeanDao.Properties.Media_url.notEq(""), FavoritesBeanDao.Properties.Isxueshuo.eq("1"), FavoritesBeanDao.Properties.Is_practice.eq("0")).orderAsc(FavoritesBeanDao.Properties.Cate_num).list();
                                twotitledb32.setTotal(list73.size() + "");
                                if (list73.size() > 0) {
                                    long[] jArr32 = new long[list73.size()];
                                    for (int i70 = 0; i70 < list73.size(); i70++) {
                                        jArr32[i70] = list73.get(i70).getQuestion_id().longValue();
                                    }
                                    twotitledb32.setList_questionid(jArr32);
                                    arrayList3.add(twotitledb32);
                                }
                                i69++;
                                list71 = list72;
                            }
                        } else if (intConfig6 == 4) {
                            List<SectionBean> list74 = list70;
                            onetitledb = onetitledb4;
                            str = str4;
                            SubjectFWNActivity subjectFWNActivity10 = this;
                            onetitledb.setTotal(ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Chapter_parent_id.eq(list.get(i54).getChapter_id()), FavoritesBeanDao.Properties.Year.lt(Integer.valueOf(SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FILTRATE_SELECT_YEAR, subjectFWNActivity10.mContext, R2.attr.icon_search))), FavoritesBeanDao.Properties.Isxueshuo.eq("1"), FavoritesBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                            int i71 = 0;
                            while (i71 < list74.size()) {
                                twoTitleDb twotitledb33 = new twoTitleDb();
                                twotitledb33.setCate_id(list74.get(i71).getChapter_id() + "");
                                twotitledb33.setCate_name(list74.get(i71).getTitle());
                                twotitledb33.setCate_p_id("1");
                                List<SectionBean> list75 = list74;
                                List<FavoritesBean> list76 = ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Chapter_id.eq(list74.get(i71).getChapter_id()), FavoritesBeanDao.Properties.Year.lt(Integer.valueOf(SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FILTRATE_SELECT_YEAR, subjectFWNActivity10.mContext, R2.attr.icon_search))), FavoritesBeanDao.Properties.Isxueshuo.eq("1"), FavoritesBeanDao.Properties.Is_practice.eq("0")).orderAsc(FavoritesBeanDao.Properties.Cate_num).list();
                                twotitledb33.setTotal(list76.size() + "");
                                if (list76.size() > 0) {
                                    long[] jArr33 = new long[list76.size()];
                                    for (int i72 = 0; i72 < list76.size(); i72++) {
                                        jArr33[i72] = list76.get(i72).getQuestion_id().longValue();
                                    }
                                    twotitledb33.setList_questionid(jArr33);
                                    arrayList3.add(twotitledb33);
                                }
                                i71++;
                                subjectFWNActivity10 = this;
                                list74 = list75;
                            }
                        } else if (intConfig6 == 6) {
                            onetitledb = onetitledb4;
                            str = str4;
                            onetitledb.setTotal(ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Chapter_parent_id.eq(list.get(i54).getChapter_id()), FavoritesBeanDao.Properties.Isxueshuo.eq("1"), FavoritesBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                            for (int i73 = 0; i73 < list70.size(); i73++) {
                                twoTitleDb twotitledb34 = new twoTitleDb();
                                twotitledb34.setCate_id(list70.get(i73).getChapter_id() + "");
                                twotitledb34.setCate_name(list70.get(i73).getTitle());
                                twotitledb34.setCate_p_id("1");
                                List<FavoritesBean> list77 = ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Chapter_id.eq(list70.get(i73).getChapter_id()), FavoritesBeanDao.Properties.Isxueshuo.eq("1"), FavoritesBeanDao.Properties.Is_practice.eq("0")).orderAsc(FavoritesBeanDao.Properties.Cate_num).list();
                                twotitledb34.setTotal(list77.size() + "");
                                if (list77.size() > 0) {
                                    long[] jArr34 = new long[list77.size()];
                                    for (int i74 = 0; i74 < list77.size(); i74++) {
                                        jArr34[i74] = list77.get(i74).getQuestion_id().longValue();
                                    }
                                    twotitledb34.setList_questionid(jArr34);
                                    arrayList3.add(twotitledb34);
                                }
                            }
                        } else if (intConfig6 != 7) {
                            onetitledb = onetitledb4;
                            onetitledb.setTotal(ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Chapter_parent_id.eq(list.get(i54).getChapter_id()), FavoritesBeanDao.Properties.Year.ge(Integer.valueOf(SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FILTRATE_SELECT_YEAR, subjectFWNActivity.mContext, R2.attr.icon_search))), FavoritesBeanDao.Properties.Isxueshuo.eq("1"), FavoritesBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                            int i75 = 0;
                            while (i75 < list70.size()) {
                                twoTitleDb twotitledb35 = new twoTitleDb();
                                StringBuilder sb12 = new StringBuilder();
                                List<SectionBean> list78 = list70;
                                sb12.append(list78.get(i75).getChapter_id());
                                sb12.append("");
                                twotitledb35.setCate_id(sb12.toString());
                                twotitledb35.setCate_name(list78.get(i75).getTitle());
                                twotitledb35.setCate_p_id("1");
                                String str27 = str4;
                                List<FavoritesBean> list79 = ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Chapter_id.eq(list78.get(i75).getChapter_id()), FavoritesBeanDao.Properties.Year.ge(Integer.valueOf(SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FILTRATE_SELECT_YEAR, subjectFWNActivity.mContext, R2.attr.icon_search))), FavoritesBeanDao.Properties.Isxueshuo.eq("1"), FavoritesBeanDao.Properties.Is_practice.eq("0")).orderAsc(FavoritesBeanDao.Properties.Cate_num).list();
                                twotitledb35.setTotal(list79.size() + "");
                                if (list79.size() > 0) {
                                    long[] jArr35 = new long[list79.size()];
                                    for (int i76 = 0; i76 < list79.size(); i76++) {
                                        jArr35[i76] = list79.get(i76).getQuestion_id().longValue();
                                    }
                                    twotitledb35.setList_questionid(jArr35);
                                    arrayList3.add(twotitledb35);
                                }
                                i75++;
                                subjectFWNActivity = this;
                                str4 = str27;
                                list70 = list78;
                            }
                            str = str4;
                        } else {
                            onetitledb = onetitledb4;
                            List<SectionBean> list80 = list70;
                            str = str4;
                            onetitledb.setTotal(ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Chapter_parent_id.eq(list.get(i54).getChapter_id()), FavoritesBeanDao.Properties.Isxueshuo.eq("1"), FavoritesBeanDao.Properties.Isgaopinkaodian.eq("1"), FavoritesBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                            int i77 = 0;
                            while (i77 < list80.size()) {
                                twoTitleDb twotitledb36 = new twoTitleDb();
                                StringBuilder sb13 = new StringBuilder();
                                List<SectionBean> list81 = list80;
                                sb13.append(list81.get(i77).getChapter_id());
                                sb13.append("");
                                twotitledb36.setCate_id(sb13.toString());
                                twotitledb36.setCate_name(list81.get(i77).getTitle());
                                twotitledb36.setCate_p_id("1");
                                List<FavoritesBean> list82 = ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Chapter_id.eq(list81.get(i77).getChapter_id()), FavoritesBeanDao.Properties.Isxueshuo.eq("1"), FavoritesBeanDao.Properties.Isgaopinkaodian.eq("1"), FavoritesBeanDao.Properties.Is_practice.eq("0")).orderAsc(FavoritesBeanDao.Properties.Cate_num).list();
                                twotitledb36.setTotal(list82.size() + "");
                                if (list82.size() > 0) {
                                    long[] jArr36 = new long[list82.size()];
                                    for (int i78 = 0; i78 < list82.size(); i78++) {
                                        jArr36[i78] = list82.get(i78).getQuestion_id().longValue();
                                    }
                                    twotitledb36.setList_questionid(jArr36);
                                    arrayList3.add(twotitledb36);
                                }
                                i77++;
                                list80 = list81;
                            }
                        }
                    }
                    onetitledb.setChapters(arrayList3);
                    onetitledb.setCate_p_id(list.get(i54).getChapter_id() + "");
                    onetitledb.setCate_name(list.get(i54).getTitle() + "");
                    onetitledb.setCate_user_count(0);
                    if (Integer.parseInt(onetitledb.getTotal()) > 0) {
                        subjectFWNActivity = this;
                        subjectFWNActivity.sListDb.add(onetitledb);
                    } else {
                        subjectFWNActivity = this;
                    }
                    i54++;
                    str4 = str;
                    str5 = str2;
                    i2 = 1;
                    i3 = 0;
                }
                break;
        }
        if (isRefush) {
            SubjectFWNExpandableListAdapter subjectFWNExpandableListAdapter = new SubjectFWNExpandableListAdapter(subjectFWNActivity.mContext, subjectFWNActivity.sListDb, subjectFWNActivity.type);
            subjectFWNActivity.mAdapter = subjectFWNExpandableListAdapter;
            subjectFWNActivity.elv_subject.setAdapter(subjectFWNExpandableListAdapter);
            if (subjectFWNActivity.sListDb.size() > 0) {
                subjectFWNActivity.findViewById(R.id.tv_empty).setVisibility(8);
            } else {
                subjectFWNActivity.findViewById(R.id.tv_empty).setVisibility(0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDataXiTong(boolean isRefush) {
        int i2;
        int i3;
        List<SectionPartBean> list;
        int i4;
        i2 = 0;
        i3 = 1;
        list = ProjectApp.mTiKuDaoSession.getSectionPartBeanDao().queryBuilder().where(SectionPartBeanDao.Properties.Parent_id.eq("0"), new WhereCondition[0]).orderAsc(SectionPartBeanDao.Properties.Sort).list();
        this.sListDb = new ArrayList<>();
        String str = this.type;
        str.hashCode();
        i4 = 2;
        switch (str) {
            case "note":
                for (int i5 = 0; i5 < list.size(); i5++) {
                    oneTitleDb onetitledb = new oneTitleDb();
                    ArrayList arrayList = new ArrayList();
                    List<SectionPartBean> list2 = ProjectApp.mTiKuDaoSession.getSectionPartBeanDao().queryBuilder().where(SectionPartBeanDao.Properties.Parent_id.eq(list.get(i5).getPart_id()), new WhereCondition[0]).orderAsc(SectionPartBeanDao.Properties.Sort).list();
                    if (SharePreferencesUtils.readStrConfig(CommonParameter.APP_TIKU_MARK, this.mContext, "").equals(CommonParameter.Xueshuo)) {
                        onetitledb.setTotal(ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Part_parent_id.eq(list.get(i5).getPart_id()), NotesBeanDao.Properties.Isxueshuo.eq("1"), NotesBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                        for (int i6 = 0; i6 < list2.size(); i6++) {
                            twoTitleDb twotitledb = new twoTitleDb();
                            twotitledb.setCate_id(list2.get(i6).getPart_id() + "");
                            twotitledb.setCate_name(list2.get(i6).getTitle());
                            twotitledb.setCate_p_id("1");
                            List<NotesBean> list3 = ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Part_id.eq(list2.get(i6).getPart_id()), NotesBeanDao.Properties.Isxueshuo.eq("1"), NotesBeanDao.Properties.Is_practice.eq("0")).orderAsc(NotesBeanDao.Properties.Part_num_am).list();
                            twotitledb.setTotal(list3.size() + "");
                            if (list3.size() > 0) {
                                long[] jArr = new long[list3.size()];
                                for (int i7 = 0; i7 < list3.size(); i7++) {
                                    jArr[i7] = list3.get(i7).getQuestion_id().longValue();
                                }
                                twotitledb.setList_questionid(jArr);
                                arrayList.add(twotitledb);
                            }
                        }
                    } else {
                        onetitledb.setTotal(ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Part_parent_id.eq(list.get(i5).getPart_id()), NotesBeanDao.Properties.Iszhuanshuo.eq("1"), NotesBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                        for (int i8 = 0; i8 < list2.size(); i8++) {
                            twoTitleDb twotitledb2 = new twoTitleDb();
                            twotitledb2.setCate_id(list2.get(i8).getPart_id() + "");
                            twotitledb2.setCate_name(list2.get(i8).getTitle());
                            twotitledb2.setCate_p_id("1");
                            List<NotesBean> list4 = ProjectApp.mDaoSession.getNotesBeanDao().queryBuilder().where(NotesBeanDao.Properties.Part_id.eq(list2.get(i8).getPart_id()), NotesBeanDao.Properties.Iszhuanshuo.eq("1"), NotesBeanDao.Properties.Is_practice.eq("0")).orderAsc(NotesBeanDao.Properties.Part_num_pm).list();
                            twotitledb2.setTotal(list4.size() + "");
                            if (list4.size() > 0) {
                                long[] jArr2 = new long[list4.size()];
                                for (int i9 = 0; i9 < list4.size(); i9++) {
                                    jArr2[i9] = list4.get(i9).getQuestion_id().longValue();
                                }
                                twotitledb2.setList_questionid(jArr2);
                                arrayList.add(twotitledb2);
                            }
                        }
                    }
                    onetitledb.setChapters(arrayList);
                    onetitledb.setCate_p_id(list.get(i5).getPart_id() + "");
                    onetitledb.setCate_name(list.get(i5).getTitle() + "");
                    onetitledb.setCate_user_count(0);
                    if (Integer.parseInt(onetitledb.getTotal()) > 0) {
                        this.sListDb.add(onetitledb);
                    }
                }
                break;
            case "error":
                for (int i10 = 0; i10 < list.size(); i10++) {
                    oneTitleDb onetitledb2 = new oneTitleDb();
                    ArrayList arrayList2 = new ArrayList();
                    List<SectionPartBean> list5 = ProjectApp.mTiKuDaoSession.getSectionPartBeanDao().queryBuilder().where(SectionPartBeanDao.Properties.Parent_id.eq(list.get(i10).getPart_id()), new WhereCondition[0]).orderAsc(SectionPartBeanDao.Properties.Sort).list();
                    if (SharePreferencesUtils.readStrConfig(CommonParameter.APP_TIKU_MARK, this.mContext, "").equals(CommonParameter.Xueshuo)) {
                        onetitledb2.setTotal(ProjectApp.mDaoSession.getWrongBeanDao().queryBuilder().where(WrongBeanDao.Properties.Part_parent_id.eq(list.get(i10).getPart_id()), WrongBeanDao.Properties.Isxueshuo.eq("1"), WrongBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                        for (int i11 = 0; i11 < list5.size(); i11++) {
                            twoTitleDb twotitledb3 = new twoTitleDb();
                            twotitledb3.setCate_id(list5.get(i11).getPart_id() + "");
                            twotitledb3.setCate_name(list5.get(i11).getTitle());
                            twotitledb3.setCate_p_id("1");
                            List<WrongBean> list6 = ProjectApp.mDaoSession.getWrongBeanDao().queryBuilder().where(WrongBeanDao.Properties.Part_id.eq(list5.get(i11).getPart_id()), WrongBeanDao.Properties.Isxueshuo.eq("1"), WrongBeanDao.Properties.Is_practice.eq("0")).orderAsc(WrongBeanDao.Properties.Part_num_am).list();
                            twotitledb3.setTotal(list6.size() + "");
                            if (list6.size() > 0) {
                                long[] jArr3 = new long[list6.size()];
                                for (int i12 = 0; i12 < list6.size(); i12++) {
                                    jArr3[i12] = list6.get(i12).getQuestion_id().longValue();
                                }
                                twotitledb3.setList_questionid(jArr3);
                                arrayList2.add(twotitledb3);
                            }
                        }
                    } else {
                        onetitledb2.setTotal(ProjectApp.mDaoSession.getWrongBeanDao().queryBuilder().where(WrongBeanDao.Properties.Part_parent_id.eq(list.get(i10).getPart_id()), WrongBeanDao.Properties.Iszhuanshuo.eq("1"), WrongBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                        for (int i13 = 0; i13 < list5.size(); i13++) {
                            twoTitleDb twotitledb4 = new twoTitleDb();
                            twotitledb4.setCate_id(list5.get(i13).getPart_id() + "");
                            twotitledb4.setCate_name(list5.get(i13).getTitle());
                            twotitledb4.setCate_p_id("1");
                            List<WrongBean> list7 = ProjectApp.mDaoSession.getWrongBeanDao().queryBuilder().where(WrongBeanDao.Properties.Part_id.eq(list5.get(i13).getPart_id()), WrongBeanDao.Properties.Iszhuanshuo.eq("1"), WrongBeanDao.Properties.Is_practice.eq("0")).orderAsc(WrongBeanDao.Properties.Part_num_pm).list();
                            twotitledb4.setTotal(list7.size() + "");
                            if (list7.size() > 0) {
                                long[] jArr4 = new long[list7.size()];
                                for (int i14 = 0; i14 < list7.size(); i14++) {
                                    jArr4[i14] = list7.get(i14).getQuestion_id().longValue();
                                }
                                twotitledb4.setList_questionid(jArr4);
                                arrayList2.add(twotitledb4);
                            }
                        }
                    }
                    onetitledb2.setChapters(arrayList2);
                    onetitledb2.setCate_p_id(list.get(i10).getPart_id() + "");
                    onetitledb2.setCate_name(list.get(i10).getTitle() + "");
                    onetitledb2.setCate_user_count(0);
                    if (Integer.parseInt(onetitledb2.getTotal()) > 0) {
                        this.sListDb.add(onetitledb2);
                    }
                }
                break;
            case "collect":
                int i15 = 0;
                while (i15 < list.size()) {
                    oneTitleDb onetitledb3 = new oneTitleDb();
                    ArrayList arrayList3 = new ArrayList();
                    QueryBuilder<SectionPartBean> queryBuilderWhere = ProjectApp.mTiKuDaoSession.getSectionPartBeanDao().queryBuilder().where(SectionPartBeanDao.Properties.Parent_id.eq(list.get(i15).getPart_id()), new WhereCondition[i2]);
                    Property[] propertyArr = new Property[i3];
                    propertyArr[i2] = SectionPartBeanDao.Properties.Sort;
                    List<SectionPartBean> list8 = queryBuilderWhere.orderAsc(propertyArr).list();
                    if (SharePreferencesUtils.readStrConfig(CommonParameter.APP_TIKU_MARK, this.mContext, "").equals(CommonParameter.Xueshuo)) {
                        QueryBuilder<FavoritesBean> queryBuilder = ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder();
                        WhereCondition whereConditionEq = FavoritesBeanDao.Properties.Part_parent_id.eq(list.get(i15).getPart_id());
                        WhereCondition[] whereConditionArr = new WhereCondition[i4];
                        whereConditionArr[i2] = FavoritesBeanDao.Properties.Isxueshuo.eq("1");
                        whereConditionArr[i3] = FavoritesBeanDao.Properties.Is_practice.eq("0");
                        onetitledb3.setTotal(queryBuilder.where(whereConditionEq, whereConditionArr).list().size() + "");
                        int i16 = i2;
                        while (i16 < list8.size()) {
                            twoTitleDb twotitledb5 = new twoTitleDb();
                            twotitledb5.setCate_id(list8.get(i16).getPart_id() + "");
                            twotitledb5.setCate_name(list8.get(i16).getTitle());
                            twotitledb5.setCate_p_id("1");
                            QueryBuilder<FavoritesBean> queryBuilder2 = ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder();
                            WhereCondition whereConditionEq2 = FavoritesBeanDao.Properties.Part_id.eq(list8.get(i16).getPart_id());
                            WhereCondition[] whereConditionArr2 = new WhereCondition[i4];
                            whereConditionArr2[i2] = FavoritesBeanDao.Properties.Isxueshuo.eq("1");
                            whereConditionArr2[1] = FavoritesBeanDao.Properties.Is_practice.eq("0");
                            List<FavoritesBean> list9 = queryBuilder2.where(whereConditionEq2, whereConditionArr2).orderAsc(FavoritesBeanDao.Properties.Part_num_am).list();
                            twotitledb5.setTotal(list9.size() + "");
                            if (list9.size() > 0) {
                                long[] jArr5 = new long[list9.size()];
                                for (int i17 = 0; i17 < list9.size(); i17++) {
                                    jArr5[i17] = list9.get(i17).getQuestion_id().longValue();
                                }
                                twotitledb5.setList_questionid(jArr5);
                                arrayList3.add(twotitledb5);
                            }
                            i16++;
                            i2 = 0;
                            i4 = 2;
                        }
                    } else {
                        onetitledb3.setTotal(ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Part_parent_id.eq(list.get(i15).getPart_id()), FavoritesBeanDao.Properties.Iszhuanshuo.eq("1"), FavoritesBeanDao.Properties.Is_practice.eq("0")).list().size() + "");
                        for (int i18 = 0; i18 < list8.size(); i18++) {
                            twoTitleDb twotitledb6 = new twoTitleDb();
                            twotitledb6.setCate_id(list8.get(i18).getPart_id() + "");
                            twotitledb6.setCate_name(list8.get(i18).getTitle());
                            twotitledb6.setCate_p_id("1");
                            List<FavoritesBean> list10 = ProjectApp.mDaoSession.getFavoritesBeanDao().queryBuilder().where(FavoritesBeanDao.Properties.Part_id.eq(list8.get(i18).getPart_id()), FavoritesBeanDao.Properties.Iszhuanshuo.eq("1"), FavoritesBeanDao.Properties.Is_practice.eq("0")).orderAsc(FavoritesBeanDao.Properties.Part_num_pm).list();
                            twotitledb6.setTotal(list10.size() + "");
                            if (list10.size() > 0) {
                                long[] jArr6 = new long[list10.size()];
                                for (int i19 = 0; i19 < list10.size(); i19++) {
                                    jArr6[i19] = list10.get(i19).getQuestion_id().longValue();
                                }
                                twotitledb6.setList_questionid(jArr6);
                                arrayList3.add(twotitledb6);
                            }
                        }
                    }
                    onetitledb3.setChapters(arrayList3);
                    onetitledb3.setCate_p_id(list.get(i15).getPart_id() + "");
                    onetitledb3.setCate_name(list.get(i15).getTitle() + "");
                    onetitledb3.setCate_user_count(0);
                    if (Integer.parseInt(onetitledb3.getTotal()) > 0) {
                        this.sListDb.add(onetitledb3);
                    }
                    i15++;
                    i3 = 1;
                    i2 = 0;
                    i4 = 2;
                }
                break;
        }
        if (isRefush) {
            SubjectFWNExpandableListAdapter subjectFWNExpandableListAdapter = new SubjectFWNExpandableListAdapter(this.mContext, this.sListDb, this.type);
            this.mAdapter = subjectFWNExpandableListAdapter;
            this.elv_subject.setAdapter(subjectFWNExpandableListAdapter);
            if (this.sListDb.size() > 0) {
                findViewById(R.id.tv_empty).setVisibility(8);
            } else {
                findViewById(R.id.tv_empty).setVisibility(0);
            }
        }
    }

    public void clearnDialog() {
        final CustomDialog customDialog = new CustomDialog(this.mContext, 2);
        customDialog.setCancelable(false);
        customDialog.setMessage("是否确定重做所有的错题");
        customDialog.setNegativeBtn(R.string.cancel, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.hm
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubjectFWNActivity.lambda$clearnDialog$0(customDialog, view);
            }
        });
        customDialog.setPositiveBtn(R.string.ok, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.im
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws SQLException {
                this.f12520c.lambda$clearnDialog$1(customDialog, view);
            }
        });
        customDialog.show();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.elv_subject = (ExpandableListView) findViewById(R.id.elv_subject);
        ((SwipeRefreshLayout) findViewById(R.id.swipe1)).setEnabled(false);
        new ProgressBarAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Integer[0]);
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

    public void onEventMainThread(ComSubFWNItemBean mComSubFWNItemBean) {
        if (mComSubFWNItemBean.getmEventStr().equals("SubjectQuestionClearn") || mComSubFWNItemBean.getmEventStr().equals("SubjectQuestionClearnEdit")) {
            new ProgressBarAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Integer[0]);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        String stringExtra = getIntent().getStringExtra("type");
        this.type = stringExtra;
        if (stringExtra == null) {
            stringExtra = "";
        }
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
        this.mBtnActionbarRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.SubjectFWNActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                if (CommonUtil.isFastClick()) {
                    return;
                }
                if (!SubjectFWNActivity.this.getIntent().getStringExtra("type").equals("note") && !SubjectFWNActivity.this.getIntent().getStringExtra("type").equals("error") && !SubjectFWNActivity.this.getIntent().getStringExtra("type").equals("collect")) {
                    SubjectFWNActivity.this.clearnDialog();
                    return;
                }
                if (UserConfig.getInstance().getUser().getIs_vip().equals("0")) {
                    SubjectFWNActivity.this.startActivity(new Intent(SubjectFWNActivity.this, (Class<?>) MemberCenterActivity.class));
                } else {
                    XPopup.Builder builder = new XPopup.Builder(SubjectFWNActivity.this);
                    SubjectFWNActivity subjectFWNActivity = SubjectFWNActivity.this;
                    builder.asCustom(new ExportDescriptionPop(subjectFWNActivity, subjectFWNActivity.getIntent().getExtras())).toggle();
                }
            }
        });
        this.elv_subject.setOnChildClickListener(new ExpandableListView.OnChildClickListener() { // from class: com.psychiatrygarden.activity.jm
            @Override // android.widget.ExpandableListView.OnChildClickListener
            public final boolean onChildClick(ExpandableListView expandableListView, View view, int i2, int i3, long j2) {
                return this.f12565a.lambda$setListenerForWidget$2(expandableListView, view, i2, i3, j2);
            }
        });
        this.elv_subject.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() { // from class: com.psychiatrygarden.activity.km
            @Override // android.widget.ExpandableListView.OnGroupClickListener
            public final boolean onGroupClick(ExpandableListView expandableListView, View view, int i2, long j2) {
                return this.f12597a.lambda$setListenerForWidget$3(expandableListView, view, i2, j2);
            }
        });
        this.elv_subject.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() { // from class: com.psychiatrygarden.activity.lm
            @Override // android.widget.ExpandableListView.OnGroupExpandListener
            public final void onGroupExpand(int i2) {
                this.f12673a.lambda$setListenerForWidget$4(i2);
            }
        });
    }
}
