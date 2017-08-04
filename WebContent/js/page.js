$.fn.extend({
    /*
     * 初始化分页插件
     * @param listCount 总页数
     * @param currentPage 当前页码
    **/
    "initPage":function(listCount,currentPage,fun){
        //初始化page
        var maxshowpageitem = $(this).attr("maxshowpageitem");
        if(maxshowpageitem!=null&&maxshowpageitem>0&&maxshowpageitem!=""){
            page.maxshowpageitem = maxshowpageitem;
        }
        var pagelistcount = $(this).attr("pagelistcount");
        if(pagelistcount!=null&&pagelistcount>0&&pagelistcount!=""){
            page.pagelistcount = pagelistcount;
        }
        var pageId = $(this).attr("id");
        page.pageId=pageId;
        if(listCount<0){
            listCount = 0;
        }
        if(currentPage<=0){
            currentPage=1;
        }
        page.setPageListCount(listCount,currentPage,fun); //设置列表总量和当前页码
    }
});

var  page = {
    "pageId":"",
    "maxshowpageitem":5,//最多显示的页码个数
    "pagelistcount":10,//每一页显示的内容条数
    /**
     * 初始化分页界面
     * @param listCount 总页数
     * @param currentPage 当前页数
     */
    "initWithUl":function(listCount,currentPage){
        var pageCount = 1;
        if(listCount>=0){
            var pageCount = listCount%page.pagelistcount>0?parseInt(listCount/page.pagelistcount)+1:parseInt(listCount/page.pagelistcount);
        }
        var appendStr = page.getPageListModel(pageCount,currentPage); //生成分页模板
        $("#"+page.pageId).html(appendStr); //渲染模板
    },
    /**
     * 设置列表总页码和当前页码
     * @param listCount 总页数
     * @param currentPage 当前页码
     * @callback fun 回调函数
     */
    "setPageListCount":function(listCount,currentPage,fun){
        listCount = parseInt(listCount);
        currentPage = parseInt(currentPage);
        page.initWithUl(listCount,currentPage); //初始化分页界面->生成分页模板
        page.initPageEvent(listCount,fun); //初始化分页事件(设置列表总页码和当前页码)
        fun(currentPage);
    },
    /*
     * 初始化分页事件
     * @param listCount 列表总量
    */
    "initPageEvent":function(listCount,fun){
        $("#"+page.pageId +">li[class='pageItem']").on("click",function(){
        	var $this = $(this);
        	var pagenumber = $(this).attr('page-data');
    	    var id = $(this.parentNode).attr('id');
    	    var url = "";
    	    var rank = $('.vip').attr('data-rank');
    	    if(rank == "-1" && id == 'page'){
    	    	url = "SelectPersonFyServlet";
    	    }else{
    	    	url = "SelectPersonFilesServlet"
    	    }
    		$.ajax({
    			  type:"POST",
    	          url:url,
    	          datatype:"json",
    	          async:true,
    	          data:{"pagenumber":pagenumber},
    	          success:function(data){
    	        	  console.log(data);
    	        	  var result = JSON.parse(data);
    	        	  if(url == "SelectPersonFyServlet"){
    	        		  renderRoot(result);
    	        	  }else{
    	        		  renderUser(result);
    	        	  }
    	        	  page.setPageListCount(listCount,$this.attr("page-data"),fun); //设置列表总页码、当前页码，注意this
    	          },
    	          error:function(){}
    		  });
           
        });
    },
    /*
     * 生成分页模板
     * @param pageCount 总页数
     * @param currentPage 当前页码
    */
    "getPageListModel":function(pageCount,currentPage){
        /*
        <li class="pageItem" page-data="1" page-rel="firstpage"></li>
        <li class="pageItem" page-data="pageNum-1" page-rel="prepage"></li>
        <li class="pageItem" page-data="pageNum" page-rel="itempage"></li>
        <li class="pageItem" page-data="pageNum+1" page-rel="nextpage"></li>
        <li class="pageItem" page-data="pageMax" page-rel="lastpage"></li>
        */
        var prePage = currentPage-1;
        var nextPage = currentPage+1;
        var prePageClass = "pageItem";
        var nextPageClass = "pageItem";
        if(prePage<=0){
            prePageClass="pageItemDisable";
        }
        if(nextPage>pageCount){
            nextPageClass="pageItemDisable";
        }
        var appendStr ="";
        appendStr+="<li class='"+prePageClass+"' page-data='1' page-rel='firstpage'>首页</li>";
        appendStr+="<li class='"+prePageClass+"' page-data='"+prePage+"' page-rel='prepage'>&lt;上一页</li>";
        var miniPageNumber = 1;
        //分页算法
        if(currentPage-parseInt(page.maxshowpageitem/2)>0&&currentPage+parseInt(page.maxshowpageitem/2)<=pageCount){
            miniPageNumber = currentPage-parseInt(page.maxshowpageitem/2);
        }else if(currentPage-parseInt(page.maxshowpageitem/2)>0&&currentPage+parseInt(page.maxshowpageitem/2)>pageCount){
            miniPageNumber = pageCount-page.maxshowpageitem+1;
            if(miniPageNumber<=0){
                miniPageNumber=1;
            }
        }
        var showPageNum = parseInt(page.maxshowpageitem);
        if(pageCount<showPageNum){
            showPageNum = pageCount
        }
        for(var i=0;i<showPageNum;i++){
            var pageNumber = miniPageNumber++;
            var itemPageClass = "pageItem";
            if(pageNumber==currentPage){
                itemPageClass = "pageItemActive";
            }
            appendStr+="<li class='"+itemPageClass+"' page-data='"+pageNumber+"' page-rel='itempage'>"+pageNumber+"</li>";
        }
        appendStr+="<li class='"+nextPageClass+"' page-data='"+nextPage+"' page-rel='nextpage'>下一页&gt;</li>";
        appendStr+="<li class='"+nextPageClass+"' page-data='"+pageCount+"' page-rel='lastpage'>尾页</li>";
        return appendStr;
    }
}