$(function(){
   $(document).on('click', '.course_tile', function(){
       let url = $(this).attr('data-url');
       window.open("/course/"+url);
   });
});