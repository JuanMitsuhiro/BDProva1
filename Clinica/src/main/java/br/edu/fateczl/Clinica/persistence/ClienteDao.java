package br.edu.fateczl.Clinica.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.edu.fateczl.Clinica.model.Cliente;



@Repository
public class ClienteDao  implements ICrud<Cliente>{

	@Autowired
	private GenericDao gDao;

	@Override
	public Cliente buscar(Cliente cliente) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "SELECT rg, nome, nascimento, telefone, senha FROM Cliente WHERE rg = ? AND senha = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1,cliente.getRg());
		ps.setString(2,cliente.getSenha());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			cliente.setRg(rs.getString("rg"));
			cliente.setNome(rs.getString("nome"));
			cliente.setNascimento(LocalDate.parse(rs.getString("nascimento")));
			cliente.setTelefone(rs.getString("telefone"));
			cliente.setSenha(rs.getString("senha"));
		}
		rs.close();
		ps.close();
		return cliente;
	}

	@Override
	public List<Cliente> listar() throws SQLException, ClassNotFoundException {
		List<Cliente> clientes = new ArrayList<>();
		Connection c = gDao.getConnection();
		String sql = "SELECT rg, nome, nascimento,"
				+ " CONVERT(CHAR(10), nascimento, 103) AS dtNasc, telefone, senha "
				+ " FROM Cliente";
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Cliente cliente = new Cliente();
			cliente.setRg(rs.getString("rg"));
			cliente.setNome(rs.getString("nome"));
			cliente.setNascimento(LocalDate.parse(rs.getString("nascimento")));
			cliente.setDtNasc(rs.getString("dtNasc"));
			cliente.setTelefone(rs.getString("telefone"));
			cliente.setSenha(rs.getString("senha"));
			
			
			clientes.add(cliente);
		}
		rs.close();
		ps.close();
		return clientes;
	}


	@Override
	public String inserir(Cliente cliente) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "{CALL sp_cadastracliente(?,?,?,?,?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, "I");
		cs.setString(2, cliente.getRg());
		cs.setString(3, cliente.getNome());
		cs.setString(4, cliente.getNascimento().toString());
		cs.setString(5, cliente.getTelefone());
		cs.setString(6, cliente.getSenha());

		cs.registerOutParameter(7, Types.VARCHAR);
		cs.execute();
		
		String saida = cs.getString(7);
		cs.close();
		
		return saida;
	}

	@Override
	public String atualizar(Cliente cliente) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "{CALL sp_cadastracliente(?,?,?,?,?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, "A");
		cs.setString(2, cliente.getRg());
		cs.setString(3, cliente.getNome());
		cs.setString(4, cliente.getNascimento().toString());
		cs.setString(5, cliente.getTelefone());
		cs.setString(6, cliente.getSenha());

		cs.registerOutParameter(7, Types.VARCHAR);
		cs.execute();
		
		String saida = cs.getString(7);
		cs.close();
		
		return saida;

	}
	
	@Override
	public String excluir(Cliente cliente) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "{CALL sp_cadastracliente(?,?,?,?,?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, "D");
		cs.setString(2, cliente.getRg());
		cs.setNull(3, Types.VARCHAR);
		cs.setNull(4, Types.VARCHAR);
		cs.setNull(5, Types.VARCHAR);
		cs.setNull(6, Types.VARCHAR);
		cs.registerOutParameter(7, Types.VARCHAR);
		cs.execute();
		
		String saida = cs.getString(7);
		cs.close();
		
		return saida;
	}
	
	public boolean validarLogin(Cliente cliente) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "{CALL sp_validalogin(?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, cliente.getRg());
		cs.setString(2, cliente.getSenha());
		cs.registerOutParameter(3, Types.BIT);
		cs.execute();
		
		boolean saida = cs.getBoolean(3);
		cs.close();
		
		return saida;
	}
	
	public Cliente buscarUsuario(Cliente cliente) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "SELECT rg, nome, nascimento, telefone, senha FROM #login";
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			cliente.setRg(rs.getString("rg"));
			cliente.setNome(rs.getString("nome"));
			cliente.setNascimento(LocalDate.parse(rs.getString("nascimento")));
			cliente.setTelefone(rs.getString("telefone"));
			cliente.setSenha(rs.getString("senha"));
		}
		rs.close();
		ps.close();
		return cliente;
	}
}
