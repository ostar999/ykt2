package cn.hutool.core.io.file;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.URLUtil;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class FileSystemUtil {
    public static FileSystem create(String str) {
        try {
            return FileSystems.newFileSystem(Paths.get(str, new String[0]).toUri(), (Map<String, ?>) MapUtil.of("create", k.a.f27523u));
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static FileSystem createZip(String str) {
        return createZip(str, null);
    }

    public static Path getRoot(FileSystem fileSystem) {
        return fileSystem.getPath("/", new String[0]);
    }

    public static FileSystem createZip(String str, Charset charset) {
        if (charset == null) {
            charset = CharsetUtil.CHARSET_UTF_8;
        }
        HashMap map = new HashMap();
        map.put("create", k.a.f27523u);
        map.put("encoding", charset.name());
        try {
            return FileSystems.newFileSystem(URI.create(URLUtil.JAR_URL_PREFIX + Paths.get(str, new String[0]).toUri()), (Map<String, ?>) map);
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }
}
