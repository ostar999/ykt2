package com.ykb.ebook.model;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.ykb.ebook.model.AnalysisBean;
import com.ykb.ebook.model.BiaoqianBeab;
import com.ykb.ebook.model.QuestionStatDataBean;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes7.dex */
public class QuestionDetailBean implements Serializable, Comparable<QuestionDetailBean> {
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
    private String old_app_id;
    private String option;
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
    private List<VideoListDTO> stem_audio_list = new ArrayList();
    private List<VideoListDTO> stem_video_list = new ArrayList();
    private List<VideoListDTO> video_list = new ArrayList();
    private String sort = "";
    private int actualStemIndex = -1;
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

            public void setExample(String str) {
                this.example = str;
            }

            public void setExample_pronunciation(String str) {
                this.example_pronunciation = str;
            }

            public void setExample_translate(String str) {
                this.example_translate = str;
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

            public void setMemory(String str) {
                this.memory = str;
            }

            public void setMemory_label(String str) {
                this.memory_label = str;
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

            public void setPronunciation(String str) {
                this.pronunciation = str;
            }

            public void setSymbols(String str) {
                this.symbols = str;
            }

            public void setTitle(String str) {
                this.title = str;
            }

            public void setTranslate(List<String> list) {
                this.translate = list;
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

        public void setExample(List<ExampleDTO> list) {
            this.example = list;
        }

        public void setMemory(List<MemoryDTO> list) {
            this.memory = list;
        }

        public void setWord(WordBean wordBean) {
            this.word = wordBean;
        }
    }

    public static class OptionDTO implements Serializable {
        private String img;
        private String key;
        private String title;
        private String type = "";

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

        public void setImg(String str) {
            this.img = str;
        }

        public void setKey(String str) {
            this.key = str;
        }

        public void setTitle(String str) {
            this.title = str;
        }

        public void setType(String str) {
            this.type = str;
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

        public void setNumber(String str) {
            this.number = str;
        }

        public void setQuestion_id(String str) {
            this.question_id = str;
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

        public void setThumb(String str) {
            this.thumb = str;
        }

        public void setType(String str) {
            this.type = str;
        }

        public void setVideo_id(String str) {
            this.video_id = str;
        }
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

    public EnglishLableBean getEnglish_label() {
        Object obj = this.english_label;
        if (((obj instanceof String) && TextUtils.isEmpty(obj.toString())) || (this.english_label instanceof ArrayList)) {
            return null;
        }
        return (EnglishLableBean) new Gson().fromJson(new Gson().toJson(this.english_label), EnglishLableBean.class);
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

    public String getOld_app_id() {
        return this.old_app_id;
    }

    public String getOption() {
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

    public void setOld_app_id(String str) {
        this.old_app_id = str;
    }

    public void setOption(String str) {
        this.option = str;
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

    public void setRelation(List<RelationDTO> list) {
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

    public void setStem_audio_list(List<VideoListDTO> list) {
        this.stem_audio_list = list;
    }

    public void setStem_video_list(List<VideoListDTO> list) {
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

    public void setVideo_list(List<VideoListDTO> list) {
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

    public int sort(QuestionDetailBean questionDetailBean) {
        if (Integer.parseInt(getSort()) > Integer.parseInt(questionDetailBean.getSort())) {
            return 1;
        }
        return Integer.parseInt(getSort()) < Integer.parseInt(questionDetailBean.getSort()) ? -1 : 0;
    }

    @Override // java.lang.Comparable
    public int compareTo(QuestionDetailBean questionDetailBean) {
        return sort(questionDetailBean);
    }
}
