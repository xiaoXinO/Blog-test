<%--
  Created by IntelliJ IDEA.
  User: xin
  Date: 2017/2/9
  Time: 10:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="data_list">
    <div class="data_list_title">
        <img src="${pageContext.request.contextPath}/static/images/about_icon.png"/>
        关于博主
    </div>
    <div style="padding: 30px;">
        ${blogger.profile}
    </div>
</div>