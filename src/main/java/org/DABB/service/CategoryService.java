package org.DABB.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.DABB.entity.Category;

public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}
