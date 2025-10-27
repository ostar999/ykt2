package com.ykb.ebook.model;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.ykb.ebook.model.AnalysisBean;
import com.ykb.ebook.model.BiaoqianBeab;
import com.ykb.ebook.model.QuestionDetailBean;
import com.ykb.ebook.model.QuestionStatDataBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes7.dex */
public class QuestionDetailBeanNew implements Serializable, Comparable<QuestionDetailBeanNew> {
    private String am_pm;
    private String app_id;
    private String book;
    private String cases_id;
    private String cases_parent_id;
    private String category;
    private String chapter_id;
    private String chapter_parent_id;
    private String chapter_parent_title;
    private String chapter_title;
    private int currentStemIndex;
    private List<BiaoqianBeab.DataBean> dataBiao;
    private Object english_label;
    private String explain;
    private String explain_img;
    private String filter_type;
    private boolean fromEbook;
    private String high_frequency;
    private String id;
    private String identity_id;
    private boolean isShowConunite;
    private String is_collection_question;

    @SerializedName(alternate = {"is_cut"}, value = "cut_question")
    private String is_cut;
    private String is_real_question;
    private String level;
    private String module_type;
    private String native_app_id;
    private String number;
    private String origin_type;
    private String outlines;
    private String outlines_am;
    private String outlines_mastery;
    private String outlines_pm;
    private String page;
    private String parent_chapter_id;
    private String part_id;
    private String part_parent_id;
    private String public_number;
    private String public_title;
    private List<QuestionDetailBean.RelationDTO> relation;
    private String restore;
    private String restore_img;
    private String score;
    private String score_describe;
    private String show_number;
    private String sort_chapter;
    private String sort_chapter_am;
    private String sort_chapter_pm;
    private String sort_part;
    private String sort_part_am;
    private String sort_part_pm;
    private String source;
    private String source_filter;
    private String title;
    private String title_img;
    private String type;
    private String type_str;
    private String unit;
    private String unit_chapter_id;
    private String unit_chapter_parent_id;
    private String wrong_count;
    private String year;
    private String answer = "";
    private String user_answer = "";
    private String is_right = "";
    private String is_new = "0";
    private List<QuestionDetailBean.OptionDTO> option = new ArrayList();
    private List<QuestionDetailBean.VideoListDTO> stem_audio_list = new ArrayList();
    private List<QuestionDetailBean.VideoListDTO> stem_video_list = new ArrayList();
    private List<QuestionDetailBean.VideoListDTO> video_list = new ArrayList();
    private String sort = "";
    private int actualStemIndex = -1;
    private String note = "";
    private AnalysisBean.DataBean mAnalysisBean = new AnalysisBean.DataBean();
    public QuestionStatDataBean.DataBean statData = new QuestionStatDataBean.DataBean();

