package controller;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Task;
import util.ConnectionFactory;


public class TaskController {
    
    public void save(Task task) {
        
        String sql = "INSERT INTO tasks (idProject, name,"
                + " description, notes, completed, deadline, "
                + "createdAt, updatedAt) VALUES (?,?,?,?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setString(4,task.getNotes());
            statement.setBoolean(5, task.isIsCompleted());
            statement.setDate(6, new java.sql.Date(task.getDeadline().getTime()));
            statement.setDate(7, new java.sql.Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new java.sql.Date(task.getUpdatedAt().getTime()));            
            statement.execute();   
            
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao salvar a tarefa" +
                    ex.getMessage(),ex);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao fechar a conexão", ex);
            } 
        }
        
    }
    
    public void update(Task task){
        String sql = "UPDATE tasks SET idProject = ?, name = ?,description = ?,"
                + "notes = ?, completed = ?, deadline = ?, createdAt = ?, "
                + "updatedAt = ?, WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            // Estabelecendo a conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            
            //Preparando a query
            statement = connection.prepareStatement(sql);
            
            //Setando os valores de statement  
            
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setString(4, task.getNotes());
            statement.setBoolean(5, task.isIsCompleted());
            statement.setDate(6, new java.sql.Date(task.getDeadline().getTime()));
            statement.setDate(7, new java.sql.Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new java.sql.Date(task.getUpdatedAt().getTime()));
            statement.setInt(9, task.getId());
            
            //Executando a query
            statement.execute();
          
        } catch (SQLException ex){
            throw new RuntimeException("Erro ao atualizar tarefa" + 
                    ex.getMessage(), ex);            
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao fechar a conexão", ex);
            } 
        }
        
    }
    
    public void removeById(int taskId) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, taskId);
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao deletar tarefa", ex);            
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao fechar a conexão", ex);
            }             
        }    
        
    }
    
    public List<Task> getAll(int idProject){
        String sql = "SELECT * FROM tasks WHERE idProject = ?";
        List<Task> tasks = new ArrayList<Task>();
        
        Connection connection = null;
        PreparedStatement statement = null;
        // Cria uma variavel onde vai ser armazenado a o resultado da busca no banco
        ResultSet resultSet = null;
        
       
        try {
            //Iniciando a conexão
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            
            //Setando o valor que corresponde ao filtro de busca:
            statement.setInt(1, idProject);
            
            //Valor retornado pela execução da Query
            resultSet = statement.executeQuery();
            
            //Enquanto houver valores a serem percorridos no meu resultSet
            while(resultSet.next()){
                
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setNotes(resultSet.getString("notes"));
                task.setIsCompleted(resultSet.getBoolean("completed"));
                task.setDeadline(resultSet.getDate("createdAt"));
                task.setUpdatedAt(resultSet.getDate("updatedAt"));
                
                tasks.add(task); 
            }
            
        } catch (SQLException ex){
            throw new RuntimeException("Erro ao inserir tarefa", ex); 
            
        } finally {
          try {
                
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao fechar a conexão", ex);
            }         
        }
        
        // Lista de tarefas criadas e carregadas no banco de dados
        return tasks;
    }
    
}
