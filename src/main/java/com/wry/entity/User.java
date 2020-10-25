package com.wry.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

/**
 * <p>Title: User</p>
 * <p>Description: </p>
 *
 * @author wenrongyu
 * @date 2020/10/24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String name;

    private Integer age;

    private Address address;

    private String family;

    public Optional<String> getFamily() {
        return Optional.ofNullable(family);
    }

    public String sayHello() {
        return "你好";
    }
}
