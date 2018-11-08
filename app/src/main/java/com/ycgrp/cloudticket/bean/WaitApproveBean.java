package com.ycgrp.cloudticket.bean;

import java.util.List;

/**
 * 待审批的贷款列表
 */
public class WaitApproveBean {


    private List<DataBeanXX> data;
    private List<IncludedBean> included;

    public List<DataBeanXX> getData() {
        return data;
    }

    public void setData(List<DataBeanXX> data) {
        this.data = data;
    }

    public List<IncludedBean> getIncluded() {
        return included;
    }

    public void setIncluded(List<IncludedBean> included) {
        this.included = included;
    }

    public static class DataBeanXX {
        /**
         * id : 421
         * type : loan
         * attributes : {"amount":"10000.0","date_of_issue":"2018-11-05","maturity_date":null,"status":"waiting_for_review"}
         * relationships : {"loanee":{"data":{"id":"1047","type":"user"}},"guarantor":{"data":{"id":"1046","type":"user"}},"bill":{"data":null}}
         */

        private String id;
        private String type;
        private AttributesBean attributes;
        private RelationshipsBean relationships;

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

        public RelationshipsBean getRelationships() {
            return relationships;
        }

        public void setRelationships(RelationshipsBean relationships) {
            this.relationships = relationships;
        }

        public static class AttributesBean {
            /**
             * amount : 10000.0
             * date_of_issue : 2018-11-05
             * maturity_date : null
             * status : waiting_for_review
             */

            private String amount;
            private String date_of_issue;
            private Object maturity_date;
            private String status;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getDate_of_issue() {
                return date_of_issue;
            }

            public void setDate_of_issue(String date_of_issue) {
                this.date_of_issue = date_of_issue;
            }

            public Object getMaturity_date() {
                return maturity_date;
            }

            public void setMaturity_date(Object maturity_date) {
                this.maturity_date = maturity_date;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }

        public static class RelationshipsBean {
            /**
             * loanee : {"data":{"id":"1047","type":"user"}}
             * guarantor : {"data":{"id":"1046","type":"user"}}
             * bill : {"data":null}
             */

            private LoaneeBean loanee;
            private GuarantorBean guarantor;
            private BillBean bill;

            public LoaneeBean getLoanee() {
                return loanee;
            }

            public void setLoanee(LoaneeBean loanee) {
                this.loanee = loanee;
            }

            public GuarantorBean getGuarantor() {
                return guarantor;
            }

            public void setGuarantor(GuarantorBean guarantor) {
                this.guarantor = guarantor;
            }

            public BillBean getBill() {
                return bill;
            }

            public void setBill(BillBean bill) {
                this.bill = bill;
            }

            public static class LoaneeBean {
                /**
                 * data : {"id":"1047","type":"user"}
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
                     * id : 1047
                     * type : user
                     */

                    private String id;
                    private String type;

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
                }
            }

            public static class GuarantorBean {
                /**
                 * data : {"id":"1046","type":"user"}
                 */

                private DataBeanX data;

                public DataBeanX getData() {
                    return data;
                }

                public void setData(DataBeanX data) {
                    this.data = data;
                }

                public static class DataBeanX {
                    /**
                     * id : 1046
                     * type : user
                     */

                    private String id;
                    private String type;

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
                }
            }

            public static class BillBean {
                /**
                 * data : null
                 */

                private Object data;

                public Object getData() {
                    return data;
                }

                public void setData(Object data) {
                    this.data = data;
                }
            }
        }
    }

    public static class IncludedBean {
        /**
         * id : 1046
         * type : user
         * attributes : {"name":"张建国"}
         */

        private String id;
        private String type;
        private AttributesBeanX attributes;

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

        public AttributesBeanX getAttributes() {
            return attributes;
        }

        public void setAttributes(AttributesBeanX attributes) {
            this.attributes = attributes;
        }

        public static class AttributesBeanX {
            /**
             * name : 张建国
             */

            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
