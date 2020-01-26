<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Profile</title>
</head>
<body>

<div class="container">

    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h2 class="panel-title">Profile</h2>
                </div>
                <div class="panel-body">
                    <fieldset>
                        <div class="form-group">
                            <i class="fa fa-user fa-fw"></i><label> Username: </label> ${username}
                            <br><i class="fa fa-group fa-fw"></i> <label> Group: </label> ${group}
                            <br><a href="/logout"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                        </div>
                    </fieldset>
                </div>
            </div>
        </div>
    </div>
</div>
