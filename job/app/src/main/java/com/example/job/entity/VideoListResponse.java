package com.example.job.entity;

import java.io.Serializable;
import java.util.List;

public class VideoListResponse implements Serializable {
    private String msg;
    private int code;
    private PageBean page;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public static class PageBean {
        /**
         * totalCount : 4
         * pageSize : 10
         * totalPage : 1
         * currPage : 1
         * list : [{"vid":1,"vtitle":"ollvm","author":"chen","coverurl":"https://p3-pc.douyinpic.com/img/aweme-avatar/tos-cn-i-0813c001_6d426cc225094ee5bd2454d2beae925e~c5_168x168.jpeg?from=2956013662","headurl":"https://p3-pc.douyinpic.com/img/aweme-avatar/tos-cn-i-0813c001_6d426cc225094ee5bd2454d2beae925e~c5_168x168.jpeg?from=2956013662","commentNum":210,"likeNum":23,"collectNum":100},{"vid":2,"vtitle":"unidbg","author":"lib","coverurl":"https://p3-pc.douyinpic.com/img/aweme-avatar/tos-cn-i-0813c001_7cbd416954c0473db8c2f02401d6c78a~c5_168x168.jpeg?from=2956013662","headurl":"https://p3-pc.douyinpic.com/img/aweme-avatar/tos-cn-i-0813c001_7cbd416954c0473db8c2f02401d6c78a~c5_168x168.jpeg?from=2956013662","commentNum":210,"likeNum":23,"collectNum":100},{"vid":3,"vtitle":"frida","author":"cccc","coverurl":"https://p3-pc.douyinpic.com/img/aweme-avatar/tos-cn-i-0813_9d0c5b8dd8d44abea0b51d94d634690f~c5_168x168.jpeg?from=2956013662","headurl":"https://p3-pc.douyinpic.com/img/aweme-avatar/tos-cn-i-0813_9d0c5b8dd8d44abea0b51d94d634690f~c5_168x168.jpeg?from=2956013662","commentNum":210,"likeNum":23,"collectNum":100}]
         */

        private int totalCount;
        private int pageSize;
        private int totalPage;
        private int currPage;
        private List<VideoEntity> list;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getCurrPage() {
            return currPage;
        }

        public void setCurrPage(int currPage) {
            this.currPage = currPage;
        }

        public List<VideoEntity> getList() {
            return list;
        }

        public void setList(List<VideoEntity> list) {
            this.list = list;
        }

//        public static class VideoEntity {
//            /**
//             * vid : 1
//             * vtitle : ollvm
//             * author : chen
//             * coverurl : https://p3-pc.douyinpic.com/img/aweme-avatar/tos-cn-i-0813c001_6d426cc225094ee5bd2454d2beae925e~c5_168x168.jpeg?from=2956013662
//             * headurl : https://p3-pc.douyinpic.com/img/aweme-avatar/tos-cn-i-0813c001_6d426cc225094ee5bd2454d2beae925e~c5_168x168.jpeg?from=2956013662
//             * commentNum : 210
//             * likeNum : 23
//             * collectNum : 100
//             */
//
//            private int vid;
//            private String vtitle;
//            private String author;
//            private String coverurl;
//            private String headurl;
//            private int commentNum;
//            private int likeNum;
//            private int collectNum;
//
//            public int getVid() {
//                return vid;
//            }
//
//            public void setVid(int vid) {
//                this.vid = vid;
//            }
//
//            public String getVtitle() {
//                return vtitle;
//            }
//
//            public void setVtitle(String vtitle) {
//                this.vtitle = vtitle;
//            }
//
//            public String getAuthor() {
//                return author;
//            }
//
//            public void setAuthor(String author) {
//                this.author = author;
//            }
//
//            public String getCoverurl() {
//                return coverurl;
//            }
//
//            public void setCoverurl(String coverurl) {
//                this.coverurl = coverurl;
//            }
//
//            public String getHeadurl() {
//                return headurl;
//            }
//
//            public void setHeadurl(String headurl) {
//                this.headurl = headurl;
//            }
//
//            public int getCommentNum() {
//                return commentNum;
//            }
//
//            public void setCommentNum(int commentNum) {
//                this.commentNum = commentNum;
//            }
//
//            public int getLikeNum() {
//                return likeNum;
//            }
//
//            public void setLikeNum(int likeNum) {
//                this.likeNum = likeNum;
//            }
//
//            public int getCollectNum() {
//                return collectNum;
//            }
//
//            public void setCollectNum(int collectNum) {
//                this.collectNum = collectNum;
//            }
//        }
    }
}
