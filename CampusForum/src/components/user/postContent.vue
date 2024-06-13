<template>
  
<div class="postListBody container-fluid" >
    <div class="listCard">
        <!-- 发帖用户 -->
        <div class="post_user">
            <el-avatar style="margin-left: 20px;" shape="square" :size="40" :src="postData.headimg"></el-avatar>
            <div style="margin-left: 10px;">
                <p style="font-weight: 800;">{{ postData.userName }}</p>
                <p>{{ postData.profile }}</p>
            </div>
            
        </div>

        <!-- 帖子标题 -->
        <p class="one-line-text postTitle"> {{ postData.title }}</p>
        <!-- 帖子内容 -->
        <p class=" postContent">
            <v-md-editor  :model-value="postData.postContent" mode="preview"></v-md-editor>
        </p>

       
        <div style="margin-left: 20px;color: #808080;margin-top: 10px;">发布于：{{ postData.datetime}}</div>
        <!-- 点赞按钮 -->
        <button @click="clickUpvote()" class="post_btn" style="margin-left: 20px;padding-top: 2px;" :class="postIsUp=== 1 ? 'btnIsUpvote' : ''">
            <img src="/src/assets/image/赞同.svg"  style="width: 20px;height: 35px;margin: 0px 5px;margin-bottom: 4px;">
            <span style="line-height: 35px;margin-right: 10px; color: #306fe2;">
                <span style="font-size: 17px;" >赞</span> {{ postData.upvote }}
            </span>
            </button>
        <!-- 踩按钮 -->
        <button class="post_btn" style="margin-left: 15px;padding-bottom: 4px;">
            <img src="/src/assets/image/踩.svg"  style="width: 23px;height: 35px;margin: 0px 5px;">
            <span style="line-height: 37px;margin-right: 10px; color: #306fe2;">踩</span>
            </button>
        <!-- 按钮列表 -->
        <div class="postbtnList">
            <span > <img src="/src/assets/image/评论.svg" style="width: 27px;height: 30px;margin-right: 10px;">{{ postData.count }} 条评论</span>
            <span class="poninter" @click="collection(postData.postId)"> <img src="/src/assets/image/收藏.svg" style="width: 30px;height: 30px;"> 收藏</span>
            <span @click="shareUrl()" class="poninter"> <img src="/src/assets/image/分享.svg" style="width: 23px;height: 30px;" > 分享</span>
        </div>

    </div>
    <!-- 发布评论编辑器 -->
    <div class="edit_user">
        <v-md-editor v-model="edit_text"  height="250px" mode="edit" placeholder="在这里输入你的评论~"></v-md-editor>
        <el-button @click="sendReply_1()" type="primary"  style="float: right;height: 40px;margin: 10px;">发布</el-button>
    </div>

    <!-- ==========================评论============================= -->
    <div class="replyBody"  v-for="(item,index) in replyData" :key="index" v-if="postData.count!=0">
        <!-- 评论用户 -->
        <div class="post_user">
            <el-avatar style="margin-left: 20px;" shape="square" :size="40" :src="item.headImg"></el-avatar>
            <div style="margin-left: 10px;">
                <p style="font-weight: 800;">{{ item.userName }}</p>
                <p>{{ item.prrofile }}</p>
            </div>
            
        </div>
        <!-- 评论内容 -->
        <p class=" postContent">{{ item.replyContent }}</p>
        <div style="margin-left: 20px;color: #808080;margin-top: 10px;">发布于：{{ postData.datetime}}</div>
        <!-- 评论点赞按钮 -->
        <button  @click="zanPingLun(item.replyId)" class="post_btn" style="margin-left: 20px;padding-top: 2px;">
            <img src="/src/assets/image/赞同.svg"  style="width: 20px;height: 35px;margin: 0px 5px;margin-bottom: 4px;">
            <span style="line-height: 35px;margin-right: 10px; color: #306fe2;">
                <span style="font-size: 17px;" >赞</span> {{ item.upvote }}
            </span>
            </button>
        <!-- 踩按钮 -->
        <button class="post_btn" style="margin-left: 15px;padding-bottom: 4px;">
            <img src="/src/assets/image/踩.svg"  style="width: 23px;height: 35px;margin: 0px 5px;">
            <span style="line-height: 37px;margin-right: 10px; color: #306fe2;">踩</span>
            </button>
        <!-- 按钮列表 -->
        <div class="postbtnList">
            <span > <img src="/src/assets/image/评论.svg" style="width: 27px;height: 30px;margin-right: 10px;">{{ item.children.length }} 条评论</span>
            <!-- <span class="poninter"> <img src="/src/assets/image/收藏.svg" style="width: 30px;height: 30px;"> 收藏</span>
            <span class="poninter"> <img src="/src/assets/image/分享.svg" style="width: 23px;height: 30px;"> 分享</span> -->
        </div>
                    <!-- 评论输入框 -->
                <div class="duoj_reply_ipt">
                <el-avatar style="" shape="square" :size="45" :src="currentUserHeadImg">a</el-avatar>
                <input v-model="duoj_inpt1" class="dupj_reply_ipt_ipt" @focus="isInput=true"  type="text"> 
                <div style="width: 780px;margin-left: 25px;margin-top: 8px;">
                    <el-button @click="sendReply_2(item.replyId,item.replyId)" type="primary"  v-if="isInput===true" class="duoj_reply_btn" >发布</el-button>
                </div>
            </div>
        <!-- 多级评论 -->
        <div class="duoj_reply" v-if="item.children.length != 0">
            
            <!-- ==================多级评论的评论====================== -->
            <div class="duoj_reply_reply">
                <div style="height: auto;" v-for="(subItem,index2) in (item.children as any)" :key="subItem.replyId">
                   <div class="duoj_reply_reply_userDiv">  
                        <el-avatar style="margin: 10px;" shape="square" :size="30" :src="subItem.headImg"></el-avatar> 
                        <span>{{subItem.userName}}</span>
                        <span style="color: #808080;margin-left: 70%;">回复给:{{ subItem.pnickName }}</span> 
                    </div>
                <p class="duoj_reply_reply_replycontent">{{subItem.replyContent}}</p>
               <p style="margin-left: 10px;color: #808080;">{{ subItem.datetime }}</p>
                    <div>
          <!-- 点赞按钮 -->
        <button  @click="replyUpvote(subItem.replyId,index,index2)" class="post_btn" style="margin-left: 10px;font-size: 15px;height: 30px;width: auto;">
            <img src="/src/assets/image/赞同.svg"  style="width: 20px;height: 35px;margin: 0px 5px;margin-bottom: 4px;">
            <span style="line-height: 35px;margin-right: 10px; color: #306fe2;">
                <span  >赞</span> {{ subItem.upvote }}
            </span>
            </button>
        <!-- 踩按钮 -->
        <button class="post_btn" style="margin-left: 15px;font-size: 15px;height: 30px;">
            <img src="/src/assets/image/踩.svg"  style="width: 23px;height: 35px;margin: 0px 5px;">
            <span style="line-height: 30px;margin-right: 10px; color: #306fe2;">踩</span>
            </button>
            <el-button @click="replyClick(subItem.replyId)" style="margin-left: 20px;">回复</el-button>

                    </div>
                        <!-- 评论输入框 -->
                        <div v-if="replyid===subItem.replyId"  class="duoj_reply_ipt" style="margin-left: 10px;margin-top: 10px;">
                <el-avatar style="" shape="square" :size="30" :src="currentUserHeadImg"></el-avatar>
                <input class="dupj_reply_ipt_ipt" @focus="isInput_reply=true"  v-model="duoj_inpt2"   type="text" style="height: 30px;"> 
                <div style="width: 780px;margin-left: 25px;margin-top: 8px;">
                    <el-button @click="sendReply_2(subItem.originId,subItem.replyId)"  type="primary"  v-if="isInput_reply===true" class="duoj_reply_btn" style="height: 30px;width: 50px;margin-right: 10px;">发布</el-button>
                    <!-- <button  class="duoj_reply_btn" >发布</button> -->
                </div>
            </div>        
                
                    <div style="margin-bottom: 10px;margin-top: 10px;" ></div>
                </div>
                
            </div>

        </div>
    </div>

    <!-- 右边热榜等 -->
    <div class="rightCard">
        <img class="hot_title" src="/src/assets/image/hot.png" >
           <ol class="hot_list ">
            <li @click="goPostFromHot(item.postId,item.categoryId)" class="dhot ChinaAndEnglishFont" v-for="(item,index) in hotListData" :key="index" style="cursor: pointer;">{{ item.title }}</li>
           </ol>
           <links></links>
          
    </div>
   
