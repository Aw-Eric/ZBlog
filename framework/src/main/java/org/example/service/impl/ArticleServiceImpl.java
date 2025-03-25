package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.constants.SystemConstants;
import org.example.domain.ResponseResult;
import org.example.domain.dto.AddArticleDto;
import org.example.domain.dto.ArticleListDto;
import org.example.domain.dto.UpdateArticleDto;
import org.example.domain.entity.Article;
import org.example.domain.entity.ArticleTag;
import org.example.domain.vo.*;
import org.example.enums.AppHttpCodeEnum;
import org.example.mapper.ArticleMapper;
import org.example.service.ArticleService;
import org.example.service.ArticleTagService;
import org.example.service.CategoryService;
import org.example.utils.BeanCopyUtils;
import org.example.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    @Lazy
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleTagService articleTagService;

    /**
     * 查询热门文章
     * @return 热门文章列表
     */
    @Override
    public ResponseResult hotArticleList() {
        // 查询热门文章，封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        // 最多只查询十条
        Page<Article> page = new Page<>(1, SystemConstants.HOT_ARTICLE_SIZE);
        page = page(page, queryWrapper);
        List<Article> articles = page.getRecords();
        // 从redis中获取viewCount
        for (Article article : articles) {
            Integer viewCount = redisCache.getCacheMapValue(SystemConstants.VIEW_COUNT_PREFIX, article.getId().toString());
            article.setViewCount(viewCount.longValue());
        }
        // Bean拷贝
//        List<HotArticleVo> articleVos = new ArrayList<>();
//        for (Article article : articles) {
//            HotArticleVo vo = new HotArticleVo();
//            BeanUtils.copyProperties(article, vo);
//            articleVos.add(vo);
//        }
        List<HotArticleVo> vo = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return ResponseResult.okResult(vo);
    }

    /**
     * 查询文章列表
     * @param pageNum 页码
     * @param pageSize 每页显示数量
     * @param categoryId 分类ID
     * @return 文章列表
     */
    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        // 查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 如果有categoryId，查询时要和传入的相同
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        // 状态是正式发布的
        lambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 对isTop进行降序排序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);
        // 分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, lambdaQueryWrapper);
        List<Article> articles = page.getRecords();
        // 从redis中获取viewCount
        for (Article article : articles) {
            Integer viewCount = redisCache.getCacheMapValue(SystemConstants.VIEW_COUNT_PREFIX, article.getId().toString());
            article.setViewCount(viewCount.longValue());
        }
        // 查询categoryName
         articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());
        // articleId去查询articleName进行设置
//        for (Article article : articles) {
//            Category category = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        }
        // 封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    /**
     * 查询文章详情
     * @param id 文章ID
     * @return 文章详情
     */
    @Override
    public ResponseResult getArticleDetail(Long id) {
        // 根据id查询文章
        Article article = getById(id);
        // 从redis中获取viewCount
        Integer viewCount = redisCache.getCacheMapValue(SystemConstants.VIEW_COUNT_PREFIX, id.toString());
        article.setViewCount(viewCount.longValue());
        // 转换成VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        // 根据分类id查询分类名称
        Long categoryId = articleDetailVo.getCategoryId();
        categoryService.getById(categoryId);
        if (categoryId != null) {
            articleDetailVo.setCategoryId(categoryId);
        }
        // 封装响应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    /**
     * 更新redis中文章浏览量
     * @param id 文章ID
     * @return 更新结果
     */
    @Override
    public ResponseResult updateViewCount(Long id) {
        // 更新redis中对应id的浏览量
        redisCache.incrementCacheMapValue(SystemConstants.VIEW_COUNT_PREFIX, id.toString(), 1);
        return ResponseResult.okResult();
    }

    /**
     * 添加文章
     * @param addArticleDto 文章信息
     * @return 添加结果
     */
    @Override
    @Transactional
    public ResponseResult add(AddArticleDto addArticleDto) {
        // 添加博客
        Article article = BeanCopyUtils.copyBean(addArticleDto, Article.class);
        save(article);
        List<ArticleTag> articleTags = addArticleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .toList();
        // 添加博客和标签的关联
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    /**
     * 分页查询
     * @param pageNum 分页
     * @param pageSize 分页大小
     * @param articleListDto 查询条件
     * @return 查询结果
     */
    @Override
    public ResponseResult pageList(Integer pageNum, Integer pageSize, ArticleListDto articleListDto) {
        // 分页查询
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(articleListDto.getTitle()), Article::getTitle, articleListDto.getTitle());
        queryWrapper.like(StringUtils.hasText(articleListDto.getSummary()), Article::getSummary, articleListDto.getSummary());
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        // 封装数据返回
        List<AdminArticleListVo> vo = BeanCopyUtils.copyBeanList(page.getRecords(), AdminArticleListVo.class);
        PageVo pageVo = new PageVo(vo, page.getTotal());
        return ResponseResult.okResult(pageVo);


    }

    /**
     * 查询文章详情
     * @param id 文章ID
     * @return 文章详情
     */
    @Override
    public ResponseResult detail(Long id) {
        // 根据id查询文章
        Article article = getById(id);
        // 转换成VO
        if (Objects.isNull(article)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.ARTICLE_NOT_FOUND);
        }
        AdminArticleDetailVo vo = BeanCopyUtils.copyBean(article, AdminArticleDetailVo.class);
        // 查询标签
        List<Long> tags = articleTagService.listTagsIdByArticleId(id);
        vo.setTags(tags);
        // 封装响应返回
        return ResponseResult.okResult(vo);
    }

    /**
     * 更新文章
     * @param updateArticleDto 文章信息
     * @return 更新结果
     */
    @Override
    public ResponseResult updateArticle(UpdateArticleDto updateArticleDto) {
        // 检测非空
        if (Objects.isNull(updateArticleDto.getId())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.ARTICLE_NOT_FOUND);
        }
        // 更新文章
        Article article = BeanCopyUtils.copyBean(updateArticleDto, Article.class);
        updateById(article);
        // 更新标签
        articleTagService.remove(new LambdaQueryWrapper<ArticleTag>().eq(ArticleTag::getArticleId, article.getId()));
        List<ArticleTag> articleTags = updateArticleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .toList();
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    /**
     * 删除文章
     * @param id 文章ID
     * @return 删除结果
     */
    @Override
    public ResponseResult deleteArticle(List<Long> id) {
        for (Long articleId : id) {
            if (Objects.isNull(getById(articleId))) {
                return ResponseResult.errorResult(AppHttpCodeEnum.ARTICLE_NOT_FOUND);
            }
            LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Article::getId, articleId).set(Article::getDelFlag, SystemConstants.DEL_FLAG_TRUE);
            update(updateWrapper);
        }
        return ResponseResult.okResult();
    }
}
