<template>
    <div id="app" class="body-container">
      <div class="login-container">
          <div class="status-bar">
            <p>12:34</p>
            <img id=battery src = '../assets/battery.png'/>
          </div>
          <img class=mainimg src = '../assets/푸르바.png'>
          <h3 class="sojemok">이름</h3>
          <form action="#" method="post">
              <input type="text" name="username" placeholder="이름을 입력해주세요" required>
          </form>
          <h3 class="sojemok">전화번호</h3>
          <form action="#" method="post">
              <input type="text" name="username" placeholder="전화번호를 입력해주세요" required>
          </form>
          <div class="withbtncontainer">
            <h2>인증번호</h2>
            <button>인증번호 받기</button>
          </div>
          <form action="#" method="post">
              <input type="text" name="username" placeholder="인증번호를 입력해주세요" required>
          </form>
          <router-link to="/"><input type="submit" class="btn2" value="회원가입하기"></router-link>
        </div>
        <router-view></router-view>
    </div>
  </template>
  
  <script>
  
  export default {
    name: 'CreateAccount',
    components: {
    }
  }
  </script>
  
  <style>
  
  .body-container {
    margin: 0;
    padding: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-color: #E5E5E5;
    box-sizing: border-box;
  }*/
  
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
  
  .login-container {
    background-color: white;
    border-radius: 10px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    text-align: center;
    height: 640px;
    width: 360px;
  }
  .withbtncontainer{
    display:flex;
    align-items: center;
  }    
  .withbtncontainer h2 {
    flex-grow:1;
    margin-right: 10px;
    color: #1F4E79;
  }          
  .withbtncontainer button {
    background-color: #2EA211;
    color: white;
    border-style : none;
    border-radius: 5px;
    margin: 30px 20px 0 0;

  }          
  
  .sojemok {
    text-align: left;
    margin: 10px auto 10px 20px;
    color: black;
  }    
  
  .login-container input[type="text"] {
    width: calc(100% - 20px);
    padding: 10px;
    margin-bottom: 15px;
    border: 2px solid #1F4E79;
    border-radius: 5px;
    color: #1F4E79;
    box-sizing: border-box;
  }
  
  .bottom_form{
    margin-top:100px;
    align-items: flex-end;
  }
  
  .btn1{
    width: calc(100% - 20px);
    padding: 10px;
    border: none;
    cursor: pointer;
    transition: background-color 0.3s ease;
    color: white;
    background-color: #2E75B6;
    border-radius: 10px;
  }
  
   .btn2{
    width: calc(100% - 20px);
    padding: 10px;
    border: none;
    cursor: pointer;
    transition: background-color 0.3s ease;
    color: white;
    background-color: #1F4E79;
    border-radius: 10px;
    
  }
  
  .login-container input[type="submit"]:hover {
    background-color: #0056b3;
  }
  
  #app {
    font-family: Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
  }
  
  .mainimg{
    margin-top: 50px;
    width : max-content;
  }
  </style>
  