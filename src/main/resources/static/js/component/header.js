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
        $('.course_cart').css('display', 'flex');
        $('.cart_over').css('color', '#1dc078');
    }, function(){
        $('.course_cart').css('display', 'none');
        $('.cart_over').css('color', '#959B9D');
    });

    $('.alarm').hover(function(){
        loadNotification();
        $('.course_alarm').css('display', 'flex');
        $('.bell_over').removeClass('far');
        $('.bell_over').addClass('fas');
    }, function(){
        $('.course_alarm').css('display', 'none');
        $('.bell_over').removeClass('fas');
        $('.bell_over').addClass('far');
    });

    $(document).on('click', '.alarm_content_template', function(){
        let id = $(this).attr('data-id');
        let type = $(this).attr('data-type');
        location.href="/member/notification/"+id+"/"+type;
    });

    $('.getNotifications').click(function(){
        location.href="/member/notifications";
    });

    $('.getCarts').click(function(){
        location.href="/course/carts";
    })
});

var notificationMap = {
    NOTICE : "[시스템 공지사항]",
    NEW_COURSE : "[신규강의]",
    COURSE_NOTICE : "[강의새소식]",
    INSTRUCTOR_NOTICE : "[강사알림]",
    QUESTION_ANSWER : "[질문답변]",
    MD_NOTICE : "[공지사항]"
}

function loadNotification(){
    $.ajax({
       url : "/member/get/notification",
       method : 'post',
       dataType: "json",
       success: function (result){
           $('.alarm_tab_content').empty();
           var cnt = 0;
           for(var i = 0 ; i < result.length; i++){
               let notification = result[i];
               //console.log(notification.create_at);
               var customTag = "";
               if(notification.read == false){
                   customTag = "noRead";
                   cnt++;
               }
               let year = notification.create_at.year;
               let month = notification.create_at.monthValue-1;
               let day = notification.create_at.dayOfMonth;

               let hour = notification.create_at.hour-9;
               let minute = notification.create_at.minute;
               let second = notification.create_at.second;
               console.log(hour + " : " + minute + " : " + second);
               var title = notification.title;
               if(title.length > 72){
                   title = title.substr(0, 72) + "...";
               }
               let value =  notification.notificationType;
               var date = new Date(Date.UTC(year, month, day, hour, minute, second));
               let formatDate = timeForToday(date);
               console.log(formatDate);
               var template = '<div class="alarm_content_template ' + customTag + '" data-id="'+notification.id+'" data-type="'+notification.notificationType+'">'+
                   '<div class="alarm_icon_value"></div>'+
                   '<div class="alarm_title_value">' + notificationMap[value] + title + '</div>'+
                   '<div class="alarm_timestamp_value">'+ formatDate +'</div>'+
                   '</div>';
               $(template).appendTo(".alarm_tab_content");
           }
           $('.no_read_alarm_count').text(cnt);
       },error : function (e){
           console.log(e);
        }
    });
}

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
            "<div class='course_title_data'><a class='course_title_a' href='/course/"+course.id+"'>"+ course.name +"</a></div>"+
            "<div class='course_price_data'><span class='course_price_separator'>&#x20a9;</span><span class='course_price_value'>" + CostSeparatorKRValue(course.price) +"</span></div>"+
            "</div></div>";
        $(template).appendTo($('.cart_tab_content'));
    }
}
function numberWithCommas(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}