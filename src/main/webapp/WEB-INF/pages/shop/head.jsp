<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

    .head-size span { /*изменения размера шрифта заголовка при изменении размера экрана*/
        font-size: 30px;
    }

    @media screen and (min-width: 0px) and (max-width: 992px) {
        .head-size span {
            font-size: 23px;
        }
    }

    .menu-size a {
        font-size: 11px;
        letter-spacing: 1px
    }

    .menu-button {
        font-size: 11px;
        letter-spacing: 1px;
        text-decoration: none;
        text-align: center;
        cursor: pointer;
    }

    .menu-button:hover {
        color: #ff7180;
    }

    .menu-selected {
        color: #ff7180;
    }
</style>

<script>
    document.addEventListener("DOMContentLoaded", loadMenu);

    function loadMenu () {
        var url = new URL(window.location.href);
        var menuParam = url.searchParams.get("menu");
        if (menuParam !== null) {
            var bigMenu = document.getElementById('bigMenu');
            [].filter.call(bigMenu.getElementsByTagName("a"), function (item) {
                if (item.id === menuParam) {
                    addClass(item, "menu-selected");
                } else {
                    removeClass(item, "menu-selected");
                }
            });
            var smallMenu = document.getElementById('smallMenu');
            [].filter.call(smallMenu.getElementsByTagName("a"), function (item) {
                if (item.id === menuParam) {
                    addClass(item, "menu-selected");
                } else {
                    removeClass(item, "menu-selected");
                }
            });
        }
    }

    function openSmallMenu() {
        var x = document.getElementById("smallMenu");
        if (x.className.indexOf("w3-show") == -1) {
            x.className += " w3-show";
        } else {
            x.className = x.className.replace(" w3-show", "");
        }
    }

    function addClass(element, className) {
        if (element.className.indexOf(className) == -1) {
            element.className += " " + className;
        }
    }

    function removeClass(element, className) {
        if (element.className.indexOf(className) != -1) {
            element.className = x.className.replace(" " + className, "");
        }
    }
</script>

<body>
<div class="w3-top w3-border-bottom">
    <div class="w3-white w3-xlarge" style="max-width:1200px;margin:auto">
        <div class="w3-row w3-padding-16">
            <div class="w3-col s2">
                <span class="w3-hide-medium w3-hide-small" style="color: white">s4</span>
                <a class="w3-hide-large" style="margin-left:16px;" href="javascript:void(0)" onclick="openSmallMenu()">
                   <i class="fa fa-bars"></i>
                </a>
                <div id="smallMenu" class="w3-bar-block w3-white w3-hide w3-hide-large w3-top"
                     style="margin-top:69px;font-size:10px;width:100%;height:100%">
                    <a href="${pageContext.request.contextPath}/start?menu=new" class="w3-bar-item menu-button"
                       onclick="openSmallMenu()" id="new">НОВИНКИ</a>
                    <a href="${pageContext.request.contextPath}/start?menu=all" class="w3-bar-item menu-button"
                       onclick="openSmallMenu()" id="all">ВСЕ УКРАШЕНИЯ</a>
                    <a href="${pageContext.request.contextPath}/start?menu=bracelet" class="w3-bar-item menu-button"
                       onclick="openSmallMenu()" id="bracelet">БРАСЛЕТЫ</a>
                    <a href="${pageContext.request.contextPath}/start?menu=earrings" class="w3-bar-item menu-button"
                       onclick="openSmallMenu()" id="earrings">СЕРЬГИ</a>
                    <a href="${pageContext.request.contextPath}/start?menu=necklace" class="w3-bar-item menu-button"
                       onclick="openSmallMenu()" id="necklace">КОЛЬЕ</a>
                </div>
            </div>

            <div class="w3-col s8 w3-center head-size" style="font-weight:600;letter-spacing:1px;white-space:nowrap">
                <span>Graceful Jewelry</span>
            </div>

            <div class="w3-col s2">
                <div class="w3-right" onclick="location.href='buy'" style="margin-right:16px;white-space:nowrap">
                    <i class="fa fa-shopping-cart w3-xlarge" style="cursor:pointer"></i>
                    <span style="font-size:14px;font-weight:400;vertical-align:middle">${order.jewelries.size()}</span>
                </div>
            </div>
        </div>
    </div>

    <div id="bigMenu" class="w3-bar w3-hide-medium w3-hide-small" style="max-width:1200px;margin:auto">
        <a href="${pageContext.request.contextPath}/start?menu=new" class="w3-bar-item menu-button"
           style="width:20%" id="new">НОВИНКИ</a>
        <a href="${pageContext.request.contextPath}/start?menu=all" class="w3-bar-item menu-button"
           style="width:20%" id="all">ВСЕ УКРАШЕНИЯ</a>
        <a href="${pageContext.request.contextPath}/start?menu=bracelet" class="w3-bar-item menu-button"
           style="width:20%" id="bracelet">БРАСЛЕТЫ</a>
        <a href="${pageContext.request.contextPath}/start?menu=earrings" class="w3-bar-item menu-button"
           style="width:20%" id="earrings">СЕРЬГИ</a>
        <a href="${pageContext.request.contextPath}/start?menu=necklace" class="w3-bar-item menu-button"
           style="width:20%" id="necklace">КОЛЬЕ</a>
    </div>
</div>
</body>
</html>