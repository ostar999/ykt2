package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class VestReplyBean implements Serializable {
    private int code;
    private DataDTO data;
    private String message;
    private int server_time;

    public static class DataDTO implements Serializable {
        private List<ListDTO> list;

        public static class ListDTO implements Serializable {
            private String avatar;
            private String nickname;
            private int red_dot;
            private int user_id;

            public String getAvatar() {
                return this.avatar;
            }

            public String getNickname() {
                return this.nickname;
            }

            public int getRed_dot() {
                return this.red_dot;
            }

            public int getUser_id() {
                return this.user_id;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setRed_dot(int red_dot) {
                this.red_dot = red_dot;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }
        }

        public List<ListDTO> getList() {
            return this.list;
        }

        public void setList(List<ListDTO> list) {
            this.list = list;
        }
    }

    public int getCode() {
        return this.code;
    }

    public DataDTO getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public int getServer_time() {
        return this.server_time;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServer_time(int server_time) {
        this.server_time = server_time;
    }
}
