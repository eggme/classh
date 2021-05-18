$(function(){
    $('.select_click_box div').on('click', function(){
        $('.select').html($(this).text());
        $('.select_click_box').addClass("hidden");
    });
    $('.select_box').click(function(){
        $('.select_click_box').removeClass("hidden");
    });
    $('.course_list_template').on('click', function(){
        let url = $(this).attr('data-url');
        window.open("/course/"+url);
    });

    $('html').click(function(e){
        if(!$(e.target).hasClass("select_box") && !$(e.target).hasClass("select") && !$(e.target).hasClass("select_caret_box")){
            $('.select_click_box').addClass("hidden");
        }
    });
});