</div>
</template>



<script lang="ts" setup >
import request from '@/utils/request';
import { onMounted ,ref,createApp } from 'vue'
import { useRoute,useRouter } from 'vue-router'
import useClipboard from 'vue-clipboard3';
import links from '@/components/other/links.vue';

onMounted( () =>{
    const id = parseInt(route.params.id[0])
    getPostData()
    getReplyData()
    getHotList(id)
} )

const app = createApp({});
app.component('links',links);
const route = useRoute();
const router = useRouter();
//当前路由带的帖子id
const postId = route.query.id;
//localStorage用户数据
const currentUser = JSON.parse(localStorage.getItem('userInfo'));
//localStorage里的当前用户头像
 const currentUserHeadImg = currentUser.headImg;
 //热帖数据
const hotListData = ref([] as any [])
 //判断多级评论框是否聚焦
 const isInput = ref(false);
 //判断多级评论的评论输入框是否聚焦
 const isInput_reply = ref(false)
//帖子数据
const postData = ref({postId:null,title:"",postContent:"",userId:null,datetime:"",upvote:null,viewCount:null,replyCount:null,isTop:null,categoryId:null,userName:"",headimg:"",profile:"",count:null});
//评论数据
const replyData = ref([{replyId: null,postId: null,userId: null,replyConten: "",datetime: "",upvote: "",originId: null,pid: null,userName: "",headImg: "",children: [],puserId: null, pnickName: null}]);
//根据路由上的帖子id请求帖子信息
const getPostData =()=>{
    request.get('/post/'+postId).then((res:any) => {
        if(res){
            postData.value = res
        }else{
            return console.log('帖子数据请求失败');
        }
        
    })
}
//根据帖子id请求评论
const getReplyData=()=>{
    request.get('/replyPost/tree/'+postId).then((res:any) => {
        if(res.code==='200'){
            replyData.value = res.data
        }else{
            return console.log('评论数据请求失败');
        }
        
    })
}



