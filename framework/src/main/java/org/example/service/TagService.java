package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.domain.ResponseResult;
import org.example.domain.dto.AddTagDto;
import org.example.domain.dto.TagListDto;
import org.example.domain.dto.UpdateTagDto;
import org.example.domain.entity.Tag;
import org.example.domain.vo.PageVo;

import java.util.List;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2025-03-17 16:04:21
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(AddTagDto addTagDto);

    ResponseResult deleteTag(List<Long> id);

    ResponseResult getTagById(Long id);

    ResponseResult updateTag(UpdateTagDto updateAddTagDto);

    ResponseResult listAllTag();
}
