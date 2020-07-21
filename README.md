# 数据库原理与设计大作业实验报告

2017326603075   陈浩骏   17信科1班

---

## 实验选题与需求分析

### 实验选题

​	**仓库进销存管理系统**

### 需求

| 需求                              | 实现   |
| --------------------------------- | ------ |
| 仓库雇员信息管理                  | ✔      |
| 各实体类信息查询                  | ✔      |
| 新增商品并初始化                  | ✔      |
| 商品入库                          | ✔      |
| 商品出库                          | ✔      |
| 删除商品                          | ✔      |
| 用户鉴权并分级(管理员 / 普通用户) | ✔      |
| 多条件连表查询                    | 未实现 |

### E-R图

![](img\ER.PNG)

### 数据库表关系模型图

![](img\Capture1.PNG)

### 数据库建表SQL

> 数据库版本: MySQL 5.7(Innodb)

```mysql
CREATE TABLE employee
(
    em_id    BIGINT       NOT NULL
        PRIMARY KEY,
    em_age   INT          NOT NULL,
    em_name  VARCHAR(255) NULL,
    em_sex   INT          NOT NULL,
    is_admin BIT          NOT NULL,
    passwd   VARCHAR(255) NULL
);
CREATE TABLE storage
(
    item_id     BIGINT       NOT NULL
        PRIMARY KEY,
    item_amount BIGINT       NULL,
    item_name   VARCHAR(255) NULL
);
CREATE TABLE instock
(
    transaction_id BIGINT      NOT NULL
        PRIMARY KEY,
    date           DATETIME(6) NULL,
    in_count       BIGINT      NULL,
    item_id        BIGINT      NULL,
    via_id         BIGINT      NULL,
    CONSTRAINT instock___emidfk
        FOREIGN KEY (via_id) REFERENCES employee (em_id),
    CONSTRAINT instock___itemidfk
        FOREIGN KEY (item_id) REFERENCES storage (item_id)
            ON DELETE CASCADE
);
CREATE TABLE outstock
(
    transaction_id BIGINT      NOT NULL
        PRIMARY KEY,
    date           DATETIME(6) NULL,
    item_id        BIGINT      NULL,
    out_count      BIGINT      NULL,
    via_id         BIGINT      NULL,
    CONSTRAINT outstock___emidfk
        FOREIGN KEY (via_id) REFERENCES employee (em_id),
    CONSTRAINT outstock___itemidfk
        FOREIGN KEY (item_id) REFERENCES storage (item_id)
            ON DELETE CASCADE
);
```

### 几点说明

1. 在上一次提交的数据库大作业demo中, 采用了procedure(储存过程)进行出库入库对库存表的读写, 因开发框架改变, 在SQL procedure层的逻辑已整合至DAO层中, 下方会给出设计demo时的procedure过程.
2. 开发环境与框架为Java11(OpenJDK 11.0.6), SpringBoot2(SpringBoot2.2.7).
3. 由于Java PO层(即Entity Object)可以方便提供条件约束, `@Valid, @Constrain`, 所以并未在表中修改逻辑结构以实现SQL Constraint, 具体于下方给出案例说明.
4. 由于鉴权逻辑相对简单且鉴权体系中的密码工具, 详见`TokenUtil`, 采用`Bcypt`进行加密, 所以并没有于DAO层上下增加VO类, PO序列化成json送至前端.

#### 大作业demo中的procedure

​		<u>**Demo代码仅用于第一次大作业提交时的python脚本, 与本项目不兼容, 意在提供数据库储存过程抽象逻辑**</u>

> 采用procedure和合理的分配表级权限能够在sql层面避免非法数据的插入, 隔离重要数据.
>
> 因采用`@Entity`, 于java内的逻辑交给了对应的Service/Repository类

