package com.easefun.polyv.livecommon.module.modules.chatroom.presenter.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.module.modules.chatroom.presenter.vo.PLVChatEventWrapVO;
import com.easefun.polyv.livescenes.chatroom.PolyvLocalMessage;
import com.easefun.polyv.livescenes.chatroom.send.img.PolyvSendLocalImgEvent;
import com.plv.livescenes.chatroom.PLVLocalMessage;
import com.plv.livescenes.chatroom.send.img.PLVSendLocalImgEvent;
import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.socket.event.PLVBaseEvent;
import com.plv.socket.event.chat.PLVChatImgEvent;
import com.plv.socket.event.chat.PLVSpeakEvent;
import com.plv.socket.event.history.PLVChatImgHistoryEvent;
import com.plv.socket.event.history.PLVSpeakHistoryEvent;
import com.plv.socket.net.model.PLVSocketLoginVO;
import com.plv.socket.user.PLVSocketUserBean;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class PLVWrapChatEventUseCase {

    public static class PLVChatImgEventWrapper extends Wrapper {
        private PLVChatImgEventWrapper() {
            super();
        }

        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.presenter.usecase.PLVWrapChatEventUseCase.Wrapper
        public PLVChatEventWrapVO wrap(PLVBaseEvent event) {
            if (!(event instanceof PLVChatImgEvent)) {
                return null;
            }
            PLVChatImgEvent pLVChatImgEvent = (PLVChatImgEvent) event;
            return new PLVChatEventWrapVO().setEvent(pLVChatImgEvent).setId(pLVChatImgEvent.getId()).setTime(Long.valueOf(pLVChatImgEvent.getTime())).setLastEventTime(Long.valueOf(pLVChatImgEvent.getTime())).setUser(pLVChatImgEvent.getUser());
        }
    }

    public static class PLVChatImgHistoryEventWrapper extends Wrapper {
        private PLVChatImgHistoryEventWrapper() {
            super();
        }

        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.presenter.usecase.PLVWrapChatEventUseCase.Wrapper
        public PLVChatEventWrapVO wrap(PLVBaseEvent event) {
            if (!(event instanceof PLVChatImgHistoryEvent)) {
                return null;
            }
            PLVChatImgHistoryEvent pLVChatImgHistoryEvent = (PLVChatImgHistoryEvent) event;
            return new PLVChatEventWrapVO().setEvent(pLVChatImgHistoryEvent).setId(pLVChatImgHistoryEvent.getId()).setTime(Long.valueOf(pLVChatImgHistoryEvent.getTime())).setLastEventTime(Long.valueOf(pLVChatImgHistoryEvent.getTime())).setUser(pLVChatImgHistoryEvent.getUser());
        }
    }

    public static class PLVLocalChatImgEventWrapper extends Wrapper {
        private PLVLocalChatImgEventWrapper() {
            super();
        }

        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.presenter.usecase.PLVWrapChatEventUseCase.Wrapper
        public PLVChatEventWrapVO wrap(PLVBaseEvent event) {
            if (!(event instanceof PLVSendLocalImgEvent)) {
                return null;
            }
            PLVSendLocalImgEvent pLVSendLocalImgEvent = (PLVSendLocalImgEvent) event;
            return new PLVChatEventWrapVO().setEvent(pLVSendLocalImgEvent).setId(pLVSendLocalImgEvent.getId()).setTime(pLVSendLocalImgEvent.getTime()).setLastEventTime(pLVSendLocalImgEvent.getTime()).setUser(Wrapper.createLocalUser());
        }
    }

    public static class PLVLocalSpeakMessageWrapper extends Wrapper {
        private PLVLocalSpeakMessageWrapper() {
            super();
        }

        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.presenter.usecase.PLVWrapChatEventUseCase.Wrapper
        public PLVChatEventWrapVO wrap(PLVBaseEvent event) {
            if (!(event instanceof PLVLocalMessage)) {
                return null;
            }
            PLVLocalMessage pLVLocalMessage = (PLVLocalMessage) event;
            return new PLVChatEventWrapVO().setEvent(pLVLocalMessage).setId(pLVLocalMessage.getId()).setTime(pLVLocalMessage.getTime()).setLastEventTime(pLVLocalMessage.getTime()).setUser(Wrapper.createLocalUser());
        }
    }

    public static class PLVSpeakEventWrapper extends Wrapper {
        private PLVSpeakEventWrapper() {
            super();
        }

        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.presenter.usecase.PLVWrapChatEventUseCase.Wrapper
        public PLVChatEventWrapVO wrap(PLVBaseEvent event) {
            if (!(event instanceof PLVSpeakEvent)) {
                return null;
            }
            PLVSpeakEvent pLVSpeakEvent = (PLVSpeakEvent) event;
            return new PLVChatEventWrapVO().setEvent(pLVSpeakEvent).setId(pLVSpeakEvent.getId()).setTime(Long.valueOf(pLVSpeakEvent.getTime())).setLastEventTime(Long.valueOf(pLVSpeakEvent.getTime())).setUser(pLVSpeakEvent.getUser());
        }
    }

    public static class PLVSpeakHistoryEventWrapper extends Wrapper {
        private PLVSpeakHistoryEventWrapper() {
            super();
        }

        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.presenter.usecase.PLVWrapChatEventUseCase.Wrapper
        public PLVChatEventWrapVO wrap(PLVBaseEvent event) {
            if (!(event instanceof PLVSpeakHistoryEvent)) {
                return null;
            }
            PLVSpeakHistoryEvent pLVSpeakHistoryEvent = (PLVSpeakHistoryEvent) event;
            return new PLVChatEventWrapVO().setEvent(pLVSpeakHistoryEvent).setId(pLVSpeakHistoryEvent.getId()).setTime(Long.valueOf(pLVSpeakHistoryEvent.getTime())).setLastEventTime(Long.valueOf(pLVSpeakHistoryEvent.getTime())).setUser(pLVSpeakHistoryEvent.getUser());
        }
    }

    public static abstract class Wrapper {
        private static final Map<Class<? extends PLVBaseEvent>, Wrapper> wrapperMap = new HashMap<Class<? extends PLVBaseEvent>, Wrapper>() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.usecase.PLVWrapChatEventUseCase.Wrapper.1
            {
                put(PLVSpeakEvent.class, new PLVSpeakEventWrapper());
                put(PLVChatImgEvent.class, new PLVChatImgEventWrapper());
                put(PLVSpeakHistoryEvent.class, new PLVSpeakHistoryEventWrapper());
                put(PLVChatImgHistoryEvent.class, new PLVChatImgHistoryEventWrapper());
                put(PLVLocalMessage.class, new PLVLocalSpeakMessageWrapper());
                put(PolyvLocalMessage.class, new PLVLocalSpeakMessageWrapper());
                put(PLVSendLocalImgEvent.class, new PLVLocalChatImgEventWrapper());
                put(PolyvSendLocalImgEvent.class, new PLVLocalChatImgEventWrapper());
            }
        };

        private Wrapper() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static PLVSocketUserBean createLocalUser() {
            PLVSocketLoginVO loginVO = PLVSocketWrapper.getInstance().getLoginVO();
            PLVSocketUserBean pLVSocketUserBean = new PLVSocketUserBean();
            if (loginVO == null) {
                return pLVSocketUserBean;
            }
            pLVSocketUserBean.setUserType(loginVO.getUserType());
            pLVSocketUserBean.setNick(loginVO.getNickName());
            pLVSocketUserBean.setUserId(loginVO.getUserId());
            pLVSocketUserBean.setPic(loginVO.getAvatarUrl());
            pLVSocketUserBean.setActor(loginVO.getActor());
            pLVSocketUserBean.setAuthorization(loginVO.getAuthorization());
            return pLVSocketUserBean;
        }

        @Nullable
        private static Wrapper findWrapperForEvent(PLVBaseEvent event) {
            for (Class<?> superclass = event.getClass(); superclass != null && superclass != Object.class; superclass = superclass.getSuperclass()) {
                Map<Class<? extends PLVBaseEvent>, Wrapper> map = wrapperMap;
                if (map.containsKey(superclass)) {
                    return map.get(superclass);
                }
            }
            return null;
        }

        public static PLVChatEventWrapVO wrapEvent(PLVBaseEvent event) {
            Wrapper wrapper = wrapperMap.get(event.getClass());
            if (wrapper != null) {
                return wrapper.wrap(event);
            }
            return null;
        }

        public abstract PLVChatEventWrapVO wrap(PLVBaseEvent event);
    }

    @NonNull
    public PLVChatEventWrapVO wrap(PLVBaseEvent event) {
        PLVChatEventWrapVO pLVChatEventWrapVOWrapEvent = Wrapper.wrapEvent(event);
        return pLVChatEventWrapVOWrapEvent == null ? new PLVChatEventWrapVO() : pLVChatEventWrapVOWrapEvent;
    }
}
