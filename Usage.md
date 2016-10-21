# Router框架具体使用详解

- 配置Route规则:

```java
// 使用add方式添加规则.可以添加多种规则.适用于多组协作开发中
Router.addRouteCreator(new RouteCreator {

       @Override
       public Map<String, RouteMap> createRouteRules() {
           Map<String,RouteMap> routes = new HashMap<>();
           // map-> key     : 用作route rule设定,用作与url进行匹配;
           // map-> value   : RouteMap类用作Route目标指定.包括与此rule适配到的需要跳转的Activity类名.需要传的参数类型
           routes.put("jumei://main",
                   new RouteMap(ParamsActivity.class)
                   .addParam("price",RouteMap.FLOAT)
                   .addParam("bookName",RouteMap.STRING)
           );
           routes.put("jumeimail://main",
                   new RouteMap("com.lzh.nonview.demo.RegisterActivity")
                   );
           return routes;
       }
   });
```

- 配置监听回调,拦截方法:

设置全局的监听器。
```java
// 对Router设置Activity Route Callback,作辅助功能
Router.setRouteCallback(new RouteCallback() {

    @Override
    public boolean interceptOpen(Uri uri, Context context, ActivityRouteBundleExtras extras) {
        // 拦截方法,返回true.表示此open事件被拦截.不继续运行,false不拦截.可在此进行登录状态判断等
        // 示例代码:
        if (DataManager.INSTANCE.isLogin()) return false;

        Toast.makeText(App.this, "未登录.请先登录", Toast.LENGTH_SHORT).show();
        Intent loginIntent = new Intent(context,LoginActivity.class);
        loginIntent.putExtra("uri",uri);
        loginIntent.putExtra("extra",extras);
        context.startActivity(loginIntent);
        return true;
    }

    @Override
    public void notFound(Uri uri, NotFoundException e) {
        // NotFound包括两种情形.一种是route rule未匹配到.一种是切尔西到route rule后,匹配的Activity不存在.
        Toast.makeText(App.this, e.getNotFoundName() + " not find", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOpenSuccess(Uri uri, String clzName) {
        // 可在此进行route追踪,用户可以在此知道具体此url是匹配跳转到哪个Activity中
        Toast.makeText(App.this, String.format("Launch activity %s success",clzName), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOpenFailed(Uri uri, Exception e) {
        // 其他异常回调通知
    }
});
```
或者对某个url设置单独的监听器:
```java
Router.create(url).setCallback(callback).open(context);
```

- 使用Router进行跳转

使用分为两种方式:
1.直接通过url直接启动:

```java
Router.create(url).open(context);
```

*此种方式跳转的.url的传参数只能为基本数据类型与String类型.*

2.通过添加额外数据进行各种跳转操作:

```java
// 获取Route对象.并添加额外数据
Bundle extras = new Bundle();
...
Router.create("jumei://main").getActivityRoute()
        .addExtras(extras)// 添加额外参数
        .requestCode(100)
        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        .setAnim(R.anim.anim_fade_in,R.anim.anim_fade_out)
        .open(this);
```

*这种方式,可以任由用户添加部分额外数据.需要注意.此种方式需要url为非http/https的*

3.也可直接获取Intent对象,方便在通知栏使用:

```java
Intent intent = Router.create("jumei://main").getActivityRoute().createIntent(context);
```

- 结合Parceler框架使用

如果你的项目中使用的Parceler框架。并且添加了Router框架的注解处理器:*apt 'org.lzh.nonview.router:router-compiler:0.3'*即可通过api提供的注解*RouterRule*自动为您的Activity创建对应的路由规则:

1.给需要创建路由规则的Activity添加*RouterRule*注解

```java
// RouterRule支持两个参数。一个是此处默认的value:设置与此Activity对应的scheme规则。
// 一个是pack.用于指定此路由规则需要生成于哪个包下的创建器中。
@RouterRule("haoge://haoge.cn/parceler")
public class ParcelerActivity extends Activity {

    @Arg
    String username;
    @Arg
    String address;
}
```

2.添加后make project一下。让编译器替你生成包含有对应规则的创建器,如下所示。以下代码即为编译时通过Route所生成的:

```java
public class RouterRuleCreator implements RouteCreator {
  @Override
  public Map<String, RouteMap> createRouteRules() {
    Map<String,RouteMap> routes = new HashMap<>();
    routes.put("haoge://haoge.cn/parceler",new RouteMap(ParcelerActivity.class).addParam("address",RouteMap.STRING).addParam("username",RouteMap.STRING));;
    return routes;
  }
}
```

3.通过将此生成的创建器添加到Router中:

```java
Router.addRouteCreator(new RouterRuleCreator());
```

继续使用吧