package com.psychiatrygarden.activity.online.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class QuestionListBean {
    private String code;
    private DataDTO data;
    private String message;
    private String server_time;

    public static class DataDTO {
        private String cognition;
        private String high_frequency;
        private List<QuestionDetailBean> list;
        private String mode;
        private String outlines_mastery;
        private List<SearchDTO> search;

        public static class SearchDTO {
            private List<SearchDataDTO> data;
            private String field;
            private String selectId;
            private String seleteTitle;
            private String type_str;

            public static class SearchDataDTO {
                private String id;
                private int isSelected = 0;
                private List<String> list;
                private String title;
                private String yearTitle;

                public String getId() {
                    return this.id;
                }

                public int getIsSelected() {
                    return this.isSelected;
                }

                public List<String> getList() {
                    return this.list;
                }

                public String getTitle() {
                    return this.title;
                }

                public String getYearTitle() {
                    return this.yearTitle;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public void setIsSelected(int isSelected) {
                    this.isSelected = isSelected;
                }

                public void setList(List<String> list) {
                    this.list = list;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public void setYearTitle(String yearTitle) {
                    this.yearTitle = yearTitle;
                }
            }

            public List<SearchDataDTO> getData() {
                return this.data;
            }

            public String getField() {
                return this.field;
            }

            public String getSelectId() {
                return this.selectId;
            }

            public String getSeleteTitle() {
                return this.seleteTitle;
            }

            public String getType_str() {
                return this.type_str;
            }

            public void setData(List<SearchDataDTO> data) {
                this.data = data;
            }

            public void setField(String field) {
                this.field = field;
            }

            public void setSelectId(String selectId) {
                this.selectId = selectId;
            }

            public void setSeleteTitle(String seleteTitle) {
                this.seleteTitle = seleteTitle;
            }

            public void setType_str(String type_str) {
                this.type_str = type_str;
            }
        }

        public String getCognition() {
            return this.cognition;
        }

        public String getHigh_frequency() {
            return this.high_frequency;
        }

        public List<QuestionDetailBean> getList() {
            return this.list;
        }

        public String getMode() {
            return this.mode;
        }

        public String getOutlines_mastery() {
            return this.outlines_mastery;
        }

        public List<SearchDTO> getSearch() {
            return this.search;
        }

        public void setCognition(String cognition) {
            this.cognition = cognition;
        }

        public void setHigh_frequency(String high_frequency) {
            this.high_frequency = high_frequency;
        }

        public void setList(List<QuestionDetailBean> list) {
            this.list = list;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }

        public void setOutlines_mastery(String outlines_mastery) {
            this.outlines_mastery = outlines_mastery;
        }

        public void setSearch(List<SearchDTO> search) {
            this.search = search;
        }
    }

    public String getCode() {
        return this.code;
    }

    public DataDTO getData() {
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

    public void setData(DataDTO data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServer_time(String server_time) {
        this.server_time = server_time;
    }
}
