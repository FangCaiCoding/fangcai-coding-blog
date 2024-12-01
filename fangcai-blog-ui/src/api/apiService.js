import axiosInst from "./axiosInst.js";


const pagePublicArticle = (pageReq = {}) => {
    return axiosInst.post("/article/public/page", pageReq);
}

const getPublicArticle = (id) => {
    return axiosInst.get("/article/public/" + id);
}


// 教程相关api
const pagePublicCourse = (pageReq = {}) => {
    return axiosInst.post("/article/public/course/page", pageReq);
}

const getPublicCourse = (id) => {
    return axiosInst.get("/article/public/course/" + id);
}


/**
 * 后端管理相关
 */

const pageArticle = (pageReq = {}) => {
    return axiosInst.post("/article/page", pageReq);
}

const getArticle = (id) => {
    return axiosInst.get("/article/" + id);
}

const addArticle = (articleSaveReq = {}) => {
    return axiosInst.post("/article", articleSaveReq);
}
const editArticle = (articleSaveReq = {}) => {
    return axiosInst.put("/article", articleSaveReq);
}

const deleteArticle = (id) => {
    return axiosInst.del("/article/" + id);
}

const uptArticleStatus = (id, status) => {
    return axiosInst.put("/article/status/" + id + "/" + status);
}

const initArticleOrderNum = () => {
    return axiosInst.put("/article/initOrderNum");
}

const pageCourse = (pageReq = {}) => {
    return axiosInst.post("/course/page", pageReq);
}

const addCourse = (courseSaveReq = {}) => {
    return axiosInst.post("/course", courseSaveReq);
}
const editCourse = (courseSaveReq = {}) => {
    return axiosInst.put("/course", courseSaveReq);
}
const deleteCourse = (id) => {
    return axiosInst.del("/course/" + id);
}

const getCourse = (id) => {
    return axiosInst.get("/course/" + id);
}

const delCourseDetail = (ids = []) => {
    return axiosInst.delByData("/course/detail", ids);
}

const addCourseDetail = (saveReqList = []) => {
    return axiosInst.post("/course/detail", saveReqList);
}
const editCourseDetail = (editReqList = []) => {
    return axiosInst.put("/course/detail", editReqList);
}

/**
 * 文章模板相关
 */

const pageArticleTemplates = (pageReq = {}) => {
    return axiosInst.post("/article/template/page", pageReq);
}

/**
 * 网站相关
 */
const pageWebsite = (pageReq = {}) => {
    return axiosInst.post("/website/page", pageReq);
}
const getPublicSites = () => {
    return axiosInst.get("/website/public/list/all");
}

const saveSite = (websiteSaveReq = {}) => {
    return axiosInst.post("/website", websiteSaveReq);
}
const uptSiteStatus = (id, status) => {
    return axiosInst.put("/website/status/" + id + "/" + status);
}
const deleteSite = (id) => {
    return axiosInst.del("/website/" + id);
}

const listSiteCate = () => {
    return axiosInst.get("/website/public/cate/all");
}
const clickSite = (id) => {
    return axiosInst.put("/website/public/click/" + id);
}

export default {
    pagePublicArticle,
    getPublicArticle,
    editArticle,
    addArticle,
    getArticle,
    pagePublicCourse,
    getPublicCourse,
    pageArticle,
    deleteArticle,
    uptArticleStatus,
    initArticleOrderNum,
    pageCourse,
    addCourse,
    editCourse,
    deleteCourse,
    getCourse,
    delCourseDetail,
    addCourseDetail,
    editCourseDetail,
    pageArticleTemplates,
    pageWebsite,
    getPublicSites,
    saveSite,
    uptSiteStatus,
    deleteSite,
    listSiteCate,
    clickSite,
}