import axios from 'axios'
import router from "@/router/index.js";
import { MessageBox, Message } from 'element-ui'

export function request(config) {
  const instance = axios.create({
    baseURL: process.env.API,
    timeout: 50000,
  })
  //axios拦截器
  instance.interceptors.request.use(config => {
      if (localStorage.token) { //判断token是否存在
        config.headers.token = localStorage.token; //将token设置成请求头
      }
      return config;
    },
    err => {
      return Promise.reject(err);
    }
  );

  // http response 拦截器
  instance.interceptors.response.use(response => {
      if (response.data.code === -101) {
        // alert('登录超时，请重新登录')
        Message({message: '登录超时，请重新登录', type: 'error', duration: 3 * 1000});
        localStorage.removeItem('token')
        router.replace('/login')
      }
      if (response.data.code === -105) {
          // alert('请选择项目!')
          Message({message: '请选择项目!', type: 'error', duration: 3 * 1000});
        }
      return response;
    },
    error => {
      return Promise.reject(error);
    }
  );
  //发送请求
  return instance(config)
}
