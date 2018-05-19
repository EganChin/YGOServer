package com.ygo.model;

import com.ygo.constant.MessageType;

/**
 * 自定义协议YGOP的数据包类
 *
 * @author Egan
 * @date 2018/5/19 11:02
 **/
public class DataPaket {

    /**
     * 版本号
     **/
    private int version;

    /**
     * 消息类型
     **/
    private MessageType type;

    /**
     * 魔数校验码
     **/
    private int magic;

    /**
     * 消息体长度
     **/
    private int len;

    /**
     * 消息体
     **/
    private String body;

    public DataPaket(MessageType type, String body) {
        this.type = type;
        this.body = body;
    }

    public DataPaket(int version, MessageType type, int magic, int len, String body) {
        this.version = version;
        this.type = type;
        this.magic = magic;
        this.len = len;
        this.body = body;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public int getMagic() {
        return magic;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
