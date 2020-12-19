<template>
  <div :class="className" :style="{height:height,width:width}"/>
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons')
export default {
  name: "Graph",
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '300px'
    }
  },
  data() {
    return {
      stompClient: '',
      chart: null,
      option: {
        title: {
          text: '历史温度',
          textStyle:{
            fontSize:14
          }
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['一网供水温度', '一网回水温度', '二网供水温度', '二网回水温度']
        },
        dataZoom: [
          {
            show: true,
            realtime: true,
            start: 0,
            end: 100
          },
        ],
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: []
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '一网供水温度',
            type: 'line',
            data: []
          },
          {
            name: '一网回水温度',
            type: 'line',
            data: []
          },
          {
            name: '二网供水温度',
            type: 'line',
            data: []
          },
          {
            name: '二网回水温度',
            type: 'line',
            data: []
          },
        ]
      },
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart()
    })
  },
  beforeDestroy: function () {
    // 页面离开时断开连接,清除定时器
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
    this.disconnect();
  },
  watch: {
    option: {
      handler() {
        this.chart.setOption(this.option);
        this.chart.resize()
      }, deep: true
    }
  },
  methods: {
    initChart() {
      this.chart = echarts.init(this.$el, 'macarons')
      this.chart.setOption(
          this.option
      )
    },
  },
}
</script>

<style scoped>

</style>
