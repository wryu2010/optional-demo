package com.wry.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p>Title: Address</p>
 * <p>Description: </p>
 *
 * @author wenrongyu
 * @date 2020/10/24
 */
@Data
@AllArgsConstructor
public class Address {
    private Country country;

    private String desc;
}
