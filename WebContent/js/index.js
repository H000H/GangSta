var GG = {
"kk":function(mm){
         // alert(mm);
  }
}

function renderRoot(data){
  var tbody = "";
  var list = data.list;
  var length = list.length;
  var i = 0;
  $("content-display thead tr").html("<td>E-mail</td><td>用户名</td><td>用户等级</td><td>操作</td><td></td><td></td>");
  for(i;i<length;i++){
    tbody += "<tr><td>"+list[i].email+"</td><td>"+list[i].name+"</td><td>"+list[i].identity+"</td><td><span class='root-check' title='查看'></td><td><span class='root-delete' title='删除'></span></td><td><span class='root-approve' title='批准'></span></td></tr>";
  }
  $(".content-display tbody").html(tbody);
  //查看用户所有文件
  $('.root-check').on('click',function(){
    var email = this.parentNode.parentNode.firstChild.innerHTML;
    $.ajax({
      type:"POST",
      url:"AdminSelectPersonFilesServlet",
      datatype:"json",
      async: true,
      data:{"email":email,"page":1},
      success:function(data){
    	console.log(data);
    	let result = JSON.parse(data);
        //进入模板2
        $(".content-display").removeClass("onflex");
        $(".content-display1").addClass("onflex");
        var $tab = $('.content-display1');
        let page = result.pagenumber;
        $("#page1").initPage(10*page,1,GG.kk);
        renderUser($tab,result);
      },
      error:function(){
        alert("进入用户文件夹失败");
      }
    });
  });
  //删除用户
  $('.root-delete').on('click',function(){
    var email = this.parentNode.parentNode.firstChild.innerHTML;
    $.ajax({
      type:"POST",
      url:"DeletePersonServlet",
      datatype:"json",
      async: true,
      data:{"email":email},
      success:function(data){
    	console.log(data);
        alert(data);
      },
      error:function(){
        alert("删除用户失败");
      }
    });
  });
  //批准申请
  $('.root-approve').on('click',function(){
	    var email = this.parentNode.parentNode.firstChild.innerHTML;
	    $.ajax({
	      type:"POST",
	      url:"AgreeForVipServlet",
	      datatype:"json",
	      async: true,
	      data:{"email":email},
	      success:function(data){
	      	console.log(data);
	        alert(data);
	      },
	      error:function(){
	        alert("批准失败");
	      }
	    });
	  });
//公告3
  $('#public').on('click',function(){
/*    var rank = $('.vip').attr("data-rank");*/
      $('.noticeBar1').removeClass("show");
      $('.noticeBar2').removeClass("show");
      $('.noticeBar').css({"display":"block"});
      $('.noticeBar3').addClass('show');
      $('#notice-public').on('click',function(){
    	  event.preventDefault();
    	  var title = $('#public-title').val();
          var content = $('#public-content').val();
          var author = $('#username span').val();
          $.ajax({
            type:"POST",
            url:"InsertNoticeServlet",
            datatype:"json",
            async:true,
            data:{"title":title,"content":content,"author":author},
            success:function(data){
              console.log(data);
              alert(data);
            },
            error:function(){}
          }); 
      });
  });
}

function renderUser($tab,data){
  var $model = $tab;
  var list = data.list;
  var length = list.length;
  var tbody = "";
  var i = 0;
  var rank = $('.vip').attr('data-rank');
  if(rank != "-1"){
	  $tab.find('#fanhui').css({"display":"none"});
  }
  $model.find('thead').find('tr').html("<td>文件名</td><td>大小</td><td>时间</td><td>操作</td><td></td><td></td>");
  for(i;i<=length-1;i++){
    tbody += "<tr data-id='"+list[i].fileid+"'><td>"+list[i].filename+"</td><td>"+list[i].size+"</td><td>"+list[i].update+"</td><td><span class='user-share' title='分享'></span></td><td><a class='user-download' href='DownLoadFileServlet?fileid="+list[i].fileid+"' title='下载'></a></td><td><span class='user-delete' title='删除'></span></td></tr>";
  }
  $model.find('tbody').html(tbody);
  //用户分享
  $('.user-share').on('click',function(){
	var fileid = $(this.parentNode.parentNode).attr('data-id');
    $.ajax({
      type:"POST",
      url:"GetFileVerifyServlet",
      datatype:"json",
      async: true,
      data:{"fileid":fileid},
      success:function(data){
      console.log(data);
        alert("分享链接："+data);
      },
      error:function(){
        alert("文件分享失败");
      }
    });
  });
  //文件删除
  $('.user-delete').on('click',function(){
	   var fileid = $(this.parentNode.parentNode).attr('data-id');
	    $.ajax({
	      type:"POST",
	      url:"DeleteFileServlet",
	      datatype:"json",
	      async: true,
	      data:{"fileid":fileid},
	      success:function(data){
	    	  console.log(data);
	    	  alert("删除成功");
	      },
	      error:function(){
	        alert("文件下载失败");
	      }
	    });
	  });  
}

function renderNotice1(data){
  var $tbody = $('.noticeBar1 table tbody');
  var tbody = "";
  var list = data.content;
  var i = 0;
  for(i;i<list.length;i++){
	  tbody += "<tr data-content='"+i+"'><td>"+list[i].title+"</td><td>"+list[i].author+"</td><td>"+list[i].updateDate+"</td></tr>"
  }
  $tbody.html(tbody);
}
function renderNotice2(id,data){
  var list = data;
  var i = id;
  var $h3 = $('.noticeBar2').find('h3');
  var $article = $('.noticeBar2').find('article');
  var $a = $('.noticeBar2').find('a');
  $h3.html("");
  $article.html("");
  $a.html("");
  $h3.html(list[i].title);
  $article.html(list[i].content);
  $a.html(list[i].url);
}

