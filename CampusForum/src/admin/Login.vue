<template>
    <div class="body">
        <div class="control">
        <div class="item Alimmfont">
            登录
        </div>
        <div class="content">
            <div style="display: block;">
                <p>账号</p>
                <input type="text" v-model="admin.account" placeholder="账号" />
                <p>密码</p>
                <input type="password" v-model="admin.password" placeholder="密码"/>
                <br/>
                <input type="submit" value="登录" @click="go()"/>
            </div>
        </div>
    </div>
    </div>
    </template>
    
    <script lang="ts" setup>
    import { useAdminStore } from '@/stores/admin';
    import { reactive } from 'vue';
    const admin = reactive({
        password:'',
        account:'',
    })
    const store = useAdminStore()
    function go(){
        let res = 0;
            store.toLogin(admin).then((result) => {
              res = result
        })
    
        if(res = 1){
            window.location.href = '/admin/userpwd'
        }else{
            alert('账号或密码错误')
        }
    }
    
    </script>
    <style scoped>
    *{
        margin: 0;
        padding: 0;
    }
    body{
        background: #f3f3f3;
    }
    .control{
        width: 440px;
        background: white;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%,-50%);
        border-radius: 5px;
        border: #adadad 1px solid;
    }
    .item{
        width: 430px;
        height: 80px;
        font-size: 40px;
        text-align: center;
        line-height: 80px;
        opacity: 0.8;
        padding-left: 10px;
        letter-spacing: 30px;
    }
    .content{
        width: 100%;
    }
    .content div{
        margin: 20px 30px;
        display: none;
        text-align: left;
    }
    p{
        color: #4a4a4a;
        margin-top: 30px;
        margin-bottom: 6px;
        font-size: 15px;
    }
    .content input[type="text"], .content input[type="password"]{
        width: 100%;
        height: 40px;
        border-radius: 3px;
        border: 1px solid #adadad;
        padding: 0 10px;
        box-sizing: border-box;
    }
    .content input[type="submit"]{
        margin-top: 40px;
        width: 100%;
        height: 40px;
        border-radius: 5px;
        color: white;
        border: 1px solid #adadad;
        background: #2c51f4;
        cursor: pointer;
        letter-spacing: 4px;
        margin-bottom: 40px;
    }
    
    
    </style>