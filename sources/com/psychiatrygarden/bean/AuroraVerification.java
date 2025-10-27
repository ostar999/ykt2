package com.psychiatrygarden.bean;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class AuroraVerification implements Serializable {
    private String app_id;
    private String app_type;
    private String user_id;
    private boolean QuestionLibraryMessage = false;
    private boolean EmpiricalMessages = false;

    public String getApp_id() {
        return this.app_id;
    }

    public String getApp_type() {
        return this.app_type;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public boolean isEmpiricalMessages() {
        return this.EmpiricalMessages;
    }

    public boolean isQuestionLibraryMessage() {
        return this.QuestionLibraryMessage;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public void setApp_type(String app_type) {
        this.app_type = app_type;
    }

    public void setEmpiricalMessages(boolean empiricalMessages) {
        this.EmpiricalMessages = empiricalMessages;
    }

    public void setQuestionLibraryMessage(boolean questionLibraryMessage) {
        this.QuestionLibraryMessage = questionLibraryMessage;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
