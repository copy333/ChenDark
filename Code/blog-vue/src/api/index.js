import {
    blogGet,
    blogPost,
    blogPut,
    blogDelete
}
from "./http"; 

//================ 登录管理 ==================
//判断管理员是否登录成功
//export const getLoginStatus = (params) => blogPost(`user/login/status`, params);

//判断验证码是否正确
export const getVcodeStatus = (params) => blogPost(`blog/iscode`, params);

//登录
export const getLoginData = (params) => blogPost(`login`, params);

//退出登录
export const logout = () => blogGet(`logout`);

//给路由守卫返回是否登录成功
export const UserStatus = () => blogGet(`root/status`);

//================ 文章管理 ==================
//按照标题获取满足文章列表（但是不会获取文章的内容（blog_content）字段，毕竟内容字段字多
export const getBlogListTitle = (params) => blogPost(`blog/listTitle`, params);

//删除文章
export const delBlogById = (id) => blogGet(`root/delete/${id}`);

//获取文章分类信息
export const getBlogType = () => blogGet(`blog/type/list`);

//获取文章分类id和name键值对
export const getBlogTypeMap = () => blogGet(`blog/type/getTypeMap`);

//获取文章分类name和id键值对
export const getBlogContraryTypeMap = () => blogGet(`blog/type/getContraryTypeMap`);

//添加文章
export const saveBlog = (params) => blogPost(`root/saveBlog`, params);

//根据文章id查询文章并返回文章信息
export const getBlogById = (id) => blogGet(`blog/blogByid/${id}`);
//根据文章id查询文章并返回状态，1代表存在，0代表不存在
export const checkBlogByid = (id) => blogGet(`blog/checkBlogByid/${id}`);

//更新修改的文章内容
export const updateBlog = (params) => blogPost(`root/updateBlog`, params);

//更新修改的分类内容
export const updateBlogType = (params) => blogPost(`root/type/updateBlogType`, params);

//删除文章分类
export const delBlogTypeById = (id) => blogGet(`root/type/delete/${id}`);

//添加分类
export const saveBlogType = (params) => blogPost(`root/type/saveBlogType`, params);

//分类文章数 +1
export const addTypeCount = (id) => blogGet(`root/type/typeCount/${id}`);

//分类文章数 -1
export const subTypeCount = (id) => blogGet(`root/type/subTypeCount/${id}`);

//================ 博客首页展示 ==================
/*
获取所有文章信息（但是不会获取文章的内容（blog_content）字段，只有点击进了某个文章才通过 id 来定向查询 文章内容（blog_content）字段）
"blog_id", "blog_title", "blog_image", "blog_type", "blog_remark", "gmt_create", "gmt_modified" 
这里只查询了博客id， 博客标题， 博客图片， 博客分类， 博客简介， 博客的创建时间和更新时间
*/
export const getBlogListNotCont = () => blogGet(`blog/getBlogListNotCont`);

//根据分类id查询该分类的文章信息
export const getBlogByTypeId = (id) => blogGet(`blog/getBlogByTypeId/${id}`);

//更新文章图片服务器地址，如 ![](www.baidu.com/img/1.png) 变成 ![](www.google.com/img/1.png)
export const updateImgUrl = (params) => blogPost(`root/updateImgUrl`, params);
export const updateImgUrlP = (params) => blogPost(`root/updateImgUrlP`, params);
//更新文章封面图片服务器地址，如 www.baidu.com/PageImg/java.png 变成 www.google.com/PageImg/java.png
export const updatePageImgUrl = (params) => blogPost(`root/updatePageImgUrl`, params);
export const updatePageImgUrlP = (params) => blogPost(`root/updatePageImgUrlP`, params);

//获取所有文章信息,只查询了博客id，博客标题 和 博客的创建时间
export const getAllBlogBaseInfo = () => blogGet(`blog/getAllBlogBaseInfo`);

//======================文件管理============
//PUT添加文档至ES索引
export const putES = () => blogPut(`root/putES`);

//删除ES索引-blog
export const deleteES = () => blogDelete(`root/deleteES`);

//使用ES全站搜索
export const searchES = (content) => blogGet(`blog/searchES/${content}`);

//================ 博客留言相关 ==================
//保存留言
export const saveMessage = (params) => blogPost(`blog/message/save`, params);
//获取留言列表
export const getMessageList = () => blogGet(`blog/message/getMsgList`);
//删除留言
export const deleteMessageById = (id) => blogDelete(`root/message/delete/${id}`);

//================ 友情链接相关 ==================
//保存友链
export const saveLink = (params) => blogPost(`root/link/save`, params);
//获取友链列表
export const getLinkList = () => blogGet(`blog/link/getLinkList`);
//删除友链列表
export const deleteLinkById = (id) => blogDelete(`root/link/delete/${id}`);
//更新友链内容
export const updateLink = (params) => blogPost(`root/link/updateLink`, params);

//================ 外部API相关-即公开的API ==================
export const getSoup = () => blogGet(`openApi/soup`);