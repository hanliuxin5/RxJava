package com.vvsai.rxjava.retrofit;

/**
 * Created by lychee on 2016/3/30.
 */
public class UploadFileBean {

    /**
     * status : 1
     * msg : 1
     * totalCount : 0
     * result : {"info":"file upload success","fileName":"1458742768797.jpg","fileSize":89506,"savePath":"c8d8c8945357736dbee8ef30cbd9b9f1.jpg"}
     */

    private int status;
    private int msg;
    private int totalCount;
    /**
     * info : file upload success
     * fileName : 1458742768797.jpg
     * fileSize : 89506
     * savePath : c8d8c8945357736dbee8ef30cbd9b9f1.jpg
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
        private String info;
        private String fileName;
        private int fileSize;
        private String savePath;

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public int getFileSize() {
            return fileSize;
        }

        public void setFileSize(int fileSize) {
            this.fileSize = fileSize;
        }

        public String getSavePath() {
            return savePath;
        }

        public void setSavePath(String savePath) {
            this.savePath = savePath;
        }
    }
}
