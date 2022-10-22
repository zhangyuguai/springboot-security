<template>
  <div>
    <el-container>
      <el-row style="margin: 10px 0">
        <el-input v-model="userName" placeholder="请输入关键字" style="width: 250px" size="mini"></el-input>
        <el-button type="primary"  size="mini" @click="searchWithUserName">搜索</el-button>
        <el-button type="warning"  size="mini" @click="reset">重置</el-button>
      </el-row>
    </el-container>
    <el-container>
      <el-table
          :data="tableData"
          style="width: 100%">
        <el-table-column
            prop="userId"
            label="编号"
            width="180">
        </el-table-column>
        <el-table-column
            prop="userName"
            label="姓名"
            width="180">
        </el-table-column>
        <el-table-column
            prop="nickName"
            label="昵称"
            width="180">
        </el-table-column>
        <el-table-column
            prop="email"
            label="邮箱">
        </el-table-column>
        <el-table-column
            prop="phone"
            label="电话">
        </el-table-column>
        <el-table-column
           label="操作"
            >
          <template slot-scope="scope">
            <el-button type="success" size="mini" @click="openDialog(scope.row)">分配权限</el-button>
            <el-button type="warning" size="mini">编辑</el-button>
            <el-button type="danger" size="mini">删除</el-button>
          </template>

        </el-table-column>
      </el-table>
    </el-container>
    <el-container>
      <div class="block" style="margin: 10px 0">
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[5, 10, 15, 20]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
        </el-pagination>
      </div>

      <el-dialog title="用户角色" :visible.sync="dialogFormVisible">
        <template >
          <div>
            <el-checkbox-group v-model="currentRole">
                <template >
                  <el-checkbox-button  v-for="role in roleList" :label="role.roleName" :key="role.roleId"
                                      style="margin-right: 4px"
                                      :value="role.roleId" >{{role.nameZh}}
                  </el-checkbox-button>
                </template>
            </el-checkbox-group>

          </div>
        </template>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="addRoleToUser()">确 定</el-button>
        </div>
      </el-dialog>
    </el-container>
  </div>
</template>


<script>
  import axios from "axios";
  import Login from "@/components/Login";

  export default {
    name:'EmpAdv',
    data(){
      return{
        tableData: [],
        userName:'',
        pageNum:1,
        pageSize:5,
        total:0,
        dialogFormVisible:false,
        roleList:[],
        currentRole:[],
        rowUserId:'',
      }
    },
    methods: {
      tableRowClassName({row, rowIndex}) {
        if (rowIndex === 1) {
          return 'warning-row';
        } else if (rowIndex === 3) {
          return 'success-row';
        }
        return '';
      },
      handleSizeChange(pageSize) {

        console.log(`每页 ${pageSize} 条`);
        this.pageSize=pageSize;
      },
      handleCurrentChange(pageNum) {
        this.pageNum=pageNum
        this.init();
        console.log(`当前页: ${pageNum}`);
      },
      //初始化加载信息
      init(){
        console.log('用户名',this.userName)
        //加载表格用户信息
        axios.get(`http://localhost:8081/user/page/${this.pageNum}/${this.pageSize}`,{
          params:{
            userName:this.userName
          }
        }).then(
            res=>{
              console.log(res)
              if (res.data.code===200){
                this.tableData=res.data.data.records;
                this.total=res.data.data.total;
              }
            }
        )

        //加载权限角色信息
        axios.get('http://localhost:8081/role').then(
            res=>{
              console.log('权限角色',res);
              this.roleList=res.data.data;
            }
        )
      },
      searchWithUserName(){
        console.log('55555',this.userName);
        this.init();
      },
      reset(){
        this.userName='';
        this.init();
      },
      openDialog(scope){
        axios.get(`http://localhost:8081/role/${scope.userId}`).then(
            res=>{
              //当前用户角色
              this.currentRole=res.data.data;
              console.log('哈哈哈',this.roleList);
            }
        )
        this.rowUserId=scope.userId;
        this.dialogFormVisible=true;
      },
      addRoleToUser(){
        console.log(this.rowUserId);
        console.log('权限',this.currentRole)
        axios.post(`http://localhost:8081/userRole/${this.rowUserId}`,this.currentRole).then(
            res=>{
              console.log("用户权限表",res);
            }
        )
        this.dialogFormVisible=false;
      }
    },

    mounted() {
      this.init();
    }
  }
</script>

<style scoped>
.el-table .warning-row {
  background: oldlace;
}

.el-table .success-row {
  background: #f0f9eb;
}
</style>
