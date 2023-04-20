package org.DABB.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.statement.alter.ValidateConstraint;
import org.DABB.commons.BaseContext;
import org.DABB.commons.R;
import org.DABB.dto.OrderDto;
import org.DABB.entity.OrderDetail;
import org.DABB.entity.Orders;
import org.DABB.entity.User;
import org.DABB.service.OrderDetailService;
import org.DABB.service.OrdersService;
import org.DABB.service.UserService;
import org.DABB.utils.ValidateCodeUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private OrdersService orderService;
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
//        获取手机号
        String phone = user.getPhone();

        if (Strings.isNotEmpty(phone)) {
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code:{}", code);

            session.setAttribute(phone, code);
            return R.success("手机验证码短信发送成功");
        }
        return R.error("手机验证码短信发送失败");

    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session) {
        //从前端获取手机号与验证码
        String phone = map.get("phone").toString();
        String code = map.get("code").toString();

        // session获取保存的验证码
        Object codeInSession = session.getAttribute(phone);

        if (codeInSession != null && codeInSession.equals(code)) {
            //
            LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
            lqw.eq(User::getPhone, phone);
            //
            User user = userService.getOne(lqw);

            if (user == null) {
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user", user.getId());
            return R.success(user);
        }
        return R.error("失败");
    }

    @PostMapping("/loginout")
    public R<String> loginout(HttpSession session) {
        session.removeAttribute("user");
        return R.success("登出成功");
    }

    //抽离的一个方法，通过订单id查询订单明细，得到一个订单明细的集合
    //这里抽离出来是为了避免在stream中遍历的时候直接使用构造条件来查询导致eq叠加，从而导致后面查询的数据都是null
    public List<OrderDetail> getOrderDetailListByOrderId(Long orderId) {
        LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDetail::getOrderId, orderId);
        List<OrderDetail> orderDetailList = orderDetailService.list(queryWrapper);
        return orderDetailList;
    }

    /**
     * 用户端展示自己的订单分页查询
     *
     * @param page
     * @param pageSize
     * @return 遇到的坑：原来分页对象中的records集合存储的对象是分页泛型中的对象，里面有分页泛型对象的数据
     * 开始的时候我以为前端只传过来了分页数据，其他所有的数据都要从本地线程存储的用户id开始查询，
     * 结果就出现了一个用户id查询到 n个订单对象，然后又使用 n个订单对象又去查询 m 个订单明细对象，
     * 结果就出现了评论区老哥出现的bug(嵌套显示数据....)
     * 正确方法:直接从分页对象中获取订单id就行，问题大大简化了......
     */
    @GetMapping("/userPage")
    public R<Page> page(int page, int pageSize) {
        //分页构造器对象
        Page<Orders> pageInfo = new Page<>(page, pageSize);
        Page<OrderDto> pageDto = new Page<>(page, pageSize);
        //构造条件查询对象
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getUserId, BaseContext.getCurrentId());
        //这里是直接把当前用户分页的全部结果查询出来，要添加用户id作为查询条件，否则会出现用户可以查询到其他用户的订单情况
        //添加排序条件，根据更新时间降序排列
        queryWrapper.orderByDesc(Orders::getOrderTime);
        orderService.page(pageInfo, queryWrapper);

        //通过OrderId查询对应的OrderDetail
        LambdaQueryWrapper<OrderDetail> queryWrapper2 = new LambdaQueryWrapper<>();

        //对OrderDto进行需要的属性赋值
        List<Orders> records = pageInfo.getRecords();
        List<OrderDto> orderDtoList = records.stream().map((item) -> {
            OrderDto orderDto = new OrderDto();
            //此时的orderDto对象里面orderDetails属性还是空 下面准备为它赋值
            Long orderId = item.getId();//获取订单id
            List<OrderDetail> orderDetailList = this.getOrderDetailListByOrderId(orderId);
            BeanUtils.copyProperties(item, orderDto);
            //对orderDto进行OrderDetails属性的赋值
            orderDto.setOrderDetails(orderDetailList);
            return orderDto;
        }).collect(Collectors.toList());

        //使用dto的分页有点难度.....需要重点掌握
        BeanUtils.copyProperties(pageInfo, pageDto, "records");
        pageDto.setRecords(orderDtoList);
        return R.success(pageDto);
    }
}
