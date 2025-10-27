package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.lang.reflect.Type;

/* loaded from: classes2.dex */
public class AwtCodec implements ObjectSerializer, ObjectDeserializer {
    public static final AwtCodec instance = new AwtCodec();

    private Object parseRef(DefaultJSONParser defaultJSONParser, Object obj) {
        JSONLexer lexer = defaultJSONParser.getLexer();
        lexer.nextTokenWithColon(4);
        String strStringVal = lexer.stringVal();
        defaultJSONParser.setContext(defaultJSONParser.getContext(), obj);
        defaultJSONParser.addResolveTask(new DefaultJSONParser.ResolveTask(defaultJSONParser.getContext(), strStringVal));
        defaultJSONParser.popContext();
        defaultJSONParser.setResolveStatus(1);
        lexer.nextToken(13);
        defaultJSONParser.accept(13);
        return null;
    }

    public static boolean support(Class<?> cls) {
        return cls == Point.class || cls == Rectangle.class || cls == Font.class || cls == Color.class;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        T t2;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() == 8) {
            jSONLexer.nextToken(16);
            return null;
        }
        if (jSONLexer.token() != 12 && jSONLexer.token() != 16) {
            throw new JSONException("syntax error");
        }
        jSONLexer.nextToken();
        if (type == Point.class) {
            t2 = (T) parsePoint(defaultJSONParser, obj);
        } else if (type == Rectangle.class) {
            t2 = (T) parseRectangle(defaultJSONParser);
        } else if (type == Color.class) {
            t2 = (T) parseColor(defaultJSONParser);
        } else {
            if (type != Font.class) {
                throw new JSONException("not support awt class : " + type);
            }
            t2 = (T) parseFont(defaultJSONParser);
        }
        ParseContext context = defaultJSONParser.getContext();
        defaultJSONParser.setContext(t2, obj);
        defaultJSONParser.setContext(context);
        return t2;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 12;
    }

    public Color parseColor(DefaultJSONParser defaultJSONParser) {
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (jSONLexer.token() != 13) {
            if (jSONLexer.token() != 4) {
                throw new JSONException("syntax error");
            }
            String strStringVal = jSONLexer.stringVal();
            jSONLexer.nextTokenWithColon(2);
            if (jSONLexer.token() != 2) {
                throw new JSONException("syntax error");
            }
            int iIntValue = jSONLexer.intValue();
            jSONLexer.nextToken();
            if (strStringVal.equalsIgnoreCase("r")) {
                i2 = iIntValue;
            } else if (strStringVal.equalsIgnoreCase("g")) {
                i3 = iIntValue;
            } else if (strStringVal.equalsIgnoreCase("b")) {
                i4 = iIntValue;
            } else {
                if (!strStringVal.equalsIgnoreCase("alpha")) {
                    throw new JSONException("syntax error, " + strStringVal);
                }
                i5 = iIntValue;
            }
            if (jSONLexer.token() == 16) {
                jSONLexer.nextToken(4);
            }
        }
        jSONLexer.nextToken();
        return new Color(i2, i3, i4, i5);
    }

    public Font parseFont(DefaultJSONParser defaultJSONParser) {
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        int iIntValue = 0;
        String strStringVal = null;
        int iIntValue2 = 0;
        while (jSONLexer.token() != 13) {
            if (jSONLexer.token() != 4) {
                throw new JSONException("syntax error");
            }
            String strStringVal2 = jSONLexer.stringVal();
            jSONLexer.nextTokenWithColon(2);
            if (strStringVal2.equalsIgnoreCase("name")) {
                if (jSONLexer.token() != 4) {
                    throw new JSONException("syntax error");
                }
                strStringVal = jSONLexer.stringVal();
                jSONLexer.nextToken();
            } else if (strStringVal2.equalsIgnoreCase(TtmlNode.TAG_STYLE)) {
                if (jSONLexer.token() != 2) {
                    throw new JSONException("syntax error");
                }
                iIntValue = jSONLexer.intValue();
                jSONLexer.nextToken();
            } else {
                if (!strStringVal2.equalsIgnoreCase(DatabaseManager.SIZE)) {
                    throw new JSONException("syntax error, " + strStringVal2);
                }
                if (jSONLexer.token() != 2) {
                    throw new JSONException("syntax error");
                }
                iIntValue2 = jSONLexer.intValue();
                jSONLexer.nextToken();
            }
            if (jSONLexer.token() == 16) {
                jSONLexer.nextToken(4);
            }
        }
        jSONLexer.nextToken();
        return new Font(strStringVal, iIntValue, iIntValue2);
    }

    public Point parsePoint(DefaultJSONParser defaultJSONParser, Object obj) {
        int iFloatValue;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        int i2 = 0;
        int i3 = 0;
        while (jSONLexer.token() != 13) {
            if (jSONLexer.token() != 4) {
                throw new JSONException("syntax error");
            }
            String strStringVal = jSONLexer.stringVal();
            if (JSON.DEFAULT_TYPE_KEY.equals(strStringVal)) {
                defaultJSONParser.acceptType("java.awt.Point");
            } else {
                if ("$ref".equals(strStringVal)) {
                    return (Point) parseRef(defaultJSONParser, obj);
                }
                jSONLexer.nextTokenWithColon(2);
                int i4 = jSONLexer.token();
                if (i4 == 2) {
                    iFloatValue = jSONLexer.intValue();
                    jSONLexer.nextToken();
                } else {
                    if (i4 != 3) {
                        throw new JSONException("syntax error : " + jSONLexer.tokenName());
                    }
                    iFloatValue = (int) jSONLexer.floatValue();
                    jSONLexer.nextToken();
                }
                if (strStringVal.equalsIgnoreCase("x")) {
                    i2 = iFloatValue;
                } else {
                    if (!strStringVal.equalsIgnoreCase("y")) {
                        throw new JSONException("syntax error, " + strStringVal);
                    }
                    i3 = iFloatValue;
                }
                if (jSONLexer.token() == 16) {
                    jSONLexer.nextToken(4);
                }
            }
        }
        jSONLexer.nextToken();
        return new Point(i2, i3);
    }

    public Rectangle parseRectangle(DefaultJSONParser defaultJSONParser) {
        int iFloatValue;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (jSONLexer.token() != 13) {
            if (jSONLexer.token() != 4) {
                throw new JSONException("syntax error");
            }
            String strStringVal = jSONLexer.stringVal();
            jSONLexer.nextTokenWithColon(2);
            int i6 = jSONLexer.token();
            if (i6 == 2) {
                iFloatValue = jSONLexer.intValue();
                jSONLexer.nextToken();
            } else {
                if (i6 != 3) {
                    throw new JSONException("syntax error");
                }
                iFloatValue = (int) jSONLexer.floatValue();
                jSONLexer.nextToken();
            }
            if (strStringVal.equalsIgnoreCase("x")) {
                i2 = iFloatValue;
            } else if (strStringVal.equalsIgnoreCase("y")) {
                i3 = iFloatValue;
            } else if (strStringVal.equalsIgnoreCase("width")) {
                i4 = iFloatValue;
            } else {
                if (!strStringVal.equalsIgnoreCase("height")) {
                    throw new JSONException("syntax error, " + strStringVal);
                }
                i5 = iFloatValue;
            }
            if (jSONLexer.token() == 16) {
                jSONLexer.nextToken(4);
            }
        }
        jSONLexer.nextToken();
        return new Rectangle(i2, i3, i4, i5);
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i2) throws IOException {
        SerializeWriter serializeWriter = jSONSerializer.out;
        if (obj == null) {
            serializeWriter.writeNull();
            return;
        }
        if (obj instanceof Point) {
            Point point = (Point) obj;
            serializeWriter.writeFieldValue(writeClassName(serializeWriter, Point.class, '{'), "x", point.x);
            serializeWriter.writeFieldValue(',', "y", point.y);
        } else if (obj instanceof Font) {
            Font font = (Font) obj;
            serializeWriter.writeFieldValue(writeClassName(serializeWriter, Font.class, '{'), "name", font.getName());
            serializeWriter.writeFieldValue(',', TtmlNode.TAG_STYLE, font.getStyle());
            serializeWriter.writeFieldValue(',', DatabaseManager.SIZE, font.getSize());
        } else if (obj instanceof Rectangle) {
            Rectangle rectangle = (Rectangle) obj;
            serializeWriter.writeFieldValue(writeClassName(serializeWriter, Rectangle.class, '{'), "x", rectangle.x);
            serializeWriter.writeFieldValue(',', "y", rectangle.y);
            serializeWriter.writeFieldValue(',', "width", rectangle.width);
            serializeWriter.writeFieldValue(',', "height", rectangle.height);
        } else {
            if (!(obj instanceof Color)) {
                throw new JSONException("not support awt class : " + obj.getClass().getName());
            }
            Color color = (Color) obj;
            serializeWriter.writeFieldValue(writeClassName(serializeWriter, Color.class, '{'), "r", color.getRed());
            serializeWriter.writeFieldValue(',', "g", color.getGreen());
            serializeWriter.writeFieldValue(',', "b", color.getBlue());
            if (color.getAlpha() > 0) {
                serializeWriter.writeFieldValue(',', "alpha", color.getAlpha());
            }
        }
        serializeWriter.write(125);
    }

    public char writeClassName(SerializeWriter serializeWriter, Class<?> cls, char c3) {
        if (!serializeWriter.isEnabled(SerializerFeature.WriteClassName)) {
            return c3;
        }
        serializeWriter.write(123);
        serializeWriter.writeFieldName(JSON.DEFAULT_TYPE_KEY);
        serializeWriter.writeString(cls.getName());
        return ',';
    }
}
