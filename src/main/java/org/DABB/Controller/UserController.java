package org.DABB.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.statement.alter.ValidateConstraint;
import org.DABB.commons.R;
import org.DABB.entity.User;
import org.DABB.service.UserService;
import org.DABB.utils.ValidateCodeUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

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
}
