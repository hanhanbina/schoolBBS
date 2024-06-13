
import request from '@/utils/request'
import { defineStore } from 'pinia'


export  const useUserStore = defineStore('user', {
  state: () => ({
    user_name: '',
    token:'',
    id:null,
    isLogin: false,
    isRemPwd:false,
  }),
  getters: {
  
  
  },
  actions: {
    async toLogin(userInfo,isRemPwd:boolean) {
      const res = await request.post("http://localhost:9090/user/login",userInfo)
      const {data,code,message} = res
      if(code === '200'){
        //如果用户在界面选中记住密码，将localstorage的记住密码标志设为true
        if(isRemPwd===true){
          this.isRemPwd = true
        }
        this.id = data.id
        this.isLogin=true
        this.token = data.token
        this.user_name = data.user_account
          return 200;
      }else{
        return message
      }
},

    logout(){
      this.isLogin = false
      this.user_name = ''
      this.token = ''
      this.isRemPwd=false,
      this.id=null
    },


  },
    //开启本地持久化
    persist:{
      storage:localStorage,
    }
    
}
)

