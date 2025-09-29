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

import br.edu.fateczl.Clinica.model.Medico;



@Repository
public class MedicoDao  implements ICrud<Medico>{

	@Autowired
	private GenericDao gDao;

	@Override
	public Medico buscar(Medico medico) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "SELECT rg, nome, telefone, especialidade, turno FROM Medico WHERE rg = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1,medico.getRg());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			medico.setRg(rs.getString("rg"));
			medico.setNome(rs.getString("nome"));
			medico.setTelefone(rs.getString("telefone"));
			medico.setEspecialidade(rs.getInt("especialidade"));
			medico.setTurno(rs.getString("turno"));
			
		}
		rs.close();
		ps.close();
		return medico;
	}

	@Override
	public List<Medico> listar() throws SQLException, ClassNotFoundException {
		List<Medico> medicos = new ArrayList<>();
		Connection c = gDao.getConnection();
		String sql = "SELECT m.rg, m.nome, m.especialidade, '(' + SUBSTRING(m.telefone,1,2) + ')'"
				+ " + SUBSTRING(m.telefone,3,5) + '-' + SUBSTRING(m.telefone,8,4) AS tel, e.nome as nomeesp, m.turno"
				+ " FROM Medico m "
				+ " INNER JOIN especialidade e ON m.especialidade = e.codigo";
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Medico medico = new Medico();
			medico.setRg(rs.getString("rg"));
			medico.setNome(rs.getString("nome"));
			medico.setTelefone(rs.getString("tel"));
			medico.setEspecialidade(rs.getInt("especialidade"));
			medico.setNomeEspecialidade(rs.getString("nomeesp"));
			medico.setTurno(rs.getString("turno"));		
			
			medicos.add(medico);
		}
		rs.close();
		ps.close();
		return medicos;
	}


	@Override
	public String inserir(Medico medico) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "{CALL sp_cadastramedico(?,?,?,?,?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, "I");
		cs.setString(2, medico.getRg());
		cs.setString(3, medico.getNome());
		cs.setString(4, medico.getTelefone());
		cs.setString(5, Integer.toString(medico.getEspecialidade()));
		cs.setString(6, medico.getTurno());
		cs.registerOutParameter(7, Types.VARCHAR);
		cs.execute();
		
		String sarga = cs.getString(7);
		cs.close();
		
		return sarga;
	}

	@Override
	public String atualizar(Medico medico) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "{CALL sp_cadastramedico(?,?,?,?,?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, "A");
		cs.setString(2, medico.getRg());
		cs.setString(3, medico.getNome());
		cs.setString(4, medico.getTelefone());
		cs.setString(5, Integer.toString(medico.getEspecialidade()));
		cs.setString(6, medico.getTurno());
		cs.registerOutParameter(7, Types.VARCHAR);
		cs.execute();
		
		String sarga = cs.getString(7);
		cs.close();
		
		return sarga;

	}
	
	@Override
	public String excluir(Medico medico) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "{CALL sp_cadastramedico(?,?,?,?,?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, "D");
		cs.setString(2, medico.getRg());
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
	
}
