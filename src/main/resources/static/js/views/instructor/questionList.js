$(function (){

    $(document).on('click', '.question_select_box_template', function(){
        $(this).children('.question_select_box').removeClass("hidden");
    });

    $(document).on('click', '.question_select_box div', function (){
        console.log($(this).parent().children('.select_caret_box').html());
    });

    $('.select_click_box div').on('click', function(){
        $('.select').html($(this).text());
        $('.select_click_box').addClass("hidden");
    });

    $('html').click(function(e){
        if(!$(e.target).hasClass("question_select_box_template") && !$(e.target).hasClass("select") && !$(e.target).hasClass("select_caret_box")){
            $('.question_select_box').addClass("hidden");
        }
    });
});