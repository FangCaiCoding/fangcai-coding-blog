import axiosInst from "./axiosInst.js";


const loginByName = (loginReq = {}) => {
    return axiosInst.post("/user/loginByName", loginReq);
}

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

export default {
    loginByName,
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
}