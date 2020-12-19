import Vue from 'vue'
import Antd, {message} from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css'
Vue.use(Antd)
Vue.prototype.$message=message;
export default message;
