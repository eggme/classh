<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-05-27
  Time: 오후 3:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="/css/aside/searchSideMenu.css"/>

<aside class="aside_menu">
    <ul class="menu">
        <li>
            <div class="menu_div">
                <span class="menu_value">전체강의</span>
            </div>
        </li>
        <c:if test="${!(categories eq null)}">
            <c:forEach var="category" items="${categories}" varStatus="index">
                <li>
                    <div class="menu_div">
                        <span class="menu_value">${category.value}</span>
                        <span class="right_button"><i class="fas fa-chevron-right"></i></span>
                    </div>
                </li>
            </c:forEach>
        </c:if>
    </ul>
    <div class="menu_bottom">
        <div class="course_level border_template">
            <span class="level_text text_template">난이도</span>
        </div>
        <ul class="level_ul ul_template">
            <c:if test="${!(levels eq null)}">
                <c:forEach var="level" items="${levels}" varStatus="index">
                    <li>
                        <div class="level_box box_template">
                            <input type="checkbox" name="level" class="level_cb cb_template" id="level_id${index.count}">
                            <label for="level_id${index.count}" class="level_label"></label>
                            <span class="level_value value_template">${level.value}</span>
                        </div>
                    </li>
                </c:forEach>
            </c:if>
        </ul>
    </div>
    <div class="menu_bottom">
        <div class="course_price border_template">
            <span class="price_text text_template">유료/무료/할인중</span>
        </div>
        <ul class="price_ul ul_template">
            <li>
                <div class="price_box box_template">
                    <input type="checkbox" name="price" class="price_cb cb_template" id="price_id1">
                    <label for="price_id1" class="price_label"></label>
                    <span class="price_value value_template">입문</span>
                </div>
            </li>
            <li>
                <div class="price_box box_template">
                    <input type="checkbox" name="price" class="price_cb cb_template" id="price_id2">
                    <label for="price_id2" class="price_label"></label>
                    <span class="price_value value_template">초급</span>
                </div>
            </li>
            <li>
                <div class="price_box box_template">
                    <input type="checkbox" name="price" class="price_cb cb_template" id="price_id3">
                    <label for="price_id3" class="price_label"></label>
                    <span class="price_value value_template">중급이상</span>
                </div>
            </li>
        </ul>
    </div>
</aside>