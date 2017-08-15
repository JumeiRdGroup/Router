package com.lzh.replugindemo;

import org.lzh.framework.updatepluginlib.callback.UpdateCheckCB;
import org.lzh.framework.updatepluginlib.callback.UpdateDownloadCB;
import org.lzh.framework.updatepluginlib.model.Update;

import java.io.File;

/**
 * Created by haoge on 2017/8/14.
 */

public class UpdateCallback implements UpdateCheckCB, UpdateDownloadCB{
    @Override
    public void onCheckStart() {

    }

    @Override
    public void hasUpdate(Update update) {

    }

    @Override
    public void noUpdate() {

    }

    @Override
    public void onCheckError(Throwable t) {

    }

    @Override
    public void onUserCancel() {

    }

    @Override
    public void onCheckIgnore(Update update) {

    }

    @Override
    public void onUpdateStart() {

    }

    @Override
    public void onUpdateComplete(File file) {

    }

    @Override
    public void onUpdateProgress(long current, long total) {

    }

    @Override
    public void onUpdateError(Throwable t) {

    }
}
