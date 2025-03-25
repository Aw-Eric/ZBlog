package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.constants.SystemConstants;
import org.example.domain.ResponseResult;
import org.example.domain.dto.AddTagDto;
import org.example.domain.dto.TagListDto;
import org.example.domain.dto.UpdateTagDto;
import org.example.domain.entity.Tag;
import org.example.domain.vo.AdminTagVo;
import org.example.domain.vo.PageVo;
import org.example.domain.vo.TagVo;
import org.example.enums.AppHttpCodeEnum;
import org.example.mapper.TagMapper;
import org.example.service.TagService;
import org.example.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2025-03-17 16:04:21
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    /**
     * 分页查询标签列表
     * @param pageNum 页码
     * @param pageSize 页面大小
     * @param tagListDto 查询条件
     * @return 标签列表
     */
    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        // 分页查询
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(tagListDto.getName()), Tag::getName, tagListDto.getName());
        queryWrapper.like(StringUtils.hasText(tagListDto.getRemark()), Tag::getRemark, tagListDto.getRemark());
        Page<Tag> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        // 封装数据返回
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(page.getRecords(), TagVo.class);
        PageVo pageVo = new PageVo(tagVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addTag(AddTagDto addTagDto) {
        // 对数据进行非空判断
        System.out.println(addTagDto);
        if (!StringUtils.hasText(addTagDto.getName())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.TAG_NAME_NULL);
        }
        if (!StringUtils.hasText(addTagDto.getRemark())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.TAG_REMARK_NULL);
        }
        // 判断标签名是否存在
        if (TagNameExist(addTagDto.getName())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.TAG_NAME_EXIST);
        }
        if (TagRemarkExist(addTagDto.getRemark())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.TAG_REMARK_EXIST);
        }
        save(BeanCopyUtils.copyBean(addTagDto, Tag.class));
        return ResponseResult.okResult();
    }

    /**
     * 删除标签
     * @param ids 标签id
     * @return 删除结果
     */
    @Override
    public ResponseResult deleteTag(List<Long> ids) {
        for(Long id : ids) {
            if (!TagExist(id)) {
                return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
            }
            else {
                Tag tag = getById(id);
                tag.setDelFlag(SystemConstants.DEL_FLAG_TRUE);
                LambdaUpdateWrapper<Tag> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(Tag::getId, id).set(Tag::getDelFlag, SystemConstants.DEL_FLAG_TRUE);
                update(updateWrapper);
            }
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getTagById(Long id) {
        if (!TagExist(id)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        else {
            return ResponseResult.okResult(BeanCopyUtils.copyBean(getById(id), TagVo.class));
        }
    }

    /**
     * 更新标签
     * @param updateAddTagDto 标签信息
     * @return 更新结果
     */
    @Override
    public ResponseResult updateTag(UpdateTagDto updateAddTagDto) {
        if (!TagExist(updateAddTagDto.getId())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        else {
            LambdaUpdateWrapper<Tag> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Tag::getId, updateAddTagDto.getId())
                    .set(StringUtils.hasText(updateAddTagDto.getName()), Tag::getName, updateAddTagDto.getName())
                    .set(StringUtils.hasText(updateAddTagDto.getRemark()), Tag::getRemark, updateAddTagDto.getRemark());
            update(updateWrapper);
            return ResponseResult.okResult();
        }
    }

    @Override
    public ResponseResult listAllTag() {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Tag::getId,Tag::getName);
        List<Tag> list = list(wrapper);
        List<AdminTagVo> tagVos = BeanCopyUtils.copyBeanList(list, AdminTagVo.class);
        return ResponseResult.okResult(tagVos);
    }

//    /**
//     * 删除标签
//     * @param id 标签id
//     * @return 删除结果
//     */
//    @Override
//    public ResponseResult deleteTag(Integer id) {
//        if (!TagExist(id)) {
//            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
//        }
//        else {
//            Tag tag = getById(id);
//            tag.setDelFlag(SystemConstants.DEL_FLAG_TRUE);
//            LambdaUpdateWrapper<Tag> updateWrapper = new LambdaUpdateWrapper<>();
//            updateWrapper.eq(Tag::getId, id).set(Tag::getDelFlag, SystemConstants.DEL_FLAG_TRUE);
//            update(updateWrapper);
//            return ResponseResult.okResult();
//        }
//    }

    /**
     * 判断标签名是否存在
     * @param name 标签名
     * @return 是否存在
     */
    private boolean TagNameExist(String name) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Tag::getName, name);
        return count(queryWrapper) > 0;
    }

    /**
     * 判断标签备注是否存在
     * @param remark 标签备注
     * @return 是否存在
     */
    private boolean TagRemarkExist(String remark) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Tag::getRemark, remark);
        return count(queryWrapper) > 0;
    }

    private boolean TagExist(Long id) {
        return Objects.nonNull(getById(id));
    }
}
