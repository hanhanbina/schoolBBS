<template>
  <el-config-provider :locale="locale">
    <!-- 面包屑 -->
    <div style="height: 50px">
      <el-breadcrumb separator="/" style="font-size: 18px">
        <el-breadcrumb-item :to="{ path: '/admin' }">用户管理</el-breadcrumb-item>
        <el-breadcrumb-item>用户资料</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <!-- 搜索框 -->
    <div style="display: flex; align-items: center; margin-bottom: 15px">

      <el-input v-model="account" placeholder="用户账号" size="large" class="elinput" />
      <el-input v-model="username" placeholder="用户名" size="large" class="elinput" />
      <el-button plain size="large" @click="toSelect">搜索</el-button>
      <el-button size="large" type="default" @click="restSelect">重置</el-button>
    </div>
    <div style="display: flex; align-items: center; margin-bottom: 15px">
      <el-input v-model="Uid" placeholder="用户id" size="large" class="elinput" />
      <el-button plain size="large" @click="serchByid">搜索</el-button>
      <el-button plain size="large" @click="exportUser()" type="primary">导出用户</el-button>
      <!-- 导入用户 -->
      <el-upload style="display: inline-block;margin-left: 10px;"
      :show-file-list="false"
      accept="xlsx"
    class="upload-demo"
    :on-success="importSuccess"
    action="http://localhost:9090/user/import"
  >
      <el-button plain size="large" type="primary">导入用户</el-button>
      </el-upload>
      <el-button plain size="large"  type="primary " style="margin-left: 10px;" @click="xinzengUserHandle()">新增用户</el-button>
    </div>
    <!-- 表格 -->
    <el-table :data="tableData" style="width: 100%" :table-layout="'auto'" class="etable">
      <el-table-column prop="userId" label="用户ID" fixed="left" />
      <el-table-column prop="userAccount" label="用户账号" />
      <el-table-column prop="userName" label="用户昵称" />
      <el-table-column prop="sex" label="性别" />
      <el-table-column prop="email" label="邮箱" />
      <el-table-column prop="profile" label="个人简介" />
      <el-table-column label="用户头像">
        <template #default="scope">
          <img :src="scope.row.headImg" class="tb_img" />
        </template>
      </el-table-column>
      <!-- prop="user_password" -->
      <el-table-column label="用户密码"> ******** </el-table-column>
      <!-- 操作按钮 -->
      <el-table-column fixed="right" label="操作">
        <template #default="scope">
          <el-button
            link
            type="primary"
            size="default"
            style="font-size: 18px"
            @click="handEdit(scope.row)"
            >修改</el-button
          >
          <el-button
            link
            type="danger"
            size="default"
            style="font-size: 18px"
            @click="showDel(scope.row)"
            >删除</el-button
          >
          <el-button link type="danger" size="default" style="font-size: 16px" @click="showPwd(scope.row)">重置密码</el-button>
          <!-- <el-button link type="danger" size="default" style="font-size: 16px" @click="restPwd"
            >重置密码</el-button -->
          
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

    <!-- 点击修改出现的弹窗 -->
    <el-dialog v-model="dialogFormVisible" title="用户信息" width="600px" style="height: 1000px">
      <el-form label-width="auto" label-position="top" size="large">
        <el-form-item label="用户 ID" style="margin-bottom: 20px">
          <el-input
            :clearable="true"
            style="margin-left: -2px"
            v-model="form.userId"
            autocomplete="off"
            disabled
          />
        </el-form-item>
        <el-form-item label="用户昵称">
          <el-input
            :clearable="true"
            style="margin-left: -2px"
            v-model="form.userName"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item label="用户账号" style="margin-top: 30px; margin-bottom: 50px">
          <el-input
            :clearable="true"
            style="margin-left: -2px"
            v-model="form.userAccount"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item label="邮箱" style="margin-top: 30px; margin-bottom: 50px">
          <el-input
            :clearable="true"
            style="margin-left: -2px"
            v-model="form.email"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item label="性别" style="margin-top: 30px; margin-bottom: 50px">
          <el-input
            :clearable="true"
            style="margin-left: -2px"
            v-model="form.sex"
            autocomplete="off"
          />
        </el-form-item>

        <el-form-item label="用户头像">
          <form
            id="uploadForm"
            :action="uploadUrl + form.userId"
            method="post"
            enctype="multipart/form-data"
            target="frameName" 
          >
            <!-- 用label标签继承上传按钮的功能 -->
            <label class="input-file-button" for="upload">上传头像</label>
            <!-- 隐藏的内嵌网页用于阻止form跳转到action链接去 -->
            <iframe src="" frameborder="0" name="frameName" style="display: none;"></iframe>
            <!-- 隐藏原本的文件上传按钮 -->
            <input
              class="filebtn"
              type="file"
              name="file"
              accept="image/*"
              id="upload"
              @change="showFile($event)"
            />
            文件名：{{ showImgName }}
            <p>预览：</p>
            <div
              style="display: flex; flex-direction: column; margin-left: 60px; margin-top:5px;height: 60px; width: 60px;border: 1px dashed #c0c0c0;">
              
        
              <img :src="showImgUrl" style="height: 60px; width: 60px" v-if="showImgUrl !== ''"/>
            </div>

            <div
              style="
                display: flex;
                justify-content: center;
                align-items: center;
                width: 580px;
                margin-top: 30px;
              "
            >
              <button type="submit" @click="save" class="uploadbtn">确定</button>
              <el-button size="large" @click="closeUpdateUserInfo()">取消</el-button>
            </div>
          </form>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 重置密码对话框 -->
    <el-dialog
      v-model="dialogPWDVisible"
      title="确认重置密码吗？"
      width="30%"
      style="
        height: 300px;
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
      "
    >
      <el-button size="large" type="danger" style="margin-right: 40px" @click="restPwd"
        >确定</el-button
      >
      <el-button size="large" @click="dialogPWDVisible = false && userid === null">取消</el-button>
    </el-dialog>

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
      <el-button size="large" @click="dialogDelVisible = false && userid === null">取消</el-button>
    </el-dialog>


     <!-- 新增用户对话框 -->
     <el-dialog
      v-model="xinzengUserDia"
      title="新增用户"
      width="50%"
      style="
        height: 800px;
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
      "
    >
    <el-form>
      <el-form-item label="用户 ID" style="margin-bottom: 20px">
          <el-input
            :clearable="true"
            style="margin-left: -2px"
            v-model="form.userId"
            autocomplete="off"
            disabled
          />
        </el-form-item>
        <el-form-item label="用户昵称">
          <el-input
            :clearable="true"
            style="margin-left: -2px"
            v-model="form.userName"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item label="用户账号" style="margin-top: 30px; margin-bottom: 50px">
          <el-input
            :clearable="true"
            style="margin-left: -2px"
            v-model="form.userAccount"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item label="邮箱" style="margin-top: 30px; margin-bottom: 50px">
          <el-input
            :clearable="true"
            style="margin-left: -2px"
            v-model="form.email"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item label="性别" style="margin-top: 30px; margin-bottom: 50px">
          <el-input
            :clearable="true"
            style="margin-left: -2px"
            v-model="form.sex"
            autocomplete="off"
          />
        </el-form-item>
        <button type="submit" @click="save" class="uploadbtn">确定</button>
         <el-button size="large" @click="xinzengUserDia=false">取消</el-button>
    </el-form>
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
      pageSize: 10,
      //输入框绑定的用户名和账号
      username: '',
      account: '',
      userid: Number,
      //修改资料绑定的对象
      form: {} as any,
      // 打开修改资料对话框
      dialogFormVisible: false,
      //打开重置密码对话框
      dialogPWDVisible: false,
      //打开删除对话框
      dialogDelVisible: false,
      //修改对话框中表单中发送请求的前置路径
      uploadUrl: 'http://localhost:9090/user/upload?user_id=',
      //修改头像预览图片的路径
      showImgUrl: '',
      //修改头像预览图片的名称
      showImgName: '',
      Uid:'',
      //新增用户对话框
      xinzengUserDia:false,
      imageUrl:''
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
        .get('/user/page', {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            userName: this.username,
            userAccount: this.account
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
    // 打开用户资料对话框，传入当前行用户数据
    handEdit(row) {
      this.form = row
      this.dialogFormVisible = true
    },
    //重置密码为123456
    restPwd() {
      request.post('/user/reset?user_id=' + this.userid, {}).then((res) => {
        if (res) {
          alert('修改成功')
          this.dialogPWDVisible = false
            // 强制刷新
            location.reload()
          this.$router.go(0)
        } else {
          alert('修改失败')
          this.dialogPWDVisible = false
            // 强制刷新
            location.reload()
          this.$router.go(0)
        }
      })
    },
    //删除
    deleteUser() {
      request.delete('/user/' + this.userid, {}).then((res) => {
        if (res) {
          alert('删除成功')
          this.dialogDelVisible = false
          // 强制刷新
          location.reload()
          this.$router.go(0)
        } else {
          alert('删除失败')
          this.dialogDelVisible = false
            // 强制刷新
            location.reload()
          this.$router.go(0)
        }
      })
    },
    //展示重置密码二次确认对话框
    showPwd(row) {
      this.userid = row.userId
      this.dialogPWDVisible = !this.dialogPWDVisible
    },
    //展示删除二次确认对话框
    showDel(row) {
      this.userid = row.userId
      this.dialogDelVisible = !this.dialogDelVisible
    },
    //修改用户资料
    save() {
      request.post('/user', this.form).then((res) => {
        if (res) {
          alert('修改成功')
          this.dialogFormVisible = false
          //刷新页面
          location.reload()
          this.$router.go(0)
        } else {
          alert('修改失败')
          this.dialogFormVisible = false
          //刷新页面
          location.reload()
          this.$router.go(0)
        }
      })
    },
    //上传图片后预览图片，和展示图片名称
    showFile(event) {
      const file = event.target.files[0]
      this.showImgName = file.name
      let url = ''
      const reader = new FileReader()
      reader.readAsDataURL(file)
      let that = this
      reader.onload = function (e) {
        url = this.result.substring(this.result.indexOf(',') + 1)
        that.showImgUrl = 'data:image/png;base64,' + url
      }
    },
    //根据id搜索用户
    toSelectByid(){
      request
        .get('/user/'+this.Uid)
        .then((res) => {
          if(res.code != 200){
            alert("没有这个人，或系统错误")
            return;
          }
          this.tableData.length = 0
          this.tableData.push(res.data)
        })
    },
    //调用搜索id方法
    serchByid(){
      if(this.Uid===''||this.Uid===null){
        alert("id不能为空")
      }else{
        this.toSelectByid()
      }
    },
    //重置
    restSelect(){
      this.username=''
      this.account= ''
      this.Uid=''
      this.toSelect()
    },
    //关闭修改弹出弹窗,重置form对象
    closeUpdateUserInfo(){
      this.dialogFormVisible = false
      this.form = null
      this.showImgUrl='';
      this.showImgName=''
    },
    //导出
    exportUser(){
      window.open("http://localhost:9090/user/export")
    },
    //导入成功提示
    importSuccess(){
      ElMessage('文件导入成功')
      this.toSelect()
    },
    xinzengUserHandle(){
      this.xinzengUserDia = true;

    }
   

  }
}
</script>

<style scoped>
.etable :deep(.el-table__row) {
  height: 70px !important;
  font-size: 20px;
}

/* 
表格每行的样式
.etable >>> .cell{
  text-align: center;
} */

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
/* 表格中用户头像展示的样式 */
.tb_img {
  height: 50px;
  width: 50px;
  border-radius: 40px;
}
/* 修改对话框确定按钮 */
.uploadbtn {
  background-color: #409eff;
  color: white;
  border-radius: 5px;
  border: none;
  padding: 10px 20px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
  transition: all 0.3s;
  margin-right: 60px;
}
.uploadbtn:hover {
  background-color: #66b1ff;
}
/* 隐藏原本的文件上传按钮 */
.filebtn {
  display: none;
}
/* 用label标签继承上传按钮的功能，label的样式 */
.input-file-button {
  background-color: #409eff;
  color: #ffffff;
  display: block;
  width: 75px;
  text-align: center;
  line-height: 35px;
  height: 36px;
  border-radius: 5px;
  margin-bottom: 10px;
}
</style>
