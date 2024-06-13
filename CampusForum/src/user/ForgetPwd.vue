<template>
    <!-- 发送验证码 -->
<div class="container-fluid main" v-if="isTrue===false">
   
    <input type="text" placeholder="邮箱" v-model="email">
    <input type="text" placeholder="验证码" v-model="code"><p @click="getCode()">获取验证码</p>
    <div>
        <button @click="forgetFun()"> 提交</button>
        <button @click="goback()" >返回登录</button>
    </div>
    
</div>
<!-- 修改密码 -->
<div v-else  class="rePwd">
       <input type="text" placeholder="请输入新密码" v-model="pwd1" style="min-width: 300px;">
       <input type="text" placeholder="请输入再次新密码" v-model="pwd2" style="min-width: 300px;">
<div>
    <button @click="updatePwd()"> 提交</button>
        <button @click="goback()" >返回登录</button>
</div>
       
    </div>
</template>

<script lang="ts" setup>
import request from '@/utils/request';
import {  ref } from 'vue';

let email = ref('')
let code = ref('')
let Uid = 0;
let pwd1 = ref('')
let pwd2 = ref('')
//获取验证码
let isclick =ref(false)
const getCode =()=>{
    if(isclick.value===true){
        ElMessage("请一分钟以后重试")
        return;
    }
    isclick.value=true; 
    request.get("/user/email/1/"+email.value).then((res)=>{
        if(res.code==='200'){
            ElMessage("发送成功");
            
        }else{
            ElMessage("发送失败："+res.message);
        }
        //计时一分钟可重新发送
        setTimeout(() => {
        isclick.value = false;
      }, 60000);

    })
}


const goback =()=>{
    window.location.href ="/"
}

//验证验证码是否正确，正确返回用户id
const isTrue = ref(false)
const forgetFun = () => {
    request.get("/user/forget",{params:{
        code:code.value,
        email:email.value
    }}).then((res)=>{
        if(res){
            ElMessage("验证码正确")
            isTrue.value = true;
            Uid = res.data;
            return;
        }else{
            ElMessage("验证码错误")
            return;
        }

    })

}

//修改密码
const updatePwd = () =>{
    const pwdReg = /^(?=.*[a-z])(?=.*\d)[a-z\d]{6,14}$/;
    if(!pwdReg.test(pwd1.value)){
            ElMessage("密码必须包含小写字母和数字长度在6-14之间")
            return false;
        }

    if(pwd1.value != pwd2.value){
        ElMessage("输入的密码不一致")
            return false;
    }
    let user = {userId:Uid,userPassword:pwd1.value}
    request.post("/user",user).then((res)=>{
            if(res.code ==='200'){
                ElMessage("密码修改成功")
                goback()
            }else{
                ElMessage("密码修改失败")
            }
    })
}


</script>
<style scoped>
.main{
    height: 100vh;
    background:linear-gradient(to top, #76AFAE,#CAE9E8 ,#F4FAF9);
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
}
input{
    padding-left: 10px;
    width: 15vw;
    height: 4vh;
    margin:40px 30px;
    border-radius: 5px;
    border: 1px solid #76AFAE;
}
input:focus{
    border: 4px solid #76AFAE;
    outline: none;
}
button{
    background-color: #0080ff;
    height: 4vh;
    width:8vw;
    margin: 40px 10px;
    border-radius: 10px;
    border: none;
    outline: 1px solid #3e72ec;
    color: #ffffff;
    font-weight: 800;
    
}
p{
    color: #0080ff;
}
p:hover{
    cursor: pointer;
}
.rePwd{
    width: 100%;
    height: 100vh;
    min-width: 1200px;
    min-height: 900px;
    background:linear-gradient(to top, #76AFAE,#CAE9E8 ,#F4FAF9);
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}
</style>