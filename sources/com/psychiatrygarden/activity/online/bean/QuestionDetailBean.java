package com.psychiatrygarden.activity.online.bean;

import android.text.TextUtils;
import cn.hutool.core.lang.RegexPool;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.psychiatrygarden.activity.answer.bean.AnalysisBean;
import com.psychiatrygarden.activity.online.bean.QuestionStatDataBean;
import com.psychiatrygarden.bean.BiaoqianBeab;
import com.psychiatrygarden.bean.QuestionTopLabel;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/* loaded from: classes5.dex */
public class QuestionDetailBean implements Serializable, Comparable<QuestionDetailBean> {
    private String allAnswerCount;
    private String allAnswerRightRate;
    private String allAnswerTime;
    private String am_pm;
    private String answerTime;
    private String app_id;
    private String book;
    private String cases_id;
    private String cases_parent_id;
    private String category;
    private String chapter_id;
    private String chapter_parent_id;
    private String chapter_parent_title;
    private String chapter_title;
    private String cognition;
    private int currentStemIndex;
    private List<BiaoqianBeab.DataBean> dataBiao;

    @SerializedName("easy_wrong")
    private String easyOption;
    private Object english_label;
    private String error_analysis;
    private String error_analysis_img;
    private String exam_id;
    private String exam_title;
    private String explain;
    private String explain_img;
    private String filter_type;
    private String high_frequency;
    private String id;
    private String identity_id;
    private String identity_title;
    private boolean isShowConunite;
    private String is_collection_question;

    @SerializedName(alternate = {"is_cut"}, value = "cut_question")
    private String is_cut;
    private String is_real_question;
    private String is_redo;
    private String knowledge_id;
    private KnowledgePointTree knowledge_path;
    private String level;
    private String module_type;
    private String myAnswerCount;
    private String myAnswerRightRate;
    private String native_app_id;
    private String node_id;
    private String number;
    private String option_analysis;
    private String option_analysis_img;
    private String origin_type;
    private String outlines;
    private String outlines_am;
    private String outlines_mastery;
    private String outlines_pm;
    private String page;
    private String paper_id;
    private String parent_chapter_id;
    private String part_id;
    private String part_parent_id;
    private String public_number;
    private String public_title;
    private String question_category_id;
    private String question_level;
    private List<RelationDTO> relation;
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
    private String study_plan_date;
    private String time_used_ms;
    private String title;
    private String title_img;
    private List<QuestionTopLabel> top_label;
    private String type;
    private String type_str;
    private String unit;
    private String unit_chapter_id;
    private String unit_chapter_parent_id;
    private String unit_id;
    private String unit_title;
    private String wrong_count;
    private String year;
    private String answer = "";
    private String user_answer = "";
    private String is_right = "";
    private String is_new = "0";
    private List<OptionDTO> option = new ArrayList();
    private List<VideoListDTO> stem_audio_list = new ArrayList();
    private List<VideoListDTO> stem_video_list = new ArrayList();
    private List<VideoListDTO> video_list = new ArrayList();
    private String sort = "";
    private int actualStemIndex = -1;
    private String doDuration = "0";
    private String note = "";
    private AnalysisBean.DataBean mAnalysisBean = new AnalysisBean.DataBean();
    public QuestionStatDataBean.DataBean statData = new QuestionStatDataBean.DataBean();

    public static class EnglishLableBean implements Serializable {

        @JsonAdapter(String2ArrayAdapter.class)
        private List<ExampleDTO> example;
        private List<MemoryDTO> memory;
        private WordBean word;

        public static class ExampleDTO implements Serializable {
            private String example;
            private String example_pronunciation;
            private String example_translate;

            public String getExample() {
                return this.example;
            }

            public String getExample_pronunciation() {
                return this.example_pronunciation;
            }

            public String getExample_translate() {
                return this.example_translate;
            }

            public void setExample(String example) {
                this.example = example;
            }

            public void setExample_pronunciation(String example_pronunciation) {
                this.example_pronunciation = example_pronunciation;
            }

            public void setExample_translate(String example_translate) {
                this.example_translate = example_translate;
            }
        }

        public static class MemoryDTO implements Serializable {
            private String memory;
            private String memory_label;

