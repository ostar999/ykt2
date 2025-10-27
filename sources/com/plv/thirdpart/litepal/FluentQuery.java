package com.plv.thirdpart.litepal;

import android.text.TextUtils;
import com.plv.thirdpart.litepal.crud.LitePalSupport;
import com.plv.thirdpart.litepal.crud.QueryHandler;
import com.plv.thirdpart.litepal.crud.async.AverageExecutor;
import com.plv.thirdpart.litepal.crud.async.CountExecutor;
import com.plv.thirdpart.litepal.crud.async.FindExecutor;
import com.plv.thirdpart.litepal.crud.async.FindMultiExecutor;
import com.plv.thirdpart.litepal.exceptions.LitePalSupportException;
import com.plv.thirdpart.litepal.tablemanager.Connector;
import com.plv.thirdpart.litepal.util.BaseUtility;
import com.plv.thirdpart.litepal.util.DBUtility;
import java.util.List;

/* loaded from: classes5.dex */
public class FluentQuery {
    String[] mColumns;
    String[] mConditions;
    String mLimit;
    String mOffset;
    String mOrderBy;

    public double average(Class<?> cls, String str) {
        return average(BaseUtility.changeCase(cls.getSimpleName()), str);
    }

    @Deprecated
    public AverageExecutor averageAsync(Class<?> cls, String str) {
        return averageAsync(BaseUtility.changeCase(DBUtility.getTableNameByClassName(cls.getName())), str);
    }

    public int count(Class<?> cls) {
        return count(BaseUtility.changeCase(cls.getSimpleName()));
    }

    @Deprecated
    public CountExecutor countAsync(Class<?> cls) {
        return countAsync(BaseUtility.changeCase(DBUtility.getTableNameByClassName(cls.getName())));
    }

    public <T> List<T> find(Class<T> cls) {
        return find(cls, false);
    }

    @Deprecated
    public <T> FindMultiExecutor<T> findAsync(Class<T> cls) {
        return findAsync(cls, false);
    }

    public <T> T findFirst(Class<T> cls) {
        return (T) findFirst(cls, false);
    }

    @Deprecated
    public <T> FindExecutor<T> findFirstAsync(Class<T> cls) {
        return findFirstAsync(cls, false);
    }

    public <T> T findLast(Class<T> cls) {
        return (T) findLast(cls, false);
    }

    @Deprecated
    public <T> FindExecutor<T> findLastAsync(Class<T> cls) {
        return findLastAsync(cls, false);
    }

    public FluentQuery limit(int i2) {
        this.mLimit = String.valueOf(i2);
        return this;
    }

    public <T> T max(Class<?> cls, String str, Class<T> cls2) {
        return (T) max(BaseUtility.changeCase(cls.getSimpleName()), str, cls2);
    }

    @Deprecated
    public <T> FindExecutor<T> maxAsync(Class<?> cls, String str, Class<T> cls2) {
        return maxAsync(BaseUtility.changeCase(DBUtility.getTableNameByClassName(cls.getName())), str, cls2);
    }

    public <T> T min(Class<?> cls, String str, Class<T> cls2) {
        return (T) min(BaseUtility.changeCase(cls.getSimpleName()), str, cls2);
    }

    @Deprecated
    public <T> FindExecutor<T> minAsync(Class<?> cls, String str, Class<T> cls2) {
        return minAsync(BaseUtility.changeCase(DBUtility.getTableNameByClassName(cls.getName())), str, cls2);
    }

    public FluentQuery offset(int i2) {
        this.mOffset = String.valueOf(i2);
        return this;
    }

    public FluentQuery order(String str) {
        this.mOrderBy = str;
        return this;
    }

    public FluentQuery select(String... strArr) {
        this.mColumns = strArr;
        return this;
    }

    public <T> T sum(Class<?> cls, String str, Class<T> cls2) {
        return (T) sum(BaseUtility.changeCase(cls.getSimpleName()), str, cls2);
    }

    @Deprecated
    public <T> FindExecutor<T> sumAsync(Class<?> cls, String str, Class<T> cls2) {
        return sumAsync(BaseUtility.changeCase(DBUtility.getTableNameByClassName(cls.getName())), str, cls2);
    }

    public FluentQuery where(String... strArr) {
        this.mConditions = strArr;
        return this;
    }

    public double average(String str, String str2) {
        double dOnAverage;
        synchronized (LitePalSupport.class) {
            dOnAverage = new QueryHandler(Connector.getDatabase()).onAverage(str, str2, this.mConditions);
        }
        return dOnAverage;
    }

