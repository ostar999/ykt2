package com.psychiatrygarden.activity.comment.bean;

import com.psychiatrygarden.http.NetworkRequestsURL;

/* loaded from: classes5.dex */
public enum DiscussStatus {
    MyComments(0, "我的评论", NetworkRequestsURL.mCommentList),
    MyPraise(1, "我的点赞", NetworkRequestsURL.mCommentList),
    QuestionBankComments(2, "题库评论", NetworkRequestsURL.listWithRuleApi),
    ExperienceReview(3, "经验评论", NetworkRequestsURL.mCommentList),
    ForumComments(4, "论坛评论", NetworkRequestsURL.mCommentList),
    IPostAQuestionBankComment(5, "发表题库评论", NetworkRequestsURL.mYComment),
    IPostForumComments(6, "发表论坛评论", NetworkRequestsURL.mYComment),
    ICommentOnMyExperience(7, "发表经验评论", NetworkRequestsURL.mYComment),
    CourseReview(8, "课程评论", NetworkRequestsURL.mCommentList),
    CommentOnMy(9, "评论我的|搜索评论点击", NetworkRequestsURL.singleCommentFloorApi),
    CollectionOfReplies(10, "回复合集", NetworkRequestsURL.mGetCommentReplyUrl),
    CommentsOnTheEaluationModelTestQuestionnaire(11, "估分模考题单课程详情评论", NetworkRequestsURL.mCommentList),
    CommentsOnTheEaluationModelTest(12, "估分模考题评论", NetworkRequestsURL.mCommentList),
    LessonList(13, "课单评论", NetworkRequestsURL.mCommentList),
    QuestionBankVideo(14, "题库视频评论", NetworkRequestsURL.mCommentList),
    Recomend(15, "热门评论", NetworkRequestsURL.commenthotApi);

    private int code;
    private String name;
    private String url;

    DiscussStatus(int code, String name, String url) {
        this.code = code;
        this.name = name;
        this.url = url;
    }

    public int getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public String getUrl() {
        return this.url;
    }
}
