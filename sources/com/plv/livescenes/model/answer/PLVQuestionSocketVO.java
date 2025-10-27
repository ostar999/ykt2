package com.plv.livescenes.model.answer;

import com.plv.business.model.PLVBaseVO;

/* loaded from: classes5.dex */
public class PLVQuestionSocketVO implements PLVBaseVO {
    private String EVENT = "ANSWER_TEST_QUESTION";
    private String nick;
    private String option;
    private String questionId;
    private String roomId;
    private String userId;

    public PLVQuestionSocketVO(String str, String str2, String str3, String str4, String str5) {
        this.option = str;
        this.nick = str2;
        this.questionId = str3;
        this.roomId = str4;
        this.userId = str5;
    }
}
