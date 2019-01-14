package br.com.desafio.tasklist.service;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.desafio.tasklist.dao.TaskDao;
import br.com.desafio.tasklist.model.Task;

@Path("/api/task")
public class TaskService {

    private final static Logger LOG = Logger.getLogger(TaskService.class.getName());

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTask(Task task) {
	LOG.info("createTask: " + task);
	TaskDao dao = null;
	try {
	    dao = new TaskDao();
	    if (dao.createTask(task)) {
		return Response.ok().status(Response.Status.CREATED).build();
	    }
	} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
	    LOG.log(Level.SEVERE, ex.getMessage(), ex);
	} finally {
	    try {
		dao.closeConnection();
	    } catch (SQLException ex) {
		LOG.log(Level.SEVERE, ex.getMessage(), ex);
	    }
	}
	return Response.notModified().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTasks() {
	LOG.info("getAllTasks: ");
	TaskDao dao = null;
	try {
	    dao = new TaskDao();
	    List<Task> taskList = dao.getAllTasks();
	    return Response.status(Status.OK).entity(getJson(taskList)).build();
	} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
	    LOG.log(Level.SEVERE, ex.getMessage(), ex);
	} finally {
	    try {
		dao.closeConnection();
	    } catch (SQLException ex) {
		LOG.log(Level.SEVERE, ex.getMessage(), ex);
	    }
	}
	return Response.ok().status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response geTaskForId(@PathParam("id") Long id) {
	LOG.info("geTaskForId: " + id);
	TaskDao dao = null;
	try {
	    dao = new TaskDao();
	    Task task = dao.geTaskForId(id);
	    if (task != null) {
		return Response.status(Status.OK).entity(task).build();
	    } else {
		return Response.status(Status.NOT_FOUND).build();
	    }
	} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
	    LOG.log(Level.SEVERE, ex.getMessage(), ex);
	} finally {
	    try {
		dao.closeConnection();
	    } catch (SQLException ex) {
		LOG.log(Level.SEVERE, ex.getMessage(), ex);
	    }
	}
	return Response.status(Status.NOT_FOUND).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTask(Task task) {
	LOG.info("updateTask: " + task);
	TaskDao dao = null;
	try {
	    dao = new TaskDao();
	    if (dao.updateTask(task)) {
		return Response.ok().status(Status.OK).build();
	    }
	} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
	    LOG.log(Level.SEVERE, ex.getMessage(), ex);
	} finally {
	    try {
		dao.closeConnection();
	    } catch (SQLException ex) {
		LOG.log(Level.SEVERE, ex.getMessage(), ex);
	    }
	}
	return Response.notModified().build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTask(@PathParam("id") Long id) {
	LOG.info("deleteTask: " + id);
	TaskDao dao = null;
	try {
	    dao = new TaskDao();
	    if (dao.deleteTask(id)) {
		return Response.ok().status(Status.NO_CONTENT).entity("").build();
	    }
	} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
	    LOG.log(Level.SEVERE, ex.getMessage(), ex);
	} finally {
	    try {
		dao.closeConnection();
	    } catch (SQLException ex) {
		LOG.log(Level.SEVERE, ex.getMessage(), ex);
	    }
	}
	return Response.notModified().build();
    }

    private String getJson(Object object) {
	Gson gson = new GsonBuilder().create();
	String json = gson.toJson(object);
	System.out.println(json);
	return json;
    }

}
