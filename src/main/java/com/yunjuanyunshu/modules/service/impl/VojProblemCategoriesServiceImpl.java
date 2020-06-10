package com.yunjuanyunshu.modules.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yunjuanyunshu.modules.entity.VojProblemCategories;
import com.yunjuanyunshu.modules.mapper.VojProblemCategoriesDao;
import com.yunjuanyunshu.modules.service.VojProblemCategoriesService;
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
 * @since 2017-11-15
 */
@Service
public class VojProblemCategoriesServiceImpl extends ServiceImpl<VojProblemCategoriesDao, VojProblemCategories> implements VojProblemCategoriesService {

    @Override
    public void getParentProblemCategories(RespInfo respInfo) {
        List<VojProblemCategories> problemCategories = super.selectList(new EntityWrapper<VojProblemCategories>().eq("problem_category_parent_id","0"));
        if (problemCategories == null) {
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setMsg("没有父节点");
            return;
        }
        respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        respInfo.setMsg("获取父节点成功");
        respInfo.setValue(problemCategories);
    }

    /**
     * 获取树型结构的试题分类
     * @param respInfo
     */
    @Override
    public void getTreeProblemCategoriesList(RespInfo respInfo) {
        Map<String,String> result = Maps.newHashMap();
        List<VojProblemCategories> allProblemCategories = super.selectList(new EntityWrapper<VojProblemCategories>());
        //获取一级试题分类
        List<VojProblemCategories> problemCategoriesLevelOne = getProblemCategoriesLevelOne(allProblemCategories);
        respInfo.setValue(treeProblemCategoriesChildData(allProblemCategories,problemCategoriesLevelOne));
        respInfo.setMsg("获取试题分类信息成功");
        respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
    }

    /**
     * 获取一级试题分类
     * @param problemCategories
     * @return
     */
    private List<VojProblemCategories> getProblemCategoriesLevelOne(List<VojProblemCategories> problemCategories) {
        List<VojProblemCategories> list = Lists.newArrayList();
        for (VojProblemCategories vojProblemCategories : problemCategories) {
            if (vojProblemCategories.getProblemCategoryParentId() == 0) {
                list.add(vojProblemCategories);
            }
        }
        return list;
    }

    /**
     * 获取父类下的子试题分类
     * @param problemCategories
     * @param pid
     * @return
     */
    private List<VojProblemCategories> getProblemCategoriesChild(List<VojProblemCategories> problemCategories,long pid) {
        List<VojProblemCategories> list = Lists.newArrayList();
        for (VojProblemCategories vojProblemCategories : problemCategories) {
            if (vojProblemCategories.getProblemCategoryParentId() == pid) {
                list.add(vojProblemCategories);
            }
        }
        return list;
    }

    /**
     * 递归构造试题树型
     * @param allProblemCategories
     * @param problemCategoriesLevelOne
     * @return
     */
    private List treeProblemCategoriesChildData(List<VojProblemCategories> allProblemCategories
                                                ,List<VojProblemCategories> problemCategoriesLevelOne) {
        List result = Lists.newArrayList();
        for (VojProblemCategories vojProblemCategories : problemCategoriesLevelOne) {
            List<VojProblemCategories> problemCategoriesChild = getProblemCategoriesChild(allProblemCategories
                    ,vojProblemCategories.getProblemCategoryId());
            Map map = Maps.newHashMap();
            if (problemCategoriesChild.size() > 0) {
                map.put("children",treeProblemCategoriesChildData(allProblemCategories,problemCategoriesChild));
            }
            map.put("id",vojProblemCategories.getProblemCategoryId());
            map.put("parentId",vojProblemCategories.getProblemCategoryParentId());
            map.put("name",vojProblemCategories.getProblemCategoryName());
            map.put("slug",vojProblemCategories.getProblemCategorySlug());
            result.add(map);
        }
        return result;
    }
}