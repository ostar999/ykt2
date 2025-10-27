package com.plv.socket.event;

/* loaded from: classes5.dex */
public class PLVEventConstant {
    public static final String EMOTION_EVENT = "emotion";
    public static final String EVENT = "EVENT";
    public static final String LOGIN_ACK_EVENT = "loginAck";
    public static final String MESSAGE_EVENT = "message";
    public static final String MESSAGE_EVENT_EXIT_WHEN_IS_MAIN = "EXIT_WHEN_IS_MAIN";
    public static final String MESSAGE_EVENT_KICK = "KICK";
    public static final String MESSAGE_EVENT_LOGIN = "LOGIN";
    public static final String MESSAGE_EVENT_LOGIN_REFUSE = "LOGIN_REFUSE";
    public static final String MESSAGE_EVENT_LOGOUT = "LOGOUT";
    public static final String MESSAGE_EVENT_RELOGIN = "RELOGIN";
    public static final String MESSAGE_EVENT_TOKEN_EXPIRED = "TOKEN_EXPIRED";
    public static final String MESSAGE_EVENT_UNKICK = "UNKICK";
    public static final String SE_CUSTOMMESSAGE = "customMessage";

    public class Chatroom {
        public static final String EVENT_CUSTOMER_MESSAGE = "CUSTOMER_MESSAGE";
        public static final String EVENT_PRODUCT_MESSAGE = "PRODUCT_MESSAGE";
        public static final String EVENT_REWARD = "REWARD";
        public static final String EVENT_S_QUESTION = "S_QUESTION";
        public static final String EVENT_T_ANSWER = "T_ANSWER";
        public static final String FOCUS_EVENT_FOCUS_SPECIAL_SPEAK = "FOCUS_SPECIAL_SPEAK";
        public static final String MESSAGE_EVENT_BANIP = "BANIP";
        public static final String MESSAGE_EVENT_CHAT_IMG = "CHAT_IMG";
        public static final String MESSAGE_EVENT_CLOSEROOM = "CLOSEROOM";
        public static final String MESSAGE_EVENT_FOCUSMODE = "FOCUSMODE";
        public static final String MESSAGE_EVENT_GONGGAO = "GONGGAO";
        public static final String MESSAGE_EVENT_LIKES = "LIKES";
        public static final String MESSAGE_EVENT_REMOVE_CONTENT = "REMOVE_CONTENT";
        public static final String MESSAGE_EVENT_REMOVE_HISTORY = "REMOVE_HISTORY";
        public static final String MESSAGE_EVENT_REMOVE_SHIELD = "REMOVE_SHIELD";
        public static final String MESSAGE_EVENT_SET_NICK = "SET_NICK";
        public static final String MESSAGE_EVENT_SHIELD = "SHIELD";
        public static final String MESSAGE_EVENT_SPEAK = "SPEAK";
        public static final String MESSAGE_EVENT_UNSHIELD = "UNSHIELD";
        public static final String SE_FOCUS = "focus";

        public Chatroom() {
        }
    }

    public class Class {
        public static final String EVENT_LOOK_AT_ME = "LOOK_AT_ME";
        public static final String EVENT_SEND_CUP = "SEND_CUP";
        public static final String FINISH_CLASS = "finishClass";
        public static final String IN_LIVE_EVENT = "inLive";
        public static final String O_TEACHER_INFO = "O_TEACHER_INFO";
        public static final String SE_SWITCH_MESSAGE = "switchView";
        public static final String SE_SWITCH_PPT_MESSAGE = "changeVideoAndPPTPosition";
        public static final String TEACHER_INFO = "TEACHER_INFO";

        public Class() {
        }
    }

    public class Interact {
        public static final String BULLETIN_REMOVE = "REMOVE_BULLETIN";
        public static final String BULLETIN_SHOW = "BULLETIN";
        public static final String NEWS_PUSH = "newsPush";
        public static final String NEWS_PUSH_CANCEL = "cancel";
        public static final String NEWS_PUSH_START = "start";
        public static final String TIMER_EVENT = "timer";

