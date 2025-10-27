package com.google.zxing.client.result;

/* loaded from: classes4.dex */
public final class EmailAddressParsedResult extends ParsedResult {
    private final String body;
    private final String emailAddress;
    private final String mailtoURI;
    private final String subject;

    public EmailAddressParsedResult(String str, String str2, String str3, String str4) {
        super(ParsedResultType.EMAIL_ADDRESS);
        this.emailAddress = str;
        this.subject = str2;
        this.body = str3;
        this.mailtoURI = str4;
    }

    public String getBody() {
        return this.body;
    }

    @Override // com.google.zxing.client.result.ParsedResult
    public String getDisplayResult() {
        StringBuilder sb = new StringBuilder(30);
        ParsedResult.maybeAppend(this.emailAddress, sb);
        ParsedResult.maybeAppend(this.subject, sb);
        ParsedResult.maybeAppend(this.body, sb);
        return sb.toString();
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public String getMailtoURI() {
        return this.mailtoURI;
    }

    public String getSubject() {
        return this.subject;
    }
}
