package splitties.systemservices;

import android.accessibilityservice.AccessibilityService;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AppOpsManager;
import android.app.DownloadManager;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.app.UiModeManager;
import android.app.WallpaperManager;
import android.app.admin.DevicePolicyManager;
import android.app.job.JobScheduler;
import android.app.role.RoleManager;
import android.app.slice.SliceManager;
import android.app.usage.NetworkStatsManager;
import android.app.usage.StorageStatsManager;
import android.app.usage.UsageStatsManager;
import android.appwidget.AppWidgetManager;
import android.bluetooth.BluetoothManager;
import android.companion.CompanionDeviceManager;
import android.content.AppCtxKt;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.RestrictionsManager;
import android.content.pm.CrossProfileApps;
import android.content.pm.LauncherApps;
import android.content.pm.ShortcutManager;
import android.hardware.ConsumerIrManager;
import android.hardware.SensorManager;
import android.hardware.biometrics.BiometricManager;
import android.hardware.camera2.CameraManager;
import android.hardware.display.DisplayManager;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.input.InputManager;
import android.hardware.usb.UsbManager;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaRouter;
import android.media.midi.MidiManager;
import android.media.projection.MediaProjectionManager;
import android.media.session.MediaSessionManager;
import android.media.tv.TvInputManager;
import android.net.ConnectivityManager;
import android.net.IpSecManager;
import android.net.nsd.NsdManager;
import android.net.wifi.WifiManager;
import android.net.wifi.aware.WifiAwareManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.rtt.WifiRttManager;
import android.nfc.NfcManager;
import android.os.BatteryManager;
import android.os.DropBoxManager;
import android.os.HardwarePropertiesManager;
import android.os.PowerManager;
import android.os.UserManager;
import android.os.Vibrator;
import android.os.health.SystemHealthManager;
import android.os.storage.StorageManager;
import android.print.PrintManager;
import android.telecom.TelecomManager;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.euicc.EuiccManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.CaptioningManager;
import android.view.autofill.AutofillManager;
import android.view.contentcapture.ContentCaptureManager;
import android.view.inputmethod.InputMethodManager;
import android.view.textclassifier.TextClassificationManager;
import android.view.textservice.TextServicesManager;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.exifinterface.media.ExifInterface;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.plv.business.model.ppt.PLVPPTAuthentic;
import com.plv.livescenes.linkmic.manager.PLVLinkMicManager;
import com.umeng.analytics.pro.am;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000Ô\u0004\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a!\u0010¥\u0002\u001a\u0003H¦\u0002\"\u0005\b\u0000\u0010¦\u00022\b\u0010§\u0002\u001a\u00030¨\u0002H\u0001¢\u0006\u0003\u0010©\u0002\"\u0012\u0010\u0000\u001a\u00020\u00018Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0003\"\u0012\u0010\u0004\u001a\u00020\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\"\u0012\u0010\b\u001a\u00020\t8Æ\u0002¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b\"\u0012\u0010\f\u001a\u00020\r8Æ\u0002¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f\"\u0012\u0010\u0010\u001a\u00020\u00118Ç\u0002¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013\"\u0012\u0010\u0014\u001a\u00020\u00158Ç\u0002¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017\"\u0012\u0010\u0018\u001a\u00020\u00198Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001b\"\u0012\u0010\u001c\u001a\u00020\u001d8Ç\u0002¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001f\"\u0012\u0010 \u001a\u00020!8Ç\u0002¢\u0006\u0006\u001a\u0004\b\"\u0010#\"\u0012\u0010$\u001a\u00020%8Ç\u0002¢\u0006\u0006\u001a\u0004\b&\u0010'\"\u0012\u0010(\u001a\u00020)8Ç\u0002¢\u0006\u0006\u001a\u0004\b*\u0010+\"\u0012\u0010,\u001a\u00020-8Ç\u0002¢\u0006\u0006\u001a\u0004\b.\u0010/\"\u0012\u00100\u001a\u0002018Ç\u0002¢\u0006\u0006\u001a\u0004\b2\u00103\"\u0012\u00104\u001a\u0002058Ç\u0002¢\u0006\u0006\u001a\u0004\b6\u00107\"\u0012\u00108\u001a\u0002098Æ\u0002¢\u0006\u0006\u001a\u0004\b:\u0010;\"\u0012\u0010<\u001a\u00020=8Ç\u0002¢\u0006\u0006\u001a\u0004\b>\u0010?\"\u0012\u0010@\u001a\u00020A8Æ\u0002¢\u0006\u0006\u001a\u0004\bB\u0010C\"\u0012\u0010D\u001a\u00020E8Ç\u0002¢\u0006\u0006\u001a\u0004\bF\u0010G\"\u0012\u0010H\u001a\u00020I8Ç\u0002¢\u0006\u0006\u001a\u0004\bJ\u0010K\"\u0012\u0010L\u001a\u00020M8Ç\u0002¢\u0006\u0006\u001a\u0004\bN\u0010O\"\u0014\u0010P\u001a\u0004\u0018\u00010Q8Æ\u0002¢\u0006\u0006\u001a\u0004\bR\u0010S\"\u0012\u0010T\u001a\u00020U8Ç\u0002¢\u0006\u0006\u001a\u0004\bV\u0010W\"\u0012\u0010X\u001a\u00020Y8Æ\u0002¢\u0006\u0006\u001a\u0004\bZ\u0010[\"\u0012\u0010\\\u001a\u00020]8Æ\u0002¢\u0006\u0006\u001a\u0004\b^\u0010_\"\u0012\u0010`\u001a\u00020a8Ç\u0002¢\u0006\u0006\u001a\u0004\bb\u0010c\"\u001d\u0010d\u001a\u0004\u0018\u00010e8Ç\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\bf\u0010g\u001a\u0004\bh\u0010i\"\u0012\u0010j\u001a\u00020k8Ç\u0002¢\u0006\u0006\u001a\u0004\bl\u0010m\"\u0012\u0010n\u001a\u00020o8Ç\u0002¢\u0006\u0006\u001a\u0004\bp\u0010q\"\u0012\u0010r\u001a\u00020s8Æ\u0002¢\u0006\u0006\u001a\u0004\bt\u0010u\"\u0012\u0010v\u001a\u00020w8Ç\u0002¢\u0006\u0006\u001a\u0004\bx\u0010y\"\u0012\u0010z\u001a\u00020{8Ç\u0002¢\u0006\u0006\u001a\u0004\b|\u0010}\"\u0014\u0010~\u001a\u00020\u007f8Æ\u0002¢\u0006\b\u001a\u0006\b\u0080\u0001\u0010\u0081\u0001\"\u0016\u0010\u0082\u0001\u001a\u00030\u0083\u00018Ç\u0002¢\u0006\b\u001a\u0006\b\u0084\u0001\u0010\u0085\u0001\"\u0016\u0010\u0086\u0001\u001a\u00030\u0087\u00018Æ\u0002¢\u0006\b\u001a\u0006\b\u0088\u0001\u0010\u0089\u0001\"\u0016\u0010\u008a\u0001\u001a\u00030\u008b\u00018Ç\u0002¢\u0006\b\u001a\u0006\b\u008c\u0001\u0010\u008d\u0001\"\u0016\u0010\u008e\u0001\u001a\u00030\u008f\u00018Ç\u0002¢\u0006\b\u001a\u0006\b\u0090\u0001\u0010\u0091\u0001\"\u0016\u0010\u0092\u0001\u001a\u00030\u0093\u00018Ç\u0002¢\u0006\b\u001a\u0006\b\u0094\u0001\u0010\u0095\u0001\"\u0016\u0010\u0096\u0001\u001a\u00030\u0097\u00018Ç\u0002¢\u0006\b\u001a\u0006\b\u0098\u0001\u0010\u0099\u0001\"\u0016\u0010\u009a\u0001\u001a\u00030\u009b\u00018Ç\u0002¢\u0006\b\u001a\u0006\b\u009c\u0001\u0010\u009d\u0001\"\u0016\u0010\u009e\u0001\u001a\u00030\u009f\u00018Æ\u0002¢\u0006\b\u001a\u0006\b \u0001\u0010¡\u0001\"\u0016\u0010¢\u0001\u001a\u00030£\u00018Æ\u0002¢\u0006\b\u001a\u0006\b¤\u0001\u0010¥\u0001\"\u0016\u0010¦\u0001\u001a\u00030§\u00018Ç\u0002¢\u0006\b\u001a\u0006\b¨\u0001\u0010©\u0001\"\u0016\u0010ª\u0001\u001a\u00030«\u00018Æ\u0002¢\u0006\b\u001a\u0006\b¬\u0001\u0010\u00ad\u0001\"\u0016\u0010®\u0001\u001a\u00030¯\u00018Ç\u0002¢\u0006\b\u001a\u0006\b°\u0001\u0010±\u0001\"\u0016\u0010²\u0001\u001a\u00030³\u00018Ç\u0002¢\u0006\b\u001a\u0006\b´\u0001\u0010µ\u0001\"\u0016\u0010¶\u0001\u001a\u00030·\u00018Ç\u0002¢\u0006\b\u001a\u0006\b¸\u0001\u0010¹\u0001\"\u0016\u0010º\u0001\u001a\u00030»\u00018Æ\u0002¢\u0006\b\u001a\u0006\b¼\u0001\u0010½\u0001\"\u0016\u0010¾\u0001\u001a\u00030¿\u00018Æ\u0002¢\u0006\b\u001a\u0006\bÀ\u0001\u0010Á\u0001\"\u0018\u0010Â\u0001\u001a\u0005\u0018\u00010Ã\u00018Ç\u0002¢\u0006\b\u001a\u0006\bÄ\u0001\u0010Å\u0001\"\u0016\u0010Æ\u0001\u001a\u00030Ç\u00018Ç\u0002¢\u0006\b\u001a\u0006\bÈ\u0001\u0010É\u0001\"\u0016\u0010Ê\u0001\u001a\u00030Ë\u00018Æ\u0002¢\u0006\b\u001a\u0006\bÌ\u0001\u0010Í\u0001\"\u0016\u0010Î\u0001\u001a\u00030Ï\u00018Ç\u0002¢\u0006\b\u001a\u0006\bÐ\u0001\u0010Ñ\u0001\"\u0016\u0010Ò\u0001\u001a\u00030Ó\u00018Ç\u0002¢\u0006\b\u001a\u0006\bÔ\u0001\u0010Õ\u0001\"\u0016\u0010Ö\u0001\u001a\u00030×\u00018Ç\u0002¢\u0006\b\u001a\u0006\bØ\u0001\u0010Ù\u0001\"\u0016\u0010Ú\u0001\u001a\u00030Û\u00018Ç\u0002¢\u0006\b\u001a\u0006\bÜ\u0001\u0010Ý\u0001\"\u0016\u0010Þ\u0001\u001a\u00030ß\u00018Æ\u0002¢\u0006\b\u001a\u0006\bà\u0001\u0010á\u0001\"\u0016\u0010â\u0001\u001a\u00030ã\u00018Ç\u0002¢\u0006\b\u001a\u0006\bä\u0001\u0010å\u0001\"\u0016\u0010æ\u0001\u001a\u00030ç\u00018Æ\u0002¢\u0006\b\u001a\u0006\bè\u0001\u0010é\u0001\"\u0016\u0010ê\u0001\u001a\u00030ë\u00018Ç\u0002¢\u0006\b\u001a\u0006\bì\u0001\u0010í\u0001\"\u0016\u0010î\u0001\u001a\u00030ï\u00018Æ\u0002¢\u0006\b\u001a\u0006\bð\u0001\u0010ñ\u0001\"\u0016\u0010ò\u0001\u001a\u00030ó\u00018Ç\u0002¢\u0006\b\u001a\u0006\bô\u0001\u0010õ\u0001\"\u0018\u0010ö\u0001\u001a\u0005\u0018\u00010÷\u00018Æ\u0002¢\u0006\b\u001a\u0006\bø\u0001\u0010ù\u0001\"\u0016\u0010ú\u0001\u001a\u00030û\u00018Ç\u0002¢\u0006\b\u001a\u0006\bü\u0001\u0010ý\u0001\"\u0016\u0010þ\u0001\u001a\u00030ÿ\u00018Æ\u0002¢\u0006\b\u001a\u0006\b\u0080\u0002\u0010\u0081\u0002\"\u0018\u0010\u0082\u0002\u001a\u0005\u0018\u00010\u0083\u00028Æ\u0002¢\u0006\b\u001a\u0006\b\u0084\u0002\u0010\u0085\u0002\"\u0018\u0010\u0086\u0002\u001a\u0005\u0018\u00010\u0087\u00028Ç\u0002¢\u0006\b\u001a\u0006\b\u0088\u0002\u0010\u0089\u0002\"\u0018\u0010\u008a\u0002\u001a\u0005\u0018\u00010\u008b\u00028Ç\u0002¢\u0006\b\u001a\u0006\b\u008c\u0002\u0010\u008d\u0002\"\u0018\u0010\u008e\u0002\u001a\u0005\u0018\u00010\u008f\u00028Æ\u0002¢\u0006\b\u001a\u0006\b\u0090\u0002\u0010\u0091\u0002\"\u0016\u0010\u0092\u0002\u001a\u00030\u0093\u00028Ç\u0002¢\u0006\b\u001a\u0006\b\u0094\u0002\u0010\u0095\u0002\"\u0016\u0010\u0096\u0002\u001a\u00030\u0097\u00028Æ\u0002¢\u0006\b\u001a\u0006\b\u0098\u0002\u0010\u0099\u0002\"\u001b\u0010\u009a\u0002\u001a\u00030\u009b\u0002*\u00030\u009c\u00028Æ\u0002¢\u0006\b\u001a\u0006\b\u009d\u0002\u0010\u009e\u0002\"\u001b\u0010\u009a\u0002\u001a\u00030\u009b\u0002*\u00030\u009f\u00028Æ\u0002¢\u0006\b\u001a\u0006\b\u009d\u0002\u0010 \u0002\"\u001b\u0010\u0096\u0002\u001a\u00030\u0097\u0002*\u00030¡\u00028Æ\u0002¢\u0006\b\u001a\u0006\b\u0098\u0002\u0010¢\u0002\"\u001b\u0010\u0096\u0002\u001a\u00030\u0097\u0002*\u00030\u009c\u00028Æ\u0002¢\u0006\b\u001a\u0006\b\u0098\u0002\u0010£\u0002\"\u001b\u0010\u0096\u0002\u001a\u00030\u0097\u0002*\u00030\u009f\u00028Æ\u0002¢\u0006\b\u001a\u0006\b\u0098\u0002\u0010¤\u0002¨\u0006ª\u0002"}, d2 = {"accessibilityManager", "Landroid/view/accessibility/AccessibilityManager;", "getAccessibilityManager", "()Landroid/view/accessibility/AccessibilityManager;", "accountManager", "Landroid/accounts/AccountManager;", "getAccountManager", "()Landroid/accounts/AccountManager;", "activityManager", "Landroid/app/ActivityManager;", "getActivityManager", "()Landroid/app/ActivityManager;", "alarmManager", "Landroid/app/AlarmManager;", "getAlarmManager", "()Landroid/app/AlarmManager;", "appOpsManager", "Landroid/app/AppOpsManager;", "getAppOpsManager", "()Landroid/app/AppOpsManager;", "appWidgetManager", "Landroid/appwidget/AppWidgetManager;", "getAppWidgetManager", "()Landroid/appwidget/AppWidgetManager;", "audioManager", "Landroid/media/AudioManager;", "getAudioManager", "()Landroid/media/AudioManager;", "autofillManager", "Landroid/view/autofill/AutofillManager;", "getAutofillManager", "()Landroid/view/autofill/AutofillManager;", "batteryManager", "Landroid/os/BatteryManager;", "getBatteryManager", "()Landroid/os/BatteryManager;", "biometricManager", "Landroid/hardware/biometrics/BiometricManager;", "getBiometricManager", "()Landroid/hardware/biometrics/BiometricManager;", "bluetoothManager", "Landroid/bluetooth/BluetoothManager;", "getBluetoothManager", "()Landroid/bluetooth/BluetoothManager;", "cameraManager", "Landroid/hardware/camera2/CameraManager;", "getCameraManager", "()Landroid/hardware/camera2/CameraManager;", "captioningManager", "Landroid/view/accessibility/CaptioningManager;", "getCaptioningManager", "()Landroid/view/accessibility/CaptioningManager;", "carrierConfigManager", "Landroid/telephony/CarrierConfigManager;", "getCarrierConfigManager", "()Landroid/telephony/CarrierConfigManager;", "clipboardManager", "Landroid/content/ClipboardManager;", "getClipboardManager", "()Landroid/content/ClipboardManager;", "companionDeviceManager", "Landroid/companion/CompanionDeviceManager;", "getCompanionDeviceManager", "()Landroid/companion/CompanionDeviceManager;", "connectivityManager", "Landroid/net/ConnectivityManager;", "getConnectivityManager", "()Landroid/net/ConnectivityManager;", "consumerIrManager", "Landroid/hardware/ConsumerIrManager;", "getConsumerIrManager", "()Landroid/hardware/ConsumerIrManager;", "contentCaptureManager", "Landroid/view/contentcapture/ContentCaptureManager;", "getContentCaptureManager", "()Landroid/view/contentcapture/ContentCaptureManager;", "crossProfileApps", "Landroid/content/pm/CrossProfileApps;", "getCrossProfileApps", "()Landroid/content/pm/CrossProfileApps;", "devicePolicyManager", "Landroid/app/admin/DevicePolicyManager;", "getDevicePolicyManager", "()Landroid/app/admin/DevicePolicyManager;", "displayManager", "Landroid/hardware/display/DisplayManager;", "getDisplayManager", "()Landroid/hardware/display/DisplayManager;", "downloadManager", "Landroid/app/DownloadManager;", "getDownloadManager", "()Landroid/app/DownloadManager;", "dropBoxManager", "Landroid/os/DropBoxManager;", "getDropBoxManager", "()Landroid/os/DropBoxManager;", "euiccManager", "Landroid/telephony/euicc/EuiccManager;", "getEuiccManager", "()Landroid/telephony/euicc/EuiccManager;", "fingerPrintManager", "Landroid/hardware/fingerprint/FingerprintManager;", "getFingerPrintManager$annotations", "()V", "getFingerPrintManager", "()Landroid/hardware/fingerprint/FingerprintManager;", "hardwarePropertiesManager", "Landroid/os/HardwarePropertiesManager;", "getHardwarePropertiesManager", "()Landroid/os/HardwarePropertiesManager;", "inputManager", "Landroid/hardware/input/InputManager;", "getInputManager", "()Landroid/hardware/input/InputManager;", "inputMethodManager", "Landroid/view/inputmethod/InputMethodManager;", "getInputMethodManager", "()Landroid/view/inputmethod/InputMethodManager;", "ipSecManager", "Landroid/net/IpSecManager;", "getIpSecManager", "()Landroid/net/IpSecManager;", "jobScheduler", "Landroid/app/job/JobScheduler;", "getJobScheduler", "()Landroid/app/job/JobScheduler;", "keyguardManager", "Landroid/app/KeyguardManager;", "getKeyguardManager", "()Landroid/app/KeyguardManager;", "launcherApps", "Landroid/content/pm/LauncherApps;", "getLauncherApps", "()Landroid/content/pm/LauncherApps;", "locationManager", "Landroid/location/LocationManager;", "getLocationManager", "()Landroid/location/LocationManager;", "mediaProjectionManager", "Landroid/media/projection/MediaProjectionManager;", "getMediaProjectionManager", "()Landroid/media/projection/MediaProjectionManager;", "mediaRouter", "Landroid/media/MediaRouter;", "getMediaRouter", "()Landroid/media/MediaRouter;", "mediaSessionManager", "Landroid/media/session/MediaSessionManager;", "getMediaSessionManager", "()Landroid/media/session/MediaSessionManager;", "midiManager", "Landroid/media/midi/MidiManager;", "getMidiManager", "()Landroid/media/midi/MidiManager;", "networkStatsManager", "Landroid/app/usage/NetworkStatsManager;", "getNetworkStatsManager", "()Landroid/app/usage/NetworkStatsManager;", "nfcManager", "Landroid/nfc/NfcManager;", "getNfcManager", "()Landroid/nfc/NfcManager;", "notificationManager", "Landroid/app/NotificationManager;", "getNotificationManager", "()Landroid/app/NotificationManager;", "nsdManager", "Landroid/net/nsd/NsdManager;", "getNsdManager", "()Landroid/net/nsd/NsdManager;", "powerManager", "Landroid/os/PowerManager;", "getPowerManager", "()Landroid/os/PowerManager;", "printManager", "Landroid/print/PrintManager;", "getPrintManager", "()Landroid/print/PrintManager;", "restrictionsManager", "Landroid/content/RestrictionsManager;", "getRestrictionsManager", "()Landroid/content/RestrictionsManager;", "roleManager", "Landroid/app/role/RoleManager;", "getRoleManager", "()Landroid/app/role/RoleManager;", "searchManager", "Landroid/app/SearchManager;", "getSearchManager", "()Landroid/app/SearchManager;", "sensorManager", "Landroid/hardware/SensorManager;", "getSensorManager", "()Landroid/hardware/SensorManager;", "shortcutManager", "Landroid/content/pm/ShortcutManager;", "getShortcutManager", "()Landroid/content/pm/ShortcutManager;", "sliceManager", "Landroid/app/slice/SliceManager;", "getSliceManager", "()Landroid/app/slice/SliceManager;", "storageManager", "Landroid/os/storage/StorageManager;", "getStorageManager", "()Landroid/os/storage/StorageManager;", "storageStatsManager", "Landroid/app/usage/StorageStatsManager;", "getStorageStatsManager", "()Landroid/app/usage/StorageStatsManager;", "subscriptionManager", "Landroid/telephony/SubscriptionManager;", "getSubscriptionManager", "()Landroid/telephony/SubscriptionManager;", "systemHealthManager", "Landroid/os/health/SystemHealthManager;", "getSystemHealthManager", "()Landroid/os/health/SystemHealthManager;", "telecomManager", "Landroid/telecom/TelecomManager;", "getTelecomManager", "()Landroid/telecom/TelecomManager;", "telephonyManager", "Landroid/telephony/TelephonyManager;", "getTelephonyManager", "()Landroid/telephony/TelephonyManager;", "textClassificationManager", "Landroid/view/textclassifier/TextClassificationManager;", "getTextClassificationManager", "()Landroid/view/textclassifier/TextClassificationManager;", "textServicesManager", "Landroid/view/textservice/TextServicesManager;", "getTextServicesManager", "()Landroid/view/textservice/TextServicesManager;", "tvInputManager", "Landroid/media/tv/TvInputManager;", "getTvInputManager", "()Landroid/media/tv/TvInputManager;", "uiModeManager", "Landroid/app/UiModeManager;", "getUiModeManager", "()Landroid/app/UiModeManager;", "usageStatsManager", "Landroid/app/usage/UsageStatsManager;", "getUsageStatsManager", "()Landroid/app/usage/UsageStatsManager;", "usbManager", "Landroid/hardware/usb/UsbManager;", "getUsbManager", "()Landroid/hardware/usb/UsbManager;", "userManager", "Landroid/os/UserManager;", "getUserManager", "()Landroid/os/UserManager;", "vibrator", "Landroid/os/Vibrator;", "getVibrator", "()Landroid/os/Vibrator;", "wallpaperManager", "Landroid/app/WallpaperManager;", "getWallpaperManager", "()Landroid/app/WallpaperManager;", "wifiAwareManager", "Landroid/net/wifi/aware/WifiAwareManager;", "getWifiAwareManager", "()Landroid/net/wifi/aware/WifiAwareManager;", "wifiManager", "Landroid/net/wifi/WifiManager;", "getWifiManager", "()Landroid/net/wifi/WifiManager;", "wifiP2pManager", "Landroid/net/wifi/p2p/WifiP2pManager;", "getWifiP2pManager", "()Landroid/net/wifi/p2p/WifiP2pManager;", "wifiRttManager", "Landroid/net/wifi/rtt/WifiRttManager;", "getWifiRttManager", "()Landroid/net/wifi/rtt/WifiRttManager;", "windowManager", "Landroid/view/WindowManager;", "getWindowManager", "()Landroid/view/WindowManager;", "layoutInflater", "Landroid/view/LayoutInflater;", "Landroid/content/Context;", "getLayoutInflater", "(Landroid/content/Context;)Landroid/view/LayoutInflater;", "Landroid/view/View;", "(Landroid/view/View;)Landroid/view/LayoutInflater;", "Landroid/accessibilityservice/AccessibilityService;", "(Landroid/accessibilityservice/AccessibilityService;)Landroid/view/WindowManager;", "(Landroid/content/Context;)Landroid/view/WindowManager;", "(Landroid/view/View;)Landroid/view/WindowManager;", "getSystemService", ExifInterface.GPS_DIRECTION_TRUE, "name", "", "(Ljava/lang/String;)Ljava/lang/Object;", "splitties-systemservices_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes9.dex */
public final class SystemServicesKt {
    @NotNull
    public static final AccessibilityManager getAccessibilityManager() {
        return (AccessibilityManager) getSystemService("accessibility");
    }

    @NotNull
    public static final AccountManager getAccountManager() {
        return (AccountManager) getSystemService("account");
    }

    @NotNull
    public static final ActivityManager getActivityManager() {
        return (ActivityManager) getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
    }

    @NotNull
    public static final AlarmManager getAlarmManager() {
        return (AlarmManager) getSystemService(NotificationCompat.CATEGORY_ALARM);
    }

    @RequiresApi(19)
    @NotNull
    public static final AppOpsManager getAppOpsManager() {
        return (AppOpsManager) getSystemService("appops");
    }

    @RequiresApi(21)
    @NotNull
    public static final AppWidgetManager getAppWidgetManager() {
        return (AppWidgetManager) getSystemService("appwidget");
    }

    @NotNull
    public static final AudioManager getAudioManager() {
        return (AudioManager) getSystemService("audio");
    }

    @RequiresApi(26)
    @NotNull
    public static final AutofillManager getAutofillManager() {
        Object systemService = AppCtxKt.getAppCtx().getSystemService((Class<Object>) AutofillManager.class);
        Intrinsics.checkNotNullExpressionValue(systemService, "appCtx.getSystemService(AutofillManager::class.java)");
        return (AutofillManager) systemService;
    }

    @RequiresApi(21)
    @NotNull
    public static final BatteryManager getBatteryManager() {
        return (BatteryManager) getSystemService("batterymanager");
    }

    @RequiresApi(29)
    @NotNull
    public static final BiometricManager getBiometricManager() {
        return (BiometricManager) getSystemService("biometric");
    }

    @RequiresApi(18)
    @NotNull
    public static final BluetoothManager getBluetoothManager() {
        return (BluetoothManager) getSystemService("bluetooth");
    }

    @RequiresApi(21)
    @NotNull
    public static final CameraManager getCameraManager() {
        return (CameraManager) getSystemService(PLVPPTAuthentic.PermissionType.CAMERA);
    }

    @RequiresApi(19)
    @NotNull
    public static final CaptioningManager getCaptioningManager() {
        return (CaptioningManager) getSystemService("captioning");
    }

    @RequiresApi(23)
    @NotNull
    public static final CarrierConfigManager getCarrierConfigManager() {
        return (CarrierConfigManager) getSystemService("carrier_config");
    }

    @NotNull
    public static final ClipboardManager getClipboardManager() {
        return (ClipboardManager) getSystemService("clipboard");
    }

    @RequiresApi(26)
    @NotNull
    public static final CompanionDeviceManager getCompanionDeviceManager() {
        return (CompanionDeviceManager) getSystemService("companiondevice");
    }

    @NotNull
    public static final ConnectivityManager getConnectivityManager() {
        return (ConnectivityManager) getSystemService("connectivity");
    }

    @RequiresApi(19)
    @NotNull
    public static final ConsumerIrManager getConsumerIrManager() {
        return (ConsumerIrManager) getSystemService("consumer_ir");
    }

    @RequiresApi(29)
    @NotNull
    public static final ContentCaptureManager getContentCaptureManager() {
        Object systemService = AppCtxKt.getAppCtx().getSystemService((Class<Object>) ContentCaptureManager.class);
        Intrinsics.checkNotNullExpressionValue(systemService, "appCtx.getSystemService(ContentCaptureManager::class.java)");
        return (ContentCaptureManager) systemService;
    }

    @RequiresApi(28)
    @NotNull
    public static final CrossProfileApps getCrossProfileApps() {
        return (CrossProfileApps) getSystemService("crossprofileapps");
    }

    @Nullable
    public static final DevicePolicyManager getDevicePolicyManager() {
        return (DevicePolicyManager) getSystemService("device_policy");
    }

    @RequiresApi(17)
    @NotNull
    public static final DisplayManager getDisplayManager() {
        return (DisplayManager) getSystemService("display");
    }

    @NotNull
    public static final DownloadManager getDownloadManager() {
        return (DownloadManager) getSystemService(AliyunLogCommon.SubModule.download);
    }

    @NotNull
    public static final DropBoxManager getDropBoxManager() {
        return (DropBoxManager) getSystemService("dropbox");
    }

    @RequiresApi(28)
    @NotNull
    public static final EuiccManager getEuiccManager() {
        return (EuiccManager) getSystemService("euicc");
    }

    @RequiresApi(23)
    @Nullable
    public static final FingerprintManager getFingerPrintManager() {
        return (FingerprintManager) getSystemService("fingerprint");
    }

    @Deprecated(message = "Use android.hardware.biometrics.BiometricPrompt instead (back-ported into JetPack).")
    public static /* synthetic */ void getFingerPrintManager$annotations() {
    }

    @RequiresApi(24)
    @NotNull
    public static final HardwarePropertiesManager getHardwarePropertiesManager() {
        return (HardwarePropertiesManager) getSystemService("hardware_properties");
    }

    @RequiresApi(16)
    @NotNull
    public static final InputManager getInputManager() {
        return (InputManager) getSystemService("input");
    }

    @NotNull
    public static final InputMethodManager getInputMethodManager() {
        return (InputMethodManager) getSystemService("input_method");
    }

    @RequiresApi(28)
    @NotNull
    public static final IpSecManager getIpSecManager() {
        return (IpSecManager) getSystemService("ipsec");
    }

    @RequiresApi(21)
    @NotNull
    public static final JobScheduler getJobScheduler() {
        return (JobScheduler) getSystemService("jobscheduler");
    }

    @NotNull
    public static final KeyguardManager getKeyguardManager() {
        return (KeyguardManager) getSystemService("keyguard");
    }

    @RequiresApi(21)
    @NotNull
    public static final LauncherApps getLauncherApps() {
        return (LauncherApps) getSystemService("launcherapps");
    }

    @NotNull
    public static final LayoutInflater getLayoutInflater(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Object systemService = context.getSystemService("layout_inflater");
        if (systemService != null) {
            return (LayoutInflater) systemService;
        }
        throw new NullPointerException("null cannot be cast to non-null type android.view.LayoutInflater");
    }

    @NotNull
    public static final LocationManager getLocationManager() {
        return (LocationManager) getSystemService("location");
    }

    @RequiresApi(21)
    @NotNull
    public static final MediaProjectionManager getMediaProjectionManager() {
        return (MediaProjectionManager) getSystemService("media_projection");
    }

    @RequiresApi(16)
    @NotNull
    public static final MediaRouter getMediaRouter() {
        return (MediaRouter) getSystemService("media_router");
    }

    @RequiresApi(21)
    @NotNull
    public static final MediaSessionManager getMediaSessionManager() {
        return (MediaSessionManager) getSystemService("media_session");
    }

    @RequiresApi(23)
    @NotNull
    public static final MidiManager getMidiManager() {
        return (MidiManager) getSystemService("midi");
    }

    @RequiresApi(23)
    @NotNull
    public static final NetworkStatsManager getNetworkStatsManager() {
        return (NetworkStatsManager) getSystemService("netstats");
    }

    @NotNull
    public static final NfcManager getNfcManager() {
        return (NfcManager) getSystemService("nfc");
    }

    @NotNull
    public static final NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(RemoteMessageConst.NOTIFICATION);
    }

    @RequiresApi(16)
    @NotNull
    public static final NsdManager getNsdManager() {
        return (NsdManager) getSystemService("servicediscovery");
    }

    @NotNull
    public static final PowerManager getPowerManager() {
        return (PowerManager) getSystemService("power");
    }

    @RequiresApi(19)
    @NotNull
    public static final PrintManager getPrintManager() {
        return (PrintManager) getSystemService("print");
    }

    @RequiresApi(21)
    @NotNull
    public static final RestrictionsManager getRestrictionsManager() {
        return (RestrictionsManager) getSystemService("restrictions");
    }

    @RequiresApi(29)
    @NotNull
    public static final RoleManager getRoleManager() {
        return (RoleManager) getSystemService("role");
    }

    @NotNull
    public static final SearchManager getSearchManager() {
        return (SearchManager) getSystemService("search");
    }

    @NotNull
    public static final SensorManager getSensorManager() {
        return (SensorManager) getSystemService(am.ac);
    }

    @RequiresApi(25)
    @Nullable
    public static final ShortcutManager getShortcutManager() {
        return (ShortcutManager) getSystemService("shortcut");
    }

    @RequiresApi(28)
    @NotNull
    public static final SliceManager getSliceManager() {
        Object systemService = AppCtxKt.getAppCtx().getSystemService((Class<Object>) SliceManager.class);
        Intrinsics.checkNotNullExpressionValue(systemService, "appCtx.getSystemService(SliceManager::class.java)");
        return (SliceManager) systemService;
    }

    @NotNull
    public static final StorageManager getStorageManager() {
        return (StorageManager) getSystemService("storage");
    }

    @RequiresApi(26)
    @NotNull
    public static final StorageStatsManager getStorageStatsManager() {
        return (StorageStatsManager) getSystemService("storagestats");
    }

    @RequiresApi(22)
    @NotNull
    public static final SubscriptionManager getSubscriptionManager() {
        return (SubscriptionManager) getSystemService("telephony_subscription_service");
    }

    @RequiresApi(24)
    @NotNull
    public static final SystemHealthManager getSystemHealthManager() {
        return (SystemHealthManager) getSystemService("systemhealth");
    }

    @PublishedApi
    public static final <T> T getSystemService(@NotNull String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return (T) AppCtxKt.getAppCtx().getSystemService(name);
    }

    @RequiresApi(21)
    @NotNull
    public static final TelecomManager getTelecomManager() {
        return (TelecomManager) getSystemService("telecom");
    }

    @NotNull
    public static final TelephonyManager getTelephonyManager() {
        return (TelephonyManager) getSystemService(AliyunLogCommon.TERMINAL_TYPE);
    }

    @RequiresApi(26)
    @NotNull
    public static final TextClassificationManager getTextClassificationManager() {
        return (TextClassificationManager) getSystemService("textclassification");
    }

    @NotNull
    public static final TextServicesManager getTextServicesManager() {
        return (TextServicesManager) getSystemService("textservices");
    }

    @RequiresApi(21)
    @NotNull
    public static final TvInputManager getTvInputManager() {
        return (TvInputManager) getSystemService("tv_input");
    }

    @NotNull
    public static final UiModeManager getUiModeManager() {
        return (UiModeManager) getSystemService("uimode");
    }

    @RequiresApi(22)
    @NotNull
    public static final UsageStatsManager getUsageStatsManager() {
        return (UsageStatsManager) getSystemService("usagestats");
    }

    @Nullable
    public static final UsbManager getUsbManager() {
        return (UsbManager) getSystemService("usb");
    }

    @RequiresApi(17)
    @NotNull
    public static final UserManager getUserManager() {
        return (UserManager) getSystemService(PLVLinkMicManager.USER);
    }

    @NotNull
    public static final Vibrator getVibrator() {
        return (Vibrator) getSystemService("vibrator");
    }

    @Nullable
    public static final WallpaperManager getWallpaperManager() {
        return (WallpaperManager) getSystemService("wallpaper");
    }

    @RequiresApi(26)
    @Nullable
    public static final WifiAwareManager getWifiAwareManager() {
        return (WifiAwareManager) getSystemService("wifiaware");
    }

    @SuppressLint({"WifiManagerLeak"})
    @Nullable
    public static final WifiManager getWifiManager() {
        return (WifiManager) getSystemService("wifi");
    }

    @Nullable
    public static final WifiP2pManager getWifiP2pManager() {
        return (WifiP2pManager) getSystemService("wifip2p");
    }

    @RequiresApi(28)
    @NotNull
    public static final WifiRttManager getWifiRttManager() {
        return (WifiRttManager) getSystemService("wifirtt");
    }

    @NotNull
    public static final WindowManager getWindowManager(@NotNull AccessibilityService accessibilityService) {
        Intrinsics.checkNotNullParameter(accessibilityService, "<this>");
        Object systemService = accessibilityService.getSystemService("window");
        if (systemService != null) {
            return (WindowManager) systemService;
        }
        throw new NullPointerException("null cannot be cast to non-null type android.view.WindowManager");
    }

    @NotNull
    public static final LayoutInflater getLayoutInflater(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        Object systemService = context.getSystemService("layout_inflater");
        if (systemService != null) {
            return (LayoutInflater) systemService;
        }
        throw new NullPointerException("null cannot be cast to non-null type android.view.LayoutInflater");
    }

    @NotNull
    public static final WindowManager getWindowManager(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        Object systemService = context.getSystemService("window");
        if (systemService != null) {
            return (WindowManager) systemService;
        }
        throw new NullPointerException("null cannot be cast to non-null type android.view.WindowManager");
    }

    @NotNull
    public static final WindowManager getWindowManager(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        Object systemService = context.getSystemService("window");
        if (systemService != null) {
            return (WindowManager) systemService;
        }
        throw new NullPointerException("null cannot be cast to non-null type android.view.WindowManager");
    }

    @NotNull
    public static final WindowManager getWindowManager() {
        return (WindowManager) getSystemService("window");
    }
}
