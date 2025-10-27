package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class WeekOrMonthReportBean {
    private String code;
    private WeekOrMonthReportDataBean data;
    private String message;

    public class EstimatedScoreDataBean {
        private String all_score;
        private String estimated_score_title;
        private String estimated_score_trend;
        private String last_year_score;
        private String pass_score;
        private String report_img;
        private String title;
        private String today_score;
        private String trend;
        private String update_time;
        private String yesterday_score;

        public EstimatedScoreDataBean() {
        }

        public String getAll_score() {
            return this.all_score;
        }

        public String getEstimated_score_title() {
            return this.estimated_score_title;
        }

        public String getEstimated_score_trend() {
            return this.estimated_score_trend;
        }

        public String getLast_year_score() {
            return this.last_year_score;
        }

        public String getPass_score() {
            return this.pass_score;
        }

        public String getReport_img() {
            return this.report_img;
        }

        public String getTitle() {
            return this.title;
        }

        public String getToday_score() {
            return this.today_score;
        }

        public String getTrend() {
            return this.trend;
        }

        public String getUpdate_time() {
            return this.update_time;
        }

        public String getYesterday_score() {
            return this.yesterday_score;
        }

        public void setAll_score(String all_score) {
            this.all_score = all_score;
        }

        public void setEstimated_score_title(String estimated_score_title) {
            this.estimated_score_title = estimated_score_title;
        }

        public void setEstimated_score_trend(String estimated_score_trend) {
            this.estimated_score_trend = estimated_score_trend;
        }

        public void setLast_year_score(String last_year_score) {
            this.last_year_score = last_year_score;
        }

        public void setPass_score(String pass_score) {
            this.pass_score = pass_score;
        }

        public void setReport_img(String report_img) {
            this.report_img = report_img;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setToday_score(String today_score) {
            this.today_score = today_score;
        }

        public void setTrend(String trend) {
            this.trend = trend;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public void setYesterday_score(String yesterday_score) {
            this.yesterday_score = yesterday_score;
        }
    }

    public class ReportDataBean {
        private String all_duration;
        private String all_question_num;
        private String all_rank;
        private String all_right_rate;
        private String avg_duration;
        private String filter_desc;
        private String has_recommend_question;
        private String has_recommend_video;
        private String is_vip;
        private String node_title;
        private String own_rank;
        private String people_percent;
        private String right_rate;
        private String start_time;
        private String title;
        private String user_right_rate;

        public ReportDataBean() {
        }

        public String getAll_duration() {
            return this.all_duration;
        }

        public String getAll_question_num() {
            return this.all_question_num;
        }

        public String getAll_rank() {
            return this.all_rank;
        }

        public String getAll_right_rate() {
            return this.all_right_rate;
        }

        public String getAvg_duration() {
            return this.avg_duration;
        }

        public String getFilter_desc() {
            return this.filter_desc;
        }

        public String getHas_recommend_question() {
            return this.has_recommend_question;
        }

        public String getHas_recommend_video() {
            return this.has_recommend_video;
        }

        public String getIs_vip() {
            return this.is_vip;
        }

        public String getNode_title() {
            return this.node_title;
        }

        public String getOwn_rank() {
            return this.own_rank;
        }

        public String getPeople_percent() {
            return this.people_percent;
        }

        public String getRight_rate() {
            return this.right_rate;
        }

        public String getStart_time() {
            return this.start_time;
        }

        public String getTitle() {
            return this.title;
        }

        public String getUser_right_rate() {
            return this.user_right_rate;
        }

        public void setAll_duration(String all_duration) {
            this.all_duration = all_duration;
        }

        public void setAll_question_num(String all_question_num) {
            this.all_question_num = all_question_num;
        }

        public void setAll_rank(String all_rank) {
            this.all_rank = all_rank;
        }

        public void setAll_right_rate(String all_right_rate) {
            this.all_right_rate = all_right_rate;
        }

        public void setAvg_duration(String avg_duration) {
            this.avg_duration = avg_duration;
        }

        public void setFilter_desc(String filter_desc) {
            this.filter_desc = filter_desc;
        }

        public void setHas_recommend_question(String has_recommend_question) {
            this.has_recommend_question = has_recommend_question;
        }

        public void setHas_recommend_video(String has_recommend_video) {
            this.has_recommend_video = has_recommend_video;
        }

        public void setIs_vip(String is_vip) {
            this.is_vip = is_vip;
        }

        public void setNode_title(String node_title) {
            this.node_title = node_title;
        }

        public void setOwn_rank(String own_rank) {
            this.own_rank = own_rank;
        }

        public void setPeople_percent(String people_percent) {
            this.people_percent = people_percent;
        }

        public void setRight_rate(String right_rate) {
            this.right_rate = right_rate;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setUser_right_rate(String user_right_rate) {
            this.user_right_rate = user_right_rate;
        }
    }

    public class WeekOrMonthReportDataBean {
        private EstimatedScoreDataBean estimated_score_detail;
        private ReportDataBean question_total;

        public WeekOrMonthReportDataBean() {
        }

        public EstimatedScoreDataBean getEstimated_score_detail() {
            return this.estimated_score_detail;
        }

        public ReportDataBean getQuestion_total() {
            return this.question_total;
        }

        public void setEstimated_score_detail(EstimatedScoreDataBean estimated_score_detail) {
            this.estimated_score_detail = estimated_score_detail;
        }

        public void setQuestion_total(ReportDataBean question_total) {
            this.question_total = question_total;
        }
    }

    public String getCode() {
        return this.code;
    }

    public WeekOrMonthReportDataBean getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(WeekOrMonthReportDataBean data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
