package org.example.enums;

/**
 * HTTP 响应状态码枚举
 */
public enum AppHttpCodeEnum {
    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 需要登录后操作
     */
    NEED_LOGIN(401, "需要登录后操作"),

    /**
     * 无权限操作
     */
    NO_OPERATOR_AUTH(403, "无权限操作"),

    /**
     * 系统错误
     */
    SYSTEM_ERROR(500, "出现错误"),

    /**
     * 用户名已存在
     */
    USERNAME_EXIST(501, "用户名已存在"),

    /**
     * 手机号已存在
     */
    PHONENUMBER_EXIST(502, "手机号已存在"),

    /**
     * 邮箱已存在
     */
    EMAIL_EXIST(503, "邮箱已存在"),

    /**
     * 必须填写用户名
     */
    REQUIRE_USERNAME(504, "必须填写用户名"),

    /**
     * 评论内容不能为空
     */
    CONTENT_NOT_NULL(506, "评论内容不能为空"),

    /**
     * 文件类型错误
     */
    FILE_TYPE_ERROR(507, "文件类型错误，请上传png格式文件"),

    /**
     * 用户名为空
     */
    USERNAME_NOT_NULL(508, "用户名不能为空"),

    /**
     * 昵称为空
     */
    NICKNAME_NOT_NULL(509, "昵称不能为空"),

    /**
     * 密码为空
     */
    PASSWORD_NOT_NULL(510, "密码不能为空"),

    /**
     * 邮箱为空
     */
    EMAIL_NOT_NULL(511, "邮箱不能为空"),

    /**
     * 昵称已存在
     */
    NICKNAME_EXIST(512, "昵称已存在"),

    /**
     * 标签名称为空
     */
    TAG_NAME_NULL(513, "标签名称不能为空"),

    /**
     * 标签备注为空
     */
    TAG_REMARK_NULL(514, "标签备注不能为空"),

    /**
     * 标签名称已存在
     */
    TAG_NAME_EXIST(515, "标签名称已存在"),

    /**
     * 标签备注已存在
     */
    TAG_REMARK_EXIST(516, "标签备注已存在"),

    /**
     * 数据不存在
     */
    DATA_NOT_EXIST(517, "数据不存在"),

    /**
     * 文章不存在
     */
    ARTICLE_NOT_FOUND(518, "文章不存在"),

    /**
     * 数据已存在
     */
    DATA_EXIST(519, "数据已存在"),

    /**
     * 选择自己作为父菜单
     */
    PARAM_INVALID(520, "不能选择自己作为父菜单"),

    DELETE_ILLEGAL(521, "存在子菜单，不允许删除"),

    /**
     * 修改管理员
     */
    CHANGE_STATUS_ILLEGAL(522, "超级管理员角色不允许修改"),

    /**
     * 用户名或密码错误
     */
    LOGIN_ERROR(505, "用户名或密码错误");


    /**
     * 状态码
     */
    int code;

    /**
     * 提示信息
     */
    String msg;

    AppHttpCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

