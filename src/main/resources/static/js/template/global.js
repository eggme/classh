/*
    protected CSRF for ajax Jquery
 */
(function ($) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajaxSetup({
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        }
    });
})(jQuery);

function image_upload_handler(blobInfo, success) {
    var formData = new FormData();
    formData.append("file", blobInfo.blob());
    $.ajax({
        url: "/course/upload/img",
        method: "POST",
        data: formData,
        processData: false,  // ajax는 기본적으로 Query String 으로 데이털르 보내는데 file 전송시에는 Query String 으로 보낼 필요가 없음. 이 값이 Query String의 유무를 체크하는 것
        contentType: false, // content-type의 기본값은 application/x-www-form-urlencoded; charset=UTF-8이다. 파일 전송시 Content Type은 Multipart/form-data 이므로
        // content-Type을 false해주게 되면 전송 헤더 값이 Multipart/form-data로 변경 됨
        cache: false,
        timeout: 600000,
        dataType: "json",
        success: function (result) {
            success(result.data_url);
        },
        error: function (e) {
            console.log("error by" + e);
        }
    });
}

function convertLocalDateTime(localDate, obj){
    let date = new Date(localDate);
    let year = date.getFullYear();
    let month = date.getMonth()+1;
    let day = date.getDate();
    console.log(localDate);
    $(obj).text(year+"년 "+month+"월 "+day+"일");
}

function showHtmlTagWithText(text, obj){
    $(obj).html(text);
}

function CostSeparator(cost, obj){
    $(obj).html(Number(cost).toLocaleString());
}

function timeFormatKor(second, obj) {
    var hours = Math.floor(second / 3600);
    var minutes = Math.floor((second - (hours * 3600)) / 60);
    var result = "";
    console.log(hours + " : " + second);
    if (hours > 0) {
        result = hours + " 시간 " + minutes + " 분";
    } else {
        result = minutes + " 분";
    }
    console.log(result);
    $(obj).html(result);
}