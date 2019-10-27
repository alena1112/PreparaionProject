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

    input, button:active, button:focus {
        outline:none;
    }

    button:hover {
        background-color: white;
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

    .w3-input {
    	padding: 2px;
    	display: block;
    	border: none;
    	border-bottom: 1px solid #ccc;
    	width: 100%
    }
</style>
<body>

<!-- Top menu -->
<div class="w3-top w3-border-bottom">
    <div class="w3-white w3-xlarge" style="max-width:1200px;margin:auto">
        <div class="w3-center w3-padding-16" style="font-size:30px;font-weight: 600;letter-spacing:1px">Graceful
            Jewelry
        </div>
    </div>
    <div class="w3-bar w3-white" style="max-width:1200px;margin:auto">
        <a href="/start?menu=new" class="w3-bar-item w3-button w3-mobile"
           style="font-size:11px;letter-spacing:1px;width:20%">НОВИНКИ</a>
        <a href="/start?menu=all" class="w3-bar-item w3-button w3-mobile"
           style="font-size:11px;letter-spacing:1px;width:20%">ВСЕ УКРАШЕНИЯ</a>
        <a href="/start?menu=bracelet" class="w3-bar-item w3-button w3-mobile"
           style="font-size:11px;letter-spacing:1px;width:20%">БРАСЛЕТЫ</a>
        <a href="/start?menu=earrings" class="w3-bar-item w3-button w3-mobile"
           style="font-size:11px;letter-spacing:1px;width:20%">СЕРЬГИ</a>
        <a href="/start?menu=necklace" class="w3-bar-item w3-button w3-mobile"
           style="font-size:11px;letter-spacing:1px;width:20%">КОЛЬЕ</a>
    </div>
</div>

<!-- !PAGE CONTENT! -->
<div class="w3-main w3-content w3-padding" style="max-width:1200px;margin-top:120px">

    <div class="w3-row-padding w3-center">

        <h4>Оформление заказа</h4>

        <div class="w3-container">
          <table class="w3-table w3-bordered">
            <tr>
              <td>
                  <div class="w3-cell">
                    <img src="${pageContext.request.contextPath}/resources/w3images/1.jpg" alt="jewelry"
                                        style="width="90" height="90"" class="w3-margin-right">
                  </div>
                  <div class="w3-cell w3-cell-middle">
                    <a href="#" class="jewelry-item-class"><p style="margin:0;font-weight:600">
                        Graceful shells</p></a>
                    <p style="margin:0;font-size:12px">Яркие ассиметричные серьги с кристальным жемчугом Swarovski</p>
                  </div>
              </td>
              <td style="text-align:right;font-size:18px;width:80px">
                  <p>1 300 ₽</p>
                  <i class="fa fa-trash" aria-hidden="true"></i>
            </td>
            </tr>
            <tr>
              <td>
                    <div class="w3-cell">
                      <img src="${pageContext.request.contextPath}/resources/w3images/2.jpg" alt="jewelry"
                                          style="width="90" height="90"" class="w3-margin-right">
                    </div>
                    <div class="w3-cell w3-cell-middle">
                        <a href="#" class="jewelry-item-class"><p style="margin:0;font-weight:600">
                            Graceful shells</p></a>
                        <p style="margin:0;font-size:12px">Яркие ассиметричные серьги с кристальным жемчугом Swarovski</p>
                      </div>
                </td>
              <td style="text-align:right;font-size:18px;width:80px">
                <p>1 300 ₽</p>
                <i class="fa fa-trash" aria-hidden="true"></i>
              </td>
            </tr>
          </table>

          <div class="w3-cell-row">
            <div class="w3-cell w3-mobile w3-cell-middle" style="text-align:left;width:33%">
                <p style="margin:8px 0">Промокод</p>
                <div class="w3-row">
                  <input class="w3-input w3-col s7" style="padding:4px 0" type="text">
                  <button class="w3-btn w3-col s5 w3-white w3-border w3-round-large"
                      style="border-color:#ff7180!important;padding:4px 0"><span style="color:#ff7180">Применить</span></button>
                </div>
              </div>
              <div class="w3-cell w3-mobile w3-cell-middle" style="margin:8px 0;width:33%">
                <button class="w3-btn w3-round-large w3-black">Оформить заказ</button>
              </div>
              <div class="w3-cell w3-mobile w3-cell-middle" style="text-align:right;width:33%">
                <p class="w3-text-grey" style="font-size:14px;margin:8px 0">Скидка: 100 ₽</p>
                <p class="w3-text-grey" style="font-size:14px;margin:8px 0">Доставка: 300 ₽</p>
                <p style="font-weight:600;font-size:18px;margin:8px 0">ИТОГО: <span
                    style="text-decoration:line-through">2 900 ₽</span><span style="color:#ff7180"> 2 800 ₽</span></p>
              </div>
          </div>
        </div>


        <div class="w3-row-padding w3-padding-16 w3-center">
            <div class="w3-half w3-justify">
                <h5>Контактная информация</h5>
                <p>
                    <label style="font-size:12px">Имя <span class="required">*</span></label>
                    <input class="w3-input" type="text">
                </p>
                <p>
                    <label style="font-size:12px">Фамилия <span class="required">*</span></label>
                    <input class="w3-input" type="text">
                </p>
                <p>
                    <label style="font-size:12px">Телефон <span class="required">*</span></label>
                    <input class="w3-input" type="text">
                </p>
                <p>
                    <label style="font-size:12px">Электонная почта <span class="required">*</span></label>
                    <input class="w3-input" type="text">
                </p>
            </div>

            <div class="w3-half w3-justify">
                <h5>Способ доставки</h5>
                <div class="w3-container w3-justify w3-light-grey" style="margin-bottom: 10px">

                    <p>
                        <input class="w3-radio" type="radio" name="delivery" value="male" checked>
                        <label>Почта России</label>
                    </p>

                    <div style="margin-left: 10%; margin-right: 10%">
                        <p>
                            <label style="font-size:12px">Город <span class="required">*</span></label>
                            <input class="w3-input w3-light-grey" type="text" >
                        </p>
                        <p>
                            <label style="font-size:12px">Адрес <span class="required">*</span></label>
                            <input class="w3-input w3-light-grey" type="text">
                        </p>
                        <p>
                            <label style="font-size:12px">Индекс <span class="required">*</span></label>
                            <input class="w3-input w3-light-grey" type="text">
                        </p>
                    </div>
                </div>

                <div class="w3-container w3-justify w3-light-grey">
                    <p>
                        <input class="w3-radio" type="radio" name="delivery" value="female">
                        <label>Самовывоз по г. Москва</label>
                    </p>
                </div>

                <h5>Способ оплаты</h5>

                <div class="w3-container w3-justify">
                    <p>
                        <input class="w3-radio" type="radio" name="payment" value="male" checked>
                        <label>Перевод на карту банка</label>
                    </p>

                    <p>
                        <input class="w3-radio" type="radio" name="payment" value="female">
                        <label>Наличными при получении</label>
                    </p>
                </div>
            </div>
        </div>
    </div>


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

    <!-- End page content -->
</div>

<script>
</script>

</body>
</html>