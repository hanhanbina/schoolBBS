<template>
<div class="container-fluid" v-loading="loading" element-loading-text="自动登录中...">

 

        <div class="body container">
             <!-- 动画 -->
           <div class="left">
            <img src="@/assets/image/southern.png" class="img-fluid">
           </div>
            <!-- 登录框 -->
            <div class="right">
                <!-- 欢迎文字 -->
                <div class="t_title">
                <p >欢迎!<br/>看看今天发生了什么吧~</p> 
                </div>
                <!-- 登录部分 -->
                <div class="login" v-if="chooseLoginOrRegister">
                  
                    <div  style="height: auto;margin-left: 40%;width: 100%;height: auto;">
                    <input type="text"  class="ipt" placeholder="账号" v-model="loginUser.user_account" v-if="isEmail===false&&isEmailCode===false" required>
                     <input type="text" class="ipt" placeholder="邮箱" v-model="loginUser.email"  v-if="isEmail===true" required>
                     <span class="pointer" @click="sendEmailCode()" style="color: #0273fd;padding-left: 5%;" v-if="isEmailCode===true && isEmail===true">获取验证码</span>
                    </div>

                    
                    <input type="password" class="ipt" placeholder="密码" v-model="loginUser.user_password" v-if="isEmailCode===false" required>
                    <input type="password" class="ipt" placeholder="邮箱验证码" v-model="loginUser.emailCode" v-if="isEmailCode===true && isEmail===true" required>
                    <input type="text" class="ipt" placeholder="验证码" v-model="code" required>
                    <!-- 验证码 -->
                    <div class="identify"><SIdentify :identifyCode="randomCode"></SIdentify>
                    <span @click="generateRandomCodes" class="pointer">看不清，换一张</span></div>
                    
                    <!-- 记住密码忘记密码 -->
                    <div class="bom_ipt">
                        <div class="form-check form-switch">
                            <input class="form-check-input pointer" type="checkbox"   v-model="isRemPwd">
                            <label class="form-check-label t_lab" >记住密码</label>
                        </div>
                        <label class="t_lab pointer" style="margin-top: -2px;" @click="toForgetPwd()">忘记密码</label>
                    </div>
                    <div class="email_btn pointer">
                        <span @click="changeEmail()">{{ isEmail===true ? '改用账号登录' :'改用邮箱登录' }}</span>
                        <span @click="isEmailCode = !isEmailCode" v-if="isEmail===true">{{ isEmailCode===true ? '改用密码登录' :'改用邮箱验证登录' }}</span>
                    </div>
                
                </div>
                <!-- 注册部分 -->
                <div class="login" v-if="!chooseLoginOrRegister">
                    <input type="text" class="ipt" placeholder="昵称" v-model="registerUser.userName" >
                    <input type="text" class="ipt" placeholder="邮箱" v-model="registerUser.email">
                    <input type="text" class="ipt" placeholder="账号" v-model="registerUser.userAccount">
                    <input type="password" class="ipt" placeholder="密码" v-model="registerUser.userPassword">
                    <input type="text" class="ipt" placeholder="验证码" v-model="code">
                    <!-- 验证码 -->
                    <div class="identify"><SIdentify :identifyCode="randomCode"></SIdentify>
                    <span @click="generateRandomCodes" class="pointer">看不清，换一张</span></div>
                    
                    <!-- 记住密码忘记密码 -->
                    <div class="bom_ipt" v-if="chooseLoginOrRegister===true">
                        <div class="form-check form-switch">
                            <input class="form-check-input pointer" type="checkbox" id="flexSwitchCheckDefault">
                            <label class="form-check-label t_lab" for="flexSwitchCheckDefault">记住密码</label>
                        </div>
                        <label class="t_lab pointer" style="margin-top: -2px;" @click="toForgetPwd()">忘记密码</label>
                    </div>
                </div>

                <!-- 登陆注册按钮 -->
                <div class="lr_btn ">
                <MyBtn class="btn" @click="chooseLoginOrRegister= !chooseLoginOrRegister">{{ chooseLoginOrRegister ? '去注册':'去登录' }}</MyBtn>
                <!-- 登录提交按钮 -->
                <MyBtn class="btn" @click="loginByAccount" v-if="chooseLoginOrRegister">提交</MyBtn>
                <!-- 注册提交按钮 -->
                <MyBtn class="btn" @click="register" v-if="!chooseLoginOrRegister">提交</MyBtn>
                </div>

            </div>

        
        </div>
        
