$(function(){
   $('.course_preview').click(function(){
      window.open("/course/"+$(this).attr("data-id"), "newWindow");
   });
   $(window).scroll(function(){
      var scrollValue = $(this).scrollTop();
      if(scrollValue > 36){
         $('.menu_template_line').css("position", "fixed");
         $('.menu_template_line').css("top", "0");
         $('.menu_template_line').css("z-index", "99");
      }else{
         $('.menu_template_line').css("position", "static");
         $('.menu_template_line').css("top", "0");
      }
   });
});