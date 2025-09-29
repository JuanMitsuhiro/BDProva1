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

<title>Agendamento</title>
</head>
<body>
	<div align="center">
		<jsp:include page="menu.jsp" />
	</div>
	<br />
	<div class="conteiner" align="center">
		<h1>Agendamento de Consultas</h1>
		<br />
		<form action="consulta" method="post">
			<input type="hidden" name="codigo" value="${consulta.codigo}">
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
					<td colspan="3">
						<label for="nome">Especialidade:</label>
						<select id="especialidade" name="especialidade" class="form-select" aria-label="Selecione uma especialidade">
			            	<option selected value="">-- Selecione uma opção --</option>	
			            	<c:forEach var="especialidade" items="${especialidades}">
			                	<option value="${especialidade.codigo}"
			                		<c:if test="${consulta.especialidade == especialidade.codigo}">selected</c:if>>
			                		${especialidade.nome}
			                	</option>
			            	</c:forEach>
			        </select>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<label for="nome">Médico:</label>
						<select id="medico" name="medico" class="form-select" aria-label="Selecione um médico">
			            	<option selected value="">-- Selecione uma opção --</option>	
			            	<c:forEach var="medico" items="${medicos}">
			                	<option value="${medico.rg}"
			                		<c:if test="${consulta.medico == medico.rg}">selected</c:if>>
			                		${medico.nome}
			                	</option>
			            	</c:forEach>
			        </select>
					</td>
				</tr>
				<tr>
					<td>
						<label for="data">Data:</label>
						<input type="date" id="data" name="data"
						value='<c:out value="${consulta.data}"/>'>
					</td>
					<td>
						<label for="hora">Hora:</label>
						<input type="time" step="1" id="hora" name="hora"
						value='<c:out value="${consulta.hora}"/>'>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<label for="tipo">Tipo</label>
					    <select id="tipo" name="tipo" class="form-select" aria-label="Selecione um tipo">
					        <option value="PARTICULAR">Particular</option>
					        <option value="PLANO">Plano de saúde</option>
					    </select>
					</td>
				</tr>
				<tr>
					<td>
						<input type="submit"
						id="botao" name="botao" value="Agendar"
						class="btn btn-success">
					</td>															
					<td>
						<input type="submit"
						id="botao" name="botao" value="Cancelar"
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
		<c:if test="${not empty erro }">
			<h2 style="color: red;"><c:out value="${erro}" /></h2>
		</c:if>
	</div>
	<div class="conteiner" align="center">
		<c:if test="${not empty consultas}">
			<table class="table table-info table-striped">
				<thead>
					<tr>
						<th>Código</th>
						<th>Especialidade</th>
						<th>Data</th>
						<th>Hora</th>
						<th>Tipo</th>
						<th>Categoria</th>
						<th></th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="c" items="${consultas}">
						<tr>
							<td>${c.codigo}</td>
							<td>${c.nomeEspecialidade}</td>
							<td>${c.data}</td>
							<td>${c.hora}</td>
							<td>${c.tipo}</td>
							<td>${c.categoria}</td>
							<td><a href="consulta?acao=editar&codigo=${c.codigo}">EDITAR</a></td>
							<td><a href="consulta?acao=excluir&codigo=${c.codigo}">EXCLUIR</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
</body>
</html>