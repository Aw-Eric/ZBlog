package org.example.constants;

public class SystemConstants {
    /**
     * 文章是草稿
     */
    public static final Integer ARTICLE_STATUS_DRAFT = 1;
    /**
     * 文章是正常发布状态
     */
    public static final Integer ARTICLE_STATUS_NORMAL = 0;

    /**
     * 分类状态：正常
     */
    public static final String STATUS_NORMAL = "0";

    /**
     * 显示的热门文章数量
     */
    public static final Integer HOT_ARTICLE_SIZE = 10;

    /**
     * 友链状态：审核通过
     */
    public static final String LINK_STATUS_NORMAL = "0";

    /**
     * redis中存储前台博客登录信息的前缀
     */
    public static final String BLOG_LOGIN_PREFIX = "bloglogin:";

    /**
     * token的header
     */
    public static final String TOKEN_HEADER = "token";

    /**
     * 根节点的id
     */
    public static final int ROOT_ID_NULL = -1;

    /**
     * 评论类型：文章
     */
    public static final String COMMENT_TYPE_ARTICLE = "0";

    /**
     * 评论类型：友链
     */
    public static final String COMMENT_TYPE_LINK = "1";

    /**
     * 域名
     */
    public static final String DOMAIN_NAME = "http://st1zsj02p.hd-bkt.clouddn.com/";

    /**
     * redis中存储vieCount的前缀
     */
    public static final String VIEW_COUNT_PREFIX = "Article:ViewCount";

    /**
     * redis中存储后台博客登录信息的前缀
     */
    public static final String ADMIN_LOGIN_PREFIX = "adminlogin:";

    /**
     * 权限类型：菜单
     */
    public static final String MENU_TYPE_MENU = "C";

    /**
     * 权限类型：按钮
     */
    public static final String MENU_TYPE_BUTTON = "F";

    /**
     * 用户角色：管理员
     */
    public static final String USER_ROLE_ADMIN = "admin";

    /**
     * 删除标记：已删除
     */
    public static final Integer DEL_FLAG_TRUE = 1;

    /**
     * 正常状态
     */
    public static final String NORMAL = "0";

    /**
     * 用户类型：管理员
     */
    public static final String USER_TYPE_ADMIN = "1";

    /**
     *
     */
    public static final int ADMIN_USER_ID = 1;


}
