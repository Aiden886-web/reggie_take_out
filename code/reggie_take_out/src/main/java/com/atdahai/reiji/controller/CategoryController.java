package com.atdahai.reiji.controller;

import com.atdahai.reiji.common.R;
import com.atdahai.reiji.entity.Category;
import com.atdahai.reiji.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category){
        if(category != null){
            if(category.getType() == 1){
                categoryService.save(category);
                return R.success("添加菜品成功");
            }else{
                return R.success("添加套餐成功");
            }
        }else{
            return R.error("添加失败");
        }
    }

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page , int pageSize){
        //分页构造器
        Page<Category> pages= new Page<>(page , pageSize);
        //条件构造器
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        //添加排序条件，根据sort进行排序
        wrapper.orderByAsc(Category::getSort);
        //进行排序查询
        categoryService.page(pages,wrapper);
        return R.success(pages);
    }

    /**
     *
     *
     * @PutMapping
     *     public R<String> delete(long id){
     *         boolean flag = categoryService.removeById(id);
     *         if(flag){
     *             return R.success("删除成功");
     *         }else{
     *             return R.error("删除失败");
     *         }
     *     }
     *     =====================================
     * @param id
     * @return
     */
    //拿不到id
    @DeleteMapping
    public R<String> delete(Long id){
        log.info("删除分类,id为：{}",id);
        //categoryService.removeById(id);
        categoryService.remove(id);
        return R.success("删除分类成功");
    }

    @PutMapping
    public R<String> update(Category category){
        log.info("修改分类，{}" , category);
        categoryService.updateById(category);
        return R.success("修改分类信息成功");
    }

    /**
     * 根据条件查询分类数据
     * @param category
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(category.getType()!= null , Category::getType , category.getType());
        //添加排序条件
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(queryWrapper);
        return R.success(list);
    }
}
