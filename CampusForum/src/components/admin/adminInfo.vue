<template>
    <el-config-provider :locale="locale">
      <!-- 面包屑 -->
      <div style="height: 50px">
        <el-breadcrumb separator="/" style="font-size: 18px">
          <el-breadcrumb-item >管理员账户</el-breadcrumb-item>
          <el-breadcrumb-item>管理员管理</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
  
      <!-- 搜索框 -->
      <div style="display: flex; align-items: center; margin-bottom: 15px">
        <!-- <el-input v-model="userId" placeholder="管理员id" size="large" class="elinput" />
        <el-button plain size="large" @click="toSelect">搜索</el-button>
        <el-button size="large" type="primary" @click="restSelect">重置</el-button> -->
        <el-button plain type="primary" size="large" @click="AddAdminDia=true">添加管理员</el-button>
      </div>
      <!-- 表格 -->
      <el-table :data="tableData" style="width: 100%" :table-layout="'auto'" class="etable">
        <el-table-column prop="id" label="管理员id" fixed="left" />
        <el-table-column prop="account" label="管理员账号"  />   
        <el-table-column prop="name" label="管理员昵称" />
        <el-table-column prop="password" label="管理员密码" />


        <!-- 操作按钮 -->
        <el-table-column fixed="right" label="操作">
          <template #default="scope">
            <el-button
              link
              type="danger"
              size="default"
              style="font-size: 18px"
              @click="showDel(scope.row)"
              >删除</el-button
            >

          </template>
        </el-table-column>


      </el-table>
  
      <!-- 分页器 -->
      <div style="width: 100%; display: flex; height: 100px; justify-content: center">
        <el-pagination
          class="pagination"
          :current-page="pageNum"
          :page-size="pageSize"
          :disabled="false"
          :small="false"
          :background="false"
          :page-sizes="[10, 20, 30, 40]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
  
  
  
      <!-- 删除对话框 -->
      <el-dialog
        v-model="dialogDelVisible"
        title="确认删除用户吗？"
        width="30%"
        style="
          height: 300px;
          display: flex;
          justify-content: center;
          align-items: center;
          flex-direction: column;
        "
      >
        <el-button size="large" type="danger" style="margin-right: 40px" @click="deleteUser"
          >确定</el-button
        >
        <el-button size="large" @click="dialogDelVisible = false">取消</el-button>
      </el-dialog>

    
      <el-dialog
        v-model="postContentExpanded"
        title="帖子完整内容"
        width="1000px"
        style="
          height: auto;
          display: flex;
          justify-content: center;
          align-items: center;
          flex-direction: column;
        "
      >
        <el-text>{{ replyContent }}</el-text>
    
    </el-dialog>
    <!-- 新增管理员信息 -->
    <el-dialog
        v-model="AddAdminDia"
        title="添加管理员"
        width="1000px"
        style="
          height: 500px;
          display: flex;
          justify-content: center;
          align-items: center;
          flex-direction: column;
        "
      >
        <el-input class="addipt" v-model="AdminObject.account" placeholder="请输入账号"/>
        <el-input class="addipt" v-model="AdminObject.password" placeholder="请输入密码"/>
        <el-input class="addipt" v-model="AdminObject.name" placeholder="请输入昵称"/>
      <el-button @click="addAdminFun()">提交</el-button>
      <el-button @click="AddAdminDia=false">取消</el-button>
    </el-dialog>


    </el-config-provider>
  </template>
  
  <script lang="ts">
  import request from '@/utils/request'
  import { zhCn } from 'element-plus/es/locale/index.mjs'
  
  
  export default {
    data() {
      return {
        // 语言包
        locale: zhCn,
        //表格数据
        tableData: [],
        //总页数
        total: 0,
        //当前？页
        pageNum: 1,
        //共展示？页
        pageSize: 20,
        //输入框绑定的
        postId:'',
        userId:'',
        //删除回复时暂存回复id
        delRepId:'',
        //打开删除对话框
        dialogDelVisible: false,
        // 用于记录帖子内容是否被点击展开
        postContentExpanded: false, 
        replyContent:'',
        //打开新增管理员对话框
        AddAdminDia:false,
        //新增管理员信息
        AdminObject:{
          account:'',
          password:'',
          name:''
        }
      }
    },
    created() {
      this.toSelect()
    },
    methods: {
      // 查询管理员数据
      toSelect() {
        request
          .get('/admin/page', {
            params: {
              pageNum: this.pageNum,
              pageSize: this.pageSize,
              postId :this.postId,
              userId :this.userId
            }
          })
          .then((res:any) => {
            this.tableData = res.records
            this.total = res.total
          })
      },
      handleCurrentChange(pageNum: number) {
        this.pageNum = pageNum
        this.toSelect()
      },
  
      handleSizeChange(pageSize: number) {
        this.pageSize = pageSize
        this.toSelect()
      },

      //删除
      deleteUser() {
        request.delete('/admin/' + this.delRepId).then((res) => {
          if (res) {
            alert('删除成功')
            this.dialogDelVisible = false
            // 强制刷新
            location.reload()
            this.$router.go(0)
          } else {
            alert('删除失败')
            this.dialogDelVisible = false
          }
        })
      },
      //展示删除二次确认对话框
      showDel(row) {
        this.delRepId = row.id
        this.dialogDelVisible = !this.dialogDelVisible
      },
            //重置
    restSelect(){
      this.postId=''
      this.userId= ''
      this.toSelect()
    },
    addAdminFun(){
      request.post("/admin",this.AdminObject).then((res)=>{
          if(res){
            ElMessage("添加成功")
            this.AddAdminDia = false
            this.AdminObject = {}
            this.toSelect()
          }else{
            ElMessage("添加失败")
          }
      })
    }
  
  },
    }
  
  </script>
  
  <style scoped>
  .etable :deep(.el-table__row) {
   
   
   
  }
  
  
  /* 表格每行内容的样式 */
  .etable :deep(.cell){
  font-size: 14px;
  max-height: 50px !important;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  }
  
  /* 分页器样式 */
  .pagination {
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 20px; /* 页码的字体大小 */
  }
  /* 共?条 */
  :deep(.el-pagination__total) {
    margin-right: 50px;
  }
  
  /* ?条/页 */
  :deep(.el-input:first-child) {
    margin: 0 10px;
    font-size: 18px;
  }
  /* 前往?页 */
  :deep(.el-pagination__jump) {
    margin-left: 30px;
  }
  
  /* 搜索输入框 */
  .elinput {
    width: 300px;
    height: 40px;
    font-size: 20px;
    margin-right: 30px !important;
  }
.huanhang{
  -webkit-line-clamp: 4;
   overflow: hidden; 
      text-overflow: ellipsis; 
      display: block; 
      -webkit-box-orient: vertical;
      max-width: 480px;;
}
.addipt{
  margin: 30px 0px;
  height: 50px;
}
  </style>
  