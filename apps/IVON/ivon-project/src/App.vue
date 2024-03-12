<template>
  <div>
    <RouterLink to="/"><h2 class="ProjectName">IVON PROJECT</h2></RouterLink>
  </div>
  <RouterView/>
</template>

<script>

export default {
  name: 'App',
  components: {
  }
}
</script>

<style>

#app {
  font-family: Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

.ProjectName {
  margin : 0;
  color : #1F4E79;
  text-decoration-line: none;
}

</style>
