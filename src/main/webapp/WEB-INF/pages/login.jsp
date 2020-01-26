<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Страница входа</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://fonts.googleapis.com/css?family=Open+Sans&display=swap" rel="stylesheet">
</head>
<body class="page page_center page_cover">

<style>
  *, *:after. *:before {
    box-sizing: border-box;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
  }

  /* PAGE */
  .page {
    padding: 0;
    margin: 0;

    font-size: 16px;
    font-family: 'Open Sans', sans-serif;
  }

  .page_cover {
    width: 100vw;
    height: 100vh;
  }

  .page_center {
    display: flex;
    justify-content: center;
    align-items: center;
  }

  /* CARD */
  .card {
    width: 320px;
    padding: 32px;

    background: #fff;

    text-align: center;

    border-radius: 4px;
    box-shadow: 0 2px 4px -1px rgba(0,0,0,.2), 0 4px 5px 0 rgba(0,0,0,.14), 0 1px 10px 0 rgba(0,0,0,.12);

    transition: .4s;
  }

  .card__title {
    margin-bottom: 10px;

    color: #7b42ff;
    font-size: 1.5rem;
    line-height: 2rem;
    font-weight: bold;
    text-transform: uppercase;
  }

  /* FORM */
  .form {
  }

  .form__item {
    margin-bottom: 10px;
  }

  /* BUTTON */
  .button {
    padding: 10px;

    color: #fff;
    font-size: 1rem;

    background: #7b42ff;
    border: none;
    outline: none;
  }

  .button:hover {
    color: #7b42ff;

    background: #fff;
    outline: 1px solid #7b42ff;
    cursor: pointer;

    transition: 0.4s;
  }

  .button_all-width {
    width: 100%;
  }

  /* INPUT */
  .input {
    width: 100%;
    padding: 10px;
    border: 1px solid #ede1eb;
    color: inherit;

    font-family: 'Open Sans', sans-serif;

    box-sizing: border-box;
  }

  .input:focus {
    outline: 1px solid #7b42ff;
    transition: 0.1s;
    background: #fcf5fb;
  }

  /* MESSAGE */
  .message {
    font-weight: 400;
    margin: 10px 0;
    padding: 5px;

    color: #fff;
  }


  .message_danger {
    background: #D0021B;
  }

  .message_success {
    background: #4BBD5C;
  }
</style>

  <div class="card">

    <div class="card__title">Вход</div>

    <c:if test="${not empty error}">
    <div class="message message_danger">Неверный логин или пароль</div>
    </c:if>

    <c:if test="${not empty logout}">
    <div class="message message_success">Вы вышли из системы</div>
    </c:if>

    <form class="form" action="<c:url value='/j_spring_security_check' />" method="post">
      <input class="form__item input" autofocus placeholder="Логин" name="username" type="text">
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      <input class="form__item input" placeholder="Пароль" name="password" type="password">
      <button class="button button_all-width" type="submit">Войти</button>
    </form>

  </div>
</body>
</html>
