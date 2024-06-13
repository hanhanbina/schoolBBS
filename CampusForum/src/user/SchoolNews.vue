<template>
    <el-config-provider :locale="locale">
<div>
        <img src="@/assets/image/fzlg.jpg" class="backimg">

    <div class="calendar">
        <el-calendar style="width: 480px;">
    <template #date-cell="{ data }">
      <p :class="data.isSelected ? 'is-selected' : ''">
        {{ data.day.split('-').slice(1).join('-') }}
      </p>
    </template>
  </el-calendar>

    <pre class="text">
        <h3 style="margin: 0;margin-left: 44%;">校历安排</h3>
                                                <strong>第一学期</strong>
        第一学期:2022年8月29日至2023年1月10日，共20周。
                8月26日补考的学生返校，8月28日其他学生返校注册。
                8月27-28日补考。8月29日学生正式上课
                9月3、4日2022级新生报到注册，9月5-18日新生体检、军训及入学教育 。
                9月5日专升本新生正式上课、9月19日2022级新生正式上课。
                11月4-5日校运动会。 
                9月10日中秋节，10月1日国庆节，2023年1月1日元旦为国家法定假日，
                2023年1月22日春节，具体安排按国家有关通知执行。
                本学期第19、20周为考试周。
                2023年1月11日至2月12日为寒假。 

                                                 <strong>第二学期</strong>
        第二学期:2023年2月13日至2023年7月2日，共20周。 
                2月11、12日学生返校，2月13日正式上课，补考学生2月10日返校，2月11-12 日补考。 
                2月5日元宵节，4月5日清明节，5月1日劳动节，6月22日端午节为国家法定假日，具体
                安排按国家有关通知执行。 
                本学期第20周为考试周。 2023年7月3日至8月31日为暑假。
    </pre>


    </div>
    <div>
        <h3 style="margin-left: 45%;margin-bottom: 20px;">校车时刻表</h3>
        <img src="@/assets/image/001.png" style="height: 550px;">
        <img src="@/assets/image/003.jpg" style="height: 400px;">
        <img src="@/assets/image/002.jpg" style="height: 550px;">
        
    </div>
</div>
</el-config-provider>
</template>

<script lang="ts" setup>
import { ElConfigProvider } from 'element-plus'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'

const locale = zhCn
</script>
<style scoped>
.backimg{
    width: 100%;
    border-radius: 10px;
}
:deep(.el-calendar-day){
    margin-top: 10px;
    height: 50px;
}
.calendar{
    display: flex;
    width: 100%;
    border-radius: 5px;
    margin-left: 20px;
}
.text{
    width:800px;
    margin-left: 20px;
}
</style>