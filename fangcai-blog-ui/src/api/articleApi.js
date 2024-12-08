import axiosInst from "./axiosInst.js";


/**
 * 公开文章相关
 */
const pagePublicArticle = (pageReq = {}) => {
    return axiosInst.post("/article/public/page", pageReq);
}

const getPublicArticle = (id) => {
    return axiosInst.get("/article/public/" + id);
}


/**
 * 后台管理相关：增删改查
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

export default {
    pagePublicArticle,
    getPublicArticle,
    pageArticle,
    getArticle,
    addArticle,
    editArticle,
    deleteArticle,
    uptArticleStatus,
    initArticleOrderNum
}