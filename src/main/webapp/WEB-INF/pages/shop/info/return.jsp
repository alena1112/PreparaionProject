<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../head.jsp" %>
<!DOCTYPE html>

<html>
<style>
    body, h1, h2, h3, h4, h5, h6 {
        font-family: 'Roboto', sans-serif;
    }

    .w3-bar-block .w3-bar-item {
        padding: 20px
    }
</style>
<body>

<div class="w3-main w3-content w3-padding" style="max-width:1200px">

    <!-- для компенсации размера шапки -->
    <div class="w3-hide-medium w3-hide-small" style="margin-top:110px">
    </div>
    <div class="w3-hide-large" style="margin-top:70px">
    </div>

    <div style="font-size: 15px; padding: 10px 0">
        <p style="font-weight: bold">
            ОБМЕН И ВОЗВРАТ
        </p>
        <p style="letter-spacing:1px">
            Товары ненадлежащего качества принимаются обратно с полной компенсацией стоимости.
            Для этого необходимо написать письмо на
            <a href="mailto:admin@gracefuljewelry.ru">admin@gracefuljewelry.ru</a> и приложить в качестве
            доказательства фотографии приобретенных украшений. Для остальных случаев обмен и возврат
            не осуществляется.
        </p>
    </div>

    <hr>

    <footer class="w3-padding-16 w3-small w3-center" id="footer">
        <div class="w3-row-padding">

            <div class="w3-col s4 w3-justify w3-text-grey">
                <p><a class="jewelry-item-class" href="${pageContext.request.contextPath}/about">О нас</a></p>
                <p><a class="jewelry-item-class" href="${pageContext.request.contextPath}/delivery">Способы доставки</a>
                </p>
                <p><a class="jewelry-item-class" href="${pageContext.request.contextPath}/payment">Оплата заказа</a></p>
            </div>

            <div class="w3-col s4 w3-justify w3-text-grey">
                <p><a class="jewelry-item-class" href="${pageContext.request.contextPath}/return">Обмен и возврат</a></p>
                <p><a class="jewelry-item-class" href="${pageContext.request.contextPath}/contacts">Контакты</a></p>
            </div>

            <div class="w3-col s4 w3-right-align">
                <a class="fa fa-envelope-o w3-hover-opacity w3-large" href="mailto:admin@gracefuljewelry.ru"></a>
            </div>
        </div>
    </footer>
</div>
</body>
</html>