$(function(){
    $('.board_title').click(function(){
        location.href="/md/course/list";
    });

    $(document).on('click', ".board_base", function (){
        let id = $(this).attr('data-id');
        location.href="/md/info/"+id;
    });
});