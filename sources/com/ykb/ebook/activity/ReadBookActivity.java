package com.ykb.ebook.activity;

import android.annotation.SuppressLint;
import android.content.AppCtxKt;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.ColorResourcesKt;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import cn.hutool.core.text.StrPool;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.gson.Gson;
import com.hjq.http.EasyConfig;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.OnDownloadListener;
import com.hjq.http.model.HttpMethod;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.lxj.xpopup.util.SmartGlideImageLoader;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.mobile.auth.gatewayauth.Constant;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import com.plv.business.sub.danmaku.entity.PLVDanmakuInfo;
import com.plv.socket.user.PLVSocketUserConstant;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.MimeTypes;
import com.psychiatrygarden.utils.SdkConstant;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.unity3d.splash.services.ads.adunit.AdUnitActivity;
import com.yikaobang.yixue.R2;
import com.ykb.common_share_lib.CommonConfig;
import com.ykb.common_share_lib.bean.OnDialogShareClickListener;
import com.ykb.common_share_lib.view.CommonShareDialog2;
import com.ykb.ebook.R;
import com.ykb.ebook.activity.ReadBookActivity;
import com.ykb.ebook.api.ApiService;
import com.ykb.ebook.api.ApiServiceKt;
import com.ykb.ebook.base.BaseListResponse;
import com.ykb.ebook.base.BaseResponse;
import com.ykb.ebook.base.BaseVmActivity;
import com.ykb.ebook.common.ConstantKt;
import com.ykb.ebook.common.EventCallback;
import com.ykb.ebook.common.PreferKeyKt;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.common_interface.LayoutProgressListener;
import com.ykb.ebook.common_interface.TimeBatteryCallback;
import com.ykb.ebook.databinding.ActivityReadBookBinding;
import com.ykb.ebook.dialog.AddReviewCommentDialog;
import com.ykb.ebook.dialog.BasicDialog;
import com.ykb.ebook.dialog.ChapterSearchDialog;
import com.ykb.ebook.dialog.CommonOneDialog;
import com.ykb.ebook.dialog.NoteListDialog;
import com.ykb.ebook.dialog.ReadGuideDialog;
import com.ykb.ebook.dialog.ReviewCommentDialog;
import com.ykb.ebook.dialog.TextOperationDialog;
import com.ykb.ebook.dialog.WriteCorrectionDialog;
import com.ykb.ebook.event.ImageUploadSuccessEvent;
import com.ykb.ebook.extensions.ActivityExtensionsKt;
import com.ykb.ebook.extensions.ContextExtensionsKt;
import com.ykb.ebook.extensions.GsonExtensionsKt;
import com.ykb.ebook.extensions.StringExtensionsKt;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import com.ykb.ebook.model.BookInfo;
import com.ykb.ebook.model.Chapter;
import com.ykb.ebook.model.CorrectType;
import com.ykb.ebook.model.EditUnderLineInfo;
import com.ykb.ebook.model.Note;
import com.ykb.ebook.model.PerusalDuration;
import com.ykb.ebook.model.QuestionDetailBean;
import com.ykb.ebook.model.QuestionDetailBeanNew;
import com.ykb.ebook.model.QuestionListData;
import com.ykb.ebook.model.TextChapter;
import com.ykb.ebook.model.TextLine;
import com.ykb.ebook.model.TextPage;
import com.ykb.ebook.model.TextPosition;
import com.ykb.ebook.model.TextSearchResult;
import com.ykb.ebook.model.Ways;
import com.ykb.ebook.network.RequestHandler;
import com.ykb.ebook.page.ChapterProvider;
import com.ykb.ebook.page.PageDirection;
import com.ykb.ebook.page.ReadBook;
import com.ykb.ebook.page.TextPageFactory;
import com.ykb.ebook.page.TextParagraph;
import com.ykb.ebook.page.column.BaseColumn;
import com.ykb.ebook.page.column.TextColumn;
import com.ykb.ebook.page.delegate.PageDelegate;
import com.ykb.ebook.receiver.TimeBatteryReceiver;
import com.ykb.ebook.task.BasicCountDownTimer;
import com.ykb.ebook.util.Debounce;
import com.ykb.ebook.util.FastClickUtilKt;
import com.ykb.ebook.util.HandlerUtilsKt;
import com.ykb.ebook.util.Log;
import com.ykb.ebook.util.ScreenUtil;
import com.ykb.ebook.util.StatusBarUtil;
import com.ykb.ebook.util.Throttle;
import com.ykb.ebook.util.ThrottleKt;
import com.ykb.ebook.util.ToastUtilsKt;
import com.ykb.ebook.vm.ReadBookViewModel;
import com.ykb.ebook.weight.ChapterListPop;
import com.ykb.ebook.weight.ContentTextView;
import com.ykb.ebook.weight.PageView;
import com.ykb.ebook.weight.ReadMenu;
import com.ykb.ebook.weight.ReadView;
import com.ykb.ebook.weight.TextActionMenu;
import de.greenrobot.event.EventBus;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutorService;
import kotlin.Deprecated;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import kotlin.text.StringsKt___StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import okhttp3.OkHttpClient;
import org.apache.commons.codec.language.bm.Languages;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import splitties.systemservices.SystemServicesKt;

