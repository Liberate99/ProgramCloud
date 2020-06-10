package com.yunjuanyunshu.modules.service;

import com.yunjuanyunshu.modules.entity.VojProblemCategories;
import com.baomidou.mybatisplus.service.IService;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author carl
 * @since 2017-11-15
 */
public interface VojProblemCategoriesService extends IService<VojProblemCategories> {

    void getParentProblemCategories(RespInfo respInfo);
    void getTreeProblemCategoriesList(RespInfo respInfo);
}