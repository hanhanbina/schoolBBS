import axios from 'axios'

const request = axios.create({
	baseURL: 'http://localhost:9090',  
    timeout: 5000
})
/* request 拦截器
可以自请求发送前对请求做一些处理
比如统一加token，对请求参数统一加密 */
request.interceptors.request.use(config => {

     // 根据请求的数据类型设置不同的 Content-Type
  if (config.data instanceof FormData) {
    // 如果是 FormData，设置为 multipart/form-data
    config.headers['Content-Type'] = 'multipart/form-data';
  } else {
    // 否则，默认设置为 application/json
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
  }

let user = sessionStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')||'') : null;
  if (user) {
        config.headers['token'] = user.token;  // 设置请求头
  }
    return config
    
}, error => {
    return Promise.reject(error)
});

/* response 拦截器
可以在接口响应后统一处理结果 */
request.interceptors.response.use(
    response => {
        let res = response.data;
        // 如果是返回的文件
        if (response.config.responseType === 'blob') {
            return res
        }

            // 兼容服务端返回的字符串数据
        if (typeof res === 'string') {
            try {
                //如果是有效的 JSON 字符串解析
                res = res ? JSON.parse(res) : res
            } catch (error) {
                //如果是普通字符串直接返回
                return res ;
            }
            
        }
        
        

        return res;
    },
    error => {
        console.log('err' + error) // for debug
        return Promise.reject(error)
    }
)


export default request;

