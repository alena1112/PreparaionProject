<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<title>Украшения ручной работы - купить дизайнерские украшения в интернет-магазине Graceful Jewelry</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto&display=swap">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script>
    window.onload = function () {
        if (${type == "SUCCESSFUL_ORDER"}) {
            document.getElementById('infoMsg').innerHTML = "Заказ успешно создан";
        }
    }
</script>
<style>
    body, h1, h2, h3, h4, h5, h6 {
        font-family: 'Roboto', sans-serif;
    }

    .w3-bar-block .w3-bar-item {
        padding: 20px
    }
</style>
<body>

<!-- Top menu -->
<div class="w3-top w3-border-bottom" style="min-height: 110px; max-height: 130px">
    <div class="w3-white w3-xlarge" style="max-width:1200px;margin:auto">
        <div class="w3-row w3-padding-16" style="margin-left:16px;margin-right:16px">
            <div class="w3-col s2">
                <span style="color: white">s4</span>
            </div>

            <div class="w3-col s8 w3-center" style="font-size:30px;font-weight: 600;letter-spacing:1px">
                <span>Graceful Jewelry</span>
            </div>

            <div class="w3-col s2">
                <div class="w3-right" onclick="location.href='buy'">
                    <i class="fa fa-shopping-cart w3-xlarge" style="cursor:pointer"></i>
                    <span style="font-size:14px;font-weight:400;vertical-align:middle">${order.jewelries.size()}</span>
                </div>
            </div>
        </div>

    </div>
    <div class="w3-bar w3-white w3-mobile" style="max-width:1200px;margin:auto">
        <a href="${pageContext.request.contextPath}/start?menu=new" class="w3-bar-item w3-button"
           style="font-size:11px;letter-spacing:1px;width:20%">НОВИНКИ</a>
        <a href="${pageContext.request.contextPath}/start?menu=all" class="w3-bar-item w3-button"
           style="font-size:11px;letter-spacing:1px;width:20%">ВСЕ УКРАШЕНИЯ</a>
        <a href="${pageContext.request.contextPath}/start?menu=bracelet" class="w3-bar-item w3-button"
           style="font-size:11px;letter-spacing:1px;width:20%">БРАСЛЕТЫ</a>
        <a href="${pageContext.request.contextPath}/start?menu=earrings" class="w3-bar-item w3-button"
           style="font-size:11px;letter-spacing:1px;width:20%">СЕРЬГИ</a>
        <a href="${pageContext.request.contextPath}/start?menu=necklace" class="w3-bar-item w3-button"
           style="font-size:11px;letter-spacing:1px;width:20%">КОЛЬЕ</a>
    </div>
</div>

<div class="w3-main w3-content w3-padding" style="max-width:1200px;margin-top:110px">

    <p id="infoMsg" class="w3-text-grey" style="margin-top: 20px;margin-bottom: 350px;font-size: 12px"></p>

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