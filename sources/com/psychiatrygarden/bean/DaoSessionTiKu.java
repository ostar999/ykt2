package com.psychiatrygarden.bean;

import android.database.sqlite.SQLiteDatabase;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;
import java.util.Map;

/* loaded from: classes5.dex */
public class DaoSessionTiKu extends AbstractDaoSession {
    private final QuestionCourseVideoBeanDao questionCourseVideoBeanDao;
    private final DaoConfig questionCourseVideoBeanDaoConfig;
    private final QuestionInfoBeanDao questionInfoBeanDao;
    private final DaoConfig questionInfoBeanDaoConfig;
    private final QuestionOptionBeanDao questionOptionBeanDao;
    private final DaoConfig questionOptionBeanDaoConfig;
    private final SectionBeanDao sectionBeanDao;
    private final DaoConfig sectionBeanDaoConfig;
    private final SectionPartBeanDao sectionPartBeanDao;
    private final DaoConfig sectionPartBeanDaoConfig;

    public DaoSessionTiKu(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> daoConfigMap) {
        super(db);
        DaoConfig daoConfigM778clone = daoConfigMap.get(QuestionInfoBeanDao.class).m778clone();
        this.questionInfoBeanDaoConfig = daoConfigM778clone;
        daoConfigM778clone.initIdentityScope(type);
        DaoConfig daoConfigM778clone2 = daoConfigMap.get(QuestionOptionBeanDao.class).m778clone();
        this.questionOptionBeanDaoConfig = daoConfigM778clone2;
        daoConfigM778clone2.initIdentityScope(type);
        DaoConfig daoConfigM778clone3 = daoConfigMap.get(SectionBeanDao.class).m778clone();
        this.sectionBeanDaoConfig = daoConfigM778clone3;
        daoConfigM778clone3.initIdentityScope(type);
        DaoConfig daoConfigM778clone4 = daoConfigMap.get(QuestionCourseVideoBeanDao.class).m778clone();
        this.questionCourseVideoBeanDaoConfig = daoConfigM778clone4;
        daoConfigM778clone4.initIdentityScope(type);
        DaoConfig daoConfigM778clone5 = daoConfigMap.get(SectionPartBeanDao.class).m778clone();
        this.sectionPartBeanDaoConfig = daoConfigM778clone5;
        daoConfigM778clone5.initIdentityScope(type);
        QuestionInfoBeanDao questionInfoBeanDao = new QuestionInfoBeanDao(daoConfigM778clone, this);
        this.questionInfoBeanDao = questionInfoBeanDao;
        QuestionOptionBeanDao questionOptionBeanDao = new QuestionOptionBeanDao(daoConfigM778clone2, this);
        this.questionOptionBeanDao = questionOptionBeanDao;
        SectionBeanDao sectionBeanDao = new SectionBeanDao(daoConfigM778clone3, this);
        this.sectionBeanDao = sectionBeanDao;
        QuestionCourseVideoBeanDao questionCourseVideoBeanDao = new QuestionCourseVideoBeanDao(daoConfigM778clone4, this);
        this.questionCourseVideoBeanDao = questionCourseVideoBeanDao;
        SectionPartBeanDao sectionPartBeanDao = new SectionPartBeanDao(daoConfigM778clone5, this);
        this.sectionPartBeanDao = sectionPartBeanDao;
        registerDao(QuestionInfoBean.class, questionInfoBeanDao);
        registerDao(QuestionOptionBean.class, questionOptionBeanDao);
        registerDao(SectionBean.class, sectionBeanDao);
        registerDao(QuestionCourseVideoBean.class, questionCourseVideoBeanDao);
        registerDao(SectionPartBean.class, sectionPartBeanDao);
    }

    public void clear() {
        this.questionInfoBeanDaoConfig.getIdentityScope().clear();
        this.questionOptionBeanDaoConfig.getIdentityScope().clear();
        this.sectionBeanDaoConfig.getIdentityScope().clear();
        this.questionCourseVideoBeanDaoConfig.getIdentityScope().clear();
        this.sectionPartBeanDaoConfig.getIdentityScope().clear();
    }

    public QuestionCourseVideoBeanDao getQuestionCourseVideoBeanDao() {
        return this.questionCourseVideoBeanDao;
    }

    public QuestionInfoBeanDao getQuestionInfoBeanDao() {
        return this.questionInfoBeanDao;
    }

    public QuestionOptionBeanDao getQuestionOptionBeanDao() {
        return this.questionOptionBeanDao;
    }

    public SectionBeanDao getSectionBeanDao() {
        return this.sectionBeanDao;
    }

    public SectionPartBeanDao getSectionPartBeanDao() {
        return this.sectionPartBeanDao;
    }
}
