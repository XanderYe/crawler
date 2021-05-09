package cn.xanderye.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;

@Data
@Accessors(chain = true)
public class User {
    @Id
    private Integer id;

    private String name;

    private String keyword;

    private String email;

    private Integer type;
}
