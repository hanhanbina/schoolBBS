<template>
<div class="body">
    <el-tabs
    v-model="activeName"
    type="card"
    class="tabs"
    @update:modelValue="handleClick"
  >
    <el-tab-pane :label="item.name" :name="item.id" v-for="(item,index) in categoryValue" :key="index" lazy>
        <!-- :key="$route.fullPath"阻止缓存，防止路由切换时数据不刷新 -->
        <router-view style="min-height: 80vh;" :key="$route.fullPath"></router-view>
    </el-tab-pane>

  </el-tabs>

</div>
</template>

<script lang="ts" setup>
import { onMounted,ref } from 'vue'
import request from '@/utils/request';
import { useRoute,useRouter } from 'vue-router';


onMounted(()=>{
    queryCategory()
    //路由参数为string需要转成number
   activeName.value=parseInt(route.params.id[0])
   
})
const route = useRoute();
const router = useRouter();



//默认激活的tab标签
let activeName = ref(1)


//接受分类信息的数组
const categoryValue = ref([] as any [])


const queryCategory = ()=>{
    request.get('/category').then((res:any)=>{
        if(res){
            categoryValue.value = res

        }
    })
}




//点击分类标签后
 const handleClick = async() => {
     router.push({name:'postlist',params:{id:activeName.value}})
}

</script>
<style scoped>
.body{
    position: absolute;
    top: 0;
   
}
/* 1标签卡整个div */
.tabs :deep(.el-tabs__header) {
    margin-bottom: 0px;
    height: 50px;
}
/* 2标签卡有标签的整体div */
.tabs :deep(.el-tabs__nav) {
    width: 100%;
}
/* 3标签卡有标签的div */
.tabs  :deep(.el-tabs__item) {
 
    width: 100%;
    height:50px;
    font-size: 18px;
    letter-spacing: 2px;
}
/* 标签内容div */
.tabs :deep(.el-tabs__content) {
    background-color: #f2f2f2;
}
</style>