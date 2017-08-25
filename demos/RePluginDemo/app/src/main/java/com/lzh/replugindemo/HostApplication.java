package com.lzh.replugindemo;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.lzh.nonview.router.RouterConfiguration;
import com.lzh.nonview.router.anno.RouteConfig;
import com.lzh.nonview.router.host.RouterHostService;
import com.lzh.replugindemo.verify.RePluginVerification;
import com.lzh.router.RouterRuleCreator;
import com.lzh.router.replugin.host.HostRouterConfiguration;
import com.lzh.router.replugin.update.IUpdateCombine;
import com.lzh.router.replugin.update.UpdateRePluginCallbacks;
import com.qihoo360.replugin.RePluginApplication;
import com.qihoo360.replugin.RePluginCallbacks;

import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.model.CheckEntity;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.model.UpdateChecker;
import org.lzh.framework.updatepluginlib.model.UpdateParser;
import org.lzh.framework.updatepluginlib.strategy.UpdateStrategy;

// 指定生成路由的baseUrl。此baseUrl会与使用RouteRule所指定的path所组合。形成一个完整的路由地址。
// 生成的路由表。参考下方添加路由规则的RouterRuleCreator类。
@RouteConfig(baseUrl = "host://")
public class HostApplication extends RePluginApplication{


    @Override
    public void onCreate() {
        super.onCreate();

        // 启动远程路由前。加入安全验证器。
        RouterHostService.setVerify(new RePluginVerification());

        HostRouterConfiguration.init("com.lzh.replugindemo", this);
        // 添加路由规则。
        RouterConfiguration.get().addRouteCreator(new RouterRuleCreator());
    }

    @Override
    protected RePluginCallbacks createCallbacks() {
        return new UpdateRePluginCallbacks(this,
                // 设置UpdateConfig。用于进行远程plugin更新。
                UpdateConfig.createConfig()
                        .updateChecker(new PluginChecker())
                        .jsonParser(new JsonParser())
                        .strategy(new PluginStrategy()),
                // 设置远程插件更新接口api组装。
                new HostUpdateCombine());
    }

    /**
     * 插件接口返回数据json解析器。在此解析出接口api更新信息。并下载。
     */
    private static class JsonParser implements UpdateParser {
        @Override
        public Update parse(String httpResponse) throws Exception {
            return JSON.parseObject(httpResponse, Update.class);
        }
    }

    /**
     * 对插件api通过上方JsonParser解析后的更新实体类进行检查。检查是否需要进行更新下载安装。
     */
    private static class PluginChecker implements UpdateChecker {

        @Override
        public boolean check(Update update) throws Exception {
            return true;
        }
    }

    /**
     * 插件的更新通知策略：显示检查到有插件可用时的弹窗以及下载进度条。
     */
    private static class PluginStrategy implements UpdateStrategy {
        @Override
        public boolean isShowUpdateDialog(Update update) {
            return true;
        }

        @Override
        public boolean isAutoInstall() {
            return true;
        }

        @Override
        public boolean isShowDownloadDialog() {
            return true;
        }
    }

    /**
     * 根据插件名组装出真正的插件api地址。
     */
    private static class HostUpdateCombine implements IUpdateCombine {

        @Override
        public CheckEntity combine(String alias) {
            return new CheckEntity().setUrl("https://raw.githubusercontent.com/JumeiRdGroup/Router/master/demos/RePluginDemo/mocked/api/" + alias + ".json");
        }
    }
}
