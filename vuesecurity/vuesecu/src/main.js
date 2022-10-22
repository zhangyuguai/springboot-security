import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import 'element-ui/lib/theme-chalk/index.css';
import ElementUI from 'element-ui';
import {getRequest} from './utils/api'
import {postRequest} from './utils/api'
import {deleteRequest} from './utils/api'
import {putRequest} from './utils/api'
import {initMenu} from "@/utils/utils";
Vue.config.productionTip = false
Vue.use(ElementUI)

router.beforeEach((to, from, next)=> {
      if (to.name === 'login') {
        next();
        return;
      }
     var token = store.state.token;
      if(token===null){
        next('login');
      } else {
        initMenu(router, store);
        next();
      }
    }
)
new Vue({
  el:'#app',
  router,
  store,
  render: h => h(App),
  mounted() {
    Vue.prototype.getRequest = getRequest;
    Vue.prototype.postRequest = postRequest;
    Vue.prototype.deleteRequest = deleteRequest;
    Vue.prototype.putRequest = putRequest;
  },
})
