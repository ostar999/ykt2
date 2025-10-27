package com.psychiatrygarden.utils;

/* loaded from: classes6.dex */
public interface Constants {

    public interface ANSWER_MODE {
        public static final String PRACTICE_MODE = "practice";
        public static final String QUICK_BRUSH_MODE = "quick_brush";
        public static final String RECITE_MODE = "recite";
        public static final String TEST_MODE = "test";
    }

    public interface API_CODE_CONSTANTS {
        public static final int API_OK = 1;
    }

    public interface API_CODE_CONSTANTS_200 {
        public static final int API_OK = 200;
    }

    public interface CIRCLE_CATE_TYPE {
        public static final String CATE_KAOYAN = "1";
        public static final String CATE_SHENGHUO = "3";
        public static final String CATE_ZIGE = "2";
    }

    public interface CIRCLE_ORDER_TYPE {
        public static final String ORDER_ASC = "asc";
        public static final String ORDER_DESC = "desc";
    }

    public interface CIRCLE_TOPIC_TYPE {
        public static final String TOPIC_MY_REPLY = "1";
        public static final String TOPIC_NORMAL = "0";
        public static final String TOPIC_REPLY_MY = "2";
        public static final String TOPIC_VOTE = "1";
    }

    public interface CONFIG_CONSTANTS {
        public static final int APP_PAGE_NUM = 10;
    }

    public interface PARAMS_CONSTANTS {
        public static final String PARAMS_CATE_ID = "bbs_cate_id";
        public static final String PARAMS_CATE_TYPE = "cate_type";
        public static final String PARAMS_FLOOR_NUM = "floor_num";
        public static final String PARAMS_KEYWORD = "keyword";
        public static final String PARAMS_NICKNAME = "nickname";
        public static final String PARAMS_PAGE = "page";
        public static final String PARAMS_PAGE_NUM = "page_num";
        public static final String PARAMS_REPLY_TYPE = "reply_type";
        public static final String PARAMS_SHOW_FLOOR_HOST = "show_floor_host";
        public static final String PARAMS_TOPIC_COMMENT_ID = "topic_comment_id";
        public static final String PARAMS_TOPIC_CONTENT = "content";
        public static final String PARAMS_TOPIC_DETAIL_ORDER = "order";
        public static final String PARAMS_TOPIC_ID = "topic_id";
        public static final String PARAMS_TOPIC_IMAGE = "img";
        public static final String PARAMS_TOPIC_LABEL = "label";
        public static final String PARAMS_TOPIC_LABEL_ID = "topic_label_id";
        public static final String PARAMS_TOPIC_NICKNAME = "nickname";
        public static final String PARAMS_TOPIC_OPTION = "option";
        public static final String PARAMS_TOPIC_REPLY_FLOOR_NUM = "floor_num";
        public static final String PARAMS_TOPIC_REPLY_TO_USER_ID = "to_user_id";
        public static final String PARAMS_TOPIC_TITLE = "title";
        public static final String PARAMS_TOPIC_TYPE = "topic_type";
        public static final String PARAMS_TO_USER_ID = "to_user_id";
        public static final String PARAMS_UPLOAD_IMAGE = "img";
        public static final String PARAMS_USER_COMMENT_ID = "user_comment_id";
        public static final String PARAMS_USER_ID = "user_id";
        public static final String PARAMS_USER_TOKEN = "token";
        public static final String PARAMS_VOTE_TOPIC_OPTION_ID = "vote_topic_option_id";
    }

    public interface REQUEST_CODE {
        public static final int REQUEST_CODE_PICKER_ALBUM = 1;
        public static final int REQUEST_CODE_PICKER_CAMERA = 2;
        public static final int REQUEST_CODE_PUBLISH_COMMENT = 3;
        public static final int REQUEST_CODE_PUBLISH_TOPIC = 4;
    }
}
