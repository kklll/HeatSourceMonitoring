<template>
  <div>
    <a-row :gutter="[16,16]">
      <a-col :span="3">
        <a-button type="primary" @click="showModal" style="margin-left: 20px">增加供热站</a-button>
      </a-col>
    </a-row>
    <a-table :columns="columns" :data-source="tableData" style="margin-top: 20px">
      <div slot="op" slot-scope="op">

        <a-space>
          <a-button type="primary" @click="editUser(op)">修改</a-button>
          <a-button type="danger" @click="deleteUser(op)">删除</a-button>
        </a-space>
      </div>
      <span slot="stationState" slot-scope="stationState">
      <a-tag
          :color="stationState === '1' ? 'green' : 'red' ">
        {{ stationState === '1' ? '运行中' : '关闭' }}
      </a-tag>
    </span>
    </a-table>

    <a-modal
        title="增加供热站"
        :visible="visible"
        okText="添加"
        :confirm-loading="confirmLoading"
        @ok="handleOk()"
        @cancel="handleCancel">
      <a-form-model ref="addref" :layout="form.layout" :model="form" :rules="rules">
        <a-form-model-item label="供热站站名" prop="stationName" required>
          <a-input v-model="form.stationName" placeholder="请输入供热站站名"/>
        </a-form-model-item>
        <a-form-model-item label="供热站状态" required prop="stationState">
          <!--          <a-input v-model="form.stationState" placeholder="请输入供热站状态"/>-->
          <a-select default-value="运行中" style="width: 120px" v-model="form.stationState">
            <a-select-option value="1">
              运行中
            </a-select-option>
            <a-select-option value="0">
              关闭
            </a-select-option>
          </a-select>
        </a-form-model-item>
        <a-form-model-item label="供热站地址" required prop="stationAddress">
          <a-input v-model="form.stationAddress" placeholder="请输入供热站地址"/>
        </a-form-model-item>
        <!--        <a-form-model-item label="姓名" required prop="name">-->
        <!--          <a-input v-model="form.name" placeholder="请输入姓名"/>-->
        <!--        </a-form-model-item>-->
        <!--        <a-form-model-item label="所属供热站" required prop="belong">-->
        <!--          <a-input v-model="form.belong" placeholder="请输入供热站"/>-->
        <!--        </a-form-model-item>-->
        <!--        <a-form-model-item label="联系电话" required prop="phone">-->
        <!--          <a-input v-model="form.phone" placeholder="请输入联系电话"/>-->
        <!--        </a-form-model-item>-->
      </a-form-model>
    </a-modal>
    <a-modal
        title="修改用户信息"
        :visible="visible2"
        okText="修改"
        :confirm-loading="confirmLoading"
        @ok="handleOk2()"
        @cancel="handleCancel2">
      <a-form-model ref="edref" :layout="form.layout" :model="edit" :rules="rules">
        <a-form-model-item label="供热站站名" prop="stationName" required>
          <a-input v-model="edit.stationName" placeholder="请输入供热站站名"/>
        </a-form-model-item>
        <a-form-model-item label="供热站状态" required prop="stationState">
          <!--          <a-input v-model="edit.stationState" placeholder="请输入供热站状态"/>-->
          <a-select default-value="运行中" style="width: 120px" v-model="edit.stationState">
            <a-select-option value="1">
              运行中
            </a-select-option>
            <a-select-option value="0">
              关闭
            </a-select-option>
          </a-select>
        </a-form-model-item>
        <a-form-model-item label="供热站地址" required prop="stationAddress">
          <a-input v-model="edit.stationAddress" placeholder="请输入供热站地址"/>
        </a-form-model-item>
        <!--        <a-form-model-item label="联系电话" required prop="phone">-->
        <!--          <a-input v-model="edit.phone" placeholder="请输入联系电话"/>-->
        <!--        </a-form-model-item>-->
      </a-form-model>
    </a-modal>
  </div>


</template>

<script>
const columns = [
  {
    title: 'ID',
    dataIndex: 'id',
    key: 'id',
  },
  {
    title: '供热站名称',
    dataIndex: 'stationName',
    key: 'stationName',
  },
  {
    title: '供热站位置',
    key: 'stationAddress',
    dataIndex: 'stationAddress',
  },
  {
    title: '供热站状态',
    key: 'stationState',
    dataIndex: 'stationState',
    scopedSlots: {customRender: 'stationState'},
  },
  {
    title: '操作',
    scopedSlots: {customRender: 'op'},
  },
];
export default {
  name: "manage",
  data() {
    return {
      visible: false,
      visible2: false,
      form: {stationState: "运行中"},
      edit: {},
      rules: {
        stationName: [{required: true, trigger: 'change', message: "请输入必填项"}],
        stationState: [{required: true, trigger: 'change', message: "请输入必填项"}],
        stationAddress: [{required: true, trigger: 'change', message: "请输入必填项"}],
        // password: [{required: true, trigger: 'change', message: "请输入必填项"}],
        // password2: [{required: true, trigger: 'change', message: "请输入必填项"}],
        // belong: [{required: true, trigger: 'change', message: "请输入必填项"}],
        // phone: [{required: true, trigger: 'change', message: "请输入必填项"}],
      },
      confirmLoading: false,
      tableData: [],
      columns,
    };
  },
  mounted() {
    const that = this;
    this.$http.get("getAllStationInfo").then(function (res) {
      that.tableData = res.data.result;
    }).catch()
  },
  methods: {
    showModal() {
      this.visible = true;
    },
    handleOk() {
      this.$refs.addref.validate(valid => {
        if (!valid) {
          return
        }
        const that = this;
        this.$http.post("addStation", this.form).then(function (res) {
          that.$message.success(res.data.result);
          that.visible = false;
          that.$http.get("getAllStationInfo").then(function (res) {
            that.tableData = res.data.result;
          }).catch()
        }).catch()
      })
    },
    handleCancel() {
      this.visible = false;
      const that=this;
      this.$http.get("getAllStationInfo").then(function (res) {
        that.tableData = res.data.result;
      }).catch()
    },
    handleOk2() {
      this.$refs.edref.validate(valid => {
        if (!valid) {
          return
        }
        const that = this;
        this.$http.post("updateStation", that.edit).then(function (res) {
          that.$message.success(res.data.result);
          that.visible2 = false;
        })
      })
    },
    handleCancel2() {
      this.visible2 = false;
      const that=this;
      this.$http.get("getAllStationInfo").then(function (res) {
        that.tableData = res.data.result;
      }).catch()
    },
    deleteUser(row) {
      const that = this;
      this.$http.post("deleteStation", row).then(function (res) {
        that.$message.success(res.data.result);
        // that.visible2=false;
        that.$http.get("getAllStationInfo").then(function (res) {
          that.tableData = res.data.result;
        }).catch()
      })
    },
    editUser(row) {
      const that = this;
      that.visible2 = true;
      that.edit = row;
    }
  },

}
</script>

<style scoped>

</style>
