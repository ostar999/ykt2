package com.tencent.tbs.reader;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;
import com.google.android.exoplayer2.audio.SilenceSkippingAudioProcessor;
import com.psychiatrygarden.utils.Constants;
import com.tencent.tbs.logger.f;
import com.tencent.tbs.one.TBSOneCallback;
import com.tencent.tbs.one.TBSOneComponent;
import com.tencent.tbs.one.TBSOneConfigurationKeys;
import com.tencent.tbs.one.TBSOneManager;
import com.tencent.tbs.reader.ITbsReader;
import com.tencent.tbs.reader.external.ITbsReaderAtom;
import java.util.HashMap;
import java.util.LinkedHashMap;

/* loaded from: classes6.dex */
public class TbsFileInterfaceImpl {
    public static int FILE_READER_WINDOW_TYPE_DEFAULT = 0;
    public static int FILE_READER_WINDOW_TYPE_VIEW = 2;
    public static TbsFileInterfaceImpl stbsFileImpl;
    public e mReaderManager;

    public TbsFileInterfaceImpl() {
        this.mReaderManager = null;
        this.mReaderManager = new e();
    }

    public static boolean canOpenFileExt(String str) {
        return c.d().a(3, str);
    }

    public static void cleanAllFileRecord(Context context) {
        c.d().a(context);
    }

    public static boolean fileEnginePreCheck(Context context) {
        return c.d().c(context);
    }

    public static boolean fileEnginePreLoad(Context context, TBSOneCallback tBSOneCallback, boolean z2) {
        return c.d().a(context, tBSOneCallback, z2);
    }

    public static TbsFileInterfaceImpl getInstance() {
        if (stbsFileImpl == null) {
            stbsFileImpl = new TbsFileInterfaceImpl();
        }
        return stbsFileImpl;
    }

    public static String getLicenseToken(Context context) {
        return c.d().d(context);
    }

    public static int getVersionCode() {
        return c.d().f22279d;
    }

    public static String getVersionName() {
        return c.d().f22278c;
    }

    public static int initEngine(Context context) throws NumberFormatException {
        TBSOneComponent tBSOneComponentLoadComponentSync;
        int i2;
        c cVarD = c.d();
        if (cVarD.f22276a != null) {
            return 0;
        }
        if (cVarD.a(cVarD.a())) {
            TBSOneManager.setNeedReportEvent(false);
        }
        String strB = cVarD.b();
        if (!TextUtils.isEmpty(strB)) {
            f.a(strB);
        }
        TBSOneManager defaultInstance = TBSOneManager.getDefaultInstance(context);
        defaultInstance.setPolicy(cVarD.a());
        try {
            Bundle bundle = new Bundle();
            bundle.putBoolean(TBSOneConfigurationKeys.IGNORE_WIFI_STATE, true);
            bundle.putBoolean(TBSOneConfigurationKeys.IGNORE_FLOW_CONTROL, true);
            if (!defaultInstance.isComponentInstalled("file")) {
                bundle.putBoolean(TBSOneConfigurationKeys.IGNORE_FREQUENCY_LIMITATION, true);
            }
            tBSOneComponentLoadComponentSync = defaultInstance.loadComponentSync("file", bundle, SilenceSkippingAudioProcessor.DEFAULT_PADDING_SILENCE_US);
        } catch (Throwable th) {
            th.printStackTrace();
            tBSOneComponentLoadComponentSync = null;
        }
        ITbsReaderEntry iTbsReaderEntryA = cVarD.a(tBSOneComponentLoadComponentSync);
        if (iTbsReaderEntryA == null) {
            return -1;
        }
        try {
            i2 = Integer.parseInt(String.valueOf(cVarD.a("authenticate", null, new Class[]{Context.class}, context)));
        } catch (Throwable unused) {
            i2 = -1;
        }
        if (i2 != 0) {
            return i2;
        }
        int iA = cVarD.a(defaultInstance, iTbsReaderEntryA);
        Log.d("ReaderEngine", "initReaderEntry,ret:" + iA);
        return iA;
    }

