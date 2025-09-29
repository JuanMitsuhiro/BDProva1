<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Médico</title>
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
		<h1>Cadastro de Médicos</h1>
		<br/>
		<form action="medico" method="post">
			<table >
				<tr>
					<td colspan="3">
						<label for="rg">RG:</label>
   						<input type="text" maxlength="9"
						id="rg" name="rg" placeholder="apenas números"
						value='<c:out value="${medico.rg}"/>'>
						
						<input type="submit" style="margin-left: 20px"
						id="botao" name="botao" value="Buscar"
						class="btn btn-info">
					</td>	
				<tr/>
				<tr>
					<td colspan="4">
						<label for="nome">Nome:</label>
						<input type="text" 
						id="nome" name="nome" placeholder="Nome completo"
						value='<c:out value="${medico.nome}"/>'>
					</td>				
				<tr/>
				<tr>
					<td colspan="3">
						<label for="nome">Telefone:</label>
						<input type="tel" maxlength="11" pattern="[0-9]*"
						id="telefone" name="telefone" placeholder="(DDD + telefone)"
						value='<c:out value="${medico.telefone}"/>'>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<label for="nome">Especialidade:</label>
						<select id="especialidade" name="especialidade" class="form-select" aria-label="Selecione uma especialidade">
			            	<option selected value="">-- Selecione uma opção --</option>	
			            	<c:forEach var="especialidade" items="${especialidades}">
			                	<option value="${especialidade.codigo}"
			                		<c:if test="${medico.especialidade == especialidade.codigo}">selected</c:if>>
			                		${especialidade.nome}
			                	</option>
			            	</c:forEach>
			        </select>
					</td>
					<td colspan="2">
						<label for="turno">Turno</label>
					    <select id="turno" name="turno" class="form-select" aria-label="Selecione um turno">
					        <option value="MANHA">Manhã</option>
					        <option value="TARDE">Tarde</option>
					        <option value="NOITE">Noite</option>
					    </select>
					</td>
				</tr>
				<tr>
					<td>
						<input type="submit"
						id="botao" name="botao" value="Inserir"
						class="btn btn-success">
					</td>								
					<td>
						<input type="submit"
						id="botao" name="botao" value="Atualizar"
						class="btn btn-primary">
					</td>								
					<td>
						<input type="submit"
						id="botao" name="botao" value="Excluir"
						class="btn btn-danger">
					</td>								
					<td>
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
		<c:if test="${not empty medicos}">
			<table class="table table-info table-striped">
				<thead>
					<tr>
						<th>RG</th>
						<th>Nome</th>
						<th>Telefone</th>
						<th>Especialidade</th>
						<th>Turno</th>
						<th></th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="m" items="${medicos}">
						<tr>
							<td>${m.rg}</td>
							<td>${m.nome}</td>
							<td>${m.telefone}</td>
							<td>${m.nomeEspecialidade}</td>
							<td>${m.turno}</td>
							<td><a href="medico?acao=editar&rg=${m.rg}">EDITAR</a></td>
							<td><a href="medico?acao=excluir&rg=${m.rg}">EXCLUIR</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
</body>
</html>