$(document).ready(function(){
  /*public*/
  //个人信息悬停效果
  $('#username').mousemove(function(){
    $('.userInfo').css({"z-index":"101","top":"40px"});
  });
  $('#username').mouseleave(function(){
    $('.userInfo').css({"z-index":"-100","top":"25px"});
  });
  //页面初始化
  $.ajax({
    type:"GET",
    url:"InitialServlet",
    datatype:"json",
    async: true,
    data:{},
    success:function(data){
      console.log(data);
      let result = JSON.parse(data);
      //渲染index页面
      $('#username span').text(result.person.name);
      $('#username').attr("data-email", function() { return result.person.email });
      $('.vip').attr("data-rank", function() { return result.person.identity });
      $('#disk').text(result.disk.used+'G/'+result.disk.all+'G');
      $caption = $('.content-display table caption');
      $caption1 = $('.content-display1 table caption');
      var $tab = $('.content-display');
      var $tab1 = $('.content-display1');
      //page component init
      var page = result.page;
      switch(result.person.identity){
        case -1:
          //管理员
          $caption.html('用户管理');
          $caption1.html('用户管理');
          renderRoot(result);
          //模板1分页初始化
          $("#page").initPage(10*page,1,GG.kk);
          break;
        case 2:
          //会员
          $caption1.html('文件管理');
          $tab.removeClass("onflex");
          $tab1.addClass("onflex");
          renderUser($tab1,result);
          $("#page1").initPage(10*page,1,GG.kk);
          break;
        default:
          $caption1.html('文件管理');
          $tab.removeClass("onflex");
          $tab1.addClass("onflex");
          $('.vip').css({'opacity':'0.5'});  
          renderUser($tab1,result);
          $("#page1").initPage(10*page,1,GG.kk);
          break;
      }
    },
    error:function(){
      console.log("页面初始化失败");
    }
  });

  //左侧分类菜单
  $('.bar-top li').on('click',function(){
    $('.bar-top .on').removeClass("on");
    var type = $(this).attr('data-type');
    $(this).addClass("on");
    alert("分类功能暂未开启，敬请期待");
  });

  // 修改信息
  $("#changeInfo").on('click',function(){
    $('.changeForm').css({"display":"block"});
  });
  $("#leaveBtn").on('click',function(){
    $('.changeForm').css({"display":"none"});
  });
  $('#changeBtn').on('click',function(){
    var email = $('#username').attr('data-email');
    var username = $('#change-username').val();
    var pwd = $('#change-pwd').val();
    var pwd1 = $('#change-pwd1').val();
    if(pwd == pwd1&&pwd!=null&&pwd1!=null){
      $.ajax({
      type:"GET",
      url:"servlet",
      datatype:"json",
      async: true,
      data:{
        "email":email,
        "username":username,
        "password":pwd
      },
      success:function(data){
        alert('修改信息成功');
      },
      error:function(){
        alert('修改信息失败');
      }
    });
    }else{
      alert("两次密码不一致！");
    }
  });
  $('#leave-notice').on('click',function(){
    $('.noticeBar1').removeClass("show");
    $('.noticeBar2').removeClass("show");
    $('.noticeBar3').removeClass("show");
    $('.noticeBar').css({"display":"none"});
  });
  //分页
/*  $('#page li').on('click',function(e){
    var curpage = $(this).attr('page-data');
    $.ajax({
      type:"GET",
      url:"servlet",
      datatype:"json",
      async: true,
      data:{"page":curpage},
      success:function(data){
      console.log(data);
        var result = JSON.parseJSON(data);
        //渲染表格
        if(result.rank == -1){
        var tbody = renderRoot(result);
        }else{
          tbody = renderUser(result);
        }
        $(".content-table tbody").html(tbody);
      },
      error:function(){
        console.log('fail to change page');
      }
    });
  });*/
  
  //用户扩容
  $('#expand').on('click',function(){
    $.ajax({
      type:"POST",
      url:"ApplyForVipServlet",
      datatype:"json",
      async: true,
      data:{},
      success:function(data){
    	  alert(data);
      },
      error:function(){}
    });
  });
//公告1
  $("#notice").on('click',function(){
    $('.noticeBar').css({"display":"block"});
    $('.noticeBar1').addClass('show');
    //公告1排版
    $.ajax({
      type:"POST",
      url:"SelectNoticeServlet",
      datatype:"json",
      async: true,
      data:{},
      success:function(data){
    	console.log(data);
        var result = JSON.parse(data);
        //渲染公告模板
        var module = renderNotice1(result);
        $('.noticeBar1 tbody tr td:first-child').on('click',function(){
        	//公告2
            $('.noticeBar1').removeClass("show");
            $('.noticeBar2').addClass('show'); 
            var id = $(this.parentNode).attr('data-content');
      	  	renderNotice2(id,result.content);
        });
      },
      error:function(data){}
    });
  });
  $("#fanhui").on('click',function(){
	  $(".content-display1 tbody").html("");
	  $(".content-display1").removeClass("onflex");
	  $(".content-display").addClass("onflex");
  });
});