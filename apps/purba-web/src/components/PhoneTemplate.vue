<template>
    <div id="app" class="backgroundContainer">
      <div class="phoneContainer">
          <div class="status-bar">
            <p>{{ currentTime }}</p>
            <img id=battery src = '../assets/battery.png'/>
          </div>
        </div>
    </div>
</template>

<script>
/*뷰 추가시 name을 뷰 이름에 맞게 변경해서 사용 */
  export default {
    name: 'PhoneTemplate',
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