<template>
  <div>
    <div style="text-align: left" v-loading="loading">
      <el-input placeholder="请输入角色英文名称..."
                size="mini"
                style="width: 250px"
                v-model="roleName">
        <template slot="prepend">ROLE_</template>
      </el-input>
      <el-input
          placeholder="请输入角色中文名称..."
          size="mini"
          style="width: 250px"
          v-model="roleNameZH">
      </el-input>
      <el-button type="primary" size="mini" style="margin-left: 5px" @click="addNewRole">添加角色</el-button>
    </div>
    <div>
      <el-collapse v-model="activeName" accordion @change="getRoleMenu">
        <el-collapse-item v-for="role in roleList" :title=role.nameZh :name=role.roleId>
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>权限菜单</span>
              <el-button style="float: right; padding: 3px 0" type="text">添加权限菜单</el-button>
            </div>
            <el-tree
                :data="data"
                show-checkbox
                node-key="menuId"
                :default-expanded-keys="checkedMenus"
                :default-checked-keys="checkedMenus"
                :props="defaultProps"
                >
            </el-tree>

          </el-card>
        </el-collapse-item>
      </el-collapse>
    </div>
  </div>
</template>
<script>
import axios from "axios";

export default {

  data() {
    return {
      loading: false,
      roleList: [],
      roleName: '',
      roleNameZH: '',
      activeName: '',
      checkedMenus:[],
      data: [],
      defaultProps: {
        children: 'children',
        label: 'menuName'
      }
    };
  },
  mounted() {
    this.initRole();
  },
  methods: {
    initRole() {
      axios.get("http://localhost:8081/role").then(
          res => {
            if (res.data.code === 200) {
              this.roleList = res.data.data;
            }
          }
      )
    },
    getRoleMenu(change) {
      console.log("change参数",change)
      if (change===''){
        return;
      }

      axios.get(`http://localhost:8081/menu/menuTree/${change}`).then(
          res=>{
           if (res.data.code===200){
             this.data=res.data.data.menuList;
             console.log("菜单",this.data)
             this.checkedMenus=res.data.data.menuIds;
             console.log("选中",this.checkedMenus)
           }
          }
      )
    },
    addNewRole() {

    },

  },

}
</script>

<style scoped>
.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}

.box-card {
  width: 480px;
}

.text {
  font-size: 14px;
}
</style>
