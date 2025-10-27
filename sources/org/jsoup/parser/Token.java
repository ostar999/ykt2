package org.jsoup.parser;

import org.jsoup.helper.Validate;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;

/* loaded from: classes9.dex */
abstract class Token {
    TokenType type;

    public static class Character extends Token {
        private final String data;

        public Character(String str) {
            super();
            this.type = TokenType.Character;
            this.data = str;
        }

        public String getData() {
            return this.data;
        }

        public String toString() {
            return getData();
        }
    }

    public static class Comment extends Token {
        boolean bogus;
        final StringBuilder data;

        public Comment() {
            super();
            this.data = new StringBuilder();
            this.bogus = false;
            this.type = TokenType.Comment;
        }

        public String getData() {
            return this.data.toString();
        }

        public String toString() {
            return "<!--" + getData() + "-->";
        }
    }

    public static class Doctype extends Token {
        boolean forceQuirks;
        final StringBuilder name;
        final StringBuilder publicIdentifier;
        final StringBuilder systemIdentifier;

        public Doctype() {
            super();
            this.name = new StringBuilder();
            this.publicIdentifier = new StringBuilder();
            this.systemIdentifier = new StringBuilder();
            this.forceQuirks = false;
            this.type = TokenType.Doctype;
        }

        public String getName() {
            return this.name.toString();
        }

        public String getPublicIdentifier() {
            return this.publicIdentifier.toString();
        }

        public String getSystemIdentifier() {
            return this.systemIdentifier.toString();
        }

        public boolean isForceQuirks() {
            return this.forceQuirks;
        }
    }

    public static class EOF extends Token {
        public EOF() {
            super();
            this.type = TokenType.EOF;
        }
    }

    public static abstract class Tag extends Token {
        Attributes attributes;
        private String pendingAttributeName;
        private StringBuilder pendingAttributeValue;
        boolean selfClosing;
        protected String tagName;

        public Tag() {
            super();
            this.selfClosing = false;
        }

        private final void ensureAttributeValue() {
            if (this.pendingAttributeValue == null) {
                this.pendingAttributeValue = new StringBuilder();
            }
        }

        public void appendAttributeName(String str) {
            String str2 = this.pendingAttributeName;
            if (str2 != null) {
                str = str2.concat(str);
            }
            this.pendingAttributeName = str;
        }

        public void appendAttributeValue(String str) {
            ensureAttributeValue();
            this.pendingAttributeValue.append(str);
        }

        public void appendTagName(String str) {
            String str2 = this.tagName;
            if (str2 != null) {
                str = str2.concat(str);
            }
            this.tagName = str;
        }

        public void finaliseTag() {
            if (this.pendingAttributeName != null) {
                newAttribute();
            }
        }

        public Attributes getAttributes() {
            return this.attributes;
        }

        public boolean isSelfClosing() {
            return this.selfClosing;
        }

        public String name() {
            String str = this.tagName;
            Validate.isFalse(str == null || str.length() == 0);
            return this.tagName;
        }

        public void newAttribute() {
            if (this.attributes == null) {
                this.attributes = new Attributes();
            }
            if (this.pendingAttributeName != null) {
                this.attributes.put(this.pendingAttributeValue == null ? new Attribute(this.pendingAttributeName, "") : new Attribute(this.pendingAttributeName, this.pendingAttributeValue.toString()));
            }
            this.pendingAttributeName = null;
            StringBuilder sb = this.pendingAttributeValue;
            if (sb != null) {
                sb.delete(0, sb.length());
            }
        }

        public void appendAttributeName(char c3) {
            appendAttributeName(String.valueOf(c3));
        }

        public void appendTagName(char c3) {
            appendTagName(String.valueOf(c3));
        }

        public void appendAttributeValue(char c3) {
            ensureAttributeValue();
            this.pendingAttributeValue.append(c3);
        }

        public Tag name(String str) {
            this.tagName = str;
            return this;
        }

        public void appendAttributeValue(char[] cArr) {
            ensureAttributeValue();
            this.pendingAttributeValue.append(cArr);
        }
    }

    public enum TokenType {
        Doctype,
        StartTag,
        EndTag,
        Comment,
        Character,
        EOF
    }

    public Character asCharacter() {
        return (Character) this;
    }

    public Comment asComment() {
        return (Comment) this;
    }

    public Doctype asDoctype() {
        return (Doctype) this;
    }

    public EndTag asEndTag() {
        return (EndTag) this;
    }

    public StartTag asStartTag() {
        return (StartTag) this;
    }

    public boolean isCharacter() {
        return this.type == TokenType.Character;
    }

    public boolean isComment() {
        return this.type == TokenType.Comment;
    }

    public boolean isDoctype() {
        return this.type == TokenType.Doctype;
    }

    public boolean isEOF() {
        return this.type == TokenType.EOF;
    }

    public boolean isEndTag() {
        return this.type == TokenType.EndTag;
    }

    public boolean isStartTag() {
        return this.type == TokenType.StartTag;
    }

    public String tokenType() {
        return getClass().getSimpleName();
    }

    public static class EndTag extends Tag {
        public EndTag() {
            this.type = TokenType.EndTag;
        }

        public String toString() {
            return "</" + name() + ">";
        }

        public EndTag(String str) {
            this();
            this.tagName = str;
        }
    }

    private Token() {
    }

    public static class StartTag extends Tag {
        public StartTag() {
            this.attributes = new Attributes();
            this.type = TokenType.StartTag;
        }

        public String toString() {
            Attributes attributes = this.attributes;
            if (attributes == null || attributes.size() <= 0) {
                return "<" + name() + ">";
            }
            return "<" + name() + " " + this.attributes.toString() + ">";
        }

        public StartTag(String str) {
            this();
            this.tagName = str;
        }

        public StartTag(String str, Attributes attributes) {
            this();
            this.tagName = str;
            this.attributes = attributes;
        }
    }
}
