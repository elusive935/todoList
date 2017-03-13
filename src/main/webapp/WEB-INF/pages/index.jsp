
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <style type="text/css">
        h3 {
            color: red;
            font-size: 20pt;
            text-align: center;
        }
        body {
            background-color: beige;
            color: brown;
            alignment: center;
        }
        table {
            border-collapse: collapse;
            /*cellpadding:"8";*/
        }
        td {
            padding: 8px;
        }
        .doneText{
            color: gray;
            text-decoration:line-through;
        }
    </style>

    <title>TODO List</title>
</head>
<body>
    <div align="center">

        <%--<form:form action="filterSelected" method="get" >--%>
            <%--<select title="filterBox" name="filterBox" onchange="this.form.submit();" >--%>

            <form:form method="post" commandName="filterState" action="filter">

                <form:select path="filterSelected" items="${filterList}"/>
                <input type="submit" value="Show"/>

                <%--<form:option value="all" label="All"/>--%>
                <%--<form:option value="allDone" label="Done"/>--%>
                <%--<form:option value="allUndone" label="Undone"/>--%>
                <%--<c:choose>--%>
                    <%--<c:when test="${filterSelected == 'all'}">--%>
                        <%--<option value="all" selected>All</option>--%>
                    <%--</c:when>--%>
                    <%--<c:otherwise>--%>
                        <%--<option value="all">All</option>--%>
                    <%--</c:otherwise>--%>
                <%--</c:choose>--%>

                <%--<c:choose>--%>
                    <%--<c:when test="${filterSelected == 'done'}">--%>
                        <%--<option value="allDone" selected>Done</option>--%>
                    <%--</c:when>--%>
                    <%--<c:otherwise>--%>
                        <%--<option value="allDone">Done</option>--%>
                    <%--</c:otherwise>--%>
                <%--</c:choose>--%>

                <%--<c:choose>--%>
                    <%--<c:when test="${filterSelected == 'undone'}">--%>
                        <%--<option value="allUndone" selected>Undone</option>--%>
                    <%--</c:when>--%>
                    <%--<c:otherwise>--%>
                        <%--<option value="allUndone">Undone</option>--%>
                    <%--</c:otherwise>--%>
                <%--</c:choose>--%>
                </form:form>
            <%--</select>--%>
        <%--</form:form>--%>

        <form action="updateTask/add" method="post">
            <input type="submit" name="add" value="Add"/>
        </form>

            <table>
                <caption><h3>TODO List</h3></caption>
                <c:forEach items="${tasks}" var="task" varStatus="id">
                    <%--<c:set var="controller" value="updateTask/${id.index}"/>--%>


                    <%--<spring:url value="test/${id.index}" var="buttonUrl" />--%>

                    <%--<c:remove var="controller"/>--%>
                    <%--<c:set var="controller" value="updateTask/${id.index}"/>--%>
                    <spring:url value="updateTask/${id.index}" var="controller"/>
                    <c:set var="listNumber" value="0" scope="page"/>
                    <%--<c:out value="${controller}"/>--%>
                        <tr>
                            <c:choose>
                                <c:when test="${task.status == 'true'}">
                                    <c:if test="${filterState.filterSelected == 'All' || filterState.filterSelected == 'Done'}">
                                        <%--<td><input type="submit" name="undone" value="${id.index} Undone"/></td>--%>
                                        <form:form action="${controller}/edit" method="post" modelAttribute="text">
                                            <td><input type="submit" value="Edit"/></td>
                                        </form:form>
                                        <%--<td><button onclick="location.href='${controller}/edit'">Edit</button></td>--%>
                                        <td><button onclick="location.href='${controller}/delete'">Delete</button></td>
                                        <td><button onclick="location.href='${controller}/undone'">Undone</button></td>
                                        <td contenteditable="true">${task.text}</td>
                                        <c:set var="listNumber" value="${listNumber + 1}" scope="page"/>
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    <c:if test="${filterState.filterSelected == 'All' || filterState.filterSelected == 'Undone'}">
                                        <form:form action="${controller}/edit" method="post" modelAttribute="text">
                                            <td><input type="submit" value="Edit"/></td>
                                            <c:set var="text" value="${task.text}"/>
                                        </form:form>
                                        <%--<td><button onclick="location.href='${controller}/edit'">Edit</button></td>--%>
                                        <td><button onclick="location.href='${controller}/delete'">Delete</button></td>
                                        <td><button onclick="location.href='${controller}/done'">Done!</button></td>
                                        <td contenteditable="true"">${task.text}</td>
                                        <c:set var="listNumber" value="${listNumber + 1}" scope="page"/>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                </c:forEach>

            </table>

            Page
            <c:out value="${listNumber}"/>
            <c:forEach begin="1" end="${listNumber/10}" varStatus="loop">
                <c:out value="${loop.count}"/>
            </c:forEach>

    </div>
</body>
</html>