</div>
</template>

<script lang="ts" setup>
import MyBtn from '@/components/other/my-btn.vue'
import SIdentify from '@/components/other/identify.vue'
import {  ref } from 'vue';
import { onMounted } from 'vue';
import request from '@/utils/request';
import {useUserStore} from '@/stores/user'
import { useRouter } from 'vue-router';


onMounted(()=>{
    isLogined()
    generateRandomCodes()
})

//随机出的验证码
const randomCode = ref('')
//登录或注册，true为登录false为注册
const chooseLoginOrRegister = ref(true)
//是否邮箱登录
const isEmail = ref(false)
//是否使用邮箱验证码 
const isEmailCode =ref(false)
//用户输入的图片验证码
let code = ref('')
//是否记住密码
let isRemPwd=false;
//实例化路由(必须定义在方法外)
const router = useRouter();
//加载中
const loading = ref(false)

//登录用户对象
let loginUser = ref({
    user_account:'',emailCode:'',email:'',user_password:''
})
//注册用户对象
let registerUser = ref({
    userName:'',email:'',userPassword:'',userAccount:''
})
//改用账号登录' :'改用邮箱登录
const changeEmail = ()=>{
    isEmail.value = !isEmail.value
    isEmailCode.value = false;
}
 // 定义生成随机码的方法
 const generateRandomCodes = () => {
    const identifyCodes = '23456789ABCDEFGHGKLMNPQRSTUVWXYZ';
    const min = 0;
    const max = identifyCodes.length - 1;
    let result = '';
    for(let i =0;i<4;i++){
        const randomIndex = Math.floor(Math.random() * (max - min + 1)) + min;
        result += identifyCodes[randomIndex];
    }
    randomCode.value = result;
 }

 //登录
 const loginByAccount = () =>{

    const store = useUserStore();
    if(!isNull()){return;}
    if(!rules()){return;}

    store.toLogin(loginUser.value,isRemPwd).then((result)=>{
        if(result === 200){
            ElMessage({
                message:'登陆成功',
                type:'success'
            })
            router.push('/index/forum/postList/1')
        }else{
            //清空用户信息
        loginUser.value = {user_account:'',emailCode:'',email:'',user_password:''}
        //验证码清空刷新
        reYanZhengM()
            ElMessage({
                message:result,
                type:'error'
            })
        }
    })
 }

//判断本地存储是否有数据表示登陆过，是的话直接登录
const isLogined = ()=>{
    if(localStorage.getItem('user')){
    const lg = JSON.parse(localStorage.getItem('user')||'');
    if(lg.isRemPwd===true){
        //开启加载动画
        loading.value=true
        //延时4秒跳转页面
        setTimeout(()=>{
            router.push('/index/forum/postList/1')
        },2500)
    }
}  
}


 //注册
 const register = ()=> {
    if(!isNull()){return;}
    if(!registerRules()){return;}
    request.post('/user/register',registerUser.value).then((res:any)=>{
        if(res.success=true){
            ElMessage({
                message:'注册成功',
                type:'success'
            })
            router.go(0)
        }else{
            reYanZhengM()
            registerUser.value = {userName:'',email:'',userPassword:'',userAccount:''}
            ElMessage({
                message:'注册失败',
                type:'error'
            })
        }
    })
 }
//忘记密码
const toForgetPwd = ()=>{

   router.push('/forget')
}

 //发送邮箱验证码
