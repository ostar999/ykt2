package com.plv.livescenes.document.event;

import com.google.gson.Gson;
import java.util.List;

/* loaded from: classes4.dex */
public class PLVRefreshMinimizeContainerDataEvent extends PLVAbsDocumentEvent {
    public static final String TYPE = "refreshMinimizeContainerData";
    private List<ContainerData> list;

    public static class ContainerData {
        private String containerId;
        private String fileExtension;
        private String fileName;
        private Boolean isMaximize;
        private Boolean isMinimize;
        private String title;

        public String getContainerId() {
            return this.containerId;
        }

        public String getFileExtension() {
            return this.fileExtension;
        }

        public String getFileName() {
            return this.fileName;
        }

        public Boolean getMaximize() {
            return this.isMaximize;
        }

        public Boolean getMinimize() {
            return this.isMinimize;
        }

        public String getTitle() {
            return this.title;
        }
    }

    public static PLVRefreshMinimizeContainerDataEvent fromJson(String str) {
        return (PLVRefreshMinimizeContainerDataEvent) new Gson().fromJson(str, PLVRefreshMinimizeContainerDataEvent.class);
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String getEventType() {
        return TYPE;
    }

    public List<ContainerData> getList() {
        return this.list;
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String toJson() {
        return new Gson().toJson(this);
    }
}
