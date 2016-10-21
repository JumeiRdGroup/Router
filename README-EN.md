#Router ![svg](https://travis-ci.org/yjfnypeu/Router.svg?branch=master)   [ ![Download](https://api.bintray.com/packages/yjfnypeu/maven/Router/images/download.svg) ](https://bintray.com/yjfnypeu/maven/Router/_latestVersion) <a href="http://www.methodscount.com/?lib=org.lzh.nonview.router%3ARouter%3A0.2"><img src="https://img.shields.io/badge/Methods and size-171 | 17 KB-e91e63.svg"/></a>


Simply say,this is a framework for start activity by url on Android.

###Dependencies

base config:
```Groovy
// add it to your build.gradle on app project
compile 'org.lzh.nonview.router:router-api:0.3'
```

And if you had added *[Parceler](https://github.com/yjfnypeu/Parceler)* to your app.
the best practical is to add a Compile-time framework to create route rules with annotations *RouterRule* to make *Parceler* more convenient:

```Groovy
// add it to your build.gradle on app project
apt 'org.lzh.nonview.router:router-compiler:0.3'
```

###Features

*Support all types of Bundle*
*Support interceptor,for eg: you can config a interceptor to request login before open a activity with url while you had not logined*
*Support open with browser when your scheme were http/https*
*Support add extras data when open a Activity*
*Support set request code and launch mode and animation when open activity by route*
*Flexible config,You can config your route rules by use RouteRule annotation automatically or create it manually*

### Picture

![route](./pics/route.gif)
###Usage

[中文使用文档](./Usage.md)
[Usage document](./Usage-en.md)

###Contacts

Email:470368500@qq.com
QQ group:108895031

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