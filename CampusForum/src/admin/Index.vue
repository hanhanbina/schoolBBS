<template>
    <!-- 外部容器 -->
    <el-container >
      <!-- 左侧侧边栏 -->
      <el-aside width="200px"  style="height: 100vh;">
        <!-- 滚动条 -->
        <el-scrollbar>
          <!-- 默认打开的菜单 -->
          <el-menu router :default-openeds="['1','2','3','4']" default-active="/admin/userpwd">
            <!-- <el-menu-item index="/admin/overview">网站概况</el-menu-item> -->
            <!-- 用户管理 -->
            <el-sub-menu index="1">
              <template #title>
                用户管理
              </template>
              <el-menu-item index="/admin/userpwd">用户资料</el-menu-item>
            </el-sub-menu>
                          <!-- 帖子管理 -->
              <el-sub-menu index="2">
                <template #title>帖子管理</template>
                <el-menu-item index="/admin/post">发帖管理</el-menu-item>
                <el-menu-item index="/admin/replypost">评论管理</el-menu-item>
              </el-sub-menu>
  
            <!-- 管理员账号管理 -->
            <el-sub-menu index="3">
              <template #title>
                管理员账号
              </template>
              <el-menu-item index="/admin/admininfo">账号管理</el-menu-item>
            </el-sub-menu>
            <!-- 板块分类管理 -->
            <el-sub-menu index="4">
              <template #title>
                板块信息管理
              </template>
              <el-menu-item index="/admin/bankuai">板块分类</el-menu-item>
            </el-sub-menu>


          </el-menu>
        </el-scrollbar>
      </el-aside>
  
  
      <el-container>
        <!-- 页面头部 -->
        <el-header
          style="
            display: flex;
            justify-content: space-between;
            align-items: center;
            background-color: #3e4e55;
          "
        >
          <!-- 无意义用于填充 -->
          <div style="width: 20px"></div>
  
          <!-- 标题 -->
          <div style="color: #fff; font-size: 24px; font-weight: bold">后台管理系统</div>
  <el-dropdown>
          <div style="display: flex;justify-content: space-around;align-items: center;width: 150px;">
              <el-icon style="margin-right: 8px; margin-top: 1px; color: #fff;" ><setting /></el-icon>
              <img src="@/assets/image/moren.png" class="admimg">
            <span class="adname">{{ admin.name }}</span>
          </div>
          <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="goToIndex()">返回前台页面</el-dropdown-item>
                  <el-dropdown-item  @click="out()">
                    <span >退出账号</span></el-dropdown-item>
                </el-dropdown-menu>
              </template>
          
        </el-dropdown>
        </el-header>
  
        <!-- 中间内容 -->
        <el-main>

         <RouterView/>
        </el-main>
      </el-container>
  
    </el-container>
  </template>
  
  <script lang="ts" setup>
  import { useAdminStore } from '@/stores/admin';
  const store = useAdminStore();
  const admin  = JSON.parse(localStorage.getItem("admin")||'');
  if(admin.isLogin===true){
    console.log('含有登录信息');
   
  }else{
    alert('请先登录')
    window.location.href = "/admin"
  }

  
   function goToIndex(){
      window.location.href = "/"
   }
  function out(){
    store.logout()
    window.location.href = "/admin"
  }
  
  
  
  </script>
  
  <style scoped>
  .admimg{
    width: 40px;
    height: 40px;
    border-radius: 30%;
  }
  .adname{
    color: #fff;
    text-align: center;
  
    
  }
  </style>
  