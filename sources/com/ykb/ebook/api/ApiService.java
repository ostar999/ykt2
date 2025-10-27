package com.ykb.ebook.api;

import com.ykb.ebook.base.BaseListResponse;
import com.ykb.ebook.base.BaseResponse;
import com.ykb.ebook.common.UrlConfigKt;
import com.ykb.ebook.model.AllDrawLine;
import com.ykb.ebook.model.AllNotes;
import com.ykb.ebook.model.AllParagraphComment;
import com.ykb.ebook.model.BookInfo;
import com.ykb.ebook.model.ChapterInfo;
import com.ykb.ebook.model.CommentData;
import com.ykb.ebook.model.CorrectType;
import com.ykb.ebook.model.NoteListBean;
import com.ykb.ebook.model.ParagraphComment;
import com.ykb.ebook.model.PermissionInfo;
import com.ykb.ebook.model.QuestionListData;
import com.ykb.ebook.model.ReportReason;
import com.ykb.ebook.model.TextSearchResult;
import com.ykb.ebook.model.UploadImgBean;
import java.util.Map;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import okhttp3.MultipartBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

@Metadata(d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\bf\u0018\u00002\u00020\u0001J-\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0007J7\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\b\b\u0001\u0010\t\u001a\u00020\u00062\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\nJ-\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0007J-\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0007J-\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0007J-\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0007J-\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0007J3\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00110\u00032\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0007J3\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00110\u00032\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0007J3\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00110\u00032\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0007J-\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u00032\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0007J-\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00032\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0007J-\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00032\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0007J-\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0007J\u0011\u0010\u001e\u001a\u00020\u001fH§@ø\u0001\u0000¢\u0006\u0002\u0010 J3\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\"0\u00110\u00032\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0007J7\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\b\b\u0001\u0010\t\u001a\u00020\u00062\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\nJ-\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0007J-\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0007J-\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0007J-\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0007J-\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0007J-\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0007J3\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020+0\u00110\u00032\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0007J-\u0010,\u001a\b\u0012\u0004\u0012\u00020-0\u00032\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0007J3\u0010.\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020/0\u00110\u00032\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0007J-\u00100\u001a\b\u0012\u0004\u0012\u0002010\u00032\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0007J-\u00102\u001a\b\u0012\u0004\u0012\u0002010\u00032\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0007J-\u00103\u001a\b\u0012\u0004\u0012\u0002040\u00032\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0007J!\u00105\u001a\b\u0012\u0004\u0012\u0002060\u00032\b\b\u0001\u00107\u001a\u000208H§@ø\u0001\u0000¢\u0006\u0002\u00109J-\u0010:\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0007J-\u0010;\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0007J-\u0010<\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00032\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0007J-\u0010=\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0007J-\u0010>\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\u0007J7\u0010?\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\b\b\u0001\u0010\t\u001a\u00020\u00062\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H§@ø\u0001\u0000¢\u0006\u0002\u0010\n\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006@"}, d2 = {"Lcom/ykb/ebook/api/ApiService;", "", "accountBanned", "Lcom/ykb/ebook/base/BaseResponse;", "params", "", "", "(Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addBook", "url", "(Ljava/lang/String;Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addCorrect", "addDrawLine", "addNote", "addOppose", "addSupport", "allDrawLine", "Lcom/ykb/ebook/base/BaseListResponse;", "Lcom/ykb/ebook/model/AllDrawLine;", "allNotes", "Lcom/ykb/ebook/model/AllNotes;", "allParagraphComment", "Lcom/ykb/ebook/model/AllParagraphComment;", "bookInfo", "Lcom/ykb/ebook/model/BookInfo;", "bookReviewListAll", "Lcom/ykb/ebook/model/CommentData;", "chapterInfo", "Lcom/ykb/ebook/model/ChapterInfo;", "commentReport", "commentReportReason", "Lcom/ykb/ebook/model/ReportReason;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "correctTypeList", "Lcom/ykb/ebook/model/CorrectType;", "delBook", "delFloorComment", "delLine", "delNote", "editComment", "editDrawLine", "editNote", "fullTextSearch", "Lcom/ykb/ebook/model/TextSearchResult;", "getQuestionList", "Lcom/ykb/ebook/model/QuestionListData;", "noteList", "Lcom/ykb/ebook/model/NoteListBean;", "paragraphReviewList", "Lcom/ykb/ebook/model/ParagraphComment;", "paragraphReviewListAll", "permissionMethod", "Lcom/ykb/ebook/model/PermissionInfo;", "postImage", "Lcom/ykb/ebook/model/UploadImgBean;", "image", "Lokhttp3/MultipartBody$Part;", "(Lokhttp3/MultipartBody$Part;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "publishBookComment", "publishReview", "replyCommentFloorList", "saveReadProgress", "uploadEbookDownloadCount", "uploadEbookShareCount", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public interface ApiService {
    @FormUrlEncoded
    @POST(UrlConfigKt.ACCOUNT_BANNED)
    @Nullable
    Object accountBanned(@FieldMap @NotNull Map<String, String> map, @NotNull Continuation<? super BaseResponse<Object>> continuation);

    @FormUrlEncoded
    @POST
    @Nullable
    Object addBook(@Url @NotNull String str, @FieldMap @NotNull Map<String, String> map, @NotNull Continuation<? super BaseResponse<Object>> continuation);

    @FormUrlEncoded
    @POST(UrlConfigKt.ADD_CORRECT)
    @Nullable
    Object addCorrect(@FieldMap @NotNull Map<String, String> map, @NotNull Continuation<? super BaseResponse<Object>> continuation);

    @FormUrlEncoded
    @POST(UrlConfigKt.ADD_DRAW_LINE)
    @Nullable
    Object addDrawLine(@FieldMap @NotNull Map<String, String> map, @NotNull Continuation<? super BaseResponse<Object>> continuation);

    @FormUrlEncoded
    @POST(UrlConfigKt.ADD_NOTE)
    @Nullable
    Object addNote(@FieldMap @NotNull Map<String, String> map, @NotNull Continuation<? super BaseResponse<Object>> continuation);

    @FormUrlEncoded
    @POST(UrlConfigKt.ADD_OPPOSE)
    @Nullable
    Object addOppose(@FieldMap @NotNull Map<String, String> map, @NotNull Continuation<? super BaseResponse<Object>> continuation);

    @FormUrlEncoded
    @POST(UrlConfigKt.ADD_AGREE)
    @Nullable
    Object addSupport(@FieldMap @NotNull Map<String, String> map, @NotNull Continuation<? super BaseResponse<Object>> continuation);

    @FormUrlEncoded
    @POST(UrlConfigKt.ALL_DRAW_LINE)
    @Nullable
    Object allDrawLine(@FieldMap @NotNull Map<String, String> map, @NotNull Continuation<? super BaseResponse<BaseListResponse<AllDrawLine>>> continuation);

    @FormUrlEncoded
    @POST(UrlConfigKt.ALL_NOTES)
    @Nullable
    Object allNotes(@FieldMap @NotNull Map<String, String> map, @NotNull Continuation<? super BaseResponse<BaseListResponse<AllNotes>>> continuation);

    @FormUrlEncoded
    @POST(UrlConfigKt.ALL_PARAGRAPH_COMMENT)
    @Nullable
    Object allParagraphComment(@FieldMap @NotNull Map<String, String> map, @NotNull Continuation<? super BaseResponse<BaseListResponse<AllParagraphComment>>> continuation);

    @FormUrlEncoded
    @POST(UrlConfigKt.BOOK_INFO)
    @Nullable
    Object bookInfo(@FieldMap @NotNull Map<String, String> map, @NotNull Continuation<? super BaseResponse<BookInfo>> continuation);

    @FormUrlEncoded
    @POST(UrlConfigKt.BOOK_REVIEW_LIST_ALL)
    @Nullable
    Object bookReviewListAll(@FieldMap @NotNull Map<String, String> map, @NotNull Continuation<? super BaseResponse<CommentData>> continuation);

    @FormUrlEncoded
    @POST(UrlConfigKt.CHAPTER_INFO)
    @Nullable
    Object chapterInfo(@FieldMap @NotNull Map<String, String> map, @NotNull Continuation<? super BaseResponse<ChapterInfo>> continuation);

    @FormUrlEncoded
    @POST(UrlConfigKt.COMMENT_REPORT)
    @Nullable
    Object commentReport(@FieldMap @NotNull Map<String, String> map, @NotNull Continuation<? super BaseResponse<Object>> continuation);

    @POST(UrlConfigKt.COMMENT_REPORT_REASON)
    @Nullable
    Object commentReportReason(@NotNull Continuation<? super ReportReason> continuation);

    @FormUrlEncoded
    @POST(UrlConfigKt.CORRECT_TYPE_LIST)
    @Nullable
    Object correctTypeList(@FieldMap @NotNull Map<String, String> map, @NotNull Continuation<? super BaseResponse<BaseListResponse<CorrectType>>> continuation);

    @FormUrlEncoded
    @POST
    @Nullable
    Object delBook(@Url @NotNull String str, @FieldMap @NotNull Map<String, String> map, @NotNull Continuation<? super BaseResponse<Object>> continuation);

    @FormUrlEncoded
    @POST(UrlConfigKt.DEL_COMMENT_FLOOR)
    @Nullable
    Object delFloorComment(@FieldMap @NotNull Map<String, String> map, @NotNull Continuation<? super BaseResponse<Object>> continuation);

    @FormUrlEncoded
    @POST(UrlConfigKt.DEL_LINE)
    @Nullable
    Object delLine(@FieldMap @NotNull Map<String, String> map, @NotNull Continuation<? super BaseResponse<Object>> continuation);

    @FormUrlEncoded
    @POST(UrlConfigKt.DEL_NOTE)
    @Nullable
    Object delNote(@FieldMap @NotNull Map<String, String> map, @NotNull Continuation<? super BaseResponse<Object>> continuation);

    @FormUrlEncoded
    @POST(UrlConfigKt.EDIT_BOOK_REVIEW)
    @Nullable
    Object editComment(@FieldMap @NotNull Map<String, String> map, @NotNull Continuation<? super BaseResponse<Object>> continuation);

    @FormUrlEncoded
    @POST(UrlConfigKt.EDIT_LINE)
    @Nullable
    Object editDrawLine(@FieldMap @NotNull Map<String, String> map, @NotNull Continuation<? super BaseResponse<Object>> continuation);

    @FormUrlEncoded
    @POST(UrlConfigKt.EDIT_NOTE)
    @Nullable
    Object editNote(@FieldMap @NotNull Map<String, String> map, @NotNull Continuation<? super BaseResponse<Object>> continuation);

    @FormUrlEncoded
    @POST(UrlConfigKt.FULL_TEXT_SEARCH)
    @Nullable
    Object fullTextSearch(@FieldMap @NotNull Map<String, String> map, @NotNull Continuation<? super BaseResponse<BaseListResponse<TextSearchResult>>> continuation);

    @FormUrlEncoded
    @POST(UrlConfigKt.QUESTION_LIST)
    @Nullable
    Object getQuestionList(@FieldMap @NotNull Map<String, String> map, @NotNull Continuation<? super BaseResponse<QuestionListData>> continuation);

    @FormUrlEncoded
    @POST(UrlConfigKt.GET_NOTE_DIALOG_LIST)
    @Nullable
    Object noteList(@FieldMap @NotNull Map<String, String> map, @NotNull Continuation<? super BaseResponse<BaseListResponse<NoteListBean>>> continuation);

    @FormUrlEncoded
    @POST(UrlConfigKt.BOOK_REVIEW_LIST_ALL)
    @Nullable
    Object paragraphReviewList(@FieldMap @NotNull Map<String, String> map, @NotNull Continuation<? super BaseResponse<ParagraphComment>> continuation);

    @FormUrlEncoded
    @POST(UrlConfigKt.PARAGRAPH_REVIEW_LIST_ALL)
    @Nullable
    Object paragraphReviewListAll(@FieldMap @NotNull Map<String, String> map, @NotNull Continuation<? super BaseResponse<ParagraphComment>> continuation);

    @FormUrlEncoded
    @POST(UrlConfigKt.PERMISSION_METHOD)
    @Nullable
    Object permissionMethod(@FieldMap @NotNull Map<String, String> map, @NotNull Continuation<? super BaseResponse<PermissionInfo>> continuation);

    @POST(UrlConfigKt.UPLOAD_FORCOMMENT)
    @Nullable
    @Multipart
    Object postImage(@NotNull @Part MultipartBody.Part part, @NotNull Continuation<? super BaseResponse<UploadImgBean>> continuation);

    @FormUrlEncoded
    @POST(UrlConfigKt.PUBLISH_COMMENT)
    @Nullable
    Object publishBookComment(@FieldMap @NotNull Map<String, String> map, @NotNull Continuation<? super BaseResponse<Object>> continuation);

    @FormUrlEncoded
    @POST(UrlConfigKt.PUBLISH_REVIEW)
    @Nullable
    Object publishReview(@FieldMap @NotNull Map<String, String> map, @NotNull Continuation<? super BaseResponse<Object>> continuation);

    @FormUrlEncoded
    @POST(UrlConfigKt.REPLY_COMMENT_LIST)
    @Nullable
    Object replyCommentFloorList(@FieldMap @NotNull Map<String, String> map, @NotNull Continuation<? super BaseResponse<CommentData>> continuation);

    @FormUrlEncoded
    @POST(UrlConfigKt.SAVE_READ_PROGRESS)
    @Nullable
    Object saveReadProgress(@FieldMap @NotNull Map<String, String> map, @NotNull Continuation<? super BaseResponse<Object>> continuation);

    @FormUrlEncoded
    @POST(UrlConfigKt.EBOOK_DOWNLOAD_COUNT)
    @Nullable
    Object uploadEbookDownloadCount(@FieldMap @NotNull Map<String, String> map, @NotNull Continuation<? super BaseResponse<Object>> continuation);

    @FormUrlEncoded
    @POST
    @Nullable
    Object uploadEbookShareCount(@Url @NotNull String str, @FieldMap @NotNull Map<String, String> map, @NotNull Continuation<? super BaseResponse<Object>> continuation);
}
