$(function(){
    $(".login").click(function(){
        $("#hidden_login").css('display', 'block');
    });
    $('.login_button').click(function(){
        var form = $('.login_form').serialize();
        $.ajax({
            url:"/signIn.do",
            method: "POST",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            dataType : 'json',
            data : form,
            success : function(result){
                if(result.state == "1"){
                    location.reload();
                }else if(result.state == "2"){
                    alert("아이디 혹은 비밀번호가 다릅니다!");
                }
            },
            error : function(e){
                console.log(e);
            }
        });
    });
    $('.logout').click(function(){
        $.ajax({
            url : "/logout.do",
            method : "get",
            success : function(r){
                if(r.state == "1"){
                    location.href='/index.do';
                }
            },
            error : function(e){

            }
        })
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
        location.href="/userCart.do";
    })
});
function loadCourseCart(){
    var user_name = $('.user_name').val();
    $.ajax({
        url : "/loadUserCourseCart.do?user_name="+user_name,
        method : "post",
        data : {"result" : 3},
        success : function(result){
            console.log(result);
            makeCourseCart(result);
        },
        error : function (e){
            console.log(e);
        }
    });
}
function makeCourseCart(result){
    $('.cart_tab_content').html("");
    for(var i = 0; i<result.length; i++){
        var listItem = result[i];
        var template = "<div class='cart_content_warp'>"+
            "<div class='course_img'>"+
            "<img src='"+listItem.courseInfo.course_img+"' /></div>"+
            "<div class='course_content_data'>"+
            "<div class='course_title_data'><a class='course_title_a' href='/courseInfo.do?course_id="+listItem.course_Id+"'>"+ listItem.title +"</a></div>"+
            "<div class='course_price_data'>&#x20a9;" + numberWithCommas(listItem.price) +"</div>"+
            "</div></div>";
        $(template).appendTo($('.cart_tab_content'));
    }
}
function numberWithCommas(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}