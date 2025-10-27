package com.psychiatrygarden.bean;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.psychiatrygarden.db.MigrationHelper;
import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.identityscope.IdentityScopeType;

/* loaded from: classes5.dex */
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 7;

    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) throws SQLException {
            QuestionCacheVideoBeanDao.createTable(db, true);
            QuestionVideoLocationBeanDao.createTable(db, true);
            CourseVideoLocationBeanDao.createTable(db, true);
            WrongBeanBeiDao.createTable(db, true);
            FavoritesBeanBeiDao.createTable(db, true);
            NotesBeanBeiDao.createTable(db, true);
            SubmitFavoritesBeanBeiDao.createTable(db, true);
            SubmitAnsweredQuestionBeanBeiDao.createTable(db, true);
            SubmitNotesBeanBeiDao.createTable(db, true);
            AnsweredQuestionBeanBeiDao.createTable(db, true);
            QuestionDataStatisticsBeanDao.createTable(db, true);
            MigrationHelper.getInstance().migrate(1, db, AnsweredQuestionBeanDao.class, SubmitAnsweredQuestionBeanDao.class, FavoritesBeanDao.class, SubmitFavoritesBeanDao.class, WrongBeanDao.class, NotesBeanDao.class, SubmitNotesBeanDao.class, QuestionDataStatisticsBeanDao.class, QuestionCacheVideoBeanDao.class, QuestionVideoLocationBeanDao.class, CourseVideoLocationBeanDao.class, WrongBeanBeiDao.class, FavoritesBeanBeiDao.class, NotesBeanBeiDao.class, SubmitFavoritesBeanBeiDao.class, SubmitAnsweredQuestionBeanBeiDao.class, SubmitNotesBeanBeiDao.class, AnsweredQuestionBeanBeiDao.class);
        }
    }

    public static abstract class OpenHelper extends SQLiteOpenHelper {
        public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory, 7);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase db) {
            Log.i("greenDAO", "Creating tables for schema version 7");
            DaoMaster.createAllTables(db, false);
        }
    }

    public DaoMaster(SQLiteDatabase db) {
        super(db, 7);
        registerDaoClass(AnsweredQuestionBeanDao.class);
        registerDaoClass(SubmitAnsweredQuestionBeanDao.class);
        registerDaoClass(FavoritesBeanDao.class);
        registerDaoClass(SubmitFavoritesBeanDao.class);
        registerDaoClass(WrongBeanDao.class);
        registerDaoClass(NotesBeanDao.class);
        registerDaoClass(SubmitNotesBeanDao.class);
        registerDaoClass(QuestionSelfStatisticsBeanDao.class);
        registerDaoClass(QuestionDataStatisticsBeanDao.class);
        registerDaoClass(CircleCategoryBeanDao.class);
        registerDaoClass(CircleCategoryLableBeanDao.class);
        registerDaoClass(CircleCategoryCollectBeanDao.class);
        registerDaoClass(CircleSchoolBeanDao.class);
        registerDaoClass(CircleTopicHistoryBeanDao.class);
        registerDaoClass(QuestionCacheVideoBeanDao.class);
        registerDaoClass(QuestionVideoLocationBeanDao.class);
        registerDaoClass(CourseVideoLocationBeanDao.class);
        registerDaoClass(WrongBeanBeiDao.class);
        registerDaoClass(NotesBeanBeiDao.class);
        registerDaoClass(FavoritesBeanBeiDao.class);
        registerDaoClass(SubmitNotesBeanBeiDao.class);
        registerDaoClass(SubmitAnsweredQuestionBeanBeiDao.class);
        registerDaoClass(SubmitFavoritesBeanBeiDao.class);
        registerDaoClass(AnsweredQuestionBeanBeiDao.class);
    }

    public static void createAllTables(SQLiteDatabase db, boolean ifNotExists) {
        AnsweredQuestionBeanDao.createTable(db, ifNotExists);
        SubmitAnsweredQuestionBeanDao.createTable(db, ifNotExists);
        FavoritesBeanDao.createTable(db, ifNotExists);
        SubmitFavoritesBeanDao.createTable(db, ifNotExists);
        WrongBeanDao.createTable(db, ifNotExists);
        NotesBeanDao.createTable(db, ifNotExists);
        SubmitNotesBeanDao.createTable(db, ifNotExists);
        QuestionSelfStatisticsBeanDao.createTable(db, ifNotExists);
        QuestionDataStatisticsBeanDao.createTable(db, ifNotExists);
        CircleCategoryBeanDao.createTable(db, ifNotExists);
        CircleCategoryLableBeanDao.createTable(db, ifNotExists);
        CircleCategoryCollectBeanDao.createTable(db, ifNotExists);
        CircleSchoolBeanDao.createTable(db, ifNotExists);
        CircleTopicHistoryBeanDao.createTable(db, ifNotExists);
        QuestionCacheVideoBeanDao.createTable(db, ifNotExists);
        QuestionVideoLocationBeanDao.createTable(db, ifNotExists);
        CourseVideoLocationBeanDao.createTable(db, ifNotExists);
        WrongBeanBeiDao.createTable(db, ifNotExists);
        FavoritesBeanBeiDao.createTable(db, ifNotExists);
        NotesBeanBeiDao.createTable(db, ifNotExists);
        SubmitNotesBeanBeiDao.createTable(db, ifNotExists);
        SubmitAnsweredQuestionBeanBeiDao.createTable(db, ifNotExists);
        SubmitFavoritesBeanBeiDao.createTable(db, ifNotExists);
        AnsweredQuestionBeanBeiDao.createTable(db, ifNotExists);
    }

    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        AnsweredQuestionBeanDao.dropTable(db, ifExists);
        SubmitAnsweredQuestionBeanDao.dropTable(db, ifExists);
        FavoritesBeanDao.dropTable(db, ifExists);
        SubmitFavoritesBeanDao.dropTable(db, ifExists);
        WrongBeanDao.dropTable(db, ifExists);
        NotesBeanDao.dropTable(db, ifExists);
        SubmitNotesBeanDao.dropTable(db, ifExists);
        QuestionSelfStatisticsBeanDao.dropTable(db, ifExists);
        QuestionDataStatisticsBeanDao.dropTable(db, ifExists);
        CircleCategoryBeanDao.dropTable(db, ifExists);
        CircleCategoryLableBeanDao.dropTable(db, ifExists);
        CircleCategoryCollectBeanDao.dropTable(db, ifExists);
        CircleSchoolBeanDao.dropTable(db, ifExists);
        CircleTopicHistoryBeanDao.dropTable(db, ifExists);
        QuestionCacheVideoBeanDao.dropTable(db, ifExists);
        QuestionVideoLocationBeanDao.dropTable(db, ifExists);
        CourseVideoLocationBeanDao.dropTable(db, ifExists);
        WrongBeanBeiDao.dropTable(db, ifExists);
        FavoritesBeanBeiDao.dropTable(db, ifExists);
        NotesBeanBeiDao.dropTable(db, ifExists);
        SubmitNotesBeanBeiDao.dropTable(db, ifExists);
        SubmitAnsweredQuestionBeanBeiDao.dropTable(db, ifExists);
        SubmitFavoritesBeanBeiDao.dropTable(db, ifExists);
        AnsweredQuestionBeanBeiDao.dropTable(db, ifExists);
    }

    @Override // de.greenrobot.dao.AbstractDaoMaster
    public DaoSession newSession() {
        return new DaoSession(this.db, IdentityScopeType.Session, this.daoConfigMap);
    }

    @Override // de.greenrobot.dao.AbstractDaoMaster
    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(this.db, type, this.daoConfigMap);
    }
}