    public static void initEngineAsync(Context context, ITbsReaderCallback iTbsReaderCallback) {
        c cVarD = c.d();
        if (cVarD.f22276a != null) {
            return;
        }
        if (cVarD.a(cVarD.a())) {
            TBSOneManager.setNeedReportEvent(false);
        }
        String strB = cVarD.b();
        if (!TextUtils.isEmpty(strB)) {
            f.a(strB);
        }
        TBSOneManager defaultInstance = TBSOneManager.getDefaultInstance(context);
        defaultInstance.setPolicy(cVarD.a());
        try {
            Bundle bundle = new Bundle();
            bundle.putBoolean(TBSOneConfigurationKeys.IGNORE_WIFI_STATE, true);
            bundle.putBoolean(TBSOneConfigurationKeys.IGNORE_FLOW_CONTROL, true);
            if (!defaultInstance.isComponentInstalled("file")) {
                bundle.putBoolean(TBSOneConfigurationKeys.IGNORE_FREQUENCY_LIMITATION, true);
            }
            defaultInstance.loadComponentAsync("file", bundle, new a(cVarD, context, iTbsReaderCallback, defaultInstance));
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static boolean isEngineLoaded() {
        return c.d().f22276a != null;
    }

    public static void setConfigSetting(String str, Object obj) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(str, obj);
        c cVarD = c.d();
        if (cVarD.f22281f == null) {
            cVarD.f22281f = new HashMap();
        }
        cVarD.f22281f.putAll(linkedHashMap);
    }

    public static void setInstallPolicy(String str) {
        c.d().f22280e = str;
    }

    public static void setLicense(String str) {
        setConfigSetting("license", str);
    }

    public static void setLicenseKey(String str) {
        setConfigSetting("license_key", str);
    }

    public static void setPrivateLicenseKey(String str) {
        setConfigSetting("private_license_key", str);
    }

    public static void setProviderSetting(String str) {
        setConfigSetting("provider_classname", str);
    }

    public void closeFileReader() {
        e eVar = this.mReaderManager;
        if (eVar != null) {
            d dVar = eVar.f22286b;
            if (dVar != null) {
                ITbsReader.IReaderCore iReaderCore = dVar.f22284a;
                if (iReaderCore != null) {
                    iReaderCore.closeFile();
                }
                ITbsReader.IReaderCore iReaderCore2 = dVar.f22284a;
                if (iReaderCore2 != null) {
                    iReaderCore2.destroy();
                }
                dVar.f22284a = null;
            }
            eVar.f22286b = null;
            ITbsReader iTbsReader = eVar.f22285a;
            if (iTbsReader != null) {
                iTbsReader.destroy();
            }
            eVar.f22285a = null;
        }
    }

    public ITbsReaderAtom createAtom(int i2) {
        ITbsReader iTbsReader = this.mReaderManager.f22285a;
        if (iTbsReader != null) {
            return (ITbsReaderAtom) iTbsReader.createAtom(i2);
        }
        return null;
    }

    public void doCommand(Integer num, Object obj, Object obj2) {
        ITbsReader iTbsReader;
        e eVar = this.mReaderManager;
        if (eVar == null || (iTbsReader = eVar.f22285a) == null) {
            return;
        }
        iTbsReader.doCommand(num, obj, obj2);
    }

    public e getReaderManager() {
        return this.mReaderManager;
    }

    public void gotoPosition(Bundle bundle) {
        int i2 = bundle.getInt("progress", -1);
        int i3 = bundle.getInt(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, -1);
        Bundle bundle2 = new Bundle();
        if (i2 != -1) {
            bundle2.putInt("progress", i2);
            bundle2.putBoolean("jumppage", true);
        } else if (i3 != -1) {
            bundle2.putInt(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, i3);
        }
        doCommand(307, bundle2, null);
    }

    public boolean initReader(Context context, ITbsReaderCallback iTbsReaderCallback) {
        return this.mReaderManager.a(context, iTbsReaderCallback);
    }

    public void onSizeChanged(int i2, int i3) {
        ITbsReader.IReaderCore iReaderCore;
        e eVar = this.mReaderManager;
        if (eVar != null) {
            Integer numValueOf = Integer.valueOf(i2);
            Integer numValueOf2 = Integer.valueOf(i3);
            d dVar = eVar.f22286b;
            if (dVar == null || (iReaderCore = dVar.f22284a) == null) {
                return;
            }
            iReaderCore.onSizeChanged(numValueOf, numValueOf2);
        }
    }

    public int openFileReader(Context context, Bundle bundle, ITbsReaderCallback iTbsReaderCallback, FrameLayout frameLayout) {
        closeFileReader();
        if (initReader(context, iTbsReaderCallback)) {
            return this.mReaderManager.a(context, bundle, frameLayout);
        }
        return -1;
    }

    public void pluginPreLoad(Context context, String str) {
        ITbsReader iTbsReader = this.mReaderManager.f22285a;
        ITbsReader.IReaderPlugin readerPlugin = iTbsReader != null ? iTbsReader.getReaderPlugin() : null;
        if (readerPlugin != null) {
            readerPlugin.initPlugin(context);
        }
        if (readerPlugin != null) {
            readerPlugin.downloadPlugin(context, str);
        }
    }

    public void setReaderManager(e eVar) {
        this.mReaderManager = eVar;
        if (eVar == null) {
            this.mReaderManager = new e();
        }
    }
}
