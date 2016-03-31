package com.vvsai.rxjava.retrofit;

import java.util.List;

/**
 * Created by lychee on 2016/3/29.
 */
public class SportsListBean {

    /**
     * status : ​1
     * msg : ​1
     * totalCount : ​30
     * result : [{"name":"乒乓球","id":"1"},{"name":"跑步","id":"10"},{"name":"健美","id":"11"},{"name":"体育舞蹈","id":"12"},{"name":"毽球","id":"13"},{"name":"门球","id":"14"},{"name":"象棋","id":"15"},{"name":"跳棋","id":"16"},{"name":"围棋","id":"17"},{"name":"国际象棋","id":"18"},{"name":"桥牌","id":"19"},{"name":"羽毛球","id":"2"},{"name":"游泳","id":"21"},{"name":"马拉松","id":"22"},{"name":"电子竞技","id":"23"},{"name":"信鸽","id":"24"},{"name":"汽车","id":"25"},{"name":"跆拳道","id":"26"},{"name":"钓鱼","id":"27"},{"name":"轮滑","id":"28"},{"name":"铁人三项","id":"29"},{"name":"网球","id":"3"},{"name":"攀岩","id":"30"},{"name":"自行车","id":"31"},{"name":"模型","id":"32"},{"name":"篮球","id":"4"},{"name":"足球","id":"5"},{"name":"排球","id":"7"},{"name":"台球","id":"8"},{"name":"高尔夫","id":"9"}]
     */

    private String status;
    private String msg;
    private String totalCount;
    /**
     * name : 乒乓球
     * id : 1
     */

    private List<ResultEntity> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public List<ResultEntity> getResult() {
        return result;
    }

    public void setResult(List<ResultEntity> result) {
        this.result = result;
    }

    public static class ResultEntity {
        private String name;
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
