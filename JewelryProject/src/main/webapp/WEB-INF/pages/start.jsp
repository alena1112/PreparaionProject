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

<!-- Sidebar (hidden by default) -->
<%--<nav class="w3-sidebar w3-bar-block w3-card w3-top w3-xlarge w3-animate-left"--%>
     <%--style="display:none;z-index:2;width:40%;min-width:300px" id="mySidebar">--%>
    <%--<a href="javascript:void(0)" onclick="w3_close()"--%>
       <%--class="w3-bar-item w3-button">Close Menu</a>--%>
    <%--<a href="#food" onclick="w3_close()" class="w3-bar-item w3-button">Food</a>--%>
    <%--<a href="#about" onclick="w3_close()" class="w3-bar-item w3-button">About</a>--%>
    <%--<a href="/jewelry/list" onclick="w3_close()" class="w3-bar-item w3-button">Admin</a>--%>
<%--</nav>--%>

<!-- Top menu -->
<div class="w3-top w3-border-bottom">
    <div class="w3-white w3-xlarge" style="max-width:1200px;margin:auto">
        <%--<div class="w3-button w3-padding-16 w3-left" onclick="w3_open()">Menu</div>--%>
        <%--<div class="w3-button w3-padding-16 w3-right">Mail</div>--%>
        <div class="w3-center w3-padding-16" style="font-size:30px;font-weight:600;letter-spacing:1px">Graceful Jewelry</div>
    </div>
    <div class="w3-bar w3-white" style="max-width:1200px;margin:auto">
        <a href="/start?menu=new" class="w3-bar-item w3-button w3-mobile" style="font-size:11px;letter-spacing:1px;width:20%">НОВИНКИ</a>
        <a href="/start?menu=all" class="w3-bar-item w3-button w3-mobile" style="font-size:11px;letter-spacing:1px;width:20%">ВСЕ УКРАШЕНИЯ</a>
        <a href="/start?menu=bracelet" class="w3-bar-item w3-button w3-mobile" style="font-size:11px;letter-spacing:1px;width:20%">БРАСЛЕТЫ</a>
        <a href="/start?menu=earrings" class="w3-bar-item w3-button w3-mobile" style="font-size:11px;letter-spacing:1px;width:20%">СЕРЬГИ</a>
        <a href="/start?menu=necklace" class="w3-bar-item w3-button w3-mobile" style="font-size:11px;letter-spacing:1px;width:20%">КОЛЬЕ</a>
    </div>
</div>

<!-- !PAGE CONTENT! -->
<div class="w3-main w3-content w3-padding" style="max-width:1200px;margin-top:110px">

    <spring:form method="get" action="/start?type=all" modelAttribute="jewelryList">
        <c:forEach items="${jewelryList}" var="list">
            <div class="w3-row-padding w3-padding-16 w3-center" id="row">
                <c:forEach items="${list}" var="item">
                    <div class="w3-quarter">
                        <a href="/buy" class="jewelry-item-class">
                            <img src="${pageContext.request.contextPath}/resources/w3images/${item.imageUrl}"
                                 alt="${item.name}"
                                 style="width:100%">
                        </a>
                        <a href="/buy" class="jewelry-item-class"><h3>${item.name}</h3></a>
                        <a href="/buy" class="jewelry-item-class"><p>${item.description}</p></a>
                        <p class="w3-text-grey">${item.formatPrice}</p>
                    </div>
                </c:forEach>
            </div>
        </c:forEach>
    </spring:form>

    <!-- Pagination -->
    <%--<div class="w3-center w3-padding-32">--%>
    <%--<div class="w3-bar">--%>
    <%--<a href="#" class="w3-bar-item w3-button w3-hover-black">Назад</a>--%>
    <%--<a href="#" class="w3-bar-item w3-black w3-button">1</a>--%>
    <%--<a href="#" class="w3-bar-item w3-button w3-hover-black">2</a>--%>
    <%--<a href="#" class="w3-bar-item w3-button w3-hover-black">3</a>--%>
    <%--<a href="#" class="w3-bar-item w3-button w3-hover-black">4</a>--%>
    <%--<a href="#" class="w3-bar-item w3-button w3-hover-black">Вперед</a>--%>
    <%--</div>--%>
    <%--</div>--%>

    <%--<hr id="about">--%>

    <%--<!-- About Section -->--%>
    <%--<div class="w3-container w3-padding-32 w3-center">--%>
    <%--<h3>About Me, The Food Man</h3><br>--%>
    <%--<img src="${pageContext.request.contextPath}/resources/w3images/chef.jpg" alt="Me" class="w3-image"--%>
    <%--style="display:block;margin:auto" width="800" height="533">--%>
    <%--<div class="w3-padding-32">--%>
    <%--<h4><b>I am Who I Am!</b></h4>--%>
    <%--<h6><i>With Passion For Real, Good Food</i></h6>--%>
    <%--<p>Just me, myself and I, exploring the universe of unknownment. I have a heart of love and an--%>
    <%--interest of--%>
    <%--lorem ipsum and mauris neque quam blog. I want to share my world with you. Praesent tincidunt--%>
    <%--sed tellus--%>
    <%--ut rutrum. Sed vitae justo condimentum, porta lectus vitae, ultricies congue gravida diam non--%>
    <%--fringilla.--%>
    <%--Praesent tincidunt sed tellus ut rutrum. Sed vitae justo condimentum, porta lectus vitae,--%>
    <%--ultricies--%>
    <%--congue gravida diam non fringilla.</p>--%>
    <%--</div>--%>
    <%--</div>--%>
    <hr>

    <!-- Footer -->
    <%--<footer class="w3-row-padding w3-padding-32">--%>
    <%--<div class="w3-third">--%>
    <%--<p>О нас</p>--%>
    <%--<p>Контакты</p>--%>
    <%--</div>--%>

    <%--<div class="w3-third">--%>
    <%--<p>Доставка и оплата</p>--%>
    <%--<p>Обмен и возврат</p>--%>
    <%--</div>--%>

    <%--<div class="w3-third">--%>
    <%--<ul class="w3-ul w3-hoverable">--%>
    <%--<li class="w3-padding-16">--%>
    <%--<img src="${pageContext.request.contextPath}/resources/w3images/workshop.jpg"--%>
    <%--class="w3-left w3-margin-right" style="width:50px">--%>
    <%--<span class="w3-large">Lorem</span><br>--%>
    <%--<span>Sed mattis nunc</span>--%>
    <%--</li>--%>
    <%--<li class="w3-padding-16">--%>
    <%--<img src="${pageContext.request.contextPath}/resources/w3images/gondol.jpg"--%>
    <%--class="w3-left w3-margin-right" style="width:50px">--%>
    <%--<span class="w3-large">Ipsum</span><br>--%>
    <%--<span>Praes tinci sed</span>--%>
    <%--</li>--%>
    <%--</ul>--%>
    <%--</div>--%>
    <%--</footer>--%>

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
                <i class="fa fa-whatsapp w3-hover-opacity w3-large" style="margin-right:8px!important"></i>
                <i class="fa fa-envelope-o w3-hover-opacity w3-large"></i>
            </div>
        </div>
    </footer>

    <!-- End page content -->
</div>

<script>
    // Script to open and close sidebar
    function w3_open() {
        document.getElementById("mySidebar").style.display = "block";
    }

    function w3_close() {
        document.getElementById("mySidebar").style.display = "none";
    }
</script>

</body>
</html>