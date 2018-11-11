package com.ycgrp.cloudticket.bean;

import java.util.List;

/**
 自己的信息
 */
public class MyInfoBean {


    /**
     * data : {"id":"4","type":"user","attributes":{"name":"王老板","phone_number":"18800000002"},"relationships":{"profiles":{"data":[{"id":"2","type":"user_profile"}]}}}
     * included : [{"id":"2","type":"user_profile","attributes":{"kind":"retailer","name":"王建国","id_number":"3102309394234234","address":""}}]
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
         * type : user
         * attributes : {"name":"王老板","phone_number":"18800000002"}
         * relationships : {"profiles":{"data":[{"id":"2","type":"user_profile"}]}}
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
             * name : 王老板
             * phone_number : 18800000002
             */

            private String name;
            private String phone_number;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhone_number() {
                return phone_number;
            }

            public void setPhone_number(String phone_number) {
                this.phone_number = phone_number;
            }
        }

        public static class RelationshipsBean {
            /**
             * profiles : {"data":[{"id":"2","type":"user_profile"}]}
             */

            private ProfilesBean profiles;

            public ProfilesBean getProfiles() {
                return profiles;
            }

            public void setProfiles(ProfilesBean profiles) {
                this.profiles = profiles;
            }

            public static class ProfilesBean {
                private List<DataBean> data;

                public List<DataBean> getData() {
                    return data;
                }

                public void setData(List<DataBean> data) {
                    this.data = data;
                }

                public static class DataBean {
                    /**
                     * id : 2
                     * type : user_profile
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
         * type : user_profile
         * attributes : {"kind":"retailer","name":"王建国","id_number":"3102309394234234","address":""}
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
             * kind : retailer
             * name : 王建国
             * id_number : 3102309394234234
             * address :
             */

            private String kind;
            private String name;
            private String id_number;
            private String address;

            public String getKind() {
                return kind;
            }

            public void setKind(String kind) {
                this.kind = kind;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId_number() {
                return id_number;
            }

            public void setId_number(String id_number) {
                this.id_number = id_number;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }
        }
    }
}
