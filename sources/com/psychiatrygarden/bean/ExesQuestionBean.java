package com.psychiatrygarden.bean;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class ExesQuestionBean implements Serializable, Comparable<ExesQuestionBean>, MultiItemEntity {
    public static final int TYPE_NORMAL = 2;
    public static final int TYPE_SHARE_STEM = 1;
    private int actualStemIndex;
    private String answer;
    private int currentStemIndex;
    private String exam_id;
    private String explain;
    private String explain_img;
    private boolean isChoosedAnswer;
    private String knowledge_id;
    private List<OptionBean> option;
    private String option_analysis;
    private String option_analysis_img;
    private String part;
    private String part_desc;
    private String part_title;
    private String public_number;
    private String public_title;

    @SerializedName(alternate = {"id"}, value = "question_id")
    private String question_id;
    private String question_num;
    private String restore;
    private String restore_img;
    private String show_number;
    private String title;
    private String title_img;
    private int tmpIndex;
    private String type;
    private String type_str;
    private String video_url;
    private int jumpPosition = -1;
    private String number = "0";
    private String score = "0";
    private String ownAns = "";
    private String isRight = "0";
    private long right_count = 0;
    private int ykb_id = 0;
    private long wrong_count = 0;
    private String comment_count = "0";
    private String outlines_pm = "";
    private String chapter_id = "";
    private String subject_id = "";
    private String media_img = "";

    @SerializedName(alternate = {"score_describe"}, value = "question_type")
    private String question_type = "";
    private int answer_mode = 0;
    private boolean isBiaoDoubt = false;
    private boolean isGroupLast = false;
    private String doDuration = "0";
    private long doEndDuration = 0;
    private long doStartDuration = 0;

    public static class OptionBean implements Serializable {
        private String img;
        private String is_right;
        private String key;
        private String questionType;
        private String title;
        private String type = "0";

        public String getImg() {
            return this.img;
        }

        public String getIs_right() {
            return this.is_right;
        }

        public String getKey() {
            return this.key;
        }

        public String getQuestionType() {
            return this.questionType;
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

        public void setIs_right(String is_right) {
            this.is_right = is_right;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public void setQuestionType(String questionType) {
            this.questionType = questionType;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public int getActualStemIndex() {
        return this.actualStemIndex;
    }

    public String getAnswer() {
        return this.answer;
    }

    public int getAnswer_mode() {
        return this.answer_mode;
    }

    public String getChapter_id() {
        return this.chapter_id;
    }

    public String getComment_count() {
        return this.comment_count;
    }

    public int getCurrentStemIndex() {
        return this.currentStemIndex;
    }

    public String getDoDuration() {
        return this.doDuration;
    }

    public long getDoEndDuration() {
        return this.doEndDuration;
    }

    public long getDoStartDuration() {
        return this.doStartDuration;
    }

    public String getExam_id() {
        return this.exam_id;
    }

    public String getExplain() {
        return this.explain;
    }

    public String getExplain_img() {
        return this.explain_img;
    }

    public String getIsRight() {
        return this.isRight;
    }

    @Override // com.chad.library.adapter.base.entity.MultiItemEntity
    public int getItemType() {
        return !TextUtils.isEmpty(this.public_number) ? 1 : 2;
    }

    public int getJumpPosition() {
        return this.jumpPosition;
    }

    public String getKnowledge_id() {
        return this.knowledge_id;
    }

    public String getMedia_img() {
        return this.media_img;
    }

    public String getNumber() {
        return this.number;
    }

    public List<OptionBean> getOption() {
        return this.option;
    }

    public String getOption_analysis() {
        return this.option_analysis;
    }

    public String getOption_analysis_img() {
        return this.option_analysis_img;
    }

    public String getOutlines_pm() {
        return this.outlines_pm;
    }

    public String getOwnAns() {
        return this.ownAns;
    }

    public String getPart() {
        return this.part;
    }

    public String getPart_desc() {
        return this.part_desc;
    }

    public String getPart_title() {
        return this.part_title;
    }

    public String getPublic_number() {
        return this.public_number;
    }

    public String getPublic_title() {
        String str = this.public_title;
        return str == null ? "" : str;
    }

    public String getQuestion_id() {
        return this.question_id;
    }

    public String getQuestion_num() {
        return this.question_num;
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

    public long getRight_count() {
        return this.right_count;
    }

    public String getScore() {
        return this.score;
    }

    public String getShow_number() {
        return this.show_number;
    }

    public String getSubject_id() {
        return this.subject_id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getTitle_img() {
        return this.title_img;
    }

    public int getTmpIndex() {
        return this.tmpIndex;
    }

    public String getType() {
        return this.type;
    }

    public String getType_str() {
        return this.type_str;
    }

    public String getVideo_url() {
        return this.video_url;
    }

    public long getWrong_count() {
        return this.wrong_count;
    }

    public boolean isBiaoDoubt() {
        return this.isBiaoDoubt;
    }

    public boolean isChoosedAnswer() {
        return this.isChoosedAnswer;
    }

    public boolean isGroupLast() {
        return this.isGroupLast;
    }

    public void setActualStemIndex(int actualStemIndex) {
        this.actualStemIndex = actualStemIndex;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setAnswer_mode(int answer_mode) {
        this.answer_mode = answer_mode;
    }

    public void setBiaoDoubt(boolean biaoDoubt) {
        this.isBiaoDoubt = biaoDoubt;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public void setChoosedAnswer(boolean choosedAnswer) {
        this.isChoosedAnswer = choosedAnswer;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public void setCurrentStemIndex(int currentStemIndex) {
        this.currentStemIndex = currentStemIndex;
    }

    public void setDoDuration(String doDuration) {
        this.doDuration = doDuration;
    }

    public void setDoEndDuration(long doEndDuration) {
        this.doEndDuration = doEndDuration;
    }

    public void setDoStartDuration(long doStartDuration) {
        this.doStartDuration = doStartDuration;
    }

    public void setExam_id(String exam_id) {
        this.exam_id = exam_id;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public void setExplain_img(String explain_img) {
        this.explain_img = explain_img;
    }

    public void setGroupLast(boolean groupLast) {
        this.isGroupLast = groupLast;
    }

    public void setIsRight(String isRight) {
        this.isRight = isRight;
    }

    public void setJumpPosition(int jumpPosition) {
        this.jumpPosition = jumpPosition;
    }

    public void setKnowledge_id(String knowledge_id) {
        this.knowledge_id = knowledge_id;
    }

    public void setMedia_img(String media_img) {
        this.media_img = media_img;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setOption(List<OptionBean> option) {
        this.option = option;
    }

    public void setOption_analysis(String option_analysis) {
        this.option_analysis = option_analysis;
    }

    public void setOption_analysis_img(String option_analysis_img) {
        this.option_analysis_img = option_analysis_img;
    }

    public void setOutlines_pm(String outlines_pm) {
        this.outlines_pm = outlines_pm;
    }

    public void setOwnAns(String ownAns) {
        this.ownAns = ownAns;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public void setPart_desc(String part_desc) {
        this.part_desc = part_desc;
    }

    public void setPart_title(String part_title) {
        this.part_title = part_title;
    }

    public void setPublic_number(String public_number) {
        this.public_number = public_number;
    }

    public void setPublic_title(String public_title) {
        this.public_title = public_title;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public void setQuestion_num(String question_num) {
        this.question_num = question_num;
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

    public void setRight_count(long right_count) {
        this.right_count = right_count;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setShow_number(String show_number) {
        this.show_number = show_number;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitle_img(String title_img) {
        this.title_img = title_img;
    }

    public void setTmpIndex(int tmpIndex) {
        this.tmpIndex = tmpIndex;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setType_str(String type_str) {
        this.type_str = type_str;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public void setWrong_count(long wrong_count) {
        this.wrong_count = wrong_count;
    }

    public int sort(ExesQuestionBean o2) {
        return Integer.compare(Integer.parseInt(getNumber()), Integer.parseInt(o2.getNumber()));
    }

    @Override // java.lang.Comparable
    public int compareTo(@NonNull ExesQuestionBean o2) {
        return sort(o2);
    }
}
