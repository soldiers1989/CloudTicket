package com.ycgrp.cloudticket.event;


import java.util.List;

/**
 * 我持有云票
 */
public class MyCloudTicketBean {


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
         * id : 2
         * type : bill
         * attributes : {"amount":"10000.0","date_of_issue":"2018-11-04","maturity_date":"2018-12-04","status":"ready_for_sale"}
         * relationships : {"loan":{"data":{"id":"2","type":"loan"}},"release":{"data":{"id":"2","type":"release"}}}
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
             * date_of_issue : 2018-11-04
             * maturity_date : 2018-12-04
             * status : ready_for_sale
             */

            private String amount;
            private String date_of_issue;
            private String maturity_date;
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

            public String getMaturity_date() {
                return maturity_date;
            }

            public void setMaturity_date(String maturity_date) {
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
             * loan : {"data":{"id":"2","type":"loan"}}
             * release : {"data":{"id":"2","type":"release"}}
             */

            private LoanBean loan;
            private ReleaseBean release;

            public LoanBean getLoan() {
                return loan;
            }

            public void setLoan(LoanBean loan) {
                this.loan = loan;
            }

            public ReleaseBean getRelease() {
                return release;
            }

            public void setRelease(ReleaseBean release) {
                this.release = release;
            }

            public static class LoanBean {
                /**
                 * data : {"id":"2","type":"loan"}
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
                     * id : 2
                     * type : loan
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

            public static class ReleaseBean {
                /**
                 * data : {"id":"2","type":"release"}
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
                     * id : 2
                     * type : release
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
        }
    }

    public static class IncludedBean {
        /**
         * id : 2
         * type : loan
         * attributes : {"amount":"10000.0","date_of_issue":"2018-11-02","maturity_date":"2018-12-04","status":"approved"}
         * relationships : {"guarantor":{"data":{"id":"4","type":"user"}}}
         */

        private String id;
        private String type;
        private AttributesBeanX attributes;
        private RelationshipsBeanX relationships;

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

        public RelationshipsBeanX getRelationships() {
            return relationships;
        }

        public void setRelationships(RelationshipsBeanX relationships) {
            this.relationships = relationships;
        }

        public static class AttributesBeanX {
            /**
             * amount : 10000.0
             * date_of_issue : 2018-11-02
             * maturity_date : 2018-12-04
             * status : approved
             */

            private String amount;
            private String date_of_issue;
            private String maturity_date;
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

            public String getMaturity_date() {
                return maturity_date;
            }

            public void setMaturity_date(String maturity_date) {
                this.maturity_date = maturity_date;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }

        public static class RelationshipsBeanX {
            /**
             * guarantor : {"data":{"id":"4","type":"user"}}
             */

            private GuarantorBean guarantor;

            public GuarantorBean getGuarantor() {
                return guarantor;
            }

            public void setGuarantor(GuarantorBean guarantor) {
                this.guarantor = guarantor;
            }

            public static class GuarantorBean {
                /**
                 * data : {"id":"4","type":"user"}
                 */

                private DataBeanXXX data;

                public DataBeanXXX getData() {
                    return data;
                }

                public void setData(DataBeanXXX data) {
                    this.data = data;
                }

                public static class DataBeanXXX {
                    /**
                     * id : 4
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
        }
    }
}
