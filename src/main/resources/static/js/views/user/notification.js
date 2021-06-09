$(function (){
   $(document).on('click', '.notification_template', function (){
       let id = $(this).attr('data-id');
       let type = $(this).attr('data-type');
       location.href="/member/notification/"+id+"/"+type;
   });
});

function setCount(cnt, obj){
    $(obj).text(cnt);
}