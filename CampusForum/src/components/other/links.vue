<template>
<div class="link_body">
    <p style="margin-top: 20px;color: #575757;">友情链接</p>
    <div class="links_a">
        <a href="http://120.35.34.241:8443/jwglxt/xtgl/login_slogin.html">教务系统</a>
        <a href="https://www.chsi.com.cn/">学信网</a>
        <a href="https://www.cnki.net/">知网</a>
        <a href="https://www.chaoxing.com/">超星学习通</a>
        <a href="https://www.ncss.cn/">大学生就业服务平台</a>
        <a href="https://cet-kw.neea.edu.cn/">全国大学英语四六级</a>
    </div>
   


</div>
</template>

<script lang="ts" setup>

</script>
<style scoped>
.link_body{
    background-color: #f2f2f2;
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;

    
    
}
.links_a{

    min-width: 450px;
    text-wrap: wrap;
    min-height: 100px;
    padding-top: 20px;
    margin-top: -15px;

}
a{
    text-decoration: none;
    color: #808080;
    margin-left: 50px;
    line-height: 50px;
}
a:hover{
    color: #0080ff;
}
</style>