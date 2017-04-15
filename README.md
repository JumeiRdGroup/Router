#Router ![svg](https://travis-ci.org/yjfnypeu/Router.svg?branch=master)
<a href="http://www.methodscount.com/?lib=com.github.yjfnypeu.Router%3Arouter-api%3A0.9"><img src="https://img.shields.io/badge/Methods count-core: 239 | deps: 2-e91e63.svg"/></a>

This is a framework for starting an activity by url*(“Scheme”)* on Android.

### Dependencies

add JitPack to your root project build.gradle:

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

```

LatestVersion=[![](https://jitpack.io/v/yjfnypeu/Router.svg)](https://jitpack.io/#yjfnypeu/Router)

*attention: because of the mistake of jCenter.we changed the repository from jCenter to JitPack.You should change the groupId when you upgrade the new versions*

Basic config:
```Groovy
// add it to your build.gradle on app project
compile "com.github.yjfnypeu.Router:router-api:$LatestVersion"
```

If you've added *[Parceler](https://github.com/yjfnypeu/Parceler)* into your app, the best practice is to add a Compile-time framework to create route rules with annotations *RouterRule* to make *Parceler* more convenient:

```Groovy
// add it to your build.gradle on app project
apt "com.github.yjfnypeu.Router:router-compiler:$LatestVersion"
```

### Features

* Support all types of *Bundle*  
* Support interceptor, for example, you can config an interceptor to **force request login** before open an activity that requires a login.
* Support open with browser when your scheme is **http/https**
* Support add extras while open an Activity  
* Support set request code and launch mode and animation when open activity by route*  
* Flexible config: You can config your route rules automatically*(by use RouteRule annotation)* or manually

### Picture

![route](./pics/route.gif)
### Usage

[Router wiki](https://github.com/yjfnypeu/Router/wiki)

### ChangeLog

see [releases](https://github.com/yjfnypeu/Router/releases)

### Contacts

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
