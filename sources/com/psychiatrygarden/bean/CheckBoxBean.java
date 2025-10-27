package com.psychiatrygarden.bean;

import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.tencent.connect.common.Constants;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class CheckBoxBean implements Serializable {
    private List<CheckBoxBean2> mCheckBoxBean2 = new ArrayList();
    private List<MarkDataBean> marks = new ArrayList();

    public class CheckBoxBean2 implements Serializable {
        private boolean App_ISTIKU;
        private String app_id;
        private String app_name;
        private String app_title;
        private String app_type;
        private int position;
        private String studen_type;
        private List<String> titleList = new ArrayList();
        private int show_count = 4;
        private int year_show = 4;

        public CheckBoxBean2() {
        }

        public String getApp_id() {
            return this.app_id;
        }

        public String getApp_name() {
            return this.app_name;
        }

        public String getApp_title() {
            return this.app_title;
        }

        public String getApp_type() {
            return this.app_type;
        }

        public int getPosition() {
            return this.position;
        }

        public int getShow_count() {
            return this.show_count;
        }

        public String getStuden_type() {
            return this.studen_type;
        }

        public List<String> getTitleList() {
            return this.titleList;
        }

        public int getYear_show() {
            return this.year_show;
        }

        public boolean isApp_ISTIKU() {
            return this.App_ISTIKU;
        }

        public void setApp_ISTIKU(boolean app_ISTIKU) {
            this.App_ISTIKU = app_ISTIKU;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public void setApp_name(String app_name) {
            this.app_name = app_name;
        }

        public void setApp_title(String app_title) {
            this.app_title = app_title;
        }

        public void setApp_type(String app_type) {
            this.app_type = app_type;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public void setShow_count(int show_count) {
            this.show_count = show_count;
        }

        public void setStuden_type(String studen_type) {
            this.studen_type = studen_type;
        }

        public void setTitleList(List<String> titleList) {
            this.titleList = titleList;
        }

        public void setYear_show(int year_show) {
            this.year_show = year_show;
        }
    }

    public class MarkDataBean implements Serializable {
        public String hideTitle;
        public String title;

        public MarkDataBean(String title, String hideTitle) {
            this.title = title;
            this.hideTitle = hideTitle;
        }

        public String getHideTitle() {
            return this.hideTitle;
        }

        public String getTitle() {
            return this.title;
        }

        public MarkDataBean setHideTitle(String hideTitle) {
            this.hideTitle = hideTitle;
            return this;
        }

        public MarkDataBean setTitle(String title) {
            this.title = title;
            return this;
        }
    }

    public CheckBoxBean() {
        initMarks();
    }

    private void initData(int type) {
        this.mCheckBoxBean2.clear();
        if (type == 1) {
            CheckBoxBean2 checkBoxBean2 = new CheckBoxBean2();
            checkBoxBean2.setApp_id("40");
            checkBoxBean2.setApp_name("本科题库（临床医学）");
            checkBoxBean2.setApp_type("Zhuanshuo");
            checkBoxBean2.setStuden_type("y");
            checkBoxBean2.setPosition(5);
            checkBoxBean2.setShow_count(3);
            checkBoxBean2.setApp_title("本科");
            checkBoxBean2.setYear_show(0);
            checkBoxBean2.setApp_ISTIKU(true);
            this.mCheckBoxBean2.add(checkBoxBean2);
        } else if (type == 2) {
            ArrayList arrayList = new ArrayList();
            arrayList.add("考点顺序");
            arrayList.add("考点乱序");
            arrayList.add("历年真题");
            arrayList.add("考点狂背");
            arrayList.add("同步练习");
            arrayList.add("模考估分");
            CheckBoxBean2 checkBoxBean22 = new CheckBoxBean2();
            checkBoxBean22.setApp_id(Constants.VIA_REPORT_TYPE_SHARE_TO_QQ);
            checkBoxBean22.setApp_name("专硕考研（临床医学）");
            checkBoxBean22.setApp_type("Zhuanshuo");
            checkBoxBean22.setStuden_type("y");
            checkBoxBean22.setPosition(0);
            checkBoxBean22.setShow_count(6);
            checkBoxBean22.setApp_title("专硕");
            checkBoxBean22.setTitleList(arrayList);
            checkBoxBean22.setYear_show(0);
            checkBoxBean22.setApp_ISTIKU(true);
            this.mCheckBoxBean2.add(checkBoxBean22);
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add("考点顺序");
            arrayList2.add("历年真题");
            CheckBoxBean2 checkBoxBean23 = new CheckBoxBean2();
            checkBoxBean23.setApp_id("20");
            checkBoxBean23.setApp_name("专硕考研（中医学）");
            checkBoxBean23.setApp_type("Zhuanshuo");
            checkBoxBean23.setStuden_type("y");
            checkBoxBean23.setPosition(3);
            checkBoxBean23.setShow_count(2);
            checkBoxBean23.setApp_title("中医专硕");
            checkBoxBean23.setTitleList(arrayList2);
            checkBoxBean23.setYear_show(0);
            checkBoxBean23.setApp_ISTIKU(true);
            this.mCheckBoxBean2.add(checkBoxBean23);
            ArrayList arrayList3 = new ArrayList();
            arrayList3.add("考点顺序");
            arrayList3.add("考点乱序");
            arrayList3.add("同步练习");
            CheckBoxBean2 checkBoxBean24 = new CheckBoxBean2();
            checkBoxBean24.setApp_id(Constants.VIA_REPORT_TYPE_SHARE_TO_QQ);
            checkBoxBean24.setApp_name("学硕考研（临床医学）");
            checkBoxBean24.setApp_type("Xueshuo");
            checkBoxBean24.setStuden_type("y");
            checkBoxBean24.setPosition(0);
            checkBoxBean24.setTitleList(arrayList3);
            checkBoxBean24.setShow_count(3);
            checkBoxBean24.setApp_title("学硕");
            checkBoxBean24.setYear_show(0);
            checkBoxBean24.setApp_ISTIKU(true);
            this.mCheckBoxBean2.add(checkBoxBean24);
        } else if (type == 3) {
            ArrayList arrayList4 = new ArrayList();
            arrayList4.add("按学科");
            arrayList4.add("按系统");
            arrayList4.add("按年份");
            arrayList4.add("病例病史");
            CheckBoxBean2 checkBoxBean25 = new CheckBoxBean2();
            checkBoxBean25.setApp_id(Constants.VIA_REPORT_TYPE_SET_AVATAR);
            checkBoxBean25.setApp_name("执业医师（临床医学）");
            checkBoxBean25.setApp_type("Zhuanshuo");
            checkBoxBean25.setStuden_type("z");
            checkBoxBean25.setPosition(1);
            checkBoxBean25.setShow_count(4);
            checkBoxBean25.setTitleList(arrayList4);
            checkBoxBean25.setApp_title("执业医师");
            checkBoxBean25.setYear_show(4);
            checkBoxBean25.setApp_ISTIKU(true);
            this.mCheckBoxBean2.add(checkBoxBean25);
            ArrayList arrayList5 = new ArrayList();
            arrayList5.add("按学科");
            arrayList5.add("按年份");
            CheckBoxBean2 checkBoxBean26 = new CheckBoxBean2();
            checkBoxBean26.setApp_id(Constants.VIA_REPORT_TYPE_JOININ_GROUP);
            checkBoxBean26.setApp_name("执业助理医师（临床医学）");
            checkBoxBean26.setApp_type("Zhuanshuo");
            checkBoxBean26.setStuden_type("z");
            checkBoxBean26.setPosition(4);
            checkBoxBean26.setTitleList(arrayList5);
            checkBoxBean26.setShow_count(2);
            checkBoxBean26.setApp_title("执业助理医师");
            checkBoxBean26.setYear_show(2);
            checkBoxBean26.setApp_ISTIKU(true);
            this.mCheckBoxBean2.add(checkBoxBean26);
            ArrayList arrayList6 = new ArrayList();
            arrayList6.add("按学科");
            CheckBoxBean2 checkBoxBean27 = new CheckBoxBean2();
            checkBoxBean27.setApp_id("21");
            checkBoxBean27.setApp_name("执业医师（中医学）");
            checkBoxBean27.setApp_type("Zhuanshuo");
            checkBoxBean27.setStuden_type("z");
            checkBoxBean27.setPosition(2);
            checkBoxBean27.setShow_count(1);
            checkBoxBean27.setTitleList(arrayList6);
            checkBoxBean27.setApp_title("中医执业医师");
            checkBoxBean27.setYear_show(0);
            checkBoxBean27.setApp_ISTIKU(true);
            this.mCheckBoxBean2.add(checkBoxBean27);
        }
        setmCheckBoxBean2(this.mCheckBoxBean2);
    }

    private void initMarks() {
        this.marks.clear();
        this.marks.add(new MarkDataBean("本科", "（临床医学）"));
        this.marks.add(new MarkDataBean("考研", "（临床医学、中医学）"));
        this.marks.add(new MarkDataBean("执业医师", "（临床医学、中医学）"));
    }

    public String getAppMark() {
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance());
        if ("40".equals(strConfig)) {
            return "[本科]";
        }
        String str = "[专硕]";
        if (Constants.VIA_REPORT_TYPE_SHARE_TO_QQ.equals(strConfig)) {
            if ("1".equals(SharePreferencesUtils.readStrConfig(CommonParameter.am_pm, ProjectApp.instance()))) {
                return "[学硕]";
            }
        } else if (!"20".equals(strConfig)) {
            str = "[执医]";
            if (!Constants.VIA_REPORT_TYPE_SET_AVATAR.equals(strConfig)) {
                if (Constants.VIA_REPORT_TYPE_JOININ_GROUP.equals(strConfig)) {
                    return "[助理]";
                }
                if (!"21".equals(strConfig)) {
                    return "";
                }
            }
        }
        return str;
    }

    public List<MarkDataBean> getMarks() {
        return this.marks;
    }

    public List<CheckBoxBean2> getmCheckBoxBean2() {
        return this.mCheckBoxBean2;
    }

    public CheckBoxBean setMarks(List<MarkDataBean> marks) {
        this.marks = marks;
        return this;
    }

    public void setmCheckBoxBean2(List<CheckBoxBean2> mCheckBoxBean2) {
        this.mCheckBoxBean2 = mCheckBoxBean2;
    }

    public CheckBoxBean(int type) {
        initData(type);
    }
}
