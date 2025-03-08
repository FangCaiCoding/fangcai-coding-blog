import axiosInst from "./axiosInst.js";

const listAllCate = () => {
    return axiosInst.get("/paper/public/cate/all");
}

const listPublicAll = (cateId) => {
    return axiosInst.get(`/paper/public/list/all`, { params: { cateId } });
}

const getPaperDetail = (id) => {
    return axiosInst.get(`/paper/public/detail/${id}`);
}

const getQuestion = (questionId) => {
    return axiosInst.get(`/paper/public/question/${questionId}`);
}

const getQuestionAnswer = (questionId) => {
    return axiosInst.get(`/paper/public/question/answer/${questionId}`);
}

const getQuestionAnalysis = (questionId) => {
    return axiosInst.get(`/paper/public/question/analysis/${questionId}`);
}

export default {
    listAllCate,
    listPublicAll,
    getPaperDetail,
    getQuestion,
    getQuestionAnswer,
    getQuestionAnalysis,
}