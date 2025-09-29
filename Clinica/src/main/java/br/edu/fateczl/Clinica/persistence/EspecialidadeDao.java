package br.edu.fateczl.Clinica.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.edu.fateczl.Clinica.model.Especialidade;



@Repository
public class EspecialidadeDao  implements ICrud<Especialidade>{

	@Autowired
	private GenericDao gDao;

	@Override
	public Especialidade buscar(Especialidade cliente) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "SELECT codigo, nome FROM Especialidade WHERE codigo = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1,Integer.toString(cliente.getCodigo()));
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			cliente.setCodigo(rs.getInt("codigo"));
			cliente.setNome(rs.getString("nome"));

			
		}
		rs.close();
		ps.close();
		return cliente;
	}

	@Override
	public List<Especialidade> listar() throws SQLException, ClassNotFoundException {
		List<Especialidade> clientes = new ArrayList<>();
		Connection c = gDao.getConnection();
		String sql = "SELECT codigo, nome FROM Especialidade";
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Especialidade cliente = new Especialidade();
			cliente.setCodigo(rs.getInt("codigo"));
			cliente.setNome(rs.getString("nome"));	
			
			clientes.add(cliente);
		}
		rs.close();
		ps.close();
		return clientes;
	}


	@Override
	public String inserir(Especialidade especialidade) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "{CALL sp_cadastraespecialidade(?,?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, "I");
		cs.setString(2, Integer.toString(especialidade.getCodigo()));
		cs.setString(3, especialidade.getNome());
		cs.registerOutParameter(4, Types.VARCHAR);
		cs.execute();
		
		String saida = cs.getString(4);
		cs.close();
		
		return saida;
	}

	@Override
	public String atualizar(Especialidade especialidade) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "{CALL sp_cadastraespecialidade(?,?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, "A");
		cs.setString(2, Integer.toString(especialidade.getCodigo()));
		cs.setString(3, especialidade.getNome());

		cs.registerOutParameter(4, Types.VARCHAR);
		cs.execute();
		
		String saida = cs.getString(4);
		cs.close();
		
		return saida;

	}
	
	@Override
	public String excluir(Especialidade especialidade) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "{CALL sp_cadastraespecialidade(?,?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, "D");
		cs.setString(2, Integer.toString(especialidade.getCodigo()));
		cs.setNull(3, Types.VARCHAR);
		cs.registerOutParameter(4, Types.VARCHAR);
		cs.execute();
		
		String saida = cs.getString(4);
		cs.close();
		
		return saida;
	}
	
}
