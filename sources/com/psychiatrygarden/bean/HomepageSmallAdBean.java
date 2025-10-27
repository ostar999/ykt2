package com.psychiatrygarden.bean;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class HomepageSmallAdBean {
    private String code;
    private DataDTO data;
    private String message;
    private String server_time;

    public static class DataDTO {
        private DataAd banner;
        private BlackAds block_ad;
        private DataAd lower_right_corner_ad;
        private DataAd question_ad;

        public class BlackAds {
            private List<BlackAd> ads;

            public class BlackAd {
                private int intervalOfRotation;
                private List<DataAd.AdsDTO> list;

                public BlackAd() {
                }

                public int getIntervalOfRotation() {
                    return this.intervalOfRotation;
                }

                public List<DataAd.AdsDTO> getList() {
                    return this.list;
                }

                public void setIntervalOfRotation(int intervalOfRotation) {
                    this.intervalOfRotation = intervalOfRotation;
                }

                public void setList(List<DataAd.AdsDTO> list) {
                    this.list = list;
                }
            }

            public BlackAds() {
            }

            public List<BlackAd> getAds() {
                return this.ads;
            }

            public void setAds(List<BlackAd> ads) {
                this.ads = ads;
            }
        }

        public static class DataAd {
            private List<AdsDTO> ads;
            private String force;
            private int time_interval;

            public static class AdsDTO {
                private ExtraDTO extra;
                private String icon;
                private String img;
                private String pad_img;
                private int push_type;
                private String smallLabel;
                private String subhead;
                private String title;

                public static class ExtraDTO {
                    private String activity_id;
                    private String app_id;
                    private String cid;
                    private String collection_id;
                    private String group_id = "";
                    private String h5_path;
                    private String have_chapter;
                    private String id;
                    private String identity_id;
                    private String identity_name;
                    private String is_rich_text;
                    private String json_path;
                    private String push_type;
                    private String question_file;
                    private String show_title;
                    private String show_type;
                    private String title;
                    private String web_link;

                    public String getActivity_id() {
                        return this.activity_id;
                    }

                    public String getApp_id() {
                        return this.app_id;
                    }

                    public String getCid() {
                        return this.cid;
                    }

                    public String getCollection_id() {
                        return this.collection_id;
                    }

                    public String getGroup_id() {
                        return this.group_id;
                    }

                    public String getH5_path() {
                        return this.h5_path;
                    }

                    public String getHave_chapter() {
                        return this.have_chapter;
                    }

                    public String getId() {
                        return this.id;
                    }

                    public String getIdentity_id() {
                        return this.identity_id;
                    }

                    public String getIdentity_name() {
                        return this.identity_name;
                    }

                    public String getIs_rich_text() {
                        return this.is_rich_text;
                    }

                    public String getJson_path() {
                        return this.json_path;
                    }

                    public String getPush_type() {
                        return this.push_type;
                    }

                    public String getQuestion_file() {
                        return this.question_file;
                    }

                    public String getShow_title() {
                        return this.show_title;
                    }

                    public String getShow_type() {
                        return this.show_type;
                    }

                    public String getTitle() {
                        return this.title;
                    }

                    public String getWeb_link() {
                        return this.web_link;
                    }

                    public void setActivity_id(String activity_id) {
                        this.activity_id = activity_id;
                    }

                    public void setApp_id(String app_id) {
                        this.app_id = app_id;
                    }

                    public void setCid(String cid) {
                        this.cid = cid;
                    }

                    public void setCollection_id(String collection_id) {
                        this.collection_id = collection_id;
                    }

                    public void setGroup_id(String group_id) {
                        this.group_id = group_id;
                    }

                    public void setH5_path(String h5_path) {
                        this.h5_path = h5_path;
                    }

                    public void setHave_chapter(String have_chapter) {
                        this.have_chapter = have_chapter;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public void setIdentity_id(String identity_id) {
                        this.identity_id = identity_id;
                    }

                    public void setIdentity_name(String identity_name) {
                        this.identity_name = identity_name;
                    }

                    public void setIs_rich_text(String is_rich_text) {
                        this.is_rich_text = is_rich_text;
                    }

                    public void setJson_path(String json_path) {
                        this.json_path = json_path;
                    }

                    public void setPush_type(String push_type) {
                        this.push_type = push_type;
                    }

                    public void setQuestion_file(String question_file) {
                        this.question_file = question_file;
                    }

                    public void setShow_title(String show_title) {
                        this.show_title = show_title;
                    }

                    public void setShow_type(String show_type) {
                        this.show_type = show_type;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }

                    public void setWeb_link(String web_link) {
                        this.web_link = web_link;
                    }
                }

                public ExtraDTO getExtra() {
                    return this.extra;
                }

                public String getIcon() {
                    return this.icon;
                }

                public String getImg() {
                    return this.img;
                }

                public String getPad_img() {
                    return this.pad_img;
                }

                public int getPush_type() {
                    return this.push_type;
                }

                public String getSmallLabel() {
                    return this.smallLabel;
                }

                public String getSubhead() {
                    return this.subhead;
                }

                public String getTitle() {
                    return this.title;
                }

                public void setExtra(ExtraDTO extra) {
                    this.extra = extra;
                }

                public void setIcon(String icon) {
                    this.icon = icon;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public void setPad_img(String pad_img) {
                    this.pad_img = pad_img;
                }

                public void setPush_type(int push_type) {
                    this.push_type = push_type;
                }

                public void setSmallLabel(String smallLabel) {
                    this.smallLabel = smallLabel;
                }

                public void setSubhead(String subhead) {
                    this.subhead = subhead;
                }

                public void setTitle(String title) {
                    this.title = title;
                }
            }

            public List<AdsDTO> getAds() {
                List<AdsDTO> list = this.ads;
                return list == null ? new ArrayList() : list;
            }

            public String getForce() {
                return this.force;
            }

            public int getTime_interval() {
                return this.time_interval;
            }

            public void setAds(List<AdsDTO> ads) {
                this.ads = ads;
            }

            public void setForce(String force) {
                this.force = force;
            }

            public void setTime_interval(int time_interval) {
                this.time_interval = time_interval;
            }
        }

        public DataAd getBanner() {
            return this.banner;
        }

        public BlackAds getBlock_ad() {
            return this.block_ad;
        }

        public DataAd getLower_right_corner_ad() {
            return this.lower_right_corner_ad;
        }

        public DataAd getQuestion_ad() {
            return this.question_ad;
        }

        public void setBanner(DataAd banner) {
            this.banner = banner;
        }

        public void setBlock_ad(BlackAds block_ad) {
            this.block_ad = block_ad;
        }

        public void setLower_right_corner_ad(DataAd lower_right_corner_ad) {
            this.lower_right_corner_ad = lower_right_corner_ad;
        }

        public void setQuestion_ad(DataAd question_ad) {
            this.question_ad = question_ad;
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
