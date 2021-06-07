<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
body {
  font-family: Arial, Helvetica, sans-serif;
  margin: 0;
}

html {
  box-sizing: border-box;
}

*, *:before, *:after {
  box-sizing: inherit;
}

.column {
  float: left;
  width: 33.3%;
  margin-bottom: 16px;
  padding: 0 8px;
}

.card {
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
  margin: 8px;
}

.about-section {
  padding: 50px;
  text-align: center;
  background-color: #3B6B37;
  color: white;
}

/* 3A5A46 */

.container {
  padding: 0 16px;
}

.container::after, .row::after {
  content: "";
  clear: both;
  display: table;
}

.title {
  color: grey;
}

.button {
  border: none;
  outline: 0;
  display: inline-block;
  padding: 8px;
  color: white;
  background-color: #000;
  text-align: center;
  cursor: pointer;
  width: 100%;
}

.center {
  display: block;
  margin-left: auto;
  margin-right: auto;
  width: 50%;
}

.button:hover {
  background-color: #555;
}

@media screen and (max-width: 650px) {
  .column {
    width: 100%;
    display: block;
  }
}
</style>
</head>
<body>

<div class="about-section">
  <h1>Welcome to Green Leaves Bank</h1>
  <p> Easily manage your bank accounts and finances online</p>
</div>

<h2 style="text-align:center"  style="font-family:Times" >Popular Services</h2>
<div class="row">
  <div class="column">
    <div class="card">
      <a class="welcome-Page" href="login"><img alt="Money Transfer logo" class="center" src="<c:url value="/resources/img/MoneyTrans.png" />  "width="120" height="120"  ></a>
    
<!--       <img src="/w3images/team2.jpg"  style="width:100%">
 -->      <div class="container">
        <h2 style="font-family:Times New Roman" >Money Transfer</h2>
        <!-- <p class="title">Art Director</p>
        <p>Some text that describes me lorem ipsum ipsum lorem.</p>
        <p>mike@example.com</p> -->
        <!-- <p><button class="button">Contact</button></p> -->
      </div>
    </div>
  </div>

  <div class="column">
    <div class="card">
      <a class="welcome-Page" href="login"><img alt="Bill Payment logo" class="center" src="<c:url value="/resources/img/BillPayments.png" />  "width="120" height="120"  ></a>
    
<!--       <img src="/w3images/team2.jpg"  style="width:100%">
 -->      <div class="container">
        <h2 style="font-family:Times New Roman" >Bill Payment</h2>
        <!-- <p class="title">Art Director</p>
        <p>Some text that describes me lorem ipsum ipsum lorem.</p>
        <p>mike@example.com</p> -->
        <!-- <p><button class="button">Contact</button></p> -->
      </div>
    </div>
  </div>
  
  <div class="column">
    <div class="card">
         <a class="welcome-Page" href="login"><img alt="Bill Payment logo" class="center" src="<c:url value="/resources/img/AccountBalance.png" />  "width="120" height="120"  ></a>
    
<!--       <img src="leafIcon.jpg"  style="width:100%">
 -->      <div class="container">
        <h2 style="font-family:Times New Roman" >Account Balance</h2>
        <!-- <p class="title">Designer</p>
        <p>Some text that describes me lorem ipsum ipsum lorem.</p>
        <p>john@example.com</p> -->
        <!-- <p><button class="button">Contact</button></p> -->
      </div>
    </div>
  </div>


<div class="column">
    <div class="card">
         <a class="welcome-Page" href="login"><img alt="Loan Apply logo" class="center" src="<c:url value="/resources/img/Loan.png" />  "width="120" height="120"  ></a>
    
<!--       <img src="leafIcon.jpg"  style="width:100%">
 -->      <div class="container">
        <h2 style="font-family:Times New Roman" >Apply For Loan</h2>
        <!-- <p class="title">Designer</p>
        <p>Some text that describes me lorem ipsum ipsum lorem.</p>
        <p>john@example.com</p> -->
        <!-- <p><button class="button">Contact</button></p> -->
      </div>
    </div>
  </div>
  
  <div class="column">
    <div class="card">
         <a class="welcome-Page" href="login"><img alt="Bill Payment logo" class="center" src="<c:url value="/resources/img/credit.png" />  "width="120" height="120"  ></a>
    
<!--       <img src="leafIcon.jpg"  style="width:100%">
 -->      <div class="container">
        <h2 style="font-family:Times New Roman" >Apply For Credit Card</h2>
        <!-- <p class="title">Designer</p>
        <p>Some text that describes me lorem ipsum ipsum lorem.</p>
        <p>john@example.com</p> -->
        <!-- <p><button class="button">Contact</button></p> -->
      </div>
    </div>
  </div>
</div>


</body>
</html>