    @Deprecated
    public AverageExecutor averageAsync(final String str, final String str2) {
        final AverageExecutor averageExecutor = new AverageExecutor();
        averageExecutor.submit(new Runnable() { // from class: com.plv.thirdpart.litepal.FluentQuery.5
            @Override // java.lang.Runnable
            public void run() {
                synchronized (LitePalSupport.class) {
                    final double dAverage = FluentQuery.this.average(str, str2);
                    if (averageExecutor.getListener() != null) {
                        Operator.getHandler().post(new Runnable() { // from class: com.plv.thirdpart.litepal.FluentQuery.5.1
                            @Override // java.lang.Runnable
                            public void run() {
                                averageExecutor.getListener().onFinish(dAverage);
                            }
                        });
                    }
                }
            }
        });
        return averageExecutor;
    }

    public int count(String str) {
        int iOnCount;
        synchronized (LitePalSupport.class) {
            iOnCount = new QueryHandler(Connector.getDatabase()).onCount(str, this.mConditions);
        }
        return iOnCount;
    }

    @Deprecated
    public CountExecutor countAsync(final String str) {
        final CountExecutor countExecutor = new CountExecutor();
        countExecutor.submit(new Runnable() { // from class: com.plv.thirdpart.litepal.FluentQuery.4
            @Override // java.lang.Runnable
            public void run() {
                synchronized (LitePalSupport.class) {
                    final int iCount = FluentQuery.this.count(str);
                    if (countExecutor.getListener() != null) {
                        Operator.getHandler().post(new Runnable() { // from class: com.plv.thirdpart.litepal.FluentQuery.4.1
                            @Override // java.lang.Runnable
                            public void run() {
                                countExecutor.getListener().onFinish(iCount);
                            }
                        });
                    }
                }
            }
        });
        return countExecutor;
    }

    public <T> List<T> find(Class<T> cls, boolean z2) {
        String str;
        List<T> listOnFind;
        synchronized (LitePalSupport.class) {
            QueryHandler queryHandler = new QueryHandler(Connector.getDatabase());
            if (this.mOffset == null) {
                str = this.mLimit;
            } else {
                if (this.mLimit == null) {
                    this.mLimit = "0";
                }
                str = this.mOffset + "," + this.mLimit;
            }
            listOnFind = queryHandler.onFind(cls, this.mColumns, this.mConditions, this.mOrderBy, str, z2);
        }
        return listOnFind;
    }

