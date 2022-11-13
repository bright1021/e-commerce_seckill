package com.shmtu.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shmtu.seckill.exception.GlobalException;
import com.shmtu.seckill.mapper.UserMapper;
import com.shmtu.seckill.pojo.User;
import com.shmtu.seckill.service.IUserService;
import com.shmtu.seckill.utils.CookieUtil;
import com.shmtu.seckill.utils.MD5Util;
import com.shmtu.seckill.utils.UUIDUtil;
import com.shmtu.seckill.vo.LoginVo;
import com.shmtu.seckill.vo.RespBean;
import com.shmtu.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Bright
 */
@Service
@Primary
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {

        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        //参数校验
        //在这里改为了  validation 注解参数检验了
//        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
//            return RespBean.error(RespBeanEnum.LOGIN_ERROR_EMPTY);
//        }
//        if(!ValidatorUtil.isMobile(mobile)){
//            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
//        }

        User user = userMapper.selectById(mobile);
        if (user == null) {
            //return RespBean.error(RespBeanEnum.LOGIN_ERROR);
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);//500,"服务异常"
        }
        if(!MD5Util.formPassToDBPass(password,user.getSalt()).equals(user.getPassword())){
            //return RespBean.error(RespBeanEnum.LOGIN_ERROR);

            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);//500,"服务异常"
        }
        //生成cookie值
        String ticket = UUIDUtil.uuid();
        //讲用户信息存入redis中实现session分布
        redisTemplate.opsForValue().set("user:"+ticket,user);
        //request.getSession().setAttribute(ticket,user);

        CookieUtil.setCookie(request,response,"userTicket",ticket);


        return RespBean.success(ticket);

    }

    @Override
    public User getUserByCookie(String userTicket,HttpServletRequest request, HttpServletResponse response) {
        if (userTicket == null) {
            return null;
        }
        User user = (User) redisTemplate.opsForValue().get("user:"+userTicket);

        if (user != null) {
            CookieUtil.setCookie(request,response,"userTicket",userTicket);
        }
        return user;
    }

    @Override
    public RespBean updatePassword(String userTicket, String password, HttpServletRequest request, HttpServletResponse response) {
        User user = getUserByCookie(userTicket, request, response);
        if (user == null) {
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        user.setPassword(MD5Util.inputPassToDBPass(password, user.getSalt()));
        int result = userMapper.updateById(user);
        if (1 == result) {
            //删除Redis
            redisTemplate.delete("user:" + userTicket);
            return RespBean.success();
        }
        return RespBean.error(RespBeanEnum.PASSWORD_UPDATE_FAIL);
    }


}
