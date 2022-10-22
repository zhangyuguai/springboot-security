import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user:localStorage.getItem('user')==null ? '':JSON.parse(localStorage.getItem('user')),
    token: localStorage.getItem('token')==null ? '':localStorage.getItem('token'),
    routes:[]
  },
  getters: {
  },
  mutations: {
    //存储登陆用户信息
    login(state,user){
      console.log('共享的',user)
      state.user=user;
      localStorage.setItem('user',JSON.stringify(user));
    },
    logout(state){
      localStorage.removeItem('user');
      localStorage.removeItem('token');
      state.routes=[]
    },
    token(state,token){
      state.token=token;
      localStorage.setItem('token',token);
    },
    initMenu(state,fmRouters){
      state.routes=fmRouters;
    }
  },
  actions: {
  },
  modules: {
  }
})
