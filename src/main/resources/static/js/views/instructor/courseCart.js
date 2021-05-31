var isChecked = false;

$(function () {
    IMP.init('imp16098775');
    $(document).on('click', '.course_tile', function () {
        let url = $(this).attr('data-url');
        window.open("/course/" + url);
    });
    $('.privacy_policy').change(function () {
        if ($('.privacy_policy').is(":checked") == true) {
            isChecked = true;
            $('.hidden_text').text('');
        } else {
            isChecked = false
            $('.hidden_text').text('구매조건 및 결제대행 서비스 약관 동의하여야 합니다.');
        }
    });
    $('.charge_box').click(function () {
        if (isChecked) payment();
    })
    $(document).on('click', '.deleteCourse', function(){
        let confirmBox = confirm("정말 수강 바구니에서 삭제할까요?");
        if(confirmBox == true){
            $(this).parent().submit();
        }
    });
});

function payment() {
    var course = $('.course_box');
    var list = new Array();
    for (var i = 0; i < course.length; i++) {
        var temp = new Object();
        var c_id = $(course[i]).attr('data-value');
        temp.course_id = c_id;
        list.push(temp);
    }
    console.log(list);
    console.log(JSON.stringify(list));
    IMP.request_pay({
        pg: 'kcp',
        pay_method: 'card',
        merchant_uid: 'merchant_' + new Date().getDate() + "_" + new Date().getSeconds(),
        name: $('.total_title').val(),
        amount: 100,
        buyer_email: $('.user_email').val(),
        buyer_tel: $('.user_tel').val(),
        buyer_name: $('.user_name').val()
    }, function (rsp) {
        console.log('test');
        console.log(rsp);
        if (rsp.success) {
            console.log(rsp);
            $.ajax({
                url: '/course/payment',
                type: 'post',
                dataType: 'json',
                data: {
                    method: rsp.pay_method,
                    cardName: rsp.card_name,
                    cardNumber: rsp.card_number,
                    imp_uid: rsp.imp_uid,
                    merchantId: rsp.merchant_uid,
                    courseName: rsp.name,
                    coursePrice: rsp.paid_amount,
                    purchaseStatus: rsp.status,
                    purchaseResult: rsp.success,
                    course_list: JSON.stringify(list)
                },
                success: function (result) {
                    alert("결제에 성공하였습니다. 내 강의보기로 이동합니다");
                    location.href="/member/list";
                }
            });
        } else {
            alert("결제 실패" + rsp.error_msg);
        }
    });
}