const replyid = ref(-1)
const replyClick = (index:number) =>{
    //点击两次关闭回复
   if(replyid.value===index){replyid.value= -1 ;return;}
   else{
    //打开回复
    replyid.value = index
   }
   
}

//发布第一层评论
const edit_text = ref('')
const replyValues =ref({        
        replyId: null,
        postId: null,
        userId: null,
        replyContent: '',
        originId: null,
        pid: null});
const sendReply_1 = ()=>{
    if(edit_text.value===''||edit_text.value===null){
        ElMessage({
            type:'error',
            message:'内容不能为空'
        })
        return;
    }
    replyValues.value.replyContent  = edit_text.value;
    replyValues.value.postId = postData.value.postId;
    replyValues.value.userId = currentUser.userId;
    request.post('/replyPost',replyValues.value).then((res:any)=>{
        if(res.code ==='200'){
            alert('发送成功')
            replyValues.value ={replyId: null,postId: null,userId: null,replyContent: '',originId: null,pid: null};
            edit_text.value=''
            getReplyData()
        }else{
            alert('发送失败')
            replyValues.value ={replyId: null,postId: null,userId: null,replyContent: '',originId: null,pid: null};
        }
    })

}
const duoj_inpt1 = ref('')
const duoj_inpt2 = ref('')
//发布第二层评论
const sendReply_2 = (Oid:number,Pid:number)=>{
    replyValues.value.originId = Oid;
    replyValues.value.pid = Pid;
    if(Oid===Pid){   replyValues.value.replyContent = duoj_inpt1.value;}else{replyValues.value.replyContent = duoj_inpt2.value}
 
    replyValues.value.postId = postData.value.postId;
    replyValues.value.userId = currentUser.userId;
    request.post('/replyPost',replyValues.value).then((res:any)=>{
        if(res.code ==='200'){
            alert('发送成功')
            replyValues.value ={replyId: null,postId: null,userId: null,replyContent: '',originId: null,pid: null};
            duoj_inpt1 .value =''
            duoj_inpt2.value=''
            getReplyData()
        }else{
            alert('发送失败')
            replyValues.value ={replyId: null,postId: null,userId: null,replyContent: '',originId: null,pid: null};
            duoj_inpt1 .value =''
            duoj_inpt2.value=''
        }
    })
}


