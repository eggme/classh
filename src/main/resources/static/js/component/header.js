$(function(){
    $('.user_id').click(function(){
       location.href="/member/profile";
    });
    $('.overmenu').hover(function (){
        $('.mouseover_menu_wrap').css('display', 'block');
    }, function (){
        $('.mouseover_menu_wrap').css('display', 'none');
    });
    $('.mouseover_menu_wrap').hover(function (){
        $(this).css('display', 'block');
    }, function (){
        $(this).css('display', 'none');
    });
    $('#instructor').on('click', function(){
        $('#student').removeClass('tab_active');
        $(this).addClass('tab_active');
        $('.instructor_list').css('display','block');
        $('.student_list').css('display','none');
    });
    $('#student').on('click', function(){
        $('#instructor').removeClass('tab_active');
        $(this).addClass('tab_active');
        $('.instructor_list').css('display','none');
        $('.student_list').css('display','block');
    });
    $('.cart').hover(function(){
        loadCourseCart();
        $('.course_cart').css('display', 'block');
        $('.cart_over').css('color', '#1dc078');
    }, function(){
        $('.course_cart').css('display', 'none');
        $('.cart_over').css('color', '#959B9D');
    });
    $('.cart_button').click(function(){
        location.href="/course/carts";
    })
});
function loadCourseCart(){
    $.ajax({
        url : "/course/select/cart",
        method : "post",
        dataType: "json",
        success : function(result){
            if(result.result != null){
                return;
            }else{
                makeCourseCart(result);
            }
        },
        error : function (e){
            console.log(e);
        }
    });
}
function makeCourseCart(result){
    $('.cart_tab_content').html("");
    for(var i = 0; i<result.length; i++){
        var course = result[i];
        var template = "<div class='cart_content_warp'>"+
            "<div class='header_course_img'>"+
            "<img src='"+course.courseImg+"' /></div>"+
            "<div class='course_content_data'>"+
            "<div class='course_title_data'><a class='course_title_a' href='/course/"+course.url+"'>"+ course.name +"</a></div>"+
            "<div class='course_price_data'><span class='course_price_separator'>&#x20a9;</span><span class='course_price_value'>" + CostSeparatorKRValue(course.price) +"</span></div>"+
            "</div></div>";
        $(template).appendTo($('.cart_tab_content'));
    }
}
function numberWithCommas(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}