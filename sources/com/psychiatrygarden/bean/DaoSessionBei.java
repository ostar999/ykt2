package com.psychiatrygarden.bean;

import android.database.sqlite.SQLiteDatabase;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;
import java.util.Map;

/* loaded from: classes5.dex */
public class DaoSessionBei extends AbstractDaoSession {
    private final QuestionKuangBeiInfoBeanDao questionKuangBeiInfoBeanDao;
    private final DaoConfig questionKuangBeiInfoBeanDaoConfig;

    public DaoSessionBei(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> daoConfigMap) {
        super(db);
        DaoConfig daoConfigM778clone = daoConfigMap.get(QuestionKuangBeiInfoBeanDao.class).m778clone();
        this.questionKuangBeiInfoBeanDaoConfig = daoConfigM778clone;
        daoConfigM778clone.initIdentityScope(type);
        QuestionKuangBeiInfoBeanDao questionKuangBeiInfoBeanDao = new QuestionKuangBeiInfoBeanDao(daoConfigM778clone, this);
        this.questionKuangBeiInfoBeanDao = questionKuangBeiInfoBeanDao;
        registerDao(QuestionKuangBeiInfoBean.class, questionKuangBeiInfoBeanDao);
    }

    public void clear() {
        this.questionKuangBeiInfoBeanDaoConfig.getIdentityScope().clear();
    }

    public QuestionKuangBeiInfoBeanDao getQuestionKuangBeiInfoBeanDao() {
        return this.questionKuangBeiInfoBeanDao;
    }
}
