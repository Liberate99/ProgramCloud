package com.yunjuanyunshu.modules.service;

import com.yunjuanyunshu.modules.entity.OjProblemCategory;
import com.baomidou.mybatisplus.service.IService;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author carl
 * @since 2018-02-12
 */
public interface OjProblemCategoryService extends IService<OjProblemCategory> {

    void getTreeList(RespInfo respInfo);

    void getParentProblemCategory(RespInfo respInfo);

    void getAllProblemCategoryList(RespInfo respInfo);
}