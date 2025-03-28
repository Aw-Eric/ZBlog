package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.constants.SystemConstants;
import org.example.domain.ResponseResult;
import org.example.domain.dto.LinkListDto;
import org.example.domain.entity.Link;
import org.example.domain.vo.LinkVo;
import org.example.domain.vo.PageVo;
import org.example.enums.AppHttpCodeEnum;
import org.example.mapper.LinkMapper;
import org.example.service.LinkService;
import org.example.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2025-03-07 16:22:47
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    /**
     * 获取所有友链
     * @return 友链列表
     */
    @Override
    public ResponseResult getAllLink() {
        // 查询所有审核通过的
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(queryWrapper);
        // 转换成vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        // 封装返回
        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult pageList(Integer pageNum, Integer pageSize, LinkListDto linkListDto) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Objects.nonNull(linkListDto.getName()), Link::getName, linkListDto.getName());
        queryWrapper.eq(Objects.nonNull(linkListDto.getStatus()), Link::getStatus, linkListDto.getStatus());
        Page<Link> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        BeanCopyUtils.copyBeanList(page.getRecords(), LinkVo.class);
        return ResponseResult.okResult(new PageVo(page.getRecords(), page.getTotal()));
    }

    @Override
    public ResponseResult get(Long id) {
        Link link = getById(id);
        if (Objects.isNull(link)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        LinkVo linkVo = BeanCopyUtils.copyBean(link, LinkVo.class);
        return ResponseResult.okResult(linkVo);
    }

    @Override
    public ResponseResult updateLink(LinkVo linkVo) {
        if (Objects.isNull(linkVo.getId())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        Link link = BeanCopyUtils.copyBean(linkVo, Link.class);
        updateById(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteLink(List<Long> ids) {
        for (Long id : ids) {
            if (Objects.isNull(id)) {
                return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
            }
            LambdaUpdateWrapper<Link> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Link::getId, id).set(Link::getDelFlag, SystemConstants.DEL_FLAG_TRUE);
            update(updateWrapper);
        }
        return ResponseResult.okResult();
    }
}
