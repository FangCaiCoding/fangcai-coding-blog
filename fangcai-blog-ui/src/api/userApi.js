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

const editUser =(editUserReq = {})=>{
    return axiosInst.put("/user/editUser",editUserReq)

}
const exchangeVip = (token) => {
    return axiosInst.put(`/user/exchangeVip/${token}`)
}

const pageUser = (pageReq = {}) => {
    return axiosInst.post("/user/page", pageReq) 
}

const uptUserStatus = (userId, enabled) => {
    return axiosInst.put(`/user/status/${userId}/${enabled}`)
}

export default {
    loginByName,
    loginByWxCode,
    getUserInfo,
    editUser,
    exchangeVip,
    pageUser,
    uptUserStatus
}