```mysql
# 入库的储存过程, 将储存过程暴露, 减少对表的直接读写, 是面向对象暴露公共接口且保护私有变量的表现
DELIMITER $$
CREATE PROCEDURE instockItem(IN itemIdin INT, IN itemamount BIGINT, IN emid INT, OUT success BIT)
BEGIN
    INSERT INTO instock(itemId, instockAmount, viaId, instockDate) VALUES (itemIdin, itemamount, emid, now());
    SET success = 1;
END;
DELIMITER $$
# 入库的触发器(在调用入库储存过程时,触发器会被触发), 避免直接写储存表, 保证数据一致性且可追溯
DELIMITER $$
CREATE TRIGGER instockTrigger
    AFTER INSERT
    ON instock
    FOR EACH ROW
BEGIN
    UPDATE storage SET itemAmount = itemAmount + NEW.instockAmount WHERE itemId = NEW.itemId;
END;
DELIMITER $$
# 出库的储存过程, 将储存过程暴露, 减少对表的直接读写, 也是面向对象暴露公共接口且保护私有变量的表现
DELIMITER $$
CREATE PROCEDURE outstockItem(IN itemIdout INT, IN itemamount BIGINT, IN emid INT, OUT success BIT)
outs:BEGIN
    DECLARE storageamount BIGINT;
    IF itemamount > storageamount THEN LEAVE outs;
    END IF;
    INSERT INTO outstock(itemId, outstockAmount, viaId, outstockDate) VALUES (itemIdout, itemamount, emid, now());
    SET success = 1;
END;
DELIMITER $$
# 出库的触发器(在调用出库储存过程时,触发器会被触发), 避免直接写储存表, 保证数据一致性且可追溯
DELIMITER $$
CREATE TRIGGER outstockTrigger
    AFTER INSERT
    ON outstock
    FOR EACH ROW
BEGIN
    UPDATE storage SET itemAmount = itemAmount - NEW.outstockAmount WHERE itemId = NEW.itemId;
END;
DELIMITER $$
# 删除员工的储存过程
DELIMITER $$
CREATE PROCEDURE del_employee(IN emid INT, IN viaid INT, OUT success BIT)
flag:BEGIN
    DECLARE adminFlag BIT;
    SELECT isAdmin FROM employee WHERE employeeId = viaid INTO adminFlag;
    IF adminFlag != 1 THEN LEAVE flag;
    END IF;
    DELETE FROM employee WHERE employeeId = emid;
    SET success = 1;
END;
DELIMITER $$
# 增加员工的储存过程
DELIMITER $$
CREATE PROCEDURE add_employee(IN emid INT, IN viaid INT, IN name VARCHAR(40), IN age INT, IN sex BIT, in passcode VARCHAR(255),OUT success BIT)
flag:BEGIN
    DECLARE adminFlag BIT;
    SELECT isAdmin FROM employee WHERE employeeId = viaid INTO adminFlag;
    IF adminFlag != 1 THEN LEAVE flag;
    END IF;
    INSERT INTO employee(employeeId, employeeName, employeeAge, employeeSex, password) VALUES (emid, name, age, sex, passcode);
    SET success = 1;
END;
DELIMITER $$
```

#### 实体类的构建(相对于表的)与Constraint

```java
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements Serializable {
    @Id
    private Long emId;
    private String emName;
    @Min(0)
    @Max(120)
    private int emAge;
    @Min(0)
    @Max(1)
    private int emSex;
    private String passwd;
    private boolean isAdmin;
    // 构造区...
}
```

```java
@Entity
@Data
@NoArgsConstructor
public class Instock implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long transactionId;
    private Long itemId;
    @Min(1)
    private Long inCount;
    private Long viaId;
    private Date date;
    // 构造区...
}
```

```java
@Entity
@Data
@NoArgsConstructor
public class Outstock implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long transactionId;
    private Long itemId;
    @Min(1)
    private Long outCount;
    private Long viaId;
    private Date date;
    // 构造区...
}
```

```java
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Storage implements Serializable {
    @Id
    private Long itemId;
    private String itemName;
    @Min(0)
    private Long itemAmount;
    // 构造区...
}
```



## 实验实现

### 关键词

**Java, SQL, Spring, Spring Boot, Hibernate(Spring Data), RESTful , H5C3JS, Bootstrap, Redis, JWT**

### 体系

| 后                    | 前                    |
| --------------------- | --------------------- |
| ![](img\Capture2.PNG) | ![](img\Capture3.PNG) |

### 产品逻辑

#### 登录鉴权逻辑

用户发起登录请求(若访问未授权页面, 跳回登录) -> Api接口收到 ->取PO值比对(**(Bcypt Dependency)**密码, **是否管理员**) -> 签发带有鉴权的token -> 载入redis并设置过期时间(注销时remove以令token失效) -> 进入主界面.

#### 各类查询或更新(提供2案例, 源码已附, 项目Readme见后)

> 已实现用户权限区分.
>
> 普通用户`isAdmin=false`的api将会在`ApiController`下, 由`@RequestMapping("/api/")`监听.
>
> 管理员用户`isAdmin=true`亦拥有普通用户api的访问权, 涉及管理的api于`AdminApiController`下, 由`@RequestMapping("/adminapi/")`监听.

