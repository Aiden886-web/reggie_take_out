package com.atdahai.reiji.service.impl;

import com.atdahai.reiji.entity.Category;
import com.atdahai.reiji.mapper.CategoryMapper;
import com.atdahai.reiji.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper , Category> implements CategoryService {

}
