package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.domain.ResponseResult;
import org.example.domain.dto.UserListDto;
import org.example.domain.entity.User;
import org.example.domain.vo.AdminUserVo;
import org.example.domain.vo.PageVo;
import org.example.domain.vo.UserInfoVo;
import org.example.enums.AppHttpCodeEnum;
import org.example.exception.SystemException;
import org.example.mapper.UserMapper;
import org.example.service.UserService;
import org.example.utils.BeanCopyUtils;
import org.example.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;


/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2025-03-11 13:55:02
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 获取用户信息
     * @return 用户信息
     */
    @Override
    public ResponseResult userInfo() {
        // 获取当前用户id
        Long userId = SecurityUtils.getUserId();
        // 根据用户id查询用户信息
        User user = getById(userId);
        // 封装成UserInfoVo
        UserInfoVo vo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    /**
     * 注册
     * @param user 用户信息
     * @return 注册结果
     */
    @Override
    public ResponseResult register(User user) {
        // 对数据进行非空判断
        if (!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        // 对数据进行是否存在的判断
        if (UsernameExist(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (NickNameExist(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        if (EmailExist(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        // 对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        // 存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    /**
     * 分页查询用户列表
     *
     * @param pageNum     页码
     * @param pageSize    页面大小
     * @param userListDto 查询条件
     * @return 用户列表
     */
    @Override
    public ResponseResult pageList(Integer pageNum, Integer pageSize, UserListDto userListDto) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Objects.nonNull(userListDto.getUserName()), User::getUserName, userListDto.getUserName())
                .like(Objects.nonNull(userListDto.getPhonenumber()), User::getPhonenumber, userListDto.getPhonenumber())
                .eq(Objects.nonNull(userListDto.getStatus()), User::getStatus, userListDto.getStatus());
        Page<User> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<AdminUserVo> adminUserVos = BeanCopyUtils.copyBeanList(page.getRecords(), AdminUserVo.class);
        return ResponseResult.okResult(new PageVo(adminUserVos, page.getTotal()));
    }

    private boolean EmailExist(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        return count(queryWrapper) > 0;
    }

    private boolean NickNameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName, nickName);
        return count(queryWrapper) > 0;
    }

    private boolean UsernameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, userName);
        return count(queryWrapper) > 0;
    }
}
