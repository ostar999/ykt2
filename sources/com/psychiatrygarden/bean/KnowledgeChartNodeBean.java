package com.psychiatrygarden.bean;

import android.text.TextUtils;
import cn.hutool.core.lang.RegexPool;
import com.chad.library.adapter.base.entity.JSectionEntity;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class KnowledgeChartNodeBean extends JSectionEntity implements Serializable {
    private String activity_id;
    private String chapterId;
    private boolean childSelect;
    private List<KnowledgeChartNodeBean> children;
    private boolean continueDo;
    private String describe;
    private String has_permission;
    private String have;
    private String id;
    private String img;
    private boolean initChildren;
    private String knowledge_count;
    private String label;
    private int level;
    private String name;
    private String parentId;
    private String parentTitle;
    private String pass;
    private String question_count;
    private String question_total;
    private String recommend_course_id;
    private String recommend_image;
    private String right_rate;
    private boolean selectAll;
    private String type;
    private String user_answer_total;
    private boolean expand = true;
    private boolean isSelect = false;
    private boolean isLast = false;

    public String getActivity_id() {
        return this.activity_id;
    }

    public String getChapterId() {
        return this.chapterId;
    }

    public List<KnowledgeChartNodeBean> getChildren() {
        return this.children;
    }

    public String getDescribe() {
        return this.describe;
    }

    public String getHas_permission() {
        return this.has_permission;
    }

    public String getHave() {
        return this.have;
    }

    public String getId() {
        return this.id;
    }

    public String getImg() {
        return this.img;
    }

    public String getKnowledge_count() {
        return (TextUtils.isEmpty(this.knowledge_count) || !this.knowledge_count.matches(RegexPool.NUMBERS)) ? "" : this.knowledge_count;
    }

    public String getLabel() {
        return this.label;
    }

    public int getLevel() {
        return this.level;
    }

    public String getName() {
        return this.name;
    }

    public String getParentId() {
        return this.parentId;
    }

    public String getParentTitle() {
        return this.parentTitle;
    }

    public String getPass() {
        return this.pass;
    }

    public String getQuestion_count() {
        return this.question_count;
    }

    public String getQuestion_total() {
        return this.question_total;
    }

    public String getRecommend_course_id() {
        return this.recommend_course_id;
    }

    public String getRecommend_image() {
        return this.recommend_image;
    }

    public String getRight_rate() {
        return this.right_rate;
    }

    public String getType() {
        return this.type;
    }

    public String getUser_answer_total() {
        return this.user_answer_total;
    }

    public boolean isChildSelect() {
        return this.childSelect;
    }

    public boolean isContinueDo() {
        return this.continueDo;
    }

    public boolean isExpand() {
        return this.expand;
    }

    @Override // com.chad.library.adapter.base.entity.SectionEntity
    public boolean isHeader() {
        List<KnowledgeChartNodeBean> list = this.children;
        return (list == null || list.isEmpty()) ? false : true;
    }

    public boolean isInitChildren() {
        return this.initChildren;
    }

    public boolean isLast() {
        return this.isLast;
    }

    public boolean isSelect() {
        return this.isSelect;
    }

    public boolean isSelectAll() {
        return this.selectAll;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public void setChildSelect(boolean childSelect) {
        this.childSelect = childSelect;
    }

    public void setChildren(List<KnowledgeChartNodeBean> children) {
        this.children = children;
    }

    public void setContinueDo(boolean continueDo) {
        this.continueDo = continueDo;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public void setHas_permission(String has_permission) {
        this.has_permission = has_permission;
    }

    public void setHave(String have) {
        this.have = have;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setInitChildren(boolean initChildren) {
        this.initChildren = initChildren;
    }

    public void setKnowledge_count(String knowledge_count) {
        this.knowledge_count = knowledge_count;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setLast(boolean last) {
        this.isLast = last;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setParentTitle(String parentTitle) {
        this.parentTitle = parentTitle;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setQuestion_count(String question_count) {
        this.question_count = question_count;
    }

    public void setQuestion_total(String question_total) {
        this.question_total = question_total;
    }

    public void setRecommend_course_id(String recommend_course_id) {
        this.recommend_course_id = recommend_course_id;
    }

    public void setRecommend_image(String recommend_image) {
        this.recommend_image = recommend_image;
    }

    public void setRight_rate(String right_rate) {
        this.right_rate = right_rate;
    }

    public void setSelect(boolean select) {
        this.isSelect = select;
    }

    public void setSelectAll(boolean selectAll) {
        this.selectAll = selectAll;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUser_answer_total(String user_answer_total) {
        this.user_answer_total = user_answer_total;
    }
}
