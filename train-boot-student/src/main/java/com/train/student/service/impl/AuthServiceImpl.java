package com.train.student.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.train.framework.common.cache.RedisCache;
import com.train.framework.common.constant.RedisConstant;
import com.train.framework.common.exception.ErrorCode;
import com.train.framework.common.exception.ServerException;
import com.train.student.service.AuthService;
import com.train.student.vo.StudentChangePasswordVO;
import com.train.student.vo.StudentLoginVO;
import com.train.train.constant.TrainConstant;
import com.train.train.convert.StudentConvert;
import com.train.train.entity.StudentEntity;
import com.train.train.service.StudentService;
import com.train.train.vo.StudentVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final RedisCache redisCache;
    private final StudentService studentService;

    @Override
    public String login(StudentLoginVO loginVO) {
        Validator.validateNotEmpty(loginVO.getPhone(), "手机号不能为空");
        Validator.validateNotEmpty(loginVO.getPassword(), "密码不能为空");
        checkCanLogin(loginVO.getPhone());
        List<StudentEntity> entityList = studentService.list(new LambdaQueryWrapper<StudentEntity>()
                .eq(StudentEntity::getPhone, loginVO.getPhone()).eq(StudentEntity::getPassword, loginVO.getPassword()));
        if (CollUtil.isEmpty(entityList)) {
            handleLoginErr(loginVO.getPhone());
            throw new ServerException("账号或密码错误");
        }
        StudentEntity entity = entityList.get(0);
        if (!TrainConstant.STUDENT_STATUS_NORMAL.equals(entity.getStatus())) {
            throw new ServerException(ErrorCode.REFRESH_TOKEN_INVALID.getCode(), "学员状态异常");
        }
        return handleLoginSuc(entity);
    }

    @Override
    public void logout(String token) {
        StudentVO studentVO = (StudentVO) redisCache.get(RedisConstant.STUDENT_TOKEN_ + token);
        if (studentVO == null){
            return;
        }
        if (StrUtil.isNotBlank(token)) {
            redisCache.delete(RedisConstant.STUDENT_TOKEN_ + token);
            redisCache.delete(RedisConstant.STUDENT_ID_ + studentVO.getId() + ":" + token);
        }
    }

    @Override
    public StudentVO checkToken(String token) {
        if (StrUtil.isBlank(token)) {
            throw new ServerException(ErrorCode.REFRESH_TOKEN_INVALID.getCode(), "token无效");
        }
        StudentVO vo = (StudentVO) redisCache.get(RedisConstant.STUDENT_TOKEN_ + token);
        if (vo == null) {
            throw new ServerException(ErrorCode.REFRESH_TOKEN_INVALID.getCode(), "token无效");
        }
        // 验证成功则延时
        redisCache.expire(RedisConstant.STUDENT_TOKEN_ + token, RedisConstant.STUDENT_TOKEN_EXPIRE);
        redisCache.expire(RedisConstant.STUDENT_ID_ + vo.getId() + ":" + token, RedisConstant.STUDENT_TOKEN_EXPIRE);
        vo.setPhone(DesensitizedUtil.mobilePhone(vo.getPhone()));
        vo.setAlternatePhone(DesensitizedUtil.mobilePhone(vo.getAlternatePhone()));
        return vo;
    }

    @Override
    public void changePassword(String token, StudentChangePasswordVO vo) {
        Validator.validateNotEmpty(vo.getNewPassword(), "新密码不能为空");
        Validator.validateNotEmpty(vo.getPassword(), "旧密码不能为空");
        StudentVO studentVO = (StudentVO) redisCache.get(RedisConstant.STUDENT_TOKEN_ + token);
        if (studentVO == null) {
            throw new ServerException(ErrorCode.REFRESH_TOKEN_INVALID.getCode(), "token无效");
        }
        StudentEntity entity = studentService.getById(studentVO.getId());
        if (!entity.getPassword().equals(vo.getPassword())) {
            throw new ServerException("旧密码错误");
        }
        StudentEntity up = new StudentEntity();
        up.setId(entity.getId());
        up.setPassword(vo.getNewPassword());
        studentService.updateById(up);
        kickOut(entity.getId());
    }

    @Override
    public void kickOut(Long id) {
        Set<String> keys = redisCache.keys(RedisConstant.STUDENT_ID_ + id + ":*");
        if (CollUtil.isEmpty(keys)) {
            return;
        }
        redisCache.delete(keys);
        for (String key : keys) {
            redisCache.delete(key.replace(":id:" + id + ":", ":token:"));
        }
    }

    /**
     * 判定登录锁定时间
     *
     * @param username 用户名
     */
    private void checkCanLogin(String username) {
        Long expire = redisCache.getExpire(RedisConstant.STUDENT_LOGIN_LOCK_ + username);
        if (expire > 0) {
            throw new ServerException("账号被锁定，请" + expire + "秒后重试");
        }
    }

    /**
     * 登录错误处理
     *
     * @param username 用户名
     */
    private void handleLoginErr(String username) {
        Long errCount = redisCache.increment(RedisConstant.STUDENT_LOGIN_ERR_ + username);
        if (errCount == 1L) {
            redisCache.expire(RedisConstant.STUDENT_LOGIN_ERR_ + username, 12 * 60 * 60);
        }
        if (errCount == 5L) {
            // 错误3次 锁定1分钟
            redisCache.set(RedisConstant.STUDENT_LOGIN_LOCK_ + username, errCount, 60L);
            throw new ServerException("账号被锁定，请" + 60 + "秒后重试");
        }
        if (errCount >= 6L) {
            // 错误4次 锁定5分钟
            redisCache.set(RedisConstant.STUDENT_LOGIN_LOCK_ + username, errCount, 300L);
            throw new ServerException("账号被锁定，请" + 300 + "秒后重试");
        }
    }

    /**
     * 登录成功
     *
     * @param entity
     * @return
     */
    private String handleLoginSuc(StudentEntity entity) {
        redisCache.delete(RedisConstant.STUDENT_LOGIN_ERR_ + entity.getPhone());
        String token = StrUtil.uuid().replace("-", "");
        StudentVO vo = StudentConvert.INSTANCE.convert(entity);
        redisCache.set(RedisConstant.STUDENT_TOKEN_ + token, vo, RedisConstant.STUDENT_TOKEN_EXPIRE);
        redisCache.set(RedisConstant.STUDENT_ID_ + entity.getId() + ":" + token, token, RedisConstant.STUDENT_TOKEN_EXPIRE);
        StudentEntity up = new StudentEntity();
        up.setId(entity.getId());
        up.setToken(DateUtil.format(new Date(),"yyyy-MM-dd HH:hh:ss SSS"));
        studentService.updateById(up);
        return token;
    }

}
