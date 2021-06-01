import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import JftevAnalyseData from './JftevAnalyseDataApp'
import '@/assets/css/common.css'
Vue.config.productionTip = false
Vue.use(ElementUI)
// eslint-disable-next-line no-new
new Vue({
  el: '#appJftevAnalyseData',
  components: { JftevAnalyseData },
  template: '<JftevAnalyseData/>'
})
