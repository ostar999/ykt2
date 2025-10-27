package com.plv.livescenes.model.answer;

import com.plv.business.model.PLVBaseVO;
import java.util.List;

/* loaded from: classes5.dex */
public class PLVQuestionResultVO implements PLVBaseVO {
    private String EVENT;
    private ContentBean content;
    private String questionId;
    private ResultBean result;

    public static class ContentBean {
        private String answer;
        private String channelId;
        private String name;
        private String option1;
        private String option2;
        private String questionId;
        private String tips1;
        private String tips2;
        private String type;

        public String getAnswer() {
            return this.answer;
        }

        public String getChannelId() {
            return this.channelId;
        }

        public String getName() {
            return this.name;
        }

        public String getOption1() {
            return this.option1;
        }

        public String getOption2() {
            return this.option2;
        }

        public String getQuestionId() {
            return this.questionId;
        }

        public String getTips1() {
            return this.tips1;
        }

        public String getTips2() {
            return this.tips2;
        }

        public String getType() {
            return this.type;
        }

        public void setAnswer(String str) {
            this.answer = str;
        }

        public void setChannelId(String str) {
            this.channelId = str;
        }

        public void setName(String str) {
            this.name = str;
        }

        public void setOption1(String str) {
            this.option1 = str;
        }

        public void setOption2(String str) {
            this.option2 = str;
        }

        public void setQuestionId(String str) {
            this.questionId = str;
        }

        public void setTips1(String str) {
            this.tips1 = str;
        }

        public void setTips2(String str) {
            this.tips2 = str;
        }

        public void setType(String str) {
            this.type = str;
        }
    }

    public static class ResultBean {
        private String answer;
        private List<?> faultUser;
        private List<?> rightUser;
        private List<Integer> singleResult;
        private int total;
        private String type;

        public String getAnswer() {
            return this.answer;
        }

        public List<?> getFaultUser() {
            return this.faultUser;
        }

        public List<?> getRightUser() {
            return this.rightUser;
        }

        public List<Integer> getSingleResult() {
            return this.singleResult;
        }

        public int getTotal() {
            return this.total;
        }

        public String getType() {
            return this.type;
        }

        public void setAnswer(String str) {
            this.answer = str;
        }

        public void setFaultUser(List<?> list) {
            this.faultUser = list;
        }

        public void setRightUser(List<?> list) {
            this.rightUser = list;
        }

        public void setSingleResult(List<Integer> list) {
            this.singleResult = list;
        }

        public void setTotal(int i2) {
            this.total = i2;
        }

        public void setType(String str) {
            this.type = str;
        }
    }

    public ContentBean getContent() {
        return this.content;
    }

    public String getEVENT() {
        return this.EVENT;
    }

    public String getQuestionId() {
        return this.questionId;
    }

    public ResultBean getResult() {
        return this.result;
    }

    public void setContent(ContentBean contentBean) {
        this.content = contentBean;
    }

    public void setEVENT(String str) {
        this.EVENT = str;
    }

    public void setQuestionId(String str) {
        this.questionId = str;
    }

    public void setResult(ResultBean resultBean) {
        this.result = resultBean;
    }
}
