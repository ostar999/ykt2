package cn.hutool.core.convert.impl;

import cn.hutool.core.convert.AbstractConverter;
import cn.hutool.core.convert.ConvertException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.XmlUtil;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.TimeZone;
import org.w3c.dom.Node;

/* loaded from: classes.dex */
public class StringConverter extends AbstractConverter<String> {
    private static final long serialVersionUID = 1;

    private static String blobToStr(Blob blob) throws IOException {
        InputStream binaryStream = null;
        try {
            try {
                binaryStream = blob.getBinaryStream();
                return IoUtil.read(binaryStream, CharsetUtil.CHARSET_UTF_8);
            } catch (SQLException e2) {
                throw new ConvertException(e2);
            }
        } finally {
            IoUtil.close((Closeable) binaryStream);
        }
    }

    private static String clobToStr(Clob clob) throws IOException {
        Reader characterStream = null;
        try {
            try {
                characterStream = clob.getCharacterStream();
                return IoUtil.read(characterStream);
            } catch (SQLException e2) {
                throw new ConvertException(e2);
            }
        } finally {
            IoUtil.close((Closeable) characterStream);
        }
    }

    @Override // cn.hutool.core.convert.AbstractConverter
    public String convertInternal(Object obj) {
        return obj instanceof TimeZone ? ((TimeZone) obj).getID() : obj instanceof Node ? XmlUtil.toStr((Node) obj) : obj instanceof Clob ? clobToStr((Clob) obj) : obj instanceof Blob ? blobToStr((Blob) obj) : obj instanceof Type ? ((Type) obj).getTypeName() : convertToStr(obj);
    }
}
