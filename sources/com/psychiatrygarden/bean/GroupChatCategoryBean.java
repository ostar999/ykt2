package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class GroupChatCategoryBean {
    private String code;
    private List<DataDTO> data;
    private String message;
    private String server_time;

    public static class DataDTO {
        private List<ChildrenDTO> children;
        private String default_icon;
        private String id;
        private String name;
        private boolean selected = false;
        private String selected_icon;

        public static class ChildrenDTO {
            private String default_icon;
            private String id;
            private String name;
            private boolean selected = false;
            private String selected_icon;

            public String getDefault_icon() {
                return this.default_icon;
            }

            public String getId() {
                return this.id;
            }

            public String getName() {
                return this.name;
            }

            public String getSelected_icon() {
                return this.selected_icon;
            }

            public boolean isSelected() {
                return this.selected;
            }

            public void setDefault_icon(String default_icon) {
                this.default_icon = default_icon;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setSelected(boolean selected) {
                this.selected = selected;
            }

            public void setSelected_icon(String selected_icon) {
                this.selected_icon = selected_icon;
            }
        }

        public List<ChildrenDTO> getChildren() {
            return this.children;
        }

        public String getDefault_icon() {
            return this.default_icon;
        }

        public String getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public String getSelected_icon() {
            return this.selected_icon;
        }

        public boolean isSelected() {
            return this.selected;
        }

        public void setChildren(List<ChildrenDTO> children) {
            this.children = children;
        }

        public void setDefault_icon(String default_icon) {
            this.default_icon = default_icon;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public void setSelected_icon(String selected_icon) {
            this.selected_icon = selected_icon;
        }
    }

    public String getCode() {
        return this.code;
    }

    public List<DataDTO> getData() {
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

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServer_time(String server_time) {
        this.server_time = server_time;
    }
}
