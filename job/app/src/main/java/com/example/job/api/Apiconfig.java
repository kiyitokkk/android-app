package com.example.job.api;

public class Apiconfig {
    public static final String BASE_URL = "http://wyyspider.natapp1.cc";
    public static final String LOGIN_URL = "/app/login";
    public static final String REGISTER_URL = "/app/register";
    public static final String SEND_EMAIL = "/send_verification_code";
    public static final String EDIT_SEND_EMAIL = "/send_verification_code/edit";
    public static final String VERITY_EMAIL = "/verify";
    public static final String PWD_VERITY_EMAIL = "/verify/edit";
    public static final String PWD_EDIT = "/pwd/edit";
    public static final String GET_INFORMATION_LIST = "/app/getinformation/list/all";
    public static final  String GET_TYPE_LIST = "/app/type/list";
    public static final  String ISCOLLECT = "/app/iscollect";
//     fenleijieko
    public static final  String VIDEO_LIST_BYCATEGORY = "/app/videolist/getListByCategoryId";
    public static  final int PAGE_SIZE = 5;
    public static final String VIDEO_CATEGORY_LIST = "/app/videocatgory/list";
    public static final String CHANGE_THE_NAME = "/app/changename";
    public static final String UNPDTE_COUNT = "/app/videolist/updateCount";
    public static final String VIDEO_MYCOLLECT = "/app/videolist/mycollect";//我的收藏
    public static  final  String MY_HOME = "/app/video/myhome";
    public static  final  String CHANGE_HEADER = "/app/myhome/headchange";
    public static final  String GET_MESSAGE = "/app/myhome/message";
    public static final String RELEASE_VIDEO = "/app/release/video";
//    delete
    public static final  String DELETE_RELEASE = "/app/delete/video";
//    edit
    public static final String EDIT_RELEASE = "/app/edit/video";
    public static final String GETCOMMENT_LIST = "/app/comment/getCommentList";
    public static final String SEND_COMMENT = "/SendComment/";
    public static final String SEARCH_VIDEO_BYKEYWORD = "/app/videolist/getListBykeywords";
}
