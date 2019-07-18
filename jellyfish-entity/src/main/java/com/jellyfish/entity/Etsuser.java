package com.jellyfish.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author will
 * @since 2019-07-11
 */
@Data
@KeySequence(value = "SEQ_ETSUSER", clazz = Integer.class)
public class Etsuser implements Serializable {


    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    private String name;


}
