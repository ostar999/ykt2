package com.psychiatrygarden.bean;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.psychiatrygarden.utils.Constants;
import com.uuzuche.lib_zxing.decoding.Intents;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import kotlinx.coroutines.debug.internal.DebugCoroutineInfoImplKt;

/* loaded from: classes5.dex */
public class CircleTopicHistoryBeanDao extends AbstractDao<CircleTopicHistoryBean, Void> {
    public static final String TABLENAME = "CIRCLE_TOPIC_HISTORY_BEAN";

    public static class Properties {
        public static final Property Id = new Property(0, String.class, "id", false, "ID");
        public static final Property Bbs_cate_id = new Property(1, String.class, Constants.PARAMS_CONSTANTS.PARAMS_CATE_ID, false, "BBS_CATE_ID");
        public static final Property Topic_label_id = new Property(2, String.class, Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_LABEL_ID, false, "TOPIC_LABEL_ID");
        public static final Property User_id = new Property(3, String.class, "user_id", false, "USER_ID");
        public static final Property Title = new Property(4, String.class, "title", false, "TITLE");
        public static final Property Content = new Property(5, String.class, "content", false, "CONTENT");
        public static final Property Sort_pos = new Property(6, String.class, "sort_pos", false, "SORT_POS");
        public static final Property Is_stick = new Property(7, String.class, "is_stick", false, "IS_STICK");
        public static final Property Is_elite = new Property(8, String.class, "is_elite", false, "IS_ELITE");
        public static final Property Is_img_topic = new Property(9, String.class, "is_img_topic", false, "IS_IMG_TOPIC");
        public static final Property View_num = new Property(10, String.class, "view_num", false, "VIEW_NUM");
        public static final Property Vote_num = new Property(11, String.class, "vote_num", false, "VOTE_NUM");
        public static final Property Reply_num = new Property(12, String.class, "reply_num", false, "REPLY_NUM");
        public static final Property Type = new Property(13, String.class, "type", false, Intents.WifiConnect.TYPE);
        public static final Property Created = new Property(14, String.class, "created", false, DebugCoroutineInfoImplKt.CREATED);
        public static final Property Updated = new Property(15, String.class, "updated", false, "UPDATED");
        public static final Property Nickname = new Property(16, String.class, "nickname", false, "NICKNAME");
        public static final Property Head_img = new Property(17, String.class, "head_img", false, "HEAD_IMG");
        public static final Property Is_praise = new Property(18, String.class, "is_praise", false, "IS_PRAISE");
        public static final Property Praise_num = new Property(19, String.class, "praise_num", false, "PRAISE_NUM");
    }

    public CircleTopicHistoryBeanDao(DaoConfig config) {
        super(config);
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) throws SQLException {
        db.execSQL("CREATE TABLE " + (ifNotExists ? "IF NOT EXISTS " : "") + "'CIRCLE_TOPIC_HISTORY_BEAN' ('ID' TEXT,'BBS_CATE_ID' TEXT,'TOPIC_LABEL_ID' TEXT,'USER_ID' TEXT,'TITLE' TEXT,'CONTENT' TEXT,'SORT_POS' TEXT,'IS_STICK' TEXT,'IS_ELITE' TEXT,'IS_IMG_TOPIC' TEXT,'VIEW_NUM' TEXT,'VOTE_NUM' TEXT,'REPLY_NUM' TEXT,'TYPE' TEXT,'CREATED' TEXT,'UPDATED' TEXT,'NICKNAME' TEXT,'HEAD_IMG' TEXT,'IS_PRAISE' TEXT,'PRAISE_NUM' TEXT);");
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE ");
        sb.append(ifExists ? "IF EXISTS " : "");
        sb.append("'CIRCLE_TOPIC_HISTORY_BEAN'");
        db.execSQL(sb.toString());
    }

    @Override // de.greenrobot.dao.AbstractDao
    public Void getKey(CircleTopicHistoryBean entity) {
        return null;
    }

