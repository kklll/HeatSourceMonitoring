<template>
  <div>
    <a-form-model layout="inline" :model="formDate" @submit="handleSubmit" @submit.native.prevent>
      <a-form-model-item label="站名" >
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
<!--      <a-form-model-item label="间隔">-->
<!--        <a-select default-value="中北大学站"  v-model="formDate.gap">-->
<!--          <a-select-option v-for="i in gaps" :key="i" :value="i">-->
<!--            {{ i }}-->
<!--          </a-select-option>-->
<!--        </a-select>-->
<!--      </a-form-model-item>-->
      <a-form-model-item>
        <a-button
            type="primary"
            html-type="submit"
            :disabled="formDate.station === '' || formDate.start === ''||formDate.end===''"
        >
          确定
        </a-button>
      </a-form-model-item>
    </a-form-model>

    <a-table
        style="margin-top: 20px"
        :columns="columns"
        :row-key="'id'"
        :data-source="data"
        :pagination="pagination"
        :loading="loading"
        bordered
        @change="handleTableChange">
      <div slot="time" slot-scope="time"> {{ filterTime(time) }}</div>
      <div slot="oneSupplyTemperature" slot-scope="s"> {{ s.toFixed(1) }}</div>
      <div slot="onceTemperature" slot-scope="s"> {{ s.toFixed(1) }}</div>
      <div slot="oneSupplyPressure" slot-scope="s"> {{ s.toFixed(1) }}</div>
      <div slot="pressureOnce" slot-scope="s"> {{ s.toFixed(1) }}</div>
      <div slot="twoSupplyTemperature" slot-scope="s"> {{ s.toFixed(1) }}</div>
      <div slot="twiceTemperature" slot-scope="s"> {{ s.toFixed(1) }}</div>
      <div slot="twoSupplyPressure" slot-scope="s"> {{ s.toFixed(1) }}</div>
      <div slot="pressureTwice" slot-scope="s"> {{ s.toFixed(1) }}</div>
      <div slot="instantaneousTraffic" slot-scope="s"> {{ s.toFixed(1) }}</div>
      <div slot="instantaneousHeat" slot-scope="s"> {{ s.toFixed(1) }}</div>
      <div slot="electricValveOpening" slot-scope="s"> {{ s.toFixed(1) }}</div>
      <div slot="circulatingPumpFrequency" slot-scope="s"> {{ s.toFixed(1) }}</div>
      <div slot="makeupPumpFrequency" slot-scope="s"> {{ s.toFixed(1) }}</div>
    </a-table>
  </div>
</template>
<script>
import moment from 'moment'
import 'moment/locale/zh-cn';
import locale from 'ant-design-vue/es/date-picker/locale/zh_CN';

const columns = [
  {
    title: '时间',
    dataIndex: 'time',
    ellipsis: false,
    align: "center",
    scopedSlots: {customRender: 'time'}
  },
  {
    title: '一供温度',
    dataIndex: 'oneSupplyTemperature',
    ellipsis: false,
    align: "center",
    scopedSlots: {customRender: 'oneSupplyTemperature'}
  },
  {
    title: '一回温度',
    dataIndex: 'onceTemperature',
    ellipsis: false,
    align: "center",
    scopedSlots: {customRender: 'onceTemperature'},
  },
  {
    title: '一供压力',
    dataIndex: 'oneSupplyPressure',
    ellipsis: false,
    align: "center",
    scopedSlots: {customRender: 'oneSupplyPressure'},

  },
  {
    title: '一回压力',
    dataIndex: 'pressureOnce',
    ellipsis: false,
    align: "center",
    scopedSlots: {customRender: 'pressureOnce'},
  },
  {
    title: '二供温度',
    dataIndex: 'twoSupplyTemperature',
    ellipsis: false,
    sorter: false,
    align: "center",
    scopedSlots: {customRender: 'twoSupplyTemperature'},
  },
  {
    title: '二回温度',
    dataIndex: 'twiceTemperature',
    ellipsis: false,
    align: "center",
    sorter: false,
    scopedSlots: {customRender: 'twiceTemperature'},

  },
  {
    title: '二供压力',
    dataIndex: 'twoSupplyPressure',
    sorter: false,
    ellipsis: false,
    align: "center",
    scopedSlots: {customRender: 'twoSupplyPressure'},

  },
  {
    title: '二回压力',
    dataIndex: 'pressureTwice',
    sorter: false,
    ellipsis: false,
    align: "center",
    scopedSlots: {customRender: 'pressureTwice'},

  },
  {
    title: '一网瞬时流量',
    dataIndex: 'instantaneousTraffic',
    sorter: false,
    ellipsis: false,
    align: "center",
    scopedSlots: {customRender: 'instantaneousTraffic'},

  },
  {
    title: '一网瞬时热量',
    dataIndex: 'instantaneousHeat',
    sorter: false,
    align: "center",
    ellipsis: false,
    scopedSlots: {customRender: 'instantaneousHeat'},

  },
  {
    title: '电动阀开度',
    dataIndex: 'electricValveOpening',
    sorter: false,
    align: "center",
    ellipsis: false,
    scopedSlots: {customRender: 'electricValveOpening'},

  },
  {
    title: '循环泵频率',
    dataIndex: 'circulatingPumpFrequency',
    sorter: false,
    ellipsis: false,
    align: "center",
    scopedSlots: {customRender: 'circulatingPumpFrequency'},

  }, {
    title: '补水泵频率',
    dataIndex: 'makeupPumpFrequency',
    sorter: true,
    ellipsis: false,
    align: "center",
    scopedSlots: {customRender: 'makeupPumpFrequency'},
  },
  // {
  //   title: '操作',
  //   dataIndex: 'action',
  //   width: '150px'
  // }
]

