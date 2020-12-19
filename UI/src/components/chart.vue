<template>
  <div>
    <a-form-model layout="inline" :model="formDate" @submit="handleSubmit" @submit.native.prevent>
      <a-form-model-item label="站名">
        <a-select default-value="中北大学站" @change="handleChange" v-model="formDate.station">
          <a-select-option v-for="i in stations" :key="i" :value="i">
            {{ i }}
          </a-select-option>
        </a-select>
      </a-form-model-item>
      <a-form-model-item label="时间范围">
        <a-range-picker
            @change="onChangeDate"
            :disabled-date="disabledDate"
            :disabled-time="disabledRangeTime"
            :locale="locale"
            :show-time="{hideDisabledOptions: true,defaultValue: [moment( moment().subtract(1, 'hour')), moment()],}"
            format="YYYY-MM-DD HH:mm:ss"
        />
      </a-form-model-item>
      <a-form-model-item label="间隔">
        <a-select default-value="中北大学站"  v-model="formDate.gap">
          <a-select-option v-for="i in gaps" :key="i" :value="i">
            {{ i }}
          </a-select-option>
        </a-select>
      </a-form-model-item>
      <a-form-model-item>
        <a-button type="primary" html-type="submit"
            :disabled="formDate.station === '' || formDate.start === ''||formDate.end===''">
          确定
        </a-button>
      </a-form-model-item>
    </a-form-model>

    <a-row :gutter="[16,16]" style="margin-top: 20px">
      <a-col :span="12">
        <graph ref="g1"></graph>

      </a-col>
      <a-col :span="12">
        <graph ref="g2"></graph>

      </a-col>
    </a-row>
    <a-row :gutter="[16,16]">
      <a-col :span="12">
        <graph ref="g3"></graph>

      </a-col>
      <a-col :span="12">
        <graph ref="g4"></graph>
      </a-col>
    </a-row>
  </div>
</template>

<script>

