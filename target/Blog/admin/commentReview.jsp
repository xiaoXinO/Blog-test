<%--
  Created by IntelliJ IDEA.
  User: xin
  Date: 2017/2/11
  Time: 17:39
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
    <script type="text/javascript">
        function formatterBlogTitle(val, row) {
            if(val==null){
                return "该博客已删除!";
            }else{
                return "<a target='blank' href='${pageContext.request.contextPath}/blog/articles/" + val.id + ".html'>" + val.title + "</a>";
            }
        }
        function commentReview(state){
            var selectRows = $("#dg").datagrid("getSelections");
            if (selectRows.length <= 0) {
                $.messager.alert("系统提示", "请选择要审核的评论！");
                return;
            }
            $.messager.confirm("系统提示", "<font color='red'>你确认要审核" + selectRows.length +
                    "片评论吗？</font>", function (r) {
                        if (r) {
                            var strIds = [];
                            for (var i = 0; i < selectRows.length; i++) {
                                strIds.push(selectRows[i].id);
                            }
                            var ids = strIds.join(",");
                            $.post(
                                    "${pageContext.request.contextPath}/admin/comment/review.do",
                                    {"ids": ids,"state":state}, function (result) {
                                        if (result.success) {
                                            $.messager.alert("系统提示", "提交成功!");
                                            $("#dg").datagrid("reload");
                                        } else {
                                            $.messager.alert("系统提示", "提交失败!");
                                        }
                                    }, "json"
                            );
                        }
                    }
            );
        }
    </script>
</head>
<body style="margin: 10px;">
<table title="评论管理" id="dg" class="easyui-datagrid" rownumbers="true"
       url="${pageContext.request.contextPath}/admin/comment/list.do?state=0"
       fit="true" rownumbers="true" toolbar="#tb" fitColumns="true" pagination="true">
    <thead>
    <tr>
        <th Field="cb" checkbox="true" align="center"></th>
        <th Field="id" width="20" align="center">编号</th>
        <th Field="blog" width="200" formatter="formatterBlogTitle" align="center">博客标题</th>
        <th Field="userIp" width="75" align="center">用户Ip</th>
        <th Field="content" width="200" align="center">评论内容</th>
        <th Field="commentDate" width="50" align="center">评论日期</th>
    </tr>
    </thead>
</table>
<div id="tb">
    <div>
        <a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:commentReview(1)" plain="true">审核通过</a>
        &nbsp;
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:commentReview(2)" plain="true">审核不通过</a>
    </div>
</div>
</body>
</html>
