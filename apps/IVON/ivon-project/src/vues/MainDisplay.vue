<template>
    <div id="app" class="body-container">
      <div class="maincontainer">
          <div class="status-bar">
            <p>12:34</p>
            <img id=battery src = '../assets/battery.png'/>
          </div>
          <div class=imgbox>
            <img class=mainimg src = '../assets/푸르바.png'>
            <RouterLink to='/cr'><img class=backimg src='../assets/뒤로가기.png'/></RouterLink>
          </div>
          <div class="btnGroup">
            <button class="mainBtn">
                <img class=mainIcon src='../assets/카메라.png'/>
                <span class="mainTxt">카메라</span>
            </button>
            <button class="mainBtn">
                <img class=mainIcon src='../assets/갤러리.png'/>
                <span class="mainTxt">갤러리</span>
            </button>
          </div>
          <div class="btnGroup">
            <button class="mainBtn">
                <img class=mainIcon src='../assets/일정관리.png'/>
                <span class="mainTxt">일정관리</span>
            </button>
            <button class="mainBtn">
                <img class=mainIcon src='../assets/대화.png'/>
                <span class="mainTxt">AI 대화</span>
            </button>
          </div>
          <div class="btnGroup">
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
    }
  }
  </script>
  
  <style>
  .maincontainer {
    background-color: white;
    border-radius: 10px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    height: 640px;
    width: 360px;
  }

  .btnGroup{
    margin-top: 20px;
    margin-left: 25px;
  }
  .mainBtn{
    margin-left: 30px;
    width:110px;
    height:110px;
    background-color: #1F4E79;
    position:relative;
    border-style: none;
    border-radius: 10px;
  }

  .mainIcon {
    width: 80px;
    height: 80px;
    margin-bottom: 20px;
    color: white;
    }

    .mainTxt {
    position: absolute;
    bottom: 0;
    text-align: center;
    left: 50%;
    transform: translateX(-50%);
    font-size: 18px;
    font-weight: bold;
    color: white;
    white-space: nowrap; /* 줄바꿈 제어 */
    }

  
  .body-container {
    margin: 0;
    padding: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-color: #E5E5E5;
    box-sizing: border-box;
  }
  
  #battery {
    width: 20px;
    height: 20px;
    color: white;
    margin-left: auto;
  }
  
  .status-bar {
    position: relative;
    top: 0;
    left: 0;
    width: 100%;
    height: 30px;
    background-color: #222222;
    color: white;
    display: flex;
    align-items: center;
    padding: 0 10px;
    box-sizing: border-box;
    z-index: 999; 
    }
  
  
  #app {
    font-family: Arial, sans-serif;
  }

  .imgbox{
    display: block;
    width:100%;
    height:auto;
  }
  .backimg{
    width: 30px;
    height: 30px;
    margin: 10px 10px 0 0;
    float: right;
    color: black;
  }
  
  .maincontainer .imgbox .mainimg{
    margin-top: 30px;
    margin-left: 90px;
    width : max-content;
  }
  </style>
  