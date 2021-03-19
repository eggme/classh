$(function(){
    // 강의 카테고리
    $('.category').children().click(function(){
        $('.category_actived').removeClass('category_actived');
        $(this).addClass('category_actived');
        $('.courseCategory').val($(this).text());
    });
    // 강의 레벨
    $('.level').children().click(function(){
        $('.level_actived').removeClass('level_actived');
        $(this).addClass('level_actived');
        $('.courseLevel').val($(this).text());
    });
    // 스킬 키워드 태그 추가
    $('.course_tag_add').click(function(){
        var value = $('.course_tag_input').val();
        if(value != ''){
            var sample = '<div class="course_tag_hidden_form">'
                + '<div class="course_tag_text">' + value + '</div>'
                + '<div class="course_tag_remove">'
                + '<i class="far fa-trash-alt"></i>'
                + '</div>'
                + '<input type="hidden" name="tags" value="'+ value +'" /></div>';
            $(sample).appendTo('.course_tag_hidden');
            $('.course_tag_input').val('');
        }
    });
    // 스킬 키워드 태그 삭제
    $(document).on('click','.course_tag_remove' ,function(){
        console.log('hi');
        console.log($(this).closest('div').attr('class'));
        console.log($(this).closest('div').parent().closest('div').attr('class'));
        $(this).closest('div').parent().closest('div').remove();
    });
    // 스킬 키워드 태그 끝
    // 추천 태그 추가
    $('.course_recommend_add').click(function(){
        var value = $('.course_recommend_input').val();
        if(value != '') {
            var sample = '<div class="course_recommend_hidden_form">'
                + '<div class="course_recommend_text">' + value + '</div>'
                + '<div class="course_recommend_remove">'
                + '<i class="far fa-trash-alt"></i>'
                + '</div>'
                + '<input type="hidden" name="recommendations" value="'+ value +'" /></div>';
            $(sample).appendTo('.course_recommend_hidden');
            $('.course_recommend_input').val('');
        }
    });
    // 추천 태그 삭제
    $(document).on('click','.course_recommend_remove' ,function(){
        console.log('hi');
        console.log($(this).closest('div').attr('class'));
        console.log($(this).closest('div').parent().closest('div').attr('class'));
        $(this).closest('div').parent().closest('div').remove();
    });
    // 추천 태그 끝
    $('.save_next_page').click(function(){
        // 전처리

        // 최종 submit
        $('.course_form').submit();
    });
});
function pageInitialized(){
    var course_title = $('.course_title').val();
    $('.course_title_input').val(course_title);
    var course_price = $('.course_price').val();
    if(course_price != 0)
        $('.course_price_input').val(course_price);
    setSkillTag();
    setRecommends();
    setCategory();
    setLevel();
}
function setSkillTag(){
    if($('.course_tags').val() != "null"){
        var tagList = $('.course_tags').val().split(',');
        for(var i = 0 ;i < tagList.length; i++){
            var sample = '<div class="course_tag_hidden_form">'
                + '<div class="course_tag_text">' + tagList[i] + '</div>'
                + '<div class="course_tag_remove">'
                + '<i class="far fa-trash-alt"></i>'
                + '</div>'
                + '<input type="hidden" name="tags" value="'+ tagList[i] +'" /></div>';
            $(sample).appendTo('.course_tag_hidden');
            $('.course_tag_input').val('');
        }
    }
}
function setRecommends(){
    if($('.course_recommends').val() != "null"){
        var recommendsList = $('.course_recommends').val().split(',');
        for( var i = 0 ; i < recommendsList.length; i++){
            var sample = '<div class="course_recommend_hidden_form">'
                + '<div class="course_recommend_text">' + recommendsList[i] + '</div>'
                + '<div class="course_recommend_remove">'
                + '<i class="far fa-trash-alt"></i>'
                + '</div>'
                + '<input type="hidden" name="recommends" value="'+ recommendsList[i] +'" /></div>';
            $(sample).appendTo('.course_recommend_hidden');
            $('.course_recommend_input').val('');
        }
    }
}
function setCategory(){
    var category = $('.course_category').val();
    var list = $('.category').children('div');
    for(var i = 0;i < list.length;i++){
        if($(list[i]).text() == category){
            $(list[i]).addClass('category_actived');
            $('.category_value').val(category);
        }
    }
}
function setLevel(){
    var level = $('.course_level').val();
    var list = $('.level').children('div');
    for(var i = 0;i < list.length;i++){
        if($(list[i]).text() == level){
            $(list[i]).addClass('level_actived');
            $('.level_value').val(level);
        }
    }
}