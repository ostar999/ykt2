package com.psychiatrygarden.bean;

import com.psychiatrygarden.bean.QuestionDataStaticSetListBean;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class RecdQuestionBean implements Serializable {
    private String code;
    private List<DataBean> data;
    private String message;
    private String server_time;

    public static class DataBean implements Serializable {
        private String am;
        private String chapter_id;
        private String chapter_parent_id;
        private String chapter_title;
        private String high_frequency;
        private String is_practice;
        private String number;
        private String number_number;
        private String number_type;
        private List<OptionBean> option;
        private String outlines_am;
        private String outlines_pm;
        private String part_id;
        private String part_num_am;
        private String part_num_pm;
        private String part_parent_id;
        private String pm;
        private String question_id;
        private String question_type;
        private String restore;
        private String s_num_am;
        private String s_num_pm;
        private String source;
        private String subject_title;
        private String title;
        private String title_img;
        private String type_str;
        private String unit;
        private String year;
        private String mEventStr = "";
        private String restore_img = "";
        private String explain = "";
        private String explain_img = "";
        private String type = "3";
        private String answer = "";
        private String media_img = "";
        private String is_real_question = "0";
        private int isdoType = 0;
        private int isNotAll = 0;
        private QuestionDataStaticSetListBean.DataBean mStaticsData = new QuestionDataStaticSetListBean.DataBean();
        private String isRight = "0";
        private String ownerAns = "";

        public static class OptionBean implements Serializable {
            private String img;
            private String key;
            private String title;
            private String type = "0";

            public String getImg() {
                return this.img;
            }

            public String getKey() {
                return this.key;
            }

            public String getTitle() {
                return this.title;
            }

            public String getType() {
                return this.type;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public String getAm() {
            return this.am;
        }

        public String getAnswer() {
            return this.answer;
        }

        public String getChapter_id() {
            return this.chapter_id;
        }

        public String getChapter_parent_id() {
            return this.chapter_parent_id;
        }

        public String getChapter_title() {
            return this.chapter_title;
        }

        public String getExplain() {
            return this.explain;
        }

        public String getExplain_img() {
            return this.explain_img;
        }

        public String getHigh_frequency() {
            return this.high_frequency;
        }

        public int getIsNotAll() {
            return this.isNotAll;
        }

        public String getIsRight() {
            return this.isRight;
        }

        public String getIs_practice() {
            return this.is_practice;
        }

        public String getIs_real_question() {
            return this.is_real_question;
        }

        public int getIsdoType() {
            return this.isdoType;
        }

        public String getMedia_img() {
            return this.media_img;
        }

        public String getNumber() {
            return this.number;
        }

        public String getNumber_number() {
            return this.number_number;
        }

        public String getNumber_type() {
            return this.number_type;
        }

        public List<OptionBean> getOption() {
            return this.option;
        }

        public String getOutlines_am() {
            return this.outlines_am;
        }

        public String getOutlines_pm() {
            return this.outlines_pm;
        }

        public String getOwnerAns() {
            return this.ownerAns;
        }

        public String getPart_id() {
            return this.part_id;
        }

        public String getPart_num_am() {
            return this.part_num_am;
        }

        public String getPart_num_pm() {
            return this.part_num_pm;
        }

        public String getPart_parent_id() {
            return this.part_parent_id;
        }

        public String getPm() {
            return this.pm;
        }

        public String getQuestion_id() {
            return this.question_id;
        }

        public String getQuestion_type() {
            return this.question_type;
        }

        public String getRestore() {
            return this.restore;
        }

        public String getRestore_img() {
            return this.restore_img;
        }

        public String getS_num_am() {
            return this.s_num_am;
        }

        public String getS_num_pm() {
            return this.s_num_pm;
        }

        public String getSource() {
            return this.source;
        }

        public String getSubject_title() {
            return this.subject_title;
        }

        public String getTitle() {
            return this.title;
        }

        public String getTitle_img() {
            return this.title_img;
        }

        public String getType() {
            return this.type;
        }

        public String getType_str() {
            return this.type_str;
        }

        public String getUnit() {
            return this.unit;
        }

        public String getYear() {
            return this.year;
        }

        public String getmEventStr() {
            return this.mEventStr;
        }

        public QuestionDataStaticSetListBean.DataBean getmStaticsData() {
            return this.mStaticsData;
        }

        public void setAm(String am) {
            this.am = am;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public void setChapter_id(String chapter_id) {
            this.chapter_id = chapter_id;
        }

        public void setChapter_parent_id(String chapter_parent_id) {
            this.chapter_parent_id = chapter_parent_id;
        }

        public void setChapter_title(String chapter_title) {
            this.chapter_title = chapter_title;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }

        public void setExplain_img(String explain_img) {
            this.explain_img = explain_img;
        }

        public void setHigh_frequency(String high_frequency) {
            this.high_frequency = high_frequency;
        }

        public void setIsNotAll(int isNotAll) {
            this.isNotAll = isNotAll;
        }

        public void setIsRight(String isRight) {
            this.isRight = isRight;
        }

        public void setIs_practice(String is_practice) {
            this.is_practice = is_practice;
        }

        public void setIs_real_question(String is_real_question) {
            this.is_real_question = is_real_question;
        }

        public void setIsdoType(int isdoType) {
            this.isdoType = isdoType;
        }

        public void setMedia_img(String media_img) {
            this.media_img = media_img;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public void setNumber_number(String number_number) {
            this.number_number = number_number;
        }

        public void setNumber_type(String number_type) {
            this.number_type = number_type;
        }

        public void setOption(List<OptionBean> option) {
            this.option = option;
        }

        public void setOutlines_am(String outlines_am) {
            this.outlines_am = outlines_am;
        }

        public void setOutlines_pm(String outlines_pm) {
            this.outlines_pm = outlines_pm;
        }

        public void setOwnerAns(String ownerAns) {
            this.ownerAns = ownerAns;
        }

        public void setPart_id(String part_id) {
            this.part_id = part_id;
        }

        public void setPart_num_am(String part_num_am) {
            this.part_num_am = part_num_am;
        }

        public void setPart_num_pm(String part_num_pm) {
            this.part_num_pm = part_num_pm;
        }

        public void setPart_parent_id(String part_parent_id) {
            this.part_parent_id = part_parent_id;
        }

        public void setPm(String pm) {
            this.pm = pm;
        }

        public void setQuestion_id(String question_id) {
            this.question_id = question_id;
        }

        public void setQuestion_type(String question_type) {
            this.question_type = question_type;
        }

        public void setRestore(String restore) {
            this.restore = restore;
        }

        public void setRestore_img(String restore_img) {
            this.restore_img = restore_img;
        }

        public void setS_num_am(String s_num_am) {
            this.s_num_am = s_num_am;
        }

        public void setS_num_pm(String s_num_pm) {
            this.s_num_pm = s_num_pm;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public void setSubject_title(String subject_title) {
            this.subject_title = subject_title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setTitle_img(String title_img) {
            this.title_img = title_img;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setType_str(String type_str) {
            this.type_str = type_str;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public void setmEventStr(String mEventStr) {
            this.mEventStr = mEventStr;
        }

        public void setmStaticsData(QuestionDataStaticSetListBean.DataBean mStaticsData) {
            this.mStaticsData = mStaticsData;
        }
    }

    public String getCode() {
        return this.code;
    }

    public List<DataBean> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public String getServer_time() {
        return this.server_time;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServer_time(String server_time) {
        this.server_time = server_time;
    }
}
