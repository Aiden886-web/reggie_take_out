package com.atdahai.reiji.controller;

import com.atdahai.reiji.common.R;
import com.atdahai.reiji.entity.Category;
import com.atdahai.reiji.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
