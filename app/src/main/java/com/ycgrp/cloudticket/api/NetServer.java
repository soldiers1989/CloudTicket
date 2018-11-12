package com.ycgrp.cloudticket.api;

import android.text.TextUtils;


import com.ycgrp.cloudticket.CloudTicketApplication;
import com.ycgrp.cloudticket.bean.AccountBlanceBean;
import com.ycgrp.cloudticket.bean.ApproveordrejetBean;
import com.ycgrp.cloudticket.bean.BeforRetailerBean;
import com.ycgrp.cloudticket.bean.CloudTticketDetailsBean;
import com.ycgrp.cloudticket.bean.GetRegisterInfoBean;
import com.ycgrp.cloudticket.bean.LoginResponseBean;
import com.ycgrp.cloudticket.bean.MyInfoBean;
import com.ycgrp.cloudticket.bean.MyLoanRecordBean;
import com.ycgrp.cloudticket.bean.ReleaseCloudBean;
import com.ycgrp.cloudticket.bean.StartCloudTicketBean;
import com.ycgrp.cloudticket.bean.TradeBean;
import com.ycgrp.cloudticket.bean.UserInfoBean;
import com.ycgrp.cloudticket.bean.WaitApproveBean;
import com.ycgrp.cloudticket.event.MyCloudTicketBean;
import com.ycgrp.cloudticket.mvp.presenter.LoginBackPS;
import com.ycgrp.cloudticket.utils.GsonUtil;
import com.ycgrp.cloudticket.utils.GsonUtils;
import com.ycgrp.cloudticket.utils.L;