//传入剪贴板的分享数据
const shareUrlData = ref('')
//结构出复制需要的方法
const { toClipboard } = useClipboard();
const shareUrl = async () =>{
    shareUrlData.value = 'http://localhost:5173/'+route.fullPath
   await  toClipboard(shareUrlData.value)
   ElMessage({
        type:'success',
        message:'链接已复制,快分享给朋友吧~'
    })
    console.log(shareUrlData.value)
    
    }

//给帖子点赞
const postIsUp = ref(-1)
const clickUpvote = async () =>{
    if(postIsUp.value===1){
        ElMessage("你已经点过赞了")
        return;
    }
  //点赞的数据
  let dianzanData = {postId:null,upvote:null}
    //用户与点赞帖子的关系数据
  let UserPostLike = {postId:null,userId:null}

  
    UserPostLike.postId = postData.value.postId
    UserPostLike.userId = JSON.parse(localStorage.getItem('user')).id

    postData.value.upvote=parseInt(postData.value.upvote)+1
    dianzanData.postId = postData.value.postId
    dianzanData.upvote = postData.value.upvote
   await request.post("/post",dianzanData).then((res:any)=>{
        if(res===true){
  
           request.post('/userPostLike',UserPostLike).then((res:any)=>{
                if(res.code==='200'){
                    ElMessage('点赞成功')
                    postIsUp.value = 1
                    return;
                }else{
                    ElMessage('点赞失败'+res.message)
                    postIsUp.value = 1
                    postData.value.upvote=parseInt(postData.value.upvote)-1
                    return;
                }
            })
            
        }else{
            console.log('点赞失败')
        }
    })
    
}
//给评论点赞
const isUp = ref(-1)
const replyUpvote = async (replyid,index,index2)=>{
    if(isUp.value===1){
        ElMessage('不要重复点赞')
        return;
    }
    replyData.value[index].children[index2].upvote= parseInt(replyData.value[index].children[index2].upvote)+1
     //回复的点赞的数据
  let ReplydianzanData = {replyId:null,upvote:null}
  ReplydianzanData.replyId =  replyid
  ReplydianzanData.upvote = replyData.value[index].children[index2].upvote

  await request.post("/replyPost",ReplydianzanData).then((res:any)=>{
        if(res.code==='200'){
            ElMessage('点赞成功')
            isUp.value=1
        }else{
            ElMessage('点赞失败'+res.message)
        }
    }).catch(()=>{
        alert('你之前已经点过赞了')
    })

    setTimeout(()=>{isUp.value=-1},2000)
}

