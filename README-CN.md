# Router  ![svg](https://travis-ci.org/yjfnypeu/Router.svg?branch=master)   [ ![Download](https://api.bintray.com/packages/yjfnypeu/maven/Router/images/download.svg) ](https://bintray.com/yjfnypeu/maven/Router/_latestVersion) <a href="http://www.methodscount.com/?lib=org.lzh.nonview.router%3ARouter%3A0.2"><img src="https://img.shields.io/badge/Methods and size-171 | 17 KB-e91e63.svg"/></a>

一款简单的Android端用于Activity跳转的路由框架.

[README DOCS](./README.md)

### 什么是router框架
简单来说.即通过一行url去指定打开指定页面Activity的工具.充分做到页面间解耦.

### 我希望的router框架所能支持的功能

```
1.可传递Activity跳转时.bundle所支持的所有数据类型,能与原生做到无缝对接
2.支持拦截器.方便对于部分页面跳转时需要检查是否登录.并跳转登录界面做统一配置
3.如果使用http/https地址时.可以直接跳转至使用浏览器
4.能支持原生跳转的各种方式,如请求码.转场动画的设置.
5.能灵活配置route规则.方便对各种开发环境灵活做兼容适配.如插件化.
```

**部分思路参考了[AndRoute](https://github.com/campusappcn/AndRouter)与[ActivityRouter](https://github.com/mzule/ActivityRouter)框架.感谢ing...奉上链接~**

### 效果图

![route](./pics/route.gif)

### Usage

- 添加gradle依赖:
基本配置:

```Groovy
// add it to your build.gradle on app project
compile 'org.lzh.nonview.router:router-api:0.3'
```

如果你也使用了我的另一个框架*[Parceler](https://github.com/yjfnypeu/Parceler)*,建议可以添加一个编译时注解处理器去更好的使用此框架。可很方便的结合*Parceler*框架生成对应的路由规则
```Groovy
// add it to your build.gradle on app project
apt 'org.lzh.nonview.router:router-compiler:0.3'
```

[查看具体使用姿势](./Usage.md)

[Usage docs](./Usage-en.md)

## License
```
Copyright 2015 

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
