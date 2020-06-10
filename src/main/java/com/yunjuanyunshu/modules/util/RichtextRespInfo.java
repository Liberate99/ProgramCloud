package com.yunjuanyunshu.modules.util;


import java.util.List;

/**
 * @Author  carl on 2017/11/04.
 */

public class RichtextRespInfo {
    String msg;
    int errno;
    List<String> data;

    public RichtextRespInfo() {
    }

    public RichtextRespInfo(String msg, int errno, List<String> data) {
        this.msg = msg;
        this.errno = errno;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
