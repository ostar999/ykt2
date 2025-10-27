package com.azhon.appupdate.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/azhon/appupdate/util/FileUtil;", "", "()V", "Companion", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class FileUtil {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t¨\u0006\n"}, d2 = {"Lcom/azhon/appupdate/util/FileUtil$Companion;", "", "()V", "createDirDirectory", "", "path", "", "md5", "file", "Ljava/io/File;", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nFileUtil.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FileUtil.kt\ncom/azhon/appupdate/util/FileUtil$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,46:1\n1#2:47\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void createDirDirectory(@NotNull String path) {
            Intrinsics.checkNotNullParameter(path, "path");
            File file = new File(path);
            if (file.exists()) {
                return;
            }
            file.mkdirs();
        }

        @NotNull
        public final String md5(@NotNull File file) throws NoSuchAlgorithmException, IOException {
            Intrinsics.checkNotNullParameter(file, "file");
            try {
                byte[] bArr = new byte[1024];
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                FileInputStream fileInputStream = new FileInputStream(file);
                while (true) {
                    int i2 = fileInputStream.read(bArr);
                    if (i2 == -1) {
                        fileInputStream.close();
                        String string = new BigInteger(1, messageDigest.digest()).toString(16);
                        Intrinsics.checkNotNullExpressionValue(string, "bigInt.toString(16)");
                        String upperCase = StringsKt__StringsKt.padStart(string, 32, '0').toUpperCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(upperCase, "this as java.lang.String).toUpperCase(Locale.ROOT)");
                        return upperCase;
                    }
                    messageDigest.update(bArr, 0, i2);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                return "";
            }
        }
    }
}
