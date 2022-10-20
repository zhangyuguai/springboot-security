import axios from 'axios'
import {Message} from 'element-ui'
import store from "@/store";

axios.interceptors.request.use(config=> {
    let token = store.state.token!==''&&store.state.token!==null?store.state.token:null;
    if (token!=null) {
       config.headers['token']=token;
    }
    console.log('!!!!!!!!!!!!!',config)
    return config;
}, err=> {
    Message.error({message: '请求超时!'});
    return Promise.resolve(err);
})
axios.interceptors.response.use(data=> {
    if (data.status && data.status == 200 && data.data.status == 'error') {
        Message.error({message: data.data.msg});
        return;
    }
    return data;
}, err=> {
    if (err.response.status == 504||err.response.status == 404) {
        Message.error({message: '服务器被吃了⊙﹏⊙∥'});
    } else if (err.response.status == 403) {
        Message.error({message: '权限不足,请联系管理员!'});
    }else {
        Message.error(err.msg);
    }
    return Promise.resolve(err);
})

let base = '';

export const postRequest = (url, params) => {
    return axios({
        method: 'post',
        url: `${base}${url}`,
        data: params,
        transformRequest: [function (data) {
            let ret = ''
            for (let it in data) {
                ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
            }
            return ret
        }],
        headers: {
            'Content-Type': 'application/JSON'
        }
    });
}
export const uploadFileRequest = (url, params) => {
    return axios({
        method: 'post',
        url: `${base}${url}`,
        data: params,
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    });
}
export const putRequest = (url, params) => {
    return axios({
        method: 'put',
        url: `${base}${url}`,
        data: params,
        transformRequest: [function (data) {
            let ret = ''
            for (let it in data) {
                ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
            }
            return ret
        }],
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    });
}
export const deleteRequest = (url) => {
    return axios({
        method: 'delete',
        url: `${base}${url}`
    });
}
export const getRequest = (url) => {
    return axios({
        method: 'get',
        url: `${base}${url}`
    });
}
