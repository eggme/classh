$(function(){
    $('.tag_ul li').click(function(){
        location.href=$(this).attr("data-url");
    })
});