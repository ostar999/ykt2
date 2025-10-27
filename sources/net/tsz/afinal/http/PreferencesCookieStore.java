package net.tsz.afinal.http;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;

/* loaded from: classes9.dex */
public class PreferencesCookieStore implements CookieStore {
    private static final String COOKIE_NAME_PREFIX = "cookie_";
    private static final String COOKIE_NAME_STORE = "names";
    private static final String COOKIE_PREFS = "CookiePrefsFile";
    private final SharedPreferences cookiePrefs;
    private final ConcurrentHashMap<String, Cookie> cookies;

    public class SerializableCookie implements Serializable {
        private static final long serialVersionUID = 6374381828722046732L;
        private transient BasicClientCookie clientCookie;
        private final transient Cookie cookie;

        public SerializableCookie(Cookie cookie) {
            this.cookie = cookie;
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            BasicClientCookie basicClientCookie = new BasicClientCookie((String) objectInputStream.readObject(), (String) objectInputStream.readObject());
            this.clientCookie = basicClientCookie;
            basicClientCookie.setComment((String) objectInputStream.readObject());
            this.clientCookie.setDomain((String) objectInputStream.readObject());
            this.clientCookie.setExpiryDate((Date) objectInputStream.readObject());
            this.clientCookie.setPath((String) objectInputStream.readObject());
            this.clientCookie.setVersion(objectInputStream.readInt());
            this.clientCookie.setSecure(objectInputStream.readBoolean());
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.writeObject(this.cookie.getName());
            objectOutputStream.writeObject(this.cookie.getValue());
            objectOutputStream.writeObject(this.cookie.getComment());
            objectOutputStream.writeObject(this.cookie.getDomain());
            objectOutputStream.writeObject(this.cookie.getExpiryDate());
            objectOutputStream.writeObject(this.cookie.getPath());
            objectOutputStream.writeInt(this.cookie.getVersion());
            objectOutputStream.writeBoolean(this.cookie.isSecure());
        }

        public Cookie getCookie() {
            Cookie cookie = this.cookie;
            BasicClientCookie basicClientCookie = this.clientCookie;
            return basicClientCookie != null ? basicClientCookie : cookie;
        }
    }

    public PreferencesCookieStore(Context context) {
        Cookie cookieDecodeCookie;
        SharedPreferences sharedPreferences = context.getSharedPreferences(COOKIE_PREFS, 0);
        this.cookiePrefs = sharedPreferences;
        this.cookies = new ConcurrentHashMap<>();
        String string = sharedPreferences.getString(COOKIE_NAME_STORE, null);
        if (string != null) {
            for (String str : TextUtils.split(string, ",")) {
                String string2 = this.cookiePrefs.getString(COOKIE_NAME_PREFIX + str, null);
                if (string2 != null && (cookieDecodeCookie = decodeCookie(string2)) != null) {
                    this.cookies.put(str, cookieDecodeCookie);
                }
            }
            clearExpired(new Date());
        }
    }

    @Override // org.apache.http.client.CookieStore
    public void addCookie(Cookie cookie) {
        String name = cookie.getName();
        if (cookie.isExpired(new Date())) {
            this.cookies.remove(name);
        } else {
            this.cookies.put(name, cookie);
        }
        SharedPreferences.Editor editorEdit = this.cookiePrefs.edit();
        editorEdit.putString(COOKIE_NAME_STORE, TextUtils.join(",", this.cookies.keySet()));
        editorEdit.putString(COOKIE_NAME_PREFIX + name, encodeCookie(new SerializableCookie(cookie)));
        editorEdit.commit();
    }

    public String byteArrayToHexString(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        for (byte b3 : bArr) {
            int i2 = b3 & 255;
            if (i2 < 16) {
                stringBuffer.append('0');
            }
            stringBuffer.append(Integer.toHexString(i2));
        }
        return stringBuffer.toString().toUpperCase();
    }

    @Override // org.apache.http.client.CookieStore
    public void clear() {
        this.cookies.clear();
        SharedPreferences.Editor editorEdit = this.cookiePrefs.edit();
        Iterator<String> it = this.cookies.keySet().iterator();
        while (it.hasNext()) {
            editorEdit.remove(COOKIE_NAME_PREFIX + it.next());
        }
        editorEdit.remove(COOKIE_NAME_STORE);
        editorEdit.commit();
    }

    @Override // org.apache.http.client.CookieStore
    public boolean clearExpired(Date date) {
        SharedPreferences.Editor editorEdit = this.cookiePrefs.edit();
        boolean z2 = false;
        for (Map.Entry<String, Cookie> entry : this.cookies.entrySet()) {
            String key = entry.getKey();
            if (entry.getValue().isExpired(date)) {
                this.cookies.remove(key);
                editorEdit.remove(COOKIE_NAME_PREFIX + key);
                z2 = true;
            }
        }
        if (z2) {
            editorEdit.putString(COOKIE_NAME_STORE, TextUtils.join(",", this.cookies.keySet()));
        }
        editorEdit.commit();
        return z2;
    }

    public Cookie decodeCookie(String str) {
        try {
            return ((SerializableCookie) new ObjectInputStream(new ByteArrayInputStream(hexStringToByteArray(str))).readObject()).getCookie();
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public String encodeCookie(SerializableCookie serializableCookie) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            new ObjectOutputStream(byteArrayOutputStream).writeObject(serializableCookie);
            return byteArrayToHexString(byteArrayOutputStream.toByteArray());
        } catch (Exception unused) {
            return null;
        }
    }

    @Override // org.apache.http.client.CookieStore
    public List<Cookie> getCookies() {
        return new ArrayList(this.cookies.values());
    }

    public byte[] hexStringToByteArray(String str) {
        int length = str.length();
        byte[] bArr = new byte[length / 2];
        for (int i2 = 0; i2 < length; i2 += 2) {
            bArr[i2 / 2] = (byte) ((Character.digit(str.charAt(i2), 16) << 4) + Character.digit(str.charAt(i2 + 1), 16));
        }
        return bArr;
    }
}
