package com.ycgrp.cloudticket.bean;


import java.io.Serializable;
import java.util.List;

public class LoginResponseBean {


    /**
     * token : rJ8HC9bShWhttQY9k4v7XZoj
     * refresh_token : k6dofw7RTESVF7g55ukqbfaT
     * expires_in : 2592000
     */


    private String token;
    private String refresh_token;
    private int expires_in;
    private List<ErrorsBean> errors;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public List<ErrorsBean> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorsBean> errors) {
        this.errors = errors;
    }

    public static class ErrorsBean {
        /**
         * status : 401
         * title : not_authenticated
         * detail : Not authenticated
         * source : {"pointer":"/request/headers/authorization"}
         */

        private int status;
        private String title;
        private String detail;
        private SourceBean source;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public SourceBean getSource() {
            return source;
        }

        public void setSource(SourceBean source) {
            this.source = source;
        }

        public static class SourceBean {
            /**
             * pointer : /request/headers/authorization
             */

            private String pointer;

            public String getPointer() {
                return pointer;
            }

            public void setPointer(String pointer) {
                this.pointer = pointer;
            }
        }
    }
}
