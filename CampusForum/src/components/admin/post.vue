<template>
    <el-config-provider :locale="locale">
      <!-- 面包屑 -->
      <div style="height: 50px">
        <el-breadcrumb separator="/" style="font-size: 18px">
          <el-breadcrumb-item :to="{ path: '/post' }">帖子管理</el-breadcrumb-item>
          <el-breadcrumb-item>发帖管理</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
  
      <!-- 搜索框 -->
      <div style="display: flex; align-items: center; margin-bottom: 15px">
        <el-input v-model="PSid" placeholder="帖子id" size="large" class="elinput" />
        <el-input v-model="USid" placeholder="发帖者id" size="large" class="elinput" />
        <el-button plain size="large" @click="toSelect">搜索</el-button>
        <el-button size="large" type="primary" @click="restSelect">重置</el-button>
      </div>
      <!-- 表格 -->
      <el-table :data="tableData" style="width: 100%" :table-layout="'auto'" class="etable">
        <el-table-column prop="postId" label="帖子ID" fixed="left" width="70px"/>
        <el-table-column  label="帖子标题" width="280px">
          <template #default="scope">
          <div class="huanhang">
            {{ scope.row.title }}
          </div>
          </template>
          </el-table-column>
        <el-table-column prop="postContent" label="帖子内容(点击显示完整内容)" width="280px" >
            <template #default="scope">
    <div>
      <div @click="toggleContent(scope.row)" style="color: #19599e;cursor: pointer;" class="huanhang">
        {{ scope.row.postContent }}
      </div>

    </div>
  </template>    
        </el-table-column>

        <el-table-column prop="userId" label="发帖者ID" width="100px"/>
        <el-table-column prop="datetime" label="时间" width="80px"/>
        <el-table-column  label="是否置顶" >
          <template #default="scope">
            {{ scope.row.isTop === 1 ? '是' : '否'}}
          </template>
        </el-table-column>
        <el-table-column prop="categoryId" label="帖子分类" width="80px"/>
        <el-table-column prop="upvote" label="点赞数" width="80px"/>
        <el-table-column prop="viewCount" label="浏览量" width="80px"/>
        <el-table-column prop="replyCount" label="评论数" width="80px"/>
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
            <el-button
              link
              type="primary"
              size="default"
              style="font-size: 18px"
             @click="showTop(scope.row)"
              >
            {{ scope.row.isTop === 0 ? '置顶' : '取消置顶'}}

              </el-button
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
        <el-button size="large" @click="dialogDelVisible = false && postid === null">取消</el-button>
      </el-dialog>

           <!-- 置顶对话框 -->
           <el-dialog
        v-model="dialogTopVisible"
        title="确认置顶/取消置顶用户吗？"
        width="30%"
        style="
          height: 300px;
          display: flex;
          justify-content: center;
          align-items: center;
          flex-direction: column;
        "
      >
        <el-button size="large" type="danger" style="margin-right: 40px" @click="toTop"
          >确定</el-button
        >
        <el-button size="large" @click="dialogTopVisible = false">取消</el-button>
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
        <el-text>{{ form.postContent }}</el-text>
    
    </el-dialog>


    </el-config-provider>
  </template>
  
  <script lang="ts">
  import request from '@/utils/request'
import { ElMessage } from 'element-plus'
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
        postid: Number,
        //置顶的帖子id
        istop: Number,
        //输入框绑定的帖子id和发帖者id
        PSid:'',
        USid:'',
        //修改资料绑定的对象
        form: {},
        // 打开修改资料对话框
        dialogFormVisible: false,
        //打开删除对话框
        dialogDelVisible: false,
         //打开置顶对话框
         dialogTopVisible: false,
        //表格中用户头像默认前置路径
        imageUrl: 'http://localhost:9090/studentUser/image/',
        //修改对话框中表单中发送请求的前置路径
        uploadUrl: 'http://localhost:9090/studentUser/upload?user_id=',
        //修改头像预览图片的路径
        showImgUrl: '',
        //修改头像预览图片的名称
        showImgName: '',
        postContentExpanded: false, // 用于记录帖子内容是否被点击展开
      }
    },
    computed: {},
    mounted() {},
    watch: {},
    created() {
      this.toSelect()
    },
    methods: {
      // 查询用户数据
      toSelect() {
        request
          .get('/post/page', {
            params: {
              pageNum: this.pageNum,
              pageSize: this.pageSize,
              post_id: this.PSid,
              user_id: this.USid
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
      showTop(row) {
        this.istop = row.postId
        this.dialogTopVisible = !this.dialogTopVisible
      },
      //删除
      deleteUser() {
        request.delete('/post/' + this.postid, {}).then((res) => {
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
        this.postid = row.postId
        this.dialogDelVisible = !this.dialogDelVisible
      },
      toggleContent(row) {
        this.form = row
          // 切换帖子内容的展开状态
          this.postContentExpanded = !this.postContentExpanded;
        },
      //置顶
        toTop(){
          request.post('/post/top/'+this.istop).then((res) => {
            if(res){
              ElMessage({
                type:'success',
                message:'操作成功'
              })
              this.istop===null
              this.dialogTopVisible=false
              this.toSelect()
            }else{
              ElMessage({
                type:'error',
                message:'失败'
              })
              this.dialogTopVisible=false
              location.reload()
            this.$router.go(0)
            }
          })
        },

            //重置
    restSelect(){
      this.USid=''
      this.PSid= ''
      this.toSelect()
    },

  
  },
    }
  
  </script>
  
  <style scoped>
  .etable :deep(.el-table__row) {
   
   
   
  }
  
  
  /* 表格每行内容的样式 */
  .etable >>> .cell{
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
      max-width: 250px;;
}
  </style>
  