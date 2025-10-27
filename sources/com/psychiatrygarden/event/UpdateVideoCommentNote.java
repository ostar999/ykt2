package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class UpdateVideoCommentNote {
    private int commentCount;
    private int noteCount;

    public UpdateVideoCommentNote(int noteCount, int commentCount) {
        this.noteCount = noteCount;
        this.commentCount = commentCount;
    }

    public int getCommentCount() {
        return this.commentCount;
    }

    public int getNoteCount() {
        return this.noteCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public void setNoteCount(int noteCount) {
        this.noteCount = noteCount;
    }
}
