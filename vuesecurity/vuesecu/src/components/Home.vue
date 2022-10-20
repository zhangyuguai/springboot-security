<template>
  <div>
    <div>
      <el-container class="home-container">
        <el-header class="home-header">
          <span class="home_title">微人事</span>
          <div style="display: flex;align-items: center;margin-right: 7px">
            <el-dropdown @command="handleCommand">
  <span class="el-dropdown-link home_userinfo" style="display: flex;align-items: center">
    {{user.name}}
    <i><img v-if="user.avatarUrl!=''" :src="user.avatarUrl"
            style="width: 40px;height: 40px;margin-right: 5px;margin-left: 5px;border-radius: 40px"/></i>
  </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>个人中心</el-dropdown-item>
                <el-dropdown-item>设置</el-dropdown-item>
                <el-dropdown-item command="logout" divided>注销</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </el-header>
<!--        <el-container>-->
<!--          <el-aside width="180px" class="home-aside">-->
<!--            <div style="display: flex;justify-content: flex-start;width: 180px;text-align: left;">-->
<!--              <el-menu style="background: #ececec;width: 180px;" unique-opened router>-->
<!--                <template v-for="(item,index) in this.routes" v-if="!item.hidden">-->
<!--                  <el-submenu :key="index" :index="index+''">-->
<!--                    <template slot="title">-->
<!--                      <i :class="item.iconCls" style="color: #20a0ff;width: 14px;"></i>-->
<!--                      <span slot="title">{{item.name}}</span>-->
<!--                    </template>-->
<!--                    <el-menu-item width="180px"-->
<!--                                  style="padding-left: 30px;padding-right:0px;margin-left: 0px;width: 170px;text-align: left"-->
<!--                                  v-for="child in item.children"-->
<!--                                  :index="child.path"-->
<!--                                  :key="child.path">{{child.name}}-->
<!--                    </el-menu-item>-->
<!--                  </el-submenu>-->
<!--                </template>-->
<!--              </el-menu>-->
<!--            </div>-->
<!--          </el-aside>-->
<!--          <el-container>-->
<!--            <el-main>-->
<!--              <el-breadcrumb separator-class="el-icon-arrow-right">-->
<!--                <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>-->
<!--                <el-breadcrumb-item v-text="this.$router.currentRoute.name"></el-breadcrumb-item>-->
<!--              </el-breadcrumb>-->
<!--              <keep-alive>-->
<!--                <router-view v-if="this.$route.meta.keepAlive"></router-view>-->
<!--              </keep-alive>-->
<!--              <router-view v-if="!this.$route.meta.keepAlive"></router-view>-->
<!--            </el-main>-->
<!--          </el-container>-->
<!--        </el-container>-->
      </el-container>
    </div>
  </div>
</template>
<script>
  import {getRequest} from "@/utils/api";

  export default {
    data() {
      return {

      }
    },
    methods:{
      handleCommand(cmd){
       if (cmd==="logout"){
         this.$confirm('是否确认注销!','提示',{
           type:'info',
           confirmButtonText:'确定',
           cancelButtonText:'取消'
         }).then(()=>{

           //清除securityholder中存储的认证信息
           getRequest('http://localhost:8081/logout');
           //清除vuex共享的数据，user，routes
           this.$store.commit('logout');
           this.$router.replace('/');
           this.$message.success('注销成功!');
         })
       }
      },
      },
    computed:{
      user(){
        return this.$store.state.user;
      }
    },

  }
</script>
<style>
.home-container {
  height: 100%;
  position: absolute;
  top: 0px;
  left: 0px;
  width: 100%;
}

.home-header {
  background-color: #20a0ff;
  color: #333;
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-sizing: content-box;
  padding: 0px;
}

.home-aside {
  background-color: #ECECEC;
}

.home-main {
  background-color: #fff;
  color: #000;
  text-align: center;
  margin: 0px;
  padding: 0px;;
}

.home_title {
  color: #fff;
  font-size: 22px;
  display: inline;
  margin-left: 45%;

}

.home_userinfo {
  color: #fff;
  cursor: pointer;
}

.home_userinfoContainer {
  display: inline;
  margin-right: 20px;
}

.el-submenu .el-menu-item {
  width: 180px;
  min-width: 175px;
}
</style>
