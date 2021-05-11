$(function(){
   $('.course_delete').on('click', function(){
        $('.course_delete_form_wrap').attr('data-id', $(this).closest(".tr_padding").attr('data-id'))
        $('.course_delete_form_course_name').text($(this).parent().siblings('.course_id').text());
        $('.course_delete_form_wrap').css("display", "block");
   });

   $('.course_delete_form_submit').click(function(){
       let id = $('.course_delete_form_wrap').attr('data-id');
       $.ajax({
           url : "/course/delete",
           method : "post",
           data : {"id" : id},
           success : function(result){
               location.href="/course/list";
           },error : function(e){
               console.log(e);
           }
       })
   });

   $('.course_delete_form_cancel').click(function(){
       $('.course_delete_form_wrap').css("display", "none");
   });
});

