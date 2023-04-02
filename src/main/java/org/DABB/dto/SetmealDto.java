package org.DABB.dto;


import lombok.Data;
import org.DABB.entity.Setmeal;
import org.DABB.entity.SetmealDish;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
