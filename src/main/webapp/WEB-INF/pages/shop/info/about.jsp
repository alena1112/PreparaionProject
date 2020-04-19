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

    <img src="${pageContext.request.contextPath}/resources/images/about.jpeg"
         style="width:100%;max-height:400px;object-fit:scale-down;margin:30px 0">

    <a style="font-size: 15px">
        <p style="letter-spacing:1px">
        Graceful Jewelry - интернет-магазин дизайнерских украшений,
        созданных вручную с использованием только натуральных камней, жемчуга и фурнитуры класса LUX.
        Такие украшения долговечны и не тускнеют со временем.
        </p>
        <p style="color: #ff7180">
            НАТУРАЛЬНЫЕ КАМНИ
        </p>
        <p style="letter-spacing:1px">
            При сборке украшений мы используем только природные камни. В нашей коллекции вы можете найти
            регалит, пейзажная яшма, лазурит, бирюза, опал, авамарин, тигровый глаз, гемиморфит, говлит,
            кварц и многие другие.
        </p>
        <p style="color: #ff7180">
            ФУРНИТУРА КЛАССА LUX
        </p>
        <p style="letter-spacing:1px">
            В наших украшениях мы используем фурнитуру от таких производителей, как Milano Lux (Италия),
            Южная Корея, Swarovski. Каждый элемент украшений изготовлен из латуни с покрытием родий/золото
            22 карата с инструктацией фианитами. Такая фурнитура гипоаллергенна и долговечна в использовании.
        </p>
        <p style="color: #ff7180">
            НАТУРАЛЬНЫЙ ЖЕМЧУГ
        </p>
        <p style="letter-spacing:1px">
            При сборке украшений применяется только натуральный жемчуг (пресноводный, культивированный). Качество
            жемчуга
            варьирует от A до AAA Grade.
        </p>

        <p style="color: #ff7180">
            РЕГУЛИРУЕМАЯ ДЛИНА УКРАШЕНИЙ
        </p>
        <p style="letter-spacing:1px">
            Практически все украшения в нашем интернет-магазине имеют регулируемую длину, подходящую под
            любые размеры.
        </p>
    </a>

    <hr>

    <footer class="w3-padding-16 w3-small w3-center" id="footer">
        <div class="w3-row-padding">

            <div class="w3-col s4 w3-justify w3-text-grey">
                <p><a class="jewelry-item-class" href="${pageContext.request.contextPath}/about">О нас</a></p>
                <p><a class="jewelry-item-class" href="${pageContext.request.contextPath}/delivery">Способы доставки</a></p>
                <p><a class="jewelry-item-class" href="${pageContext.request.contextPath}/payment">Оплата заказа</a></p>
            </div>

            <div class="w3-col s4 w3-justify w3-text-grey">
                <p><a class="jewelry-item-class" href="${pageContext.request.contextPath}/return">Обмен и возврат</a></p>
                <p><a class="jewelry-item-class" href="${pageContext.request.contextPath}/contacts">Контакты</a></p>
            </div>

            <div class="w3-col s4 w3-right-align">
                <a class="fa fa-instagram w3-hover-opacity w3-large" href="https://www.instagram.com/graceful.jewelry"
                   style="margin-right:8px!important"></a>
                <a class="fa fa-envelope-o w3-hover-opacity w3-large" href="mailto:admin@gracefuljewelry.ru"></a>
            </div>
        </div>
    </footer>
</div>
</body>
</html>