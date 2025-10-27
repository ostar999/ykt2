package com.yddmi.doctor.repository;

import com.alipay.sdk.cons.c;
import com.aliyun.player.aliyunplayerbase.net.ServiceCommon;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.catchpig.mvvm.network.manager.NetManager;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.exoplayer2.util.MimeTypes;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.psychiatrygarden.utils.Constants;
import com.tencent.open.SocialConstants;
import com.yddmi.doctor.config.YddHostConfig;
import com.yddmi.doctor.config.YddUserManager;
import com.yddmi.doctor.entity.request.BankReqResult;
import com.yddmi.doctor.entity.request.ExamCheatReq;
import com.yddmi.doctor.entity.request.ExamDetailInfoReq;
import com.yddmi.doctor.entity.request.ExamResultReq;
import com.yddmi.doctor.entity.request.ExamSaveReq;
import com.yddmi.doctor.entity.request.ExamUpdateReq;
import com.yddmi.doctor.entity.request.FavoriteSaveReq;
import com.yddmi.doctor.entity.request.FeedBackReq;
import com.yddmi.doctor.entity.request.HomeAtlasReq;
import com.yddmi.doctor.entity.request.HomeDaskSaveReq;
import com.yddmi.doctor.entity.request.HomeGuideReq;
import com.yddmi.doctor.entity.request.HomeMeAuditSubmit;
import com.yddmi.doctor.entity.request.HomeMsgReq;
import com.yddmi.doctor.entity.request.HomeTenantReq;
import com.yddmi.doctor.entity.request.InquiryCheckReq;
import com.yddmi.doctor.entity.request.InquiryDiseaseReq;
import com.yddmi.doctor.entity.request.InquiryDiseaseUpdateReq;
import com.yddmi.doctor.entity.request.InquiryPatientInfoSave;
import com.yddmi.doctor.entity.request.InquirySaveBasisReq;
import com.yddmi.doctor.entity.request.InquirySaveNoteReq;
import com.yddmi.doctor.entity.request.InquiryUpdateStatus;
import com.yddmi.doctor.entity.request.IntegralAcquireReq;
import com.yddmi.doctor.entity.request.IntegralUnlockReq;
import com.yddmi.doctor.entity.request.LoginWx;
import com.yddmi.doctor.entity.request.LoginWxBindingReq;
import com.yddmi.doctor.entity.request.LoginWxReBindingReq;
import com.yddmi.doctor.entity.request.MeReplyReq;
import com.yddmi.doctor.entity.request.PointSaveReq;
import com.yddmi.doctor.entity.result.AppConfig;
import com.yddmi.doctor.entity.result.AppVersion;
import com.yddmi.doctor.entity.result.AtlasRow;
import com.yddmi.doctor.entity.result.AuthLoginResult;
import com.yddmi.doctor.entity.result.AuthLoginResult1;
import com.yddmi.doctor.entity.result.BannerData;
import com.yddmi.doctor.entity.result.ExamCheat;
import com.yddmi.doctor.entity.result.ExamItem;
import com.yddmi.doctor.entity.result.ExamLeaderBoardResult;
import com.yddmi.doctor.entity.result.ExamList;
import com.yddmi.doctor.entity.result.ExamQuestion;
import com.yddmi.doctor.entity.result.ExamResultDetail;
import com.yddmi.doctor.entity.result.ExamResultVo;
import com.yddmi.doctor.entity.result.ExamRuleResult;
import com.yddmi.doctor.entity.result.FeedBackPageList;
import com.yddmi.doctor.entity.result.HeartData;
import com.yddmi.doctor.entity.result.HeartDetail;
import com.yddmi.doctor.entity.result.HomeAtlas;
import com.yddmi.doctor.entity.result.HomeAtlasDetails;
import com.yddmi.doctor.entity.result.HomeClinicalDetail;
import com.yddmi.doctor.entity.result.HomeConfig;
import com.yddmi.doctor.entity.result.HomeDaskResult;
import com.yddmi.doctor.entity.result.HomeGuideList;
import com.yddmi.doctor.entity.result.HomeGuideListItem;
import com.yddmi.doctor.entity.result.HomeItem;
import com.yddmi.doctor.entity.result.HomeMsg;
import com.yddmi.doctor.entity.result.HomeMsgList;
import com.yddmi.doctor.entity.result.HomeStudyResult;
import com.yddmi.doctor.entity.result.HomeTeaching;
import com.yddmi.doctor.entity.result.HomeTeachingCase;
import com.yddmi.doctor.entity.result.HomeTeachingClass;
import com.yddmi.doctor.entity.result.InquiryAsk;
import com.yddmi.doctor.entity.result.InquiryAskInfo;
import com.yddmi.doctor.entity.result.InquiryAskList;
import com.yddmi.doctor.entity.result.InquiryBindData;
import com.yddmi.doctor.entity.result.InquiryCheckInfo;
import com.yddmi.doctor.entity.result.InquiryDiseaseList;
import com.yddmi.doctor.entity.result.InquiryNote;
import com.yddmi.doctor.entity.result.MeAgencyRow;
import com.yddmi.doctor.entity.result.MeFiles;
import com.yddmi.doctor.entity.result.MeProfile;
import com.yddmi.doctor.entity.result.MeProfileIntegral;
import com.yddmi.doctor.entity.result.RowCase;
import com.yddmi.doctor.entity.result.ScoreAnalyseCorrect;
import com.yddmi.doctor.entity.result.ScoreAnalyseRadar;
import com.yddmi.doctor.entity.result.ScoreAnalyseTrainInfo;
import com.yddmi.doctor.entity.result.ScoreBlood;
import com.yddmi.doctor.entity.result.ScoreDetail;
import com.yddmi.doctor.entity.result.SkillCall;
import com.yddmi.doctor.entity.result.SkillHome;
import com.yddmi.doctor.entity.result.SkillHomeList;
import com.yddmi.doctor.entity.result.SkillIntegral;
import com.yddmi.doctor.entity.result.SkillLabel;
import com.yddmi.doctor.entity.result.SkillPayWx;
import com.yddmi.doctor.entity.result.SkillShare;
import com.yddmi.doctor.entity.result.SkillTicket;
import com.yddmi.doctor.entity.result.TeachCasebook;
import com.yddmi.doctor.entity.result.TeachRow;
import com.yddmi.doctor.entity.result.TrainInfoList;
import com.yddmi.doctor.network.api.OtherService;
import com.yddmi.doctor.network.api.YddApi;
import com.yddmi.doctor.network.api.YddService;
import com.yddmi.doctor.network.api.YddServiceDev;
import com.yddmi.doctor.network.api.YddServiceFormal;
import com.yddmi.doctor.network.api.YddServiceTest;
import com.yddmi.doctor.network.api.YddServiceTest126;
import com.yddmi.doctor.network.api.YddServiceTest192;
import com.yddmi.doctor.network.api.YddServiceUat;
import com.yddmi.doctor.utils.FucUtil;
import com.yddmi.doctor.utils.OtherUtils;
import com.yikaobang.yixue.R2;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import okhttp3.MultipartBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import retrofit2.http.Body;

