
import request from '@/utils/request'
import { defineStore } from 'pinia'


export const useAdminStore = defineStore('admin', {
  state: () => ({
    id:'',
    account: '',
    name:'',
    token:'',
    isLogin: false,
  }),
  getters: {
    getUserName(state): string{
      return state.name
  },
  getisLogin(state): boolean{
      return state.isLogin
  },
  getAccount(state): string{
    return state.account
  }
  },
  actions: {
    async toLogin(adminInfo) {
      const res = await request.post("http://localhost:9090/admin/login",adminInfo)
      const {data,code,message} = res
      if(code === '200'){
        this.isLogin=true
        this.name = data.name
        this.account = data.account
        this.id = data.id
        this.token = data.token
          return 1
      }else{
        return 0
      }
},

    logout(){
      this.isLogin = false
      this.name = ''
      this.account = ''
      this.id = ''
      this.token = ''
    }

  },
       //开启本地持久化
    persist:{
        storage:localStorage,
      }//开启本地持久化
  
    
}
)

