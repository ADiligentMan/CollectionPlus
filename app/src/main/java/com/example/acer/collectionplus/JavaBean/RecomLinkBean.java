package com.example.acer.collectionplus.JavaBean;

import com.example.acer.collectionplus.Base.BaseBean;

public class RecomLinkBean extends BaseBean<RecomLinkBean.Entity> {
    public static class Entity{
        public Entity(String title) {
            this.title = title;
        }

        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}