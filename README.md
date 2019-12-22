# QHUOJ 项目接口规范

**注意**

*项目采用 RESTful 风格*

*项目端口 8080*

*除了 GET 的参数，项目中所有参数都通过 JSON 发送， 将参数放在 requestbody 中， GET 的参数放请求在路径中*

*返回结果都是 JSON，除了实时结果前面存在 “data:” 去掉之后就是JSON*

返回格式：{

​	“time”：时间，

​	“status”：状态（success error），

​	“message”：返回的消息，如果出错就是错误消息，成功就是返回的对象，对象中可能会存在无穷嵌套

​	“path”：访问路径

}

样例：
```json


{
​    "time": "2019-12-22 12:06:49",
​    "status": "success",
​    "message": {
​        "id": 37,
​        "problem": {
​            "id": 1,
​            "name": "test",
​            "content": "test",
​            "example": "test",
​            "note": "test",
​            "timeLimit": 1000,
​            "memoryLimit": 256
​        },
​        "user": {
​            "id": 1,
​            "username": "dongshuai",
​            "nickname": "dongshuai",
​            "password": "*********",
​            "score": 0
​        },
​        "language": {
​            "id": 1,
​            "name": "C",
​            "suffix": "c",
​            "compileCommand": "gcc -O2 -s -Wall -o {filename}.exe {filename}.c -lm",
​            "runningCommand": "{filename}.exe"
​        },
​        "usedTime": 0,
​        "usedMemory": 0,
​        "judgeResult": null,
​        "code": "#include <stdio.h>\nint main(){\nint a=0, b=0;\nscanf(\"%d %d\", &a, &b);\nprintf(\"%d\", a+b);\nreturn 0;\n}",
​        "judgeScore": 0,
​        "judgeLog": null
​    },
​    "path": "/submissions"
}
```
- 用户 /users
  - 增 *POST* 
    - 字段：username password nickname（username 用于登录）
  - 删 *DELETE*
  - 改 *PUT*
  - 查 *GET*  参数 .../{id}
    - example: /users/1 表示查询 id 为 1 的用户
    - 查全部 *GET*  .../all  
      - example: /users/all 表示查询全部用户
- 试题 /problems
  - 增 *POST* 
    - 字段：name content example timeLimit memoryLimit note
  - 删 *DELETE*
  - 改 *PUT*
  - 查 *GET*  参数 .../{exp} 可以是 id 和 name
    - example: /problems/1 /problems/A+B
- 测试点 /testpoints
  - 增 *POST* 
    - 字段：input output score problem 
    - *problem 对象中的 id 为外键*
  - 删 *DELETE*
  - 改 *PUT*
  - 查 *GET*  参数 .../{id} 
- 提交 /submissions
  - 增 *POST* 
    - 字段：code languageId problemId userId
    - *problemId languageId problemId userId 为外键*
  - 删 *DELETE* 
  - 改 *PUT*
  - 查 *GET*  参数 .../{id}
  - 查询实时结果 *GET* 参数 .../realtime/{id} 