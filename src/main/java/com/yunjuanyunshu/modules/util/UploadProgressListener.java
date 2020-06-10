package com.yunjuanyunshu.modules.util;

import org.apache.commons.fileupload.ProgressListener;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.NumberFormat;

/**
 * 文件进度监听类
 * @Author  carl on 2017/11/21.
 */

public class UploadProgressListener implements ProgressListener {

    private HttpSession session;
    private long kiloBytes = -1;

    public UploadProgressListener(HttpServletRequest request) {
        this.session = request.getSession();
    }

    @Override
    public void update(long PBytesRead, long PContentLength, int PItems) {

        Long KBytes = PBytesRead / 1024;
        if (kiloBytes == KBytes) {
            return;
        }
        kiloBytes = KBytes;
        System.out.println("正在读取项目" + KBytes);
        if (PContentLength == -1) {
            System.out.println("目前已经读取了" + PBytesRead + "字节数据");
        } else {
            System.out.println("目前已经读取了" + PContentLength + "中的" + PBytesRead + "字节数据");
        }
        //获取上传进度的百分比
        double read = ((double) KBytes) / (PContentLength / 1024);


        NumberFormat nf = NumberFormat.getPercentInstance();

        session.setAttribute("key", nf.format(read));

        System.out.println("进度时间:" + nf.format(read));

    }
}
