package com.example.acer.collectionplus.Model;

import android.util.Log;

import com.example.acer.collectionplus.Base.BaseBean;
import com.example.acer.collectionplus.Base.BaseLoadListener;
import com.example.acer.collectionplus.Helper.SharedHelper;
import com.example.acer.collectionplus.Helper.TimeHelper;
import com.example.acer.collectionplus.Helper.ToastUtils;
import com.example.acer.collectionplus.Http.HttpUtils;
import com.example.acer.collectionplus.JavaBean.DirBean;
import com.example.acer.collectionplus.JavaBean.SimpleDirBean;
import com.example.acer.collectionplus.JavaBean.SimpleUserBean;
import com.example.acer.collectionplus.JavaBean.UserBean;
import com.example.acer.collectionplus.RetrofitInterface.MainFragment;
import com.example.acer.collectionplus.RetrofitInterface.UserFragmentImpl;
import com.example.acer.collectionplus.View.UserFragment;
import com.example.acer.collectionplus.databinding.FragmentUserBinding;

import org.jsoup.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by asus on 2018/5/19.
 */

public class UserModel implements IUserModel {
    public static final  String  TAG = "UserModel" ;
    //定义binding对象
    private List<SimpleUserBean> simpleUserBeanList = new ArrayList<SimpleUserBean>();
    Boolean issuccess;
    public void loadData(final BaseLoadListener<SimpleUserBean> loadListener, Map<String, String> params) {
        //通过shareshelper得到用户名数据
        final String username = SharedHelper.getInstance().getValue("username");
        Log.d("UserModel",username);
        Observable<UserBean> observable = HttpUtils.getRetrofit().create(UserFragmentImpl.class).getUserInfo(username);
       // HttpUtils.getRetrofit().create(UserFragmentImpl.class);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<UserBean>() {
                    public void onNext(@NonNull UserBean userBean) {
                        Log.i(TAG, "onNext: ");
                        //转化成simplebean
                        Log.d(TAG,"onnext方法执行了");

                       UserOnNext(userBean);
                    }
                    @Override
                    public void onError(Throwable e) {
                       //Log.d(TAG,simpleUserBean.toString());

                        Log.i(TAG, "onError: " + e.getMessage());
                        loadListener.loadFailure(e.getMessage());
                    }
                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: ");
                        loadListener.loadSuccess(simpleUserBeanList);
                        loadListener.loadComplete();
                    }
                });




    }



    //UserModel层 修改用户信息
    @Override
    public void modifyuser(final BaseLoadListener<BaseBean> loadListener, Map<String, String> usermap) {
       Log.d("usermodelmine","success");
       Log.d("modelusermap",usermap.toString());
       //和服务器进行连接
        Observable<BaseBean> observablemodify=HttpUtils.getRetrofit().
                create(UserFragmentImpl.class).modifyUser(usermap);
      Log.d("111","success");

        observablemodify.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        loadListener.loadFailure(baseBean.getInfo());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d("oncomplete","success");

                    }
                });

    }


    /**
     * 把UserBean转化为SimpleUserBean
     *

     */
    //通知页面自动更新
    private void UserOnNext(UserBean userBean) {
        List<UserBean.Entity> entities = userBean.getData();
        simpleUserBeanList.clear();
        if (entities != null && entities.size() > 0) {
            for (UserBean.Entity entity : entities) {
                //获取
                 // String dirname = entity.getDirname();
                 String username=entity.getUsername();
                 String password=entity.getPassword();
                 String phone=entity.getPhone();
                 String email=entity.getEmail();
                 String gender=entity.getGender();
                 String introduce=entity.getIntroduce();
                 String age=entity.getAge();
                 String sharenumber=entity.getSharenumber();
                 String likenumber=entity.getLikenumber();
                 String funnumber=entity.getFunnumber();
                 String sourcenumber=entity.getSourcenumber();
                 String notenumber=entity.getNotenumber();
                 String address=entity.getAddress();

                //设置

                SimpleUserBean sub = new SimpleUserBean();
                sub.username.set(username);
                sub.password.set(password);
                sub.phone.set(phone);
                sub.email.set(email);
                sub.gender.set(gender);
                sub.introduce.set(introduce);

                sub.age.set(age);
                sub.sharenumber.set(sharenumber);
                sub.likenumber.set(likenumber);
                sub.funnumber.set(funnumber);
                sub.sourcenumber.set(sourcenumber);
                sub.notenumber.set(notenumber);
                sub.address.set(address);

               simpleUserBeanList.add(sub);
            }
        }

    }



}
