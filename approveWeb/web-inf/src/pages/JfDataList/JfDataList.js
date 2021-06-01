import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import JfDataList from './App'
import '@/assets/css/common.css'
Vue.config.productionTip = false
Vue.use(ElementUI)
new Vue({
  el: '#appJfDataList',
  components: { JfDataList },
  template: '<JfDataList/>'
})