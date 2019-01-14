package br.com.desafio.tasklist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.desafio.tasklist.model.Task;
import br.com.desafio.tasklist.service.TaskService;

public class TaskDao {

    private final static Logger LOG = Logger.getLogger(TaskService.class.getName());
    private Connection con = null;

    public TaskDao() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
	String url = "jdbc:mysql://184.107.22.174:3306/grandevc_tasklist";
	String user = "grandevc_tasks";
	String password = "rEf2b(k5BYbN";

	Class.forName("com.mysql.jdbc.Driver").newInstance();
	con = DriverManager.getConnection(url, user, password);
    }

    public void closeConnection() throws SQLException {
	con.close();
    }

    public boolean createTask(Task task) {
	try {
	    PreparedStatement preparedStatement = con.prepareStatement("INSERT TASK (DT_CRIACAO, TITULO, DESCRICAO, CONCLUIDA)" + " VALUES (?,?,?,?)");
	    preparedStatement.setDate(1, new java.sql.Date(new Date().getTime()));
	    preparedStatement.setString(2, task.getTitulo());
	    preparedStatement.setString(3, task.getDescricao());
	    preparedStatement.setBoolean(4, task.isConcluida());
	    int ins = preparedStatement.executeUpdate();
	    LOG.log(Level.INFO, "ins " + ins);
	    return ins > 0 ? true : false;
	} catch (SQLException ex) {
	    LOG.log(Level.SEVERE, ex.getMessage(), ex);
	}
	return false;
    }

    public List<Task> getAllTasks() {
	List<Task> tasks = new ArrayList<>();
	Task task = null;
	try {
	    PreparedStatement preparedStatement = con.prepareStatement("SELECT ID, DT_CRIACAO, TITULO, DESCRICAO, CONCLUIDA FROM TASK ORDER BY ID");
	    ResultSet rs = preparedStatement.executeQuery();

	    while (rs.next()) {
		task = new Task();
		task.setId(rs.getInt(1));
		task.setDtCriacao(rs.getDate(2));
		task.setTitulo(rs.getString(3));
		task.setDescricao(rs.getString(4));
		task.setConcluida(rs.getBoolean(5));
		tasks.add(task);
	    }
	} catch (SQLException ex) {
	    LOG.log(Level.SEVERE, ex.getMessage(), ex);
	}
	return tasks;
    }

    public Task geTaskForId(Long id) {
	Task task = null;
	try {
	    PreparedStatement preparedStatement = con.prepareStatement("SELECT ID, DT_CRIACAO, TITULO, DESCRICAO, CONCLUIDA FROM TASK WHERE ID = ?");
	    preparedStatement.setLong(1, id);

	    ResultSet rs = preparedStatement.executeQuery();

	    if (rs.next()) {
		task = new Task();
		task.setId(rs.getInt(1));
		task.setDtCriacao(rs.getDate(2));
		task.setTitulo(rs.getString(3));
		task.setDescricao(rs.getString(4));
		task.setConcluida(rs.getBoolean(5));
	    }
	} catch (SQLException ex) {
	    LOG.log(Level.SEVERE, ex.getMessage(), ex);
	}
	return task;
    }

    public boolean updateTask(Task task) {
	try {
	    PreparedStatement preparedStatement = con.prepareStatement("UPDATE TASK SET TITULO = ?, DESCRICAO = ?, CONCLUIDA = ? WHERE ID = ?");
	    preparedStatement.setString(1, task.getTitulo());
	    preparedStatement.setString(2, task.getDescricao());
	    preparedStatement.setBoolean(3, task.isConcluida());
	    preparedStatement.setLong(4, task.getId());
	    int upd = preparedStatement.executeUpdate();
	    return upd > 0 ? true : false;
	} catch (SQLException ex) {
	    LOG.log(Level.SEVERE, ex.getMessage(), ex);
	}
	return false;
    }

    public boolean deleteTask(Long id) {
	try {
	    PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM TASK WHERE ID = ?");
	    preparedStatement.setLong(1, id);
	    int del = preparedStatement.executeUpdate();
	    return del > 0 ? true : false;
	} catch (SQLException ex) {
	    LOG.log(Level.SEVERE, ex.getMessage(), ex);
	}
	return false;
    }
}
