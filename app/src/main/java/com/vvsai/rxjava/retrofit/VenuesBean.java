package com.vvsai.rxjava.retrofit;

import java.util.List;

/**
 * Created by lychee on 2016/1/12.
 */
public class VenuesBean {


    /**
     * status : 1
     * msg : 1
     * totalCount : 104222
     * result : {"totalCount":104222,"pageCount":10423,"arenas":[{"icon":"http://i1.s2.dpfile.com/pc/wk3QytW-9L1X1n11IgLAZIWLTbmIYj_LnpyZ31vSoDSn5MNxCNt3Lbf2fbDO1Zn1CjM_FsO3sW809PHY7spB8g.jpg","id":"10","cityName":"","address":"市传染病医院以东800米","shortName":"","sportClassName":"","sportClass":"","name":"阿克苏西湖滑雪场·胡杨林景区","openTime":""},{"icon":"http://i1.s2.dpfile.com/pc/K5kmIaavu0OwqQ-c6ZjorL0euePNzGMEJp_wWMC1l6AtK3wb9F7IB5J1s1qSdMTsCjM_FsO3sW809PHY7spB8g.jpg","id":"100","cityName":"","address":"天山路鑫淼超市旁","shortName":"","sportClassName":"","sportClass":"","name":"动感8号球吧","openTime":""},{"icon":"http://i1.s2.dpfile.com/pc/EvnTfd_dt-UQ4vHa2J2uyk0z8xIbrF8BvSrlRWrtFU7-XpaxNThYTNC-v-14AzIVCjM_FsO3sW809PHY7spB8g.jpg","id":"1000","cityName":"","address":"胜利北路","shortName":"","sportClassName":"","sportClass":"","name":"北出口跆拳道馆","openTime":""},{"icon":"http://i2.s2.dpfile.com/pc/uqUZx2xMN59tuUvuicdKuGCGi3p6-aOm0ABZFL_F6AqkgM4rs06qpyoYEiWfm4VECjM_FsO3sW809PHY7spB8g.jpg","id":"10000","cityName":"","address":"呼伦街","shortName":"","sportClassName":"","sportClass":"","name":"新时代台球厅","openTime":""},{"icon":"http://i2.s2.dpfile.com/pc/RtaNpOlZVzkFJ5liQC3hvz31cUu_xwqhQQU9kmotVZajbEh9aJcKudTGPW8y4y9NCjM_FsO3sW809PHY7spB8g.jpg","id":"100000","cityName":"","address":"东环路宝龙国际酒店对面","shortName":"","sportClassName":"","sportClass":"","name":"通泰游泳馆","openTime":""},{"icon":"http://i1.s2.dpfile.com/pc/gN7X-bUusnu6szigL-zIXRbc5VVJh6vlkjyTJjczJ2Txxiv5S677nAqgVNqRW9zSCjM_FsO3sW809PHY7spB8g.jpg","id":"100001","cityName":"","address":"商务街工商分局斜对面","shortName":"","sportClassName":"","sportClass":"","name":"奥林健身俱乐部","openTime":""},{"icon":"http://i3.s2.dpfile.com/pc/cRd6G4M7p3iUMGV2xoCXCIRWXSe_ZbKaMXCcf6xmvmVNrE1DXBy54tg1PcQWdBOiCjM_FsO3sW809PHY7spB8g.jpg","id":"100002","cityName":"","address":"鱼儿山后路","shortName":"","sportClassName":"","sportClass":"","name":"领跑健身俱乐部","openTime":""},{"icon":"http://i1.s2.dpfile.com/pc/FTX6D4N_lMIg21ycR7hnUQ5Z9f_YZ-wCsOXZBdkVdY5ul1muRgR5RGSIQNHnSdjSCjM_FsO3sW809PHY7spB8g.jpg","id":"100003","cityName":"","address":"金鹰花园小区","shortName":"","sportClassName":"","sportClass":"","name":"动岚健身","openTime":""},{"icon":"http://i1.s2.dpfile.com/pc/FTX6D4N_lMIg21ycR7hnUQ5Z9f_YZ-wCsOXZBdkVdY5ul1muRgR5RGSIQNHnSdjSCjM_FsO3sW809PHY7spB8g.jpg","id":"100004","cityName":"","address":"工业东街22号号","shortName":"","sportClassName":"","sportClass":"","name":"菲灵健身","openTime":""},{"icon":"http://i2.s2.dpfile.com/pc/Uec4suRnbqxdU9023SStUmyiI238prJw1m3iOj_QDK7Mx2OiUO-4IY6nRDC81QUHCjM_FsO3sW809PHY7spB8g.jpg","id":"100005","cityName":"","address":"西豁子街9号","shortName":"","sportClassName":"","sportClass":"","name":"全民健身中心","openTime":"6:30-22:00"}],"pageSize":10}
     */

