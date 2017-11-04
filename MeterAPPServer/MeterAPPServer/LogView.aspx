<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="LogView.aspx.cs" Inherits="MeterAPPServer.LogView" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title></title>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        
    <div>
       抄表员：
        <asp:DropDownList ID="DD_Login" runat="server"></asp:DropDownList>
         表本：
        <asp:DropDownList ID="DD_Note" runat="server"></asp:DropDownList>
        <asp:Button ID="Btn_Search" runat="server" Text="查询" OnClick="Btn_Search_Click" />
        <asp:Label ID="LB_Num" runat="server" Text=""></asp:Label>
    </div>
<div>

    <asp:GridView ID="GridView1" runat="server">
        <Columns>
            <asp:BoundField DataField="UserNo" FooterText="用户号" HeaderText="用户号" />
            <asp:BoundField DataField="StealNo" HeaderText="表号" />
            <asp:BoundField DataField="UserFName" HeaderText="用户名" />
            <asp:BoundField DataField="UpdateDate" HeaderText="上传日期" />
            <asp:BoundField DataField="CurrentMonthValue" HeaderText="本月读数" />
            <asp:BoundField DataField="TotalCharge" HeaderText="本月水费" />
            <asp:BoundField DataField="ChaoBiaoTag" HeaderText="抄表状态" />
            <asp:BoundField DataField="chargeID" HeaderText="收费ID" />
            <asp:BoundField DataField="OrderNumber" HeaderText="排序" />
        </Columns>
    </asp:GridView>
</div>
    </div>
        <asp:GridView ID="GV_LOG" runat="server">
        </asp:GridView>
    </form>
</body>
</html>