//根据分类id请求热帖
const getHotList =(id:number)=>{
    request.get('post/hot/'+id).then((res:any)=>{
        if(res===null){return console.log('热榜数据请求错误')}
        hotListData.value = res
    })
}
const collectionObject = ref({postId:null,userId:null}) 
//收藏按钮方法
const collection = (Pid) =>{
    const Uid = JSON.parse(localStorage.getItem("userInfo")).userId
    collectionObject.value.postId = Pid;
    collectionObject.value.userId = Uid;
    request.post("/favoritesUser",collectionObject.value).then((res)=>{
        if(res.code==='200'){
            ElMessage({
                type:'success',
                message:'收藏成功'
            })
        }else{
            ElMessage({
                type:'error',
                message:res.message
            })
        }
    }).catch(e=>{
        console.log(e+"=====收藏失败")
    })
}

// 点击热榜帖子跳转
const goPostFromHot= (postId,Cid)=>{
    router.push("/post/"+Cid+"?id="+postId);
}

//评论点赞
const zanPingLun = (Rid:number)=>{
    //增加点数数量
    request.post("/replyPost/zan/"+Rid).then((res)=>{
        if(res.code==='200'){
            const userReplyLike = {replyId:null,userId:null}
            userReplyLike.replyId = Rid;
            userReplyLike.userId = JSON.parse(localStorage.getItem("userInfo")).userId;
            request.post("/userReplyLike",userReplyLike).then((res)=>{
                if(res.code==='200'){
                    console.log("回复点赞用户关系表添加成功")
                }else{
                    ElMessage("你已经点过赞了")
                    return;
                }
            })

            ElMessage("点赞成功")
            getReplyData()
            return;
        }
        ElMessage("点赞失败")
    })
}



