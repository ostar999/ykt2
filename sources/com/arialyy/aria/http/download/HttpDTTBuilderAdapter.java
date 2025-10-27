package com.arialyy.aria.http.download;

import com.arialyy.aria.core.TaskRecord;
import com.arialyy.aria.core.common.SubThreadConfig;
import com.arialyy.aria.core.loader.AbsNormalTTBuilderAdapter;
import com.arialyy.aria.core.loader.IRecordHandler;
import com.arialyy.aria.core.task.IThreadTaskAdapter;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.BufferedRandomAccessFile;
import com.arialyy.aria.util.FileUtil;
import java.io.File;
import java.io.IOException;

/* loaded from: classes2.dex */
final class HttpDTTBuilderAdapter extends AbsNormalTTBuilderAdapter {
    @Override // com.arialyy.aria.core.loader.AbsNormalTTBuilderAdapter
    public IThreadTaskAdapter getAdapter(SubThreadConfig subThreadConfig) {
        return new HttpDThreadTaskAdapter(subThreadConfig);
    }

    @Override // com.arialyy.aria.core.loader.AbsNormalTTBuilderAdapter
    public boolean handleNewTask(TaskRecord taskRecord, int i2) throws Throwable {
        if (taskRecord.isBlock) {
            for (int i3 = 0; i3 < i2; i3++) {
                File file = new File(String.format(IRecordHandler.SUB_PATH, getTempFile().getPath(), Integer.valueOf(i3)));
                if (file.exists()) {
                    ALog.d(this.TAG, String.format("分块【%s】已经存在，将删除该分块", Integer.valueOf(i3)));
                    FileUtil.deleteFile(file);
                }
            }
        } else if (getTempFile().exists()) {
            FileUtil.deleteFile(getTempFile());
        }
        BufferedRandomAccessFile bufferedRandomAccessFile = null;
        if (i2 > 1) {
            try {
                try {
                    if (!taskRecord.isBlock) {
                        BufferedRandomAccessFile bufferedRandomAccessFile2 = new BufferedRandomAccessFile(new File(getTempFile().getPath()), "rwd", 8192);
                        try {
                            bufferedRandomAccessFile2.setLength(getEntity().getFileSize());
                            bufferedRandomAccessFile = bufferedRandomAccessFile2;
                        } catch (IOException e2) {
                            e = e2;
                            bufferedRandomAccessFile = bufferedRandomAccessFile2;
                            e.printStackTrace();
                            ALog.e(this.TAG, String.format("下载失败，filePath: %s, url: %s", getEntity().getFilePath(), getEntity().getUrl()));
                            if (bufferedRandomAccessFile != null) {
                                try {
                                    bufferedRandomAccessFile.close();
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                }
                            }
                            return false;
                        } catch (Throwable th) {
                            th = th;
                            bufferedRandomAccessFile = bufferedRandomAccessFile2;
                            if (bufferedRandomAccessFile != null) {
                                try {
                                    bufferedRandomAccessFile.close();
                                } catch (IOException e4) {
                                    e4.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (IOException e5) {
                e = e5;
            }
        }
        if (bufferedRandomAccessFile != null) {
            try {
                bufferedRandomAccessFile.close();
            } catch (IOException e6) {
                e6.printStackTrace();
            }
        }
        return true;
    }
}
