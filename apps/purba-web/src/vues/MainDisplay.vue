<template>
    <div id="app" class="backgroundContainer">
      <div class="phoneContainer">
          <div class="status-bar">
            <p>{{ currentTime }}</p>
            <img id=battery src = '../assets/battery.png'/>
          </div>
          <div class=withBack>
            <img class="logoPoorba PoorbaMove" src = '../assets/푸르바.png'>
            <RouterLink to='/cr'><img class=backImg src='../assets/뒤로가기.png'/></RouterLink>
          </div>

          <div class="btnBundles">
            <RouterLink to="/cm">
            <button class="mainBtn">
                <img class=mainIcon src='../assets/카메라.png'/>
                <span class="mainTxt">카메라</span>
            </button>
            <button class="mainBtn">
                <img class=mainIcon src='../assets/갤러리.png'/>
                <span class="mainTxt">갤러리</span>
            </button>
            </RouterLink>
            
          </div>

          <div class="btnBundles">
            <button class="mainBtn">
                <img class=mainIcon src='../assets/일정관리.png'/>
                <span class="mainTxt">일정관리</span>
            </button>
            <button class="mainBtn">
                <img class=mainIcon src='../assets/대화.png'/>
                <span class="mainTxt">AI 대화</span>
            </button>
          </div>

          <div class="btnBundles">
            <button class="mainBtn">
                <img class=mainIcon src='../assets/공지사항.png'/>
                <span class="mainTxt">공지사항</span>
            </button>
            <button class="mainBtn">
                <img class=mainIcon src='../assets/환경설정.png'/>
                <span class="mainTxt">환경설정</span>
            </button>
          </div>
          
        </div>
    </div>
</template>
  
<script>
  
  export default {
    name: 'MainDisplay',
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
  