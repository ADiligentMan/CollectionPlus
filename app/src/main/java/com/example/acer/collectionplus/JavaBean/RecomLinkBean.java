package com.example.acer.collectionplus.JavaBean;

import com.example.acer.collectionplus.Base.BaseBean;

public class RecomLinkBean extends BaseBean<RecomLinkBean.Entity> {
    public static class Entity{
//        public Entity(String title) {
//            this.title = title;
//        }

        private String title;
        private String value;
        private String picPath;
        private String time;


        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getPicPath() {
            return picPath;
        }

        public void setPicPath(String picPath) {
            this.picPath = picPath;
        }


        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
