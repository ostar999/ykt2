package androidx.room;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.room.util.CopyLock;
import androidx.room.util.DBUtil;
import androidx.room.util.FileUtil;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/* loaded from: classes.dex */
class SQLiteCopyOpenHelper implements SupportSQLiteOpenHelper {

    @NonNull
    private final Context mContext;

    @Nullable
    private final String mCopyFromAssetPath;

    @Nullable
    private final File mCopyFromFile;

    @Nullable
    private DatabaseConfiguration mDatabaseConfiguration;
    private final int mDatabaseVersion;

    @NonNull
    private final SupportSQLiteOpenHelper mDelegate;
    private boolean mVerified;

    public SQLiteCopyOpenHelper(@NonNull Context context, @Nullable String str, @Nullable File file, int i2, @NonNull SupportSQLiteOpenHelper supportSQLiteOpenHelper) {
        this.mContext = context;
        this.mCopyFromAssetPath = str;
        this.mCopyFromFile = file;
        this.mDatabaseVersion = i2;
        this.mDelegate = supportSQLiteOpenHelper;
    }

    private void copyDatabaseFile(File file) throws IOException {
        ReadableByteChannel channel;
        if (this.mCopyFromAssetPath != null) {
            channel = Channels.newChannel(this.mContext.getAssets().open(this.mCopyFromAssetPath));
        } else {
            if (this.mCopyFromFile == null) {
                throw new IllegalStateException("copyFromAssetPath and copyFromFile == null!");
            }
            channel = new FileInputStream(this.mCopyFromFile).getChannel();
        }
        File fileCreateTempFile = File.createTempFile("room-copy-helper", ".tmp", this.mContext.getCacheDir());
        fileCreateTempFile.deleteOnExit();
        FileUtil.copy(channel, new FileOutputStream(fileCreateTempFile).getChannel());
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists() && !parentFile.mkdirs()) {
            throw new IOException("Failed to create directories for " + file.getAbsolutePath());
        }
        if (fileCreateTempFile.renameTo(file)) {
            return;
        }
        throw new IOException("Failed to move intermediate file (" + fileCreateTempFile.getAbsolutePath() + ") to destination (" + file.getAbsolutePath() + ").");
    }

    private void verifyDatabaseFile() throws IOException {
        String databaseName = getDatabaseName();
        File databasePath = this.mContext.getDatabasePath(databaseName);
        DatabaseConfiguration databaseConfiguration = this.mDatabaseConfiguration;
        CopyLock copyLock = new CopyLock(databaseName, this.mContext.getFilesDir(), databaseConfiguration == null || databaseConfiguration.multiInstanceInvalidation);
        try {
            copyLock.lock();
            if (!databasePath.exists()) {
                try {
                    copyDatabaseFile(databasePath);
                    copyLock.unlock();
                    return;
                } catch (IOException e2) {
                    throw new RuntimeException("Unable to copy database file.", e2);
                }
            }
            if (this.mDatabaseConfiguration == null) {
                copyLock.unlock();
                return;
            }
            try {
                int version = DBUtil.readVersion(databasePath);
                int i2 = this.mDatabaseVersion;
                if (version == i2) {
                    copyLock.unlock();
                    return;
                }
                if (this.mDatabaseConfiguration.isMigrationRequired(version, i2)) {
                    copyLock.unlock();
                    return;
                }
                if (this.mContext.deleteDatabase(databaseName)) {
                    try {
                        copyDatabaseFile(databasePath);
                    } catch (IOException e3) {
                        Log.w("ROOM", "Unable to copy database file.", e3);
                    }
                } else {
                    Log.w("ROOM", "Failed to delete database file (" + databaseName + ") for a copy destructive migration.");
                }
                copyLock.unlock();
                return;
            } catch (IOException e4) {
                Log.w("ROOM", "Unable to read database version.", e4);
                copyLock.unlock();
                return;
            }
        } catch (Throwable th) {
            copyLock.unlock();
            throw th;
        }
        copyLock.unlock();
        throw th;
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper, java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() {
        this.mDelegate.close();
        this.mVerified = false;
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper
    public String getDatabaseName() {
        return this.mDelegate.getDatabaseName();
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper
    public synchronized SupportSQLiteDatabase getReadableDatabase() {
        if (!this.mVerified) {
            verifyDatabaseFile();
            this.mVerified = true;
        }
        return this.mDelegate.getReadableDatabase();
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper
    public synchronized SupportSQLiteDatabase getWritableDatabase() {
        if (!this.mVerified) {
            verifyDatabaseFile();
            this.mVerified = true;
        }
        return this.mDelegate.getWritableDatabase();
    }

    public void setDatabaseConfiguration(@Nullable DatabaseConfiguration databaseConfiguration) {
        this.mDatabaseConfiguration = databaseConfiguration;
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper
    @RequiresApi(api = 16)
    public void setWriteAheadLoggingEnabled(boolean z2) {
        this.mDelegate.setWriteAheadLoggingEnabled(z2);
    }
}
