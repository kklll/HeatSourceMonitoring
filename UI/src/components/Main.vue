<template>
  <a-layout id="components-layout-demo-side" style="min-height: 100vh">
    <a-layout-sider v-model="collapsed" collapsible>
      <div class="logo">
        <span style="color: brown">热源监控系统</span>
      </div>
      <a-menu theme="dark" :defaultOpenKeys="defaultOpenKeys" mode="inline">
        <a-sub-menu v-for="i in list" :key="i.index">
          <span slot="title">
            <a-icon type="down-circle"/>
            <span>{{ i.name }}</span>
          </span>
          <a-menu-item :to="x.path" v-for="x in i.lists" :key="x.index"
                       @click="saveState(x.index);$router.push('/'+x.path)">
            {{ x.name }}
          </a-menu-item>
        </a-sub-menu>
      </a-menu>
    </a-layout-sider>
    <a-layout>
      <a-layout-header style="background: #FFFFFF; padding: 0">
        <template>
          <a-page-header
              style="border: 1px solid rgb(235, 237, 240)"
              title="Back"
              @back="() => this.$router.back()"
          />
          <!--          sub-title="This is a subtitle"-->
        </template>
      </a-layout-header>
      <a-layout-content style="margin: 0 16px">
        <a-breadcrumb style="margin: 16px 0">
          <!--          <a-breadcrumb-item>User</a-breadcrumb-item>-->
          <!--          <a-breadcrumb-item>Bill</a-breadcrumb-item>-->
        </a-breadcrumb>
        <div :style="{ padding: '24px', background: '#fff', minHeight: '360px' }">
          <router-view></router-view>
        </div>
      </a-layout-content>
      <a-layout-footer style="text-align: center">
        Ant Design ©2018 Created by Ant UED
      </a-layout-footer>
    </a-layout>
  </a-layout>
</template>


<script>
export default {
  name: "Main",
  data: function () {
    return {
      collapsed: false,
      activePath: [],
      defaultOpenKeys: [],
      list: []
    }
  },
  methods: {
    onCollapse(collapsed, type) {
      console.log(collapsed, type);
    },
    onBreakpoint(broken) {
      console.log(broken);
    },
    toggleCollapse: function () {
      this.collapse = !this.collapse;
    },
    saveState: function (path) {
      window.sessionStorage.setItem("path", path);
      this.activePath = path;
    }
  },
  created() {
    const that = this;
    this.$http.get("getMenu").then(function (res) {
      that.list = res.data.result;
    }).catch()
    this.activePath = window.sessionStorage.getItem("path");
    this.defaultOpenKeys = window.sessionStorage.getItem("open");
  }
}
</script>

<style lang="less" scoped>
.a-header {
  background-color: #324A8B;
  display: flex;
  justify-content: space-between;
  padding-left: 0;
  align-items: center;
  color: #ffffff;
  font-size: 20px;

  > div {
    display: flex;
    align-items: center;

    span {
      margin-left: 15px;
    }
  }
}
#components-layout-demo-side .logo {
  height: 32px;
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  font-size: medium;
  //background: rgba(255, 255, 255, 0.2);
  margin: 16px;
}

.a-menu-item {
  align-content: center;
}

</style>
