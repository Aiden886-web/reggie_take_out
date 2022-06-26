package com.atdahai.reiji.service.impl;

import com.atdahai.reiji.common.CustomException;
import com.atdahai.reiji.dto.SetmealDto;
import com.atdahai.reiji.entity.Setmeal;
import com.atdahai.reiji.entity.SetmealDish;
import com.atdahai.reiji.mapper.SetmealMapper;
import com.atdahai.reiji.service.SetMealDishService;
import com.atdahai.reiji.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper , Setmeal>  implements SetmealService {
    @Autowired
    private SetMealDishService setMealDishService;

    public void saveWithDish(SetmealDto setmealDto){
        //保存套餐的基本信息 ， 操作setmeal  ， 执行insert操作
        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) ->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        //保存套餐和菜品的关联信息，操作setmeal_dish，执行insert操作
        setMealDishService.saveBatch(setmealDishes);
    }

    @Override
    public void removeWithDish(List<Long> ids) {
        //select count(*) from setmeal where id in (1,2,3) and status = 1
        //查询套餐的状态，确认是否可以删除
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,  ids);
        //等值查询
        queryWrapper.eq(Setmeal::getStatus , 1);
        int count = this.count(queryWrapper);

        //如果不能删除，抛出一个业务异常
        if(count > 0 ){
            throw new CustomException("套餐正在售卖中，不能删除");
        }
        //如果可以删除，先删除套餐表中的数据 -- setmeal
        this.removeByIds(ids);
        //删除关系表中的数据  -- setmeal_dish
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId , ids);
        //setmealDishService
        setMealDishService.remove(lambdaQueryWrapper);
    }
}
