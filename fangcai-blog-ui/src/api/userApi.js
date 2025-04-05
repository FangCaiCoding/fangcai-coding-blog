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

export default {
    loginByName,
    loginByWxCode,
    getUserInfo,
    editUser,
    exchangeVip
}