    public static QuestionDetailBeanNew getDataFromQuestionDetailBean(QuestionDetailBean questionDetailBean) {
        QuestionDetailBeanNew questionDetailBeanNew = new QuestionDetailBeanNew();
        questionDetailBeanNew.id = questionDetailBean.getId();
        questionDetailBeanNew.is_cut = questionDetailBean.getIs_cut();
        questionDetailBeanNew.year = questionDetailBean.getYear();
        questionDetailBeanNew.unit = questionDetailBean.getUnit();
        questionDetailBeanNew.title = questionDetailBean.getTitle();
        questionDetailBeanNew.show_number = questionDetailBean.getShow_number();
        questionDetailBeanNew.public_title = questionDetailBean.getPublic_title();
        questionDetailBeanNew.public_number = questionDetailBean.getPublic_number();
        questionDetailBeanNew.title_img = questionDetailBean.getTitle_img();
        questionDetailBeanNew.number = questionDetailBean.getNumber();
        questionDetailBeanNew.type = questionDetailBean.getType();
        questionDetailBeanNew.origin_type = questionDetailBean.getOrigin_type();
        questionDetailBeanNew.restore = questionDetailBean.getRestore();
        questionDetailBeanNew.restore_img = questionDetailBean.getRestore_img();
        questionDetailBeanNew.explain = questionDetailBean.getExplain();
        questionDetailBeanNew.explain_img = questionDetailBean.getExplain_img();
        questionDetailBeanNew.answer = questionDetailBean.getAnswer();
        questionDetailBeanNew.user_answer = questionDetailBean.getUser_answer();
        questionDetailBeanNew.is_right = questionDetailBean.getIs_right();
        questionDetailBeanNew.is_new = questionDetailBean.getIs_new();
        ArrayList arrayList = new ArrayList();
        for (QuestionDetailBean.OptionDTO optionDTO : (QuestionDetailBean.OptionDTO[]) new Gson().fromJson(questionDetailBean.getOption(), QuestionDetailBean.OptionDTO[].class)) {
            arrayList.add(optionDTO);
        }
        questionDetailBeanNew.option = arrayList;
        questionDetailBeanNew.stem_audio_list = questionDetailBean.getStem_audio_list();
        questionDetailBeanNew.stem_video_list = questionDetailBean.getStem_video_list();
        questionDetailBeanNew.video_list = questionDetailBean.getVideo_list();
        questionDetailBeanNew.native_app_id = questionDetailBean.getNative_app_id();
        if (TextUtils.isEmpty(questionDetailBean.getApp_id())) {
            questionDetailBeanNew.app_id = questionDetailBean.getOld_app_id();
        } else {
            questionDetailBeanNew.app_id = questionDetailBean.getApp_id();
        }
        questionDetailBeanNew.chapter_id = questionDetailBean.getChapter_id();
        questionDetailBeanNew.chapter_parent_id = questionDetailBean.getChapter_parent_id();
        questionDetailBeanNew.unit_chapter_id = questionDetailBean.getUnit_chapter_id();
        questionDetailBeanNew.unit_chapter_parent_id = questionDetailBean.getUnit_chapter_parent_id();
        questionDetailBeanNew.cases_id = questionDetailBean.getCases_id();
        questionDetailBeanNew.cases_parent_id = questionDetailBean.getCases_parent_id();
        questionDetailBeanNew.part_id = questionDetailBean.getPart_id();
        questionDetailBeanNew.part_parent_id = questionDetailBean.getPart_parent_id();
        questionDetailBeanNew.sort_chapter = questionDetailBean.getSort_chapter();
        questionDetailBeanNew.sort_chapter_am = questionDetailBean.getSort_chapter_am();
        questionDetailBeanNew.sort_chapter_pm = questionDetailBean.getSort_chapter_pm();
        questionDetailBeanNew.outlines = questionDetailBean.getOutlines();
        questionDetailBeanNew.outlines_am = questionDetailBean.getOutlines_am();
        questionDetailBeanNew.outlines_pm = questionDetailBean.getOutlines_pm();
        questionDetailBeanNew.sort = questionDetailBean.getSort();
        if (TextUtils.isEmpty(questionDetailBean.getSort())) {
            questionDetailBeanNew.setSort("1");
        }
        questionDetailBeanNew.sort_part = questionDetailBean.getSort_part();
        questionDetailBeanNew.sort_part_am = questionDetailBean.getSort_part_am();
        questionDetailBeanNew.sort_part_pm = questionDetailBean.getSort_part_pm();
        questionDetailBeanNew.high_frequency = questionDetailBean.getHigh_frequency();
        questionDetailBeanNew.am_pm = questionDetailBean.getAm_pm();
        questionDetailBeanNew.is_collection_question = questionDetailBean.getIs_collection_question();
        questionDetailBeanNew.is_real_question = questionDetailBean.getIs_real_question();
        questionDetailBeanNew.source = questionDetailBean.getSource();
        questionDetailBeanNew.relation = questionDetailBean.getRelation();
        questionDetailBeanNew.source_filter = questionDetailBean.getSource_filter();
        questionDetailBeanNew.filter_type = questionDetailBean.getFilter_type();
        questionDetailBeanNew.type_str = questionDetailBean.getType_str();
        questionDetailBeanNew.score = questionDetailBean.getScore();
        questionDetailBeanNew.score_describe = questionDetailBean.getScore_describe();
        questionDetailBeanNew.chapter_title = questionDetailBean.getChapter_title();
        questionDetailBeanNew.chapter_parent_title = questionDetailBean.getChapter_parent_title();
        questionDetailBeanNew.wrong_count = questionDetailBean.getWrong_count();
        questionDetailBeanNew.identity_id = questionDetailBean.getIdentity_id();
        questionDetailBeanNew.module_type = questionDetailBean.getModule_type();
        questionDetailBeanNew.category = questionDetailBean.getCategory();
        questionDetailBeanNew.actualStemIndex = questionDetailBean.getActualStemIndex();
        questionDetailBeanNew.currentStemIndex = questionDetailBean.getCurrentStemIndex();
        questionDetailBeanNew.outlines_mastery = questionDetailBean.getOutlines_mastery();
        questionDetailBeanNew.isShowConunite = questionDetailBean.isShowConunite();
        questionDetailBeanNew.english_label = questionDetailBean.getEnglish_label();
        questionDetailBeanNew.note = questionDetailBean.getNote();
        questionDetailBeanNew.book = questionDetailBean.getBook();
        questionDetailBeanNew.level = questionDetailBean.getLevel();
        questionDetailBeanNew.page = questionDetailBean.getPage();
        questionDetailBeanNew.parent_chapter_id = questionDetailBean.getParent_chapter_id();
        questionDetailBeanNew.dataBiao = questionDetailBean.getDataBiao();
        questionDetailBeanNew.mAnalysisBean = questionDetailBean.getmAnalysisBean();
        questionDetailBeanNew.statData = questionDetailBean.getStatData();
        return questionDetailBeanNew;
    }

