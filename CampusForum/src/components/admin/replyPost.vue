<template>
    <el-config-provider :locale="locale">
      <!-- 面包屑 -->
      <div style="height: 50px">
        <el-breadcrumb separator="/" style="font-size: 18px">
          <el-breadcrumb-item :to="{ path: '/admin-index' }">帖子管理</el-breadcrumb-item>
          <el-breadcrumb-item>评论管理</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
  
      <!-- 搜索框 -->
      <div style="display: flex; align-items: center; margin-bottom: 15px">
        <el-input v-model="userId" placeholder="发布回复者id" size="large" class="elinput" />
        <el-input v-model="postId" placeholder="所属帖子id" size="large" class="elinput" />
        <el-button plain size="large" @click="toSelect">搜索</el-button>
        <el-button size="large" type="primary" @click="restSelect">重置</el-button>
      </div>
      <!-- 表格 -->
      <el-table :data="tableData" style="width: 100%" :table-layout="'auto'" class="etable">
        <el-table-column prop="replyId" label="回复ID" fixed="left" width="70px"/>
        <el-table-column prop="replyContent" label="回复内容(点击显示完整内容)" width="580px" >
            <template #default="scope">
    <div>
      <div @click="toggleContent(scope.row)" style="color: #19599e;cursor: pointer;" class="huanhang">
        {{ scope.row.replyContent }}
      </div>

    </div>
  </template>    
        </el-table-column>

        <el-table-column prop="postId" label="所属帖子ID" width="180px"/>
        <el-table-column prop="datetime" label="回复时间" width="280px"/>
        <el-table-column  label="发布回复的用户id" prop="userId" width="180px"/>
        <el-table-column prop="upvote" label="点赞数量" width="180px"/>

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
      }
    },
    created() {
      this.toSelect()
    },
    methods: {
      // 查询用户数据
      toSelect() {
        request
          .get('/replyPost/page', {
            params: {
              pageNum: this.pageNum,
              pageSize: this.pageSize,
              postId :this.postId,
              userId :this.userId
            }
          })
          .then((res) => {
            this.tableData = res.data.records
            this.total = res.data.total
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
      toggleContent(data){
        this.postContentExpanded = !this.postContentExpanded
        this.replyContent = data.replyContent
      },

      //删除
      deleteUser() {
        request.delete('/replyPost/' + this.delRepId, {}).then((res) => {
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
        this.delRepId = row.replyId
        this.dialogDelVisible = !this.dialogDelVisible
      },
            //重置
    restSelect(){
      this.postId=''
      this.userId= ''
      this.toSelect()
    },

  
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
  </style>
  