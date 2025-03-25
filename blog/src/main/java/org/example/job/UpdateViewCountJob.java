package org.example.job;

import org.example.constants.SystemConstants;
import org.example.domain.entity.Article;
import org.example.service.ArticleService;
import org.example.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UpdateViewCountJob {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0/55 * * * * ?")
    public void UpdateViewCount() {
        // 获取redis中的浏览量
        Map<String, Integer> viewCountMap =
                redisCache.getCacheMap(SystemConstants.VIEW_COUNT_PREFIX);
        List<Article> collect = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        // 更新数据库中的浏览量
        articleService.updateBatchById(collect);
    }
}
