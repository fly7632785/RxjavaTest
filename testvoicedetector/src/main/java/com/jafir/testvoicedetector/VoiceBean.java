package com.jafir.testvoicedetector;

import java.util.List;

/**
 * Created by jafir on 16/10/24.
 */

public class VoiceBean {

    /**
     * sn : 2
     * ls : true
     * bg : 0
     * ed : 0
     * ws : [{"bg":0,"cw":[{"sc":0,"w":"！"}]}]
     */

    private int sn;
    private boolean ls;
    private int bg;
    private int ed;
    /**
     * bg : 0
     * cw : [{"sc":0,"w":"！"}]
     */

    private List<WsBean> ws;

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public boolean isLs() {
        return ls;
    }

    public void setLs(boolean ls) {
        this.ls = ls;
    }

    public int getBg() {
        return bg;
    }

    public void setBg(int bg) {
        this.bg = bg;
    }

    public int getEd() {
        return ed;
    }

    public void setEd(int ed) {
        this.ed = ed;
    }

    public List<WsBean> getWs() {
        return ws;
    }

    public void setWs(List<WsBean> ws) {
        this.ws = ws;
    }

    public static class WsBean {

        @Override
        public String toString() {
            return "WsBean{" +
                    "bg=" + bg +
                    ", cw=" + cw +
                    '}';
        }

        private int bg;
        /**
         * sc : 0.0
         * w : ！
         */

        private List<CwBean> cw;

        public int getBg() {
            return bg;
        }

        public void setBg(int bg) {
            this.bg = bg;
        }

        public List<CwBean> getCw() {
            return cw;
        }

        public void setCw(List<CwBean> cw) {
            this.cw = cw;
        }

        public static class CwBean {

            @Override
            public String toString() {
                return "CwBean{" +
                        "sc=" + sc +
                        ", w='" + w + '\'' +
                        '}';
            }

            private double sc;
            private String w;

            public double getSc() {
                return sc;
            }

            public void setSc(double sc) {
                this.sc = sc;
            }

            public String getW() {
                return w;
            }

            public void setW(String w) {
                this.w = w;
            }
        }
    }

    @Override
    public String toString() {
        return "VoiceBean{" +
                "sn=" + sn +
                ", ls=" + ls +
                ", bg=" + bg +
                ", ed=" + ed +
                ", ws=" + ws +
                '}';
    }
}
