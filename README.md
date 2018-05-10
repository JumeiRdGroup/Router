# Router [![](https://jitpack.io/v/yjfnypeu/Router.svg)](https://jitpack.io/#yjfnypeu/Router)
一款简单的，支持在**单品、组件化、插件化**等环境下使用的路由框架。


### 什么是router框架
简单来说.即通过一行url去指定打开指定页面Activity的框架.充分做到页面间解耦.

### 我希望的router框架所能支持的功能

- **自动化** 可自动解析标准url参数
- **安全**: 路由启动过程中。全程catch住异常并通知用户。完全不用担心crash问题。
- **强大的拦截器**:与大部分的路由不同。提供三种路由拦截器机制，对应不同业务下使用。
- **方便**: 使用apt注解生成路由表，配置方便，易维护。
- **灵活**: 配置路由表方式多样，满足你在任意条件下进行使用。
- **支持两种路由**:页面路由与动作路由。
- **支持重启路由**:路由被拦截后。可通过一行代码无缝恢复重启路由。在登录检查中会很有用。
- **高度可定制**:单品、组件化完美支持，对于插件化环境。也可以针对性的定制使用。

### 版本依赖

1. 添加JitPack仓库

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

2. 添加依赖

LatestVersion=[![](https://jitpack.io/v/yjfnypeu/Router.svg)](https://jitpack.io/#yjfnypeu/Router)

```Groovy
compile "com.github.yjfnypeu.Router:router-api:$LatestVersion"
annotationProcessor "com.github.yjfnypeu.Router:router-compiler:$LatestVersion"
```

## 用法

### 为Activity添加路由规则

- 指定路由前缀与路由表生成包名

```
@RouteConfig(
	baseUrl = "haoge://page/", // 路由前缀：用于结合RouterRule合成完整的路由链接
	pack = "com.haoge.studio") // 路由表生成包名：配置后。路由表生成类将会放置于此包下
class App extends Application {...}
```

- 为目标页指定路由规则链接

```
// 在目标Activity上添加RouterRule注解，添加对应的路由规则
// 同一目标页可以添加多个不同的路由链接。
@RouterRule({
	// 可只指定path, 此种数据会与RouteConfig中的baseUrl进行结合:
	// 最终完整链接为：haoge://page/example
	"example", 
	// 也可直接指定完整链接
	"total://example"
	})
class ExampleActivity extends BaseActivity { ... }
```

### 注册路由表

经过上面的配置后，编译后即可生成对应的路由表类，生成的路由表类名统一为**RouterRuleCreator**:

然后即可通过以下代码进行路由表注册使用：

```java
RouterConfiguration.get().addRouteCreator(new RouterRuleCreator())
```

### 启动路由

还是以上面的example为例。要使用Router启动ExampleActivity, 使用以下链接进行跳转

```java
Router.create("haoge://page/example").open(context)
```

### 启动浏览器打开网页

当路由链接为http/https时，且此时本地的也没有页面配置过此链接地址时，将触发使用跳转浏览器打开

比如浏览器打开百度页面

```
Router.create("https://www.baidu.com").open(context)
```

### 启用内部日志打印

```java
Router.DEBUG = true
```

### 添加额外数据启动

```java
Bundle extra = getExtras();
Router.create(url)
	.addFlags(flag) // 添加启动标记位，与Intent.addFlags(flag)相同
	.addExtras(extra) // 添加额外数据：Intent.addExtras(bundle)
	.requestCode(code) // 使用startActivityForResult进行启动
	.setAnim(in, out) // 设置转场动画。Activity.overridePendingTransition(inAnim, outAnim)
	.open(context)
```

### 使用路由回调

路由回调为**RouteCallback**接口，用于在进行路由启动后，对该次路由事件的状态做回调通知：

```java
public interface RouteCallback {
	// 当用于启动的路由链接未匹配到对应的目标页时。回调到此
	void notFound(Uri uri, NotFoundException e);
	// 当启动路由成功后，回调到此。可在此通过rule.getRuleClz()获取对应的目标页类名。
	void onOpenSuccess(Uri uri, RouteRule rule);
	// 当启动路由失败后，回调到此。
	void onOpenFailed(Uri uri, Throwable e);
}
```

路由回调的配置分为两种：

1. 全局路由回调：所有的路由启动事件均会回调到此

```java
RouterConfiguration.get().setCallback(callback)
```

2. 临时路由回调：只对当次路由事件生效

```java
Router.create(url).setCallback(callback).open(context)
```

路由回调机制在进行界面路由跳转埋点时，是个很好的特性。

有时候我们会需要在回调中使用启动时添加的额外数据，而回调的api中并没有提供此数据，所以此时我们需要使用以下方法进行额外数据获取：

```java
此方法只能在回调方法内调用，运行完回调方法后会自动清空。
RouteBundleExtras extras = RouterConfiguration.get().restoreExtras(uri);
```

### 使用ActivityResultCallback

**ActivityResultCallback**接口用于自动处理onActivityResult逻辑，可有效避免在onActivityResult中写一堆的判断switch逻辑。是个很棒的特性。

```java
public interface ActivityResultCallback {
	void onResult(int resultCode, Intent data);
}
```

使用此特性前，需要在BaseActivity中的onActivityResult方法处，添加上派发方法：

```java
RouterConfiguration.get()
	.dispatchActivityResult(this, requestCode, resultCode, data)
```

然后即可直接使用

```java
// 添加了resultCallback属性后，即可不指定requestCode了。免去了取值的烦恼
Router.create(url).resultCallback(resultCallback).open(context)
```

### 使用路由拦截器拦截器

拦截器，顾名思义，就是在路由启动过程中，进行中间状态判断，是否需要拦截掉此次路由事件。使其失败。

拦截器的接口名为**RouteInterceptor**

```java
public interface RouteInterceptor{
	// 在此进行状态判断。判断是否需要拦截此次路由事件
	boolean intercept (Uri uri, RouteBundleExtras extras, Context context);
	// 当intercept方法返回true时，此方法被调用。
	void onIntercepted(Uri uri, RouteBundleExtras extras, Context context);
}
```

Router经过长期的迭代，对拦截器进行了详细的分类，提供了三种拦截器提供使用:

**1. 全局拦截器**：对所有的路由事件生效。

```
RouterConfiguration.get().setInterceptor(interceptor);
```

**2. 单次拦截器**：对当次路由事件生效。

```java
Router.create(url)
	// 是的你没有看错，可以配置多个不同的拦截器实例
	.addInterceptor(interceptor1)
	.addInterceptor(interceptor2)
	.open(context);
```

**3. 指定目标的拦截器**：对指定的目标页面生效

```java
// 在配置的RouterRule的目标页，添加此RouteInterceptors注解即可。
// 在此配置的拦截器，当使用路由启动此页面时，即可被触发。
@RouteInterceptors({CustomRouteInterceptor.class})
@RouterRule("user")
public class UserActivity extends BaseActivity {...}
```

### 恢复路由的方式

既然路由可以被拦截，那么也可以直接被恢复。

```java
Router.resume(uri, extras).open(context);
```

光这样看有点不太直观。我们举个最经典的**登录检查拦截**案例作为说明：

![](https://user-gold-cdn.xitu.io/2018/2/1/16150ba1206c981a?w=1130&h=274&f=png&s=47475)

当不使用路由进行跳转时，这种情况就会导致你本地写上了大量的登录判断逻辑代码。这在维护起来是很费劲的。而且也非常不灵活，而使用拦截器的方式来做登录检查，就会很方便了：

![](https://user-gold-cdn.xitu.io/2018/2/1/16150c2dc3758e35?w=902&h=654&f=png&s=86527)

下面是一个简单的登录拦截实现：

```java
// 实现RouteInterceptor接口
public class LoginInterceptor implements RouteInterceptor{
    @Override
    public boolean intercept(Uri uri, RouteBundleExtras extras, Context context){
    	// 未登录时进行拦截
        return !LoginChecker.isLogin();
    }

    @Override
    public void onIntercepted(Uri uri, RouteBundleExtras extras, Context context) {
    	// 拦截后跳转登录页并路由信息传递过去，便于登录后进行恢复
        Intent loginIntent = new Intent(context,LoginActivity.class);
        // uri为路由链接
        loginIntent.putExtra("uri",uri);
        // extras中装载了所有的额外配置数据
        loginIntent.putExtra("extras",extras);
        context.startActivity(loginIntent);
    }
}
```

```java
public class LoginActivity extends BaseActivity {

	@Arg
	Uri uri;
	@Arg
	RouteBundleExtras extras;
	
	void onLoginSuccess() {
		if(uri != null) {
			// 登录成功。使用此方法直接无缝恢复路由启动
			Router.resume(uri, extras).open(context);
		}
		finish();
	}
}
```

### 自动解析传递url参数

Router支持自动从url中解析参数进行传递：

```java
Router.create("haoge://page/user?username=haoge&uid=123456")
	.open(context);
```

上面的链接即代表：跳转到**haoge://page/user**页面，并传递username和uid数据过去。

### 结合Parceler框架使用

[Parceler框架](https://github.com/JumeiRdGroup/Parceler)是我另一款超轻量型的Bundle数据处理框架，具备良好的兼容性。

将Router与Parceler结合进行使用。可做到 **自动匹配目标页的数据类型进行传递**的效果。

以上方的带参数链接为例：解析后传递到目标页的数据全部是String类型的

| key			| value 	| type		|
|---			|---		|---		|
| username	| haoge	|String	|
| uid			| 123456	|String	|

这样在数据传递的时候就比较麻烦，因为你如果要传递其他类型的数据，还得需要到目标页自己去手动转换一下。

#### 使用Arg注解

解决方法就是使用Arg注解, 比如如下页面：

```kotlin
@RouterRule("parceler-args")
class ArgsActivity:BaseActivity() {

    // 为对应参数的成员变量添加Arg注解
    // 基本数据类型
    @Arg
    var mBoolean:Boolean? = null
    @Arg
    var mInt:Int? = null
    @Arg
    var mByte:Byte? = null
    @Arg
    var mShort:Short? = null
    @Arg
    var mChar:Char? = null
    @Arg
    var mFloat:Float? = null
    @Arg
    var mLong:Long? = null
    @Arg
    var mDouble:Double? = null
    // 默认类型String
    @Arg
    var mString:String? = null
    // 普通实体bean
    @Arg
    var mUser: User? = null
    // 子链接
    @Arg
    var mUrl:String? = null
	
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 从intent中读取数据，注入到Arg注解的成员变量中去。
        Parceler.toEntity(this, intent)
    }
}
```

然后此时使用Router往此页面跳转，传值。将会自动进行转换，比如我们使用如下方式进行传递：

```kotlin
val url = Uri.parse("haoge://page/parceler-args")
    .buildUpon()
    // 添加基本数据类型
    .appendQueryParameter("mBoolean", "true")
    .appendQueryParameter("mByte", "0")
    .appendQueryParameter("mShort", "1")
    .appendQueryParameter("mChar", "c")
    .appendQueryParameter("mInt", "3")
    .appendQueryParameter("mFloat", "3.14")
    .appendQueryParameter("mDouble", "3.14")
    .appendQueryParameter("mLong", "5")
    .appendQueryParameter("mString", "HaogeStudio")
    // 非可序列化对象可通过json格式传递
    .appendQueryParameter("mUser", JSON.toJSONString(User("HaogeStudio")))
    // 转义字符串。比如参数中需要传递网址时
    // appendQueryParameter本身会将数据先进行转义后再拼接上。所以此处是转义的链接
    .appendQueryParameter("mUrl", "https://www.baidu.com")
    .build()

Router.create(url).open(this)
```

这样就很简单的达到了**自动匹配目标页的数据类型进行传递**目的。

### 使用动作路由

上面主要介绍的页面跳转的路由，也叫页面路由，但实际上。有的时候我们使用路由启动的，并不是需要启动某个页面。而是需要执行一些特殊的操作：比如**添加购物车**、**强制登出**等。此时就需要使用动作路由了。

#### 创建动作路由

动作路由通过继承**ActionSupport**类进行创建：

```java
// 与页面路由一样。添加RouterRule注解配置路由链接即可。
@RouterRule("action/hello")
public class SayHelloAction extends ActionSupport {
	@Override
	public void onRouteTrigger(Context context, Bundle data) {
		//  启动动作路由成功会触发调用此方法。
		Toast.makeText(context, "Hello! this is an action route!", Toast.LENGTH_SHORT).show();
	}
}
```

动作路由的启动方式与页面路由一致:

```java
Router.create("haoge://page/action/hello").open(context)
```

#### 指定动作路由的执行线程

动作路由是用于执行一些特殊的操作的路由，而有时候部分操作是需要在指定线程进行处理的：

动作路由提供两种指定线程的操作：

1. 启动前进行配置(优先级高)：

```java
Router.create(url).setExecutor(executor).open(context);
```

2. 在定制动作路由时，直接指定线程：

```java
@RouteExecutor(CustomExecutor.class)
@RouterRule("action/hello")
public class SayHelloAction extends ActionSupport {...}
```

**在没有配置过线程切换器时。默认使用MainThreadExecutor。指定线程为主线程**

### 在目标页获取启动链接

```java
// 先从目标页读取bundle数据
Bundle bundle = getBundle();
// 然后从bundle中读取即可
Uri lauchUri = bundle.getParcelable(Router.RAW_URI);
```

## 更多介绍

基本使用方式说明，请参考[Router:一款单品、组件化、插件化全支持的路由框架](https://juejin.im/post/5a37771f6fb9a0450e7636e0)

插件化中的使用方式，请参考[Router: 教你如何进行任意插件化环境下的路由适配](https://juejin.im/post/5a7a9e9a6fb9a06332299210)

## 更新日志

查看 [releases](https://github.com/yjfnypeu/Router/releases)

### 联系作者
email: 470368500@qq.com

<a target="_blank" href="http://shang.qq.com/wpa/qunwpa?idkey=99e758d20823a18049a06131b6d1b2722878720a437b4690e238bce43aceb5e1"><img border="0" src="http://pub.idqqimg.com/wpa/images/group.png" alt="安卓交流会所" title="安卓交流会所"></a>

或者手动加入QQ群: 108895031

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
