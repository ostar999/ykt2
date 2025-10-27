package org.eclipse.jetty.server.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.server.AbstractHttpConnection;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.util.ByteArrayISO8859Writer;

/* loaded from: classes9.dex */
public class ErrorHandler extends AbstractHandler {
    boolean _showStacks = true;
    boolean _showMessageInTitle = true;
    String _cacheControl = "must-revalidate,no-cache,no-store";

    public String getCacheControl() {
        return this._cacheControl;
    }

    public boolean getShowMessageInTitle() {
        return this._showMessageInTitle;
    }

    @Override // org.eclipse.jetty.server.Handler
    public void handle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        AbstractHttpConnection currentConnection = AbstractHttpConnection.getCurrentConnection();
        currentConnection.getRequest().setHandled(true);
        String method = httpServletRequest.getMethod();
        if (method.equals("GET") || method.equals("POST") || method.equals("HEAD")) {
            httpServletResponse.setContentType(MimeTypes.TEXT_HTML_8859_1);
            String str2 = this._cacheControl;
            if (str2 != null) {
                httpServletResponse.setHeader("Cache-Control", str2);
            }
            ByteArrayISO8859Writer byteArrayISO8859Writer = new ByteArrayISO8859Writer(4096);
            handleErrorPage(httpServletRequest, byteArrayISO8859Writer, currentConnection.getResponse().getStatus(), currentConnection.getResponse().getReason());
            byteArrayISO8859Writer.flush();
            httpServletResponse.setContentLength(byteArrayISO8859Writer.size());
            byteArrayISO8859Writer.writeTo(httpServletResponse.getOutputStream());
            byteArrayISO8859Writer.destroy();
        }
    }

    public void handleErrorPage(HttpServletRequest httpServletRequest, Writer writer, int i2, String str) throws IOException {
        writeErrorPage(httpServletRequest, writer, i2, str, this._showStacks);
    }

    public boolean isShowStacks() {
        return this._showStacks;
    }

    public void setCacheControl(String str) {
        this._cacheControl = str;
    }

    public void setShowMessageInTitle(boolean z2) {
        this._showMessageInTitle = z2;
    }

    public void setShowStacks(boolean z2) {
        this._showStacks = z2;
    }

    public void write(Writer writer, String str) throws IOException {
        if (str == null) {
            return;
        }
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt == '&') {
                writer.write("&amp;");
            } else if (cCharAt == '<') {
                writer.write("&lt;");
            } else if (cCharAt == '>') {
                writer.write("&gt;");
            } else if (!Character.isISOControl(cCharAt) || Character.isWhitespace(cCharAt)) {
                writer.write(cCharAt);
            } else {
                writer.write(63);
            }
        }
    }

    public void writeErrorPage(HttpServletRequest httpServletRequest, Writer writer, int i2, String str, boolean z2) throws IOException {
        if (str == null) {
            str = HttpStatus.getMessage(i2);
        }
        String str2 = str;
        writer.write("<html>\n<head>\n");
        writeErrorPageHead(httpServletRequest, writer, i2, str2);
        writer.write("</head>\n<body>");
        writeErrorPageBody(httpServletRequest, writer, i2, str2, z2);
        writer.write("\n</body>\n</html>\n");
    }

    public void writeErrorPageBody(HttpServletRequest httpServletRequest, Writer writer, int i2, String str, boolean z2) throws IOException {
        writeErrorPageMessage(httpServletRequest, writer, i2, str, httpServletRequest.getRequestURI());
        if (z2) {
            writeErrorPageStacks(httpServletRequest, writer);
        }
        writer.write("<hr /><i><small>Powered by Jetty://</small></i>");
        for (int i3 = 0; i3 < 20; i3++) {
            writer.write("<br/>                                                \n");
        }
    }

    public void writeErrorPageHead(HttpServletRequest httpServletRequest, Writer writer, int i2, String str) throws IOException {
        writer.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\"/>\n");
        writer.write("<title>Error ");
        writer.write(Integer.toString(i2));
        if (this._showMessageInTitle) {
            writer.write(32);
            write(writer, str);
        }
        writer.write("</title>\n");
    }

    public void writeErrorPageMessage(HttpServletRequest httpServletRequest, Writer writer, int i2, String str, String str2) throws IOException {
        writer.write("<h2>HTTP ERROR ");
        writer.write(Integer.toString(i2));
        writer.write("</h2>\n<p>Problem accessing ");
        write(writer, str2);
        writer.write(". Reason:\n<pre>    ");
        write(writer, str);
        writer.write("</pre></p>");
    }

    public void writeErrorPageStacks(HttpServletRequest httpServletRequest, Writer writer) throws IOException {
        for (Throwable cause = (Throwable) httpServletRequest.getAttribute("javax.servlet.error.exception"); cause != null; cause = cause.getCause()) {
            writer.write("<h3>Caused by:</h3><pre>");
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            cause.printStackTrace(printWriter);
            printWriter.flush();
            write(writer, stringWriter.getBuffer().toString());
            writer.write("</pre>\n");
        }
    }
}
