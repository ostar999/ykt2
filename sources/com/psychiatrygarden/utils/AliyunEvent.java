package com.psychiatrygarden.utils;

/* loaded from: classes6.dex */
public enum AliyunEvent {
    ErrorExport("ErrorExport", "错题导出"),
    CollectionExport("CollectionExport", "收藏导出"),
    NoteExport("NoteExport", "笔记导出"),
    VisitColumn("VisitColumn", "访问栏目"),
    VisitModule("VisitModule", "访问模块"),
    VisitQuestion("VisitQuestion", "浏览试题"),
    RedoAnswer_Chapter("RedoAnswer_Chapter", "重做（章节）"),
    SubmitAnswer("SubmitAnswer", "提交答案"),
    AddCutQuestion("AddCutQuestion", "斩题"),
    DelCutQuestion("DelCutQuestion", "取消斩题"),
    CutQuestionMoveBack("CutQuestionMoveBack", "批量取消斩题"),
    SelectTag("SelectTag", "标签选择"),
    AddNote("AddNote", "发布笔记"),
    EditNote("EditNote", "编辑笔记"),
    DeleNote("DeleNote", "删除笔记"),
    CollectionQuestion("CollectionQuestion", "收藏试题"),
    CancelCollectionQuestion("CancelCollectionQuestion", "取消收藏试题"),
    PublishComment("PublishComment", "发布评论"),
    DeleComment("DeleComment", "删除评论"),
    EditComment("EditComment", "编辑评论"),
    AddPraise("AddPraise", "评论点赞"),
    CancelPraise("CancelPraise", "评论取消点赞"),
    AddOppose("AddOppose", "评论反对"),
    CancelOppose("CancelOppose", "评论取消反对"),
    CopyComment("CopyComment", "复制评论"),
    BehaviorReport("BehaviorReport", "举报评论"),
    MakePaper("MakePaper", "自主组题"),
    DelePaper("DelePaper", "删除组题"),
    ErrorQuestionSetting("ErrorQuestionSetting", "错题设置"),
    SearchQuestion("SearchQuestion", "试题搜索"),
    CheckUniversities("CheckUniversities", "查院校"),
    CheckMajor("CheckMajor", "查专业"),
    PostgraduateCalendar("PostgraduateCalendar", "考研日历"),
    SelectSchool("SelectSchool", "智能择校"),
    Means("Means", "资讯"),
    HomePageBannerArea("HomePageBannerArea", "首页金刚区"),
    AddSchool("AddSchool", "添加目标院校"),
    DeleSchool("DeleSchool", "移除目标院校"),
    FollowedMajor("FollowedMajor", "关注专业"),
    CancelMajor("CancelMajor", "取消关注专业"),
    LookRecruit("LookRecruit", "查看简章"),
    FollowedInstitutions("FollowedInstitutions", "关注院校"),
    CancelFolloweSchool("CancelFolloweSchool", "取消关注院校"),
    CheckTheOfferedMajors("CheckTheOfferedMajors", "查看开设专业"),
    PreviewMajor("PreviewMajor", "预览专业"),
    UniversityBannerArea("UniversityBannerArea", "院校金刚区"),
    PreviewUniversities("PreviewUniversities", "预览院校"),
    LookSchoolDetail("LookSchoolDetail", "查看院校简介"),
    ShareSchool("ShareSchool", "分享院校"),
    EnterpriseWeChatFeedback("EnterpriseWeChatFeedback", "企微反馈"),
    ShareMajor("ShareMajor", "分享专业");

    private final String key;
    private final String value;

    AliyunEvent(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static AliyunEvent fromKey(String key) {
        for (AliyunEvent aliyunEvent : values()) {
            if (aliyunEvent.key.equals(key)) {
                return aliyunEvent;
            }
        }
        throw new IllegalArgumentException("未知 key: " + key);
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}
