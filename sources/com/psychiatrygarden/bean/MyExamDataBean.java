package com.psychiatrygarden.bean;

import android.text.TextUtils;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import java.text.SimpleDateFormat;
import java.util.Locale;

/* loaded from: classes5.dex */
public class MyExamDataBean implements MultiItemEntity {
    private int answer_number;
    private int answer_time;
    private int app_id;
    private String created_at;
    private String description;
    private String endTimeShow;
    private String ent_time;
    private String exam_number;
    private String exam_qr_code;
    private String hospital;
    private int hospital_id;
    private int id;
    private int out_answer_number;
    private String participants;
    private int pass_score;
    private String question_file;
    private int score;
    private long serverTime;
    private String startTimeShow;
    private String start_time;
    private int status;
    private int test_paper_id;
    private int time;
    private String title;
    private String updated_at;
    private String user_group;
    private long startTimeStamp = 0;
    private long remainTimeStamp = 0;

    private long calcStartTimeStamp() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        if (TextUtils.isEmpty(this.start_time)) {
            return 0L;
        }
        try {
            return simpleDateFormat.parse(this.start_time).getTime() - System.currentTimeMillis();
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0L;
        }
    }

    public int getAnswer_number() {
        return this.answer_number;
    }

    public int getAnswer_time() {
        return this.answer_time;
    }

    public int getApp_id() {
        return this.app_id;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public String getDescription() {
        return this.description;
    }

    public String getEndTimeShow() {
        return this.endTimeShow;
    }

    public String getEnt_time() {
        return this.ent_time;
    }

    public String getExam_number() {
        return this.exam_number;
    }

    public String getExam_qr_code() {
        return this.exam_qr_code;
    }

    public String getHospital() {
        return this.hospital;
    }

    public int getHospital_id() {
        return this.hospital_id;
    }

    public int getId() {
        return this.id;
    }

    @Override // com.chad.library.adapter.base.entity.MultiItemEntity
    public int getItemType() {
        return getStatus();
    }

    public int getOut_answer_number() {
        return this.out_answer_number;
    }

    public String getParticipants() {
        return this.participants;
    }

    public int getPass_score() {
        return this.pass_score;
    }

    public String getQuestion_file() {
        return this.question_file;
    }

    public long getRemainTimeStamp() {
        return this.remainTimeStamp;
    }

    public int getScore() {
        return this.score;
    }

    public long getServerTime() {
        return this.serverTime;
    }

    public String getStartTimeShow() {
        return this.startTimeShow;
    }

    public long getStartTimeStamp() {
        return this.startTimeStamp;
    }

    public String getStart_time() {
        return this.start_time;
    }

    public int getStatus() {
        return this.status;
    }

    public int getTest_paper_id() {
        return this.test_paper_id;
    }

    public int getTime() {
        return this.time;
    }

    public String getTitle() {
        return this.title;
    }

    public String getUpdated_at() {
        return this.updated_at;
    }

    public String getUser_group() {
        return this.user_group;
    }

    public void setAnswer_number(int answer_number) {
        this.answer_number = answer_number;
    }

    public void setAnswer_time(int answer_time) {
        this.answer_time = answer_time;
    }

    public void setApp_id(int app_id) {
        this.app_id = app_id;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEndTimeShow(String endTimeShow) {
        this.endTimeShow = endTimeShow;
    }

    public void setEnt_time(String ent_time) {
        this.ent_time = ent_time;
    }

    public void setExam_number(String exam_number) {
        this.exam_number = exam_number;
    }

    public void setExam_qr_code(String exam_qr_code) {
        this.exam_qr_code = exam_qr_code;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public void setHospital_id(int hospital_id) {
        this.hospital_id = hospital_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOut_answer_number(int out_answer_number) {
        this.out_answer_number = out_answer_number;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public void setPass_score(int pass_score) {
        this.pass_score = pass_score;
    }

    public void setQuestion_file(String question_file) {
        this.question_file = question_file;
    }

    public void setRemainTimeStamp(long remainTimeStamp) {
        this.remainTimeStamp = remainTimeStamp;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setServerTime(long serverTime) {
        this.serverTime = serverTime;
    }

    public void setStartTimeShow(String startTimeShow) {
        this.startTimeShow = startTimeShow;
    }

    public void setStartTimeStamp(long startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTest_paper_id(int test_paper_id) {
        this.test_paper_id = test_paper_id;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public void setUser_group(String user_group) {
        this.user_group = user_group;
    }
}
