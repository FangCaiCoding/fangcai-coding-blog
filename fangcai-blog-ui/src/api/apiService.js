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

const getArticle = (id) => {
    return axiosInst.get("/article/" + id);
}

const addArticle = (articleSaveReq ={}) => {
    return axiosInst.post("/article", articleSaveReq);
}
const editArticle = (articleSaveReq ={}) => {
    return axiosInst.put("/article", articleSaveReq);
}

export default {
    loginByName,
    pagePublicArticle,
    getPublicArticle,
    editArticle,
    addArticle,
    getArticle,
}