package com.ycgrp.cloudticket.bean;

import java.util.List;

public class CloudTticketDetailsBean {


    /**
     * data : {"id":"42","type":"bill","attributes":{"amount":"2.0","date_of_issue":"2018-11-10","maturity_date":"2018-11-10","status":"ready_for_sale"},"relationships":{"loan":{"data":{"id":"112","type":"loan"}},"releases":{"data":[{"id":"61","type":"release"},{"id":"64","type":"release"}]}}}
     * included : [{"id":"112","type":"loan","attributes":{"amount":"2.0","date_of_issue":"2018-11-10","maturity_date":"2018-11-10","status":"approved","name":"王老板","interest_rate":"0.02"},"relationships":{"loanee":{"data":{"id":"3","type":"user"}},"guarantor":{"data":{"id":"4","type":"user"}},"bill":{"data":{"id":"42","type":"bill"}},"endorsement":{"data":{"id":"19","type":"endorsement"}},"endorsee":{"data":{"id":"4","type":"user"}}}},{"id":"61","type":"release","attributes":{"interest_rate":"0.02"},"relationships":{"endorsement":{"data":{"id":"19","type":"endorsement"}}}},{"id":"64","type":"release","attributes":{"interest_rate":"0.6"},"relationships":{"endorsement":{"data":null}}},{"id":"19","type":"endorsement","relationships":{"endorsee":{"data":{"id":"4","type":"user"}}}},{"id":"4","type":"user","attributes":{"name":"王老板","phone_number":"18800000002"},"relationships":{"profiles":{"data":[{"id":"2","type":"user_profile"},{"id":"7","type":"user_profile"},{"id":"8","type":"user_profile"},{"id":"9","type":"user_profile"}]}}}]
     */

    private DataBeanXX data;
    private List<IncludedBean> included;

    public DataBeanXX getData() {
        return data;
    }

    public void setData(DataBeanXX data) {
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
         * id : 42
         * type : bill
         * attributes : {"amount":"2.0","date_of_issue":"2018-11-10","maturity_date":"2018-11-10","status":"ready_for_sale"}
         * relationships : {"loan":{"data":{"id":"112","type":"loan"}},"releases":{"data":[{"id":"61","type":"release"},{"id":"64","type":"release"}]}}
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
             * amount : 2.0
             * date_of_issue : 2018-11-10
             * maturity_date : 2018-11-10
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
             * loan : {"data":{"id":"112","type":"loan"}}
             * releases : {"data":[{"id":"61","type":"release"},{"id":"64","type":"release"}]}
             */

            private LoanBean loan;
            private ReleasesBean releases;

            public LoanBean getLoan() {
                return loan;
            }

            public void setLoan(LoanBean loan) {
                this.loan = loan;
            }

            public ReleasesBean getReleases() {
                return releases;
            }

            public void setReleases(ReleasesBean releases) {
                this.releases = releases;
            }

            public static class LoanBean {
                /**
                 * data : {"id":"112","type":"loan"}
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
                     * id : 112
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

            public static class ReleasesBean {
                private List<DataBeanX> data;

                public List<DataBeanX> getData() {
                    return data;
                }

                public void setData(List<DataBeanX> data) {
                    this.data = data;
                }

                public static class DataBeanX {
                    /**
                     * id : 61
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
         * id : 112
         * type : loan
         * attributes : {"amount":"2.0","date_of_issue":"2018-11-10","maturity_date":"2018-11-10","status":"approved","name":"王老板","interest_rate":"0.02"}
         * relationships : {"loanee":{"data":{"id":"3","type":"user"}},"guarantor":{"data":{"id":"4","type":"user"}},"bill":{"data":{"id":"42","type":"bill"}},"endorsement":{"data":{"id":"19","type":"endorsement"}},"endorsee":{"data":{"id":"4","type":"user"}}}
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
             * amount : 2.0
             * date_of_issue : 2018-11-10
             * maturity_date : 2018-11-10
             * status : approved
             * name : 王老板
             * interest_rate : 0.02
             */

            private String amount;
            private String date_of_issue;
            private String maturity_date;
            private String status;
            private String name;
            private String interest_rate;

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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getInterest_rate() {
                return interest_rate;
            }

            public void setInterest_rate(String interest_rate) {
                this.interest_rate = interest_rate;
            }
        }

        public static class RelationshipsBeanX {
            /**
             * loanee : {"data":{"id":"3","type":"user"}}
             * guarantor : {"data":{"id":"4","type":"user"}}
             * bill : {"data":{"id":"42","type":"bill"}}
             * endorsement : {"data":{"id":"19","type":"endorsement"}}
             * endorsee : {"data":{"id":"4","type":"user"}}
             */

            private LoaneeBean loanee;
            private GuarantorBean guarantor;
            private BillBean bill;
            private EndorsementBean endorsement;
            private EndorseeBean endorsee;

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

            public EndorsementBean getEndorsement() {
                return endorsement;
            }

            public void setEndorsement(EndorsementBean endorsement) {
                this.endorsement = endorsement;
            }

            public EndorseeBean getEndorsee() {
                return endorsee;
            }

            public void setEndorsee(EndorseeBean endorsee) {
                this.endorsee = endorsee;
            }

            public static class LoaneeBean {
                /**
                 * data : {"id":"3","type":"user"}
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
                     * id : 3
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
                 * data : {"id":"4","type":"user"}
                 */

                private DataBeanXXXX data;

                public DataBeanXXXX getData() {
                    return data;
                }

                public void setData(DataBeanXXXX data) {
                    this.data = data;
                }

                public static class DataBeanXXXX {
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

            public static class BillBean {
                /**
                 * data : {"id":"42","type":"bill"}
                 */

                private DataBeanXXXXX data;

                public DataBeanXXXXX getData() {
                    return data;
                }

                public void setData(DataBeanXXXXX data) {
                    this.data = data;
                }

                public static class DataBeanXXXXX {
                    /**
                     * id : 42
                     * type : bill
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

            public static class EndorsementBean {
                /**
                 * data : {"id":"19","type":"endorsement"}
                 */

                private DataBeanXXXXXX data;

                public DataBeanXXXXXX getData() {
                    return data;
                }

                public void setData(DataBeanXXXXXX data) {
                    this.data = data;
                }

                public static class DataBeanXXXXXX {
                    /**
                     * id : 19
                     * type : endorsement
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

            public static class EndorseeBean {
                /**
                 * data : {"id":"4","type":"user"}
                 */

                private DataBeanXXXXXXX data;

                public DataBeanXXXXXXX getData() {
                    return data;
                }

                public void setData(DataBeanXXXXXXX data) {
                    this.data = data;
                }

                public static class DataBeanXXXXXXX {
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
