<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-LN+7fdVzj6u52u30Kp6M/trliBMCMKTyK833zpbD+pXdCLuTusPj697FH4R/5mcr" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/js/bootstrap.bundle.min.js" integrity="sha384-ndDqU0Gzau9qJ1lfW4pNLlhNTkCfHzAVBReH9diLvGRem5+R9g2FzA8ZGN954O5Q" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<title>Cadastro</title>
</head>
<body>
	<div align="center">
		<jsp:include page="menu.jsp" />
	</div>
	<br />
	<div class="conteiner" align="center">
		<h1>Cadastro de Cliente</h1>
		<br />
		<form action="cadastro" method="post">
			<table>
				<tr>
					<td colspan="2">
						<label for="rg">RG:</label>
   						<input type="text" maxlength="9"
						id="rg" name="rg" placeholder="apenas números"
						value='<c:out value="${cliente.rg}"/>'>
					</td>
				</tr>
				<tr>
					<td>
						<label for="nome">Nome:</label>
						<input type="text" 
						id="nome" name="nome" placeholder="Nome completo"
						value='<c:out value="${cliente.nome}"/>'>
					</td>
				</tr>
				<tr>
					<td colspan="4">
					<label for="nascimento">Data nascimento:</label>
						<input type="date" 
						id="nascimento" name="nascimento"
						value='<c:out value="${cliente.nascimento}"/>'>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<label for="telefone">Telefone:</label>
						<input type="tel" maxlength="11" pattern="[0-9]*"
						id="telefone" name="telefone" placeholder="(DDD + telefone)"
						value='<c:out value="${cliente.telefone}"/>'>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<label for="senha">Senha:</label>
						<input type="password" maxlength="8"
						id="senha" name="senha" placeholder="Senha"
						value='<c:out value="${cliente.senha}"/>'>
					</td>
				</tr>
				<tr>						
					<td>
						<input type="submit"
						id="botao" name="botao" value="Cadastrar"
						class="btn btn-primary">
					</td>								
					<td>
				</tr>
				<tr>
					<td>
						<a href="login">ENTRAR</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<br />
		<div class="conteiner" align="center">
		<c:if test="${not empty saida}">
			<h2 style="color: blue;"><c:out value="${saida}" /></h2>
		</c:if>
	</div>
	<div class="conteiner" align="center">
		<c:if test="${not empty erro}">
			<h2 style="color: red;"><c:out value="${erro}" /></h2>
		</c:if>
	</div>
</body>
</html>