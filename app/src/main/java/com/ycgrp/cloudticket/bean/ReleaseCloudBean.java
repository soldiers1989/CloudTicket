package com.ycgrp.cloudticket.bean;

/**
 * 发布云票
 */
public class ReleaseCloudBean {

    /**
     * data : {"id":"9","type":"release","attributes":{"interest_rate":"0.01"},"relationships":{"user":{"data":{"id":"4","type":"user"}},"endorsement":{"data":null}}}
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
         * id : 9
         * type : release
         * attributes : {"interest_rate":"0.01"}
         * relationships : {"user":{"data":{"id":"4","type":"user"}},"endorsement":{"data":null}}
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
             * interest_rate : 0.01
             */

            private String interest_rate;

            public String getInterest_rate() {
                return interest_rate;
            }

            public void setInterest_rate(String interest_rate) {
                this.interest_rate = interest_rate;
            }
        }

        public static class RelationshipsBean {
            /**
             * user : {"data":{"id":"4","type":"user"}}
             * endorsement : {"data":null}
             */

            private UserBean user;
            private EndorsementBean endorsement;

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public EndorsementBean getEndorsement() {
                return endorsement;
            }

            public void setEndorsement(EndorsementBean endorsement) {
                this.endorsement = endorsement;
            }

            public static class UserBean {
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

            public static class EndorsementBean {
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
}
