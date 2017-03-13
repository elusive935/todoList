
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
        }
        td {
            padding: 8px;
        }
        input[title="undoneText"]{
            border: none;
            background: transparent;
            color: brown;
        }
        input[title="doneText"]{
            border:none;
            background: transparent;
            color: gray;
            text-decoration:line-through;
        }
    </style>

    <title>TODO List</title>
</head>
<body>
    <div align="center">

        <form:form method="post" commandName="filterState" action="filter">
            <form:select path="filterSelected" items="${filterList}"/>
            <input type="submit" value="Show"/>
        </form:form>

        <form action="add" method="post">
            <input type="submit" name="add" value="Add"/>
        </form>

        <table>
            <caption><h3>TODO List</h3></caption>

            <c:forEach items="${tasks}" var="task" varStatus="id">
                <spring:url value="updateTask/${id.index}" var="controller"/>
                <tr>
                    <c:choose>
                        <c:when test="${task.status == 'true'}">
                            <form:form action="${controller}" method="post">
                                <td><input type="submit" value="Delete" name="delete"/></td>
                            </form:form>

                            <form:form action="${controller}" method="post">
                                <td><input type="submit" value="Undone" name="undone"/></td>
                            </form:form>

                            <form:form action="${controller}" method="post">
                                <td><input type="text" title="doneText" name="edit" value="${task.text}"/></td>
                            </form:form>
                        </c:when>

                        <c:otherwise>
                            <form:form action="${controller}" method="post">
                                <td><input type="submit" value="Delete" name="delete"/></td>
                            </form:form>

                            <form:form action="${controller}" method="post">
                                <td><input type="submit" value="Done!" name="done"/></td>
                            </form:form>

                            <form:form action="${controller}" method="post">
                                <td><input type="text" title="undoneText" name="edit" value="${task.text}"/></td>
                            </form:form>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>

            <c:choose>
                <c:when test="${tasks.size() == 0 && currentPage > 1}">
                    <c:redirect url="paging/${currentPage - 1}"/>
                </c:when>
            </c:choose>

        </table>

        <c:choose>
            <c:when test="${count == 0}">
                <c:choose>
                    <c:when test="${filterState.filterSelected != 'Done'}">
                        Everything is done!<br><br>
                        Now you can relax a bit.<br>
                        Take a cup of whiskey and add next todo task to become sober.
                    </c:when>
                    <c:otherwise>
                        Nothing is done.<br><br>
                        You should work harder.<br>
                        Pull up all your courage and go ahead, guy.
                    </c:otherwise>
                </c:choose>
            </c:when>

            <c:otherwise>
                Page
                <c:set var="lastPage" value="${count%10}"/>
                <c:set var="pageNum" value="${count/10}"/>
                <c:if test="${lastPage > 0}">
                    <c:set var="pageNum" value="${pageNum + 1}"/>
                </c:if>

                <c:forEach begin="1" end="${pageNum}" varStatus="loop">
                    <c:choose>
                        <c:when test="${currentPage != loop.count}">
                            <a href="<c:url value="paging/${loop.count}" />"> ${loop.count} </a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${loop.count}"/>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
