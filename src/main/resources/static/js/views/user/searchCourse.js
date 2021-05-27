$(function(){
   $(document).on('click', '.course_item_wrap', function(){
      let url = $(this).attr("data-url");
      location.href="/course/"+url;
   });
});