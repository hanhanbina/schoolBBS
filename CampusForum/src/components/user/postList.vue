<template>
    <!-- infinite-scroll-immediate="false"禁用初始加载  infinite-scroll-delay="500"节流时延500ms -->
<div class="postListBody container-fluid" v-infinite-scroll="loading"  infinite-scroll-immediate="false" infinite-scroll-delay="500" infinite-scroll-distance="10">
    <div class="listCard " v-for="(item,index) in postlistData" :key="index" >
        <div v-if="item.isTop===1" style="position: absolute;left: 15px; color: #306fe2;" >▲</div>
        <!-- 帖子标题 -->
        <p class="one-line-text postTitle poninter" @click="goToPostContent(item.postId)"> {{ item.title }}</p>
        <!-- 帖子内容 -->
        <p class="two-line-text postContent poninter" @click="goToPostContent(item.postId)">{{ item.postContent }}</p>
        <!-- 点赞按钮 -->
        <button @click="clickUpvote(index,item.dianzan)" class="post_btn" style="margin-left: 20px;padding-top: 2px;" :class="item.dianzan===1 ? 'isBlue' :''" >
            <img src="/src/assets/image/赞同.svg"  style="width: 20px;height: 35px;margin: 0px 5px;margin-bottom: 4px;">
            <span  style="line-height: 35px;margin-right: 10px; color: #306fe2;">
                <span style="font-size: 17px;" >赞</span> {{ item.upvote }}
            </span>
            </button>
        <!-- 踩按钮 -->
        <button class="post_btn" style="margin-left: 5px;padding-bottom: 4px;" :class="isDown === index ? 'isBlue' :''" @click="clickDown(index)">
            <img src="/src/assets/image/踩.svg"  style="width: 23px;height: 35px;margin: 0px 5px;">
            <span style="line-height: 37px;margin-right: 10px; color: #306fe2;">踩</span>
            </button>
        <!-- 按钮列表 -->
        <div class="postbtnList">
            <span class="poninter"  @click="goToPostContent(item.postId)"> <img src="/src/assets/image/评论.svg" style="width: 27px;height: 30px;margin-right: 10px;">{{ item.count }}条评论</span>
            <span class="poninter" @click="collection(item.postId)"> <img src="/src/assets/image/收藏.svg" style="width: 30px;height: 30px;" > 收藏</span>
            <span class="poninter" @click="shareUrl(item.postId)"> <img src="/src/assets/image/分享.svg" style="width: 23px;height: 30px;"> 分享</span>
        </div>

    </div>
    <!-- 没有数据以后显示 -->
    <div class="listCard" v-if="isnull===true"  style="display: flex;justify-content: center;align-items: center;">
            <span class="poninter" style="color: #306fe2;" >
                没有帖子了哦~
                <!-- {{ isnull === true ? '没有帖子了哦~' : '加载更多' }} -->
            </span>
    </div>
    <!-- 右边热榜等 -->
    <div class="rightCard">
           <img class="hot_title" src="/src/assets/image/hot.png" >
           <ol class="hot_list ">
            <li @click="goPostFromHot(item.postId,item.categoryId)" class="ChinaAndEnglishFont poninter" v-for="(item,index) in hotListData" :key="index">{{ item.title }}</li>
           </ol>
           <links></links>
    </div>
   
</div>
</template>



<script lang="ts" setup >
import request from '@/utils/request';
import { ref,onMounted,createApp} from 'vue'
import { useRoute,useRouter } from 'vue-router';
import useClipboard from 'vue-clipboard3';
import links from '@/components/other/links.vue';


const route = useRoute();
const router = useRouter();
const app = createApp({});
app.component('links',links);

//挂载后
 onMounted(()=>{
    // postlistData.value.length=0;
    // hotListData.value.length=0;
    
    
    if(route.query.searchData != null &&route.query.searchData != undefined){
        searchPost(route.query.searchData)
        const id = parseInt(route.params.id[0])
        getHotList(id)
    }else{
        //设置定时器，防止获取到的路由参数还没改变就被获取
        setTimeout(()=>{
        const id = parseInt(route.params.id[0])
        getPostList(id)
        getHotList(id)
    },200)
    }
   
})


//判断请求的帖子数据是否为空
const isnull = ref(false)


//传入剪贴板的分享数据
const shareUrlData = ref('')
//结构出复制需要的方法
const { toClipboard } = useClipboard();
const shareUrl = async (id:any) =>{
    try{
    shareUrlData.value = "http://localhost:5173/post/"+route.params.id[0]+"?"+"id="+id
    await  toClipboard(shareUrlData.value)
    ElMessage({
        type:'success',
        message:'链接已复制,快分享给朋友吧~'
    })
    }catch(e){
        console.log('复制失败:'+e)
        ElMessage({
        type:'error',
        message:'复制失败'
    })
    }
}

//页数
let pageNum = ref(1);
//每页请求数量
let pageSize = ref(5);
//帖子标题列表数据
let postlistData = ref([] as any[]);

//热帖数据
const hotListData = ref([] as any [])

