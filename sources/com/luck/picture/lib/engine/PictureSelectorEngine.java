package com.luck.picture.lib.engine;

import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnInjectLayoutResourceListener;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;

/* loaded from: classes4.dex */
public interface PictureSelectorEngine {
    CompressEngine createCompressEngine();

    ImageEngine createImageLoaderEngine();

    OnInjectLayoutResourceListener createLayoutResourceListener();

    ExtendLoaderEngine createLoaderDataEngine();

    SandboxFileEngine createSandboxFileEngine();

    OnResultCallbackListener<LocalMedia> getResultCallbackListener();
}
