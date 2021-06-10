function selectMenu(data){
    $('.tag_ul li').removeClass('select_menu');
    $('.tag_ul li[data-menu="'+data+'"]').addClass('select_menu');
}
var map = {};
map['dashboard'] = "학습 대시보드";
map['notification'] = "알림";
map['courseList'] = "내 강의";
map[''] = "학습노트";
map[''] = "내 강의목록";
map['questionList'] = "내 질문";
map[''] = "문의";
map[''] = "위시리스트";
map[''] = "쿠폰함";
map[''] = "내 포인트";
map['profile'] = "프로필 설정";
map[''] = "알림 설정";
map['communityBoard'] = "새소식";

function mappingMenu(data){
    return map[data];
}