package com.ycgrp.cloudticket.bean;


import java.util.List;

/**
 * 审批云票后的返回结果
 */
public class ApproveordrejetBean {


    /**
     * data : {"id":"4","type":"loan","attributes":{"amount":"30000.0","date_of_issue":"2018-11-02","maturity_date":"2018-11-30","status":"approved"},"relationships":{"guarantor":{"data":{"id":"4","type":"user"}}}}
     * included : [{"id":"4","type":"user","attributes":{"name":"王老板"}}]
     */

    private DataBeanX data;
    private List<IncludedBean> included;

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public List<IncludedBean> getIncluded() {
        return included;
    }

    public void setIncluded(List<IncludedBean> included) {
        this.included = included;
    }

    public static class DataBeanX {
        /**
         * id : 4
         * type : loan
         * attributes : {"amount":"30000.0","date_of_issue":"2018-11-02","maturity_date":"2018-11-30","status":"approved"}
         * relationships : {"guarantor":{"data":{"id":"4","type":"user"}}}
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
             * amount : 30000.0
             * date_of_issue : 2018-11-02
             * maturity_date : 2018-11-30
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

        public static class RelationshipsBean {
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

                private DataBean data;

                public DataBean getData() {
                    return data;
                }

                public void setData(DataBean data) {
                    this.data = data;
                }

                public static class DataBean {
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

    public static class IncludedBean {
        /**
         * id : 4
         * type : user
         * attributes : {"name":"王老板"}
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
             * name : 王老板
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
