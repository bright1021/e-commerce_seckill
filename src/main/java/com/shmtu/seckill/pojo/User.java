package com.shmtu.seckill.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Bright
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户I的，手机号码
     */
    private Long id;

    private String nickname;

    /**
     * MD5加密（MD5加密(pasa明文+固定salt)+固定salt)
     */
    private String password;

    private String salt;

    /**
     * 头像
     */
    private String head;

    /**
     * 注册时间
     */
    private Date registerDate;

    private Integer loginCount;

    /**
     * 最后登陆时间
     */
    private Date lastLoginDate;


}
