<template>
<div class="o_body">
    <div id="UserEChart" class="echart"></div>
    <div id="PostEChart" class="echart"></div>
    <div id="ReplyEChart" class="echart"></div>
</div>
</template>

<script lang="ts" setup>

import { onMounted,ref } from 'vue'
import * as echarts from 'echarts';
import request from '@/utils/request';

onMounted(()=>{
 
      getData()
})


const  getRenderer=()=> {
    //初始化用户饼图
    const userChart = echarts.init(document.getElementById('UserEChart'));  
    //初始化帖子饼图
    const postChart = echarts.init(document.getElementById('PostEChart')); 
    //初始化回复饼图
    const replyChart = echarts.init(document.getElementById('ReplyEChart')); 


    //配置用户饼图
    userChart.setOption({  
    title: { text: webData.value.userTotal+"人" ,left:'center',top:'top'}, 
    legend: {
    orient: 'vertical',
    right: 10,
    top: 'top',
    left:'left'
  }, 
    series: [
    {
      type: 'pie',
      data: [
        {
          value: webData.value.man,
          name: '男性用户'
        },
        {
          value: webData.value.women,
          name: '女性用户'
        }
      ],
      radius: ['20%', '50%']
    }
  ]
  });  


   //配置帖子饼图
   postChart.setOption({  
    title: { text: webData.value.postTotal ,left:'center',top:'top'}, 
    legend: {
    orient: 'vertical',
    right: 10,
    top: 'top',
    left:'left'
  }, 
    series: [
    {
      type: 'pie',
      data: [
        {
          value: webData.value.postTotal,
          name: '帖子总数'
        }
      ],
      radius: ['20%', '50%']
    }
  ]
  });  


   //配置回复饼图
   replyChart.setOption({  
    title: { text: webData.value.replyTotal ,left:'center',top:'top'}, 
    legend: {
    orient: 'vertical',
    right: 10,
    top: 'top',
    left:'left'
  }, 
    series: [
    {
      type: 'pie',
      data: [
        {
          value: webData.value.replyTotal,
          name: '回复总数'
        }
      ],
      radius: ['20%', '50%']
    }
  ]
  });  


}

//请求数据
const webData = ref({})
 const getData =async ()=>{
    request.get("/admin/web").then((res) => {
          if(res.code==='200'){
               webData.value = res.data
                console.log("网站概况数据请求成功")
                getRenderer()
            }else{
                console.log("网站概况数据请求失败")
            }
    })
   
}

</script>
<style scoped>
.echart{
    width:500px;
    height: 500px;
}
.o_body{
    width: 100%;
    min-width: 1400px;
    height: auto;
    display: flex;
  
}
</style>