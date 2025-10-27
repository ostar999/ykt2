package com.psychiatrygarden.activity.online.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class YearStatisticsBean {
    private String code;
    private DataDTO data;
    private String message;
    private String server_time;

    public static class DataDTO {
        private String full_mark;
        private String score;
        private List<TreeDTO> tree;

        public static class TreeDTO {
            private String activity_id;
            private String am_pm;
            private String chapter_id;
            private List<ChildrenDTO> children;
            private String full_mark;
            private String have;
            private String parent_id;
            private String question_count;
            private String score;
            private String sort;
            private String title;
            private String wrong_count;

            public static class ChildrenDTO {
                private String activity_id;
                private String am_pm;
                private String chapter_id;
                private String full_mark;
                private String have;
                private String parent_id;
                private String question_count;
                private List<String> question_ids;
                private String score;
                private String sort;
                private String title;
                private String wrong_count;

                public String getActivity_id() {
                    return this.activity_id;
                }

                public String getAm_pm() {
                    return this.am_pm;
                }

                public String getChapter_id() {
                    return this.chapter_id;
                }

                public String getFull_mark() {
                    return this.full_mark;
                }

                public String getHave() {
                    return this.have;
                }

                public String getParent_id() {
                    return this.parent_id;
                }

                public String getQuestion_count() {
                    return this.question_count;
                }

                public List<String> getQuestion_ids() {
                    return this.question_ids;
                }

                public String getScore() {
                    return this.score;
                }

                public String getSort() {
                    return this.sort;
                }

                public String getTitle() {
                    return this.title;
                }

                public String getWrong_count() {
                    return this.wrong_count;
                }

                public void setActivity_id(String activity_id) {
                    this.activity_id = activity_id;
                }

                public void setAm_pm(String am_pm) {
                    this.am_pm = am_pm;
                }

                public void setChapter_id(String chapter_id) {
                    this.chapter_id = chapter_id;
                }

                public void setFull_mark(String full_mark) {
                    this.full_mark = full_mark;
                }

                public void setHave(String have) {
                    this.have = have;
                }

                public void setParent_id(String parent_id) {
                    this.parent_id = parent_id;
                }

                public void setQuestion_count(String question_count) {
                    this.question_count = question_count;
                }

                public void setQuestion_ids(List<String> question_ids) {
                    this.question_ids = question_ids;
                }

                public void setScore(String score) {
                    this.score = score;
                }

                public void setSort(String sort) {
                    this.sort = sort;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public void setWrong_count(String wrong_count) {
                    this.wrong_count = wrong_count;
                }
            }

            public String getActivity_id() {
                return this.activity_id;
            }

            public String getAm_pm() {
                return this.am_pm;
            }

            public String getChapter_id() {
                return this.chapter_id;
            }

            public List<ChildrenDTO> getChildren() {
                return this.children;
            }

            public String getFull_mark() {
                return this.full_mark;
            }

            public String getHave() {
                return this.have;
            }

            public String getParent_id() {
                return this.parent_id;
            }

            public String getQuestion_count() {
                return this.question_count;
            }

            public String getScore() {
                return this.score;
            }

            public String getSort() {
                return this.sort;
            }

            public String getTitle() {
                return this.title;
            }

            public String getWrong_count() {
                return this.wrong_count;
            }

            public void setActivity_id(String activity_id) {
                this.activity_id = activity_id;
            }

            public void setAm_pm(String am_pm) {
                this.am_pm = am_pm;
            }

            public void setChapter_id(String chapter_id) {
                this.chapter_id = chapter_id;
            }

            public void setChildren(List<ChildrenDTO> children) {
                this.children = children;
            }

            public void setFull_mark(String full_mark) {
                this.full_mark = full_mark;
            }

            public void setHave(String have) {
                this.have = have;
            }

            public void setParent_id(String parent_id) {
                this.parent_id = parent_id;
            }

            public void setQuestion_count(String question_count) {
                this.question_count = question_count;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setWrong_count(String wrong_count) {
                this.wrong_count = wrong_count;
            }
        }

        public String getFull_mark() {
            return this.full_mark;
        }

        public String getScore() {
            return this.score;
        }

        public List<TreeDTO> getTree() {
            return this.tree;
        }

        public void setFull_mark(String full_mark) {
            this.full_mark = full_mark;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public void setTree(List<TreeDTO> tree) {
            this.tree = tree;
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
