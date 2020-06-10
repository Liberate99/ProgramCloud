package com.yunjuanyunshu.modules.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.BusProduct;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.modules.mapper.BusProductDao;
import com.yunjuanyunshu.modules.service.BusProductService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunjuanyunshu.modules.service.UserService;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import com.yunjuanyunshu.yunfd.sysInfo.ServiceErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjz
 * @since 2017-07-27
 */
@Service
public class BusProductServiceImpl extends ServiceImpl<BusProductDao, BusProduct> implements BusProductService {

        @Autowired
        private UserService userService;
        @Autowired
        private BusProductService busProductService;
        @Override
        public void getPro(BusProduct busProduct, RespInfo respInfo) {
                try{

                BusProduct bsProduct= super.selectById(busProduct.getId());
                User user = userService.selectById(bsProduct.getUserId());
                bsProduct.setPhoto(user.getPhoto());
                bsProduct.setUsername(user.getLoginName());
                        respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
                        respInfo.setMsg(ServiceErrorCodeEnum.SUCCESS.getErrorStr());
                        respInfo.setValue(bsProduct);
                }catch (Exception ex){
                        respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
                        respInfo.setMsg(ServiceErrorCodeEnum.ERROR.getErrorStr());
                }



        }

        @Override
        public void getBusProductByPage(Page<BusProduct> page, RespInfo respInfo){
                try{
                        Page<BusProduct> busProductPage = busProductService.selectPage(page,
                                new EntityWrapper<BusProduct>().orderBy(page.getOrderByField(),
                                        page.isAsc()));

                        List<BusProduct> busProductList = busProductPage.getRecords();

                        for (BusProduct busProduct : busProductList) {
                                User user = userService.selectOne(new EntityWrapper<User>().eq("id", busProduct.getUserId()));
                                busProduct.setUsername(user.getName());
                        }

                        busProductPage.setRecords(busProductList);
                        respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
                        respInfo.setMsg(ServiceErrorCodeEnum.SUCCESS.getErrorStr());
                        respInfo.setValue(busProductPage);
                } catch (Exception ex) {
                        respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
                        respInfo.setMsg(ServiceErrorCodeEnum.ERROR.getErrorStr());
                }
        }
}