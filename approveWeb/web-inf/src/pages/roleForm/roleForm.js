import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import roleForm from './App'
import '@/assets/css/common.css'
Vue.config.productionTip = false
Vue.use(ElementUI)
new Vue({
  el: '#approleForm',
  components: { roleForm },
  template: '<roleForm/>'
})
