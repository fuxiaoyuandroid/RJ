package com.rxjava.rj.beans;

import android.util.Log;

/**
 * Created by Administrator on 2017/12/1 0001.
 */

public class TranslationAfter {
    private int status;
    private content content;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public content getContent() {
        return content;
    }

    public void setContent(content content) {
        this.content = content;
    }

    private static class content{

        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errorNo;

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getVendor() {
            return vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }

        public String getOut() {
            return out;
        }

        public void setOut(String out) {
            this.out = out;
        }

        public int getErrorNo() {
            return errorNo;
        }

        public void setErrorNo(int errorNo) {
            this.errorNo = errorNo;
        }
    }

    public String show() {

        //Log.d("RxJava", "翻译内容After = " + content.out);
        return "翻译内容After = " + content.out;
    }
}
