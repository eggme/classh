<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-03-12
  Time: 오후 2:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script src="/js/addCourse.js"></script>
<link rel="stylesheet" href="/css/addCourse.css" >
<section class="main_session">

    <h5 class="margin_h_tag">강의 제작</h5>
    <h3 class="margin_h_tag margin_bottom">강의 정보</h3>
    <form class="course_form" action="/course/${course.id}/save/information" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <p class="input_des">강의 제목</p>
        <c:choose>
            <c:when test="${!course.isReleased()}">
                <input type="text" name="name" class="gray_input course_title_input" value="${course.name}"/>
            </c:when>
            <c:otherwise>
                <input type="text" name="name" class="gray_input course_title_input" value="${course.name}" readonly/>
            </c:otherwise>
        </c:choose>

        <br/>
        <p class="input_des">가격 설정</p>
        <div class="price_wrap">
            <span class="money_mark">&#8361;</span>
            <c:choose>
                <c:when test="${!course.isReleased()}">
                    <input type="number" name="price" class="gray_input price_input course_price_input" value="${course.price}"
                           placeholder="가격을 설정해 주세요."/>
                </c:when>
                <c:otherwise>
                    <input type="number" name="price" class="gray_input price_input course_price_input" value="${course.price}"
                           readonly/>
                </c:otherwise>
            </c:choose>
            <p class="price_description">
                1. 가격 설정 후 제출하신 후에는, 가격 변경이 되지 않아요! 바꾸고 싶은 경우에는 운영팀에 문의해주세요 :)<br/>
                <font color="#fFB6351">2. 입력하신 가격은 부가세 미포함 가격입니다. 실제 수강생에는 부가세 10% 합산된 가격으로 보입니다.</font><br/>
                3. 가격은 무료의 경우 0원으로 유료의 경우 10,000원 이상 1,000원 단위로 설정할 수 있습니다.
            </p>
        </div>
        <br/>
        <p class="input_des">이런 걸 배울 수 있어요</p>
        <c:choose>
            <c:when test="${!course.isReleased()}">
                <input type="text" name="course_tag" class="gray_input course_tag_input" placeholder="ex) Spring framework">
                <div class="course_tag_add course_button_template">추가하기</div>
                <div class="course_tag_required">두 개 이상 넣어주세요.</div>
                <div class="course_tag_hidden"></div>
                <%--  스킬 태그 및 추천인 세팅 하면됩니다, jstl if 로 스크립트로 전송해야할듯     <c:if test></c:if>--%>
                <script>
                    <c:forEach var="skillTag" items="${course.skillTags}" varStatus="status">
                    setSkillTag('${skillTag.value}', false);
                    </c:forEach>
                </script>
            </c:when>
            <c:otherwise>
                <input type="text" name="course_tag" class="gray_input course_tag_input" placeholder="ex) Spring framework" readonly>
                <div class="course_button_template readonly">추가하기</div>
                <div class="course_tag_required">두 개 이상 넣어주세요.</div>
                <div class="course_tag_hidden"></div>
                <%--  스킬 태그 및 추천인 세팅 하면됩니다, jstl if 로 스크립트로 전송해야할듯     <c:if test></c:if>--%>
                <script>
                    <c:forEach var="skillTag" items="${course.skillTags}" varStatus="status">
                    setSkillTag('${skillTag.value}', true);
                    </c:forEach>
                </script>
            </c:otherwise>
        </c:choose>


        <br/>
        <p class="input_des">이런 분들에게 추천해요</p>
        <c:choose>
            <c:when test="${!course.isReleased()}">
                <input type="text" name="course_recommend" class="gray_input course_recommend_input"
                       placeholder="ex) 자바를 공부한 사람">
                <div class="course_recommend_add course_button_template">추가하기</div>
                <div class="course_tag_required">두 개 이상 넣어주세요.</div>
                <div class="course_recommend_hidden"></div>
                <script>
                    <c:forEach var="recommend" items="${course.recommendations}" varStatus="status">
                    setRecommends('${recommend.value}', false);
                    </c:forEach>
                </script>
            </c:when>
            <c:otherwise>
                <input type="text" name="course_recommend" class="gray_input course_recommend_input"
                       placeholder="ex) 자바를 공부한 사람" readonly>
                <div class="course_button_template readonly">추가하기</div>
                <div class="course_tag_required">두 개 이상 넣어주세요.</div>
                <div class="course_recommend_hidden"></div>
                <script>
                    <c:forEach var="recommend" items="${course.recommendations}" varStatus="status">
                    setRecommends('${recommend.value}', true);
                    </c:forEach>
                </script>
            </c:otherwise>
        </c:choose>


        <br/>
        <p class="input_des">카테고리</p>
        <c:choose>
            <c:when test="${!course.isReleased()}">
                <div class="tag_container category">
                    <input type="hidden" name="courseCategory" class="courseCategory" value=""/>
                    <c:forEach var="course_category" items="${categories}" varStatus="status">
                        <c:choose>
                            <c:when test="${course.courseCategory.value eq course_category.value}">
                                <div class="tag_box category_box category_actived"><c:out value="${course_category.value}"/></div>
                                <script>
                                    setCategory('${course_category.value}');
                                </script>
                            </c:when>
                            <c:otherwise>
                                <div class="tag_box category_box"><c:out value="${course_category.value}"/></div>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div class="tag_container">
                    <input type="hidden" name="courseCategory" class="courseCategory" value=""/>
                    <c:forEach var="course_category" items="${categories}" varStatus="status">
                        <c:choose>
                            <c:when test="${course.courseCategory.value eq course_category.value}">
                                <div class="tag_box category_box category_actived"><c:out value="${course_category.value}"/></div>
                                <script>
                                    setCategory('${course_category.value}');
                                </script>
                            </c:when>
                            <c:otherwise>
                                <div class="tag_box category_box"><c:out value="${course_category.value}"/></div>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>

        <br/>
        <p class="input_des">강의 수준</p>
        <c:choose>
            <c:when test="${!course.isReleased()}">
                <div class="tag_container level">
                    <input type="hidden" name="courseLevel" class="courseLevel" value=""/>
                    <c:forEach var="course_level" items="${level}" varStatus="status">
                        <c:choose>
                            <c:when test="${course.courseLevel.value eq course_level.value}">
                                <div class="tag_box level_box level_actived"><c:out value="${course_level.value}"/></div>
                                <script>
                                    setLevel('${course_level.value}');
                                </script>
                            </c:when>
                            <c:otherwise>
                                <div class="tag_box level_box"><c:out value="${course_level.value}"/></div>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div class="tag_container">
                    <input type="hidden" name="courseLevel" class="courseLevel" value=""/>
                    <c:forEach var="course_level" items="${level}" varStatus="status">
                        <c:choose>
                            <c:when test="${course.courseLevel.value eq course_level.value}">
                                <div class="tag_box level_box level_actived"><c:out value="${course_level.value}"/></div>
                                <script>
                                    setLevel('${course_level.value}');
                                </script>
                            </c:when>
                            <c:otherwise>
                                <div class="tag_box level_box"><c:out value="${course_level.value}"/></div>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>

        <div class="main_center">
            <c:choose>
                <c:when test="${!course.isReleased()}">
                    <div class="save_next_page">저장 후 다음이동</div>
                </c:when>
            </c:choose>
        </div>
    </form>

</section>
