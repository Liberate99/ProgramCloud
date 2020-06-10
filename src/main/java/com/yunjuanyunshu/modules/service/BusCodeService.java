package com.yunjuanyunshu.modules.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.BusChapter;
import com.yunjuanyunshu.modules.entity.BusCode;
import com.baomidou.mybatisplus.service.IService;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xjz
 * @since 2017-07-04
 */
public interface BusCodeService extends IService<BusCode> {
        void getCodeListByChapter(Page<BusCode> page, BusChapter chapter, RespInfo respInfo);
        void getCodeListToFront(BusChapter chapter, RespInfo respInfo);
        void insertOrUpdateCode(User user,BusChapter busChapter, BusCode busCode, RespInfo respInfo);
        void deleteCode(BusCode busCode, RespInfo respInfo);
        void getByChapterId(BusCode buscode, RespInfo respInfo);
        }