package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class StudyPlanListBean {
    private String code;
    private DataBean data;
    private String message;
    private String server_time;
    private boolean success;

    public static class DataBean {
        private String desc;
        private PlanInProgressBean plan_in_progress;
        private String rule;
        private List<SystemPlanBean> system_plan;
        private String title;

        public static class PlanInProgressBean {
            private String complete_days;
            private String completion;
            private String days;
            private String description;
            private String id;
            private String name;
            private String start_time;
            private String status;
            private String type;

            public String getComplete_days() {
                return this.complete_days;
            }

            public String getCompletion() {
                return this.completion;
            }

            public String getDays() {
                return this.days;
            }

            public String getDescription() {
                return this.description;
            }

            public String getId() {
                return this.id;
            }

            public String getName() {
                return this.name;
            }

            public String getStart_time() {
                return this.start_time;
            }

            public String getStatus() {
                return this.status;
            }

            public String getType() {
                return this.type;
            }

            public void setComplete_days(String complete_days) {
                this.complete_days = complete_days;
            }

            public void setCompletion(String completion) {
                this.completion = completion;
            }

            public void setDays(String days) {
                this.days = days;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class SystemPlanBean {
            private String complete_days;
            private String completion;
            private String days;
            private String description;
            private boolean expanded;
            private String id;
            private ItemType item_type;
            private String name;
            private String start_time;
            private String status;
            private String type;

            public enum ItemType {
                WITH_PROGRESS,
                RECEIVE
            }

            public String getComplete_days() {
                return this.complete_days;
            }

            public String getCompletion() {
                return this.completion;
            }

            public String getDays() {
                return this.days;
            }

            public String getDescription() {
                return this.description;
            }

            public String getId() {
                return this.id;
            }

            public ItemType getItem_type() {
                return this.item_type;
            }

            public String getName() {
                return this.name;
            }

            public String getStart_time() {
                return this.start_time;
            }

            public String getStatus() {
                return this.status;
            }

            public String getType() {
                return this.type;
            }

            public boolean isExpanded() {
                return this.expanded;
            }

            public void setComplete_days(String complete_days) {
                this.complete_days = complete_days;
            }

            public void setCompletion(String completion) {
                this.completion = completion;
            }

            public void setDays(String days) {
                this.days = days;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public void setExpanded(boolean expanded) {
                this.expanded = expanded;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setItem_type(ItemType item_type) {
                this.item_type = item_type;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public String getDesc() {
            return this.desc;
        }

        public PlanInProgressBean getPlan_in_progress() {
            return this.plan_in_progress;
        }

        public String getRule() {
            return this.rule;
        }

        public List<SystemPlanBean> getSystem_plan() {
            return this.system_plan;
        }

        public String getTitle() {
            return this.title;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public void setPlan_in_progress(PlanInProgressBean plan_in_progress) {
            this.plan_in_progress = plan_in_progress;
        }

        public void setRule(String rule) {
            this.rule = rule;
        }

        public void setSystem_plan(List<SystemPlanBean> system_plan) {
            this.system_plan = system_plan;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public String getCode() {
        return this.code;
    }

    public DataBean getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public String getServer_time() {
        return this.server_time;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServer_time(String server_time) {
        this.server_time = server_time;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
