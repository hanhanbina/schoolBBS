<template>
<div class="main">
    <!-- 导航栏 -->
    <div class="nav">
        <!-- LOGO图片 -->
        <div class="flexCenter" style="width: 15vw; min-width: 200px;height: 80px;">
        <img src="../assets/image/logo.png" style="width: 150px;">
    </div>
    <!-- 导航栏选项卡 -->
        <div class="nav_hr">
           <router-link to="/index/forum/postList/1">论坛</router-link>  
        </div>
         <input type="text" class="nav_ipt" v-model="searchValue"> 
         <button class="nav_btn" style="margin-right: 10px;" @click="isHandlePostDia = true">发贴</button>
         <button class="nav_btn" @click="clickSearch()">搜索</button>
       <div class="nav_hr"><router-link to="/schoolnews">校园资讯</router-link></div>
        <!-- <div class="nav_hr"><router-link to="">投票</router-link></div> -->
        <div class="nav_hr"><router-link to="/index/map">校园地图</router-link></div>
            <img @click="goPeople()" id="first_img" src="../assets/image/点赞.svg" class="nav_img" style="width: 25px;margin-top: -2px;">
            <img @click="goPeople()" src="../assets/image/评论.svg" class="nav_img" style="width: 30px; margin-left: 20px;">
            <img @click="goPeople()" src="../assets/image/收藏.svg" class="nav_img" style="width: 34px">

            <el-dropdown @command="handleCommand">
        <div class="nav_user" > <el-avatar shape="square" :size="40" :src="userData.headImg">{{ userData.userName }}</el-avatar><p>{{ userData.userName }}</p></div>
        <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item command="pepole">我的主页</el-dropdown-item>
        <el-dropdown-item command="logout">退出登陆</el-dropdown-item>
      </el-dropdown-menu>
    </template>
    </el-dropdown>       
        
    </div>
    <!-- 主要内容 -->
    <div class="container body">
    <router-view ></router-view>
    </div>


<!-- 发贴对话框 -->
<el-dialog v-model="isHandlePostDia" title="发布帖子" style="width: 900px;">
    <el-form :model="postCacheFormData">
      <el-form-item label="帖子标题" >
        <el-input  autocomplete="off" v-model.trim="postCacheFormData.title"/>
      </el-form-item>
      <el-form-item label="帖子分类" >
        <el-select v-model="postCacheFormData.categoryId" placeholder="选择帖子分类">
          <el-option v-for="(i,index) in categoryValue " :key="index" :label="i.name" :value="i.id" ></el-option>
        </el-select>
      </el-form-item >
    <el-form-item label="内容"> 
        <v-md-editor   :disabled-menus="[]" 
        v-model.trim="postCacheFormData.postContent"  height="350px"  mode="edit" 
        placeholder="在这里输入你的评论~"
        @upload-image="handleUploadImage"
        ></v-md-editor>
    </el-form-item>
      
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="SendPost()">提交</el-button>
        <el-button @click="isHandlePostDia=false">取消</el-button>
      </span>
    </template>
  </el-dialog>


</div>
</template>

<script lang="ts" setup>
import request from '@/utils/request';
import { ref,onMounted } from 'vue'
import { useRouter } from 'vue-router'
import {useUserStore} from '@/stores/user'
import { ElMessage } from 'element-plus'
onMounted(()=>{
    getUserInfo()
    getCategory()
})


//从localStorage中获取用户账号
const userId= JSON.parse(localStorage.getItem('user')).id
//用户数据
const userData = ref({userId: "",userName: "",profile: "",userAccount: "",userPassword: "",headImg: "",email: "",sex: ""})
const store = useUserStore();
const router = useRouter();

//根据用户id查询用户
const getUserInfo =()=>{
    request.get('/user/'+userId).then((res)=>{
        if(res.code==="200"){
            userData.value = res.data
            localStorage.setItem('userInfo',JSON.stringify(res.data))
        }else{
            alert('查询不到用户信息')
            return
        }
    })
}
//搜索
const searchValue = ref('')
const clickSearch = ()=>{
   let data = searchValue.value
    router.push({name:'postlist', query:{searchData:data} })
}

//下拉菜单点击事件
const handleCommand = (command: string | number) => {
    if(command==='pepole'){
        router.push('/people')
    }else{
        localStorage.removeItem('user')
        localStorage.removeItem('userInfo')
        store.logout()
        ElMessage('已退出登录')
        router.push('/')


    }
}
//前往个人主页
const goPeople=()=>{
    router.push('/people')
}

