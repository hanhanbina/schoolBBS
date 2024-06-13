<template>
<div class="people_body">
    <div class="user_head">
        <!-- 用户资料背景图 -->
        <div class="background">
            <!-- 用户背景图 -->
            <img src="../assets/image/backimg.png" class="background">
        </div>
        <!-- 用户资料 -->
        <div class="userInfo">
            <div style="display: flex;
            flex-direction: column;">
                <h2 >{{ userInfo.userName }}</h2>
                <span>性别：{{ userInfo.sex}}</span>
                <span >简介：{{ userInfo.profile }}</span>
                <span>邮箱：{{ userInfo.email }}</span>
            </div>
            <el-button type="primary" style="margin-top: 120px;margin-right: 30px;height: 40px;" @click="isUpdate= !isUpdate">{{ isUpdate===true ?'返回':'编辑个人资料' }}</el-button>
        </div>
        <!-- 用户头像 -->
        <el-avatar class="userHeadImg" shape="square" :size="160" :fit="'cover'" :src="userInfo.headImg" >未找到用户头像</el-avatar>
    </div>
    <!-- 分割线div -->
    <div style="height: 10px;background-color: #f2f2f2;" ></div>
    <!-- 选项卡 -->
    <div class="tabs" v-if="isUpdate===false">
    <el-tabs v-model="activeName" class="people_tabs" @tab-click="handleClick" >

    <!-- 我的赞 -->
    <el-tab-pane label="我的赞" name="first" lazy>
        <my-like></my-like>
    </el-tab-pane>
    <!-- 我的收藏 -->
    <el-tab-pane label="我的收藏" name="second" lazy>
        <fav></fav>
    </el-tab-pane>

    <!-- 我的评论 -->
    <el-tab-pane label="我的评论" name="third" lazy>
        <my-reply></my-reply>
    </el-tab-pane>
     </el-tabs>
    </div>

    <!-- 编辑用户资料表单 -->
    <div class="updateForm" v-if="isUpdate===true">
    <el-form
    :label-position="'top'"
    label-width="100px"
    :model="userInfo"
    style="min-width: 460px"
  >
  <el-form-item label="头像">
    <el-upload
    class="uploadImg"
    action="http://localhost:9090/file/upload"
    :show-file-list="false"
    :on-success="handleAvatarSuccess"
    :before-upload="beforeAvatarUpload"
  >
    <img v-if="imageUrl" :src="imageUrl" class="upload_avatar" />
    <svg v-else style="margin-left: 25px;margin-top: 24px;" t="1702310906354" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4233" width="48" height="48"><path d="M533.333333 490.666667V128h-42.666666v362.666667H128v42.666666h362.666667v362.666667h42.666666V533.333333h362.666667v-42.666666z" fill="#cdcdcd" p-id="4234"></path></svg>

  </el-upload>


</el-form-item>
    <el-form-item label="昵称">
      <el-input v-model="userInfo.userName" />
    </el-form-item>
    <el-form-item label="性别">
      <el-input v-model="userInfo.sex" />
    </el-form-item>
    <el-form-item label="简介">
      <el-input v-model="userInfo.profile" />
    </el-form-item>
  </el-form>
  <el-button type="primary" @click="upadteUserInfo()">提交</el-button>
    </div>



</div>
</template>

<script lang="ts" setup>
import {ref,onMounted,createApp} from 'vue'
import type { TabsPaneContext } from 'element-plus'
import MyLike from '@/components/user/myLike.vue'
import MyReply from '@/components/user/myReply.vue'
import request from '@/utils/request';
import type { UploadProps } from 'element-plus'
import Fav from '@/components/user/myFavorites.vue'


onMounted(()=>{
    getUserInfo()
})

const app = createApp({})
app.component('myLike',MyLike)
app.component('myReply',MyReply)
app.component('fav',Fav)


const imageUrl = ref('')
//用户信息对象
const userInfo = ref({
    email: "",headImg: "",profile: "",sex: "",userAccount: "",userId: -1,userName: "",userPassword: ""})
// 从localStorage中获取用户信息
const getUserInfo=()=>{
    const u = JSON.parse(localStorage.getItem('userInfo')||'')
    userInfo.value=u
}
//标签页激活的选项卡名称
const activeName = ref('first')
// 点击标签页触发事件
const handleClick = (tab: TabsPaneContext, event: Event) => {
    switch (tab.props.name) {
        case "first" :

            break;
        case "second" :
            break;
        case "third" :
            break;
        default:
            return;
    }

}
const isUpdate=ref(false);
const imgguod = ref('')
const upadteUserInfo =  () =>{
    userInfo.value.headImg = imgguod.value;
     request.post('/user',userInfo.value).then((res:any)=>{
        if(res.code==='200'){
            ElMessage("修改成功")
            isUpdate.value= false
        }else{
            ElMessage("修改失败")
        }
    }).catch(()=>{
        ElMessage({
            type:'error',
            message:"修改失败"
        })
    })
  
}
//上传成功后
const handleAvatarSuccess: UploadProps['onSuccess'] = (
  response,
  uploadFile
) => {
  imageUrl.value = URL.createObjectURL(uploadFile.raw!)
   imgguod.value=response
}
//上传之前
const beforeAvatarUpload: UploadProps['beforeUpload'] = (rawFile) => {
  if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png') {
    ElMessage.error(rawFile.type )
    return false
  } else if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error('Avatar picture size can not exceed 2MB!')
    return false
  }
  return true
}

</script>
<style scoped>
.people_body{
    margin: 0;
    padding: 0;
    height: auto;
    min-width: 1320px;
    margin-left: -12px;
    border-radius: 10px;
    position: relative;
    min-height: 860px;
 
}
.user_head{
    min-height: 400px;
    max-height: 400px;
    border-radius: 10px;
    background-color: #fff;
}
.background{
    min-height: 210px;
    max-height: 210px;
    width: 100%;
    border-radius: 10px 10px 0px 0px ;
    margin: 0;
    padding: 0;
    background-color: #c0c0c0;
}
.userInfo{
    min-height:190px;
    margin-left: 240px;
    display: flex;
    justify-content: space-between;
    margin-top: 15px;

    & span{
        color: #808080;
        margin-bottom: 5px;
    }
}
.userHeadImg{
    position: absolute;
    top: 170px;
    left:50px;
    outline: 4px solid #fff;
}
:deep(.el-tabs__item) {
    font-size: 15px ;
    width: 130px;
   
    height: 50px;
    letter-spacing: 2px;
}
.updateForm{
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 500px;
    flex-direction: column;

}
.el-input{
     margin-bottom: 20px;   
    }
.uploadImg{
    height: 100px;
    width: 100px; 
    border: 1px dashed #c0c0c0;
    margin-left: 40%;
}
.uploadImg:hover{
    cursor: pointer;
    background-color: #e3eaff;
}
.upload_avatar{
        height: 100px;
    width: 100px; 
}
.tabs{
    background-color: #fff;
}
</style>