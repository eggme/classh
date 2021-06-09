$(function(){
   $(document).on('click', '.course_title', function(){
       let id = $(this).attr('data-id');
       location.href="/course/"+id;
   });
});