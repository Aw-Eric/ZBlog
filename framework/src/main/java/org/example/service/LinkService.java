package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.domain.ResponseResult;
import org.example.domain.dto.LinkListDto;
import org.example.domain.entity.Link;
import org.example.domain.vo.LinkVo;

import java.util.List;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2025-03-07 16:22:47
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult pageList(Integer pageNum, Integer pageSize, LinkListDto linkListDto);

    ResponseResult get(Long id);

    ResponseResult updateLink(LinkVo linkVo);

    ResponseResult deleteLink(List<Long> id);
}
