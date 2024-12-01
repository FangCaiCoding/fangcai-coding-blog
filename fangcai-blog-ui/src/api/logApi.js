import axiosInst from "./axiosInst.js";


const page = (pageReq = {}) => {
    return axiosInst.post("/log/page", pageReq);
}

export default {
    page,
}