@Metadata(d1 = {"\u0000Â\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f0\u000bJ \u0010\r\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f0\u000bJ\u0016\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\b2\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014J\u0006\u0010\u0015\u001a\u00020\u0012J\u0016\u0010\u0016\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00170\b2\u0006\u0010\n\u001a\u00020\u0018J\u0016\u0010\u0019\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001a0\b2\u0006\u0010\n\u001a\u00020\u001bJ\"\u0010\u001c\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\b2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f0\u000bJ\u000e\u0010\u001d\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\bJ\u000e\u0010\u001e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\bJ%\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010!\u001a\u00020\"2\b\b\u0002\u0010#\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010$J \u0010%\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010 0\b2\u0006\u0010!\u001a\u00020\"2\b\b\u0002\u0010#\u001a\u00020\"J!\u0010&\u001a\n\u0012\u0004\u0012\u00020(\u0018\u00010'2\u0006\u0010)\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010*J2\u0010+\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010,0\b2\u0006\u0010!\u001a\u00020\"2\u0006\u0010-\u001a\u00020\"2\b\b\u0002\u0010.\u001a\u00020\"2\b\b\u0002\u0010/\u001a\u00020\"J\u0019\u00100\u001a\u0002012\u0006\u0010)\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010*J\u000e\u00102\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001030\bJ\u0014\u00104\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020 \u0018\u00010'0\bJ\u001f\u00105\u001a\b\u0012\u0004\u0012\u00020\f0'2\u0006\u00106\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010*J+\u00107\u001a\f\u0012\u0006\u0012\u0004\u0018\u000108\u0018\u00010'2\u0006\u00109\u001a\u00020\"2\u0006\u0010!\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010$J\u001f\u0010:\u001a\b\u0012\u0004\u0012\u00020\f0'2\u0006\u00106\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010*J1\u0010;\u001a\n\u0012\u0004\u0012\u00020<\u0018\u00010'2\n\b\u0002\u0010=\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010>\u001a\u0004\u0018\u00010\fH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010?J\u001f\u0010@\u001a\b\u0012\u0004\u0012\u00020\f0'2\u0006\u00106\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010*J\u000e\u0010A\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\bJ$\u0010B\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010C0\b2\u0014\u0010D\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\u00010EJ\u0016\u0010F\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010G0\b2\u0006\u0010H\u001a\u00020\"J)\u0010I\u001a\u0004\u0018\u00010C2\u0014\u0010D\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\u00010EH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010JJ\u0019\u0010K\u001a\n\u0012\u0004\u0012\u00020L\u0018\u00010'H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010MJ\u001c\u0010N\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020L\u0018\u00010'0\b2\u0006\u0010O\u001a\u00020\fJ\u0013\u0010P\u001a\u0004\u0018\u00010QH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010MJ\u001f\u0010R\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u000208\u0018\u00010'0\bH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010MJ\u0019\u0010S\u001a\n\u0012\u0004\u0012\u000208\u0018\u00010'H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010MJ\u001c\u0010T\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020U\u0018\u00010'0\b2\u0006\u00106\u001a\u00020\"J\u001f\u0010V\u001a\b\u0012\u0004\u0012\u00020\f0'2\u0006\u00106\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010*J!\u0010W\u001a\n\u0012\u0004\u0012\u00020X\u0018\u00010'2\u0006\u0010Y\u001a\u00020ZH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010[J\u0016\u0010\\\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010]0\b2\u0006\u00106\u001a\u00020\"J$\u0010^\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010_0\b2\u0014\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\u00010EJ\u001b\u0010`\u001a\u0004\u0018\u00010a2\u0006\u0010b\u001a\u00020\fH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010cJ#\u0010d\u001a\u0004\u0018\u00010a2\u0006\u0010e\u001a\u00020\"2\u0006\u0010f\u001a\u00020\fH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010gJ\u0014\u0010h\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\f\u0018\u00010'0\bJ3\u0010i\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010j\u0018\u00010'2\u0006\u0010e\u001a\u00020\"2\u0006\u0010f\u001a\u00020\f2\u0006\u0010k\u001a\u00020\fH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010lJ6\u0010m\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010n0\b2\b\u0010H\u001a\u0004\u0018\u00010\f2\b\u0010o\u001a\u0004\u0018\u00010\f2\b\b\u0002\u0010.\u001a\u00020\"2\b\b\u0002\u0010/\u001a\u00020\"J!\u0010p\u001a\n\u0012\u0004\u0012\u00020q\u0018\u00010'2\u0006\u00109\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010*J\u001b\u0010r\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010s\u0018\u00010'H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010MJ/\u0010t\u001a\u0004\u0018\u00010u2\u0006\u0010v\u001a\u00020\"2\b\b\u0002\u0010.\u001a\u00020\"2\b\b\u0002\u0010/\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010wJ\u001b\u0010x\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010y\u0018\u00010'H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010MJ\u0014\u0010z\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\f\u0018\u00010'0\bJ\u0019\u0010{\u001a\n\u0012\u0004\u0012\u000208\u0018\u00010'H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010MJ\u0013\u0010|\u001a\u0004\u0018\u00010}H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010MJ\u0013\u0010~\u001a\u0004\u0018\u00010 H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010MJ\u0017\u0010\u007f\u001a\u000b\u0012\u0007\u0012\u0005\u0018\u00010\u0080\u00010\b2\u0006\u0010Y\u001a\u00020\"J:\u0010\u0081\u0001\u001a\u000b\u0012\u0005\u0012\u00030\u0082\u0001\u0018\u00010'2\u0006\u0010H\u001a\u00020\f2\t\b\u0002\u0010\u0083\u0001\u001a\u00020\"2\t\b\u0002\u0010\u0084\u0001\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0003\u0010\u0085\u0001J\u001a\u0010\u0086\u0001\u001a\u000b\u0012\u0007\u0012\u0005\u0018\u00010\u0087\u00010\b2\b\u0010>\u001a\u0004\u0018\u00010\fJ&\u0010\u0088\u0001\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\b2\u0015\u0010\u0089\u0001\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\u00010EJ$\u0010\u008a\u0001\u001a\f\u0012\u0006\u0012\u0004\u0018\u000108\u0018\u00010'2\u0006\u0010!\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010*J(\u0010\u008b\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0006\u0012\u0004\u0018\u000108\u0018\u00010'0\b2\u0007\u0010\u008c\u0001\u001a\u00020\"2\u0006\u0010!\u001a\u00020\"J6\u0010\u008d\u0001\u001a\u000b\u0012\u0007\u0012\u0005\u0018\u00010\u008e\u00010\b2\u0006\u0010)\u001a\u00020\"2\n\b\u0002\u0010.\u001a\u0004\u0018\u00010\"2\n\b\u0002\u0010/\u001a\u0004\u0018\u00010\"¢\u0006\u0003\u0010\u008f\u0001J9\u0010\u0090\u0001\u001a\u000b\u0012\u0007\u0012\u0005\u0018\u00010\u008e\u00010\b2\n\b\u0002\u0010.\u001a\u0004\u0018\u00010\"2\n\b\u0002\u0010/\u001a\u0004\u0018\u00010\"2\t\b\u0002\u0010\u0091\u0001\u001a\u00020\"¢\u0006\u0003\u0010\u0092\u0001J.\u0010\u0093\u0001\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\b2\u0007\u0010\u0094\u0001\u001a\u00020\f2\t\b\u0002\u0010\u0095\u0001\u001a\u00020\"2\t\b\u0002\u0010\u0096\u0001\u001a\u00020\fJ.\u0010\u0097\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0006\u0012\u0004\u0018\u00010U\u0018\u00010'0\b2\u0015\u0010\u0098\u0001\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\u00010EJ1\u0010\u0099\u0001\u001a\u0011\u0012\r\u0012\u000b\u0012\u0005\u0012\u00030\u009a\u0001\u0018\u00010'0\b2\t\u0010\u009b\u0001\u001a\u0004\u0018\u00010\f2\u0006\u00106\u001a\u00020\"2\u0006\u00109\u001a\u00020\"J\u001c\u0010\u009c\u0001\u001a\u0004\u0018\u00010y2\u0006\u0010Y\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010*J#\u0010\u009d\u0001\u001a\u000b\u0012\u0007\u0012\u0005\u0018\u00010\u009e\u00010\b2\u0006\u00106\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010*J/\u0010\u009f\u0001\u001a\u0012\u0012\u000e\u0012\f\u0012\u0006\u0012\u0004\u0018\u000108\u0018\u00010'0\b2\u0006\u00106\u001a\u00020\"2\u0006\u00109\u001a\u00020\"2\u0006\u0010!\u001a\u00020\"J4\u0010 \u0001\u001a\f\u0012\u0006\u0012\u0004\u0018\u000108\u0018\u00010'2\u0006\u00106\u001a\u00020\"2\u0006\u00109\u001a\u00020\"2\u0006\u0010!\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010wJ\u0010\u0010¡\u0001\u001a\u000b\u0012\u0007\u0012\u0005\u0018\u00010¢\u00010\bJ\u001a\u0010£\u0001\u001a\n\u0012\u0004\u0012\u00020 \u0018\u00010'H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010MJ\u0018\u0010¤\u0001\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\b2\u0007\u0010\u000f\u001a\u00030¥\u0001J\u001d\u0010¤\u0001\u001a\u0005\u0018\u00010¥\u00012\u0006\u0010)\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010*J'\u0010¦\u0001\u001a\u0004\u0018\u00010<2\b\u0010Y\u001a\u0004\u0018\u00010\f2\u0006\u0010)\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0003\u0010§\u0001J+\u0010¨\u0001\u001a\u0004\u0018\u00010\f2\u0015\u0010\u0089\u0001\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\u00010EH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010JJ$\u0010©\u0001\u001a\u000b\u0012\u0007\u0012\u0005\u0018\u00010ª\u00010\b2\u0007\u0010\u0094\u0001\u001a\u00020\f2\t\b\u0002\u0010\u0095\u0001\u001a\u00020\"J\u001c\u0010«\u0001\u001a\u0004\u0018\u00010<2\u0006\u0010>\u001a\u00020\fH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010cJF\u0010¬\u0001\u001a\u0004\u0018\u00010<2\u0006\u0010>\u001a\u00020\f2\u0007\u0010\u00ad\u0001\u001a\u00020\"2\u0007\u0010\u0096\u0001\u001a\u00020\f2\n\b\u0002\u0010®\u0001\u001a\u00030¯\u00012\t\b\u0002\u0010°\u0001\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0003\u0010±\u0001J\u0018\u0010²\u0001\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\b2\u0007\u0010³\u0001\u001a\u00020\fJ\u0018\u0010´\u0001\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\b2\u0007\u0010³\u0001\u001a\u00020\fJ\u0014\u0010µ\u0001\u001a\u0004\u0018\u00010<H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010MJ\u0018\u0010¶\u0001\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\b2\u0007\u0010·\u0001\u001a\u00020\"J\u0018\u0010¸\u0001\u001a\u000b\u0012\u0007\u0012\u0005\u0018\u00010¹\u00010\b2\u0006\u00106\u001a\u00020\"J'\u0010º\u0001\u001a\u000b\u0012\u0007\u0012\u0005\u0018\u00010»\u00010\b2\u0015\u0010\u0098\u0001\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\u00010EJ6\u0010¼\u0001\u001a\u000b\u0012\u0007\u0012\u0005\u0018\u00010\u008e\u00010\b2\u0007\u0010½\u0001\u001a\u00020\"2\u0007\u0010³\u0001\u001a\u00020\f2\b\b\u0002\u0010.\u001a\u00020\"2\b\b\u0002\u0010/\u001a\u00020\"J\"\u0010¾\u0001\u001a\n\u0012\u0004\u0012\u00020q\u0018\u00010'2\u0006\u00109\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010*J\u001d\u0010¿\u0001\u001a\u0005\u0018\u00010À\u00012\u0006\u00109\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010*J\u001d\u0010Á\u0001\u001a\u0005\u0018\u00010À\u00012\u0006\u00109\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010*J%\u0010Â\u0001\u001a\r\u0012\u0007\u0012\u0005\u0018\u00010À\u0001\u0018\u00010'2\u0006\u00109\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010*J\u001d\u0010Ã\u0001\u001a\u0005\u0018\u00010À\u00012\u0006\u00109\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010*J\u001d\u0010Ä\u0001\u001a\u0005\u0018\u00010À\u00012\u0006\u00109\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010*J%\u0010Å\u0001\u001a\r\u0012\u0007\u0012\u0005\u0018\u00010À\u0001\u0018\u00010'2\u0006\u00109\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010*J\u000f\u0010Æ\u0001\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\bJ,\u0010Ç\u0001\u001a\u0005\u0018\u00010È\u00012\u0015\u0010\u0089\u0001\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\u00010EH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010JJ-\u0010É\u0001\u001a\n\u0012\u0004\u0012\u00020<\u0018\u00010'2\u0006\u0010-\u001a\u00020\f2\b\b\u0002\u0010!\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0003\u0010§\u0001J\u001a\u0010Ê\u0001\u001a\n\u0012\u0004\u0012\u00020<\u0018\u00010'H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010MJ\u001a\u0010Ë\u0001\u001a\n\u0012\u0004\u0012\u00020<\u0018\u00010'H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010MJ\u001a\u0010Ì\u0001\u001a\n\u0012\u0004\u0012\u00020<\u0018\u00010'H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010MJ#\u0010Í\u0001\u001a\n\u0012\u0004\u0012\u00020<\u0018\u00010'2\u0007\u0010°\u0001\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010*J\"\u0010Î\u0001\u001a\n\u0012\u0004\u0012\u00020<\u0018\u00010'2\u0006\u0010!\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010*JK\u0010Ï\u0001\u001a\u0005\u0018\u00010Ð\u00012\u0007\u0010Ñ\u0001\u001a\u00020\"2\n\b\u0002\u0010.\u001a\u0004\u0018\u00010\"2\n\b\u0002\u0010/\u001a\u0004\u0018\u00010\"2\u0007\u0010\u00ad\u0001\u001a\u00020\"2\t\b\u0002\u0010Ò\u0001\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0003\u0010Ó\u0001J\r\u0010Ô\u0001\u001a\b\u0012\u0004\u0012\u00020\"0\bJ5\u0010Õ\u0001\u001a\f\u0012\u0006\u0012\u0004\u0018\u000108\u0018\u00010'2\u0006\u00109\u001a\u00020\"2\u0006\u0010!\u001a\u00020\"2\u0007\u0010\u008c\u0001\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010wJ:\u0010Ö\u0001\u001a\f\u0012\u0006\u0012\u0004\u0018\u000108\u0018\u00010'2\u0006\u00109\u001a\u00020\"2\u0006\u0010!\u001a\u00020\"2\u000b\b\u0002\u0010\u009b\u0001\u001a\u0004\u0018\u00010\fH\u0086@ø\u0001\u0000¢\u0006\u0003\u0010×\u0001J\u001e\u0010Ø\u0001\u001a\u0005\u0018\u00010Ù\u00012\u0007\u0010Ú\u0001\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010*J&\u0010Û\u0001\u001a\u000b\u0012\u0007\u0012\u0005\u0018\u00010Ü\u00010\b2\u0014\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\u00010EJ\u0015\u0010Ý\u0001\u001a\u0005\u0018\u00010Ù\u0001H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010MJ&\u0010Þ\u0001\u001a\u000b\u0012\u0007\u0012\u0005\u0018\u00010ß\u00010\b2\u0014\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\u00010EJ\u001d\u0010à\u0001\u001a\u0005\u0018\u00010á\u00012\u0006\u00109\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010*J&\u0010â\u0001\u001a\u000b\u0012\u0007\u0012\u0005\u0018\u00010ã\u00010\b2\u0014\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\u00010EJ+\u0010ä\u0001\u001a\u0005\u0018\u00010ã\u00012\u0014\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\u00010EH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010JJ#\u0010å\u0001\u001a\u000b\u0012\u0007\u0012\u0005\u0018\u00010\u009e\u00010\b2\u0006\u00106\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010*J\u0018\u0010æ\u0001\u001a\u000b\u0012\u0007\u0012\u0005\u0018\u00010\u009e\u00010\b2\u0006\u00109\u001a\u00020\"J\u0018\u0010ç\u0001\u001a\u000b\u0012\u0007\u0012\u0005\u0018\u00010À\u00010\b2\u0006\u00109\u001a\u00020\"J,\u0010è\u0001\u001a\f\u0012\u0006\u0012\u0004\u0018\u000108\u0018\u00010'2\u0006\u00109\u001a\u00020\"2\u0006\u0010!\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010$J \u0010é\u0001\u001a\b\u0012\u0004\u0012\u00020\f0'2\u0006\u00106\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010*J\r\u0010ê\u0001\u001a\b\u0012\u0004\u0012\u00020\"0\bJ%\u0010ë\u0001\u001a\u000b\u0012\u0005\u0012\u00030ì\u0001\u0018\u00010'2\b\b\u0002\u0010>\u001a\u00020ZH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010[JA\u0010í\u0001\u001a\u0005\u0018\u00010î\u00012\u0006\u0010e\u001a\u00020\"2\t\b\u0002\u0010ï\u0001\u001a\u00020\f2\n\b\u0002\u0010.\u001a\u0004\u0018\u00010\"2\n\b\u0002\u0010/\u001a\u0004\u0018\u00010\"H\u0086@ø\u0001\u0000¢\u0006\u0003\u0010ð\u0001J\u001c\u0010ñ\u0001\u001a\u0004\u0018\u00010 2\u0006\u0010!\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010*J \u0010ò\u0001\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\b2\u0007\u0010³\u0001\u001a\u00020\f2\u0006\u0010-\u001a\u00020\fJ\u000f\u0010ó\u0001\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\bJ \u0010ô\u0001\u001a\u0013\u0012\u000f\u0012\r\u0012\u0007\u0012\u0005\u0018\u00010õ\u0001\u0018\u00010'0\b2\u0006\u00106\u001a\u00020\"J\u001c\u0010ö\u0001\u001a\u000f\u0012\u000b\u0012\t\u0012\u0005\u0012\u00030\u009a\u00010'0\b2\u0006\u00106\u001a\u00020\"J\u001e\u0010÷\u0001\u001a\u0011\u0012\r\u0012\u000b\u0012\u0007\u0012\u0005\u0018\u00010ø\u00010'0\b2\u0006\u00106\u001a\u00020\"J\u0017\u0010ù\u0001\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010y0\b2\u0006\u0010Y\u001a\u00020\"J'\u0010ú\u0001\u001a\u000b\u0012\u0007\u0012\u0005\u0018\u00010ã\u00010\b2\u0015\u0010\u0098\u0001\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\u00010EJ,\u0010û\u0001\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020y\u0018\u00010'0\b2\u0015\u0010\u0098\u0001\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\u00010EJ\u0018\u0010ü\u0001\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\"0\b2\u0007\u0010\n\u001a\u00030ý\u0001J8\u0010þ\u0001\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f0\u000b2\u0015\u0010ÿ\u0001\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f\u0018\u00010EJ\u0019\u0010\u0080\u0002\u001a\u000b\u0012\u0007\u0012\u0005\u0018\u00010\u0081\u00020\b2\u0007\u0010\n\u001a\u00030\u0081\u0002J\u0018\u0010\u0082\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\b2\u0007\u0010\u0083\u0002\u001a\u00020\fJ\u0019\u0010\u0084\u0002\u001a\u000b\u0012\u0007\u0012\u0005\u0018\u00010¯\u00010\b2\u0007\u0010\n\u001a\u00030\u0085\u0002J\u0018\u0010\u0086\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010C0\b2\u0007\u0010\u000f\u001a\u00030\u0087\u0002J \u0010\u0088\u0002\u001a\u0012\u0012\u000e\u0012\f\u0012\u0006\u0012\u0004\u0018\u000108\u0018\u00010'0\b2\u0007\u0010\u000f\u001a\u00030\u0087\u0002J\u0017\u0010\u0089\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\b2\u0006\u0010!\u001a\u00020\"J\u0019\u0010\u008a\u0002\u001a\u000b\u0012\u0007\u0012\u0005\u0018\u00010\u008b\u00020\b2\u0007\u0010\n\u001a\u00030\u008c\u0002J\u0017\u0010\u008d\u0002\u001a\t\u0012\u0005\u0012\u00030¯\u00010\b2\u0007\u0010\n\u001a\u00030\u008e\u0002J-\u0010\u008f\u0002\u001a\u000b\u0012\u0005\u0012\u00030\u0090\u0002\u0018\u00010'2\u000f\u0010\u0091\u0002\u001a\n\u0012\u0005\u0012\u00030\u0093\u00020\u0092\u0002H\u0086@ø\u0001\u0000¢\u0006\u0003\u0010\u0094\u0002J\u0019\u0010\u0095\u0002\u001a\u000b\u0012\u0007\u0012\u0005\u0018\u00010\u0096\u00020\b2\u0007\u0010\n\u001a\u00030\u0097\u0002J\u0019\u0010\u0098\u0002\u001a\u000b\u0012\u0007\u0012\u0005\u0018\u00010\u0096\u00020\b2\u0007\u0010\n\u001a\u00030\u0097\u0002J\u0017\u0010\u0099\u0002\u001a\t\u0012\u0005\u0012\u00030¯\u00010\b2\u0007\u0010\u000f\u001a\u00030\u009a\u0002J\"\u0010\u009b\u0002\u001a\t\u0012\u0005\u0012\u00030\u009c\u00020\b2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f0\u000bJ\u000f\u0010\u009d\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010 0\bJ)\u0010\u009e\u0002\u001a\r\u0012\u0007\u0012\u0005\u0018\u00010\u009f\u0002\u0018\u00010'2\t\b\u0001\u0010\u000f\u001a\u00030 \u0002H\u0086@ø\u0001\u0000¢\u0006\u0003\u0010¡\u0002J\u0016\u0010¢\u0002\u001a\t\u0012\u0005\u0012\u00030¯\u00010\b2\u0006\u0010o\u001a\u00020\fJ!\u0010£\u0002\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f0\u000bJ)\u0010¤\u0002\u001a\u0005\u0018\u00010¥\u00022\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\"0EH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010JJ$\u0010¦\u0002\u001a\u000b\u0012\u0007\u0012\u0005\u0018\u00010¥\u00020\b2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\"0EJ%\u0010§\u0002\u001a\u000b\u0012\u0007\u0012\u0005\u0018\u00010¯\u00010\b2\u0007\u0010\n\u001a\u00030¨\u0002H\u0086@ø\u0001\u0000¢\u0006\u0003\u0010©\u0002J%\u0010ª\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\b2\u0014\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000bJ\u0018\u0010«\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\b2\u0007\u0010\u000f\u001a\u00030¬\u0002J$\u0010\u00ad\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\b2\u0007\u0010\u000f\u001a\u00030®\u0002H\u0086@ø\u0001\u0000¢\u0006\u0003\u0010¯\u0002J#\u0010°\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\b2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f0EJ\u0017\u0010±\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\b2\u0006\u0010-\u001a\u00020\fJ\u001e\u0010²\u0002\u001a\u0004\u0018\u00010\f2\u0007\u0010\u000f\u001a\u00030³\u0002H\u0086@ø\u0001\u0000¢\u0006\u0003\u0010´\u0002J\u001a\u0010µ\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\"0\b2\t\b\u0001\u0010\u000f\u001a\u00030¶\u0002J$\u0010·\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\"0\b2\u0007\u0010\n\u001a\u00030¨\u0002H\u0086@ø\u0001\u0000¢\u0006\u0003\u0010©\u0002J\u0018\u0010¸\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\"0\b2\u0007\u0010\u000f\u001a\u00030¨\u0002J\u0018\u0010¹\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\b2\u0007\u0010\u000f\u001a\u00030º\u0002J\u0018\u0010»\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\b2\u0007\u0010\u000f\u001a\u00030¼\u0002J\u001e\u0010½\u0002\u001a\u0004\u0018\u00010\t2\u0007\u0010\u000f\u001a\u00030¾\u0002H\u0086@ø\u0001\u0000¢\u0006\u0003\u0010¿\u0002J\u0018\u0010À\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\b2\u0007\u0010\n\u001a\u00030Á\u0002J\u001d\u0010Â\u0002\u001a\u0005\u0018\u00010Ã\u00022\u0006\u00109\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010*J\u001d\u0010Ä\u0002\u001a\u0005\u0018\u00010Å\u00022\u0006\u00109\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010*J\u001d\u0010Æ\u0002\u001a\u0005\u0018\u00010Å\u00022\u0006\u00109\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010*J\u0018\u0010Ç\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\b2\u0007\u0010\u000f\u001a\u00030È\u0002J\u001e\u0010É\u0002\u001a\u0004\u0018\u00010\f2\u0007\u0010\u000f\u001a\u00030È\u0002H\u0086@ø\u0001\u0000¢\u0006\u0003\u0010Ê\u0002J\u0017\u0010Ë\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\"0\b2\u0006\u0010\u000f\u001a\u00020\u0010J\u001d\u0010Ì\u0002\u001a\u0004\u0018\u00010\"2\u0006\u0010\u000f\u001a\u00020\u0010H\u0086@ø\u0001\u0000¢\u0006\u0003\u0010Í\u0002J'\u0010Î\u0002\u001a\f\u0012\u0006\u0012\u0004\u0018\u000108\u0018\u00010'2\t\u0010\u009b\u0001\u001a\u0004\u0018\u00010\fH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010cJ\u0017\u0010Ï\u0002\u001a\u0012\u0012\u000e\u0012\f\u0012\u0006\u0012\u0004\u0018\u000108\u0018\u00010'0\bJ@\u0010Ð\u0002\u001a\u0005\u0018\u00010Ñ\u00022\u0007\u0010\u009b\u0001\u001a\u00020\f2\u0007\u0010Ò\u0002\u001a\u00020\f2\n\b\u0002\u0010.\u001a\u0004\u0018\u00010\"2\n\b\u0002\u0010/\u001a\u0004\u0018\u00010\"H\u0086@ø\u0001\u0000¢\u0006\u0003\u0010Ó\u0002J'\u0010Ô\u0002\u001a\f\u0012\u0006\u0012\u0004\u0018\u000108\u0018\u00010'2\t\u0010\u009b\u0001\u001a\u0004\u0018\u00010\fH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010cJ\u0017\u0010Õ\u0002\u001a\u0012\u0012\u000e\u0012\f\u0012\u0006\u0012\u0004\u0018\u000108\u0018\u00010'0\bJ\u0018\u0010Ö\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\"0\b2\u0007\u0010\n\u001a\u00030×\u0002J\u0019\u0010Ø\u0002\u001a\u000b\u0012\u0007\u0012\u0005\u0018\u00010À\u00010\b2\u0007\u0010Ù\u0002\u001a\u00020\"J\u001e\u0010Ú\u0002\u001a\u0005\u0018\u00010À\u00012\u0007\u0010Ù\u0002\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010*J\u0018\u0010Û\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\"0\b2\u0007\u0010\n\u001a\u00030Ü\u0002J\u0017\u0010Ý\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\"0\b2\u0006\u0010Y\u001a\u00020\"J\u001f\u0010Þ\u0002\u001a\u0012\u0012\u000e\u0012\f\u0012\u0006\u0012\u0004\u0018\u00010y\u0018\u00010'0\b2\u0006\u00109\u001a\u00020\"J\u0018\u0010ß\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\"0\b2\u0007\u0010\n\u001a\u00030à\u0002J\u000f\u0010á\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010y0\bJ'\u0010â\u0002\u001a\u000b\u0012\u0007\u0012\u0005\u0018\u00010ã\u00010\b2\u0015\u0010\u0098\u0001\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\u00010EJ\u0018\u0010ã\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010y0\b2\u0007\u0010\n\u001a\u00030ä\u0002J\u0019\u0010å\u0002\u001a\u000b\u0012\u0007\u0012\u0005\u0018\u00010¯\u00010\b2\u0007\u0010\n\u001a\u00030æ\u0002J'\u0010ç\u0002\u001a\u000b\u0012\u0007\u0012\u0005\u0018\u00010À\u00010\b2\u0015\u0010\u0098\u0001\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\u00010EJ,\u0010è\u0002\u001a\u0005\u0018\u00010À\u00012\u0015\u0010\u0098\u0001\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\u00010EH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010JJ%\u0010é\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\"0\b2\u0014\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\"0EJ#\u0010ê\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\b2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f0\u000bJ\u0019\u0010ë\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\b2\b\u0010ì\u0002\u001a\u00030\u0093\u0002J%\u0010í\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\b2\u0014\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006î\u0002"}, d2 = {"Lcom/yddmi/doctor/repository/YddClinicRepository;", "", "()V", "otherService", "Lcom/yddmi/doctor/network/api/OtherService;", "yddService", "Lcom/yddmi/doctor/network/api/YddService;", "authLogin", "Lkotlinx/coroutines/flow/Flow;", "Lcom/yddmi/doctor/entity/result/AuthLoginResult;", TtmlNode.TAG_BODY, "", "", "codeLogin", "commitExamInfo", HiAnalyticsConstant.Direction.REQUEST, "Lcom/yddmi/doctor/entity/request/ExamUpdateReq;", "dealChangeBaseUrl", "", c.f3231f, "Lcom/yddmi/doctor/config/YddHostConfig$YddHost;", "dealOtherService", "examDetailInfo", "Lcom/yddmi/doctor/entity/result/ExamResultDetail;", "Lcom/yddmi/doctor/entity/request/ExamDetailInfoReq;", "examResult", "Lcom/yddmi/doctor/entity/result/ExamResultVo;", "Lcom/yddmi/doctor/entity/request/ExamResultReq;", "forgetPwd", "getAllNoticeRead", "getAllRead", "getAppGeneralConfig", "Lcom/yddmi/doctor/entity/result/HomeMsg;", "type", "", MimeTypes.BASE_TYPE_APPLICATION, "(IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAppGeneralConfigFlow", "getAppHomeList", "", "Lcom/yddmi/doctor/entity/result/HomeItem;", "userId", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAppKnowledgeStatistics", "Lcom/yddmi/doctor/entity/result/HeartData;", "code", "pageNum", ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE, "getAppTableList", "Lcom/yddmi/doctor/entity/result/HomeDaskResult;", "getAppVersion", "Lcom/yddmi/doctor/entity/result/AppVersion;", "getAppWarn", "getAskTip", "patientId", "getAssistCheckCategoryTree", "Lcom/yddmi/doctor/entity/result/InquiryCheckInfo;", "trainId", "getAssistTip", "getBodyCheck", "Lcom/yddmi/doctor/entity/result/SkillHome;", "dictConfigId", "skillId", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBodyCheckTip", "getChatGptUse", "getClinicalGuideline", "Lcom/yddmi/doctor/entity/result/HomeGuideList;", "query", "", "getClinicalGuidelineDetail", "Lcom/yddmi/doctor/entity/result/HomeClinicalDetail;", "categoryId", "getClinicalGuidelineN0Flow", "(Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getConfigWhite", "Lcom/yddmi/doctor/entity/result/AppConfig;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getConfigWhiteF", "fUrl", "getContact", "Lcom/yddmi/doctor/entity/result/SkillCall;", "getDepartment", "getDepartment1", "getDiagnoseClinicalGuide", "Lcom/yddmi/doctor/entity/result/MeAgencyRow;", "getDiagnoseTip", "getDictConfigLabel", "Lcom/yddmi/doctor/entity/result/SkillLabel;", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getEcommendedVideo", "Lcom/yddmi/doctor/entity/result/TeachRow;", "getEcommendedVideoList", "Lcom/yddmi/doctor/entity/result/HomeTeaching;", "getExamByCode", "Lcom/yddmi/doctor/entity/result/ExamItem;", "keyWord", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getExamByUser", "candidateId", "examId", "(ILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getExamHorseLamp", "getExamQuestionList", "Lcom/yddmi/doctor/entity/result/ExamQuestion;", "paperId", "(ILjava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFeedBackPage", "Lcom/yddmi/doctor/entity/result/FeedBackPageList;", "medicalKnowledgeId", "getGardeScore", "Lcom/yddmi/doctor/entity/result/ScoreDetail;", "getHomeBanner", "Lcom/yddmi/doctor/entity/result/BannerData;", "getHomeInfoList", "Lcom/yddmi/doctor/entity/result/HomeConfig;", "bannerType", "(IIILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getHomePatient", "Lcom/yddmi/doctor/entity/result/RowCase;", "getHorseLamp", "getInformationTree", "getIntegralApp", "Lcom/yddmi/doctor/entity/result/MeProfileIntegral;", "getInviterInfo", "getKnowledgeAtlasID", "Lcom/yddmi/doctor/entity/result/HomeAtlasDetails;", "getKnowledgeDetails", "Lcom/yddmi/doctor/entity/result/HeartDetail;", "isFavorite", "isMastered", "(Ljava/lang/String;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLeaderBoard", "Lcom/yddmi/doctor/entity/result/ExamLeaderBoardResult;", "getMaintain", "map", "getMedicineDirectionTree", "getMedicineSearchList", "pid", "getMessageList", "Lcom/yddmi/doctor/entity/result/HomeMsgList;", "(ILjava/lang/Integer;Ljava/lang/Integer;)Lkotlinx/coroutines/flow/Flow;", "getNoticeList", "releaseStatus", "(Ljava/lang/Integer;Ljava/lang/Integer;I)Lkotlinx/coroutines/flow/Flow;", "getOrderStr", "productInfoId", "all", "couponRecordId", "getOrganzationSearch", "queryMap", "getPatientAskGetByKeyword", "Lcom/yddmi/doctor/entity/result/InquiryAskInfo;", Constants.PARAMS_CONSTANTS.PARAMS_KEYWORD, "getPatientInfoById", "getPatientNote", "Lcom/yddmi/doctor/entity/result/InquiryNote;", "getPatientTreeRecord", "getPatientTreeRecordN0Flow", "getPersonInfoApp", "Lcom/yddmi/doctor/entity/result/MeProfile;", "getPopUserList", "getPostUserBankInfo", "Lcom/yddmi/doctor/entity/request/BankReqResult;", "getPracticeRecord", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPracticeRecordExam", "getPrepayWx", "Lcom/yddmi/doctor/entity/result/SkillPayWx;", "getProductInfo", "getProductInfoSkill", "skillType", "useCoupon", "", "skill24", "(Ljava/lang/String;ILjava/lang/String;ZILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPushCodeForgetPwd", AliyunLogCommon.TERMINAL_TYPE, "getPushCodeRegister", "getRandomSkill", "getReadNoticeId", RemoteMessageConst.MSGID, "getRecommendInfo", "Lcom/yddmi/doctor/entity/result/TeachCasebook;", "getRecordEquityDetail", "Lcom/yddmi/doctor/entity/result/SkillIntegral;", "getReplyAppList", SocialConstants.PARAM_SOURCE, "getReportGradeItemScore", "getReportHarmItem", "Lcom/yddmi/doctor/entity/result/InquiryAskList;", "getReportHelpItem", "getReportIdentityDiagnose", "getReportIrrelevantItem", "getReportMainDiagnose", "getReportOtherDiagnose", "getRulelatest", "getSicknessRelationList", "Lcom/yddmi/doctor/entity/result/HomeAtlas;", "getSkillBasic", "getSkillBasicHome", "getSkillBodyHome", "getSkillHome", "getSkillMy", "getSkillRandom", "getSkillRecord", "Lcom/yddmi/doctor/entity/result/SkillHomeList;", "isExam", "system", "(ILjava/lang/Integer;Ljava/lang/Integer;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getSkillTimes", "getSysTrainRecordGetKeyWord", "getSysTrainRecordGetKeyWordSearch", "(IILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTeachingCenter", "Lcom/yddmi/doctor/entity/result/HomeStudyResult;", "orgId", "getTeachingList", "Lcom/yddmi/doctor/entity/result/HomeTeachingCase;", "getTeachingResourceCenter", "getTeachingResourceList", "Lcom/yddmi/doctor/entity/result/HomeTeachingClass;", "getTrainInfoFindById", "Lcom/yddmi/doctor/entity/result/ScoreAnalyseTrainInfo;", "getTrainInfoStudyList", "Lcom/yddmi/doctor/entity/result/TrainInfoList;", "getTrainInfoStudyList1", "getTrainMedical", "getTrainNoteById", "getTrainRecordListAsk", "getTreatmentCategoryTree", "getTreatmentTip", "getUnreadCount", "getUserCoupon", "Lcom/yddmi/doctor/entity/result/SkillTicket;", "getUserExamList", "Lcom/yddmi/doctor/entity/result/ExamList;", "status", "(ILjava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUserSpecificCoupon", "getVeriFyMsg", "getVersionU3d", "patientAskGetAskTree", "Lcom/yddmi/doctor/entity/result/InquiryAsk;", "patientAskGetAskTree1", "patientAssistGetBindData", "Lcom/yddmi/doctor/entity/result/InquiryBindData;", "patientInfoGetById", "patientInfoList", "patientInfoRandom", "patientInfoSave", "Lcom/yddmi/doctor/entity/request/InquiryPatientInfoSave;", "postAgentLogin", "headMap", "postAppFavoriteSave", "Lcom/yddmi/doctor/entity/request/FavoriteSaveReq;", "postAppRead", "str", "postAppTableSave", "Lcom/yddmi/doctor/entity/request/HomeDaskSaveReq;", "postClinicalGuideline", "Lcom/yddmi/doctor/entity/request/HomeGuideReq;", "postClinicalGuideline1", "postCouponRecord", "postExamCheat", "Lcom/yddmi/doctor/entity/result/ExamCheat;", "Lcom/yddmi/doctor/entity/request/ExamCheatReq;", "postFeedBackAdd", "Lcom/yddmi/doctor/entity/request/FeedBackReq;", "postFileUploadBatch", "Lcom/yddmi/doctor/entity/result/MeFiles;", "files", "", "Lokhttp3/MultipartBody$Part;", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "postIntegralAcquire", "Lcom/yddmi/doctor/entity/result/SkillShare;", "Lcom/yddmi/doctor/entity/request/IntegralAcquireReq;", "postIntegralFreeReceive", "postIntegralUnlock", "Lcom/yddmi/doctor/entity/request/IntegralUnlockReq;", "postInviteLogin", "Lcom/yddmi/doctor/entity/result/AuthLoginResult1;", "postInviterSave", "postKnowledgeAtlasQuary", "Lcom/yddmi/doctor/entity/result/AtlasRow;", "Lcom/yddmi/doctor/entity/request/HomeAtlasReq;", "(Lcom/yddmi/doctor/entity/request/HomeAtlasReq;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "postKnowledgeLearnRecordSave", "postOneclickLogin", "postPatientAssistInfo", "Lcom/yddmi/doctor/entity/result/ScoreBlood;", "postPatientAssistInfoGo", "postPatientNoteSave", "Lcom/yddmi/doctor/entity/request/InquirySaveNoteReq;", "(Lcom/yddmi/doctor/entity/request/InquirySaveNoteReq;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "postPersonInfo", "postPersonSubmit", "Lcom/yddmi/doctor/entity/request/HomeMeAuditSubmit;", "postPointSave", "Lcom/yddmi/doctor/entity/request/PointSaveReq;", "(Lcom/yddmi/doctor/entity/request/PointSaveReq;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "postPopUserUpdateStatus", "postRedeemCode", "postReplySave", "Lcom/yddmi/doctor/entity/request/MeReplyReq;", "(Lcom/yddmi/doctor/entity/request/MeReplyReq;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "postTenantInfoRegister", "Lcom/yddmi/doctor/entity/request/HomeTenantReq;", "postTrainMedicalSave", "postTrainNoteSave", "postUserBinding", "Lcom/yddmi/doctor/entity/request/LoginWxBindingReq;", "postUserReBinding", "Lcom/yddmi/doctor/entity/request/LoginWxReBindingReq;", "postWxLogin", "Lcom/yddmi/doctor/entity/request/LoginWx;", "(Lcom/yddmi/doctor/entity/request/LoginWx;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "readMessageList", "Lcom/yddmi/doctor/entity/request/HomeMsgReq;", "reportAnalyseRadar", "Lcom/yddmi/doctor/entity/result/ScoreAnalyseRadar;", "reportRatioCorrect", "Lcom/yddmi/doctor/entity/result/ScoreAnalyseCorrect;", "reportRatioMatch", "saveExamQuestionResult", "Lcom/yddmi/doctor/entity/request/ExamSaveReq;", "saveExamQuestionResultN0Flow", "(Lcom/yddmi/doctor/entity/request/ExamSaveReq;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "startExam", "startExam1", "(Lcom/yddmi/doctor/entity/request/ExamUpdateReq;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sysAssistCheck", "sysAssistCheckGetTree", "sysDiseaseList", "Lcom/yddmi/doctor/entity/result/InquiryDiseaseList;", "name", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sysTreatment", "sysTreatmentGetTree", "trainDagnoseSave", "Lcom/yddmi/doctor/entity/request/InquiryDiseaseReq;", "trainDiagnoseBasisGetSavedBasis", "trainDiagnoseId", "trainDiagnoseBasisGetSavedBasisNoFlow", "trainDiagnoseBasisSaveBasis", "Lcom/yddmi/doctor/entity/request/InquirySaveBasisReq;", "trainDiagnoseDelete", "trainDiagnoseGetByTrainId", "trainDiagnoseUpdate", "Lcom/yddmi/doctor/entity/request/InquiryDiseaseUpdateReq;", "trainInfoGetUnCompletee", "trainInfoList", "trainInfoUpdateStatus", "Lcom/yddmi/doctor/entity/request/InquiryUpdateStatus;", "trainRecordBatchSave", "Lcom/yddmi/doctor/entity/request/InquiryCheckReq;", "trainRecordListDoneDetails", "trainRecordListDoneDetailsNoFlow", "trainRecordSave", "updatePwd", "uploadImagesHeader", "iconFile", "userAccountCancel", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class YddClinicRepository {

    @NotNull
    public static final YddClinicRepository INSTANCE = new YddClinicRepository();

    @NotNull
    private static OtherService otherService = (OtherService) NetManager.INSTANCE.getInstance().getServiceBase(OtherService.class, YddHostConfig.INSTANCE.getInstance().servicePrivateApiGet());
    private static YddService yddService;

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[YddHostConfig.YddHost.values().length];
            try {
                iArr[YddHostConfig.YddHost.DEV.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[YddHostConfig.YddHost.TEST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[YddHostConfig.YddHost.TEST192.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[YddHostConfig.YddHost.TEST126.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[YddHostConfig.YddHost.UAT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[YddHostConfig.YddHost.BASE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/AuthLoginResult;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$authLogin$1", f = "YddClinicRepository.kt", i = {}, l = {71, 71}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$authLogin$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<FlowCollector<? super AuthLoginResult>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Map<String, String> $body;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Map<String, String> map, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$body = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$body, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super AuthLoginResult> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                Map<String, String> map = this.$body;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.authLogin(map, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/AuthLoginResult;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$codeLogin$1", f = "YddClinicRepository.kt", i = {}, l = {77, 77}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$codeLogin$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09121 extends SuspendLambda implements Function2<FlowCollector<? super AuthLoginResult>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Map<String, String> $body;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09121(Map<String, String> map, Continuation<? super C09121> continuation) {
            super(2, continuation);
            this.$body = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09121 c09121 = new C09121(this.$body, continuation);
            c09121.L$0 = obj;
            return c09121;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super AuthLoginResult> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09121) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                Map<String, String> map = this.$body;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.codeLogin(map, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$commitExamInfo$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.bvp_indicator_radius, R2.attr.bvp_indicator_radius}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$commitExamInfo$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09131 extends SuspendLambda implements Function2<FlowCollector<? super String>, Continuation<? super Unit>, Object> {
        final /* synthetic */ ExamUpdateReq $req;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09131(ExamUpdateReq examUpdateReq, Continuation<? super C09131> continuation) {
            super(2, continuation);
            this.$req = examUpdateReq;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09131 c09131 = new C09131(this.$req, continuation);
            c09131.L$0 = obj;
            return c09131;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09131) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                ExamUpdateReq examUpdateReq = this.$req;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.commitExamInfo(examUpdateReq, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/ExamResultDetail;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$examDetailInfo$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.cardViewStyle, R2.attr.cardViewStyle}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$examDetailInfo$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09141 extends SuspendLambda implements Function2<FlowCollector<? super ExamResultDetail>, Continuation<? super Unit>, Object> {
        final /* synthetic */ ExamDetailInfoReq $body;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09141(ExamDetailInfoReq examDetailInfoReq, Continuation<? super C09141> continuation) {
            super(2, continuation);
            this.$body = examDetailInfoReq;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09141 c09141 = new C09141(this.$body, continuation);
            c09141.L$0 = obj;
            return c09141;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super ExamResultDetail> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09141) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                ExamDetailInfoReq examDetailInfoReq = this.$body;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.examDetailInfo(examDetailInfoReq, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/ExamResultVo;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$examResult$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.cardCornerRadius, R2.attr.cardCornerRadius}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$examResult$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09151 extends SuspendLambda implements Function2<FlowCollector<? super ExamResultVo>, Continuation<? super Unit>, Object> {
        final /* synthetic */ ExamResultReq $body;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09151(ExamResultReq examResultReq, Continuation<? super C09151> continuation) {
            super(2, continuation);
            this.$body = examResultReq;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09151 c09151 = new C09151(this.$body, continuation);
            c09151.L$0 = obj;
            return c09151;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super ExamResultVo> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09151) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                ExamResultReq examResultReq = this.$body;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.examResult(examResultReq, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$forgetPwd$1", f = "YddClinicRepository.kt", i = {}, l = {115, 115}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$forgetPwd$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09161 extends SuspendLambda implements Function2<FlowCollector<? super String>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Map<String, String> $body;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09161(Map<String, String> map, Continuation<? super C09161> continuation) {
            super(2, continuation);
            this.$body = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09161 c09161 = new C09161(this.$body, continuation);
            c09161.L$0 = obj;
            return c09161;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09161) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                Map<String, String> map = this.$body;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.forgetPwd(map, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getAllNoticeRead$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.bnb_anim, R2.attr.bnb_anim}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getAllNoticeRead$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09171 extends SuspendLambda implements Function2<FlowCollector<? super String>, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        public C09171(Continuation<? super C09171> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09171 c09171 = new C09171(continuation);
            c09171.L$0 = obj;
            return c09171;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09171) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.getAllNoticeRead(this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getAllRead$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.blur_corner_radius_top_left, R2.attr.blur_corner_radius_top_left}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getAllRead$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09181 extends SuspendLambda implements Function2<FlowCollector<? super String>, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        public C09181(Continuation<? super C09181> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09181 c09181 = new C09181(continuation);
            c09181.L$0 = obj;
            return c09181;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09181) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.getAllRead(this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/HomeMsg;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getAppGeneralConfigFlow$1", f = "YddClinicRepository.kt", i = {}, l = {1051, 1051}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getAppGeneralConfigFlow$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09191 extends SuspendLambda implements Function2<FlowCollector<? super HomeMsg>, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $application;
        final /* synthetic */ int $type;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09191(int i2, int i3, Continuation<? super C09191> continuation) {
            super(2, continuation);
            this.$type = i2;
            this.$application = i3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09191 c09191 = new C09191(this.$type, this.$application, continuation);
            c09191.L$0 = obj;
            return c09191;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super HomeMsg> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09191) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                YddService yddService = YddClinicRepository.yddService;
                if (yddService == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                }
                int i3 = this.$type;
                int i4 = this.$application;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.getAppGeneralConfig(i3, i4, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/HeartData;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getAppKnowledgeStatistics$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.corner_radius_top_right, R2.attr.corner_radius_top_right}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getAppKnowledgeStatistics$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09201 extends SuspendLambda implements Function2<FlowCollector<? super HeartData>, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $code;
        final /* synthetic */ int $pageNum;
        final /* synthetic */ int $pageSize;
        final /* synthetic */ int $type;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09201(int i2, int i3, int i4, int i5, Continuation<? super C09201> continuation) {
            super(2, continuation);
            this.$type = i2;
            this.$code = i3;
            this.$pageNum = i4;
            this.$pageSize = i5;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09201 c09201 = new C09201(this.$type, this.$code, this.$pageNum, this.$pageSize, continuation);
            c09201.L$0 = obj;
            return c09201;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super HeartData> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09201) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            YddService yddService;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                YddService yddService2 = YddClinicRepository.yddService;
                if (yddService2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                } else {
                    yddService = yddService2;
                }
                int i3 = this.$type;
                int i4 = this.$code;
                int i5 = this.$pageNum;
                int i6 = this.$pageSize;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.getAppKnowledgeStatistics(i3, i4, i5, i6, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/AppVersion;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getAppVersion$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.border_width_selected, R2.attr.border_width_selected}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getAppVersion$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09211 extends SuspendLambda implements Function2<FlowCollector<? super AppVersion>, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        public C09211(Continuation<? super C09211> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09211 c09211 = new C09211(continuation);
            c09211.L$0 = obj;
            return c09211;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super AppVersion> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09211) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                YddService yddService = YddClinicRepository.yddService;
                if (yddService == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                }
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.getAppVersion(9, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003*\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "Lcom/yddmi/doctor/entity/result/HomeMsg;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getAppWarn$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.bondline2, R2.attr.bondline2}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getAppWarn$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09221 extends SuspendLambda implements Function2<FlowCollector<? super List<HomeMsg>>, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        public C09221(Continuation<? super C09221> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09221 c09221 = new C09221(continuation);
            c09221.L$0 = obj;
            return c09221;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super List<HomeMsg>> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09221) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                YddService yddService = YddClinicRepository.yddService;
                if (yddService == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                }
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.getAppWarn(this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getChatGptUse$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.bl_unChecked_stroke_color, R2.attr.bl_unChecked_stroke_color}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getChatGptUse$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09231 extends SuspendLambda implements Function2<FlowCollector<? super String>, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        public C09231(Continuation<? super C09231> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09231 c09231 = new C09231(continuation);
            c09231.L$0 = obj;
            return c09231;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09231) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.getChatGptUse(this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/HomeGuideList;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getClinicalGuideline$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.bl_function, R2.attr.bl_function}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getClinicalGuideline$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09241 extends SuspendLambda implements Function2<FlowCollector<? super HomeGuideList>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Map<String, Object> $query;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09241(Map<String, Object> map, Continuation<? super C09241> continuation) {
            super(2, continuation);
            this.$query = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09241 c09241 = new C09241(this.$query, continuation);
            c09241.L$0 = obj;
            return c09241;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super HomeGuideList> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09241) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                YddService yddService = YddClinicRepository.yddService;
                if (yddService == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                }
                Map<String, Object> map = this.$query;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.getClinicalGuideline(map, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/HomeClinicalDetail;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getClinicalGuidelineDetail$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.bl_pressed_gradient_endColor, R2.attr.bl_pressed_gradient_endColor}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getClinicalGuidelineDetail$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09251 extends SuspendLambda implements Function2<FlowCollector<? super HomeClinicalDetail>, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $categoryId;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09251(int i2, Continuation<? super C09251> continuation) {
            super(2, continuation);
            this.$categoryId = i2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09251 c09251 = new C09251(this.$categoryId, continuation);
            c09251.L$0 = obj;
            return c09251;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super HomeClinicalDetail> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09251) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                int i3 = this.$categoryId;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.getClinicalGuidelineDetail(i3, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003*\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "Lcom/yddmi/doctor/entity/result/AppConfig;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getConfigWhiteF$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.boxBackgroundMode, R2.attr.boxBackgroundMode}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getConfigWhiteF$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09261 extends SuspendLambda implements Function2<FlowCollector<? super List<AppConfig>>, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $fUrl;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09261(String str, Continuation<? super C09261> continuation) {
            super(2, continuation);
            this.$fUrl = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09261 c09261 = new C09261(this.$fUrl, continuation);
            c09261.L$0 = obj;
            return c09261;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super List<AppConfig>> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09261) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                String str = this.$fUrl;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.getConfigWhiteF(str, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003*\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "Lcom/yddmi/doctor/entity/result/InquiryCheckInfo;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getDepartment$2", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.bgColor, R2.attr.bgColor}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getDepartment$2, reason: invalid class name */
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<FlowCollector<? super List<InquiryCheckInfo>>, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        public AnonymousClass2(Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super List<InquiryCheckInfo>> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.getDepartment(this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003*\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "Lcom/yddmi/doctor/entity/result/MeAgencyRow;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getDiagnoseClinicalGuide$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.barrierDirection, R2.attr.barrierDirection}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getDiagnoseClinicalGuide$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09271 extends SuspendLambda implements Function2<FlowCollector<? super List<MeAgencyRow>>, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $patientId;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09271(int i2, Continuation<? super C09271> continuation) {
            super(2, continuation);
            this.$patientId = i2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09271 c09271 = new C09271(this.$patientId, continuation);
            c09271.L$0 = obj;
            return c09271;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super List<MeAgencyRow>> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09271) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                int i3 = this.$patientId;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.getDiagnoseClinicalGuide(i3, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/TeachRow;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getEcommendedVideo$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.biaozianzi, R2.attr.biaozianzi}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getEcommendedVideo$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09281 extends SuspendLambda implements Function2<FlowCollector<? super TeachRow>, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $patientId;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09281(int i2, Continuation<? super C09281> continuation) {
            super(2, continuation);
            this.$patientId = i2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09281 c09281 = new C09281(this.$patientId, continuation);
            c09281.L$0 = obj;
            return c09281;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super TeachRow> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09281) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                int i3 = this.$patientId;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.getEcommendedVideo(i3, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/HomeTeaching;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getEcommendedVideoList$1", f = "YddClinicRepository.kt", i = {}, l = {510, 510}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getEcommendedVideoList$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09291 extends SuspendLambda implements Function2<FlowCollector<? super HomeTeaching>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Map<String, Object> $body;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09291(Map<String, Object> map, Continuation<? super C09291> continuation) {
            super(2, continuation);
            this.$body = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09291 c09291 = new C09291(this.$body, continuation);
            c09291.L$0 = obj;
            return c09291;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super HomeTeaching> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09291) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                Map<String, Object> map = this.$body;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.getEcommendedVideoList(map, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003*\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getExamHorseLamp$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.computer_statistics_top_start_color, R2.attr.computer_statistics_top_start_color}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getExamHorseLamp$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09301 extends SuspendLambda implements Function2<FlowCollector<? super List<String>>, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        public C09301(Continuation<? super C09301> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09301 c09301 = new C09301(continuation);
            c09301.L$0 = obj;
            return c09301;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super List<String>> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09301) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                YddService yddService = YddClinicRepository.yddService;
                if (yddService == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                }
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.getExamHorseLamp(this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/FeedBackPageList;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getFeedBackPage$1", f = "YddClinicRepository.kt", i = {}, l = {1200, 1200}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getFeedBackPage$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09311 extends SuspendLambda implements Function2<FlowCollector<? super FeedBackPageList>, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $categoryId;
        final /* synthetic */ String $medicalKnowledgeId;
        final /* synthetic */ int $pageNum;
        final /* synthetic */ int $pageSize;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09311(String str, String str2, int i2, int i3, Continuation<? super C09311> continuation) {
            super(2, continuation);
            this.$categoryId = str;
            this.$medicalKnowledgeId = str2;
            this.$pageNum = i2;
            this.$pageSize = i3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09311 c09311 = new C09311(this.$categoryId, this.$medicalKnowledgeId, this.$pageNum, this.$pageSize, continuation);
            c09311.L$0 = obj;
            return c09311;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super FeedBackPageList> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09311) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            YddService yddService;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                YddService yddService2 = YddClinicRepository.yddService;
                if (yddService2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                } else {
                    yddService = yddService2;
                }
                String str = this.$categoryId;
                String str2 = this.$medicalKnowledgeId;
                Integer numBoxInt = Boxing.boxInt(this.$pageNum);
                Integer numBoxInt2 = Boxing.boxInt(this.$pageSize);
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.getFeedBackPage(str, str2, numBoxInt, numBoxInt2, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003*\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getHorseLamp$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.color_hot_circle_three_end, R2.attr.color_hot_circle_three_end}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getHorseLamp$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09321 extends SuspendLambda implements Function2<FlowCollector<? super List<String>>, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        public C09321(Continuation<? super C09321> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09321 c09321 = new C09321(continuation);
            c09321.L$0 = obj;
            return c09321;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super List<String>> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09321) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                YddService yddService = YddClinicRepository.yddService;
                if (yddService == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                }
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.getHorseLamp(this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/HomeAtlasDetails;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getKnowledgeAtlasID$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.bl_size_width, R2.attr.bl_size_width}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getKnowledgeAtlasID$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09331 extends SuspendLambda implements Function2<FlowCollector<? super HomeAtlasDetails>, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $id;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09331(int i2, Continuation<? super C09331> continuation) {
            super(2, continuation);
            this.$id = i2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09331 c09331 = new C09331(this.$id, continuation);
            c09331.L$0 = obj;
            return c09331;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super HomeAtlasDetails> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09331) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                YddService yddService = YddClinicRepository.yddService;
                if (yddService == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                }
                int i3 = this.$id;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.getKnowledgeAtlasID(i3, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/ExamLeaderBoardResult;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getLeaderBoard$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.constraint_referenced_tags, R2.attr.constraint_referenced_tags}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getLeaderBoard$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09341 extends SuspendLambda implements Function2<FlowCollector<? super ExamLeaderBoardResult>, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $skillId;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09341(String str, Continuation<? super C09341> continuation) {
            super(2, continuation);
            this.$skillId = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09341 c09341 = new C09341(this.$skillId, continuation);
            c09341.L$0 = obj;
            return c09341;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super ExamLeaderBoardResult> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09341) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                YddService yddService = YddClinicRepository.yddService;
                if (yddService == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                }
                String str = this.$skillId;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.getLeaderBoard(str, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getMaintain$1", f = "YddClinicRepository.kt", i = {0}, l = {1110, 1112}, m = "invokeSuspend", n = {"$this$flow"}, s = {"L$0"})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getMaintain$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09351 extends SuspendLambda implements Function2<FlowCollector<? super String>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Map<String, Object> $map;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09351(Map<String, Object> map, Continuation<? super C09351> continuation) {
            super(2, continuation);
            this.$map = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09351 c09351 = new C09351(this.$map, continuation);
            c09351.L$0 = obj;
            return c09351;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09351) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            String strDelHTMLTag;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            boolean z2 = true;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                YddService yddService = YddClinicRepository.yddService;
                if (yddService == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                }
                Map<String, Object> map = this.$map;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.getMaintain(map, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            ExamRuleResult examRuleResult = (ExamRuleResult) obj;
            String content = examRuleResult != null ? examRuleResult.getContent() : null;
            if (content != null && content.length() != 0) {
                z2 = false;
            }
            if (z2) {
                strDelHTMLTag = "";
            } else {
                Intrinsics.checkNotNull(examRuleResult);
                strDelHTMLTag = FucUtil.delHTMLTag(examRuleResult.getContent());
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(strDelHTMLTag, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003*\u0012\u0012\u000e\u0012\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "Lcom/yddmi/doctor/entity/result/InquiryCheckInfo;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getMedicineSearchList$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.bl_selected_gradient_centerColor, R2.attr.bl_selected_gradient_centerColor}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getMedicineSearchList$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09361 extends SuspendLambda implements Function2<FlowCollector<? super List<InquiryCheckInfo>>, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $pid;
        final /* synthetic */ int $type;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09361(int i2, int i3, Continuation<? super C09361> continuation) {
            super(2, continuation);
            this.$pid = i2;
            this.$type = i3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09361 c09361 = new C09361(this.$pid, this.$type, continuation);
            c09361.L$0 = obj;
            return c09361;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super List<InquiryCheckInfo>> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09361) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                YddService yddService = YddClinicRepository.yddService;
                if (yddService == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                }
                Integer numBoxInt = Boxing.boxInt(this.$pid);
                int i3 = this.$type;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.getMedicineSearchList(numBoxInt, i3, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/HomeMsgList;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getMessageList$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.bl_unFocused_solid_color, R2.attr.bl_unFocused_solid_color}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getMessageList$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09371 extends SuspendLambda implements Function2<FlowCollector<? super HomeMsgList>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Integer $pageNum;
        final /* synthetic */ Integer $pageSize;
        final /* synthetic */ int $userId;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09371(int i2, Integer num, Integer num2, Continuation<? super C09371> continuation) {
            super(2, continuation);
            this.$userId = i2;
            this.$pageNum = num;
            this.$pageSize = num2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09371 c09371 = new C09371(this.$userId, this.$pageNum, this.$pageSize, continuation);
            c09371.L$0 = obj;
            return c09371;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super HomeMsgList> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09371) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                int i3 = this.$userId;
                Integer num = this.$pageNum;
                Integer num2 = this.$pageSize;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.getMessageList(i3, num, num2, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/HomeMsgList;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getNoticeList$1", f = "YddClinicRepository.kt", i = {}, l = {752, 751}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getNoticeList$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09381 extends SuspendLambda implements Function2<FlowCollector<? super HomeMsgList>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Integer $pageNum;
        final /* synthetic */ Integer $pageSize;
        final /* synthetic */ int $releaseStatus;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09381(Integer num, Integer num2, int i2, Continuation<? super C09381> continuation) {
            super(2, continuation);
            this.$pageNum = num;
            this.$pageSize = num2;
            this.$releaseStatus = i2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09381 c09381 = new C09381(this.$pageNum, this.$pageSize, this.$releaseStatus, continuation);
            c09381.L$0 = obj;
            return c09381;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super HomeMsgList> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09381) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            YddService yddService;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                YddService yddService2 = YddClinicRepository.yddService;
                if (yddService2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                } else {
                    yddService = yddService2;
                }
                Integer num = this.$pageNum;
                Integer num2 = this.$pageSize;
                int i3 = this.$releaseStatus;
                int iUserId = YddUserManager.INSTANCE.getInstance().userId();
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.getNoticeList(num, num2, i3, iUserId, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getOrderStr$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.chipIconEnabled, R2.attr.chipIconEnabled}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getOrderStr$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09391 extends SuspendLambda implements Function2<FlowCollector<? super String>, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $all;
        final /* synthetic */ String $couponRecordId;
        final /* synthetic */ String $productInfoId;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09391(String str, int i2, String str2, Continuation<? super C09391> continuation) {
            super(2, continuation);
            this.$productInfoId = str;
            this.$all = i2;
            this.$couponRecordId = str2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09391 c09391 = new C09391(this.$productInfoId, this.$all, this.$couponRecordId, continuation);
            c09391.L$0 = obj;
            return c09391;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09391) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                YddService yddService = YddClinicRepository.yddService;
                if (yddService == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                }
                String str = this.$productInfoId;
                int i3 = this.$all;
                String str2 = this.$couponRecordId;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.getOrderStr(str, i3, str2, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003*\u0012\u0012\u000e\u0012\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "Lcom/yddmi/doctor/entity/result/MeAgencyRow;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getOrganzationSearch$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.bl_unChecked_gradient_endColor, R2.attr.bl_unChecked_gradient_endColor}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getOrganzationSearch$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09401 extends SuspendLambda implements Function2<FlowCollector<? super List<MeAgencyRow>>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Map<String, Object> $queryMap;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09401(Map<String, Object> map, Continuation<? super C09401> continuation) {
            super(2, continuation);
            this.$queryMap = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09401 c09401 = new C09401(this.$queryMap, continuation);
            c09401.L$0 = obj;
            return c09401;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super List<MeAgencyRow>> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09401) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                Map<String, Object> map = this.$queryMap;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.getOrganzationSearch(map, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003*\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "Lcom/yddmi/doctor/entity/result/InquiryAskInfo;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getPatientAskGetByKeyword$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.ad_height, R2.attr.ad_height}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getPatientAskGetByKeyword$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09411 extends SuspendLambda implements Function2<FlowCollector<? super List<InquiryAskInfo>>, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $keyword;
        final /* synthetic */ int $patientId;
        final /* synthetic */ int $trainId;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09411(String str, int i2, int i3, Continuation<? super C09411> continuation) {
            super(2, continuation);
            this.$keyword = str;
            this.$patientId = i2;
            this.$trainId = i3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09411 c09411 = new C09411(this.$keyword, this.$patientId, this.$trainId, continuation);
            c09411.L$0 = obj;
            return c09411;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super List<InquiryAskInfo>> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09411) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                String str = this.$keyword;
                int i3 = this.$patientId;
                int i4 = this.$trainId;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.getPatientAskGetByKeyword(str, i3, i4, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/InquiryNote;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getPatientNote$2", f = "YddClinicRepository.kt", i = {}, l = {500, 500}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getPatientNote$2, reason: invalid class name and case insensitive filesystem */
    public static final class C09422 extends SuspendLambda implements Function2<FlowCollector<? super InquiryNote>, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $patientId;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09422(int i2, Continuation<? super C09422> continuation) {
            super(2, continuation);
            this.$patientId = i2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09422 c09422 = new C09422(this.$patientId, continuation);
            c09422.L$0 = obj;
            return c09422;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super InquiryNote> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09422) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                int i3 = this.$patientId;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.getPatientNote(i3, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003*\u0012\u0012\u000e\u0012\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "Lcom/yddmi/doctor/entity/result/InquiryCheckInfo;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getPatientTreeRecord$1", f = "YddClinicRepository.kt", i = {}, l = {408, 408}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getPatientTreeRecord$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09431 extends SuspendLambda implements Function2<FlowCollector<? super List<InquiryCheckInfo>>, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $patientId;
        final /* synthetic */ int $trainId;
        final /* synthetic */ int $type;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09431(int i2, int i3, int i4, Continuation<? super C09431> continuation) {
            super(2, continuation);
            this.$patientId = i2;
            this.$trainId = i3;
            this.$type = i4;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09431 c09431 = new C09431(this.$patientId, this.$trainId, this.$type, continuation);
            c09431.L$0 = obj;
            return c09431;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super List<InquiryCheckInfo>> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09431) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                int i3 = this.$patientId;
                int i4 = this.$trainId;
                int i5 = this.$type;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.getPatientTreeRecord(i3, i4, i5, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/MeProfile;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getPersonInfoApp$1", f = "YddClinicRepository.kt", i = {}, l = {127, 127}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getPersonInfoApp$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09441 extends SuspendLambda implements Function2<FlowCollector<? super MeProfile>, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        public C09441(Continuation<? super C09441> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09441 c09441 = new C09441(continuation);
            c09441.L$0 = obj;
            return c09441;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super MeProfile> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09441) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.getPersonInfoApp(this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getPostUserBankInfo$2", f = "YddClinicRepository.kt", i = {}, l = {187, 187}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getPostUserBankInfo$2, reason: invalid class name and case insensitive filesystem */
    public static final class C09452 extends SuspendLambda implements Function2<FlowCollector<? super String>, Continuation<? super Unit>, Object> {
        final /* synthetic */ BankReqResult $req;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09452(BankReqResult bankReqResult, Continuation<? super C09452> continuation) {
            super(2, continuation);
            this.$req = bankReqResult;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09452 c09452 = new C09452(this.$req, continuation);
            c09452.L$0 = obj;
            return c09452;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09452) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                YddService yddService = YddClinicRepository.yddService;
                if (yddService == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                }
                BankReqResult bankReqResult = this.$req;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.getPostUserBankInfo(bankReqResult, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/SkillPayWx;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getPrepayWx$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.chipSpacingVertical, R2.attr.chipSpacingVertical}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getPrepayWx$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09461 extends SuspendLambda implements Function2<FlowCollector<? super SkillPayWx>, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $all;
        final /* synthetic */ String $productInfoId;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09461(String str, int i2, Continuation<? super C09461> continuation) {
            super(2, continuation);
            this.$productInfoId = str;
            this.$all = i2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09461 c09461 = new C09461(this.$productInfoId, this.$all, continuation);
            c09461.L$0 = obj;
            return c09461;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super SkillPayWx> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09461) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                YddService yddService = YddClinicRepository.yddService;
                if (yddService == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                }
                String str = this.$productInfoId;
                int i3 = this.$all;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.getPrepayWx(str, i3, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getPushCodeForgetPwd$1", f = "YddClinicRepository.kt", i = {}, l = {109, 109}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getPushCodeForgetPwd$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09471 extends SuspendLambda implements Function2<FlowCollector<? super String>, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $phone;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09471(String str, Continuation<? super C09471> continuation) {
            super(2, continuation);
            this.$phone = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09471 c09471 = new C09471(this.$phone, continuation);
            c09471.L$0 = obj;
            return c09471;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09471) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws UnsupportedEncodingException {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                Map<String, String> httpHeaderMap = OtherUtils.INSTANCE.getHttpHeaderMap();
                OtherService otherService = YddClinicRepository.otherService;
                String str = this.$phone;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.getPushCodeForgetPwd(str, 2, 3, httpHeaderMap, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getPushCodeRegister$1", f = "YddClinicRepository.kt", i = {}, l = {96, 96}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getPushCodeRegister$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09481 extends SuspendLambda implements Function2<FlowCollector<? super String>, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $phone;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09481(String str, Continuation<? super C09481> continuation) {
            super(2, continuation);
            this.$phone = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09481 c09481 = new C09481(this.$phone, continuation);
            c09481.L$0 = obj;
            return c09481;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09481) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws UnsupportedEncodingException {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                Map<String, String> httpHeaderMap = OtherUtils.INSTANCE.getHttpHeaderMap();
                OtherService otherService = YddClinicRepository.otherService;
                String str = this.$phone;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.getPushCodeRegister(str, 2, 3, httpHeaderMap, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getReadNoticeId$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.bl_unSelected_gradient_type, R2.attr.bl_unSelected_gradient_type}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getReadNoticeId$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09491 extends SuspendLambda implements Function2<FlowCollector<? super String>, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $msgId;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09491(int i2, Continuation<? super C09491> continuation) {
            super(2, continuation);
            this.$msgId = i2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09491 c09491 = new C09491(this.$msgId, continuation);
            c09491.L$0 = obj;
            return c09491;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09491) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                Integer numBoxInt = Boxing.boxInt(this.$msgId);
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.getReadNoticeId(numBoxInt, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/TeachCasebook;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getRecommendInfo$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.bl_checkable_drawable, R2.attr.bl_checkable_drawable}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getRecommendInfo$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09501 extends SuspendLambda implements Function2<FlowCollector<? super TeachCasebook>, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $patientId;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09501(int i2, Continuation<? super C09501> continuation) {
            super(2, continuation);
            this.$patientId = i2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09501 c09501 = new C09501(this.$patientId, continuation);
            c09501.L$0 = obj;
            return c09501;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super TeachCasebook> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09501) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                int i3 = this.$patientId;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.getRecommendInfo(i3, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/SkillIntegral;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getRecordEquityDetail$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.circularflow_viewCenter, R2.attr.circularflow_viewCenter}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getRecordEquityDetail$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09511 extends SuspendLambda implements Function2<FlowCollector<? super SkillIntegral>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Map<String, Object> $queryMap;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09511(Map<String, Object> map, Continuation<? super C09511> continuation) {
            super(2, continuation);
            this.$queryMap = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09511 c09511 = new C09511(this.$queryMap, continuation);
            c09511.L$0 = obj;
            return c09511;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super SkillIntegral> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09511) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                YddService yddService = YddClinicRepository.yddService;
                if (yddService == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                }
                Map<String, Object> map = this.$queryMap;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.getRecordEquityDetail(map, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/HomeMsgList;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getReplyAppList$1", f = "YddClinicRepository.kt", i = {}, l = {1003, 1002}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getReplyAppList$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09521 extends SuspendLambda implements Function2<FlowCollector<? super HomeMsgList>, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $pageNum;
        final /* synthetic */ int $pageSize;
        final /* synthetic */ String $phone;
        final /* synthetic */ int $source;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09521(int i2, String str, int i3, int i4, Continuation<? super C09521> continuation) {
            super(2, continuation);
            this.$source = i2;
            this.$phone = str;
            this.$pageNum = i3;
            this.$pageSize = i4;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09521 c09521 = new C09521(this.$source, this.$phone, this.$pageNum, this.$pageSize, continuation);
            c09521.L$0 = obj;
            return c09521;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super HomeMsgList> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09521) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            YddService yddService;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                YddService yddService2 = YddClinicRepository.yddService;
                if (yddService2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                } else {
                    yddService = yddService2;
                }
                int i3 = this.$source;
                String str = this.$phone;
                Integer numBoxInt = Boxing.boxInt(this.$pageNum);
                Integer numBoxInt2 = Boxing.boxInt(this.$pageSize);
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.getReplyAppList(i3, str, numBoxInt, numBoxInt2, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getRulelatest$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.clear_input, R2.attr.clear_input}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getRulelatest$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09531 extends SuspendLambda implements Function2<FlowCollector<? super String>, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        public C09531(Continuation<? super C09531> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09531 c09531 = new C09531(continuation);
            c09531.L$0 = obj;
            return c09531;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09531) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                YddService yddService = YddClinicRepository.yddService;
                if (yddService == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                }
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.getRulelatest(this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getSkillTimes$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.contentPadding, R2.attr.contentPadding}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getSkillTimes$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09541 extends SuspendLambda implements Function2<FlowCollector<? super Integer>, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        public C09541(Continuation<? super C09541> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09541 c09541 = new C09541(continuation);
            c09541.L$0 = obj;
            return c09541;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super Integer> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09541) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                YddService yddService = YddClinicRepository.yddService;
                if (yddService == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                }
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.getSkillTimes(this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/HomeTeachingCase;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getTeachingList$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.bl_checkable_gradient_type, R2.attr.bl_checkable_gradient_type}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getTeachingList$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09551 extends SuspendLambda implements Function2<FlowCollector<? super HomeTeachingCase>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Map<String, Object> $body;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09551(Map<String, Object> map, Continuation<? super C09551> continuation) {
            super(2, continuation);
            this.$body = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09551 c09551 = new C09551(this.$body, continuation);
            c09551.L$0 = obj;
            return c09551;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super HomeTeachingCase> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09551) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                Map<String, Object> map = this.$body;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.getTeachingList(map, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/HomeTeachingClass;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getTeachingResourceList$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.bl_checked_gradient_centerColor, R2.attr.bl_checked_gradient_centerColor}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getTeachingResourceList$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09561 extends SuspendLambda implements Function2<FlowCollector<? super HomeTeachingClass>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Map<String, Object> $body;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09561(Map<String, Object> map, Continuation<? super C09561> continuation) {
            super(2, continuation);
            this.$body = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09561 c09561 = new C09561(this.$body, continuation);
            c09561.L$0 = obj;
            return c09561;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super HomeTeachingClass> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09561) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                Map<String, Object> map = this.$body;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.getTeachingResourceList(map, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/TrainInfoList;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getTrainInfoStudyList$1", f = "YddClinicRepository.kt", i = {}, l = {209, 209}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getTrainInfoStudyList$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09571 extends SuspendLambda implements Function2<FlowCollector<? super TrainInfoList>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Map<String, Object> $body;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09571(Map<String, Object> map, Continuation<? super C09571> continuation) {
            super(2, continuation);
            this.$body = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09571 c09571 = new C09571(this.$body, continuation);
            c09571.L$0 = obj;
            return c09571;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super TrainInfoList> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09571) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                Map<String, Object> map = this.$body;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.getTrainInfoStudyList(map, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/InquiryNote;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getTrainMedical$2", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.behavior_peekHeight, R2.attr.behavior_peekHeight}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getTrainMedical$2, reason: invalid class name and case insensitive filesystem */
    public static final class C09582 extends SuspendLambda implements Function2<FlowCollector<? super InquiryNote>, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $patientId;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09582(int i2, Continuation<? super C09582> continuation) {
            super(2, continuation);
            this.$patientId = i2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09582 c09582 = new C09582(this.$patientId, continuation);
            c09582.L$0 = obj;
            return c09582;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super InquiryNote> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09582) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                int i3 = this.$patientId;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.getTrainMedical(i3, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/InquiryNote;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getTrainNoteById$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.autoSizeStepGranularity, R2.attr.autoSizeStepGranularity}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getTrainNoteById$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09591 extends SuspendLambda implements Function2<FlowCollector<? super InquiryNote>, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $trainId;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09591(int i2, Continuation<? super C09591> continuation) {
            super(2, continuation);
            this.$trainId = i2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09591 c09591 = new C09591(this.$trainId, continuation);
            c09591.L$0 = obj;
            return c09591;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super InquiryNote> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09591) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                int i3 = this.$trainId;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.getTrainNoteById(i3, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/InquiryAskList;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getTrainRecordListAsk$1", f = "YddClinicRepository.kt", i = {}, l = {300, 300}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getTrainRecordListAsk$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09601 extends SuspendLambda implements Function2<FlowCollector<? super InquiryAskList>, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $trainId;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09601(int i2, Continuation<? super C09601> continuation) {
            super(2, continuation);
            this.$trainId = i2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09601 c09601 = new C09601(this.$trainId, continuation);
            c09601.L$0 = obj;
            return c09601;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super InquiryAskList> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09601) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                int i3 = this.$trainId;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.getTrainRecordListAsk(i3, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getUnreadCount$1", f = "YddClinicRepository.kt", i = {0, 1, 1}, l = {R2.attr.blendSrc, R2.attr.blue_v_color, R2.attr.blurOverlayColor}, m = "invokeSuspend", n = {"$this$flow", "$this$flow", "msg"}, s = {"L$0", "L$0", "I$0"})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getUnreadCount$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09611 extends SuspendLambda implements Function2<FlowCollector<? super Integer>, Continuation<? super Unit>, Object> {
        int I$0;
        private /* synthetic */ Object L$0;
        int label;

        public C09611(Continuation<? super C09611> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09611 c09611 = new C09611(continuation);
            c09611.L$0 = obj;
            return c09611;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super Integer> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09611) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:22:0x0079 A[RETURN] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r7) {
            /*
                r6 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r6.label
                r2 = 3
                r3 = 2
                r4 = 1
                if (r1 == 0) goto L2f
                if (r1 == r4) goto L27
                if (r1 == r3) goto L1d
                if (r1 != r2) goto L15
                kotlin.ResultKt.throwOnFailure(r7)
                goto L7a
            L15:
                java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r7.<init>(r0)
                throw r7
            L1d:
                int r1 = r6.I$0
                java.lang.Object r3 = r6.L$0
                kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3
                kotlin.ResultKt.throwOnFailure(r7)
                goto L63
            L27:
                java.lang.Object r1 = r6.L$0
                kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
                kotlin.ResultKt.throwOnFailure(r7)
                goto L48
            L2f:
                kotlin.ResultKt.throwOnFailure(r7)
                java.lang.Object r7 = r6.L$0
                kotlinx.coroutines.flow.FlowCollector r7 = (kotlinx.coroutines.flow.FlowCollector) r7
                com.yddmi.doctor.network.api.OtherService r1 = com.yddmi.doctor.repository.YddClinicRepository.access$getOtherService$p()
                r6.L$0 = r7
                r6.label = r4
                java.lang.Object r1 = r1.getUnreadCount(r6)
                if (r1 != r0) goto L45
                return r0
            L45:
                r5 = r1
                r1 = r7
                r7 = r5
            L48:
                java.lang.Number r7 = (java.lang.Number) r7
                int r7 = r7.intValue()
                com.yddmi.doctor.network.api.OtherService r4 = com.yddmi.doctor.repository.YddClinicRepository.access$getOtherService$p()
                r6.L$0 = r1
                r6.I$0 = r7
                r6.label = r3
                java.lang.Object r3 = r4.getUnreadNoticeCount(r6)
                if (r3 != r0) goto L5f
                return r0
            L5f:
                r5 = r1
                r1 = r7
                r7 = r3
                r3 = r5
            L63:
                java.lang.Number r7 = (java.lang.Number) r7
                int r7 = r7.intValue()
                int r1 = r1 + r7
                java.lang.Integer r7 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r1)
                r1 = 0
                r6.L$0 = r1
                r6.label = r2
                java.lang.Object r7 = r3.emit(r7, r6)
                if (r7 != r0) goto L7a
                return r0
            L7a:
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.repository.YddClinicRepository.C09611.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getVeriFyMsg$1", f = "YddClinicRepository.kt", i = {}, l = {102, 102}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getVeriFyMsg$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09621 extends SuspendLambda implements Function2<FlowCollector<? super String>, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $code;
        final /* synthetic */ String $phone;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09621(String str, String str2, Continuation<? super C09621> continuation) {
            super(2, continuation);
            this.$phone = str;
            this.$code = str2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09621 c09621 = new C09621(this.$phone, this.$code, continuation);
            c09621.L$0 = obj;
            return c09621;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09621) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                String str = this.$phone;
                String str2 = this.$code;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.getVeriFyMsg(str, str2, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$getVersionU3d$1", f = "YddClinicRepository.kt", i = {}, l = {994, 994}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$getVersionU3d$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09631 extends SuspendLambda implements Function2<FlowCollector<? super String>, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        public C09631(Continuation<? super C09631> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09631 c09631 = new C09631(continuation);
            c09631.L$0 = obj;
            return c09631;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09631) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                YddService yddService = YddClinicRepository.yddService;
                if (yddService == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                }
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.getVersionU3d(this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003*\u0012\u0012\u000e\u0012\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "Lcom/yddmi/doctor/entity/result/InquiryAsk;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$patientAskGetAskTree$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.actionModeCloseDrawable, R2.attr.actionModeCloseDrawable}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$patientAskGetAskTree$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09641 extends SuspendLambda implements Function2<FlowCollector<? super List<InquiryAsk>>, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $patientId;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09641(int i2, Continuation<? super C09641> continuation) {
            super(2, continuation);
            this.$patientId = i2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09641 c09641 = new C09641(this.$patientId, continuation);
            c09641.L$0 = obj;
            return c09641;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super List<InquiryAsk>> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09641) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                int i3 = this.$patientId;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.patientAskGetAskTree(i3, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "Lcom/yddmi/doctor/entity/result/InquiryAskInfo;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$patientAskGetAskTree1$1", f = "YddClinicRepository.kt", i = {0}, l = {R2.attr.actionModeSelectAllDrawable, R2.attr.adScopeRadius}, m = "invokeSuspend", n = {"$this$flow"}, s = {"L$0"})
    @SourceDebugExtension({"SMAP\nYddClinicRepository.kt\nKotlin\n*S Kotlin\n*F\n+ 1 YddClinicRepository.kt\ncom/yddmi/doctor/repository/YddClinicRepository$patientAskGetAskTree1$1\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,1204:1\n1855#2:1205\n1855#2,2:1206\n1856#2:1208\n*S KotlinDebug\n*F\n+ 1 YddClinicRepository.kt\ncom/yddmi/doctor/repository/YddClinicRepository$patientAskGetAskTree1$1\n*L\n253#1:1205\n259#1:1206,2\n253#1:1208\n*E\n"})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$patientAskGetAskTree1$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09651 extends SuspendLambda implements Function2<FlowCollector<? super List<InquiryAskInfo>>, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $patientId;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09651(int i2, Continuation<? super C09651> continuation) {
            super(2, continuation);
            this.$patientId = i2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09651 c09651 = new C09651(this.$patientId, continuation);
            c09651.L$0 = obj;
            return c09651;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super List<InquiryAskInfo>> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09651) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object objPatientAskGetAskTree;
            List<InquiryAskInfo> patientAskVos;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                int i3 = this.$patientId;
                this.L$0 = flowCollector;
                this.label = 1;
                objPatientAskGetAskTree = otherService.patientAskGetAskTree(i3, this);
                if (objPatientAskGetAskTree == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
                objPatientAskGetAskTree = obj;
            }
            List<InquiryAsk> list = (List) objPatientAskGetAskTree;
            ArrayList arrayList = new ArrayList();
            if (list != null) {
                for (InquiryAsk inquiryAsk : list) {
                    InquiryAskInfo inquiryAskInfo = new InquiryAskInfo((String) null, (Integer) null, (Integer) null, (Integer) null, (Integer) null, (String) null, (Integer) null, (Integer) null, 0, (String) null, false, false, 4095, (DefaultConstructorMarker) null);
                    inquiryAskInfo.setItemTitle(inquiryAsk != null ? inquiryAsk.getName() : null);
                    arrayList.add(inquiryAskInfo);
                    if (inquiryAsk != null && (patientAskVos = inquiryAsk.getPatientAskVos()) != null) {
                        for (InquiryAskInfo inquiryAskInfo2 : patientAskVos) {
                            if (inquiryAskInfo2 != null) {
                                arrayList.add(inquiryAskInfo2);
                            }
                        }
                    }
                }
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(arrayList, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003*\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "Lcom/yddmi/doctor/entity/result/InquiryBindData;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$patientAssistGetBindData$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.autoCompleteTextViewStyle, R2.attr.autoCompleteTextViewStyle}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$patientAssistGetBindData$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09661 extends SuspendLambda implements Function2<FlowCollector<? super List<InquiryBindData>>, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $patientId;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09661(int i2, Continuation<? super C09661> continuation) {
            super(2, continuation);
            this.$patientId = i2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09661 c09661 = new C09661(this.$patientId, continuation);
            c09661.L$0 = obj;
            return c09661;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super List<InquiryBindData>> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09661) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                int i3 = this.$patientId;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.patientAssistGetBindData(i3, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/RowCase;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$patientInfoGetById$1", f = "YddClinicRepository.kt", i = {}, l = {239, 239}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$patientInfoGetById$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09671 extends SuspendLambda implements Function2<FlowCollector<? super RowCase>, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $id;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09671(int i2, Continuation<? super C09671> continuation) {
            super(2, continuation);
            this.$id = i2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09671 c09671 = new C09671(this.$id, continuation);
            c09671.L$0 = obj;
            return c09671;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super RowCase> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09671) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                int i3 = this.$id;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.patientInfoGetById(i3, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/TrainInfoList;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$patientInfoList$1", f = "YddClinicRepository.kt", i = {}, l = {221, 221}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$patientInfoList$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09681 extends SuspendLambda implements Function2<FlowCollector<? super TrainInfoList>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Map<String, Object> $queryMap;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09681(Map<String, Object> map, Continuation<? super C09681> continuation) {
            super(2, continuation);
            this.$queryMap = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09681 c09681 = new C09681(this.$queryMap, continuation);
            c09681.L$0 = obj;
            return c09681;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super TrainInfoList> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09681) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                Map<String, Object> map = this.$queryMap;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.patientInfoList(map, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003*\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "Lcom/yddmi/doctor/entity/result/RowCase;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$patientInfoRandom$1", f = "YddClinicRepository.kt", i = {}, l = {233, 233}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$patientInfoRandom$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09691 extends SuspendLambda implements Function2<FlowCollector<? super List<RowCase>>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Map<String, Object> $queryMap;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09691(Map<String, Object> map, Continuation<? super C09691> continuation) {
            super(2, continuation);
            this.$queryMap = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09691 c09691 = new C09691(this.$queryMap, continuation);
            c09691.L$0 = obj;
            return c09691;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super List<RowCase>> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09691) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                Map<String, Object> map = this.$queryMap;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.patientInfoRandom(map, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$patientInfoSave$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.adjustable, R2.attr.adjustable}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$patientInfoSave$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09701 extends SuspendLambda implements Function2<FlowCollector<? super Integer>, Continuation<? super Unit>, Object> {
        final /* synthetic */ InquiryPatientInfoSave $body;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09701(InquiryPatientInfoSave inquiryPatientInfoSave, Continuation<? super C09701> continuation) {
            super(2, continuation);
            this.$body = inquiryPatientInfoSave;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09701 c09701 = new C09701(this.$body, continuation);
            c09701.L$0 = obj;
            return c09701;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super Integer> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09701) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                InquiryPatientInfoSave inquiryPatientInfoSave = this.$body;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.patientInfoSave(inquiryPatientInfoSave, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/AuthLoginResult;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$postAgentLogin$1", f = "YddClinicRepository.kt", i = {}, l = {193, 193}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$postAgentLogin$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09711 extends SuspendLambda implements Function2<FlowCollector<? super AuthLoginResult>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Map<String, String> $body;
        final /* synthetic */ Map<String, String> $headMap;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09711(Map<String, String> map, Map<String, String> map2, Continuation<? super C09711> continuation) {
            super(2, continuation);
            this.$body = map;
            this.$headMap = map2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09711 c09711 = new C09711(this.$body, this.$headMap, continuation);
            c09711.L$0 = obj;
            return c09711;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super AuthLoginResult> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09711) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                YddService yddService = YddClinicRepository.yddService;
                if (yddService == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                }
                Map<String, String> map = this.$body;
                Map<String, String> map2 = this.$headMap;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.postAgentLogin(map, map2, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/request/FavoriteSaveReq;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$postAppFavoriteSave$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.course_tag_promotion_bg, R2.attr.course_tag_promotion_bg}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$postAppFavoriteSave$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09721 extends SuspendLambda implements Function2<FlowCollector<? super FavoriteSaveReq>, Continuation<? super Unit>, Object> {
        final /* synthetic */ FavoriteSaveReq $body;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09721(FavoriteSaveReq favoriteSaveReq, Continuation<? super C09721> continuation) {
            super(2, continuation);
            this.$body = favoriteSaveReq;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09721 c09721 = new C09721(this.$body, continuation);
            c09721.L$0 = obj;
            return c09721;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super FavoriteSaveReq> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09721) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                YddService yddService = YddClinicRepository.yddService;
                if (yddService == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                }
                FavoriteSaveReq favoriteSaveReq = this.$body;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.postAppFavoriteSave(favoriteSaveReq, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$postAppRead$1", f = "YddClinicRepository.kt", i = {}, l = {1014, 1014}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$postAppRead$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09731 extends SuspendLambda implements Function2<FlowCollector<? super String>, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $str;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09731(String str, Continuation<? super C09731> continuation) {
            super(2, continuation);
            this.$str = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09731 c09731 = new C09731(this.$str, continuation);
            c09731.L$0 = obj;
            return c09731;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09731) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                linkedHashMap.put("id", this.$str);
                YddService yddService = YddClinicRepository.yddService;
                if (yddService == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                }
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.postAppRead(linkedHashMap, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$postAppTableSave$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.bl_unEnabled_textColor, R2.attr.bl_unEnabled_textColor}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$postAppTableSave$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09741 extends SuspendLambda implements Function2<FlowCollector<? super Boolean>, Continuation<? super Unit>, Object> {
        final /* synthetic */ HomeDaskSaveReq $body;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09741(HomeDaskSaveReq homeDaskSaveReq, Continuation<? super C09741> continuation) {
            super(2, continuation);
            this.$body = homeDaskSaveReq;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09741 c09741 = new C09741(this.$body, continuation);
            c09741.L$0 = obj;
            return c09741;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super Boolean> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09741) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                HomeDaskSaveReq homeDaskSaveReq = this.$body;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.postAppTableSave(homeDaskSaveReq, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/HomeGuideList;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$postClinicalGuideline$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.bl_gradient_gradientRadius, R2.attr.bl_gradient_gradientRadius}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$postClinicalGuideline$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09751 extends SuspendLambda implements Function2<FlowCollector<? super HomeGuideList>, Continuation<? super Unit>, Object> {
        final /* synthetic */ HomeGuideReq $req;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09751(HomeGuideReq homeGuideReq, Continuation<? super C09751> continuation) {
            super(2, continuation);
            this.$req = homeGuideReq;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09751 c09751 = new C09751(this.$req, continuation);
            c09751.L$0 = obj;
            return c09751;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super HomeGuideList> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09751) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                YddService yddService = YddClinicRepository.yddService;
                if (yddService == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                }
                HomeGuideReq homeGuideReq = this.$req;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.postClinicalGuideline(homeGuideReq, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003*\u0012\u0012\u000e\u0012\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "Lcom/yddmi/doctor/entity/result/InquiryCheckInfo;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$postClinicalGuideline1$1", f = "YddClinicRepository.kt", i = {0}, l = {R2.attr.bl_multi_selector3, R2.attr.bl_padding_left}, m = "invokeSuspend", n = {"$this$flow"}, s = {"L$0"})
    @SourceDebugExtension({"SMAP\nYddClinicRepository.kt\nKotlin\n*S Kotlin\n*F\n+ 1 YddClinicRepository.kt\ncom/yddmi/doctor/repository/YddClinicRepository$postClinicalGuideline1$1\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,1204:1\n1855#2,2:1205\n*S KotlinDebug\n*F\n+ 1 YddClinicRepository.kt\ncom/yddmi/doctor/repository/YddClinicRepository$postClinicalGuideline1$1\n*L\n632#1:1205,2\n*E\n"})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$postClinicalGuideline1$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09761 extends SuspendLambda implements Function2<FlowCollector<? super List<InquiryCheckInfo>>, Continuation<? super Unit>, Object> {
        final /* synthetic */ HomeGuideReq $req;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09761(HomeGuideReq homeGuideReq, Continuation<? super C09761> continuation) {
            super(2, continuation);
            this.$req = homeGuideReq;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09761 c09761 = new C09761(this.$req, continuation);
            c09761.L$0 = obj;
            return c09761;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super List<InquiryCheckInfo>> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09761) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object objPostClinicalGuideline;
            List<HomeGuideListItem> records;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                HomeGuideReq homeGuideReq = this.$req;
                this.L$0 = flowCollector;
                this.label = 1;
                objPostClinicalGuideline = otherService.postClinicalGuideline(homeGuideReq, this);
                if (objPostClinicalGuideline == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
                objPostClinicalGuideline = obj;
            }
            HomeGuideList homeGuideList = (HomeGuideList) objPostClinicalGuideline;
            ArrayList arrayList = new ArrayList();
            if (homeGuideList != null && (records = homeGuideList.getRecords()) != null) {
                for (HomeGuideListItem homeGuideListItem : records) {
                    arrayList.add(new InquiryCheckInfo(homeGuideListItem.getId(), homeGuideListItem.getTitle(), null, homeGuideListItem.getCourse(), 0, null, null, null, 0 == true ? 1 : 0, null, null, 0, false, null, homeGuideListItem.getCategoryId(), 99, 0, null, null, 0, false, 0, 0, false, 16728052, null));
                }
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(arrayList, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$postCouponRecord$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.color_ffffff, R2.attr.color_ffffff}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$postCouponRecord$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09771 extends SuspendLambda implements Function2<FlowCollector<? super String>, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $type;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09771(int i2, Continuation<? super C09771> continuation) {
            super(2, continuation);
            this.$type = i2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09771 c09771 = new C09771(this.$type, continuation);
            c09771.L$0 = obj;
            return c09771;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09771) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                linkedHashMap.put("type", Boxing.boxInt(this.$type));
                linkedHashMap.put("userId", Boxing.boxInt(YddUserManager.INSTANCE.getInstance().userId()));
                YddService yddService = YddClinicRepository.yddService;
                if (yddService == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                }
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.postCouponRecord(linkedHashMap, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/ExamCheat;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$postExamCheat$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.carousel_nextState, R2.attr.carousel_nextState}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$postExamCheat$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09781 extends SuspendLambda implements Function2<FlowCollector<? super ExamCheat>, Continuation<? super Unit>, Object> {
        final /* synthetic */ ExamCheatReq $body;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09781(ExamCheatReq examCheatReq, Continuation<? super C09781> continuation) {
            super(2, continuation);
            this.$body = examCheatReq;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09781 c09781 = new C09781(this.$body, continuation);
            c09781.L$0 = obj;
            return c09781;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super ExamCheat> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09781) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                ExamCheatReq examCheatReq = this.$body;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.postExamCheat(examCheatReq, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$postFeedBackAdd$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.cpv_progressNormalSize, R2.attr.cpv_progressNormalSize}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$postFeedBackAdd$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09791 extends SuspendLambda implements Function2<FlowCollector<? super Boolean>, Continuation<? super Unit>, Object> {
        final /* synthetic */ FeedBackReq $body;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09791(FeedBackReq feedBackReq, Continuation<? super C09791> continuation) {
            super(2, continuation);
            this.$body = feedBackReq;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09791 c09791 = new C09791(this.$body, continuation);
            c09791.L$0 = obj;
            return c09791;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super Boolean> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09791) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                YddService yddService = YddClinicRepository.yddService;
                if (yddService == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                }
                FeedBackReq feedBackReq = this.$body;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.postFeedBackAdd(feedBackReq, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/SkillShare;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$postIntegralAcquire$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.cimg2, R2.attr.cimg2}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$postIntegralAcquire$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09801 extends SuspendLambda implements Function2<FlowCollector<? super SkillShare>, Continuation<? super Unit>, Object> {
        final /* synthetic */ IntegralAcquireReq $body;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09801(IntegralAcquireReq integralAcquireReq, Continuation<? super C09801> continuation) {
            super(2, continuation);
            this.$body = integralAcquireReq;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09801 c09801 = new C09801(this.$body, continuation);
            c09801.L$0 = obj;
            return c09801;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super SkillShare> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09801) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                YddService yddService = YddClinicRepository.yddService;
                if (yddService == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                }
                IntegralAcquireReq integralAcquireReq = this.$body;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.postIntegralAcquire(integralAcquireReq, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/SkillShare;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$postIntegralFreeReceive$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.circle_guide_text_color, R2.attr.circle_guide_text_color}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$postIntegralFreeReceive$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09811 extends SuspendLambda implements Function2<FlowCollector<? super SkillShare>, Continuation<? super Unit>, Object> {
        final /* synthetic */ IntegralAcquireReq $body;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09811(IntegralAcquireReq integralAcquireReq, Continuation<? super C09811> continuation) {
            super(2, continuation);
            this.$body = integralAcquireReq;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09811 c09811 = new C09811(this.$body, continuation);
            c09811.L$0 = obj;
            return c09811;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super SkillShare> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09811) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                YddService yddService = YddClinicRepository.yddService;
                if (yddService == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                }
                IntegralAcquireReq integralAcquireReq = this.$body;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.postIntegralFreeReceive(integralAcquireReq, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$postIntegralUnlock$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.circle_tag_icon, R2.attr.circle_tag_icon}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$postIntegralUnlock$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09821 extends SuspendLambda implements Function2<FlowCollector<? super Boolean>, Continuation<? super Unit>, Object> {
        final /* synthetic */ IntegralUnlockReq $req;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09821(IntegralUnlockReq integralUnlockReq, Continuation<? super C09821> continuation) {
            super(2, continuation);
            this.$req = integralUnlockReq;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09821 c09821 = new C09821(this.$req, continuation);
            c09821.L$0 = obj;
            return c09821;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super Boolean> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09821) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                YddService yddService = YddClinicRepository.yddService;
                if (yddService == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                }
                IntegralUnlockReq integralUnlockReq = this.$req;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.postIntegralUnlock(integralUnlockReq, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/AuthLoginResult1;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$postInviteLogin$1", f = "YddClinicRepository.kt", i = {}, l = {89, 89}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$postInviteLogin$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09831 extends SuspendLambda implements Function2<FlowCollector<? super AuthLoginResult1>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Map<String, String> $body;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09831(Map<String, String> map, Continuation<? super C09831> continuation) {
            super(2, continuation);
            this.$body = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09831 c09831 = new C09831(this.$body, continuation);
            c09831.L$0 = obj;
            return c09831;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super AuthLoginResult1> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09831) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                Map<String, String> map = this.$body;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.postInviteLogin(map, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/HomeMsg;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$postInviterSave$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.cornerFamilyTopLeft, R2.attr.cornerFamilyTopLeft}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$postInviterSave$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09841 extends SuspendLambda implements Function2<FlowCollector<? super HomeMsg>, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        public C09841(Continuation<? super C09841> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09841 c09841 = new C09841(continuation);
            c09841.L$0 = obj;
            return c09841;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super HomeMsg> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09841) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                YddService yddService = YddClinicRepository.yddService;
                if (yddService == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                }
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.postInviterSave(this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$postKnowledgeLearnRecordSave$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.cpv_innerProgressColor, R2.attr.cpv_innerProgressColor}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$postKnowledgeLearnRecordSave$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09851 extends SuspendLambda implements Function2<FlowCollector<? super Boolean>, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $medicalKnowledgeId;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09851(String str, Continuation<? super C09851> continuation) {
            super(2, continuation);
            this.$medicalKnowledgeId = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09851 c09851 = new C09851(this.$medicalKnowledgeId, continuation);
            c09851.L$0 = obj;
            return c09851;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super Boolean> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09851) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                YddService yddService = YddClinicRepository.yddService;
                if (yddService == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                }
                String str = this.$medicalKnowledgeId;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.postKnowledgeLearnRecordSave(str, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/AuthLoginResult;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$postOneclickLogin$1", f = "YddClinicRepository.kt", i = {}, l = {83, 83}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$postOneclickLogin$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09861 extends SuspendLambda implements Function2<FlowCollector<? super AuthLoginResult>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Map<String, String> $body;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09861(Map<String, String> map, Continuation<? super C09861> continuation) {
            super(2, continuation);
            this.$body = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09861 c09861 = new C09861(this.$body, continuation);
            c09861.L$0 = obj;
            return c09861;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super AuthLoginResult> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09861) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                Map<String, String> map = this.$body;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.postOneclickLogin(map, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/ScoreBlood;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$postPatientAssistInfoGo$1", f = "YddClinicRepository.kt", i = {}, l = {610, 610}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$postPatientAssistInfoGo$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09871 extends SuspendLambda implements Function2<FlowCollector<? super ScoreBlood>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Map<String, Integer> $req;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09871(Map<String, Integer> map, Continuation<? super C09871> continuation) {
            super(2, continuation);
            this.$req = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09871 c09871 = new C09871(this.$req, continuation);
            c09871.L$0 = obj;
            return c09871;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super ScoreBlood> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09871) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                Map<String, Integer> map = this.$req;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.postPatientAssistInfo(map, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$postPatientNoteSave$2", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.bg_invite_friend_bg, R2.attr.bg_invite_friend_bg}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$postPatientNoteSave$2, reason: invalid class name and case insensitive filesystem */
    public static final class C09882 extends SuspendLambda implements Function2<FlowCollector<? super Boolean>, Continuation<? super Unit>, Object> {
        final /* synthetic */ InquirySaveNoteReq $body;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09882(InquirySaveNoteReq inquirySaveNoteReq, Continuation<? super C09882> continuation) {
            super(2, continuation);
            this.$body = inquirySaveNoteReq;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09882 c09882 = new C09882(this.$body, continuation);
            c09882.L$0 = obj;
            return c09882;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super Boolean> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09882) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                InquirySaveNoteReq inquirySaveNoteReq = this.$body;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.postPatientNoteSave(inquirySaveNoteReq, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$postPersonInfo$1", f = "YddClinicRepository.kt", i = {}, l = {133, 133}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$postPersonInfo$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09891 extends SuspendLambda implements Function2<FlowCollector<? super String>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Map<String, String> $body;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09891(Map<String, String> map, Continuation<? super C09891> continuation) {
            super(2, continuation);
            this.$body = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09891 c09891 = new C09891(this.$body, continuation);
            c09891.L$0 = obj;
            return c09891;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09891) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                Map<String, String> map = this.$body;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.postPersonInfo(map, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$postPersonSubmit$1", f = "YddClinicRepository.kt", i = {}, l = {155, 155}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$postPersonSubmit$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09901 extends SuspendLambda implements Function2<FlowCollector<? super String>, Continuation<? super Unit>, Object> {
        final /* synthetic */ HomeMeAuditSubmit $req;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09901(HomeMeAuditSubmit homeMeAuditSubmit, Continuation<? super C09901> continuation) {
            super(2, continuation);
            this.$req = homeMeAuditSubmit;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09901 c09901 = new C09901(this.$req, continuation);
            c09901.L$0 = obj;
            return c09901;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09901) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                HomeMeAuditSubmit homeMeAuditSubmit = this.$req;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.postPersonSubmit(homeMeAuditSubmit, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$postPointSave$2", f = "YddClinicRepository.kt", i = {}, l = {177, 177}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$postPointSave$2, reason: invalid class name and case insensitive filesystem */
    public static final class C09912 extends SuspendLambda implements Function2<FlowCollector<? super String>, Continuation<? super Unit>, Object> {
        final /* synthetic */ PointSaveReq $req;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09912(PointSaveReq pointSaveReq, Continuation<? super C09912> continuation) {
            super(2, continuation);
            this.$req = pointSaveReq;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09912 c09912 = new C09912(this.$req, continuation);
            c09912.L$0 = obj;
            return c09912;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09912) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                YddService yddService = YddClinicRepository.yddService;
                if (yddService == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                }
                PointSaveReq pointSaveReq = this.$req;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.postPointSave(pointSaveReq, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$postPopUserUpdateStatus$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.controller_layout_id, R2.attr.controller_layout_id}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$postPopUserUpdateStatus$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09921 extends SuspendLambda implements Function2<FlowCollector<? super String>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Map<String, String> $body;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09921(Map<String, String> map, Continuation<? super C09921> continuation) {
            super(2, continuation);
            this.$body = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09921 c09921 = new C09921(this.$body, continuation);
            c09921.L$0 = obj;
            return c09921;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09921) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                YddService yddService = YddClinicRepository.yddService;
                if (yddService == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                }
                Map<String, String> map = this.$body;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.postPopUserUpdateStatus(map, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$postRedeemCode$1", f = "YddClinicRepository.kt", i = {}, l = {1023, 1023}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$postRedeemCode$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09931 extends SuspendLambda implements Function2<FlowCollector<? super String>, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $code;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09931(String str, Continuation<? super C09931> continuation) {
            super(2, continuation);
            this.$code = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09931 c09931 = new C09931(this.$code, continuation);
            c09931.L$0 = obj;
            return c09931;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09931) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                linkedHashMap.put("name", this.$code);
                linkedHashMap.put("userName", YddUserManager.INSTANCE.getInstance().userName());
                YddService yddService = YddClinicRepository.yddService;
                if (yddService == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("yddService");
                    yddService = null;
                }
                this.L$0 = flowCollector;
                this.label = 1;
                obj = yddService.postRedeemCode(linkedHashMap, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$postTenantInfoRegister$1", f = "YddClinicRepository.kt", i = {}, l = {700, 700}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$postTenantInfoRegister$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09941 extends SuspendLambda implements Function2<FlowCollector<? super Integer>, Continuation<? super Unit>, Object> {
        final /* synthetic */ HomeTenantReq $req;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09941(HomeTenantReq homeTenantReq, Continuation<? super C09941> continuation) {
            super(2, continuation);
            this.$req = homeTenantReq;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09941 c09941 = new C09941(this.$req, continuation);
            c09941.L$0 = obj;
            return c09941;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super Integer> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09941) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                HomeTenantReq homeTenantReq = this.$req;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.postTenantInfoRegister(homeTenantReq, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$postTrainMedicalSave$2", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.behavior_draggable, R2.attr.behavior_draggable}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$postTrainMedicalSave$2, reason: invalid class name and case insensitive filesystem */
    public static final class C09952 extends SuspendLambda implements Function2<FlowCollector<? super Integer>, Continuation<? super Unit>, Object> {
        final /* synthetic */ InquirySaveNoteReq $body;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09952(InquirySaveNoteReq inquirySaveNoteReq, Continuation<? super C09952> continuation) {
            super(2, continuation);
            this.$body = inquirySaveNoteReq;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09952 c09952 = new C09952(this.$body, continuation);
            c09952.L$0 = obj;
            return c09952;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super Integer> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09952) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                InquirySaveNoteReq inquirySaveNoteReq = this.$body;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.postTrainMedicalSave(inquirySaveNoteReq, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$postTrainNoteSave$1", f = "YddClinicRepository.kt", i = {}, l = {400, 400}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$postTrainNoteSave$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09961 extends SuspendLambda implements Function2<FlowCollector<? super Integer>, Continuation<? super Unit>, Object> {
        final /* synthetic */ InquirySaveNoteReq $req;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09961(InquirySaveNoteReq inquirySaveNoteReq, Continuation<? super C09961> continuation) {
            super(2, continuation);
            this.$req = inquirySaveNoteReq;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09961 c09961 = new C09961(this.$req, continuation);
            c09961.L$0 = obj;
            return c09961;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super Integer> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09961) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                InquirySaveNoteReq inquirySaveNoteReq = this.$req;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.postTrainNoteSave(inquirySaveNoteReq, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$postUserBinding$1", f = "YddClinicRepository.kt", i = {}, l = {161, 161}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$postUserBinding$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09971 extends SuspendLambda implements Function2<FlowCollector<? super String>, Continuation<? super Unit>, Object> {
        final /* synthetic */ LoginWxBindingReq $req;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09971(LoginWxBindingReq loginWxBindingReq, Continuation<? super C09971> continuation) {
            super(2, continuation);
            this.$req = loginWxBindingReq;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09971 c09971 = new C09971(this.$req, continuation);
            c09971.L$0 = obj;
            return c09971;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09971) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                LoginWxBindingReq loginWxBindingReq = this.$req;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.postUserBinding(loginWxBindingReq, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$postUserReBinding$1", f = "YddClinicRepository.kt", i = {}, l = {167, 167}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$postUserReBinding$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09981 extends SuspendLambda implements Function2<FlowCollector<? super String>, Continuation<? super Unit>, Object> {
        final /* synthetic */ LoginWxReBindingReq $req;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09981(LoginWxReBindingReq loginWxReBindingReq, Continuation<? super C09981> continuation) {
            super(2, continuation);
            this.$req = loginWxReBindingReq;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09981 c09981 = new C09981(this.$req, continuation);
            c09981.L$0 = obj;
            return c09981;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09981) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                LoginWxReBindingReq loginWxReBindingReq = this.$req;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.postUserReBinding(loginWxReBindingReq, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$readMessageList$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.bl_unSelected_gradient_centerColor, R2.attr.bl_unSelected_gradient_centerColor}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$readMessageList$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09991 extends SuspendLambda implements Function2<FlowCollector<? super String>, Continuation<? super Unit>, Object> {
        final /* synthetic */ HomeMsgReq $body;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09991(HomeMsgReq homeMsgReq, Continuation<? super C09991> continuation) {
            super(2, continuation);
            this.$body = homeMsgReq;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C09991 c09991 = new C09991(this.$body, continuation);
            c09991.L$0 = obj;
            return c09991;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C09991) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                HomeMsgReq homeMsgReq = this.$body;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.readMessageList(homeMsgReq, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$saveExamQuestionResult$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.button_icon_color, R2.attr.button_icon_color}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$saveExamQuestionResult$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10001 extends SuspendLambda implements Function2<FlowCollector<? super String>, Continuation<? super Unit>, Object> {
        final /* synthetic */ ExamSaveReq $req;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10001(ExamSaveReq examSaveReq, Continuation<? super C10001> continuation) {
            super(2, continuation);
            this.$req = examSaveReq;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10001 c10001 = new C10001(this.$req, continuation);
            c10001.L$0 = obj;
            return c10001;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C10001) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                ExamSaveReq examSaveReq = this.$req;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.saveExamQuestionResult(examSaveReq, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$startExam$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.bvp_page_style, R2.attr.bvp_page_style}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$startExam$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10011 extends SuspendLambda implements Function2<FlowCollector<? super Integer>, Continuation<? super Unit>, Object> {
        final /* synthetic */ ExamUpdateReq $req;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10011(ExamUpdateReq examUpdateReq, Continuation<? super C10011> continuation) {
            super(2, continuation);
            this.$req = examUpdateReq;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10011 c10011 = new C10011(this.$req, continuation);
            c10011.L$0 = obj;
            return c10011;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super Integer> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C10011) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                ExamUpdateReq examUpdateReq = this.$req;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.startExam(examUpdateReq, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003*\u0012\u0012\u000e\u0012\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "Lcom/yddmi/doctor/entity/result/InquiryCheckInfo;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$sysAssistCheckGetTree$1", f = "YddClinicRepository.kt", i = {}, l = {322, 322}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$sysAssistCheckGetTree$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10021 extends SuspendLambda implements Function2<FlowCollector<? super List<InquiryCheckInfo>>, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        public C10021(Continuation<? super C10021> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10021 c10021 = new C10021(continuation);
            c10021.L$0 = obj;
            return c10021;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super List<InquiryCheckInfo>> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C10021) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.sysAssistCheckGetTree(this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003*\u0012\u0012\u000e\u0012\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "Lcom/yddmi/doctor/entity/result/InquiryCheckInfo;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$sysTreatmentGetTree$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.antiAlias, R2.attr.antiAlias}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$sysTreatmentGetTree$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10031 extends SuspendLambda implements Function2<FlowCollector<? super List<InquiryCheckInfo>>, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        public C10031(Continuation<? super C10031> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10031 c10031 = new C10031(continuation);
            c10031.L$0 = obj;
            return c10031;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super List<InquiryCheckInfo>> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C10031) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.sysTreatmentGetTree(this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$trainDagnoseSave$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.arcLabelText, R2.attr.arcLabelText}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$trainDagnoseSave$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10041 extends SuspendLambda implements Function2<FlowCollector<? super Integer>, Continuation<? super Unit>, Object> {
        final /* synthetic */ InquiryDiseaseReq $body;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10041(InquiryDiseaseReq inquiryDiseaseReq, Continuation<? super C10041> continuation) {
            super(2, continuation);
            this.$body = inquiryDiseaseReq;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10041 c10041 = new C10041(this.$body, continuation);
            c10041.L$0 = obj;
            return c10041;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super Integer> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C10041) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                InquiryDiseaseReq inquiryDiseaseReq = this.$body;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.trainDagnoseSave(inquiryDiseaseReq, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/InquiryAskList;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$trainDiagnoseBasisGetSavedBasis$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.arcTickPadding, R2.attr.arcTickPadding}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$trainDiagnoseBasisGetSavedBasis$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10051 extends SuspendLambda implements Function2<FlowCollector<? super InquiryAskList>, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $trainDiagnoseId;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10051(int i2, Continuation<? super C10051> continuation) {
            super(2, continuation);
            this.$trainDiagnoseId = i2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10051 c10051 = new C10051(this.$trainDiagnoseId, continuation);
            c10051.L$0 = obj;
            return c10051;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super InquiryAskList> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C10051) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                int i3 = this.$trainDiagnoseId;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.trainDiagnoseBasisGetSavedBasis(i3, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$trainDiagnoseBasisSaveBasis$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.arcThumbColor, R2.attr.arcThumbColor}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$trainDiagnoseBasisSaveBasis$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10061 extends SuspendLambda implements Function2<FlowCollector<? super Integer>, Continuation<? super Unit>, Object> {
        final /* synthetic */ InquirySaveBasisReq $body;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10061(InquirySaveBasisReq inquirySaveBasisReq, Continuation<? super C10061> continuation) {
            super(2, continuation);
            this.$body = inquirySaveBasisReq;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10061 c10061 = new C10061(this.$body, continuation);
            c10061.L$0 = obj;
            return c10061;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super Integer> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C10061) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                InquirySaveBasisReq inquirySaveBasisReq = this.$body;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.trainDiagnoseBasisSaveBasis(inquirySaveBasisReq, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$trainDiagnoseDelete$1", f = "YddClinicRepository.kt", i = {}, l = {360, 360}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$trainDiagnoseDelete$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10071 extends SuspendLambda implements Function2<FlowCollector<? super Integer>, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $id;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10071(int i2, Continuation<? super C10071> continuation) {
            super(2, continuation);
            this.$id = i2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10071 c10071 = new C10071(this.$id, continuation);
            c10071.L$0 = obj;
            return c10071;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super Integer> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C10071) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                int i3 = this.$id;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.trainDiagnoseDelete(i3, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0004\u001a\u00020\u0003*\u0012\u0012\u000e\u0012\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "Lcom/yddmi/doctor/entity/result/RowCase;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$trainDiagnoseGetByTrainId$1", f = "YddClinicRepository.kt", i = {}, l = {316, 316}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$trainDiagnoseGetByTrainId$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10081 extends SuspendLambda implements Function2<FlowCollector<? super List<RowCase>>, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $trainId;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10081(int i2, Continuation<? super C10081> continuation) {
            super(2, continuation);
            this.$trainId = i2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10081 c10081 = new C10081(this.$trainId, continuation);
            c10081.L$0 = obj;
            return c10081;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super List<RowCase>> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C10081) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                int i3 = this.$trainId;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.trainDiagnoseGetByTrainId(i3, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$trainDiagnoseUpdate$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.arcShowTick, R2.attr.arcShowTick}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$trainDiagnoseUpdate$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10091 extends SuspendLambda implements Function2<FlowCollector<? super Integer>, Continuation<? super Unit>, Object> {
        final /* synthetic */ InquiryDiseaseUpdateReq $body;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10091(InquiryDiseaseUpdateReq inquiryDiseaseUpdateReq, Continuation<? super C10091> continuation) {
            super(2, continuation);
            this.$body = inquiryDiseaseUpdateReq;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10091 c10091 = new C10091(this.$body, continuation);
            c10091.L$0 = obj;
            return c10091;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super Integer> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C10091) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                InquiryDiseaseUpdateReq inquiryDiseaseUpdateReq = this.$body;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.trainDiagnoseUpdate(inquiryDiseaseUpdateReq, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/RowCase;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$trainInfoGetUnCompletee$1", f = "YddClinicRepository.kt", i = {}, l = {227, 227}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$trainInfoGetUnCompletee$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10101 extends SuspendLambda implements Function2<FlowCollector<? super RowCase>, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        int label;

        public C10101(Continuation<? super C10101> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10101 c10101 = new C10101(continuation);
            c10101.L$0 = obj;
            return c10101;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super RowCase> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C10101) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.trainInfoGetUnCompletee(this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/TrainInfoList;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$trainInfoList$1", f = "YddClinicRepository.kt", i = {}, l = {201, 201}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$trainInfoList$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10111 extends SuspendLambda implements Function2<FlowCollector<? super TrainInfoList>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Map<String, Object> $queryMap;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10111(Map<String, Object> map, Continuation<? super C10111> continuation) {
            super(2, continuation);
            this.$queryMap = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10111 c10111 = new C10111(this.$queryMap, continuation);
            c10111.L$0 = obj;
            return c10111;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super TrainInfoList> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C10111) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                Map<String, Object> map = this.$queryMap;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.trainInfoList(map, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/RowCase;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$trainInfoUpdateStatus$1", f = "YddClinicRepository.kt", i = {}, l = {310, 310}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$trainInfoUpdateStatus$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10121 extends SuspendLambda implements Function2<FlowCollector<? super RowCase>, Continuation<? super Unit>, Object> {
        final /* synthetic */ InquiryUpdateStatus $body;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10121(InquiryUpdateStatus inquiryUpdateStatus, Continuation<? super C10121> continuation) {
            super(2, continuation);
            this.$body = inquiryUpdateStatus;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10121 c10121 = new C10121(this.$body, continuation);
            c10121.L$0 = obj;
            return c10121;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super RowCase> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C10121) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                InquiryUpdateStatus inquiryUpdateStatus = this.$body;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.trainInfoUpdateStatus(inquiryUpdateStatus, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$trainRecordBatchSave$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.app_theme_red, R2.attr.app_theme_red}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$trainRecordBatchSave$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10131 extends SuspendLambda implements Function2<FlowCollector<? super Boolean>, Continuation<? super Unit>, Object> {
        final /* synthetic */ InquiryCheckReq $body;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10131(InquiryCheckReq inquiryCheckReq, Continuation<? super C10131> continuation) {
            super(2, continuation);
            this.$body = inquiryCheckReq;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10131 c10131 = new C10131(this.$body, continuation);
            c10131.L$0 = obj;
            return c10131;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super Boolean> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C10131) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                InquiryCheckReq inquiryCheckReq = this.$body;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.trainRecordBatchSave(inquiryCheckReq, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/InquiryAskList;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$trainRecordListDoneDetails$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.all_course_live_banner_start_bg_color, R2.attr.all_course_live_banner_start_bg_color}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$trainRecordListDoneDetails$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10141 extends SuspendLambda implements Function2<FlowCollector<? super InquiryAskList>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Map<String, Object> $queryMap;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10141(Map<String, Object> map, Continuation<? super C10141> continuation) {
            super(2, continuation);
            this.$queryMap = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10141 c10141 = new C10141(this.$queryMap, continuation);
            c10141.L$0 = obj;
            return c10141;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super InquiryAskList> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C10141) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                Map<String, Object> map = this.$queryMap;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.trainRecordListDoneDetails(map, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$trainRecordSave$1", f = "YddClinicRepository.kt", i = {}, l = {R2.attr.alignContent, R2.attr.alignContent}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$trainRecordSave$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10151 extends SuspendLambda implements Function2<FlowCollector<? super Integer>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Map<String, Integer> $body;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10151(Map<String, Integer> map, Continuation<? super C10151> continuation) {
            super(2, continuation);
            this.$body = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10151 c10151 = new C10151(this.$body, continuation);
            c10151.L$0 = obj;
            return c10151;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super Integer> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C10151) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                Map<String, Integer> map = this.$body;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.trainRecordSave(map, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$updatePwd$1", f = "YddClinicRepository.kt", i = {}, l = {121, 121}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$updatePwd$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10161 extends SuspendLambda implements Function2<FlowCollector<? super String>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Map<String, String> $body;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10161(Map<String, String> map, Continuation<? super C10161> continuation) {
            super(2, continuation);
            this.$body = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10161 c10161 = new C10161(this.$body, continuation);
            c10161.L$0 = obj;
            return c10161;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C10161) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                Map<String, String> map = this.$body;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.updatePwd(map, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$uploadImagesHeader$1", f = "YddClinicRepository.kt", i = {}, l = {149, 149}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$uploadImagesHeader$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10171 extends SuspendLambda implements Function2<FlowCollector<? super String>, Continuation<? super Unit>, Object> {
        final /* synthetic */ MultipartBody.Part $iconFile;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10171(MultipartBody.Part part, Continuation<? super C10171> continuation) {
            super(2, continuation);
            this.$iconFile = part;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10171 c10171 = new C10171(this.$iconFile, continuation);
            c10171.L$0 = obj;
            return c10171;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C10171) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                MultipartBody.Part part = this.$iconFile;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.uploadImagesHeader(part, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.repository.YddClinicRepository$userAccountCancel$1", f = "YddClinicRepository.kt", i = {}, l = {143, 143}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.repository.YddClinicRepository$userAccountCancel$1, reason: invalid class name and case insensitive filesystem */
    public static final class C10181 extends SuspendLambda implements Function2<FlowCollector<? super String>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Map<String, String> $body;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10181(Map<String, String> map, Continuation<? super C10181> continuation) {
            super(2, continuation);
            this.$body = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C10181 c10181 = new C10181(this.$body, continuation);
            c10181.L$0 = obj;
            return c10181;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C10181) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                OtherService otherService = YddClinicRepository.otherService;
                Map<String, String> map = this.$body;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = otherService.userAccountCancel(map, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    private YddClinicRepository() {
    }

    public static /* synthetic */ Object getAppGeneralConfig$default(YddClinicRepository yddClinicRepository, int i2, int i3, Continuation continuation, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i3 = 3;
        }
        return yddClinicRepository.getAppGeneralConfig(i2, i3, continuation);
    }

    public static /* synthetic */ Flow getAppGeneralConfigFlow$default(YddClinicRepository yddClinicRepository, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i3 = 3;
        }
        return yddClinicRepository.getAppGeneralConfigFlow(i2, i3);
    }

    public static /* synthetic */ Flow getAppKnowledgeStatistics$default(YddClinicRepository yddClinicRepository, int i2, int i3, int i4, int i5, int i6, Object obj) {
        if ((i6 & 4) != 0) {
            i4 = 1;
        }
        if ((i6 & 8) != 0) {
            i5 = 100;
        }
        return yddClinicRepository.getAppKnowledgeStatistics(i2, i3, i4, i5);
    }

    public static /* synthetic */ Object getBodyCheck$default(YddClinicRepository yddClinicRepository, String str, String str2, Continuation continuation, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = "";
        }
        if ((i2 & 2) != 0) {
            str2 = "";
        }
        return yddClinicRepository.getBodyCheck(str, str2, continuation);
    }

    public static /* synthetic */ Flow getFeedBackPage$default(YddClinicRepository yddClinicRepository, String str, String str2, int i2, int i3, int i4, Object obj) {
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = 100;
        }
        return yddClinicRepository.getFeedBackPage(str, str2, i2, i3);
    }

    public static /* synthetic */ Object getHomeInfoList$default(YddClinicRepository yddClinicRepository, int i2, int i3, int i4, Continuation continuation, int i5, Object obj) {
        if ((i5 & 2) != 0) {
            i3 = 1;
        }
        if ((i5 & 4) != 0) {
            i4 = 10;
        }
        return yddClinicRepository.getHomeInfoList(i2, i3, i4, continuation);
    }

    public static /* synthetic */ Object getKnowledgeDetails$default(YddClinicRepository yddClinicRepository, String str, int i2, int i3, Continuation continuation, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i2 = 2;
        }
        if ((i4 & 4) != 0) {
            i3 = 2;
        }
        return yddClinicRepository.getKnowledgeDetails(str, i2, i3, continuation);
    }

    public static /* synthetic */ Flow getMessageList$default(YddClinicRepository yddClinicRepository, int i2, Integer num, Integer num2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            num = 1;
        }
        if ((i3 & 4) != 0) {
            num2 = 10;
        }
        return yddClinicRepository.getMessageList(i2, num, num2);
    }

    public static /* synthetic */ Flow getNoticeList$default(YddClinicRepository yddClinicRepository, Integer num, Integer num2, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            num = 1;
        }
        if ((i3 & 2) != 0) {
            num2 = 10;
        }
        if ((i3 & 4) != 0) {
            i2 = 1;
        }
        return yddClinicRepository.getNoticeList(num, num2, i2);
    }

    public static /* synthetic */ Flow getOrderStr$default(YddClinicRepository yddClinicRepository, String str, int i2, String str2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = 0;
        }
        if ((i3 & 4) != 0) {
            str2 = "";
        }
        return yddClinicRepository.getOrderStr(str, i2, str2);
    }

    public static /* synthetic */ Flow getPrepayWx$default(YddClinicRepository yddClinicRepository, String str, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = 0;
        }
        return yddClinicRepository.getPrepayWx(str, i2);
    }

    public static /* synthetic */ Flow getReplyAppList$default(YddClinicRepository yddClinicRepository, int i2, String str, int i3, int i4, int i5, Object obj) {
        if ((i5 & 4) != 0) {
            i3 = 1;
        }
        if ((i5 & 8) != 0) {
            i4 = 10;
        }
        return yddClinicRepository.getReplyAppList(i2, str, i3, i4);
    }

    public static /* synthetic */ Object getSkillBasic$default(YddClinicRepository yddClinicRepository, String str, int i2, Continuation continuation, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = 1;
        }
        return yddClinicRepository.getSkillBasic(str, i2, continuation);
    }

    public static /* synthetic */ Object getSysTrainRecordGetKeyWordSearch$default(YddClinicRepository yddClinicRepository, int i2, int i3, String str, Continuation continuation, int i4, Object obj) {
        if ((i4 & 4) != 0) {
            str = "";
        }
        return yddClinicRepository.getSysTrainRecordGetKeyWordSearch(i2, i3, str, continuation);
    }

    public static /* synthetic */ Object getUserCoupon$default(YddClinicRepository yddClinicRepository, long j2, Continuation continuation, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            j2 = -1;
        }
        return yddClinicRepository.getUserCoupon(j2, continuation);
    }

    public static /* synthetic */ Object getUserExamList$default(YddClinicRepository yddClinicRepository, int i2, String str, Integer num, Integer num2, Continuation continuation, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            str = "";
        }
        String str2 = str;
        if ((i3 & 4) != 0) {
            num = 1;
        }
        Integer num3 = num;
        if ((i3 & 8) != 0) {
            num2 = 10;
        }
        return yddClinicRepository.getUserExamList(i2, str2, num3, num2, continuation);
    }

    public static /* synthetic */ Object sysDiseaseList$default(YddClinicRepository yddClinicRepository, String str, String str2, Integer num, Integer num2, Continuation continuation, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            num = 0;
        }
        Integer num3 = num;
        if ((i2 & 8) != 0) {
            num2 = 100;
        }
        return yddClinicRepository.sysDiseaseList(str, str2, num3, num2, continuation);
    }

    @NotNull
    public final Flow<AuthLoginResult> authLogin(@NotNull Map<String, String> body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return FlowKt.flow(new AnonymousClass1(body, null));
    }

    @NotNull
    public final Flow<AuthLoginResult> codeLogin(@NotNull Map<String, String> body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return FlowKt.flow(new C09121(body, null));
    }

    @NotNull
    public final Flow<String> commitExamInfo(@NotNull ExamUpdateReq req) {
        Intrinsics.checkNotNullParameter(req, "req");
        return FlowKt.flow(new C09131(req, null));
    }

    public final void dealChangeBaseUrl(@NotNull YddHostConfig.YddHost host) {
        Intrinsics.checkNotNullParameter(host, "host");
        switch (WhenMappings.$EnumSwitchMapping$0[host.ordinal()]) {
            case 1:
                yddService = (YddService) NetManager.INSTANCE.getInstance().getService(YddServiceDev.class);
                break;
            case 2:
                yddService = (YddService) NetManager.INSTANCE.getInstance().getService(YddServiceTest.class);
                break;
            case 3:
                yddService = (YddService) NetManager.INSTANCE.getInstance().getService(YddServiceTest192.class);
                break;
            case 4:
                yddService = (YddService) NetManager.INSTANCE.getInstance().getService(YddServiceTest126.class);
                break;
            case 5:
                yddService = (YddService) NetManager.INSTANCE.getInstance().getService(YddServiceUat.class);
                break;
            case 6:
                yddService = (YddService) NetManager.INSTANCE.getInstance().getServiceBase(OtherService.class, YddApi.INSTANCE.getBaseUrl());
                break;
            default:
                yddService = (YddService) NetManager.INSTANCE.getInstance().getService(YddServiceFormal.class);
                break;
        }
    }

    public final void dealOtherService() {
        otherService = (OtherService) NetManager.INSTANCE.getInstance().getServiceBase(OtherService.class, YddHostConfig.INSTANCE.getInstance().servicePrivateApiGet());
    }

    @NotNull
    public final Flow<ExamResultDetail> examDetailInfo(@NotNull ExamDetailInfoReq body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return FlowKt.flow(new C09141(body, null));
    }

    @NotNull
    public final Flow<ExamResultVo> examResult(@NotNull ExamResultReq body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return FlowKt.flow(new C09151(body, null));
    }

    @NotNull
    public final Flow<String> forgetPwd(@NotNull Map<String, String> body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return FlowKt.flow(new C09161(body, null));
    }

    @NotNull
    public final Flow<String> getAllNoticeRead() {
        return FlowKt.flow(new C09171(null));
    }

    @NotNull
    public final Flow<String> getAllRead() {
        return FlowKt.flow(new C09181(null));
    }

    @Nullable
    public final Object getAppGeneralConfig(int i2, int i3, @NotNull Continuation<? super HomeMsg> continuation) {
        YddService yddService2 = yddService;
        if (yddService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("yddService");
            yddService2 = null;
        }
        return yddService2.getAppGeneralConfig(i2, i3, continuation);
    }

    @NotNull
    public final Flow<HomeMsg> getAppGeneralConfigFlow(int type, int application) {
        return FlowKt.flow(new C09191(type, application, null));
    }

    @Nullable
    public final Object getAppHomeList(int i2, @NotNull Continuation<? super List<HomeItem>> continuation) {
        return otherService.getAppHomeList(i2, continuation);
    }

    @NotNull
    public final Flow<HeartData> getAppKnowledgeStatistics(int type, int code, int pageNum, int pageSize) {
        return FlowKt.flow(new C09201(type, code, pageNum, pageSize, null));
    }

    @Nullable
    public final Object getAppTableList(int i2, @NotNull Continuation<? super HomeDaskResult> continuation) {
        return otherService.getAppTableList(i2, continuation);
    }

    @NotNull
    public final Flow<AppVersion> getAppVersion() {
        return FlowKt.flow(new C09211(null));
    }

    @NotNull
    public final Flow<List<HomeMsg>> getAppWarn() {
        return FlowKt.flow(new C09221(null));
    }

    @Nullable
    public final Object getAskTip(int i2, @NotNull Continuation<? super List<String>> continuation) {
        return otherService.getAskTip(i2, continuation);
    }

    @Nullable
    public final Object getAssistCheckCategoryTree(int i2, int i3, @NotNull Continuation<? super List<InquiryCheckInfo>> continuation) {
        return otherService.getAssistCheckCategoryTree(i2, i3, continuation);
    }

    @Nullable
    public final Object getAssistTip(int i2, @NotNull Continuation<? super List<String>> continuation) {
        return otherService.getAssistTip(i2, continuation);
    }

    @Nullable
    public final Object getBodyCheck(@Nullable String str, @Nullable String str2, @NotNull Continuation<? super List<SkillHome>> continuation) {
        YddService yddService2 = yddService;
        if (yddService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("yddService");
            yddService2 = null;
        }
        return yddService2.getBodyCheck(str, str2, continuation);
    }

    @Nullable
    public final Object getBodyCheckTip(int i2, @NotNull Continuation<? super List<String>> continuation) {
        return otherService.getBodyCheckTip(i2, continuation);
    }

    @NotNull
    public final Flow<String> getChatGptUse() {
        return FlowKt.flow(new C09231(null));
    }

    @NotNull
    public final Flow<HomeGuideList> getClinicalGuideline(@NotNull Map<String, Object> query) {
        Intrinsics.checkNotNullParameter(query, "query");
        return FlowKt.flow(new C09241(query, null));
    }

    @NotNull
    public final Flow<HomeClinicalDetail> getClinicalGuidelineDetail(int categoryId) {
        return FlowKt.flow(new C09251(categoryId, null));
    }

    @Nullable
    public final Object getClinicalGuidelineN0Flow(@NotNull Map<String, Object> map, @NotNull Continuation<? super HomeGuideList> continuation) {
        return otherService.getClinicalGuideline(map, continuation);
    }

    @Nullable
    public final Object getConfigWhite(@NotNull Continuation<? super List<AppConfig>> continuation) {
        return otherService.getConfigWhite(continuation);
    }

    @NotNull
    public final Flow<List<AppConfig>> getConfigWhiteF(@NotNull String fUrl) {
        Intrinsics.checkNotNullParameter(fUrl, "fUrl");
        return FlowKt.flow(new C09261(fUrl, null));
    }

    @Nullable
    public final Object getContact(@NotNull Continuation<? super SkillCall> continuation) {
        YddService yddService2 = yddService;
        if (yddService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("yddService");
            yddService2 = null;
        }
        return yddService2.getContact(continuation);
    }

    @Nullable
    public final Object getDepartment(@NotNull Continuation<? super Flow<? extends List<InquiryCheckInfo>>> continuation) {
        return FlowKt.flow(new AnonymousClass2(null));
    }

    @Nullable
    public final Object getDepartment1(@NotNull Continuation<? super List<InquiryCheckInfo>> continuation) {
        return otherService.getDepartment(continuation);
    }

    @NotNull
    public final Flow<List<MeAgencyRow>> getDiagnoseClinicalGuide(int patientId) {
        return FlowKt.flow(new C09271(patientId, null));
    }

    @Nullable
    public final Object getDiagnoseTip(int i2, @NotNull Continuation<? super List<String>> continuation) {
        return otherService.getDiagnoseTip(i2, continuation);
    }

    @Nullable
    public final Object getDictConfigLabel(long j2, @NotNull Continuation<? super List<SkillLabel>> continuation) {
        YddService yddService2 = yddService;
        if (yddService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("yddService");
            yddService2 = null;
        }
        return yddService2.getDictConfigLabel(j2, continuation);
    }

    @NotNull
    public final Flow<TeachRow> getEcommendedVideo(int patientId) {
        return FlowKt.flow(new C09281(patientId, null));
    }

    @NotNull
    public final Flow<HomeTeaching> getEcommendedVideoList(@NotNull Map<String, Object> body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return FlowKt.flow(new C09291(body, null));
    }

    @Nullable
    public final Object getExamByCode(@NotNull String str, @NotNull Continuation<? super ExamItem> continuation) {
        return otherService.getExamByCode(str, continuation);
    }

    @Nullable
    public final Object getExamByUser(int i2, @NotNull String str, @NotNull Continuation<? super ExamItem> continuation) {
        return otherService.getExamByUser(i2, str, continuation);
    }

    @NotNull
    public final Flow<List<String>> getExamHorseLamp() {
        return FlowKt.flow(new C09301(null));
    }

    @Nullable
    public final Object getExamQuestionList(int i2, @NotNull String str, @NotNull String str2, @NotNull Continuation<? super List<ExamQuestion>> continuation) {
        return otherService.getExamQuestionList(i2, str, str2, continuation);
    }

    @NotNull
    public final Flow<FeedBackPageList> getFeedBackPage(@Nullable String categoryId, @Nullable String medicalKnowledgeId, int pageNum, int pageSize) {
        return FlowKt.flow(new C09311(categoryId, medicalKnowledgeId, pageNum, pageSize, null));
    }

    @Nullable
    public final Object getGardeScore(int i2, @NotNull Continuation<? super List<ScoreDetail>> continuation) {
        return otherService.getGardeScore(i2, continuation);
    }

    @Nullable
    public final Object getHomeBanner(@NotNull Continuation<? super List<BannerData>> continuation) {
        YddService yddService2 = yddService;
        if (yddService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("yddService");
            yddService2 = null;
        }
        return yddService2.getHomeBanner(continuation);
    }

    @Nullable
    public final Object getHomeInfoList(int i2, int i3, int i4, @NotNull Continuation<? super HomeConfig> continuation) {
        return otherService.getHomeInfoList(i2, Boxing.boxInt(i3), Boxing.boxInt(i4), continuation);
    }

    @Nullable
    public final Object getHomePatient(@NotNull Continuation<? super List<RowCase>> continuation) {
        return otherService.getHomePatient(continuation);
    }

    @NotNull
    public final Flow<List<String>> getHorseLamp() {
        return FlowKt.flow(new C09321(null));
    }

    @Nullable
    public final Object getInformationTree(@NotNull Continuation<? super List<InquiryCheckInfo>> continuation) {
        return otherService.getInformationTree(continuation);
    }

    @Nullable
    public final Object getIntegralApp(@NotNull Continuation<? super MeProfileIntegral> continuation) {
        YddService yddService2 = yddService;
        if (yddService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("yddService");
            yddService2 = null;
        }
        return yddService2.getIntegralApp(continuation);
    }

    @Nullable
    public final Object getInviterInfo(@NotNull Continuation<? super HomeMsg> continuation) {
        YddService yddService2 = yddService;
        if (yddService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("yddService");
            yddService2 = null;
        }
        return yddService2.getInviterInfo(continuation);
    }

    @NotNull
    public final Flow<HomeAtlasDetails> getKnowledgeAtlasID(int id) {
        return FlowKt.flow(new C09331(id, null));
    }

    @Nullable
    public final Object getKnowledgeDetails(@NotNull String str, int i2, int i3, @NotNull Continuation<? super List<HeartDetail>> continuation) {
        YddService yddService2 = yddService;
        if (yddService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("yddService");
            yddService2 = null;
        }
        return yddService2.getKnowledgeDetails(str, i2, i3, YddUserManager.INSTANCE.getInstance().userId(), continuation);
    }

    @NotNull
    public final Flow<ExamLeaderBoardResult> getLeaderBoard(@Nullable String skillId) {
        return FlowKt.flow(new C09341(skillId, null));
    }

    @NotNull
    public final Flow<String> getMaintain(@NotNull Map<String, Object> map) {
        Intrinsics.checkNotNullParameter(map, "map");
        return FlowKt.flow(new C09351(map, null));
    }

    @Nullable
    public final Object getMedicineDirectionTree(int i2, @NotNull Continuation<? super List<InquiryCheckInfo>> continuation) {
        YddService yddService2 = yddService;
        if (yddService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("yddService");
            yddService2 = null;
        }
        return yddService2.getMedicineDirectionTree(i2, continuation);
    }

    @NotNull
    public final Flow<List<InquiryCheckInfo>> getMedicineSearchList(int pid, int type) {
        return FlowKt.flow(new C09361(pid, type, null));
    }

    @NotNull
    public final Flow<HomeMsgList> getMessageList(int userId, @Nullable Integer pageNum, @Nullable Integer pageSize) {
        return FlowKt.flow(new C09371(userId, pageNum, pageSize, null));
    }

    @NotNull
    public final Flow<HomeMsgList> getNoticeList(@Nullable Integer pageNum, @Nullable Integer pageSize, int releaseStatus) {
        return FlowKt.flow(new C09381(pageNum, pageSize, releaseStatus, null));
    }

    @NotNull
    public final Flow<String> getOrderStr(@NotNull String productInfoId, int all, @NotNull String couponRecordId) {
        Intrinsics.checkNotNullParameter(productInfoId, "productInfoId");
        Intrinsics.checkNotNullParameter(couponRecordId, "couponRecordId");
        return FlowKt.flow(new C09391(productInfoId, all, couponRecordId, null));
    }

    @NotNull
    public final Flow<List<MeAgencyRow>> getOrganzationSearch(@NotNull Map<String, Object> queryMap) {
        Intrinsics.checkNotNullParameter(queryMap, "queryMap");
        return FlowKt.flow(new C09401(queryMap, null));
    }

    @NotNull
    public final Flow<List<InquiryAskInfo>> getPatientAskGetByKeyword(@Nullable String keyword, int patientId, int trainId) {
        return FlowKt.flow(new C09411(keyword, patientId, trainId, null));
    }

    @Nullable
    public final Object getPatientInfoById(int i2, @NotNull Continuation<? super RowCase> continuation) {
        return otherService.getPatientInfoById(i2, continuation);
    }

    @Nullable
    public final Object getPatientNote(int i2, @NotNull Continuation<? super Flow<InquiryNote>> continuation) {
        return FlowKt.flow(new C09422(i2, null));
    }

    @NotNull
    public final Flow<List<InquiryCheckInfo>> getPatientTreeRecord(int patientId, int trainId, int type) {
        return FlowKt.flow(new C09431(patientId, trainId, type, null));
    }

    @Nullable
    public final Object getPatientTreeRecordN0Flow(int i2, int i3, int i4, @NotNull Continuation<? super List<InquiryCheckInfo>> continuation) {
        return otherService.getPatientTreeRecord(i2, i3, i4, continuation);
    }

    @NotNull
    public final Flow<MeProfile> getPersonInfoApp() {
        return FlowKt.flow(new C09441(null));
    }

    @Nullable
    public final Object getPopUserList(@NotNull Continuation<? super List<HomeMsg>> continuation) {
        YddService yddService2 = yddService;
        if (yddService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("yddService");
            yddService2 = null;
        }
        return yddService2.getPopUserList(continuation);
    }

    @Nullable
    public final Object getPostUserBankInfo(int i2, @NotNull Continuation<? super BankReqResult> continuation) {
        YddService yddService2 = yddService;
        if (yddService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("yddService");
            yddService2 = null;
        }
        return yddService2.getPostUserBankInfo(i2, continuation);
    }

    @Nullable
    public final Object getPracticeRecord(@Nullable String str, int i2, @NotNull Continuation<? super SkillHome> continuation) {
        YddService yddService2 = yddService;
        if (yddService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("yddService");
            yddService2 = null;
        }
        return yddService2.getPracticeRecord(str, i2, continuation);
    }

    @Nullable
    public final Object getPracticeRecordExam(@NotNull Map<String, Object> map, @NotNull Continuation<? super String> continuation) {
        YddService yddService2 = yddService;
        if (yddService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("yddService");
            yddService2 = null;
        }
        return yddService2.getPracticeRecordExam(map, continuation);
    }

    @NotNull
    public final Flow<SkillPayWx> getPrepayWx(@NotNull String productInfoId, int all) {
        Intrinsics.checkNotNullParameter(productInfoId, "productInfoId");
        return FlowKt.flow(new C09461(productInfoId, all, null));
    }

    @Nullable
    public final Object getProductInfo(@NotNull String str, @NotNull Continuation<? super SkillHome> continuation) {
        YddService yddService2 = yddService;
        if (yddService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("yddService");
            yddService2 = null;
        }
        return yddService2.getProductInfo(str, continuation);
    }

    @Nullable
    public final Object getProductInfoSkill(@NotNull String str, int i2, @NotNull String str2, boolean z2, int i3, @NotNull Continuation<? super SkillHome> continuation) {
        YddService yddService2 = yddService;
        if (yddService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("yddService");
            yddService2 = null;
        }
        return yddService2.getProductInfoSkill(str, i2, YddUserManager.INSTANCE.getInstance().userId(), str2, z2, i3, continuation);
    }

    @NotNull
    public final Flow<String> getPushCodeForgetPwd(@NotNull String phone) {
        Intrinsics.checkNotNullParameter(phone, "phone");
        return FlowKt.flow(new C09471(phone, null));
    }

    @NotNull
    public final Flow<String> getPushCodeRegister(@NotNull String phone) {
        Intrinsics.checkNotNullParameter(phone, "phone");
        return FlowKt.flow(new C09481(phone, null));
    }

    @Nullable
    public final Object getRandomSkill(@NotNull Continuation<? super SkillHome> continuation) {
        YddService yddService2 = yddService;
        if (yddService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("yddService");
            yddService2 = null;
        }
        return yddService2.getRandomSkill(continuation);
    }

    @NotNull
    public final Flow<String> getReadNoticeId(int msgId) {
        return FlowKt.flow(new C09491(msgId, null));
    }

    @NotNull
    public final Flow<TeachCasebook> getRecommendInfo(int patientId) {
        return FlowKt.flow(new C09501(patientId, null));
    }

    @NotNull
    public final Flow<SkillIntegral> getRecordEquityDetail(@NotNull Map<String, Object> queryMap) {
        Intrinsics.checkNotNullParameter(queryMap, "queryMap");
        return FlowKt.flow(new C09511(queryMap, null));
    }

    @NotNull
    public final Flow<HomeMsgList> getReplyAppList(int source, @NotNull String phone, int pageNum, int pageSize) {
        Intrinsics.checkNotNullParameter(phone, "phone");
        return FlowKt.flow(new C09521(source, phone, pageNum, pageSize, null));
    }

    @Nullable
    public final Object getReportGradeItemScore(int i2, @NotNull Continuation<? super List<ScoreDetail>> continuation) {
        return otherService.getReportGradeItemScore(i2, continuation);
    }

    @Nullable
    public final Object getReportHarmItem(int i2, @NotNull Continuation<? super InquiryAskList> continuation) {
        return otherService.getReportHarmItem(i2, continuation);
    }

    @Nullable
    public final Object getReportHelpItem(int i2, @NotNull Continuation<? super InquiryAskList> continuation) {
        return otherService.getReportHelpItem(i2, continuation);
    }

    @Nullable
    public final Object getReportIdentityDiagnose(int i2, @NotNull Continuation<? super List<InquiryAskList>> continuation) {
        return otherService.getReportIdentityDiagnose(i2, continuation);
    }

    @Nullable
    public final Object getReportIrrelevantItem(int i2, @NotNull Continuation<? super InquiryAskList> continuation) {
        return otherService.getReportIrrelevantItem(i2, continuation);
    }

    @Nullable
    public final Object getReportMainDiagnose(int i2, @NotNull Continuation<? super InquiryAskList> continuation) {
        return otherService.getReportMainDiagnose(i2, continuation);
    }

    @Nullable
    public final Object getReportOtherDiagnose(int i2, @NotNull Continuation<? super List<InquiryAskList>> continuation) {
        return otherService.getReportOtherDiagnose(i2, continuation);
    }

    @NotNull
    public final Flow<String> getRulelatest() {
        return FlowKt.flow(new C09531(null));
    }

    @Nullable
    public final Object getSicknessRelationList(@NotNull Map<String, Object> map, @NotNull Continuation<? super HomeAtlas> continuation) {
        YddService yddService2 = yddService;
        if (yddService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("yddService");
            yddService2 = null;
        }
        return yddService2.getSicknessRelationList(map, continuation);
    }

    @Nullable
    public final Object getSkillBasic(@NotNull String str, int i2, @NotNull Continuation<? super List<SkillHome>> continuation) {
        YddService yddService2 = yddService;
        if (yddService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("yddService");
            yddService2 = null;
        }
        return yddService2.getSkillBasic(str, i2, continuation);
    }

    @Nullable
    public final Object getSkillBasicHome(@NotNull Continuation<? super List<SkillHome>> continuation) {
        YddService yddService2 = yddService;
        if (yddService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("yddService");
            yddService2 = null;
        }
        return yddService2.getSkillBasicHome(continuation);
    }

    @Nullable
    public final Object getSkillBodyHome(@NotNull Continuation<? super List<SkillHome>> continuation) {
        YddService yddService2 = yddService;
        if (yddService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("yddService");
            yddService2 = null;
        }
        return yddService2.getSkillBodyHome(continuation);
    }

    @Nullable
    public final Object getSkillHome(@NotNull Continuation<? super List<SkillHome>> continuation) {
        YddService yddService2 = null;
        if (YddUserManager.INSTANCE.getInstance().userIsLogin()) {
            YddService yddService3 = yddService;
            if (yddService3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("yddService");
            } else {
                yddService2 = yddService3;
            }
            return yddService2.getSkillHome(continuation);
        }
        YddService yddService4 = yddService;
        if (yddService4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("yddService");
        } else {
            yddService2 = yddService4;
        }
        return yddService2.getSkillHomePage(continuation);
    }

    @Nullable
    public final Object getSkillMy(int i2, @NotNull Continuation<? super List<SkillHome>> continuation) {
        YddService yddService2 = yddService;
        if (yddService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("yddService");
            yddService2 = null;
        }
        return yddService2.getSkillMy(i2, continuation);
    }

    @Nullable
    public final Object getSkillRandom(int i2, @NotNull Continuation<? super List<SkillHome>> continuation) {
        YddService yddService2 = yddService;
        if (yddService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("yddService");
            yddService2 = null;
        }
        return yddService2.getSkillRandom(i2, continuation);
    }

    @Nullable
    public final Object getSkillRecord(int i2, @Nullable Integer num, @Nullable Integer num2, int i3, int i4, @NotNull Continuation<? super SkillHomeList> continuation) {
        YddService yddService2 = yddService;
        if (yddService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("yddService");
            yddService2 = null;
        }
        return YddService.DefaultImpls.getSkillRecord$default(yddService2, i2, num, num2, YddUserManager.INSTANCE.getInstance().userId(), i3, i4, 0, 0, continuation, 192, null);
    }

    @NotNull
    public final Flow<Integer> getSkillTimes() {
        return FlowKt.flow(new C09541(null));
    }

    @Nullable
    public final Object getSysTrainRecordGetKeyWord(int i2, int i3, int i4, @NotNull Continuation<? super List<InquiryCheckInfo>> continuation) {
        return otherService.getSysTrainRecordGetKeyWord(i2, i3, i4, continuation);
    }

    @Nullable
    public final Object getSysTrainRecordGetKeyWordSearch(int i2, int i3, @Nullable String str, @NotNull Continuation<? super List<InquiryCheckInfo>> continuation) {
        return otherService.getSysTrainRecordGetKeyWordSearch(i2, i3, str, continuation);
    }

    @Nullable
    public final Object getTeachingCenter(int i2, @NotNull Continuation<? super HomeStudyResult> continuation) {
        return otherService.getTeachingCenter(i2, continuation);
    }

    @NotNull
    public final Flow<HomeTeachingCase> getTeachingList(@NotNull Map<String, Object> body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return FlowKt.flow(new C09551(body, null));
    }

    @Nullable
    public final Object getTeachingResourceCenter(@NotNull Continuation<? super HomeStudyResult> continuation) {
        return otherService.getTeachingResourceCenter(continuation);
    }

    @NotNull
    public final Flow<HomeTeachingClass> getTeachingResourceList(@NotNull Map<String, Object> body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return FlowKt.flow(new C09561(body, null));
    }

    @Nullable
    public final Object getTrainInfoFindById(int i2, @NotNull Continuation<? super ScoreAnalyseTrainInfo> continuation) {
        return otherService.getTrainInfoFindById(i2, continuation);
    }

    @NotNull
    public final Flow<TrainInfoList> getTrainInfoStudyList(@NotNull Map<String, Object> body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return FlowKt.flow(new C09571(body, null));
    }

    @Nullable
    public final Object getTrainInfoStudyList1(@NotNull Map<String, Object> map, @NotNull Continuation<? super TrainInfoList> continuation) {
        return otherService.getTrainInfoStudyList(map, continuation);
    }

    @Nullable
    public final Object getTrainMedical(int i2, @NotNull Continuation<? super Flow<InquiryNote>> continuation) {
        return FlowKt.flow(new C09582(i2, null));
    }

    @NotNull
    public final Flow<InquiryNote> getTrainNoteById(int trainId) {
        return FlowKt.flow(new C09591(trainId, null));
    }

    @NotNull
    public final Flow<InquiryAskList> getTrainRecordListAsk(int trainId) {
        return FlowKt.flow(new C09601(trainId, null));
    }

    @Nullable
    public final Object getTreatmentCategoryTree(int i2, int i3, @NotNull Continuation<? super List<InquiryCheckInfo>> continuation) {
        return otherService.getTreatmentCategoryTree(i2, i3, continuation);
    }

    @Nullable
    public final Object getTreatmentTip(int i2, @NotNull Continuation<? super List<String>> continuation) {
        return otherService.getTreatmentTip(i2, continuation);
    }

    @NotNull
    public final Flow<Integer> getUnreadCount() {
        return FlowKt.flow(new C09611(null));
    }

    @Nullable
    public final Object getUserCoupon(long j2, @NotNull Continuation<? super List<SkillTicket>> continuation) {
        YddService yddService2 = yddService;
        if (yddService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("yddService");
            yddService2 = null;
        }
        return yddService2.getUserCoupon(j2, YddUserManager.INSTANCE.getInstance().userId(), continuation);
    }

    @Nullable
    public final Object getUserExamList(int i2, @NotNull String str, @Nullable Integer num, @Nullable Integer num2, @NotNull Continuation<? super ExamList> continuation) {
        return otherService.getUserExamList(i2, str, num, num2, continuation);
    }

    @Nullable
    public final Object getUserSpecificCoupon(int i2, @NotNull Continuation<? super HomeMsg> continuation) {
        YddService yddService2 = yddService;
        if (yddService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("yddService");
            yddService2 = null;
        }
        return yddService2.getUserSpecificCoupon(i2, continuation);
    }

    @NotNull
    public final Flow<String> getVeriFyMsg(@NotNull String phone, @NotNull String code) {
        Intrinsics.checkNotNullParameter(phone, "phone");
        Intrinsics.checkNotNullParameter(code, "code");
        return FlowKt.flow(new C09621(phone, code, null));
    }

    @NotNull
    public final Flow<String> getVersionU3d() {
        return FlowKt.flow(new C09631(null));
    }

    @NotNull
    public final Flow<List<InquiryAsk>> patientAskGetAskTree(int patientId) {
        return FlowKt.flow(new C09641(patientId, null));
    }

    @NotNull
    public final Flow<List<InquiryAskInfo>> patientAskGetAskTree1(int patientId) {
        return FlowKt.flow(new C09651(patientId, null));
    }

    @NotNull
    public final Flow<List<InquiryBindData>> patientAssistGetBindData(int patientId) {
        return FlowKt.flow(new C09661(patientId, null));
    }

    @NotNull
    public final Flow<RowCase> patientInfoGetById(int id) {
        return FlowKt.flow(new C09671(id, null));
    }

    @NotNull
    public final Flow<TrainInfoList> patientInfoList(@NotNull Map<String, Object> queryMap) {
        Intrinsics.checkNotNullParameter(queryMap, "queryMap");
        return FlowKt.flow(new C09681(queryMap, null));
    }

    @NotNull
    public final Flow<List<RowCase>> patientInfoRandom(@NotNull Map<String, Object> queryMap) {
        Intrinsics.checkNotNullParameter(queryMap, "queryMap");
        return FlowKt.flow(new C09691(queryMap, null));
    }

    @NotNull
    public final Flow<Integer> patientInfoSave(@NotNull InquiryPatientInfoSave body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return FlowKt.flow(new C09701(body, null));
    }

    @NotNull
    public final Flow<AuthLoginResult> postAgentLogin(@NotNull Map<String, String> body, @Nullable Map<String, String> headMap) {
        Intrinsics.checkNotNullParameter(body, "body");
        return FlowKt.flow(new C09711(body, headMap, null));
    }

    @NotNull
    public final Flow<FavoriteSaveReq> postAppFavoriteSave(@NotNull FavoriteSaveReq body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return FlowKt.flow(new C09721(body, null));
    }

    @NotNull
    public final Flow<String> postAppRead(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "str");
        return FlowKt.flow(new C09731(str, null));
    }

    @NotNull
    public final Flow<Boolean> postAppTableSave(@NotNull HomeDaskSaveReq body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return FlowKt.flow(new C09741(body, null));
    }

    @NotNull
    public final Flow<HomeGuideList> postClinicalGuideline(@NotNull HomeGuideReq req) {
        Intrinsics.checkNotNullParameter(req, "req");
        return FlowKt.flow(new C09751(req, null));
    }

    @NotNull
    public final Flow<List<InquiryCheckInfo>> postClinicalGuideline1(@NotNull HomeGuideReq req) {
        Intrinsics.checkNotNullParameter(req, "req");
        return FlowKt.flow(new C09761(req, null));
    }

    @NotNull
    public final Flow<String> postCouponRecord(int type) {
        return FlowKt.flow(new C09771(type, null));
    }

    @NotNull
    public final Flow<ExamCheat> postExamCheat(@NotNull ExamCheatReq body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return FlowKt.flow(new C09781(body, null));
    }

    @NotNull
    public final Flow<Boolean> postFeedBackAdd(@NotNull FeedBackReq body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return FlowKt.flow(new C09791(body, null));
    }

    @Nullable
    public final Object postFileUploadBatch(@NotNull List<MultipartBody.Part> list, @NotNull Continuation<? super List<MeFiles>> continuation) {
        YddService yddService2 = yddService;
        if (yddService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("yddService");
            yddService2 = null;
        }
        return yddService2.postFileUploadBatch(list, continuation);
    }

    @NotNull
    public final Flow<SkillShare> postIntegralAcquire(@NotNull IntegralAcquireReq body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return FlowKt.flow(new C09801(body, null));
    }

    @NotNull
    public final Flow<SkillShare> postIntegralFreeReceive(@NotNull IntegralAcquireReq body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return FlowKt.flow(new C09811(body, null));
    }

    @NotNull
    public final Flow<Boolean> postIntegralUnlock(@NotNull IntegralUnlockReq req) {
        Intrinsics.checkNotNullParameter(req, "req");
        return FlowKt.flow(new C09821(req, null));
    }

    @NotNull
    public final Flow<AuthLoginResult1> postInviteLogin(@NotNull Map<String, String> body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return FlowKt.flow(new C09831(body, null));
    }

    @NotNull
    public final Flow<HomeMsg> postInviterSave() {
        return FlowKt.flow(new C09841(null));
    }

    @Nullable
    public final Object postKnowledgeAtlasQuary(@Body @NotNull HomeAtlasReq homeAtlasReq, @NotNull Continuation<? super List<AtlasRow>> continuation) {
        YddService yddService2 = yddService;
        if (yddService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("yddService");
            yddService2 = null;
        }
        return yddService2.postKnowledgeAtlasQuary(homeAtlasReq, continuation);
    }

    @NotNull
    public final Flow<Boolean> postKnowledgeLearnRecordSave(@NotNull String medicalKnowledgeId) {
        Intrinsics.checkNotNullParameter(medicalKnowledgeId, "medicalKnowledgeId");
        return FlowKt.flow(new C09851(medicalKnowledgeId, null));
    }

    @NotNull
    public final Flow<AuthLoginResult> postOneclickLogin(@NotNull Map<String, String> body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return FlowKt.flow(new C09861(body, null));
    }

    @Nullable
    public final Object postPatientAssistInfo(@NotNull Map<String, Integer> map, @NotNull Continuation<? super ScoreBlood> continuation) {
        return otherService.postPatientAssistInfo(map, continuation);
    }

    @NotNull
    public final Flow<ScoreBlood> postPatientAssistInfoGo(@NotNull Map<String, Integer> req) {
        Intrinsics.checkNotNullParameter(req, "req");
        return FlowKt.flow(new C09871(req, null));
    }

    @Nullable
    public final Object postPatientNoteSave(@NotNull InquirySaveNoteReq inquirySaveNoteReq, @NotNull Continuation<? super Flow<Boolean>> continuation) {
        return FlowKt.flow(new C09882(inquirySaveNoteReq, null));
    }

    @NotNull
    public final Flow<String> postPersonInfo(@NotNull Map<String, String> body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return FlowKt.flow(new C09891(body, null));
    }

    @NotNull
    public final Flow<String> postPersonSubmit(@NotNull HomeMeAuditSubmit req) {
        Intrinsics.checkNotNullParameter(req, "req");
        return FlowKt.flow(new C09901(req, null));
    }

    @Nullable
    public final Object postPointSave(@NotNull PointSaveReq pointSaveReq, @NotNull Continuation<? super Flow<String>> continuation) {
        return FlowKt.flow(new C09912(pointSaveReq, null));
    }

    @NotNull
    public final Flow<String> postPopUserUpdateStatus(@NotNull Map<String, String> body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return FlowKt.flow(new C09921(body, null));
    }

    @NotNull
    public final Flow<String> postRedeemCode(@NotNull String code) {
        Intrinsics.checkNotNullParameter(code, "code");
        return FlowKt.flow(new C09931(code, null));
    }

    @Nullable
    public final Object postReplySave(@NotNull MeReplyReq meReplyReq, @NotNull Continuation<? super String> continuation) {
        YddService yddService2 = yddService;
        if (yddService2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("yddService");
            yddService2 = null;
        }
        return yddService2.postReplySave(meReplyReq, continuation);
    }

    @NotNull
    public final Flow<Integer> postTenantInfoRegister(@Body @NotNull HomeTenantReq req) {
        Intrinsics.checkNotNullParameter(req, "req");
        return FlowKt.flow(new C09941(req, null));
    }

    @Nullable
    public final Object postTrainMedicalSave(@NotNull InquirySaveNoteReq inquirySaveNoteReq, @NotNull Continuation<? super Flow<Integer>> continuation) {
        return FlowKt.flow(new C09952(inquirySaveNoteReq, null));
    }

    @NotNull
    public final Flow<Integer> postTrainNoteSave(@NotNull InquirySaveNoteReq req) {
        Intrinsics.checkNotNullParameter(req, "req");
        return FlowKt.flow(new C09961(req, null));
    }

    @NotNull
    public final Flow<String> postUserBinding(@NotNull LoginWxBindingReq req) {
        Intrinsics.checkNotNullParameter(req, "req");
        return FlowKt.flow(new C09971(req, null));
    }

    @NotNull
    public final Flow<String> postUserReBinding(@NotNull LoginWxReBindingReq req) {
        Intrinsics.checkNotNullParameter(req, "req");
        return FlowKt.flow(new C09981(req, null));
    }

    @Nullable
    public final Object postWxLogin(@NotNull LoginWx loginWx, @NotNull Continuation<? super AuthLoginResult> continuation) {
        return otherService.postWxLogin(loginWx, continuation);
    }

    @NotNull
    public final Flow<String> readMessageList(@NotNull HomeMsgReq body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return FlowKt.flow(new C09991(body, null));
    }

    @Nullable
    public final Object reportAnalyseRadar(int i2, @NotNull Continuation<? super ScoreAnalyseRadar> continuation) {
        return otherService.reportAnalyseRadar(i2, continuation);
    }

    @Nullable
    public final Object reportRatioCorrect(int i2, @NotNull Continuation<? super ScoreAnalyseCorrect> continuation) {
        return otherService.reportRatioCorrect(i2, continuation);
    }

    @Nullable
    public final Object reportRatioMatch(int i2, @NotNull Continuation<? super ScoreAnalyseCorrect> continuation) {
        return otherService.reportRatioMatch(i2, continuation);
    }

    @NotNull
    public final Flow<String> saveExamQuestionResult(@NotNull ExamSaveReq req) {
        Intrinsics.checkNotNullParameter(req, "req");
        return FlowKt.flow(new C10001(req, null));
    }

    @Nullable
    public final Object saveExamQuestionResultN0Flow(@NotNull ExamSaveReq examSaveReq, @NotNull Continuation<? super String> continuation) {
        return otherService.saveExamQuestionResult(examSaveReq, continuation);
    }

    @NotNull
    public final Flow<Integer> startExam(@NotNull ExamUpdateReq req) {
        Intrinsics.checkNotNullParameter(req, "req");
        return FlowKt.flow(new C10011(req, null));
    }

    @Nullable
    public final Object startExam1(@NotNull ExamUpdateReq examUpdateReq, @NotNull Continuation<? super Integer> continuation) {
        return otherService.startExam(examUpdateReq, continuation);
    }

    @Nullable
    public final Object sysAssistCheck(@Nullable String str, @NotNull Continuation<? super List<InquiryCheckInfo>> continuation) {
        return otherService.sysAssistCheck(str, continuation);
    }

    @NotNull
    public final Flow<List<InquiryCheckInfo>> sysAssistCheckGetTree() {
        return FlowKt.flow(new C10021(null));
    }

    @Nullable
    public final Object sysDiseaseList(@NotNull String str, @NotNull String str2, @Nullable Integer num, @Nullable Integer num2, @NotNull Continuation<? super InquiryDiseaseList> continuation) {
        return otherService.sysDiseaseList(str, str2, num, num2, continuation);
    }

    @Nullable
    public final Object sysTreatment(@Nullable String str, @NotNull Continuation<? super List<InquiryCheckInfo>> continuation) {
        return otherService.sysTreatment(str, continuation);
    }

    @NotNull
    public final Flow<List<InquiryCheckInfo>> sysTreatmentGetTree() {
        return FlowKt.flow(new C10031(null));
    }

    @NotNull
    public final Flow<Integer> trainDagnoseSave(@NotNull InquiryDiseaseReq body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return FlowKt.flow(new C10041(body, null));
    }

    @NotNull
    public final Flow<InquiryAskList> trainDiagnoseBasisGetSavedBasis(int trainDiagnoseId) {
        return FlowKt.flow(new C10051(trainDiagnoseId, null));
    }

    @Nullable
    public final Object trainDiagnoseBasisGetSavedBasisNoFlow(int i2, @NotNull Continuation<? super InquiryAskList> continuation) {
        return otherService.trainDiagnoseBasisGetSavedBasis(i2, continuation);
    }

    @NotNull
    public final Flow<Integer> trainDiagnoseBasisSaveBasis(@NotNull InquirySaveBasisReq body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return FlowKt.flow(new C10061(body, null));
    }

    @NotNull
    public final Flow<Integer> trainDiagnoseDelete(int id) {
        return FlowKt.flow(new C10071(id, null));
    }

    @NotNull
    public final Flow<List<RowCase>> trainDiagnoseGetByTrainId(int trainId) {
        return FlowKt.flow(new C10081(trainId, null));
    }

    @NotNull
    public final Flow<Integer> trainDiagnoseUpdate(@NotNull InquiryDiseaseUpdateReq body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return FlowKt.flow(new C10091(body, null));
    }

    @NotNull
    public final Flow<RowCase> trainInfoGetUnCompletee() {
        return FlowKt.flow(new C10101(null));
    }

    @NotNull
    public final Flow<TrainInfoList> trainInfoList(@NotNull Map<String, Object> queryMap) {
        Intrinsics.checkNotNullParameter(queryMap, "queryMap");
        return FlowKt.flow(new C10111(queryMap, null));
    }

    @NotNull
    public final Flow<RowCase> trainInfoUpdateStatus(@NotNull InquiryUpdateStatus body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return FlowKt.flow(new C10121(body, null));
    }

    @NotNull
    public final Flow<Boolean> trainRecordBatchSave(@NotNull InquiryCheckReq body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return FlowKt.flow(new C10131(body, null));
    }

    @NotNull
    public final Flow<InquiryAskList> trainRecordListDoneDetails(@NotNull Map<String, Object> queryMap) {
        Intrinsics.checkNotNullParameter(queryMap, "queryMap");
        return FlowKt.flow(new C10141(queryMap, null));
    }

    @Nullable
    public final Object trainRecordListDoneDetailsNoFlow(@NotNull Map<String, Object> map, @NotNull Continuation<? super InquiryAskList> continuation) {
        return otherService.trainRecordListDoneDetails(map, continuation);
    }

    @NotNull
    public final Flow<Integer> trainRecordSave(@NotNull Map<String, Integer> body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return FlowKt.flow(new C10151(body, null));
    }

    @NotNull
    public final Flow<String> updatePwd(@NotNull Map<String, String> body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return FlowKt.flow(new C10161(body, null));
    }

    @NotNull
    public final Flow<String> uploadImagesHeader(@NotNull MultipartBody.Part iconFile) {
        Intrinsics.checkNotNullParameter(iconFile, "iconFile");
        return FlowKt.flow(new C10171(iconFile, null));
    }

    @NotNull
    public final Flow<String> userAccountCancel(@NotNull Map<String, String> body) {
        Intrinsics.checkNotNullParameter(body, "body");
        return FlowKt.flow(new C10181(body, null));
    }

    @NotNull
    public final Flow<String> getPostUserBankInfo(@NotNull BankReqResult req) {
        Intrinsics.checkNotNullParameter(req, "req");
        return FlowKt.flow(new C09452(req, null));
    }
}
