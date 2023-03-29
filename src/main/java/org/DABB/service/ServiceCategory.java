package org.DABB.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.DABB.entity.Category;

public interface ServiceCategory extends IService<Category> {
    public void remove(Long id);
}
