package com.example.acer.collectionplus.JavaBean;

import com.example.acer.collectionplus.Base.BaseBean;

public class UserDetailInfoBean extends BaseBean<UserDetailInfoBean.Entity> {

    public static class Entity {

        private String collectNum;
        private String folderNum;
        private String fansNum;
        private String followNum;

        public Entity(String collectNum, String folderNum, String fansNum, String followNum) {
            this.collectNum = collectNum;
            this.folderNum = folderNum;
            this.fansNum = fansNum;
            this.followNum = followNum;
        }

        public String getCollectNum() {
            return collectNum;
        }

        public void setCollectNum(String collectNum) {
            this.collectNum = collectNum;
        }

        public String getFolderNum() {
            return folderNum;
        }

        public void setFolderNum(String folderNum) {
            this.folderNum = folderNum;
        }

        public String getFansNum() {
            return fansNum;
        }

        public void setFansNum(String fansNum) {
            this.fansNum = fansNum;
        }

        public String getFollowNum() {
            return followNum;
        }

        public void setFollowNum(String followNum) {
            this.followNum = followNum;
        }
    }
}
