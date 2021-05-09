package cn.xanderye.entity;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * @author XanderYe
 * @description:
 * @date 2021/5/8 16:57
 */
@Data
public class RecruitData {
    @Id
    private Integer id;

    private String title;

    private String link;

    private Integer type;

    private Date date;
}
