<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="infinitybank.model.Client" %>
<%@ page import="infinitybank.model.JavaBeans" %>
<%@ page import="java.util.List" %>
<%
	@SuppressWarnings("unchecked")
	List<Client> clientList = (List<Client>)request.getAttribute("clients");
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>Infinity Bank | Dashboard</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
<link rel="icon" href="images/favicon.png">
<link rel="stylesheet" type="text/css" href="css/normalize.css">
<link rel="stylesheet" type="text/css" href="css/style.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
	<div id="app" class="principal">
		<div class="content">
			<div>	
				<h1>Conta</h1>
				<a class="newaccount__btn bg-green" href="/newaccount.html">criar nova conta</a>
				<a class="newaccount__btn bg-green" href="/relatory">criar relatório</a>
				
				<table id="table">
					<thead>
						<tr>
							<th>Id</th>
							<th>Nome</th>
							<th>Grana</th>
							<th>Ações</th>
						</tr>
					</thead>
					<tbody>
						<% for(int i = 0; i < clientList.size(); i++) { 
							Integer id = clientList.get(i).getId();
							String name = clientList.get(i).getClientName();
							Double money = clientList.get(i).getAccount().getMoney();
							String password = clientList.get(i).getPassword();
						%>
						
							<tr>
								<td><%= id %></td>
								<td><%= name %></td>
								<td class="money"><%= money %></td>
								<td>
									<div class="actions">
										<a href="/addmoney.html?<%= String.format("id=%s&amount=%.2f", id, money) %>" class="actions__btn bg-add">adicionar</a>
										<a href="/editaccount.html?<%= String.format("id=%s&name=%s&password=%s", id, name, password) %>" class="actions__btn bg-edit">editar</a>
										<a href="javascript: handleDelete(<%=id %>)" class="actions__btn bg-delete">excluir</a>
									</div>
								</td>
							</tr>
						
						<% } %>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<script src="js/valida.js"></script>
</body>
</html>
