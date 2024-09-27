<template>
    <div id="app" class="backgroundContainer">
      <div class="phoneContainer">
        <div class="status-bar">
          <p>{{ currentTime }}</p>
          <img id=battery src = '../assets/battery.png'/>
        </div>
        <div class=withBack>
          <RouterLink to='/main'><img class=backImg src='../assets/뒤로가기.png'/></RouterLink>
        </div>

        <video class="cameraDisplay" ref="videoElement" autoplay></video>
        <button class="captureBtn" @click="capture">촬영</button>
        <canvas ref="canvas" style="display: none;"></canvas>
      </div>
    </div>
</template>

<script>
  export default {
    name: 'CameraFirst',
    components: {
    },
    data() {
      return { currentTime: null };
    },
    mounted() {
      this.updateTime();
      this.startCamera();
      this.timer = setInterval(this.updateTime, 60000);
    },
    methods: {
    async startCamera() {
      try {
        const stream = await navigator.mediaDevices.getUserMedia({ video: true });
        this.$refs.videoElement.srcObject = stream;
      } catch (err) {
        console.error("에러가 발생했습니다.", err);
      }
    },
    capture() {
      const video = this.$refs.videoElement;
      const canvas = this.$refs.canvas;
      canvas.width = 800;
      canvas.height = 600;
      canvas.getContext('2d').drawImage(video, 0, 0, canvas.width, canvas.height);
      
      // 이미지를 저장하거나 처리하기
      this.saveImage(canvas);
    },
    saveImage(canvas) {
      // 예: 이미지를 Base64 형식으로 변환
      const imageDataUrl = canvas.toDataURL('image/png');
      // 이미지 데이터를 사용하여 필요한 작업 수행 (예: 서버에 업로드, 로컬에 저장 등)
      console.log(imageDataUrl); // 실제 애플리케이션에서는 콘솔에 로그를 찍는 대신 이미지를 처리합니다.

      // a 태그를 생성하여 다운로드 링크를 구성
      const link = document.createElement('a');
      link.href = imageDataUrl;
      link.download = 'ivon_image.png'; // 다운로드될 파일의 이름 지정
      document.body.appendChild(link); // DOM에 링크 추가
      link.click(); // 클릭 이벤트를 발생시켜 다운로드
      document.body.removeChild(link); // 사용 후 링크 삭제
    },
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