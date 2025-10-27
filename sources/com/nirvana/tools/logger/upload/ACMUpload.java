package com.nirvana.tools.logger.upload;

import com.nirvana.tools.logger.model.ACMRecord;
import java.util.List;

/* loaded from: classes4.dex */
public interface ACMUpload<T extends ACMRecord> {
    boolean upload(List<T> list);
}
