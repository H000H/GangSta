 /*change banner*/
 function changeImg(){
  var className = $('#banner').attr("class");
  switch(className){
    case "login-banner-2":
    $('#banner').removeClass();
    $('#banner').addClass("login-banner-0");
    break;
    case "login-banner-0":
    $('#banner').removeClass();
    $('#banner').addClass("login-banner-1");
    break;
    case "login-banner-1":
    $('#banner').removeClass();
    $('#banner').addClass("login-banner-2");
    break;
  }
}

/*ready*/
$(function(){
  var loginUrl="http://localhost:8080/GangSta/welcome.html";
  //banner change
  setInterval(changeImg,3000);
  /*表单时间*/
  //登录
  $('#login').on('click',function(event){
	event.preventDefault();
    let email = $('#email').val();
    let pwd = $('#password').val();
    $.ajax({
      type:"POST",
      url:"LoginServlet",
      datatype:"json",
      async: false,
      data:{
        "email":email,
        "password":pwd
      },
      success:function(data){
      	console.log(data);
      	let result = JSON.parse(data);
      	if(result.email == "用户不存在"){
      		alert("用户不存在");
      	}else if(result.email == "用户密码错误"){
      		alert("用户密码错误");
      	}else if(result.email == "用户已经登录，不能重复登录！"){
      		alert("用户已经登录，不能重复登录！");
      	}else{
      		window.location.href=loginUrl;
      	}
      },
      error:function(){
        console.log("登录通信失败");
      }
    });  
  });
  // 发送验证码
  $('#send').on('click',function(){
	  event.preventDefault();
    let email = $("#r_email").val();
    $.ajax({
      type:"POST",
      url:"SaveCodeServlet",
      datatype:"json",
      async: true,
      data:{"email":email},
      success:function(data){
    	console.log(data);
      },
      error:function(){}
    });
  });
  // 创建帐号
  $('#create').on('click',function(){
	  event.preventDefault();
      let email = $('#r_email').val();
      let username = $('#r_username').val();
      let pwd = $('#r_password').val();
      let code = $('#r_code').val();
      //create account
      $.ajax({
        type:"POST",
        url:"RegistServlet",
        datatype:"json",
        async: true,
        data:{
          "email":email,
          "username":username,
          "password":pwd,
          "code":code
        },
        success:function(data){
        	console.log(data[1]);
            if (data[1] == "1") {
            	window.location.href=loginUrl;
            }else if(data[1] == "2"){
            	$("#login_form").removeClass('shake_effect');  
                setTimeout(function(){
                  $("#login_form").addClass('shake_effect');
                  alert("注册email已经被注册！");
                },1); 
            }else if(data[1] == "3"){
            	$("#login_form").removeClass('shake_effect');  
                setTimeout(function(){
                  $("#login_form").addClass('shake_effect');
                  alert("验证码错误！");
                },1);
            }
        },
        error:function(data){
          let result = JSON.stringify(data);
          $("#login_form").removeClass('shake_effect');  
          setTimeout(function(){
            $("#login_form").addClass('shake_effect');
            alert(result.state);
          },1); 
        }
      });  

    });
  /*表单变化*/
  $('.message a').click(function () {
    $('form').animate({
      height: 'toggle',
      opacity: 'toggle'
    }, 'slow');
  });
  
});