@Metadata(d1 = {"\u0000Ò\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0010\u0018\u0000 §\u00022\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\u00020\u00042\u00020\u00052\u00020\u00062\u00020\u00072\u00020\b2\u00020\t2\u00020\n2\u00020\u000b2\u00020\f2\u00020\r:\u0002§\u0002B\u0005¢\u0006\u0002\u0010\u000eJ\t\u0010\u0092\u0001\u001a\u00020_H\u0016J\u0012\u0010\u0093\u0001\u001a\u00020_2\u0007\u0010\u0094\u0001\u001a\u00020\u0010H\u0016J\t\u0010\u0095\u0001\u001a\u000207H\u0002J\t\u0010\u0096\u0001\u001a\u00020_H\u0016J\t\u0010\u0097\u0001\u001a\u00020_H\u0016J\t\u0010\u0098\u0001\u001a\u00020_H\u0014J$\u0010\u0099\u0001\u001a\u00020\u00102\u0007\u0010\u009a\u0001\u001a\u00020\u001f2\u0007\u0010\u009b\u0001\u001a\u00020\u001f2\u0007\u0010\u009c\u0001\u001a\u00020\u001fH\u0002J\u001d\u0010\u009d\u0001\u001a\u00020\u00102\u0007\u0010\u009e\u0001\u001a\u00020\u001f2\t\b\u0002\u0010\u009f\u0001\u001a\u00020\u0010H\u0002J\t\u0010 \u0001\u001a\u00020_H\u0002J\u001c\u0010¡\u0001\u001a\u00020_2\b\u0010¢\u0001\u001a\u00030£\u00012\u0007\u0010¤\u0001\u001a\u00020\u001dH\u0002J\t\u0010¥\u0001\u001a\u00020_H\u0002J\t\u0010¦\u0001\u001a\u00020_H\u0016J\t\u0010§\u0001\u001a\u00020\u0003H\u0014J\u0012\u0010¨\u0001\u001a\u00020\u001d2\u0007\u0010©\u0001\u001a\u00020\u0010H\u0002J\u0012\u0010ª\u0001\u001a\u00020\u001d2\u0007\u0010©\u0001\u001a\u00020\u0010H\u0002J\t\u0010«\u0001\u001a\u00020\u001dH\u0016J\u0012\u0010¬\u0001\u001a\u00020_2\u0007\u0010\u00ad\u0001\u001a\u00020\u001dH\u0002J\u0013\u0010®\u0001\u001a\u00020_2\b\u0010¢\u0001\u001a\u00030£\u0001H\u0002J'\u0010¯\u0001\u001a\u00020_2\b\u0010¢\u0001\u001a\u00030£\u00012\t\b\u0002\u0010°\u0001\u001a\u00020\u001d2\u0007\u0010¤\u0001\u001a\u00020\u001dH\u0002J\u0012\u0010±\u0001\u001a\u00020_2\u0007\u0010²\u0001\u001a\u00020\u001dH\u0016J\t\u0010³\u0001\u001a\u00020_H\u0016J\t\u0010´\u0001\u001a\u00020_H\u0014J\u0015\u0010µ\u0001\u001a\u00020_2\n\u0010¶\u0001\u001a\u0005\u0018\u00010·\u0001H\u0015J'\u0010¸\u0001\u001a\u00020_2\u0007\u0010¹\u0001\u001a\u00020\u00102\u0007\u0010º\u0001\u001a\u00020\u00102\n\u0010»\u0001\u001a\u0005\u0018\u00010¼\u0001H\u0015J\t\u0010½\u0001\u001a\u00020_H\u0016J\t\u0010¾\u0001\u001a\u00020_H\u0016J\t\u0010¿\u0001\u001a\u00020_H\u0016J\t\u0010À\u0001\u001a\u00020_H\u0016J\t\u0010Á\u0001\u001a\u00020_H\u0016J\t\u0010Â\u0001\u001a\u00020_H\u0002J\t\u0010Ã\u0001\u001a\u00020_H\u0016J\t\u0010Ä\u0001\u001a\u00020_H\u0016J\u0012\u0010Å\u0001\u001a\u00020_2\u0007\u0010\u009e\u0001\u001a\u00020\u001fH\u0016J\u0015\u0010Æ\u0001\u001a\u00020_2\n\u0010Ç\u0001\u001a\u0005\u0018\u00010È\u0001H\u0017J\u0013\u0010É\u0001\u001a\u00020_2\b\u0010Ê\u0001\u001a\u00030Ë\u0001H\u0016J\t\u0010Ì\u0001\u001a\u00020_H\u0014J\t\u0010Í\u0001\u001a\u00020_H\u0016J\t\u0010Î\u0001\u001a\u00020_H\u0016J+\u0010Ï\u0001\u001a\u00020_2\u0007\u0010\u009e\u0001\u001a\u00020\u001f2\u0006\u0010*\u001a\u00020\u00102\u0006\u00105\u001a\u00020\u00102\u0007\u0010Ð\u0001\u001a\u00020\u0010H\u0016J\u0015\u0010Ñ\u0001\u001a\u00020_2\n\u0010Ç\u0001\u001a\u0005\u0018\u00010È\u0001H\u0016J(\u0010Ò\u0001\u001a\u00020_2\n\u0010Ç\u0001\u001a\u0005\u0018\u00010È\u00012\u0011\u0010Ó\u0001\u001a\f\u0018\u00010Ô\u0001j\u0005\u0018\u0001`Õ\u0001H\u0016J\u0012\u0010Ö\u0001\u001a\u00020_2\u0007\u0010\u009e\u0001\u001a\u00020\u001fH\u0016J\u0014\u0010×\u0001\u001a\u00020_2\t\u0010Ø\u0001\u001a\u0004\u0018\u00010\u001fH\u0007J\u0012\u0010Ù\u0001\u001a\u00020_2\u0007\u0010Ú\u0001\u001a\u00020\u001fH\u0016J$\u0010Û\u0001\u001a\u00020_2\u0007\u0010Ü\u0001\u001a\u00020(2\u0007\u0010Ý\u0001\u001a\u00020(2\u0007\u0010Ú\u0001\u001a\u00020\u001fH\u0016J\u001c\u0010Þ\u0001\u001a\u00020\u001d2\u0007\u0010©\u0001\u001a\u00020\u00102\b\u0010Ø\u0001\u001a\u00030ß\u0001H\u0016J\u001e\u0010à\u0001\u001a\u00020\u001d2\u0007\u0010©\u0001\u001a\u00020\u00102\n\u0010Ø\u0001\u001a\u0005\u0018\u00010ß\u0001H\u0016J\u001c\u0010á\u0001\u001a\u00020_2\u0007\u0010â\u0001\u001a\u00020\u00102\b\u0010ã\u0001\u001a\u00030ä\u0001H\u0016J\u0013\u0010å\u0001\u001a\u00020\u001d2\b\u0010Ø\u0001\u001a\u00030æ\u0001H\u0016J\t\u0010ç\u0001\u001a\u00020_H\u0016J\t\u0010è\u0001\u001a\u00020_H\u0016J\u001d\u0010é\u0001\u001a\u00020_2\u0007\u0010ê\u0001\u001a\u00020\u00102\t\u0010ë\u0001\u001a\u0004\u0018\u00010\u0013H\u0016J\t\u0010ì\u0001\u001a\u00020_H\u0016J\u0012\u0010í\u0001\u001a\u00020_2\u0007\u0010\u009e\u0001\u001a\u00020\u001fH\u0016JQ\u0010í\u0001\u001a\u00020_2\u0006\u0010&\u001a\u00020\u001f2\u0007\u0010\u0084\u0001\u001a\u00020\u00102\u0007\u0010î\u0001\u001a\u00020\u00102\u0007\u0010\u009e\u0001\u001a\u00020\u001f2\b\u0010P\u001a\u0004\u0018\u00010\u001f2\u0007\u0010ï\u0001\u001a\u00020\u001d2\u0007\u0010Ü\u0001\u001a\u00020(2\u0007\u0010Ý\u0001\u001a\u00020(H\u0016J\t\u0010ð\u0001\u001a\u00020_H\u0014J\u001e\u0010ñ\u0001\u001a\u00020_2\n\u0010Ç\u0001\u001a\u0005\u0018\u00010È\u00012\u0007\u0010ò\u0001\u001a\u00020\u0010H\u0016J\u0011\u0010e\u001a\u00020_2\u0007\u0010ó\u0001\u001a\u00020\u001fH\u0016J\t\u0010ô\u0001\u001a\u00020_H\u0015J\u001a\u0010õ\u0001\u001a\u00020_2\u0007\u0010ó\u0001\u001a\u00020\u001f2\u0006\u0010&\u001a\u00020\u001fH\u0016J\t\u0010ö\u0001\u001a\u00020_H\u0016J\u0012\u0010÷\u0001\u001a\u00020_2\u0007\u0010\u009e\u0001\u001a\u00020\u001fH\u0016J+\u0010ø\u0001\u001a\u00020_2\u0007\u0010\u0086\u0001\u001a\u00020(2\u0007\u0010\u0087\u0001\u001a\u00020(2\u0006\u0010;\u001a\u00020(2\u0006\u0010<\u001a\u00020(H\u0016J\u0015\u0010ù\u0001\u001a\u00020_2\n\u0010Ç\u0001\u001a\u0005\u0018\u00010È\u0001H\u0016J\u001d\u0010ú\u0001\u001a\u00020\u001d2\b\u0010û\u0001\u001a\u00030ü\u00012\b\u0010Ø\u0001\u001a\u00030æ\u0001H\u0017J\u0011\u0010ý\u0001\u001a\u00020_2\b\u0010þ\u0001\u001a\u00030ÿ\u0001J\u0012\u0010\u0080\u0002\u001a\u00020_2\u0007\u0010\u0081\u0002\u001a\u00020\u001dH\u0016J\b\u0010h\u001a\u00020_H\u0016J\u0007\u0010\u0082\u0002\u001a\u00020_J\t\u0010\u0083\u0002\u001a\u00020_H\u0016J\t\u0010\u0084\u0002\u001a\u00020_H\u0016J\u0012\u0010\u0085\u0002\u001a\u00020_2\u0007\u0010\u009e\u0001\u001a\u00020\u001fH\u0016J\u0007\u0010\u0086\u0002\u001a\u00020_J\t\u0010\u0087\u0002\u001a\u00020_H\u0016J,\u0010\u0088\u0002\u001a\u00020_2\b\u0010\u0089\u0002\u001a\u00030\u008a\u00022\u0006\u00104\u001a\u00020\u001d2\u0006\u0010*\u001a\u00020\u00102\u0007\u0010\u0088\u0001\u001a\u00020\u0010H\u0016J\u0007\u0010\u008b\u0002\u001a\u00020_J\u0012\u0010\u008c\u0002\u001a\u00020_2\u0007\u0010\u008d\u0002\u001a\u00020\u001dH\u0016J\t\u0010\u008e\u0002\u001a\u00020_H\u0002J5\u0010\u008f\u0002\u001a\u00020_2\u0006\u00104\u001a\u00020\u001d2\u0006\u0010*\u001a\u00020\u00102\u0007\u0010\u0088\u0001\u001a\u00020\u00102\u0007\u0010\u0084\u0001\u001a\u00020\u00102\b\u0010P\u001a\u0004\u0018\u00010\u001fH\u0016J\u0012\u0010\u0090\u0002\u001a\u00020_2\u0007\u0010â\u0001\u001a\u00020\u0010H\u0016J\t\u0010\u0091\u0002\u001a\u00020_H\u0002J\t\u0010\u0092\u0002\u001a\u00020_H\u0016J\t\u0010\u0093\u0002\u001a\u00020_H\u0016J-\u0010\u0094\u0002\u001a\u00020_2\u0007\u0010\u0095\u0002\u001a\u00020\u00102\u0007\u0010\u0096\u0002\u001a\u00020\u001d2\u0010\u0010\u0097\u0002\u001a\u000b\u0012\u0004\u0012\u00020_\u0018\u00010\u0098\u0002H\u0016J\t\u0010\u0099\u0002\u001a\u00020_H\u0016J\t\u0010\u009a\u0002\u001a\u00020_H\u0016J\t\u0010\u009b\u0002\u001a\u00020_H\u0002J\t\u0010\u009c\u0002\u001a\u00020_H\u0002J\t\u0010\u009d\u0002\u001a\u00020_H\u0002J\u001b\u0010\u009e\u0002\u001a\u00020_2\u0007\u0010Ü\u0001\u001a\u00020(2\u0007\u0010Ý\u0001\u001a\u00020(H\u0016J$\u0010\u009f\u0002\u001a\u00020_2\u0007\u0010Ü\u0001\u001a\u00020(2\u0007\u0010Ý\u0001\u001a\u00020(2\u0007\u0010 \u0002\u001a\u00020(H\u0016J\t\u0010¡\u0002\u001a\u00020_H\u0016J4\u0010¢\u0002\u001a\u00020_2\u0007\u0010\u0086\u0001\u001a\u00020(2\u0007\u0010\u0087\u0001\u001a\u00020(2\u0006\u0010;\u001a\u00020(2\u0006\u0010<\u001a\u00020(2\u0007\u0010£\u0002\u001a\u00020\u001fH\u0016J\u001a\u0010¤\u0002\u001a\u00020_2\u0007\u0010\u0085\u0001\u001a\u00020:2\u0006\u00109\u001a\u00020:H\u0016J\t\u0010¥\u0002\u001a\u00020_H\u0002J\u001c\u0010¦\u0002\u001a\u00020\u001d2\b\u0010¢\u0001\u001a\u00030£\u00012\u0007\u0010¤\u0001\u001a\u00020\u001dH\u0002R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0017\u001a\u00020\u00028TX\u0094\u0084\u0002¢\u0006\f\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u0018\u0010\u0019R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010 \u001a\u0004\u0018\u00010!X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010#\u001a\u00020\u00102\u0006\u0010\"\u001a\u00020\u0010@BX\u0082\u000e¢\u0006\b\n\u0000\"\u0004\b$\u0010%R\u000e\u0010&\u001a\u00020\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020(X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020(X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00102\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00103\u001a\u00020\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00106\u001a\u0004\u0018\u000107X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00108\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00109\u001a\u0004\u0018\u00010:X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010;\u001a\u00020(X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010<\u001a\u00020(X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010=\u001a\u00020>X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010?\u001a\u00020@8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bC\u0010\u001b\u001a\u0004\bA\u0010BR\u000e\u0010D\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010E\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bF\u0010GR\u0014\u0010H\u001a\u00020\u001d8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bH\u0010IR\u000e\u0010J\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010K\u001a\u00020\u001d8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bK\u0010IR\u001a\u0010L\u001a\u00020\u001dX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bL\u0010I\"\u0004\bM\u0010NR\u000e\u0010O\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010P\u001a\u00020\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010Q\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010R\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010S\u001a\u00020\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010T\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010U\u001a\u0004\u0018\u00010VX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010W\u001a\u00020\u001d8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bX\u0010IR\u000e\u0010Y\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010Z\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010[\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\\\u001a\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R!\u0010]\u001a\b\u0012\u0004\u0012\u00020_0^8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bb\u0010\u001b\u001a\u0004\b`\u0010aR\u0010\u0010c\u001a\u0004\u0018\u00010dX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010e\u001a\u00020\u001dX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bf\u0010I\"\u0004\bg\u0010NR\u000e\u0010h\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010i\u001a\u0004\u0018\u00010j8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bk\u0010lR\u0014\u0010m\u001a\u00020n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bo\u0010pR!\u0010q\u001a\b\u0012\u0004\u0012\u00020_0^8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bs\u0010\u001b\u001a\u0004\br\u0010aR\u0014\u0010t\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010u\u001a\u00020vX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010w\u001a\u00020xX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010y\u001a\u00020xX\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010z\u001a\u00020{8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b~\u0010\u001b\u001a\u0004\b|\u0010}R\u000e\u0010\u007f\u001a\u00020vX\u0082\u000e¢\u0006\u0002\n\u0000R\u0017\u0010\u0080\u0001\u001a\u00020\u001f8VX\u0096\u0004¢\u0006\b\u001a\u0006\b\u0081\u0001\u0010\u0082\u0001R\u000f\u0010\u0083\u0001\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u000f\u0010\u0084\u0001\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0085\u0001\u001a\u0004\u0018\u00010:X\u0082\u000e¢\u0006\u0002\n\u0000R\u000f\u0010\u0086\u0001\u001a\u00020(X\u0082\u000e¢\u0006\u0002\n\u0000R\u000f\u0010\u0087\u0001\u001a\u00020(X\u0082\u000e¢\u0006\u0002\n\u0000R\u000f\u0010\u0088\u0001\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0089\u0001\u001a\u0005\u0018\u00010\u008a\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u008b\u0001\u001a\u00030\u008c\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u008d\u0001\u001a\u00020\u001dX\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u008e\u0001\u0010I\"\u0005\b\u008f\u0001\u0010NR\u0016\u0010\u0090\u0001\u001a\t\u0012\u0004\u0012\u00020_0\u0091\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006¨\u0002"}, d2 = {"Lcom/ykb/ebook/activity/ReadBookActivity;", "Lcom/ykb/ebook/base/BaseVmActivity;", "Lcom/ykb/ebook/databinding/ActivityReadBookBinding;", "Lcom/ykb/ebook/vm/ReadBookViewModel;", "Lcom/ykb/ebook/weight/ReadMenu$Callback;", "Lcom/ykb/ebook/weight/TextActionMenu$Callback;", "Lcom/ykb/ebook/weight/ReadView$Callback;", "Lcom/ykb/ebook/weight/ContentTextView$CallBack;", "Lcom/ykb/ebook/page/ReadBook$CallBack;", "Landroid/view/View$OnTouchListener;", "Lcom/ykb/ebook/common_interface/TimeBatteryCallback;", "Lcom/ykb/ebook/common_interface/LayoutProgressListener;", "Lcom/ykb/ebook/common/EventCallback;", "Lcom/hjq/http/listener/OnDownloadListener;", "()V", "actionMenuY", "", "addDrawLineObserver", "Landroidx/lifecycle/Observer;", "", "addNoteObser", "backupJob", "Lkotlinx/coroutines/Job;", "binding", "getBinding", "()Lcom/ykb/ebook/databinding/ActivityReadBookBinding;", "binding$delegate", "Lkotlin/Lazy;", "bookChanged", "", "bookId", "", "bookInfo", "Lcom/ykb/ebook/model/BookInfo;", "value", "bottomDialog", "setBottomDialog", "(I)V", "chapterId", "clickNoteX", "", "clickNoteY", "color", "currentChapter", "currentPage", "cursorHandleFlag", "deleteDrawLineObser", "drawContent", "drawLine", "drawLineFromNote", "drawLineOperate", "drawLineText", "drawOrCancel", "drawType", "editUnderLineInfo", "Lcom/ykb/ebook/model/EditUnderLineInfo;", "endSelectStartPosition", "endTextPosition", "Lcom/ykb/ebook/model/TextPosition;", "endX", "endY", "executor", "Ljava/util/concurrent/ExecutorService;", "handler", "Landroid/os/Handler;", "getHandler", "()Landroid/os/Handler;", "handler$delegate", "hasNote", "headerHeight", "getHeaderHeight", "()I", "isInitFinish", "()Z", "isPubComment", "isScroll", "isSelectingSearchResult", "setSelectingSearchResult", "(Z)V", "lineColor", "lineId", "lineRangeSelect", "lineRangeStartPosition", "lineText", "loadStates", "loadingView", "Lcom/lxj/xpopup/impl/LoadingPopupView;", "menuLayoutIsVisible", "getMenuLayoutIsVisible", "mergeAddLine", "mergeContent", "moveSelectStart", "mviewModel", "nextPageDebounce", "Lcom/ykb/ebook/util/Debounce;", "", "getNextPageDebounce", "()Lcom/ykb/ebook/util/Debounce;", "nextPageDebounce$delegate", "noteDialog", "Landroidx/appcompat/app/AppCompatDialog;", "onQuestionClick", "getOnQuestionClick", "setOnQuestionClick", "pageChanged", "pageDelegate", "Lcom/ykb/ebook/page/delegate/PageDelegate;", "getPageDelegate", "()Lcom/ykb/ebook/page/delegate/PageDelegate;", "pageFactory", "Lcom/ykb/ebook/page/TextPageFactory;", "getPageFactory", "()Lcom/ykb/ebook/page/TextPageFactory;", "prevPageDebounce", "getPrevPageDebounce", "prevPageDebounce$delegate", "questionListOb", "readTime", "", "readTimer", "Lcom/ykb/ebook/task/BasicCountDownTimer;", "restTimer", "screenOffRunnable", "Ljava/lang/Runnable;", "getScreenOffRunnable", "()Ljava/lang/Runnable;", "screenOffRunnable$delegate", "screenTimeOut", "selectedText", "getSelectedText", "()Ljava/lang/String;", "sort", "startPosition", "startTextPosition", "startX", "startY", TtmlNode.TAG_STYLE, "textActionMenu", "Lcom/ykb/ebook/weight/TextActionMenu;", "timeBatteryReceiver", "Lcom/ykb/ebook/receiver/TimeBatteryReceiver;", "toUnlock", "getToUnlock", "setToUnlock", "upSeekBarThrottle", "Lcom/ykb/ebook/util/Throttle;", "backgroundChange", "batterBack", DBHelpTool.RecordEntry.COLUMN_NAME_LEVEL, "checkSelectTextHasDrawLine", "contentLoadFinish", "dismissFunctionMenu", "doBusiness", "getJumpToChapterPos", "chapterString", "searchString", "keyWord", "getTextChapterPositionIndex", "text", "startIndex", "gotoLastReadPosition", "handleKeyPage", HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION, "Lcom/ykb/ebook/page/PageDirection;", "longPress", "hideSystemUI", "initStatusBar", "initViewModel", "isNextKey", "keyCode", "isPrevKey", "isShowActionMenu", AdUnitActivity.EXTRA_KEEP_SCREEN_ON, DebugKt.DEBUG_PROPERTY_VALUE_ON, "keyPage", "keyPageDebounce", "mouseWheel", "loadingImage", "isLoadingImage", "notifyBookChanged", "observeViewModel", "onActivityCreated", "savedInstanceState", "Landroid/os/Bundle;", "onActivityResult", Constant.LOGIN_ACTIVITY_REQUEST_CODE, "resultCode", "data", "Landroid/content/Intent;", "onAddLibraryClick", "onAllTagClick", "onBackClick", "onBookReadOver", "onBookReviewClick", "onBottomDialogChange", "onCancelSelect", "onChapterListClick", "onCommentClick", "onComplete", "file", "Ljava/io/File;", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "onDestroy", "onDismissActionMenu", "onDownloadClick", "onDrawLineBack", "operator", "onEnd", "onError", AliyunLogKey.KEY_EVENT, "Ljava/lang/Exception;", "Lkotlin/Exception;", "onErrorClick", "onEvent", NotificationCompat.CATEGORY_EVENT, "onImageClick", "src", "onImageLongPress", "x", "y", "onKeyDown", "Landroid/view/KeyEvent;", "onKeyUp", "onLayoutPageCompleted", "index", Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "Lcom/ykb/ebook/model/TextPage;", "onLongScreenshotTouchEvent", "Landroid/view/MotionEvent;", "onMenuHide", "onMenuShow", "onMessage", "what", Languages.ANY, "onMoreSettingClick", "onNoteClick", SessionDescription.ATTR_LENGTH, "hasUnderLine", "onPause", "onProgress", "progress", "id", "onResume", "onReviewClick", "onShareBookClick", "onShareClick", "onShowActionMenu", "onStart", "onTouch", "v", "Landroid/view/View;", "onUnlockClick", "payWay", "Lcom/ykb/ebook/model/Ways;", "onWindowFocusChanged", "hasFocus", "readFirstPageContent", "screenOffTimerStart", "scrollAnimChange", "sendToClip", "setLibraryState", "showActionMenu", "showDrawLineTextActionMenu", "column", "Lcom/ykb/ebook/page/column/TextColumn;", "showGuidDialog", "showHideContinueRead", "show", "showReadMenuView", "showTextActionMenu", "skipToChapter", "startBackupJob", "timeBack", "uiConfigChange", "upContent", "relativePosition", "resetPageOffset", "success", "Lkotlin/Function0;", "upMenuView", "upPageAnim", "upReadProgress", "upScreenTimeOut", "upSeekBarProgress", "upSelectedEnd", "upSelectedStart", PLVDanmakuInfo.FONTMODE_TOP, "upSystemUiVisibility", "updateSelectCursor", "selectText", "updateSelectTextPosition", "updateStatusBar", "volumeKeyPage", "Companion", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nReadBookActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ReadBookActivity.kt\ncom/ykb/ebook/activity/ReadBookActivity\n+ 2 ActivityViewBindings.kt\ncom/ykb/ebook/extensions/ActivityViewBindingsKt\n+ 3 View.kt\nandroidx/core/view/ViewKt\n+ 4 ColorResources.kt\nsplitties/resources/ColorResourcesKt\n+ 5 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 6 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 7 ContextExtensions.kt\ncom/ykb/ebook/extensions/ContextExtensionsKt\n+ 8 SystemServices.kt\nsplitties/systemservices/SystemServicesKt\n*L\n1#1,2671:1\n13#2,10:2672\n252#3:2682\n42#4:2683\n42#4:2684\n42#4:2688\n42#4:2689\n1#5:2685\n1855#6,2:2686\n1855#6,2:2690\n1855#6,2:2697\n1855#6,2:2699\n1855#6,2:2701\n1855#6:2703\n1855#6,2:2704\n1856#6:2706\n1855#6,2:2707\n1855#6,2:2709\n1855#6:2711\n1855#6,2:2712\n1856#6:2714\n1855#6,2:2715\n1855#6,2:2717\n1855#6,2:2719\n350#6,7:2721\n1864#6,2:2728\n350#6,7:2730\n1866#6:2737\n350#6,7:2738\n1855#6:2745\n1855#6,2:2746\n1856#6:2748\n26#7,4:2692\n188#8:2696\n*S KotlinDebug\n*F\n+ 1 ReadBookActivity.kt\ncom/ykb/ebook/activity/ReadBookActivity\n*L\n151#1:2672,10\n160#1:2682\n351#1:2683\n353#1:2684\n747#1:2688\n748#1:2689\n453#1:2686,2\n814#1:2690,2\n1327#1:2697,2\n1362#1:2699,2\n1473#1:2701,2\n1499#1:2703\n1500#1:2704,2\n1499#1:2706\n1510#1:2707,2\n1526#1:2709,2\n1576#1:2711\n1577#1:2712,2\n1576#1:2714\n1734#1:2715,2\n1802#1:2717,2\n1927#1:2719,2\n2190#1:2721,7\n2196#1:2728,2\n2202#1:2730,7\n2196#1:2737\n2220#1:2738,7\n2117#1:2745\n2129#1:2746,2\n2117#1:2748\n1092#1:2692,4\n1112#1:2696\n*E\n"})
/* loaded from: classes6.dex */
public final class ReadBookActivity extends BaseVmActivity<ActivityReadBookBinding, ReadBookViewModel> implements ReadMenu.Callback, TextActionMenu.Callback, ReadView.Callback, ContentTextView.CallBack, ReadBook.CallBack, View.OnTouchListener, TimeBatteryCallback, LayoutProgressListener, EventCallback, OnDownloadListener {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    public static final int LINE_OPERATION_ADD = 0;
    public static final int LINE_OPERATION_DELETE = 1;
    public static final int LINE_OPERATION_EDIT = 2;
    private int actionMenuY;
    private Observer<Object> addDrawLineObserver;
    private Observer<Object> addNoteObser;

    @Nullable
    private Job backupJob;

    /* renamed from: binding$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy binding;
    private boolean bookChanged;

    @Nullable
    private BookInfo bookInfo;
    private int bottomDialog;
    private float clickNoteX;
    private float clickNoteY;
    private int color;
    private int currentPage;
    private boolean cursorHandleFlag;
    private Observer<Object> deleteDrawLineObser;
    private boolean drawLine;
    private boolean drawLineFromNote;
    private int drawLineOperate;
    private boolean drawOrCancel;
    private int drawType;

    @Nullable
    private EditUnderLineInfo editUnderLineInfo;
    private int endSelectStartPosition;

    @Nullable
    private TextPosition endTextPosition;
    private float endX;
    private float endY;
    private boolean hasNote;
    private boolean isPubComment;
    private boolean isSelectingSearchResult;
    private int lineColor;
    private boolean lineRangeSelect;
    private boolean loadStates;

    @Nullable
    private LoadingPopupView loadingView;
    private boolean mergeAddLine;
    private boolean mergeContent;
    private boolean moveSelectStart;

    @Nullable
    private ReadBookViewModel mviewModel;

    @Nullable
    private AppCompatDialog noteDialog;
    private boolean onQuestionClick;
    private boolean pageChanged;
    private Observer<Object> questionListOb;
    private long readTime;
    private BasicCountDownTimer readTimer;
    private BasicCountDownTimer restTimer;
    private long screenTimeOut;
    private boolean sort;

    @Nullable
    private TextPosition startTextPosition;
    private float startX;
    private float startY;
    private int style;

    @Nullable
    private TextActionMenu textActionMenu;
    private boolean toUnlock;

    @NotNull
    private String currentChapter = "";

    @NotNull
    private final TimeBatteryReceiver timeBatteryReceiver = new TimeBatteryReceiver(this);

    @NotNull
    private final ExecutorService executor = ReadBook.INSTANCE.getExecutor();

    /* renamed from: screenOffRunnable$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy screenOffRunnable = LazyKt__LazyJVMKt.lazy(new ReadBookActivity$screenOffRunnable$2(this));

    /* renamed from: handler$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy handler = LazyKt__LazyJVMKt.lazy(new Function0<Handler>() { // from class: com.ykb.ebook.activity.ReadBookActivity$handler$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        @NotNull
        public final Handler invoke() {
            return HandlerUtilsKt.buildMainHandler();
        }
    });

    /* renamed from: nextPageDebounce$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy nextPageDebounce = LazyKt__LazyJVMKt.lazy(new Function0<Debounce<Unit>>() { // from class: com.ykb.ebook.activity.ReadBookActivity$nextPageDebounce$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        @NotNull
        public final Debounce<Unit> invoke() {
            final ReadBookActivity readBookActivity = this.this$0;
            return new Debounce<>(0L, 0L, false, false, new Function0<Unit>() { // from class: com.ykb.ebook.activity.ReadBookActivity$nextPageDebounce$2.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    readBookActivity.keyPage(PageDirection.NEXT);
                }
            }, 15, null);
        }
    });

    /* renamed from: prevPageDebounce$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy prevPageDebounce = LazyKt__LazyJVMKt.lazy(new Function0<Debounce<Unit>>() { // from class: com.ykb.ebook.activity.ReadBookActivity$prevPageDebounce$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        @NotNull
        public final Debounce<Unit> invoke() {
            final ReadBookActivity readBookActivity = this.this$0;
            return new Debounce<>(0L, 0L, false, false, new Function0<Unit>() { // from class: com.ykb.ebook.activity.ReadBookActivity$prevPageDebounce$2.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    readBookActivity.keyPage(PageDirection.PREV);
                }
            }, 15, null);
        }
    });

    @NotNull
    private final Throttle<Unit> upSeekBarThrottle = ThrottleKt.throttle$default(200, false, false, new ReadBookActivity$upSeekBarThrottle$1(this), 6, null);

    @NotNull
    private String bookId = "";
    private int startPosition = -1;

    @NotNull
    private String drawLineText = "";

    @NotNull
    private String chapterId = "";

    @NotNull
    private String lineId = "";
    private int lineRangeStartPosition = -1;

    @NotNull
    private String lineText = "";

    @NotNull
    private String drawContent = "";

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[PageDirection.values().length];
            try {
                iArr[PageDirection.NEXT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[PageDirection.PREV.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", AdvanceSetting.NETWORK_TYPE, "Lcom/ykb/ebook/model/BookInfo;", "kotlin.jvm.PlatformType", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.ykb.ebook.activity.ReadBookActivity$observeViewModel$2, reason: invalid class name */
    public static final class AnonymousClass2 extends Lambda implements Function1<BookInfo, Unit> {
        public AnonymousClass2() {
            super(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(ReadBookActivity this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            FrameLayout root = this$0.getBinding().getRoot();
            Intrinsics.checkNotNullExpressionValue(root, "binding.root");
            ViewExtensionsKt.visible(root);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(BookInfo bookInfo) throws SecurityException, NumberFormatException {
            invoke2(bookInfo);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(BookInfo bookInfo) throws SecurityException, NumberFormatException {
            ReadBookActivity.this.bookInfo = bookInfo;
            ReadBookActivity.this.getBinding().readMenu.setCommentNumber(bookInfo.getBookReviewCount());
            String str = ReadBookActivity.this.getIntent().getStringExtra("user_id") + '_' + ReadBookActivity.this.bookId + "_READ_PAGE";
            String str2 = ReadBookActivity.this.getIntent().getStringExtra("user_id") + '_' + ReadBookActivity.this.bookId + "_READ_CHAPTER";
            int prefInt = ContextExtensionsKt.getPrefInt(AppCtxKt.getAppCtx(), str, -1);
            if (ContextExtensionsKt.getPrefString(AppCtxKt.getAppCtx(), str2, null) == null || prefInt < 0) {
                ReadConfig readConfig = ReadConfig.INSTANCE;
                readConfig.setShowFirstPage(true);
                readConfig.setFirstPage(true);
                readConfig.setLastPage(false);
                ReadBook.loadContent$default(ReadBook.INSTANCE, true, false, null, 4, null);
            } else {
                ReadConfig.INSTANCE.setFirstPage(false);
                ReadBookActivity.this.gotoLastReadPosition();
            }
            ReadBookActivity.this.getBinding().readMenu.setAddLibaaryTv();
            Handler handler = ReadBookActivity.this.getHandler();
            final ReadBookActivity readBookActivity = ReadBookActivity.this;
            handler.postDelayed(new Runnable() { // from class: com.ykb.ebook.activity.q1
                @Override // java.lang.Runnable
                public final void run() {
                    ReadBookActivity.AnonymousClass2.invoke$lambda$0(readBookActivity);
                }
            }, 50L);
            ReadBookActivity.this.getBinding().readMenu.setReadTime(Long.parseLong(bookInfo.getPerusalDuration().getDuration()));
            ReadBookActivity.this.hideLoading();
        }
    }

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "Lcom/ykb/ebook/base/BaseResponse;", "Lcom/ykb/ebook/base/BaseListResponse;", "Lcom/ykb/ebook/model/CorrectType;", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.activity.ReadBookActivity$onErrorClick$1", f = "ReadBookActivity.kt", i = {}, l = {R2.attr.defaultCheckedHolidayTextColor}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.activity.ReadBookActivity$onErrorClick$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10231 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super BaseResponse<BaseListResponse<CorrectType>>>, Object> {
        int label;

        public C10231(Continuation<? super C10231> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C10231(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super BaseResponse<BaseListResponse<CorrectType>>> continuation) {
            return ((C10231) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                ApiService api = ApiServiceKt.getAPI();
                Map<String, String> mapEmptyMap = MapsKt__MapsKt.emptyMap();
                this.label = 1;
                obj = api.correctTypeList(mapEmptyMap, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return obj;
        }
    }

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0006\u001a\u00020\u0005*\u00020\u00002\u0012\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "Lcom/ykb/ebook/base/BaseResponse;", "Lcom/ykb/ebook/base/BaseListResponse;", "Lcom/ykb/ebook/model/CorrectType;", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.activity.ReadBookActivity$onErrorClick$2", f = "ReadBookActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.activity.ReadBookActivity$onErrorClick$2, reason: invalid class name and case insensitive filesystem */
    public static final class C10242 extends SuspendLambda implements Function3<CoroutineScope, BaseResponse<BaseListResponse<CorrectType>>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Ref.ObjectRef<String> $drawContent;
        final /* synthetic */ Ref.IntRef $posIndex;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10242(Ref.ObjectRef<String> objectRef, Ref.IntRef intRef, Continuation<? super C10242> continuation) {
            super(3, continuation);
            this.$drawContent = objectRef;
            this.$posIndex = intRef;
        }

        @Override // kotlin.jvm.functions.Function3
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @NotNull BaseResponse<BaseListResponse<CorrectType>> baseResponse, @Nullable Continuation<? super Unit> continuation) {
            C10242 c10242 = ReadBookActivity.this.new C10242(this.$drawContent, this.$posIndex, continuation);
            c10242.L$0 = baseResponse;
            return c10242.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            List<CorrectType> listEmptyList;
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            BaseResponse baseResponse = (BaseResponse) this.L$0;
            WriteCorrectionDialog.Builder citation = new WriteCorrectionDialog.Builder(ReadBookActivity.this, true).setCitation(this.$drawContent.element);
            BaseListResponse baseListResponse = (BaseListResponse) baseResponse.getData();
            if (baseListResponse == null || (listEmptyList = baseListResponse.getList()) == null) {
                listEmptyList = CollectionsKt__CollectionsKt.emptyList();
            }
            WriteCorrectionDialog.Builder correctData = citation.setCorrectData(listEmptyList);
            final ReadBookActivity readBookActivity = ReadBookActivity.this;
            final Ref.ObjectRef<String> objectRef = this.$drawContent;
            final Ref.IntRef intRef = this.$posIndex;
            correctData.setPublishClick(new Function2<String, String, Unit>() { // from class: com.ykb.ebook.activity.ReadBookActivity.onErrorClick.2.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(String str, String str2) {
                    invoke2(str, str2);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull String id, @NotNull String content) {
                    Intrinsics.checkNotNullParameter(id, "id");
                    Intrinsics.checkNotNullParameter(content, "content");
                    readBookActivity.getViewModel().addCorrect(MapsKt__MapsKt.hashMapOf(new Pair("book_id", readBookActivity.bookId), new Pair("chapter_id", readBookActivity.getBinding().readView.getCurPage().getTextChapter().getChapter().getId()), new Pair("draw_content", objectRef.element), new Pair(PreferKeyKt.START_POSITION, String.valueOf(intRef.element)), new Pair(SessionDescription.ATTR_LENGTH, String.valueOf(objectRef.element.length())), new Pair("correct_content", content), new Pair("correct_category_id", id)));
                }
            }).show();
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003*\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u0001H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.activity.ReadBookActivity$onErrorClick$3", f = "ReadBookActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.activity.ReadBookActivity$onErrorClick$3, reason: invalid class name and case insensitive filesystem */
    public static final class C10253 extends SuspendLambda implements Function3<CoroutineScope, Throwable, Continuation<? super Unit>, Object> {
        int label;

        public C10253(Continuation<? super C10253> continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
            return ReadBookActivity.this.new C10253(continuation).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            if (!ReadBookActivity.this.getViewModel().getToastReShow()) {
                ToastUtilsKt.toastOnUi$default(ReadBookActivity.this, "纠错类型获取失败", 0, 2, (Object) null);
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.activity.ReadBookActivity$startBackupJob$1", f = "ReadBookActivity.kt", i = {0}, l = {R2.attr.maxLine}, m = "invokeSuspend", n = {"$this$launch"}, s = {"L$0"})
    /* renamed from: com.ykb.ebook.activity.ReadBookActivity$startBackupJob$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10311 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        public C10311(Continuation<? super C10311> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10311 c10311 = new C10311(continuation);
            c10311.L$0 = obj;
            return c10311;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C10311) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            CoroutineScope coroutineScope;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope2 = (CoroutineScope) this.L$0;
                this.L$0 = coroutineScope2;
                this.label = 1;
                if (DelayKt.delay(300000L, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                coroutineScope = coroutineScope2;
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                coroutineScope = (CoroutineScope) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            if (ReadBook.INSTANCE.getBook() != null) {
                CoroutineScopeKt.ensureActive(coroutineScope);
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.activity.ReadBookActivity$upContent$1", f = "ReadBookActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.activity.ReadBookActivity$upContent$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10321 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $relativePosition;
        final /* synthetic */ boolean $resetPageOffset;
        final /* synthetic */ Function0<Unit> $success;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10321(int i2, boolean z2, Function0<Unit> function0, Continuation<? super C10321> continuation) {
            super(2, continuation);
            this.$relativePosition = i2;
            this.$resetPageOffset = z2;
            this.$success = function0;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return ReadBookActivity.this.new C10321(this.$relativePosition, this.$resetPageOffset, this.$success, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C10321) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws SecurityException, NumberFormatException {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ReadBookActivity.this.getBinding().readView.upContent(this.$relativePosition, this.$resetPageOffset);
            if (this.$relativePosition == 0) {
                ReadBookActivity.this.upSeekBarProgress();
            }
            ReadBookActivity.this.loadStates = false;
            Function0<Unit> function0 = this.$success;
            if (function0 != null) {
                function0.invoke();
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.ykb.ebook.activity.ReadBookActivity$upPageAnim$1", f = "ReadBookActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.ykb.ebook.activity.ReadBookActivity$upPageAnim$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10331 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C10331(Continuation<? super C10331> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return ReadBookActivity.this.new C10331(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C10331) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ReadBookActivity.this.getBinding().readView.upPageAnim();
            return Unit.INSTANCE;
        }
    }

    public ReadBookActivity() {
        final boolean z2 = false;
        this.binding = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, (Function0) new Function0<ActivityReadBookBinding>() { // from class: com.ykb.ebook.activity.ReadBookActivity$special$$inlined$viewBindingActivity$default$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final ActivityReadBookBinding invoke() {
                LayoutInflater layoutInflater = this.getLayoutInflater();
                Intrinsics.checkNotNullExpressionValue(layoutInflater, "layoutInflater");
                ActivityReadBookBinding activityReadBookBindingInflate = ActivityReadBookBinding.inflate(layoutInflater);
                if (z2) {
                    this.setContentView(activityReadBookBindingInflate.getRoot());
                }
                return activityReadBookBindingInflate;
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:83:0x02c9  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x031c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final com.ykb.ebook.model.EditUnderLineInfo checkSelectTextHasDrawLine() {
        /*
            Method dump skipped, instructions count: 820
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ykb.ebook.activity.ReadBookActivity.checkSelectTextHasDrawLine():com.ykb.ebook.model.EditUnderLineInfo");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void contentLoadFinish$lambda$55(ReadBookActivity this$0) throws SecurityException, NumberFormatException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.upSystemUiVisibility();
        this$0.getBinding().readView.updateThemeStyle(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Handler getHandler() {
        return (Handler) this.handler.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int getJumpToChapterPos(String chapterString, String searchString, String keyWord) {
        String strReplace$default = StringsKt__StringsJVMKt.replace$default(StringsKt__StringsJVMKt.replace$default(chapterString, "\n", "", false, 4, (Object) null), " ", "", false, 4, (Object) null);
        int iIndexOf$default = StringsKt__StringsKt.indexOf$default((CharSequence) searchString, keyWord, 0, false, 6, (Object) null);
        String strSubstring = searchString.substring(0, searchString.length() - 1);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
        if (!StringsKt__StringsKt.contains$default((CharSequence) strReplace$default, (CharSequence) strSubstring, false, 2, (Object) null)) {
            return 0;
        }
        int iIndexOf$default2 = StringsKt__StringsKt.indexOf$default((CharSequence) strReplace$default, strSubstring, 0, false, 6, (Object) null) + iIndexOf$default + 0;
        Log.INSTANCE.logD("TEST", "搜索的TextColumn：pos: " + iIndexOf$default2);
        return iIndexOf$default2;
    }

    private final boolean getMenuLayoutIsVisible() {
        if (this.bottomDialog > 0) {
            return true;
        }
        ReadMenu readMenu = getBinding().readMenu;
        Intrinsics.checkNotNullExpressionValue(readMenu, "binding.readMenu");
        return readMenu.getVisibility() == 0;
    }

    private final Debounce<Unit> getNextPageDebounce() {
        return (Debounce) this.nextPageDebounce.getValue();
    }

    private final Debounce<Unit> getPrevPageDebounce() {
        return (Debounce) this.prevPageDebounce.getValue();
    }

    private final Runnable getScreenOffRunnable() {
        return (Runnable) this.screenOffRunnable.getValue();
    }

    private final int getTextChapterPositionIndex(String text, int startIndex) {
        TextChapter textChapter = getBinding().readView.getCurPage().getTextChapter();
        String handleContent = getBinding().readView.getCurPage().getTextPage().getTextChapter().getChapter().getHandleContent();
        return (StringsKt__StringsKt.contains$default((CharSequence) text, (CharSequence) "\n", false, 2, (Object) null) ? StringsKt__StringsKt.indexOf$default((CharSequence) handleContent, (String) StringsKt__StringsKt.split$default((CharSequence) text, new String[]{"\n"}, false, 0, 6, (Object) null).get(0), startIndex, false, 4, (Object) null) : StringsKt__StringsKt.indexOf$default((CharSequence) handleContent, text, startIndex, false, 4, (Object) null)) + (textChapter.getPages().isEmpty() ^ true ? textChapter.getPages().get(0).getTitle().length() : 0);
    }

    public static /* synthetic */ int getTextChapterPositionIndex$default(ReadBookActivity readBookActivity, String str, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = 0;
        }
        return readBookActivity.getTextChapterPositionIndex(str, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00fd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void gotoLastReadPosition() {
        /*
            r14 = this;
            com.ykb.ebook.model.BookInfo r0 = r14.bookInfo
            if (r0 == 0) goto L112
            android.content.Intent r1 = r14.getIntent()
            java.lang.String r2 = "chapterId"
            java.lang.String r1 = r1.getStringExtra(r2)
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            java.lang.String r3 = "_READ_CHAPTER"
            java.lang.String r4 = "_READ_PAGE"
            r5 = 95
            java.lang.String r6 = "user_id"
            r7 = 0
            if (r1 != 0) goto L7c
            android.content.Context r1 = android.content.AppCtxKt.getAppCtx()
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            android.content.Intent r9 = r14.getIntent()
            java.lang.String r9 = r9.getStringExtra(r6)
            r8.append(r9)
            r8.append(r5)
            java.lang.String r9 = r14.bookId
            r8.append(r9)
            r8.append(r3)
            java.lang.String r8 = r8.toString()
            android.content.Intent r9 = r14.getIntent()
            java.lang.String r2 = r9.getStringExtra(r2)
            com.ykb.ebook.extensions.ContextExtensionsKt.putPrefString(r1, r8, r2)
            android.content.Context r1 = android.content.AppCtxKt.getAppCtx()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            android.content.Intent r8 = r14.getIntent()
            java.lang.String r8 = r8.getStringExtra(r6)
            r2.append(r8)
            r2.append(r5)
            java.lang.String r8 = r14.bookId
            r2.append(r8)
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            android.content.Intent r8 = r14.getIntent()
            java.lang.String r9 = "position"
            int r8 = r8.getIntExtra(r9, r7)
            com.ykb.ebook.extensions.ContextExtensionsKt.putPrefInt(r1, r2, r8)
        L7c:
            android.content.Context r1 = android.content.AppCtxKt.getAppCtx()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            android.content.Intent r8 = r14.getIntent()
            java.lang.String r8 = r8.getStringExtra(r6)
            r2.append(r8)
            r2.append(r5)
            java.lang.String r8 = r14.bookId
            r2.append(r8)
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            int r10 = com.ykb.ebook.extensions.ContextExtensionsKt.getPrefInt(r1, r2, r7)
            android.content.Context r1 = android.content.AppCtxKt.getAppCtx()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            android.content.Intent r4 = r14.getIntent()
            java.lang.String r4 = r4.getStringExtra(r6)
            r2.append(r4)
            r2.append(r5)
            java.lang.String r4 = r14.bookId
            r2.append(r4)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r3 = 0
            java.lang.String r1 = com.ykb.ebook.extensions.ContextExtensionsKt.getPrefString(r1, r2, r3)
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 != 0) goto Lfd
            java.util.List r0 = r0.getChapterList()
            java.lang.Iterable r0 = (java.lang.Iterable) r0
            java.util.Iterator r0 = r0.iterator()
        Ldb:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto Lf3
            java.lang.Object r2 = r0.next()
            r4 = r2
            com.ykb.ebook.model.Chapter r4 = (com.ykb.ebook.model.Chapter) r4
            java.lang.String r4 = r4.getId()
            boolean r4 = android.text.TextUtils.equals(r4, r1)
            if (r4 == 0) goto Ldb
            r3 = r2
        Lf3:
            com.ykb.ebook.model.Chapter r3 = (com.ykb.ebook.model.Chapter) r3
            if (r3 == 0) goto Lfd
            int r0 = r3.getIndex()
            r9 = r0
            goto Lfe
        Lfd:
            r9 = r7
        Lfe:
            com.ykb.ebook.common.ReadConfig r0 = com.ykb.ebook.common.ReadConfig.INSTANCE
            r0.setFirstPage(r7)
            r0.setShowFirstPage(r7)
            if (r9 < 0) goto L112
            if (r10 < 0) goto L112
            com.ykb.ebook.page.ReadBook r8 = com.ykb.ebook.page.ReadBook.INSTANCE
            r11 = 0
            r12 = 4
            r13 = 0
            com.ykb.ebook.page.ReadBook.openChapter$default(r8, r9, r10, r11, r12, r13)
        L112:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ykb.ebook.activity.ReadBookActivity.gotoLastReadPosition():void");
    }

    private final void handleKeyPage(PageDirection direction, boolean longPress) {
        if (ReadConfig.INSTANCE.getKeyPageOnLongPress() || direction == PageDirection.NONE) {
            keyPage(direction);
        } else {
            keyPageDebounce$default(this, direction, false, longPress, 2, null);
        }
        this.cursorHandleFlag = false;
    }

    private final void hideSystemUI() {
        int iColor;
        Window window = getWindow();
        ReadConfig readConfig = ReadConfig.INSTANCE;
        if (readConfig.getColorMode() == 1) {
            iColor = AppCtxKt.getAppCtx().getColor(R.color.color_EDE2C3);
        } else if (readConfig.getColorMode() != 2) {
            iColor = ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_f9fafb);
        } else {
            iColor = ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_171C2D);
        }
        window.setNavigationBarColor(iColor);
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 30) {
            getWindow().setDecorFitsSystemWindows(false);
            WindowInsetsController insetsController = getWindow().getInsetsController();
            if (insetsController != null) {
                insetsController.hide(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
                insetsController.setSystemBarsBehavior(2);
            }
        } else {
            getWindow().getDecorView().setSystemUiVisibility(4098);
        }
        if (i2 >= 28) {
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            Intrinsics.checkNotNullExpressionValue(attributes, "getWindow().getAttributes()");
            attributes.layoutInDisplayCutoutMode = 1;
            getWindow().setAttributes(attributes);
        }
    }

    private final boolean isNextKey(int keyCode) {
        String prefString$default;
        List listSplit$default;
        if (keyCode == 0 || (prefString$default = ContextExtensionsKt.getPrefString$default(this, PreferKeyKt.NEXT_KEYS, null, 2, null)) == null || (listSplit$default = StringsKt__StringsKt.split$default((CharSequence) prefString$default, new String[]{","}, false, 0, 6, (Object) null)) == null) {
            return false;
        }
        return listSplit$default.contains(String.valueOf(keyCode));
    }

    private final boolean isPrevKey(int keyCode) {
        String prefString$default;
        List listSplit$default;
        if (keyCode == 0 || (prefString$default = ContextExtensionsKt.getPrefString$default(this, PreferKeyKt.PREV_KEYS, null, 2, null)) == null || (listSplit$default = StringsKt__StringsKt.split$default((CharSequence) prefString$default, new String[]{","}, false, 0, 6, (Object) null)) == null) {
            return false;
        }
        return listSplit$default.contains(String.valueOf(keyCode));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void keepScreenOn(boolean on) {
        if (on == ((getWindow().getAttributes().flags & 128) != 0)) {
            return;
        }
        if (on) {
            getWindow().addFlags(128);
        } else {
            getWindow().clearFlags(128);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void keyPage(PageDirection direction) {
        getBinding().readView.cancelSelect();
        PageDelegate pageDelegate = getBinding().readView.getPageDelegate();
        if (pageDelegate != null) {
            pageDelegate.setCancel(false);
        }
        PageDelegate pageDelegate2 = getBinding().readView.getPageDelegate();
        if (pageDelegate2 != null) {
            pageDelegate2.keyTurnPage(direction);
        }
    }

    private final void keyPageDebounce(PageDirection direction, boolean mouseWheel, boolean longPress) {
        if (longPress) {
            return;
        }
        Debounce<Unit> nextPageDebounce = getNextPageDebounce();
        nextPageDebounce.setWait(200L);
        nextPageDebounce.setLeading(!mouseWheel);
        nextPageDebounce.setTrailing(mouseWheel);
        Debounce<Unit> prevPageDebounce = getPrevPageDebounce();
        prevPageDebounce.setWait(200L);
        prevPageDebounce.setLeading(!mouseWheel);
        prevPageDebounce.setTrailing(mouseWheel);
        int i2 = WhenMappings.$EnumSwitchMapping$0[direction.ordinal()];
        if (i2 == 1) {
            getNextPageDebounce().invoke();
        } else if (i2 != 2) {
            Unit unit = Unit.INSTANCE;
        } else {
            getPrevPageDebounce().invoke();
        }
    }

    public static /* synthetic */ void keyPageDebounce$default(ReadBookActivity readBookActivity, PageDirection pageDirection, boolean z2, boolean z3, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        readBookActivity.keyPageDebounce(pageDirection, z2, z3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$10(ReadBookActivity this$0, Object obj) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        TextActionMenu textActionMenu = this$0.textActionMenu;
        if (textActionMenu != null) {
            textActionMenu.editLineSuccess();
        }
        ReadBook.loadContent$default(ReadBook.INSTANCE, false, false, null, 4, null);
        if (this$0.getViewModel().getToastReShow()) {
            return;
        }
        ToastUtilsKt.toastOnUi$default(this$0, "编辑成功！", 0, 2, (Object) null);
        ImageView imageView = this$0.getBinding().cursorLeft;
        Intrinsics.checkNotNullExpressionValue(imageView, "binding.cursorLeft");
        ViewExtensionsKt.invisible(imageView);
        ImageView imageView2 = this$0.getBinding().cursorRight;
        Intrinsics.checkNotNullExpressionValue(imageView2, "binding.cursorRight");
        ViewExtensionsKt.invisible(imageView2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$11(ReadBookActivity this$0, Object obj) {
        Pair<Integer, Integer> offset;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (!this$0.getViewModel().getToastReShow()) {
            ToastUtilsKt.toastOnUi$default(this$0, "取消成功！", 0, 2, (Object) null);
        }
        if (this$0.drawLineFromNote) {
            TextActionMenu textActionMenu = this$0.textActionMenu;
            if (textActionMenu != null) {
                View view = this$0.getBinding().textMenuPosition;
                Intrinsics.checkNotNullExpressionValue(view, "binding.textMenuPosition");
                TextActionMenu textActionMenu2 = this$0.textActionMenu;
                Pair<Integer, Integer> offset2 = textActionMenu2 != null ? textActionMenu2.getOffset() : null;
                Intrinsics.checkNotNull(offset2);
                int iIntValue = offset2.getFirst().intValue();
                TextActionMenu textActionMenu3 = this$0.textActionMenu;
                offset = textActionMenu3 != null ? textActionMenu3.getOffset() : null;
                Intrinsics.checkNotNull(offset);
                ViewExtensionsKt.dismissWithUpdate(textActionMenu, view, iIntValue, offset.getSecond().intValue());
            }
        } else {
            TextActionMenu textActionMenu4 = this$0.textActionMenu;
            if (textActionMenu4 != null) {
                View view2 = this$0.getBinding().textMenuPosition;
                Intrinsics.checkNotNullExpressionValue(view2, "binding.textMenuPosition");
                TextActionMenu textActionMenu5 = this$0.textActionMenu;
                Pair<Integer, Integer> offset3 = textActionMenu5 != null ? textActionMenu5.getOffset() : null;
                Intrinsics.checkNotNull(offset3);
                int iIntValue2 = offset3.getFirst().intValue();
                TextActionMenu textActionMenu6 = this$0.textActionMenu;
                offset = textActionMenu6 != null ? textActionMenu6.getOffset() : null;
                Intrinsics.checkNotNull(offset);
                ViewExtensionsKt.dismissWithUpdate(textActionMenu4, view2, iIntValue2, offset.getSecond().intValue());
            }
        }
        this$0.lineId = "";
        this$0.drawLineText = "";
        ReadBook.loadContent$default(ReadBook.INSTANCE, false, false, null, 4, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$12(ReadBookActivity this$0, Object obj) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (!this$0.getViewModel().getToastReShow()) {
            ToastUtilsKt.toastOnUi$default(this$0, "添加笔记成功！", 0, 2, (Object) null);
        }
        TextActionMenu textActionMenu = this$0.textActionMenu;
        if (textActionMenu != null) {
            View view = this$0.getBinding().textMenuPosition;
            Intrinsics.checkNotNullExpressionValue(view, "binding.textMenuPosition");
            TextActionMenu textActionMenu2 = this$0.textActionMenu;
            Pair<Integer, Integer> offset = textActionMenu2 != null ? textActionMenu2.getOffset() : null;
            Intrinsics.checkNotNull(offset);
            int iIntValue = offset.getFirst().intValue();
            TextActionMenu textActionMenu3 = this$0.textActionMenu;
            Pair<Integer, Integer> offset2 = textActionMenu3 != null ? textActionMenu3.getOffset() : null;
            Intrinsics.checkNotNull(offset2);
            ViewExtensionsKt.dismissWithUpdate(textActionMenu, view, iIntValue, offset2.getSecond().intValue());
        }
        ImageView imageView = this$0.getBinding().cursorRight;
        Intrinsics.checkNotNullExpressionValue(imageView, "binding.cursorRight");
        ViewExtensionsKt.invisible(imageView);
        ImageView imageView2 = this$0.getBinding().cursorLeft;
        Intrinsics.checkNotNullExpressionValue(imageView2, "binding.cursorLeft");
        ViewExtensionsKt.invisible(imageView2);
        this$0.lineId = "";
        this$0.drawLineText = "";
        ReadBook.loadContent$default(ReadBook.INSTANCE, false, false, null, 4, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$13(ReadBookActivity this$0, Object obj) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.getViewModel().getToastReShow()) {
            return;
        }
        ToastUtilsKt.toastOnUi$default(this$0, "添加纠错成功！", 0, 2, (Object) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$14(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$15(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$16(ReadBookActivity this$0, Object obj) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (!this$0.getViewModel().getToastReShow()) {
            Toast.makeText(this$0, "已加入书架", 0).show();
        }
        BookInfo book = ReadBook.INSTANCE.getBook();
        if (book != null) {
            book.setBookshelf("1");
        }
        this$0.setLibraryState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$17(ReadBookActivity this$0, Object obj) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (!this$0.getViewModel().getToastReShow()) {
            Toast.makeText(this$0, "已移出书架", 0).show();
        }
        BookInfo book = ReadBook.INSTANCE.getBook();
        if (book != null) {
            book.setBookshelf("0");
        }
        this$0.setLibraryState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$6(ReadBookActivity this$0, Object obj) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.readTime = 0L;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$7(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$9(ReadBookActivity this$0, Object obj) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (!this$0.getViewModel().getToastReShow()) {
            ToastUtilsKt.toastOnUi$default(this$0, "添加划线成功！", 0, 2, (Object) null);
        }
        if (this$0.drawLineFromNote) {
            TextActionMenu textActionMenu = new TextActionMenu(this$0, this$0);
            textActionMenu.initCheck(false, this$0.color, this$0.style, false, true);
            textActionMenu.showAtLocation(this$0.getBinding().textMenuPosition, 49, 0, (int) (this$0.clickNoteY - ScreenUtil.getPxByDp((Context) this$0, 20.0f)));
            this$0.textActionMenu = textActionMenu;
        }
        TextActionMenu textActionMenu2 = this$0.textActionMenu;
        if (textActionMenu2 != null) {
            textActionMenu2.addDrawSuccess();
        }
        ImageView imageView = this$0.getBinding().cursorLeft;
        Intrinsics.checkNotNullExpressionValue(imageView, "binding.cursorLeft");
        ViewExtensionsKt.invisible(imageView);
        ImageView imageView2 = this$0.getBinding().cursorRight;
        Intrinsics.checkNotNullExpressionValue(imageView2, "binding.cursorRight");
        ViewExtensionsKt.invisible(imageView2);
        this$0.drawLineFromNote = false;
        ReadBook.loadContent$default(ReadBook.INSTANCE, false, false, null, 4, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$0(ReadBookActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getViewModel().setToastReShow(false);
    }

    private final void onBottomDialogChange() {
        int i2 = this.bottomDialog;
        if (i2 == 0) {
            onMenuHide();
        } else {
            if (i2 != 1) {
                return;
            }
            onMenuShow();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onNoteClick$lambda$48(ReadBookActivity this$0, DialogInterface dialogInterface) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getBinding().readView.cancelSelect();
        this$0.noteDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void pageChanged$lambda$53(ReadBookActivity this$0) {
        BookInfo bookInfo;
        List<Chapter> chapterList;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.upSeekBarProgress();
        this$0.upReadProgress();
        ReadConfig readConfig = ReadConfig.INSTANCE;
        if (readConfig.getLastPage() || readConfig.getFirstPage() || (bookInfo = this$0.bookInfo) == null) {
            return;
        }
        String str = this$0.getIntent().getStringExtra("user_id") + '_' + this$0.bookId + "_READ_PAGE";
        String str2 = this$0.getIntent().getStringExtra("user_id") + '_' + this$0.bookId + "_CURRENT_PAGE";
        Context appCtx = AppCtxKt.getAppCtx();
        ReadBook readBook = ReadBook.INSTANCE;
        ContextExtensionsKt.putPrefInt(appCtx, str2, readBook.getDurPageIndex());
        String str3 = this$0.getIntent().getStringExtra("user_id") + '_' + this$0.bookId + "_READ_CHAPTER";
        ContextExtensionsKt.getPrefString(AppCtxKt.getAppCtx(), str3, null);
        ContextExtensionsKt.putPrefInt(AppCtxKt.getAppCtx(), str, readBook.getDurChapterPos());
        for (Chapter chapter : bookInfo.getChapterList()) {
            int index = chapter.getIndex();
            ReadBook readBook2 = ReadBook.INSTANCE;
            if (index == readBook2.getDurChapterIndex()) {
                if (!TextUtils.equals(chapter.getId(), this$0.currentChapter)) {
                    BookInfo book = readBook2.getBook();
                    if (Intrinsics.areEqual("1", book != null ? book.getPass() : null)) {
                        this$0.getViewModel().saveReadProgress(this$0.bookId, chapter.getId(), this$0.readTime);
                        this$0.currentChapter = chapter.getId();
                        BookInfo book2 = readBook2.getBook();
                        if (book2 != null && (chapterList = book2.getChapterList()) != null) {
                            for (Chapter chapter2 : chapterList) {
                                if (Intrinsics.areEqual(chapter2.getId(), this$0.currentChapter)) {
                                    chapter2.setPerusal("1");
                                }
                            }
                        }
                    }
                }
                ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), str3, chapter.getId());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void pageChanged$lambda$54(ReadBookActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.startBackupJob();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void screenOffTimerStart$lambda$18(ReadBookActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        long j2 = this$0.screenTimeOut;
        if (j2 < 0) {
            this$0.keepScreenOn(true);
        } else {
            if (j2 - ContextExtensionsKt.getSysScreenOffTime(this$0) <= 0) {
                this$0.keepScreenOn(false);
                return;
            }
            this$0.keepScreenOn(true);
            this$0.getHandler().removeCallbacks(this$0.getScreenOffRunnable());
            this$0.getHandler().postDelayed(this$0.getScreenOffRunnable(), this$0.screenTimeOut);
        }
    }

    private final void setBottomDialog(int i2) {
        if (this.bottomDialog != i2) {
            this.bottomDialog = i2;
            onBottomDialogChange();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showDrawLineTextActionMenu$lambda$42(ReadBookActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getBinding().readView.cancelSelect();
        this$0.mergeAddLine = false;
    }

    private final void showReadMenuView() {
        int iColor;
        ReadMenu readMenu = getBinding().readMenu;
        Intrinsics.checkNotNullExpressionValue(readMenu, "binding.readMenu");
        ViewExtensionsKt.visible(readMenu);
        getBinding().readMenu.runMenuIn();
        TextActionMenu textActionMenu = this.textActionMenu;
        if (textActionMenu != null) {
            textActionMenu.dismiss();
        }
        Window window = getWindow();
        int colorMode = ReadConfig.INSTANCE.getColorMode();
        if (colorMode == 0) {
            iColor = -1;
        } else if (colorMode != 1) {
            iColor = ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_121622);
        } else {
            iColor = ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_F5EBCE);
        }
        window.setStatusBarColor(iColor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showTextActionMenu$lambda$20(ReadBookActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.mergeAddLine = false;
    }

    private final void startBackupJob() {
        Job job = this.backupJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
        }
        this.backupJob = BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), Dispatchers.getIO(), null, new C10311(null), 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void upMenuView$lambda$49(ReadBookActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getBinding().readMenu.upBookView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void upReadProgress() {
        String readProgress = getBinding().readView.getPageFactory().getCurPage().getReadProgress();
        if (Intrinsics.areEqual("0.0%", readProgress)) {
            return;
        }
        getBinding().readMenu.setProgress(readProgress);
    }

    private final void upScreenTimeOut() {
        this.screenTimeOut = ReadConfig.INSTANCE.getKeepLight() * 1000;
        screenOffTimerStart();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void upSeekBarProgress() {
        getBinding().readMenu.setSeekPage(ReadBook.INSTANCE.getDurChapterIndex());
    }

    private final void updateStatusBar() {
        getWindow().addFlags(Integer.MIN_VALUE);
        getWindow().clearFlags(67108864);
        getWindow().setStatusBarColor(0);
        getWindow().getDecorView().setSystemUiVisibility(ReadConfig.INSTANCE.getColorMode() < 2 ? 8192 : 256);
    }

    private final boolean volumeKeyPage(PageDirection direction, boolean longPress) {
        if (!ReadConfig.INSTANCE.getVolumeKeyPage()) {
            return false;
        }
        handleKeyPage(direction, longPress);
        return true;
    }

    @Override // com.ykb.ebook.weight.ReadMenu.Callback
    public void backgroundChange() throws SecurityException, NumberFormatException {
        List<TextPage> pages;
        ReadBook readBook = ReadBook.INSTANCE;
        readBook.resetBookInfoDataSet();
        getBinding().readView.updateThemeStyle(true);
        TextChapter curTextChapter = readBook.getCurTextChapter();
        if (curTextChapter != null && (pages = curTextChapter.getPages()) != null) {
            for (TextPage textPage : pages) {
                textPage.invalidateAll();
                textPage.getCanvasRecorder().invalidate();
            }
        }
        updateStatusBar();
        uiConfigChange();
    }

    @Override // com.ykb.ebook.common_interface.TimeBatteryCallback
    public void batterBack(int level) {
        getBinding().readView.upBattery(level);
    }

    @Override // com.ykb.ebook.page.ReadBook.CallBack
    public void contentLoadFinish() {
        this.loadStates = true;
        getHandler().postDelayed(new Runnable() { // from class: com.ykb.ebook.activity.c1
            @Override // java.lang.Runnable
            public final void run() throws SecurityException, NumberFormatException {
                ReadBookActivity.contentLoadFinish$lambda$55(this.f26108c);
            }
        }, 100L);
    }

    @Override // com.ykb.ebook.weight.ReadView.Callback
    public void dismissFunctionMenu() {
        this.moveSelectStart = false;
        if (!this.cursorHandleFlag) {
            ImageView imageView = getBinding().cursorLeft;
            Intrinsics.checkNotNullExpressionValue(imageView, "binding.cursorLeft");
            ViewExtensionsKt.invisible(imageView);
            ImageView imageView2 = getBinding().cursorRight;
            Intrinsics.checkNotNullExpressionValue(imageView2, "binding.cursorRight");
            ViewExtensionsKt.invisible(imageView2);
        }
        getBinding().readView.cancelSelect();
    }

    @Override // com.ykb.ebook.base.BaseActivity
    public void doBusiness() {
        showLoading();
        getViewModel().getPayWays(this.bookId);
        getViewModel().initData(this.bookId, ContextExtensionsKt.getPrefString(AppCtxKt.getAppCtx(), "user_id", ""));
    }

    @Override // com.ykb.ebook.weight.ContentTextView.CallBack
    public int getHeaderHeight() {
        return getBinding().readView.getCurPage().getHeaderHeight();
    }

    public final boolean getOnQuestionClick() {
        return this.onQuestionClick;
    }

    @Override // com.ykb.ebook.weight.ContentTextView.CallBack
    @Nullable
    public PageDelegate getPageDelegate() {
        return getBinding().readView.getPageDelegate();
    }

    @Override // com.ykb.ebook.weight.ContentTextView.CallBack
    @NotNull
    public TextPageFactory getPageFactory() {
        return getBinding().readView.getPageFactory();
    }

    @Override // com.ykb.ebook.weight.TextActionMenu.Callback
    @NotNull
    public String getSelectedText() {
        return getBinding().readView.getSelectText();
    }

    public final boolean getToUnlock() {
        return this.toUnlock;
    }

    @Override // com.ykb.ebook.base.BaseActivity
    public void initStatusBar() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }
        StatusBarUtil.setTranslucentForCoordinatorLayout(this, 255);
    }

    @Override // com.ykb.ebook.weight.ReadView.Callback
    public boolean isInitFinish() {
        return this.mviewModel != null;
    }

    @Override // com.ykb.ebook.weight.ContentTextView.CallBack
    public boolean isScroll() {
        return getBinding().readView.getIsScroll();
    }

    @Override // com.ykb.ebook.weight.ContentTextView.CallBack
    /* renamed from: isSelectingSearchResult, reason: from getter */
    public boolean getIsSelectingSearchResult() {
        return this.isSelectingSearchResult;
    }

    @Override // com.ykb.ebook.weight.ReadView.Callback
    public boolean isShowActionMenu() {
        TextActionMenu textActionMenu = this.textActionMenu;
        if (textActionMenu != null) {
            return textActionMenu.isShowing();
        }
        return false;
    }

    @Override // com.ykb.ebook.page.ReadBook.CallBack
    public void loadingImage(boolean isLoadingImage) {
        if (isLoadingImage) {
            showLoading();
        } else {
            hideLoading();
        }
    }

    @Override // com.ykb.ebook.page.ReadBook.CallBack
    public void notifyBookChanged() {
        this.bookChanged = true;
    }

    @Override // com.ykb.ebook.base.BaseVmActivity
    public void observeViewModel() {
        super.observeViewModel();
        getViewModel().getAddRecord().observe(this, new Observer() { // from class: com.ykb.ebook.activity.j1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ReadBookActivity.observeViewModel$lambda$6(this.f26151a, obj);
            }
        });
        MutableLiveData<BookInfo> bookInfo = getViewModel().getBookInfo();
        final AnonymousClass2 anonymousClass2 = new AnonymousClass2();
        bookInfo.observe(this, new Observer() { // from class: com.ykb.ebook.activity.l1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ReadBookActivity.observeViewModel$lambda$7(anonymousClass2, obj);
            }
        });
        this.addDrawLineObserver = new Observer() { // from class: com.ykb.ebook.activity.m1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ReadBookActivity.observeViewModel$lambda$9(this.f26169a, obj);
            }
        };
        MutableLiveData<Object> addDrawLine = getViewModel().getAddDrawLine();
        Observer<? super Object> observer = this.addDrawLineObserver;
        Observer<? super Object> observer2 = null;
        if (observer == null) {
            Intrinsics.throwUninitializedPropertyAccessException("addDrawLineObserver");
            observer = null;
        }
        addDrawLine.observe(this, observer);
        getViewModel().getEditDrawLine().observe(this, new Observer() { // from class: com.ykb.ebook.activity.n1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ReadBookActivity.observeViewModel$lambda$10(this.f26174a, obj);
            }
        });
        this.deleteDrawLineObser = new Observer() { // from class: com.ykb.ebook.activity.o1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ReadBookActivity.observeViewModel$lambda$11(this.f26177a, obj);
            }
        };
        MutableLiveData<Object> deleteDrawLine = getViewModel().getDeleteDrawLine();
        Observer<? super Object> observer3 = this.deleteDrawLineObser;
        if (observer3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("deleteDrawLineObser");
            observer3 = null;
        }
        deleteDrawLine.observe(this, observer3);
        this.addNoteObser = new Observer() { // from class: com.ykb.ebook.activity.p1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ReadBookActivity.observeViewModel$lambda$12(this.f26180a, obj);
            }
        };
        MutableLiveData<Object> addNote = getViewModel().getAddNote();
        Observer<? super Object> observer4 = this.addNoteObser;
        if (observer4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("addNoteObser");
        } else {
            observer2 = observer4;
        }
        addNote.observe(this, observer2);
        getViewModel().getAddCorrect().observe(this, new Observer() { // from class: com.ykb.ebook.activity.x0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ReadBookActivity.observeViewModel$lambda$13(this.f26215a, obj);
            }
        });
        MutableLiveData<Throwable> error = getViewModel().getError();
        final Function1<Throwable, Unit> function1 = new Function1<Throwable, Unit>() { // from class: com.ykb.ebook.activity.ReadBookActivity.observeViewModel.8
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable th) {
                LoadingPopupView loadingPopupView = ReadBookActivity.this.loadingView;
                if (loadingPopupView != null) {
                    loadingPopupView.dismiss();
                }
            }
        };
        error.observe(this, new Observer() { // from class: com.ykb.ebook.activity.y0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ReadBookActivity.observeViewModel$lambda$14(function1, obj);
            }
        });
        MutableLiveData<QuestionListData> questionList = getViewModel().getQuestionList();
        final Function1<QuestionListData, Unit> function12 = new Function1<QuestionListData, Unit>() { // from class: com.ykb.ebook.activity.ReadBookActivity.observeViewModel.9
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(QuestionListData questionListData) {
                invoke2(questionListData);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(QuestionListData questionListData) {
                LoadingPopupView loadingPopupView = ReadBookActivity.this.loadingView;
                if (loadingPopupView != null) {
                    loadingPopupView.dismiss();
                }
                ArrayList<QuestionDetailBeanNew> arrayList = new ArrayList();
                Iterator<T> it = questionListData.getList().iterator();
                while (it.hasNext()) {
                    QuestionDetailBeanNew dataFromQuestionDetailBean = QuestionDetailBeanNew.getDataFromQuestionDetailBean((QuestionDetailBean) it.next());
                    Intrinsics.checkNotNullExpressionValue(dataFromQuestionDetailBean, "getDataFromQuestionDetailBean(it)");
                    arrayList.add(dataFromQuestionDetailBean);
                }
                int i2 = 0;
                if (arrayList.isEmpty()) {
                    if (ReadBookActivity.this.getViewModel().getToastReShow()) {
                        return;
                    }
                    ToastUtilsKt.toastOnUi$default(ReadBookActivity.this, "获取题目失败，请联系客服处理", 0, 2, (Object) null);
                    return;
                }
                for (QuestionDetailBeanNew questionDetailBeanNew : arrayList) {
                    questionDetailBeanNew.setFromEbook(true);
                    i2++;
                    questionDetailBeanNew.setSort(String.valueOf(i2));
                }
                if (ReadBookActivity.this.getOnQuestionClick()) {
                    String json = new Gson().toJson(arrayList);
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setPackage(ReadBookActivity.this.getPackageName());
                    intent.setData(Uri.parse("ebook://ykb/?questionList=" + json));
                    ReadBookActivity.this.startActivity(intent);
                }
            }
        };
        questionList.observe(this, new Observer() { // from class: com.ykb.ebook.activity.z0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ReadBookActivity.observeViewModel$lambda$15(function12, obj);
            }
        });
        ReadBookViewModel readBookViewModel = this.mviewModel;
        Intrinsics.checkNotNull(readBookViewModel);
        readBookViewModel.getAddBookData().observe(this, new Observer() { // from class: com.ykb.ebook.activity.a1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ReadBookActivity.observeViewModel$lambda$16(this.f26096a, obj);
            }
        });
        ReadBookViewModel readBookViewModel2 = this.mviewModel;
        Intrinsics.checkNotNull(readBookViewModel2);
        readBookViewModel2.getDelBookData().observe(this, new Observer() { // from class: com.ykb.ebook.activity.k1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ReadBookActivity.observeViewModel$lambda$17(this.f26158a, obj);
            }
        });
    }

    @Override // com.ykb.ebook.base.BaseActivity
    @SuppressLint({"ClickableViewAccessibility"})
    public void onActivityCreated(@Nullable Bundle savedInstanceState) throws SecurityException, NumberFormatException {
        EventBus.getDefault().register(this);
        ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), "user_id", getIntent().getStringExtra("user_id"));
        ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), "app_id", getIntent().getStringExtra("app_id"));
        ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), PLVSocketUserConstant.ROLE_ADMIN, getIntent().getStringExtra(PLVSocketUserConstant.ROLE_ADMIN));
        ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), "avatar", getIntent().getStringExtra("avatar"));
        ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), "token", getIntent().getStringExtra("token"));
        ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), "secret", getIntent().getStringExtra("secret"));
        ReadBook readBook = ReadBook.INSTANCE;
        readBook.resetPages();
        readBook.resetPageBitMap();
        readBook.resetBookInfoDataSet();
        String stringExtra = getIntent().getStringExtra("book_id");
        if (stringExtra == null) {
            stringExtra = "";
        }
        this.bookId = stringExtra;
        ContextExtensionsKt.putPrefInt(AppCtxKt.getAppCtx(), this.bookId + "_CURRENT_PAGE", 0);
        ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), PreferKeyKt.JUMP_DRAW_ID, "");
        ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), "book_id", this.bookId);
        ReadConfig readConfig = ReadConfig.INSTANCE;
        if (readConfig.getColorMode() != 2) {
            readConfig.setColorModePre(readConfig.getColorMode());
        }
        readConfig.setShowFirstPage(true);
        readConfig.setShowFeiYe(false);
        readConfig.setLastPage(false);
        getBinding().cursorLeft.setOnTouchListener(this);
        getBinding().cursorRight.setOnTouchListener(this);
        upScreenTimeOut();
        readBook.register(this);
        final String str = ContextExtensionsKt.getPrefString(AppCtxKt.getAppCtx(), "user_id", "1") + "_book_id_" + this.bookId;
        ContextExtensionsKt.getPrefLong$default(AppCtxKt.getAppCtx(), str, 0L, 2, null);
        com.ykb.ebook.common.EventBus.INSTANCE.register(this);
        final int restRemind = readConfig.getRestRemind();
        BasicCountDownTimer basicCountDownTimer = new BasicCountDownTimer(restRemind * 1000, 1000L);
        this.restTimer = basicCountDownTimer;
        basicCountDownTimer.setOnTickListener(new Function1<Long, Unit>() { // from class: com.ykb.ebook.activity.ReadBookActivity.onActivityCreated.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Long l2) {
                invoke(l2.longValue());
                return Unit.INSTANCE;
            }

            public final void invoke(long j2) {
            }
        });
        BasicCountDownTimer basicCountDownTimer2 = this.restTimer;
        if (basicCountDownTimer2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("restTimer");
            basicCountDownTimer2 = null;
        }
        basicCountDownTimer2.setOnFinishListener(new Function0<Unit>() { // from class: com.ykb.ebook.activity.ReadBookActivity.onActivityCreated.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                CommonOneDialog.Builder rightText = new CommonOneDialog.Builder(ReadBookActivity.this).setTitle("温馨提示").setSubTitle("您已经连续阅读" + (restRemind / 60) + "分钟，休息一下").setLeftText("休息一下").setRightText("继续阅读");
                final ReadBookActivity readBookActivity = ReadBookActivity.this;
                CommonOneDialog.Builder leftClick = rightText.setLeftClick(new Function1<BasicDialog, Unit>() { // from class: com.ykb.ebook.activity.ReadBookActivity.onActivityCreated.2.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(BasicDialog basicDialog) {
                        invoke2(basicDialog);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(@Nullable BasicDialog basicDialog) {
                        readBookActivity.finish();
                    }
                });
                final ReadBookActivity readBookActivity2 = ReadBookActivity.this;
                leftClick.setRightClick(new Function1<BasicDialog, Unit>() { // from class: com.ykb.ebook.activity.ReadBookActivity.onActivityCreated.2.2
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(BasicDialog basicDialog) {
                        invoke2(basicDialog);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(@Nullable BasicDialog basicDialog) {
                        BasicCountDownTimer basicCountDownTimer3 = readBookActivity2.restTimer;
                        if (basicCountDownTimer3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("restTimer");
                            basicCountDownTimer3 = null;
                        }
                        basicCountDownTimer3.start();
                    }
                }).show();
            }
        });
        if (restRemind > 0) {
            BasicCountDownTimer basicCountDownTimer3 = this.restTimer;
            if (basicCountDownTimer3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("restTimer");
                basicCountDownTimer3 = null;
            }
            basicCountDownTimer3.start();
        }
        this.readTimer = new BasicCountDownTimer(60000L, 1000L);
        ContextExtensionsKt.putPrefLong(AppCtxKt.getAppCtx(), str, 0L);
        BasicCountDownTimer basicCountDownTimer4 = this.readTimer;
        if (basicCountDownTimer4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("readTimer");
            basicCountDownTimer4 = null;
        }
        basicCountDownTimer4.setOnTickListener(new Function1<Long, Unit>() { // from class: com.ykb.ebook.activity.ReadBookActivity.onActivityCreated.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Long l2) {
                invoke(l2.longValue());
                return Unit.INSTANCE;
            }

            public final void invoke(long j2) {
                ReadConfig readConfig2 = ReadConfig.INSTANCE;
                if (readConfig2.getLastPage() || readConfig2.getFirstPage()) {
                    return;
                }
                ContextExtensionsKt.putPrefLong(AppCtxKt.getAppCtx(), str, ContextExtensionsKt.getPrefLong$default(AppCtxKt.getAppCtx(), str, 0L, 2, null) + 1);
                this.readTime++;
            }
        });
        BasicCountDownTimer basicCountDownTimer5 = this.readTimer;
        if (basicCountDownTimer5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("readTimer");
            basicCountDownTimer5 = null;
        }
        basicCountDownTimer5.setOnFinishListener(new Function0<Unit>() { // from class: com.ykb.ebook.activity.ReadBookActivity.onActivityCreated.4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                PerusalDuration perusalDuration;
                String duration;
                long prefLong$default = ContextExtensionsKt.getPrefLong$default(AppCtxKt.getAppCtx(), str, 0L, 2, null);
                BookInfo bookInfo = this.bookInfo;
                BasicCountDownTimer basicCountDownTimer6 = null;
                Long lValueOf = (bookInfo == null || (perusalDuration = bookInfo.getPerusalDuration()) == null || (duration = perusalDuration.getDuration()) == null) ? null : Long.valueOf(Long.parseLong(duration) + prefLong$default);
                BookInfo book = ReadBook.INSTANCE.getBook();
                Intrinsics.checkNotNull(book);
                book.getPerusalDuration().setDuration(String.valueOf(lValueOf));
                if (lValueOf != null) {
                    this.getBinding().readMenu.setReadTime(lValueOf.longValue());
                }
                ContextExtensionsKt.putPrefLong(AppCtxKt.getAppCtx(), str, 0L);
                BasicCountDownTimer basicCountDownTimer7 = this.readTimer;
                if (basicCountDownTimer7 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("readTimer");
                } else {
                    basicCountDownTimer6 = basicCountDownTimer7;
                }
                basicCountDownTimer6.start();
            }
        });
        ReadView readView = getBinding().readView;
        Intrinsics.checkNotNullExpressionValue(readView, "binding.readView");
        ReadView.updateThemeStyle$default(readView, false, 1, null);
        readConfig.setFirstPage(false);
        updateStatusBar();
        hideSystemUI();
        getHandler().postDelayed(new Runnable() { // from class: com.ykb.ebook.activity.g1
            @Override // java.lang.Runnable
            public final void run() {
                ReadBookActivity.onActivityCreated$lambda$0(this.f26131c);
            }
        }, 500L);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    @Deprecated(message = "Deprecated in Java")
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String stringExtra;
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 999 || resultCode != -1 || data == null || (stringExtra = data.getStringExtra("imageUrl")) == null) {
            return;
        }
        org.greenrobot.eventbus.EventBus.getDefault().post(new ImageUploadSuccessEvent(stringExtra));
    }

    @Override // com.ykb.ebook.weight.ReadMenu.Callback
    public void onAddLibraryClick() {
        BookInfo bookInfo = this.bookInfo;
        Intrinsics.checkNotNull(bookInfo);
        if (Intrinsics.areEqual(bookInfo.isBookshelf(), "0")) {
            ReadBookViewModel readBookViewModel = this.mviewModel;
            Intrinsics.checkNotNull(readBookViewModel);
            BookInfo bookInfo2 = this.bookInfo;
            Intrinsics.checkNotNull(bookInfo2);
            readBookViewModel.addBook(bookInfo2.getId());
            return;
        }
        ReadBookViewModel readBookViewModel2 = this.mviewModel;
        Intrinsics.checkNotNull(readBookViewModel2);
        BookInfo bookInfo3 = this.bookInfo;
        Intrinsics.checkNotNull(bookInfo3);
        readBookViewModel2.delBook(bookInfo3.getId());
    }

    @Override // com.ykb.ebook.weight.ReadMenu.Callback
    public void onAllTagClick() {
        Intent intent = new Intent(this, (Class<?>) AllTagActivity.class);
        intent.putExtra("position", 1);
        startActivity(intent);
    }

    @Override // com.ykb.ebook.weight.ReadMenu.Callback
    public void onBackClick() {
        finish();
    }

    @Override // com.ykb.ebook.weight.ReadView.Callback
    public void onBookReadOver() {
    }

    @Override // com.ykb.ebook.weight.ReadMenu.Callback
    public void onBookReviewClick() {
        BookReviewActivity.INSTANCE.newIntent(this, this.bookId);
    }

    @Override // com.hjq.http.listener.OnDownloadListener
    public /* synthetic */ void onByte(File file, long j2, long j3) {
        z0.a.a(this, file, j2, j3);
    }

    @Override // com.ykb.ebook.weight.ContentTextView.CallBack
    public void onCancelSelect() {
        ActivityReadBookBinding binding = getBinding();
        ImageView cursorLeft = binding.cursorLeft;
        Intrinsics.checkNotNullExpressionValue(cursorLeft, "cursorLeft");
        ViewExtensionsKt.invisible(cursorLeft);
        ImageView cursorRight = binding.cursorRight;
        Intrinsics.checkNotNullExpressionValue(cursorRight, "cursorRight");
        ViewExtensionsKt.invisible(cursorRight);
        TextActionMenu textActionMenu = this.textActionMenu;
        if (textActionMenu != null) {
            View view = getBinding().textMenuPosition;
            Intrinsics.checkNotNullExpressionValue(view, "binding.textMenuPosition");
            TextActionMenu textActionMenu2 = this.textActionMenu;
            Pair<Integer, Integer> offset = textActionMenu2 != null ? textActionMenu2.getOffset() : null;
            Intrinsics.checkNotNull(offset);
            int iIntValue = offset.getFirst().intValue();
            TextActionMenu textActionMenu3 = this.textActionMenu;
            Pair<Integer, Integer> offset2 = textActionMenu3 != null ? textActionMenu3.getOffset() : null;
            Intrinsics.checkNotNull(offset2);
            ViewExtensionsKt.dismissWithUpdate(textActionMenu, view, iIntValue, offset2.getSecond().intValue());
        }
    }

    @Override // com.ykb.ebook.weight.ReadMenu.Callback
    public void onChapterListClick() {
        BookInfo bookInfo = this.bookInfo;
        if (bookInfo != null) {
            BasePopupView basePopupViewAsCustom = new XPopup.Builder(this).asCustom(new ChapterListPop(this));
            Intrinsics.checkNotNull(basePopupViewAsCustom, "null cannot be cast to non-null type com.ykb.ebook.weight.ChapterListPop");
            final ChapterListPop chapterListPop = (ChapterListPop) basePopupViewAsCustom;
            chapterListPop.setData(bookInfo).initSort(this.sort).setSortCallback(new Function1<Boolean, Unit>() { // from class: com.ykb.ebook.activity.ReadBookActivity$onChapterListClick$1$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                    invoke(bool.booleanValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(boolean z2) {
                    this.this$0.sort = z2;
                }
            }).setSearchClick(new Function0<Unit>() { // from class: com.ykb.ebook.activity.ReadBookActivity$onChapterListClick$1$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    ChapterSearchDialog.Builder bookId = new ChapterSearchDialog.Builder(this.this$0).setBookId(this.this$0.bookId);
                    final ChapterListPop chapterListPop2 = chapterListPop;
                    final ReadBookActivity readBookActivity = this.this$0;
                    bookId.setOnItemClick(new Function4<Integer, TextSearchResult, String, String, Unit>() { // from class: com.ykb.ebook.activity.ReadBookActivity$onChapterListClick$1$2.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(4);
                        }

                        @Override // kotlin.jvm.functions.Function4
                        public /* bridge */ /* synthetic */ Unit invoke(Integer num, TextSearchResult textSearchResult, String str, String str2) {
                            invoke(num.intValue(), textSearchResult, str, str2);
                            return Unit.INSTANCE;
                        }

                        public final void invoke(int i2, @NotNull final TextSearchResult textSearchResult, @NotNull final String keyString, @NotNull final String keyWord) {
                            Intrinsics.checkNotNullParameter(textSearchResult, "textSearchResult");
                            Intrinsics.checkNotNullParameter(keyString, "keyString");
                            Intrinsics.checkNotNullParameter(keyWord, "keyWord");
                            ReadBook readBook = ReadBook.INSTANCE;
                            int sort = textSearchResult.getSort() - 1;
                            final ReadBookActivity readBookActivity2 = readBookActivity;
                            readBook.loadChapterContentByIndex(sort, new Function1<String, Unit>() { // from class: com.ykb.ebook.activity.ReadBookActivity.onChapterListClick.1.2.1.1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                public /* bridge */ /* synthetic */ Unit invoke(String str) {
                                    invoke2(str);
                                    return Unit.INSTANCE;
                                }

                                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                public final void invoke2(@NotNull String it) {
                                    Intrinsics.checkNotNullParameter(it, "it");
                                    ReadBook.openChapterFromSearch$default(ReadBook.INSTANCE, textSearchResult.getSort() - 1, readBookActivity2.getJumpToChapterPos(it, keyString, keyWord), null, 4, null);
                                }
                            });
                            chapterListPop2.dismiss();
                        }
                    }).show();
                }
            }).setOnItemClick(new Function2<Integer, Chapter, Unit>() { // from class: com.ykb.ebook.activity.ReadBookActivity$onChapterListClick$1$3
                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Integer num, Chapter chapter) {
                    invoke(num.intValue(), chapter);
                    return Unit.INSTANCE;
                }

                public final void invoke(int i2, @NotNull Chapter chapter) {
                    Intrinsics.checkNotNullParameter(chapter, "<anonymous parameter 1>");
                    if (i2 == 0) {
                        ReadConfig.INSTANCE.setFirstPage(false);
                    }
                    ReadBook.openChapter$default(ReadBook.INSTANCE, i2, 0, null, 6, null);
                }
            });
            chapterListPop.show();
        }
    }

    @Override // com.ykb.ebook.weight.TextActionMenu.Callback
    public void onCommentClick(@NotNull String text) {
        int i2;
        String str;
        String str2;
        String text2 = text;
        Intrinsics.checkNotNullParameter(text2, "text");
        String str3 = "";
        if (Intrinsics.areEqual(text2, "")) {
            text2 = this.drawLineText;
        }
        String handleContent = getBinding().readView.getCurPage().getTextChapter().getChapter().getHandleContent();
        List mutableList = CollectionsKt___CollectionsKt.toMutableList((Collection) StringsKt__StringsKt.split$default((CharSequence) handleContent, new String[]{"\n"}, false, 0, 6, (Object) null));
        TextChapter textChapter = getBinding().readView.getCurPage().getTextChapter();
        int selectTextPosition_original = getBinding().readView.getSelectTextPosition_original();
        if (selectTextPosition_original == -1) {
            selectTextPosition_original = this.startPosition;
        }
        if (this.moveSelectStart) {
            selectTextPosition_original = StringsKt__StringsKt.indexOf$default((CharSequence) getBinding().readView.getCurPage().getTextPage().getTextChapter().getChapter().getHandleContent(), text2, 0, false, 6, (Object) null) + getBinding().readView.getCurPage().getTextChapter().getPages().get(0).getTitle().length();
        }
        int length = selectTextPosition_original + text2.length();
        List list = StringsKt___StringsKt.toList(handleContent);
        int length2 = (length - textChapter.getPages().get(0).getTitle().length()) - 1;
        int i3 = length2;
        while (true) {
            if (-1 >= i3) {
                i2 = 0;
                break;
            } else {
                if (Intrinsics.areEqual(String.valueOf(((Character) list.get(i3)).charValue()), "\n")) {
                    i2 = i3 + 1;
                    break;
                }
                i3--;
            }
        }
        int size = list.size();
        while (true) {
            if (length2 >= size) {
                length2 = 0;
                break;
            } else if (Intrinsics.areEqual(String.valueOf(((Character) list.get(length2)).charValue()), "\n")) {
                break;
            } else {
                length2++;
            }
        }
        if (length2 == 0) {
            length2 = handleContent.length();
        }
        if (mutableList.size() > 1) {
            mutableList.remove(0);
        }
        Iterator<TextParagraph> it = textChapter.getPageParagraphs().iterator();
        while (true) {
            if (!it.hasNext()) {
                str = "";
                str2 = str;
                break;
            }
            TextParagraph next = it.next();
            if (length2 < i2) {
                length2 = i2 + 2;
            }
            String strSubstring = handleContent.substring(i2, length2);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
            int i4 = 2;
            if (StringsKt__StringsKt.contains$default((CharSequence) StringExtensionsKt.formatContent(strSubstring), (CharSequence) next.getText(), false, 2, (Object) null) && (next.getTextLines().get(0).getColumns().get(0) instanceof TextColumn)) {
                BaseColumn baseColumn = next.getTextLines().get(0).getColumns().get(0);
                Intrinsics.checkNotNull(baseColumn, "null cannot be cast to non-null type com.ykb.ebook.page.column.TextColumn");
                int charIndex = ((TextColumn) baseColumn).getCharIndex();
                String strSubstring2 = StringExtensionsKt.formatContent(handleContent).substring(charIndex, next.getLength() + charIndex);
                Intrinsics.checkNotNullExpressionValue(strSubstring2, "this as java.lang.String…ing(startIndex, endIndex)");
                String strSubstring3 = handleContent.substring(i2, length2);
                Intrinsics.checkNotNullExpressionValue(strSubstring3, "this as java.lang.String…ing(startIndex, endIndex)");
                if (StringsKt__StringsKt.contains$default((CharSequence) StringExtensionsKt.formatContent(strSubstring3), (CharSequence) strSubstring2, false, 2, (Object) null)) {
                    String strSubstring4 = handleContent.substring(i2, length2);
                    Intrinsics.checkNotNullExpressionValue(strSubstring4, "this as java.lang.String…ing(startIndex, endIndex)");
                    String content = StringExtensionsKt.formatContent(strSubstring4);
                    String.valueOf(i2);
                    Iterator it2 = mutableList.iterator();
                    int i5 = 0;
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        int i6 = i5 + 1;
                        if (StringsKt__StringsKt.contains$default((CharSequence) StringExtensionsKt.formatContent((String) it2.next()), (CharSequence) strSubstring2, false, i4, (Object) null)) {
                            int length3 = StringExtensionsKt.formatContent(CollectionsKt___CollectionsKt.joinToString$default(mutableList.subList(0, i6), "\n", null, null, 0, null, null, 62, null)).length();
                            String strSubstring5 = handleContent.substring(0, length2);
                            Intrinsics.checkNotNullExpressionValue(strSubstring5, "this as java.lang.String…ing(startIndex, endIndex)");
                            if (length3 == StringExtensionsKt.formatContent(strSubstring5).length()) {
                                str3 = textChapter.getChapter().getParagraphIdList().get(i5);
                                break;
                            }
                        }
                        i5 = i6;
                        i4 = 2;
                    }
                    str2 = content;
                    str = str3;
                }
            }
        }
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return;
        }
        new AddReviewCommentDialog.Builder(this, this, this.bookId, textChapter.getChapter().getId(), str, "", str2, "写段评", null, null, false, null, R2.attr.triangleHeight, null).setOnPublishSuccess(new Function0<Unit>() { // from class: com.ykb.ebook.activity.ReadBookActivity.onCommentClick.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                TextActionMenu textActionMenu = ReadBookActivity.this.textActionMenu;
                if (textActionMenu != null) {
                    View view = ReadBookActivity.this.getBinding().textMenuPosition;
                    Intrinsics.checkNotNullExpressionValue(view, "binding.textMenuPosition");
                    TextActionMenu textActionMenu2 = ReadBookActivity.this.textActionMenu;
                    Pair<Integer, Integer> offset = textActionMenu2 != null ? textActionMenu2.getOffset() : null;
                    Intrinsics.checkNotNull(offset);
                    int iIntValue = offset.getFirst().intValue();
                    TextActionMenu textActionMenu3 = ReadBookActivity.this.textActionMenu;
                    Pair<Integer, Integer> offset2 = textActionMenu3 != null ? textActionMenu3.getOffset() : null;
                    Intrinsics.checkNotNull(offset2);
                    ViewExtensionsKt.dismissWithUpdate(textActionMenu, view, iIntValue, offset2.getSecond().intValue());
                }
                ImageView imageView = ReadBookActivity.this.getBinding().cursorRight;
                Intrinsics.checkNotNullExpressionValue(imageView, "binding.cursorRight");
                ViewExtensionsKt.invisible(imageView);
                ImageView imageView2 = ReadBookActivity.this.getBinding().cursorLeft;
                Intrinsics.checkNotNullExpressionValue(imageView2, "binding.cursorLeft");
                ViewExtensionsKt.invisible(imageView2);
                ReadBookActivity.this.getBinding().readView.resetSelect();
                ReadBook.loadContent$default(ReadBook.INSTANCE, false, false, null, 6, null);
            }
        }).show();
    }

    @Override // com.hjq.http.listener.OnDownloadListener
    @SuppressLint({"QueryPermissionsNeeded"})
    public void onComplete(@Nullable File file) {
        if (!getViewModel().getToastReShow()) {
            ToastUtilsKt.toastOnUi$default(this, "下载成功！", 0, 2, (Object) null);
        }
        Intrinsics.checkNotNull(file);
        Uri uriForFile = FileProvider.getUriForFile(this, ConstantKt.FILE_PROVIDER_AUTHORITY, file);
        Log log = Log.INSTANCE;
        String logTag = getLogTag();
        Intrinsics.checkNotNullExpressionValue(logTag, "logTag");
        log.logE(logTag, "下载文件的Uri:" + uriForFile);
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setFlags(67108864);
        intent.putExtra("android.intent.extra.STREAM", uriForFile);
        intent.setType(MimeTypes.ANY_TYPE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(intent, "分享到："));
        } else {
            ToastUtilsKt.toastOnUi$default(this, "没有找到对应的应用！", 0, 2, (Object) null);
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(@NotNull Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
        if (ScreenUtil.isTablet(this)) {
            recreate();
        }
    }

    @Override // com.ykb.ebook.base.BaseVmActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        ReadBook readBook = ReadBook.INSTANCE;
        Observer<? super Object> observer = null;
        ReadBook.upData$default(readBook, null, false, 2, null);
        TextActionMenu textActionMenu = this.textActionMenu;
        if (textActionMenu != null) {
            textActionMenu.dismiss();
        }
        getBinding().readView.onDestroy();
        getBinding().getRoot().removeAllViews();
        readBook.resetPages();
        readBook.resetBookInfoDataSet();
        readBook.resetPageBitMap();
        BasicCountDownTimer basicCountDownTimer = this.restTimer;
        if (basicCountDownTimer == null) {
            Intrinsics.throwUninitializedPropertyAccessException("restTimer");
            basicCountDownTimer = null;
        }
        basicCountDownTimer.cancel();
        BasicCountDownTimer basicCountDownTimer2 = this.readTimer;
        if (basicCountDownTimer2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("readTimer");
            basicCountDownTimer2 = null;
        }
        basicCountDownTimer2.cancel();
        readBook.setDurChapterIndex(0);
        readBook.setDurChapterPos(0);
        readBook.unregister(this);
        com.ykb.ebook.common.EventBus.INSTANCE.unregister(this);
        MutableLiveData<Object> addNote = getViewModel().getAddNote();
        Observer<? super Object> observer2 = this.addNoteObser;
        if (observer2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("addNoteObser");
            observer2 = null;
        }
        addNote.removeObserver(observer2);
        MutableLiveData<Object> addDrawLine = getViewModel().getAddDrawLine();
        Observer<? super Object> observer3 = this.addDrawLineObserver;
        if (observer3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("addDrawLineObserver");
            observer3 = null;
        }
        addDrawLine.removeObserver(observer3);
        MutableLiveData<Object> deleteDrawLine = getViewModel().getDeleteDrawLine();
        Observer<? super Object> observer4 = this.deleteDrawLineObser;
        if (observer4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("deleteDrawLineObser");
        } else {
            observer = observer4;
        }
        deleteDrawLine.removeObserver(observer);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override // com.ykb.ebook.weight.ContentTextView.CallBack
    public void onDismissActionMenu() {
        TextActionMenu textActionMenu = this.textActionMenu;
        if (textActionMenu != null) {
            View view = getBinding().textMenuPosition;
            Intrinsics.checkNotNullExpressionValue(view, "binding.textMenuPosition");
            TextActionMenu textActionMenu2 = this.textActionMenu;
            Pair<Integer, Integer> offset = textActionMenu2 != null ? textActionMenu2.getOffset() : null;
            Intrinsics.checkNotNull(offset);
            int iIntValue = offset.getFirst().intValue();
            TextActionMenu textActionMenu3 = this.textActionMenu;
            Pair<Integer, Integer> offset2 = textActionMenu3 != null ? textActionMenu3.getOffset() : null;
            Intrinsics.checkNotNull(offset2);
            ViewExtensionsKt.dismissWithUpdate(textActionMenu, view, iIntValue, offset2.getSecond().intValue());
        }
    }

    @Override // com.ykb.ebook.weight.ReadMenu.Callback
    public void onDownloadClick() {
        final String downloadUrl;
        String title;
        BookInfo bookInfo = this.bookInfo;
        final String str = "";
        if (bookInfo == null || (downloadUrl = bookInfo.getDownloadUrl()) == null) {
            downloadUrl = "";
        }
        BookInfo bookInfo2 = this.bookInfo;
        if (bookInfo2 != null) {
            bookInfo2.getFreeChapterCount();
        }
        BookInfo bookInfo3 = this.bookInfo;
        if (bookInfo3 != null && (title = bookInfo3.getTitle()) != null) {
            str = title;
        }
        ReadBook readBook = ReadBook.INSTANCE;
        readBook.getChapterSize();
        BookInfo book = readBook.getBook();
        final boolean zAreEqual = Intrinsics.areEqual("1", book != null ? book.getPass() : null);
        new CommonOneDialog.Builder(this).setTitle("温馨提示").setSubTitle(zAreEqual ? "下载书籍将以zip格式发送至您置顶的位置\n例如您可以发送至“微信-收藏”，\n在微信收藏中即可找到您要下载的文件。" : "权限不足，无法下载").setLeftText("取消").setRightText(!zAreEqual ? "获得本书" : "确定").setRightClick(new Function1<BasicDialog, Unit>() { // from class: com.ykb.ebook.activity.ReadBookActivity.onDownloadClick.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(BasicDialog basicDialog) {
                invoke2(basicDialog);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@Nullable BasicDialog basicDialog) {
                String string;
                if (!zAreEqual) {
                    List<Ways> payWays = ReadBook.INSTANCE.getPayWays();
                    if (true ^ payWays.isEmpty()) {
                        Intent intent = new Intent();
                        intent.addCategory("android.intent.category.DEFAULT");
                        intent.setPackage(this.getPackageName());
                        intent.setAction("android.intent.action.VIEW");
                        intent.setData(Uri.parse("ebook_unlock://ykb/?payways=" + GsonExtensionsKt.getGSON().toJson(payWays)));
                        this.startActivity(intent);
                        return;
                    }
                    return;
                }
                if (downloadUrl.length() == 0) {
                    return;
                }
                String str2 = CommonConfig.INSTANCE.getYI_KAO_BANG() ? SdkConstant.UMENG_ALIS : "hukaobang";
                StringBuilder sb = new StringBuilder();
                String str3 = File.separator;
                sb.append(str3);
                sb.append(str2);
                sb.append(str3);
                sb.append("ResourceDownload/");
                String string2 = sb.toString();
                StringBuilder sb2 = new StringBuilder();
                File externalFilesDir = AppCtxKt.getAppCtx().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
                sb2.append(externalFilesDir != null ? externalFilesDir.getAbsolutePath() : null);
                sb2.append(string2);
                String string3 = sb2.toString();
                if (Build.VERSION.SDK_INT >= 29) {
                    StringBuilder sb3 = new StringBuilder();
                    File externalFilesDir2 = AppCtxKt.getAppCtx().getExternalFilesDir(null);
                    sb3.append(externalFilesDir2 != null ? externalFilesDir2.getAbsolutePath() : null);
                    sb3.append(string2);
                    string3 = sb3.toString();
                }
                Log.INSTANCE.logE("file_root", "file====>" + string3);
                int iLastIndexOf$default = StringsKt__StringsKt.lastIndexOf$default((CharSequence) downloadUrl, StrPool.DOT, 0, false, 6, (Object) null);
                if (iLastIndexOf$default != -1) {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(str);
                    String strSubstring = downloadUrl.substring(iLastIndexOf$default);
                    Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String).substring(startIndex)");
                    sb4.append(strSubstring);
                    string = sb4.toString();
                } else {
                    string = str + ".docx";
                }
                EasyConfig.with(new OkHttpClient.Builder().build()).setLogEnabled(ConstantKt.getIS_DEBUG()).setServer("https://www.wanandroid.com/").setHandler(new RequestHandler()).into();
                EasyHttp.download(this).method(HttpMethod.GET).file(new File(string3, string)).url(downloadUrl).listener(this).start();
            }
        }).show();
    }

    /* JADX WARN: Removed duplicated region for block: B:112:0x0276  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x0458 A[LOOP:8: B:173:0x041d->B:185:0x0458, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:231:0x0456 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:238:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0258  */
    @Override // com.ykb.ebook.weight.TextActionMenu.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onDrawLineBack(@org.jetbrains.annotations.NotNull java.lang.String r20, int r21, int r22, int r23) {
        /*
            Method dump skipped, instructions count: 1274
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ykb.ebook.activity.ReadBookActivity.onDrawLineBack(java.lang.String, int, int, int):void");
    }

    @Override // com.hjq.http.listener.OnDownloadListener
    public void onEnd(@Nullable File file) {
        Log log = Log.INSTANCE;
        String logTag = getLogTag();
        Intrinsics.checkNotNullExpressionValue(logTag, "logTag");
        log.logD(logTag, "电子书下载结束》》》》》》》》》》》》》》》");
    }

    @Override // com.hjq.http.listener.OnDownloadListener
    public void onError(@Nullable File file, @Nullable Exception e2) {
        if (!getViewModel().getToastReShow()) {
            ToastUtilsKt.toastOnUi$default(this, "电子书下载失败！", 0, 2, (Object) null);
        }
        Log log = Log.INSTANCE;
        String logTag = getLogTag();
        Intrinsics.checkNotNullExpressionValue(logTag, "logTag");
        log.logD(logTag, "电子书下载失败》》》》》》》》》》》》》》》");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0040  */
    /* JADX WARN: Type inference failed for: r0v1, types: [T, java.lang.String] */
    @Override // com.ykb.ebook.weight.TextActionMenu.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onErrorClick(@org.jetbrains.annotations.NotNull java.lang.String r15) {
        /*
            r14 = this;
            java.lang.String r0 = "text"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r15, r0)
            kotlin.jvm.internal.Ref$ObjectRef r15 = new kotlin.jvm.internal.Ref$ObjectRef
            r15.<init>()
            java.lang.String r0 = r14.drawLineText
            r15.element = r0
            kotlin.jvm.internal.Ref$IntRef r0 = new kotlin.jvm.internal.Ref$IntRef
            r0.<init>()
            com.ykb.ebook.databinding.ActivityReadBookBinding r1 = r14.getBinding()
            com.ykb.ebook.weight.ReadView r1 = r1.readView
            int r1 = r1.getSelectTextPosition()
            r0.element = r1
            r2 = -1
            if (r1 != r2) goto L27
            int r1 = r14.startPosition
            r0.element = r1
        L27:
            boolean r1 = r14.moveSelectStart
            r3 = 0
            r4 = 1
            if (r1 == 0) goto L40
            com.ykb.ebook.common.ReadConfig r1 = com.ykb.ebook.common.ReadConfig.INSTANCE
            int r1 = r1.getSelectTextPosition()
            r0.element = r1
            if (r1 != r2) goto L40
            java.lang.String r1 = "操作失败"
            r2 = 2
            r5 = 0
            com.ykb.ebook.util.ToastUtilsKt.toastOnUi$default(r14, r1, r5, r2, r3)
            goto L41
        L40:
            r5 = r4
        L41:
            if (r5 == 0) goto L66
            com.ykb.ebook.util.Coroutine$Companion r6 = com.ykb.ebook.util.Coroutine.INSTANCE
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            com.ykb.ebook.activity.ReadBookActivity$onErrorClick$1 r11 = new com.ykb.ebook.activity.ReadBookActivity$onErrorClick$1
            r11.<init>(r3)
            r12 = 15
            r13 = 0
            com.ykb.ebook.util.Coroutine r1 = com.ykb.ebook.util.Coroutine.Companion.async$default(r6, r7, r8, r9, r10, r11, r12, r13)
            com.ykb.ebook.activity.ReadBookActivity$onErrorClick$2 r2 = new com.ykb.ebook.activity.ReadBookActivity$onErrorClick$2
            r2.<init>(r15, r0, r3)
            com.ykb.ebook.util.Coroutine r15 = com.ykb.ebook.util.Coroutine.onSuccess$default(r1, r3, r2, r4, r3)
            com.ykb.ebook.activity.ReadBookActivity$onErrorClick$3 r0 = new com.ykb.ebook.activity.ReadBookActivity$onErrorClick$3
            r0.<init>(r3)
            com.ykb.ebook.util.Coroutine.onError$default(r15, r3, r0, r4, r3)
        L66:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ykb.ebook.activity.ReadBookActivity.onErrorClick(java.lang.String):void");
    }

    @Subscribe
    public final void onEvent(@Nullable String event) {
        if (Intrinsics.areEqual(event, "BuySuccess")) {
            doBusiness();
        }
    }

    @Override // com.ykb.ebook.weight.ContentTextView.CallBack
    public void onImageClick(@NotNull String src) {
        Intrinsics.checkNotNullParameter(src, "src");
        new XPopup.Builder(this).asImageViewer(null, src, new SmartGlideImageLoader(true, R.drawable.imgplacehodel_image)).isShowSaveButton(false).show();
    }

    @Override // com.ykb.ebook.weight.ContentTextView.CallBack
    public void onImageLongPress(float x2, float y2, @NotNull String src) {
        Intrinsics.checkNotNullParameter(src, "src");
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, @NotNull KeyEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (getMenuLayoutIsVisible()) {
            return super.onKeyDown(keyCode, event);
        }
        boolean z2 = event.getRepeatCount() > 0;
        if (isPrevKey(keyCode)) {
            handleKeyPage(PageDirection.PREV, z2);
            return true;
        }
        if (isNextKey(keyCode)) {
            handleKeyPage(PageDirection.NEXT, z2);
            return true;
        }
        if (keyCode != 24) {
            if (keyCode == 25 && volumeKeyPage(PageDirection.NEXT, z2)) {
                return true;
            }
        } else if (volumeKeyPage(PageDirection.PREV, z2)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyUp(int keyCode, @Nullable KeyEvent event) {
        if ((keyCode == 24 || keyCode == 25) && volumeKeyPage(PageDirection.NONE, false)) {
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override // com.ykb.ebook.common_interface.LayoutProgressListener
    public void onLayoutCompleted() {
        ReadBook.CallBack.DefaultImpls.onLayoutCompleted(this);
    }

    @Override // com.ykb.ebook.common_interface.LayoutProgressListener
    public void onLayoutException(@NotNull Throwable th) {
        ReadBook.CallBack.DefaultImpls.onLayoutException(this, th);
    }

    @Override // com.ykb.ebook.common_interface.LayoutProgressListener
    public void onLayoutPageCompleted(int index, @NotNull TextPage page) {
        Intrinsics.checkNotNullParameter(page, "page");
        this.upSeekBarThrottle.invoke();
        getBinding().readView.onLayoutPageCompleted(index, page);
    }

    @Override // com.ykb.ebook.weight.ContentTextView.CallBack
    public boolean onLongScreenshotTouchEvent(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        return getBinding().readView.onTouchEvent(event);
    }

    @Override // com.ykb.ebook.weight.ReadMenu.Callback
    public void onMenuHide() {
        updateStatusBar();
    }

    @Override // com.ykb.ebook.weight.ReadMenu.Callback
    public void onMenuShow() {
        updateStatusBar();
    }

    @Override // com.ykb.ebook.common.EventCallback
    public void onMessage(int what, @Nullable Object any) {
        Integer numValueOf;
        List<Chapter> chapterList;
        Integer numValueOf2;
        List<Chapter> chapterList2;
        Integer numValueOf3;
        List<Chapter> chapterList3;
        BasicCountDownTimer basicCountDownTimer = null;
        if (what == 32) {
            BasicCountDownTimer basicCountDownTimer2 = this.restTimer;
            if (basicCountDownTimer2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("restTimer");
                basicCountDownTimer2 = null;
            }
            basicCountDownTimer2.cancel();
            final int restRemind = ReadConfig.INSTANCE.getRestRemind();
            BasicCountDownTimer basicCountDownTimer3 = new BasicCountDownTimer(restRemind * 1000, 1000L);
            this.restTimer = basicCountDownTimer3;
            basicCountDownTimer3.setOnTickListener(new Function1<Long, Unit>() { // from class: com.ykb.ebook.activity.ReadBookActivity.onMessage.2
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Long l2) {
                    invoke(l2.longValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(long j2) {
                }
            });
            BasicCountDownTimer basicCountDownTimer4 = this.restTimer;
            if (basicCountDownTimer4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("restTimer");
                basicCountDownTimer4 = null;
            }
            basicCountDownTimer4.setOnFinishListener(new Function0<Unit>() { // from class: com.ykb.ebook.activity.ReadBookActivity.onMessage.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    CommonOneDialog.Builder rightText = new CommonOneDialog.Builder(ReadBookActivity.this).setTitle("温馨提示").setSubTitle("您已经连续阅读" + (restRemind / 60) + "分钟，休息一下").setLeftText("休息一下").setRightText("继续阅读");
                    final ReadBookActivity readBookActivity = ReadBookActivity.this;
                    CommonOneDialog.Builder leftClick = rightText.setLeftClick(new Function1<BasicDialog, Unit>() { // from class: com.ykb.ebook.activity.ReadBookActivity.onMessage.3.1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(BasicDialog basicDialog) {
                            invoke2(basicDialog);
                            return Unit.INSTANCE;
                        }

                        /* renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(@Nullable BasicDialog basicDialog) {
                            readBookActivity.finish();
                        }
                    });
                    final ReadBookActivity readBookActivity2 = ReadBookActivity.this;
                    leftClick.setRightClick(new Function1<BasicDialog, Unit>() { // from class: com.ykb.ebook.activity.ReadBookActivity.onMessage.3.2
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(BasicDialog basicDialog) {
                            invoke2(basicDialog);
                            return Unit.INSTANCE;
                        }

                        /* renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(@Nullable BasicDialog basicDialog) {
                            BasicCountDownTimer basicCountDownTimer5 = readBookActivity2.restTimer;
                            if (basicCountDownTimer5 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("restTimer");
                                basicCountDownTimer5 = null;
                            }
                            basicCountDownTimer5.start();
                        }
                    }).show();
                }
            });
            if (restRemind > 0) {
                BasicCountDownTimer basicCountDownTimer5 = this.restTimer;
                if (basicCountDownTimer5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("restTimer");
                } else {
                    basicCountDownTimer = basicCountDownTimer5;
                }
                basicCountDownTimer.start();
                return;
            }
            return;
        }
        if (what == 291) {
            getBinding().readView.upPageAnim();
            return;
        }
        switch (what) {
            case 16:
                getBinding().readView.upTimeShow();
                break;
            case 17:
                getViewModel().setToastReShow(true);
                this.onQuestionClick = false;
                recreate();
                break;
            case 18:
                upScreenTimeOut();
                break;
            case 19:
                finish();
                break;
            default:
                switch (what) {
                    case 21:
                    case 22:
                        ReadBook.loadContent$default(ReadBook.INSTANCE, false, false, null, 4, null);
                        break;
                    case 23:
                        ReadBook.loadContent$default(ReadBook.INSTANCE, false, false, null, 4, null);
                        break;
                    case 24:
                        ReadBook.loadContent$default(ReadBook.INSTANCE, false, false, null, 4, null);
                        break;
                    case 25:
                        String prefString = ContextExtensionsKt.getPrefString(AppCtxKt.getAppCtx(), PreferKeyKt.JUMP_DRAW_ID, "");
                        String string = prefString != null ? StringsKt__StringsKt.trim((CharSequence) prefString).toString() : null;
                        Intrinsics.checkNotNull(string);
                        int prefInt$default = ContextExtensionsKt.getPrefInt$default(AppCtxKt.getAppCtx(), PreferKeyKt.START_POSITION, 0, 2, null);
                        String prefString$default = ContextExtensionsKt.getPrefString$default(AppCtxKt.getAppCtx(), PreferKeyKt.JUMP_PARAGRAPH_ID, null, 2, null);
                        String prefString$default2 = ContextExtensionsKt.getPrefString$default(AppCtxKt.getAppCtx(), PreferKeyKt.JUMP_CHAPTER_ID, null, 2, null);
                        int i2 = -1;
                        if (prefInt$default == 0) {
                            if (TextUtils.isEmpty(prefString$default)) {
                                int i3 = 0;
                                for (Object obj : getBinding().readView.getCurPage().getTextChapter().getPages()) {
                                    int i4 = i3 + 1;
                                    if (i3 < 0) {
                                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                                    }
                                    if (StringsKt__StringsKt.contains$default((CharSequence) StringExtensionsKt.formatContent(((TextPage) obj).getText()), (CharSequence) string, false, 2, (Object) null)) {
                                        ReadBook readBook = ReadBook.INSTANCE;
                                        TextChapter curTextChapter = readBook.getCurTextChapter();
                                        readBook.setDurChapterPos(curTextChapter != null ? curTextChapter.getReadLength(i3) : 0);
                                        BookInfo book = readBook.getBook();
                                        if (book == null || (chapterList2 = book.getChapterList()) == null) {
                                            numValueOf2 = null;
                                        } else {
                                            Iterator<Chapter> it = chapterList2.iterator();
                                            int i5 = 0;
                                            while (true) {
                                                if (!it.hasNext()) {
                                                    i5 = -1;
                                                } else if (!Intrinsics.areEqual(getBinding().readView.getCurPage().getTextChapter().getChapter().getId(), it.next().getId())) {
                                                    i5++;
                                                }
                                            }
                                            numValueOf2 = Integer.valueOf(i5);
                                        }
                                        if (numValueOf2 == null || numValueOf2.intValue() != -1) {
                                            ReadBook readBook2 = ReadBook.INSTANCE;
                                            readBook2.setDurChapterIndex(numValueOf2 != null ? numValueOf2.intValue() : 0);
                                            ReadBook.skipToPage$default(readBook2, i3, null, 2, null);
                                            ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), PreferKeyKt.JUMP_DRAW_ID, "");
                                        }
                                    }
                                    i3 = i4;
                                }
                                break;
                            } else {
                                PageView curPage = getBinding().readView.getCurPage();
                                if (curPage.getTextChapter().getParagraphs().size() != 0) {
                                    Intrinsics.checkNotNull(prefString$default);
                                    if (Integer.parseInt(prefString$default) < curPage.getTextChapter().getParagraphs().size()) {
                                        ArrayList<TextLine> textLines = curPage.getTextChapter().getParagraphs().get(Integer.parseInt(prefString$default)).getTextLines();
                                        int index = textLines.get(textLines.size() - 1).getTextPage().getIndex();
                                        BookInfo book2 = ReadBook.INSTANCE.getBook();
                                        if (book2 == null || (chapterList3 = book2.getChapterList()) == null) {
                                            numValueOf3 = null;
                                        } else {
                                            Iterator<Chapter> it2 = chapterList3.iterator();
                                            while (true) {
                                                if (it2.hasNext()) {
                                                    if (Intrinsics.areEqual(prefString$default2, it2.next().getId())) {
                                                        i2 = i;
                                                    } else {
                                                        i++;
                                                    }
                                                }
                                            }
                                            numValueOf3 = Integer.valueOf(i2);
                                        }
                                        ReadBook readBook3 = ReadBook.INSTANCE;
                                        Intrinsics.checkNotNull(numValueOf3);
                                        readBook3.setDurChapterIndex(numValueOf3.intValue());
                                        ReadBook.skipToPage$default(readBook3, index, null, 2, null);
                                        ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), PreferKeyKt.JUMP_DRAW_ID, "");
                                        ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), PreferKeyKt.JUMP_PARAGRAPH_ID, "");
                                        break;
                                    }
                                }
                            }
                        } else {
                            List<TextPage> pages = getBinding().readView.getCurPage().getTextChapter().getPages();
                            Intrinsics.checkNotNull(pages);
                            Iterator<TextPage> it3 = pages.iterator();
                            int i6 = 0;
                            int length = 0;
                            while (it3.hasNext()) {
                                int i7 = i6 + 1;
                                length += StringExtensionsKt.formatContent(it3.next().getText()).length();
                                if (prefInt$default <= length) {
                                    ReadBook readBook4 = ReadBook.INSTANCE;
                                    TextChapter curTextChapter2 = readBook4.getCurTextChapter();
                                    readBook4.setDurChapterPos(curTextChapter2 != null ? curTextChapter2.getReadLength(i6) : 0);
                                    BookInfo book3 = readBook4.getBook();
                                    if (book3 == null || (chapterList = book3.getChapterList()) == null) {
                                        numValueOf = null;
                                    } else {
                                        Iterator<Chapter> it4 = chapterList.iterator();
                                        int i8 = 0;
                                        while (true) {
                                            if (!it4.hasNext()) {
                                                i8 = -1;
                                            } else if (!Intrinsics.areEqual(getBinding().readView.getCurPage().getTextChapter().getChapter().getId(), it4.next().getId())) {
                                                i8++;
                                            }
                                        }
                                        numValueOf = Integer.valueOf(i8);
                                    }
                                    if (numValueOf == null || numValueOf.intValue() != -1) {
                                        ReadBook readBook5 = ReadBook.INSTANCE;
                                        readBook5.setDurChapterIndex(numValueOf != null ? numValueOf.intValue() : 0);
                                        ReadBook.skipToPage$default(readBook5, i6, null, 2, null);
                                        ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), PreferKeyKt.JUMP_DRAW_ID, "");
                                        break;
                                    }
                                } else {
                                    i6 = i7;
                                }
                            }
                            break;
                        }
                        break;
                }
        }
    }

    @Override // com.ykb.ebook.weight.ReadMenu.Callback
    public void onMoreSettingClick() {
        getBinding().readMenu.runMenuOut(new Function0<Unit>() { // from class: com.ykb.ebook.activity.ReadBookActivity.onMoreSettingClick.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                ReadBookActivity.this.startActivity(new Intent(ReadBookActivity.this, (Class<?>) MoreSetAct.class).putExtra("fromModuleInner", true));
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:10:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00e6  */
    /* JADX WARN: Type inference failed for: r2v1, types: [T, java.lang.String] */
    @Override // com.ykb.ebook.weight.TextActionMenu.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onNoteClick(@org.jetbrains.annotations.NotNull java.lang.String r20) {
        /*
            Method dump skipped, instructions count: 294
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ykb.ebook.activity.ReadBookActivity.onNoteClick(java.lang.String):void");
    }

    @Override // com.ykb.ebook.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        Job job = this.backupJob;
        BasicCountDownTimer basicCountDownTimer = null;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
        }
        BasicCountDownTimer basicCountDownTimer2 = this.readTimer;
        if (basicCountDownTimer2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("readTimer");
        } else {
            basicCountDownTimer = basicCountDownTimer2;
        }
        basicCountDownTimer.cancel();
        unregisterReceiver(this.timeBatteryReceiver);
        if (!ConstantKt.getIS_DEBUG()) {
            ReadBook.INSTANCE.uploadProgress();
        }
        BookInfo bookInfo = this.bookInfo;
        if (bookInfo != null) {
            for (Chapter chapter : bookInfo.getChapterList()) {
                if (chapter.getIndex() == ReadBook.INSTANCE.getDurChapterIndex()) {
                    System.out.println((Object) ("this is request readTime:" + this.readTime));
                    getViewModel().saveReadProgress(this.bookId, chapter.getId(), this.readTime);
                }
            }
        }
    }

    @Override // com.hjq.http.listener.OnDownloadListener
    public void onProgress(@Nullable File file, int progress) {
        Log log = Log.INSTANCE;
        String logTag = getLogTag();
        Intrinsics.checkNotNullExpressionValue(logTag, "logTag");
        log.logD(logTag, "电子书下载进度：" + progress);
    }

    @Override // com.ykb.ebook.weight.ContentTextView.CallBack
    public void onQuestionClick(@NotNull String id) {
        Intrinsics.checkNotNullParameter(id, "id");
        Object systemService = getSystemService("connectivity");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.net.ConnectivityManager");
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) systemService).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            this.onQuestionClick = true;
            getViewModel().getQuestList(id);
        } else {
            if (getViewModel().getToastReShow()) {
                return;
            }
            ToastUtilsKt.toastOnUi$default(this, "请检查网络连接", 0, 2, (Object) null);
        }
    }

    @Override // com.ykb.ebook.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    @SuppressLint({"UnspecifiedRegisterReceiverFlag"})
    public void onResume() {
        super.onResume();
        String str = "read_start" + this.bookId;
        if (ContextExtensionsKt.getPrefLong$default(AppCtxKt.getAppCtx(), str, 0L, 2, null) == 0) {
            ContextExtensionsKt.putPrefLong(AppCtxKt.getAppCtx(), str, System.currentTimeMillis());
        }
        if (this.bookChanged) {
            this.bookChanged = false;
            ReadBook.INSTANCE.setCallBack(this);
        }
        BasicCountDownTimer basicCountDownTimer = this.readTimer;
        if (basicCountDownTimer == null) {
            Intrinsics.throwUninitializedPropertyAccessException("readTimer");
            basicCountDownTimer = null;
        }
        basicCountDownTimer.start();
        upSystemUiVisibility();
        TimeBatteryReceiver timeBatteryReceiver = this.timeBatteryReceiver;
        registerReceiver(timeBatteryReceiver, timeBatteryReceiver.getFilter());
        getBinding().readView.upTime();
        if (this.toUnlock) {
            this.toUnlock = false;
            doBusiness();
        }
    }

    @Override // com.ykb.ebook.weight.ContentTextView.CallBack
    public void onReviewClick(@NotNull String id, @NotNull String chapterId) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(chapterId, "chapterId");
        showLoading();
        new ReviewCommentDialog.Builder(this, this, this.bookId, id, chapterId).setCallBack(new ReviewCommentDialog.CallBack() { // from class: com.ykb.ebook.activity.ReadBookActivity.onReviewClick.1
            @Override // com.ykb.ebook.dialog.ReviewCommentDialog.CallBack
            public void updateParagraphCount(boolean addComment) {
                ReadBook.loadContent$default(ReadBook.INSTANCE, false, false, null, 4, null);
            }
        }).show();
        hideLoading();
    }

    @Override // com.ykb.ebook.weight.ReadMenu.Callback
    public void onShareBookClick() {
        new CommonShareDialog2(this, new OnDialogShareClickListener() { // from class: com.ykb.ebook.activity.ReadBookActivity$onShareBookClick$dialog$1
            public void shareCallBack(int clickType, boolean success) {
            }

            @Override // com.ykb.common_share_lib.bean.OnDialogShareClickListener
            public /* bridge */ /* synthetic */ void shareCallBack(Integer num, boolean z2) {
                shareCallBack(num.intValue(), z2);
            }

            @Override // com.ykb.common_share_lib.bean.OnDialogShareClickListener
            public /* bridge */ /* synthetic */ void shareClick(Integer num) {
                shareClick(num.intValue());
            }

            public void shareClick(int clickType) {
                String str = ConstantKt.getBOOK_SHARE_URL() + "download/ebookIntro.html?book_id=" + this.this$0.bookId;
                BookInfo bookInfo = this.this$0.bookInfo;
                Intrinsics.checkNotNull(bookInfo);
                String title = bookInfo.getTitle();
                BookInfo bookInfo2 = this.this$0.bookInfo;
                Intrinsics.checkNotNull(bookInfo2);
                String describe = bookInfo2.getDescribe();
                ReadBookActivity readBookActivity = this.this$0;
                BookInfo bookInfo3 = readBookActivity.bookInfo;
                Intrinsics.checkNotNull(bookInfo3);
                UMImage uMImage = new UMImage(readBookActivity, bookInfo3.getThumbnail());
                UMWeb uMWeb = new UMWeb(str);
                uMWeb.setTitle(title);
                uMWeb.setThumb(uMImage);
                uMWeb.setDescription(describe);
                this.this$0.getViewModel().uploadShareCount(this.this$0.bookId);
                new ShareAction(this.this$0).withMedia(uMWeb).setPlatform(CommonShareDialog2.initPlatforms().get(clickType).mPlatform).setCallback(new UMShareListener() { // from class: com.ykb.ebook.activity.ReadBookActivity$onShareBookClick$dialog$1$shareClick$1
                    @Override // com.umeng.socialize.UMShareListener
                    public void onCancel(@Nullable SHARE_MEDIA p02) {
                    }

                    @Override // com.umeng.socialize.UMShareListener
                    public void onError(@Nullable SHARE_MEDIA p02, @Nullable Throwable p12) {
                    }

                    @Override // com.umeng.socialize.UMShareListener
                    public void onResult(@Nullable SHARE_MEDIA p02) {
                    }

                    @Override // com.umeng.socialize.UMShareListener
                    public void onStart(@Nullable SHARE_MEDIA p02) {
                    }
                }).share();
            }
        }).show();
    }

    @Override // com.ykb.ebook.weight.TextActionMenu.Callback
    public void onShareClick(@NotNull String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        if (Intrinsics.areEqual(text, "")) {
            text = this.drawLineText;
        }
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        BookInfo bookInfo = this.bookInfo;
        String title = bookInfo != null ? bookInfo.getTitle() : null;
        BookInfo bookInfo2 = this.bookInfo;
        String author = bookInfo2 != null ? bookInfo2.getAuthor() : null;
        BookInfo bookInfo3 = this.bookInfo;
        String thumbnail = bookInfo3 != null ? bookInfo3.getThumbnail() : null;
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setPackage(getPackageName());
        intent.setData(Uri.parse("ebook://ykbshare/?bookShareText=" + StringExtensionsKt.formatContent(text) + "&bookName=" + title + "&bookAuthor=" + author + "&bookImageUrl=" + thumbnail + "&theme=" + ReadConfig.INSTANCE.getColorMode()));
        startActivity(intent);
    }

    @Override // com.ykb.ebook.weight.ContentTextView.CallBack
    public void onShowActionMenu(float startX, float startY, float endX, float endY) {
        ReadView.Callback.DefaultImpls.showTextActionMenu$default(this, false, 0, 0, 0, null, 24, null);
    }

    @Override // com.hjq.http.listener.OnDownloadListener
    public void onStart(@Nullable File file) {
        Log log = Log.INSTANCE;
        String logTag = getLogTag();
        Intrinsics.checkNotNullExpressionValue(logTag, "logTag");
        log.logD(logTag, "电子书下载开始》》》》》》》》》》》》》》》");
        ReadBookViewModel viewModel = getViewModel();
        BookInfo bookInfo = this.bookInfo;
        Intrinsics.checkNotNull(bookInfo);
        viewModel.uploadDownloadCount(bookInfo.getId());
    }

    @Override // android.view.View.OnTouchListener
    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouch(@NotNull View v2, @NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(v2, "v");
        Intrinsics.checkNotNullParameter(event, "event");
        ActivityReadBookBinding binding = getBinding();
        this.cursorHandleFlag = true;
        int action = event.getAction();
        if (action == 0) {
            TextActionMenu textActionMenu = this.textActionMenu;
            if (textActionMenu != null) {
                View view = getBinding().textMenuPosition;
                Intrinsics.checkNotNullExpressionValue(view, "binding.textMenuPosition");
                TextActionMenu textActionMenu2 = this.textActionMenu;
                Pair<Integer, Integer> offset = textActionMenu2 != null ? textActionMenu2.getOffset() : null;
                Intrinsics.checkNotNull(offset);
                int iIntValue = offset.getFirst().intValue();
                TextActionMenu textActionMenu3 = this.textActionMenu;
                Pair<Integer, Integer> offset2 = textActionMenu3 != null ? textActionMenu3.getOffset() : null;
                Intrinsics.checkNotNull(offset2);
                ViewExtensionsKt.dismissWithUpdate(textActionMenu, view, iIntValue, offset2.getSecond().intValue());
            }
        } else if (action == 1) {
            binding.readView.getCurPage().resetReverseCursor();
            if (getSelectedText().length() > 0) {
                this.drawLineText = getSelectedText();
            }
            ReadView.Callback.DefaultImpls.showTextActionMenu$default(this, true, 0, 0, 0, null, 24, null);
        } else if (action == 2) {
            int id = v2.getId();
            if (id == R.id.cursor_left) {
                if (binding.readView.getCurPage().getReverseStartCursor()) {
                    PageView.selectEndMove$default(binding.readView.getCurPage(), event.getRawX() - binding.cursorRight.getWidth(), event.getRawY() - binding.cursorRight.getHeight(), false, 4, null);
                } else {
                    this.moveSelectStart = true;
                    PageView.selectStartMove$default(binding.readView.getCurPage(), binding.cursorLeft.getWidth() + event.getRawX(), event.getRawY() - binding.cursorLeft.getHeight(), false, 4, null);
                }
            } else if (id == R.id.cursor_right) {
                if (binding.readView.getCurPage().getReverseEndCursor()) {
                    this.moveSelectStart = true;
                    PageView.selectStartMove$default(binding.readView.getCurPage(), binding.cursorLeft.getWidth() + event.getRawX(), event.getRawY() - binding.cursorLeft.getHeight(), false, 4, null);
                } else {
                    PageView.selectEndMove$default(binding.readView.getCurPage(), event.getRawX() - binding.cursorRight.getWidth(), event.getRawY() - binding.cursorRight.getHeight(), false, 4, null);
                }
            }
        }
        return true;
    }

    public final void onUnlockClick(@NotNull Ways payWay) {
        Intrinsics.checkNotNullParameter(payWay, "payWay");
        ReadConfig readConfig = ReadConfig.INSTANCE;
        if (readConfig.getFirstPage() || readConfig.getLastPage()) {
            return;
        }
        getViewModel().getToastReShow();
        String way = payWay.getWay();
        switch (way.hashCode()) {
            case -1430680109:
                if (way.equals("join_us")) {
                    this.toUnlock = true;
                    String posterHtml = payWay.getData().getPosterHtml();
                    Intent intent = new Intent("com.psychiatrygarden.activity.WebLongSaveActivity");
                    intent.putExtra("title", "活动");
                    intent.putExtra("web_url", posterHtml);
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setPackage(getPackageName());
                    startActivity(intent);
                    return;
                }
                return;
            case -456661547:
                if (way.equals("buy_ebook")) {
                    this.toUnlock = true;
                    if (TextUtils.isEmpty(StringsKt__StringsKt.trim((CharSequence) payWay.getData().getPrice()).toString())) {
                        return;
                    }
                    String string = new BigDecimal(payWay.getData().getPrice()).multiply(new BigDecimal("100")).toString();
                    Intrinsics.checkNotNullExpressionValue(string, "BigDecimal(payWay.data.p…ecimal(\"100\")).toString()");
                    Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse("ebook://ykb_buy_ebook_order_confirm"));
                    intent2.addCategory("android.intent.category.DEFAULT");
                    intent2.setPackage(getPackageName());
                    intent2.putExtra("quantity", "1");
                    intent2.putExtra("ebook_id", payWay.getData().getBookId());
                    intent2.putExtra("total_amount", string);
                    intent2.putExtra("price", string);
                    intent2.putExtra("user_address_id", "");
                    intent2.putExtra("flag", "");
                    intent2.putExtra("goodType", "4");
                    intent2.putExtra("upgrade_type", "");
                    intent2.putExtra("message", "");
                    startActivity(intent2);
                    return;
                }
                return;
            case 321961112:
                if (!way.equals("svip_enable")) {
                    return;
                }
                break;
            case 812937189:
                if (!way.equals("vip_enable")) {
                    return;
                }
                break;
            default:
                return;
        }
        this.toUnlock = true;
        Intent intent3 = new Intent("com.psychiatrygarden.activity.vip.MemberCenterActivity");
        intent3.addCategory("android.intent.category.DEFAULT");
        intent3.setPackage(getPackageName());
        startActivity(intent3);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override // com.ykb.ebook.page.ReadBook.CallBack
    public void pageChanged() {
        this.pageChanged = true;
        ReadBook readBook = ReadBook.INSTANCE;
        if (readBook.getDurPageIndex() != this.currentPage) {
            this.currentPage = readBook.getDurPageIndex();
            this.drawLineText = "";
            this.lineId = "";
            TextActionMenu textActionMenu = this.textActionMenu;
            if (textActionMenu != null) {
                View view = getBinding().textMenuPosition;
                Intrinsics.checkNotNullExpressionValue(view, "binding.textMenuPosition");
                TextActionMenu textActionMenu2 = this.textActionMenu;
                Pair<Integer, Integer> offset = textActionMenu2 != null ? textActionMenu2.getOffset() : null;
                Intrinsics.checkNotNull(offset);
                int iIntValue = offset.getFirst().intValue();
                TextActionMenu textActionMenu3 = this.textActionMenu;
                Pair<Integer, Integer> offset2 = textActionMenu3 != null ? textActionMenu3.getOffset() : null;
                Intrinsics.checkNotNull(offset2);
                ViewExtensionsKt.dismissWithUpdate(textActionMenu, view, iIntValue, offset2.getSecond().intValue());
            }
            getBinding().readView.cancelSelect();
            ImageView imageView = getBinding().cursorRight;
            Intrinsics.checkNotNullExpressionValue(imageView, "binding.cursorRight");
            ViewExtensionsKt.invisible(imageView);
            ImageView imageView2 = getBinding().cursorLeft;
            Intrinsics.checkNotNullExpressionValue(imageView2, "binding.cursorLeft");
            ViewExtensionsKt.invisible(imageView2);
        }
        this.cursorHandleFlag = false;
        getBinding().readView.onPageChange();
        getHandler().post(new Runnable() { // from class: com.ykb.ebook.activity.e1
            @Override // java.lang.Runnable
            public final void run() {
                ReadBookActivity.pageChanged$lambda$53(this.f26118c);
            }
        });
        this.executor.execute(new Runnable() { // from class: com.ykb.ebook.activity.f1
            @Override // java.lang.Runnable
            public final void run() {
                ReadBookActivity.pageChanged$lambda$54(this.f26126c);
            }
        });
    }

    public final void readFirstPageContent() {
        PageDelegate pageDelegate = getBinding().readView.getPageDelegate();
        if (pageDelegate != null) {
            pageDelegate.setCancel(false);
        }
        getBinding().readView.readFirstPageContent();
    }

    @Override // com.ykb.ebook.weight.ReadView.Callback
    public void screenOffTimerStart() {
        getHandler().post(new Runnable() { // from class: com.ykb.ebook.activity.w0
            @Override // java.lang.Runnable
            public final void run() {
                ReadBookActivity.screenOffTimerStart$lambda$18(this.f26211c);
            }
        });
    }

    @Override // com.ykb.ebook.weight.ReadMenu.Callback
    public void scrollAnimChange() {
        getBinding().readView.upPageAnim();
    }

    @Override // com.ykb.ebook.weight.TextActionMenu.Callback
    public void sendToClip(@NotNull String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        if (Intrinsics.areEqual(text, "")) {
            text = this.drawLineText;
        }
        ((ClipboardManager) SystemServicesKt.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText(null, StringExtensionsKt.formatContent(text)));
        Toast.makeText(this, R.string.copy_complete, 1).show();
    }

    public final void setLibraryState() {
        getBinding().readMenu.setAddLibaaryTv();
        getBinding().readView.getCurPage().setLibraryState();
        EventBus.getDefault().post("updateBookList");
    }

    public final void setOnQuestionClick(boolean z2) {
        this.onQuestionClick = z2;
    }

    @Override // com.ykb.ebook.weight.ContentTextView.CallBack
    public void setSelectingSearchResult(boolean z2) {
        this.isSelectingSearchResult = z2;
    }

    public final void setToUnlock(boolean z2) {
        this.toUnlock = z2;
    }

    @Override // com.ykb.ebook.weight.ReadView.Callback
    public void showActionMenu() {
        getBinding().readView.cancelSelect();
        ImageView imageView = getBinding().cursorRight;
        Intrinsics.checkNotNullExpressionValue(imageView, "binding.cursorRight");
        ViewExtensionsKt.invisible(imageView);
        ImageView imageView2 = getBinding().cursorLeft;
        Intrinsics.checkNotNullExpressionValue(imageView2, "binding.cursorLeft");
        ViewExtensionsKt.invisible(imageView2);
        if (this.drawLineText.length() > 0) {
            this.drawLineText = "";
            if (this.hasNote) {
                showReadMenuView();
            }
        } else {
            TextActionMenu textActionMenu = this.textActionMenu;
            if (textActionMenu != null && textActionMenu.isShowing()) {
                TextActionMenu textActionMenu2 = this.textActionMenu;
                if (textActionMenu2 != null) {
                    View view = getBinding().textMenuPosition;
                    Intrinsics.checkNotNullExpressionValue(view, "binding.textMenuPosition");
                    TextActionMenu textActionMenu3 = this.textActionMenu;
                    Pair<Integer, Integer> offset = textActionMenu3 != null ? textActionMenu3.getOffset() : null;
                    Intrinsics.checkNotNull(offset);
                    int iIntValue = offset.getFirst().intValue();
                    TextActionMenu textActionMenu4 = this.textActionMenu;
                    Pair<Integer, Integer> offset2 = textActionMenu4 != null ? textActionMenu4.getOffset() : null;
                    Intrinsics.checkNotNull(offset2);
                    ViewExtensionsKt.dismissWithUpdate(textActionMenu2, view, iIntValue, offset2.getSecond().intValue());
                }
            } else {
                ReadConfig readConfig = ReadConfig.INSTANCE;
                if (!readConfig.getFirstPage() && !readConfig.getLastPage()) {
                    showReadMenuView();
                }
            }
        }
        this.cursorHandleFlag = false;
    }

    @Override // com.ykb.ebook.weight.ContentTextView.CallBack
    public void showDrawLineTextActionMenu(@NotNull TextColumn column, boolean drawOrCancel, int color, int style) {
        Intrinsics.checkNotNullParameter(column, "column");
        ReadConfig readConfig = ReadConfig.INSTANCE;
        if (readConfig.getFirstPage() || readConfig.getLastPage()) {
            return;
        }
        TextActionMenu textActionMenu = this.textActionMenu;
        if (textActionMenu != null) {
            textActionMenu.dismiss();
        }
        this.hasNote = false;
        int startPosition = -1;
        int length = -1;
        for (Note note : getBinding().readView.getCurPage().getTextChapter().getChapter().getDrawNotesList()) {
            if (Intrinsics.areEqual(note.getId(), column.getLineId())) {
                this.drawLineText = note.getDrawContent();
                this.lineId = note.getId();
                this.startPosition = note.getStartPosition();
                if (note.getType() == 1) {
                    startPosition = note.getStartPosition();
                    length = note.getLength();
                    this.hasNote = note.hasNote();
                }
            }
        }
        if (this.hasNote) {
            ContentTextView.CallBack.DefaultImpls.onNoteClick$default(this, getBinding().readView.getCurPage().getTextPage().textChapter.getChapter().getId(), startPosition, length, this.drawLineText, this.lineId, true, 0.0f, 0.0f, 192, null);
            return;
        }
        Log.INSTANCE.logD("selectedText 点击文字弹框", getSelectedText());
        int lineTop = ((int) column.getTextLine().getLineTop()) - ScreenUtil.getPxByDp((Context) this, 10.0f);
        column.getStart();
        this.actionMenuY = lineTop;
        TextActionMenu textActionMenu2 = new TextActionMenu(this, this);
        this.textActionMenu = textActionMenu2;
        textActionMenu2.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.ykb.ebook.activity.h1
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                ReadBookActivity.showDrawLineTextActionMenu$lambda$42(this.f26136c);
            }
        });
        TextActionMenu textActionMenu3 = this.textActionMenu;
        if (textActionMenu3 != null) {
            textActionMenu3.initCheck(drawOrCancel, color, style, (24 & 8) != 0 ? false : false, (24 & 16) != 0 ? false : false);
        }
        TextActionMenu textActionMenu4 = this.textActionMenu;
        if (textActionMenu4 != null) {
            textActionMenu4.showAtLocation(getBinding().textMenuPosition, 49, 0, lineTop);
        }
    }

    public final void showGuidDialog() {
        if (ContextExtensionsKt.getPrefBoolean$default(AppCtxKt.getAppCtx(), PreferKeyKt.READ_GUIDE, false, 2, null)) {
            return;
        }
        new ReadGuideDialog.Builder(this).addOnDismissListener(new BasicDialog.OnDismissListener() { // from class: com.ykb.ebook.activity.ReadBookActivity.showGuidDialog.1
            @Override // com.ykb.ebook.dialog.BasicDialog.OnDismissListener
            public void onDismiss(@Nullable BasicDialog dialog) {
                ContextExtensionsKt.putPrefBoolean(AppCtxKt.getAppCtx(), PreferKeyKt.READ_GUIDE, true);
                if (ContextExtensionsKt.getPrefBoolean$default(AppCtxKt.getAppCtx(), PreferKeyKt.TEXT_OPERATION, false, 2, null)) {
                    return;
                }
                new TextOperationDialog.Builder(ReadBookActivity.this, true).show();
            }
        }).show();
    }

    @Override // com.ykb.ebook.weight.ReadView.Callback
    public void showHideContinueRead(boolean show) {
    }

    @Override // com.ykb.ebook.weight.ReadView.Callback
    public void showTextActionMenu(boolean drawOrCancel, int color, int style, int startPosition, @Nullable String lineId) {
        ReadConfig readConfig = ReadConfig.INSTANCE;
        if (readConfig.getFirstPage() || readConfig.getLastPage()) {
            return;
        }
        this.editUnderLineInfo = checkSelectTextHasDrawLine();
        TextActionMenu textActionMenu = this.textActionMenu;
        if (textActionMenu != null) {
            textActionMenu.dismiss();
        }
        this.color = color;
        this.style = style;
        if (!TextUtils.isEmpty(lineId)) {
            Intrinsics.checkNotNull(lineId);
            this.lineId = lineId;
        }
        this.drawOrCancel = drawOrCancel;
        String str = this.drawLineText;
        int selectTextPosition = this.endSelectStartPosition;
        if (selectTextPosition <= 0) {
            selectTextPosition = getBinding().readView.getSelectTextPosition();
        }
        int textChapterPositionIndex = getTextChapterPositionIndex(str, selectTextPosition);
        this.lineRangeSelect = false;
        this.lineRangeStartPosition = -1;
        boolean z2 = false;
        for (Note note : getBinding().readView.getCurPage().getTextPage().textChapter.getChapter().getDrawNotesList()) {
            if ((note.getStartPosition() + note.getLength()) - 1 >= textChapterPositionIndex && !TextUtils.isEmpty(lineId) && TextUtils.equals(note.getId(), lineId) && !z2) {
                this.lineRangeSelect = true;
                this.lineRangeStartPosition = note.getStartPosition();
                Log.INSTANCE.logD("select_range", "content = " + note.getDrawContent() + ",start_position = " + note.getStartPosition());
                this.drawOrCancel = false;
                z2 = true;
            }
        }
        TextActionMenu textActionMenu2 = new TextActionMenu(this, this);
        this.textActionMenu = textActionMenu2;
        boolean z3 = this.drawOrCancel;
        EditUnderLineInfo editUnderLineInfo = this.editUnderLineInfo;
        Intrinsics.checkNotNull(editUnderLineInfo);
        textActionMenu2.initCheck(z3, color, style, editUnderLineInfo.getHasUnderLine(), this.lineRangeSelect);
        TextActionMenu textActionMenu3 = this.textActionMenu;
        if (textActionMenu3 != null) {
            textActionMenu3.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.ykb.ebook.activity.b1
                @Override // android.widget.PopupWindow.OnDismissListener
                public final void onDismiss() {
                    ReadBookActivity.showTextActionMenu$lambda$20(this.f26101c);
                }
            });
        }
        int navigationBarHeight = ActivityExtensionsKt.getNavigationBarGravity(this) == 80 ? ActivityExtensionsKt.getNavigationBarHeight(this) : 0;
        TextActionMenu textActionMenu4 = this.textActionMenu;
        if (textActionMenu4 != null) {
            View view = getBinding().textMenuPosition;
            Intrinsics.checkNotNullExpressionValue(view, "binding.textMenuPosition");
            textActionMenu4.show(view, getBinding().getRoot().getHeight() + navigationBarHeight, (int) getBinding().textMenuPosition.getX(), ((int) getBinding().textMenuPosition.getY()) - ScreenUtil.getPxByDp((Context) this, 10), ((int) (this.drawLineFromNote ? this.clickNoteY : getBinding().cursorLeft.getY())) + getBinding().cursorLeft.getHeight(), (int) getBinding().cursorRight.getX(), ((int) (this.drawLineFromNote ? this.clickNoteY : getBinding().cursorRight.getY())) + getBinding().cursorRight.getHeight());
        }
        TextActionMenu textActionMenu5 = this.textActionMenu;
        if (textActionMenu5 != null) {
            textActionMenu5.update();
        }
    }

    @Override // com.ykb.ebook.weight.ReadMenu.Callback
    public void skipToChapter(int index) {
        ReadBook.openChapter$default(ReadBook.INSTANCE, index, 0, null, 6, null);
    }

    @Override // com.ykb.ebook.common_interface.TimeBatteryCallback
    public void timeBack() {
        getBinding().readView.upTime();
    }

    @Override // com.ykb.ebook.weight.ReadMenu.Callback
    public void uiConfigChange() {
        ChapterProvider.INSTANCE.upStyle();
        if (isInitFinish()) {
            ReadBook.loadContent$default(ReadBook.INSTANCE, false, false, null, 6, null);
        }
    }

    @Override // com.ykb.ebook.page.ReadBook.CallBack
    public void upContent(int relativePosition, boolean resetPageOffset, @Nullable Function0<Unit> success) {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C10321(relativePosition, resetPageOffset, success, null), 3, null);
    }

    @Override // com.ykb.ebook.page.ReadBook.CallBack
    public void upMenuView() {
        getHandler().post(new Runnable() { // from class: com.ykb.ebook.activity.i1
            @Override // java.lang.Runnable
            public final void run() {
                ReadBookActivity.upMenuView$lambda$49(this.f26144c);
            }
        });
    }

    @Override // com.ykb.ebook.page.ReadBook.CallBack
    public void upPageAnim() {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C10331(null), 3, null);
    }

    @Override // com.ykb.ebook.weight.ContentTextView.CallBack
    public void upSelectedEnd(float x2, float y2) {
        ActivityReadBookBinding binding = getBinding();
        this.endX = x2;
        this.endY = y2;
        binding.cursorRight.setX(x2 - (r1.getWidth() / 2));
        binding.cursorRight.setY((y2 - r1.getHeight()) + 20);
        ImageView cursorRight = binding.cursorRight;
        Intrinsics.checkNotNullExpressionValue(cursorRight, "cursorRight");
        ViewExtensionsKt.visible(cursorRight, true);
        Log.INSTANCE.logD(" selectedText 长按多选", "  x = " + binding.cursorRight.getX() + ", x new = " + x2 + ",end y = " + binding.cursorRight.getY() + ",end y new = " + y2);
        this.drawLineFromNote = false;
        this.drawLineText = getSelectedText();
    }

    @Override // com.ykb.ebook.weight.ContentTextView.CallBack
    public void upSelectedStart(float x2, float y2, float top2) {
        ActivityReadBookBinding binding = getBinding();
        this.startX = x2;
        this.startY = y2;
        binding.cursorLeft.setX(x2 - (r1.getWidth() / 2));
        binding.cursorLeft.setY(y2 - r1.getHeight());
        ImageView cursorLeft = binding.cursorLeft;
        Intrinsics.checkNotNullExpressionValue(cursorLeft, "cursorLeft");
        ViewExtensionsKt.visible(cursorLeft, true);
        binding.textMenuPosition.setX(x2);
        binding.textMenuPosition.setY(top2);
    }

    @Override // com.ykb.ebook.weight.ReadMenu.Callback, com.ykb.ebook.weight.ReadView.Callback
    public void upSystemUiVisibility() {
        if (Build.VERSION.SDK_INT >= 30) {
            WindowInsetsController insetsController = getWindow().getInsetsController();
            if (insetsController != null) {
                if (getMenuLayoutIsVisible() || !ReadConfig.INSTANCE.getHideStatusBar()) {
                    insetsController.show(WindowInsets.Type.statusBars());
                    return;
                } else {
                    insetsController.hide(WindowInsets.Type.statusBars());
                    return;
                }
            }
            return;
        }
        int systemUiVisibility = getWindow().getDecorView().getSystemUiVisibility() | 2 | 4096;
        if (!isInMultiWindow() && ReadConfig.INSTANCE.getHideStatusBar()) {
            systemUiVisibility |= 1024;
        }
        ReadConfig readConfig = ReadConfig.INSTANCE;
        if (readConfig.getHideStatusBar() && !getMenuLayoutIsVisible()) {
            systemUiVisibility |= 4;
        }
        getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
        if (!readConfig.getHideStatusBar()) {
            getWindow().clearFlags(1024);
            getWindow().getDecorView().setSystemUiVisibility(getWindow().getDecorView().getSystemUiVisibility() & (-5));
        }
        if (readConfig.getColorMode() != 2) {
            getWindow().clearFlags(67108864);
            getWindow().addFlags(Integer.MIN_VALUE);
            getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility | 8192);
        }
    }

    @Override // com.ykb.ebook.weight.ReadView.Callback
    public void updateSelectCursor(float startX, float startY, float endX, float endY, @NotNull String selectText) {
        Intrinsics.checkNotNullParameter(selectText, "selectText");
        this.drawLineText = selectText;
        getBinding().cursorLeft.setX(startX - (getBinding().cursorLeft.getWidth() / 2));
        getBinding().cursorLeft.setY(startY - getBinding().cursorLeft.getHeight());
        getBinding().cursorRight.setX(endX - (getBinding().cursorRight.getWidth() / 2));
        getBinding().cursorRight.setY((endY - getBinding().cursorRight.getHeight()) + 20);
        ImageView imageView = getBinding().cursorLeft;
        Intrinsics.checkNotNullExpressionValue(imageView, "binding.cursorLeft");
        ViewExtensionsKt.visible(imageView);
        ImageView imageView2 = getBinding().cursorRight;
        Intrinsics.checkNotNullExpressionValue(imageView2, "binding.cursorRight");
        ViewExtensionsKt.visible(imageView2);
    }

    @Override // com.ykb.ebook.weight.ReadView.Callback
    public void updateSelectTextPosition(@NotNull TextPosition startTextPosition, @NotNull TextPosition endTextPosition) {
        Intrinsics.checkNotNullParameter(startTextPosition, "startTextPosition");
        Intrinsics.checkNotNullParameter(endTextPosition, "endTextPosition");
        this.startTextPosition = startTextPosition;
        this.endTextPosition = endTextPosition;
    }

    @Override // com.ykb.ebook.base.BaseActivity
    @NotNull
    public ActivityReadBookBinding getBinding() {
        return (ActivityReadBookBinding) this.binding.getValue();
    }

    @Override // com.ykb.ebook.base.BaseVmActivity
    @NotNull
    public ReadBookViewModel initViewModel() {
        ReadBookViewModel readBookViewModel = (ReadBookViewModel) new ViewModelProvider(this).get(ReadBookViewModel.class);
        this.mviewModel = readBookViewModel;
        Intrinsics.checkNotNull(readBookViewModel);
        return readBookViewModel;
    }

    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JF\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\fJV\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/ykb/ebook/activity/ReadBookActivity$Companion;", "", "()V", "LINE_OPERATION_ADD", "", "LINE_OPERATION_DELETE", "LINE_OPERATION_EDIT", "newIntent", "Landroid/content/Intent;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "bookId", "", "userId", "appId", PLVSocketUserConstant.ROLE_ADMIN, "avatar", "token", "secret", "chapterId", "position", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final Intent newIntent(@NotNull Context context, @NotNull String bookId, @NotNull String userId, @NotNull String appId, @NotNull String admin, @NotNull String avatar, @NotNull String token, @NotNull String secret) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(bookId, "bookId");
            Intrinsics.checkNotNullParameter(userId, "userId");
            Intrinsics.checkNotNullParameter(appId, "appId");
            Intrinsics.checkNotNullParameter(admin, "admin");
            Intrinsics.checkNotNullParameter(avatar, "avatar");
            Intrinsics.checkNotNullParameter(token, "token");
            Intrinsics.checkNotNullParameter(secret, "secret");
            Intent intent = new Intent(context, (Class<?>) ReadBookActivity.class);
            intent.putExtra("book_id", bookId);
            intent.putExtra("user_id", userId);
            intent.putExtra("app_id", appId);
            intent.putExtra(PLVSocketUserConstant.ROLE_ADMIN, admin);
            intent.putExtra("avatar", avatar);
            intent.putExtra("token", token);
            intent.putExtra("secret", secret);
            return intent;
        }

        @NotNull
        public final Intent newIntent(@NotNull Context context, @NotNull String bookId, @NotNull String userId, @NotNull String appId, @NotNull String admin, @NotNull String avatar, @NotNull String token, @NotNull String secret, @NotNull String chapterId, int position) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(bookId, "bookId");
            Intrinsics.checkNotNullParameter(userId, "userId");
            Intrinsics.checkNotNullParameter(appId, "appId");
            Intrinsics.checkNotNullParameter(admin, "admin");
            Intrinsics.checkNotNullParameter(avatar, "avatar");
            Intrinsics.checkNotNullParameter(token, "token");
            Intrinsics.checkNotNullParameter(secret, "secret");
            Intrinsics.checkNotNullParameter(chapterId, "chapterId");
            Intent intent = new Intent(context, (Class<?>) ReadBookActivity.class);
            intent.putExtra("book_id", bookId);
            intent.putExtra("user_id", userId);
            intent.putExtra("app_id", appId);
            intent.putExtra(PLVSocketUserConstant.ROLE_ADMIN, admin);
            intent.putExtra("avatar", avatar);
            intent.putExtra("token", token);
            intent.putExtra("secret", secret);
            intent.putExtra("chapterId", chapterId);
            intent.putExtra("position", position);
            return intent;
        }
    }

    @Override // com.ykb.ebook.weight.ContentTextView.CallBack
    public void onNoteClick(@NotNull String chapterId, int startPosition, int length, @NotNull final String text, @Nullable String lineId, final boolean hasUnderLine, final float x2, final float y2) {
        Intrinsics.checkNotNullParameter(chapterId, "chapterId");
        Intrinsics.checkNotNullParameter(text, "text");
        Log.INSTANCE.logD("note click", text);
        ReadConfig readConfig = ReadConfig.INSTANCE;
        if (readConfig.getFirstPage() || readConfig.getLastPage() || FastClickUtilKt.isFastClick()) {
            return;
        }
        this.startPosition = startPosition;
        this.drawLineText = text;
        this.chapterId = chapterId;
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        for (Note note : getBinding().readView.getCurPage().getTextChapter().getChapter().getDrawNotesList()) {
            if (TextUtils.equals(StringExtensionsKt.formatContent(note.getDrawContent()), StringExtensionsKt.formatContent(text)) && note.getStyle() == 1) {
                this.drawType = 0;
                booleanRef.element = true;
            }
        }
        NoteListDialog.Builder deleteClick = new NoteListDialog.Builder(this).setParams(this.bookId, chapterId, startPosition, length, text, (64 & 32) != 0 ? false : hasUnderLine, (64 & 64) != 0).setDrawLineClick(new Function1<Boolean, Unit>() { // from class: com.ykb.ebook.activity.ReadBookActivity$onNoteClick$builder$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                invoke(bool.booleanValue());
                return Unit.INSTANCE;
            }

            public final void invoke(boolean z2) {
                Iterator<T> it = this.this$0.getBinding().readView.getCurPage().getTextPage().getLines().iterator();
                int lineColor = 0;
                while (it.hasNext()) {
                    for (BaseColumn baseColumn : ((TextLine) it.next()).getColumns()) {
                        if (baseColumn instanceof TextColumn) {
                            TextColumn textColumn = (TextColumn) baseColumn;
                            if (textColumn.getIsDrawUnderLine()) {
                                lineColor = textColumn.getLineColor();
                            } else if (textColumn.getIsDrawDashLine()) {
                                lineColor = textColumn.getDashColor();
                            }
                        }
                    }
                }
                this.this$0.drawLineFromNote = true;
                boolean z3 = hasUnderLine;
                int i2 = z3 ? 2 : 0;
                if (z3 && !z2) {
                    i2 = 1;
                }
                if (i2 == 1) {
                    List<Note> drawNotesList = this.this$0.getBinding().readView.getCurPage().getTextPage().textChapter.getChapter().getDrawNotesList();
                    ReadBookActivity readBookActivity = this.this$0;
                    for (Note note2 : drawNotesList) {
                        if (TextUtils.equals(StringExtensionsKt.formatContent(note2.getDrawContent()), StringExtensionsKt.formatContent(readBookActivity.getSelectedText()))) {
                            readBookActivity.lineId = note2.getId();
                        }
                    }
                }
                this.this$0.color = lineColor;
                this.this$0.drawType = 0;
                this.this$0.clickNoteX = x2;
                this.this$0.clickNoteY = y2;
                Log.INSTANCE.logD("note_operate", i2 != 1 ? i2 != 2 ? "添加划线" : "编辑划线" : "删除划线");
                this.this$0.onDrawLineBack(text, lineColor, booleanRef.element ? 1 : 0, i2);
            }
        }).setShareClick(new Function0<Unit>() { // from class: com.ykb.ebook.activity.ReadBookActivity$onNoteClick$builder$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                BookInfo bookInfo = this.this$0.bookInfo;
                String title = bookInfo != null ? bookInfo.getTitle() : null;
                BookInfo bookInfo2 = this.this$0.bookInfo;
                String author = bookInfo2 != null ? bookInfo2.getAuthor() : null;
                BookInfo bookInfo3 = this.this$0.bookInfo;
                String thumbnail = bookInfo3 != null ? bookInfo3.getThumbnail() : null;
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setPackage(this.this$0.getPackageName());
                intent.setData(Uri.parse("ebook://ykbshare/?bookShareText=" + StringExtensionsKt.formatContent(this.this$0.drawLineText) + "&bookName=" + title + "&bookAuthor=" + author + "&bookImageUrl=" + thumbnail + "&theme=" + ReadConfig.INSTANCE.getColorMode()));
                this.this$0.startActivity(intent);
            }
        }).setDeleteClick(new Function0<Unit>() { // from class: com.ykb.ebook.activity.ReadBookActivity$onNoteClick$builder$3
            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                ReadBook.INSTANCE.loadContent(false, true, new Function0<Unit>() { // from class: com.ykb.ebook.activity.ReadBookActivity$onNoteClick$builder$3.1
                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                    }
                });
            }
        });
        deleteClick.show();
        BasicDialog dialog = deleteClick.getDialog();
        this.noteDialog = dialog;
        if (dialog != null) {
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.ykb.ebook.activity.d1
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    ReadBookActivity.onNoteClick$lambda$48(this.f26113c, dialogInterface);
                }
            });
        }
    }
}
