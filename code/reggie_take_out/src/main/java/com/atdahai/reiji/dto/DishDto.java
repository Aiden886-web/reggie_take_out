package com.atdahai.reiji.dto;

import com.atdahai.reiji.entity.Dish;
import com.atdahai.reiji.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
