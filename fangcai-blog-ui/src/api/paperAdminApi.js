import axiosInst from "./axiosInst.js";

// 获取所有的题库-允许cateId为空
const listAllByCate = (cateId = null) => {
    return axiosInst.get("/paper/list/all", {
        params: { cateId }
    });
};

// 获取题库详情
const getPaperDetail = (id) => {
    return axiosInst.get(`/paper/detail/${id}`);
};

// 获取题目的列表
const pageQuestion = (pageReq) => {
    return axiosInst.post("/paper/question/page", pageReq);
};

// 新增题目
const addQuestion = (addReq) => {
    return axiosInst.post("/paper/question", addReq);
};

// 修改问题的名称
const uptQuestionName = (uptReq) => {
    return axiosInst.put("/paper/question/name", uptReq);
};

// 修改问题的描述
const uptQuestionIntro = (uptReq) => {
    return axiosInst.put("/paper/question/intro", uptReq);
};

// 修改问题的答案
const uptQuestionAnswer = (uptReq) => {
    return axiosInst.put("/paper/question/answer", uptReq);
};

// 修改问题的解析
const uptQuestionAnalysis = (uptReq) => {
    return axiosInst.put("/paper/question/analysis", uptReq);
};

export default {
    listAllByCate,
    getPaperDetail,
    pageQuestion,
    addQuestion,
    uptQuestionName,
    uptQuestionIntro,
    uptQuestionAnswer,
    uptQuestionAnalysis,
};