package com.psychiatrygarden.bean;

import android.text.TextUtils;

/* loaded from: classes5.dex */
public class ShareTaskBean {
    private String code;
    private ShareTaskData data;
    private String message;

    public class ShareTaskData {
        private String book_status;
        private String describe;
        private String file_status;
        private String id;
        private String join_reward;
        private String join_reward_name;
        private String join_reward_type;
        private String link;
        private String reward_type;
        private String task_title;
        private String title;

        public ShareTaskData() {
        }

        public String getBook_status() {
            if (TextUtils.isEmpty(this.book_status)) {
                this.book_status = "0";
            }
            return this.book_status;
        }

        public String getDescribe() {
            return this.describe;
        }

        public String getFile_status() {
            if (TextUtils.isEmpty(this.file_status)) {
                this.file_status = "0";
            }
            return this.file_status;
        }

        public String getId() {
            return this.id;
        }

        public String getJoin_reward() {
            return this.join_reward;
        }

        public String getJoin_reward_name() {
            return this.join_reward_name;
        }

        public String getJoin_reward_type() {
            return this.join_reward_type;
        }

        public String getLink() {
            return this.link;
        }

        public String getReward_type() {
            return this.reward_type;
        }

        public String getTask_title() {
            return this.task_title;
        }

        public String getTitle() {
            return this.title;
        }

        public void setBook_status(String book_status) {
            this.book_status = book_status;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public void setFile_status(String file_status) {
            this.file_status = file_status;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setJoin_reward(String join_reward) {
            this.join_reward = join_reward;
        }

        public void setJoin_reward_name(String join_reward_name) {
            this.join_reward_name = join_reward_name;
        }

        public void setJoin_reward_type(String join_reward_type) {
            this.join_reward_type = join_reward_type;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public void setReward_type(String reward_type) {
            this.reward_type = reward_type;
        }

        public void setTask_title(String task_title) {
            this.task_title = task_title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public String getCode() {
        return this.code;
    }

    public ShareTaskData getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(ShareTaskData data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