import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class NetServer {
    private NetAPI netAPI;
    private Observer obsever;
    private CompositeDisposable compositeDisposable;

    private NetServer() {
        netAPI = RetrofitClient.getInstance().getmRetrofit().create(NetAPI.class);
        compositeDisposable = new CompositeDisposable();
    }


    public static NetServer getInstance() {
        return new NetServer();
    }

    /**
     * 注册
     *
     * @param phonenumber
     * @param name
     * @param password
     * @param listener
     */
    public void register(String phonenumber, String name, String password, BaseCallBackListener<GetRegisterInfoBean> listener) {
        netAPI.getRegisterInfo(phonenumber, name, password).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(getObserver(listener));
    }

    /**
     * 登录
     *
     * @param phonenumber
     * @param password
     * @param listener
     */
    public void login(String phonenumber, String password, BaseCallBackListener<LoginResponseBean> listener) {
        netAPI.login(phonenumber, password).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(getObserver(listener));
    }

    /**
     * 发起云票
     *
     * @param amount       金额
     * @param guarantor_id 接收人
     * @param listener
     */
    public void startCloudTicket(String amount, String guarantor_id, BaseCallBackListener<StartCloudTicketBean> listener) {
        netAPI.startCloudTicket(amount, guarantor_id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(getObserver(listener));
    }

    /**
     * 获取待审批列表
     */
    public void getWaitApproveList(BaseCallBackListener<WaitApproveBean> listener) {
        if (checkToken(getObserver(listener))) {
//            goSubscribe(netAPI.getWaitApproveList());
            netAPI.getWaitApproveList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(getObserver(listener));
        }

    }


    /**
     * 审批云票
     */
    public void approveOrRejet(String id,String maturity_date,String status,BaseCallBackListener<ApproveordrejetBean> listener) {

        if (checkToken(getObserver(listener))){
            netAPI.approveOrRejet(id,maturity_date,status).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(getObserver(listener));
        }

    }

    /**
     * 我持有的云票
     * @param listener
     */
    public void getMyCloudTicket(BaseCallBackListener<MyCloudTicketBean> listener){
        if (checkToken(getObserver(listener))){
            netAPI.getMyCloudTicket().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(getObserver(listener));
        }
    }

    /**
     * 获取自己的信息
     * @param listener
     */
    public void getMyInfo(BaseCallBackListener<MyInfoBean> listener){
        if (checkToken(getObserver(listener))){
            netAPI.getMyInfo().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(getObserver(listener));
        }
    }

    /**
     *
     * @param id 用户id
     * @return  返回用户信息
     */
    public void getUserInfo(String id,BaseCallBackListener<UserInfoBean> listener){
        if (checkToken(getObserver(listener))){
            netAPI.getUserInfo(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(getObserver(listener));
        }
    }

    /**
     * 获取自己的信息
     * @param listener
     */
    public void getMyLoanRecord(BaseCallBackListener<MyLoanRecordBean> listener){
        if (checkToken(getObserver(listener))){
            netAPI.getMyLoanRecord().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(getObserver(listener));
        }
    }

    /**
     * 发布云票
     * @param bill_id 云票id
     * @param interest_rate 利率
     * @param listener
     */
    public void release( String bill_id, String interest_rate,BaseCallBackListener<ReleaseCloudBean> listener){
        if (checkToken(getObserver(listener))){

            netAPI.release(bill_id,interest_rate).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(getObserver(listener));
        }
    }

    /**
     * 撤回云票
     * @param bill_id 拥有的云票id
     * @param id 发布到市场的id
     * @param listener
     */
    public void recall( String bill_id,String id,BaseCallBackListener<ResponseBody> listener){
        if (checkToken(getObserver(listener))){

            netAPI.recall(bill_id,id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(getObserver(listener));
        }
    }

    /**
     * 购买云票
     * @param release_id 最后一次发布的id
     * @param listener
     */
    public void buyCloudTicket(String release_id,BaseCallBackListener<ResponseBody> listener){
        if (checkToken(getObserver(listener))){

            netAPI.buyCloudTicket(release_id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(getObserver(listener));
        }
    }

    /**
     * 实名认证
     * @param id_number 身份证号
     * @param address 地址
     * @param listener
     */
  public void verified(String id_number,String address,BaseCallBackListener<ResponseBody> listener){
        if (checkToken(getObserver(listener))){

            netAPI.verified(id_number,address).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(getObserver(listener));
        }
    }

    /**
     * 获取以前担保过的零售商
     * @param listener
     */
  public void getBeforRetailer(BaseCallBackListener<BeforRetailerBean> listener){
        if (checkToken(getObserver(listener))){

            netAPI.getBeforRetailer().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(getObserver(listener));
        }
    }

    /**
     * 获取市场上正在出售的云票
     * @param listener
     */
    public void getTrade(BaseCallBackListener<TradeBean> listener){
        if (checkToken(getObserver(listener))){

            netAPI.getTrade().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(getObserver(listener));
        }
    }

    /**
     * 获取云票详情
     * @param listener
     * @param  id 云票id
     */
    public void getCloudTicketDetial(String id,BaseCallBackListener<CloudTticketDetailsBean> listener){
        if (checkToken(getObserver(listener))){
            netAPI.getCloudTicketDetial(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(getObserver(listener));
        }
    }

    /**
     * 获取账户明细
     * @param listener
     */
    public void getAccountBlance(BaseCallBackListener<AccountBlanceBean> listener){
        if (checkToken(getObserver(listener))){
            netAPI.getAccountBlance().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(getObserver(listener));
        }
    }


    /**
     * 保存事件
     *
     * @param value
     */
    public void addDisposable(Disposable value) {
        if (compositeDisposable != null) {
            compositeDisposable.add(value);
        }
    }


    /**
     * 检测token是否存在
     *
     * @return
     */
    public boolean checkToken(Observer observer) {
        LoginResponseBean login = CloudTicketApplication.getInstance().getmLoginResponse();
        if (login == null) {//尝试从sp取出login info
            String userinfo = LoginBackPS.getInstance().getCache(CloudTicketApplication.getContext());
            login = GsonUtils.getBean(userinfo, LoginResponseBean.class);
            CloudTicketApplication.getInstance().setmLoginResponse(login);
        }
        if (login == null) {
            observer.onError(new Throwable("unknown user"));
            return false;
        } else {
            if (login.getToken() == null) {
                return false;
            }
            if (TextUtils.isEmpty(login.getToken())) {
                observer.onError(new Throwable("unknown user"));
                return false;
            } else {
                return true;
            }
        }
    }


//     ----------------------------------------------  这是一个理智的分割线  --------------------------------------------------


    /**
     * 封装 observe，在中间层处理 错误返回码 或者正确的 java bean
     *
     * @param listener
     * @return
     */

    Observer getObserver(final BaseCallBackListener listener) {
        obsever = new Observer() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                addDisposable(d);
                if (listener != null) {
                    listener.onBefore(d);
                }
            }

            @Override
            public void onNext(@NonNull Object o) {
                if (o != null) {
                    String response = GsonUtil.toJsonString(o);
//                    L.loggerD("返回的 response = \n" + response);
                    if (o instanceof NullPointerException) {
                        L.i("访问成功，但是没有返回数据，制空，不然会出现类转换错误");
                        o = null;
                    }
                    if (!TextUtils.isEmpty(response)) {
                        String errorMessage = null;
                        try {
//                            errorMessage = ErrorLog.getError(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (!TextUtils.isEmpty(errorMessage)) {
                            if (listener != null) {
                                listener.onError(new Throwable(errorMessage));
//                                listener.errorByResult(errorMessage);
                            }
                        } else {
                            if (listener != null) {
                                listener.onSuccess(o);
                            }
                        }
                    }

                } else {
                    if (listener != null) {
                        listener.onSuccess(o);
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                if (e != null) {
                    if (e instanceof HttpException) {
                        ResponseBody body = null;
                        try {
                            body = ((HttpException) e).response().errorBody();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        if (body != null) {
                            String s = null;
                            try {
//                                s = ErrorLog.getError(body.string());
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
//                            L.loggerE("拿到error body = ", e);
                            if (listener != null) {
                                if (TextUtils.isEmpty(s)) {
                                    listener.onError(e);
                                } else {
                                    listener.onError(new Throwable(s));
                                }
                            }
//                                listener.errorByResult(ErrorLog.getError(s));
                            return;
                        }

                    }
//                    L.loggerE(e.getMessage() + "\n"
//                            + e.getLocalizedMessage() + "\n"
//                    );
                }
                if (listener != null) {
                    listener.onError(e);
                }
            }

            @Override
            public void onComplete() {
                if (listener != null) {
                    listener.onComplete();
                }
            }
        };
        return obsever;
    }

    /**
     * 订阅事件
     *
     * @param ob
     * @param retryTime
     */
    void goSubscribe(Observable ob, int retryTime) {
        if (obsever == null) {
            return;
        }

        ob
                .map(new Function() {
                    @Override
                    public Object apply(@NonNull Object o) throws Exception {
                        L.d("apply:\n");
                        if (o == null) {
                            return new NullPointerException("服务器访问成功，但是居然不给数据。");
                        } else {
                            L.loggerJson(GsonUtil.toJsonString(o));
                            return o;
                        }
                    }
                })
                .debounce(3000, TimeUnit.MILLISECONDS)//过滤3秒的同一请求，避免频繁的网络操作
                .retry(retryTime)//失败重试次数
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(obsever);
    }

    void goSubscribe(Observable ob) {
        goSubscribe(ob, 0);
    }


}
