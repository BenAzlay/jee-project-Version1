<%-- 
    Document   : welcome
    Created on : 20 sept. 2019, 10:33:43
    Author     : JAA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>  
    </head>
    <body>
        <div align="right">
            Hello ${user.login} ! Your session is active
        </div>
        <h2>Details of employee: ${employee.firstname} ${employee.lastname}</h2>
        <form>
            <div class="form-group">
                
                ID <input type="number" readonly class="form-control-plaintext" name="id" value=${employee.id}>
                Name <input type="text" class="form-control" name="lastname" value=${employee.lastname}>
                First Name <input type="text" class="form-control" name="firstname" value=${employee.firstname}>
                Home phone <input type="text" class="form-control" name="telHome" value=${employee.telHome}>
                Mobile phone <input type="text" class="form-control" name="telMob" value=${employee.telMob}>
                Work phone <input type="text" class="form-control" name="telPro" value=${employee.telPro}>
                Address <input type="text" class="form-control" name="address" value=${employee.address}>
                Postal code <input type="text" class="form-control" name="postalcode" value=${employee.postalcode}>
                <div class="row">
                    <div class="col">
                        City <input type="text" class="form-control" name="city" value=${employee.city}>
                    </div>
                    <div class="col">
                        Email <input type="text" class="form-control" name="email" value=${employee.email}>
                    </div>
                  </div>
                </div>
            <input type='submit' name="action" value="Cancel"/>
            <c:if test = "${user.login == 'admin'}">
                <input type='submit' name="action" value="Save"/>
            </c:if>
            <button type='submit' name='action' value='Logout'><span class="glyphicon glyphicon-off"></span></button>
        </form>
    </body>
</html>