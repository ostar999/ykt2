package com.psychiatrygarden.bean;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class QuestionInfoBean implements Serializable {
    private String answer;
    private String chapter_id;
    private String chapter_parent_id;
    private String explain;
    private String explain_img;
    private String is_example;
    private String is_hard;
    private String is_practice;
    private String isgaopinkaodian;
    private String isxueshuo;
    private String iszhuanshuo;
    private String media_id;
    private String media_img;
    private String media_url;
    private String number;
    private Long number_number;
    private String number_type;
    private Long part_id;
    private Long part_num_am;
    private Long part_num_pm;
    private Long part_parent_id;
    private Long question_id;
    private String question_type;
    private String restore;
    private String restore_img;
    private Long s_num;
    private Long s_num_xue;
    private String source;
    private String source_filter;
    private String subject_name;
    private String title;
    private String title_img;
    private String type;
    private String unit;
    private String xuehsuodagang;
    private String year;
    private String zhuanshuodagang;

    public QuestionInfoBean() {
        this.question_id = 0L;
        this.subject_name = "";
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

    public String getExplain() {
        return this.explain;
    }

    public String getExplain_img() {
        return this.explain_img;
    }

    public String getIs_example() {
        return this.is_example;
    }

    public String getIs_hard() {
        return this.is_hard;
    }

    public String getIs_practice() {
        return this.is_practice;
    }

    public String getIsgaopinkaodian() {
        return this.isgaopinkaodian;
    }

    public String getIsxueshuo() {
        return this.isxueshuo;
    }

    public String getIszhuanshuo() {
        return this.iszhuanshuo;
    }

    public String getMedia_id() {
        return this.media_id;
    }

    public String getMedia_img() {
        return this.media_img;
    }

    public String getMedia_url() {
        return this.media_url;
    }

    public String getNumber() {
        return this.number;
    }

    public Long getNumber_number() {
        return this.number_number;
    }

    public String getNumber_type() {
        return this.number_type;
    }

    public Long getPart_id() {
        return this.part_id;
    }

    public Long getPart_num_am() {
        return this.part_num_am;
    }

    public Long getPart_num_pm() {
        return this.part_num_pm;
    }

    public Long getPart_parent_id() {
        return this.part_parent_id;
    }

    public Long getQuestion_id() {
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

    public Long getS_num() {
        return this.s_num;
    }

    public Long getS_num_xue() {
        return this.s_num_xue;
    }

    public String getSource() {
        return this.source;
    }

    public String getSource_filter() {
        return this.source_filter;
    }

    public String getSubject_name() {
        return this.subject_name;
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

    public String getUnit() {
        return this.unit;
    }

    public String getXuehsuodagang() {
        return this.xuehsuodagang;
    }

    public String getYear() {
        return this.year;
    }

    public String getZhuanshuodagang() {
        return this.zhuanshuodagang;
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

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public void setExplain_img(String explain_img) {
        this.explain_img = explain_img;
    }

    public QuestionInfoBean setIs_example(String is_example) {
        this.is_example = is_example;
        return this;
    }

    public QuestionInfoBean setIs_hard(String is_hard) {
        this.is_hard = is_hard;
        return this;
    }

    public void setIs_practice(String is_practice) {
        this.is_practice = is_practice;
    }

    public void setIsgaopinkaodian(String isgaopinkaodian) {
        this.isgaopinkaodian = isgaopinkaodian;
    }

    public void setIsxueshuo(String isxueshuo) {
        this.isxueshuo = isxueshuo;
    }

    public void setIszhuanshuo(String iszhuanshuo) {
        this.iszhuanshuo = iszhuanshuo;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public void setMedia_img(String media_img) {
        this.media_img = media_img;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setNumber_number(Long number_number) {
        this.number_number = number_number;
    }

    public void setNumber_type(String number_type) {
        this.number_type = number_type;
    }

    public void setPart_id(Long part_id) {
        this.part_id = part_id;
    }

    public void setPart_num_am(Long part_num_am) {
        this.part_num_am = part_num_am;
    }

    public void setPart_num_pm(Long part_num_pm) {
        this.part_num_pm = part_num_pm;
    }

    public void setPart_parent_id(Long part_parent_id) {
        this.part_parent_id = part_parent_id;
    }

    public void setQuestion_id(Long question_id) {
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

    public void setS_num(Long s_num) {
        this.s_num = s_num;
    }

    public void setS_num_xue(Long s_num_xue) {
        this.s_num_xue = s_num_xue;
    }

    public QuestionInfoBean setSource(String source) {
        this.source = source;
        return this;
    }

    public QuestionInfoBean setSource_filter(String source_filter) {
        this.source_filter = source_filter;
        return this;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
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

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setXuehsuodagang(String xuehsuodagang) {
        this.xuehsuodagang = xuehsuodagang;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setZhuanshuodagang(String zhuanshuodagang) {
        this.zhuanshuodagang = zhuanshuodagang;
    }

    public QuestionInfoBean(Long question_id) {
        this.subject_name = "";
        this.question_id = question_id;
    }

    public QuestionInfoBean(Long question_id, String chapter_parent_id, String chapter_id, String title, String title_img, Long s_num, Long s_num_xue, String number, String year, Long number_number, String number_type, String restore, String explain, String type, String answer, String media_url, String media_img, String media_id, String subject_name, String xuehsuodagang, String zhuanshuodagang, String isxueshuo, String iszhuanshuo, String isgaopinkaodian, String unit, String question_type, String is_practice, String restore_img, String explain_img, Long part_id, Long part_parent_id, Long part_num_am, Long part_num_pm, String source, String is_hard, String is_example, String source_filter) {
        this.question_id = question_id;
        this.chapter_parent_id = chapter_parent_id;
        this.chapter_id = chapter_id;
        this.title = title;
        this.title_img = title_img;
        this.s_num = s_num;
        this.s_num_xue = s_num_xue;
        this.number = number;
        this.year = year;
        this.number_number = number_number;
        this.number_type = number_type;
        this.restore = restore;
        this.explain = explain;
        this.type = type;
        this.answer = answer;
        this.media_url = media_url;
        this.media_img = media_img;
        this.media_id = media_id;
        this.subject_name = subject_name;
        this.xuehsuodagang = xuehsuodagang;
        this.zhuanshuodagang = zhuanshuodagang;
        this.isxueshuo = isxueshuo;
        this.iszhuanshuo = iszhuanshuo;
        this.isgaopinkaodian = isgaopinkaodian;
        this.unit = unit;
        this.question_type = question_type;
        this.is_practice = is_practice;
        this.restore_img = restore_img;
        this.explain_img = explain_img;
        this.part_id = part_id;
        this.part_parent_id = part_parent_id;
        this.part_num_am = part_num_am;
        this.part_num_pm = part_num_pm;
        this.source = source;
        this.is_hard = is_hard;
        this.is_example = is_example;
        this.source_filter = source_filter;
    }
}
