package com.enzip.robot.component.event.component;

import com.enzip.robot.component.contact.GroupMember;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Enzip
 * @since 2023/11/21 9:00
 */
@Data
@NoArgsConstructor
@ToString
public class Sender {

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("nickname")
    private String nickName;

    @JsonProperty("sex")
    private String sex;

    @JsonProperty("age")
    private Integer age;

    @JsonProperty("group_id")
    private Long groupId;

    @JsonProperty("card")
    private String card;

    @JsonProperty("area")
    private String area;

    @JsonProperty("level")
    private String level;

    @JsonProperty("role")
    private String role;

    @JsonProperty("title")
    private String title;

    public Sender(GroupMember groupMember) {
        this.userId = groupMember.getUserId();
        this.nickName = groupMember.getNickname();
        this.sex = groupMember.getSex();
        this.age = groupMember.getAge();
        this.card = groupMember.getCard();
        this.area = groupMember.getArea();
        this.level = groupMember.getLevel();
        this.role = groupMember.getRole();
        this.title = groupMember.getTitle();
    }
}
