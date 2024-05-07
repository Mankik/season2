<template>
    <div id="app" class="backgroundContainer">
      <div class="phoneContainer centerAlign">
          <div class="status-bar">
            <p>{{ currentTime }}</p>
            <img id=battery src = '../assets/battery.png'/>
          </div>
          <img class=logoPoorba src = '../assets/푸르바.png'>
          <h2>전화번호</h2>
          <form action="#" method="post">
              <input type="text" name="username" class="inputBar" placeholder="전화번호를 입력해주세요" required>
              <input type="submit" id="color1" class="longCLickBtn" value="로그인하기">
          </form>
          <form class="bottomFirst">
            <h3 class="subTitleLeft">회원이 아니신가요?</h3>
            <router-link to="/cr"><input type="submit" class="longCLickBtn" value="회원가입하기"></router-link>
          </form>
        </div>
    </div>
</template>
  
<script>
  
  export default {
    name: 'FirstIn',
    components: {
    },
    data() {
      return { currentTime: null };
    },
    mounted() {
      this.updateTime();
      this.timer = setInterval(this.updateTime, 60000);
    },
    methods: {
      updateTime() {
        const now = new Date();
        const hours = now.getHours().toString().padStart(2, '0');
        const minutes = now.getMinutes().toString().padStart(2, '0');
        this.currentTime = `${hours}:${minutes}`;
      }
    }
  }
</script>
  
<style>
  @import '../css/index.css';

</style>
  