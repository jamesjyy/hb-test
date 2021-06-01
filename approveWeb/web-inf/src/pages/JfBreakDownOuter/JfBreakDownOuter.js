import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import JfBreakDownOuter from './App'
import '@/assets/css/common.css'
Vue.config.productionTip = false
Vue.use(ElementUI)
new Vue({
  el: '#appJfBreakDownOuter',
  components: { JfBreakDownOuter },
  template: '<JfBreakDownOuter/>'
})