    public int getActualStemIndex() {
        return this.actualStemIndex;
    }

    public String getAm_pm() {
        return this.am_pm;
    }

    public String getAnswer() {
        return this.answer;
    }

    public String getApp_id() {
        return this.app_id;
    }

    public String getBook() {
        return this.book;
    }

    public String getCases_id() {
        return this.cases_id;
    }

    public String getCases_parent_id() {
        return this.cases_parent_id;
    }

    public String getCategory() {
        return this.category;
    }

    public String getChapter_id() {
        return this.chapter_id;
    }

    public String getChapter_parent_id() {
        return this.chapter_parent_id;
    }

    public String getChapter_parent_title() {
        return this.chapter_parent_title;
    }

    public String getChapter_title() {
        return this.chapter_title;
    }

    public int getCurrentStemIndex() {
        return this.currentStemIndex;
    }

    public List<BiaoqianBeab.DataBean> getDataBiao() {
        return this.dataBiao;
    }

    public QuestionDetailBean.EnglishLableBean getEnglish_label() {
        if (this.english_label instanceof ArrayList) {
            return null;
        }
        return (QuestionDetailBean.EnglishLableBean) new Gson().fromJson(new Gson().toJson(this.english_label), QuestionDetailBean.EnglishLableBean.class);
    }

    public String getExplain() {
        return this.explain;
    }

    public String getExplain_img() {
        return this.explain_img;
    }

    public String getFilter_type() {
        return this.filter_type;
    }

    public String getHigh_frequency() {
        return this.high_frequency;
    }

    public String getId() {
        return this.id;
    }

    public String getIdentity_id() {
        return this.identity_id;
    }

    public String getIs_collection_question() {
        return this.is_collection_question;
    }

    public String getIs_cut() {
        return this.is_cut;
    }

    public String getIs_new() {
        return this.is_new;
    }

    public String getIs_real_question() {
        return this.is_real_question;
    }

    public String getIs_right() {
        if (TextUtils.isEmpty(this.user_answer)) {
            this.is_right = "";
        } else if (this.answer.equals(this.user_answer) || this.user_answer.equals("1")) {
            this.is_right = "1";
        } else {
            this.is_right = "0";
        }
        return this.is_right;
    }

    public String getLevel() {
        return this.level;
    }

    public String getModule_type() {
        return this.module_type;
    }

    public String getNative_app_id() {
        return this.native_app_id;
    }

    public String getNote() {
        return this.note;
    }

    public String getNumber() {
        return this.number;
    }

    public List<QuestionDetailBean.OptionDTO> getOption() {
        return this.option;
    }

    public String getOrigin_type() {
        return this.origin_type;
    }

    public String getOutlines() {
        return this.outlines;
    }

    public String getOutlines_am() {
        return this.outlines_am;
    }

    public String getOutlines_mastery() {
        return this.outlines_mastery;
    }

    public String getOutlines_pm() {
        return this.outlines_pm;
    }

    public String getPage() {
        return this.page;
    }

    public String getParent_chapter_id() {
        return this.parent_chapter_id;
    }

    public String getPart_id() {
        return this.part_id;
    }

    public String getPart_parent_id() {
        return this.part_parent_id;
    }

    public String getPublic_number() {
        return this.public_number;
    }

    public String getPublic_title() {
        String str = this.public_title;
        return str == null ? "" : str;
    }

    public List<QuestionDetailBean.RelationDTO> getRelation() {
        return this.relation;
    }

    public String getRestore() {
        return this.restore;
    }

    public String getRestore_img() {
        return this.restore_img;
    }

