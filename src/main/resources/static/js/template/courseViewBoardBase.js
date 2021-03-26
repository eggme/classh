$(function(){
    $(window).scroll(function(){
        var scrollValue = $(this).scrollTop();
        if(scrollValue > 36 && scrollValue < 350) {
            $('.header_wrap').css("position", "fixed");
            $('.header_wrap').css("top", "0");
            $('.header_wrap').css("z-index", "90");
            $('.course_nav').css("position", "static");
            $('.course_nav').css("top", "0");
        }else if(scrollValue > 350){
            $('.header_wrap').css("position", "static");
            $('.header_wrap').css("top", "0");
            $('.course_nav').css("position", "fixed");
            $('.course_nav').css("top", "0");
            $('.course_nav').css("z-index", "90");
        }else{
            $('.header_wrap').css("position", "static");
            $('.header_wrap').css("top", "0");
            $('.course_nav').css("position", "static");
            $('.course_nav').css("top", "0");
        }
    });
});