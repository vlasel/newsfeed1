<%@ page import="model.pojos.User" %>
<%--
<%@page import="java.model.util.Iterator"%>
<%@page import="model.pojos.PageData"%>
<%@page import="java.model.util.ArrayList"%>
<%@page import="model.model.dao.DbPagesDAO"%>
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>User page</title>
	<style>
		a.edit { color: #FFA500; font: 80% sans-serif; }
		a.delete { color: #FF0000; font: 80% sans-serif; }
		a.add { color: #008000; font: 80% sans-serif; }
		span.menu {font: bold 100% serif;}
		span.info {font: 90% serif;}
		dd.annot {font: 90% serif; font-style: italic;}
        hr {
             border: none; /* Убираем границу */
            background-color: #AAAAAA; /* Цвет линии */
            color: #AAAAAA; /* Цвет линии для IE6-7 */
            height: 2px; /* Толщина линии */
         }
        input.number {width:30px}
	</style>
</head>

<body>

<table border="1" width="100%" cellpadding="3">

<tr><td colspan="2">

    <table border="0" width="100%">
        <tr>
            <td width="200"><img alt="tomcat.png" src="images/tomcat.png"></td>
            <td align="center"><h2>  User's page</h2></td>
        </tr>
    </table>

</td>

<td width="300">
<!--_________________ user and auth ______________________  -->
<p>
<c:if test="${ empty sessionScope.userInfo }">
	<a href="Auth">&gt;&gt;Authorize to Admin's mode</a>
</c:if>
<c:if test="${!empty sessionScope.userInfo }">
	Hello, <c:out value="${ sessionScope.userInfo.name }"></c:out>&nbsp;<c:out value="${ sessionScope.userInfo.surname }"></c:out>!
	<br>You login: <c:out value="${ sessionScope.userData.email }"></c:out>
	<form action="Auth" method="post">
		<input type="hidden" name="operation" value="logout">
		<input type="submit" value="Logout">
	</form>
	<p>
	<a href="admin/AdminController">&gt;&gt;go to Admin mode</a>
	</p>
</c:if>
</p>
<!--______________________________________________________  -->
</td>

</tr>


<tr>

<!--___________________ Left - Menu  _________________________________-->
<td width="300" valign ="top">

<%--
Выберите дату:
<br>
<form>
    <select size="1" name="Выберите дату">
        <option value="22.03.2014">22.03.2014</option>
        <option value="23.03.2014">22.03.2014</option>
    </select>
    <input type="submit" value=Применить>
</form>
--%>

<h3>Категории новостей:</h3>
<a href="SiteController">Все новости</a>
<ul>
	<c:forEach var="category" items="${ requestScope.categoryList}">
	<li>
		<a href="SiteController?viewby=category&id=<c:out value="${ category.id }"></c:out>">
            <c:out value="${ category.name }"></c:out>
        </a>
	</li>
	</c:forEach>
	
</ul>
</td>
<!--_________________________________________________________-->





<!--_______________________ Right - Main View ____________________________ -->
<td colspan="2" valign="top">




<c:if test="${ param.viewby == 'onenews' }">
<p>
<a href="SiteController">&lt;&lt; Назад к списку новостей</a>
</p>
	<h2 align="center"><c:out value="${ news.name }" escapeXml="false"></c:out></h2>
				<span class="info">
				
				<c:out value="${ news.date }"></c:out>-<c:out value="${ newsdInfo.time }"></c:out>
				&ensp;- Автор:
                    <c:if test="${!empty sessionScope.userInfo }">
                        &nbsp;<c:out value="${ news.user.name }"></c:out>
                        &nbsp;<c:out value="${ news.user.surname }"></c:out>
                    </c:if>
				
				</span>
				
				<c:out value="${ news.maintext }" escapeXml="false"></c:out>
</c:if>


<c:if test="${ param.viewby != 'onenews' }">
<h3 align="center">Список новостей</h3>
        <c:forEach var="category" items="${ requestScope.categoryList}">
            <c:if test="${ category.id == param.id}">
                <h4 align="center">в категории
                    <c:out value="'${ category.name }'"></c:out>
                </h4>
            </c:if>
        </c:forEach>


    <hr>
    <form action="SiteController" method="get">
        <c:if test="${ requestScope.viewby == 'category' and not empty requestScope.id}">
            <input type="hidden" name="id" value="<c:out value="${ requestScope.id }"></c:out>">
            <input type="hidden" name="viewby" value="category">
        </c:if>
        Сортировать по &nbsp;
        <select size="1" name="orderField">
            <option value="date" <c:if test="${ requestScope.orderField == 'date' }">selected</c:if>>Дате</option>
            <%--<option value="author" <c:if test="${ param.orderField == 'author' }">selected</c:if>>Автору</option>--%>
            <option value="name" <c:if test="${ requestScope.orderField == 'name' }">selected</c:if>>Названию</option>
        </select>
        &nbsp; по &nbsp;
        <select size="1" name="orderDir">
            <option value="desc" <c:if test="${ requestScope.orderDir == 'desc' }">selected</c:if>>Убыванию</option>
            <option value="asc" <c:if test="${ requestScope.orderDir == 'asc' }">selected</c:if>>Возрастанию</option>
        </select>
        Выводить по
        <input class="number" type="text" name="maxResults" value="<c:out value="${requestScope.maxResults}"></c:out>">
        Страница №
        <input class="number" type="text" name="pageNumber" value="<c:out value="${requestScope.pageNumber}"></c:out>">
        из &nbsp;<c:out value="${requestScope.pagesCount}"></c:out>
        <input type="submit" value="Применить">
    </form>
    Всего новостей: &nbsp;<c:out value="${requestScope.newsListCount}"></c:out>
    <hr>
<dl>
<c:if test="${ not empty requestScope.newsList }">
<c:forEach var="news" items="${ requestScope.newsList }">
			<dt>
				<span class="info">
				<c:out value="${ news.date }"></c:out>&ndash;<c:out value="${ news.time }"></c:out>
				</span>
				<a href="SiteController?viewby=onenews&id=<c:out value="${ news.id }"></c:out>">
					<c:out value="${ news.name }"></c:out></a>
				<span class="info">
					&ensp; - Автор:
                    &nbsp;<c:out value="${ news.userInfo.name }"></c:out>
                    &nbsp;<c:out value="${ news.userInfo.surname }"></c:out>
				</span>
			</dt>
			<dd class="annot">
				&emsp;&emsp;&emsp;&emsp;&ensp;<c:out value="${ news.annotation }"></c:out>
			</dd>
</c:forEach>
</c:if>
</dl>
</c:if>



</td>
</tr>
</table>

</body>
</html>