            public String getMemory() {
                return this.memory;
            }

            public String getMemory_label() {
                return this.memory_label;
            }

            public void setMemory(String memory) {
                this.memory = memory;
            }

            public void setMemory_label(String memory_label) {
                this.memory_label = memory_label;
            }
        }

        public static class WordBean implements Serializable {
            private String pronunciation;
            private String symbols;
            private String title;
            private List<String> translate;

            public String getPronunciation() {
                return this.pronunciation;
            }

            public String getSymbols() {
                return this.symbols;
            }

            public String getTitle() {
                return this.title;
            }

            public List<String> getTranslate() {
                return this.translate;
            }

            public void setPronunciation(String pronunciation) {
                this.pronunciation = pronunciation;
            }

            public void setSymbols(String symbols) {
                this.symbols = symbols;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setTranslate(List<String> translate) {
                this.translate = translate;
            }
        }

        public List<ExampleDTO> getExample() {
            return this.example;
        }

        public List<MemoryDTO> getMemory() {
            return this.memory;
        }

        public WordBean getWord() {
            return this.word;
        }

        public void setExample(List<ExampleDTO> example) {
            this.example = example;
        }

        public void setMemory(List<MemoryDTO> memory) {
            this.memory = memory;
        }

        public void setWord(WordBean word) {
            this.word = word;
        }
    }

    public static class KnowledgePointTree {
        private KnowledgePointTree children;
        private String id;
        private String name;
        private String number;
        private List<Map<String, String>> top_label;
        private boolean expand = false;
        private boolean childExpand = false;
        private boolean visible = false;
        private boolean showLine = false;

        public KnowledgePointTree getChildren() {
            return this.children;
        }

        public String getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public String getNumber() {
            String str = this.number;
            if (str == null || !str.matches(RegexPool.NUMBERS) || Integer.parseInt(this.number) == 0) {
                return null;
            }
            return this.number;
        }

        public List<Map<String, String>> getTop_label() {
            return this.top_label;
        }

        public boolean isChildExpand() {
            return this.childExpand;
        }

        public boolean isExpand() {
            return this.expand;
        }

        public boolean isShowLine() {
            return this.showLine;
        }

        public boolean isVisible() {
            return this.visible;
        }

        public void setChildExpand(boolean childExpand) {
            this.childExpand = childExpand;
        }

        public void setChildren(KnowledgePointTree children) {
            this.children = children;
        }

        public void setExpand(boolean expand) {
            this.expand = expand;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public void setShowLine(boolean showLine) {
            this.showLine = showLine;
        }

        public void setTop_label(List<Map<String, String>> top_label) {
            this.top_label = top_label;
        }

        public void setVisible(boolean visible) {
            this.visible = visible;
        }
    }

    public static class OptionDTO implements Serializable {
        private String img;
        private String key;
        private String title;
        private String type = "";
        private String rate = "0";

        public String getImg() {
            return this.img;
        }

        public String getKey() {
            return this.key;
        }