用户发起查询(异步ajax) -> Api接口收到 -> Service层询库拿对象并执行逻辑 ->序列化统一返回对象(成功/失败/非法/) -> 返回前端互动.

##### 增加库存案例

1. 前端输入好商品id, 入库数量后, 确认入库触发

   <img src="img\Capture16.PNG" style="zoom:80%;" />
   
   ```javascript
   function inputItem() {
       var formData = new FormData();
       formData.append("itemid", $("#itemId1").val());
       formData.append("viaid", sessionStorage.getItem("emId"));
       formData.append("itemcount", $("#itemCount1").val());
       $.ajax("/api/inputitem", {
           type: "post",
           contentType: false,
           processData: false,
           data: formData,
           beforeSend: function (xhr) {
               xhr.setRequestHeader("Access-Token", sessionStorage.getItem("Access-Token"));
           },
           success: function (data) {
               if (data.code === 200) {
                   document.getElementById("status1").innerHTML = tick;
                   freshtable();
               } else {
                   document.getElementById("status1").innerHTML = cross + data.content;
               }
           },
           error: function () {
               alert('Err ajax');
           }
       })
}
   ```

   2. `BasicAuthInterceptor`捕捉请求, 由`TokenUtil`验证token的真实性, 并由`Redis4TokenUtil`询Redis该token的有效性.
   
      ```java
      // 该方法于BasicAuthInterceptor类内
      @Override
          public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
              request.setCharacterEncoding("UTF-8");
              response.setCharacterEncoding("UTF-8");
              String token = request.getHeader("Access-Token");
              if (token == null) {
                  token = request.getParameter("Access-Token");
              }
      
              if (token == null) {
                  noAuthGo(response);
                  return false;
              }
      		// 验证Token -> TokenUtil
              Map<String, Claim> claims = tokenUtil.verify(token);
      
              if (claims != null) {
                  request.setAttribute("emId", claims.get("emId"));
                  request.setAttribute("emName", claims.get("emName"));
                  return true;
              }
              noAuthGo(response);
              return false;
          }
      // 该方法于TokenUtil类内
      public Map<String, Claim> verify(String token) {
              try {
                  JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SALT)).withIssuer(ISSUER).build();
                  DecodedJWT jwt = verifier.verify(token);
                  Map<String, Claim> claims = jwt.getClaims();
                  // 询Redis内的token
                  if (!redis4TokenUtil.hasJWT(claims.get("emId").asString())) {
                      return null;
                  }
                  return jwt.getClaims();
              } catch (Exception e) {
                  return null;
              }
          }
      ```
   
   3. 放行请求后, 由`ApiController`Handle请求.
   
      ```java
          @PostMapping(value = "/instock", produces = "application/json")
          public ResultReturn<String> instock(@RequestParam String id, @RequestParam String viaid, @RequestParam String count) {
              Long itemId = Long.valueOf(id);
              Long viaId = Long.valueOf(viaid);
              Long itemCount = Long.valueOf(count);
      
              return instockService.inputItem(itemId, viaId, itemCount);
          }
      ```
   
   4. 由Service层进入Repository层操作库内对象, <u>**与上述demo中抽象出的储存过程逻辑相同**</u>, 双表操作都成功并返回结果.
   
      ```java
      	public ResultReturn<String> inputItem(Long itemId, Long viaId, Long itemCount) {
              ResultReturn<String> resultReturn = storageService.inputStorage(itemId, itemCount);
              if (resultReturn.getCode() == 200) {
                  Instock instock = new Instock(itemId, itemCount, viaId, new Date());
                  instockRepository.save(instock);
              }
              return resultReturn;
          }
      ```
   
      <img src="img\Capture17.PNG" style="zoom: 80%;" />

##### 删除员工案例

1. 由管理员访问员工管理页面, 选择员工并点击删除.

   ![](img\Capture18.PNG)

2. 由于该请求是管理员的接口, `AdminAuthInterceptor`会拦截请求, 先验证是否为管理员再放行, 步骤与上述2相似

3. 由`AdminApiController` Handle请求.

   ```java
   @PostMapping(value = "/delemployee", produces = "application/json")
       public ResultReturn<String> delemployee(@RequestParam String emid) {
           Long id = Long.valueOf(emid);
           return employeeService.delEmployee(id);
       }
   ```

   

