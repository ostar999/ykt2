package com.plv.livescenes.model.answer;

import com.plv.livescenes.model.PLVInteractiveCallbackVO;
import java.util.List;

/* loaded from: classes5.dex */
public class PLVQuestionnaireSocketVO {
    private String EVENT = PLVInteractiveCallbackVO.EVENT_QUESTIONNAIRE;
    private List<AnswerBean> answer;
    private String nick;
    private String questionnaireId;
    private String roomId;
    private String userId;

    public static class AnswerBean {
        private String answer;
        private String questionId;

        public AnswerBean(String str, String str2) {
            this.questionId = str;
            this.answer = str2;
        }

        public String getAnswer() {
            return this.answer;
        }

        public String getQuestionId() {
            return this.questionId;
        }

        public void setAnswer(String str) {
            this.answer = str;
        }

        public void setQuestionId(String str) {
            this.questionId = str;
        }
    }

    public PLVQuestionnaireSocketVO(String str, List<AnswerBean> list) {
        this.questionnaireId = str;
        this.answer = list;
    }

    public List<AnswerBean> getAnswer() {
        return this.answer;
    }

    public String getEVENT() {
        return this.EVENT;
    }

    public String getNick() {
        return this.nick;
    }

    public String getQuestionnaireId() {
        return this.questionnaireId;
    }

    public String getRoomId() {
        return this.roomId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setAnswer(List<AnswerBean> list) {
        this.answer = list;
    }

    public void setNick(String str) {
        this.nick = str;
    }

    public void setQuestionnaireId(String str) {
        this.questionnaireId = str;
    }

    public void setRoomId(String str) {
        this.roomId = str;
    }

    public void setUserId(String str) {
        this.userId = str;
    }
}
