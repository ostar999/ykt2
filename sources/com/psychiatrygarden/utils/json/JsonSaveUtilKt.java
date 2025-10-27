package com.psychiatrygarden.utils.json;

import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.SdkConstant;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u001a\u0018\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0003\u001a\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0003\u001a\b\u0010\u0006\u001a\u00020\u0003H\u0002¨\u0006\u0007"}, d2 = {"cacheJsonData", "", CommonParameter.JSON_DATA, "", "fileName", "loadJsonData", "saveFilePath", "xizongapp_ykbRelease"}, k = 2, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nJsonSaveUtil.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JsonSaveUtil.kt\ncom/psychiatrygarden/utils/json/JsonSaveUtilKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,60:1\n1#2:61\n*E\n"})
/* loaded from: classes6.dex */
public final class JsonSaveUtilKt {
    public static final void cacheJsonData(@Nullable String str, @NotNull String fileName) throws IOException {
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        try {
            File file = new File(saveFilePath());
            if (!file.exists()) {
                file.mkdirs();
            }
            File file2 = new File(saveFilePath(), fileName);
            if (!file2.exists()) {
                file2.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file2);
            fileWriter.write(str);
            fileWriter.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    @Nullable
    public static final String loadJsonData(@NotNull String fileName) throws IOException {
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        try {
            if (!new File(saveFilePath(), fileName).exists()) {
                return null;
            }
            BufferedReader bufferedReader = new BufferedReader(new FileReader(saveFilePath() + File.separator + fileName));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    bufferedReader.close();
                    return sb.toString();
                }
                sb.append(line);
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static final String saveFilePath() {
        StringBuilder sb = new StringBuilder();
        String str = File.separator;
        sb.append(str);
        sb.append(SdkConstant.UMENG_ALIS);
        sb.append(str);
        sb.append("ResourceDownload");
        String string = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        File externalFilesDir = ProjectApp.instance().getExternalFilesDir(null);
        Intrinsics.checkNotNull(externalFilesDir);
        sb2.append(externalFilesDir.getAbsolutePath());
        sb2.append(string);
        return sb2.toString();
    }
}
