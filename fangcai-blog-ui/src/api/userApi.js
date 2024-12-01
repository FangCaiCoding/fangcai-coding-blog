import axiosInst from "./axiosInst.js";


const loginByName = (loginReq = {}) => {
    return axiosInst.post("/login/loginByName", loginReq);
}
const loginByWxCode = (loginReq = {}) => {
    return axiosInst.post("/login/loginByWxCode", loginReq);
}
const getUserInfo = () => {
    return axiosInst.get("/login/getUser")
}
export default {
    loginByName,
    loginByWxCode,
    getUserInfo
}