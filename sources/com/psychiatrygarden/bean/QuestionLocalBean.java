package com.psychiatrygarden.bean;

import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.tencent.connect.common.Constants;
import com.tencent.open.SocialConstants;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class QuestionLocalBean {
    private static volatile QuestionLocalBean qlocalbean;
    public List<QLocalBean> qLocalBeans = new ArrayList();

    public static class QLocalBean {
        public List<QCLocalBean> qcLocalBeanList;
        public String text;
        public String type;

        public static class QCLocalBean {
            private int isSelected = 0;
            public String source;
            public String text;
            public String type;

            public int getIsSelected() {
                return this.isSelected;
            }

            public String getSource() {
                return this.source;
            }

            public String getText() {
                return this.text;
            }

            public String getType() {
                return this.type;
            }

            public QCLocalBean setIsSelected(int isSelected) {
                this.isSelected = isSelected;
                return this;
            }

            public QCLocalBean setSource(String source) {
                this.source = source;
                return this;
            }

            public QCLocalBean setText(String text) {
                this.text = text;
                return this;
            }

            public QCLocalBean setType(String type) {
                this.type = type;
                return this;
            }
        }

        public List<QCLocalBean> getQcLocalBeanList() {
            return this.qcLocalBeanList;
        }

        public String getText() {
            return this.text;
        }

        public String getType() {
            return this.type;
        }

        public QLocalBean setQcLocalBeanList(List<QCLocalBean> qcLocalBeanList) {
            this.qcLocalBeanList = qcLocalBeanList;
            return this;
        }

        public QLocalBean setText(String text) {
            this.text = text;
            return this;
        }

        public QLocalBean setType(String type) {
            this.type = type;
            return this;
        }
    }

    public static QuestionLocalBean getInstance() {
        if (qlocalbean == null) {
            synchronized (QuestionLocalBean.class) {
                if (qlocalbean == null) {
                    qlocalbean = new QuestionLocalBean();
                }
            }
        }
        return qlocalbean;
    }

    public void getModel() {
        QLocalBean qLocalBean = new QLocalBean();
        qLocalBean.setText("模式");
        qLocalBean.setType("model");
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < 3; i2++) {
            QLocalBean.QCLocalBean qCLocalBean = new QLocalBean.QCLocalBean();
            if (i2 == 0) {
                qCLocalBean.setText("练习模式");
                qCLocalBean.setType("0");
            } else if (i2 == 1) {
                qCLocalBean.setText("测试模式");
                qCLocalBean.setType("1");
            } else if (i2 == 2) {
                qCLocalBean.setText("背题模式");
                qCLocalBean.setType("2");
            }
            arrayList.add(qCLocalBean);
        }
        qLocalBean.setQcLocalBeanList(arrayList);
        this.qLocalBeans.add(qLocalBean);
    }

    public void getQuestionData() {
        QLocalBean qLocalBean = new QLocalBean();
        qLocalBean.setText("题型");
        qLocalBean.setType("question");
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        if (SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "").equals("40")) {
            while (i2 < 3) {
                QLocalBean.QCLocalBean qCLocalBean = new QLocalBean.QCLocalBean();
                if (i2 == 0) {
                    qCLocalBean.setText("全部");
                    qCLocalBean.setType("-1");
                } else if (i2 == 1) {
                    qCLocalBean.setText("选择题");
                    qCLocalBean.setType("2");
                } else if (i2 == 2) {
                    qCLocalBean.setText("非选择题");
                    qCLocalBean.setType("3");
                }
                arrayList.add(qCLocalBean);
                i2++;
            }
        } else {
            while (i2 < 3) {
                QLocalBean.QCLocalBean qCLocalBean2 = new QLocalBean.QCLocalBean();
                if (i2 == 0) {
                    qCLocalBean2.setText("全部");
                    qCLocalBean2.setType("-1");
                } else if (i2 == 1) {
                    qCLocalBean2.setText("单选题");
                    qCLocalBean2.setType("0");
                } else if (i2 == 2) {
                    qCLocalBean2.setText("多选题");
                    qCLocalBean2.setType("1");
                }
                arrayList.add(qCLocalBean2);
                i2++;
            }
        }
        qLocalBean.setQcLocalBeanList(arrayList);
        this.qLocalBeans.add(qLocalBean);
    }

    public void getSource() {
        if (SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "").equals("40")) {
            QLocalBean qLocalBean = new QLocalBean();
            qLocalBean.setText("来源");
            qLocalBean.setType(SocialConstants.PARAM_SOURCE);
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < 5; i2++) {
                QLocalBean.QCLocalBean qCLocalBean = new QLocalBean.QCLocalBean();
                if (i2 == 0) {
                    qCLocalBean.setText("全部");
                    qCLocalBean.setType("-1");
                } else if (i2 == 1) {
                    qCLocalBean.setText("考研真题");
                    qCLocalBean.setType("0");
                } else if (i2 == 2) {
                    qCLocalBean.setText("复试真题");
                    qCLocalBean.setType("1");
                } else if (i2 == 3) {
                    qCLocalBean.setText("名校题库");
                    qCLocalBean.setType("2");
                } else if (i2 == 4) {
                    qCLocalBean.setText("执医真题");
                    qCLocalBean.setType("3");
                }
                arrayList.add(qCLocalBean);
            }
            qLocalBean.setQcLocalBeanList(arrayList);
            this.qLocalBeans.add(qLocalBean);
        }
    }

    public void getYearData() {
        QLocalBean qLocalBean = new QLocalBean();
        qLocalBean.setText("年份");
        qLocalBean.setType("year");
        ArrayList arrayList = new ArrayList();
        int i2 = 1;
        if (SharePreferencesUtils.readStrConfig(CommonParameter.Student_Type, ProjectApp.instance(), "").equals("y")) {
            int i3 = 0;
            while (i3 < 9) {
                QLocalBean.QCLocalBean qCLocalBean = new QLocalBean.QCLocalBean();
                if (i3 == 0) {
                    qCLocalBean.setText("全部");
                    qCLocalBean.setType("-1");
                } else if (i3 == i2) {
                    qCLocalBean.setText("近25年");
                    qCLocalBean.setType(Constants.VIA_SHARE_TYPE_INFO);
                } else if (i3 == 2) {
                    qCLocalBean.setText("近20年");
                    qCLocalBean.setType("4");
                } else if (i3 == 3) {
                    qCLocalBean.setText("近15年");
                    qCLocalBean.setType("3");
                } else if (i3 == 4) {
                    qCLocalBean.setText("近10年");
                    qCLocalBean.setType("2");
                } else if (i3 == 5) {
                    qCLocalBean.setText("近5年");
                    qCLocalBean.setType("1");
                } else if (i3 == 6) {
                    qCLocalBean.setText("排除近5年");
                    qCLocalBean.setType("0");
                } else if (i3 == 7) {
                    qCLocalBean.setText("排除近3年");
                    qCLocalBean.setType("7");
                } else if (i3 == 8) {
                    qCLocalBean.setText("排除近1年");
                    qCLocalBean.setType(Constants.VIA_SHARE_TYPE_PUBLISHVIDEO);
                }
                arrayList.add(qCLocalBean);
                i3++;
                i2 = 1;
            }
        } else {
            for (int i4 = 0; i4 < 6; i4++) {
                QLocalBean.QCLocalBean qCLocalBean2 = new QLocalBean.QCLocalBean();
                if (i4 == 0) {
                    qCLocalBean2.setText("全部");
                    qCLocalBean2.setType("-1");
                } else if (i4 == 1) {
                    qCLocalBean2.setText("近15年");
                    qCLocalBean2.setType("3");
                } else if (i4 == 2) {
                    qCLocalBean2.setText("近10年");
                    qCLocalBean2.setType("2");
                } else if (i4 == 3) {
                    qCLocalBean2.setText("近5年");
                    qCLocalBean2.setType("1");
                } else if (i4 == 4) {
                    qCLocalBean2.setText("近3年");
                    qCLocalBean2.setType(Constants.VIA_SHARE_TYPE_MINI_PROGRAM);
                } else {
                    if (i4 == 5) {
                        qCLocalBean2.setText("排除近3年");
                        qCLocalBean2.setType("7");
                    }
                    arrayList.add(qCLocalBean2);
                }
                arrayList.add(qCLocalBean2);
            }
        }
        qLocalBean.setQcLocalBeanList(arrayList);
        this.qLocalBeans.add(qLocalBean);
    }

    public List<QLocalBean> initLocalData(int noyear, boolean ISPractice) {
        this.qLocalBeans = new ArrayList();
        if (noyear == 1 && !SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "").equals("40")) {
            getYearData();
        }
        if (!SharePreferencesUtils.readStrConfig(CommonParameter.Student_Type, ProjectApp.instance(), "").equals("z")) {
            getQuestionData();
        }
        if (!ISPractice) {
            getModel();
        }
        return this.qLocalBeans;
    }

    public void modelType() {
        if (SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "").equals("40")) {
            return;
        }
        QLocalBean qLocalBean = new QLocalBean();
        qLocalBean.setText("类型");
        qLocalBean.setType("modeltype");
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < 4; i2++) {
            QLocalBean.QCLocalBean qCLocalBean = new QLocalBean.QCLocalBean();
            if (i2 == 0) {
                qCLocalBean.setText("全部");
                qCLocalBean.setType("-1");
            } else if (i2 == 1) {
                qCLocalBean.setText("重点题");
                qCLocalBean.setType("0");
            } else if (i2 == 2) {
                qCLocalBean.setText("难点题");
                qCLocalBean.setType("1");
            } else if (i2 == 3) {
                qCLocalBean.setText("病例题");
                qCLocalBean.setType("2");
            }
            arrayList.add(qCLocalBean);
        }
        qLocalBean.setQcLocalBeanList(arrayList);
        this.qLocalBeans.add(qLocalBean);
    }
}
