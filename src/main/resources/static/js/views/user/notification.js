$(function (){
   $(document).on('click', '.notification_timestamp_wrap', function (){
       let id = $(this).attr('data-id');
       location.href="/member/notification/"+id;
   });
});