    @Override // de.greenrobot.dao.AbstractDao
    public boolean isEntityUpdateable() {
        return true;
    }

    @Override // de.greenrobot.dao.AbstractDao
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }

    @Override // de.greenrobot.dao.AbstractDao
    public Void updateKeyAfterInsert(CircleTopicHistoryBean entity, long rowId) {
        return null;
    }

    public CircleTopicHistoryBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void bindValues(SQLiteStatement stmt, CircleTopicHistoryBean entity) {
        stmt.clearBindings();
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
        String bbs_cate_id = entity.getBbs_cate_id();
        if (bbs_cate_id != null) {
            stmt.bindString(2, bbs_cate_id);
        }
        String topic_label_id = entity.getTopic_label_id();
        if (topic_label_id != null) {
            stmt.bindString(3, topic_label_id);
        }
        String user_id = entity.getUser_id();
        if (user_id != null) {
            stmt.bindString(4, user_id);
        }
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(5, title);
        }
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(6, content);
        }
        String sort_pos = entity.getSort_pos();
        if (sort_pos != null) {
            stmt.bindString(7, sort_pos);
        }
        String is_stick = entity.getIs_stick();
        if (is_stick != null) {
            stmt.bindString(8, is_stick);
        }
        String is_elite = entity.getIs_elite();
        if (is_elite != null) {
            stmt.bindString(9, is_elite);
        }
        String is_img_topic = entity.getIs_img_topic();
        if (is_img_topic != null) {
            stmt.bindString(10, is_img_topic);
        }
        String view_num = entity.getView_num();
        if (view_num != null) {
            stmt.bindString(11, view_num);
        }
        String vote_num = entity.getVote_num();
        if (vote_num != null) {
            stmt.bindString(12, vote_num);
        }
        String reply_num = entity.getReply_num();
        if (reply_num != null) {
            stmt.bindString(13, reply_num);
        }
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(14, type);
        }
        String created = entity.getCreated();
        if (created != null) {
            stmt.bindString(15, created);
        }
        String updated = entity.getUpdated();
        if (updated != null) {
            stmt.bindString(16, updated);
        }
        String nickname = entity.getNickname();
        if (nickname != null) {
            stmt.bindString(17, nickname);
        }
        String head_img = entity.getHead_img();
        if (head_img != null) {
            stmt.bindString(18, head_img);
        }
        String is_praise = entity.getIs_praise();
        if (is_praise != null) {
            stmt.bindString(19, is_praise);
        }
        String praise_num = entity.getPraise_num();
        if (praise_num != null) {
            stmt.bindString(20, praise_num);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // de.greenrobot.dao.AbstractDao
    public CircleTopicHistoryBean readEntity(Cursor cursor, int offset) {
        int i2 = offset + 0;
        String string = cursor.isNull(i2) ? null : cursor.getString(i2);
        int i3 = offset + 1;
        String string2 = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = offset + 2;
        String string3 = cursor.isNull(i4) ? null : cursor.getString(i4);
        int i5 = offset + 3;
        String string4 = cursor.isNull(i5) ? null : cursor.getString(i5);
        int i6 = offset + 4;
        String string5 = cursor.isNull(i6) ? null : cursor.getString(i6);
        int i7 = offset + 5;
        String string6 = cursor.isNull(i7) ? null : cursor.getString(i7);
        int i8 = offset + 6;
        String string7 = cursor.isNull(i8) ? null : cursor.getString(i8);
        int i9 = offset + 7;
        String string8 = cursor.isNull(i9) ? null : cursor.getString(i9);
        int i10 = offset + 8;
        String string9 = cursor.isNull(i10) ? null : cursor.getString(i10);
        int i11 = offset + 9;
        String string10 = cursor.isNull(i11) ? null : cursor.getString(i11);
        int i12 = offset + 10;
        String string11 = cursor.isNull(i12) ? null : cursor.getString(i12);
        int i13 = offset + 11;
        String string12 = cursor.isNull(i13) ? null : cursor.getString(i13);
        int i14 = offset + 12;
        String string13 = cursor.isNull(i14) ? null : cursor.getString(i14);
        int i15 = offset + 13;
        String string14 = cursor.isNull(i15) ? null : cursor.getString(i15);
        int i16 = offset + 14;
        String string15 = cursor.isNull(i16) ? null : cursor.getString(i16);
        int i17 = offset + 15;
        String string16 = cursor.isNull(i17) ? null : cursor.getString(i17);
        int i18 = offset + 16;
        String string17 = cursor.isNull(i18) ? null : cursor.getString(i18);
        int i19 = offset + 17;
        String string18 = cursor.isNull(i19) ? null : cursor.getString(i19);
        int i20 = offset + 18;
        String string19 = cursor.isNull(i20) ? null : cursor.getString(i20);
        int i21 = offset + 19;
        return new CircleTopicHistoryBean(string, string2, string3, string4, string5, string6, string7, string8, string9, string10, string11, string12, string13, string14, string15, string16, string17, string18, string19, cursor.isNull(i21) ? null : cursor.getString(i21));
    }

    @Override // de.greenrobot.dao.AbstractDao
    public void readEntity(Cursor cursor, CircleTopicHistoryBean entity, int offset) {
        int i2 = offset + 0;
        entity.setId(cursor.isNull(i2) ? null : cursor.getString(i2));
        int i3 = offset + 1;
        entity.setBbs_cate_id(cursor.isNull(i3) ? null : cursor.getString(i3));
        int i4 = offset + 2;
        entity.setTopic_label_id(cursor.isNull(i4) ? null : cursor.getString(i4));
        int i5 = offset + 3;
        entity.setUser_id(cursor.isNull(i5) ? null : cursor.getString(i5));
        int i6 = offset + 4;
        entity.setTitle(cursor.isNull(i6) ? null : cursor.getString(i6));
        int i7 = offset + 5;
        entity.setContent(cursor.isNull(i7) ? null : cursor.getString(i7));
        int i8 = offset + 6;
        entity.setSort_pos(cursor.isNull(i8) ? null : cursor.getString(i8));
        int i9 = offset + 7;
        entity.setIs_stick(cursor.isNull(i9) ? null : cursor.getString(i9));
        int i10 = offset + 8;
        entity.setIs_elite(cursor.isNull(i10) ? null : cursor.getString(i10));
        int i11 = offset + 9;
        entity.setIs_img_topic(cursor.isNull(i11) ? null : cursor.getString(i11));
        int i12 = offset + 10;
        entity.setView_num(cursor.isNull(i12) ? null : cursor.getString(i12));
        int i13 = offset + 11;
        entity.setVote_num(cursor.isNull(i13) ? null : cursor.getString(i13));
        int i14 = offset + 12;
        entity.setReply_num(cursor.isNull(i14) ? null : cursor.getString(i14));
        int i15 = offset + 13;
        entity.setType(cursor.isNull(i15) ? null : cursor.getString(i15));
        int i16 = offset + 14;
        entity.setCreated(cursor.isNull(i16) ? null : cursor.getString(i16));
        int i17 = offset + 15;
        entity.setUpdated(cursor.isNull(i17) ? null : cursor.getString(i17));
        int i18 = offset + 16;
        entity.setNickname(cursor.isNull(i18) ? null : cursor.getString(i18));
        int i19 = offset + 17;
        entity.setHead_img(cursor.isNull(i19) ? null : cursor.getString(i19));
        int i20 = offset + 18;
        entity.setIs_praise(cursor.isNull(i20) ? null : cursor.getString(i20));
        int i21 = offset + 19;
        entity.setPraise_num(cursor.isNull(i21) ? null : cursor.getString(i21));
    }
}
