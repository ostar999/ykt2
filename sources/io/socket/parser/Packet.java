package io.socket.parser;

/* loaded from: classes8.dex */
public class Packet<T> {
    public int attachments;
    public T data;
    public int id;
    public String nsp;
    public String query;
    public int type;

    public Packet() {
        this.type = -1;
        this.id = -1;
    }

    public Packet(int i2) {
        this.id = -1;
        this.type = i2;
    }

    public Packet(int i2, T t2) {
        this.id = -1;
        this.type = i2;
        this.data = t2;
    }
}
