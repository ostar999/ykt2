package com.ykb.ebook.common;

import com.psychiatrygarden.utils.EventBusConstant;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000e\n\u0002\b%\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\t\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\n\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u000b\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\f\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\r\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u000e\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u000f\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0010\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0011\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0012\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0013\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0014\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0015\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0016\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0017\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0018\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0019\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u001a\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u001b\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u001c\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u001d\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u001e\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u001f\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010 \u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010!\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\"\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010#\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010$\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010%\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"ACCOUNT_BANNED", "", "ADD_AGREE", "ADD_BOOK", "ADD_CORRECT", "ADD_DRAW_LINE", EventBusConstant.ADD_NOTE, "ADD_OPPOSE", "ALL_DRAW_LINE", "ALL_NOTES", "ALL_PARAGRAPH_COMMENT", "BOOK_INFO", "BOOK_REVIEW_LIST", "BOOK_REVIEW_LIST_ALL", "CHAPTER_INFO", "COMMENT_REPORT", "COMMENT_REPORT_REASON", "CORRECT_TYPE_LIST", "DEL_BOOK", "DEL_COMMENT_FLOOR", "DEL_LINE", EventBusConstant.DEL_NOTE, "EBOOK_DOWNLOAD_COUNT", "EBOOK_SHARE_COUNT", "EDIT_BOOK_REVIEW", "EDIT_LINE", "EDIT_NOTE", "FULL_TEXT_SEARCH", "GET_NOTE_DIALOG_LIST", "PARAGRAPH_REVIEW_LIST_ALL", "PERMISSION_METHOD", "PUBLISH_BOOK_REVIEW", "PUBLISH_COMMENT", "PUBLISH_REVIEW", "QUESTION_LIST", "REPLY_COMMENT_LIST", "SAVE_READ_PROGRESS", "UPLOAD_FORCOMMENT", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class UrlConfigKt {

    @NotNull
    public static final String ACCOUNT_BANNED = "User/Access/ban";

    @NotNull
    public static final String ADD_AGREE = "ebook/review/agree";

    @NotNull
    public static final String ADD_BOOK = "ebook/bookshelf/addBook";

    @NotNull
    public static final String ADD_CORRECT = "ebook/correct/addCorrect";

    @NotNull
    public static final String ADD_DRAW_LINE = "ebook/draw/addDraw";

    @NotNull
    public static final String ADD_NOTE = "ebook/notes/addNotes";

    @NotNull
    public static final String ADD_OPPOSE = "ebook/review/oppose";

    @NotNull
    public static final String ALL_DRAW_LINE = "ebook/draw/getAllDraw";

    @NotNull
    public static final String ALL_NOTES = "ebook/notes/getAllNotes";

    @NotNull
    public static final String ALL_PARAGRAPH_COMMENT = "ebook/review/getAllParagraphReview";

    @NotNull
    public static final String BOOK_INFO = "ebook/book/getBookInfo";

    @NotNull
    public static final String BOOK_REVIEW_LIST = "ebook/review/getBookReviewList";

    @NotNull
    public static final String BOOK_REVIEW_LIST_ALL = "ebook/review/getList";

    @NotNull
    public static final String CHAPTER_INFO = "ebook/chapter/getChapterInfo";

    @NotNull
    public static final String COMMENT_REPORT = "ebook/review/report";

    @NotNull
    public static final String COMMENT_REPORT_REASON = "bbs/Article/reportReason";

    @NotNull
    public static final String CORRECT_TYPE_LIST = "ebook/correct/getCorrectCategoryList";

    @NotNull
    public static final String DEL_BOOK = "ebook/bookshelf/delBook";

    @NotNull
    public static final String DEL_COMMENT_FLOOR = "ebook/review/del";

    @NotNull
    public static final String DEL_LINE = "ebook/draw/delDraw";

    @NotNull
    public static final String DEL_NOTE = "ebook/notes/delNotes";

    @NotNull
    public static final String EBOOK_DOWNLOAD_COUNT = "ebook/book/bookDownloadCount";

    @NotNull
    public static final String EBOOK_SHARE_COUNT = "ebook/book/shareCount";

    @NotNull
    public static final String EDIT_BOOK_REVIEW = "ebook/review/editReview";

    @NotNull
    public static final String EDIT_LINE = "ebook/draw/editDraw";

    @NotNull
    public static final String EDIT_NOTE = "ebook/notes/editNotes";

    @NotNull
    public static final String FULL_TEXT_SEARCH = "ebook/chapter/fullTextSearch";

    @NotNull
    public static final String GET_NOTE_DIALOG_LIST = "ebook/notes/getNotesList";

    @NotNull
    public static final String PARAGRAPH_REVIEW_LIST_ALL = "ebook/review/getParagraphReviewListNew";

    @NotNull
    public static final String PERMISSION_METHOD = "ebook/chapter/unlock";

    @NotNull
    public static final String PUBLISH_BOOK_REVIEW = "ebook/review/addBookReview";

    @NotNull
    public static final String PUBLISH_COMMENT = "ebook/review/addReview";

    @NotNull
    public static final String PUBLISH_REVIEW = "ebook/review/addComment";

    @NotNull
    public static final String QUESTION_LIST = "ebook/chapter/getQuestionList";

    @NotNull
    public static final String REPLY_COMMENT_LIST = "ebook/review/getReply";

    @NotNull
    public static final String SAVE_READ_PROGRESS = "ebook/book/addPerusalRecord";

    @NotNull
    public static final String UPLOAD_FORCOMMENT = "stock/file/uploadForComment";
}
