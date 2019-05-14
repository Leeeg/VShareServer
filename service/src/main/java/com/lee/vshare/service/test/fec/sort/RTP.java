package com.lee.vshare.service.test.fec.sort;

import java.util.Arrays;

/**
 * @Create by lee
 * @emil JefferyLeeeg@gmail.com
 * @Date 19-3-4
 * @Time 上午10:43
 */
public class RTP implements Comparable<RTP> {

    private Integer seq;
    private long time;
    private byte[] data;
    private int type;

    private int groupNumber;

    public RTP() {
    }

    public RTP(Integer seq, long time, byte[] data, int type) {
        this.seq = seq;
        this.time = time;
        this.data = data;
        this.type = type;
    }

    public RTP(Integer seq, long time, byte[] data, int type, int groupNumber) {
        this.seq = seq;
        this.time = time;
        this.data = data;
        this.type = type;
        this.groupNumber = groupNumber;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    public Integer getSeq() {
        return seq;
    }

    public RTP setSeq(Integer seq) {
        this.seq = seq;
        return this;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public byte[] getData() {
        return data;
    }

    public RTP setData(byte[] data) {
        this.data = data;
        return this;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    @Override
    public int compareTo(RTP o) {
        if (null == this.seq) {
            return -1;
        }
        if (null == o.getSeq()) {
            return 1;
        }
        return this.seq.compareTo(o.getSeq());
    }

    @Override
    public String toString() {
        return "RTP{" +
                "groupNumber=" + groupNumber +
                ", seq=" + seq +
                ", time=" + time +
                ", data=" + Arrays.toString(data) +
                ", type=" + type +
                '}';
    }
}