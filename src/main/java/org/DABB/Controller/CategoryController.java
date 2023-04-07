package org.DABB.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.DABB.commons.R;
import org.DABB.entity.Category;
import org.DABB.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;


    /**
     * 添加
     *
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category) {
        log.info("Category:{}", category);

        categoryService.save(category);

        return R.success("添加成功");
    }

    @GetMapping("/page")
    public R<Page> pageR(int page, int pageSize) {
        Page<Category> page1 = new Page<>(page, pageSize);
//排序
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        lqw.orderByAsc(Category::getSort);

        categoryService.page(page1, lqw);

        return R.success(page1);
    }

    @DeleteMapping
    public R<String> delete(Long ids) {
        categoryService.remove(ids);
        return R.success("删除成功");
    }

    /**
     * 根据条件来查询分类数据
     *
     * @param category
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> listR(Category category) {
//        添加构造器
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
//         过滤条件
        lqw.eq(category.getType() != null, Category::getType, category.getType());
//         排序条件
        lqw.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        return R.success(categoryService.list(lqw));
    }


}
