package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.domain.ResponseResult;
import org.example.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2025-03-07 16:22:47
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}
