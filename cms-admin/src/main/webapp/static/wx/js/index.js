
  $(function(){
	  $(".boxSearch").click(function(){
	  	$(".boxSearch").removeClass("boxSearchHover");
	  	$(".search_form_suggest").hide();
	  	$(this).addClass("boxSearchHover");
	  	$(this).siblings().show();
	  });
	  
	  
	  $(".search_hotList dd a").click(function(){
	  	$(".search_hotList dd a").removeClass("bg1");
	  	$(this).addClass("bg1");
	  });

	  function time(){
        var oDate = new Date();
        var year = oDate.getFullYear();
        var mounth = oDate.getMonth()+1;
        var day = oDate.getDate();
        var hours = oDate.getHours();
        var minute = oDate.getMinutes();
        var seconds = oDate.getSeconds();
        var time = null;
        var week = new Array(7);
        week[0]='星期天';
        week[1]='星期一';
        week[2]='星期二';
        week[3]='星期三';
        week[4]='星期四';
        week[5]='星期五';
        week[6]='星期六';
        var weeks = week[oDate.getDay()];
        var data = document.getElementById('time');
        data.innerHTML =year+'年'+mounth+'月'+day+'日'+'  '+weeks+'  '+fillZero(hours)+':'+fillZero(minute)+':'+fillZero(seconds);
        function fillZero(n){
            if(n<10){
                return '0'+n
            }
            else{
                return ''+n
            }
        }
    }
     time();
    setInterval(time,1000)





})