package com.ycgrp.cloudticket.mvp.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.ycgrp.cloudticket.R;
import com.ycgrp.cloudticket.api.BaseCallBackListener;
import com.ycgrp.cloudticket.api.NetServer;
import com.ycgrp.cloudticket.bean.CloudTticketDetailsBean;
import com.ycgrp.cloudticket.bean.MyInfoBean;
import com.ycgrp.cloudticket.bean.ReleaseCloudBean;
import com.ycgrp.cloudticket.bean.UserInfoBean;
import com.ycgrp.cloudticket.utils.GsonUtil;
import com.ycgrp.cloudticket.utils.L;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class CloudTicketDetailsActivity extends BaseActivity {

    /**
     * 买入价
     */
    @BindView(R.id.buy_price)
    TextView buy_price;
    /**
     * 利率
     */
    @BindView(R.id.buy_rate)
    TextView buy_rate;
    /**
     * 农户
     */
    @BindView(R.id.farmer)
    TextView farmer;
    /**
     * 零售商
     */
    @BindView(R.id.retailer)
    TextView retailer;
    /**
     * 持有人
     */
    @BindView(R.id.holder)
    TextView holder;
    /**
     * 签发日期
     */
    @BindView(R.id.date_of_issue)
    TextView date_of_issue;
    /**
     * 到期日期
     */
    @BindView(R.id.date_of_maturity)
    TextView date_of_maturity;

    /**
     * 持有人ID
     */
    String holderID = "";
    /**
     * 背书ID
     */
    String endorsementID = "";
    /**
     * 第一个按钮
     */
    @BindView(R.id.btn_one)
    Button btn_one;
    /**
     * 第一个按钮
     */
    @BindView(R.id.btn_two)
    Button btn_two;
    /**
     * 云票状态
     */
    private String CloudTicketStatus = "";

    /**
     * 云票ID
     */
    String id = "";

    String releaseID="";
    /**
     *
     */
    Boolean isExitOtherHolderFlag=true;
    /**
     * AlertDialog对话框
     */
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cloud_ticket_details);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        releaseID=intent.getStringExtra("releaseID");
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        if (id != null) {
            getCloudTicketDetail(id);

        } else {
            Toast.makeText(this, "获取失败", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 点击第一个按钮
     */
    @OnClick(R.id.btn_one)
    public void click_btn_one() {
        if (btn_one.getText().equals("买入")) {
            buy(releaseID,id);
        } else if (btn_one.getText().toString().equals("卖出")) {
            setAlertDialog();
        } else if (btn_one.getText().toString().equals("撤回")) {
            NetServer.getInstance().getCloudTicketDetial(id, new BaseCallBackListener<CloudTticketDetailsBean>() {
                @Override
                public void onSuccess(CloudTticketDetailsBean result) {
                    super.onSuccess(result);
                    if (result!=null){
                     releaseID=result.getData().getRelationships().getReleases().getData().get(result.getData().getRelationships().getReleases().getData().size()-1).getId();
                        sendRecall(id,releaseID);
                    }
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    showTastTips("卖出失败"+e.getMessage());
                }
            });


        }
    }
    /**
     *
     * @param bill_id 云票id
     * @param id 发布id
     */
    public void sendRecall(String bill_id, final String id){
        NetServer.getInstance().recall(bill_id, id, new BaseCallBackListener<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody result) {
                super.onSuccess(result);
                L.e("撤回状态---"+result.toString());
                btn_one.setText("卖出");
                showTastTips("撤回成功");
            }

            @Override
            public void onError(Throwable e) {
                showTastTips("撤回失败"+e.getMessage());
                super.onError(e);
            }

            @Override
            public void onComplete() {
                super.onComplete();
            }
        });
    }
    /**
     * 点击第二个按钮
     */
    @OnClick(R.id.btn_two)
    public void click_btn_two() {
        if (btn_two.getText().toString().equals("贴现")) {
            DiscountAlertDialog();
        }
    }

    /**
     * 弹出确定利率
     */
    public void DiscountAlertDialog(){
        builder = new AlertDialog.Builder(this);
        TextView title = new TextView(this);
        title.setText("请确认贴现利率");
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        builder.setCustomTitle(title);
        TextView rate=new TextView(this);
        rate.setText("贴现利率为:0.01%");
        rate.setTextSize(25);
        rate.setTextColor(Color.RED);
        rate.setGravity(Gravity.CENTER);


        builder.setView(rate);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("贴现", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
    /**
     * 弹出对话框输入利率
     */
    public void setAlertDialog() {
        builder = new AlertDialog.Builder(this);
        TextView title = new TextView(this);
        title.setText(R.string.please_input_rate);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        builder.setCustomTitle(title);
        final EditText et_input_intersrest_rate = new EditText(this);
        et_input_intersrest_rate.setId(R.id.input_intersrest_rate);
        builder.setView(et_input_intersrest_rate);
        builder.setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String txt_interest_rate = et_input_intersrest_rate.getText().toString();
                if (txt_interest_rate.isEmpty()) {
                    showTastTips("利率不能为空");
                } else {
                    if (Float.parseFloat(txt_interest_rate) < 0) {
                        showTastTips("利率不能小于0");
                    } else {
                        sendRelease(txt_interest_rate);
                    }
                }
            }
        });
        builder.show();
    }

    /**
     * 通过接口发布到市场
     *
     * @param interest_rate 利率
     */
    public void sendRelease(String interest_rate) {
        NetServer.getInstance().release(id, interest_rate, new BaseCallBackListener<ReleaseCloudBean>() {
            @Override
            public void onSuccess(ReleaseCloudBean result) {
                super.onSuccess(result);
                Logger.addLogAdapter(new AndroidLogAdapter());
                if (result != null) {
                    Logger.json(GsonUtil.toJson(result));
                }
                showTastTips("卖出成功");
                //刷新UI
                btn_one.setText("撤回");

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                showTastTips("卖出失败" + e.getMessage());

            }

            @Override
            public void onComplete() {
                super.onComplete();
            }
        });
    }

    /**
     *
     * @param releaseID 云票最后一次发布的id
     * @param id  云票id
     */
    public void buy(final String releaseID, final String id) {

        NetServer.getInstance().buyCloudTicket(releaseID, new BaseCallBackListener<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody result) {
                showTastTips("购买成功");
                //刷新UI
              btn_one.setText("卖出");
                super.onSuccess(result);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                showTastTips("购买失败"+e.getMessage());
            }

            @Override
            public void onComplete() {
                super.onComplete();
            }
        });

    }


    public void getCloudTicketDetail(final String id) {
        NetServer.getInstance().getCloudTicketDetial(id, new BaseCallBackListener<CloudTticketDetailsBean>() {
            @Override
            public void onSuccess(CloudTticketDetailsBean result) {
                super.onSuccess(result);
                //云票状态
                CloudTicketStatus = result.getData().getAttributes().getStatus();
                //买入价
                buy_price.setText(result.getData().getAttributes().getAmount());
                //签发日期 到期日期
                date_of_issue.setText(result.getData().getAttributes().getDate_of_issue());
                date_of_maturity.setText(result.getData().getAttributes().getMaturity_date());
                //利率
                for (CloudTticketDetailsBean.IncludedBean ic : result.getIncluded()) {

                    if (result.getData().getRelationships().getReleases().getData().isEmpty() || result.getData().getRelationships().getReleases().getData() == null) {
                        //还没有发布，利率为空
                        buy_rate.setText("");
                    } else if (result.getData().getRelationships().getReleases().getData().get(result.getData().getRelationships().getReleases().getData().size() - 1).getId().equals(ic.getId())) {
                        buy_rate.setText(ic.getAttributes().getInterest_rate());
                    }
                }
                //农户，零售商，持有人名字
                setName(result.getIncluded().get(0).getRelationships().getLoanee().getData().getId(), farmer);
                setName(result.getIncluded().get(0).getRelationships().getGuarantor().getData().getId(), retailer);

                /**
                 * 每个云票都有一个当前持有人: holder ; 通过以下步骤获得：
                 云票最后一次被人买走的发布: release
                 如果该 release 不存在，holder 为 loan.payee
                 如果该 release 存在，holder 为 release.endorsement.endorsee
                 云票最后一次被人买走的 release" 指的就是最后一个 endorsement 不为 null 的 release
                 云票可用动作（按钮）判断：

                 if holder 不是当前登陆的用户
                 买入
                 else
                 贴现 +

                 if status == held
                 卖出
                 else if status == ready_for_sale
                 撤回

                 */
                //release数据为为空或者只有一组数据且对应的endorsement 为null holder 为 loan.guarantor，
                // 其他情况就是release.endorsement.edorsee
                //release为空的情况

               NetServer.getInstance().getCloudTicketDetial(id, new BaseCallBackListener<CloudTticketDetailsBean>() {
                   @Override
                   public void onSuccess(CloudTticketDetailsBean result) {
                       super.onSuccess(result);
                       if (result!=null){
                           CloudTticketDetailsBean.DataBeanXX.RelationshipsBean.ReleasesBean Release=result.getData().getRelationships().getReleases();
                           //不是担保人是云票持有者才去寻找
                               for (int i=Release.getData().size()-1;i>=0;i--){
                                   if (isExitOtherHolderFlag){

                                       for (CloudTticketDetailsBean.IncludedBean ic:result.getIncluded()){
                                           if (Release.getData().get(i).getId().equals(ic.getId())){
                                               if (ic.getRelationships().getEndorsement().getData()!=null){
                                                   endorsementID=ic.getRelationships().getEndorsement().getData().getId();
                                                   isExitOtherHolderFlag=false;//找到第一个不位空的endorsementID，标志一下不再循环
                                               }
                                           }
                                       }
                                   }

                               }//不是担保人为持有者
                               if (!isExitOtherHolderFlag){
                                   for (CloudTticketDetailsBean.IncludedBean ic:result.getIncluded()){
                                       if (ic.getId().equals(endorsementID)&&ic.getType().equals("endorsement")){
                                           holderID=ic.getRelationships().getEndorsee().getData().getId();
                                       }
                                   }
                               }else {
                                   for (CloudTticketDetailsBean.IncludedBean ic:result.getIncluded()){
                                       if (ic.getType().equals("loan")){
                                           holderID=ic.getRelationships().getGuarantor().getData().getId();
                                       }
                                   }
                               }


                           //设置持有人名字
                           setName(holderID, holder);
                           //设置按钮状态
                           NetServer.getInstance().getMyInfo(new BaseCallBackListener<MyInfoBean>() {
                               @Override
                               public void onSuccess(MyInfoBean result) {
                                   super.onSuccess(result);
                                   if (result != null) {
                                       //  holder为当前登录人
                                       if (holderID.equals(result.getData().getId())) {
                                           if (CloudTicketStatus.equals("held")) {
                                               btn_one.setText("卖出");
                                               btn_two.setText("贴现");
                                           } else if (CloudTicketStatus.equals("ready_for_sale")) {
                                               btn_one.setText("撤回");
                                               btn_two.setText("贴现");
                                           }
                                       } else {
                                           //holder不是当前登录人
                                           btn_one.setText("买入");
                                           btn_two.setVisibility(View.GONE);
                                       }
                                   }
                               }

                               @Override
                               public void onError(Throwable e) {
                                   super.onError(e);
                               }
                           });

                       }
                   }

                   @Override
                   public void onError(Throwable e) {
                       super.onError(e);
                   }
               });

            }

            public void setName(String id, final TextView tv) {
                NetServer.getInstance().getUserInfo(id, new BaseCallBackListener<UserInfoBean>() {
                    @Override
                    public void onSuccess(UserInfoBean result) {
                        super.onSuccess(result);
                        tv.setText(result.getData().getAttributes().getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                showTastTips("获取云票详情失败" + e.getMessage());
            }

            @Override
            public void onComplete() {
                super.onComplete();
            }
        });
    }
}
