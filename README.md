[TOC]
# 一、不使用Optional与使用Optional
### 1.不使用Optional
```java
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
```

### 2.使用Optional
```java
String name = Optional.ofNullable(user)
.map(User::getAddress).map(Address::getCountry).map(Country::getName).map(String::toUpperCase)
                .filter("wenrongyu"::equals)
                .orElse("default");
```

# 二、Optional用法
## 1.创建Optional实例
### of 使用方法
返回Optional实例。将user包装成Optional，当user为null时会抛出NullPointException。
```java
Optional<User> optional = Optional.of(user);
```

### ofNullable 使用方法
返回Optional实例。将user包装成Optional，当user为null时不会抛出NullPointException。

```java
Optional<User> optional = Optional.ofNullable(user);
```

## 2.获取Optional实例包装的值

### get 使用方法
返回对应类型的值。从Optional实例中获取user，如果user为null会抛出NoSuchElementException: No value present。
```java
Optional<User> optional = Optional.ofNullable(user);
User result = optional.get();
```

## 3.返回默认值和异常
```java
public static User create() {
	System.out.println("创建User对象");
	return new User();
}
```

### orElse
返回对应类型的值。将user包装成Optional，当user为null时返回默认的User，**注意不管user是否为null，都会调用create方法**。
```java
User result = Optional.ofNullable(user).orElse(create());//也可以写成Optional.ofNullable(user).orElse(new User())
```

### orElseGet
返回对应类型的值。将user包装成Optional，当user为null时返回默认的User，**注意user不为null，不会调用create方法**。
```java
User result = Optional.ofNullable(user).orElseGet(Demo::create);
```

### orElseThrow
返回对应类型的值。将user包装成Optional，当user为null时抛出指定的异常。
```java
User result = Optional.ofNullable(user).orElseThrow(IllegalArgumentException::new);//等同于Optional.ofNullable(user).orElseThrow(()-> new IllegalArgumentException())
```

## 4.判断Optional实例包装的值是否存在
### isPresent
返回boolean类型的值。判断数据是否为nul，如果user为null会抛出NoSuchElementException: No value present
```java
Optional<User> optional = Optional.ofNullable(user);
System.out.println(optional.isPresent());
```

### ifPresent
无返回值。判断user是否为null，如果不为null执行user.sayHello()，否则不执行。
```java
Optional<User> optional = Optional.ofNullable(user);
optional.ifPresent(User::sayHello);//等同于optional.ifPresent(u -> u.sayHello());
```

## 5.转换
### map
返回Optional实例。对user应用作为map方法参数的函数，然后将返回的值包装在Optional实例中。
```java
String result = Optional.ofNullable(user).map(User::getName).orElse("shuaige");//获取user中的name属性值并包装到Optional中，如果name属性值为null，则返回默认值
```

### flatMap
返回Optional实例。作用类似map()，区别在于flapMap()的方法参数使用Optional进行包装，可用来解除Optional的包装
```java
String result = Optional.ofNullable(user).flatMap(User::getFamily).orElse("xiaojiajia");//User的getFamily()方法需要返回Optional实例
```

## 6.过滤
### filter
返回Optional实例。如果filter()方法参数传入的Predicate结果为true，返回对应的user，否则返回空的Optional。
```java
Optional<User> result = Optional.ofNullable(user).filter(u -> u.getName() != null && 
"wenrongyu".equals(u.getName()));
```

# 三、Java9增强
## 1.or
返回Optional实例。与orElse和orElseGet类似，但是会返回Optional实例。
```java
User result = Optional.ofNullable(user)
      .or( () -> Optional.of(new User("default","1234"))).get();
```

## 2.ifPresentOrElse
无返回值。结合了ifPresent和orElse，同时指定user不为null时和user为null时的处理方式。
```java
Optional.ofNullable(user).ifPresentOrElse( u -> logger.info("User is:" + u.getEmail()),
  () -> logger.info("User not found"));
```

## 3.stream
把Optional实例转换为Stream实例，如果没有值，它会返回空的Stream实例。
```java
User user = new User("john@gmail.com", "1234");
List<String> emails = Optional.ofNullable(user)
      .stream()
      .filter(u -> u.getEmail() != null && u.getEmail().contains("@"))
      .map( u -> u.getEmail())
      .collect(Collectors.toList());
```

## 4.与流或者其他Optional方法结合
```java
List<User> users = new ArrayList<>();
User user = users.stream().findFirst().orElse(new User("default", "1234"));//findFirst()返回Optional实例
```