const sendEmailCode =()=>{
    request.get('/user/email/1/'+loginUser.value.email).then((res)=>{
        if(res.code==='200'){
            ElMessage({
                message:'发送成功',
                type:'success'
            })
        }else{
            ElMessage({
                message:'发送失败：'+res.message,
                type:'error'
            })
        }
    })
}
//清空用户验证码，并刷新随机验证码
const reYanZhengM = ()=> {
    code.value = ''
    generateRandomCodes()
}
//验证码是否正确，是否为空
const isNull= ()=>{
    const aaa = ref(code.value.toUpperCase())
    if(code.value=== ''||code.value===null){
        alert("验证码不能为空")
        reYanZhengM()
        return false;
    }else if(aaa.value != randomCode.value){
        alert("验证码错误")
        reYanZhengM()
        return false;
    }
    return true;
}
//验证登录时字段
const rules = ()=>{
    //账号＋密码
    if(isEmail.value===false && isEmailCode.value===false){
        if(loginUser.value.user_account.trim() ===''){
            alert("账号不能为空")
            return false;
        }else if(loginUser.value.user_password.trim() === ''){
            alert("密码不能为空")
            return false;
        }
        return true;
    }else if(isEmail.value===true && isEmailCode.value === false){
         //邮箱＋密码
        if(loginUser.value.email.trim() === ''){
            alert("邮箱不能为空")
            return false;
        }else if(loginUser.value.user_password.trim() === ''){
            alert("密码不能为空")
            return false;
        }
        
        return true;
    }else if(isEmail.value===true && isEmailCode.value===true){
         //邮箱＋邮箱验证码
        if(loginUser.value.email.trim()===''){
            alert("邮箱不能为空")
            return false;
        }else if(loginUser.value.emailCode.trim()===''){
            alert("邮箱验证码不能为空")
            return false;
        }
        return true;
    }else{
        //其他
        return console.log(" rules方法错误");
    }
}

//验证注册的字段
const registerRules = () =>{
    const reg = /^[a-z0-9]{6,12}$/
    const emailReg = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/;
    const nicknameReg = /^[\u4e00-\u9fa5a-zA-Z0-9]{2,10}$/;
    const pwdReg = /^(?=.*[a-z])(?=.*\d)[a-z\d]{6,14}$/;
        if(!reg.test(registerUser.value.userAccount)){
            ElMessage("账号只能为小写字母或数字，且长度在6-12之间")
            return false;
        }
        if(!emailReg.test(registerUser.value.email)){
            ElMessage("请输入正确的邮箱")
            return false;
        }
        if(!nicknameReg.test(registerUser.value.userName)){
            ElMessage("昵称不能含有特殊字符且长度为4-10之间")
            return false;
        }
        if(!pwdReg.test(registerUser.value.userPassword)){
            ElMessage("密码必须包含小写字母和数字长度在6-14之间")
            return false;
        }

        return true;
    
}
</script>

<style scoped>





.container-fluid{
    height: 100vh;
    width: 100%;
    background:linear-gradient(to top, #76AFAE,#CAE9E8 ,#F4FAF9);
    display: flex;
    align-items: center;

}
.body{
    height: auto;
    display: flex;
    align-items: center;
    border: 4px solid #fff;
    z-index: 9;

}
/* 右边部分 */
.right{ 
    width: 60%;
    height: 100%;

    /* 欢迎title */
    .t_title{
        font-size: 2em;
        font-weight: 1000;
        margin-left: 28%;
        line-height: 2em;
        margin-bottom: -3%;
        }
    .t_title:hover{
        cursor: default;
    }

    /* 登录部分 */
    .login{
        display: flex;
        flex-direction: column;
        margin-left: 16%;
        margin-top: 2%;
        height: 100%;
        width: 65%;
        align-items: center;
        justify-content: center;
        /* 账号密码输入框 */
        .ipt{
        width:60%;
        height: 3em;
        border-radius: 10px;
        border: 4px solid #96b8d3;
        padding-left: 15px;
        margin-top: 5%;
            }
    /* 输入框被选中时 */
        .ipt:focus{
            border: 4px solid #3e8295;
            outline: none;
            }

        /* 记住密码立即注册 */
        .bom_ipt{
            display: flex;
            justify-content: space-between;
            align-items: center;
            width: 52%;
            height: 3em;
            .t_lab{
                color: #253130;
                font-size: 15px;
                
                    }
                }
                /* 验证码 */
                .identify{
                    display: flex;
                    align-items: center;
                    justify-content: space-around;
                    width: 80%;
                    margin-top: 5%;
                    & span{
                        color: #0273fd;
                    }
                }

                /* 改用邮箱登录 */
                .email_btn{
                    color: #525252;
                    margin-bottom: 2%;
                    display: flex;
                    width: 80%;
                    justify-content: space-around;
                }

            }
            /* 登录注册按钮 */
        .lr_btn{
            display: flex;
            margin-left: 20%;
            width: 60%;
            margin-top: 2%;
            justify-content: space-around;
          .btn{
            width: 5em;
           
          }
        }
}

/* 左边图片部分 */
.left{
    width: 40%;
    height: 70vh;
    background-color: #0273fd;
    /* 图片 */
    & img{
    height:100%;
        }
}

/* 鼠标改手 */
.pointer:hover{
    cursor: pointer;
}

</style>