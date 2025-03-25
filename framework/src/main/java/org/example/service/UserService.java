package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.domain.ResponseResult;
import org.example.domain.dto.AddUserDto;
import org.example.domain.dto.ChangeUserStatusDto;
import org.example.domain.dto.UpdateUserDto;
import org.example.domain.dto.UserListDto;
import org.example.domain.entity.User;

import java.util.List;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2025-03-11 13:55:01
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult pageList(Integer pageNum, Integer pageSize, UserListDto userListDto);

    ResponseResult add(AddUserDto addUserDto);

    ResponseResult deleteUser(List<Long> id);

    ResponseResult getUserInfoById(Long id);

    ResponseResult updateUser(UpdateUserDto user);

    ResponseResult changeStatus(ChangeUserStatusDto changeUserStatusDto);
}
