package com.atdahai.reiji.service;

import com.atdahai.reiji.dto.DishDto;
import com.atdahai.reiji.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DishService extends IService<Dish> {

    //新增菜品，同时插入对应的口味数据，需要操作两张表：dish  ，dish_flavor
    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);

}
