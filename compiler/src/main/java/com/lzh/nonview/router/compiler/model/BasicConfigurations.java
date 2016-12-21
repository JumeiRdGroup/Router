package com.lzh.nonview.router.compiler.model;

/**
 * Created by haoge on 2016/12/21.
 */

public class BasicConfigurations {
    private String schema;
    private String pack;

    public BasicConfigurations(String schema, String pack) {
        this.schema = schema;
        this.pack = pack;
    }

    public String getSchema() {
        return schema;
    }

    public String getPack() {
        return pack;
    }
}
