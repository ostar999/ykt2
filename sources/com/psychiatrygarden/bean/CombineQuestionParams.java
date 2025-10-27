package com.psychiatrygarden.bean;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class CombineQuestionParams {
    private String count;
    private String mode;
    private String source;
    private String title;
    private List<String> subjects = new ArrayList();
    private List<String> years = new ArrayList();
    private List<String> types = new ArrayList();

    public String getCount() {
        return this.count;
    }

    public String getMode() {
        return this.mode;
    }

    public String getSource() {
        return this.source;
    }

    public List<String> getSubjects() {
        return this.subjects;
    }

    public String getTitle() {
        return this.title;
    }

    public List<String> getTypes() {
        return this.types;
    }

    public List<String> getYears() {
        return this.years;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public void setYears(List<String> years) {
        this.years = years;
    }
}
