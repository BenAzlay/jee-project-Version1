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
        
            
        
        <c:if test = "${empList.size() == 0}">
            <font color="red">The company has no employee!</font>
        </c:if>
        <form method='post' action="Controller">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th scope="col">Sel</th>
                        <th scope="col">Name</th>
                        <th scope="col">First name</th>
                        <th scope="col">Home phone</th>
                        <th scope="col">Mobile phone</th>
                        <th scope="col">Work phone</th>
                        <th scope="col">Address</th>
                        <th scope="col">Postal Code</th>
                        <th scope="col">City</th>
                        <th scope="col">Email</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${empList}" var="employee">
                    
                    <tr> 
                    
                        <th scope="row"><div class="form-check"><input class="form-check-input" type="radio" name="employeeID" id="exampleRadios1" value=${employee.id} checked></div></th>
                        <td>  ${employee.lastname}</td>
                        <td>  ${employee.firstname}</td>
                        <td>  ${employee.telHome}</td>
                        <td>  ${employee.telMob}</td>
                        <td>  ${employee.telPro}</td>
                        <td>  ${employee.address}</td>
                        <td>  ${employee.postalcode}</td>
                        <td>  ${employee.city}</td>
                        <td>  ${employee.email}  </td>
                    </tr> 
                </c:forEach>
                </tbody>
            </table>
            <input type='submit' name="action" value="Details"/>
            <c:if test = "${user.login == 'admin'}">
                <input type='submit' name="action" value="Add"/>
                <input type='submit' name="action" value="Delete"/>
            </c:if>
            <button type='submit' name='action' value='Logout'><span class="glyphicon glyphicon-off"></span></button>
            
        </form>
    </body>
</html>