package com.psychiatrygarden.activity.forum.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class GiveLogBean implements Serializable {
    public String code;
    public DataBean data;
    public String message;

    public static final class DataBean implements Serializable {
        public String due_date;
        public List<DataChildlistBean> list = new ArrayList();
        public String today_give_day;
        public String total_give_day;

        public static final class DataChildlistBean implements Serializable, MultiItemEntity {
            public String count;
            public List<LogBean> logs = new ArrayList();
            public String month;

            public static final class LogBean implements Serializable {
                public String give_day;
                public String time;
                public String tip;

                public String getGive_day() {
                    return this.give_day;
                }

                public String getTime() {
                    return this.time;
                }

                public String getTip() {
                    return this.tip;
                }

                public void setGive_day(String give_day) {
                    this.give_day = give_day;
                }

                public void setTime(String time) {
                    this.time = time;
                }

                public void setTip(String tip) {
                    this.tip = tip;
                }
            }

            public String getCount() {
                return this.count;
            }

            @Override // com.chad.library.adapter.base.entity.MultiItemEntity
            public int getItemType() {
                return 0;
            }

            public List<LogBean> getLogs() {
                return this.logs;
            }

            public String getMonth() {
                return this.month;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public void setLogs(List<LogBean> logs) {
                this.logs = logs;
            }

            public void setMonth(String month) {
                this.month = month;
            }
        }

        public String getDue_date() {
            return this.due_date;
        }

        public List<DataChildlistBean> getList() {
            return this.list;
        }

        public String getToday_give_day() {
            return this.today_give_day;
        }

        public String getTotal_give_day() {
            return this.total_give_day;
        }

        public void setDue_date(String due_date) {
            this.due_date = due_date;
        }

        public void setList(List<DataChildlistBean> list) {
            this.list = list;
        }

        public void setToday_give_day(String today_give_day) {
            this.today_give_day = today_give_day;
        }

        public void setTotal_give_day(String total_give_day) {
            this.total_give_day = total_give_day;
        }
    }
}
