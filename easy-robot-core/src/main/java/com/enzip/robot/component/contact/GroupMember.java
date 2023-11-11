package com.enzip.robot.component.contact;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 群成员
 *
 * @author Enzip
 * @since 2023/8/23 9:54
 */
@Slf4j
@Getter
@AllArgsConstructor
public class GroupMember {

    /**
     * 群号
     */
    private Long groupId;
    /**
     * Q号
     */
    private Long userId;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 群名片／备注
     */
    private String card;
    /**
     * 性别, male 或 female 或 unknown
     */
    private String sex;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 地区
     */
    private String area;
    /**
     * 加群时间戳
     */
    private Long joinTime;
    /**
     * 最后发言时间戳
     */
    private Long lastSentTime;
    /**
     * 成员等级
     */
    private String level;
    /**
     * 角色, owner 或 admin 或 member
     */
    private String role;
    /**
     * 是否不良记录成员
     */
    private Boolean unfriendly;
    /**
     * 专属头衔
     */
    private String title;
    /**
     * 专属头衔过期时间戳
     */
    private Long titleExpireTime;
    /**
     * 是否允许修改群名片
     */
    private Boolean cardChangeable;
    /**
     * 禁言到期时间
     */
    private Long shutUpTimestamp;
    /**
     * 群
     */
    private Group group;
    /**
     * 机器人
     */
    private Bot bot;


}