    public String getScore() {
        return this.score;
    }

    public String getScore_describe() {
        return this.score_describe;
    }

    public String getShow_number() {
        return this.show_number;
    }

    public String getSort() {
        return this.sort;
    }

    public String getSort_chapter() {
        return this.sort_chapter;
    }

    public String getSort_chapter_am() {
        return this.sort_chapter_am;
    }

    public String getSort_chapter_pm() {
        return this.sort_chapter_pm;
    }

    public String getSort_part() {
        return this.sort_part;
    }

    public String getSort_part_am() {
        return this.sort_part_am;
    }

    public String getSort_part_pm() {
        return this.sort_part_pm;
    }

    public String getSource() {
        return this.source;
    }

    public String getSource_filter() {
        return this.source_filter;
    }

    public QuestionStatDataBean.DataBean getStatData() {
        return this.statData;
    }

    public List<QuestionDetailBean.VideoListDTO> getStem_audio_list() {
        return this.stem_audio_list;
    }

    public List<QuestionDetailBean.VideoListDTO> getStem_video_list() {
        return this.stem_video_list;
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

    public String getUnit_chapter_id() {
        return this.unit_chapter_id;
    }

    public String getUnit_chapter_parent_id() {
        return this.unit_chapter_parent_id;
    }

    public String getUser_answer() {
        return this.user_answer;
    }

    public List<QuestionDetailBean.VideoListDTO> getVideo_list() {
        return this.video_list;
    }

    public String getWrong_count() {
        return this.wrong_count;
    }

    public String getYear() {
        return this.year;
    }

    public AnalysisBean.DataBean getmAnalysisBean() {
        return this.mAnalysisBean;
    }

    public boolean isFromEbook() {
        return this.fromEbook;
    }

    public boolean isShowConunite() {
        return this.isShowConunite;
    }

    public void setActualStemIndex(int i2) {
        this.actualStemIndex = i2;
    }

    public void setAm_pm(String str) {
        this.am_pm = str;
    }

    public void setAnswer(String str) {
        this.answer = str;
    }

    public void setApp_id(String str) {
        this.app_id = str;
    }

    public void setBook(String str) {
        this.book = str;
    }

    public void setCases_id(String str) {
        this.cases_id = str;
    }

    public void setCases_parent_id(String str) {
        this.cases_parent_id = str;
    }

    public void setCategory(String str) {
        this.category = str;
    }

    public void setChapter_id(String str) {
        this.chapter_id = str;
    }

    public void setChapter_parent_id(String str) {
        this.chapter_parent_id = str;
    }

    public void setChapter_parent_title(String str) {
        this.chapter_parent_title = str;
    }

    public void setChapter_title(String str) {
        this.chapter_title = str;
    }

    public void setCurrentStemIndex(int i2) {
        this.currentStemIndex = i2;
    }

    public void setDataBiao(List<BiaoqianBeab.DataBean> list) {
        this.dataBiao = list;
    }

    public void setEnglish_label(Object obj) {
        this.english_label = obj;
    }

    public void setExplain(String str) {
        this.explain = str;
    }

    public void setExplain_img(String str) {
        this.explain_img = str;
    }

    public void setFilter_type(String str) {
        this.filter_type = str;
    }

    public void setFromEbook(boolean z2) {
        this.fromEbook = z2;
    }

    public void setHigh_frequency(String str) {
        this.high_frequency = str;
    }

    public void setId(String str) {
        this.id = str;
    }

    public void setIdentity_id(String str) {
        this.identity_id = str;
    }

    public void setIs_collection_question(String str) {
        this.is_collection_question = str;
    }

    public void setIs_cut(String str) {
        this.is_cut = str;
    }

    public void setIs_new(String str) {
        this.is_new = str;
    }

    public void setIs_real_question(String str) {
        this.is_real_question = str;
    }

    public void setIs_right(String str) {
        this.is_right = str;
    }

    public void setLevel(String str) {
        this.level = str;
    }

    public void setModule_type(String str) {
        this.module_type = str;
    }

    public void setNative_app_id(String str) {
        this.native_app_id = str;
    }

    public void setNote(String str) {
        this.note = str;
    }

    public void setNumber(String str) {
        this.number = str;
    }

    public void setOption(List<QuestionDetailBean.OptionDTO> list) {
        this.option = list;
    }

    public void setOrigin_type(String str) {
        this.origin_type = str;
    }

    public void setOutlines(String str) {
        this.outlines = str;
    }

    public void setOutlines_am(String str) {
        this.outlines_am = str;
    }

    public void setOutlines_mastery(String str) {
        this.outlines_mastery = str;
    }

    public void setOutlines_pm(String str) {
        this.outlines_pm = str;
    }

    public void setPage(String str) {
        this.page = str;
    }

    public void setParent_chapter_id(String str) {
        this.parent_chapter_id = str;
    }

    public void setPart_id(String str) {
        this.part_id = str;
    }

    public void setPart_parent_id(String str) {
        this.part_parent_id = str;
    }

    public void setPublic_number(String str) {
        this.public_number = str;
    }

    public void setPublic_title(String str) {
        this.public_title = str;
    }

    public void setRelation(List<QuestionDetailBean.RelationDTO> list) {
        this.relation = list;
    }

    public void setRestore(String str) {
        this.restore = str;
    }

    public void setRestore_img(String str) {
        this.restore_img = str;
    }

    public void setScore(String str) {
        this.score = str;
    }

    public void setScore_describe(String str) {
        this.score_describe = str;
    }

    public void setShowConunite(boolean z2) {
        this.isShowConunite = z2;
    }

    public void setShow_number(String str) {
        this.show_number = str;
    }

    public void setSort(String str) {
        this.sort = str;
    }

    public void setSort_chapter(String str) {
        this.sort_chapter = str;
    }

    public void setSort_chapter_am(String str) {
        this.sort_chapter_am = str;
    }

    public void setSort_chapter_pm(String str) {
        this.sort_chapter_pm = str;
    }

    public void setSort_part(String str) {
        this.sort_part = str;
    }

    public void setSort_part_am(String str) {
        this.sort_part_am = str;
    }

    public void setSort_part_pm(String str) {
        this.sort_part_pm = str;
    }

    public void setSource(String str) {
        this.source = str;
    }

    public void setSource_filter(String str) {
        this.source_filter = str;
    }

    public void setStatData(QuestionStatDataBean.DataBean dataBean) {
        this.statData = dataBean;
    }

    public void setStem_audio_list(List<QuestionDetailBean.VideoListDTO> list) {
        this.stem_audio_list = list;
    }

    public void setStem_video_list(List<QuestionDetailBean.VideoListDTO> list) {
        this.stem_video_list = list;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public void setTitle_img(String str) {
        this.title_img = str;
    }

    public void setType(String str) {
        this.type = str;
    }

    public void setType_str(String str) {
        this.type_str = str;
    }

    public void setUnit(String str) {
        this.unit = str;
    }

    public void setUnit_chapter_id(String str) {
        this.unit_chapter_id = str;
    }

    public void setUnit_chapter_parent_id(String str) {
        this.unit_chapter_parent_id = str;
    }

    public void setUser_answer(String str) {
        String str2;
        int iIndexOf;
        this.user_answer = str;
        if (TextUtils.isEmpty(str) || (str2 = this.filter_type) == null) {
            return;
        }
        if (str2.contains("3") && (iIndexOf = this.filter_type.indexOf("3")) != -1) {
            int length = this.filter_type.length();
            StringBuilder sb = new StringBuilder();
            int i2 = 0;
            while (i2 < length) {
                if (i2 != iIndexOf) {
                    sb.append(this.filter_type.charAt(i2));
                    sb.append(i2 != length + (-1) ? "," : "");
                }
                i2++;
            }
            this.filter_type = sb.toString();
        }
        if (!"0".equals(getIs_right()) || this.filter_type.contains("1")) {
            return;
        }
        this.filter_type += ",1";
    }

    public void setVideo_list(List<QuestionDetailBean.VideoListDTO> list) {
        this.video_list = list;
    }

    public void setWrong_count(String str) {
        this.wrong_count = str;
    }

    public void setYear(String str) {
        this.year = str;
    }

    public void setmAnalysisBean(AnalysisBean.DataBean dataBean) {
        this.mAnalysisBean = dataBean;
    }

    public int sort(QuestionDetailBeanNew questionDetailBeanNew) {
        if (Integer.parseInt(getSort()) > Integer.parseInt(questionDetailBeanNew.getSort())) {
            return 1;
        }
        return Integer.parseInt(getSort()) < Integer.parseInt(questionDetailBeanNew.getSort()) ? -1 : 0;
    }

    @Override // java.lang.Comparable
    public int compareTo(QuestionDetailBeanNew questionDetailBeanNew) {
        return sort(questionDetailBeanNew);
    }
}
