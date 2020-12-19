<template>
  <div>
    <a-row :gutter="[16,16]">
      <a-col :span="3">
        <a-button type="primary" @click="showModal" style="margin-left: 20px">增加用户</a-button>
      </a-col>
    </a-row>
    <a-table :columns="columns" :data-source="tableData" style="margin-top: 20px">
      <div slot="op" slot-scope="op">
        <a-space>
          <a-button type="primary" @click="editUser(op)">修改</a-button>
          <a-button type="danger" @click="deleteUser(op)">删除</a-button>
        </a-space>
      </div>
    </a-table>

    <a-modal
        title="增加用户"
        :visible="visible"
        okText="添加"
        :confirm-loading="confirmLoading"
        @ok="handleOk()"
        @cancel="handleCancel">
      <a-form-model ref="addref" :layout="form.layout" :model="form" :rules="rules">
        <a-form-model-item label="用户名" prop="username" required>
          <a-input v-model="form.username" placeholder="请输入用户名"/>
        </a-form-model-item>
        <a-form-model-item label="密码" required prop="password">
          <a-input type="password" v-model="form.password" placeholder="输入密码"/>
        </a-form-model-item>
        <a-form-model-item label="确认密码" required prop="password2">
          <a-input type="password" v-model="form.password2" placeholder="确认密码"/>
        </a-form-model-item>
        <a-form-model-item label="姓名" required prop="name">
          <a-input v-model="form.name" placeholder="请输入姓名"/>
        </a-form-model-item>
        <a-form-model-item label="所属供热站" required prop="belong">
          <!--          <a-input v-model="form.belong" placeholder="请输入供热站"/>-->
          <a-select default-value="中北大学站" v-model="form.belong">
            <a-select-option v-for="i in stations" :key="i" :value="i">
              {{ i }}
            </a-select-option>
          </a-select>
        </a-form-model-item>
        <a-form-model-item label="联系电话" required prop="phone">
          <a-input v-model="form.phone" placeholder="请输入联系电话"/>
        </a-form-model-item>
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
        <a-form-model-item label="用户名" prop="username" required>
          <a-input v-model="edit.username" placeholder="请输入用户名"/>
        </a-form-model-item>
        <a-form-model-item label="密码">
          <a-input type="password" v-model="edit.password" placeholder="输入密码"/>
        </a-form-model-item>
        <a-form-model-item label="姓名" required prop="name">
          <a-input v-model="edit.name" placeholder="请输入姓名"/>
        </a-form-model-item>
        <a-form-model-item label="所属供热站" required prop="belong">
          <!--          <a-input v-model="edit.belong" placeholder="请输入供热站"/>-->
          <a-select default-value="中北大学站" v-model="edit.belong">
            <a-select-option v-for="i in stations" :key="i" :value="i">
              {{ i }}
            </a-select-option>
          </a-select>
        </a-form-model-item>
        <a-form-model-item label="联系电话" required prop="phone">
          <a-input v-model="edit.phone" placeholder="请输入联系电话"/>
        </a-form-model-item>
      </a-form-model>
    </a-modal>
  </div>


</template>

<script>
import md5 from "md5";

const columns = [
  {
    title: 'ID',
    dataIndex: 'id',
    key: 'id',
  },
  {
    title: '用户名',
    dataIndex: 'username',
    key: 'username',
  },
  {
    title: '姓名',
    key: 'name',
    dataIndex: 'name',
  },
  {
    title: '联系电话',
    key: 'phone',
    dataIndex: 'phone',
  },
  {
    title: '所属供热站',
    key: 'belong',
    dataIndex: 'belong',
  }, {
    title: '操作',
    scopedSlots: {customRender: 'op'},
  },
];
export default {
  name: "user",
  data() {
    return {
      visible: false,
      visible2: false,
      form: {},
      edit: {},
      stations: [],
      rules: {
        username: [{required: true, trigger: 'change', message: "请输入必填项"}],
        name: [{required: true, trigger: 'change', message: "请输入必填项"}],
        password: [{required: true, trigger: 'change', message: "请输入必填项"}],
        password2: [{required: true, trigger: 'change', message: "请输入必填项"}],
        belong: [{required: true, trigger: 'change', message: "请输入必填项"}],
        phone: [{required: true, trigger: 'change', message: "请输入必填项"}],
      },
      confirmLoading: false,
      tableData: [],
      columns,
    };
  },
  mounted() {
    const that = this;
    this.$http.get("getAllUsers").then(function (res) {
      that.tableData = res.data.result;
    }).catch()
    this.$http.get("getStationsRunning").then(function (res) {
      that.stations = res.data.result;
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
        if (this.form.password !== this.form.password2) {
          that.$message.error("两次密码不一致！，请检查！")
          return
        }
        let pass = this.form.password;
        for (let i = 0; i < 10; i++) {
          pass = md5(pass)
        }
        this.form.password=pass;
        this.$http.post("addUsers", this.form).then(function (res) {
          that.$message.success(res.data.result);
          that.visible = false;
          that.$http.get("getAllUsers").then(function (res) {
            that.tableData = res.data.result;
          }).catch()
        }).catch()
      })
    },
    handleCancel() {
      this.visible = false;
      const that = this;
      this.$http.get("getAllUsers").then(function (res) {
        that.tableData = res.data.result;
      }).catch()
    },
    handleOk2() {
      this.$refs.edref.validate(valid => {
        if (!valid) {
          return
        }
        const that = this;
        //加密传输
        let pass = this.edit.password;
        for (let i = 0; i < 10; i++) {
          pass = md5(pass)
        }
        this.edit.password=pass;
        this.$http.post("updateUsers", that.edit).then(function (res) {
          that.$message.success(res.data.result);
          that.visible2 = false;
        })
      })
    },
    handleCancel2() {
      this.visible2 = false;
      const that = this;
      this.$http.get("getAllUsers").then(function (res) {
        that.tableData = res.data.result;
      }).catch()
    },
    deleteUser(row) {
      const that = this;
      this.$http.post("deleteUsers", row).then(function (res) {
        that.$message.success(res.data.result);
        that.$http.get("getAllUsers").then(function (res) {
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
