package com.psychiatrygarden.activity.comment.bean;

import com.google.gson.annotations.SerializedName;
import com.psychiatrygarden.db.SQLHelper;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.Constants;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class DiscussRuleBean implements Serializable {

    @SerializedName("code")
    private String code;

    @SerializedName("data")
    private DataDTO data;

    @SerializedName("message")
    private String message;

    @SerializedName("server_time")
    private String serverTime;

    public static class DataDTO implements Serializable {

        @SerializedName("labels")
        private List<LabelsDTO> labels;

        @SerializedName(CommonParameter.page_title)
        private String pageTitle;

        @SerializedName(SQLHelper.SELECTED)
        private String selected;

        public static class LabelsDTO implements Serializable {

            @SerializedName("description")
            private String description;

            @SerializedName(Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_LABEL)
            private String label;

            @SerializedName("value")
            private String value;

            public String getDescription() {
                return this.description;
            }

            public String getLabel() {
                return this.label;
            }

            public String getValue() {
                return this.value;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public List<LabelsDTO> getLabels() {
            return this.labels;
        }

        public String getPageTitle() {
            return this.pageTitle;
        }

        public String getSelected() {
            return this.selected;
        }

        public void setLabels(List<LabelsDTO> labels) {
            this.labels = labels;
        }

        public void setPageTitle(String pageTitle) {
            this.pageTitle = pageTitle;
        }

        public void setSelected(String selected) {
            this.selected = selected;
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