    private int status;
    private int msg;
    private int totalCount;
    /**
     * totalCount : 104222
     * pageCount : 10423
     * arenas : [{"icon":"http://i1.s2.dpfile.com/pc/wk3QytW-9L1X1n11IgLAZIWLTbmIYj_LnpyZ31vSoDSn5MNxCNt3Lbf2fbDO1Zn1CjM_FsO3sW809PHY7spB8g.jpg","id":"10","cityName":"","address":"市传染病医院以东800米","shortName":"","sportClassName":"","sportClass":"","name":"阿克苏西湖滑雪场·胡杨林景区","openTime":""},{"icon":"http://i1.s2.dpfile.com/pc/K5kmIaavu0OwqQ-c6ZjorL0euePNzGMEJp_wWMC1l6AtK3wb9F7IB5J1s1qSdMTsCjM_FsO3sW809PHY7spB8g.jpg","id":"100","cityName":"","address":"天山路鑫淼超市旁","shortName":"","sportClassName":"","sportClass":"","name":"动感8号球吧","openTime":""},{"icon":"http://i1.s2.dpfile.com/pc/EvnTfd_dt-UQ4vHa2J2uyk0z8xIbrF8BvSrlRWrtFU7-XpaxNThYTNC-v-14AzIVCjM_FsO3sW809PHY7spB8g.jpg","id":"1000","cityName":"","address":"胜利北路","shortName":"","sportClassName":"","sportClass":"","name":"北出口跆拳道馆","openTime":""},{"icon":"http://i2.s2.dpfile.com/pc/uqUZx2xMN59tuUvuicdKuGCGi3p6-aOm0ABZFL_F6AqkgM4rs06qpyoYEiWfm4VECjM_FsO3sW809PHY7spB8g.jpg","id":"10000","cityName":"","address":"呼伦街","shortName":"","sportClassName":"","sportClass":"","name":"新时代台球厅","openTime":""},{"icon":"http://i2.s2.dpfile.com/pc/RtaNpOlZVzkFJ5liQC3hvz31cUu_xwqhQQU9kmotVZajbEh9aJcKudTGPW8y4y9NCjM_FsO3sW809PHY7spB8g.jpg","id":"100000","cityName":"","address":"东环路宝龙国际酒店对面","shortName":"","sportClassName":"","sportClass":"","name":"通泰游泳馆","openTime":""},{"icon":"http://i1.s2.dpfile.com/pc/gN7X-bUusnu6szigL-zIXRbc5VVJh6vlkjyTJjczJ2Txxiv5S677nAqgVNqRW9zSCjM_FsO3sW809PHY7spB8g.jpg","id":"100001","cityName":"","address":"商务街工商分局斜对面","shortName":"","sportClassName":"","sportClass":"","name":"奥林健身俱乐部","openTime":""},{"icon":"http://i3.s2.dpfile.com/pc/cRd6G4M7p3iUMGV2xoCXCIRWXSe_ZbKaMXCcf6xmvmVNrE1DXBy54tg1PcQWdBOiCjM_FsO3sW809PHY7spB8g.jpg","id":"100002","cityName":"","address":"鱼儿山后路","shortName":"","sportClassName":"","sportClass":"","name":"领跑健身俱乐部","openTime":""},{"icon":"http://i1.s2.dpfile.com/pc/FTX6D4N_lMIg21ycR7hnUQ5Z9f_YZ-wCsOXZBdkVdY5ul1muRgR5RGSIQNHnSdjSCjM_FsO3sW809PHY7spB8g.jpg","id":"100003","cityName":"","address":"金鹰花园小区","shortName":"","sportClassName":"","sportClass":"","name":"动岚健身","openTime":""},{"icon":"http://i1.s2.dpfile.com/pc/FTX6D4N_lMIg21ycR7hnUQ5Z9f_YZ-wCsOXZBdkVdY5ul1muRgR5RGSIQNHnSdjSCjM_FsO3sW809PHY7spB8g.jpg","id":"100004","cityName":"","address":"工业东街22号号","shortName":"","sportClassName":"","sportClass":"","name":"菲灵健身","openTime":""},{"icon":"http://i2.s2.dpfile.com/pc/Uec4suRnbqxdU9023SStUmyiI238prJw1m3iOj_QDK7Mx2OiUO-4IY6nRDC81QUHCjM_FsO3sW809PHY7spB8g.jpg","id":"100005","cityName":"","address":"西豁子街9号","shortName":"","sportClassName":"","sportClass":"","name":"全民健身中心","openTime":"6:30-22:00"}]
     * pageSize : 10
     */

    private ResultEntity result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMsg() {
        return msg;
    }

    public void setMsg(int msg) {
        this.msg = msg;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public ResultEntity getResult() {
        return result;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public static class ResultEntity {
        private int totalCount;
        private int pageCount;
        private int pageSize;
        /**
         * icon : http://i1.s2.dpfile.com/pc/wk3QytW-9L1X1n11IgLAZIWLTbmIYj_LnpyZ31vSoDSn5MNxCNt3Lbf2fbDO1Zn1CjM_FsO3sW809PHY7spB8g.jpg
         * id : 10
         * cityName :
         * address : 市传染病医院以东800米
         * shortName :
         * sportClassName :
         * sportClass :
         * name : 阿克苏西湖滑雪场·胡杨林景区
         * openTime :
         */

        private List<ArenasEntity> arenas;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public List<ArenasEntity> getArenas() {
            return arenas;
        }

        public void setArenas(List<ArenasEntity> arenas) {
            this.arenas = arenas;
        }

        public static class ArenasEntity {
            private String icon;
            private String id;
            private String cityName;
            private String address;
            private String shortName;
            private String sportClassName;
            private String sportClass;
            private String name;
            private String openTime;

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCityName() {
                return cityName;
            }

            public void setCityName(String cityName) {
                this.cityName = cityName;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getShortName() {
                return shortName;
            }

            public void setShortName(String shortName) {
                this.shortName = shortName;
            }

            public String getSportClassName() {
                return sportClassName;
            }

            public void setSportClassName(String sportClassName) {
                this.sportClassName = sportClassName;
            }

            public String getSportClass() {
                return sportClass;
            }

            public void setSportClass(String sportClass) {
                this.sportClass = sportClass;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getOpenTime() {
                return openTime;
            }

            public void setOpenTime(String openTime) {
                this.openTime = openTime;
            }
        }
    }
}