import echarts from 'echarts'
import moment from 'moment'
import 'moment/locale/zh-cn';
import locale from 'ant-design-vue/es/date-picker/locale/zh_CN';
import Graph from "@/components/graph";
require('echarts/theme/macarons')
export default {
  name: "chart",
  components: {Graph},
  data() {
    return {
      data: [],
      gaps:[2,4,5,10,20],
      nowStation: '',
      formDate: {
        gap:5,
        station: "中北大学站", start: moment().subtract(1, 'hour').format("YYYY-MM-DD HH:mm:ss"),
        end: moment().format("YYYY-MM-DD HH:mm:ss")
      },
      stations: [],
      moment,
      locale,
      stompClient: '',
      chart: null,
    }
  },
  mounted() {
    const that = this;
    this.$http.get("getStationsRunning").then(function (res) {
      that.stations = res.data.result;
    }).catch()
    this.handleSubmit();
    this.initWebSocket();
    this.initData();
  },
  beforeDestroy: function () {
    // 页面离开时断开连接,清除定时器
    // if (!this.chart) {
    //   return
    // }
    // this.chart.dispose()
    // this.chart = null
    this.disconnect();
  },

  methods: {
    initData(){
      this.$refs.g2.option.legend.data = ['一网瞬时流量', '一网瞬时热量']
      this.$refs.g3.option.legend.data = ['一网供水压力', '一网回水压力', '二网供水压力', '二网回水压力']
      this.$refs.g4.option.legend.data = ['电动阀开度', '循环泵频率', '补水泵频率']
      this.$refs.g2.option.title.text = '历史热量流量'
      this.$refs.g3.option.title.text = '历史压力'
      this.$refs.g4.option.title.text = '历史设备状态'
      this.$refs.g2.option.series = [
        {
          name: '一网瞬时流量',
          type: 'line',
          data: []
        },
        {
          name: '一网瞬时热量',
          type: 'line',
          data: []
        },]
      this.$refs.g3.option.series = [
        {
          name: '一网供水压力',
          type: 'line',
          data: []
        },
        {
          name: '一网回水压力',
          type: 'line',
          data: []
        }, {
          name: '二网供水压力',
          type: 'line',
          data: []
        }, {
          name: '二网回水压力',
          type: 'line',
          data: []
        },
      ]
      this.$refs.g4.option.series = [
        {
          name: '电动阀开度',
          type: 'line',
          data: []
        },
        {
          name: '循环泵频率',
          type: 'line',
          data: []
        }, {
          name: '补水泵频率',
          type: 'line',
          data: []
        },
      ]
    },
    handleSubmit() {
      const that = this;
      this.$http.get('getRecordWithFixRate', {
        params: {
          station: this.formDate.station,
          gap: this.formDate.gap,
          start: this.formDate.start,
          end: this.formDate.end
        }
      }).then(function (res) {
        that.changeValue(res)
      }).catch()
    },
    range(start, end) {
      const result = [];
      for (let i = start; i < end; i++) {
        result.push(i);
      }
      return result;
    },
    handleChange(value) {
      this.nowStation = value;
    },
    onChangeDate(date) {
      this.formDate.start = date[0].format("YYYY-MM-DD HH:mm:ss");
      this.formDate.end = date[1].format("YYYY-MM-DD HH:mm:ss");
      console.log(this.formDate)
    },
    disabledRangeTime(_, type) {
      if (type === 'start') {
        return {};
      }
      return {};
    },
    disabledDate(current) {
      // Can not select days before today and today
      return current && current > moment().endOf('day');
    },
    initChart() {
      this.chart = echarts.init(this.$el, 'macarons')
      this.chart.setOption(
          this.option
      )
    },
    changeValue(res){
      const that=this;
      console.log(res.data.result);
      const list = res.data.result;
      that.$refs.g1.option.xAxis.data = []
      that.$refs.g2.option.xAxis.data = []
      that.$refs.g3.option.xAxis.data = []
      that.$refs.g4.option.xAxis.data = []
      that.$refs.g1.option.series[0].data = []
      that.$refs.g1.option.series[1].data = []
      that.$refs.g1.option.series[2].data = []
      that.$refs.g1.option.series[3].data = []
      that.$refs.g2.option.series[0].data = []
      that.$refs.g2.option.series[1].data = []
      that.$refs.g3.option.series[0].data = []
      that.$refs.g3.option.series[1].data = []
      that.$refs.g3.option.series[2].data = []
      that.$refs.g3.option.series[3].data = []
      that.$refs.g4.option.series[0].data = []
      that.$refs.g4.option.series[1].data = []
      that.$refs.g4.option.series[2].data = []
      for (let i = 0; i < list.length; i++) {
        const time=moment(list[i].time).format("YYYY-MM-DD HH:mm:ss");
        that.$refs.g1.option.xAxis.data.push(time);
        that.$refs.g2.option.xAxis.data.push(time);
        that.$refs.g3.option.xAxis.data.push(time);
        that.$refs.g4.option.xAxis.data.push(time);
        that.$refs.g1.option.series[0].data.push(list[i].oneSupplyTemperature);
        that.$refs.g1.option.series[1].data.push(list[i].onceTemperature);
        that.$refs.g1.option.series[2].data.push(list[i].twoSupplyTemperature);
        that.$refs.g1.option.series[3].data.push(list[i].twiceTemperature);
        that.$refs.g2.option.series[0].data.push(list[i].instantaneousTraffic);
        that.$refs.g2.option.series[1].data.push(list[i].instantaneousHeat);
        that.$refs.g3.option.series[0].data.push(list[i].oneSupplyPressure);
        that.$refs.g3.option.series[1].data.push(list[i].pressureOnce);
        that.$refs.g3.option.series[2].data.push(list[i].twoSupplyPressure);
        that.$refs.g3.option.series[3].data.push(list[i].pressureTwice);
        that.$refs.g4.option.series[0].data.push(list[i].electricValveOpening);
        that.$refs.g4.option.series[1].data.push(list[i].circulatingPumpFrequency);
        that.$refs.g4.option.series[2].data.push(list[i].makeupPumpFrequency);
      }
    }
  },
}
</script>

<style scoped>

</style>
