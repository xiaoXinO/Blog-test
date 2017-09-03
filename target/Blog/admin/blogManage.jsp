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
        function formatBlogType(val, row) {
            return val.typeName;
        }
        function formatTitle(val, row) {
            return "<a target='_blank' href='${pageContext.request.contextPath}/blog/articles/" + row.id + ".html'>" + val + "</a>";
        }
        function search() {
            $("#dg").datagrid('load', {
                "title": $("#s_title").val()
            });
        }
        function deleteBlog() {
            var selectRows = $("#dg").datagrid("getSelections");
            if (selectRows.length == 0) {
                $.messager.alert("系统提示", "请选择要删除的数据！");
                return;
            }
            $.messager.confirm("系统提示", "<font color='red'>你确认要删除" + selectRows.length +
                    "条数据吗？</font>", function (r) {
                        if (r) {
                            var strIds = [];
                            for (var i = 0; i < selectRows.length; i++) {
                                strIds.push(selectRows[i].id);
                            }
                            var ids = strIds.join(",");
                            $.post(
                                    "${pageContext.request.contextPath}/admin/blog/delete.do",
                                    {"ids": ids}, function (result) {
                                        if (result.success) {
                                            $.messager.alert("系统提示", "删除成功!");
                                            $("#dg").datagrid("reload");
                                        } else {
                                            $.messager.alert("系统提示", "删除数据失败!");
                                        }
                                    }, "json"
                            );
                        }
                    }
            );
        }
        function openBlogModifyTab() {
            var selectRows = $("#dg").datagrid("getSelections");
            if (selectRows.length != 1) {
                $.messager.alert("系统提示", "请选择一条数据!");
                return;
            } else {
                var row = selectRows[0];
                window.parent.openTab("修改博客", "modifyBlog.jsp?id=" + row.id, "icon-writeblog;");
            }
        }
    </script>
</head>
<body style="margin: 10px;">
<table title="博客管理" id="dg" class="easyui-datagrid" rownumbers="true"
       url="${pageContext.request.contextPath}/admin/blog/list.do"
       fit="true" rownumbers="true" toolbar="#tb" fitColumns="true" pagination="true">
    <thead>
    <tr>
        <th Field="cb" checkbox="true" align="center"></th>
        <th Field="id" align="center">编号</th>
        <th Field="title" width="100" formatter="formatTitle" align="center">标题</th>
        <th Field="releaseDate" width="50" align="center">发布日期</th>
        <th Field="blogType" width="50" formatter="formatBlogType" align="center">博客类型</th>
    </tr>
    </thead>
</table>
<div id="tb">
    <div>
        <a class="easyui-linkbutton" iconCls="icon-edit" href="javascript:openBlogModifyTab()" plain="true">修改</a>
        &nbsp;
        <a class="easyui-linkbutton" iconCls="icon-remove" href="javascript:deleteBlog()" plain="true">删除</a>
    </div>
    <div>
        &nbsp;&nbsp;标题:&nbsp;&nbsp;<input type="text" id="s_title" name="s_title" size="20"
                                          onkeydown="if(event.keyCode==13) search()"/>
        <a class="easyui-linkbutton" iconCls="icon-search" href="javascript:search()" plain="true">搜索</a>&nbsp;
    </div>
</div>
</body>
</html>