//获取分类信息用于发帖选择分类
const categoryValue =  ref([]);
const getCategory = () =>{
    request.get('/category').then((res:any)=>{
        if(res){
            categoryValue.value = res
        }else{
            console.error("分类请求失败")
        }
    })
}


// 发布帖子
const isHandlePostDia = ref(false);
const postCacheFormData = ref({title:'',postContent:'',userId:null,categoryId:null})
const SendPost = async () =>{
    postCacheFormData.value.userId = parseInt(JSON.parse(localStorage.getItem('user')).id) 
    isNulled(postCacheFormData.value.title,postCacheFormData.value.postContent)
    await  request.post('/post',postCacheFormData.value).then((res)=>{
        if(res){
            ElMessage({
                type:'success',
                message:'发布成功'
            })
            isHandlePostDia.value=false
            postCacheFormData.value = {title:'',postContent:'',userId:userId.value,categoryId:null}
        }else{
            ElMessage('发布失败')
            postCacheFormData.value = {title:'',postContent:'',userId:userId.value,categoryId:null}
        }
    }).catch(e=>{
        ElMessage('发布失败')
        postCacheFormData.value = {title:'',postContent:'',userId:userId.value,categoryId:null}
        console.log('帖子发送失败\n'+e)
    })
}
const isNulled = (data1:string,data2:string) =>{
    if(data1===''||data1===null){
        ElMessage({
                type:'error',
                message:'内容不能为空'
            })
    }else if(data2===''||data2===null){
        ElMessage({
                type:'error',
                message:'内容不能为空'
            })
    }else{

    }
}
//上传图片
const handleUploadImage = (event, insertImage, files) =>{
    // 创建一个 FormData 对象
  const formData = new FormData();
   // 将文件添加到 FormData
    formData.append('file', files[0]);
    request.post('/file/upload',formData,{
        headers: {
             'Content-Type': 'multipart/form-data'
  }
    }).then((res)=>{
        insertImage({
        url:res,
        desc: '图片',
        width: '100px',
        height: '100px',
      });
    })
}

</script>
<style scoped>
.main{
    position: relative;
    width: 100vw;
    height: 100vh;
    background-color: #F2F2F2;
}
/* 整个导航栏 */
.nav{
    position: absolute;
    background-color: #FFFFFF;
    height: 75px;
    flex-wrap: nowrap;
    min-width: 100vw;
    display: flex;
    align-items: center;
    position: fixed;
    z-index: 99;
}
.body{
    top: 85px; 
    border-radius: 10px;
    min-width: 1300px;
    position: relative;
    padding: 0;
   background-color: #ffffff;
}
/* 导航栏每个选项卡 */
.nav_hr{
    height: 70%;
    width: 8%;
    min-width: 100px;
    flex-wrap: nowrap;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size:1.2em;
    margin-right: 20px;
    margin-left: 20px;
    & a{
        color: #000;
        font-weight: 800;
        letter-spacing: 5px;
        text-decoration: none;
        /* text-shadow: #dbcaca 1px 0 rgb(223, 196, 196)000 0 1px 0, #000 -1px 0 0, #000 0 -1px 0; */
        
    }
}
/* 导航栏用户 */
.nav_user{
    height: 100%;
    display: flex;
    align-items: center;
    width: 12vw;
    min-width: 200px;
    margin-left: 50px;
    justify-content: center;
    & p{
        color: #a6a6a6;
        margin-left: 20px;
        font-size: 16px;
    }
}
/* 导航栏输入框 */
.nav_ipt{
    outline: none;
    border-radius: 5px;
    /* background-image: url('/src/assets/image/search.svg');
    background-size: contain;
    background-repeat: no-repeat;
    background-position: right; */
    min-height: 30px;
    min-width: 250px;
    margin-right: 20px;
    border: #a2a2a2 1px solid;
    background-color: #f5f5f5;

}
/* 导航栏搜索按钮 */
.nav_btn{
    min-width: 70px;
    width: 3vw;
    height: 35px;
    border-radius: 10px;
    outline: none;
    border: none;
    background-color: #36a3f3;
    color: #fff;
    letter-spacing: 2px;
   
}
/* 导航栏图片点赞等 */
.nav_img{
 min-width: 25px;
margin: 0px 14px;
}
.nav_img:hover{
    cursor: pointer;
}

/* 弹性盒子水平居中 */
.flexCenter{
    display: flex;
    justify-content: center;
    align-items: center;
}
#first_img{
    margin-left: 3vw;
}

/* 去除下拉菜单黑框 */
:deep(.el-tooltip__trigger:focus-visible) {
      outline: none;
    }
</style>