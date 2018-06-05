package com.example.acer.collectionplus.JavaBean;

import com.example.acer.collectionplus.Base.BaseBean;

public class RecomUserBean extends BaseBean<RecomUserBean.Entity> {
    public static class Entity{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
