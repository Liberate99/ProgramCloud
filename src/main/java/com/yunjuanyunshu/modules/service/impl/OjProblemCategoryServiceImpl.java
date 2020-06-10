package com.yunjuanyunshu.modules.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yunjuanyunshu.modules.common.ResponseCode;
import com.yunjuanyunshu.modules.entity.OjProblem;
import com.yunjuanyunshu.modules.entity.OjProblemCategory;
import com.yunjuanyunshu.modules.entity.VojProblemCategories;
import com.yunjuanyunshu.modules.mapper.OjProblemCategoryDao;
import com.yunjuanyunshu.modules.service.OjProblemCategoryService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import com.yunjuanyunshu.yunfd.sysInfo.ServiceErrorCodeEnum;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author carl
 * @since 2018-02-12
 */
@Service
public class OjProblemCategoryServiceImpl extends ServiceImpl<OjProblemCategoryDao, OjProblemCategory> implements OjProblemCategoryService {

    @Override
    public void getAllProblemCategoryList(RespInfo respInfo) {
        List<OjProblemCategory> problemCategories = super.selectList(new EntityWrapper<OjProblemCategory>());
        respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        respInfo.setMsg("获取所有试题分类成功");
        respInfo.setValue(problemCategories);
    }

    @Override
    public void getParentProblemCategory(RespInfo respInfo) {
        List<OjProblemCategory> problemCategories = super.selectList(new EntityWrapper<OjProblemCategory>().eq("parent_id",0));
        if (problemCategories == null) {
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setMsg("没有父节点");
            return;
        }
        respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        respInfo.setMsg("获取父节点成功");
        respInfo.setValue(problemCategories);
    }

    @Override
    public void getTreeList(RespInfo respInfo) {
        Map<String, String> result = Maps.newHashMap();
        List<OjProblemCategory> pcAll = super.selectList(new EntityWrapper<>());

        List<OjProblemCategory> pcTop = getListTop(pcAll);

        respInfo.setValue(makeTreeList(pcAll, pcTop));
        respInfo.setMsg("获取试题分类树型信息成功");
        respInfo.setCode(ResponseCode.SUCCESS.getCode());

    }

    private List<OjProblemCategory> getListTop(List<OjProblemCategory> pcAll) {
        List<OjProblemCategory> resultList = Lists.newArrayList();

        for (OjProblemCategory pcItem : pcAll) {
            if (pcItem.getParentId() == 0) {
                resultList.add(pcItem);
            }
        }
        return resultList;
    }

    private List<OjProblemCategory> getListChild(List<OjProblemCategory> pcAll, int pid) {
        List<OjProblemCategory> resultList = Lists.newArrayList();

        for (OjProblemCategory pcItem : pcAll) {
            if (pcItem.getParentId() == pid) {
                resultList.add(pcItem);
            }
        }
        return resultList;
    }

    private List makeTreeList(List<OjProblemCategory> pcAll, List<OjProblemCategory> pcParent) {
        List resultList = Lists.newArrayList();
        for (OjProblemCategory pcItem : pcParent) {
            List<OjProblemCategory> pcChild = getListChild(pcAll, pcItem.getId());
            Map map = Maps.newHashMap();
            if (pcChild.size() > 0) {
                map.put("children", makeTreeList(pcAll, pcChild));
            }
            map.put("id", pcItem.getId());
            map.put("parentId", pcItem.getParentId());
            map.put("name", pcItem.getCategoryName());
            map.put("slug", pcItem.getCategorySlug());
            resultList.add(map);
        }
        return resultList;
    }
}