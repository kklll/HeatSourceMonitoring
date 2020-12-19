<template>
  <div>
  <graph ref="lswd"></graph>
  </div>
</template>

<script>
import Graph from "@/components/graph";
export default {
name: "test",
  components: {Graph},
  mounted() {
    console.log(this.$refs.lswd.option)
    this.$refs.lswd.option.legend.data=['a','b','c','d']
  }
}
</script>

<style scoped>

</style>
