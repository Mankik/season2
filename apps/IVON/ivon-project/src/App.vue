<template>
  <div id="app" class="body-container">
    <div class="login-container">
        <div class="status-bar">
          <p>12:34</p>
          <img id=battery src = './assets/battery.png'/>
        </div>
        <img class=mainimg src = './assets/푸르바.png'>
        <h2>전화번호</h2>
        <form action="#" method="post">
            <input type="text" name="username" placeholder="전화번호를 입력해주세요" required>
            <input type="submit" class="btn1" value="로그인하기">
        </form>
        <form class="bottom_form">
          <h3>회원이 아니신가요?</h3>
          <router-link to="/CreateAccount"><input type="submit" class="btn2" value="회원가입하기"></router-link>
        </form>
      </div>
      <router-view></router-view>
  </div>
</template>

<script>

export default {
  name: 'App',
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

.login-container {
  background-color: white;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  text-align: center;
  height: 640px;
  width: 360px;
}
           
.login-container h2 {
  text-align: left;
  margin: 50px auto 20px 20px;
  color: #1F4E79;
}          

form h3 {
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