        public Interact() {
        }
    }

    public class LinkMic {
        public static final String EVENT_ADD_GUEST_FROM_SIP = "addGuestFromSIP";
        public static final String EVENT_CHANGE_MIC_SITE = "changeMicSite";
        public static final String EVENT_MUTE_USER_MEDIA = "MUTE_USER_MEDIA";
        public static final String EVENT_MUTE_USER_MICRO = "MUTE_USER_MICRO";
        public static final String EVENT_OPEN_MICROPHONE = "OPEN_MICROPHONE";
        public static final String EVENT_REJOIN_MIC = "reJoinMic";
        public static final String JOIN_ANSWER_EVENT = "joinAnswer";
        public static final String JOIN_LEAVE_EVENT = "joinLeave";
        public static final String JOIN_REQUEST_EVENT = "joinRequest";
        public static final String JOIN_RESPONSE_EVENT = "joinResponse";
        public static final String JOIN_SUCCESS_EVENT = "joinSuccess";
        public static final String TEACHER_SET_PERMISSION = "TEACHER_SET_PERMISSION";

        public LinkMic() {
        }
    }

    public class Ppt {
        public static final String ON_ASSISTANT_CONTROL = "assistantSliceControl";
        public static final String ON_SLICE_CLOSE_EVENT = "onSliceClose";
        public static final String ON_SLICE_CONTROL_EVENT = "onSliceControl";
        public static final String ON_SLICE_DRAW_EVENT = "onSliceDraw";
        public static final String ON_SLICE_ID_EVENT = "onSliceID";
        public static final String ON_SLICE_OPEN_EVENT = "onSliceOpen";
        public static final String ON_SLICE_START_EVENT = "onSliceStart";

        public Ppt() {
        }
    }

    public class QuestionAndAnswer {
        public static final String EVENT_DELETE_QA_ANSWER = "DELETE_QA_ANSWER";
        public static final String EVENT_LAUNCH_A = "LAUNCH_A";
        public static final String EVENT_LAUNCH_Q = "LAUNCH_Q";

        public QuestionAndAnswer() {
        }
    }

    public class Seminar {
        public static final String EVENT_CANCEL_HELP = "cancelHelp";
        public static final String EVENT_DELETE_GROUP = "deleteGroup";
        public static final String EVENT_GROUP_REQUEST_HELP = "groupRequestHelp";
        public static final String EVENT_HOST_JOIN = "hostJoin";
        public static final String EVENT_HOST_LEAVE = "hostLeave";
        public static final String EVENT_HOST_SEND_TO_ALL_GROUP = "hostSendToAllGroup";
        public static final String EVENT_JOIN_DISCUSS = "joinDiscuss";
        public static final String EVENT_JOIN_GROUP = "joinGroup";
        public static final String EVENT_JOIN_SUCCESS = "joinSuccess";
        public static final String EVENT_LEAVE_DISCUSS = "leaveDiscuss";
        public static final String EVENT_OVER_DISCUSS = "overDiscuss";
        public static final String EVENT_SET_LEADER = "setLeader";
        public static final String EVENT_START_DISCUSS = "startDiscuss";
        public static final String SEMINAR_EVENT = "seminar";

        public Seminar() {
        }
    }

    public class TuWen {
        public static final String EVENT_CREATE_IMAGE_TEXT = "CREATE_IMAGE_TEXT";
        public static final String EVENT_DELETE_IMAGE_TEXT = "DELETE_IMAGE_TEXT";
        public static final String EVENT_SET_IMAGE_TEXT_MSG = "SET_IMAGE_TEXT_MSG";
        public static final String EVENT_SET_TOP_IMAGE_TEXT = "SET_TOP_IMAGE_TEXT";

        public TuWen() {
        }
    }
}
