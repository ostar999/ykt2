package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class UserOwnerBean implements Serializable {
    private String code;
    private List<DataBean> data;
    private String message;
    private String server_time;

    public static class DataBean implements Serializable {
        private String id;
        private String title;
        private List<ChildBean> child = new ArrayList();
        private List<String> obj_id = new ArrayList();
        private String count = "0";
        private String scroe = "0";
        private String worngScore = "0";

        public static class ChildBean implements Serializable {
            private String id;
            private List<ThreadDati> mThreadDati;
            private Double score;
            private String title;
            private Double wrongscroe;
            private List<String> obj_id = new ArrayList();
            private String count = "0";

            public String getCount() {
                return this.count;
            }

            public String getId() {
                return this.id;
            }

            public List<String> getObj_id() {
                return this.obj_id;
            }

            public Double getScore() {
                return this.score;
            }

            public String getTitle() {
                return this.title;
            }

            public Double getWrongscroe() {
                return this.wrongscroe;
            }

            public List<ThreadDati> getmThreadDati() {
                return this.mThreadDati;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setObj_id(List<String> obj_id) {
                this.obj_id = obj_id;
            }

            public void setScore(Double score) {
                this.score = score;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setWrongscroe(Double wrongscroe) {
                this.wrongscroe = wrongscroe;
            }

            public void setmThreadDati(List<ThreadDati> mThreadDati) {
                this.mThreadDati = mThreadDati;
            }
        }

        public static class ThreadDati implements Serializable {
            private String number_number;
            private String obj;
            private String scoretotal;
            private String unit;
            private String wrongscore;

            public String getNumber_number() {
                return this.number_number;
            }

            public String getObj() {
                return this.obj;
            }

            public String getScoretotal() {
                return this.scoretotal;
            }

            public String getUnit() {
                return this.unit;
            }

            public String getWrongscore() {
                return this.wrongscore;
            }

            public void setNumber_number(String number_number) {
                this.number_number = number_number;
            }

            public void setObj(String obj) {
                this.obj = obj;
            }

            public void setScoretotal(String scoretotal) {
                this.scoretotal = scoretotal;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public void setWrongscore(String wrongscore) {
                this.wrongscore = wrongscore;
            }
        }

        public List<ChildBean> getChild() {
            return this.child;
        }

        public String getCount() {
            return this.count;
        }

        public String getId() {
            return this.id;
        }

        public List<String> getObj_id() {
            return this.obj_id;
        }

        public String getScroe() {
            return this.scroe;
        }

        public String getTitle() {
            return this.title;
        }

        public String getWorngScore() {
            return this.worngScore;
        }

        public void setChild(List<ChildBean> child) {
            this.child = child;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setObj_id(List<String> obj_id) {
            this.obj_id = obj_id;
        }

        public void setScroe(String scroe) {
            this.scroe = scroe;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setWorngScore(String worngScore) {
            this.worngScore = worngScore;
        }
    }

    public String getCode() {
        return this.code;
    }

    public List<DataBean> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public String getServer_time() {
        return this.server_time;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServer_time(String server_time) {
        this.server_time = server_time;
    }
}
