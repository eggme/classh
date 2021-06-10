$(function (){
    $(document).on('click', '.question_content', function (){
       location.href=$(this).attr('data-url');
    });
})