package br.edu.fateczl.Clinica.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.edu.fateczl.Clinica.model.Consulta;



@Repository
public class ConsultaDao  implements ICrud<Consulta>{

	@Autowired
	private GenericDao gDao;

	@Override
	public Consulta buscar(Consulta consulta) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "SELECT codigo, especialidade, data, hora, tipo, categoria FROM Consulta WHERE cliente = ? ";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1,consulta.getClienteRg());
		ResultSet rs = ps.executeQuery();
        
		if (rs.next()) {
			consulta.setCodigo(rs.getInt("codigo"));
			consulta.setEspecialidade(rs.getInt("especialidade"));
			consulta.setData(LocalDate.parse(rs.getString("data")));
			consulta.setHora(LocalTime.parse(rs.getString("hora")));
			consulta.setTipo(rs.getString("tipo"));	
			consulta.setCategoria(rs.getString("categoria"));
			
		}
		rs.close();
		ps.close();
		return consulta;
	}

	@Override
	public List<Consulta> listar() throws SQLException, ClassNotFoundException {
		List<Consulta> consultas = new ArrayList<>();
		Connection c = gDao.getConnection();
		String sql = "SELECT	c.codigo, c.especialidade, e.nome as nomeesp, c.data,"
				+ " CONVERT(CHAR(25), data, 103) AS dtConsulta, c.hora, c.tipo, c.categoria m.nome"
				+ " FROM Consulta c"
				+ " INNER JOIN Especialidade e"
				+ " ON c.especialidade = e.codigo"
				+ " INNER JOIN Medico m"
				+ " ON m.rg = c.medico"
				+ "WHERE c.cliente = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {

			Consulta consulta = new Consulta();
			consulta.setCodigo(rs.getInt("codigo"));
			consulta.setEspecialidade(rs.getInt("especialidade"));
			consulta.setNomeEspecialidade(rs.getString("nomeesp"));
			consulta.setData(LocalDate.parse(rs.getString("data")));
			consulta.setDtConsulta(rs.getString("dtConsulta"));
			consulta.setHora(LocalTime.parse(rs.getString("hora")));
			consulta.setTipo(rs.getString("tipo"));	
			consulta.setCategoria(rs.getString("categoria"));
			
			consultas.add(consulta);
		}
		rs.close();
		ps.close();
		return consultas;
	}


	@Override
	public String inserir(Consulta consulta) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "{CALL sp_cadastraconsulta(?,?,?,?,?,?,?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		
		cs.setString(1, "I");
		cs.setString(2, consulta.getClienteRg());
		cs.setString(3, Integer.toString(consulta.getEspecialidade()));
		cs.setString(4, consulta.getData().toString());
		cs.setString(5, consulta.getHora().toString());
		cs.setString(6, consulta.getTipo());
		cs.setString(7, consulta.getCategoria());
		cs.registerOutParameter(8, Types.INTEGER);
		cs.registerOutParameter(9, Types.VARCHAR);
		cs.execute();
		
		String saida = cs.getString(9);
		cs.close();
		
		return saida;
	}

	@Override
	public String atualizar(Consulta consulta) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "{CALL sp_cadastraconsulta(?,?,?,?,?,?,?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		
		cs.setString(1, "A");
		cs.setString(2, consulta.getClienteRg());
		cs.setString(3, Integer.toString(consulta.getEspecialidade()));
		cs.setString(4, consulta.getData().toString());
		cs.setString(5, consulta.getHora().toString());
		cs.setString(6, consulta.getTipo());
		cs.setString(7, consulta.getCategoria());
		cs.setString(8, Integer.toString(consulta.getCodigo()));
		cs.registerOutParameter(9, Types.VARCHAR);
		cs.execute();
		
		String saida = cs.getString(9);
		cs.close();
		
		return saida;

	}
	
	@Override
	public String excluir(Consulta consulta) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "{CALL sp_cadastraconsulta(?,?,?,?,?,?,?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, "D");
		cs.setNull(2, Types.VARCHAR);
		cs.setNull(3, Types.VARCHAR);
		cs.setNull(4, Types.VARCHAR);
		cs.setNull(5, Types.VARCHAR);
		cs.setNull(6, Types.VARCHAR);
		cs.setNull(7, Types.VARCHAR);
		cs.setString(8, Integer.toString(consulta.getCodigo()));
		cs.registerOutParameter(9, Types.VARCHAR);
		cs.execute();
		
		String saida = cs.getString(9);
		cs.close();
		
		return saida;
	}
	
}
