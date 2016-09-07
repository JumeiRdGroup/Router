package com.lzh.nonview.router.module;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 16/9/6.
 */
public class RawParse {

    /**
     * pkg : com.lzh.nonview.router.demo
     * scheme : jumei
     * route : [{"path":"main","clz":"MainActivity","pms":{"i":"key","f":"price"}}]
     */

    private String pkg;
    private String scheme;
    /**
     * path : main
     * clz : MainActivity
     * pms : [{"i":"key"},{"f":"price"}]
     */

    private List<RouteBean> route;

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public List<RouteBean> getRoute() {
        return route;
    }

    public void setRoute(List<RouteBean> route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return "RawParse{" +
                "pkg='" + pkg + '\'' +
                ", scheme='" + scheme + '\'' +
                ", route=" + route +
                '}';
    }

    public static class RouteBean {
        private String path;
        private String clz;
        /**
         * i : key
         */

        private Map<String,String> pms;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getClz() {
            return clz;
        }

        public void setClz(String clz) {
            this.clz = clz;
        }

        public Map<String,String> getPms() {
            return pms;
        }

        public void setPms(Map<String,String> pms) {
            this.pms = pms;
        }

        public static class PmsBean {
            private String i;

            public String getI() {
                return i;
            }

            public void setI(String i) {
                this.i = i;
            }
        }

        @Override
        public String toString() {
            return "RouteBean{" +
                    "path='" + path + '\'' +
                    ", clz='" + clz + '\'' +
                    ", pms=" + pms +
                    '}';
        }
    }
}
