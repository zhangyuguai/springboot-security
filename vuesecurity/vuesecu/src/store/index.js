import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user:{
      name: window.localStorage.getItem('user' || '[]') == null ? '未登录' : JSON.parse(window.localStorage.getItem('user' || '[]')).userName,
      avatarUrl: window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).avatarUrl
    },
    token: localStorage.getItem('token'||[])==null ? '':localStorage.getItem('token')
  },
  getters: {
  },
  mutations: {
    //存储登陆用户信息
    login(state,user){
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
    }
  },
  actions: {
  },
  modules: {
  }
})
