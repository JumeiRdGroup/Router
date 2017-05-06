# Router2 
This is a framework for starting an activity by url*(“Scheme”)* on Android.

### Dependencies

LatestVersion=[ ![Download](https://api.bintray.com/packages/yjfnypeu/maven/Router2/images/download.svg) ](https://bintray.com/yjfnypeu/maven/Router2/_latestVersion)

```Groovy
// add it to your build.gradle on app project
compile "org.haoge.router2:router-api:$LatestVersion"
annotationProcessor "org.haoge.router2:router-compiler:$LatestVersion"
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
Copyright 2015 Haoge

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
