package com.psychiatrygarden.activity.online.bean;

import com.google.gson.annotations.SerializedName;
import com.psychiatrygarden.utils.Constants;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class SearchHeaderBean implements Serializable {

    @SerializedName("code")
    private String code;

    @SerializedName("data")
    private DataDTO data;

    @SerializedName("message")
    private String message;

    @SerializedName("server_time")
    private String serverTime;

    public static class DataDTO implements Serializable {

        @SerializedName("qsBank")
        private List<QsBankDTO> qsBank;

        @SerializedName("questionType")
        private List<QsBankDTO> questionType;

        @SerializedName("sortType")
        private List<QsBankDTO> sortType;

        public static class QsBankDTO implements Serializable {

            @SerializedName("children")
            private List<QsBankDTO> children;

            @SerializedName("have")
            private String have;

            @SerializedName("identity_id")
            private String identityId;

            @SerializedName(Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_LABEL)
            private String label;

            @SerializedName("parent_id")
            private String parentId;

            @SerializedName("value")
            private String value;

            public List<QsBankDTO> getChildren() {
                return this.children;
            }

            public String getHave() {
                return this.have;
            }

            public String getIdentityId() {
                return this.identityId;
            }

            public String getLabel() {
                return this.label;
            }

            public String getParentId() {
                return this.parentId;
            }

            public String getValue() {
                return this.value;
            }

            public void setChildren(List<QsBankDTO> children) {
                this.children = children;
            }

            public void setHave(String have) {
                this.have = have;
            }

            public void setIdentityId(String identityId) {
                this.identityId = identityId;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public List<QsBankDTO> getQsBank() {
            return this.qsBank;
        }

        public List<QsBankDTO> getQuestionType() {
            return this.questionType;
        }

        public List<QsBankDTO> getSortType() {
            return this.sortType;
        }

        public void setQsBank(List<QsBankDTO> qsBank) {
            this.qsBank = qsBank;
        }

        public void setQuestionType(List<QsBankDTO> questionType) {
            this.questionType = questionType;
        }

        public void setSortType(List<QsBankDTO> sortType) {
            this.sortType = sortType;
        }
    }

    public String getCode() {
        return this.code;
    }

    public DataDTO getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public String getServerTime() {
        return this.serverTime;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }
}
