package com.ycgrp.cloudticket.bean;

/**
 * 账户余额
 */
public class AccountBlanceBean {

    /**
     * data : {"id":"1017","type":"account","attributes":{"balance":"0.0","accumulated_earnings":"0.0"}}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1017
         * type : account
         * attributes : {"balance":"0.0","accumulated_earnings":"0.0"}
         */

        private String id;
        private String type;
        private AttributesBean attributes;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public AttributesBean getAttributes() {
            return attributes;
        }

        public void setAttributes(AttributesBean attributes) {
            this.attributes = attributes;
        }

        public static class AttributesBean {
            /**
             * balance : 0.0
             * accumulated_earnings : 0.0
             */

            private String balance;
            private String accumulated_earnings;

            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }

            public String getAccumulated_earnings() {
                return accumulated_earnings;
            }

            public void setAccumulated_earnings(String accumulated_earnings) {
                this.accumulated_earnings = accumulated_earnings;
            }
        }
    }
}
