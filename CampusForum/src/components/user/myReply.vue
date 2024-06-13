<template>
    <div class="myLike_main" >
    <el-card class="myLike_card" v-for="(item,index) in (myLikeData as any[])" :key="index">
               <!-- 删除图标按钮 -->
     <img class="delete-icon" src="@/assets/image/删除.svg" @click="cancelReply(item.replyId)" style="width: 15px; position: absolute;top: 5px;right: 4px;">
    <template #header>
        <!-- 标题 -->
      <div class=" ChinaAndEnglishFont" style="height: 50px;overflow: hidden;">
        <strong>{{ item.title }}</strong>
    </div>
    </template>

    <div class="myLike_content ChinaAndEnglishFont" style="min-height: 100px;max-height: 100px;overflow: hidden;">
        <!-- 内容 -->
   我的评论: {{ item.replyContent }}
    </div>
    <hr style="margin-top: 0px;margin-bottom: 20px;" />
    <div >
        <div class="myLikeCard_footerText" style="font-size: 15px;margin-left: -10px;">
            <span>
                <img src="@/assets/image/点赞.svg" style="width: 15px;margin-top: -4px;" >
                {{ item.upvote }}</span>
            <span style="color: #808080;">
                <img src="@/assets/image/时间.svg" style="width: 15px;margin-top: -4px;;" >
                {{item.datetime}}</span> 
            </div>
            
    </div>
  </el-card>


  <span v-if="myLikeData[0]===undefined||myLikeData.length===0">没有点赞数据哦~快去评论吧</span>
</div>
       <!-- 分页器 -->
       <div style="width: 100%; display: flex;  height: 50px;padding-bottom: 10px;">
        <el-pagination
          class="pagination"
          :current-page="pageNumber"
          :page-size="pageSize1"
          :disabled="false"
          :small="false"
          :background="true"
          layout="total,  prev, pager, next, jumper"
          :total="thetotal"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
</template>

<script lang="ts" setup>
import {ref,onMounted} from 'vue'
import request from '@/utils/request';


onMounted(()=>{
    getUserInfo()
    getMyLike()
})
//用户信息对象
const userInfo = ref({
    email: "",headImg: "",profile: "",sex: "",userAccount: "",userId: -1,userName: "",userPassword: ""})
// 从localStorage中获取用户信息
const getUserInfo=()=>{
    const u = JSON.parse(localStorage.getItem('userInfo')||'')
    userInfo.value=u
}

const pageSize1 = ref(8) ;
const pageNumber = ref(1);
const thetotal = ref(100);
const handleCurrentChange=(pageNum: number)=>{
    pageNumber.value= pageNum;
    getMyLike()
}
const handleSizeChange=(pageSize2: number)=>{
     pageSize1.value = pageSize2;
}
//根据用户id请求{我的评论}
const myLikeData = ref([{}])
const getMyLike = async ()=>{
    await request.get('user/myreply/'+userInfo.value.userId,
    {params:{
        pageNum:pageNumber.value,
        pageSize:pageSize1.value
    }}).then((res:any)=>{
            if(res){
               myLikeData.value = res.records
               thetotal.value = res.total
            }else{
                console.log("我的评论数据请求错误")
            }
    }).catch(()=>{
       return console.log("我的评论数据请求错误");
    })
}

const cancelReply=(id:number)=>{
    request.delete('/replyPost/'+id).then((res)=>{
        if(res){
            ElMessage('删除评论成功')
            getMyLike()
        }else{
            ElMessage('删除评论失败,请稍后再试')
        }
    }).catch(()=>{
        ElMessage('删除评论失败,请稍后再试')
    })
}
</script>
<style scoped>
.myLike_main{
    width: 100%;
    display: flex;
    flex-wrap: wrap;
    margin-left: 20px;
    min-height: 100px;
}
.myLike_card{
    width: 300px;
    margin: 10px;
    position: relative;
}
.myLikeCard_footerText{
    position: absolute;
    bottom: 10px;
   
    & span{
        margin-left: 15px;
    }
}
.pagination {
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    
    font-size: 20px; /* 页码的字体大小 */
    background-color: white;
  }
  .myLike_content{
    color: #808080;
    white-space:pre-line;    /* 属性设置如何处理元素内的空白 */
    word-wrap:break-word;   /* 属性用来标明是否允许浏览器在单词内进行断句，这是为了防止当一个字符串太长而找不到它的自然断句点时产生溢出现象。 */
    word-break: break-all;    /* 属性用来标明怎么样进行单词内的断句。 */
  }
  .delete-icon:hover{
    cursor: pointer;
  }
</style>