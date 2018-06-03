package com.example.acer.collectionplus.JavaBean;

import com.example.acer.collectionplus.Base.BaseBean;
import com.google.gson.annotations.SerializedName;

/**
 * Created by asus on 2018/5/19.
 */

public class UserBean extends BaseBean<UserBean.Entity> {


    public static class Entity {
        private String username;
        private String password;
        private String phone;
        private String email;
        private String gender;
        private String introduce;
        private String address;
        private String age;
        private String sharenumber;
        private String likenumber;
        @SerializedName("fannumber")
        private String funnumber;
        private String sourcenumber;
        private String notenumber;





        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getSharenumber() {
            return sharenumber;
        }

        public void setSharenumber(String sharenumber) {
            this.sharenumber = sharenumber;
        }

        public String getLikenumber() {
            return likenumber;
        }

        public void setLikenumber(String likenumber) {
            this.likenumber = likenumber;
        }

        public String getFunnumber() {
            return funnumber;
        }

        public void setFunnumber(String funnumber) {
            this.funnumber = funnumber;
        }

        public String getNotenumber() {
            return notenumber;
        }

        public void setNotenumber(String notenumber) {
            this.notenumber = notenumber;
        }

        public String getSourcenumber() {
            return sourcenumber;
        }

        public void setSourcenumber(String sourcenumber) {
            this.sourcenumber = sourcenumber;
        }
        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
