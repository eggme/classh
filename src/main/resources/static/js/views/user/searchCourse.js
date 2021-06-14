$(function(){
   $(document).on('click', '.course_item_wrap', function(){
      let id = $(this).attr("data-id");
      location.href="/course/"+id;
   });
});