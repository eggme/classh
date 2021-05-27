$(function(){
   $(document).on('click', '.course_title', function(){
       let url = $(this).attr('data-url');
       location.href="/course/"+url;
   });
});