package net.tsz.afinal.exception;

/* loaded from: classes9.dex */
public class ViewException extends AfinalException {
    private static final long serialVersionUID = 1;
    private String strMsg;

    public ViewException(String str) {
        this.strMsg = str;
    }

    @Override // java.lang.Throwable
    public void printStackTrace() {
        String str = this.strMsg;
        if (str != null) {
            System.err.println(str);
        }
        super.printStackTrace();
    }
}
