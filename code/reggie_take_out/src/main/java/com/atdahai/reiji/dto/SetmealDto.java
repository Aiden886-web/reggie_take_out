package com.atdahai.reiji.dto;

import com.atdahai.reiji.entity.Setmeal;
import com.atdahai.reiji.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