</script>
<style scoped>
/* 组件整体的body */
.postListBody{
    position: relative;
    min-width: 1300px;
    width: 100%;
    display: flex;
    flex-direction: column;
    height: auto;
    margin-left: -10px;
 
   
}
/* 每个帖子的div */
.listCard{
    min-width: 850px;
    max-width: 65%;
    width: 65%;
    /* height: 33vh; */
    min-height: 280px;
    border-bottom: 1px solid #e6e6e6;
    background-color: #fff;
    padding-top: 10px;
}
/* 一行 */
.one-line-text {  
  white-space: nowrap; /* 确保所有字符都在一行 */  
  overflow: hidden;    /* 隐藏超出元素框的内容 */  
  text-overflow: ellipsis; /* 当文本溢出时显示省略号 */  
  width: 800px; /* 你需要指定一个宽度，这样超出这个宽度的文本才会被隐藏 */  
}
/* 两行 */
.two-line-text {  
  white-space: wrap; /* 确保所有字符都在一行 */  
  overflow: hidden;    /* 隐藏超出元素框的内容 */  
  text-overflow: ellipsis; /* 当文本溢出时显示省略号 */  
  width: 800px; /* 你需要指定一个宽度，这样超出这个宽度的文本才会被隐藏 */  
  display: -webkit-box; /* 启用CSS的弹性盒子模型，这是实现省略号的必要步骤 */  
  -webkit-line-clamp: 2; /* 限制显示的行数，这里设置为2行 */  
  -webkit-box-orient: vertical; /* 将弹性盒子模型的方向设置为垂直 */  
  display: -webkit-inline-box; /* 将元素设置为内联元素，这是实现省略号的必要步骤 */  
}
/* 右边热榜 */
.rightCard{
    min-width: 445px;
    width: 445px;
    min-height: 850px;
    height: 85vh;
    left: 66%;
    margin-left: 10px;
    position: absolute;
    background-color: #fff;
    display: flex;
    align-items: center;
    flex-direction: column;
 

}
/* 帖子标题 */
.postTitle{
    font-size: 22px;
    font-weight:bold;
    margin-left: 20px;
}
/* 帖子内容 */
.postContent{
    font-size: 18px;
    margin-left: 20px;
    margin-right: 20px;
    margin-top: 20px;
    white-space:pre-line;    /* 属性设置如何处理元素内的空白 */
    word-wrap:break-word;   /* 属性用来标明是否允许浏览器在单词内进行断句，这是为了防止当一个字符串太长而找不到它的自然断句点时产生溢出现象。 */
    word-break: break-all;    /* 属性用来标明怎么样进行单词内的断句。 */
}
/* 赞同和踩按钮 */
.post_btn{
    background-color: #E6F0FD;
    border: none;
    min-height: 35px;
    border-radius: 5px;
    min-width: 60px;
    margin-top: 10px;

}
/* 帖子评论收藏等按钮 */
.postbtnList{
    float: right;
    margin-top: 20px;
    margin-right: 100px;
    display: flex;
    justify-content: space-around;
    width: 300px;
    color:#979797;
    margin-bottom: 10px;
}
.poninter{
    cursor: pointer;
}
.poninter:hover{
    color:#306fe2;
}
.dhot:hover{
    color:#306fe2;
}
/* 热榜文字格式 */
.hot_title{
    width: 100px;
    margin-top: 10px;
    margin-bottom: 10px;
}
/* 热榜列表 */
.hot_list{
    margin-top: 10px;
    font-size: 18px;
    min-height: 500px;
    height: 500px;
    /* display: flex;
    flex-direction: column;
    justify-content: flex-start; */
    color: #4f4f4f;


}
.hot_list li{
    margin-left: -30px;
    min-width: 400px;
    width: 400px;
    /* 限制文本只有一行，超出隐藏并加省略号 */
    white-space: nowrap; 
    overflow: hidden;    
  text-overflow: ellipsis; 
  /* overflow: hidden; 会将有序列表的数字弄没需要加上这个属性才能显示 */
  list-style-position: inside;
  margin-bottom: 23px;
}
.post_user{
  margin-bottom: 10px;
    min-height: 50px;
    display: flex;
    align-items: center;
}
/* 帖子的评论编辑器 */
.edit_user{
    background-color: #fff;
    margin-top: 10px;
    min-width: 850px;
    max-width: 850px;
    width: 850px;
    border-radius: 5px;
    min-height: 250px;
    
}
/* 评论 */
.replyBody{
    min-width: 850px;
    max-width: 65%;
    width: 65%;
    min-height: 210px;
    border-bottom: 1px solid #e6e6e6;
    background-color: #fff;
    padding-top: 10px;
    margin-top: 10px;
    margin-bottom: 0px;
}
/* 多级评论 */
.duoj_reply{
    margin: 20px;
    min-height: 100px;
   
    border-radius: 5px;

}
/* 多级评论区域 */
.duoj_reply_ipt{
    display: flex;
    flex-wrap: wrap;
    margin-left: 20px;
    margin-top: 20px
}
/* 多级评论的评论输入框 */
.dupj_reply_ipt_ipt{
    height: 45px;
    width: 753px;
    margin-left: 10px;
    border: 1px solid #dadad5;
    border-radius: 5px;
    
}
.dupj_reply_ipt_ipt:focus{
    outline: 1px solid #aaaaaa;
}
/* 多级评论的发布按钮 */
.duoj_reply_btn{
    float: right;
    width: 60px;
    height: 36px;
    margin-bottom: 5px;
}
/* 多级评论的评论输入框下面的部分 */
.duoj_reply_reply{
    min-height: 100px;
    border: #dadada solid 1px;
    border-radius: 5px;
}
/* 多级评论的评论用户的部分 */
.duoj_reply_reply_userDiv{
    min-height: 50px;
    display: flex;
    align-items: center;
}
/* 多级评论的评论的评论内容的部分 */
.duoj_reply_reply_replycontent{
    margin-left: 10px;
    margin-bottom: 10px;
    margin-right: 10px;
    white-space:pre-line;    /* 属性设置如何处理元素内的空白 */
    word-wrap:break-word;   /* 属性用来标明是否允许浏览器在单词内进行断句，这是为了防止当一个字符串太长而找不到它的自然断句点时产生溢出现象。 */
    word-break: break-all;    /* 属性用来标明怎么样进行单词内的断句。 */
}
.btnIsUpvote{
    background-color: #8daef1;
}
</style>