    @Deprecated
    public <T> FindMultiExecutor<T> findAsync(final Class<T> cls, final boolean z2) {
        final FindMultiExecutor<T> findMultiExecutor = new FindMultiExecutor<>();
        findMultiExecutor.submit(new Runnable() { // from class: com.plv.thirdpart.litepal.FluentQuery.1
            @Override // java.lang.Runnable
            public void run() {
                synchronized (LitePalSupport.class) {
                    final List listFind = FluentQuery.this.find(cls, z2);
                    if (findMultiExecutor.getListener() != null) {
                        Operator.getHandler().post(new Runnable() { // from class: com.plv.thirdpart.litepal.FluentQuery.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                findMultiExecutor.getListener().onFinish(listFind);
                            }
                        });
                    }
                }
            }
        });
        return findMultiExecutor;
    }

    public <T> T findFirst(Class<T> cls, boolean z2) {
        synchronized (LitePalSupport.class) {
            String str = this.mLimit;
            if (!"0".equals(str)) {
                this.mLimit = "1";
            }
            List<T> listFind = find(cls, z2);
            this.mLimit = str;
            if (listFind.size() <= 0) {
                return null;
            }
            if (listFind.size() != 1) {
                throw new LitePalSupportException("Found multiple records while only one record should be found at most.");
            }
            return listFind.get(0);
        }
    }

    @Deprecated
    public <T> FindExecutor<T> findFirstAsync(final Class<T> cls, final boolean z2) {
        final FindExecutor<T> findExecutor = new FindExecutor<>();
        findExecutor.submit(new Runnable() { // from class: com.plv.thirdpart.litepal.FluentQuery.2
            @Override // java.lang.Runnable
            public void run() {
                synchronized (LitePalSupport.class) {
                    final Object objFindFirst = FluentQuery.this.findFirst(cls, z2);
                    if (findExecutor.getListener() != null) {
                        Operator.getHandler().post(new Runnable() { // from class: com.plv.thirdpart.litepal.FluentQuery.2.1
                            @Override // java.lang.Runnable
                            public void run() {
                                findExecutor.getListener().onFinish(objFindFirst);
                            }
                        });
                    }
                }
            }
        });
        return findExecutor;
    }

    public <T> T findLast(Class<T> cls, boolean z2) {
        synchronized (LitePalSupport.class) {
            String str = this.mOrderBy;
            String str2 = this.mLimit;
            if (TextUtils.isEmpty(this.mOffset) && TextUtils.isEmpty(this.mLimit)) {
                if (TextUtils.isEmpty(this.mOrderBy)) {
                    this.mOrderBy = "id desc";
                } else if (this.mOrderBy.endsWith(" desc")) {
                    this.mOrderBy = this.mOrderBy.replace(" desc", "");
                } else {
                    this.mOrderBy += " desc";
                }
                if (!"0".equals(this.mLimit)) {
                    this.mLimit = "1";
                }
            }
            List<T> listFind = find(cls, z2);
            this.mOrderBy = str;
            this.mLimit = str2;
            int size = listFind.size();
            if (size <= 0) {
                return null;
            }
            return listFind.get(size - 1);
        }
    }

    @Deprecated
    public <T> FindExecutor<T> findLastAsync(final Class<T> cls, final boolean z2) {
        final FindExecutor<T> findExecutor = new FindExecutor<>();
        findExecutor.submit(new Runnable() { // from class: com.plv.thirdpart.litepal.FluentQuery.3
            @Override // java.lang.Runnable
            public void run() {
                synchronized (LitePalSupport.class) {
                    final Object objFindLast = FluentQuery.this.findLast(cls, z2);
                    if (findExecutor.getListener() != null) {
                        Operator.getHandler().post(new Runnable() { // from class: com.plv.thirdpart.litepal.FluentQuery.3.1
                            @Override // java.lang.Runnable
                            public void run() {
                                findExecutor.getListener().onFinish(objFindLast);
                            }
                        });
                    }
                }
            }
        });
        return findExecutor;
    }

    public <T> T max(String str, String str2, Class<T> cls) {
        T t2;
        synchronized (LitePalSupport.class) {
            t2 = (T) new QueryHandler(Connector.getDatabase()).onMax(str, str2, this.mConditions, cls);
        }
        return t2;
    }

    @Deprecated
    public <T> FindExecutor<T> maxAsync(final String str, final String str2, final Class<T> cls) {
        final FindExecutor<T> findExecutor = new FindExecutor<>();
        findExecutor.submit(new Runnable() { // from class: com.plv.thirdpart.litepal.FluentQuery.6
            @Override // java.lang.Runnable
            public void run() {
                synchronized (LitePalSupport.class) {
                    final Object objMax = FluentQuery.this.max(str, str2, (Class<Object>) cls);
                    if (findExecutor.getListener() != null) {
                        Operator.getHandler().post(new Runnable() { // from class: com.plv.thirdpart.litepal.FluentQuery.6.1
                            @Override // java.lang.Runnable
                            public void run() {
                                findExecutor.getListener().onFinish(objMax);
                            }
                        });
                    }
                }
            }
        });
        return findExecutor;
    }

    public <T> T min(String str, String str2, Class<T> cls) {
        T t2;
        synchronized (LitePalSupport.class) {
            t2 = (T) new QueryHandler(Connector.getDatabase()).onMin(str, str2, this.mConditions, cls);
        }
        return t2;
    }

    @Deprecated
    public <T> FindExecutor<T> minAsync(final String str, final String str2, final Class<T> cls) {
        final FindExecutor<T> findExecutor = new FindExecutor<>();
        findExecutor.submit(new Runnable() { // from class: com.plv.thirdpart.litepal.FluentQuery.7
            @Override // java.lang.Runnable
            public void run() {
                synchronized (LitePalSupport.class) {
                    final Object objMin = FluentQuery.this.min(str, str2, (Class<Object>) cls);
                    if (findExecutor.getListener() != null) {
                        Operator.getHandler().post(new Runnable() { // from class: com.plv.thirdpart.litepal.FluentQuery.7.1
                            @Override // java.lang.Runnable
                            public void run() {
                                findExecutor.getListener().onFinish(objMin);
                            }
                        });
                    }
                }
            }
        });
        return findExecutor;
    }

    public <T> T sum(String str, String str2, Class<T> cls) {
        T t2;
        synchronized (LitePalSupport.class) {
            t2 = (T) new QueryHandler(Connector.getDatabase()).onSum(str, str2, this.mConditions, cls);
        }
        return t2;
    }

    @Deprecated
    public <T> FindExecutor<T> sumAsync(final String str, final String str2, final Class<T> cls) {
        final FindExecutor<T> findExecutor = new FindExecutor<>();
        findExecutor.submit(new Runnable() { // from class: com.plv.thirdpart.litepal.FluentQuery.8
            @Override // java.lang.Runnable
            public void run() {
                synchronized (LitePalSupport.class) {
                    final Object objSum = FluentQuery.this.sum(str, str2, (Class<Object>) cls);
                    if (findExecutor.getListener() != null) {
                        Operator.getHandler().post(new Runnable() { // from class: com.plv.thirdpart.litepal.FluentQuery.8.1
                            @Override // java.lang.Runnable
                            public void run() {
                                findExecutor.getListener().onFinish(objSum);
                            }
                        });
                    }
                }
            }
        });
        return findExecutor;
    }
}
