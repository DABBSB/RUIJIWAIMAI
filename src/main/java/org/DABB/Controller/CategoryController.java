package org.DABB.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.DABB.commons.R;
import org.DABB.entity.Category;
import org.DABB.service.ServiceCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    final
    ServiceCategory serviceCategory;

    public CategoryController(ServiceCategory serviceCategory) {
        this.serviceCategory = serviceCategory;
    }
    /**
     * 添加
     *
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category) {
        log.info("Category:{}", category);

        serviceCategory.save(category);

        return R.success("添加成功");
    }

    @GetMapping("/page")
    public R<Page> pageR(int page, int pageSize) {
        Page<Category> page1 = new Page<>(page, pageSize);
//排序
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        lqw.orderByAsc(Category::getSort);

        serviceCategory.page(page1, lqw);

        return R.success(page1);
    }

    @DeleteMapping
    public R<String> delete(Long ids) {
        serviceCategory.removeById(ids);
        return R.success("删除成功");
    }
}
