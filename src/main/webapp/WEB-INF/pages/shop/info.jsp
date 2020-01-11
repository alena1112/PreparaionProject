<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="head.jsp" %>
<!DOCTYPE html>

<html>
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

<div class="w3-main w3-content w3-padding" style="max-width:1200px">

    <!-- для компенсации размера шапки -->
    <div class="w3-hide-medium w3-hide-small" style="margin-top:110px">
    </div>
    <div class="w3-hide-large" style="margin-top:70px">
    </div>

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