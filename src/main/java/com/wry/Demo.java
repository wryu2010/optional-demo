package com.wry;

import com.wry.entity.Address;
import com.wry.entity.Country;
import com.wry.entity.User;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Optional;

/**
 * <p>Title: Demo</p>
 * <p>Description: </p>
 *
 * @author wenrongyu
 * @date 2020/10/24
 */

public class Demo {
    public static void main(String[] args) {
        //User empty = null;
        User user = new User("wenrongyu", 18, new Address(new Country("China"),"中国"), "yuyanyan");
        //testOfNullable(null);
        //testPresent(null);
        //testOrElse(null);
        //testMap(user);
        //testFilter(user);
        testOptional(user);
    }

    static void testOfNullable(User user) {
        /* 将user包装成Optional，当user为null时会抛出NullPointException
        Optional<User> optional = Optional.of(user);*/

        /* 将user包装成Optional，当user为null时不会抛出NullPointException*/
        Optional<User> optional = Optional.ofNullable(user);


        /* 从Optional获取user，如果user为null会抛出NoSuchElementException: No value present*/
        User result = optional.get();
        System.out.println(result);
    }

    static void testPresent(User user) {
        /* 将user包装成Optional，当user为null时不会抛出NullPointException*/
        Optional<User> optional = Optional.ofNullable(user);

        /* 判断数据是否为nul，如果user为null会抛出NoSuchElementException: No value present*/
        System.out.println(optional.isPresent());

        /* 判断user是否为null，如果不为null执行user.sayHello()，否则不执行，等同于optional.ifPresent(u -> u.sayHello());*/
        optional.ifPresent(User::sayHello);
    }

    static void testOrElse(User user) {
        /* 将user包装成Optional，当user为null时返回默认的User，注意不管user是否为null，都会调用create方法
        User result = Optional.ofNullable(user).orElse(create());*/

        /* 将user包装成Optional，当user为null时返回默认的User，注意user不为null，不会调用create方法
        User result = Optional.ofNullable(user).orElseGet(Demo::create);*/

        /* 将user包装成Optional，当user为null时抛出指定的异常，下面等同于Optional.ofNullable(user).orElseThrow(()-> new IllegalArgumentException())*/
        User result = Optional.ofNullable(user).orElseThrow(IllegalArgumentException::new);

        System.out.println("testOrElse："+result);
    }

    static void testMap(User user) {
        /* map() 对user应用作为方法参数的函数，然后将返回的值包装在Optional中
           这里获取user中的name属性值并包装到Optional中，如果name属性值为null，则返回默认值
        String result = Optional.ofNullable(user).map(User::getName).orElse("shuaige");*/

        /* 作用类似map()，区别在于flapMap()的方法参数使用Optional进行包装，可用来解除Optional的包装 */
        String result = Optional.ofNullable(user).flatMap(User::getFamily).orElse("xiaojiajia");

        System.out.println(user);
        System.out.println(result);
    }

    static void testFilter(User user) {
        /* 如果filter()方法参数传入的Predicate结果为true，返回对应的user，否则返回空的Optional*/
        Optional<User> result = Optional.ofNullable(user).filter(u -> u.getName() != null && "wenrongyu".equals(u.getName()));
        System.out.println(result);
    }



    public static User create() {
        System.out.println("创建User对象");
        return new User();
    }


    /**
     * 使用Optional
     */
    static void testOptional(User user){
        /* 不使用Optional
        if (null != user) {
            Address address = user.getAddress();
            if (address != null) {
                Country country = address.getCountry();
                if (country != null) {
                    String name = country.getName();
                    if (name != null) {
                        if ("wenrongyu".equals(name)) {
                            name = name.toUpperCase();
                            System.out.println("name=" + name);
                        }
                    }
                }
            }
        }
        */

        /* 使用Optional*/
        String name = Optional.ofNullable(user)
                .map(User::getAddress).map(Address::getCountry).map(Country::getName).map(String::toUpperCase)
                .filter("wenrongyu"::equals)
                .orElse("default");
        System.out.println(name);
    }
}
