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
        var url;
        function openBlogTypeAddDialog() {
            $("#dlg").dialog("open").dialog("setTitle", "添加博客类别");
            url = "${pageContext.request.contextPath}/admin/blogType/save.do";
        }
        function openBlogTypeModifyDialog() {
            var selectRows = $("#dg").datagrid("getSelections");
            if (selectRows.length != 1) {
                $.messager.alert("系统提示", "请选择一条需要修改的博客类别!");
                return;
            } else {
                var row = selectRows[0]
                $("#dlg").dialog("open").dialog("setTitle", "修改博客类别");
                $("#fm").form("load", row);
                url = "${pageContext.request.contextPath}/admin/blogType/save.do?id=" + row.id + "";

            }
        }
        function deleteBlogType() {
            var selectRows = $("#dg").datagrid("getSelections");
            if (selectRows.length <= 0) {
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
                                    "${pageContext.request.contextPath}/admin/blogType/delete.do",
                                    {"ids": ids}, function (result) {
                                        if (result.success) {
                                            if (result.exist) {
                                                $.messager.alert("系统提示", result.exist);
                                            } else {
                                                $.messager.alert("系统提示", "删除数据成功!");
                                            }
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
        function saveBlogType() {
            $("#fm").form("submit", {
                url: url,
                onSubmit: function () {
                    return $(this).form("validate");
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.success) {
                        $.messager.alert("系统提示", "保存成功！");
                        resetValue();
                        $("#dlg").dialog("close");
                        $("#dg").datagrid("reload");
                    } else {
                        $.messager.alert("系统提示", "保存失败！");
                        return;
                    }
                }
            });
        }
        function closeBlogTypeDialog() {
            $("#dlg").dialog("close");
            resetValue();
        }
        function resetValue() {
            $("#typeName").val("");
            $("#orderNo").val("");
        }
    </script>
</head>
<body style="margin: 10px;">
<table title="博客类别管理" id="dg" class="easyui-datagrid" rownumbers="true"
       url="${pageContext.request.contextPath}/admin/blogType/list.do"
       fit="true" rownumbers="true" toolbar="#tb" fitColumns="true" pagination="true">
    <thead>
    <tr>
        <th Field="cb" checkbox="true" align="center"></th>
        <th Field="id" width="50" align="center">编号</th>
        <th Field="typeName" width="200" align="center">博客类型名称</th>
        <th Field="orderNo" width="200" align="center">排序序号</th>
    </tr>
    </thead>
</table>
<div id="tb">
    <div>
        <a class="easyui-linkbutton" iconCls="icon-add" href="javascript:openBlogTypeAddDialog()" plain="true">添加</a>
        &nbsp;
        <a class="easyui-linkbutton" iconCls="icon-edit" href="javascript:openBlogTypeModifyDialog()"
           plain="true">修改</a>
        &nbsp;
        <a class="easyui-linkbutton" iconCls="icon-remove" href="javascript:deleteBlogType()" plain="true">删除</a>
    </div>
</div>
<div id="dlg" class="easyui-dialog" style="width: 550px;height: 180px;padding: 10px 20px" closed="true"
     buttons="#dg-buttons">
    <form id="fm" method="post">
        <table cellspacing="8px">
            <tr>
                <td>博客类别名称：</td>
                <td>
                    <input type="text" id="typeName" name="typeName" class="easyui-validatebox" required="true"/>
                </td>
            </tr>
            <tr>
                <td>博客类别排序：</td>
                <td>
                    <input type="text" id="orderNo" name="orderNo" class="easyui-numberbox" required="true"
                           style="width: 60px"/>&nbsp;(类别根据排序序号从小到大排序)
                </td>
            </tr>
        </table>
    </form>
</div>

<div id="dg-buttons">
    <a href="javascript:saveBlogType()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:closeBlogTypeDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a></div>
</body>
</html>
