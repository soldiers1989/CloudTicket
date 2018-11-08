package com.ycgrp.cloudticket.api;


import com.ycgrp.cloudticket.bean.ApproveordrejetBean;
import com.ycgrp.cloudticket.bean.GetRegisterInfoBean;
import com.ycgrp.cloudticket.bean.LoginResponseBean;
import com.ycgrp.cloudticket.bean.MyInfoBean;
import com.ycgrp.cloudticket.bean.MyLoanRecordBean;
import com.ycgrp.cloudticket.bean.ReleaseCloudBean;
import com.ycgrp.cloudticket.bean.StartCloudTicketBean;
import com.ycgrp.cloudticket.bean.TradeBean;
import com.ycgrp.cloudticket.bean.WaitApproveBean;
import com.ycgrp.cloudticket.event.MyCloudTicketBean;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import okhttp3.ResponseBody;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * retrofit 文档 http://square.github.io/retrofit/
 * 动态参数直接用@query
 */

public interface NetAPI {

    /**
     * 注册
     *
     * @param phonenumber 手机号
     * @param name        用户名
     * @param password    密码
     * @return
     */
    @FormUrlEncoded
    @POST("users")
    Observable<GetRegisterInfoBean> getRegisterInfo(@Field("user[phone_number]") String phonenumber, @Field("user[name]") String name, @Field("user[password]") String password);

    /**
     * @param phonenumber 手机号
     * @param password    密码
     * @return
     */
    @FormUrlEncoded
    @POST("access_tokens")
    Observable<LoginResponseBean> login(@Field("phone_number") String phonenumber, @Field("password") String password);

    /**
     * @param amount       金额
     * @param guarantor_id 接受云票人的id
     * @return
     */
    @FormUrlEncoded
    @POST("loans")
    Observable<StartCloudTicketBean> startCloudTicket(@Field("loan[amount]") String amount, @Field("loan[guarantor_id]") String guarantor_id);


    /**
     * 获取待审批列表
     *
     * @return
     */

    @GET("/loans/guaranteed")
    Observable<WaitApproveBean> getWaitApproveList();

    /**
     * 审批云票
     *
     * @param maturity_date 还款日期
     * @param status        approved (通过) 或 rejected (拒绝)
     * @return
     */
    @FormUrlEncoded
    @PATCH("loans/{id}")
    Observable<ApproveordrejetBean> approveOrRejet(@Path("id") String id, @Field("loan[maturity_date]") String maturity_date, @Field("loan[status]") String status);

    /**
     * 我持有的云票
     *
     * @return
     */
    @GET("/bills/own")
    Observable<MyCloudTicketBean> getMyCloudTicket();

    /**
     * 我的借款
     * @return
     */
    @GET("/loans/outstanding")
    Observable<MyLoanRecordBean>getMyLoanRecord();

    /**
     * 获取自己的信息
     *
     * @return
     */
    @GET("/users/me")
    Observable<MyInfoBean> getMyInfo();

    /**
     * 发布云票
     * @param bill_id 云票id
     * @param interest_rate 利率
     * @return
     */
    @FormUrlEncoded
    @POST("/bills/{bill_id}/releases")
    Observable <ReleaseCloudBean> release(@Path("bill_id") String bill_id, @Field("release[interest_rate]") String interest_rate);

    /**
     * 撤回云票
     * @param bill_id 拥有的云票id
     * @param id 已发布云票id
     * @return
     */
    @DELETE("/bills/{bill_id}/releases/{id}")
    Observable<ResponseBody> recall(@Path("bill_id") String bill_id, @Path("id")String id);

    /**
     * 获取市场上正在出售的云票
     * @return
     */
    @GET("bills/for_sale")
    Observable<TradeBean>getTrade();

    /**
     * 买入云票
     * @param release_id 云票最后一次发布的id
     * @return
     */
    @FormUrlEncoded
    @POST("/endorsements")
    Observable<ResponseBody>buyCloudTicket(@Field("endorsement[release_id]")String release_id );
}