//赞的按钮点击
const isUpvote = ref(-1)
const clickUpvote = (index:number,isDianzan:number) =>{
  //点赞的数据
  let dianzanData = {postId:null,upvote:null}
    //用户与点赞帖子的关系数据
  let UserPostLike = {postId:null,userId:null}

    
    if(isUpvote.value === index||isDianzan===1){
        isUpvote.value=-1;
        postlistData.value[index].upvote=parseInt(postlistData.value[index].upvote)-1
        postlistData.value[index].dianzan = -1
        dianzanData.postId = postlistData.value[index].postId
        dianzanData.upvote = postlistData.value[index].upvote
        //将对应帖子的点赞数量减一
        request.post("/post",dianzanData).then((res:any)=>{
        if(res===true){
            //同时删除用户点赞关系表的数据
            request.delete("/userPostLike/"+dianzanData.postId+"/"+JSON.parse(localStorage.getItem("userInfo")).userId ).then((res)=>{
                if(res.code==='200'){
                    ElMessage("已取消点赞")
                    
                }else{
                    ElMessage("操作失败")
                }
                
            })
            
            return;
        }else{
            ElMessage("操作失败")
            return;
        }
    })
        return;
       }
    isUpvote.value = index
    postlistData.value[index].dianzan = 1
  
    UserPostLike.postId = postlistData.value[index].postId
    UserPostLike.userId = JSON.parse(localStorage.getItem('user')).id

    postlistData.value[index].upvote=parseInt(postlistData.value[index].upvote)+1
    dianzanData.postId = postlistData.value[index].postId
    dianzanData.upvote = postlistData.value[index].upvote
    request.post("/post",dianzanData).then((res:any)=>{
        if(res===true){
  
            request.post('/userPostLike',UserPostLike).then((res:any)=>{
                if(res.code==='200'){
                    ElMessage("点赞成功")
                }else{
                    ElMessage(res.message)
                }
            }).catch(()=>{
                alert('你已经点过赞了')
                postlistData.value[index].dianzan = 1
            })
            
        }else{
            console.log('点赞失败')
        }
    })
    
}
//踩的按钮点击
const isDown = ref(-1)
const clickDown = (index:number) =>{
    isDown.value = index
    
}

//请求帖子
const getPostList =(id:number)=>{
    console.log('请求帖子列表数据')
    request.get('post/page',{
        params:{
            pageNum:pageNum.value,
            pageSize:pageSize.value,
            cate_id:id
        }
    }).then((res)=>{
        // const guodu = []
        //循环把数据插入数组
        for(let i= 0;i<5;i++){
            //如果没有数据了返回，并让没有帖子的文字显示出来
            if(res.data.records[i] === null || res.data.records[i] === undefined){
                isnull.value = true
                return;
            }
            postlistData.value.push(res.data.records[i])
        }
              // 将所有置顶的帖子筛选出来
    const topPosts = postlistData.value.filter(post => post.isTop === 1);

    // 使用slice()创建数组副本，将置顶的帖子放在头部，再进行排序
    postlistData.value = topPosts.concat(postlistData.value.slice().filter(post => post.isTop === 0));
        // console.log("排序后")
        // console.log(postlistData.value)
    })
}

//根据分类id请求热帖
const getHotList =(id:number)=>{
    request.get('post/hot/'+id).then((res:any)=>{
        if(res===null){return console.log('热榜数据请求错误')}
        hotListData.value = res
    })
}


// 无限滚动加载，后端调用分页，页数加1
const loading = ()=>{
    pageNum.value += 1;
    if(route.query.searchData != null &&route.query.searchData != undefined){
        searchPost(route.query.searchData)
    }else{
        getPostList(parseInt(route.params.id[0]))
    }
    
}
//点击帖子跳转到对应帖子
const goToPostContent = (postId:number) =>{
    router.push({name:'postContent',params:{id:route.params.id[0]}, query:{id:postId} })
}
//搜索帖子
const searchPost = (value:string)=>{
    if(value != null&&value != ''){
        request.get('/post/searchPage',{params:{
            content:value,
            pageSize:pageSize.value,
            pageNum:pageNum.value
        }}).then((res)=>{
            for(let i= 0;i<5;i++){
            //如果没有数据了返回，并让没有帖子的文字显示出来
            if(res.data.records[i] === null || res.data.records[i] === undefined){
                isnull.value = true
                return;
            }
            postlistData.value.push(res.data.records[i])
        }
        }).catch(e=>{
            console.log("搜索帖子方法错误"+e)
        })
        console.log("搜索帖子方法触发")

    }else{
        console.log("搜索数据为空")
    }

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
    width: 65%;
    height: 20vh;
    min-height: 200px;
    border-bottom: 1px solid #e6e6e6;
    background-color: #fff;
    padding-top: 15px;
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
    background-color: #fff;
    display: flex;
    align-items: center;
    flex-direction: column;
    position: absolute;

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
    margin-top: 20px;
}
/* 赞同和踩按钮 */
.post_btn{
    background-color: #E6F0FD;
    border: none;
    min-height: 35px;
    border-radius: 5px;
    min-width: 60px;

    margin-top: 20px;

}
/* 帖子评论收藏等按钮 */
.postbtnList{
    float: right;
    margin-top: 25px;
    margin-right: 100px;
    display: flex;
    justify-content: space-around;
    width: 300px;
    color:#979797;
}
.poninter{
    cursor: pointer;
}
.poninter:hover{
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
.isBlue{
    background-color: #ffffff;
}
</style>