<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: xin
  Date: 2017/2/10
  Time: 20:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>

    <script type="text/javascript" charset="gbk"
            src="${pageContext.request.contextPath}/static/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="gbk"
            src="${pageContext.request.contextPath}/static/ueditor/ueditor.all.min.js"></script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="gbk"
            src="${pageContext.request.contextPath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript">
        function submitData() {
            var title = $("#title").val();
            var BlogTypeId = $("#BlogTypeId").combobox("getValue");
            var content = UE.getEditor('editor').getContent();
            var keyWord = $("#keyWord").val();
            if (title == null || title == "") {
                alert("请填写博客标题!");
            } else if (BlogTypeId == null || BlogTypeId == "") {
                alert("请选择博客类别");
            } else if (content == null || content == "") {
                alert("请填写博客内容");
            } else {
                $.post("${pageContext.request.contextPath}/admin/blog/save.do", {
                    "title": title,
                    "BlogType.id": BlogTypeId,
                    "content": content,
                    "summary": UE.getEditor('editor').getContentTxt().substr(0.150),
                    "keyWord": keyWord,
                    "contentNotag": UE.getEditor('editor').getContentTxt()
                }, function (result) {
                    if (result.success) {
                        alert("博客修改成功！");
                    } else {
                        alert("博客修改失败！");
                    }
                }, "json");
            }
        }
        function resultValue() {
            $("#title").val("");
            $("#keyWord").val("");
            $("#BlogTypeId").combobox("setValue", "");
            UE.getEditor('editor').setContent("");
        }
    </script>
</head>
<body>
<script type="text/javascript">
    var ue = UE.getEditor('editor');
    ue.addListener("ready", function () {
        UE.ajax.request("${pageContext.request.contextPath}/admin/blog/findById.do",
                {
                    method: "post",
                    async: false,
                    data: {"id": "${param.id}"},
                    onsuccess: function (result) {
                        result = eval("(" + result.responseText + ")");
                        $("#title").val(result.title);
                        $("#keyWord").val(result.keyWord);
                        $("#BlogTypeId").combobox("setValue", result.blogType.id);
                        UE.getEditor('editor').setContent(result.content);
                    }
                }
        );
    });
</script>
<div class="easyui-panel" title="修改博客" style="padding: 10px;;">
    <table cellpadding="20px;">
        <tr>
            <td width="80px;">博客标题:</td>
            <td>
                <input id="title" name="title" type="text" style="width:450px;"/>
            </td>
        </tr>
        <tr>
            <td>博客类别:</td>
            <td>
                <select class="easyui-combobox" id="BlogTypeId" name="blogType.id" style="width: 154px;"
                        editable="false" panelHeight="auto">
                    <option value="">请选择博客类别、、、</option>
                    <c:forEach items="${blogTypeCountList}" var="blogType">
                        <option value="${blogType.id}">${blogType.typeName}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td width="80px;" valign="top">博客内容:</td>
            <td>
                <script id="editor" name="content" style="width:100%;height:400px" type="text/plain"></script>
            </td>
        </tr>
        <tr>
            <td width="80px;">关键字:</td>
            <td>
                <input id="keyWord" name="keyWord" type="text" style="width:300px;"/>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <a href="javaScript:submitData();" class="easyui-linkbutton"
                   data-options="iconCls:'icon-submit'">修改博客</a>
            </td>
        </tr>
    </table>
</div>
</body>
</html>