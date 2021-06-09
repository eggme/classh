<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-03-11
  Time: 오후 2:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="/css/dashboard.css">
<section class="main_section">
    <div class="card_line">
        <div class="card_wrap">
            <div class="card">
                <div class="card_title">😀 <c:out value="${member.nickName}"/>님 프로필</div>
                <div class="card_content">
                    <div class="card_content_wrap">
                        <div class="card_logo">
                            <img src="${member.profile}">
                        </div>
                        <div class="card_description">
                            <div class="user_name"><c:out value="${member.nickName}"/>님, 안녕하세요! 👋🏼</div>
                        </div>
                    </div>
                    <div class="card_toolbox_wrap">
                        <div class="edit_profile card_toolbox_button_template">프로필 수정하기</div>
                    </div>
                </div>
            </div>
        </div>
        <div class="card_wrap">
            <div class="card">
                <div class="card_title">📖최근 학습 강의</div>
                <div class="card_content">
                    <c:choose>
                        <c:when test="${false}">

                        </c:when>
                        <c:otherwise>
                            <div class="dashboard_flex_center nosearch">최근 학습중인 강의가 없습니다.</div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
    <div class="card_line">
        <div class="card_wrap">
            <div class="card">
                <div class="card_title">📝최근 내 노트</div>
                <div class="card_content">
                    <c:choose>
                        <c:when test="${false}">

                        </c:when>
                        <c:otherwise>
                            <div class="card_content_wrap nosearch">작성한 노트가 없어요 🙅‍♀️</div>
                            <div class="card_toolbox_wrap">
                                <div class="edit_profile card_toolbox_button_template">내 노트 전체보기</div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <div class="card_wrap">
            <div class="card">
                <div class="card_title">🙋🏻‍♀️최근 내 질문</div>
                <div class="card_content">
                    <c:choose>
                        <c:when test="${false}">

                        </c:when>
                        <c:otherwise>
                            <div class="card_content_wrap nosearch">궁금한게 있으면 질문을 남겨보세요 🙋‍♀</div>
                            <div class="card_toolbox_wrap">
                                <div class="edit_profile card_toolbox_button_template">내 질문 전체보기</div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
    <div class="card_line">
        <div class="card_wrap">
            <div class="card">
                <div class="card_title">🏃🏻학습 통계</div>
                <div class="card_content">
                    <div class="card_logo">
                        <img src="/imgs/mini_icon_1.png" style="width: 64px; height: 64px;">
                    </div>
                    <div class="card_description">
                        <div class="user_name">이승준님, 즐거운 하루!!!</div>
                        <div class="user_nickname">닉네임 : 이승준</div>
                        <div class="user_email">이메일 : kyyeto9984@naver.com</div>
                    </div>
                </div>
            </div>
        </div>
        <div class="card_wrap">
            <div class="card">
                <div class="card_title">📚최근 학습중인 강의</div>
                <div class="card_content">
                    <c:choose>
                        <c:when test="${false}">

                        </c:when>
                        <c:otherwise>
                            <div class="card_content_wrap nosearch">최근 학습중인 강의가 없습니다.</div>
                            <div class="card_toolbox_wrap">
                                <div class="edit_profile card_toolbox_button_template">내 강의 전체보기</div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
    <div class="card_line">
        <div class="card_wrap">
            <div class="card">
                <div class="card_title">🎓완료한 강의</div>
                <div class="card_content">
                    <div class="card_logo">
                        <img src="/imgs/mini_icon_1.png" style="width: 64px; height: 64px;">
                    </div>
                    <div class="card_description">
                        <div class="user_name">이승준님, 즐거운 하루!!!</div>
                        <div class="user_nickname">닉네임 : 이승준</div>
                        <div class="user_email">이메일 : kyyeto9984@naver.com</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>