import axiosInst from "./axiosInst.js";


// 教程相关api
const pagePublicCourse = (pageReq = {}) => {
    return axiosInst.post("/article/public/course/page", pageReq);
}

const getPublicCourse = (id) => {
    return axiosInst.get("/article/public/course/" + id);
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
    pagePublicCourse,
    getPublicCourse,
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