export default {
  name: "heatData",
  data() {
    return {
      data: [],
      gaps:[2,4,5,10,20],
      nowStation: '',
      formDate: {station: "中北大学站",gap:5},
      stations: [],
      pagination: {pageSize: 20},
      loading: false,
      moment,
      columns,
      locale,
    };
  },
  mounted() {
    const that = this;
    this.$http.get("getStationsRunning").then(function (res) {
      that.stations = res.data.result;
    }).catch()
    this.fetch({
      "station": this.formDate.station,
      "pageSize": 20,
      "cur": 1,
      start: moment().subtract(1, 'hour').format("YYYY-MM-DD HH:mm:ss"),
      end: moment().format("YYYY-MM-DD HH:mm:ss")
    });
  },
  methods: {
    // moment,
    handleTableChange(pagination, filters, sorter) {
      console.log(pagination);
      const pager = {...this.pagination};
      pager.current = pagination.current;
      this.pagination = pager;
      this.fetch({
        pageSize: pagination.pageSize,
        cur: pagination.current,
        sortField: sorter.field,
        sortOrder: sorter.order,
        start: this.formDate.start,
        end: this.formDate.end,
        station: this.formDate.station,
        ...filters,
      });
    },
    fetch(params = {}) {
      console.log('params:', params);
      this.loading = true;
      this.$http.get("/getRecord", {params: params}).then(data => {
        console.log(data)
        const pagination = {...this.pagination};
        // Read total count from server
        // pagination.total = data.totalCount;
        pagination.total = data.data.result.total;
        this.loading = false;
        this.data = data.data.result.records;
        this.pagination = pagination;
      }).catch()
    },
    handleSubmit() {
      this.loading = true;
      this.fetch({
        station: this.formDate.station,
        pageSize: 20,
        cur: 1,
        start: this.formDate.start,
        end: this.formDate.end
      });
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
    filterTime(time) {
      return moment(time).format("YYYY-MM-DD HH:mm:ss");
    },
    onChangeDate(date) {
      this.formDate.start = date[0].format("YYYY-MM-DD HH:mm:ss");
      this.formDate.end = date[1].format("YYYY-MM-DD HH:mm:ss");
      console.log(this.formDate)
    },
    disabledRangeTime(_, type) {
      if (type === 'start') {
        return {
          // disabledHours: () => this.range(0, 60).splice(4, 20),
          // disabledMinutes: () => this.range(30, 60),
          // disabledSeconds: () => [55, 56],
        };
      }
      return {
        // disabledHours: () => this.range(0, 60).splice(20, 4),
        // disabledMinutes: () => this.range(0, 31),
        // disabledSeconds: () => [55, 56],
      };
    },
    disabledDate(current) {
      // Can not select days before today and today
      return current && current > moment().endOf('day');
    },
  },
};
</script>

<style scoped>
.gutter-example >>> .ant-row > div {
  background: transparent;
  border: 0;
}

.gutter-box {
  background: #00a0e9;
  padding: 5px 0;
}
</style>
