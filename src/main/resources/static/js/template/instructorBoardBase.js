function selectMenu(data){
    $('.tag_ul li').removeClass('select_menu');
    $('.tag_ul li[data-menu="'+data+'"]').addClass('select_menu');
}
var map = {};
map['instructorDashboard'] = "대시보드";
map['courseList'] = "강의 관리";
map[''] = "내게 한 질문";
map[''] = "내 강의 수강평";
map[''] = "새소식(공지사항) 관리";
map[''] = "월별 수입";
map[''] = "쿠폰 관리";
map[''] = "수강 문의";

function mappingMenu(data){
    return map[data];
}