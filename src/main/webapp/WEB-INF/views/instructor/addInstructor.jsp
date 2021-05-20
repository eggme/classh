<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-05-20
  Time: 오전 9:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" href="/css/views/instructor/addInstructor.css">
<!-- Swiper -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/Swiper/4.5.1/css/swiper.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/Swiper/4.5.1/js/swiper.min.js"></script>

<script src="/js/views/instructor/addInstructor.js"></script>
<script>
    $(function () {
        var swiper = new Swiper('.swiper-container', {
            direction: 'horizontal',
            loop: true,
            autoplay: {
                delay: 3000,
                disableOnInteraction: true
            },
            autoplayDisableOnInteraction: false,
            // Navigation arrows
            navigation: {
                nextEl: '.swiper-button-next',
                prevEl: '.swiper-button-prev',
            }
        });
    });
</script>

<div class="main_section">
    <div class="main_flex_wrap">
        <div class="section_wrap">
            <div class="main_banner">
                <div class="main_banner_title">나의 지식에 가치를 부여하세요</div>
                <div class="main_banner_subTitle">
                    <p>전체 지식공유자 평균 수익 3025만원!!</p>
                    <p>나의 지식을 나눠 사람들에게 배움의 기회를 주고, 의미있는 대가를 가져가세요.</p>
                    <p>인프런은 지식으로 의미있는 수익과 공유가 가능한 한국 유일한 플랫폼 입니다.</p>
                </div>
                <div class="main_banner_button_area">
                    <div class="button_success">지식공유참여</div>
                    <div class="button_description">지식공유자 안내서</div>
                </div>
            </div>
        </div>
        <div class="main_icon_wrap">
            <div class="main_icon_flex_wrap">
                <div class="main_icon_template first_item">
                    <div class="icon_template">
                        <i class="fas fa-database"></i>
                    </div>
                    <div class="description_template">
                        <div class="description_title">1인 평균누적수익</div>
                        <div class="description_point_title">3025만원</div>
                    </div>
                </div>
                <div class="main_icon_template">
                    <div class="icon_template">
                        <i class="fas fa-user-graduate"></i>
                    </div>
                    <div class="description_template">
                        <div class="description_title">총 회원수</div>
                        <div class="description_point_title">47만명</div>
                    </div>
                </div>
                <div class="main_icon_template">
                    <div class="icon_template">
                        <i class="fas fa-users"></i>
                    </div>
                    <div class="description_template">
                        <div class="description_title">누적 수강생</div>
                        <div class="description_point_title">228만명</div>
                    </div>
                </div>
            </div>
        </div>
        <div class="swipe_wrap">
            <!-- Slider main container -->
            <div class="swiper-container">
                <!-- Additional required wrapper -->
                <div class="swiper-wrapper">
                    <!-- Slides -->
                    <div class="swiper-slide">
                        <div class="swipe_content_wrap">
                            <div class="carousel_flex_wrap">
                                <div class="carousel_item_title">
                                    <div class="carousel_user_nickName">김정환</div>
                                    <div class="carousel_user_job">우아한 형제들 개발자</div>
                                </div>
                                <div class="carousel_tag_wrap">
                                    <div class="carousel_tag_value">#인텔리제이</div>
                                    <div class="carousel_tag_value">#IDE</div>
                                    <div class="carousel_tag_value">#Git</div>
                                    <div class="carousel_tag_value">#리팩토링</div>
                                </div>
                                <div class="carousel_text_wrap">
                                    <div class="carousel_text_value">
                                        <p>우리나라는 자기 경력에 관련한 교육에 댓가가 낮은 경우가 많아요. Hoflearn은 강사와 학습자 모두가</p>
                                        <p>만족하는 좋은 사례를 만들어 가고 있다고 생각합니다.</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="swiper-slide">
                        <div class="swipe_content_wrap">
                            <div class="carousel_flex_wrap">
                                <div class="carousel_item_title">
                                    <div class="carousel_user_nickName">박조은</div>
                                    <div class="carousel_user_job">데이터분석가</div>
                                </div>
                                <div class="carousel_tag_wrap">
                                    <div class="carousel_tag_value">#데이터분석</div>
                                    <div class="carousel_tag_value">#자연어처리</div>
                                </div>
                                <div class="carousel_text_wrap">
                                    <div class="carousel_text_value">
                                        <p>지식공유는 경제적인 수익 뿐만 아니라 개인의 성장을 위해서도 좋은 방법이에요!</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="swiper-slide">
                        <div class="swipe_content_wrap">
                            <div class="carousel_flex_wrap">
                                <div class="carousel_item_title">
                                    <div class="carousel_user_nickName">장기효(캡틴판교)</div>
                                    <div class="carousel_user_job">프론트엔드 개발자</div>
                                </div>
                                <div class="carousel_tag_wrap">
                                    <div class="carousel_tag_value">#Vue</div>
                                    <div class="carousel_tag_value">#Frontend</div>
                                    <div class="carousel_tag_value">#Javascript</div>
                                </div>
                                <div class="carousel_text_wrap">
                                    <div class="carousel_text_value">
                                        <p>다른 지식공유자분도 마찬가지지만 결국에는 같이 간다는 마음이 저는 의미 있다고 생각하거든요.</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="swiper-slide">
                        <div class="swipe_content_wrap">
                            <div class="carousel_flex_wrap">
                                <div class="carousel_item_title">
                                    <div class="carousel_user_nickName">백기선</div>
                                    <div class="carousel_user_job">마이크로소프트 개발자</div>
                                </div>
                                <div class="carousel_tag_wrap">
                                    <div class="carousel_tag_value">#자바</div>
                                    <div class="carousel_tag_value">#스프링</div>
                                </div>
                                <div class="carousel_text_wrap">
                                    <div class="carousel_text_value">
                                        <p>누군가에게 무언가를 설명하려면 평소 알고 있었던 지식이라도 다시 한 번 돌아보고 다듬어야 하며</p>
                                        <p>효율적인 전달 방법을 고민해야 합니다. 그 과정을 반복하며 보다 나은 실력을 쌓을 수 있습니다.</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- If we need navigation buttons -->
                <div class="swiper-button-prev"></div>
                <div class="swiper-button-next"></div>
            </div>
        </div>
        <div class="graph_wrap">
            <div class="graph_flex_wrap">
                <div class="left_wrap">
                    <div class="main_title">지식공유자를 위한<br/>높은 정산 비율</div>
                    <div class="main_subText">
                        <p>보통은 교육컨텐츠 수입의</p>
                        <p>50% 이상을 플랫폼이나 학원이 가져갑니다.</p>
                        <p>자신의 지식을 나누는 지식공유자에게</p>
                        <p>더 많은 보상이 주어져야 하는 것 아닐까요?</p>
                    </div>
                    <div class="sub_title">호프런은<br/>70%~90% 비율로 정산합니다.</div>
                    <div class="sub_subText">
                        <p>호프런은 합리적인 정책으로 지식공유자와</p>
                        <p>학습자 모두에게 도움이 되는 선순환을 만들어 갑니다.</p>
                    </div>
                </div>
                <div class="right_wrap">
                    <img class="right_graph_image" src="/imgs/graph.png">
                </div>
            </div>
        </div>
        <div class="table_wrap">
            <div class="table_flex_wrap">
                <div class="table_title_wrap">
                    <div class="table_title_image">
                        <img src="/imgs/mini_icon_1.png">
                    </div>
                    <div class="table_title_value">왜 호프런일까요?</div>
                </div>
                <div class="table_row_wrap">
                    <div class="table_column_wrap">
                        <div class="table_column_title_image">
                            <i class="fas fa-percent"></i>
                        </div>
                        <div class="table_column_title">지식 콘텐츠를 함부로 다루지 않습니다.</div>
                        <div class="table_column_subText">너무 크고 잦은 할인은 결과적으로 콘텐츠의 질을 떨어뜨리고, 지식의 가치를 존중하지 않는 일이라 생각합니다.
                            호프런은 합리적인 가격 정책으로 지식공유자와 학습자 모두에게 도움이 되는 생태계를 만들어 가고 있습니다.
                        </div>
                    </div>
                    <div class="table_column_wrap">
                        <div class="table_column_title_image">
                            <i class="fas fa-search-dollar"></i>
                        </div>
                        <div class="table_column_title">수익이 가능한 유일한 곳</div>
                        <div class="table_column_subText">호프런은 기술 강의로 의미 있는 보상을 가질 수 있는 유일한 플랫폼 입니다. 수강생이 강의를 신청할 때마다
                            수익을 얻으세요. 지속가능한 수익과 명예를 가져가세요. 강의 매출의 70% ~ 90% 를 가져가실 수 있습니다.
                        </div>
                    </div>
                    <div class="table_column_wrap">
                        <div class="table_column_title_image">
                            <i class="fas fa-hand-holding-heart"></i>
                        </div>
                        <div class="table_column_title">사회적 가치를 실현하세요.</div>
                        <div class="table_column_subText">오직 호프런에서만 가능합니다. 자신의 지식으로 지속가능한 수익을 올리면서도, 많은 사람들에게 경제적인 배움의
                            기회를 줄 수 있습니다. 사람들이 성장하고, 자신의 능력을 향상하도록 호프런과 함께해주세요.
                        </div>
                    </div>
                </div>
                <div class="table_row_wrap">
                    <div class="table_column_wrap">
                        <div class="table_column_title_image">
                            <i class="fas fa-quote-right"></i>
                        </div>
                        <div class="table_column_title">자유로운 강의 운영</div>
                        <div class="table_column_subText">마음껏 자신의 강의를 운영해 보세요. 쿠폰발행, 학생추가, 강의편집 등 자유롭게 강의를 운영할 수 있습니다. 폭
                            넓은 활용 방법을 이용해서 자신만의 강의 영역을 확대해 보세요.
                        </div>
                    </div>
                    <div class="table_column_wrap">
                        <div class="table_column_title_image">
                            <i class="fas fa-network-wired"></i>
                        </div>
                        <div class="table_column_title">네트워크 & 인프라</div>
                        <div class="table_column_subText">호프런의 네트워크를 활용하세요. 수많은 IT 기업에서 이미 호프런과 연계를 맺고 함께 성장하고 있어요. 호프런에
                            강의를 개설한다면, 수많은 사람들과 기업들이 당신의 강의를 보게될 거에요.
                        </div>
                    </div>
                    <div class="table_column_wrap">
                        <div class="table_column_title_image">
                            <i class="fas fa-quran"></i>
                        </div>
                        <div class="table_column_title">호프런 만의 노하우</div>
                        <div class="table_column_subText">강의에 필요한 노하우와 지식이 없다고 걱정하지 마세요.기본적인 준비를 함께하며, 노하우를 알려드립니다. 좋은
                            지식을 나누실 분들은 오피스를 찾아주세요. 부담없이 이야기 나누며 강의를 함께 만들어요!
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="getting_start_wrap">
            <div class="getting_start_flex_wrap">
                <div class="getting_start_title">
                    지금 시작하세요.
                </div>
                <div class="getting_start_subTitle">
                    호프런과 함께라면 단계별로 쉽고 간편하게 지식을 나누고 운영할 수 있습니다.<br/>
                    지식으로 경제적 보상과 명예를 모두 가져보세요.
                </div>
                <div class="getting_start_button_wrap">
                    <div class="getting_start_button_success">지식공유참여</div>
                    <div class="getting_start_button_description">지식공유자 안내서</div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal_form_wrapper">
    <div class="modal_form animate">
        <sec:authentication property="principal" var="user"/>
        <div class="modal_exit_icon_wrap">
            <i class="fas fa-times"></i>
        </div>
        <div class="modal_title_wrap">
            <div class="message_description">
                <span class="username"><c:out value="${user.nickName}" /></span><span class="message_user">님! 안녕하세요.</span><br/>
                <span class="special">지식공유에 동참해 주셔서 감사합니다!</span><br/>
                <span class="message_data">호프런의 이야기를 들어주세요!</span>
            </div>
            <div class="message_form_value hidden">
                <span class="message_user">감사합니다</span><span class="username"><c:out value="${user.nickName}" /></span>님<br/>
                <span class="special">지식공유자가 되기 위해서</span><br/>
                <span class="message_data">아래 정보가 필요해요.</span>
            </div>
        </div>
        <div class="modal_subText_wrap">
            <div class="modal_subText_area">
                <div class="modal_subText_value">
                    <h2>1. 호프런은 성장기회의 평등을 추구합니다.</h2>
                    <p>우리는 때로 무언가를 배워야만 합니다.<br>
                        하지만 여러 이유로 당연하다고 생각되어 지는것들이 누군가에게는 사치가 되기도 합니다.<br>
                        호프런은 누구나, 경제적으로 시간적 제약없이 내가 원하는 것을 배우고, 지식을 나눌 수 있는 공간입니다.</p>
                </div>
                <div class="modal_subText_value">
                    <h2>2. 전문 지식으로 수익이 가능한 유일한 곳.</h2>
                    <p>호프런은 기술 강의, 멘토링으로 의미 있는 보상을 가질 수 있는 유일한 플랫폼 입니다. 47만명의 수강생이 강의를 신청할 때마다 수익을 얻을 수 있어요!<br/>
                        지속가능한 수익과 명예를 가져가세요 :)</p>
                </div>
                <div class="modal_subText_value">
                    <h2>3. 호프런은 70% ~ 100% 의 비율의 높은 수익을 제공합니다.</h2>
                    <p>좋은 지식은 합당한 보상에서 나온다고 호프런은 생각합니다. 때문에 인프런은 다른 학습 서비스에 비해 월등히 높은 수익을 드리고 있어요.<br />
                        실제로 호프런엔 꾸준히 월 수백 ~ 수천 만원 이상의 수익을 가져가는 많은 지식공유자들이 계셔요. </p>
                </div>
                <div class="modal_subText_value">
                <h2>4. 호프런의 강의는 지식공유자가 자유롭게 운영할 수 있습니다.</h2>
                <p>지식공유자는 학생추가, 새소식 알림, 운영, 쿠폰 발행 등으로 자신의 강의를 자유롭게 운영할 수 있습니다. 학습자들과 소식을 공유하고 자유롭게 운영해 주세요.</p>
            </div>
            </div>
            <div class="modal_form_value hidden">
                <form action="/add/instructor" method="post" class="instructor_form">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="hoflearn_username modal_margin">
                        <div class="modal_form_menu_template">호프런 계정</div>
                        <div class="modal_form_input_template">
                            <input type="text" name="username" class="modal_input_template readonly" value="${user.username}" readonly/>
                        </div>

                    </div>
                    <div class="hoflearn_email modal_margin">
                        <div class="modal_form_menu_template">연락받을 이메일</div>
                        <div class="modal_form_input_template">
                            <input type="text" name="email" class="modal_input_template" value="${user.username}"/>
                        </div>
                    </div>

                    <div class="hoflearn_memberName modal_margin">
                        <div class="modal_form_menu_template">이름 (실명)</div>
                        <div class="modal_form_input_template">
                            <input type="text" name="memberName" class="modal_input_template" placeholder="실명을 입력해주세요"/>
                        </div>
                    </div>
                    <div class="hoflearn_tel">
                        <div class="modal_form_menu_template">연락처</div>
                        <div class="modal_form_input_template">
                            <input type="tel" name="tel" class="modal_input_template modal_tel" placeholder="000-0000-0000"/>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <div class="modal_button_wrap">
            <div class="modal_getting_start">지식공유 참여</div>
            <div class="modal_submit hidden">제출</div>
        </div>
    </div>
</div>