4. 由Service层进入Repository层先验条件后删除对象, 因为仓储与删除的员工无关, 如果有`Delete Cascade`会影响入库出库流水的完整性, 故删除员工不会对其他表有任何操作.

   > 附: 删除商品时, 入库出库表中有关该商品的数据将会变得无用, 所以在删除商品时设计有`Instock Outstock`的`Delete Cascade`

```java
    public ResultReturn<String> delEmployee(Long emId) {
        Employee employee = employeeRepository.findByEmId(emId);
        if (employee == null) {
            return new ResultReturn<>(300, "ERR", "No employee has been found");
        }
        employeeRepository.deleteById(emId);
        return new ResultReturn<>(200, "OK", "OK");
    }
```

![](img\Capture19.PNG)

## 实现总结

​	由需求推断出模型, 构造E-R图, 再创建数据库的具体模型, 虽然各实体之间的联系较为简单, 但是需要考虑到许多联系到生产环境实质性的流程. 显然若想要从零开始快速构建管理系统, 还是有必要先将数据库具体模型列出, 新建储存过程, 在SQL层面模拟数据流与数据字典, 而不是根据设计语言内的实体类来盲目的构造函数式关系.

## 项目概览

> 项目源码已附报告发送

### 登录页及首页

<img src="img\Capture4.PNG" alt="Capture4" style="zoom:67%;" />

<img src="img\Capture5.PNG" alt="Capture5" style="zoom:67%;" />

### 按需查询

<img src="img\Capture6.PNG" alt="Capture6" style="zoom:67%;" />

<img src="img\Capture7.PNG" alt="Capture7" style="zoom:67%;" />

<img src="img\Capture8.PNG" alt="Capture8" style="zoom:67%;" />

### 入库出库

> 非法值拦截
>
> + 入库负值
> + 出库超过库存值
> + 新增商品存在覆盖风险

<img src="img\Capture9.PNG" alt="Capture9" style="zoom:67%;" />

<img src="img\Capture10.PNG" alt="Capture10" style="zoom:67%;" />

### 员工管理

<img src="img\Capture11.PNG" alt="Capture11" style="zoom:67%;" />

<img src="img\Capture12.PNG" alt="Capture12" style="zoom:67%;" />

### 日志表(流水号跨表连续)

<img src="img\Capture13.PNG" alt="Capture13" style="zoom: 67%;" />

<img src="img\Capture14.PNG" alt="Capture14" style="zoom:67%;" />

### 普通用户权限页面级拦截

<img src="img\Capture20.PNG" style="zoom:67%;" />

### 普通用户api级拦截

> 带有已登录的非管理员用户的Access-Token, GET 管理员端口的数据

![](img\Capture21.PNG)

## 参考资料

1. [Spring Framework Documentation](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/)
2. [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/2.2.7.RELEASE/reference/html/)
3. [W3CSchool SQL 教程](https://www.w3cschool.cn/sql/)
4. [W3CSchool jQuery 教程](https://www.w3cschool.cn/jquery/jquery-intro.html)
5. [Bootstrap Documentation](https://getbootstrap.com/docs/4.1/getting-started/introduction/)

## Project Readme

### Create user and database

> Stronger password recommended, but remember to configure application.yml(application.properties) in spring boot.

```mysql
CREATE SCHEMA IF NOT EXISTS wms;
GRANT ALL PRIVILEGES ON wms.* TO wms@'%' IDENTIFIED BY 'wms';
```

### Run the SQL Script 

>  `./wms/sql/*.sql` (Project Root)

​	Running these script will pre-configure the database' table construct. Also, these SQL Scripts come with some test data.(Contain user that can log in)

​	If not, you may need to insert a row in `employee` table, with password using `BCypt` to pre-encrypt the password for yourself.


### External Service(Dependencies)

+ MySQL 5.7(Recommend version) change SQL accent if necessary when you adopt a different SQL server. (port is configured at 10000 by project property)
+ Redis 6 (port is configured at 10005 by project property)

### Maven Dependency Resolve

> If opening project with idea/eclipse, it is recommended to sync(import) dependencies by only click `reimport` in GUI.

```bash
! mvn clean
! mvn install
```

### Running with `java -jar`

> This project is developed under OpenJDK11. Stability is not tested on other version of Java. 

```bash
java -jar $Project_Root_Directory$/target/target/wms-0.0.1-SNAPSHOT.jar
```

### Visit

​	Go to browser and hit `http://localhost:8101`. You would be redirected to login page of the project.

### License

[View on Github](https://github.com/MijazzChan/wms/blob/master/LICENSE)

