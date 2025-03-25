package org.example.domain.entity;


import java.io.Serial;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 文章标签关联表(ArticleTag)表实体类
 *
 * @author makejava
 * @since 2025-03-20 17:30:31
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("article_tag")
public class ArticleTag implements Serializable {

    @Serial
    private static final long serialVersionUID = 625337492348897098L;

    /**
     * 文章id
     */
    @TableId(value = "article_id", type = IdType.INPUT)
    @Schema(description = "文章id")
    private Long articleId;

    /**
     * 标签id
     */
    @TableField(value = "tag_id")
    @Schema(description = "标签id")
    private Long tagId;

}

