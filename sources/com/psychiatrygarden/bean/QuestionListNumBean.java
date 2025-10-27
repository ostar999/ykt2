package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class QuestionListNumBean implements Comparable<QuestionListNumBean> {
    public int numpage;
    public long question_id;

    public int getNumpage() {
        return this.numpage;
    }

    public long getQuestion_id() {
        return this.question_id;
    }

    public QuestionListNumBean setNumpage(int numpage) {
        this.numpage = numpage;
        return this;
    }

    public QuestionListNumBean setQuestion_id(long question_id) {
        this.question_id = question_id;
        return this;
    }

    public int sort(QuestionListNumBean o2) {
        if (this.numpage > o2.getNumpage()) {
            return 1;
        }
        return this.numpage < o2.getNumpage() ? -1 : 0;
    }

    @Override // java.lang.Comparable
    public int compareTo(QuestionListNumBean o2) {
        return sort(o2);
    }
}
