package com.example.acer.collectionplus.JavaBean;

import com.example.acer.collectionplus.Base.BaseBean;
import com.google.gson.annotations.SerializedName;

public class DirBean extends BaseBean<DirBean.Entity>{

    public static class Entity{
        private int ID;
        private String dirname;
        private String username;
        private String time;
        private String type;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDirname() {
            return dirname;
        }

        public void setDirname(String dirname) {
            this.dirname = dirname;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
