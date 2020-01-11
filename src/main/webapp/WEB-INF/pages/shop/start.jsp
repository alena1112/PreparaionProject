<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="head.jsp" %>
<!DOCTYPE html>
<html>

<style>
    body, h1, h2, h3, h4, h5, h6 {
        font-family: 'Roboto', sans-serif;
    }

    .w3-bar-block .w3-bar-item {
        padding: 20px
    }

    a.jewelry-item-class { /*для создания кликабельного украшения*/
        display: block;
        height: 100%;
        width: 100%;
        text-decoration: none;
    }

    a.jewelry-item-class:hover { /*подчеркивание текста при наведении мышки*/
        text-decoration: underline;
    }
</style>

<body>
<div class="w3-main w3-content w3-padding" style="max-width:1200px">

    <!-- для компенсации размера шапки -->
    <div class="w3-hide-medium w3-hide-small" style="margin-top:110px">
    </div>
    <div class="w3-hide-large" style="margin-top:70px">
    </div>

    <p id="emptyList" class="w3-text-grey"
       style="display: ${jewelryList.size() == 0 ? 'display' : 'none'};margin-top:20px;margin-bottom:350px;font-size:12px">
        В данном разделе украшения пока отсутствуют</p>
    <spring:form method="get" action="/start?type=all" modelAttribute="jewelryList">
        <c:forEach items="${jewelryList}" var="list">
            <div class="w3-row-padding w3-padding-16 w3-center" id="row">
                <c:forEach items="${list}" var="item">
                    <div class="w3-quarter">
                        <a href="${pageContext.request.contextPath}/jewelry?id=${item.id}"
                           class="jewelry-item-class">
                            <img src="${imageHelper.getMainImageFullPath(item)}"
                                 alt="${item.name}"
                                 style="width:100%">
                        </a>
                        <a href="${pageContext.request.contextPath}/jewelry?id=${item.id}"
                           class="jewelry-item-class"><h3>${item.name}</h3></a>
                        <a href="${pageContext.request.contextPath}/jewelry?id=${item.id}"
                           class="jewelry-item-class"><p>${item.description}</p></a>
                        <p class="w3-text-grey">${item.sold ? 'Нет в наличии' : item.formatPrice}</p>
                    </div>
                </c:forEach>
            </div>
        </c:forEach>
    </spring:form>

    <hr>

    <footer class="w3-padding-16 w3-small w3-center" id="footer">
        <div class="w3-row-padding">

            <div class="w3-col s4 w3-justify w3-text-grey">
                <p><a class="jewelry-item-class" href="#">О нас</a></p>
                <p><a class="jewelry-item-class" href="#">Способы доставки</a></p>
                <p><a class="jewelry-item-class" href="#">Оплата заказа</a></p>
            </div>

            <div class="w3-col s4 w3-justify w3-text-grey">
                <p><a class="jewelry-item-class" href="#">Обмен и возврат</a></p>
                <p><a class="jewelry-item-class" href="#">Контакты</a></p>
            </div>

            <div class="w3-col s4 w3-right-align">
                <i class="fa fa-instagram w3-hover-opacity w3-large" style="margin-right:8px!important"></i>
                <i class="fa fa-envelope-o w3-hover-opacity w3-large"></i>
            </div>
        </div>
    </footer>
</div>
</body>
</html>