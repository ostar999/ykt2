package com.psychiatrygarden.bean;

import android.database.sqlite.SQLiteDatabase;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;
import java.util.Map;

/* loaded from: classes5.dex */
public class DaoSession extends AbstractDaoSession {
    private final AnsweredQuestionBeanBeiDao answeredQuestionBeanBeiDao;
    private final DaoConfig answeredQuestionBeanBeiDaoConfig;
    private final AnsweredQuestionBeanDao answeredQuestionBeanDao;
    private final DaoConfig answeredQuestionBeanDaoConfig;
    private final CircleCategoryBeanDao circleCategoryBeanDao;
    private final DaoConfig circleCategoryBeanDaoConfig;
    private final CircleCategoryCollectBeanDao circleCategoryCollectBeanDao;
    private final DaoConfig circleCategoryCollectBeanDaoConfig;
    private final CircleCategoryLableBeanDao circleCategoryLableBeanDao;
    private final DaoConfig circleCategoryLableBeanDaoConfig;
    private final CircleSchoolBeanDao circleSchoolBeanDao;
    private final DaoConfig circleSchoolBeanDaoConfig;
    private final CircleTopicHistoryBeanDao circleTopicHistoryBeanDao;
    private final DaoConfig circleTopicHistoryBeanDaoConfig;
    private final CourseVideoLocationBeanDao courseVideoLocationBeanDao;
    private final DaoConfig courseVideoLocationBeanDaoConfig;
    private final FavoritesBeanBeiDao favoritesBeanBeiDao;
    private final DaoConfig favoritesBeanBeiDaoConfig;
    private final FavoritesBeanDao favoritesBeanDao;
    private final DaoConfig favoritesBeanDaoConfig;
    private final NotesBeanBeiDao notesBeanBeiDao;
    private final DaoConfig notesBeanBeiDaoConfig;
    private final NotesBeanDao notesBeanDao;
    private final DaoConfig notesBeanDaoConfig;
    private final QuestionCacheVideoBeanDao questionCacheVideoBeanDao;
    private final DaoConfig questionCacheVideoBeanDaoConfig;
    private final QuestionDataStatisticsBeanDao questionDataStatisticsBeanDao;
    private final DaoConfig questionDataStatisticsBeanDaoConfig;
    private final QuestionSelfStatisticsBeanDao questionSelfStatisticsBeanDao;
    private final DaoConfig questionSelfStatisticsBeanDaoConfig;
    private final QuestionVideoLocationBeanDao questionVideoLocationBeanDao;
    private final DaoConfig questionVideoLocationBeanDaoConfig;
    private final SubmitAnsweredQuestionBeanBeiDao submitAnsweredQuestionBeanBeiDao;
    private final DaoConfig submitAnsweredQuestionBeanBeiDaoConfig;
    private final SubmitAnsweredQuestionBeanDao submitAnsweredQuestionBeanDao;
    private final DaoConfig submitAnsweredQuestionBeanDaoConfig;
    private final SubmitFavoritesBeanBeiDao submitFavoritesBeanBeiDao;
    private final DaoConfig submitFavoritesBeanBeiDaoConfig;
    private final SubmitFavoritesBeanDao submitFavoritesBeanDao;
    private final DaoConfig submitFavoritesBeanDaoConfig;
    private final SubmitNotesBeanBeiDao submitNotesBeanBeiDao;
    private final DaoConfig submitNotesBeanBeiDaoConfig;
    private final SubmitNotesBeanDao submitNotesBeanDao;
    private final DaoConfig submitNotesBeanDaoConfig;
    private final WrongBeanBeiDao wrongBeanBeiDao;
    private final DaoConfig wrongBeanBeiDaoConfig;
    private final WrongBeanDao wrongBeanDao;
    private final DaoConfig wrongBeanDaoConfig;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> daoConfigMap) {
        super(db);
        DaoConfig daoConfigM778clone = daoConfigMap.get(AnsweredQuestionBeanDao.class).m778clone();
        this.answeredQuestionBeanDaoConfig = daoConfigM778clone;
        daoConfigM778clone.initIdentityScope(type);
        DaoConfig daoConfigM778clone2 = daoConfigMap.get(SubmitAnsweredQuestionBeanDao.class).m778clone();
        this.submitAnsweredQuestionBeanDaoConfig = daoConfigM778clone2;
        daoConfigM778clone2.initIdentityScope(type);
        DaoConfig daoConfigM778clone3 = daoConfigMap.get(FavoritesBeanDao.class).m778clone();
        this.favoritesBeanDaoConfig = daoConfigM778clone3;
        daoConfigM778clone3.initIdentityScope(type);
        DaoConfig daoConfigM778clone4 = daoConfigMap.get(SubmitFavoritesBeanDao.class).m778clone();
        this.submitFavoritesBeanDaoConfig = daoConfigM778clone4;
        daoConfigM778clone4.initIdentityScope(type);
        DaoConfig daoConfigM778clone5 = daoConfigMap.get(WrongBeanDao.class).m778clone();
        this.wrongBeanDaoConfig = daoConfigM778clone5;
        daoConfigM778clone5.initIdentityScope(type);
        DaoConfig daoConfigM778clone6 = daoConfigMap.get(NotesBeanDao.class).m778clone();
        this.notesBeanDaoConfig = daoConfigM778clone6;
        daoConfigM778clone6.initIdentityScope(type);
        DaoConfig daoConfigM778clone7 = daoConfigMap.get(SubmitNotesBeanDao.class).m778clone();
        this.submitNotesBeanDaoConfig = daoConfigM778clone7;
        daoConfigM778clone7.initIdentityScope(type);
        DaoConfig daoConfigM778clone8 = daoConfigMap.get(QuestionSelfStatisticsBeanDao.class).m778clone();
        this.questionSelfStatisticsBeanDaoConfig = daoConfigM778clone8;
        daoConfigM778clone8.initIdentityScope(type);
        DaoConfig daoConfigM778clone9 = daoConfigMap.get(QuestionDataStatisticsBeanDao.class).m778clone();
        this.questionDataStatisticsBeanDaoConfig = daoConfigM778clone9;
        daoConfigM778clone9.initIdentityScope(type);
        DaoConfig daoConfigM778clone10 = daoConfigMap.get(CircleCategoryBeanDao.class).m778clone();
        this.circleCategoryBeanDaoConfig = daoConfigM778clone10;
        daoConfigM778clone10.initIdentityScope(type);
        DaoConfig daoConfigM778clone11 = daoConfigMap.get(CircleCategoryLableBeanDao.class).m778clone();
        this.circleCategoryLableBeanDaoConfig = daoConfigM778clone11;
        daoConfigM778clone11.initIdentityScope(type);
        DaoConfig daoConfigM778clone12 = daoConfigMap.get(CircleCategoryCollectBeanDao.class).m778clone();
        this.circleCategoryCollectBeanDaoConfig = daoConfigM778clone12;
        daoConfigM778clone12.initIdentityScope(type);
        DaoConfig daoConfigM778clone13 = daoConfigMap.get(CircleSchoolBeanDao.class).m778clone();
        this.circleSchoolBeanDaoConfig = daoConfigM778clone13;
        daoConfigM778clone13.initIdentityScope(type);
        DaoConfig daoConfigM778clone14 = daoConfigMap.get(CircleTopicHistoryBeanDao.class).m778clone();
        this.circleTopicHistoryBeanDaoConfig = daoConfigM778clone14;
        daoConfigM778clone14.initIdentityScope(type);
        DaoConfig daoConfigM778clone15 = daoConfigMap.get(QuestionCacheVideoBeanDao.class).m778clone();
        this.questionCacheVideoBeanDaoConfig = daoConfigM778clone15;
        daoConfigM778clone15.initIdentityScope(type);
        DaoConfig daoConfigM778clone16 = daoConfigMap.get(QuestionVideoLocationBeanDao.class).m778clone();
        this.questionVideoLocationBeanDaoConfig = daoConfigM778clone16;
        daoConfigM778clone16.initIdentityScope(type);
        DaoConfig daoConfigM778clone17 = daoConfigMap.get(CourseVideoLocationBeanDao.class).m778clone();
        this.courseVideoLocationBeanDaoConfig = daoConfigM778clone17;
        daoConfigM778clone17.initIdentityScope(type);
        DaoConfig daoConfigM778clone18 = daoConfigMap.get(AnsweredQuestionBeanBeiDao.class).m778clone();
        this.answeredQuestionBeanBeiDaoConfig = daoConfigM778clone18;
        daoConfigM778clone18.initIdentityScope(type);
        DaoConfig daoConfigM778clone19 = daoConfigMap.get(WrongBeanBeiDao.class).m778clone();
        this.wrongBeanBeiDaoConfig = daoConfigM778clone19;
        daoConfigM778clone19.initIdentityScope(type);
        DaoConfig daoConfigM778clone20 = daoConfigMap.get(FavoritesBeanBeiDao.class).m778clone();
        this.favoritesBeanBeiDaoConfig = daoConfigM778clone20;
        daoConfigM778clone20.initIdentityScope(type);
        DaoConfig daoConfigM778clone21 = daoConfigMap.get(NotesBeanBeiDao.class).m778clone();
        this.notesBeanBeiDaoConfig = daoConfigM778clone21;
        daoConfigM778clone21.initIdentityScope(type);
        DaoConfig daoConfigM778clone22 = daoConfigMap.get(SubmitAnsweredQuestionBeanBeiDao.class).m778clone();
        this.submitAnsweredQuestionBeanBeiDaoConfig = daoConfigM778clone22;
        daoConfigM778clone22.initIdentityScope(type);
        DaoConfig daoConfigM778clone23 = daoConfigMap.get(SubmitFavoritesBeanBeiDao.class).m778clone();
        this.submitFavoritesBeanBeiDaoConfig = daoConfigM778clone23;
        daoConfigM778clone23.initIdentityScope(type);
        DaoConfig daoConfigM778clone24 = daoConfigMap.get(SubmitNotesBeanBeiDao.class).m778clone();
        this.submitNotesBeanBeiDaoConfig = daoConfigM778clone24;
        daoConfigM778clone24.initIdentityScope(type);
        WrongBeanBeiDao wrongBeanBeiDao = new WrongBeanBeiDao(daoConfigM778clone19, this);
        this.wrongBeanBeiDao = wrongBeanBeiDao;
        FavoritesBeanBeiDao favoritesBeanBeiDao = new FavoritesBeanBeiDao(daoConfigM778clone20, this);
        this.favoritesBeanBeiDao = favoritesBeanBeiDao;
        NotesBeanBeiDao notesBeanBeiDao = new NotesBeanBeiDao(daoConfigM778clone21, this);
        this.notesBeanBeiDao = notesBeanBeiDao;
        SubmitAnsweredQuestionBeanBeiDao submitAnsweredQuestionBeanBeiDao = new SubmitAnsweredQuestionBeanBeiDao(daoConfigM778clone22, this);
        this.submitAnsweredQuestionBeanBeiDao = submitAnsweredQuestionBeanBeiDao;
        SubmitFavoritesBeanBeiDao submitFavoritesBeanBeiDao = new SubmitFavoritesBeanBeiDao(daoConfigM778clone23, this);
        this.submitFavoritesBeanBeiDao = submitFavoritesBeanBeiDao;
        SubmitNotesBeanBeiDao submitNotesBeanBeiDao = new SubmitNotesBeanBeiDao(daoConfigM778clone24, this);
        this.submitNotesBeanBeiDao = submitNotesBeanBeiDao;
        AnsweredQuestionBeanBeiDao answeredQuestionBeanBeiDao = new AnsweredQuestionBeanBeiDao(daoConfigM778clone18, this);
        this.answeredQuestionBeanBeiDao = answeredQuestionBeanBeiDao;
        AnsweredQuestionBeanDao answeredQuestionBeanDao = new AnsweredQuestionBeanDao(daoConfigM778clone, this);
        this.answeredQuestionBeanDao = answeredQuestionBeanDao;
        SubmitAnsweredQuestionBeanDao submitAnsweredQuestionBeanDao = new SubmitAnsweredQuestionBeanDao(daoConfigM778clone2, this);
        this.submitAnsweredQuestionBeanDao = submitAnsweredQuestionBeanDao;
        FavoritesBeanDao favoritesBeanDao = new FavoritesBeanDao(daoConfigM778clone3, this);
        this.favoritesBeanDao = favoritesBeanDao;
        SubmitFavoritesBeanDao submitFavoritesBeanDao = new SubmitFavoritesBeanDao(daoConfigM778clone4, this);
        this.submitFavoritesBeanDao = submitFavoritesBeanDao;
        WrongBeanDao wrongBeanDao = new WrongBeanDao(daoConfigM778clone5, this);
        this.wrongBeanDao = wrongBeanDao;
        NotesBeanDao notesBeanDao = new NotesBeanDao(daoConfigM778clone6, this);
        this.notesBeanDao = notesBeanDao;
        SubmitNotesBeanDao submitNotesBeanDao = new SubmitNotesBeanDao(daoConfigM778clone7, this);
        this.submitNotesBeanDao = submitNotesBeanDao;
        QuestionSelfStatisticsBeanDao questionSelfStatisticsBeanDao = new QuestionSelfStatisticsBeanDao(daoConfigM778clone8, this);
        this.questionSelfStatisticsBeanDao = questionSelfStatisticsBeanDao;
        QuestionDataStatisticsBeanDao questionDataStatisticsBeanDao = new QuestionDataStatisticsBeanDao(daoConfigM778clone9, this);
        this.questionDataStatisticsBeanDao = questionDataStatisticsBeanDao;
        CircleCategoryBeanDao circleCategoryBeanDao = new CircleCategoryBeanDao(daoConfigM778clone10, this);
        this.circleCategoryBeanDao = circleCategoryBeanDao;
        CircleCategoryLableBeanDao circleCategoryLableBeanDao = new CircleCategoryLableBeanDao(daoConfigM778clone11, this);
        this.circleCategoryLableBeanDao = circleCategoryLableBeanDao;
        CircleCategoryCollectBeanDao circleCategoryCollectBeanDao = new CircleCategoryCollectBeanDao(daoConfigM778clone12, this);
        this.circleCategoryCollectBeanDao = circleCategoryCollectBeanDao;
        CircleSchoolBeanDao circleSchoolBeanDao = new CircleSchoolBeanDao(daoConfigM778clone13, this);
        this.circleSchoolBeanDao = circleSchoolBeanDao;
        CircleTopicHistoryBeanDao circleTopicHistoryBeanDao = new CircleTopicHistoryBeanDao(daoConfigM778clone14, this);
        this.circleTopicHistoryBeanDao = circleTopicHistoryBeanDao;
        QuestionCacheVideoBeanDao questionCacheVideoBeanDao = new QuestionCacheVideoBeanDao(daoConfigM778clone15, this);
        this.questionCacheVideoBeanDao = questionCacheVideoBeanDao;
        QuestionVideoLocationBeanDao questionVideoLocationBeanDao = new QuestionVideoLocationBeanDao(daoConfigM778clone16, this);
        this.questionVideoLocationBeanDao = questionVideoLocationBeanDao;
        CourseVideoLocationBeanDao courseVideoLocationBeanDao = new CourseVideoLocationBeanDao(daoConfigM778clone17, this);
        this.courseVideoLocationBeanDao = courseVideoLocationBeanDao;
        registerDao(AnsweredQuestionBean.class, answeredQuestionBeanDao);
        registerDao(SubmitAnsweredQuestionBean.class, submitAnsweredQuestionBeanDao);
        registerDao(FavoritesBean.class, favoritesBeanDao);
        registerDao(SubmitFavoritesBean.class, submitFavoritesBeanDao);
        registerDao(WrongBean.class, wrongBeanDao);
        registerDao(NotesBean.class, notesBeanDao);
        registerDao(SubmitNotesBean.class, submitNotesBeanDao);
        registerDao(QuestionSelfStatisticsBean.class, questionSelfStatisticsBeanDao);
        registerDao(QuestionDataStatisticsBean.class, questionDataStatisticsBeanDao);
        registerDao(CircleCategoryBean.class, circleCategoryBeanDao);
        registerDao(CircleCategoryLableBean.class, circleCategoryLableBeanDao);
        registerDao(CircleCategoryCollectBean.class, circleCategoryCollectBeanDao);
        registerDao(CircleSchoolBean.class, circleSchoolBeanDao);
        registerDao(CircleTopicHistoryBean.class, circleTopicHistoryBeanDao);
        registerDao(QuestionCacheVideoBean.class, questionCacheVideoBeanDao);
        registerDao(QuestionVideoLocationBean.class, questionVideoLocationBeanDao);
        registerDao(CourseVideoLocationBean.class, courseVideoLocationBeanDao);
        registerDao(WrongBeanBei.class, wrongBeanBeiDao);
        registerDao(NotesBeanBei.class, notesBeanBeiDao);
        registerDao(FavoritesBeanBei.class, favoritesBeanBeiDao);
        registerDao(SubmitAnsweredQuestionBeanBei.class, submitAnsweredQuestionBeanBeiDao);
        registerDao(SubmitNotesBeanBei.class, submitNotesBeanBeiDao);
        registerDao(SubmitFavoritesBeanBei.class, submitFavoritesBeanBeiDao);
        registerDao(AnsweredQuestionBeanBei.class, answeredQuestionBeanBeiDao);
    }

    public void clear() {
        this.answeredQuestionBeanDaoConfig.getIdentityScope().clear();
        this.submitAnsweredQuestionBeanDaoConfig.getIdentityScope().clear();
        this.favoritesBeanDaoConfig.getIdentityScope().clear();
        this.submitFavoritesBeanDaoConfig.getIdentityScope().clear();
        this.wrongBeanDaoConfig.getIdentityScope().clear();
        this.notesBeanDaoConfig.getIdentityScope().clear();
        this.submitNotesBeanDaoConfig.getIdentityScope().clear();
        this.questionSelfStatisticsBeanDaoConfig.getIdentityScope().clear();
        this.questionDataStatisticsBeanDaoConfig.getIdentityScope().clear();
        this.circleCategoryBeanDaoConfig.getIdentityScope().clear();
        this.circleCategoryLableBeanDaoConfig.getIdentityScope().clear();
        this.circleCategoryCollectBeanDaoConfig.getIdentityScope().clear();
        this.circleSchoolBeanDaoConfig.getIdentityScope().clear();
        this.circleTopicHistoryBeanDaoConfig.getIdentityScope().clear();
        this.questionCacheVideoBeanDaoConfig.getIdentityScope().clear();
        this.questionVideoLocationBeanDaoConfig.getIdentityScope().clear();
        this.courseVideoLocationBeanDaoConfig.getIdentityScope().clear();
        this.wrongBeanBeiDaoConfig.getIdentityScope().clear();
        this.favoritesBeanBeiDaoConfig.getIdentityScope().clear();
        this.notesBeanBeiDaoConfig.getIdentityScope().clear();
        this.submitAnsweredQuestionBeanBeiDaoConfig.getIdentityScope().clear();
        this.submitFavoritesBeanBeiDaoConfig.getIdentityScope().clear();
        this.submitNotesBeanBeiDaoConfig.getIdentityScope().clear();
        this.answeredQuestionBeanBeiDaoConfig.getIdentityScope().clear();
    }

    public AnsweredQuestionBeanBeiDao getAnsweredQuestionBeanBeiDao() {
        return this.answeredQuestionBeanBeiDao;
    }

    public AnsweredQuestionBeanDao getAnsweredQuestionBeanDao() {
        return this.answeredQuestionBeanDao;
    }

    public CircleCategoryBeanDao getCircleCategoryBeanDao() {
        return this.circleCategoryBeanDao;
    }

    public CircleCategoryCollectBeanDao getCircleCategoryCollectBeanDao() {
        return this.circleCategoryCollectBeanDao;
    }

    public CircleCategoryLableBeanDao getCircleCategoryLableBeanDao() {
        return this.circleCategoryLableBeanDao;
    }

    public CircleSchoolBeanDao getCircleSchoolBeanDao() {
        return this.circleSchoolBeanDao;
    }

    public CircleTopicHistoryBeanDao getCircleTopicHistoryBeanDao() {
        return this.circleTopicHistoryBeanDao;
    }

    public CourseVideoLocationBeanDao getCourseVideoLocationBeanDao() {
        return this.courseVideoLocationBeanDao;
    }

    public FavoritesBeanBeiDao getFavoritesBeanBeiDao() {
        return this.favoritesBeanBeiDao;
    }

    public FavoritesBeanDao getFavoritesBeanDao() {
        return this.favoritesBeanDao;
    }

    public NotesBeanBeiDao getNotesBeanBeiDao() {
        return this.notesBeanBeiDao;
    }

    public NotesBeanDao getNotesBeanDao() {
        return this.notesBeanDao;
    }

    public QuestionCacheVideoBeanDao getQuestionCacheVideoBeanDao() {
        return this.questionCacheVideoBeanDao;
    }

    public QuestionDataStatisticsBeanDao getQuestionDataStatisticsBeanDao() {
        return this.questionDataStatisticsBeanDao;
    }

    public QuestionSelfStatisticsBeanDao getQuestionSelfStatisticsBeanDao() {
        return this.questionSelfStatisticsBeanDao;
    }

    public QuestionVideoLocationBeanDao getQuestionVideoLocationBeanDao() {
        return this.questionVideoLocationBeanDao;
    }

    public SubmitAnsweredQuestionBeanBeiDao getSubmitAnsweredQuestionBeanBeiDao() {
        return this.submitAnsweredQuestionBeanBeiDao;
    }

    public SubmitAnsweredQuestionBeanDao getSubmitAnsweredQuestionBeanDao() {
        return this.submitAnsweredQuestionBeanDao;
    }

    public SubmitFavoritesBeanBeiDao getSubmitFavoritesBeanBeiDao() {
        return this.submitFavoritesBeanBeiDao;
    }

    public SubmitFavoritesBeanDao getSubmitFavoritesBeanDao() {
        return this.submitFavoritesBeanDao;
    }

    public SubmitNotesBeanBeiDao getSubmitNotesBeanBeiDao() {
        return this.submitNotesBeanBeiDao;
    }

    public SubmitNotesBeanDao getSubmitNotesBeanDao() {
        return this.submitNotesBeanDao;
    }

    public WrongBeanBeiDao getWrongBeanBeiDao() {
        return this.wrongBeanBeiDao;
    }

    public WrongBeanDao getWrongBeanDao() {
        return this.wrongBeanDao;
    }
}
