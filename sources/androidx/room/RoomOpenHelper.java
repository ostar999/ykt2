package androidx.room;

import android.database.Cursor;
import android.database.SQLException;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.util.Iterator;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class RoomOpenHelper extends SupportSQLiteOpenHelper.Callback {

    @Nullable
    private DatabaseConfiguration mConfiguration;

    @NonNull
    private final Delegate mDelegate;

    @NonNull
    private final String mIdentityHash;

    @NonNull
    private final String mLegacyHash;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static abstract class Delegate {
        public final int version;

        public Delegate(int i2) {
            this.version = i2;
        }

        public abstract void createAllTables(SupportSQLiteDatabase supportSQLiteDatabase);

        public abstract void dropAllTables(SupportSQLiteDatabase supportSQLiteDatabase);

        public abstract void onCreate(SupportSQLiteDatabase supportSQLiteDatabase);

        public abstract void onOpen(SupportSQLiteDatabase supportSQLiteDatabase);

        public void onPostMigrate(SupportSQLiteDatabase supportSQLiteDatabase) {
        }

        public void onPreMigrate(SupportSQLiteDatabase supportSQLiteDatabase) {
        }

        @NonNull
        public ValidationResult onValidateSchema(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
            validateMigration(supportSQLiteDatabase);
            return new ValidationResult(true, null);
        }

        @Deprecated
        public void validateMigration(SupportSQLiteDatabase supportSQLiteDatabase) {
            throw new UnsupportedOperationException("validateMigration is deprecated");
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static class ValidationResult {

        @Nullable
        public final String expectedFoundMsg;
        public final boolean isValid;

        public ValidationResult(boolean z2, @Nullable String str) {
            this.isValid = z2;
            this.expectedFoundMsg = str;
        }
    }

    public RoomOpenHelper(@NonNull DatabaseConfiguration databaseConfiguration, @NonNull Delegate delegate, @NonNull String str, @NonNull String str2) {
        super(delegate.version);
        this.mConfiguration = databaseConfiguration;
        this.mDelegate = delegate;
        this.mIdentityHash = str;
        this.mLegacyHash = str2;
    }

    private void checkIdentity(SupportSQLiteDatabase supportSQLiteDatabase) throws SQLException {
        if (!hasRoomMasterTable(supportSQLiteDatabase)) {
            ValidationResult validationResultOnValidateSchema = this.mDelegate.onValidateSchema(supportSQLiteDatabase);
            if (validationResultOnValidateSchema.isValid) {
                this.mDelegate.onPostMigrate(supportSQLiteDatabase);
                updateIdentity(supportSQLiteDatabase);
                return;
            } else {
                throw new IllegalStateException("Pre-packaged database has an invalid schema: " + validationResultOnValidateSchema.expectedFoundMsg);
            }
        }
        Cursor cursorQuery = supportSQLiteDatabase.query(new SimpleSQLiteQuery(RoomMasterTable.READ_QUERY));
        try {
            String string = cursorQuery.moveToFirst() ? cursorQuery.getString(0) : null;
            cursorQuery.close();
            if (!this.mIdentityHash.equals(string) && !this.mLegacyHash.equals(string)) {
                throw new IllegalStateException("Room cannot verify the data integrity. Looks like you've changed schema but forgot to update the version number. You can simply fix this by increasing the version number.");
            }
        } catch (Throwable th) {
            cursorQuery.close();
            throw th;
        }
    }

    private void createMasterTableIfNotExists(SupportSQLiteDatabase supportSQLiteDatabase) throws SQLException {
        supportSQLiteDatabase.execSQL(RoomMasterTable.CREATE_QUERY);
    }

    private static boolean hasEmptySchema(SupportSQLiteDatabase supportSQLiteDatabase) {
        Cursor cursorQuery = supportSQLiteDatabase.query("SELECT count(*) FROM sqlite_master WHERE name != 'android_metadata'");
        try {
            boolean z2 = false;
            if (cursorQuery.moveToFirst()) {
                if (cursorQuery.getInt(0) == 0) {
                    z2 = true;
                }
            }
            return z2;
        } finally {
            cursorQuery.close();
        }
    }

    private static boolean hasRoomMasterTable(SupportSQLiteDatabase supportSQLiteDatabase) {
        Cursor cursorQuery = supportSQLiteDatabase.query("SELECT 1 FROM sqlite_master WHERE type = 'table' AND name='room_master_table'");
        try {
            boolean z2 = false;
            if (cursorQuery.moveToFirst()) {
                if (cursorQuery.getInt(0) != 0) {
                    z2 = true;
                }
            }
            return z2;
        } finally {
            cursorQuery.close();
        }
    }

    private void updateIdentity(SupportSQLiteDatabase supportSQLiteDatabase) throws SQLException {
        createMasterTableIfNotExists(supportSQLiteDatabase);
        supportSQLiteDatabase.execSQL(RoomMasterTable.createInsertQuery(this.mIdentityHash));
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper.Callback
    public void onConfigure(SupportSQLiteDatabase supportSQLiteDatabase) {
        super.onConfigure(supportSQLiteDatabase);
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper.Callback
    public void onCreate(SupportSQLiteDatabase supportSQLiteDatabase) throws SQLException {
        boolean zHasEmptySchema = hasEmptySchema(supportSQLiteDatabase);
        this.mDelegate.createAllTables(supportSQLiteDatabase);
        if (!zHasEmptySchema) {
            ValidationResult validationResultOnValidateSchema = this.mDelegate.onValidateSchema(supportSQLiteDatabase);
            if (!validationResultOnValidateSchema.isValid) {
                throw new IllegalStateException("Pre-packaged database has an invalid schema: " + validationResultOnValidateSchema.expectedFoundMsg);
            }
        }
        updateIdentity(supportSQLiteDatabase);
        this.mDelegate.onCreate(supportSQLiteDatabase);
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper.Callback
    public void onDowngrade(SupportSQLiteDatabase supportSQLiteDatabase, int i2, int i3) throws SQLException {
        onUpgrade(supportSQLiteDatabase, i2, i3);
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper.Callback
    public void onOpen(SupportSQLiteDatabase supportSQLiteDatabase) throws SQLException {
        super.onOpen(supportSQLiteDatabase);
        checkIdentity(supportSQLiteDatabase);
        this.mDelegate.onOpen(supportSQLiteDatabase);
        this.mConfiguration = null;
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper.Callback
    public void onUpgrade(SupportSQLiteDatabase supportSQLiteDatabase, int i2, int i3) throws SQLException {
        boolean z2;
        List<Migration> listFindMigrationPath;
        DatabaseConfiguration databaseConfiguration = this.mConfiguration;
        if (databaseConfiguration == null || (listFindMigrationPath = databaseConfiguration.migrationContainer.findMigrationPath(i2, i3)) == null) {
            z2 = false;
        } else {
            this.mDelegate.onPreMigrate(supportSQLiteDatabase);
            Iterator<Migration> it = listFindMigrationPath.iterator();
            while (it.hasNext()) {
                it.next().migrate(supportSQLiteDatabase);
            }
            ValidationResult validationResultOnValidateSchema = this.mDelegate.onValidateSchema(supportSQLiteDatabase);
            if (!validationResultOnValidateSchema.isValid) {
                throw new IllegalStateException("Migration didn't properly handle: " + validationResultOnValidateSchema.expectedFoundMsg);
            }
            this.mDelegate.onPostMigrate(supportSQLiteDatabase);
            updateIdentity(supportSQLiteDatabase);
            z2 = true;
        }
        if (z2) {
            return;
        }
        DatabaseConfiguration databaseConfiguration2 = this.mConfiguration;
        if (databaseConfiguration2 != null && !databaseConfiguration2.isMigrationRequired(i2, i3)) {
            this.mDelegate.dropAllTables(supportSQLiteDatabase);
            this.mDelegate.createAllTables(supportSQLiteDatabase);
            return;
        }
        throw new IllegalStateException("A migration from " + i2 + " to " + i3 + " was required but not found. Please provide the necessary Migration path via RoomDatabase.Builder.addMigration(Migration ...) or allow for destructive migrations via one of the RoomDatabase.Builder.fallbackToDestructiveMigration* methods.");
    }

    public RoomOpenHelper(@NonNull DatabaseConfiguration databaseConfiguration, @NonNull Delegate delegate, @NonNull String str) {
        this(databaseConfiguration, delegate, "", str);
    }
}