        public String getRate() {
            return this.rate;
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

        public void setRate(String rate) {
            this.rate = rate;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class RelationDTO implements Serializable {
        private String number;
        private String question_id;

        public String getNumber() {
            return this.number;
        }

        public String getQuestion_id() {
            return this.question_id;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public void setQuestion_id(String question_id) {
            this.question_id = question_id;
        }
    }

    public static class String2ArrayAdapter implements JsonDeserializer<List<EnglishLableBean.ExampleDTO>> {
        @Override // com.google.gson.JsonDeserializer
        public List<EnglishLableBean.ExampleDTO> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if (!jsonElement.isJsonArray()) {
                return new ArrayList();
            }
            try {
                return jsonElement.getAsJsonArray().size() > 0 ? Arrays.asList((EnglishLableBean.ExampleDTO[]) new Gson().fromJson(jsonElement, EnglishLableBean.ExampleDTO[].class)) : new ArrayList();
            } catch (Exception unused) {
                return new ArrayList();
            }
        }
    }

    public static class VideoListDTO implements Serializable {
        private String thumb;
        private String type;
        private String video_id;

        public String getThumb() {
            return this.thumb;
        }

        public String getType() {
            return this.type;
        }

        public String getVideo_id() {
            return this.video_id;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }
    }

    public int getActualStemIndex() {
        return this.actualStemIndex;
    }

    public String getAllAnswerCount() {
        String str = this.allAnswerCount;
        return str == null ? "-" : str;
    }

    public String getAllAnswerRightRate() {
        return this.allAnswerRightRate + "%";
    }

    public String getAllAnswerTime() {
        String str = this.allAnswerTime;
        return str == null ? "-" : str;
    }

    public String getAm_pm() {
        return this.am_pm;
    }

    public String getAnswer() {
        return this.answer;
    }

    public String getAnswerTime() {
        String str = this.answerTime;
        return str == null ? "-" : str;
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

    public String getCognition() {
        return this.cognition;
    }

    public int getCurrentStemIndex() {
        return this.currentStemIndex;
    }

    public List<BiaoqianBeab.DataBean> getDataBiao() {
        return this.dataBiao;
    }

    public String getDoDuration() {
        return this.doDuration;
    }

    public String getEasyOption() {
        String str = this.easyOption;
        return str == null ? "-" : str;
    }

    public EnglishLableBean getEnglish_label() {
        Object obj = this.english_label;
        if (((obj instanceof String) && TextUtils.isEmpty((String) obj)) || (this.english_label instanceof ArrayList)) {
            return null;
        }
        return (EnglishLableBean) new Gson().fromJson(new Gson().toJson(this.english_label), EnglishLableBean.class);
    }

    public String getError_analysis() {
        return this.error_analysis;
    }

    public String getError_analysis_img() {
        return this.error_analysis_img;
    }

    public String getExam_id() {
        return this.exam_id;
    }

    public String getExam_title() {
        return this.exam_title;
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

    public String getIdentity_title() {
        return this.identity_title;
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

    public String getIs_redo() {
        return this.is_redo;
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

    public String getKnowledge_id() {
        return this.knowledge_id;
    }

    public KnowledgePointTree getKnowledge_path() {
        return this.knowledge_path;
    }

    public String getLevel() {
        return this.level;
    }

    public String getModule_type() {
        return this.module_type;
    }

    public String getMyAnswerCount() {
        String str = this.myAnswerCount;
        return str == null ? "-" : str;
    }

    public String getMyAnswerRightRate() {
        return this.myAnswerRightRate + "%";
    }

    public String getNative_app_id() {
        return this.native_app_id;
    }

    public String getNode_id() {
        return this.node_id;
    }

    public String getNote() {
        return this.note;
    }

    public String getNumber() {
        return this.number;
    }

    public List<OptionDTO> getOption() {
        return this.option;
    }

    public String getOption_analysis() {
        return this.option_analysis;
    }

    public String getOption_analysis_img() {
        return this.option_analysis_img;
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

    public String getPaper_id() {
        return this.paper_id;
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

    public String getQuestion_category_id() {
        return this.question_category_id;
    }

    public String getQuestion_level() {
        return this.question_level;
    }

    public List<RelationDTO> getRelation() {
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

    public List<VideoListDTO> getStem_audio_list() {
        return this.stem_audio_list;
    }

    public List<VideoListDTO> getStem_video_list() {
        return this.stem_video_list;
    }

    public String getStudy_plan_date() {
        return this.study_plan_date;
    }

    public String getTime_used_ms() {
        return this.time_used_ms;
    }

    public String getTitle() {
        return this.title;
    }

    public String getTitle_img() {
        return this.title_img;
    }

    public List<QuestionTopLabel> getTop_label() {
        return this.top_label;
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

    public String getUnit_id() {
        return this.unit_id;
    }

    public String getUnit_title() {
        return this.unit_title;
    }

    public String getUser_answer() {
        return this.user_answer;
    }

    public List<VideoListDTO> getVideo_list() {
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

    public boolean isShowConunite() {
        return this.isShowConunite;
    }

    public void setActualStemIndex(int actualStemIndex) {
        this.actualStemIndex = actualStemIndex;
    }

    public void setAllAnswerCount(String allAnswerCount) {
        this.allAnswerCount = allAnswerCount;
    }

    public void setAllAnswerRightRate(String allAnswerRightRate) {
        this.allAnswerRightRate = allAnswerRightRate;
    }

    public void setAllAnswerTime(String allAnswerTime) {
        this.allAnswerTime = allAnswerTime;
    }

    public void setAm_pm(String am_pm) {
        this.am_pm = am_pm;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public void setCases_id(String cases_id) {
        this.cases_id = cases_id;
    }

    public void setCases_parent_id(String cases_parent_id) {
        this.cases_parent_id = cases_parent_id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public void setChapter_parent_id(String chapter_parent_id) {
        this.chapter_parent_id = chapter_parent_id;
    }

    public void setChapter_parent_title(String chapter_parent_title) {
        this.chapter_parent_title = chapter_parent_title;
    }

    public void setChapter_title(String chapter_title) {
        this.chapter_title = chapter_title;
    }

    public void setCognition(String cognition) {
        this.cognition = cognition;
    }

    public void setCurrentStemIndex(int currentStemIndex) {
        this.currentStemIndex = currentStemIndex;
    }

    public void setDataBiao(List<BiaoqianBeab.DataBean> dataBiao) {
        this.dataBiao = dataBiao;
    }

    public void setDoDuration(String doDuration) {
        this.doDuration = doDuration;
    }

    public void setEasyOption(String easyOption) {
        this.easyOption = easyOption;
    }

    public void setEnglish_label(Object english_label) {
        this.english_label = english_label;
    }

    public void setError_analysis(String error_analysis) {
        this.error_analysis = error_analysis;
    }

    public void setError_analysis_img(String error_analysis_img) {
        this.error_analysis_img = error_analysis_img;
    }

    public void setExam_id(String exam_id) {
        this.exam_id = exam_id;
    }

    public void setExam_title(String exam_title) {
        this.exam_title = exam_title;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public void setExplain_img(String explain_img) {
        this.explain_img = explain_img;
    }

    public void setFilter_type(String filter_type) {
        this.filter_type = filter_type;
    }

    public void setHigh_frequency(String high_frequency) {
        this.high_frequency = high_frequency;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIdentity_id(String identity_id) {
        this.identity_id = identity_id;
    }

    public void setIdentity_title(String identity_title) {
        this.identity_title = identity_title;
    }

    public void setIs_collection_question(String is_collection_question) {
        this.is_collection_question = is_collection_question;
    }

    public void setIs_cut(String is_cut) {
        this.is_cut = is_cut;
    }

    public void setIs_new(String is_new) {
        this.is_new = is_new;
    }

    public void setIs_real_question(String is_real_question) {
        this.is_real_question = is_real_question;
    }

    public void setIs_redo(String is_redo) {
        this.is_redo = is_redo;
    }

    public void setIs_right(String is_right) {
        this.is_right = is_right;
    }

    public void setKnowledge_id(String knowledge_id) {
        this.knowledge_id = knowledge_id;
    }

    public void setKnowledge_path(KnowledgePointTree knowledge_path) {
        this.knowledge_path = knowledge_path;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setModule_type(String module_type) {
        this.module_type = module_type;
    }

    public void setMyAnswerCount(String myAnswerCount) {
        this.myAnswerCount = myAnswerCount;
    }

    public void setMyAnswerRightRate(String myAnswerRightRate) {
        this.myAnswerRightRate = myAnswerRightRate;
    }

    public void setNative_app_id(String native_app_id) {
        this.native_app_id = native_app_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setOption(List<OptionDTO> option) {
        this.option = option;
    }

    public void setOption_analysis(String option_analysis) {
        this.option_analysis = option_analysis;
    }

    public void setOption_analysis_img(String option_analysis_img) {
        this.option_analysis_img = option_analysis_img;
    }

    public void setOrigin_type(String origin_type) {
        this.origin_type = origin_type;
    }

    public void setOutlines(String outlines) {
        this.outlines = outlines;
    }

    public void setOutlines_am(String outlines_am) {
        this.outlines_am = outlines_am;
    }

    public void setOutlines_mastery(String outlines_mastery) {
        this.outlines_mastery = outlines_mastery;
    }

    public void setOutlines_pm(String outlines_pm) {
        this.outlines_pm = outlines_pm;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setPaper_id(String paper_id) {
        this.paper_id = paper_id;
    }

    public void setParent_chapter_id(String parent_chapter_id) {
        this.parent_chapter_id = parent_chapter_id;
    }

    public void setPart_id(String part_id) {
        this.part_id = part_id;
    }

    public void setPart_parent_id(String part_parent_id) {
        this.part_parent_id = part_parent_id;
    }

    public void setPublic_number(String public_number) {
        this.public_number = public_number;
    }

    public void setPublic_title(String public_title) {
        this.public_title = public_title;
    }

    public void setQuestion_category_id(String question_category_id) {
        this.question_category_id = question_category_id;
    }

    public void setQuestion_level(String question_level) {
        this.question_level = question_level;
    }

    public void setRelation(List<RelationDTO> relation) {
        this.relation = relation;
    }

    public void setRestore(String restore) {
        this.restore = restore;
    }

    public void setRestore_img(String restore_img) {
        this.restore_img = restore_img;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setScore_describe(String score_describe) {
        this.score_describe = score_describe;
    }

    public void setShowConunite(boolean showConunite) {
        this.isShowConunite = showConunite;
    }

    public void setShow_number(String show_number) {
        this.show_number = show_number;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setSort_chapter(String sort_chapter) {
        this.sort_chapter = sort_chapter;
    }

    public void setSort_chapter_am(String sort_chapter_am) {
        this.sort_chapter_am = sort_chapter_am;
    }

    public void setSort_chapter_pm(String sort_chapter_pm) {
        this.sort_chapter_pm = sort_chapter_pm;
    }

    public void setSort_part(String sort_part) {
        this.sort_part = sort_part;
    }

    public void setSort_part_am(String sort_part_am) {
        this.sort_part_am = sort_part_am;
    }

    public void setSort_part_pm(String sort_part_pm) {
        this.sort_part_pm = sort_part_pm;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setSource_filter(String source_filter) {
        this.source_filter = source_filter;
    }

    public void setStatData(QuestionStatDataBean.DataBean statData) {
        this.statData = statData;
    }

    public void setStem_audio_list(List<VideoListDTO> stem_audio_list) {
        this.stem_audio_list = stem_audio_list;
    }

    public void setStem_video_list(List<VideoListDTO> stem_video_list) {
        this.stem_video_list = stem_video_list;
    }

    public void setStudy_plan_date(String study_plan_date) {
        this.study_plan_date = study_plan_date;
    }

    public void setTime_used_ms(String time_used_ms) {
        this.time_used_ms = time_used_ms;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitle_img(String title_img) {
        this.title_img = title_img;
    }

    public void setTop_label(List<QuestionTopLabel> top_label) {
        this.top_label = top_label;
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

    public void setUnit_chapter_id(String unit_chapter_id) {
        this.unit_chapter_id = unit_chapter_id;
    }

    public void setUnit_chapter_parent_id(String unit_chapter_parent_id) {
        this.unit_chapter_parent_id = unit_chapter_parent_id;
    }

    public void setUnit_id(String unit_id) {
        this.unit_id = unit_id;
    }

    public void setUnit_title(String unit_title) {
        this.unit_title = unit_title;
    }

    public void setUser_answer(String user_answer) {
        String str;
        int iIndexOf;
        this.user_answer = user_answer;
        if (TextUtils.isEmpty(user_answer) || (str = this.filter_type) == null) {
            return;
        }
        if (str.contains("3") && (iIndexOf = this.filter_type.indexOf("3")) != -1) {
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

    public void setVideo_list(List<VideoListDTO> video_list) {
        this.video_list = video_list;
    }

    public void setWrong_count(String wrong_count) {
        this.wrong_count = wrong_count;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setmAnalysisBean(AnalysisBean.DataBean mAnalysisBean) {
        this.mAnalysisBean = mAnalysisBean;
    }

    public int sort(QuestionDetailBean o2) {
        if (Integer.parseInt(getSort()) > Integer.parseInt(o2.getSort())) {
            return 1;
        }
        return Integer.parseInt(getSort()) < Integer.parseInt(o2.getSort()) ? -1 : 0;
    }

    @Override // java.lang.Comparable
    public int compareTo(QuestionDetailBean o2) {
        return sort(o2);
    }
}
