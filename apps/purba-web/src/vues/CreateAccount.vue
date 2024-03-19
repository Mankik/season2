<template>
    <div id="app" class="backgroundContainer">
      <div class="phoneContainer centerAlign">
          <div class="status-bar">
            <p>{{ currentTime }}</p>
            <img id=battery src = '../assets/battery.png'/>
          </div>
          <div class=withBack>
            <img class=logoPoorba src = '../assets/푸르바.png'>
            <RouterLink to='/'><img class=backImg src='../assets/뒤로가기.png'/></RouterLink>
          </div>


          <h3 class="subTitleLeft">이름</h3>
          <form action="#" method="post">
              <input type="text" name="username" class="inputBar" placeholder="이름을 입력해주세요" required>
          </form>
          <h3 class="subTitleLeft">전화번호</h3>
          <form action="#" method="post">
              <input type="text" name="username" class="inputBar" placeholder="전화번호를 입력해주세요" required>
          </form>

          <div class="authContainer">
            <h2>인증번호</h2>
            <router-link to='/main'><button>인증번호 받기</button></router-link>
          </div>
          <form action="#" method="post">
              <input type="text" name="username" class="inputBar" placeholder="인증번호를 입력해주세요" required>
          </form>
          <input type="submit" class="longCLickBtn" id="color2" value="회원가입하기">
        </div>
    </div>
</template>
  
<script>
  export default {
    name: 'CreateAccount',
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
  