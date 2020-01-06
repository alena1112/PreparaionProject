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
        var btn = document.getElementById("addInOrderBtn");
        if (${jewelry.sold} === true) {
            btn.innerText = "Нет в наличии";
            btn.disabled = true;
        } else if (${isContains} === true) {
            btn.innerText = "Товар в корзине";
            btn.disabled = true;
        }
    }
</script>
<style>
    body, h1, h2, h3, h4, h5, h6 {
        font-family: 'Roboto', sans-serif;
    }

    .mySlides {
        display: none
    }

    .w3-btn:disabled {
        cursor: default;
    }

    .main-img {
        width: 100%;
        max-height: 500px;
        object-fit: scale-down;
    }

    .small-img {
        width: 100%;
        max-height: 140px;
        object-fit: scale-down;
        cursor:pointer;
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

    <spring:form method="post" action="${pageContext.request.contextPath}/jewelry/addInOrder?id=${jewelry.id}" modelAttribute="order" id="addInOrderForm">

        <div class="w3-row w3-white w3-padding-16">

            <div class="w3-col s7 w3-mobile">
                <div class="w3-container" id="imagesDiv">
                    <c:forEach var="i" begin="1" end="${jewelry.images.size() - 1}" step="1" varStatus="status">
                        <div class="w3-display-container mySlides">
                            <img class="main-img" src="${imageHelper.getImageFullPath(i)}"
                                 alt="${jewelry.name}">
                        </div>
                    </c:forEach>
                </div>

                <div class="w3-row-padding w3-section">
                    <c:forEach var="i" begin="1" end="${jewelry.images.size() - 1}" step="1" varStatus="status">
                        <div class="w3-col s3 w3-center">
                            <img class="small-img demo w3-opacity w3-hover-opacity-off"
                                 alt="${jewelry.name}"
                                 src="${imageHelper.getImageFullPath(i)}"
                                 onclick="currentDiv(${i})">
                        </div>
                    </c:forEach>
                </div>
            </div>


            <div class="w3-container w3-col s5 w3-mobile">
                <h3 style="font-weight:600;font-size:24px;margin-top: 0;margin-bottom: 10px">${jewelry.name}</h3>
                <p style="letter-spacing:1px;font-size:12px;margin-top: 0;margin-bottom: 10px"
                   align="justify">${jewelry.description}</p>
                <p style="font-weight:600;font-size:20px;margin: 0">${jewelry.formatPrice}</p>
                <div style="width: 100%;display: flex;align-items: center;justify-content:center;margin: 24px 0">
                    <button id="addInOrderBtn" type="submit" class="w3-btn w3-round-large" form="addInOrderForm"
                            style="width:50%;min-width:150px;background-color:black;color:white;margin:0">
                        Купить
                    </button>
                </div>
                <p style="letter-spacing:1px;font-size:12px;margin-top: 0;margin-bottom: 10px" align="justify">
                    МАТЕРИАЛЫ: ${jewelry.materialDescription}</p>
                <p style="letter-spacing:1px;font-size:12px;margin-top: 0;margin-bottom: 10px" align="justify">РАЗМЕР:
                        ${jewelry.size}</p>
                <p style="letter-spacing:1px;font-size:12px;margin: 0" align="justify">ВЕС: ${jewelry.weight}</p>
            </div>
        </div>

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
                <i class="fa fa-whatsapp w3-hover-opacity w3-large" style="margin-right:8px!important"></i>
                <i class="fa fa-envelope-o w3-hover-opacity w3-large"></i>
            </div>
        </div>
    </footer>

</div>

<script>
    // Script to open and close sidebar when on tablets and phones
    function w3_open() {
        document.getElementById("mySidebar").style.display = "block";
        document.getElementById("myOverlay").style.display = "block";
    }

    function w3_close() {
        document.getElementById("mySidebar").style.display = "none";
        document.getElementById("myOverlay").style.display = "none";
    }

    // Slideshow Images
    var slideIndex = 1;
    showDivs(slideIndex);

    function plusDivs(n) {
        showDivs(slideIndex += n);
    }

    function currentDiv(n) {
        showDivs(slideIndex = n);
    }

    function showDivs(n) {
        var i;
        var x = document.getElementsByClassName("mySlides");
        var dots = document.getElementsByClassName("demo");
        if (n > x.length) {
            slideIndex = 1
        }
        if (n < 1) {
            slideIndex = x.length
        }
        for (i = 0; i < x.length; i++) {
            x[i].style.display = "none";
        }
        for (i = 0; i < dots.length; i++) {
            dots[i].className = dots[i].className.replace(" w3-opacity-off", "");
        }
        x[slideIndex - 1].style.display = "block";
        dots[slideIndex - 1].className += " w3-opacity-off";
    }
</script>

</body>
</html>