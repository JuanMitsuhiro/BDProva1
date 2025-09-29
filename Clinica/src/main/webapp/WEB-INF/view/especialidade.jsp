<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Especialidade</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

</head>
<body>
	<div align="center">
		<jsp:include page="menu.jsp"/>
	</div>
	<br/>
	<div class="conteiner" align="center">
		<h1>Cadastro de Especialidades</h1>
		<br/>
		<form action="especialidade" method="post">
			<table >
				<tr>
					<td colspan="3">
						<label for="codigo">codigo:</label>
   						<input type="number" maxlength="3"
						id="codigo" name="codigo" placeholder="Apenas números"
						value='<c:out value="${especialidade.codigo}"/>'>
						
						<input type="submit" 
						id="botao" name="botao" value="Buscar"
						class="btn btn-info">
					</td>	
				<tr/>
				<tr>
					<td colspan="4">
						<label for="nome">Nome:</label>
						<input type="text" style="min-width: 270px;"
						id="nome" name="nome" placeholder="Nome da especialidade"
						value='<c:out value="${especialidade.nome}"/>'>
					</td>				
				<tr/>
				<tr>
					<td>
						<input type="submit"
						id="botao" name="botao" value="Inserir"
						class="btn btn-success">

						<input type="submit"
						id="botao" name="botao" value="Atualizar"
						class="btn btn-primary">

						<input type="submit"
						id="botao" name="botao" value="Excluir"
						class="btn btn-danger">

						<input type="submit"
						id="botao" name="botao" value="Listar"
						class="btn btn-secondary">
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
	<div class="conteiner" align="center">
		<c:if test="${not empty especialidades}">
			<table class="table table-info table-striped">
				<thead>
					<tr>
						<th>Código</th>
						<th>Nome</th>
						<th></th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="esp" items="${especialidades}">
						<tr>
							<td>${esp.codigo}</td>
							<td>${esp.nome}</td>
							<td><a href="especialidade?acao=editar&codigo=${esp.codigo}">EDITAR</a></td>
							<td><a href="especialidade?acao=excluir&codigo=${esp.codigo}">EXCLUIR</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
</body>
</html>